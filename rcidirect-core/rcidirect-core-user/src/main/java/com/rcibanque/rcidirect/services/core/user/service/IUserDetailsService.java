package com.rcibanque.rcidirect.services.core.user.service;

import com.rcibanque.rcidirect.services.core.domain.IContext;
import com.rcibanque.rcidirect.services.core.user.User;
import com.rcibanque.rcidirect.services.core.user.UserDetails;
import com.rcibanque.rcidirect.services.core.user.dao.criteria.UserCriteria;

public interface IUserDetailsService {


	UserDetails loadUserByUsername(String pUserName);


	User getUser(IContext pContext, UserCriteria pCriteria);

	User getUser(IContext pContext, Integer pUserUtilCode);

}
