import com.newfoundcodes.amaquet.*;
import java.util.*;

/** Example 39: AbsoluteExpiration. Connects to a running Amaquet server. */
public final class Workflow39AbsoluteExpiration {
  /** Creates an instance of this example entrypoint. */
  public Workflow39AbsoluteExpiration() {}

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
      try {
        c.set(
            key,
            "integer",
            11,
            Json.map(
                "expires_at_unix_nano",
                java.math.BigInteger.valueOf(System.currentTimeMillis() + 60000)
                    .multiply(java.math.BigInteger.valueOf(1000000))));
        System.out.println(c.ttl(key));
      } finally {
        c.delete(key);
      }
    }
  }
}
