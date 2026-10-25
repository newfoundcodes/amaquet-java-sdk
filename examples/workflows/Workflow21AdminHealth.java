import com.newfoundcodes.amaquet.*;
import java.util.*;

/** Example 21: AdminHealth. Requires an Amaquet HTTP admin endpoint. */
public final class Workflow21AdminHealth {
  /** Creates an instance of this example entrypoint. */
  public Workflow21AdminHealth() {}

  /**
   * Runs this example against the configured Amaquet server.
   *
   * @param args command-line arguments; the examples use their source-defined defaults.
   * @throws Exception if the example cannot connect or complete its scenario.
   */
  public static void main(String[] args) throws Exception {
    String endpoint = System.getenv().getOrDefault("AMAQUET_ADMIN_URL", "http://127.0.0.1:13379");
    AdminClient a = new AdminClient(endpoint, System.getenv("AMAQUET_ADMIN_TOKEN"));

    System.out.println(a.health());
    System.out.println(a.ready());
  }
}
