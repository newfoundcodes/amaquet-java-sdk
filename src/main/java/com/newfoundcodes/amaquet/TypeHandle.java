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

import java.util.Map;

/** Base handle for a key and its registered Amaquet wire type. */
public class TypeHandle {
  /** Client used to dispatch operations for this handle. */
  protected final Client client;

  /** Amaquet key represented by this handle. */
  public final String key;

  /** Registered Amaquet wire type represented by this handle. */
  public final String wireType;

  /**
   * Creates a handle for a key and wire type.
   *
   * @param client connected Amaquet client
   * @param key Amaquet key represented by the handle
   * @param wireType registered Amaquet wire type
   */
  public TypeHandle(Client client, String key, String wireType) {
    this.client = client;
    this.key = key;
    this.wireType = wireType;
  }

  /**
   * Create a new value with a registered wire type and its construction options.
   * @param options Call settings or documented construction flags, as accepted by this method.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   * @return The server response or computed SDK result; exact shape depends on the operation.
   */
  public Object create(Map<String, Object> options) {
    return client.create(key, wireType, options);
  }

  /**
   * Create a new value with a registered wire type and its construction options.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   * @return The server response or computed SDK result; exact shape depends on the operation.
   */
  public Object create() {
    return create(Map.of());
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * @param value Data to write or transmit, using the wire-type JSON format.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   * @return The server response or computed SDK result; exact shape depends on the operation.
   */
  public Object replace(Object value) {
    return client.set(key, wireType, value);
  }

  /**
   * Read the server snapshot of the current key; returned JSON shape depends on the stored wire type.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   * @return The server response or computed SDK result; exact shape depends on the operation.
   */
  public Object snapshot() {
    return client.get(key);
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   * @return The server response or computed SDK result; exact shape depends on the operation.
   */
  public Object deleteKey() {
    return client.delete(key);
  }

  /**
   * Dispatch a typed OP command using the key and its registered wire type.
   * @param args Exact JSON argument properties supported by the server; names are not translated.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   * @param operation value supplied for operation.
   * @return The server response or computed SDK result; exact shape depends on the operation.
   */
  public Object op(String operation, Map<String, Object> args) {
    return client.op(key, operation, args);
  }
}
