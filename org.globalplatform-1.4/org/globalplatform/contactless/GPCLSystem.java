package org.globalplatform.contactless;

import javacard.framework.AID;

/**
*
* The GPCLSystem class exposes a subset of the behavior of the CRS (OPEN extension)
* to other on-card components.  
* The class is composed of static methods visible to all applets importing the
* org.globalplatform.contactless package.
* 
* Static methods are provided to: 
* <ul>
* <li>Retrieve Contactless Registry Objects
* <li>Configure access to ISO 14443 interface
* <li>Set Volatile Priority
* <li>Retrieve the OPEN's Default and Current Protocol Parameters 
* <ul>
* <li>Default Protocol Parameter, 
* <li>Current Protocol Parameters,
* </ul>
* </ul>
*/
public class GPCLSystem {

	private GPCLSystem() {
		// empty
	}

  /**
	 * The OPEN uses this identifier to retrieve the {@link CLApplet} interface implemented by an applet.
	 */
	public static final byte GPCL_CL_APPLICATION=(byte)0x86;
	/**
	 * The OPEN uses this identifier to retrieve the {@link CRSApplication} interface implemented by an applet.
	 */
	public static final byte GPCL_CRS_APPLICATION=(byte)0x84;
	/**
	 * The OPEN uses this identifier to retrieve the {@link CRELApplication} interface implemented by an applet.
	 */
	public static final byte GPCL_CREL_APPLICATION=(byte)0x85;	

	/**
	 * Gets a reference to a <code>{@link GPCLRegistryEntry}</code> interface.
	 * 
	 * If no AID is input (i.e. <code>entry</code> is <code>null</code>),
	 * this method provides the <code>{@link GPCLRegistryEntry}</code> of the
	 * requesting applet.
	 * 
	 * <p>
	 * Contactless applets are those applets that have access to the contactless interface.
	 * 
	 * @param oAIDidentifies the applet for which the <code>GPCLRegistryEntry</code> interface should be
	 * retrieved, or <code>null</code>.
	 * 
	 * @return the <code>GPCLRegistryEntry</code> interface object, or
	 *         <code>null</code> 
	 *      	<li>if there is no applet with the specified entry or,
	 * 			<li>if the caller 
	 * 				<ul>
	 * 					<li>has not GLOBAL_REGISTRY privilege, and
	 * 					<li>is not the Security Domain directly or indirectly associated with the applet being investigated, and
	 * 					<li>is not the investigated applet itself, and
	 * 					<li>is a not CREL Application registered in the investigated applet's CREL list
	 * 				</ul>
	 */
	public static GPCLRegistryEntry getGPCLRegistryEntry(AID oAID) {
		return null;
	}
	/**
	 * Constant to use with <code>{@link #getNextGPCLRegistryEntry(GPCLRegistryEntry, short)}</code> 
	 * to look for all Contactless applications that belong to any family
	 */
	public static final short AFI_ANY = (short)0x0000;
	/**
	 * Constant to use with <code>{@link #getNextGPCLRegistryEntry(GPCLRegistryEntry, short)}</code> 
	 * to look for applications that belong to the Transport family
	 */
	public static final short AFI_TRANSPORT=(short)0x10;
	/**
	 * Constant to use with <code>{@link #getNextGPCLRegistryEntry(GPCLRegistryEntry, short)}</code> 
	 * to look for applications that belong to the Financial family
	 */
	public static final short AFI_FINANCIAL=(short)0x20;
	/**
	 * Constant to use with <code>{@link #getNextGPCLRegistryEntry(GPCLRegistryEntry, short)}</code> 
	 * to look for applications that belong to the Identification family
	 */
	public static final short AFI_IDENTIFICATION=(short)0x30;
	/**
	 * Constant to use with <code>{@link #getNextGPCLRegistryEntry(GPCLRegistryEntry, short)}</code> 
	 * to look for applications that belong to the Telecommunication family
	 */
	public static final short AFI_TELECOMMUNICATION=(short)0x40;
	/**
	 * Constant to use with <code>{@link #getNextGPCLRegistryEntry(GPCLRegistryEntry, short)}</code> 
	 * to look for applications that belong to the Medical family
	 */
	public static final short AFI_MEDICAL=(short)0x50;
	/**
	 * Constant to use with <code>{@link #getNextGPCLRegistryEntry(GPCLRegistryEntry, short)}</code> 
	 * to look for applications that belong to the Multimedia family
	 */
	public static final short AFI_MULTIMEDIA=(short)0x60;
	/**
	 * Constant to use with <code>{@link #getNextGPCLRegistryEntry(GPCLRegistryEntry, short)}</code> 
	 * to look for applications that belong to the Gaming family
	 */
	public static final short AFI_GAMING=(short)0x70;	
	/**
	 * Constant to use with <code>{@link #getNextGPCLRegistryEntry(GPCLRegistryEntry, short)}</code> 
	 * to look for applications that belong to the Data Storage family
	 */
	public static final short AFI_DATA_STORAGE=(short)0x80;
	/**
	 * Looks up contactless applets belonging to a particular application family.
	 * 
	 * The OPEN maintains an internal list of contactless applets. This method
	 * returns the <code>GPCLRegistryEntry</code> object for the next contactless applet that 
	 * matches the search criteria.
	 * <p>
	 * Contactless applets are those applets that have access to the contactless interface.
	 * <p>
	 * <ul>
	 * <li>Application with GLOBAL_REGISTRY privilege can iterate over all application
	 * <li>A CREL Application can only iterate over its referencing Applications.
	 * <li>Security Domain can only iterate on its directly or indirectly associated Application(s)
	 *     unless it has the GLOBAL_REGISTRY privilege.
	 * </ul>
	 * @param oEntry
	 * <ul>
	 * 	<li>if <code>oEntry</code> is null, this method returns the first Entry matching the specified family.
	 *	<li>If the list is empty, the method shall return null.
	 *	<li>If <code>oEntry</code> is not null, and represents an application matching the specified family, 
	 *	this method retrieves the next application matching the specified family, otherwise it shall return null.
	 *	<li>If <code>oEntry</code> points to the last application matching the specified family, the method shall return null.
	 *</ul>
	 * @param sFamily the Family Identifier to look for.
	 * 
	 * @return the reference to the <code>GPCLRegistryEntry</code> interface object of the contactless applet matching the search
	 * criteria; <code>null</code> is returned if no application is matching or the end of the list is reached.
	 * 
	 * @throws <code>ISOException</code> with reason
	 * <ul>
	 * <li> <code>ISO7816.SW_CONDITIONS_NOT_SATISFIED</code> if
	 * <ul>
	 * <li>the caller has not GLOBAL_REGISTRY privilege, and
	 * <li>the caller is not a Security Domain or is a Security Domain but the <code>oEntry</code> is not 
	 *     directly or indirectly associated with this Security Domain, and
	 * <li>the caller is not a CREL Application, or is a CRELApplication but the <code>oEntry</code> is not
	 *     referencing this CREL Application.
	 * </ul>
	 * </ul>
	 *
	 * @see AFI_ANY
	 * @see AFI_TRANSPORT
	 * @see AFI_TELECOMMUNICATION
	 * @see AFI_MULTIMEDIA
	 * @see AFI_MEDICAL
	 * @see AFI_IDENTIFICATION
	 * @see AFI_GAMING
	 * @see AFI_FINANCIAL
	 * @see AFI_DATA_STORAGE
	 */
	public static GPCLRegistryEntry getNextGPCLRegistryEntry(GPCLRegistryEntry oEntry, short sFamily) {
		return null;
	}


	/**
	 * Sets up or discards the volatile priority. 
	 * 
	 * @param oEntry 
	 * <ul>
	 * 	<li><code>null</code> value discards the volatile priority</li>
	 *  <li>The GPCLRegistryEntry to set in the volatile priority
	 *  If the <code>GPCLentry</code> identifies a Head of a group
	 *  all applications of this group are part of the volatile priority
	 *  in the same order as in the GlobalPlatform registry.</li>
	 *  </ul>
	 * @exception <code>ISOException</code> with reason
	 * <ul>
	 * <li>ISO7816.SW_CONDITION_NOT_STATISFIED 
	 * 	if the caller has not {@link GPCLRegistryEntry#PRIVILEGE_CONTACTLESS_ACTIVATION}</li>
	 * </ul>
	 */
	public static void setVolatilePriority(GPCLRegistryEntry oEntry){
		
	}
	/**
	 * The requested information is the DEFAULT_PROTOCOL_DATA_TYPE_A. 
	 */
	public final static short CARD_INFO_DEFAULT_PROTOCOL_DATA_TYPE_A=(short)0x01;
	/**
	 * The requested information is the DEFAULT_PROTOCOL_DATA_TYPE_B. 
	 */
	public final static short CARD_INFO_DEFAULT_PROTOCOL_DATA_TYPE_B=(short)0x02;
	/**
	 * The requested information is the DEFAULT_PROTOCOL_DATA_TYPE_F. 
	 */
	public final static short CARD_INFO_DEFAULT_PROTOCOL_DATA_TYPE_F=(short)0x03;
	/**
	 * The requested information is the CURRENT_PROTOCOL_DATA_TYPE_A. 
	 */
	public final static short CARD_INFO_CURRENT_PROTOCOL_DATA_TYPE_A=(short)0x04;
	/**
	 * The requested information is the CURRENT_PROTOCOL_DATA_TYPE_B. 
	 */
	public final static short CARD_INFO_CURRENT_PROTOCOL_DATA_TYPE_B=(short)0x05;
	/**
	 * The requested information is the CURRENT_PROTOCOL_DATA_TYPE_F. 
	 */
	public final static short CARD_INFO_CURRENT_PROTOCOL_DATA_TYPE_F=(short)0x06;
	
	/**
	 * The Card GPCLentry update counter.  
	 * Each time a <code>{@link GPCLRegistryEntry}</code> information is updated this counter is incremented.
	 * Used to synchronize the CRS/CREL application list.
	 */
	public final static short CARD_INFO_COUNTER_UPDATE=0x07;
	
	/**
	 * Retrieve the OPEN's conctactless parameters.
	 *  
	 * @param buffer where requested information shall be written
	 * @param offset within <code>buffer</code>, where requested information shall be written
	 * @param info any CARD_INFO_XX constant.
	 * 
	 * @return (<code>offset</code> + length of data written in <code>buffer</code>)
	 *
	 * @throws  <code>ArrayIndexOutOfBoundsException</code>
	 * if storing the Application Information bytes would cause access 
	 * outside array bounds or the <code>offset</code> is negative.
	 * 
	 * @throws <code>NullPointerException</code>
	 * if <code>buffer</code> is <code>null</code>
	 */
	public static short getCardCLInfo(byte[] buffer,short offset,short info){
		return offset;
	}
	
	/**
	 * This constant is used with  <code>{@link #setCommunicationInterface(short, boolean)}</code>
	 * to manage a ISO14443 based communication interface. 
	 */
	public final static short GPCL_INTERFACE_ISO14443=(short)0x01;
	/**
	 * This method allows switching ON or switching OFF the ISO 14443 interface at GlobalPlatform card level.
	 * The technical implementation for switching an interface is configuration dependent.
	 * 
	 * @param sInterface the interface identifier: 	GPCL_INTERFACE_ISO14443.
	 * 
	 * @param onOff <code>true</code> to switch ON, <code>false</code> to switch OFF
	 * 
	 * @exception <code>ISOException</code> with reason
	 * <ul>
	 * <li><code>ISO7816.SW_CONDITION_NOT_STATISFIED</code> if the caller has not 
	 * {@link GPCLRegistryEntry#PRIVILEGE_CONTACTLESS_ACTIVATION} when managing the {@link #GPCL_INTERFACE_ISO14443}
	 * <li><code>ISO7816.SW_WRONG_DATA</code>if sInterface identifier does not exist.
	 * </ul>
	 */
	public static void setCommunicationInterface(short sInterface,boolean onOff){
		
	}
}
