package com.rcibanque.rcidirect.services.core.user.dao;

import com.rcibanque.rcidirect.services.core.domain.IContext;
import com.rcibanque.rcidirect.services.core.user.User;
import com.rcibanque.rcidirect.services.core.user.dao.criteria.UserCriteria;

public interface IUserDAO {


	void createUser(IContext pContext, User pUser);

	void updateUser(IContext pContext, User pUser);

	User getUser(UserCriteria pCriteria);

}