package com.rcibanque.rcidirect.services.core.user.service;

import java.util.List;

import com.rcibanque.rcidirect.services.core.domain.IContext;
import com.rcibanque.rcidirect.services.core.user.domain.Lock;

public interface ILockService {


	List<Lock> getLocks(IContext pContext, List<String> pLockCodes);

	Lock getLock(IContext pContext, String pLockCode, Integer pUserUtilCode);

	boolean isLockedForCurrentUser(IContext pContext, String pLockCode);


	boolean insertLockForCurrentUser(IContext pContext, String pLockCode);


	void deleteLock(IContext pContext, String pLockCode);

	void deleteLocksOfCurrentUser(IContext pContext);

}
