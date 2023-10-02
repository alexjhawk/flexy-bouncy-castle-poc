package com.hms_networks.americas.sc.poc;

import com.hms_networks.americas.sc.extensions.string.StringUtils;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The class containing proof-of-concept (poc) code for the parsing of Azure connection strings on
 * Ewon Flexy.
 *
 * @since 0.0.1
 * @author HMS Networks, MU Americas Solution Center
 */
public class PocAzureConnectionStringParser {

  /**
   * The delimiter used to separate key-value pairs in Azure connection strings.
   *
   * @since 0.0.1
   */
  public static final String SAS_FIELD_DELIMITER = ";";

  /**
   * The delimiter used to separate keys and values in Azure connection strings.
   *
   * @since 0.0.1
   */
  public static final String SAS_KEY_VALUE_DELIMITER = "=";

  /**
   * The key for the host name field in Azure connection strings.
   *
   * @since 0.0.1
   */
  public static final String HOST_NAME = "HostName";

  /**
   * The key for the device ID field in Azure connection strings.
   *
   * @since 0.0.1
   */
  public static final String DEVICE_ID = "DeviceId";

  /**
   * The key for the shared access key field in Azure connection strings.
   *
   * @since 0.0.1
   */
  public static final String SHARED_ACCESS_KEY = "SharedAccessKey";

  /**
   * The key for the shared access key name field in Azure connection strings.
   *
   * @since 0.0.1
   */
  public static final String SHARED_ACCESS_KEY_NAME = "SharedAccessKeyName";

  /**
   * The key for the gateway host name field in Azure connection strings.
   *
   * @since 0.0.1
   */
  public static final String GATEWAY_HOST_NAME = "GatewayHostName";

  /**
   * The key for the ID scope field in Azure connection strings.
   *
   * @since 0.0.1
   */
  public static final String ID_SCOPE = "IdScope";

  /**
   * The key for the registration ID field in Azure connection strings.
   *
   * @since 0.0.1
   */
  public static final String REGISTRATION_ID = "RegistrationId";

  /**
   * The key for the X.509 certificate field in Azure connection strings.
   *
   * @since 0.0.1
   */
  public static final String X_509 = "X509";

  /**
   * The path for the device ID field in Azure resource URIs derived from connection strings.
   *
   * @since 0.0.1
   */
  public static final String RESOURCE_URI_DEVICE_ID_PATH = "/devices/";

  /**
   * The map of key-value pairs parsed from the Azure connection string.
   *
   * @since 0.0.1
   */
  Map connectionStringKeyValuePairs = new HashMap();

  /**
   * The proof-of-concept method for parsing an Azure connection string.
   *
   * <p>The Azure connection string is expected to be in a valid format. Modified, or otherwise
   * invalid, connection strings may result in an exception.
   *
   * @param connectionString the Azure connection string
   * @throws IllegalArgumentException if the provided connection string is invalid, malformed, or
   *     otherwise results in an exception
   * @since 0.0.1
   */
  public PocAzureConnectionStringParser(String connectionString) {
    try {
      // Loop through each key-value pair in the connection string
      List connectionStringParts = StringUtils.split(connectionString, SAS_FIELD_DELIMITER);
      for (int i = 0; i < connectionStringParts.size(); i++) {
        String connectionStringPart = (String) connectionStringParts.get(i);

        // Split the key-value pair into key and value
        List connectionStringPartParts =
            StringUtils.split(connectionStringPart, SAS_KEY_VALUE_DELIMITER);
        String connectionStringPartKey = (String) connectionStringPartParts.get(0);
        String connectionStringPartValue = (String) connectionStringPartParts.get(1);

        // Store the key-value pair
        connectionStringKeyValuePairs.put(connectionStringPartKey, connectionStringPartValue);
      }
    } catch (Exception e) {
      throw new IllegalArgumentException(
          "The provided connection string resulted in an exception.");
    }
  }

  /**
   * Gets the value for the specified key in the Azure connection string.
   *
   * @param key the key to get the value for
   * @return the value for the specified key
   * @throws IllegalArgumentException if the provided key is invalid or not found
   * @since 0.0.1
   */
  public String get(String key) {
    if (connectionStringKeyValuePairs.containsKey(key)) {
      return (String) connectionStringKeyValuePairs.get(key);
    } else {
      throw new IllegalArgumentException("The provided key is invalid or not found.");
    }
  }

  /**
   * Gets the value for the specified key in the Azure connection string, or the specified default
   * value if the key is invalid or not found.
   *
   * @param key the key to get the value for
   * @param defaultValue the default value to return if the key is invalid or not found
   * @return the value for the specified key, or the specified default value if the key is invalid
   *     or not found
   * @since 0.0.1
   */
  public String get(String key, String defaultValue) {
    if (connectionStringKeyValuePairs.containsKey(key)) {
      return (String) connectionStringKeyValuePairs.get(key);
    } else {
      return defaultValue;
    }
  }

  /**
   * Gets the host name for the Azure connection string.
   *
   * <p>The {@code HostName} field contains the DNS name of the IoT Hub.
   *
   * @return the host name for the Azure connection string
   * @since 0.0.1
   */
  public String getHostName() {
    return get(HOST_NAME);
  }

  /**
   * Gets the host name for the Azure connection string, or the specified default value if the key
   * is invalid or not found.
   *
   * <p>The {@code HostName} field contains the DNS name of the IoT Hub.
   *
   * @param defaultValue the default value to return if the key is invalid or not found
   * @return the host name for the Azure connection string, or the specified default value if the
   *     key is invalid or not found
   * @since 0.0.1
   */
  public String getHostName(String defaultValue) {
    return get(HOST_NAME, defaultValue);
  }

  /**
   * Gets the device ID for the Azure connection string.
   *
   * <p>The {@code DeviceId} field contains the ID of the IoT device.
   *
   * <p>This field is optional and is not included under most circumstances. If the field is not
   * included, it is recommended to use a device-derived unique identifier for the device ID.
   *
   * @return the device ID for the Azure connection string
   * @since 0.0.1
   */
  public String getDeviceId() {
    return get(DEVICE_ID);
  }

  /**
   * Gets the device ID for the Azure connection string, or the specified default value if the key
   * is invalid or not found.
   *
   * <p>The {@code DeviceId} field contains the ID of the IoT device.
   *
   * <p>This field is optional and is not included under most circumstances. If the field is not
   * included, it is recommended to use a device-derived unique identifier for the device ID.
   *
   * @param defaultValue the default value to return if the key is invalid or not found
   * @return the device ID for the Azure connection string, or the specified default value if the
   *     key is invalid or not found
   * @since 0.0.1
   */
  public String getDeviceId(String defaultValue) {
    return get(DEVICE_ID, defaultValue);
  }

  /**
   * Gets the shared access key for the Azure connection string.
   *
   * <p>The {@code SharedAccessKey} field contains the primary or secondary key for the shared
   * access policy.
   *
   * @return the shared access key for the Azure connection string
   * @since 0.0.1
   */
  public String getSharedAccessKey() {
    return get(SHARED_ACCESS_KEY);
  }

  /**
   * Gets the shared access key for the Azure connection string, or the specified default value if
   * the key is invalid or not found.
   *
   * <p>The {@code SharedAccessKey} field contains the primary or secondary key for the shared
   * access policy.
   *
   * @param defaultValue the default value to return if the key is invalid or not found
   * @return the shared access key for the Azure connection string, or the specified default value
   *     if the key is invalid or not found
   * @since 0.0.1
   */
  public String getSharedAccessKey(String defaultValue) {
    return get(SHARED_ACCESS_KEY, defaultValue);
  }

  /**
   * Gets the shared access key name for the Azure connection string.
   *
   * <p>The {@code SharedAccessKeyName} field contains the name of the shared access policy, such as
   * {@code iothubowner}.
   *
   * @return the shared access key name for the Azure connection string
   * @since 0.0.1
   */
  public String getSharedAccessKeyName() {
    return get(SHARED_ACCESS_KEY_NAME);
  }

  /**
   * Gets the shared access key name for the Azure connection string, or the specified default value
   * if the key is invalid or not found.
   *
   * <p>The {@code SharedAccessKeyName} field contains the name of the shared access policy, such as
   * {@code iothubowner}.
   *
   * @param defaultValue the default value to return if the key is invalid or not found
   * @return the shared access key name for the Azure connection string, or the specified default
   *     value if the key is invalid or not found
   * @since 0.0.1
   */
  public String getSharedAccessKeyName(String defaultValue) {
    return get(SHARED_ACCESS_KEY_NAME, defaultValue);
  }

  /**
   * Gets the gateway host name for the Azure connection string.
   *
   * <p>The {@code GatewayHostName} field contains the name of the gateway host, if using IoT Hub or
   * Central behind a gateway.
   *
   * @return the gateway host name for the Azure connection string
   * @since 0.0.1
   */
  public String getGatewayHostName() {
    return get(GATEWAY_HOST_NAME);
  }

  /**
   * Gets the gateway host name for the Azure connection string, or the specified default value if
   * the key is invalid or not found.
   *
   * <p>The {@code GatewayHostName} field contains the name of the gateway host, if using IoT Hub or
   * Central behind a gateway.
   *
   * @param defaultValue the default value to return if the key is invalid or not found
   * @return the gateway host name for the Azure connection string, or the specified default value
   *     if the key is invalid or not found
   * @since 0.0.1
   */
  public String getGatewayHostName(String defaultValue) {
    return get(GATEWAY_HOST_NAME, defaultValue);
  }

  /**
   * Gets the ID scope for the Azure connection string.
   *
   * <p>The {@code IdScope} field contains the unique identifier of the applicable device
   * provisioning service instance.
   *
   * @return the ID scope for the Azure connection string
   * @since 0.0.1
   */
  public String getIdScope() {
    return get(ID_SCOPE);
  }

  /**
   * Gets the ID scope for the Azure connection string, or the specified default value if the key is
   * invalid or not found.
   *
   * <p>The {@code IdScope} field contains the unique identifier of the applicable device
   * provisioning service instance.
   *
   * @param defaultValue the default value to return if the key is invalid or not found
   * @return the ID scope for the Azure connection string, or the specified default value if the key
   *     is invalid or not found
   * @since 0.0.1
   */
  public String getIdScope(String defaultValue) {
    return get(ID_SCOPE, defaultValue);
  }

  /**
   * Gets the registration ID for the Azure connection string.
   *
   * <p>The {@code RegistrationId} field contains the registration ID of the specific device within
   * the ID scope.
   *
   * @return the registration ID for the Azure connection string
   * @since 0.0.1
   */
  public String getRegistrationId() {
    return get(REGISTRATION_ID);
  }

  /**
   * Gets the registration ID for the Azure connection string, or the specified default value if the
   * key is invalid or not found.
   *
   * <p>The {@code RegistrationId} field contains the registration ID of the specific device within
   * the ID scope.
   *
   * @param defaultValue the default value to return if the key is invalid or not found
   * @return the registration ID for the Azure connection string, or the specified default value if
   *     the key is invalid or not found
   * @since 0.0.1
   */
  public String getRegistrationId(String defaultValue) {
    return get(REGISTRATION_ID, defaultValue);
  }

  /**
   * Gets the X.509 certificate for the Azure connection string.
   *
   * <p>The {@code X509} field indicates whether the connection should use X.509 certificate
   * credentials.
   *
   * @return the X.509 certificate for the Azure connection string
   * @since 0.0.1
   */
  public String getX509() {
    return get(X_509);
  }

  /**
   * Gets the X.509 certificate for the Azure connection string, or the specified default value if
   * the key is invalid or not found.
   *
   * <p>The {@code X509} field indicates whether the connection should use X.509 certificate
   * credentials.
   *
   * @param defaultValue the default value to return if the key is invalid or not found
   * @return the X.509 certificate for the Azure connection string, or the specified default value
   *     if the key is invalid or not found
   * @since 0.0.1
   */
  public String getX509(String defaultValue) {
    return get(X_509, defaultValue);
  }

  /**
   * Gets the resource URI derived for the Azure connection string.
   *
   * <p>The resource URI is built in the following format: {@code HostName}/devices/{@code
   * DeviceId}.
   *
   * <p>The {@code DeviceId} field is optional and is not included under most circumstances. While
   * it is recommended to use a device-derived unique identifier for the default device ID, this
   * method expects the device ID to be specified in the connection string, and will throw an
   * exception if it is not found.
   *
   * @throws IllegalArgumentException if the device ID is not specified in the connection string
   * @return the resource URI derived for the Azure connection string
   * @since 0.0.1
   */
  public String getDerivedResourceUri() {
    // Check if device ID is specified
    if (!connectionStringKeyValuePairs.containsKey(DEVICE_ID)) {
      throw new IllegalArgumentException(
          "The provided connection string does not contain a device ID.");
    }

    return getHostName() + RESOURCE_URI_DEVICE_ID_PATH + getDeviceId();
  }

  /**
   * Gets the resource URI derived for the Azure connection string, using the specified default
   * device ID value if the device ID is not specified in the connection string.
   *
   * <p>The resource URI is built in the following format: {@code HostName}/devices/{@code
   * DeviceId}.
   *
   * <p>The {@code DeviceId} field is optional and is not included under most circumstances. If the
   * field is not included, it is recommended to use a device-derived unique identifier for the
   * specified default device ID.
   *
   * @param defaultDeviceId the default device ID to use if the device ID is not specified in the
   *     connection string
   * @return the resource URI derived for the Azure connection string, using the specified default
   *     device ID value if the device ID is not specified in the connection string
   * @since 0.0.1
   */
  public String getDerivedResourceUri(String defaultDeviceId) {
    // Get device ID if specified, otherwise use default
    String deviceId = defaultDeviceId;
    if (connectionStringKeyValuePairs.containsKey(DEVICE_ID)) {
      deviceId = getDeviceId();
    }

    return getHostName() + RESOURCE_URI_DEVICE_ID_PATH + deviceId;
  }
}
