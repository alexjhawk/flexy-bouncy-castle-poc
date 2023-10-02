package com.hms_networks.americas.sc.poc;

import com.hms_networks.americas.sc.extensions.system.time.SCTimeUnit;
import java.io.FileReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.x509.Certificate;
import org.bouncycastle.crypto.digests.SHA256Digest;
import org.bouncycastle.crypto.macs.HMac;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.util.encoders.Base64;
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

  /**
   * Generates a SAS token for the specified device ID and connection string.
   *
   * <p>The expiry time for the SAS token is specified as a UNIX timestamp in seconds.
   *
   * <p>For example, to generate an SAS token that expires in 5 minutes, you may retrieve the
   * current UNIX timestamp using {@code System.currentTimeMillis()}, convert it to seconds, then
   * add 300 (5 minutes) to it. It is recommended to use the {@link SCTimeUnit} class to perform the
   * conversion.
   *
   * @param deviceId the device ID to generate the SAS token for
   * @param connectionString the connection string to use for generating the SAS token
   * @param expiryTime the expiry time for the SAS token, represented as a UNIX timestamp in
   *     seconds.
   * @return the generated SAS token
   * @since 0.0.1
   */
  public static String generateSasToken(String deviceId, String connectionString, long expiryTime)
      throws UnsupportedEncodingException {
    // Parse connection string
    PocAzureConnectionStringParser connectionStringParser =
        new PocAzureConnectionStringParser(connectionString);
    String sasResourceUri = connectionStringParser.getDerivedResourceUri(deviceId);

    // Construct SAS token string
    String toSign = sasResourceUri + "\n" + expiryTime;
    String signature = encodeHmacSha256(connectionStringParser.getSharedAccessKey(), toSign);

    // Perform URL encoding
    String encodedSasResourceUri = URLEncoder.encode(sasResourceUri, "UTF-8");

    // Construct and return SAS token
    return "SharedAccessSignature sr="
        + encodedSasResourceUri
        + "&sig="
        + signature
        + "&se="
        + expiryTime;
  }

  /**
   * Encodes a message using the HMAC SHA256 algorithm.
   *
   * @param key the key to use for encoding
   * @param message the message to encode
   * @return the encoded message
   * @since 0.0.1
   */
  public static String encodeHmacSha256(String key, String message)
      throws UnsupportedEncodingException {
    // Get bytes of the key and initialize HMAC
    byte[] keyBytes = key.getBytes("UTF-8");
    HMac hmac = new HMac(new SHA256Digest());
    hmac.init(new KeyParameter(keyBytes));

    // Get bytes of the message and perform HMAC encoding
    byte[] messageBytes = message.getBytes("UTF-8");
    byte[] hash = new byte[hmac.getMacSize()];
    hmac.update(messageBytes, 0, messageBytes.length);
    hmac.doFinal(hash, 0);

    // Return base64 encoded hash
    // TODO: MONITOR ME -- Created string should be UTF-8 but JDK restrictions prevent dev of it
    String base64EncodedHash = Base64.toBase64String(hash);
    return new String(base64EncodedHash);
  }

  /**
   * Loads the Bouncy Castle security provider into the JVM.
   *
   * @since 0.0.1
   */
  public static void loadBouncyCastleSecurityProvider() {
    System.out.println("Loading Bouncy Castle security provider...");
    org.bouncycastle.jce.provider.BouncyCastleProvider bouncyCastleProvider =
        new org.bouncycastle.jce.provider.BouncyCastleProvider();
    java.security.Security.addProvider(bouncyCastleProvider);
    System.out.println("Bouncy Castle security provider loaded.");
  }
}
