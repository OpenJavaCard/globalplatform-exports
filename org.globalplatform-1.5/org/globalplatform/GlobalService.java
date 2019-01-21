
package org.globalplatform;

import javacard.framework.*;

/**
 * This defines the interface for requesting a Global Services Application to provide its actual service
 * interface. The Global Services Application uses this interface to check the validity of the request 
 * presented by an on-card entity.
 * Prior to using this interface, an Application is required to obtain a handle to the Global Services
 * Application by invoking the <code>GPSystem.getService()</code> method. 
 * @since export file version 1.1
 */

public interface GlobalService extends Shareable
{

    /**
    * Key Access indicating key may be used by the Security Domain and any associated application (0x00).
    */
    public static final byte KEY_ACCESS_ANY = (byte) 0x00;

    /**
    * Key Access indicating key may be used by the Security Domain but not by any associated
    * application (0x01).
    */
    public static final byte KEY_ACCESS_SECURITY_DOMAIN = (byte) 0x01;

    /**
    * Key Access indicating key may be used by any associated application but not by the 
    * Security Domain (0x02).
    */
    public static final byte KEY_ACCESS_APPLICATION = (byte) 0x02;

    /**
    * Key type indicating AES (0x88).
    */
    public static final byte KEY_TYPE_AES = (byte) 0x88;

    /**
    * Key type indicating Triple DES reserved for specific implementations (0x81).
    */
    public static final byte KEY_TYPE_3DES = (byte) 0x81;

    /**
    * Key type indicating Triple DES in CBC mode (0x82).
    */
    public static final byte KEY_TYPE_3DES_CBC = (byte) 0x82;

    /**
    * Key type indicating DES with ECB/CBC implicitly known (0x80).
    */
    public static final byte KEY_TYPE_DES = (byte) 0x80;

    /**
    * Key type indicating DES in CBC mode (0x84).
    */
    public static final byte KEY_TYPE_DES_CBC = (byte) 0x84;

    /**
    * Key type indicating DES in ECB mode (0x83).
    */
    public static final byte KEY_TYPE_DES_ECB = (byte) 0x83;

    /**
    * Key type indicating extended key format (0xFF).
    */
    public static final byte KEY_TYPE_EXTENDED = (byte) 0xFF;

    /**
    * Key type indicating HMAC SHA1, length of HMAC implicitly known (0x90).
    */
    public static final byte KEY_TYPE_HMAC_SHA1 = (byte) 0x90;

    /**
    * Key type indicating HMAC SHA1, length of HMAC is 160 bits (0x91).
    */
    public static final byte KEY_TYPE_HMAC_SHA1_160 = (byte) 0x91;

    /**
    * Key type indicating RSA Private Key Chinese Remainder p component (0xA4).
    */
    public static final byte KEY_TYPE_RSA_PRIVATE_CRT_P = (byte) 0xA4;

    /**
    * Key type indicating RSA Private Key Chinese Remainder q component (0xA5).
    */
    public static final byte KEY_TYPE_RSA_PRIVATE_CRT_Q = (byte) 0xA5;

    /**
    * Key type indicating RSA Private Key Chinese Remainder pq component (0xA6).
    */
    public static final byte KEY_TYPE_RSA_PRIVATE_CRT_PQ = (byte) 0xA6;

    /**
    * Key type indicating RSA Private Key Chinese Remainder dp1 component (0xA7).
    */
    public static final byte KEY_TYPE_RSA_PRIVATE_CRT_DP1 = (byte) 0xA7;

    /**
    * Key type indicating RSA Private Key Chinese Remainder dq1 component (0xA8).
    */
    public static final byte KEY_TYPE_RSA_PRIVATE_CRT_DQ1 = (byte) 0xA8;

    /**
    * Key type indicating RSA Private exponent (0xA3).
    */
    public static final byte KEY_TYPE_RSA_PRIVATE_EXPONENT = (byte) 0xA3;

    /**
    * Key type indicating RSA Private Key modulus (0xA2).
    */
    public static final byte KEY_TYPE_RSA_PRIVATE_MODULUS = (byte) 0xA2;

    /**
    * Key type indicating RSA Public Key exponent (0xA0).
    */
    public static final byte KEY_TYPE_RSA_PUBLIC_EXPONENT = (byte) 0xA0;

    /**
    * Key type indicating RSA Public Key modulus (0xA1).
    */
    public static final byte KEY_TYPE_RSA_PUBLIC_MODULUS = (byte) 0xA1;

    /**
    * Key usage indicating computation and decipherment (0x40).
    */
    public static final byte KEY_USAGE_COMPUTATION_DECIPHERMENT = (byte) 0x40;

    /**
    * Key usage indicating sensitive data confidentiality (0x08).
    */
    public static final byte KEY_USAGE_CONFIDENTIALITY = (byte) 0x08;

    /**
    * Key usage indicating cryptographic authorization (0x01).
    */
    public static final byte KEY_USAGE_CRYPTOGRAPHIC_AUTHORIZATION = (byte) 0x01;

    /**
    * Key usage indicating cryptographic checksum e.g. MAC (0x04).
    */
    public static final byte KEY_USAGE_CRYPTOGRAPHIC_CHECKSUM = (byte) 0x04;

    /**
    * Key usage indicating Digital Signature (0x02).
    */
    public static final byte KEY_USAGE_DIGITAL_SIGNATURE = (byte) 0x02;

    /**
    * Key usage indicating Secure Messaging in command data field (0x10).
    */
    public static final byte KEY_USAGE_SM_COMMAND = (byte) 0x10;

    /**
    * Key usage indicating Secure Messaging in response data field (0x20).
    */
    public static final byte KEY_USAGE_SM_RESPONSE = (byte) 0x20;

    /**
    * Key usage indicating verification and encipherment (0x80).
    */
    public static final byte KEY_USAGE_VERIFICATION_ENCIPHERMENT = (byte) 0x80;

    /**
    * This method returns a handle to the requested service interface of a Global Services Application.
    * <p>Note:<ul>
    * <li><em>The Global Services Application verifies the validity of the request according to its own security policies
    * for this <ode>sServiceName</code> and based on the identity of the requesting on-card entity and its characteristics as
    * registered in the <code>clientRegistryEntry</code>;</em>
    * <li><em>On a valid service request, the Global Service Application returns the reference of the Shareable Interface 
    * Object implementing the actual service: this Shareable Interface Object may either be this Global Service 
    * interface or another Java Card shareable interface;</em>
    * <li><em>Depending on its own security policy, the Global Service Application may reject an invalid service request
    * by either throwing an exception or just returning <code>null</code>.</em>
    * </ul>
    * @param clientRegistryEntry the GP Registry entry reference of the requesting on-card entity.
    * @param sServiceName the requested service name.
    * @param baBuffer the source byte array containing additional parameters of the service request.
    * @param sOffset offset of the additional request parameters within the source byte array.
    * @param sLength length of the additional request parameters.
    * @return the specific service interface reference or null.
    * @exception ISOException with the following reason codes:
    * <li><code>ISO7816.SW_CONDITIONS_NOT_SATISFIED<code>
    * <li><code>ISO7816.SW_SECURITY_STATUS_NOT_SATISFIED</code>
    */

    public Shareable getServiceInterface(GPRegistryEntry clientRegistryEntry, short sServiceName, byte[ ] baBuffer, short sOffset, short sLength) throws ISOException;
}


