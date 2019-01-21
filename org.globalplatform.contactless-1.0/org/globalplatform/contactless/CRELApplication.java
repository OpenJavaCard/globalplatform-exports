package org.globalplatform.contactless;


import javacard.framework.Shareable;

/**
 * A CREL Application is an application associated to one or several
 * Applications.  <p>
 *
 * This interface shall be implemented by CREL applications so that they can be
 * notified by the OPEN of changes occurring to their associated contactless
 * applets (parameters and/or life cycle).  <p>
 * 
 * The CREL application shall expose the <code>CRELApplication</code> interface object(s) through 
 * {@link javacard.framework.Applet#getShareableInterfaceObject(javacard.framework.AID, byte)}
 * only if the client AID is null and the parameter is set to {@link GPCLSystem#GPCL_CREL_APPLICATION}.
 * 
 * A CREL Application shall not be notified when it is the originator of an event.
 * 
 */
public interface CRELApplication extends Shareable 
{
	/**
	 * Notifies the CREL Application that the applet associated with specified
	 * GPCLRegistryEntry was updated according to specified event.
	 * <p>
	 * In case of EVENT_DELETED the target GPCLRegistryEntry shall only be used to retrieve the AID;
	 * other methods will fail and throw an <code>javacard.framework.SystemException</code>
	 * <p>
	 * If the Applet implementing the <code>CRELApplication</code> interface returns 
	 * from the notification and initiated transaction in progress,then 
	 * the OPEN automatically aborts the transaction. 
	 * 
	 * @param target the GPCLRegistryEntry of the Application whose state or data were updated
	 * @param event event ({@link CLAppletEvent}) affecting the <code>target</code> application
	 * 
	 * 
	 */
	public void notifyCLEvent(GPCLRegistryEntry target, short event);

}
