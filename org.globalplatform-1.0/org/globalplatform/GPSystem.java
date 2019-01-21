
package org.globalplatform;

import javacard.framework.*;

/**
 *
 * The GPSystem class exposes a subset of the behavior of the OPEN to the outside world. The OPEN
 * implements and enforces a Card Issuer's security policy relating to these services. This OPEN
 * class provides functionality at the same level as the JCRE, i.e. the "system" context with
 * special privileges. This class is composed of static methods visible to all applets importing
 *  the globalplatform package.
*/

public class GPSystem
{

    /**
    * The current applet context is in the Life Cycle State of INSTALLED (0x03).
    * <p>Note:<ul>
    * <li><em>The Life Cycle State INSTALLED could be indicated along with another application
    * specific Life Cycle State, e.g. a value of (0x07) indicates that the applet has been made
    * selectable.</em>
    * </ul>
    */
    public static final byte APPLICATION_INSTALLED = (byte) 0x03;

    /**
    * The current applet context is in the Life Cycle State of SELECTABLE (0x07).
    * <p>Note:<ul>
    * <li><em>The Life Cycle State SELECTABLE could be indicated along with another application
    * specific Life Cycle State.</em>
    * </ul>
    */
    public static final byte APPLICATION_SELECTABLE = (byte) 0x07;

    /**
    * The Security Domain is in the Life Cycle State of PERSONALIZED (0x0F).
    */
    public static final byte SECURITY_DOMAIN_PERSONALIZED = (byte) 0x0F;

    /**
    * The card is in the Life Cycle State of OP_READY (0x01).
    */
    public static final byte CARD_OP_READY = (byte) 0x01;

    /**
    * The card is in the Life Cycle State of INITIALIZED (0x07).
    */
    public static final byte CARD_INITIALIZED = (byte) 0x07;

    /**
    * The card is in the Life Cycle State of SECURED (0x0F).
    */
    public static final byte CARD_SECURED = (byte) 0x0F;

    /**
    * The card is in the Life Cycle State of CARD_LOCKED (0x7F).
    */
    public static final byte CARD_LOCKED = (byte) 0x7F;

    /**
    * The card is in the Life Cycle State of TERMINATED (0xFF).
    */
    public static final byte CARD_TERMINATED = (byte) 0xFF;

    /**
    * Indicates that the CVM interface required is a Global PIN (0x11).
    */
    public static final byte CVM_GLOBAL_PIN = (byte) 0x11;

    /**
    * This method returns the Life Cycle State of the current applet context.
    * <p>
    * <p>Notes:<ul>
    * <li><em>This method shall not be invoked from the <code>Applet.install()</code> method.</em>
    * <li><em>The OPEN locates the entry of the current applet context in the Open Platform Registry and
    * retrieves the Life Cycle State.</em>
    * </ul>
    * <p>
    * @return the Life Cycle State of the current applet context.
    * @see <code>APPLICATION_INSTALLED, APPLICATION_SELECTABLE, SECURITY_DOMAIN_PERSONALIZED</code>
    */
    public static byte getCardContentState()
    {      
        return ((byte)0);
    }

    /**
    * This method returns the Life Cycle State of the card.
    * <p>
    * @return the Life Cycle State of the card
    * @see <code>CARD_OP_READY, CARD_INITIALIZED, CARD_SECURED, CARD_LOCKED,
    * CARD_TERMINATED</code>
    */
    public static byte getCardState()
    {      
        return((byte)0);
    }

    /**
    * This method returns a handle to the CVM interface.
    * <p>
    * @param <code>bCVMIdentifier</code> identifies the required CVM interface.
    * @return the CVM interface object reference.
    * @see CVM_GLOBAL_PIN
    */
    public static CVM getCVM(byte bCVMIdentifier)
    {      
        return (null);
    }

    /**
    * This method returns a handle to the SecureChannel interface.
    * <p>Notes:<ul>
    * <li><em>This method shall not be invoked from the <code>Applet.install()</code> method.</em>
    * <li><em>The OPEN locates the entry of the current applet context in the Open Platform Registry to
    * determine the application's associated Security Domain.</em>
    * </ul>
    * <p>
    * @return the SecureChannel interface object reference.
    */
    public static SecureChannel getSecureChannel()
    {      
        return (null);
    }

    /**
    * This method locks the card.
    * <p>Notes:<ul>
    * <li><em>This method shall not be invoked from the <code>Applet.install()</code> method.</em>
    * <li><em>The OPEN locates the entry of the current applet context in the Open Platform
    * Registry and verifies that the application has the card lock privilege.</em>
    * </ul>
    * <p>
    * @return <code>true</code> if card locked, <code>false</code> otherwise
    */
    public static boolean lockCard ()
    {      
        return (false);
    }

    /**
    * This method terminates the card.
    * <p>Notes:<ul>
    * <li><em>This method shall not be invoked from the <code>Applet.install()</code> method.</em>
    * <li><em>The OPEN locates the entry of the current applet context in the Open Platform
    * Registry and verifies that the application has the card terminate privilege.</em>
    * </ul>
    * <p>
    * @return <code>true</code> if card terminated, <code>false</code> otherwise.
    */
    public static boolean terminateCard ()
    {
        return (false);
    }

    /**
    * This method sets the historical bytes of the ATR (Answer To Reset) string.
    * The sequence of bytes will be visible on a subsequent power-up or reset.
    * <p>Notes:<ul>
    * <li><em>This method shall not be invoked from the <code>Applet.install()</code> method.</em>
    * <li><em>The OPEN locates the entry of the current applet context in the Open Platform
    * Registry and verifies that the application has the "default selected" privilege.</em>
    * <li><em>The OPEN is responsible for updating the length of historical bytes in
    * Format Character T0 of the ATR.</em>
    * </ul>
    * <p>
    * @param <code>baBuffer</code> the source byte array containing the ATR historical bytes. Must be a global array.
    * @param <code>sOffset</code> offset of the ATR historical bytes within source byte array.
    * @param <code>bLength</code> the number of historical bytes.
    * @return <code>true</code> if ATR bytes set, <code>false</code> otherwise.
    */
     public static boolean setATRHistBytes (byte[] baBuffer, short sOffset, byte bLength)
    {      
        return (false);
    }

    /**
    * This method sets the application specific Life Cycle State of the current applet context.
    * Application specific Life Cycle States range from 0x07 to 0x7F as long as the 3 low order bits
    * are set.
    * <p>Notes:<ul>
    * <li><em>This method shall not be invoked from the <code>Applet.install()</code> method.</em>
    * <li><em>The OPEN shall reject any transition request to the Life Cycle States INSTALLED or LOCKED.</em>
    * <li><em>The OPEN locates the entry of the current applet context in the Open Platform Registry
    * and modifies the value of the Life Cycle State.</em>
    * </ul>
    * <p>
    * @param <code>bState</code> the application specific state.
    * @return <code>true</code> if the operation is successful, <code>false</code> otherwise.
    * @see <code>SECURITY_DOMAIN_PERSONALIZED</code>
    */
    public static boolean setCardContentState(byte bState)
    {      
        return (false);
    }

}


