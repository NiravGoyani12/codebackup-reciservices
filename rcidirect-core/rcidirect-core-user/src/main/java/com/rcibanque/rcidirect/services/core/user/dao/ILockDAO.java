package com.rcibanque.rcidirect.services.core.user.dao;

import java.util.List;

import com.rcibanque.rcidirect.services.core.domain.IContext;
import com.rcibanque.rcidirect.services.core.user.dao.criteria.LockCriteria;
import com.rcibanque.rcidirect.services.core.user.domain.Lock;

public interface ILockDAO {

	List<Lock> getLocks(IContext pContext, LockCriteria pCriteria);

	void insertLock(IContext pContext, Lock pLock);

	void deleteLocks(IContext pContext, LockCriteria pCriteria);


	void clearUserLocks();

}
