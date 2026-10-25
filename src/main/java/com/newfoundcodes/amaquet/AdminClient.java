package com.newfoundcodes.amaquet;

import java.io.*;
import java.net.*;
import java.net.http.*;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

/** Amaquet HTTP(S) administration client. No redirect, bounded replies, strict path validation. */
/**
 * Performs the corresponding documented Amaquet operation.
 * Control-plane requests use a separate HTTP(S) base URL and may require administrative authorization.
 * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
 */
public final class AdminClient {
  /** Default maximum administration response size, in bytes. */
  public static final int DEFAULT_MAX_RESPONSE = 16 << 20;
  private final URI origin;
  private final HttpClient http;
  private final String token, tokenHeader;
  private final Duration timeout;
  private final int maxResponse;
  private final boolean cookies;
  private final AtomicReference<String> session = new AtomicReference<>();

  /**
   * Performs the corresponding documented Amaquet operation.
   * Control-plane requests use a separate HTTP(S) base URL and may require administrative authorization.
   * @param endpoint Host and port of the native service or HTTP administration origin.
   * @param token Administrative token; avoid sending it over untrusted HTTP.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   */
  public AdminClient(String endpoint, String token) {
    this(endpoint, token, "Authorization", Duration.ofSeconds(30), DEFAULT_MAX_RESPONSE, true);
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * Control-plane requests use a separate HTTP(S) base URL and may require administrative authorization.
   * @param endpoint Host and port of the native service or HTTP administration origin.
   * @param timeout Time budget for the request; timeout does not guarantee server-side cancellation.
   * @param token Administrative token; avoid sending it over untrusted HTTP.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   * @param tokenHeader value supplied for tokenHeader.
   * @param maxResponse value supplied for maxResponse.
   * @param cookies value supplied for cookies.
   */
  public AdminClient(
      String endpoint,
      String token,
      String tokenHeader,
      Duration timeout,
      int maxResponse,
      boolean cookies) {
    URI uri = URI.create(endpoint);
    if (!(uri.getScheme().equals("http") || uri.getScheme().equals("https"))
        || uri.getHost() == null
        || uri.getRawUserInfo() != null
        || uri.getFragment() != null
        || uri.getRawQuery() != null
        || !(uri.getRawPath().isEmpty() || uri.getRawPath().equals("/"))) {
      throw new IllegalArgumentException("admin endpoint must be a bare HTTP(S) origin");
    }

    if (!tokenHeader.equals("Authorization") && !tokenHeader.equals("X-Amaquet-Admin-Token")) {
      throw new IllegalArgumentException("invalid token header");
    }

    if (token != null && (token.contains("\r") || token.contains("\n"))) {
      throw new IllegalArgumentException("invalid admin token");
    }

    if (timeout.isNegative() || timeout.isZero() || maxResponse < 1) {
      throw new IllegalArgumentException("invalid HTTP limits");
    }

    this.origin =
        URI.create(
            uri.getScheme()
                + "://"
                + (uri.getHost().contains(":") ? "[" + uri.getHost() + "]" : uri.getHost())
                + (uri.getPort() > 0 ? ":" + uri.getPort() : ""));

    this.token = token;
    this.tokenHeader = tokenHeader;
    this.timeout = timeout;
    this.maxResponse = maxResponse;
    this.cookies = cookies;

    this.http =
        HttpClient.newBuilder()
            .followRedirects(HttpClient.Redirect.NEVER)
            .connectTimeout(timeout)
            .build();
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   */
  public void clearSession() {
    session.set(null);
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   * @param id value supplied for id.
   * @return The server response or computed SDK result; exact shape depends on the operation.
   */
  public static String pathId(String id) {
    if (id == null
        || id.isEmpty()
        || id.equals(".")
        || id.equals("..")
        || id.chars().anyMatch(c -> c == '/' || c == '\\' || c == 0 || c == '\n' || c == '\r')) {
      throw new IllegalArgumentException("invalid resource ID");
    }

    return encode(id);
  }

  private static String encode(String x) {
    try {
      return URLEncoder.encode(x, StandardCharsets.UTF_8).replace("+", "%20");
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException("invalid URI parameter", e);
    }
  }

  private static String query(Map<String, Object> m) {
    StringJoiner j = new StringJoiner("&");
    m.forEach((k, v) -> j.add(encode(k) + "=" + encode(String.valueOf(v))));
    return j.toString();
  }

  private static void checkPath(String path) {
    if (path == null
        || !path.startsWith("/")
        || path.startsWith("//")
        || path.chars().anyMatch(c -> c == '\\' || c == '#' || c < 0x20)) {
      throw new IllegalArgumentException("unsafe admin path");
    }

    String actual = path.split("\\?", 2)[0];
    for (String seg : actual.split("/", -1)) {
      String decoded = URLDecoder.decode(seg, StandardCharsets.UTF_8);
      if (decoded.equals(".")
          || decoded.equals("..")
          || decoded.indexOf('/') >= 0
          || decoded.indexOf('\\') >= 0
          || decoded.indexOf('\0') >= 0) {
        throw new IllegalArgumentException("unsafe admin path segment");
      }
    }
  }

  /**
   * Submit an operation with a request ID and await its matching response or a timeout or connection error.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   * @param method value supplied for method.
   * @param path value supplied for path.
   * @return The server response or computed SDK result; exact shape depends on the operation.
   */
  public Object request(String method, String path) {
    return request(method, path, null, false);
  }

  /**
   * Submit an operation with a request ID and await its matching response or a timeout or connection error.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   * @param method value supplied for method.
   * @param path value supplied for path.
   * @param body value supplied for body.
   * @return The server response or computed SDK result; exact shape depends on the operation.
   */
  public Object request(String method, String path, Object body) {
    return request(method, path, body, true);
  }

  private Object request(String method, String path, Object body, boolean hasBody) {
    checkPath(path);

    String verb = method.toUpperCase(Locale.ROOT);
    if (!Set.of("GET", "POST", "PUT", "PATCH", "DELETE", "HEAD").contains(verb)) {
      throw new IllegalArgumentException("invalid HTTP verb");
    }

    byte[] payload = hasBody ? Json.encode(body) : new byte[0];
    if (payload.length > Protocol.MAX_PAYLOAD) {
      throw new IllegalArgumentException("request JSON too large");
    }

    HttpRequest.BodyPublisher publisher =
        hasBody
            ? HttpRequest.BodyPublishers.ofByteArray(payload)
            : HttpRequest.BodyPublishers.noBody();
    HttpRequest.Builder b =
        HttpRequest.newBuilder(origin.resolve(path))
            .timeout(timeout)
            .header("Accept", path.equals("/metrics") ? "text/plain" : "application/json")
            .method(verb, publisher);

    if (hasBody) {
      b.header("Content-Type", "application/json");
    }

    if (token != null) {
      b.header(tokenHeader, tokenHeader.equals("Authorization") ? "Bearer " + token : token);
    }

    String cookie = session.get();
    if (cookie != null) {
      b.header("Cookie", cookie);
    }

    try {
      HttpResponse<InputStream> res =
          http.send(b.build(), HttpResponse.BodyHandlers.ofInputStream());
      byte[] bytes;

      try (InputStream stream = res.body()) {
        bytes = stream.readNBytes(maxResponse + 1);
      }

      if (bytes.length > maxResponse) {
        throw new AmaquetException.Protocol("administration response size limit");
      }

      int status = res.statusCode();
      String text = new String(bytes, StandardCharsets.UTF_8);

      if (status < 200 || status >= 300) {
        Object err = null;
        try {
          err = Json.parse(bytes);
        } catch (RuntimeException ignored) {
        }

        String code = "", message = text;
        if (err instanceof Map<?, ?>) {
          Map<String, Object> m = Json.object(err);
          code = String.valueOf(m.getOrDefault("code", ""));

          Object e = m.get("error");
          if (e instanceof Map<?, ?>) {
            Map<String, Object> m2 = Json.object(e);

            code = code.isEmpty() ? String.valueOf(m2.getOrDefault("code", "")) : code;
            message = String.valueOf(m2.getOrDefault("message", text));
          } else if (e instanceof String s) {
            message = s;
          } else {
            message = String.valueOf(m.getOrDefault("message", text));
          }
        }

        throw new AmaquetException.Http(status, code, message, text);
      }

      if (cookies && path.equals("/api/session") && verb.equals("POST")) {
        for (String raw : res.headers().allValues("set-cookie")) {
          if (raw.trim().startsWith("amaquet_session=")) {
            session.set(raw.trim().split(";", 2)[0]);
            break;
          }
        }
      }

      if (path.equals("/api/session") && verb.equals("DELETE")) {
        session.set(null);
      }

      if (path.equals("/metrics")) {
        return text;
      }

      if (bytes.length == 0) {
        return null;
      }

      try {
        return Json.parse(bytes);
      } catch (IllegalArgumentException e) {
        throw new AmaquetException.Protocol("invalid administration JSON", e);
      }
    } catch (IOException e) {
      throw new AmaquetException.Transport("admin HTTP failure", e);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      throw new AmaquetException("admin request interrupted", e);
    }
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   * @return The server response or computed SDK result; exact shape depends on the operation.
   */
  public Object openapi() {
    return request("GET", "/openapi.json");
  }

  /**
   * Query the administration service health endpoint; it does not authenticate a native connection.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   * @return The server response or computed SDK result; exact shape depends on the operation.
   */
  public Object health() {
    return request("GET", "/api/health");
  }

  /**
   * Ready using the implementation's validation, error, and resource-lifetime rules.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   * @return The server response or computed SDK result; exact shape depends on the operation.
   */
  public Object ready() {
    return request("GET", "/api/ready");
  }

  /**
   * Retrieve Prometheus-format text from the HTTP control plane, not a JSON document.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   * @return The server response or computed SDK result; exact shape depends on the operation.
   */
  public String metrics() {
    return (String) request("GET", "/metrics");
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   * @return The server response or computed SDK result; exact shape depends on the operation.
   */
  public Object checkAuth() {
    return request("GET", "/api/auth/check");
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * @param token Administrative token; avoid sending it over untrusted HTTP.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   * @param mfaCode value supplied for mfaCode.
   * @return The server response or computed SDK result; exact shape depends on the operation.
   */
  public Object createSession(String token, String mfaCode) {
    return request("POST", "/api/session", Json.map("token", token, "mfa_code", mfaCode));
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   * @return The server response or computed SDK result; exact shape depends on the operation.
   */
  public Object deleteSession() {
    return request("DELETE", "/api/session");
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   * @param invite value supplied for invite.
   * @return The server response or computed SDK result; exact shape depends on the operation.
   */
  public Object acceptInvite(String invite) {
    return request("POST", "/api/identity/accept-invite", Json.map("invite", invite));
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   * @return The server response or computed SDK result; exact shape depends on the operation.
   */
  public Object beginMfa() {
    return request("POST", "/api/identity/mfa");
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   * @param code value supplied for code.
   * @return The server response or computed SDK result; exact shape depends on the operation.
   */
  public Object confirmMfa(String code) {
    return request("PUT", "/api/identity/mfa", Json.map("code", code));
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   * @param code value supplied for code.
   * @return The server response or computed SDK result; exact shape depends on the operation.
   */
  public Object disableMfa(String code) {
    return request("DELETE", "/api/identity/mfa", Json.map("code", code));
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   * @return The server response or computed SDK result; exact shape depends on the operation.
   */
  public Object overview() {
    return request("GET", "/api/overview");
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   * @return The server response or computed SDK result; exact shape depends on the operation.
   */
  public Object organization() {
    return request("GET", "/api/organization");
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * @param name Native command or type operation name sent to the server.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   * @param slug value supplied for slug.
   * @return The server response or computed SDK result; exact shape depends on the operation.
   */
  public Object updateOrganization(String name, String slug) {
    return request("PUT", "/api/organization", Json.map("name", name, "slug", slug));
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   * @return The server response or computed SDK result; exact shape depends on the operation.
   */
  public Object members() {
    return request("GET", "/api/members");
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * @param name Native command or type operation name sent to the server.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   * @param email value supplied for email.
   * @param role value supplied for role.
   * @return The server response or computed SDK result; exact shape depends on the operation.
   */
  public Object addMember(String name, String email, String role) {
    return request("POST", "/api/members", Json.map("name", name, "email", email, "role", role));
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   * @param id value supplied for id.
   * @param role value supplied for role.
   * @param status value supplied for status.
   * @return The server response or computed SDK result; exact shape depends on the operation.
   */
  public Object patchMember(String id, String role, String status) {
    Map<String, Object> b = Json.map("role", role);
    if (status != null) {
      b.put("status", status);
    }

    return request("PATCH", "/api/members/" + pathId(id), b);
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   * @param id value supplied for id.
   * @return The server response or computed SDK result; exact shape depends on the operation.
   */
  public Object deleteMember(String id) {
    return request("DELETE", "/api/members/" + pathId(id));
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   * @param id value supplied for id.
   * @param hours value supplied for hours.
   * @return The server response or computed SDK result; exact shape depends on the operation.
   */
  public Object inviteMember(String id, int hours) {
    return request("POST", "/api/members/" + pathId(id) + "/invite", Json.map("ttl_hours", hours));
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   * @return The server response or computed SDK result; exact shape depends on the operation.
   */
  public Object rbac() {
    return request("GET", "/api/rbac");
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   * @param config value supplied for config.
   * @return The server response or computed SDK result; exact shape depends on the operation.
   */
  public Object updateRbac(Map<String, Object> config) {
    return request("PUT", "/api/rbac", config);
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   * @return The server response or computed SDK result; exact shape depends on the operation.
   */
  public Object apiKeys() {
    return request("GET", "/api/api-keys");
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * @param name Native command or type operation name sent to the server.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   * @param role value supplied for role.
   * @param expiresAt value supplied for expiresAt.
   * @return The server response or computed SDK result; exact shape depends on the operation.
   */
  public Object createApiKey(String name, String role, String expiresAt) {
    return request(
        "POST", "/api/api-keys", Json.map("name", name, "role", role, "expires_at", expiresAt));
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   * @param id value supplied for id.
   * @return The server response or computed SDK result; exact shape depends on the operation.
   */
  public Object revokeApiKey(String id) {
    return request("DELETE", "/api/api-keys/" + pathId(id));
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   * @return The server response or computed SDK result; exact shape depends on the operation.
   */
  public Object system() {
    return request("GET", "/api/system");
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   * @param config value supplied for config.
   * @return The server response or computed SDK result; exact shape depends on the operation.
   */
  public Object updateSystem(Object config) {
    return request("PUT", "/api/system", config);
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * @param name Native command or type operation name sent to the server.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   * @return The server response or computed SDK result; exact shape depends on the operation.
   */
  public Object checkpoint(String name) {
    return request("POST", "/api/persistence/checkpoint", Json.map("name", name));
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   * @return The server response or computed SDK result; exact shape depends on the operation.
   */
  public Object compact() {
    return request("POST", "/api/persistence/compact");
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   * @param cursor value supplied for cursor.
   * @param prefix value supplied for prefix.
   * @param count value supplied for count.
   * @return The server response or computed SDK result; exact shape depends on the operation.
   */
  public Object dataKeys(String cursor, String prefix, int count) {
    return request(
        "GET",
        "/api/data/keys?" + query(Json.map("cursor", cursor, "prefix", prefix, "count", count)));
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * @param key Target Amaquet key; use unique keys for examples and tests.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   * @return The server response or computed SDK result; exact shape depends on the operation.
   */
  public Object dataKey(String key) {
    return request("GET", "/api/data/key?" + query(Json.map("key", key)));
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * @param key Target Amaquet key; use unique keys for examples and tests.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   * @return The server response or computed SDK result; exact shape depends on the operation.
   */
  public Object deleteDataKey(String key) {
    return request("DELETE", "/api/data/key?" + query(Json.map("key", key)));
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * @param args Exact JSON argument properties supported by the server; names are not translated.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   * @param command value supplied for command.
   * @return The server response or computed SDK result; exact shape depends on the operation.
   */
  public Object dataCommand(String command, Map<String, Object> args) {
    return request(
        "POST",
        "/api/data/command",
        Json.map("command", command, "args", args == null ? Map.of() : args));
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   * @param limit value supplied for limit.
   * @return The server response or computed SDK result; exact shape depends on the operation.
   */
  public Object audit(int limit) {
    return request("GET", "/api/audit?" + query(Json.map("limit", limit)));
  }
}
