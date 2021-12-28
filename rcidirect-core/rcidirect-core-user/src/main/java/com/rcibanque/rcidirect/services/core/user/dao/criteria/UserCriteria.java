package com.rcibanque.rcidirect.services.core.user.dao.criteria;

import com.rcibanque.rcidirect.services.core.dao.criteria.Criteria;

public class UserCriteria extends Criteria {

	private static final long serialVersionUID = 5238926251619779946L;


	private String _userName;

	private String _userActorCode;

	private Integer _userUtilCode;

	private Boolean _handleDisabledUsers;


	public String getUserName() {
		return _userName;
	}

	public void setUserName(String pUserName) {
		_userName = pUserName;
	}


	public String getUserActorCode() {
		return _userActorCode;
	}

	public void setUserActorCode(String pUserActorCode) {
		_userActorCode = pUserActorCode;
	}


	public Integer getUserUtilCode() {
		return _userUtilCode;
	}

	public void setUserUtilCode(Integer pUserUtilCode) {
		_userUtilCode = pUserUtilCode;
	}


	public Boolean getHandleDisabledUsers() {
		return _handleDisabledUsers;
	}

	public void setHandleDisabledUsers(Boolean pHandleDisabledUsers) {
		_handleDisabledUsers = pHandleDisabledUsers;
	}

}
