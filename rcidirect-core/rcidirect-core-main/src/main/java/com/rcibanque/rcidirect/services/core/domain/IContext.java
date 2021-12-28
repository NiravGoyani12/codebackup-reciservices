package com.rcibanque.rcidirect.services.core.domain;

import java.util.Locale;

import com.rcibanque.rcidirect.services.core.response.IMessages;
import com.rcibanque.rcidirect.services.core.user.IUser;

/**
 * Context object that gives access to:
 * <ul>
 * <li>User details</li>
 * <li>Messages</li>
 * </ul>
 */
public interface IContext {


	/**
	 * @return Connected user's details
	 */
	IUser getUser();

	/**
	 * @return Connected user's language code
	 */
	Integer getLanguageCode();

	/**
	 * @return Connected user's locale
	 */
	Locale getLocale();

	/**
	 * @return Access to current context messages
	 */
	IMessages getMessages();

}
