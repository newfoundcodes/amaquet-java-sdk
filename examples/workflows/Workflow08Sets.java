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

/** Example 08: Sets. Connects to a running Amaquet server. */
public final class Workflow08Sets {
  /** Creates an instance of this example entrypoint. */
  public Workflow08Sets() {}

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
      TypeHandle h = c.typed().forType("set", key);
      try {
        h.create();

        System.out.println(h.op("ADD", Json.map("value", Json.map("type", "integer", "value", 3))));
        System.out.println(h.op("MEMBERS", Map.of()));
      } finally {
        c.delete(key);
      }
    }
  }
}
