import com.newfoundcodes.amaquet.*;
import java.util.*;

/** Example 38: BlobStream. Connects to a running Amaquet server. */
public final class Workflow38BlobStream {
  /** Creates an instance of this example entrypoint. */
  public Workflow38BlobStream() {}

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
      byte[] bytes = new byte[8192];
      new java.util.Random(1).nextBytes(bytes);
      try {
        c.uploadBlob(key, new java.io.ByteArrayInputStream(bytes), bytes.length, 2048, false);
        java.io.ByteArrayOutputStream out = new java.io.ByteArrayOutputStream();

        c.downloadBlob(key, out, 2048, 10000);
        System.out.println(out.size());
      } finally {
        c.delete(key);
      }
    }
  }
}
