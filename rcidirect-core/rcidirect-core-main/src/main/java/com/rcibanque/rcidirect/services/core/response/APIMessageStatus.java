package com.rcibanque.rcidirect.services.core.response;

/**
 * REST service call response message status
 */
public enum APIMessageStatus {

	/**
	 * User message - information
	 */
	INFO,

	/**
	 * User message - warning
	 */
	WARNING,

	/**
	 * User message - error
	 */
	ERROR,

	/**
	 * <b>Internal</b> message not provided to the user
	 */
	LOG,

}
