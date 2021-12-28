package com.rcibanque.rcidirect.services.core.user.domain;

import java.util.Date;

import com.rcibanque.rcidirect.services.core.domain.DTO;

public class Lock extends DTO {

	private static final long serialVersionUID = 789113027335474005L;


	private String _lockCode;

	private Integer _userUtilCode;

	private Date _accessDate;


	public String getLockCode() {
		return _lockCode;
	}

	public void setLockCode(String pLockCode) {
		_lockCode = pLockCode;
	}

	public Integer getUserUtilCode() {
		return _userUtilCode;
	}

	public void setUserUtilCode(Integer pUserUtilCode) {
		_userUtilCode = pUserUtilCode;
	}

	public Date getAccessDate() {
		return _accessDate;
	}

	public void setAccessDate(Date pAccessDate) {
		_accessDate = pAccessDate;
	}


}
