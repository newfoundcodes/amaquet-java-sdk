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

package com.newfoundcodes.amaquet;

/** Base unchecked SDK exception for client, protocol, transport, and administration failures. */
public class AmaquetException extends RuntimeException {
  private static final long serialVersionUID = 1L;

  /**
   * Creates an SDK exception with a diagnostic message.
   *
   * @param message diagnostic message; credentials should not be included
   */
  public AmaquetException(String message) {
    super(message);
  }

  /**
   * Creates an SDK exception with a detail message and cause.
   *
   * @param message diagnostic message
   * @param cause underlying failure
   */
  public AmaquetException(String message, Throwable cause) {
    super(message, cause);
  }

  /** Indicates a protocol framing or server-protocol failure. */
  public static final class Protocol extends AmaquetException {
    private static final long serialVersionUID = 1L;

    /**
     * Creates a protocol exception.
     *
     * @param message protocol diagnostic message
     */
    public Protocol(String message) {
      super(message);
    }

    /**
     * Creates a protocol exception with its underlying cause.
     *
     * @param message protocol diagnostic message
     * @param cause underlying protocol failure
     */
    public Protocol(String message, Throwable cause) {
      super(message, cause);
    }
  }

  /** Indicates a server error associated with a request. */
  public static final class Request extends AmaquetException {
    private static final long serialVersionUID = 1L;
    /** Server error code. */
    public final String code;

    /**
     * Creates a request exception with the server error code and message.
     *
     * @param code server error code
     * @param message server error message
     */
    public Request(String code, String message) {
      super(message);
      this.code = code;
    }
  }

  /** Indicates that a request exceeded its configured time budget. */
  public static final class Timeout extends AmaquetException {
    private static final long serialVersionUID = 1L;

    /**
     * Creates a timeout exception with its underlying cause.
     *
     * @param message timeout diagnostic message
     * @param cause underlying timeout
     */
    public Timeout(String message, Throwable cause) {
      super(message, cause);
    }
  }

  /** Indicates an I/O or connection failure. */
  public static final class Transport extends AmaquetException {
    private static final long serialVersionUID = 1L;

    /**
     * Creates a transport exception with its underlying cause.
     *
     * @param message transport diagnostic message
     * @param cause underlying transport failure
     */
    public Transport(String message, Throwable cause) {
      super(message, cause);
    }
  }

  /** Indicates an HTTP administration request failure. */
  public static final class Http extends AmaquetException {
    private static final long serialVersionUID = 1L;
    /** HTTP status code returned by the administration endpoint. */
    public final int status;

    /** Server error code returned by the administration endpoint. */
    public final String code;

    /** Raw response body returned by the administration endpoint. */
    public final String body;

    /**
     * Creates an HTTP administration exception.
     *
     * @param status HTTP status code
     * @param code server error code
     * @param message exception message
     * @param body raw response body
     */
    public Http(int status, String code, String message, String body) {
      super(message);
      this.status = status;
      this.code = code;
      this.body = body;
    }
  }
}
