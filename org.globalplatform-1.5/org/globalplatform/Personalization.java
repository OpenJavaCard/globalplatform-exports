package org.globalplatform;

import javacard.framework.Shareable;

/**
 * This defines the interface that represents an applet method accessible through the OPEN to 
 * the Application's associated Security Domain. The Applet class that will use the additional 
 * functionality that allows a Security Domain to pass data to the applet and send back Applet’s 
 * data personalization must implement this interface.
 * <p><ul>
 * <li><em>The OPEN is responsible of calling this new interface if implemented by the Applet to 
 * personalize, otherwise the interface Application shall be called. 
 * See also section 7.3.3 of the GlobalPlatform Card Specification v2.2 - Personalization support.</em>
 * </ul>
 * @since export file version 1.2
 */
public interface Personalization extends Shareable {
    /**
     * This method processes Application specific data received from another entity
     * on the card. If this other entity is the Application's associated Security Domain,
     * this data is the APDU buffer.
     * <p>Notes:<ul>
     * <li><em>During the invocation the Java Card VM performs a context switch;</em>
     * <li><em>The applet is responsible for managing the atomicity of its own data;</em>
     * <li><em> As a Security Domain immaterial of the Application Life Cycle State can
     * invoke this method, it is the responsibility of the applet to ensure that its 
     * current Life Cycle State is valid for this operation.</em>
     * <li><em>As the applet is not the selected Application,
     * it should not use the CLEAR_ON_DESELECT when creating transient arrays.</em>
     * <li><em>If the method throws an ISOException the JavaCard runtime environment sends 
     * the associated reason code as the response status instead. 
     * If the Exception thrown does not extends ISOException the reason code is <code>ISO7816.SW_UNKNOWN</code>.
     * The personalization session remains open.</em>
     * </ul>
     *  
     * @param inBuffer the source byte array containing the data expected by the applet.
     * This buffer must be global.
     * @param inOffset starting offset within source byte array.
     * @param inLength length of data.
     * @param outBuffer the out byte array where the data expected by the Off-Card Entity shall be set. 
     * This buffer shall be global.
     * @param outOffset starting offset within out byte array
     * @return The number of bytes set inoutBuffer at outOffset.
     * @throws Exceptions thrown are Application specific.
     * 
     */
     public  short processData (byte[] inBuffer,short inOffset,short inLength,byte[] outBuffer,short outOffset);
}
