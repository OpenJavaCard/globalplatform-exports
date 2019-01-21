package org.globalplatform;

import javacard.framework.Shareable;
/**
 * An applet may implement this interface in order to receive notification on HTTPAdministration request processing.
 * 
 * <p>
 * Such an applet shall expose the <code>HTTPReportListener</code> interface object(s) through 
 * {@link javacard.framework.Applet#getShareableInterfaceObject(javacard.framework.AID, byte)}
 * only if the client AID is null, and the parameter is set to {@link GPSystem#FAMILY_HTTP_REPORT}.
 * </p>
 * @since export file version 1.3
 */

public interface HTTPReportListener extends Shareable {
	/**
	 * Constant notifying that HTTPAdministration session End successfully
	 */
	public final static short HTTP_SESSION_NO_ERROR=0x0001;
	/**
	 * Constant notifying that the HTTPAdministartion session fails. 
	 * The retry policy of the session is exhausted and the administration session request is aborted.
	 */
	public final static short HTTP_SESSION_ERROR=(short) 0x8001; 

	/**
	 * Notifies the applet that the requested HTTPAdministrationSession has been successfully completed or not.
	 * <p>
	 * The OPEN notify the Applet when the HTTPAdministrationSession end or when retry policy is exhausted.
	 * 
	 * @param status With following meaning
	 *   <li>HTTP_SESSION_NO_ERROR:  HTTPAdministration session End</li>
	 *   <li>HTTP_SESSION_ERROR: retry policy of the HTTPAdministration session is exhausted</li>
	 */
	public void httpAdministationSessionReport(short status);

}