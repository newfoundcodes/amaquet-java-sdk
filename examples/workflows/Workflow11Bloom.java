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

/** Example 11: Bloom. Connects to a running Amaquet server. */
public final class Workflow11Bloom {
  /** Creates an instance of this example entrypoint. */
  public Workflow11Bloom() {}

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
      TypeHandle h = c.typed().forType("bloom_filter", key);
      try {
        h.create(Json.map("expected", 100, "false_positive_rate", 0.01));
        System.out.println(h.snapshot());
      } finally {
        c.delete(key);
      }
    }
  }
}
