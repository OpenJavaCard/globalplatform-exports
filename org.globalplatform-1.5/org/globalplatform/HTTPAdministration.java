package org.globalplatform;

import javacard.framework.Shareable;
/**
 * This interface handles an HTTP administration session triggering request.
 * <p>
 * The object implementing this interface shall belong to the JCRE to have 
 * access to any object. 
 * The HTTPAdministration service shall verify that the method parameters are 
 * within bounds and all objects passed in as parameters are accessible from 
 * the caller’s context.
 * @since export file version 1.3
 */
public interface HTTPAdministration extends Shareable {
	/**
	 * Request an administration session.
	 * <p> 
	 * The Security Domain of the calling application will handle the PSK TLS security of the communication.
	 * <p>
	 * The calling application will be notified of the request execution if it implements the interface {@link HTTPReportListener}
	 * <p>
	 * @param triggeringParameters this buffer contains the administration session triggering parameters
	 * 		  as defined in Amendment B. 
	 * 		  
	 * @param offset offset within triggeringParameters
	 * @param length length of parameters within triggeringParameters
	 * 
	 * @exception <code>java.lang.SecurityException</code> if <code>triggeringParameters</code> is not accessible in the caller’s context.
	 * @exception <code>java.lang.NullPointerException</code> if <code>triggeringParameters</code> is equal to null.
	 * @exception <code>java.lang.ArrayIndexOutOfBoundsException</code> if <code>offset</code> or <code>length</code> would lead to access outside array bounds.
	 * @exception <code>javacard.framework.ISOException with the following reason codes:
	 * <ul>
	 * 	<li> <code>SW_WRONG_DATA</code> if data within triggeringParameters are not correctly formatted.
	 * 	<li> <code>SW_CONDITIONS_NOT_SATISFIED</code> if operation could not be processed (for example if no SCP 81 support is possible for the calling application).
	 * </ul>
	*  
	 */
	void requestHTTPAdministrationSession (
			byte[] triggeringParameters,
			short offset,
			short length);
}
