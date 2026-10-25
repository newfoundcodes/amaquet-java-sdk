/*
 * Amaquet Java SDK - A Newfoundcodes project.
 *
 * Copyright (C) 2026 Jonathan Eldy Baldivicio
 *
 * Author: Jonathan Eldy Baldivicio
 * Contact: jonathaneldy.baldivicio@newfoundcodes.com
 *
 * SPDX-License-Identifier: MIT
 */

package com.newfoundcodes.amaquet;

import com.sun.net.httpserver.HttpServer;
import java.io.*;
import java.lang.reflect.*;
import java.math.*;
import java.net.*;
import java.net.http.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.*;

/** Zero-library regression suite invoked by the Maven/JUnit test suite. */
public final class SelfTest {
  /** Creates the test entrypoint. */
  public SelfTest() {}

  static int assertions, groups;

  static void yes(boolean ok, String reason) {
    assertions++;
    if (!ok) {
      throw new AssertionError(reason);
    }
  }

  static void equal(Object expected, Object actual) {
    yes(Objects.equals(expected, actual), "expected " + expected + " got " + actual);
  }

  static void throwsType(Class<? extends Throwable> klass, Runnable r) {
    assertions++;

    try {
      r.run();
    } catch (Throwable t) {
      if (klass.isInstance(t)) {
        return;
      }

      throw new AssertionError("Expected " + klass + " got " + t, t);
    }

    throw new AssertionError("expected exception " + klass);
  }

  static void group(String name, Runnable r) {
    r.run();
    groups++;

    System.out.println("PASS " + name);
  }

  /**
   * Runs the self-test suite.
   *
   * @param argv command-line arguments; the self-test does not currently consume them
   * @throws Exception if a self-test operation fails
   */
  public static void main(String[] argv) throws Exception {
    Path root = Path.of(System.getProperty("amaquet.projectRoot", "."));
    group(
        "json-exact-numbers",
        () -> {
          String[] vals = {
            "0",
            "-0",
            "9223372036854775807",
            "18446744073709551615",
            "123456789012345678901234567890",
            "0.0000000000000000000001",
            "1e-100",
            "3.141592653589793238462643383279502884197"
          };

          for (String x : vals) {
            Object v = Json.parse(x);
            Object reread = Json.parse(Json.stringify(v));
            equal(v, reread);
          }

          equal(new BigInteger("18446744073709551615"), Json.parse("18446744073709551615"));
          equal("中文🐋", Json.parse(Json.encode("中文🐋")));
          equal("\"a\"", Json.parse("\"\\\"a\\\"\""));
          equal(Map.of("k", 1), Map.of("k", 1));
          throwsType(IllegalArgumentException.class, () -> Json.parse("{\"x\":1,\"x\":2}"));

          for (String malformed :
              List.of(
                  "NaN",
                  "Infinity",
                  "1e",
                  "01",
                  "[1,]",
                  "{\"a\":}",
                  "\"\\uD800\"",
                  "\"\n\"",
                  "tru",
                  "null true")) {
            throwsType(IllegalArgumentException.class, () -> Json.parse(malformed));
          }

          throwsType(IllegalArgumentException.class, () -> Json.stringify(Double.NaN));
          throwsType(
              IllegalArgumentException.class, () -> Json.stringify(Double.POSITIVE_INFINITY));
        });

    group(
        "frame-boundaries",
        () -> {
          byte[] body = Json.encode(Json.map("x", 1));
          byte[] frame = Protocol.encode(new Protocol.Frame(1, 1, 123, body));
          equal(24 + body.length, frame.length);

          Protocol.Header h = Protocol.header(Arrays.copyOf(frame, 24), Protocol.MAX_PAYLOAD);
          equal(123L, h.requestId());
          equal(1, h.opcode());

          Protocol.Decoder d = new Protocol.Decoder(1024);
          for (int i = 0; i < frame.length - 1; i++) {
            yes(d.feed(new byte[] {frame[i]}).isEmpty(), "partial frame incorrectly decoded");
          }

          equal(1, d.feed(new byte[] {frame[frame.length - 1]}).size());

          byte[] bad = frame.clone();
          bad[0] = '?';

          byte[] badMagic = bad;
          throwsType(
              IllegalArgumentException.class,
              () -> Protocol.header(Arrays.copyOf(badMagic, 24), 1024));
          bad = frame.clone();
          bad[10] = 1;

          byte[] badReserved = bad;
          throwsType(
              IllegalArgumentException.class,
              () -> Protocol.header(Arrays.copyOf(badReserved, 24), 1024));
          bad = frame.clone();
          bad[20] = 127;

          byte[] oversized = bad;
          throwsType(
              IllegalArgumentException.class,
              () -> Protocol.header(Arrays.copyOf(oversized, 24), 1024));

          byte[] both = new byte[frame.length * 2];
          System.arraycopy(frame, 0, both, 0, frame.length);
          System.arraycopy(frame, 0, both, frame.length, frame.length);

          equal(2, new Protocol.Decoder(1024).feed(both).size());
        });

    group(
        "endpoint-and-pins",
        () -> {
          equal("127.0.0.1", Endpoint.parse("amaquet://127.0.0.1").host());
          equal(13378, Endpoint.parse("amaquet://localhost").port());
          equal(true, Endpoint.parse("amaquets://localhost:8123").tls());

          for (String url :
              List.of(
                  "http://localhost",
                  "amaquet://",
                  "amaquet://localhost:99999",
                  "amaquet://localhost/db",
                  "amaquet://localhost?api_key=secret",
                  "amaquet://me:pass@localhost")) {
            throwsType(IllegalArgumentException.class, () -> Endpoint.parse(url));
          }

          equal("secret", Endpoint.parse("amaquet://localhost?api_key=secret", true).apiKey());
          throwsType(IllegalArgumentException.class, () -> AdminClient.pathId("../bad"));
        });

    try (MockServer server = new MockServer()) {
      Client.Options opt =
          new Client.Options()
              .apiKey("test-key")
              .identity(server.publicKey)
              .fingerprint(server.fingerprint)
              .timeout(Duration.ofSeconds(2));

      group(
          "signed-hello-and-auth",
          () -> {
            try (Client c = Client.connect(server.endpoint(), opt)) {
              equal(1, ((Number) c.serverInfo().get("protocol")).intValue());
              yes(c.serverInfo().containsKey("identity_public_key"), "no identity");
            }

            throwsType(
                AmaquetException.Request.class,
                () ->
                    Client.connect(
                        server.endpoint(),
                        new Client.Options().apiKey("bad").requireIdentity(true)));

            throwsType(
                AmaquetException.Protocol.class,
                () ->
                    Client.connect(
                        server.endpoint(),
                        new Client.Options()
                            .apiKey("test-key")
                            .identity("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA=")));
          });

      group(
          "native-commands-and-multiplexing",
          () -> {
            try (Client c = Client.connect(server.endpoint(), opt)) {
              Client.Request first = c.sendCommand("GET", Json.map("key", "slow"));
              Client.Request second = c.sendCommand("GET", Json.map("key", "fast"));

              equal("fast", Json.object(Json.object(c.await(second)).get("args")).get("key"));
              equal("slow", Json.object(Json.object(c.await(first)).get("args")).get("key"));

              for (String command :
                  List.of(
                      "PING",
                      "SET",
                      "CREATE",
                      "CAS",
                      "BATCH",
                      "GET",
                      "DEL",
                      "EXISTS",
                      "TYPE",
                      "TTL",
                      "EXPIRE",
                      "PERSIST",
                      "KEYS",
                      "SCAN",
                      "TYPES",
                      "INFO",
                      "MEMORY",
                      "OP",
                      "BLOB_BEGIN",
                      "BLOB_CHUNK",
                      "BLOB_COMMIT",
                      "BLOB_ABORT",
                      "BLOB_READ")) {

                if (command.startsWith("BLOB")) {
                  continue;
                }

                equal(command, Json.object(c.command(command)).get("command"));
              }

              equal(
                  "SET",
                  Json.object(
                          c.set("k", "unsigned_integer", new BigInteger("18446744073709551615")))
                      .get("command"));
              equal("EXPIRE", Json.object(c.expire("k", 50)).get("command"));
              equal("EXPIRE", Json.object(c.expireAt("k", 184467440737095L)).get("command"));
              equal(
                  "BATCH",
                  Json.object(
                          c.batch(
                              List.of(Json.map("command", "GET", "args", Json.map("key", "k"))),
                              false))
                      .get("command"));
              throwsType(AmaquetException.Request.class, () -> c.command("ERROR_TEST"));

              Client.Request slow = c.sendCommand("GET", Json.map("key", "slow"));
              throwsType(AmaquetException.Timeout.class, () -> c.await(slow, Duration.ofMillis(1)));
              yes(c.ping() != null, "connection unusable after cancellation");

              CompletableFuture<Object> pending = c.asyncCommand("INFO", Map.of());
              yes(pending.join() != null, "async result absent");
            }
          });

      group(
          "all-91-types-and-242-op-methods",
          () -> {
            try (Client c = Client.connect(server.endpoint(), opt)) {
              TypedHandles typed = c.typed().generated();
              int count = 0, ops = 0;

              for (var entry : TypedHandles.CATALOG.entrySet()) {
                String type = entry.getKey();
                TypeHandle h = c.typed().forType(type, "example:" + type);

                equal(type, h.wireType);
                count++;

                Path file = root.resolve("examples/scenarios/" + type + ".json");
                Map<String, Object> scenario;

                try {
                  scenario = Json.object(Json.parse(Files.readAllBytes(file)));
                } catch (IOException e) {
                  throw new RuntimeException(e);
                }

                Map<String, Object> construction = Json.object(scenario.get("construction"));
                equal(
                    construction.get("command"),
                    Json.object(
                            c.command(
                                (String) construction.get("command"),
                                Json.object(construction.get("args"))))
                        .get("command"));

                try {
                  String className =
                      Arrays.stream(type.split("_"))
                          .map(x -> x.substring(0, 1).toUpperCase() + x.substring(1))
                          .reduce("", String::concat);
                  Class<?> cls =
                      Class.forName("com.newfoundcodes.amaquet.TypedHandles$" + className);

                  Constructor<?> ct = cls.getConstructor(Client.class, String.class);
                  Object obj = ct.newInstance(c, "example:" + type);

                  for (Object operation : (List<?>) scenario.get("operations")) {
                    Map<String, Object> outer = Json.object(operation),
                        inner = Json.object(outer.get("args"));
                    String op = (String) inner.get("operation");
                    Map<String, Object> args = Json.object(inner.get("args"));
                    String[] words = op.toLowerCase(Locale.ROOT).split("_");
                    String method = words[0];

                    for (int j = 1; j < words.length; j++) {
                      method += words[j].substring(0, 1).toUpperCase() + words[j].substring(1);
                    }

                    Method m = cls.getMethod(method, Map.class);
                    Object result = m.invoke(obj, args);
                    Map<String, Object> call = Json.object(result);
                    equal("OP", call.get("command"));

                    Map<String, Object> wire = Json.object(call.get("args"));
                    equal(op, wire.get("operation"));
                    equal(args, wire.get("args"));

                    ops++;
                  }
                } catch (ReflectiveOperationException e) {
                  throw new RuntimeException("type=" + type, e);
                }
              }

              equal(91, count);
              equal(242, ops);
            }
          });

      group(
          "pubsub-overflow",
          () -> {
            try (Client c = Client.connect(server.endpoint(), opt);
                Subscription sub = c.subscribe("bus", "changes", 12, 2)) {
              long deadline = System.nanoTime() + TimeUnit.SECONDS.toNanos(2);
              while (sub.dropped() < 8 && System.nanoTime() < deadline) {
                try {
                  Thread.sleep(10);
                } catch (InterruptedException e) {
                  Thread.currentThread().interrupt();
                }
              }

              yes(sub.dropped() >= 8, "expected bounded queue drops");
              yes(sub.next(Duration.ofMillis(50)) != null, "expected event");

              equal(true, sub.unsubscribe());
              equal(true, sub.isClosed());
            }
          });

      group(
          "blob-upload-and-download",
          () -> {
            byte[] data = new byte[100_333];
            new java.util.Random(42).nextBytes(data);

            try (Client c = Client.connect(server.endpoint(), opt)) {
              c.uploadBlob(
                  "example:blob", new ByteArrayInputStream(data), data.length, 4000, false);

              yes(Arrays.equals(data, server.data("example:blob")), "upload corruption");
              yes(
                  Arrays.equals(data, c.downloadBlobBytes("example:blob", 200000)),
                  "download corruption");

              throwsType(
                  AmaquetException.Protocol.class, () -> c.downloadBlobBytes("example:blob", 1000));
              throwsType(IllegalArgumentException.class, () -> c.blobChunk("id", 0, new byte[0]));
              throwsType(IllegalArgumentException.class, () -> c.blobReadRange("k", -1, 5));
            }
          });

      group(
          "pending-request-backpressure",
          () -> {
            Client.Options bounded =
                new Client.Options()
                    .apiKey("test-key")
                    .maxInFlight(1)
                    .timeout(Duration.ofSeconds(2));

            try (Client c = Client.connect(server.endpoint(), bounded)) {
              Client.Request slow = c.sendCommand("GET", Json.map("key", "slow"));
              throwsType(AmaquetException.class, () -> c.sendCommand("INFO", Map.of()));

              yes(c.await(slow) != null, "slow request did not complete");
              yes(c.ping() != null, "pending request limit did not release");
            }
          });

      group(
          "connection-pool",
          () -> {
            try (ClientPool pool = new ClientPool(server.endpoint(), 3, opt)) {
              yes(pool.acquire() != pool.acquire(), "pool not rotating");
              equal("PING", Json.object(pool.command("PING", Map.of())).get("command"));

              pool.replace(1);
              equal("PING", Json.object(pool.command("PING", Map.of())).get("command"));
            }
          });
    }

    group(
        "HTTP-all-33-routes",
        () -> {
          try {
            HttpServer http =
                HttpServer.create(new InetSocketAddress(InetAddress.getLoopbackAddress(), 0), 0);
            List<String> seen = Collections.synchronizedList(new ArrayList<>());

            http.createContext(
                "/",
                exchange -> {
                  byte[] bytes = exchange.getRequestBody().readAllBytes();
                  seen.add(exchange.getRequestMethod() + " " + exchange.getRequestURI().getPath());

                  String path = exchange.getRequestURI().getPath();
                  if (path.equals("/redirect")) {
                    exchange.getResponseHeaders().set("Location", "http://example.org/");
                    exchange.sendResponseHeaders(302, -1);
                    exchange.close();
                    return;
                  }

                  if (path.equals("/error")) {
                    byte[] err =
                        Json.encode(
                            Json.map(
                                "error",
                                Json.map("code", "TEST_ERROR", "message", "controlled error")));
                    exchange.sendResponseHeaders(400, err.length);

                    try (var stream = exchange.getResponseBody()) {
                      stream.write(err);
                    }

                    return;
                  }

                  if (path.equals("/large")) {
                    byte[] large = new byte[1000];
                    Arrays.fill(large, (byte) 'x');
                    exchange.sendResponseHeaders(200, large.length);

                    try (var stream = exchange.getResponseBody()) {
                      stream.write(large);
                    }

                    return;
                  }

                  if (!path.equals("/metrics")) {
                    yes(
                        exchange
                            .getRequestHeaders()
                            .getFirst("Authorization")
                            .equals("Bearer test-admin"),
                        "admin auth header wrong");
                  }

                  byte[] result =
                      path.equals("/metrics")
                          ? "amqt_up 1\n".getBytes(StandardCharsets.UTF_8)
                          : Json.encode(
                              Json.map(
                                  "ok",
                                  true,
                                  "method",
                                  exchange.getRequestMethod(),
                                  "path",
                                  path,
                                  "body",
                                  bytes.length == 0 ? null : Json.parse(bytes)));

                  if (path.equals("/api/session") && exchange.getRequestMethod().equals("POST")) {
                    exchange
                        .getResponseHeaders()
                        .add("Set-Cookie", "amaquet_session=secret; HttpOnly; Path=/");
                  }

                  exchange.sendResponseHeaders(200, result.length);
                  try (var stream = exchange.getResponseBody()) {
                    stream.write(result);
                  }
                });

            http.start();
            try {
              AdminClient a =
                  new AdminClient("http://127.0.0.1:" + http.getAddress().getPort(), "test-admin");
              a.openapi();
              a.health();
              a.ready();
              equal("amqt_up 1\n", a.metrics());

              a.checkAuth();
              a.createSession("token", "123456");
              a.deleteSession();
              a.acceptInvite("invite");
              a.beginMfa();
              a.confirmMfa("1234");
              a.disableMfa("1234");
              a.overview();
              a.organization();
              a.updateOrganization("name", "slug");
              a.members();
              a.addMember("name", "email", "viewer");
              a.patchMember("with spaces", "viewer", null);
              a.deleteMember("m");
              a.inviteMember("m", 12);
              a.rbac();
              a.updateRbac(Map.of());
              a.apiKeys();
              a.createApiKey("api", "admin", null);
              a.revokeApiKey("k");
              a.system();
              a.updateSystem(Map.of());
              a.checkpoint("c");
              a.compact();
              a.dataKeys("", "", 100);
              a.dataKey("sp ace");
              a.deleteDataKey("key");
              a.dataCommand("PING", Map.of());
              a.audit(100);

              equal(33, seen.size());

              yes(seen.contains("POST /api/session"), "missing admin session");
              yes(
                  seen.contains("PATCH /api/members/with spaces")
                      || seen.contains("PATCH /api/members/with%20spaces"),
                  "missing URL-encoded member");

              throwsType(IllegalArgumentException.class, () -> a.request("GET", "/api/../secret"));
              throwsType(
                  IllegalArgumentException.class, () -> a.request("GET", "//evil.test/path"));
              throwsType(
                  IllegalArgumentException.class, () -> a.request("GET", "/api/%2e%2e/private"));
              throwsType(AmaquetException.Http.class, () -> a.request("GET", "/redirect"));
              throwsType(AmaquetException.Http.class, () -> a.request("GET", "/error"));

              AdminClient limited =
                  new AdminClient(
                      "http://127.0.0.1:" + http.getAddress().getPort(),
                      "test-admin",
                      "Authorization",
                      Duration.ofSeconds(2),
                      20,
                      true);
              throwsType(AmaquetException.Protocol.class, () -> limited.request("GET", "/large"));
            } finally {
              http.stop(0);
            }
          } catch (IOException e) {
            throw new RuntimeException(e);
          }
        });

    System.out.println("PASS " + groups + " test groups, " + assertions + " assertions");
  }
}
