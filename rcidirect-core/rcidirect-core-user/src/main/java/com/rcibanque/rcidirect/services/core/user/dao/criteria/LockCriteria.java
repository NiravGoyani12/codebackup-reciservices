package com.rcibanque.rcidirect.services.core.user.dao.criteria;

import java.util.List;

import com.rcibanque.rcidirect.services.core.dao.criteria.Criteria;

public class LockCriteria extends Criteria {

	private static final long serialVersionUID = -8141984435988964660L;


	private String _lockCode;

	private List<String> _lockCodes;

	private Integer _userUtilCode;


	public String getLockCode() {
		return _lockCode;
	}

	public void setLockCode(String pLockCode) {
		_lockCode = pLockCode;
	}

	public List<String> getLockCodes() {
		return _lockCodes;
	}

	public void setLockCodes(List<String> pLockCodes) {
		_lockCodes = pLockCodes;
	}

	public Integer getUserUtilCode() {
		return _userUtilCode;
	}

	public void setUserUtilCode(Integer pUserUtilCode) {
		_userUtilCode = pUserUtilCode;
	}

}
