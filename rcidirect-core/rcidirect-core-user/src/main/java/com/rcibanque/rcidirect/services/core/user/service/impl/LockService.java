package com.rcibanque.rcidirect.services.core.user.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rcibanque.rcidirect.services.core.domain.IContext;
import com.rcibanque.rcidirect.services.core.system.service.ISystemService;
import com.rcibanque.rcidirect.services.core.user.dao.ILockDAO;
import com.rcibanque.rcidirect.services.core.user.dao.criteria.LockCriteria;
import com.rcibanque.rcidirect.services.core.user.domain.Lock;
import com.rcibanque.rcidirect.services.core.user.service.ILockService;

@Service
public class LockService extends com.rcibanque.rcidirect.services.core.service.Service implements ILockService {

	@Autowired
	private ISystemService _systemService;

	@Autowired
	private ILockDAO _lockDao;


	@Override
	public boolean isLockedForCurrentUser(IContext pContext, String pLockCode) {

		return getLock(pContext, pLockCode, pContext.getUser().getUserUtilCode()) != null;
	}

	@Override
	public Lock getLock(IContext pContext, String pLockCode, Integer pUserUtilCode) {

		LockCriteria criteria = new LockCriteria();
		criteria.setLockCode(pLockCode);
		criteria.setUserUtilCode(pUserUtilCode);

		return getUniqueElement(_lockDao.getLocks(pContext, criteria));
	}


	@Override
	public List<Lock> getLocks(IContext pContext, List<String> pLockCodes) {

		LockCriteria criteria = new LockCriteria();
		criteria.setLockCodes(pLockCodes);

		return _lockDao.getLocks(pContext, criteria);
	}


	@Override
	public boolean insertLockForCurrentUser(IContext pContext, String pLockCode) {

		// Already locked?
		if(isLockedForCurrentUser(pContext, pLockCode)) {
			return true;
		}

		// Remove existing lock?
		Lock existingLock = getLock(pContext, pLockCode, null);
		if(existingLock != null) {
			boolean force = pContext.getUser().getHeadOffice(); // TODO
			if(force) {
				deleteLock(pContext, pLockCode);
			}
			else {
				return false;
			}
		}

		// Insert the lock
		Lock pLock = new Lock();
		pLock.setLockCode(pLockCode);
		pLock.setUserUtilCode(pContext.getUser().getUserUtilCode());
		pLock.setAccessDate(_systemService.getCurrentDateUpdatedTime());

		_lockDao.insertLock(pContext, pLock);

		return true;
	}


	@Override
	public void deleteLock(IContext pContext, String pLockCode) {

		LockCriteria pLockCriteria  = new LockCriteria();
		pLockCriteria.setLockCode(pLockCode);

		_lockDao.deleteLocks(pContext,pLockCriteria);
	}


	@Override
	public void deleteLocksOfCurrentUser(IContext pContext) {

		LockCriteria pLockCriteria = new LockCriteria();
		pLockCriteria.setUserUtilCode(pContext.getUser().getUserUtilCode());

		_lockDao.deleteLocks(pContext,pLockCriteria);
	}

}
