import com.hms_networks.americas.sc.poc.PocBouncyCastle;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import junit.framework.*;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x500.X500NameBuilder;
import org.bouncycastle.asn1.x509.Certificate;

/**
 * Test class for the Ewon Flexy Bouncy Castle Proof of Concept Project.
 *
 * @author HMS Networks, MU Americas Solution Center
 * @version 1.0
 */
public class PocTest extends TestCase {

  /** The resource name of the certificate resource used for testing. */
  private static final String TEST_CERT_RESOURCE_NAME = "gdig2.crt.pem";

  /**
   * The expected version of the certificate resource used for testing. This is used to verify that
   * the certificate was read successfully.
   */
  private static final ASN1Integer TEST_CERT_RESOURCE_EXPECTED_VERSION = new ASN1Integer(2);

  /**
   * The expected version number of the certificate resource used for testing. This is used to
   * verify that the certificate was read successfully.
   */
  private static final int TEST_CERT_RESOURCE_EXPECTED_VERSION_NUMBER = 3;

  /**
   * The expected serial number of the certificate resource used for testing. This is used to verify
   * that the certificate was read successfully.
   */
  private static final ASN1Integer TEST_CERT_RESOURCE_EXPECTED_SERIAL_NUMBER = new ASN1Integer(7);

  /**
   * The expected subject of the certificate resource used for testing. This is used to verify that
   * the certificate was read successfully.
   *
   * <p>Each RDN is added in a specific order to ensure that the comparison against the read
   * certificate is exact.
   */
  private static final X500Name TEST_CERT_RESOURCE_EXPECTED_SUBJECT =
      new X500NameBuilder()
          .addRDN(org.bouncycastle.asn1.x500.style.BCStyle.C, "US")
          .addRDN(org.bouncycastle.asn1.x500.style.BCStyle.ST, "Arizona")
          .addRDN(org.bouncycastle.asn1.x500.style.BCStyle.L, "Scottsdale")
          .addRDN(org.bouncycastle.asn1.x500.style.BCStyle.O, "GoDaddy.com, Inc.")
          .addRDN(
              org.bouncycastle.asn1.x500.style.BCStyle.OU, "http://certs.godaddy.com/repository/")
          .addRDN(
              org.bouncycastle.asn1.x500.style.BCStyle.CN,
              "Go Daddy Secure Certificate Authority - G2")
          .build();

  /**
   * The expected subject of the certificate resource used for testing. This is used to verify that
   * the certificate was read successfully.
   *
   * <p>Each RDN is added in a specific order to ensure that the comparison against the read
   * certificate is exact.
   */
  private static final X500Name TEST_CERT_RESOURCE_EXPECTED_ISSUER =
      new X500NameBuilder()
          .addRDN(org.bouncycastle.asn1.x500.style.BCStyle.C, "US")
          .addRDN(org.bouncycastle.asn1.x500.style.BCStyle.ST, "Arizona")
          .addRDN(org.bouncycastle.asn1.x500.style.BCStyle.L, "Scottsdale")
          .addRDN(org.bouncycastle.asn1.x500.style.BCStyle.O, "GoDaddy.com, Inc.")
          .addRDN(
              org.bouncycastle.asn1.x500.style.BCStyle.CN,
              "Go Daddy Root Certificate Authority - G2")
          .build();

  /** The {@link Reader} instance used for reading the PEM certificate contents from file. */
  protected Reader pemContentsFileReader;

  /** The {@link Reader} instance used for reading the PEM certificate contents from string. */
  protected Reader pemContentsStringReader;

  /**
   * Main test method. Invokes all test methods with a name starting with 'test' and no required
   * parameters/arguments.
   *
   * @param args test arguments (ignored)
   */
  public static void main(String[] args) {
    junit.textui.TestRunner.run(PocTest.class);
  }

  /**
   * Utility method which returns the contents of an {@link InputStream} of a resource as a string.
   *
   * @param resourceStream the {@link InputStream} of the resource
   * @return the contents of the {@link InputStream} as a string
   * @throws IOException if unable to read the {@link InputStream}
   */
  private static String getResourceStreamAsString(InputStream resourceStream) throws IOException {
    assert resourceStream != null;

    InputStreamReader resourceStreamReader = new InputStreamReader(resourceStream);
    StringBuffer resourceStreamStringContents = new StringBuffer();
    int character;
    while ((character = resourceStreamReader.read()) != -1) {
      resourceStreamStringContents.append((char) character);
    }
    return resourceStreamStringContents.toString();
  }

  /**
   * Utility method which asserts that the specified certificate was read successfully. This method
   * is used to verify that the certificate was read successfully by asserting that a number of
   * fields match the expected values.
   *
   * @param certificate the certificate to check
   */
  protected static void assertCertificateReadSuccess(Certificate certificate) {
    // Check that PEM certificate is not null
    assertNotNull(certificate);

    // Check PEM certificate version
    assertNotNull(certificate.getVersion());
    assertEquals(
        "PEM certificate version does not match expected value",
        TEST_CERT_RESOURCE_EXPECTED_VERSION,
        certificate.getVersion());

    // Check PEM certificate version number
    assertEquals(
        "PEM certificate version number does not match expected value",
        TEST_CERT_RESOURCE_EXPECTED_VERSION_NUMBER,
        certificate.getVersionNumber());

    // Check PEM certificate serial number
    assertNotNull(certificate.getSerialNumber());
    assertEquals(
        "PEM certificate serial number does not match expected value",
        TEST_CERT_RESOURCE_EXPECTED_SERIAL_NUMBER,
        certificate.getSerialNumber());

    // Check PEM certificate issuer
    assertNotNull(certificate.getIssuer());
    assertEquals(
        "PEM certificate issuer does not match expected value",
        TEST_CERT_RESOURCE_EXPECTED_ISSUER,
        certificate.getIssuer());

    // Check PEM certificate subject
    assertNotNull(certificate.getSubject());
    assertEquals(
        "PEM certificate subject does not match expected value",
        TEST_CERT_RESOURCE_EXPECTED_SUBJECT,
        certificate.getSubject());
  }

  /**
   * Set up required variables, classes or other resources before testing is run.
   *
   * @throws Exception if unable to perform setup
   */
  protected void setUp() throws Exception {
    // Set up PEM test via String
    InputStream pemContentsStringReaderCertificateResource =
        PocTest.class.getClassLoader().getResourceAsStream(TEST_CERT_RESOURCE_NAME);
    String pemContentsStringReaderCertificateString =
        getResourceStreamAsString(pemContentsStringReaderCertificateResource);
    pemContentsStringReader = new StringReader(pemContentsStringReaderCertificateString);

    // Set up PEM test via File
    InputStream pemContentsFileReaderCertificateResource =
        PocTest.class.getClassLoader().getResourceAsStream(TEST_CERT_RESOURCE_NAME);
    assert pemContentsFileReaderCertificateResource != null;
    pemContentsFileReader = new InputStreamReader(pemContentsFileReaderCertificateResource);

    super.setUp();
  }

  /**
   * Cleanup required variables, classes, or other resources after testing has run.
   *
   * @throws Exception if unable to perform cleanup
   */
  protected void tearDown() throws Exception {
    // Clean up test variables/classes/etc
    if (pemContentsFileReader != null) {
      pemContentsFileReader.close();
      pemContentsFileReader = null;
    }
    if (pemContentsStringReader != null) {
      pemContentsStringReader.close();
      pemContentsStringReader = null;
    }

    super.tearDown();
  }

  /**
   * Test method for reading PEM certificate contents from a File. This test method is automatically
   * run because its name begins with 'test' and no parameters are required.
   */
  public void testPemFileRead() {
    // Read PEM certificate
    Certificate result = null;
    try {
      result = PocBouncyCastle.readPemCertificateContents(pemContentsFileReader);
    } catch (Exception e) {
      fail("Error reading PEM certificate contents from file: " + e.getMessage());
    }

    // Check PEM certificate
    assertCertificateReadSuccess(result);
  }

  /**
   * Test method for reading PEM certificate contents from a String. This test method is
   * automatically run because its name begins with 'test' and no parameters are required.
   */
  public void testPemStringRead() {
    // Read PEM certificate
    Certificate result = null;
    try {
      result = PocBouncyCastle.readPemCertificateContents(pemContentsStringReader);
    } catch (Exception e) {
      fail("Error reading PEM certificate contents from string: " + e.getMessage());
    }

    // Check PEM certificate
    assertCertificateReadSuccess(result);
  }
}
