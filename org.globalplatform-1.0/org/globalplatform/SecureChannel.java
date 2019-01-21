
package org.globalplatform;

import javacard.framework.*;

/**
 * This interface may be used by an applet wishing to delegate the handling of entity
 * authentication and APDU security to its associated Security Domain.
 * This interface is designed to offer interoperability to the applet in that it requires no
 * knowledge of the mechanisms used to perform the authentication or of the scheme used for
 * APDU security and shall allow an applet to interface correctly with a Security Domain
 * immaterial of the mechanisms or schemes used. Prior to using this interface, an Application
 * is required to obtain a handle to its associated Security Domain by invoking the
 * <code>GPSystem.getSecureChannel()</code> method. The Secure Channel Interface shall only be
 * exposed through the <code>GPSystem.getSecureChannel</code> method<p>
 *
 * The byte value returned by the <code>getSecurityLevel()</code> method is a bit map indicating
 * whether Entity Authentication has occurred and what level of security will be applied when
 * invoking the <code>wrap</code> and <code>unwrap</code> methods. This byte value is coded according
 * to table A-1. Note that more than one security level may be set.
 */

public interface SecureChannel extends Shareable
{

    /**
    * Entity Authentication has occurred (0x80).
    * <p>Note:<ul>
    * <li><em>Entity Authentication and the level of security that will be applied by the <code>wrap</code>
    * and <code>unwrap</code> methods are not necessarily related. A Security Domain, by default, could
    * verify the MAC on any command passed as a parameter in the <code>unwrap</code> method without
    * entity authentication previously having occurred.</em>
    */
    public static final byte AUTHENTICATED = (byte) 0x80;

    /**
    * The <code>unwrap</code> method will decrypt incoming command data (0x02).
    * <p>Note:<ul>
    * <li><em>Command data decryption could be indicated along with entity authentication and one or more
    * levels of security.</em>
    */
    public static final byte C_DECRYPTION = (byte) 0x02;

    /**
    * The <code>unwrap</code> method will verify the MAC on an incoming command (0x01).
    * <p>Note:<ul>
    * <li><em>MAC verification could be indicated along with entity authentication and one or more
    * levels of security, e.g. a value of '03' indicates that while entity authentication has not
    * occurred, the <code>unwrap</code> method will decrypt the command data of incoming commands
    * and verify the MAC on incoming commands.</em>
    */
    public static final byte C_MAC = (byte) 0x01;

    /**
    * The <code>wrap</code> method will encrypt the outgoing response data (0x20).
    * <p>Note:<ul>
    * <li><em>Response data encryption could be indicated along with entity authentication and one
    * or more levels of security.</em>
    */
    public static final byte R_ENCRYPTION = (byte) 0x20;

    /**
    * The <code>wrap</code> method will generate a MAC for the outgoing response data (0x10).
    * <p>Note:<ul>
    * <li><em>MAC generation could be indicated along with entity authentication and one or more
    * levels of security, e.g. a value of '91' indicates that entity authentication has occurred,
    * that the <code>unwrap</code> method will verify the MAC on incoming commands and that the
    * <code>wrap</code> method will generate a MAC on outgoing response data.</em>
    */
    public static final byte R_MAC = (byte) 0x10;

    /**
    * Entity Authentication has not occurred (0x00).
    * <p>Note:<ul>
    * <li><em>Entity Authentication and the level of security that will be applied by the
    * <code>wrap</code> and <code>unwrap</code> methods are not necessarily related. A
    * Security Domain, by default, could verify the MAC on any command passed as a parameter in
    * the <code>unwrap</code> method without entity authentication previously having occurred.</em>
    * <li><em>The <code>wrap</code> and <code>unwrap</code> methods will not apply any cryptographic
    * processing to command or response data.</em>
    */
    public static final byte NO_SECURITY_LEVEL = (byte) 0x00;

    /**
    * Processes security related APDU commands.
    * <p>
    * This method is used by an applet to process APDU commands that possibly relate to the security
    * mechanism used by the Security Domain. As the intention is to allow an applet to be associated
    * with a Security Domain without having any knowledge of the security mechanisms used by the
    * Security Domain, the applet assumes that APDU commands that it does not recognize are part of
    * the security mechanism and will be recognized by the Security Domain. The applet can either invoke
    * this method prior to determining if it recognizes the instruction or only invoke this method for
    * instructions it does not recognize.
    * <p>Notes:<ul>
    * <li><em>The method is responsible for receiving the data field of commands that are recognized.</em>
    * <li><em>The applet is responsible for recognizing instructions that the method refused to process.</em>
    * <li><em>The applet is responsible for outputting status words returned due to the processing of
    * instructions recognized by the method.</em>
    * <li><em>If response data is present, this data will be placed in the <code>APDU</code> buffer at
    * offset <code>ISO7816.OFFSET_CDATA</code>. The return value indicates the length and the applet is
    * responsible for outputting this data if necessary.</em>
    * </ul>
    * <p>
    * @param <code>apdu</code> the incoming <code>APDU</code> object.
    * @return the number of bytes to be output.
    * @exception ISOException with the following reason codes (other security mechanism
    * related status words may be returned):<ul>
    * <li><code>ISO7816.SW_CLA_NOT_SUPPORTED</code> class byte is not recognized by the method.</li>
    * <li><code>ISO7816.SW_INS_NOT_SUPPORTED</code> instruction byte is not recognized by the method.</li>
    */

    public short processSecurity(APDU apdu) throws ISOException;

    /**
    * This method is used to apply additional security processing to outgoing response data and Status
    * Words according to the security level.
    * <p>Notes:<ul>
    * <li><em>The applet is able to ensure that the security level it requires (<code>R_MAC, R_ENCRYPTION</code>)
    * will be applied by invoking the <code>getSecurityLevel()</code> method.</em>
    * <li><em>The <code>getSecurityLevel()</code> method invocation may also indicate that entity authentication
    * (<code>AUTHENTICATED</code>) has previously occurred.</em>
    * <li><em>If <code>NO_SECURITY_LEVEL</code> is indicated, this method will do no processing.</em>
    * </ul>
    * <p>
    * @param <code>baBuffer</code> the source of the data to be wrapped. This buffer must be global.
    * @param <code>sOffset</code> the offset within <code>baBuffer</code> of the data to wrap.
    * @param <code>sLength</code> the length of the data to wrap.
    * @return the length of the wrapped data.
    * @exception ISOException security mechanism related status words might be returned.
    * @exception ArrayIndexOutOfBoundsException if wrapping produces data outside array bounds.
    */
    public short wrap(byte[] baBuffer, short sOffset, short sLength) throws ArrayIndexOutOfBoundsException, ISOException;

    /**
    * This method is used to process and verify the secure messaging of an incoming command according to the
    * security level.
    * <p>Notes:<ul>
    * <li><em>The applet is able to query what level of security will be assumed (<code>C_MAC, C_DECRYPTION</code>)
    * to be present by the Security Domain by invoking the <code>getSecurityLevel()</code> method.</em>
    * <li><em>The <code>getSecurityLevel()</code> method invocation may also indicate that entity authentication
    * (<code>AUTHENTICATED</code>) has previously occurred.</em>
    * <li><em>If <code>NO_SECURITY_LEVEL</code> is indicated, this method will do no processing.</em>
    * <li><em>If the class byte does not indicate secure messaging (ISO 7816-4), this method will
    * do no processing.</em>
    * <li><em>The applet is responsible for receiving the data field of the command.</em>
    * <li><em>Correct processing of the <code>unwrap</code> method will result in the incoming command being reformatted
    * within the incoming <code>APDU</code> object with all data relating to the Secure Messaging removed.</em>
    * <li><em>Incorrect processing of the <code>unwrap</code> method will result in the information relating to the current
    * Secure Channel being reset.</em>
    * </ul>
    * <p>
    * @param <code>baBuffer</code> the source of the data to be unwrapped. This buffer must be global.
    * @param <code>sOffset</code> the offset within <code>baBuffer</code> of the data to unwrap.
    * @param <code>sLength</code> the length of the data to unwrap.
    * @return the length of the unwrapped data.
    * @exception ISOException with the following reason code (other security mechanism related
    * status words may be returned):<ul>
    * <li><code>ISO7816.SW_CLA_NOT_SUPPORTED</code> class byte is not recognized by the method.</li>
    */
   public short unwrap(byte [] baBuffer, short sOffset, short sLength) throws ISOException;

    /**
    * This method is used to decrypt data located in the input buffer.
    * <p>Notes:<ul>
    * <li><em>The Security Domain implicitly knows the key to be used for decryption.</em>
    * <li><em>The Security Domain is implicitly aware of any padding that may be present in the
    * decrypted data and this is discarded.</em>
    * <li><em>The clear text data replaces the ciphered data within the byte array. The removal
    * of padding may cause the length of the clear text data to be shorter than the length of the ciphered data.</em>
    * <li><em>The applet is responsible for checking the integrity of the decrypted data. </em>
    * </ul>
    * <p>
    * @param <code>baBuffer</code> the byte array containing the data to be processed. This buffer must be global.
    * @param <code>sOffset</code> offset within the byte array to start the decryption.
    * @param <code>sLength</code> the number of bytes to decrypt.
    * @return The length of the clear text data.
    * @exception ISOException if the length of data to be decrypted is not valid.
    */
    public short decryptData(byte[] baBuffer, short sOffset,  short sLength) throws ISOException;

    /**
    * This method is used to encrypt data located in the input buffer.
    * <p>Notes:<ul>
    * <li><em>The Security Domain is implicitly aware of any padding that must be applied to the
    * clear text data prior to encryption.</em>
    * <li><em>The Security Domain implicitly knows the key to be used for encryption.</em>
    * <li><em>The ciphered data replaces the clear text data within the byte array.</em>
    * <li><em>Due to padding, the length of the ciphered data may be longer than the
    * length of the clear text data and the applet must make allowances for this.</em>
    * </ul>
    * <p>
    * @param <code>baBuffer</code> the byte array containing the data to be processed. This buffer must be global.
    * @param <code>sOffset</code> offset within the byte array to start the encryption.
    * @param <code>sLength</code> the number of bytes to encrypt.
    * @return The length of the encrypted data.
    * @exception ArrayIndexOutOfBoundsException if enciphering produces data outside array bounds.
    */
    public short encryptData(byte[] baBuffer, short sOffset, short sLength) throws ArrayIndexOutOfBoundsException;

    /**
    * This method is used to reset information relating to the current Secure Channel.
    * <p>Notes:<ul>
    * <li><em>Applets using the services of a Security Domain should invoke this method in the
    * <code>Applet.deselect()</code> method.</em>
    * <li><em>The Security Domain will reset all information relating to the current Secure Channel, i.e.
    * all Secure Channel session keys, state information and security level information will be erased.</em>
    * <li><em>This method shall not fail if no Secure Channel has been initiated.</em>
    * </ul>
    */
    public void resetSecurity();

    /**
    * This method is used to determine whether the Security Domain has performed authentication and to
    * determine what level of security will be applied by the <code>wrap</code> and <code>unwrap</code>
    * methods.
    * <p>Notes:<ul>
    * <li><em>Applets must invoke this method to ensure that application specific security requirements
    * have been previously met or will be enforced by the Security Domain.</em>
    * <li><em>More than one level of security may be active and these may change during a Secure
    * Channel, e.g. an R_MAC session may be initiated during a C_MAC session.</em>
    * </ul>
    * <p>
    * @return NO_SECURITY_LEVEL (0x00) indicating that Entity Authentication has not occurred
    * and that the <code>wrap</code> and <code>unwrap</code> methods will not apply any cryptographic
    * processing to command or response data, or a bitmap of the security level as follows:<ul>
    * <li>AUTHENTICATED (0x80): Entity authentication has occurred.
    * <li>C_MAC (0x01): The <code>unwrap</code> method will verify the MAC on the incoming command.
    * <li>R_MAC (0x10): The <code>wrap</code> method will generate a MAC for the outgoing response data.
    * <li>C_DECRYPTION (0x02): The <code>unwrap</code> method will decrypt the incoming command data.
    * <li>R_ENCRYPTION (0x20): The <code>wrap</code> method will encrypt the outgoing response data.
    * </ul>
    */
    public byte getSecurityLevel();
}


