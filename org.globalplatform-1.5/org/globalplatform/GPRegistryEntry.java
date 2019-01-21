
package org.globalplatform;

import javacard.framework.*;

/**
 * This defines the interface corresponding to the GPRegistryEntry of a single Application. 
 * The Global Service Application uses this interface to check the validity of the request presented 
 * by an on-card entity.
 * Prior to using this interface, an Application is required to obtain a handle to the GPRegistryEntry of an 
 * Application by invoking the <code>GPSystem.getRegistryEntry()</code> method. 
 * @since export file version 1.1
 */

public interface GPRegistryEntry extends Shareable
{

    /**
    * Privilege indicating Authorized Management (0x09).
    */
    public static final byte PRIVILEGE_AUTHORIZED_MANAGEMENT = (byte) 0x09;

    /**
    * Privilege indicating Card Lock (0x03).
    */
    public static final byte PRIVILEGE_CARD_LOCK = (byte) 0x03;

    /**
    * Privilege indicating Card Reset (0x05).
    */
    public static final byte PRIVILEGE_CARD_RESET = (byte) 0x05;

    /**
    * Privilege indicating Card Terminate (0x04).
    */
    public static final byte PRIVILEGE_CARD_TERMINATE = (byte) 0x04;

    /**
    * Privilege indicating CVM Management (0x06).
    */
    public static final byte PRIVILEGE_CVM_MANAGEMENT = (byte) 0x06;

    /**
    * Privilege indicating DAP verification (0x01).
    */
    public static final byte PRIVILEGE_DAP_VERIFICATION = (byte) 0x01;

    /**
    * Privilege indicating Delegated Management (0x02).
    */
    public static final byte PRIVILEGE_DELEGATED_MANAGEMENT = (byte) 0x02;

    /**
    * Privilege indicating Final Application (0x0E).
    */
    public static final byte PRIVILEGE_FINAL_APPLICATION = (byte) 0x0E;

    /**
    * Privilege indicating Global Delete (0x0B).
    */
    public static final byte PRIVILEGE_GLOBAL_DELETE = (byte) 0x0B;

    /**
    * Privilege indicating Global Lock (0x0C).
    */
    public static final byte PRIVILEGE_GLOBAL_LOCK = (byte) 0x0C;

    /**
    * Privilege indicating Global Registry (0x0D).
    */
    public static final byte PRIVILEGE_GLOBAL_REGISTRY = (byte) 0x0D;

    /**
    * Privilege indicating Global Service (0x0F).
    */
    public static final byte PRIVILEGE_GLOBAL_SERVICE = (byte) 0x0F;

    /**
    * Privilege indicating Mandated DAP verification privilege (0x07).
    */
    public static final byte PRIVILEGE_MANDATED_DAP = (byte) 0x07;

    /**
    * Privilege indicating Receipt Generation (0x10).
    */
    public static final byte PRIVILEGE_RECEIPT_GENERATION = (byte) 0x10;

    /**
    * Privilege indicating application is a Security Domain (0x00).
    */
    public static final byte PRIVILEGE_SECURITY_DOMAIN = (byte) 0x00;

    /**
    * Privilege indicating Token Verification (0x0A).
    */
    public static final byte PRIVILEGE_TOKEN_VERIFICATION = (byte) 0x0A;

    /**
    * Privilege indicating Trusted Path (0x08).
    */
    public static final byte PRIVILEGE_TRUSTED_PATH = (byte) 0x08;
    /**
    * Privilege indicating Ciphered Load File Data Block (0x11).
    */
    public static final byte PRIVILEGE_CIPHERED_LOAD_FILE_DATA_BLOCK = (byte) 0x11;

    /**
    * This method allows a Global Services Application (e.g. a CVM Application) to deregister a service name.
    * <p>Notes:<ul>
    * <li><em>The OPEN checks that the requesting on-card entity has the Global Service Privilege and is associated 
    * with this registry entry;</em>
    * <li><em>The OPEN checks that the service name is registered as unique for the requesting on-card entity.</em>
    * </ul>
    * @param sServiceName the unique service name to deregister.
    * @exception ISOException with the following reason code: 
    * <li><code>ISO7816.SW_CONDITIONS_NOT_SATISFIED</code>
    */
    public void deregisterService(short sServiceName) throws ISOException;

    /**
    * This method returns the Application's AID registered in the current GlobalPlatform Registry's entry.
    * <p>Notes:<ul>
    * <li><em>The OPEN checks that the requesting on-card entity has the Global Service Privilege and is associated 
    * with this registry entry;</em>
    * <li><em>The OPEN checks that the service name is registered as unique for the requesting on-card entity.</em>
    * </ul>
    * @return the <code>AID</code> object.
    */
    public AID getAID();

    /**
    * This method returns all the Privileges bytes registered in the current GlobalPlatform registry entry.
    * @param baBuffer The byte array where Privileges bytes are to be stored.
    * @param sOffset The offset in baBuffer at which to begin the Privileges bytes.
    * @return sOffset + Length of the Privileges.
    * @exception ArrayIndexOutOfBoundsException <li>if storing the Privileges bytes would 
    * cause access outside array bounds or the sOffset is negative. 
    */
    public short getPrivileges(byte[] baBuffer, short sOffset) throws ArrayIndexOutOfBoundsException;

    /**
    * This method returns the Life Cycle State registered in the current GlobalPlatform Registry entry.
    * @return The Life Cycle State as defined in section 11.11.
    */
    public byte getState();

    /**
    * This method allows to verify if the entity whose AID is provided in the input parameters is registered
    * as the associated Security Domain of this GPRegistryEntry.
    * <p>Notes:<ul>
    * <li><em>The OPEN determines if the SDAID is registered in the current GlobalPlatform Registry's entry as 
    * the associated Security Domain.</em>
    * </ul>
    * @param SDAID object of the investigated Security Domain.
    * @return True if the GP Registry references the Security Domain as being associated with this GPRegistryEntry, or
    *         False otherwise 
    */
    public boolean isAssociated(AID SDAID);

    /**
    * This method allows an Application (e.g. a CVM Application) to verify if a given Privilege is registered
    * in this GPRegistryEntry (e.g. check the CVM Management privilege of another Application invoking 
    * the CVM.update() method).
    * @param bPrivilege the privilege number to verify, as defined in Table 6-1.
    * @return True  if at least the referenced Privilege is registered in the GP Registry entry, or
    *         False if the referenced Privilege is not registered in the GP Registry entry. 
    */
    public boolean isPrivileged(byte bPrivilege);

    /**
    * This method allows a Global Services Application (e.g. a CVM Application) to register a unique service 
    * identifier within the GlobalPlatform Registry.
    * <p>Notes:<ul>
    * <li><em>The OPEN checks that the requesting on-card entity has the Global Service Privilege and is associated 
    * with the current GlobalPlatform Registry entry;</em>
    * <li><em>The OPEN checks that the requested service identifier matches with (one of) the Service Parameter(s) 
    * recorded in the current GlobalPlatform Registry entry;</em>
    * <li><em>The OPEN checks that the service identifier is not already registered as unique by any other entry in
    * the GlobalPlatform Registry.</em>
    * </ul>
    * @param sServiceName the unique service name to register.
    * @exception ISOException with the following reason code: 
    * <li><code>ISO7816.SW_CONDITIONS_NOT_SATISFIED</code>
    */
    public void registerService(short sServiceName) throws ISOException;

    /**
    * This method allows the Life Cycle state of this GPRegistryEntry to be transitioned to the 
    * requested target state.
    * <p>Notes:<ul>
    * <li><em>A transition request to the Life Cycle State INSTALLED shall be rejected;</em>
    * <li><em>A transition request to Life Cycle state other than APPLICATION_LOCKED and APPLICATION_UNLOCKED 
    * shall be accepted only if the invoking Application  corresponds to this GPRegistryEntry;</em>
    * <li><em>An Application shall be able to lock and shall not be able to unlock itself;</em>
    * <li><em>Only an Application with GlobalLock privilege or the directly or indirectly associated Security Domain
    * of this GPRegistryEntry shall be able to lock or unlock this GPRegistry Entry;</em>
    * <li><em>This method shall fail if this GPRegsitryEntry corresponds to the Issuer Security Domain.</em>
    * </ul>
    * @param bState the target state for this GPRegistryEntry.
    * @return True  if the transition is successful, or
    *         False otherwise.
    */
    public boolean setState(byte bState);
}


