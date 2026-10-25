package com.newfoundcodes.amaquet;

import java.util.*;

/** Source-generated names and operations are under {@link TypedHandles}. */
/**
 * Performs the corresponding documented Amaquet operation.
 * Connects registered Amaquet wire-type names to per-key handles; server validation determines legal arguments and operations.
 * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
 */
public final class Types {
  private final Client client;

  /**
   * Performs the corresponding documented Amaquet operation.
   * @param client Existing client connected to the Amaquet endpoint.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   */
  public Types(Client client) {
    this.client = client;
  }

  /**
   * Creates a generic handle for a registered wire type and key.
   *
   * @param wireType registered Amaquet wire type
   * @param key Amaquet key represented by the handle
   * @return a generic type handle
   * @throws IllegalArgumentException if the wire type is not registered
   */
  public TypeHandle forType(String wireType, String key) {
    if (!TypedHandles.CATALOG.containsKey(wireType)) {
      throw new IllegalArgumentException("unknown Amaquet type: " + wireType);
    }

    return new TypeHandle(client, key, wireType);
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   * @return The server response or computed SDK result; exact shape depends on the operation.
   */
  public Set<String> registered() {
    return TypedHandles.CATALOG.keySet();
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   * @return The server response or computed SDK result; exact shape depends on the operation.
   */
  public TypedHandles generated() {
    return new TypedHandles(client);
  }
}
