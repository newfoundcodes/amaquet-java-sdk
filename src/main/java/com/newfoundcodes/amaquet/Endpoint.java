package com.newfoundcodes.amaquet;

import java.net.*;
import java.nio.charset.StandardCharsets;

/**
 * Parsed native Amaquet endpoint.
 *
 * @param host endpoint host name or address
 * @param port endpoint TCP port
 * @param tls whether the endpoint uses TLS
 * @param apiKey optional API key extracted from the URI
 */
public record Endpoint(String host, int port, boolean tls, String apiKey) {
  /**
   * Parse using the implementation's validation, error, and resource-lifetime rules.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   * @param raw value supplied for raw.
   * @return The server response or computed SDK result; exact shape depends on the operation.
   */
  public static Endpoint parse(String raw) {
    return parse(raw, false);
  }

  /**
   * Parse using the implementation's validation, error, and resource-lifetime rules.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   * @param raw value supplied for raw.
   * @param allowSecrets value supplied for allowSecrets.
   * @return The server response or computed SDK result; exact shape depends on the operation.
   */
  public static Endpoint parse(String raw, boolean allowSecrets) {
    try {
      URI uri = URI.create(raw);
      if (!"amaquet".equals(uri.getScheme()) && !"amaquets".equals(uri.getScheme())) {
        throw new IllegalArgumentException("scheme must be amaquet or amaquets");
      }

      String host = uri.getHost();
      if (host == null
          || host.isBlank()
          || uri.getFragment() != null
          || !(uri.getRawPath() == null
              || uri.getRawPath().isEmpty()
              || uri.getRawPath().equals("/"))) {
        throw new IllegalArgumentException("invalid Amaquet endpoint");
      }

      int port = uri.getPort() == -1 ? 13378 : uri.getPort();
      if (port < 1 || port > 65535) {
        throw new IllegalArgumentException("invalid Amaquet port");
      }

      String secret = null;
      String query = uri.getRawQuery();

      if (query != null) {
        if (!query.startsWith("api_key=") || query.indexOf('&') >= 0) {
          throw new IllegalArgumentException("unsupported Amaquet query");
        }

        secret = URLDecoder.decode(query.substring(8), StandardCharsets.UTF_8);
      }

      if (uri.getRawUserInfo() != null) {
        String rawUser = uri.getRawUserInfo();
        secret =
            URLDecoder.decode(
                rawUser.substring(rawUser.lastIndexOf(':') + 1), StandardCharsets.UTF_8);
      }

      if (secret != null && !allowSecrets) {
        throw new IllegalArgumentException("URI credentials disabled");
      }

      return new Endpoint(host, port, uri.getScheme().equals("amaquets"), secret);
    } catch (IllegalArgumentException e) {
      throw e;
    } catch (Exception e) {
      throw new IllegalArgumentException("invalid Amaquet URI", e);
    }
  }
}
