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

import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.*;
import java.util.*;

/** Verifies a signed server HELLO identity and optional public-key or fingerprint pins. */
public final class Identity {
  private Identity() {}

  /**
   * Verifies the signed server identity in a HELLO response.
   *
   * @param hello decoded HELLO response
   * @param nonce original challenge sent to the server
   * @param required whether the server must provide an identity
   * @param expectedKey expected public-key pin, or {@code null}
   * @param expectedFingerprint expected fingerprint pin, or {@code null}
   * @throws AmaquetException.Protocol if the identity is missing or invalid
   */
  public static void verify(
      Map<String, Object> hello,
      String nonce,
      boolean required,
      String expectedKey,
      String expectedFingerprint) {
    Object k = hello.get("identity_public_key"), sig = hello.get("nonce_signature");
    if (k == null && sig == null) {
      if (required || expectedKey != null || expectedFingerprint != null) {
        throw new AmaquetException.Protocol("server identity required");
      }

      return;
    }

    if (!(k instanceof String key) || !(sig instanceof String signature)) {
      throw new AmaquetException.Protocol("incomplete server identity");
    }

    try {
      byte[] publicKey = b64(key), signatureBytes = b64(signature);
      if (publicKey.length != 32 || signatureBytes.length != 64) {
        throw new IllegalArgumentException("invalid Ed25519 lengths");
      }

      String fingerprint =
          "ed25519:"
              + HexFormat.of().formatHex(MessageDigest.getInstance("SHA-256").digest(publicKey));
      Object advertised = hello.get("identity_fingerprint");

      if (advertised != null
          && (!(advertised instanceof String)
              || !MessageDigest.isEqual(
                  fingerprint.getBytes(StandardCharsets.UTF_8),
                  ((String) advertised).getBytes(StandardCharsets.UTF_8)))) {
        throw new IllegalArgumentException("invalid fingerprint");
      }

      if (expectedKey != null && !MessageDigest.isEqual(publicKey, b64(expectedKey))) {
        throw new IllegalArgumentException("server public key does not match pin");
      }

      if (expectedFingerprint != null
          && !MessageDigest.isEqual(
              fingerprint.getBytes(StandardCharsets.UTF_8),
              expectedFingerprint.getBytes(StandardCharsets.UTF_8))) {
        throw new IllegalArgumentException("server fingerprint does not match pin");
      }

      // SubjectPublicKeyInfo Ed25519 DER prefix (RFC 8410).
      byte[] der = HexFormat.of().parseHex("302a300506032b6570032100");
      byte[] x509 = Arrays.copyOf(der, der.length + 32);
      System.arraycopy(publicKey, 0, x509, der.length, 32);

      PublicKey verifyKey =
          KeyFactory.getInstance("Ed25519").generatePublic(new X509EncodedKeySpec(x509));
      Signature verifier = Signature.getInstance("Ed25519");

      verifier.initVerify(verifyKey);
      verifier.update(nonce.getBytes(StandardCharsets.UTF_8));

      if (!verifier.verify(signatureBytes)) {
        throw new IllegalArgumentException("invalid server challenge signature");
      }
    } catch (GeneralSecurityException | IllegalArgumentException e) {
      throw new AmaquetException.Protocol("server identity verification failed", e);
    }
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * @param value Data to write or transmit, using the wire-type JSON format.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   * @return The server response or computed SDK result; exact shape depends on the operation.
   */
  public static byte[] b64(String value) {
    if (value.length() > 1024 || !value.matches("[A-Za-z0-9+/]*={0,2}")) {
      throw new IllegalArgumentException("invalid base64");
    }

    byte[] b = Base64.getDecoder().decode(value);
    if (!Base64.getEncoder().encodeToString(b).equals(value)
        && !Base64.getEncoder().withoutPadding().encodeToString(b).equals(value)) {
      throw new IllegalArgumentException("noncanonical base64");
    }

    return b;
  }
}
