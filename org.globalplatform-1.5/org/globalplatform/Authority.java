package org.globalplatform;

import javacard.framework.Shareable;
import javacard.security.CryptoException;

/**
 * This interface provides services to recover a cryptographic key and to sign data. The CASD shall publish 
 * this interface to the OPEN, using Global Service interface, to make its services available 
 * to other Applications. The APSD is the first entity requiring this service from the CASD as shown in 2.1.
 * There is only one CASD inside the card. The CASD shall register this service as a unique Global Service with
 * the service family identifier =’83’ (per section 8.1.3 of GlobalPlatform Card Specification v2.2).
 * 
 * <p><ul>The <code>Authority</code> interface is responsible for signing and verifying message with a key 
 * implicitly known. The implementer of this interface has the knowledge of the keys used for the different operations.</ul> 
 * @since export file version 1.2
 */

public interface Authority extends Shareable {
	/**
     * Used in <code>init()</code> methods to indicate signature sign mode.
     */
	public final byte  MODE_SIGN=1;
	/**
     * Used in <code>init()</code> methods to indicate key recovery mode.
     */
	public final byte  MODE_KEY_RECOVERY=2;

	/**
     * Initializes the Authority interface with the appropriate mode (<code>MODE_SIGN</code> or <code>MODE_KEY_RECOVERY</code>).
     * 
     * @param theMode one of <code>MODE_SIGN</code> or <code>MODE_KEY_RECOVERY</code>
     * @exception CryptoException  with the following reason code:
     * <li><code>ILLEGUAL_VALUE</code> if theMode option is an undefined value</li> 
 
     */
    public void init(byte theMode) throws CryptoException;
    
    /**
     * Generates the signature of all/last input data. 
     * A call to this method resets this Authority interface to the state it was 
     * in when previously initialized via a call to init().
     * That is, the object is reset and available to sign another message.
     * The input and output buffer may overlap and shall be global arrays. 
     * 
     * @param inBuff the input buffer of data to be signed
     * @param inOffset the offset in input buffer at which the signature starts
     * @param inLength the byte length to sign 
     * @param sigBuff the output buffer to store signature data
     * @param sigOffset the offset into sigBuff at which to begin signature generation
     * 
     * @return the number of bytes of signature output in sigBuff
     * 
     * @throws CryptoException with the following reason codes:
     * <li><code>INVALID_INIT</code> if this Authority interface is not initialized or 
     * initialized in <code>MODE_KEY_RECOVERY</code> mode.</li> 
     * <li><code>ILLEGAL_USE</code> if this Authority algorithm does not pad the message and
     * the message is not block aligned.</li>
     * @throws SecurityException if the inBuff or sigBuff are not global array.
     */
    public short sign(byte[] inBuff,
            short inOffset,
            short inLength,
            byte[] sigBuff,
            short sigOffset)
     throws CryptoException;
    
    /**
     * Accumulates input data. for the current operation (<code>MODE_SIGN</code> or <code>MODE_KEY_RECOVERY</code>).
     * <p>
     * When this method is used, temporary storage of intermediate results is required. 
     * This method should only be used if all the input data required for the current operation
     * is not available in one byte array.
     * The <code>sign</code> or <code>recoverKey</code> methods are recommended whenever possible. 
     * The inBuff shall be global array. 
     * 
     * @param inBuff buffer containing input data
     * @param inOffset offset of input data
     * @param inLength length of input data 
     * @throws CryptoException with the following reason codes:
     * <li><code>INVALID_INIT</code> if this Authority interface is not initialized.</li>
     * @throws SecurityException if the inBuff is not global array.
     */
    public void update(byte[] inBuff,
            short inOffset,
            short inLength)
     throws CryptoException;

    /**
     * Recovers a cryptographic key from a set of data 
     * structures provided in the input buffer (inBuff).
     * As a mandatory step, the recovery mechanism includes the verification of
     * the origin and integrity of the recovered key. 
     * This method knows, from the set of data structures present in the input 
     * buffer, which recovery mechanism is to be used.
     * The recovered key is written in the ouput buffer (outBuff) at specified
     * offset (outOffset), in the form of a key data structure whose format 
     * depends on the type of the key. 
     * A call to this method resets this instance of the Authority interface to
     * the state it was in when previously initialized via a call to init(). 
     * That is, the object is reset and available to recover another key. 
     * The input and output buffers may overlap and shall be global arrays.
     * 
     * @param inBuff containing input data.
     * @param inOffset offset of input data.
     * @param inLength length of input data.
     * @param outBuff the buffer where recovered key data structure shall be written 
     * @param outOffset offset where recovered key data structure shall be written
     * @return <code>Length</code> of the recovered key data structure written 
     * in outBuff at outOffset,or 0 if the recovery mechanism failed 
     * (e.g. recovered key was considered invalid).
     *  
     * @throws CryptoException - with the following reason codes:
     * 	<li><code>INVALID_INIT</code> if this Authority interface is not initialized or
     * 	initialized in <code>MODE_SIGN</code> mode.</li>
     * @throws SecurityException if the inBuff or outBuff are not global array.
     */
    public short recoverKey(byte[] inBuff,
                short inOffset,
                short inLength,
                byte[] outBuff,
                short outOffset)
    throws CryptoException;

}
