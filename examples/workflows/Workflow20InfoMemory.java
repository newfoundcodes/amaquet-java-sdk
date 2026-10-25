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

/** Example 20: InfoMemory. Connects to a running Amaquet server. */
public final class Workflow20InfoMemory {
  /** Creates an instance of this example entrypoint. */
  public Workflow20InfoMemory() {}

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
      System.out.println(c.info());
      System.out.println(c.memory(System.getenv().getOrDefault("AMAQUET_MEMORY_KEY", key)));
    }
  }
}
