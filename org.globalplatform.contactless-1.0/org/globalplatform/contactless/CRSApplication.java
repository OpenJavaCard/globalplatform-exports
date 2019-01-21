package org.globalplatform.contactless;

import org.globalplatform.GPRegistryEntry;

/**
 * This interface allows processing state change requests for applets
 * participating to the contactless front end.
 * <p>
 * The CRS application shall expose the <code>CRSApplication</code> interface object(s) through 
 * {@link javacard.framework.Applet#getShareableInterfaceObject(javacard.framework.AID, byte)}
 * only if the client AID is null, and the parameter is set to {@link GPCLSystem#GPCL_CRS_APPLICATION}.
 */
public interface CRSApplication extends CRELApplication{

	/**
	 * Called by the OPEN for each activation requested by an Application that does not have
	 * the Self-Activation Privilege.
	 * <p>
	 * The CRS Application shall use <code>GPCLRegistryEntry</code> to change the life
	 * cycle state of the requesting applet.
	 * <p>
	 * If the Applet implementing the <code>CRSApplication</code> interface returns
	 * from the notification and initiated transaction in progress,then
	 * the OPEN automatically aborts the transaction. 
	 *
	 * @param requester the GPRegistryEntry of the applet requesting the change
	 * @param target   the GPCLRegistryEntry of the contactless applet to update
	 * @param event    requested change: <ul>
     *   <li>{@link CLAppletEvent#EVENT_ACTIVATED}
      * </ul>
	 * 
	 * @return <code>true</code> if transition success, <code>false</code> otherwise.
	 */
	public boolean processCLRequest(GPRegistryEntry requester, GPCLRegistryEntry target, short event);

}