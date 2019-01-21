package org.globalplatform.contactless;


/**
 * Definition of contactless events.
 *
 * These events shall be used to notify current state, or state change request in the following methods:<ul>
 * <li>{@link CRSApplication#processCLRequest(org.globalplatform.GPRegistryEntry, GPCLRegistryEntry, short)}
 * <li>{@link CRSApplication#notifyCLEvent(GPCLRegistryEntry, short)}
 * <li>{@link CRELApplication#notifyCLEvent(GPCLRegistryEntry, short)}
 * <li>{@link CLApplet#notifyCLEvent(short)}
 * </ul>
 */
public interface CLAppletEvent {

	/**
	 * Constant notifying that the applet is now NON ACTIVATABLE on the contactless interface.
	 */
	public static final short EVENT_NON_ACTIVATABLE = (short) 0x81;
	/**
	 * Constant notifying that the applet is now ACTIVATED on the contactless interface.
	 */
	public static final short EVENT_ACTIVATED = (short) 0x02;
	/**
	 * Constant notifying that the applet is now DEACTIVATED on the contactless interface.
	 */
	public static final short EVENT_DEACTIVATED = (short) 0x82;
	/**
	 * Constant notifying that the URI associated with the applet has changed.
	 */
	public final static short EVENT_URI=(short)0x04;
	/**
	 * Constant notifying that discretionary data has changed.
	 */
	public final static short EVENT_DISCRETIONARY_DATA=(short)0x05;
	/**
	 * Constant notifying that the LOGO of the applet has changed.
	 */
	public final static short EVENT_LOGO=(short)0x06;
	/**
	 * Constant notifying that applet's Type A Protocol Data have changed.
	 */
	public final static short EVENT_PROTOCOL_DATA_TYPE_A=(short)0x07;
	/**
	 * Constant notifying that applet's Type B Protocol Data have changed.
	 */
	public final static short EVENT_PROTOCOL_DATA_TYPE_B=(short)0x08;
	/**
	 * Constant notifying that applet's Type F Protocol Data have changed.
	 */
	public final static short EVENT_PROTOCOL_DATA_TYPE_F=(short)0x09;
	/**
	 * Constant notifying that applet's supported protocol types have changed.
	 */
	public final static short EVENT_IMPLICIT_SELECTION_PROTOCOLS=(short)0x0B;
	
	/**
	 * Constant notifying that applet's Family Identifier has changed.
	 */
	public final static short EVENT_FAMILY_IDENTIFIER=(short)0x0C;
	
	/**
	 * Constant notifying that continuous processing is now enabled.
	 */
	public final static short EVENT_CONTINUOUS_PROCESS_ON=(short)0x0D;
	/**
	 * Constant notifying that continuous processing is now disabled.
	 */
	public final static short EVENT_CONTINUOUS_PROCESS_OFF=(short)0x8D;

	/**
	 * Constant notifying that a CREL application has been added to a CREL Application AID List
	 */
	public final static short EVENT_CREL_ADDED=(short)0x0E;
	/**
	 * Constant notifying that a CREL application has been removed from a CREL Application AID List
	 */
	public final static short EVENT_CREL_REMOVED =(short) 0x8E;
	/**
	 * Constant notifying that the applet has joined a Group.
	 */
	public final static short EVENT_GROUP_MEMBER_ADDED=(short)0x0F;
	/**
	 * Constant notifying that the applet has left a Group.
	 */
	public final static short EVENT_GROUP_MEMBER_REMOVED=(short)0x8F;
	/**
	 * Constant notifying that the applet has been installed but not selectable.
	 */
	public static final short EVENT_INSTALLED = (short) 0x11;
	/**
	 * Constant notifying that the applet is selectable..
	 */
	public static final short EVENT_SELECTABLE = (short) 0x12;
	/**
	 * Constant notifying that the applet was locked.
	 */
	public static final short EVENT_LOCKED = (short) 0x13;
	/**
	 * Constant notifying that the applet was unlocked.
	 */
	public static final short EVENT_UNLOCKED = (short) 0x93;
	/**
	 * Constant notifying that the applet was deleted.
	 */
	public static final short EVENT_DELETED = (short) 0x14;
	

}
