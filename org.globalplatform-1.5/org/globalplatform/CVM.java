
package org.globalplatform;

import javacard.framework.*;

/**
 *This defines the interface of a Global Services Application implementing one or more Cardholder Verification 
 *Methods. This class offers basic Cardholder Verification Method services (e.g. CVM verification, CVM state 
 *interrogation) to any of the Applications present on the card, while some of the services (e.g. unblock CVM, change 
 *CVM value) are restricted to Applications with the privilege to change the CVM values. Prior to using this interface,
 *an Application is required to obtain a handle to the CVM services. The CVM application shall expose the 
 *GlobalService interface object(s) through <code>applet.getShareableInterfaceObject()</code> according to this specification.
 * @since export file version 1.0
 */

public interface CVM extends Shareable
{

    /**
    * The CVM value comparison was successful.
    */
    public static final short CVM_SUCCESS = 0;

    /**
    * The CVM value comparison failed.
    */
    public static final short CVM_FAILURE = -1;

    /**
    * The CVM value is formatted as ASCII bytes.
    * <p>Note:<ul>
    * <li><em>If the CVM value is stored in a format other than ASCII, it is the responsibility
    * of the interface to convert to the expected format.</em>
    * </ul>
    */
    public static final byte FORMAT_ASCII = (byte) 0x01;

    /**
    * The CVM value is formatted as numerical digits, coded on a nibble (4 bits) and left justified.
    * <p>Note:<ul>
    * <li><em>If the CVM value is stored in a format other than BCD, it is the responsibility of
    * the interface to convert to the expected format.</em>
    * <li><em>If the length of the CVM value is odd, the right most nibble of the CVM value
    * shall be high values ('F').</em>
    * </ul>
    */
    public static final byte FORMAT_BCD = (byte) 0x02;

    /**
    * The CVM value is formatted as hexadecimal (binary) data.
    * <p>Note:<ul>
    * <li><em>If the CVM value is stored in a format other than HEX, it is the responsibility of the
    * interface to convert to the expected format.</em>
    * </ul>
    */
    public static final byte FORMAT_HEX = (byte) 0x03;

    /**
    * This method indicates whether the CVM is present and activated.
    * If active the CVM could be in any one of the following states: ACTIVE, INVALID_SUBMISSION,
    * VALIDATED or BLOCKED.
    * @return <code>true</code> if the CVM state is (at least) ACTIVE, <code>false</code> otherwise.
    */
    public boolean isActive();

    /**
    * This method indicates whether an attempt has been made to compare the CVM value.
    * <p>Note:<ul>
    * <li><em> This method does not differentiate whether the CVM value has been successfully
    * verified or not, i.e. CVM states of VALIDATED or INVALID_SUBMISSION.</em>
    * </ul>
    * @return <code>true</code> if the CVM state is (at least) SUBMITTED, <code>false</code> otherwise.
    */
    public boolean isSubmitted();

    /**
    * This method indicates whether a successful comparison of the CVM value has occurred (CVM state
    * of VALIDATED).
    * @return <code>true</code> if the CVM state is VALIDATED, <code>false</code> otherwise.
    */
    public boolean isVerified();

    /**
    * This method indicates whether the CVM is currently BLOCKED.
    * @return <code>true</code> if the CVM state is BLOCKED, <code>false</code> otherwise.
    */
    public boolean isBlocked();

    /**
    * This method returns the number of tries remaining for the CVM.
    * This indicates the number of times the CVM value can be incorrectly presented prior
    * to the CVM reaching the state of BLOCKED.
    * @return Tries remaining.
    */
    public byte getTriesRemaining();

    /**
    * This method changes the CVM value.
    * <p>Notes:<ul>
    * <li><em>The CVM Application shall verify the CVM Management privilege using the GPRegistryEntry interface of the invoking applet;</em>
    * <li><em>The invoking applet is responsible for specifying the format of the CVM value;</em>
    * <li><em>The CVM Retry Counter is reset when changing the CVM value;</em>
    *  <li><em>The CVM state is reset to ACTIVE when changing the CVM value. 
    * When setting the CVM value before the CVM state is ACTIVE, the CVM state transitions 
    * to ACTIVE only if the Retry Limit is already set;</em>
    *  <li><em>Data presented always replaces the previous data regardless of its format 
    * or length. The CVM shall remember the format, length, and value of the CVM data.
    * The CVM may (or may not) do editing checks on the data and reject the CVM update if the data fails the editing checks (e.g. reject data that is presented as BCD that is not numerical).</em>
    * </ul>
    * <p>
    * @param baBuffer the source byte array containing the CVM value. This buffer must be global.
    * @param sOffset the offset of the CVM value within source byte array.
    * @param bLength the length of the CVM value.
    * @param bFormat the format of the CVM value.
    * @return <code>true</code> if the CVM value was changed, <code>false</code> otherwise.
    */
    public boolean update(byte[] baBuffer, short sOffset, byte bLength, byte bFormat);

    /**
    * This method resets the CVM state to ACTIVE.
    * <p>Notes:<ul>
    * <li><em>The state of the CVM can only be set to ACTIVE from the states INVALID_SUBMISSION or VALIDATED.</em>
    * <li><em>The state of the CVM cannot be set to ACTIVE from BLOCKED.</em>
    * </ul>
    * <p>
    * @return <code>true</code> if the CVM state was reset, <code>false</code> otherwise.
    */
    public boolean resetState();

    /**
    * This method sets the CVM state to BLOCKED.
    * <p>Notes:<ul>
    * <li><em>The CVM application shall verify the CVM Management privilege using the GPRegistryEntry interface of the invoking applet.</em>
    * </ul>
    * <p>    
    * @return <code>true</code> if the CVM state was set to BLOCKED, <code>false</code> otherwise.
    */
    public boolean blockState();

    /**
    * This method resets the CVM state from BLOCKED to ACTIVE.
    * <p>Notes:<ul>
    * <li><em>The CVM Application shall verify the CVM Management privilege using 
    * the <code>GPRegistryEntry</code> interface of the invoking applet;</em>
    * <li><em>The CVM try counter is reset when unblocking the CVM.</em>
    * </ul>
    * <p>
    * @return <code>true</code> if the CVM state was reset to ACTIVE, <code>false</code> otherwise.
    */
    public boolean resetAndUnblockState();

    /**
    * This method sets the maximum number of tries for the CVM.
    * <p>Notes:<ul>
    * <li><em>The CVM Application shall verify the CVM Management privilege using 
    * the <code>GPRegistryEntry</code> interface of the invoking applet;</em>
    * <li><em>The CVM try counter is reset when setting the maximum number of tries;</em>
    * <li><em>The CVM state is reset to ACTIVE when changing the maximum number of tries.
    * When setting the maximum number of tries before the CVM state is ACTIVE, the CVM state 
    * transitions to ACTIVE only if the CVM value is already set.</em>
    * </ul> 
    * <p>
    * @param bTryLimit the maximum number of tries for the CVM.
    * @return <code>true</code> if the maximum number of tries was set, <code>false</code> otherwise.
    */
    public boolean setTryLimit(byte bTryLimit);

    /**
    * This method compares the stored CVM value with the one passed as parameter.
    * <p>Notes:<ul>
    * <li><em>If the value passed as parameter is not in the same format as the CVM value,
    * the value passed as parameter must be converted prior to comparing.</em>
    * <li><em>If HEX format is presented for CVM verification and ASCII or BCD format 
    * was used for updating the CVM value, the comparison fails;</em>
    * <li><em>If HEX format is presented for CVM verification and HEX format was used 
    * for updating the CVM value, the comparison succeeds when the length and the data value match exactly;</em>
    * <li><em>If BCD or ASCII format is presented for CVM verification and HEX format was used 
    * for updating the CVM value, the comparison fails;</em>
    * <li><em>If ASCII format is presented for CVM verification and BCD format was used 
    * for updating the CVM value, the comparison fails if the ASCII characters presented 
    * for verification are not all numerical (zero to nine). 
    * If all the ASCII characters are numerical, format conversion occurs and the comparison succeeds
    *  when the length and the data value match exactly;</em>
    * <li><em>If BCD format is presented for CVM verification and ASCII format was used for
    *  updating the CVM value, the comparison fails if the CVM value contains non-numerical ASCII 
    * characters. If the CVM value contains only numerical ASCII characters, format conversion occurs 
    * and the comparison succeeds when the length and the data value match exactly;</em>
    * <li><em>If the comparison is successful, the try counter must be reset and the CVM state
    * must be set to VALIDATED.</em>
    * <li><em>If the comparison is unsuccessful, the try counter must be updated and the
    * CVM state must be set to INVALID_SUBMISSION.</em>
    * <li><em>The Retry Counter object and the CVM states VALIDATED and INVALID_SUBMISSION shall not conform 
    * to a transaction in progress, i.e. they shall not revert to a previous value if a transaction in progress is aborted;</em>
    * <li><em>If the maximum number of tries has been reached, the CVM state must be set to BLOCKED.</em>
    * </ul>
    * <p>
    * @param baBuffer the source byte array containing the submitted CVM value. This buffer must be global.
    * @param sOffset the offset of the submitted CVM value within source byte array.
    * @param bLength the length of the submitted CVM value.
    * @param bFormat the format of the submitted CVM value.
    * @return value indicating whether the comparison was successful or not. Values other than CVM_SUCCESS (0) or
    * CVM_FAILURE (-1) are Reserved for Future Use.
    */
    public short verify(byte[] baBuffer, short sOffset, byte bLength, byte bFormat);
}


