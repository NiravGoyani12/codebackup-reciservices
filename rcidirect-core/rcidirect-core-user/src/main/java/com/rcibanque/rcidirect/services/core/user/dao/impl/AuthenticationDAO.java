package com.rcibanque.rcidirect.services.core.user.dao.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.rcibanque.rcidirect.services.core.dao.DAO;
import com.rcibanque.rcidirect.services.core.domain.IContext;
import com.rcibanque.rcidirect.services.core.system.dao.ISystemDAO;
import com.rcibanque.rcidirect.services.core.user.dao.IAuthenticationDAO;

@Repository
public class AuthenticationDAO extends DAO implements IAuthenticationDAO {


	@Autowired
	private ISystemDAO _systemDAO;


	@Override
	public void createPasswordResetToken(String pUserActorCode, String pToken, Date pExpiryDate) {

		Map<String, Object> paramsCode = newParamsMap();
		paramsCode.put("actor_code", pUserActorCode);
		paramsCode.put("reset_token", pToken);
		paramsCode.put("dt_expiry", pExpiryDate);

		executeProcedure("log_forgotten_password_request", paramsCode);
		// No return value
	}


	@Override
	public Date getResetPasswordTokenExpiryDate(String pUserActorCode, String pToken) {

		StringBuilder sql = new StringBuilder();
		sql.append(" select dt_expiry ");
		sql.append(" from LOG_forgotten_password ");
		sql.append(" where ka_acteur = :actorCode ");
		sql.append(" and token = :token ");

		Map<String, Object> paramsCode = newParamsMap();
		paramsCode.put("actorCode", pUserActorCode);
		paramsCode.put("token", pToken);

		Date res = queryObject(sql.toString(), paramsCode, Date.class);
		return res;
	}


	@Override
	public void deleteResetPasswordToken(IContext pContext, String pUserActorCode) {

		StringBuilder sql = new StringBuilder();
		sql.append(" delete from LOG_forgotten_password ");
		sql.append(" where ka_acteur = :actorCode ");

		Map<String, Object> paramsCode = newParamsMap();
		paramsCode.put("actorCode", pUserActorCode);

		update(sql.toString(), paramsCode);
		// Not necessarily a record, so don't check
	}


	@Override
	public void updatePassword(IContext pContext, String pUserActorCode, String pPassword) {

		StringBuilder sql = new StringBuilder();
		sql.append(" update pautil set ");
		sql.append(" tg_password = :password ");
		sql.append(" where ka_acteur = :actorCode ");

		Map<String, Object> params = newParamsMap();

		params.put("password", pPassword);
		params.put("actorCode", pUserActorCode);

		int res = update(sql.toString(), params);
		handleResult(res, pContext, "Could not update password for user {0}", pUserActorCode);
	}


	@Override
	public void createHiPassword(IContext pContext, String pUserActorCode, String pPassword) {

		StringBuilder sql = new StringBuilder();
		sql.append(" insert into hipassword ");
		sql.append(" (ka_acteur, kdt_date, tg_password) ");
		sql.append(" values ");
		sql.append(" (:actorCode, :createDate, :password) ");

		Map<String, Object> params = newParamsMap();
		params.put("actorCode", pUserActorCode);
		params.put("password", pPassword);
		params.put("createDate", new Timestamp(_systemDAO.getCurrentDateUpdatedTime().getTime()));


		int res = update(sql.toString(), params);
		handleResult(res, pContext, "Could not insert hipassword for user {0}", pUserActorCode);
	}


	@Override
	public int authenticate(String pUsername, String pPassword) {
		Map<String, Object> paramsCode = newParamsMap();
		paramsCode.put("lc_login", pUsername);
		paramsCode.put("password", pPassword);
		paramsCode.put("result", 0);

		Map<String, Object> authenticateRes = executeProcedure("validate_login_out", paramsCode);
		Integer returnCode = (Integer) authenticateRes.get("result");
		return returnCode;
	}

	@Override
	public boolean checkPasswordExistence(IContext pContext, String pUserActorCode, String pEncodedPassword, Integer pLastNPassword) {

		// We use this to check that the new password hasn't already been used (in the previous N passwords)
		// But there is also a unique key constraint, so we actually need to check all previous passwords

		StringBuilder sql = new StringBuilder();
		sql.append(" select tg_password ");
		sql.append(" from hipassword ");
		sql.append(" where ka_acteur = :actorCode ");

		Map<String, Object> paramsCode = newParamsMap();
		paramsCode.put("actorCode", pUserActorCode);

		List<String> oldPasswords = queryList(sql.toString(), paramsCode, String.class);

		return oldPasswords != null && oldPasswords.stream().anyMatch(password -> password.equals(pEncodedPassword));
	}
}