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

import java.math.*;
import java.util.*;

/** Repeatable 13-target, coverage/property/mutation fuzz harness. No external fuzzer needed. */
public final class FuzzTargets {
  /** Creates the test entrypoint. */
  public FuzzTargets() {}

  static final int TARGETS = 13;
  static final String[] NAMES = {
    "json-roundtrip",
    "json-malformed",
    "utf8-errors",
    "unicode-escapes",
    "frame-header-mutations",
    "frame-splits",
    "frame-concatenation",
    "frame-payload-limits",
    "endpoint-parser",
    "admin-path-validation",
    "write-options",
    "identity-base64",
    "numeric-precision"
  };

  static final long SEED = 0xA6A6A67378L;
  static int passed;

  static void check(boolean b, String description) {
    if (!b) {
      throw new AssertionError(description);
    }
  }

  static String word(Random r, int limit) {
    String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789 /\\\"_";
    int n = r.nextInt(limit + 1);

    StringBuilder b = new StringBuilder();
    for (int i = 0; i < n; i++) {
      b.append(chars.charAt(r.nextInt(chars.length())));
    }

    return b.toString();
  }

  static byte[] randomBytes(Random r, int max) {
    byte[] bytes = new byte[r.nextInt(max + 1)];
    r.nextBytes(bytes);

    return bytes;
  }

  static void fuzz(int target, Random r) {
    switch (target) {
      case 0 -> {
        Object o =
            Json.map(
                "key",
                word(r, 60),
                "value",
                new BigInteger(1, randomBytes(r, 100)),
                "array",
                List.of(r.nextBoolean(), r.nextInt(), word(r, 25)));
        check(
            Json.parse(Json.encode(o)).equals(Json.parse(Json.encode(Json.parse(Json.encode(o))))),
            "JSON roundtrip mismatch");
      }

      case 1 -> {
        byte[] bytes = randomBytes(r, 180);
        try {
          Object x = Json.parse(bytes);
          check(Json.parse(Json.encode(x)) != null || x == null, "cannot reparse JSON");
        } catch (IllegalArgumentException accepted) {
        }
      }

      case 2 -> {
        byte[] bytes = randomBytes(r, 100);
        try {
          Object x = Json.parse(bytes);
          check(Json.parse(Json.encode(x)) != null || x == null, "UTF8 roundtrip failure");
        } catch (IllegalArgumentException accepted) {
        }
      }

      case 3 -> {
        String input = "\"" + word(r, 20).replace("\\", "\\\\").replace("\"", "\\\"") + "\"";
        try {
          Object v = Json.parse(input);
          check(Json.parse(Json.encode(v)).equals(v), "unicode mismatch");
        } catch (IllegalArgumentException accepted) {
        }
      }

      case 4 -> {
        byte[] frame =
            Protocol.encode(
                new Protocol.Frame(
                    Protocol.REQUEST,
                    Protocol.COMMAND,
                    r.nextInt(Integer.MAX_VALUE),
                    randomBytes(r, 130)));

        int index = r.nextInt(24);
        frame[index] = (byte) r.nextInt(256);

        try {
          Protocol.Header h = Protocol.header(Arrays.copyOf(frame, 24), 256);
          check(h.size() <= 256, "header exceeded limit");
        } catch (IllegalArgumentException accepted) {
        }
      }

      case 5 -> {
        byte[] raw = Protocol.encode(new Protocol.Frame(1, 1, 123, randomBytes(r, 4096)));
        Protocol.Decoder d = new Protocol.Decoder(8192);

        int i = 0, n = 0;
        while (i < raw.length) {
          int chunk = Math.min(raw.length - i, 1 + r.nextInt(30));
          n += d.feed(Arrays.copyOfRange(raw, i, i + chunk)).size();
          i += chunk;
        }

        check(n == 1 && d.bufferedBytes() == 0, "fragmentation lost frame");
      }

      case 6 -> {
        byte[] a = Protocol.encode(new Protocol.Frame(1, 2, 5, randomBytes(r, 128))),
            b = Protocol.encode(new Protocol.Frame(2, 4, 10, randomBytes(r, 128)));
        byte[] c = new byte[a.length + b.length];

        System.arraycopy(a, 0, c, 0, a.length);
        System.arraycopy(b, 0, c, a.length, b.length);

        check(new Protocol.Decoder(1024).feed(c).size() == 2, "concatenation failed");
      }

      case 7 -> {
        int max = 1 + r.nextInt(8192), len = r.nextInt(16384);
        byte[] bytes = new byte[len];

        try {
          Protocol.encode(new Protocol.Frame(1, 1, 1, bytes), max);
          check(len <= max, "oversized payload admitted");
        } catch (IllegalArgumentException e) {
          check(len > max, "valid payload rejected");
        }
      }

      case 8 -> {
        String s = r.nextBoolean() ? "amaquet://localhost:" + (1 + r.nextInt(65535)) : word(r, 100);
        try {
          Endpoint e = Endpoint.parse(s);
          check(e.port() >= 1 && e.port() <= 65535, "invalid endpoint admitted");
        } catch (IllegalArgumentException accepted) {
        }
      }

      case 9 -> {
        String s = "/api/members/" + word(r, 40).replace(" ", "%20");
        try {
          AdminClient.pathId(s.substring(s.lastIndexOf('/') + 1));
        } catch (IllegalArgumentException accepted) {
        }
      }

      case 10 -> {
        Map<String, Object> options =
            Json.map(
                "nx", r.nextBoolean(), "xx", r.nextBoolean(), "ttl_ms", (long) r.nextInt(100000));

        try {
          Map<String, Object> valid = Client.validateWriteOptions(options);
          check(
              !(Boolean.TRUE.equals(valid.get("nx")) && Boolean.TRUE.equals(valid.get("xx"))),
              "conflict accepted");
        } catch (IllegalArgumentException accepted) {
        }
      }

      case 11 -> {
        byte[] original = randomBytes(r, 300);
        String encoded = Base64.getEncoder().encodeToString(original);

        try {
          byte[] decoded = Identity.b64(encoded);
          check(Arrays.equals(original, decoded), "identity base64 mismatch");
        } catch (IllegalArgumentException e) {
          check(encoded.length() > 1024, "valid short base64 rejected");
        }
      }

      case 12 -> {
        BigInteger n = new BigInteger(1, randomBytes(r, 150));
        String encoded = Json.stringify(n);
        check(n.equals(Json.parse(encoded)), "large integer precision lost");

        BigDecimal d = new BigDecimal(n, 1 + r.nextInt(100));
        check(d.equals(Json.parse(Json.stringify(d))), "decimal precision lost");
      }

      default -> throw new AssertionError("unknown fuzz target");
    }
  }

  /**
   * Runs the fuzzing target entry point.
   *
   * @param args command-line arguments; the first argument controls cases per target
   */
  public static void main(String[] args) {
    int runs = args.length == 0 ? 2000 : Integer.parseInt(args[0]);
    if (runs < 1 || runs > 1_000_000) {
      throw new IllegalArgumentException("cases per target must be 1..1m");
    }

    long total = 0;
    for (int t = 0; t < TARGETS; t++) {
      Random r = new Random(SEED + t);
      for (int j = 0; j < runs; j++) {
        try {
          fuzz(t, r);
        } catch (Throwable e) {
          throw new AssertionError(
              "Fuzz failure target=" + NAMES[t] + " seed=" + (SEED + t) + " case=" + j, e);
        }

        total++;
      }

      System.out.println("PASS " + NAMES[t] + ": " + runs + " generated cases");
    }

    System.out.println("PASS " + total + " fuzz cases across " + TARGETS + " targets");
  }
}
