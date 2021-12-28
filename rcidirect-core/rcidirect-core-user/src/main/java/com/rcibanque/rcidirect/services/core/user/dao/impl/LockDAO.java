package com.rcibanque.rcidirect.services.core.user.dao.impl;

import java.sql.ResultSet;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.rcibanque.rcidirect.services.core.dao.DAO;
import com.rcibanque.rcidirect.services.core.domain.IContext;
import com.rcibanque.rcidirect.services.core.user.dao.ILockDAO;
import com.rcibanque.rcidirect.services.core.user.dao.criteria.LockCriteria;
import com.rcibanque.rcidirect.services.core.user.domain.Lock;

@Repository
public class LockDAO extends DAO implements ILockDAO {


	private static final String LOCK_MODULE = "RCIDirect";

	private static final String LOCK_ADDRESS = "V2";

	private static final String LOCK_ACTION = "M"; // Modification


	@Override
	public List<Lock> getLocks(IContext pContext, LockCriteria pCriteria) {
		Assert.isTrue(StringUtils.isNotEmpty(pCriteria.getLockCode()) || CollectionUtils.isNotEmpty(pCriteria.getLockCodes()), "Lock code(s) is mandatory");

		Map<String, Object> params = newParamsMap();

		StringBuilder sql = new StringBuilder();
		sql.append(" select * ");
		sql.append(" from paacconc conc ");
		sql.append(" where 1 = 1 ");

		if(StringUtils.isNotBlank(pCriteria.getLockCode())) {
			sql.append(" and ll_code = :lockCode ");
			params.put("lockCode", pCriteria.getLockCode());
		}

		if(CollectionUtils.isNotEmpty(pCriteria.getLockCodes())) {
			sql.append(" and ll_code in (:lockCodes) ");
			params.put("lockCodes", pCriteria.getLockCodes());
		}

		if(pCriteria.getUserUtilCode() != null) {
			sql.append(" and cc_util = :userUtilCode ");
			params.put("userUtilCode", pCriteria.getUserUtilCode());
		}

		return queryList(sql.toString(), params, GET_LOCKS_RM);
	}

	private static final RowMapper<Lock> GET_LOCKS_RM = (ResultSet rs, int rowNumber) -> {
		Lock res = new Lock();

		res.setLockCode(StringUtils.trim(getString(rs, "ll_code")));
		res.setUserUtilCode(getInt(rs, "cc_util"));
		res.setAccessDate(getDateTime(rs, "dt_acces"));

		return res;
	};


	@Override
	public void insertLock(IContext pContext, Lock pLock) {

		Map<String, Object> params = newParamsMap();

		StringBuilder sql = new StringBuilder();
		sql.append(" insert into paacconc ");
		sql.append(" (lc_module , ll_code, dt_acces, ln_adresse, l1_typact, cc_util) ");
		sql.append(" values ");
		sql.append(" (:module, :lockCode, :accessDate, :address, :typeAction, :userUtilCode) ");

		params.put("lockCode", pLock.getLockCode());
		params.put("userUtilCode", pLock.getUserUtilCode());
		params.put("accessDate", pLock.getAccessDate());

		params.put("module", LOCK_MODULE);
		params.put("address", LOCK_ADDRESS);
		params.put("typeAction", LOCK_ACTION);

		int res = update(sql.toString(), params);

		handleResult(res, pContext, "Could not insert lock {0} for user {1}", pLock.getLockCode(), pLock.getUserUtilCode());
	}


	@Override
	public void deleteLocks(IContext pContext, LockCriteria pCriteria) {
		Assert.isTrue(StringUtils.isNotEmpty(pCriteria.getLockCode()) || pCriteria.getUserUtilCode() != null, "Lock code or user util code is mandatory");

		Map<String, Object> params = newParamsMap();

		StringBuilder sql = new StringBuilder();
		sql.append(" delete from paacconc ");
		sql.append(" where 1=1 ");

		if(pCriteria.getUserUtilCode() != null) {
			sql.append(" and cc_util = :userUtilCode ");
			params.put("userUtilCode", pCriteria.getUserUtilCode());
		}

		if(StringUtils.isNotEmpty(pCriteria.getLockCode())) {
			sql.append(" and ll_code = :lockCode ");
			params.put("lockCode", pCriteria.getLockCode());
		}

		update(sql.toString(), params);
	}


	@Override
	public void clearUserLocks() {

		// Remove locks (from RCIDirect V2) of users that aren't connected anymore

		Map<String, Object> params = newParamsMap();

		StringBuilder sql = new StringBuilder();
		sql.append(" delete from paacconc ");
		sql.append(" where lc_module = :module");
		sql.append(" and ln_adresse = :address");
		sql.append(" and not exists ( ");
		sql.append("   select 1 ");
		sql.append("   from pautil ut ");
		sql.append("   join SPRING_SESSION ses on ses.PRINCIPAL_NAME = ut.lc_login ");
		sql.append("   where ut.cc_util = paacconc.cc_util ");
		sql.append(" ) ");

		params.put("module", LOCK_MODULE);
		params.put("address", LOCK_ADDRESS);

		update(sql.toString(), params);
	}


}
