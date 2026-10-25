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

/**
 * Provides the Amaquet Java SDK.
 *
 * <p>The package contains clients for the native Amaquet protocol and HTTP administration API,
 * typed handles for the supported wire types, protocol and JSON helpers, and lifecycle types for
 * subscriptions and pooled connections.
 *
 * <p>Clients perform network I/O and expose server responses as ordinary Java values. Applications
 * should configure timeouts, use TLS for remote connections, avoid embedding credentials in URI
 * strings unless explicitly enabled, and close clients, pools, and subscriptions when finished.
 */
package com.newfoundcodes.amaquet;
