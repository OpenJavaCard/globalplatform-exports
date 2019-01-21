package org.globalplatform.contactless;

import javacard.framework.AID;
import javacard.framework.ISOException;

import org.globalplatform.GPRegistryEntry;

/**
 * Defines methods that manage the GlobalPlatform registry extension on contactless interface
 * for an applet.
 * <p>
 * The caller shall use <code>{@link GPCLSystem#getGPCLRegistryEntry(AID)}</code> to
 * retrieve an OPEN owned interface object.
 * <p>
 * All GPRegistryEntry of Application installed will be extended by this interface.
 * <p>
 * When the application represented by a <code>{@link GPCLRegistryEntry}</code> object is deleted,
 * and Logically Deleted with References, then this <code>{@link GPCLRegistryEntry}</code> object shall be
 * disabled and all its methods shall throw an <code>javacard.framework.SystemException</code> 
 * with reason code <code>SystemException.ILLEGAL_USE</code>.
 * <p>
 * The OPEN shall ensure that this <code>{@link GPCLRegistryEntry}</code> object can never be re-enabled, 
 * even if an application with the same AID as the deleted application 
 * (previously bound to this <code>{@link GPCLRegistryEntry}</code> object) is installed.
 * An application holding a reference to a disabled <code>{@link GPCLRegistryEntry}</code> object 
 * should release it, as it has become useless and will only throw exceptions.
 * <p>
 * Note: Developer shall pay attention of {@link CRELApplication} , {@link CLApplet} and {@link CRSApplication} 
 * notifications when using the service interface. Thus it is strongly recommended do not call 
 * service provided by this interface under transaction. 
 * 
 *  
 * @see GPCLSystem#getGPCLRegistryEntry(AID)
 */
public interface GPCLRegistryEntry extends GPRegistryEntry 
{
	/**
	 * Contactless Activation privilege (18).
	 * This privilege allows <ul>
	 * <li> Enable/Disable ISO 14443 interface.
	 * <li> Activate/Deactivate an applet on the ISO 14443 interface .</li>
	 * <li> Managing the GlobalPlatform Registry order (for partial selection).</li>
	 * <li> Managing the GlobalPlatform Volatile Priority List.
	 * </ul>
	 */
	public final static byte PRIVILEGE_CONTACTLESS_ACTIVATION = (byte) 18;

	/**
	 * Self Activation privilege (19). This privilege
	 * allows an Applet to transition to ACTIVATED on the Contactless interface.
	 */
	public final static byte PRIVILEGE_CONTACTLESS_SELF_ACTIVATION = (byte) 19;

	/**
	 * Constant used to change the state of an applet to NON ACTIVATABLE over the
	 * contactless interface. When applet is NON ACTIVATABLE, it is also
	 * implicitly DEACTIVATED.
	 */
	public static final byte STATE_CL_NON_ACTIVATABLE = (byte) 0x80;

	/**
	 * Constant used to activate an applet on the contactless interface.
	 * An applet that is currently NON ACTIVATABLE can not be ACTIVATED.
	 */
	public static final byte STATE_CL_ACTIVATED = (byte) 0x01;

	/**
	 * Constant used to deactivate an applet on the contactless interface.
	 */
	public static final byte STATE_CL_DEACTIVATED = (byte) 0x00;

	/**
	 * Changes the contactless life cycle state of the applet associated with
	 * this <code>GPCLRegistryEntry</code>.
	 * <p>
	 * When this entry is a group member and is in {@link #STATE_CL_NON_ACTIVATABLE} state
	 * then this entry follows the activation state of the Head Application when
	 * state transition of this entry is to {@link #STATE_CL_ACTIVATED} or
	 * to {@link #STATE_CL_DEACTIVATED}.
	 * <p>
	 * Only the application associated to this entry may transition to the 
	 * {@link #STATE_CL_NON_ACTIVATABLE} state.
	 * <p>
	 * The application associated to this entry, or
	 * the Application with the {@link #PRIVILEGE_CONTACTLESS_ACTIVATION} privilege, or
	 * CREL Applications registered in the investigated applet's CREL list may transition 
	 * this entry to the {@link #STATE_CL_DEACTIVATED}
	 * <p>
	 * When the state transition of this entry is to {@link #STATE_CL_ACTIVATED}, the caller must have
	 * <ul> 
	 * <li>the {@link #PRIVILEGE_CONTACTLESS_SELF_ACTIVATION} privilege or
	 * <li>the {@link #PRIVILEGE_CONTACTLESS_ACTIVATION} privilege 
	 * <li>otherwise
	 * <ul>
	 * <li>The OPEN will locate the CRSApplication.</li>
	 * <li>The OPEN will call the <code>{@link CRSApplication#processCLRequest(GPRegistryEntry, GPCLRegistryEntry, short)}</code></li>
	 * <li>The CRSApplication shall use <code>{@link GPCLRegistryEntry#setCLState(byte)}</code>
	 * to change the application state on the contactless interface.</li>
	 * </ul> 
	 * </ul> 
	 * <p> 
	 * The OPEN is responsible to notify all Application(s) implementing {@link CRELApplication#notifyCLEvent(GPCLRegistryEntry, short)}.
	 * If the Application associated to this entry, implements the interface {@link CLApplet} and
	 * the Application is not at the origin of the request then
	 * OPEN shall notify the application by calling {@link CLApplet#notifyCLEvent(short)}
	 * 
	 * <p>
	 * It shall be supported to change the activation state of a contactless
	 * applet irrespective of the state of the contactless front end in the handset.
	 * When the contactless functionality is enabled the OPEN shall ensure that
	 * the contactless front end is provisioned so that it reflects the
	 * configuration of the contactless applets in the OPEN.
	 * <p>
	 * 
	 * @param state requested Availability state on Contactless interface
	 * The possible state are
	 * 	<li>{@link #STATE_CL_ACTIVATED}
	 * 	<li>{@link #STATE_CL_DEACTIVATED}
	 * 	<li>{@link #STATE_CL_NON_ACTIVATABLE}
	 * 
	 * @return the resulting Availablity state on Contactless interface. 
	 * 
	 * @throws ISOException with reason
	 * <ul>
	 * <li><code>ISO7816.SW_CONDITIONS_NOT_SATISFIED</code> if
	 * the caller does not have enough privileges.
	 * <li><code>ISO7816.SW_WRONG_DATA</code> if the application
	 * cannot be activated on the contactless interface because of
	 * conflicting RF parameters.
	 * <li><code>ISO7816.SW_CONDITIONS_NOT_SATISFIED</code> 
	 * if the requested Availability state transition is not valid.
	 * </ul>
	 * 
	 * @see GPRegistryEntry
	 */
	public byte setCLState(byte state);

	/**
	 * This method returns the contactless life cycle state of application.
	 * 
	 * @return byte value of contactless state
	 * 		<li>{@link #STATE_CL_ACTIVATED}
	 * 		<li>{@link #STATE_CL_DEACTIVATED}
	 * 		<li>{@link #STATE_CL_NON_ACTIVATABLE}
	 */
	public byte getCLState();

	/**
	 * The requested information is an URI. 
	 */
	public final static short INFO_URI=(short)0x04;
	/**
	 * The requested information is discretionary data.
	 */
	public final static short INFO_DISCRETIONARY_DATA=(short)0x05;
	/**
	 * The requested information is a LOGO. 
	 */
	public final static short INFO_LOGO=(short)0x06;
	/**
	 * The requested information is the PROTOCOL_DATA_TYPE_A. 
	 */
	public final static short INFO_PROTOCOL_DATA_TYPE_A=(short)0x07;
	/**
	 * The requested information is the PROTOCOL_DATA_TYPE_B. 
	 */
	public final static short INFO_PROTOCOL_DATA_TYPE_B=(short)0x08;
	/**
	 * The requested information is the PROTOCOL_DATA_TYPE_F. 
	 */
	public final static short INFO_PROTOCOL_DATA_TYPE_F=(short)0x09;
	
	/**
	 * The Application family identifier is a short value.
	 * <ul>
	 * <li>MSB byte 1 set to 00 the LSB byte holds the AFI as defined 
	 * in table 12 of ISO/IEC 14443-3. 
	 * <li>Other values of are reserved for proprietary use.
	 */
	public final static short INFO_FAMILY_IDENTIFIER=(short)0x0B;
	
	/**
	 * The supported protocol Type A, Type B or / and Type F.
	 *   
	 * <p>Encoding of this information is a sequence of bytes 
	 * representing the value(s) of one or more of the following constants.
	 * <li>{@link #IMPLICIT_SELECTION_TYPE_A}
	 * <li>{@link #IMPLICIT_SELECTION_TYPE_B}
	 * <li>{@link #IMPLICIT_SELECTION_TYPE_F}
	 * 
	 * If the sequence is empty, the application can be implicity selected 
	 * using any of these protocol types (see definition of TLV "Assigned 
	 * Protocol for Implicit Selection").
	 */
	public final static short INFO_IMPLICIT_SELECTION_PROTOCOLS=(short)0x0C;
	/**
	 * The Application continuous processing is a Byte value 1 Continuous Processing 0 Interleave Processing 
	 */
	public final static short INFO_CONTINUOUS_PROCESS=(short)0x0D;
	/**
	 * The GPCLentry update counter.  
	 * Each time a <code>{@link GPCLRegistryEntry}</code> information is updated this counter is incremented.
	 * Used to synchronize the CRS/CREL application list.
	 */
	public final static short INFO_COUNTER_UPDATE=(short)0x0E;
	/**
	 * The GPCLRegistryEntry display on off requirement. 
	 * Used to know if the application can work in display off mode.
	 * <li>Byte value 1 can work when Display OFF 
	 * <li>Byte value 0 can not work when display OFF
	 */
	public final static short INFO_DISPLAY_REQUIREMENT=(short)0x0F;

	/**
	 *  Constant indicating that the application supports contactless protocol Type A for implicit selection
	 */
	public static final byte IMPLICIT_SELECTION_TYPE_A = (byte)0x81;

	/**
	 *  Constant indicating that the application supports contactless protocol Type B for implicit selection
	 */
	public static final byte IMPLICIT_SELECTION_TYPE_B = (byte)0x82;

	/**
	 *  Constant indicating that the application supports contactless protocol Type F for implicit selection
	 */
	public static final byte IMPLICIT_SELECTION_TYPE_F = (byte)0x84;

	/**
	 * Returns the contents of the Application Information entry in the GlobalPlatform registry.
	 * 
	 * @param buffer where requested information shall be written. For the coding of the data
	 * see the documentation of the constants INFO_XX.
	 * 
	 * @param offset within <code>buffer</code>, where requested information shall be written.
	 * 
	 * @param info is any constant with name INFO_XX which is defined in this interface.
	 * 
	 * @return (<code>offset</code> + length of data written in <code>buffer</code>) 
	 *
	 * @throws <code>ISOException</code> with reason
	 * <ul>
	 * <li> <code>ISO7816.SW_CONDITION_NOT_SATISFIED</code> if the caller 
	 * <ul>
	 * <li>is not the Application itself, and
	 * <li>does not have the {@link #PRIVILEGE_GLOBAL_REGISTRY} privilege, and
	 * <li>is not a Security Domain that is directly or indirectly associated to the 
	 *     Application identified by this entry, and
	 * <li>is not a CREL Application registered in the CREL list of the Application
	 *     identified by this entry.
	 * </ul>
	 * <li> <code>ISO7816.SW_WRONG_DATA</code> if the value of <code>info</code> is unknown.
	 * <li> <code>ISO7816.SW_RECORD_NOT_FOUND</code> if the info is not present.
	 * </ul>
	 * @throws <code>ArrayIndexOutOfBoundsException</code>
	 *             if storing the Application Information bytes would cause
	 *             access outside array bounds or the <code>offset</code> is
	 *             negative.
	 * @throws <code>NullPointerException</code>
	 *             if <code>buffer</code> is <code>null</code>
	 */
	public short getInfo(byte[] buffer, short offset, short info);
	
	/**
	 * Update the contents of the Information associated to this <code>{@link GPCLRegistryEntry}</code>.
	 * 
	 * @param buffer contains the updated information. For the coding of the data
	 * see the documentation of the constants INFO_XX.
	 * @param offset within <code>buffer</code>, where updated information can be found
	 * @param length of the updated information
	 * @param info is any constant with name INFO_XX which is defined in this interface.
	 *            
	 * @return (<code>offset</code> + <code>length</code>)
	 * 
	 * @throws <code>ISOException</code> with reason
	 *  <ul>
	 *  <li> <code>ISO7816.SW_CONDITION_NOT_SATISFIED</code> if the caller is not
	 *    <ul>
	 *    <li>the Associated Security Domain
	 *    <li>or the Application itself</li>
	 *    </ul>
	 *  <li> <code>ISO7816.SW_WRONG_DATA</code> if the value of <code>info</code> is unknown.
	 *  </ul>
	 *  
	 * @throws <code>ArrayIndexOutOfBoundsException</code>
	 *             if storing the Application Information bytes would cause
	 *             access outside array bounds or the <code>offset</code> is
	 *             negative.
	 * @throws <code>NullPointerException</code>
	 *             if <code>buffer</code> is <code>null</code>
	 */
	public short setInfo(byte[] buffer, short offset,short length, short info);

	/**
	 * This method allows iteration over the currently activated contactless applets that would
	 * conflict if the applet associated with this entry was activated.
	 * <p>
	 * 
	 * @param oEntry
	 *		<li>if <code>oEntry</code> is null, this method returns the first Entry representing the first conflicting application.
	 *		<li>If the list is empty, the method shall return null.
	 *		<li>If <code>oEntry</code> is not null, and represents a conflicting application, 
	 *		this method retrieves the next conflicting application following <code>oEntry</code>, otherwise it shall return null.
	 *		<li>If <code>oEntry</code> points to the last conflicting application, the method shall return null.
	 *
	 * @return the reference to the contactless application which would cause a
	 *         conflict. The value <code>null</code> is returned if no
	 *         contactless application is conflicting or the end of the list
	 *         has been reached.
	 * 
	 * @throws <code>ISOException</code> with reason
	 * <ul>
	 * <li> <code>ISO7816.SW_CONDITION_NOT_SATISFIED</code> if
	 * caller does not have the {@link #PRIVILEGE_CONTACTLESS_ACTIVATION} privilege.
	 * </ul>
	 */
	public GPCLRegistryEntry getNextConflictingApplication(GPCLRegistryEntry oEntry);
	
	/**
	 * Associates the <code>GPCLRegistryEntry</code> to which this <code>GPCLRegistryEntry</code> delegates its 
	 * information parameters (i.e. head application).
	 *
	 * @param oHead Head Application's AID
	 * 
	 * @throws <code>ISOException</code> with reason
	 * <ul>
	 * <li> <code>ISO7816.SW_CONDITION_NOT_SATISFIED</code>
	 * <ul>
	 * <li>if the caller is not the Application itself or
	 * <li>is associated to another Head Application or 
	 * <li>is a Head application
	 * </ul>
	 * </ul>
	 */
	public void joinGroup(AID oHead);

	/**
	 * Retrieves the list of application grouped with this Head Application.
	 * 
	 * @param oEntry search 
	 * 		<ul>
	 * 		<li>If <code>this</code> does not reference a Head Application, this method returns null.
	 * 		Otherwise,
	 *		<li>if <code>oEntry</code> is null, this method returns the first Entry representing the first member of the Application Group.
	 *		<li>If the list is empty, the method shall return null.
	 *		<li>If <code>oEntry</code> is not null, and is a member of the group, this method retrieves the next Entry following
	 *		<code>oEntry</code> belonging to the same Application Group, otherwise it shall return null.
	 *		<li>If <code>oEntry</code> points to the last member of the group, the method shall return null.
	 *		<li>The caller must have the <code>{@link #PRIVILEGE_CONTACTLESS_ACTIVATION}</code> Contactless Activation 
	 *    </ul>
	 * @return the Entry that is part of the group or null if no group or end of group list
	 * 
	 * @throws <code>ISOException</code> with reason
	 * <ul>
	 * <li><code>ISO7816.SW_CONDITION_NOT_SATISFIED</code>
	 * <ul>
	 * <li>if the caller has not {@link #PRIVILEGE_CONTACTLESS_ACTIVATION} and 
	 * <li>is not a CRELApplication associated to this entry</li>
	 * </ul>
	 * </ul>
	 */
	public GPCLRegistryEntry getNextGroupMember(GPCLRegistryEntry oEntry);
	
	/**
	 * This method is used to add an AID to the Group Authorization list.
	 * 
	 * @param baAID contains the AID value to manage
	 * @param offsetAID start offset of the AID value
	 * @param lengthAID length of the AID value
	 * 
	 * @throws <code>ISOException</code> with reason
	 * <ul>
	 * <li><code>ISO7816.SW_CONDITION_NOT_SATISFIED</code>
	 * <ul>
     * <li>if the caller application is already associated to a Head Application, or
	 * <li>if the caller application is neither the application itself or the associated Security Domain.
	 * </ul>
* </ul>
	 */
	public void addToGroupAuthorizationList(byte[]baAID, short offsetAID, short lengthAID);
	/**
	 * This method is used to remove an AID from the Group Authorization list.
	 * 
	 * @param baAID contains the AID value to removed
	 * @param offsetAID start offset of the AID value
	 * @param lengthAID length of the AID value
	 * 
	 * @throws <code>ISOException</code> with reason
	 * <li><code>ISO7816.SW_CONDITION_NOT_SATISFIED</code> if the caller application is 
	 * neither the application itself or the associated Security Domain.
	 */
	public void removeFromGroupAuthorizationList(byte[]baAID, short offsetAID, short lengthAID);

	/**
	 * Manage the GlobalPlatform registry order for partial selection priority.
	 * If this GPCLentry is a head of a group, group members are moved in the same 
	 * order.
	 * @param TopBottom 
	 * <ul>
	 * 	<li>true: the <code>GPRegistryEntry</code> of this entry, becomes 
	 * 	the first selected on partial selection.
	 * 	<li>false:the <code>GPRegistryEntry</code> of this entry becomes 
	 * 	the last selected on partial selection.
	 * </ul>
	 * 
	 * @throws <code>ISOException</code> with reason
	 * <ul>
	 * <li><code>ISO7816.SW_CONDITION_NOT_SATISFIED</code> if the caller has not {@link #PRIVILEGE_CONTACTLESS_ACTIVATION}</li>
	 * </ul>
	 */
	public void setPartialSelectionOrder(boolean TopBottom);
	
	/**
	 * Retrieve the list of CREL applications referenced by this <code>GPCLRegistryEntry</code>
	 * 
	 * @param oEntry search
	 * <ul> 
	 * 	<li>if <code>oEntry</code> is null, this method returns the first Entry representing the first <code>CRELApplication</code>.
	 * 	<li>If the list is empty, the method shall return null.
	 * 	<li>If <code>oEntry</code> is not null, and represents a <code>{@link CRELApplication} CRELApplication</code> reference by this <code>GPCLRegistryEntry</code>, 
	 * 	this method retrieves the next Entry following <code>oEntry</code> referenced by this <GPCLRegistryEntry</code>, otherwise it shall return null.
	 * 	<li>If <code>oEntry</code> points to the last CRELApplication's GPCLRegistryEntry, the method shall return null.
	 * </ul>
	 * 
	 * @return 
	 * <ul>
	 *	<li>next entry referencing a CREL application.
	 *	<li>null, if no more entry.
	 *  </ul>
	 * @throws <code>ISOException</code> with reason
	 * <ul>
	 * <li><code>ISO7816.SW_CONDITION_NOT_SATISFIED</code> if the caller has not {@link #PRIVILEGE_CONTACTLESS_ACTIVATION}</li>
	 * </ul>
	 */
	public GPCLRegistryEntry getNextCRELApplication(GPCLRegistryEntry oEntry);
	
	/**
	 * This method add an AID to the CREL Application list referenced by this GPCLRegistryEntry 
	 * 
	 * @param baAID contains the CREL Application's AID value to manage
	 * @param offsetAID start offset of the CREL Application's AID value
	 * @param lengthAID length of the AID value
	 * 
	 * @throws <code>ISOException</code> with reason
	 * <ul> 
	 * <li><code>ISO7816.SW_CONDITION_NOT_SATISFIED</code> if
	 * <ul>
	 * <li>is not the Security Domain directly associated with the applet being investigated, and
	 * <li>is not the investigated applet itself.
	 * </ul>
	 * </ul>
	 */
	public void addToCRELApplicationList(byte[]baAID, short offsetAID, short lengthAID);

	/**
	 * This method removes an AID from the CREL Application list referenced by this GPCLRegistryEntry 
	 * 
	 * @param baAID contains the CREL Application's AID value to manage
	 * @param offsetAID start offset of the CREL Application's AID value
	 * @param lengthAID length of the AID value
	 * 
	 * @throws <code>ISOException </code> with reason
	 * <ul> 
	 * <li><code>ISO7816.SW_CONDITIONS_NOT_SATISFIED</code> if
	 * <ul>
	 * <li>is not the Security Domain directly associated with the applet being investigated, and
	 * <li>is not the investigated applet itself.
	 * </ul>
	 * </ul>
	 */
	public void removeFromCRELApplicationList(byte[]baAID, short offsetAID, short lengthAID);
	
	/**
	 * Retrieve the list of applications that reference this CRELApplication's <code>GPCLRegistryEntry</code> 
	 * 
	 * @param oEntry search 
	 * <ul>
	 *	<li>if <code>oEntry</code> is null, this method returns the first Entry representing the first <code>GPCLRegistryEntry</code>, that references this  <code>CRELApplication</code>
	 *	<li>If the list is empty, the method shall return null.
	 *	<li>If <code>oEntry</code> is not null, and represents a <code>GPCLRegistryEntry</code> that references this  <code>CRELApplication</code>
	 *	this method retrieves the next Entry following <code>oEntry</code> referencing this <code>CRELApplication</code>, otherwise it shall return null.
	 *	<li>If <code>oEntry</code> points to the last CRELApplication's GPCLRegistryEntry, the method shall return null.
	 *	<li>The caller shall implement <code>CRELApplication</code> interface. 
	 *  </ul>
	 * @return 
	 * <ul>
	 * 	<li>next entry referencing this CRELApplication.
	 *	<li>null, if no more entry.
	 * </ul>
	 * @throws <code>ISOException</code> with reason
	 * <ul>
	 * <li><code>ISO7816.SW_CONDITION_NOT_STATISFIED</code> 
	 * if the caller does not implement <code>CRELApplication</code> interface.</li>
	 * </ul>
	 */
	public GPCLRegistryEntry getNextReferencingApplication(GPCLRegistryEntry oEntry);

	/**
	 * Does this {@link GPCLRegistryEntry} represents a group's Head.
	 * 
	 * @return <code>true</code> true if this Application is a Group Head, 
	 * false otherwise.
	 * 
	 * @throws <code>ISOException </code> with reason
	 * 	<li><code>ISO7816.SW_CONDITIONS_NOT_SATISFIED</code> if
	 *      the caller has not {@link GPRegistryEntry#PRIVILEGE_GLOBAL_REGISTRY}
	 */
	public boolean isGroupHead();
	/**
	 * Does this {@link GPCLRegistryEntry} represents a group's Member.
	 * 
	 * @return <code>true</code> is this Application is a if this Application is a Group Member, 
	 * 		   <code>false</code> if this is a standalone Application.
	 * 
	 * @throws <code>ISOException</code> with reason
	 * <ul>
	 * <li><code>ISO7816.SW_CONDITIONS_NOT_SATISFIED</code> if
	 * the caller has not {@link GPRegistryEntry#PRIVILEGE_GLOBAL_REGISTRY}
	 * </ul>
	 */
	public boolean isGroupMember();

}
