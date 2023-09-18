package com.hms_networks.americas.sc.poc;

import java.io.File;
import org.bouncycastle.asn1.x509.Certificate;

/**
 * Proof-of-concept (poc) main class for the Ewon Flexy Bouncy Castle Proof of Concept Project.
 *
 * @author HMS Networks, MU Americas Solution Center
 * @version 0.0.1
 */
public class PocMain {

  /**
   * The file path to the test certificate which should be parsed as part of the proof of concept
   * execution.
   *
   * @since 0.0.1
   */
  public static final String TEST_CERT_PATH =
      File.separator + "usr" + File.separator + "testCert.pem";

  /**
   * Main method for the Ewon Flexy Bouncy Castle Proof of Concept Project.
   *
   * @param args project arguments
   * @since 0.0.1
   */
  public static void main(String[] args) {
    // Log startup
    System.out.println(
        "Starting "
            + PocMain.class.getPackage().getImplementationTitle()
            + " v"
            + PocMain.class.getPackage().getImplementationVersion());

    // Log certificate path specified by TEST_CERT_PATH
    System.out.println("Reading Certificate (via POC) from: " + TEST_CERT_PATH);

    // Read certificate from path specified by TEST_CERT_PATH
    try {
      Certificate certificate = PocBouncyCastle.readPemCertificateFile(TEST_CERT_PATH);
      PocBouncyCastle.logCertificateInformation(certificate);
    } catch (Exception e) {
      System.err.println(
          "Error reading certificate from " + TEST_CERT_PATH + ": " + e.getMessage());
    }

    // Log shutdown
    System.out.println(
        "Finished running "
            + PocMain.class.getPackage().getImplementationTitle()
            + " v"
            + PocMain.class.getPackage().getImplementationVersion());
  }
}
