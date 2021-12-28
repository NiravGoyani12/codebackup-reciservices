package com.rcibanque.rcidirect.services.core.system.dao.impl;

import java.sql.ResultSet;
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
import com.rcibanque.rcidirect.services.core.system.dao.IParamExDao;
import com.rcibanque.rcidirect.services.core.system.dao.criteria.ParamExCriteria;
import com.rcibanque.rcidirect.services.core.system.domain.ParamEx;
import com.rcibanque.rcidirect.services.core.system.domain.ParamExEnum;
import com.rcibanque.rcidirect.services.core.utils.IteratorUtils;

@Repository
public class ParamExDao extends DAO implements IParamExDao {


	private static final Logger LOGGER = LoggerFactory.getLogger(ParamExDao.class);


	@Override
	@Cacheable(value = "paramExCache", keyGenerator = "keyGenerator")
	public ParamEx getParamEx(IContext pContext, ParamExEnum pParameter) {

		List<ParamEx> res = getParamExs(pContext, ParamExCriteria.getInstance(pParameter));

		return IteratorUtils.firstElement(res);
	}


	@Override
	@Cacheable(value = "paramExsCache", key = "#pCriteria")
	public List<ParamEx> getParamExs(IContext pContext, ParamExCriteria pCriteria) {
		LOGGER.debug("getParamExs");

		Map<String, Object> params = newParamsMap();

		StringBuilder sql = new StringBuilder();
		sql.append(" select k_scope, k_code, tg_comment, tg_value ");
		sql.append(" from paramEX ");
		sql.append(" where 1 = 1 ");

		if (ArrayUtils.isNotEmpty(pCriteria.getParameters())) {
			sql.append(" and ( ");
			for(int i=0; i<pCriteria.getParameters().length; i++) {
				ParamExEnum param = pCriteria.getParameters()[i];
				if(i>0) {
					sql.append(" or ");
				}
				String scopeParam = "k_scope" + i;
				String codeParam = "k_code" + i;
				sql.append(" (k_scope = :").append(scopeParam).append(" and k_code = :").append(codeParam).append(") ");
				params.put(scopeParam, param.getParamScope());
				params.put(codeParam, param.getParamCode());
			}
			sql.append(" ) ");
		}

		return queryList(sql.toString(), params, GET_PARAMEXS_RM);
	}


	private static final RowMapper<ParamEx> GET_PARAMEXS_RM = (ResultSet rs, int rowNumber) -> {

		return new ParamEx(
				ParamExEnum.valueOf(
						getString(rs, "k_scope"),
						getString(rs, "k_code")),
				getString(rs, "tg_comment"),
				getString(rs, "tg_value"));
	};


}
