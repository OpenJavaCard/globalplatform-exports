
package org.globalplatform;

import javacard.framework.*;

/**
 * The GPSystem class exposes a subset of the behavior of the OPEN to the
 * outside world. The OPEN implements and enforces a Card Issuer's security
 * policy relating to these services. This OPEN class provides functionality at
 * the same level as the JCRE, i.e. the "system" context with special
 * privileges. This class is composed of static methods visible to all applets
 * importing the globalplatform package.
 * @since <ul>
 * <li>export file version 1.0: initial version.
 * <li>export file version 1.1: new services and/or constants added.
 * <li>export file version 1.3: new services and/or constants added.
 * <li>export file version 1.5: new services and/or constants added.
 * </ul>
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
   * Indicates that the CVM interface required is the ETSI PIN App 1 (0x01).
   * @since export file version 1.5
   */
  public static final byte CVM_ETSI_PIN_APP_1 = (byte) 0x01;

  /**
   * Indicates that the CVM interface required is the ETSI PIN App 2 (0x02).
   * @since export file version 1.5
   */
  public static final byte CVM_ETSI_PIN_APP_2 = (byte) 0x02;

  /**
   * Indicates that the CVM interface required is the ETSI PIN App 3 (0x03).
   * @since export file version 1.5
   */
  public static final byte CVM_ETSI_PIN_APP_3 = (byte) 0x03;

  /**
   * Indicates that the CVM interface required is the ETSI PIN App 4 (0x04).
   * @since export file version 1.5
   */
  public static final byte CVM_ETSI_PIN_APP_4 = (byte) 0x04;

  /**
   * Indicates that the CVM interface required is the ETSI PIN App 5 (0x05).
   * @since export file version 1.5
   */
  public static final byte CVM_ETSI_PIN_APP_5 = (byte) 0x05;

  /**
   * Indicates that the CVM interface required is the ETSI PIN App 6 (0x06).
   * @since export file version 1.5
   */
  public static final byte CVM_ETSI_PIN_APP_6 = (byte) 0x06;

  /**
   * Indicates that the CVM interface required is the ETSI PIN App 7 (0x07).
   * @since export file version 1.5
   */
  public static final byte CVM_ETSI_PIN_APP_7 = (byte) 0x07;

  /**
   * Indicates that the CVM interface required is the ETSI PIN App 8 (0x08).
   * @since export file version 1.5
   */
  public static final byte CVM_ETSI_PIN_APP_8 = (byte) 0x08;

  /**
   * The current applet context is in the Life Cycle State of LOCKED (0x80).
   * To know if an application is locked, a logical AND operation shall be 
   * performed between this constant and the current application life cycle 
   * state.        
   */
  public static final byte APPLICATION_LOCKED = (byte) 0x80;
    
  /**
   * Indicates the family of the Secure Channel Global Service Identifier (0x81).
   * @since export file version 1.1
   */
  public static final byte FAMILY_SECURE_CHANNEL = (byte) 0x81;
    
  /**
   * Indicates the family of the CVM Global Service Identifier (0x82).
   * @since export file version 1.1
   */
  public static final byte FAMILY_CVM = (byte) 0x82;
    
  /**
   * @since export file version 1.2
   * @deprecated Use {@link #FAMILY_AUTHORITY} instead.
   */
  public static final byte FAMILY_AUHTORITY= (byte)0x83;
 
  /**
   * Indicates the family of the Authority Service Identifier (0x83).
   * @since export file version 1.2
   */
  public static final byte FAMILY_AUTHORITY= (byte)0x83;

  /**
   * Indicates the family of the HTTP Administration Service Identifier (0x84).
   * @since export file version 1.3
   */
  public static final byte FAMILY_HTTP_ADMINISTRATION= (byte)0x84;
     
  /**
   * Indicates the family of the HTTP Report Service Identifier (0x85). 
   * Application is not requested to expose this service through {@link #getService(AID, short)}
   * @since export file version 1.3
   */
  public static final byte FAMILY_HTTP_REPORT= (byte)0x85;
    
  /**
   * Indicates the family of the USSM Global Service Identifier (0xA0).
   * @since export file version 1.1
   */
  public static final byte FAMILY_USSM = (byte) 0xA0;
    
  /**
   * Indicates the generic Global Service Identifier (0x80).
   * @since export file version 1.1
   */
  public static final byte GLOBAL_SERVICE_IDENTIFIER = (byte) 0x80;

  /**
   * This method returns the Life Cycle State of the current applet context.
   * <p>
   * <p>Notes:<ul>
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
   * <p>Notes:<ul>
   * <li><em>OPEN searches the GlobalPlatform Registry for the CVM Application that has the Global Service
   * privilege and is uniquely registered with <code>FAMILY_CVM</code> and this <code> bCVMIdentifier</code> or is uniquely 
   * registered for the entire <code>FAMILY_CVM</code>;</em>
   * <li><em>OPEN invokes the <code>Applet.getShareableInterfaceObject()</code> method of the corresponding
   * CVM Application . OPEN provides the following parameters to the
   * <code>Applet.getShareableInterfaceObject()</code> method: ‘clientAID’ is set to the AID of the
   * current applet context (i.e. the requesting on-card entity) and ‘parameter’ is set to 
   * <code>GLOBAL_SERVICE_IDENTIFIER</code>;</em>
   * <li><em>The CVM Application returns the reference of the Shareable Interface Object implementing the
   * GlobalService interface. OPEN invokes the <code>GlobalService.getServiceInterface()</code> method with
   * the following parameters: <code>‘GPRegistryEntry’</code> is set to the GPRegistryEntry interface of the current applet
   * context (i.e. the requesting on-card entity), <code>‘sServiceName’</code> first byte is set to <code>FAMILY_CVM</code>
   * and second byte to <code>bCVMIdentifier</code>, baBuffer is set to null (indicating no buffer), 
   * sOffset and sLength are set to zero;</em>
   * <li><em>The CVM Application returns the reference of the Shareable Interface Object implementing 
   * the CVM interface corresponding to <code>bCVMIdentifier</code>. 
   * This handle is then returned by OPEN to the requesting applet.</em>
   * </ul>
   * @param bCVMIdentifier identifies the required CVM interface.
   * @return the CVM interface object reference.
   * <code>null</code> if there is no matching CVM Interface in the GlobalPlatform Registry.
   * @see <code>CVM_GLOBAL_PIN</code>
   */
  public static CVM getCVM(byte bCVMIdentifier)
  {      
    return (null);
  }

  /**
   * This method returns a handle to the SecureChannel interface.
   * <p>Notes:<ul>
   * <li><em>The OPEN locates the entry of the current applet context in the Open Platform Registry to
   * determine the application's associated Security Domain,</em>
   * <li><em>This method may be implemented using a similar mechanism as described for the <code>getCVM</code> method.</em>
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
   * The sequence of bytes will be visible on a subsequent power-up or cold reset.
   * <p>Notes:<ul>
   * <li><em>The OPEN locates the entry of the current applet context in the Open Platform
   * Registry and verifies that the application has the "Card Reset" privilege for the current card I/O interface;</em>
   * <li><em>The OPEN is responsible for synchronizing the length of historical bytes in
   * Format Character T0 of the ATR.</em>
   * </ul>
   * <p>
   * @param baBuffer the source byte array containing the ATR historical bytes.
   * @param sOffset offset of the ATR historical bytes within source byte array.
   * @param bLength the number of historical bytes.
   * @return <code>true</code> if ATR bytes set, <code>false</code> otherwise.
   */
  public static boolean setATRHistBytes (byte[] baBuffer, short sOffset, byte bLength)
  {      
    return (false);
  }

  /**
   * This method allows the application associated with the current applet context 
   * to change its state to an application specific life cycle state or to lock itself. 
   * An application cannot unlock itself using this method. 
   * <p>
   * @param bState an application specific life cycle state (0x07 to 0x7F with 3 low order bits set), 
   * or {@link #APPLICATION_LOCKED} (0x80).
   * @return <code>false</code> if the application invoking this method is currently locked.
   * @return <code>true</code> if the operation is successful, <code>false</code> otherwise.
   * @since <ul>
   * <li>export file version 1.0: initial version.
   * <li>export file version 1.5: this method now allows the application associated with the current applet context to lock itself.
   * </ul>
   */
  public static boolean setCardContentState(byte bState)
  {      
    return (false);
  }

  /**
   * This method allows an Application (e.g. a CVM) to access the GP Registry entry of another Application.
   * If no AID is input, this method provides the GP Registry entry of the requesting Application. 
   * It returns a handle to the GPRegistryEntry interface.
   * <p>Notes:<ul>
   * <li><em>The OPEN verifies that the requesting Application has the Global Registry privilege, 
   * or is the Security Domain associated with the Application being investigated, 
   * or is the investigated Application itself.</em>
   * </ul>
   * <p>
   * @param reqAID identifies the required GPRegistryEntry interface.
   * @return The <code>GPRegistryEntry</code> interface reference corresponding to the input AID, or
   * <code>null</code> if there is no Application in the GP Registry that corresponds to the input AID or
   * if the requesting Application is not authorized to access the corresponding GP Registry entry.
   * @see <code>AID</code>
   * @since export file version 1.1
   */
  public static GPRegistryEntry getRegistryEntry(AID reqAID)
  {      
    return (null);
  }

  /**
   * This method returns a handle to the Global Services interface of a Global Services Application. 
   * <p>Notes:<ul>
   * <li><em>The <code>serverAID</code> parameter is optional: if not known by the requesting on-card entity, it is set to <code>null</code>;</em>
   * <li><em>The <code>sServiceName</code> parameter is mandatory. When the serverAID is Null, 
   * the OPEN searches the GlobalPlatform Registry for the Global Services Application corresponding 
   * to this unique <code>sServiceName</code> or to this entire service family. 
   * When the <code>serverAID</code> is not <code>null</code>, the OPEN checks that the <code>sServiceName</code> or
   * to this entire service family is recorded in the GP Registry for this <code>serverAID</code>;</em>
   * <li><em>OPEN invokes <code>the Applet.getShareableInterfaceObject()</code> method of the corresponding Global Services Application.
   * OPEN provides the following parameters to the <code>Applet.getShareableInterfaceObject()</code> method: 
   * <code>clientAID</code> is set to the AID of the current applet context (i.e. the requesting on-card entity) and
   *  parameter is set to <code>GLOBAL_SERVICE_IDENTIFIER<code>;</em>
   * <li><em>The Global Services Application returns the reference of the Shareable Interface Object implementing the GlobalService interface. 
   * This handle to the GlobalService interface is then returned by OPEN to the requesting applet.</em>
   * </ul>
   * <p>
   * @param serverAID identifies the required Global Service Application AID or is null.
   * @param sServiceName identifies the required Global Service Service Name or the entire Service Family.
   * @return the GlobalService interface object reference or null.
   * @see #GLOBAL_SERVICE_IDENTIFIER
   * @see #FAMILY_CVM 
   * @see #FAMILY_SECURE_CHANNEL
   * @see #FAMILY_USSM
   * @see #FAMILY_AUTHORITY
   * @see #FAMILY_HTTP_ADMINISTRATION
   * @see #FAMILY_HTTP_REPORT
   * @since export file version 1.1
   */
  public static GlobalService getService(AID serverAID, short sServiceName)
  {      
    return (null);
  }

}


