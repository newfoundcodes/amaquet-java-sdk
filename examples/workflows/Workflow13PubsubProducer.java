import com.newfoundcodes.amaquet.*;
import java.util.*;

/** Example 13: PubsubProducer. Connects to a running Amaquet server. */
public final class Workflow13PubsubProducer {
  /** Creates an instance of this example entrypoint. */
  public Workflow13PubsubProducer() {}

  /**
   * Runs this example against the configured Amaquet server.
   *
   * @param args command-line arguments; the examples use their source-defined defaults.
   * @throws Exception if the example cannot connect or complete its scenario.
   */
  public static void main(String[] args) throws Exception {
    String uri = System.getenv().getOrDefault("AMAQUET_URL", "amaquet://127.0.0.1:13378");
    Client.Options options = new Client.Options().apiKey(System.getenv("AMAQUET_API_KEY"));
    String key = "java:example:" + java.util.UUID.randomUUID();

    try (Client c = Client.connect(uri, options)) {
      TypeHandle h = c.typed().forType("pubsub", key);
      try {
        h.create();
        System.out.println(
            h.op(
                "PUBLISH",
                Json.map(
                    "channel",
                    "updates",
                    "value",
                    Json.map("type", "utf8_string", "value", "hello"))));
      } finally {
        c.delete(key);
      }
    }
  }
}
