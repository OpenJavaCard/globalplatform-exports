
package org.globalplatform;

/**
* This defines an interface which extends SecureChannel Interface and includes a supplementary method. See 
* <code>SecureChannel</code> interface for a description of the underlying interface. Prior to using this interface, an Application is
* required to obtain a handle to its associated Security Domain's <code>SecureChannelx</code> interface object by invoking the 
* <code>GPSystem.getSecureChannel()</code> method and casting the returned object to type <code>SecureChannelx</code>.  
* The <code>SecureChannelx</code> Interface shall be implemented by a Security Domain compliant to this version of the 
* specification and the corresponding object reference shall be exposed through the 
* <code>GPSystem.getSecureChannel()</code> method.
* @see <code>SecureChannel</code>
* @since export file version 1.1
 */

public interface SecureChannelx extends SecureChannel
{
    
    /**
    * <p>
    * This method updates the Current Security Level for all subsequent invocations of <code>wrap()</code> and <code>unwrap()</code> 
    * methods, except when Secure Channel is not active or was aborted during the same Application session.
    * Current Security Level is coded as a bit-map according to table 10-1. The current Security Level cannot be set 
    * below the compulsory Session Security Level, buty only equal or above. The Current Security Level may be 
    * increased or decreased during a Secure Channel Session as long as it is at least equal to the compulsory Session
    * Security Level.
    * <p>Notes:<ul>
    * <li><em>This method fails if a Secure Channel Session is not open, if the corresponding session keys are not 
    * available or if the Current Security Level is equal to <code>NO_SECURITY_LEVEL</code>.</em>
    * </ul>
    * @param bSecurityLevel <li>the Current Security Level to be set.</li>
    * @exception ISOException with the following reason codes (other security mechanism
    * related status words may be returned):<ul>
    * <li><code>ISO7816.SW_CONDITION_NOT_SATISFIED</code></li></ul>
    */

    public void setSecurityLevel(byte bSecurityLevel);
}


