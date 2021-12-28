package com.rcibanque.rcidirect.services.core.user;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rcibanque.rcidirect.services.core.domain.DTO;

public final class User extends DTO implements IUser {

	private static final long serialVersionUID = -304602503226721243L;


	@JsonIgnore
	private Boolean _enabled;

	private String _username;

	@JsonIgnore
	private String _password;

	private Boolean _passwordExpired;

	@JsonIgnore
	private Date _lastLoginDate;

	@JsonIgnore
	private Boolean _lastLoginAttemptFailed;

	private Integer _profileCode;

	@JsonIgnore
	private Integer _profileGroup;

	@JsonIgnore
	private String _profile;

	private String _profileLabel;

	private Integer _profileRoleCode;

	@JsonIgnore
	private Integer _providerTypeCode;

	private Boolean _headOffice;

	private String _firstName;

	private String _lastName;

	private String _userActorCode;

	@JsonIgnore
	private String _email;

	@JsonIgnore
	private Integer _userUtilCode;

	private UserPreferences _userPreferences;

	@JsonIgnore
	private Map<String, Serializable> _extraAttributes;


	public User() {
		super();
		_userPreferences = new UserPreferences();
		_extraAttributes = new HashMap<>();
	}


	@Override
	public Boolean getEnabled() {
		return _enabled;
	}

	public void setEnabled(Boolean pEnabled) {
		_enabled = pEnabled;
	}

	@Override
	public String getUsername() {
		return _username;
	}

	public void setUsername(String pUsername) {
		this._username = pUsername;
	}

	@Override
	public String getPassword() {
		return _password;
	}

	public void setPassword(String pPassword) {
		this._password = pPassword;
	}

	public Boolean getPasswordExpired() {
		return _passwordExpired;
	}

	public void setPasswordExpired(Boolean pPasswordExpired) {
		this._passwordExpired = pPasswordExpired;
	}

	public Date getLastLoginDate() {
		return _lastLoginDate;
	}

	public void setLastLoginDate(Date pLastLoginDate) {
		_lastLoginDate = pLastLoginDate;
	}

	public Boolean getLastLoginAttemptFailed() {
		return _lastLoginAttemptFailed;
	}

	public void setLastLoginAttemptFailed(Boolean pLastLoginAttemptFailed) {
		_lastLoginAttemptFailed = pLastLoginAttemptFailed;
	}

	@Override
	public Integer getProfileCode() {
		return _profileCode;
	}

	public void setProfileCode(Integer pProfileCode) {
		_profileCode = pProfileCode;
	}

	@Override
	public Integer getProfileGroup() {
		return _profileGroup;
	}

	public void setProfileGroup(Integer pProfileGroup) {
		_profileGroup = pProfileGroup;
	}

	public String getProfile() {
		return _profile;
	}

	public void setProfile(String pProfile) {
		this._profile = pProfile;
	}

	@Override
	public String getProfileLabel() {
		return _profileLabel;
	}

	public void setProfileLabel(String pProfileLabel) {
		_profileLabel = pProfileLabel;
	}

	@Override
	public Integer getProfileRoleCode() {
		return _profileRoleCode;
	}

	public void setProfileRoleCode(Integer pProfileRoleCode) {
		_profileRoleCode = pProfileRoleCode;
	}

	@Override
	public Boolean getHeadOffice() {
		return _headOffice;
	}

	public void setHeadOffice(Boolean pHeadOffice) {
		_headOffice = pHeadOffice;
	}

	@Override
	public Integer getProviderTypeCode() {
		return _providerTypeCode;
	}

	public void setProviderTypeCode(Integer pProviderTypeCode) {
		_providerTypeCode = pProviderTypeCode;
	}

	@Override
	public String getFirstName() {
		return _firstName;
	}

	public void setFirstName(String pFirstName) {
		_firstName = pFirstName;
	}

	@Override
	public String getLastName() {
		return _lastName;
	}

	public void setLastName(String pLastName) {
		_lastName = pLastName;
	}

	@Override
	public String getUserActorCode() {
		return _userActorCode;
	}

	public void setUserActorCode(String pUserActorCode) {
		_userActorCode = pUserActorCode;
	}

	@Override
	public String getEmail() {
		return _email;
	}

	public void setEmail(String pEmail) {
		_email = pEmail;
	}

	@Override
	public Integer getUserUtilCode() {
		return _userUtilCode;
	}

	public void setUserUtilCode(Integer pUserUtilCode) {
		_userUtilCode = pUserUtilCode;
	}


	@Override
	public IUserPreferences getPreferences() {
		return _userPreferences;
	}


	@Override
	public Map<String, Serializable> getExtraAttributes() {
		return _extraAttributes;
	}


	@Override
	public void apply(IUser pUser) {

		if(pUser.getProfileRoleCode() != null) {
			setProfileRoleCode(pUser.getProfileRoleCode());
		}

		if(pUser.getEnabled() != null) {
			setEnabled(pUser.getEnabled());
		}
	}


}
