package com.rcibanque.rcidirect.services.core.system.dao.impl;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.rcibanque.rcidirect.services.core.dao.DAO;
import com.rcibanque.rcidirect.services.core.domain.IContext;
import com.rcibanque.rcidirect.services.core.system.dao.IParamDao;
import com.rcibanque.rcidirect.services.core.system.dao.criteria.ParamCriteria;
import com.rcibanque.rcidirect.services.core.system.domain.Param;
import com.rcibanque.rcidirect.services.core.system.domain.ParamEnum;
import com.rcibanque.rcidirect.services.core.utils.IteratorUtils;

@Repository
public class ParamDao extends DAO implements IParamDao {


	private static final Logger LOGGER = LoggerFactory.getLogger(ParamDao.class);


	@Override
	@Cacheable(value = "paramCache", keyGenerator = "keyGenerator")
	public Param getParam(IContext pContext, ParamEnum pParameter) {

		List<Param> res = getParams(pContext, ParamCriteria.getInstance(pParameter));

		return IteratorUtils.firstElement(res);
	}


	@Override
	@Cacheable(value = "paramsCache", key = "#pCriteria")
	public List<Param> getParams(IContext pContext, ParamCriteria pCriteria) {
		LOGGER.debug("getParams");

		Map<String, Object> params = newParamsMap();

		StringBuilder sql = new StringBuilder();
		sql.append(" select kcd_numpar, cd_typpar, tg_libpar, tg_param, va_param, mt_param, fl_param, dt_param, pc_param ");
		sql.append(" from param ");
		sql.append(" where 1 = 1 ");

		if (ArrayUtils.isNotEmpty(pCriteria.getParameters())) {
			sql.append(" and kcd_numpar in (:paramCodes) ");
			params.put("paramCodes", getParameterCodes(pCriteria.getParameters()));
		}

		return queryList(sql.toString(), params, GET_PARAMS_RM);
	}


	private static List<Integer> getParameterCodes(ParamEnum[] pParameters) {
		List<Integer> res = new ArrayList<>();
		for(ParamEnum param : pParameters) {
			res.add(param.getParamCode());
		}
		return res;
	}

	private static final RowMapper<Param> GET_PARAMS_RM = (ResultSet rs, int rowNumber) -> {

		return new Param(
				ParamEnum.valueOf(getInt(rs, "kcd_numpar")),
				getString(rs, "tg_libpar"),
				getString(rs, "tg_param"),
				getInt(rs, "va_param"),
				getBigDecimal(rs, "mt_param"),
				getBoolean(rs, "fl_param"),
				getDate(rs, "dt_param"),
				getDouble(rs, "pc_param"));
	};


}
