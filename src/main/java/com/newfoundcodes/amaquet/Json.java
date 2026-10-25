package com.newfoundcodes.amaquet;

import java.math.*;
import java.nio.*;
import java.nio.charset.*;
import java.util.*;

/** Bounded, exact-number JSON codec; consumes and produces ordinary Java values. */
/**
 * Performs the corresponding documented Amaquet operation.
 * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
 */
public final class Json {
  private Json() {}

  /**
   * Encode using the implementation's validation, error, and resource-lifetime rules.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   * @param data value supplied for data.
   * @return The server response or computed SDK result; exact shape depends on the operation.
   */
  public static byte[] encode(Object data) {
    return stringify(data).getBytes(StandardCharsets.UTF_8);
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   * @param data value supplied for data.
   * @return The server response or computed SDK result; exact shape depends on the operation.
   */
  public static String stringify(Object data) {
    StringBuilder sb = new StringBuilder();
    write(sb, data, 0);

    return sb.toString();
  }

  private static void write(StringBuilder sb, Object x, int depth) {
    if (depth > 128) {
      throw new IllegalArgumentException("JSON nesting limit exceeded");
    }

    if (x == null) {
      sb.append("null");
    } else if (x instanceof String s) {
      quote(sb, s);
    } else if (x instanceof Boolean b) {
      sb.append(b);
    } else if (x instanceof BigDecimal d) {
      sb.append(d.toPlainString());
    } else if (x instanceof BigInteger
        || x instanceof Byte
        || x instanceof Short
        || x instanceof Integer
        || x instanceof Long) {
      sb.append(x);
    } else if (x instanceof Double d) {
      if (!Double.isFinite(d)) {
        throw new IllegalArgumentException("non-finite number");
      }

      sb.append(d);
    } else if (x instanceof Float f) {
      if (!Float.isFinite(f)) {
        throw new IllegalArgumentException("non-finite number");
      }

      sb.append(f);
    } else if (x instanceof Map<?, ?> m) {
      sb.append('{');

      boolean first = true;
      for (var e : m.entrySet()) {
        if (!(e.getKey() instanceof String s)) {
          throw new IllegalArgumentException("non-string JSON object key");
        }

        if (!first) {
          sb.append(',');
        }

        first = false;
        quote(sb, s);

        sb.append(':');
        write(sb, e.getValue(), depth + 1);
      }

      sb.append('}');
    } else if (x instanceof Collection<?> c) {
      sb.append('[');

      boolean first = true;
      for (Object item : c) {
        if (!first) {
          sb.append(',');
        }

        first = false;
        write(sb, item, depth + 1);
      }

      sb.append(']');
    } else if (x.getClass().isArray() && !(x instanceof byte[])) {
      sb.append('[');

      int n = java.lang.reflect.Array.getLength(x);
      for (int i = 0; i < n; i++) {
        if (i > 0) {
          sb.append(',');
        }

        write(sb, java.lang.reflect.Array.get(x, i), depth + 1);
      }

      sb.append(']');
    } else {
      throw new IllegalArgumentException("unsupported JSON value: " + x.getClass());
    }
  }

  private static void quote(StringBuilder sb, String s) {
    sb.append('"');

    for (int i = 0; i < s.length(); i++) {
      char c = s.charAt(i);
      if (c == '"' || c == '\\') {
        sb.append('\\').append(c);
      } else if (c == '\b') {
        sb.append("\\b");
      } else if (c == '\f') {
        sb.append("\\f");
      } else if (c == '\n') {
        sb.append("\\n");
      } else if (c == '\r') {
        sb.append("\\r");
      } else if (c == '\t') {
        sb.append("\\t");
      } else if (c < 0x20) {
        sb.append(String.format("\\u%04x", (int) c));
      } else if (Character.isHighSurrogate(c)) {
        if (i + 1 == s.length() || !Character.isLowSurrogate(s.charAt(i + 1))) {
          throw new IllegalArgumentException("unpaired surrogate");
        }

        sb.append(c).append(s.charAt(++i));
      } else if (Character.isLowSurrogate(c)) {
        throw new IllegalArgumentException("unpaired surrogate");
      } else {
        sb.append(c);
      }
    }

    sb.append('"');
  }

  /**
   * Parse using the implementation's validation, error, and resource-lifetime rules.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   * @param bytes value supplied for bytes.
   * @return The server response or computed SDK result; exact shape depends on the operation.
   */
  public static Object parse(byte[] bytes) {
    try {
      return parse(
          StandardCharsets.UTF_8
              .newDecoder()
              .onMalformedInput(CodingErrorAction.REPORT)
              .onUnmappableCharacter(CodingErrorAction.REPORT)
              .decode(ByteBuffer.wrap(bytes))
              .toString());
    } catch (CharacterCodingException e) {
      throw new IllegalArgumentException("invalid UTF-8 JSON", e);
    }
  }

  /**
   * Parse using the implementation's validation, error, and resource-lifetime rules.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   * @param text value supplied for text.
   * @return The server response or computed SDK result; exact shape depends on the operation.
   */
  public static Object parse(String text) {
    Parser p = new Parser(text);
    Object value = p.value(0);
    p.ws();

    if (p.pos != text.length()) {
      throw new IllegalArgumentException("trailing JSON");
    }

    return value;
  }

  /**
   * Casts a decoded JSON value to an object map.
   *
   * @param x decoded JSON value
   * @return the value as a string-keyed object map
   * @throws IllegalArgumentException if {@code x} is not a JSON object
   */
  @SuppressWarnings("unchecked")
  public static Map<String, Object> object(Object x) {
    if (!(x instanceof Map<?, ?>)) {
      throw new IllegalArgumentException("expected JSON object");
    }

    return (Map<String, Object>) x;
  }

  /**
   * Builds a string-keyed JSON object map from alternating keys and values.
   *
   * @param kv alternating string keys and JSON-compatible values
   * @return a mutable insertion-ordered map
   * @throws IllegalArgumentException if the number of arguments is odd
   */
  public static Map<String, Object> map(Object... kv) {
    if (kv.length % 2 != 0) {
      throw new IllegalArgumentException("odd JSON key/value list");
    }

    Map<String, Object> m = new LinkedHashMap<>();
    for (int i = 0; i < kv.length; i += 2) {
      m.put((String) kv[i], kv[i + 1]);
    }

    return m;
  }

  /**
   * Parser using the implementation's validation, error, and resource-lifetime rules.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   */
  private static final class Parser {
    /**
     * Parser using the implementation's validation, error, and resource-lifetime rules.
     * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
     */
    final String s;

    int pos;

    Parser(String s) {
      this.s = Objects.requireNonNull(s);
    }

    void ws() {
      while (pos < s.length()
          && (s.charAt(pos) == ' '
              || s.charAt(pos) == '\t'
              || s.charAt(pos) == '\n'
              || s.charAt(pos) == '\r')) {
        pos++;
      }
    }

    char next() {
      if (pos >= s.length()) {
        throw new IllegalArgumentException("truncated JSON");
      }

      return s.charAt(pos++);
    }

    Object value(int depth) {
      if (depth > 128) {
        throw new IllegalArgumentException("JSON nesting limit exceeded");
      }
      ws();

      char c = next();
      if (c == '"') {
        return string();
      }

      if (c == '{') {
        Map<String, Object> m = new LinkedHashMap<>();
        ws();

        if (pos < s.length() && s.charAt(pos) == '}') {
          pos++;
          return m;
        }

        do {
          ws();

          if (next() != '"') {
            throw new IllegalArgumentException("expected object key");
          }

          String k = string();
          ws();

          if (next() != ':') {
            throw new IllegalArgumentException("expected colon");
          }

          Object v = value(depth + 1);
          if (m.containsKey(k)) {
            throw new IllegalArgumentException("duplicate JSON key");
          }

          m.put(k, v);
          ws();

          char sep = next();
          if (sep == '}') {
            return m;
          }

          if (sep != ',') {
            throw new IllegalArgumentException("expected comma");
          }
        } while (true);
      }

      if (c == '[') {
        List<Object> a = new ArrayList<>();
        ws();

        if (pos < s.length() && s.charAt(pos) == ']') {
          pos++;
          return a;
        }

        do {
          a.add(value(depth + 1));
          ws();

          char sep = next();
          if (sep == ']') {
            return a;
          }

          if (sep != ',') {
            throw new IllegalArgumentException("expected comma");
          }
        } while (true);
      }

      if (c == 't') {
        literal("rue");
        return true;
      }

      if (c == 'f') {
        literal("alse");
        return false;
      }

      if (c == 'n') {
        literal("ull");
        return null;
      }

      if (c == '-' || (c >= '0' && c <= '9')) {
        int start = pos - 1;
        if (c == '-') {
          c = next();
        }

        if (c == '0') {
          if (pos < s.length() && Character.isDigit(s.charAt(pos))) {
            throw new IllegalArgumentException("leading zero");
          }
        } else {
          if (c < '1' || c > '9') {
            throw new IllegalArgumentException("invalid number");
          }

          while (pos < s.length() && asciiDigit(s.charAt(pos))) {
            pos++;
          }
        }

        boolean decimal = false;
        if (pos < s.length() && s.charAt(pos) == '.') {
          decimal = true;
          pos++;

          if (pos >= s.length() || !asciiDigit(s.charAt(pos))) {
            throw new IllegalArgumentException("invalid fraction");
          }

          while (pos < s.length() && asciiDigit(s.charAt(pos))) {
            pos++;
          }
        }

        if (pos < s.length() && (s.charAt(pos) == 'e' || s.charAt(pos) == 'E')) {
          decimal = true;
          pos++;

          if (pos < s.length() && (s.charAt(pos) == '+' || s.charAt(pos) == '-')) {
            pos++;
          }

          if (pos >= s.length() || !asciiDigit(s.charAt(pos))) {
            throw new IllegalArgumentException("invalid exponent");
          }

          while (pos < s.length() && asciiDigit(s.charAt(pos))) {
            pos++;
          }
        }

        String n = s.substring(start, pos);
        return decimal ? new BigDecimal(n) : new BigInteger(n);
      }

      throw new IllegalArgumentException("invalid JSON token");
    }

    /**
     * Performs the corresponding documented Amaquet operation.
     * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
     * @param c value supplied for c.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    static boolean asciiDigit(char c) {
      return c >= '0' && c <= '9';
    }

    void literal(String t) {
      for (int i = 0; i < t.length(); i++) {
        if (next() != t.charAt(i)) {
          throw new IllegalArgumentException("invalid literal");
        }
      }
    }

    String string() {
      StringBuilder b = new StringBuilder();
      while (true) {
        char c = next();
        if (c == '"') {
          return b.toString();
        }

        if (c < 0x20) {
          throw new IllegalArgumentException("control character");
        }

        if (c == '\\') {
          char e = next();
          switch (e) {
            case '"', '\\', '/' -> b.append(e);
            case 'b' -> b.append('\b');
            case 'f' -> b.append('\f');
            case 'n' -> b.append('\n');
            case 'r' -> b.append('\r');
            case 't' -> b.append('\t');
            case 'u' -> {
              int code = hex4();
              char ch = (char) code;

              if (Character.isHighSurrogate(ch)) {
                if (next() != '\\' || next() != 'u') {
                  throw new IllegalArgumentException("invalid surrogate pair");
                }

                char low = (char) hex4();
                if (!Character.isLowSurrogate(low)) {
                  throw new IllegalArgumentException("invalid low surrogate");
                }

                b.append(ch).append(low);
              } else if (Character.isLowSurrogate(ch)) {
                throw new IllegalArgumentException("unexpected low surrogate");
              } else {
                b.append(ch);
              }
            }
            default -> throw new IllegalArgumentException("invalid escape");
          }
        } else {
          if (Character.isHighSurrogate(c)) {
            char low = next();
            if (!Character.isLowSurrogate(low)) {
              throw new IllegalArgumentException("invalid surrogate");
            }

            b.append(c).append(low);
          } else if (Character.isLowSurrogate(c)) {
            throw new IllegalArgumentException("invalid surrogate");
          } else {
            b.append(c);
          }
        }
      }
    }

    int hex4() {
      int n = 0;
      for (int i = 0; i < 4; i++) {
        int v = Character.digit(next(), 16);
        if (v < 0) {
          throw new IllegalArgumentException("bad Unicode escape");
        }

        n = n * 16 + v;
      }

      return n;
    }
  }
}
