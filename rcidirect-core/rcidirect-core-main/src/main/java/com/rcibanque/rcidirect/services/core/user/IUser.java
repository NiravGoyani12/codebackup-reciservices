package com.rcibanque.rcidirect.services.core.user;

import java.io.Serializable;
import java.util.Map;

/**
 * User
 * <ul>
 * <li>provides the user details (login and password (for authentication), etc.)</li>
 * <li>provides the profile and related information</li>
 * <li>provides the preferences (language code, etc.)</li>
 * </ul>
 */
public interface IUser {

	/**
	 * @return User active
	 */
	Boolean getEnabled();

	/**
	 * @return User actor code (ka_acteur)
	 */
	String getUserActorCode();

	/**
	 * @return User code (cc_util)
	 */
	Integer getUserUtilCode();

	/**
	 * @return User login
	 */
	String getUsername();

	/**
	 * @return User password
	 */
	String getPassword();

	/**
	 * @return User last name
	 */
	String getLastName();

	/**
	 * @return User first name
	 */
	String getFirstName();

	/**
	 * @return User email
	 */
	String getEmail();

	/**
	 * @return User profile code (kcd_profil)
	 */
	Integer getProfileCode();

	/**
	 * @return User profile group code (kcd_profgrp)
	 */
	Integer getProfileGroup();

	/**
	 * @return User profile label
	 */
	String getProfileLabel();

	/**
	 * @return User profile role code
	 */
	Integer getProfileRoleCode();

	/**
	 * @return True if user is a head office user
	 */
	Boolean getHeadOffice();

	/**
	 * @return 1 if Head office else 2
	 */
	Integer getProviderTypeCode();

	/**
	 * @return User preferences (language code, etc.)
	 */
	IUserPreferences getPreferences();

	/**
	 * @return User extra attributes
	 */
	Map<String, Serializable> getExtraAttributes();


	void apply(IUser pUser);

}
