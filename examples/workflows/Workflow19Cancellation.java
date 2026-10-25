import com.newfoundcodes.amaquet.*;
import java.util.*;

/** Example 19: Cancellation. Connects to a running Amaquet server. */
public final class Workflow19Cancellation {
  /** Creates an instance of this example entrypoint. */
  public Workflow19Cancellation() {}

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
      Client.Request r = c.sendCommand("INFO", Map.of());

      System.out.println(c.cancel(r.id()));
      System.out.println(c.await(r));
    }
  }
}
