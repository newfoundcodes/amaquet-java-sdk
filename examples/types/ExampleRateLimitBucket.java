import com.newfoundcodes.amaquet.*;
import java.nio.file.*;
import java.util.*;

/** Source-backed scenario: examples/scenarios/rate_limit_bucket.json */
public class ExampleRateLimitBucket {
  /** Creates an instance of this example entrypoint. */
  public ExampleRateLimitBucket() {}

  /**
   * Runs this example against the configured Amaquet server.
   *
   * @param args command-line arguments; the examples use their source-defined defaults.
   * @throws Exception if the example cannot connect or complete its scenario.
   */
  public static void main(String[] args) throws Exception {
    String uri = System.getenv().getOrDefault("AMAQUET_URL", "amaquet://127.0.0.1:13378");
    Client.Options opts = new Client.Options().apiKey(System.getenv("AMAQUET_API_KEY"));

    try (Client client = Client.connect(uri, opts)) {
      Map<String, Object> scenario =
          Json.object(
              Json.parse(Files.readAllBytes(Path.of("examples/scenarios/rate_limit_bucket.json"))));
      Map<String, Object> construction = Json.object(scenario.get("construction"));

      System.out.println(
          "Creating rate_limit_bucket: "
              + client.command(
                  (String) construction.get("command"), Json.object(construction.get("args"))));

      // Each operation in the scenario can mutate or block. Enable explicitly when prepared.
      if (Boolean.parseBoolean(System.getenv().getOrDefault("AMAQUET_RUN_OPERATIONS", "false"))) {
        for (Object entry : (List<?>) scenario.get("operations")) {
          Map<String, Object> op = Json.object(entry);
          System.out.println(
              client.command((String) op.get("command"), Json.object(op.get("args"))));
        }
      }
    }
  }
}
