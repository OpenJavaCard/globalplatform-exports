
package org.globalplatform;

import javacard.framework.*;

/**
 * This defines the interface that represents an applet method accessible through the OPEN to
 * the application's associated Security Domain. This interface must be implemented by the Applet
 * class that will use the additional functionality allowing a Security Domain to pass data to the
 * applet.
 */

public interface Application extends Shareable
{

    /**
    * This method processes application specific data received from another entity on the card.
    * If this other entity is the Application's associated Security Domain, this data is the
    * APDU buffer.
    * <p>
    * <p>Notes:<ul>
    * <li><em>During the invocation the Java Card VM performs a context switch.</em>
    * <li><em>The application is responsible for managing the atomicity of its own data.</em>
    * <li><em>As this method can be invoked by a Security Domain immaterial of the Application Life
    * Cycle State, it is the responsibility of the applet to ensure that its current Life Cycle State
    * is valid for this operation.</em>
    * <li><em>As the applet is not the selected application, it should not use the
    * <code>CLEAR_ON_DESELECT</code> when creating transient arrays.</em>
    * </ul>
    * <p>
    * @param <code>baBuffer</code> the source byte array containing the data expected by the applet. This buffer must be global.
    * @param <code>sOffset</code> starting offset of data within source byte array.
    * @param <code>sLength</code>  length of data.
    */
    public abstract void processData(byte[] baBuffer, short sOffset, short sLength);

}


