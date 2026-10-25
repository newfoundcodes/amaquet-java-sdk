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

import java.nio.*;

/** Amaquet protocol v1 framing, encoding, decoding, and bounded parsing helpers. */
public final class Protocol {
  private Protocol() {}

  /** Fixed protocol header size, in bytes. */
  public static final int HEADER = 24;

  /** Maximum protocol payload size, in bytes. */
  public static final int MAX_PAYLOAD = 64 * 1024 * 1024;

  /** Frame kind for a client request. */
  public static final int REQUEST = 1;

  /** Frame kind for a server response. */
  public static final int RESPONSE = 2;

  /** Frame kind for a server event. */
  public static final int EVENT = 3;

  /** Command opcode. */
  public static final int COMMAND = 1;

  /** Handshake opcode. */
  public static final int HELLO = 2;

  /** Authentication opcode. */
  public static final int AUTH = 3;

  /** Subscription opcode. */
  public static final int SUBSCRIBE = 4;

  /** Unsubscription opcode. */
  public static final int UNSUBSCRIBE = 5;

  /** Cancellation opcode. */
  public static final int CANCEL = 6;

  /**
   * A complete protocol frame.
   *
   * @param kind frame kind, such as {@link #REQUEST}, {@link #RESPONSE}, or {@link #EVENT}
   * @param opcode operation opcode
   * @param requestId request identifier associated with the frame
   * @param flags protocol flags
   * @param payload frame payload
   */
  public record Frame(int kind, int opcode, long requestId, int flags, byte[] payload) {
    /**
     * Creates a frame with no protocol flags.
     *
     * @param kind frame kind
     * @param opcode operation opcode
     * @param id request identifier
     * @param bytes frame payload
     */
    public Frame(int kind, int opcode, long id, byte[] bytes) {
      this(kind, opcode, id, 0, bytes);
    }
  }

  /**
   * Parsed fixed-size protocol header.
   *
   * @param kind frame kind
   * @param opcode operation opcode
   * @param requestId request identifier
   * @param flags protocol flags
   * @param size declared payload size
   */
  public record Header(int kind, int opcode, long requestId, int flags, int size) {}

  /**
   * Encode using the implementation's validation, error, and resource-lifetime rules.
   * Protocol v1 frames use a fixed 24-byte header and a maximum 64 MiB payload; reject malformed frames.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   * @param f value supplied for f.
   * @return The server response or computed SDK result; exact shape depends on the operation.
   */
  public static byte[] encode(Frame f) {
    return encode(f, MAX_PAYLOAD);
  }

  /**
   * Encode using the implementation's validation, error, and resource-lifetime rules.
   * Protocol v1 frames use a fixed 24-byte header and a maximum 64 MiB payload; reject malformed frames.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   * @param f value supplied for f.
   * @param max value supplied for max.
   * @return The server response or computed SDK result; exact shape depends on the operation.
   */
  public static byte[] encode(Frame f, int max) {
    if (f.kind < 1
        || f.kind > 3
        || f.opcode < 1
        || f.opcode > 6
        || f.requestId < 0
        || f.flags < 0
        || f.flags > 65535
        || f.payload == null
        || f.payload.length > Math.min(MAX_PAYLOAD, max)) {
      throw new IllegalArgumentException("invalid Amaquet frame");
    }

    ByteBuffer b = ByteBuffer.allocate(HEADER + f.payload.length).order(ByteOrder.BIG_ENDIAN);
    b.put(new byte[] {'A', 'M', 'Q', 'T'})
        .put((byte) 1)
        .put((byte) f.kind)
        .putShort((short) f.flags)
        .putShort((short) f.opcode)
        .putShort((short) 0)
        .putLong(f.requestId)
        .putInt(f.payload.length)
        .put(f.payload);

    return b.array();
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * Protocol v1 frames use a fixed 24-byte header and a maximum 64 MiB payload; reject malformed frames.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   * @param raw value supplied for raw.
   * @param max value supplied for max.
   * @return The server response or computed SDK result; exact shape depends on the operation.
   */
  public static Header header(byte[] raw, int max) {
    if (raw.length != HEADER) {
      throw new IllegalArgumentException("invalid frame header length");
    }

    ByteBuffer b = ByteBuffer.wrap(raw).order(ByteOrder.BIG_ENDIAN);
    if (b.get() != 'A' || b.get() != 'M' || b.get() != 'Q' || b.get() != 'T' || b.get() != 1) {
      throw new IllegalArgumentException("invalid magic/version");
    }

    int kind = Byte.toUnsignedInt(b.get()),
        flags = Short.toUnsignedInt(b.getShort()),
        opcode = Short.toUnsignedInt(b.getShort()),
        reserved = Short.toUnsignedInt(b.getShort());
    long id = b.getLong();
    long size = Integer.toUnsignedLong(b.getInt());

    if (kind < 1
        || kind > 3
        || opcode < 1
        || opcode > 6
        || reserved != 0
        || size > Math.min(MAX_PAYLOAD, max)) {
      throw new IllegalArgumentException("invalid Amaquet frame header");
    }

    return new Header(kind, opcode, id, flags, (int) size);
  }

  /** Stateful parser, bounded to one declared payload and a single 24-byte header. */
  public static final class Decoder {
    private final int max;
    private byte[] header = new byte[HEADER];
    private int headUsed;
    private Header current;
    private byte[] payload;
    private int used;

    /**
     * Creates a decoder with a maximum payload limit.
     *
     * @param max maximum accepted payload size, in bytes
     */
    public Decoder(int max) {
      if (max < 1 || max > MAX_PAYLOAD) {
        throw new IllegalArgumentException("payload limit");
      }

      this.max = max;
    }

    /**
     * Performs the corresponding documented Amaquet operation.
     * Protocol v1 frames use a fixed 24-byte header and a maximum 64 MiB payload; reject malformed frames.
     * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
     * @param bytes value supplied for bytes.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public java.util.List<Frame> feed(byte[] bytes) {
      java.util.List<Frame> out = new java.util.ArrayList<>();
      int i = 0;

      while (i < bytes.length || current != null && payload.length == 0) {
        if (current == null) {
          int n = Math.min(HEADER - headUsed, bytes.length - i);
          System.arraycopy(bytes, i, header, headUsed, n);

          headUsed += n;
          i += n;

          if (headUsed != HEADER) {
            break;
          }

          current = Protocol.header(header, max);
          headUsed = 0;

          payload = new byte[current.size()];
          used = 0;
        }

        int n = Math.min(payload.length - used, bytes.length - i);
        System.arraycopy(bytes, i, payload, used, n);

        used += n;
        i += n;

        if (used < payload.length) {
          break;
        }

        out.add(new Frame(current.kind, current.opcode, current.requestId, current.flags, payload));

        current = null;
        payload = null;
      }

      return out;
    }

    /**
     * Performs the corresponding documented Amaquet operation.
     * Protocol v1 frames use a fixed 24-byte header and a maximum 64 MiB payload; reject malformed frames.
     * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public int bufferedBytes() {
      return current == null ? headUsed : HEADER + used;
    }
  }
}
