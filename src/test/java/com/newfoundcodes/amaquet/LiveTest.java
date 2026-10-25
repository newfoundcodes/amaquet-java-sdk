package com.newfoundcodes.amaquet;

import java.io.*;
import java.nio.file.*;
import java.security.SecureRandom;
import java.time.Duration;
import java.util.*;

/** Opt-in integration test for a dedicated running Amaquet deployment.
 *
 * Set AMAQUET_TEST_URL and AMAQUET_TEST_API_KEY before invoking this class.
 * Set AMAQUET_TEST_ALL_OPS=1 to run potentially mutating/blocking documented OP scenarios.
 */
public final class LiveTest {
  /** Creates the test entrypoint. */
  public LiveTest() {}

  private static String value(String name) {
    String s = System.getenv(name);
    return s == null || s.isBlank() ? null : s;
  }

  /**
   * Runs the opt-in integration test against the configured Amaquet server.
   *
   * @param args command-line arguments; the test reads its settings from environment variables
   * @throws Exception if the integration test cannot connect or complete
   */
  public static void main(String[] args) throws Exception {
    String uri = value("AMAQUET_TEST_URL");
    if (uri == null) {
      System.out.println("SKIP live tests: AMAQUET_TEST_URL not set");
      return;
    }

    boolean allOps = "1".equals(value("AMAQUET_TEST_ALL_OPS"));
    Path root = Path.of(System.getProperty("amaquet.projectRoot", "."));
    List<String> failures = new ArrayList<>();
    int ok = 0, operations = 0;

    Client.Options opts =
        new Client.Options().apiKey(value("AMAQUET_TEST_API_KEY")).timeout(Duration.ofSeconds(20));

    String pin = value("AMAQUET_TEST_IDENTITY_FINGERPRINT");
    if (pin != null) {
      opts.fingerprint(pin);
    }

    try (Client client = Client.connect(uri, opts)) {
      Object typesResult = client.types();
      if (!(typesResult instanceof List<?> wireTypes)) {
        throw new AssertionError("server TYPES must be a list");
      }

      Set<?> names = new HashSet<>(wireTypes);
      for (String type : TypedHandles.CATALOG.keySet()) {
        if (!names.contains(type)) {
          failures.add(type + ": missing from server TYPES");
          continue;
        }

        Map<String, Object> scenario =
            Json.object(
                Json.parse(
                    Files.readAllBytes(root.resolve("examples/scenarios/" + type + ".json"))));
        String key = "java:sdk:live:" + type + ":" + UUID.randomUUID().toString().replace("-", "");

        try {
          Map<String, Object> construct = Json.object(scenario.get("construction"));
          Map<String, Object> argsCopy = new LinkedHashMap<>(Json.object(construct.get("args")));

          argsCopy.put("key", key);
          client.command((String) construct.get("command"), argsCopy);

          Map<String, Object> snapshot = Json.object(client.get(key));
          if (!type.equals(snapshot.get("type"))) {
            throw new AssertionError("stored type mismatch: " + snapshot.get("type"));
          }

          if (allOps) {
            for (Object operation : (List<?>) scenario.get("operations")) {
              Map<String, Object> raw = Json.object(operation),
                  call = new LinkedHashMap<>(Json.object(raw.get("args")));

              call.put("key", key);
              client.command((String) raw.get("command"), call, Duration.ofSeconds(10));

              operations++;
            }
          }

          ok++;
        } catch (Exception | AssertionError e) {
          failures.add(type + ": " + e);
        } finally {
          try {
            client.delete(key);
          } catch (RuntimeException ignored) {
          }
        }
      }

      String blob = "java:sdk:live:blob:" + UUID.randomUUID();
      byte[] bytes = new byte[1_300_019];
      new SecureRandom().nextBytes(bytes);

      try {
        client.uploadBlob(blob, new ByteArrayInputStream(bytes), bytes.length, 100_001, false);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        client.downloadBlob(blob, out, 99_997, bytes.length);

        if (!Arrays.equals(bytes, out.toByteArray())) {
          failures.add("blob: roundtrip mismatch");
        }
      } catch (RuntimeException e) {
        failures.add("blob: " + e);
      } finally {
        try {
          client.delete(blob);
        } catch (RuntimeException ignored) {
        }
      }
    }

    System.out.println(
        "Live construction/snapshot verified: "
            + ok
            + "/91; OPs: "
            + operations
            + "; failures: "
            + failures.size());

    for (String fail : failures) {
      System.err.println("FAIL " + fail);
    }

    if (ok != 91 || !failures.isEmpty()) {
      throw new AssertionError("live Amaquet integration failed");
    }
  }
}
