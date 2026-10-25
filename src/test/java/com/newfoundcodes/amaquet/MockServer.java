package com.newfoundcodes.amaquet;

import java.io.*;
import java.math.*;
import java.net.*;
import java.security.*;
import java.security.interfaces.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;

/** Controlled native Amaquet test server, deliberately supports out-of-order replies. */
public final class MockServer implements AutoCloseable {
  private final ServerSocket server;
  private final Thread acceptor;
  private final ExecutorService workers = Executors.newCachedThreadPool();
  private final KeyPair identity;
  private final AtomicBoolean active = new AtomicBoolean(true);

  /** Number of accepted client connections. */
  public final AtomicInteger connections = new AtomicInteger();
  private final ConcurrentHashMap<String, byte[]> uploaded = new ConcurrentHashMap<>();
  private final ConcurrentHashMap<String, ByteArrayOutputStream> uploads =
      new ConcurrentHashMap<>();

  /** Base64-encoded raw Ed25519 public key advertised by the server. */
  public final String publicKey;

  /** SHA-256 fingerprint advertised by the server. */
  public final String fingerprint;

  /**
   * Creates a loopback server without TLS.
   *
   * @throws Exception if the test server cannot create its identity or listening socket
   */
  public MockServer() throws Exception {
    this(null);
  }

  /**
   * Creates a loopback server using the supplied TLS context.
   *
   * @param tls TLS context, or {@code null} for a plain TCP server
   * @throws Exception if the test server cannot create its identity or listening socket
   */
  public MockServer(javax.net.ssl.SSLContext tls) throws Exception {
    identity = KeyPairGenerator.getInstance("Ed25519").generateKeyPair();

    byte[] encoded = identity.getPublic().getEncoded();
    byte[] raw = Arrays.copyOfRange(encoded, encoded.length - 32, encoded.length);

    publicKey = Base64.getEncoder().encodeToString(raw);
    fingerprint =
        "ed25519:" + HexFormat.of().formatHex(MessageDigest.getInstance("SHA-256").digest(raw));
    server =
        tls == null
            ? new ServerSocket(0, 64, InetAddress.getLoopbackAddress())
            : tls.getServerSocketFactory()
                .createServerSocket(0, 64, InetAddress.getLoopbackAddress());
    acceptor =
        new Thread(
            () -> {
              while (active.get()) {
                try {
                  Socket client = server.accept();

                  connections.incrementAndGet();
                  workers.execute(() -> serve(client));
                } catch (IOException e) {
                  if (active.get()) {
                    e.printStackTrace();
                  }
                }
              }
            },
            "amaquet-test-accept");

    acceptor.setDaemon(true);
    acceptor.start();
  }

  /**
   * Returns the native Amaquet URI for this server.
   *
   * @return native Amaquet URI
   */
  public String endpoint() {
    return "amaquet://127.0.0.1:" + server.getLocalPort();
  }

  /**
   * Seeds a blob value used by blob-read tests.
   *
   * @param key blob key
   * @param bytes blob contents
   */
  public void seed(String key, byte[] bytes) {
    uploaded.put(key, bytes);
  }

  /**
   * Returns the seeded blob bytes.
   *
   * @param key blob key
   * @return seeded blob contents, or {@code null}
   */
  public byte[] data(String key) {
    return uploaded.get(key);
  }

  private void serve(Socket socket) {
    try (socket;
        DataInputStream in = new DataInputStream(socket.getInputStream())) {
      OutputStream out = socket.getOutputStream();
      Object mutex = new Object();

      while (!socket.isClosed()) {
        byte[] hdr = new byte[24];
        in.readFully(hdr);

        Protocol.Header h = Protocol.header(hdr, Protocol.MAX_PAYLOAD);
        byte[] payload = new byte[h.size()];
        in.readFully(payload);

        Map<String, Object> req = Json.object(Json.parse(payload));
        Runnable job =
            () -> {
              try {
                if (h.opcode() == Protocol.HELLO) {
                  String nonce = (String) req.get("nonce");
                  Signature signature = Signature.getInstance("Ed25519");

                  signature.initSign(identity.getPrivate());
                  signature.update(nonce.getBytes(java.nio.charset.StandardCharsets.UTF_8));

                  String signed = Base64.getEncoder().encodeToString(signature.sign());
                  respond(
                      out,
                      mutex,
                      h,
                      Json.map(
                          "protocol",
                          1,
                          "selected_protocol",
                          1,
                          "auth_required",
                          true,
                          "identity_public_key",
                          publicKey,
                          "identity_fingerprint",
                          fingerprint,
                          "nonce_signature",
                          signed));
                } else if (h.opcode() == Protocol.AUTH) {
                  if (!"test-key".equals(req.get("api_key"))) {
                    error(out, mutex, h, "AUTH_FAILED", "invalid api key");
                  } else {
                    respond(out, mutex, h, Json.map("authorized", true));
                  }
                } else if (h.opcode() == Protocol.SUBSCRIBE) {
                  respond(out, mutex, h, Json.map("subscribed", true));

                  for (int i = 0; i < 12; i++) {
                    byte[] frame =
                        Protocol.encode(
                            new Protocol.Frame(
                                Protocol.EVENT,
                                Protocol.SUBSCRIBE,
                                h.requestId(),
                                Json.encode(
                                    Json.map(
                                        "channel",
                                        req.get("channel"),
                                        "payload",
                                        i,
                                        "published_at",
                                        "2026-01-01T00:00:00Z"))));

                    synchronized (mutex) {
                      out.write(frame);
                      out.flush();
                    }
                  }
                } else if (h.opcode() == Protocol.UNSUBSCRIBE) {
                  respond(out, mutex, h, Json.map("unsubscribed", true));
                } else if (h.opcode() == Protocol.CANCEL) {
                  respond(
                      out, mutex, h, Json.map("request_id", req.get("request_id"), "found", true));
                } else if (h.opcode() == Protocol.COMMAND) {
                  String cmd = String.valueOf(req.get("command"));
                  Map<String, Object> args = Json.object(req.get("args"));

                  if (cmd.equals("GET") && "slow".equals(args.get("key"))) {
                    Thread.sleep(220);
                  }

                  if (cmd.equals("BLOB_BEGIN")) {
                    String key = (String) args.get("key"), id = (String) args.get("upload_id");

                    uploads.put(id, new ByteArrayOutputStream());
                    respond(out, mutex, h, Json.map("upload_id", id, "key", key));
                  } else if (cmd.equals("BLOB_CHUNK")) {
                    String id = (String) args.get("upload_id");
                    byte[] data = Base64.getDecoder().decode((String) args.get("data"));
                    ByteArrayOutputStream buf = uploads.get(id);

                    buf.write(data);
                    respond(out, mutex, h, Json.map("offset", args.get("offset")));
                  } else if (cmd.equals("BLOB_COMMIT")) {
                    String id = (String) args.get("upload_id");
                    ByteArrayOutputStream buf = uploads.remove(id);

                    uploaded.put("example:blob", buf.toByteArray());
                    respond(out, mutex, h, true);
                  } else if (cmd.equals("BLOB_ABORT")) {
                    uploads.remove((String) args.get("upload_id"));
                    respond(out, mutex, h, true);
                  } else if (cmd.equals("BLOB_READ")) {
                    String key = (String) args.get("key");
                    byte[] data = uploaded.get(key);

                    if (data == null) {
                      error(out, mutex, h, "NOT_FOUND", "no blob");
                      return;
                    }

                    int offset = ((Number) args.get("offset")).intValue(),
                        size = ((Number) args.get("length")).intValue();
                    byte[] slice =
                        Arrays.copyOfRange(data, offset, Math.min(data.length, offset + size));

                    respond(
                        out,
                        mutex,
                        h,
                        Json.map(
                            "key",
                            key,
                            "offset",
                            offset,
                            "size",
                            data.length,
                            "version",
                            1,
                            "data",
                            Base64.getEncoder().encodeToString(slice)));
                  } else if (cmd.equals("ERROR_TEST")) {
                    error(out, mutex, h, "BAD_TEST", "controlled failure");
                  } else {
                    respond(out, mutex, h, req);
                  }
                }
              } catch (Exception e) {
                if (!(e instanceof java.net.SocketException)) {
                  e.printStackTrace();
                }

                try {
                  socket.close();
                } catch (IOException ignore) {
                }
              }
            };

        if (h.opcode() == Protocol.COMMAND) {
          workers.execute(job);
        } else {
          job.run();
        }
      }
    } catch (IOException ignored) {
    }
  }

  private static void respond(OutputStream out, Object lock, Protocol.Header h, Object result)
      throws IOException {
    write(out, lock, h, Json.map("ok", true, "result", result));
  }

  private static void error(
      OutputStream out, Object lock, Protocol.Header h, String code, String message)
      throws IOException {
    write(out, lock, h, Json.map("ok", false, "error", Json.map("code", code, "message", message)));
  }

  private static void write(OutputStream out, Object lock, Protocol.Header h, Object body)
      throws IOException {
    byte[] raw =
        Protocol.encode(
            new Protocol.Frame(Protocol.RESPONSE, h.opcode(), h.requestId(), Json.encode(body)));

    synchronized (lock) {
      out.write(raw);
      out.flush();
    }
  }

  /** Stops accepting connections and shuts down worker threads. */
  @Override
  public void close() {
    active.set(false);

    try {
      server.close();
    } catch (IOException ignored) {
    }

    workers.shutdownNow();
  }
}
