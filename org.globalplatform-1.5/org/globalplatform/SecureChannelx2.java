package org.globalplatform;

import javacard.framework.ISOException;


/**
 * This defines an interface which extends the SecureChannel Interface and overrides the method processSecurity().
 * See <code>SecureChannel</code> interface for a description of the underlying interface.
 * Prior to using this interface, an Application is required to obtain a handle to its associated 
 * Security Domain's SecureChannelx2 interface object by invoking the GPSystem.getSecureChannel() method 
 * and casting the returned object to type SecureChannelx2.
 * The SecureChannelx2 Interface shall be implemented by a Security Domain compliant to this version of the specification
 * and the corresponding object reference shall be exposed through the GPSystem.getSecureChannel() method
* @see <code>SecureChannel</code>
* @since export file version 1.4
 */
public interface SecureChannelx2 extends SecureChannel {
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
	    * <li><em>The applet is responsible for setting command header and the data field of commands to process in the input buffer;</em>
	    * <li><em>The applet is responsible for recognizing commands that the method refused to process ('6E00' and '6D00');.</em>
	    * <li><em>The applet is responsible for outputting status  bytes returned due to the processing of instructions recognized by the method;</em>
	    * <li><em>If response data is present, this data will be placed in the <code>baBuffer</code> buffer at offset specified by <code>sOutOffset</code>.
	    * The return code indicates the length and the applet is responsible for outputting this data.</em>
	    * </ul>
	    * <p>
		* @param baBuffer the source and response byte array.  
		* @param sInOffset offset within the source byte array to start the security processing.
		* @param sInLength the number of bytes to process.
		* @param sOutOffset offset within the response byte array to start the response.
		*
		* @return the number of bytes to be output
		* 
	    * @throws ISOException with the following reason codes (other security mechanism
	    * related status words may be returned):<ul>
	    * <li><code>ISO7816.SW_CLA_NOT_SUPPORTED</code> class byte is not recognized by the method.</li>
	    * <li><code>ISO7816.SW_INS_NOT_SUPPORTED</code> instruction byte is not recognized by the method.</li></ul>
	    * @exception <code>java.lang.SecurityException</code> <li> if <code>baBuffer</code> is not accessible in the caller’s context 
	    * e.g. <code>baBuffer</code> is not a global array nor an array belonging to the caller context.</li>
	    */

	    public short processSecurity(byte []baBuffer,short sInOffset, short sInLength,short sOutOffset) throws ISOException;

}
