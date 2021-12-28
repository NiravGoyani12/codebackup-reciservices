package com.rcibanque.rcidirect.services.core.user.dao.impl;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.rcibanque.rcidirect.services.core.dao.DAO;
import com.rcibanque.rcidirect.services.core.domain.DateTime;
import com.rcibanque.rcidirect.services.core.domain.IContext;
import com.rcibanque.rcidirect.services.core.property.dao.IPropertyDao;
import com.rcibanque.rcidirect.services.core.system.dao.IParamExDao;
import com.rcibanque.rcidirect.services.core.system.dao.ISystemDAO;
import com.rcibanque.rcidirect.services.core.system.domain.Language;
import com.rcibanque.rcidirect.services.core.system.domain.ParamEx;
import com.rcibanque.rcidirect.services.core.system.domain.ParamExEnum;
import com.rcibanque.rcidirect.services.core.system.domain.Site;
import com.rcibanque.rcidirect.services.core.user.IUser;
import com.rcibanque.rcidirect.services.core.user.IUserPreferences;
import com.rcibanque.rcidirect.services.core.user.User;
import com.rcibanque.rcidirect.services.core.user.UserPreferences;
import com.rcibanque.rcidirect.services.core.user.dao.IUserDAO;
import com.rcibanque.rcidirect.services.core.user.dao.criteria.UserCriteria;
import com.rcibanque.rcidirect.services.core.user.domain.UserProfileGroup;
import com.rcibanque.rcidirect.services.core.user.property.UserProperty;
import com.rcibanque.rcidirect.services.core.utils.ConvertUtils;

@Repository
public class UserDAO extends DAO implements IUserDAO {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserDAO.class);


	/** Provider type for dealer call (RCIDirect settlement and rat) */
	private static final Integer PROVIDER_TYPE_DEALER = Integer.valueOf(2);

	/** Provider type for user call (row and omega settlement) */
	private static final Integer PROVIDER_TYPE_USER = Integer.valueOf(1);


	@Autowired
	private ISystemDAO _systemDAO;

	@Autowired
	private IParamExDao _paramExDAO;

	@Autowired
	private IPropertyDao _propertyDao;


	@Override
	public void createUser(IContext pContext, User pUser) {
		LOGGER.debug("Create user");

		Map<String, Object> params = newParamsMap();

		Site site = _systemDAO.getSite();

		// Get new unique user code
		pUser.setUserUtilCode(getNewUserCode(pContext, pUser));

		// Insert new user details
		StringBuilder sql = new StringBuilder();
		sql.append(" insert into pautil ");
		sql.append(" (ka_acteur, cc_util, lc_login, fl_super, fl_derog, fl_accept, kcd_langue, fl_recouv, kl3_pays, kl3_site, fl_habperte, fl_coaccept, kcd_profpv, tg_password, fl_actif) ");
		sql.append(" values ");
		sql.append(" (:actorCode, :cc_util, :userName, :superUser, :flderog, :fl_accept, :language, :fl_recouv, :kl3_pays, :kl3_site, :fl_habperte, :fl_coaccept, :kcd_profpv, :password, :active) ");

		params.put("actorCode", pUser.getUserActorCode());
		params.put("cc_util", pUser.getUserUtilCode());
		params.put("userName", pUser.getUsername());
		params.put("superUser", Boolean.FALSE);
		params.put("flderog", Boolean.FALSE);
		params.put("fl_accept", Boolean.FALSE);
		params.put("language", pContext.getLanguageCode());
		params.put("fl_recouv", Boolean.FALSE);
		params.put("kl3_pays", site.getCountryCode());
		params.put("kl3_site", site.getSiteCode());
		params.put("fl_habperte", Boolean.FALSE);
		params.put("fl_coaccept", Boolean.FALSE);
		params.put("kcd_profpv", pUser.getProfileCode());
		params.put("password", pUser.getPassword());
		params.put("active", Boolean.TRUE);

		int res = update(sql.toString(), params);
		handleResult(res, pContext, "Could not create user {0}", pUser.getUserActorCode());

		insertSkillSet(pContext, pUser);

		// Properties
		handleProperties(pContext, pUser);
	}

	private Integer getNewUserCode(IContext pContext, User pUser) {

		// Unique constraint will double check this

		String sql = " select isnull(max(cc_util), 0) + 1 from pautil ";

		return queryObject(sql, null, Integer.class);
	}


	@Override
	public void updateUser(IContext pContext, User pUser) {

		Map<String, Object> params = newParamsMap();

		StringBuilder sql = new StringBuilder();
		sql.append(" update pautil set ");
		sql.append(" fl_actif = :active, ");
		sql.append(" kcd_langue = :languageCode ");
		sql.append(" where ka_acteur = :actorCode ");

		params.put("active", pUser.getEnabled());
		params.put("languageCode", pUser.getPreferences().getLanguageCode());
		params.put("actorCode", pUser.getUserActorCode());

		int res = update(sql.toString(), params);
		handleResult(res, pContext, "Could not update user {0}", pUser.getUserActorCode());

		// Properties
		handleProperties(pContext, pUser);

		// Language update => locale update
		loadLocale(pUser);
	}


	private void handleProperties(IContext pContext, User pUser) {

		_propertyDao.updateOrInsert(pContext, UserProperty.PROFILE_ROLE, pUser.getUserActorCode(), pUser.getProfileRoleCode());

		IUserPreferences userPreferences = pUser.getPreferences() != null ? pUser.getPreferences() : new UserPreferences();
		_propertyDao.updateOrInsert(pContext, UserProperty.EMAIL_WHEN_NEW_MESSAGE, pUser.getUserActorCode(), userPreferences.getNewMessageEmail());
		_propertyDao.updateOrInsert(pContext, UserProperty.EMAIL_WHEN_PROPOSAL_EXPIRY, pUser.getUserActorCode(), userPreferences.getProposalExpiryEmail());
	}


	private void insertSkillSet(IContext pContext, User pUser) {
		LOGGER.debug("Create user skill set");

		Map<String, Object> params = newParamsMap();

		StringBuilder sql = new StringBuilder();
		sql.append(" insert into reskillset ");
		sql.append(" (ka_acteur, mt_maxinvoice, mt_maxrefund, mt_maxexgratia, mt_crednote, fl_unterm, fl_hterm, fl_nhterm, fl_defsatisf, vl_duedatedays, va_eocmonths, va_extmonths, mt_maxext, mt_maxfeereb, mt_maxEarlyFeeReb) ");
		sql.append(" values ");
		sql.append(" (:actorCode, :mt_maxinvoice, :mt_maxrefund, :mt_maxexgratia, :mt_crednote, :fl_unterm, :fl_hterm, :fl_nhterm, :fl_defsatisf, :vl_duedatedays, :va_eocmonths, :va_extmonths, :mt_maxext, :mt_maxfeereb, :mt_maxEarlyFeeReb) ");

		params.put("actorCode", pUser.getUserActorCode());
		params.put("mt_maxinvoice", BigDecimal.ZERO);
		params.put("mt_maxrefund", BigDecimal.ZERO);
		params.put("mt_maxexgratia", BigDecimal.ZERO);
		params.put("mt_crednote", BigDecimal.ZERO);
		params.put("fl_unterm", Boolean.FALSE);
		params.put("fl_hterm",  Boolean.FALSE);
		params.put("fl_nhterm",  Boolean.FALSE);
		params.put("fl_defsatisf",  Boolean.FALSE);
		params.put("vl_duedatedays",  Boolean.FALSE);
		params.put("va_eocmonths",  Boolean.FALSE);
		params.put("va_extmonths",  Boolean.FALSE);
		params.put("mt_maxext", BigDecimal.ZERO);
		params.put("mt_maxfeereb", BigDecimal.ZERO);
		params.put("mt_maxEarlyFeeReb", BigDecimal.ZERO);

		int res = update(sql.toString(), params);
		handleResult(res, pContext, "Could not create skill set for user {0} ", pUser.getUserActorCode());
	}



	@Override
	public User getUser(UserCriteria pCriteria) {

		Map<String, Object> params = newParamsMap();

		StringBuilder sql = new StringBuilder();
		sql.append(" select case when prof.fl_actif = 0 then 0 else util.fl_actif end enabled,");
		sql.append(" util.lc_login login, ");
		sql.append(" isnull((SELECT tg_password FROM hipassword pswd WHERE pswd.ka_acteur = util.ka_acteur GROUP BY pswd.ka_acteur HAVING pswd.kdt_date = MAX(pswd.kdt_date)), :missingPassword) password, ");
		sql.append(" isnull((SELECT case when datediff(dd, max(kdt_date), :date) >= :passwordExpiryDays then 1 else 0 end FROM hipassword pswd WHERE pswd.ka_acteur = util.ka_acteur GROUP BY pswd.ka_acteur), 1) fl_passwordExpired, ");
		sql.append(" case when isnull(log.no_of_tries, 0) > 0 then 1 else 0 end lastLoginAttemptFailed, ");
		sql.append(" util.ka_acteur actorCode, ");
		sql.append(" util.kcd_profpv profileCode, ");
		sql.append(" util.cc_util utilCode, ");
		sql.append(" prof.tg_profil profile, ");
		sql.append(" prof.tg_proflabel profileLabel, ");
		sql.append(" prof.kcd_profgrp profileGroup, ");
		sql.append(" util.kcd_langue language, ");
		sql.append(" part.tl_prenom firstName, ");
		sql.append(" act.tl_nom lastName, ");
		sql.append(" act.tg_email email, ");
		sql.append(" emailNewMessageProp.co_value emailNewMessage, ");
		sql.append(" emailProposalExpiryProp.co_value emailProposalExpiry, ");
		sql.append(" profileRoleProp.co_value profileRole, ");
		sql.append(" lastLoginDateProp.co_value lastLoginDate, ");
		sql.append(" noLoginExpiryProp.co_value noLoginExpiry ");

		sql.append(" from pautil util ");
		sql.append(" left outer join LOG_invalid_login log on log.ka_acteur = util.ka_acteur ");
		sql.append(" inner join paprofil prof on prof.kcd_profil = util.kcd_profpv and prof.kcd_langue = util.kcd_langue ");
		sql.append(" inner join acteurs act on act.ka_acteur = util.ka_acteur ");
		sql.append(" inner join acpartic part on part.ka_acteur = act.ka_acteur ");
		sql.append(" left outer join acteurs_prop emailNewMessageProp on emailNewMessageProp.ka_acteur = util.ka_acteur and emailNewMessageProp.klc_property = :emailNewMessagePropName ");
		sql.append(" left outer join acteurs_prop emailProposalExpiryProp on emailProposalExpiryProp.ka_acteur = util.ka_acteur and emailProposalExpiryProp.klc_property = :emailProposalExpiryPropName ");
		sql.append(" left outer join acteurs_prop profileRoleProp on profileRoleProp.ka_acteur = util.ka_acteur and profileRoleProp.klc_property = :profileRolePropName ");
		sql.append(" left outer join acteurs_prop lastLoginDateProp on lastLoginDateProp.ka_acteur = util.ka_acteur and lastLoginDateProp.klc_property = :lastLoginDatePropName ");
		sql.append(" left outer join acteurs_prop noLoginExpiryProp on noLoginExpiryProp.ka_acteur = util.ka_acteur and noLoginExpiryProp.klc_property = :noLoginExpiryPropName ");

		sql.append(" where 1 = 1 ");

		params.put("date", _systemDAO.getCurrentDate());
		params.put("passwordExpiryDays", getPasswordExpiryDays());
		params.put("missingPassword", UUID.randomUUID().toString());
		params.put("emailNewMessagePropName", UserProperty.EMAIL_WHEN_NEW_MESSAGE.getPropertyName());
		params.put("emailProposalExpiryPropName", UserProperty.EMAIL_WHEN_PROPOSAL_EXPIRY.getPropertyName());
		params.put("profileRolePropName", UserProperty.PROFILE_ROLE.getPropertyName());
		params.put("lastLoginDatePropName", UserProperty.LAST_LOGGED_IN.getPropertyName());
		params.put("noLoginExpiryPropName", UserProperty.NO_LOGIN_EXPIRY.getPropertyName());

		if(! BooleanUtils.isTrue(pCriteria.getHandleDisabledUsers())) {

			sql.append(" and util.fl_actif = 1 ");
		}

		if(StringUtils.isNotBlank(pCriteria.getUserName())) {

			sql.append(" and lc_login = :userName");

			params.put("userName", pCriteria.getUserName());
		}
		else if(StringUtils.isNotBlank(pCriteria.getUserActorCode())) {

			sql.append(" and util.ka_acteur = :userActorCode");

			params.put("userActorCode", pCriteria.getUserActorCode());
		}
		else if(pCriteria.getUserUtilCode() != null) {

			sql.append(" and util.cc_util = :userUtilCode");

			params.put("userUtilCode", pCriteria.getUserUtilCode());
		}
		else {

			sql.append(" and 1 = 0 ");
		}


		User user = queryObject(sql.toString(), params, USER_RM);

		// Language => locale
		loadLocale(user);

		return user;
	}

	private Integer getPasswordExpiryDays() {
		Integer res = null;
		ParamEx param = _paramExDAO.getParamEx(null, ParamExEnum.LOGIN_PASSWORD_EXPIRY_DAYS);
		if(param != null) {
			res = param.getValue();
		}
		return res != null ? res : 90;
	}

	private static final RowMapper<User> USER_RM = (ResultSet rs, int rowNumber) -> {

		User user = new User();
		user.setEnabled(getBoolean(rs, "enabled"));

		user.setUsername(StringUtils.trim(getString(rs, "login")));
		user.setPassword(getString(rs, "password"));
		user.setPasswordExpired(getBoolean(rs, "fl_passwordExpired") && BooleanUtils.isNotTrue(getBooleanProperty(rs, "noLoginExpiry")));
		user.setLastLoginDate(new DateTime(ConvertUtils.parseDateTimeDisplayV1(getString(rs, "lastLoginDate"))));
		user.setLastLoginAttemptFailed(getBoolean(rs, "lastLoginAttemptFailed"));
		user.setUserActorCode(getString(rs, "actorCode"));
		user.setFirstName(getString(rs, "firstName"));
		user.setLastName(getString(rs, "lastName"));
		user.setEmail(getString(rs, "email"));

		user.setProfileCode(getInt(rs, "profileCode"));
		user.setUserUtilCode(getInt(rs, "utilCode"));
		user.setProfile(getString(rs, "profile"));
		user.setProfileLabel(getString(rs, "profileLabel"));
		user.setProfileRoleCode(getIntProperty(rs, "profileRole"));
		user.setProfileGroup(getInt(rs, "profileGroup"));
		user.setHeadOffice(UserProfileGroup.HEAD_OFFICE.equalCodes(user.getProfileGroup()));
		user.setProviderTypeCode(user.getHeadOffice() ? PROVIDER_TYPE_USER : PROVIDER_TYPE_DEALER);

		user.getPreferences().setLanguageCode(getInt(rs, "language"));
		user.getPreferences().setNewMessageEmail(BooleanUtils.isTrue(getBooleanProperty(rs, "emailNewMessage")));
		user.getPreferences().setProposalExpiryEmail(BooleanUtils.isTrue(getBooleanProperty(rs, "emailProposalExpiry")));

		return user;
	};


	private void loadLocale(IUser pUser) {

		if(pUser != null) {
			IUserPreferences userPreferences = pUser.getPreferences();

			Language locale = _systemDAO.getLanguage(userPreferences.getLanguageCode());

			userPreferences.setLocaleLanguage(locale.getLanguageCodeISO());
			userPreferences.setLocaleCountry(locale.getCountryCodeISO());
		}
	}


}