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

import java.io.*;
import java.net.*;
import java.security.SecureRandom;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import javax.net.ssl.*;

/**
 * Thread-safe TCP/TLS Amaquet protocol v1 client.
 *
 * <p>A client owns one socket, performs the protocol handshake and authentication, dispatches
 * replies to pending requests, and routes subscription events. Mutating operations are sent once;
 * this class does not retry them automatically.
 *
 * <p>Use TLS and identity pins for remote endpoints. A client should be closed when it is no
 * longer needed so its reader thread, socket, and pending requests are released.
 */
public final class Client implements AutoCloseable {
  /** Maximum blob size accepted by the client, in bytes. */
  public static final int MAX_BLOB_SIZE = 1 << 30;

  /** Maximum single blob chunk accepted by the client, in bytes. */
  public static final int MAX_BLOB_CHUNK = 32 << 20;

  /** Default chunk size used by blob upload and download helpers, in bytes. */
  public static final int DEFAULT_CHUNK = 1 << 20;

  /** Optional connection, authentication, timeout, and payload settings for a {@link Client}. */
  public static final class Options {
    /** Creates options with the SDK defaults. */
    public Options() {}

    /** API key sent during authentication, or {@code null} when authentication is not configured. */
    public String apiKey;

    /** Expected server identity public key in the SDK's base64 representation. */
    public String expectedIdentityKey;

    /** Expected server identity fingerprint. */
    public String expectedIdentityFingerprint;

    /** Whether the connection must present a server identity matching the configured expectation. */
    public boolean requireIdentity;

    /** Whether credentials embedded in the endpoint URI may be used. */
    public boolean allowUriSecrets;

    /** Overall request timeout. */
    public Duration timeout = Duration.ofSeconds(30);

    /** Socket connection timeout. */
    public Duration connectTimeout = Duration.ofSeconds(10);

    /** Maximum protocol payload accepted from the server, in bytes. */
    public int maxPayload = Protocol.MAX_PAYLOAD;

    /** Maximum number of requests allowed to remain in flight. */
    public int maxInFlight = 4096;

    /** Optional socket factory used for TLS connections. */
    public SSLSocketFactory tlsFactory;

    /**
     * Performs the corresponding documented Amaquet operation.
     * Use TLS and identity pins for remote endpoints; close resources and bound pending requests in long-running applications.
     * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
     * @param x value supplied for x.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Options apiKey(String x) {
      apiKey = x;
      return this;
    }

    /**
     * Performs the corresponding documented Amaquet operation.
     * Use TLS and identity pins for remote endpoints; close resources and bound pending requests in long-running applications.
     * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
     * @param x value supplied for x.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Options timeout(Duration x) {
      timeout = x;
      return this;
    }

    /**
     * Performs the corresponding documented Amaquet operation.
     * Use TLS and identity pins for remote endpoints; close resources and bound pending requests in long-running applications.
     * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
     * @param x value supplied for x.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Options connectTimeout(Duration x) {
      connectTimeout = x;
      return this;
    }

    /**
     * Performs the corresponding documented Amaquet operation.
     * Use TLS and identity pins for remote endpoints; close resources and bound pending requests in long-running applications.
     * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
     * @param x value supplied for x.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Options maxPayload(int x) {
      maxPayload = x;
      return this;
    }

    /**
     * Performs the corresponding documented Amaquet operation.
     * Use TLS and identity pins for remote endpoints; close resources and bound pending requests in long-running applications.
     * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
     * @param x value supplied for x.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Options maxInFlight(int x) {
      maxInFlight = x;
      return this;
    }

    /**
     * Performs the corresponding documented Amaquet operation.
     * Use TLS and identity pins for remote endpoints; close resources and bound pending requests in long-running applications.
     * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
     * @param base64Key value supplied for base64Key.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Options identity(String base64Key) {
      requireIdentity = true;
      expectedIdentityKey = base64Key;

      return this;
    }

    /**
     * Performs the corresponding documented Amaquet operation.
     * Use TLS and identity pins for remote endpoints; close resources and bound pending requests in long-running applications.
     * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
     * @param pin value supplied for pin.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Options fingerprint(String pin) {
      requireIdentity = true;
      expectedIdentityFingerprint = pin;

      return this;
    }

    /**
     * Performs the corresponding documented Amaquet operation.
     * Use TLS and identity pins for remote endpoints; close resources and bound pending requests in long-running applications.
     * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
     * @param x value supplied for x.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Options requireIdentity(boolean x) {
      requireIdentity = x;
      return this;
    }

    /**
     * Performs the corresponding documented Amaquet operation.
     * Use TLS and identity pins for remote endpoints; close resources and bound pending requests in long-running applications.
     * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
     * @param x value supplied for x.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Options tlsFactory(SSLSocketFactory x) {
      tlsFactory = x;
      return this;
    }
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * Use TLS and identity pins for remote endpoints; close resources and bound pending requests in long-running applications.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   * @param opcode value supplied for opcode.
   * @param future value supplied for future.
   */
  private record Pending(int opcode, CompletableFuture<Object> future) {}

  /**
   * A submitted operation and the future that receives its matching response.
   *
   * @param id protocol request identifier
   * @param opcode protocol operation code
   * @param future future completed with the decoded response or an exception
   */
  public record Request(long id, int opcode, CompletableFuture<Object> future) {}

  private final Socket socket;
  private final DataInputStream input;
  private final OutputStream output;
  private final Options options;
  private final Object writeMutex = new Object(), subscribeMutex = new Object();
  private final AtomicLong ids = new AtomicLong(1);
  private final ConcurrentHashMap<Long, Pending> pending = new ConcurrentHashMap<>();
  private final ConcurrentHashMap<Long, Subscription> subscriptions = new ConcurrentHashMap<>();
  private final ConcurrentHashMap<String, Long> subscriptionTargets = new ConcurrentHashMap<>();
  private final AtomicBoolean closed = new AtomicBoolean();

  private volatile Map<String, Object> serverInfo = Map.of();
  private volatile AmaquetException failure;

  private final Thread reader;

  private Client(Socket socket, Options options) throws IOException {
    this.socket = socket;
    this.options = options;
    this.input = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
    this.output = socket.getOutputStream();
    this.reader = new Thread(this::readLoop, "amaquet-reader");
    this.reader.setDaemon(true);
    this.reader.start();
  }

  /**
   * Opens a connection using default client options.
   *
   * @param uri native Amaquet URI; use {@code amaquets://} for TLS
   * @return a connected client
   * @throws AmaquetException if the endpoint cannot be reached or the handshake fails
   */
  public static Client connect(String uri) {
    return connect(uri, new Options());
  }

  /**
   * Opens a connection, negotiates protocol version 1, verifies the configured server identity,
   * and authenticates before returning a usable client.
   *
   * @param uri native Amaquet URI; use {@code amaquets://} for TLS
   * @param options connection, authentication, timeout, and payload settings
   * @return a connected client
   * @throws AmaquetException if the endpoint cannot be reached or the handshake fails
   */
  public static Client connect(String uri, Options options) {
    Objects.requireNonNull(options);
    Endpoint endpoint = Endpoint.parse(uri, options.allowUriSecrets);

    if (options.timeout == null
        || options.timeout.isZero()
        || options.timeout.isNegative()
        || options.connectTimeout == null
        || options.connectTimeout.isZero()
        || options.connectTimeout.isNegative()
        || options.maxPayload < 1
        || options.maxPayload > Protocol.MAX_PAYLOAD
        || options.maxInFlight < 1
        || options.maxInFlight > 100_000) {
      throw new IllegalArgumentException("invalid client options");
    }

    Socket socket = new Socket();
    try {
      socket.connect(
          new InetSocketAddress(endpoint.host(), endpoint.port()),
          Math.max(1, Math.toIntExact(options.connectTimeout.toMillis())));

      if (endpoint.tls()) {
        SSLSocketFactory factory =
            options.tlsFactory != null
                ? options.tlsFactory
                : (SSLSocketFactory) SSLSocketFactory.getDefault();
        SSLSocket tls =
            (SSLSocket) factory.createSocket(socket, endpoint.host(), endpoint.port(), true);
        SSLParameters params = tls.getSSLParameters();
        params.setEndpointIdentificationAlgorithm("HTTPS");

        tls.setSSLParameters(params);
        tls.startHandshake();

        socket = tls;
      } else if (options.tlsFactory != null) {
        throw new IllegalArgumentException("TLS factory requires amaquets://");
      }

      Client client = new Client(socket, options);
      try {
        byte[] nonceBytes = new byte[32];
        new SecureRandom().nextBytes(nonceBytes);

        String nonce = Base64.getEncoder().withoutPadding().encodeToString(nonceBytes);
        Object response =
            client.requestOpcode(
                Protocol.HELLO, Json.map("nonce", nonce, "min_protocol", 1, "max_protocol", 1));
        Map<String, Object> hello = Json.object(response);

        if (!numberIs(hello.get("protocol"), 1)
            || !numberIs(hello.getOrDefault("selected_protocol", 1), 1)) {
          throw new AmaquetException.Protocol("unsupported Amaquet protocol");
        }

        Identity.verify(
            hello,
            nonce,
            options.requireIdentity,
            options.expectedIdentityKey,
            options.expectedIdentityFingerprint);
        client.serverInfo = Collections.unmodifiableMap(new LinkedHashMap<>(hello));

        String apiKey = options.apiKey != null ? options.apiKey : endpoint.apiKey();
        if (Boolean.TRUE.equals(hello.get("auth_required")) && apiKey == null) {
          throw new AmaquetException.Protocol("server requires an API key");
        }

        if (apiKey != null) {
          client.requestOpcode(Protocol.AUTH, Json.map("api_key", apiKey));
        }
        return client;

      } catch (RuntimeException e) {
        client.close();
        throw e;
      }
    } catch (IOException e) {
      try {
        socket.close();
      } catch (IOException ignore) {
      }

      throw new AmaquetException.Transport("failed to connect to Amaquet", e);
    } catch (RuntimeException e) {
      try {
        socket.close();
      } catch (IOException ignore) {
      }

      throw e;
    }
  }

  private static boolean numberIs(Object x, long value) {
    return x instanceof Number
        && new java.math.BigDecimal(x.toString()).compareTo(java.math.BigDecimal.valueOf(value))
            == 0;
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * Use TLS and identity pins for remote endpoints; close resources and bound pending requests in long-running applications.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   * @return The server response or computed SDK result; exact shape depends on the operation.
   */
  public Map<String, Object> serverInfo() {
    return serverInfo;
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * Use TLS and identity pins for remote endpoints; close resources and bound pending requests in long-running applications.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   * @return The server response or computed SDK result; exact shape depends on the operation.
   */
  public boolean isClosed() {
    return closed.get();
  }

  private void readLoop() {
    try {
      byte[] header = new byte[Protocol.HEADER];

      while (!closed.get()) {
        input.readFully(header);
        Protocol.Header h;

        try {
          h = Protocol.header(header, options.maxPayload);
        } catch (IllegalArgumentException e) {
          throw new AmaquetException.Protocol("malformed frame header", e);
        }

        byte[] data = new byte[h.size()];
        input.readFully(data);

        receive(new Protocol.Frame(h.kind(), h.opcode(), h.requestId(), h.flags(), data));
      }
    } catch (Exception e) {
      shutdown(
          e instanceof AmaquetException a
              ? a
              : new AmaquetException.Transport("socket read failed", e));
    }
  }

  private void receive(Protocol.Frame frame) {
    Object body;
    try {
      body = Json.parse(frame.payload());
    } catch (IllegalArgumentException e) {
      throw new AmaquetException.Protocol("malformed JSON response", e);
    }

    if (frame.kind() == Protocol.EVENT) {
      if (frame.opcode() != Protocol.SUBSCRIBE || !(body instanceof Map<?, ?>)) {
        throw new AmaquetException.Protocol("invalid event frame");
      }

      Subscription sub = subscriptions.get(frame.requestId());
      if (sub != null) {
        sub.deliver(Json.object(body));
      }

      return;
    }

    if (frame.kind() != Protocol.RESPONSE) {
      throw new AmaquetException.Protocol("unexpected non-response frame");
    }

    Pending p = pending.get(frame.requestId());
    if (p == null) {
      return; // timed out or intentionally discarded reply
    }

    if ((frame.opcode() != Protocol.COMMAND
            && frame.opcode() != p.opcode())
        || p.future().isDone()) {
      throw new AmaquetException.Protocol("response mismatch/duplicate");
    }

    if (!(body instanceof Map<?, ?>)) {
      throw new AmaquetException.Protocol("invalid response envelope");
    }

    Map<String, Object> env = Json.object(body);
    if (!(env.get("ok") instanceof Boolean ok)) {
      throw new AmaquetException.Protocol("missing response status");
    }

    if (ok) {
      p.future().complete(env.get("result"));
    } else {
      if (!(env.get("error") instanceof Map<?, ?>)) {
        throw new AmaquetException.Protocol("invalid error envelope");
      }

      Map<String, Object> error = Json.object(env.get("error"));
      p.future()
          .completeExceptionally(
              new AmaquetException.Request(
                  String.valueOf(error.getOrDefault("code", "ERROR")),
                  String.valueOf(error.getOrDefault("message", "request failed"))));
    }
  }

  /**
   * Send using the implementation's validation, error, and resource-lifetime rules.
   * Use TLS and identity pins for remote endpoints; close resources and bound pending requests in long-running applications.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   * @param opcode value supplied for opcode.
   * @param request value supplied for request.
   * @return The server response or computed SDK result; exact shape depends on the operation.
   */
  public Request send(int opcode, Object request) {
    return send(opcode, request, null);
  }

  private Request send(int opcode, Object request, java.util.function.LongConsumer beforeWrite) {
    byte[] body = Json.encode(request);
    if (body.length > options.maxPayload) {
      throw new IllegalArgumentException("request exceeds max payload");
    }

    synchronized (writeMutex) {
      if (closed.get()) {
        throw failure == null ? new AmaquetException("connection closed") : failure;
      }

      if (pending.size() >= options.maxInFlight) {
        throw new AmaquetException("too many pending requests");
      }

      long id = ids.getAndIncrement();
      if (id <= 0) {
        throw new IllegalStateException("request ID range exhausted");
      }

      CompletableFuture<Object> future = new CompletableFuture<>();
      Pending p = new Pending(opcode, future);
      pending.put(id, p);

      try {
        if (beforeWrite != null) {
          beforeWrite.accept(id);
        }

        byte[] frame =
            Protocol.encode(
                new Protocol.Frame(Protocol.REQUEST, opcode, id, body), options.maxPayload);
        output.write(frame);
        output.flush();

        return new Request(id, opcode, future);
      } catch (IOException e) {
        pending.remove(id);
        shutdown(new AmaquetException.Transport("socket write failed", e));

        throw new AmaquetException.Transport("socket write failed", e);
      } catch (RuntimeException e) {
        pending.remove(id);
        throw e;
      }
    }
  }

  /**
   * Send Command using the implementation's validation, error, and resource-lifetime rules.
   * Use TLS and identity pins for remote endpoints; close resources and bound pending requests in long-running applications.
   * @param args Exact JSON argument properties supported by the server; names are not translated.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   * @param command value supplied for command.
   * @return The server response or computed SDK result; exact shape depends on the operation.
   */
  public Request sendCommand(String command, Map<String, Object> args) {
    return send(
        Protocol.COMMAND,
        Json.map(
            "command", command.toUpperCase(Locale.ROOT), "args", args == null ? Map.of() : args));
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * Use TLS and identity pins for remote endpoints; close resources and bound pending requests in long-running applications.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   * @param request value supplied for request.
   * @return The server response or computed SDK result; exact shape depends on the operation.
   */
  public Object await(Request request) {
    return await(request, options.timeout);
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * Use TLS and identity pins for remote endpoints; close resources and bound pending requests in long-running applications.
   * @param timeout Time budget for the request; timeout does not guarantee server-side cancellation.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   * @param request value supplied for request.
   * @return The server response or computed SDK result; exact shape depends on the operation.
   */
  public Object await(Request request, Duration timeout) {
    if (timeout == null || timeout.isNegative() || timeout.isZero()) {
      throw new IllegalArgumentException("invalid timeout");
    }

    try {
      return request.future().get(timeout.toNanos(), TimeUnit.NANOSECONDS);
    } catch (java.util.concurrent.TimeoutException e) {
      if (request.opcode() == Protocol.COMMAND) {
        bestEffortCancel(request.id());
      }

      throw new AmaquetException.Timeout("Amaquet request timed out; mutation outcome unknown", e);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      if (request.opcode() == Protocol.COMMAND) {
        bestEffortCancel(request.id());
      }

      throw new AmaquetException("request interrupted", e);
    } catch (ExecutionException e) {
      Throwable cause = e.getCause();
      if (cause instanceof RuntimeException r) {
        throw r;
      }

      throw new AmaquetException("request failed", cause);
    } finally {
      pending.remove(request.id());
    }
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * Use TLS and identity pins for remote endpoints; close resources and bound pending requests in long-running applications.
   * @param args Exact JSON argument properties supported by the server; names are not translated.
   * @param name Native command or type operation name sent to the server.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   * @return The server response or computed SDK result; exact shape depends on the operation.
   */
  public CompletableFuture<Object> asyncCommand(String name, Map<String, Object> args) {
    Request r = sendCommand(name, args);
    r.future().whenComplete((value, error) -> pending.remove(r.id()));

    return r.future();
  }

  private void bestEffortCancel(long id) {
    try {
      Request r = send(Protocol.CANCEL, Json.map("request_id", id));
      pending.remove(r.id());
    } catch (RuntimeException ignored) {
    }
  }

  /**
   * Send a framed native opcode and parse the corresponding response.
   * Use TLS and identity pins for remote endpoints; close resources and bound pending requests in long-running applications.
   * @param args Exact JSON argument properties supported by the server; names are not translated.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   * @param opcode value supplied for opcode.
   * @return The server response or computed SDK result; exact shape depends on the operation.
   */
  public Object requestOpcode(int opcode, Object args) {
    return await(send(opcode, args));
  }

  /**
   * Send a named native command with its JSON payload; this is the extension point for supported server commands.
   * Use TLS and identity pins for remote endpoints; close resources and bound pending requests in long-running applications.
   * @param name Native command or type operation name sent to the server.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   * @return The server response or computed SDK result; exact shape depends on the operation.
   */
  public Object command(String name) {
    return command(name, Map.of());
  }

  /**
   * Send a named native command with its JSON payload; this is the extension point for supported server commands.
   * Use TLS and identity pins for remote endpoints; close resources and bound pending requests in long-running applications.
   * @param args Exact JSON argument properties supported by the server; names are not translated.
   * @param name Native command or type operation name sent to the server.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   * @return The server response or computed SDK result; exact shape depends on the operation.
   */
  public Object command(String name, Map<String, Object> args) {
    return await(sendCommand(name, args));
  }

  /**
   * Send a named native command with its JSON payload; this is the extension point for supported server commands.
   * Use TLS and identity pins for remote endpoints; close resources and bound pending requests in long-running applications.
   * @param timeout Time budget for the request; timeout does not guarantee server-side cancellation.
   * @param args Exact JSON argument properties supported by the server; names are not translated.
   * @param name Native command or type operation name sent to the server.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   * @return The server response or computed SDK result; exact shape depends on the operation.
   */
  public Object command(String name, Map<String, Object> args, Duration timeout) {
    return await(sendCommand(name, args), timeout);
  }

  /**
   * Dispatch a typed OP command using the key and its registered wire type.
   * Use TLS and identity pins for remote endpoints; close resources and bound pending requests in long-running applications.
   * @param key Target Amaquet key; use unique keys for examples and tests.
   * @param args Exact JSON argument properties supported by the server; names are not translated.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   * @param operation value supplied for operation.
   * @return The server response or computed SDK result; exact shape depends on the operation.
   */
  public Object op(String key, String operation, Map<String, Object> args) {
    return command(
        "OP",
        Json.map(
            "key",
            key,
            "operation",
            operation.toUpperCase(Locale.ROOT),
            "args",
            args == null ? Map.of() : args));
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * Use TLS and identity pins for remote endpoints; close resources and bound pending requests in long-running applications.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   * @param nonce value supplied for nonce.
   * @return The server response or computed SDK result; exact shape depends on the operation.
   */
  public Object hello(String nonce) {
    return requestOpcode(
        Protocol.HELLO, Json.map("nonce", nonce, "min_protocol", 1, "max_protocol", 1));
  }

  /**
   * Send the API key over the established native connection and handle authentication failures.
   * Use TLS and identity pins for remote endpoints; close resources and bound pending requests in long-running applications.
   * @param key Target Amaquet key; use unique keys for examples and tests.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   * @return The server response or computed SDK result; exact shape depends on the operation.
   */
  public Object authenticate(String key) {
    return requestOpcode(Protocol.AUTH, Json.map("api_key", key));
  }

  /**
   * Request cancellation of a pending operation; cancellation can race with a reply already in flight.
   * Use TLS and identity pins for remote endpoints; close resources and bound pending requests in long-running applications.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   * @param id value supplied for id.
   * @return The server response or computed SDK result; exact shape depends on the operation.
   */
  public Object cancel(long id) {
    return requestOpcode(Protocol.CANCEL, Json.map("request_id", id));
  }

  /**
   * Send a liveness probe. A successful response confirms the endpoint accepts this command.
   * Use TLS and identity pins for remote endpoints; close resources and bound pending requests in long-running applications.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   * @return The server response or computed SDK result; exact shape depends on the operation.
   */
  public Object ping() {
    return command("PING");
  }

  /**
   * Write a value using the server documented type and options; this may replace existing state.
   * Use TLS and identity pins for remote endpoints; close resources and bound pending requests in long-running applications.
   * @param key Target Amaquet key; use unique keys for examples and tests.
   * @param value Data to write or transmit, using the wire-type JSON format.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   * @param type value supplied for type.
   * @return The server response or computed SDK result; exact shape depends on the operation.
   */
  public Object set(String key, String type, Object value) {
    return set(key, type, value, Map.of());
  }

  /**
   * Write a value using the server documented type and options; this may replace existing state.
   * Use TLS and identity pins for remote endpoints; close resources and bound pending requests in long-running applications.
   * @param key Target Amaquet key; use unique keys for examples and tests.
   * @param value Data to write or transmit, using the wire-type JSON format.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   * @param type value supplied for type.
   * @param writeOptions value supplied for writeOptions.
   * @return The server response or computed SDK result; exact shape depends on the operation.
   */
  public Object set(String key, String type, Object value, Map<String, Object> writeOptions) {
    Map<String, Object> args = Json.map("key", key, "type", type, "value", value);
    args.putAll(validateWriteOptions(writeOptions));

    return command("SET", args);
  }

  /**
   * Create a new value with a registered wire type and its construction options.
   * Use TLS and identity pins for remote endpoints; close resources and bound pending requests in long-running applications.
   * @param key Target Amaquet key; use unique keys for examples and tests.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   * @param type value supplied for type.
   * @param config value supplied for config.
   * @return The server response or computed SDK result; exact shape depends on the operation.
   */
  public Object create(String key, String type, Map<String, Object> config) {
    return create(key, type, config, Map.of());
  }

  /**
   * Create a new value with a registered wire type and its construction options.
   * Use TLS and identity pins for remote endpoints; close resources and bound pending requests in long-running applications.
   * @param key Target Amaquet key; use unique keys for examples and tests.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   * @param type value supplied for type.
   * @param config value supplied for config.
   * @param writeOptions value supplied for writeOptions.
   * @return The server response or computed SDK result; exact shape depends on the operation.
   */
  public Object create(
      String key, String type, Map<String, Object> config, Map<String, Object> writeOptions) {
    Map<String, Object> a =
        Json.map("key", key, "type", type, "options", config == null ? Map.of() : config);

    a.putAll(validateWriteOptions(writeOptions));
    return command("CREATE", a);
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * Use TLS and identity pins for remote endpoints; close resources and bound pending requests in long-running applications.
   * @param key Target Amaquet key; use unique keys for examples and tests.
   * @param value Data to write or transmit, using the wire-type JSON format.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   * @param version value supplied for version.
   * @param type value supplied for type.
   * @return The server response or computed SDK result; exact shape depends on the operation.
   */
  public Object cas(String key, long version, String type, Object value) {
    if (version <= 0) {
      throw new IllegalArgumentException("invalid version");
    }

    return command(
        "CAS", Json.map("key", key, "expected_version", version, "type", type, "value", value));
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * Use TLS and identity pins for remote endpoints; close resources and bound pending requests in long-running applications.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   * @param commands value supplied for commands.
   * @param continueOnError value supplied for continueOnError.
   * @return The server response or computed SDK result; exact shape depends on the operation.
   */
  public Object batch(List<Map<String, Object>> commands, boolean continueOnError) {
    if (commands == null || commands.isEmpty() || commands.size() > 1024) {
      throw new IllegalArgumentException("batch must have 1..1024 items");
    }

    return command("BATCH", Json.map("requests", commands, "continue_on_error", continueOnError));
  }

  /**
   * Read current data for a key or type-specific field, subject to server permissions and key existence.
   * Use TLS and identity pins for remote endpoints; close resources and bound pending requests in long-running applications.
   * @param key Target Amaquet key; use unique keys for examples and tests.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   * @return The server response or computed SDK result; exact shape depends on the operation.
   */
  public Object get(String key) {
    return command("GET", Json.map("key", key));
  }

  /**
   * Remove one or more keys or named items as defined by the command arguments.
   * Use TLS and identity pins for remote endpoints; close resources and bound pending requests in long-running applications.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   * @param keys value supplied for keys.
   * @return The server response or computed SDK result; exact shape depends on the operation.
   */
  public Object delete(String... keys) {
    return command("DEL", Json.map("keys", List.of(keys)));
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * Use TLS and identity pins for remote endpoints; close resources and bound pending requests in long-running applications.
   * @param key Target Amaquet key; use unique keys for examples and tests.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   * @return The server response or computed SDK result; exact shape depends on the operation.
   */
  public Object exists(String key) {
    return command("EXISTS", Json.map("key", key));
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * Use TLS and identity pins for remote endpoints; close resources and bound pending requests in long-running applications.
   * @param key Target Amaquet key; use unique keys for examples and tests.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   * @return The server response or computed SDK result; exact shape depends on the operation.
   */
  public Object typeOf(String key) {
    return command("TYPE", Json.map("key", key));
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * Use TLS and identity pins for remote endpoints; close resources and bound pending requests in long-running applications.
   * @param key Target Amaquet key; use unique keys for examples and tests.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   * @return The server response or computed SDK result; exact shape depends on the operation.
   */
  public Object ttl(String key) {
    return command("TTL", Json.map("key", key));
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * Use TLS and identity pins for remote endpoints; close resources and bound pending requests in long-running applications.
   * @param key Target Amaquet key; use unique keys for examples and tests.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   * @param millis value supplied for millis.
   * @return The server response or computed SDK result; exact shape depends on the operation.
   */
  public Object expire(String key, long millis) {
    if (millis <= 0) {
      throw new IllegalArgumentException("TTL must be positive");
    }

    return command("EXPIRE", Json.map("key", key, "ttl_ms", millis));
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * Use TLS and identity pins for remote endpoints; close resources and bound pending requests in long-running applications.
   * @param key Target Amaquet key; use unique keys for examples and tests.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   * @param unixNanoseconds value supplied for unixNanoseconds.
   * @return The server response or computed SDK result; exact shape depends on the operation.
   */
  public Object expireAt(String key, long unixNanoseconds) {
    return command("EXPIRE", Json.map("key", key, "expires_at_unix_nano", unixNanoseconds));
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * Use TLS and identity pins for remote endpoints; close resources and bound pending requests in long-running applications.
   * @param key Target Amaquet key; use unique keys for examples and tests.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   * @return The server response or computed SDK result; exact shape depends on the operation.
   */
  public Object persist(String key) {
    return command("PERSIST", Json.map("key", key));
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * Use TLS and identity pins for remote endpoints; close resources and bound pending requests in long-running applications.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   * @param prefix value supplied for prefix.
   * @param limit value supplied for limit.
   * @return The server response or computed SDK result; exact shape depends on the operation.
   */
  public Object keys(String prefix, int limit) {
    return command("KEYS", Json.map("prefix", prefix, "limit", limit));
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * Use TLS and identity pins for remote endpoints; close resources and bound pending requests in long-running applications.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   * @param cursor value supplied for cursor.
   * @param prefix value supplied for prefix.
   * @param count value supplied for count.
   * @return The server response or computed SDK result; exact shape depends on the operation.
   */
  public Object scan(String cursor, String prefix, int count) {
    return command("SCAN", Json.map("cursor", cursor, "prefix", prefix, "count", count));
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * Use TLS and identity pins for remote endpoints; close resources and bound pending requests in long-running applications.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   * @return The server response or computed SDK result; exact shape depends on the operation.
   */
  public Object types() {
    return command("TYPES");
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * Use TLS and identity pins for remote endpoints; close resources and bound pending requests in long-running applications.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   * @return The server response or computed SDK result; exact shape depends on the operation.
   */
  public Object info() {
    return command("INFO");
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * Use TLS and identity pins for remote endpoints; close resources and bound pending requests in long-running applications.
   * @param key Target Amaquet key; use unique keys for examples and tests.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   * @return The server response or computed SDK result; exact shape depends on the operation.
   */
  public Object memory(String key) {
    return command("MEMORY", Json.map("key", key));
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * Use TLS and identity pins for remote endpoints; close resources and bound pending requests in long-running applications.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   * @return The server response or computed SDK result; exact shape depends on the operation.
   */
  public Types typed() {
    return new Types(this);
  }

  /**
   * Validate Write Options using the implementation's validation, error, and resource-lifetime rules.
   * Use TLS and identity pins for remote endpoints; close resources and bound pending requests in long-running applications.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   * @param input value supplied for input.
   * @return The server response or computed SDK result; exact shape depends on the operation.
   */
  public static Map<String, Object> validateWriteOptions(Map<String, Object> input) {
    Map<String, Object> out = new LinkedHashMap<>();
    if (input == null) {
      return out;
    }

    out.putAll(input);
    if (Boolean.TRUE.equals(out.get("nx")) && Boolean.TRUE.equals(out.get("xx"))) {
      throw new IllegalArgumentException("nx and xx conflict");
    }

    if (out.containsKey("ttl_ms") && !(out.get("ttl_ms") instanceof Number)) {
      throw new IllegalArgumentException("invalid TTL");
    }

    if (out.containsKey("ttl_ms")
        && new java.math.BigDecimal(out.get("ttl_ms").toString()).signum() < 0) {
      throw new IllegalArgumentException("negative TTL");
    }

    if (out.containsKey("expires_at_unix_nano")
        && out.containsKey("ttl_ms")
        && new java.math.BigDecimal(out.get("ttl_ms").toString()).signum() > 0) {
      throw new IllegalArgumentException("relative and absolute expiry conflict");
    }

    return out;
  }

  /**
   * Register a channel subscription; consume events from the bounded local queue and unsubscribe during cleanup.
   * Use TLS and identity pins for remote endpoints; close resources and bound pending requests in long-running applications.
   * @param key Target Amaquet key; use unique keys for examples and tests.
   * @param buffer Bounded storage for incoming payloads or events.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   * @param channel value supplied for channel.
   * @param capacity value supplied for capacity.
   * @return The server response or computed SDK result; exact shape depends on the operation.
   */
  public Subscription subscribe(String key, String channel, int buffer, int capacity) {
    if (buffer < 1 || capacity < 1 || capacity > 100000) {
      throw new IllegalArgumentException("invalid subscription buffer");
    }

    String target = key + "\0" + channel;
    final Subscription[] holder = new Subscription[1];

    Request r;
    synchronized (subscribeMutex) {
      if (subscriptionTargets.containsKey(target)) {
        throw new IllegalArgumentException("duplicate subscription");
      }

      r =
          send(
              Protocol.SUBSCRIBE,
              Json.map("key", key, "channel", channel, "buffer", buffer),
              id -> {
                Subscription sub = new Subscription(this, key, channel, id, capacity);
                subscriptions.put(id, sub);
                subscriptionTargets.put(target, id);
                holder[0] = sub;
              });
    }

    try {
      await(r);
      return holder[0];
    } catch (RuntimeException e) {
      subscriptionTargets.remove(target);
      Subscription sub = subscriptions.remove(r.id());

      if (sub != null) {
        sub.finish();
      }

      try {
        requestOpcode(Protocol.UNSUBSCRIBE, Json.map("key", key, "channel", channel));
      } catch (RuntimeException ignored) {
      }

      throw e;
    }
  }

  /**
   * Stop delivery of events for an existing subscription and release its local state.
   * Use TLS and identity pins for remote endpoints; close resources and bound pending requests in long-running applications.
   * @param key Target Amaquet key; use unique keys for examples and tests.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   * @param channel value supplied for channel.
   * @return The server response or computed SDK result; exact shape depends on the operation.
   */
  public boolean unsubscribe(String key, String channel) {
    try {
      Object x = requestOpcode(Protocol.UNSUBSCRIBE, Json.map("key", key, "channel", channel));
      return Boolean.TRUE.equals(Json.object(x).get("unsubscribed"));
    } finally {
      Long id = subscriptionTargets.remove(key + "\0" + channel);

      if (id != null) {
        Subscription s = subscriptions.remove(id);
        if (s != null) {
          s.finish();
        }
      }
    }
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * Use TLS and identity pins for remote endpoints; close resources and bound pending requests in long-running applications.
   * @param key Target Amaquet key; use unique keys for examples and tests.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   * @param size value supplied for size.
   * @param id value supplied for id.
   * @param connectionScoped value supplied for connectionScoped.
   * @param opts value supplied for opts.
   * @return The server response or computed SDK result; exact shape depends on the operation.
   */
  public Object beginBlob(
      String key, long size, String id, boolean connectionScoped, Map<String, Object> opts) {
    if (size < 0 || size > MAX_BLOB_SIZE) {
      throw new IllegalArgumentException("binary size out of range");
    }

    Map<String, Object> a =
        Json.map(
            "key",
            key,
            "total_size",
            size,
            "upload_id",
            id == null ? UUID.randomUUID().toString().replace("-", "") : id,
            "connection_scoped",
            connectionScoped);

    a.putAll(validateWriteOptions(opts));
    return command("BLOB_BEGIN", a);
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * Use TLS and identity pins for remote endpoints; close resources and bound pending requests in long-running applications.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   * @param uploadId value supplied for uploadId.
   * @param offset value supplied for offset.
   * @param bytes value supplied for bytes.
   * @return The server response or computed SDK result; exact shape depends on the operation.
   */
  public Object blobChunk(String uploadId, long offset, byte[] bytes) {
    if (offset < 0
        || offset > MAX_BLOB_SIZE
        || bytes == null
        || bytes.length < 1
        || bytes.length > MAX_BLOB_CHUNK) {
      throw new IllegalArgumentException("invalid blob chunk");
    }

    return command(
        "BLOB_CHUNK",
        Json.map(
            "upload_id",
            uploadId,
            "offset",
            offset,
            "data",
            Base64.getEncoder().encodeToString(bytes)));
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * Use TLS and identity pins for remote endpoints; close resources and bound pending requests in long-running applications.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   * @param id value supplied for id.
   * @return The server response or computed SDK result; exact shape depends on the operation.
   */
  public Object commitBlob(String id) {
    return command("BLOB_COMMIT", Json.map("upload_id", id));
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * Use TLS and identity pins for remote endpoints; close resources and bound pending requests in long-running applications.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   * @param id value supplied for id.
   * @return The server response or computed SDK result; exact shape depends on the operation.
   */
  public Object abortBlob(String id) {
    return command("BLOB_ABORT", Json.map("upload_id", id));
  }

  /**
   * A chunk returned by the blob range operation.
   *
   * @param key blob key
   * @param offset byte offset of this chunk
   * @param size total blob size
   * @param version server-side blob version
   * @param data chunk bytes
   */
  public record BlobRange(String key, long offset, long size, long version, byte[] data) {}

  /**
   * Performs the corresponding documented Amaquet operation.
   * Use TLS and identity pins for remote endpoints; close resources and bound pending requests in long-running applications.
   * @param key Target Amaquet key; use unique keys for examples and tests.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   * @param offset value supplied for offset.
   * @param length value supplied for length.
   * @return The server response or computed SDK result; exact shape depends on the operation.
   */
  public BlobRange blobReadRange(String key, long offset, int length) {
    if (offset < 0 || offset > MAX_BLOB_SIZE || length < 0 || length > MAX_BLOB_CHUNK) {
      throw new IllegalArgumentException("invalid blob read range");
    }

    Map<String, Object> r =
        Json.object(command("BLOB_READ", Json.map("key", key, "offset", offset, "length", length)));
    try {
      String text = (String) r.get("data");
      if (text.length() > ((long) length + 2) / 3 * 4 + 4) {
        throw new IllegalArgumentException("blob base64 too large");
      }

      byte[] bytes = Base64.getDecoder().decode(text);
      if (!Base64.getEncoder().encodeToString(bytes).equals(text)) {
        throw new IllegalArgumentException("noncanonical blob base64");
      }

      long gotOffset = asLong(r.get("offset")),
          size = asLong(r.get("size")),
          version = asLong(r.get("version"));
      if (!Objects.equals(key, r.get("key"))
          || gotOffset != offset
          || size < 0
          || size > MAX_BLOB_SIZE
          || version < 1
          || offset > size
          || bytes.length > length
          || offset + bytes.length > size) {
        throw new IllegalArgumentException("inconsistent blob range metadata");
      }

      return new BlobRange(key, offset, size, version, bytes);
    } catch (RuntimeException e) {
      throw new AmaquetException.Protocol("invalid blob read response", e);
    }
  }

  private static long asLong(Object n) {
    return new java.math.BigDecimal(n.toString()).longValueExact();
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * Use TLS and identity pins for remote endpoints; close resources and bound pending requests in long-running applications.
   * @param key Target Amaquet key; use unique keys for examples and tests.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   * @param input value supplied for input.
   * @param size value supplied for size.
   * @param chunkSize value supplied for chunkSize.
   * @param connectionScoped value supplied for connectionScoped.
   * @return The server response or computed SDK result; exact shape depends on the operation.
   */
  public Object uploadBlob(
      String key, InputStream input, long size, int chunkSize, boolean connectionScoped) {
    if (size < 0
        || size > MAX_BLOB_SIZE
        || chunkSize < 1
        || chunkSize > MAX_BLOB_CHUNK
        || (size + chunkSize - 1) / chunkSize > 65536) {
      throw new IllegalArgumentException("invalid blob upload size/chunks");
    }

    String id =
        (String)
            Json.object(beginBlob(key, size, null, connectionScoped, Map.of())).get("upload_id");
    boolean committed = false;

    try {
      long sent = 0;
      byte[] buffer = new byte[chunkSize];

      while (sent < size) {
        int want = (int) Math.min(chunkSize, size - sent), n = 0;
        while (n < want) {
          int r = input.read(buffer, n, want - n);
          if (r < 0) {
            throw new EOFException("blob source shorter than declared");
          }

          if (r == 0) {
            continue;
          }

          n += r;
        }

        blobChunk(id, sent, Arrays.copyOf(buffer, n));
        sent += n;
      }

      Object result = commitBlob(id);
      committed = true;

      return result;
    } catch (IOException e) {
      throw new AmaquetException.Transport("blob source I/O failure", e);
    } finally {
      if (!committed) {
        try {
          abortBlob(id);
        } catch (RuntimeException ignored) {
        }
      }
    }
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * Use TLS and identity pins for remote endpoints; close resources and bound pending requests in long-running applications.
   * @param key Target Amaquet key; use unique keys for examples and tests.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   * @param bytes value supplied for bytes.
   * @return The server response or computed SDK result; exact shape depends on the operation.
   */
  public Object uploadBlob(String key, byte[] bytes) {
    return uploadBlob(key, new ByteArrayInputStream(bytes), bytes.length, DEFAULT_CHUNK, false);
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * Use TLS and identity pins for remote endpoints; close resources and bound pending requests in long-running applications.
   * @param key Target Amaquet key; use unique keys for examples and tests.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   * @param target value supplied for target.
   * @param chunkSize value supplied for chunkSize.
   * @param maxBytes value supplied for maxBytes.
   * @return The server response or computed SDK result; exact shape depends on the operation.
   */
  public long downloadBlob(String key, OutputStream target, int chunkSize, long maxBytes) {
    if (chunkSize < 1 || chunkSize > MAX_BLOB_CHUNK || maxBytes < 0 || maxBytes > MAX_BLOB_SIZE) {
      throw new IllegalArgumentException("invalid download limit");
    }

    long offset = 0, size = -1, version = -1;
    try {
      do {
        BlobRange b = blobReadRange(key, offset, chunkSize);
        if (size < 0) {
          size = b.size;
          version = b.version;
        } else if (size != b.size || version != b.version) {
          throw new AmaquetException.Protocol("blob changed during download");
        }

        if (offset + b.data.length > maxBytes) {
          throw new AmaquetException.Protocol("blob exceeds download limit");
        }

        if (b.data.length == 0 && offset < size) {
          throw new AmaquetException.Protocol("early EOF in blob range");
        }

        target.write(b.data);
        offset += b.data.length;
      } while (offset < size);

      return offset;
    } catch (IOException e) {
      throw new AmaquetException.Transport("blob destination I/O failure", e);
    }
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * Use TLS and identity pins for remote endpoints; close resources and bound pending requests in long-running applications.
   * @param key Target Amaquet key; use unique keys for examples and tests.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   * @param maxBytes value supplied for maxBytes.
   * @return The server response or computed SDK result; exact shape depends on the operation.
   */
  public byte[] downloadBlobBytes(String key, int maxBytes) {
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    downloadBlob(key, out, DEFAULT_CHUNK, maxBytes);

    return out.toByteArray();
  }

  private void shutdown(AmaquetException cause) {
    if (!closed.compareAndSet(false, true)) {
      return;
    }

    failure = cause;
    try {
      socket.close();
    } catch (IOException ignored) {
    }

    pending.values().forEach(p -> p.future().completeExceptionally(cause));
    pending.clear();

    subscriptions.values().forEach(Subscription::finish);
    subscriptions.clear();
    subscriptionTargets.clear();
  }

  @Override
  public void close() {
    shutdown(new AmaquetException("Amaquet connection closed"));
  }
}
