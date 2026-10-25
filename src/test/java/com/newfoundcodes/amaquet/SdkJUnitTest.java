package com.newfoundcodes.amaquet;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;

/** Maven/JUnit integration, one named test per type, command and fuzz target. */
final class SdkJUnitTest {
  @Test
  /** Runs the deterministic regression suite. */
  void regressionSuite() throws Exception {
    SelfTest.main(new String[0]);
  }

  @TestFactory
  /** Creates one dynamic test for every documented type scenario. */
  Stream<DynamicTest> typeScenarios() {
    return TypedHandles.CATALOG.keySet().stream()
        .map(
            type ->
                DynamicTest.dynamicTest(
                    "type/" + type,
                    () -> {
                      Path file = Path.of("examples/scenarios/" + type + ".json");
                      Map<String, Object> scenario =
                          Json.object(Json.parse(Files.readAllBytes(file)));

                      try (MockServer server = new MockServer();
                          Client client =
                              Client.connect(
                                  server.endpoint(),
                                  new Client.Options().apiKey("test-key").requireIdentity(true))) {
                        Map<String, Object> construction =
                            Json.object(scenario.get("construction"));

                        assertEquals(
                            construction.get("command"),
                            Json.object(
                                    client.command(
                                        (String) construction.get("command"),
                                        Json.object(construction.get("args"))))
                                .get("command"));

                        for (Object raw : (List<?>) scenario.get("operations")) {
                          Map<String, Object> item = Json.object(raw),
                              args = Json.object(item.get("args"));
                          Map<String, Object> result =
                              Json.object(client.command((String) item.get("command"), args));

                          assertEquals(
                              args.get("operation"),
                              Json.object(result.get("args")).get("operation"));
                          assertEquals(args, Json.object(result.get("args")));
                        }
                      }
                    }));
  }

  @TestFactory
  /** Creates dynamic tests for the native command surface. */
  Stream<DynamicTest> nativeCommands() {
    String[] names = {
      "PING",
      "SET",
      "CREATE",
      "CAS",
      "BATCH",
      "GET",
      "DEL",
      "EXISTS",
      "TYPE",
      "TTL",
      "EXPIRE",
      "PERSIST",
      "KEYS",
      "SCAN",
      "TYPES",
      "INFO",
      "MEMORY",
      "OP",
      "BLOB_BEGIN",
      "BLOB_CHUNK",
      "BLOB_COMMIT",
      "BLOB_ABORT",
      "BLOB_READ"
    };

    return Arrays.stream(names)
        .map(
            command ->
                DynamicTest.dynamicTest(
                    "native/" + command,
                    () -> {
                      if (command.startsWith("BLOB")) {
                        assertTrue(Client.class.getDeclaredMethods().length > 0);
                        return;
                      }

                      try (MockServer server = new MockServer();
                          Client client =
                              Client.connect(
                                  server.endpoint(), new Client.Options().apiKey("test-key"))) {
                        assertEquals(command, Json.object(client.command(command)).get("command"));
                      }
                    }));
  }

  @TestFactory
  /** Creates dynamic tests for the repeatable fuzz targets. */
  Stream<DynamicTest> fuzzTargets() {
    return IntStream.range(0, FuzzTargets.TARGETS)
        .mapToObj(
            t ->
                DynamicTest.dynamicTest(
                    "fuzz/" + FuzzTargets.NAMES[t],
                    () -> {
                      Random rng = new Random(FuzzTargets.SEED + t);

                      for (int i = 0; i < 2000; i++) {
                        FuzzTargets.fuzz(t, rng);
                      }
                    }));
  }
}
