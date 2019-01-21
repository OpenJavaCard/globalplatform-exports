
package org.globalplatform;

import javacard.framework.*;

/**
 * This defines an interface to be used by an Application that may want to delegate the handling of entity 
 * authentication and APDU security to its associated Security Domain. 
 * This interface is designed to offer interoperability to the Application in that it requires no knowledge 
 * of the mechanisms used to perform the authentication or of the scheme used for APDU security and shall allow 
 * an Application to interface correctly with a Security Domain immaterial of the mechanisms or schemes used. 
 * Prior to using this interface, an Application is required to obtain a handle to its associated Security Domain’s 
 * SecureChannel interface object by invoking the <code>GPSystem.getSecureChannel()</code> method. 
 * The SecureChannel interface shall only be exposed through the <code>GPSystem.getSecureChannel()</code> method.
 * 
 * In all cases where the Application passes the APDU buffer as a parameter to the Security Domain, the class byte 
 * of the APDU shall not be modified. This ensures that the Security Domain knows the logical channel number. 
 * If the card supports logical channels, it is the responsibility of the Security Domain to correctly manage the 
 * logical channel information when processing the APDU.
 *
 * @since export file version 1.0
 */

public interface SecureChannel extends Shareable
{

    /**
    * Entity Authentication has occurred  as Application Provider (0x80).
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
    * Entity Any Authentication has occurred (0x40).
    * <p>Note:<ul>
    * Authenticated entity is not the Application Provider of the Application.
    * The level of security that will be applied by the <code>wrap</code>
    * and <code>unwrap</code> methods are not necessarily related. A Security Domain, by default, could
    * verify the MAC on any command passed as a parameter in the <code>unwrap</code> method without
    * entity authentication previously having occurred.</em>
    */
    public static final byte ANY_AUTHENTICATED = (byte) 0x40;

    /**
    * Processes security related APDU commands.
    * <p>
    * This method is used by an applet to process APDU commands that possibly relate to the security 
    * mechanism used by the Security Domain. As the intention is to allow an Application to be associated 
    * with a Security Domain without having any knowledge of the security mechanisms used by the Security 
    * Domain, the applet assumes that APDU commands that it does not recognize are part of the security mechanism 
    * and will be recognized by the Security Domain. The applet can either invoke this method prior to determining 
    * if it recognizes the command or only invoke this method for commands it does not recognize.
    * The method sets the compulsory Session Security Level that is established at Secure Channel initiation and 
    * which is required for the whole Secure Channel Session. On successful initialization of the Secure Channel Session, 
    * the Current Security Level is set to the same value as the compulsory Session Security Level. 
    * The Current Security Level is updated (R-MAC or not) on the successful processing of the 
    * BEGIN R-MAC SESSION / END R-MAC SESSION commands.
    * 
    * <p>Notes:<ul>
    * <li><em>Processing this method shall comply to the rules of the Secure Channel Protocol implemented by the Security Domain;</em>
    * <li><em>The method is responsible for receiving the data field of commands that are recognized;</em>
    * <li><em>The applet is responsible for recognizing commands that the method refused to process ('6E00' and '6D00');.</em>
    * <li><em>The applet is responsible for outputting status  bytes returned due to the processing of instructions recognized by the method;</em>
    * <li><em>If response data is present, this data will be placed in the <code>APDU</code> buffer at offset <code>ISO7816.OFFSET_CDATA</code>.
    * The return code indicates the length and the applet is responsible for outputting this data.</em>
    * </ul>
    * <p>
    * @param apdu the incoming <code>APDU</code> object.
    * @return the number of bytes to be output.
    * @exception ISOException with the following reason codes (other security mechanism
    * related status words may be returned):<ul>
    * <li><code>ISO7816.SW_CLA_NOT_SUPPORTED</code> class byte is not recognized by the method.</li>
    * <li><code>ISO7816.SW_INS_NOT_SUPPORTED</code> instruction byte is not recognized by the method.</li>
    */

    public short processSecurity(APDU apdu) throws ISOException;

    /**
    * This method applies secure messaging to the current outgoing response according to the Current Security Level and 
    * Session Security Level of the Secure Channel Session.
    * <p>Notes:<ul>
    * <li><em>Processing this method shall comply to the rules of the Secure Channel Protocol implemented by the Security Domain;</em>
    * <li><em>This method attempts secure messaging processing of the current outgoing response when the Current Security Level indicates
    * <code>R_MAC</code> and/or <code>R_ENCRYPTION</code>;</em>
    * <li><em>If the Current Security Level does not indicate <code>R_MAC</code> and/or <code>R_ENCRYPTION</code>, when complying with the 
    * Secure Channel Protocol rules, this method will do no processing and the outgoing response message will remain as is in the APDU object. 
    * The returned length of the “wrapped” data is set to the value of the sLength parameter minus 2 (indicating the status  bytes are no 
    * longer present at the end of the returned data);</em>
    * <li><em>The applet is responsible for appending the expected status bytes at the end of the response data in order for them to be 
    * protected by secure messaging;</em>
    * <li><em>The returned data does not include the status  bytes;</em>
    * <li><em>The method fails if the secure messaging of the incoming command is not successfully verified or does not match the 
    * Current Security Level (as defined either as the compulsory Session Security Level set at initialization of the Secure Channel Session or
    * as the Current Security Level set by the <code>setSecurityLevel()</code> method). 
    * If the method fails, the Current Security Level is reset to <code>NO_SECURITY_LEVEL</code>, but not the compulsory Session Security Level;</em>
    * <li><em>This method does not fail if a Secure Channel Session is not open (that is, when Session Security Level = <code>NO_SECURITY_LEVEL</code>) or 
    * if the corresponding session keys are not available.</em>
    * </ul>
    * <p>
    * @param baBuffer the source of the data to be wrapped. 
    * @param sOffset the offset within <code>baBuffer</code> of the data to wrap.
    * @param sLength the length of the data to wrap.
    * <p>Notes:<ul>
    * <li><em>The length Li of the Response Data Field is automatically calculated by the Security Domain and 
    * prepended to the Response Data Field for computation of the R-MAC</em>.
    * </ul>

    * @return the length of the wrapped data.
    * @exception ISOException <li>if security mechanism related status words might be returned.</li>
    * @exception ArrayIndexOutOfBoundsException <li>if wrapping produces data outside array bounds.</li>
    * @exception <code>java.lang.SecurityException</code><li> if <code>baBuffer</code> is not accessible in the caller’s context 
    * e.g. <code>baBuffer</code> is not a global array nor an array belonging to the caller context.</li>
    */
    public short wrap(byte[] baBuffer, short sOffset, short sLength) throws ArrayIndexOutOfBoundsException, ISOException;

    /**
    * This method is used to process and verify the secure messaging of an incoming command according to the
    * security level and Session Security Level of the current Secure Channel Session.
    * <p>Notes:<ul>
    * <li><em>Processing this method shall comply to the rules of the Secure Channel Protocol implemented by the Security Domain;</em>
    * <li><em>If <code>NO_SECURITY_LEVEL</code>, <code>AUTHENTICATED</code> or <code>ANY_AUTHENTICATED</code> only is indicated, 
    * when complying to the Secure Channel Protocol rules, this method will not attempt any secure messaging processing on the incoming command, 
    * the incoming command will remain as is within the incoming APDU object and the returned length of the “unwrapped” data is set to the value 
    * of the sLength parameter, otherwise a security error is returned;</em>
    * <li><em>If the class byte does not indicate secure messaging (according to ISO/IEC 7816-4), this method will not attempt any secure messaging 
    * processing on the incoming command and the incoming command will remain as is within the incoming APDU object. 
    * When complying with the Secure Channel Protocol rules, the returned length of the “unwrapped” data is set to the value of the sLength parameter
    * otherwise a security error is returned;</em>
    * <li><em>The applet is responsible for receiving the data field of the command;</em>
    * <li><em>Correct secure messaging processing of the <code>unwrap()</code> will result in the incoming command being reformatted within the 
    * incoming APDU object with all data relating to the secure messaging removed. 
    * A reformatted case 1 or case 2 command will include an Lc byte set to zero;</em>
    * <li><em>If R_MAC is indicated in the Current Security Level of the Secure Channel Session, once secure messaging processing of the incoming 
    * command has successfully completed, R-MAC computation on the reformatted command (i.e. after all the data relating to secure messaging 
    * has been removed) will be initiated. If no secure messaging processing was required for the incoming command, R-MAC computation will 
    * be initiated on the unmodified incoming command, appended with a Lc byte of zero in event of a case 1 or case 2 command;</em>
    * <li><em>Incorrect processing of the <code>unwrap</code> method will result in the information relating to the current Secure Channel being reset.
    * <li><em>This method does not fail if a Secure Channel Session is not open (that is, when Session Security Level = <code>NO_SECURITY_LEVEL</code>) or
    * if the corresponding session keys are not available;</em>
    * <li><em>The method fails if the secure messaging of the incoming command is not successfully verified or 
    * does not match the Current Security Level (as defined either as the compulsory Session Security Level set at 
    * initialization of the Secure Channel Session or as the Current Security Level set by the <code>setSecurityLevel()</code> method). 
    * If the method fails, the Current Security Level is reset to <code>NO_SECURITY_LEVEL</code>, but not the compulsory Session Security Level.</em>
    * </ul>
    * <p>
    * @param baBuffer the source of the data to be unwrapped.
    * @param sOffset the offset within <code>baBuffer</code> of the APDU data to unwrap, i.e. the offset of the command header.
    * @param sLength the length of the APDU data to unwrap, i.e the length of the command header and data field.
    * 
    * @return the length of the unwrapped data, i.e the length of the command header and data field.
    * 
    * @exception ISOException with the following reason code (other security mechanism related
    * status words may be returned):<ul>
    * <li><code>ISO7816.SW_SECURITY_STATUS_NOT_SATISFIED</code> if secure messaging verification failed.</li>
    * <li><code>ISO7816.SW_CLA_NOT_SUPPORTED</code> class byte is not recognized by the method.</li></ul>
    * @exception <code>java.lang.SecurityException</code> <li>if <code>baBuffer</code> is not accessible in the caller’s context 
    * e.g. <code>baBuffer</code> is not a global array nor an array belonging to the caller context.</li>
    */
   public short unwrap(byte [] baBuffer, short sOffset, short sLength) throws ISOException;

    /**
    * This method is used to decrypt data located in the input buffer.
    * <p>Notes:<ul>
    * <li><em>Processing this method shall comply to the rules of the Secure Channel Protocol 
    * implemented by the Security Domain;</em>
    * <li><em>The Security Domain implicitly knows the key to be used for decryption.</em>
    * <li><em>The Security Domain is implicitly aware of any padding that may be present in the
    * decrypted data and this is discarded.</em>
    * <li><em>The clear text data replaces the ciphered data within the byte array. The removal
    * of padding may cause the length of the clear text data to be shorter than the length of the ciphered data.</em>
    * <li><em>The applet is responsible for checking the integrity of the decrypted data. </em>
    * <li><em>A Secure Channel Session shall be opened and a sensitive data decryption key shall be available;</em>
    * <li><em>This method fails if a Secure Channel Session is not open 
    * (i.e. Session Security Level = NO_SECURITY_LEVEL) or if the corresponding session keys are not available.</em>
    * </ul>
    * <p>
    * @param baBuffer the source byte array.
    * @param sOffset offset within the source byte array to start the decryption.
    * @param sLength the number of bytes to decrypt.
    * @return The length of the clear text data.
    * @exception ISOException with the following reason codes:
    * <li><code>ISO7816.SW_SECURITY_STATUS_NOT_SATISFIED</code> if a Secure Channel Session has not been opened.</li>
    * <li><code>ISO7816.SW_WRONG_LENGTH</code> if the length of data to be decrypted is not valid.</li>
    * @exception <code>java.lang.SecurityException</code> <li>if <code>baBuffer</code> is not accessible in the caller’s context 
    * e.g. <code>baBuffer</code> is not a global array nor an array belonging to the caller context.</li>
    */
    public short decryptData(byte[] baBuffer, short sOffset,  short sLength) throws ISOException;

    /**
    * This method is used to encrypt data located in the input buffer.
    * <p>Notes:<ul>
    * <li><em>Processing this method shall comply to the rules of the Secure Channel Protocol 
    * implemented by the Security Domain;</em>
    * <li><em>The Security Domain is implicitly aware of any padding that must be applied to the
    * clear text data prior to encryption according to the Secure Channel Protocol;</em>
    * <li><em>The Security Domain implicitly knows the key to be used for encryption.</em>
    * <li><em>The ciphered data replaces the clear text data within the byte array.</em>
    * <li><em>A Secure Channel Session shall be opened and a sensitive data encryption key shall be available;</em>
    * <li><em>This method fails if a Secure Channel Session is not open 
    * (i.e. Session Security Level = NO_SECURITY_LEVEL) or if the corresponding session keys are not available.</em>
    * </ul>
    * <p>
    * @param baBuffer the byte array containing the data to be processed.
    * @param sOffset offset within the byte array to start the encryption.
    * @param sLength the number of bytes to encrypt.
    * @return The length of the encrypted data.
    * @exception ArrayIndexOutOfBoundsException 
    * <li>if enciphering produces data outside array bounds.</li> 
    * @exception ISOException with the following reason code:
    * <li><code>ISO7816.SW_SECURITY_STATUS_NOT_SATISFIED</code> if a Secure Channel Session is not open.</li>
    * @exception <code>java.lang.SecurityException</code><li> if <code>baBuffer</code> is not accessible in the caller’s context 
    * e.g. <code>baBuffer</code> is not a global array nor an array belonging to the caller context.</li>
    */
    public short encryptData(byte[] baBuffer, short sOffset, short sLength) throws ArrayIndexOutOfBoundsException;

    /**
    * This method is used to reset all information relating to the current Secure Channel Session.
    * It resets both the compulsory Session Security Level and the Current Security Level to NO_SECURITY_LEVEL,
    * terminates the current Secure Channel Session and erases all session keys.
    * <p>Notes:<ul>
    * <li><em>It is strongly recommended that applets using the services of a Security Domain invoke 
    * this method in the <code>Applet.deselect()</code> method;</em>
    * <li><em>The Security Domain will reset all information relating to the current Secure Channel, i.e.
    * all Secure Channel session keys, state information and security level information will be erased.</em>
    * <li><em>This method shall not fail if no Secure Channel has been initiated.</em>
    * </ul>
    */
    public void resetSecurity();

    /**
    * This method returns, from the requester's standpoint, the Current Security Level coded 
    * as a bit-map according to Table 10 1 indicating whether entity authentication has occurred and
    *  what level of security is currently applicable to command and response messages processed 
    * by the <code>unwrap()</code> and <code>wrap()</code> methods.
    * <p>Notes:<ul>
    * <li><em>Applets must invoke this method to ensure that application specific security requirements
    * have been previously met or will be enforced by the Security Domain.</em>
    * <li><em>More than one level of security may be active and these may change during a Secure
    * Channel, e.g. an R_MAC session may be initiated during a C_MAC session.</em>
    * <li><em>The Current Security Level may be different at the same time for an Application invoking 
    * the method and its associated Security Domain depending on the Secure Channel Protocol and 
    * the authenticated off-card entity's Application Provider ID (e.g. it may be ANY_AUTHENTICATED 
    * for the application and AUTHENTICATED for its associated Security Domain).</em>
    * </ul>
    * <p>
    * @return <li><em>The current Security Level</em>
    */
    public byte getSecurityLevel();
}


