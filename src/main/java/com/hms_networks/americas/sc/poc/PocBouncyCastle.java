package com.hms_networks.americas.sc.poc;

import java.io.FileReader;
import java.io.Reader;
import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.x509.Certificate;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemReader;

/**
 * The class containing proof-of-concept (poc) code for the usage of the Bouncy Castle library on
 * Ewon Flexy.
 *
 * @since 0.0.1
 * @author HMS Networks, MU Americas Solution Center
 */
public class PocBouncyCastle {

  /**
   * The proof-of-concept method for reading a PEM certificate file and parsing it using the Bouncy
   * Castle library.
   *
   * @param pemFilePath the file path to the PEM certificate file
   * @since 0.0.1
   * @throws Exception if unable to read PEM certificate contents
   */
  public static Certificate readPemCertificateFile(String pemFilePath) throws Exception {
    return readPemCertificateContents(new FileReader(pemFilePath));
  }

  /**
   * The proof-of-concept method for reading PEM certificate contents and parsing it using the
   * Bouncy Castle library.
   *
   * @param pemFileReader the reader for the PEM certificate contents
   * @since 0.0.1
   * @throws Exception if unable to read PEM certificate contents
   */
  public static Certificate readPemCertificateContents(Reader pemFileReader) throws Exception {
    PemReader pemReader = new PemReader(pemFileReader);

    try {
      PemObject pemObject = pemReader.readPemObject();

      if (pemObject == null) {
        throw new Exception("No PEM object found in file");
      } else if (!pemObject.getType().equals("CERTIFICATE")) {
        throw new Exception("PEM object is not a certificate");
      }

      ASN1InputStream asn1InputStream = new ASN1InputStream(pemObject.getContent());
      return Certificate.getInstance(asn1InputStream.readObject());
    } finally {
      pemReader.close();
    }
  }

  /**
   * Logs the certificate information to standard out in a human-readable format.
   *
   * @param certificate the certificate to log information for
   * @since 0.0.1
   */
  public static void logCertificateInformation(Certificate certificate) {
    System.out.println("\n\nCertificate Information:\n========================");
    System.out.println("Certificate Not After (Start): " + certificate.getStartDate());
    System.out.println("Certificate Not Before (End): " + certificate.getEndDate());
    System.out.println("Certificate Serial Number: " + certificate.getSerialNumber().toString());
    System.out.println("Certificate Signature: " + certificate.getSignature().toString());
    System.out.println("Certificate Issuer: " + certificate.getIssuer().toString());
    System.out.println("Certificate Subject: " + certificate.getSubject().toString());
    System.out.println("Certificate Version: " + certificate.getVersion());
    System.out.println("Certificate Version Number: " + certificate.getVersionNumber());
    System.out.println(
        "Certificate Subject Public Key Info: " + certificate.getSubjectPublicKeyInfo().toString());
    System.out.println("Certificate Signature Algorithm: " + certificate.getSignatureAlgorithm());
    System.out.println("Certificate Signature TBS Certificate: " + certificate.getTBSCertificate());
    System.out.println("========================\n");
  }
}
