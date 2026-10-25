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

/** Example 29: AdminCreateKey. Requires an Amaquet HTTP admin endpoint. */
public final class Workflow29AdminCreateKey {
  /** Creates an instance of this example entrypoint. */
  public Workflow29AdminCreateKey() {}

  /**
   * Runs this example against the configured Amaquet server.
   *
   * @param args command-line arguments; the examples use their source-defined defaults.
   * @throws Exception if the example cannot connect or complete its scenario.
   */
  public static void main(String[] args) throws Exception {
    String endpoint = System.getenv().getOrDefault("AMAQUET_ADMIN_URL", "http://127.0.0.1:13379");
    AdminClient a = new AdminClient(endpoint, System.getenv("AMAQUET_ADMIN_TOKEN"));

    if (!Boolean.getBoolean("amaquet.allowAdminMutations")) {
      throw new IllegalStateException("-Damaquet.allowAdminMutations=true required");
    }

    System.out.println(a.createApiKey("example-java", "viewer", null));
  }
}
