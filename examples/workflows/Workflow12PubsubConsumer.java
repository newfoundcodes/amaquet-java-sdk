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

import com.newfoundcodes.amaquet.*;
import java.util.*;

/** Example 12: PubsubConsumer. Connects to a running Amaquet server. */
public final class Workflow12PubsubConsumer {
  /** Creates an instance of this example entrypoint. */
  public Workflow12PubsubConsumer() {}

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
        try (Subscription sub = c.subscribe(key, "updates", 16, 64)) {
          System.out.println(sub.next(java.time.Duration.ofSeconds(5)));
          System.out.println(sub.dropped());
        }
      } finally {
        c.delete(key);
      }
    }
  }
}
