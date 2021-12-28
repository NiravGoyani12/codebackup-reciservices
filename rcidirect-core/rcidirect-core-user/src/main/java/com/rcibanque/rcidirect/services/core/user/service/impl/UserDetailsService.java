package com.rcibanque.rcidirect.services.core.user.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rcibanque.rcidirect.services.core.domain.IContext;
import com.rcibanque.rcidirect.services.core.user.User;
import com.rcibanque.rcidirect.services.core.user.UserDetails;
import com.rcibanque.rcidirect.services.core.user.dao.IUserDAO;
import com.rcibanque.rcidirect.services.core.user.dao.criteria.UserCriteria;
import com.rcibanque.rcidirect.services.core.user.service.IUserDetailsService;

@Service
public class UserDetailsService extends com.rcibanque.rcidirect.services.core.service.Service implements IUserDetailsService {


	@Autowired
	private IUserDAO _userDAO;


	@Override
	public UserDetails loadUserByUsername(String pUserName) {
		UserCriteria criteria = new UserCriteria();
		criteria.setUserName(pUserName);
		criteria.setHandleDisabledUsers(Boolean.TRUE); // Authentication fails is user is not enabled

		User user = _userDAO.getUser(criteria);

		UserDetails userDetails = null;
		if (user != null) {
			userDetails = new UserDetails(user);
		}

		return userDetails;
	}


	@Override
	public User getUser(IContext pContext, UserCriteria pCriteria) {

		return _userDAO.getUser(pCriteria);
	}


	@Override
	public User getUser(IContext pContext, Integer pUserUtilCode) {

		UserCriteria criteria = new UserCriteria();
		criteria.setLanguageCode(pContext.getLanguageCode());
		criteria.setUserUtilCode(pUserUtilCode);

		return getUser(pContext, criteria);
	}

}