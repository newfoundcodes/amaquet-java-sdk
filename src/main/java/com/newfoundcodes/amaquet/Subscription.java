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

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.*;

/** Non-blocking, bounded local Pub/Sub queue. Overflow drops messages. */
/**
 * Performs the corresponding documented Amaquet operation.
 * Buffers server events and exposes subscribe, receive, and unsubscribe lifecycle behavior.
 * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
 */
public final class Subscription implements AutoCloseable {
  private final Client client;
  private final String key, channel;

  /** Server request identifier for this subscription. */
  public final long requestId;
  private final ArrayBlockingQueue<Map<String, Object>> queue;
  private final AtomicLong dropped = new AtomicLong();
  private final AtomicBoolean closed = new AtomicBoolean();

  Subscription(Client client, String key, String channel, long id, int capacity) {
    this.client = client;
    this.key = key;
    this.channel = channel;
    this.requestId = id;
    this.queue = new ArrayBlockingQueue<>(capacity);
  }

  void deliver(Map<String, Object> event) {
    if (!closed.get() && !queue.offer(event)) {
      dropped.incrementAndGet();
    }
  }

  void finish() {
    closed.set(true);
    queue.clear();
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   * @return The server response or computed SDK result; exact shape depends on the operation.
   */
  public boolean isClosed() {
    return closed.get();
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   * @return The server response or computed SDK result; exact shape depends on the operation.
   */
  public long dropped() {
    return dropped.get();
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * @param timeout Time budget for the request; timeout does not guarantee server-side cancellation.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   * @return The server response or computed SDK result; exact shape depends on the operation.
   */
  public Map<String, Object> next(Duration timeout) {
    if (closed.get()) {
      return null;
    }

    try {
      return queue.poll(timeout.toNanos(), TimeUnit.NANOSECONDS);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      throw new AmaquetException("subscription interrupted", e);
    }
  }

  /**
   * Stops delivery of events and releases this subscription's local state.
   *
   * @return {@code true} when an unsubscribe request was sent; {@code false} if already closed
   */
  public boolean unsubscribe() {
    if (closed.get()) {
      return false;
    }

    return client.unsubscribe(key, channel);
  }

  /** Stops delivery of events and releases this subscription's local state. */
  @Override
  public void close() {
    unsubscribe();
  }
}
