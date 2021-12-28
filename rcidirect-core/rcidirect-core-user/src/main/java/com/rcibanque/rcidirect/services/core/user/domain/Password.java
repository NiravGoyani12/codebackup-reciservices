package com.rcibanque.rcidirect.services.core.user.domain;

import com.rcibanque.rcidirect.services.core.domain.DTO;

public class Password extends DTO {

	private static final long serialVersionUID = 5948786691717790360L;


	private String _userId;

	private String _newKey;

	private String _newKeyRepeat;

	private String _password;


	public String getUserId() {
		return _userId;
	}

	public void setUserId(String pUserId) {
		_userId = pUserId;
	}

	public String getNewKey() {
		return _newKey;
	}

	public void setNewKey(String pNewKey) {
		_newKey = pNewKey;
	}

	public String getNewKeyRepeat() {
		return _newKeyRepeat;
	}

	public void setNewKeyRepeat(String pNewKeyRepeat) {
		_newKeyRepeat = pNewKeyRepeat;
	}

	public String getPassword() {
		return _password;
	}

	public void setPassword(String pPassword) {
		_password = pPassword;
	}

}
