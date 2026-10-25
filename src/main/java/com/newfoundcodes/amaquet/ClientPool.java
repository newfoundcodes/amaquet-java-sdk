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

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/** Fixed-size round-robin pool of Amaquet clients. Failed operations are never replayed. */
public final class ClientPool implements AutoCloseable {
  private final Client[] clients;
  private final String uri;
  private final Client.Options options;
  private final AtomicInteger cursor = new AtomicInteger();

  /**
   * Creates and connects a pool of clients.
   *
   * @param uri native Amaquet URI; use {@code amaquets://} for TLS
   * @param size number of clients to create, from 1 through 256
   * @param options connection settings shared by every client
   * @throws RuntimeException if any client cannot be connected; already-created clients are closed
   */
  public ClientPool(String uri, int size, Client.Options options) {
    if (size < 1 || size > 256) {
      throw new IllegalArgumentException("pool size must be 1..256");
    }

    this.uri = uri;
    this.options = options;
    clients = new Client[size];

    int made = 0;
    try {
      for (; made < size; made++) {
        clients[made] = Client.connect(uri, options);
      }
    } catch (RuntimeException e) {
      for (int i = 0; i < made; i++) {
        clients[i].close();
      }

      throw e;
    }
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   * @return The server response or computed SDK result; exact shape depends on the operation.
   */
  public Client acquire() {
    return clients[Math.floorMod(cursor.getAndIncrement(), clients.length)];
  }

  /**
   * Sends a named native command through the next pooled client.
   *
   * @param name native command or type operation name
   * @param args wire operation arguments; keys are preserved
   * @return decoded server response
   */
  public Object command(String name, Map<String, Object> args) {
    return acquire().command(name, args);
  }

  /**
   * Replaces one client in the pool.
   *
   * @param index zero-based pool slot
   * @return the newly connected client
   */
  public synchronized Client replace(int index) {
    if (index < 0 || index >= clients.length) {
      throw new IllegalArgumentException("invalid slot");
    }

    Client fresh = Client.connect(uri, options), old = clients[index];
    clients[index] = fresh;

    old.close();
    return fresh;
  }

  /** Closes every client currently owned by this pool. */
  @Override
  public void close() {
    for (Client c : clients) {
      if (c != null) {
        c.close();
      }
    }
  }
}
