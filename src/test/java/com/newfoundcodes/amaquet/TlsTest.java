package com.newfoundcodes.amaquet;

import java.io.*;
import java.nio.file.*;
import java.security.*;
import java.time.Duration;
import javax.net.ssl.*;

/** True JSSE loopback TLS + Ed25519 server identity regression. */
public final class TlsTest {
  /** Creates the test entrypoint. */
  public TlsTest() {}

  /**
   * Runs this example against the configured Amaquet server.
   *
   * @param args command-line arguments; the examples use their source-defined defaults.
   * @throws Exception if the example cannot connect or complete its scenario.
   */
  public static void main(String[] args) throws Exception {
    if (args.length != 1) {
      throw new IllegalArgumentException("provide PKCS12 keystore path");
    }

    KeyStore ks = KeyStore.getInstance("PKCS12");
    try (InputStream in = Files.newInputStream(Path.of(args[0]))) {
      ks.load(in, "testpass".toCharArray());
    }

    KeyManagerFactory km = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
    km.init(ks, "testpass".toCharArray());

    TrustManagerFactory tm =
        TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
    tm.init(ks);

    SSLContext serverCtx = SSLContext.getInstance("TLS");
    serverCtx.init(km.getKeyManagers(), null, new SecureRandom());

    SSLContext clientCtx = SSLContext.getInstance("TLS");
    clientCtx.init(null, tm.getTrustManagers(), new SecureRandom());

    try (MockServer server = new MockServer(serverCtx)) {
      String uri = server.endpoint().replace("amaquet://127.0.0.1", "amaquets://localhost");
      Client.Options opts =
          new Client.Options()
              .apiKey("test-key")
              .tlsFactory(clientCtx.getSocketFactory())
              .identity(server.publicKey)
              .requireIdentity(true)
              .timeout(Duration.ofSeconds(2));

      try (Client client = Client.connect(uri, opts)) {
        if (!client.serverInfo().containsKey("identity_fingerprint") || client.ping() == null) {
          throw new AssertionError("TLS + Ed25519 handshake failed");
        }
      }

      boolean denied = false;
      try (Client invalid = Client.connect(uri, new Client.Options().apiKey("test-key"))) {
      } catch (AmaquetException.Transport | AmaquetException.Protocol expected) {
        denied = true;
      }

      if (!denied) {
        throw new AssertionError("self-signed certificate accepted without trust anchor");
      }
    }

    System.out.println("PASS TLS trust validation and signed server identity");
  }
}
