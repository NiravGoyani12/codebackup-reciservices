package com.rcibanque.rcidirect.services.core.system.dao.impl;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.stereotype.Repository;

import com.rcibanque.rcidirect.services.core.dao.DAO;
import com.rcibanque.rcidirect.services.core.domain.IContext;
import com.rcibanque.rcidirect.services.core.system.dao.ISystemDAO;
import com.rcibanque.rcidirect.services.core.system.dao.criteria.FunctionCriteria;
import com.rcibanque.rcidirect.services.core.system.domain.Function;
import com.rcibanque.rcidirect.services.core.system.domain.FunctionDetails;
import com.rcibanque.rcidirect.services.core.system.domain.Language;
import com.rcibanque.rcidirect.services.core.system.domain.Site;

@Repository
public class SystemDAO extends DAO implements ISystemDAO {


	private static final String SITE_CODE = "RCI";

	private static final boolean SYSTEM_TIME = true;


	@Override
	@Cacheable(value = "systemDateCache", keyGenerator = "keyGenerator")
	public Date getCurrentDate() {
		String sql = "select dt_jour from paserver where kcc_noserv = 1";
		Date res = queryObject(sql, null, Date.class);
		return new Date(res.getTime());
	}


	@Override
	public Date getCurrentDateUpdatedTime() {
		Date res = null;

		if(SYSTEM_TIME) {
			// This is used to be compatible with V1:
			//    - V1 runs on a system in local time
			//    - V2 runs on a system in UTC time => set VM argument 'user.timezone' to the desired time zone
			res = getCurrentDateUpdatedTimeSystem();
		}
		else {
			// This should be the favoured solution, as it is what is used by stored procedures, triggers, etc.
			// However, DB runs on UTC time, so the above solution is used.
			// As a result, dates defined by triggers for example are UTC, and dates defined by Java back-end are local time...
			res = getCurrentDateUpdatedTimeDB();
		}

		return res;
	}

	private Date getCurrentDateUpdatedTimeDB() {
		Map<String, Object> params = newParamsMap();

		SqlOutParameter xdhParam = getOutputParameterTimestamp("xdh");
		SqlOutParameter xdhcParam = getOutputParameterString("xdh_c");

		Map<String, Object> results = executeProcedure("xdh_get", params, xdhParam, xdhcParam);

		return new Date(((Timestamp) results.get(xdhParam.getName())).getTime());
	}

	private Date getCurrentDateUpdatedTimeSystem() {

		return addSystemTimePart(getCurrentDate());
	}

	private static Date addSystemTimePart(Date pDate) {

		// Current time
		Calendar currentTime = Calendar.getInstance();
		currentTime.setTimeInMillis(System.currentTimeMillis());

		// Add time to date
		Calendar resCalendar = Calendar.getInstance();
		resCalendar.setTime(pDate);
		resCalendar.set(Calendar.HOUR, currentTime.get(Calendar.HOUR));
		resCalendar.set(Calendar.MINUTE, currentTime.get(Calendar.MINUTE));
		resCalendar.set(Calendar.SECOND, currentTime.get(Calendar.SECOND));
		resCalendar.set(Calendar.MILLISECOND, currentTime.get(Calendar.MILLISECOND));
		resCalendar.set(Calendar.AM_PM, currentTime.get(Calendar.AM_PM));

		Date res = resCalendar.getTime();
		return res;
	}


	@Override
	@Cacheable(value = "systemMaintenanceCache", keyGenerator = "keyGenerator")
	public Boolean isSystemInMaintenance() {
		String sql = "select dt_coupure from paserver where kcc_noserv = 1";
		Date res = queryObject(sql, null, Date.class);
		return res != null;
	}


	@Override
	@Cacheable(value = "functionsCache", key = "#pCriteria")
	public List<FunctionDetails> getFunctions(IContext pContext, FunctionCriteria pCriteria) {

		Map<String, Object> params = newParamsMap();

		StringBuilder sql = new StringBuilder();
		sql.append(" select spec.kcd_numspec, co_libspec, ");
		sql.append(" case when kl3_pays is not null then 1 when kl3_site is not null then 1 else 0 end fl_enabled ");
		sql.append(" from paspec spec ");
		sql.append(" left outer join papayspe payspe on payspe.kcd_numspec = spec.kcd_numspec ");
		sql.append(" left outer join pasitspe sitspe on sitspe.kcd_numspec = spec.kcd_numspec ");
		sql.append(" where exists ( ");
		sql.append(" 	select 1  ");
		sql.append(" 	from ldsite site  ");
		sql.append(" 	where (payspe.kl3_pays is null or payspe.kl3_pays = site.kl3_pays) ");
		sql.append(" 	and (sitspe.kl3_site is null or sitspe.kl3_site = site.kl3_site) ");
		sql.append(" ) ");

		if (ArrayUtils.isNotEmpty(pCriteria.getFunctionCodes())) {
			sql.append(" and spec.kcd_numspec in (:functionCodes) ");
			params.put("functionCodes", asList(pCriteria.getFunctionCodes()));
		}
		return queryList(sql.toString(), params, GET_FUNCTION_RM);
	}

	private static final RowMapper<FunctionDetails> GET_FUNCTION_RM = (ResultSet rs, int rowNumber) -> {

		FunctionDetails res = new FunctionDetails();
		res.setFunction(Function.valueOf(getInt(rs, "kcd_numspec")));
		res.setDescription(getString(rs, "co_libspec"));
		res.setEnabled(getBoolean(rs, "fl_enabled"));
		return res;
	};


	@Override
	@Cacheable(value = "functionCache", keyGenerator = "keyGenerator")
	public Boolean isFunctionEnabled(IContext pContext, Function pFunction) {

		FunctionCriteria criteria = FunctionCriteria.getInstance(pFunction.getCode());

		List<FunctionDetails> functionDetails = getFunctions(pContext, criteria);

		return CollectionUtils.isEmpty(functionDetails) ? Boolean.FALSE : functionDetails.get(0).getEnabled();
	}


	@Override
	@Cacheable(value = "siteCache", keyGenerator = "keyGenerator")
	public Site getSite() {

		Map<String, Object> params = newParamsMap();

		StringBuilder sql = new StringBuilder();
		sql.append(" select * ");
		sql.append(" from ldsite site ");
		sql.append(" where kl3_site = :siteCode ");

		params.put("siteCode", SITE_CODE);

		return queryObject(sql.toString(), params, SITE_RM);
	}

	private static final RowMapper<Site> SITE_RM = (ResultSet rs, int rowNumber) -> {

		Site site = new Site();

		site.setSiteCode(StringUtils.trim(getString(rs, "kl3_site")));
		site.setSiteLabel(getString(rs, "tg_site"));
		site.setCountryCode(StringUtils.trim(getString(rs, "kl3_pays")));

		return site;
	};


	@Override
	@Cacheable(value = "languagesCache", keyGenerator = "keyGenerator")
	public List<Language> getLanguages() {

		Map<String, Object> params = newParamsMap();

		StringBuilder sql = new StringBuilder();
		sql.append(" select kcd_langue, tg_langue, lower(lang.kl3_iso) kl3_iso, upper(site.kl3_pays) kl3_pays ");
		sql.append(" from ldsite site, palangue lang ");
		sql.append(" where site.kl3_site = :siteCode ");
		sql.append(" and fl_actif = 1 ");

		params.put("siteCode", SITE_CODE);

		return queryList(sql.toString(), params, LANGUAGES_RM);
	}

	@Override
	@Cacheable(value = "languageCache", key = "#pLanguageCode")
	public Language getLanguage(Integer pLanguageCode) {
		Language res = null;

		for(Language language : getLanguages()) {
			if(language.getLanguageCode().equals(pLanguageCode)) {
				res = language;
				break;
			}
		}

		return res;
	}

	private static final RowMapper<Language> LANGUAGES_RM = (ResultSet rs, int rowNumber) -> {

		Language language = new Language();

		language.setLanguageCode(getInt(rs, "kcd_langue"));
		language.setLanguageLabel(getString(rs, "tg_langue"));
		language.setLanguageCodeISO(StringUtils.trim(getString(rs, "kl3_iso")));
		language.setCountryCodeISO(StringUtils.trim(getString(rs, "kl3_pays")));
		language.setLocale(new Locale(language.getLanguageCodeISO(), language.getCountryCodeISO()));

		return language;
	};


}
