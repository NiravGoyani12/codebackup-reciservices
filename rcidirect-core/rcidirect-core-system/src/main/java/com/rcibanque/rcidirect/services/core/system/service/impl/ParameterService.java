package com.rcibanque.rcidirect.services.core.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rcibanque.rcidirect.services.core.domain.IContext;
import com.rcibanque.rcidirect.services.core.system.dao.IParamDao;
import com.rcibanque.rcidirect.services.core.system.dao.IParamExDao;
import com.rcibanque.rcidirect.services.core.system.dao.criteria.ParamCriteria;
import com.rcibanque.rcidirect.services.core.system.dao.criteria.ParamExCriteria;
import com.rcibanque.rcidirect.services.core.system.domain.Param;
import com.rcibanque.rcidirect.services.core.system.domain.ParamEnum;
import com.rcibanque.rcidirect.services.core.system.domain.ParamEx;
import com.rcibanque.rcidirect.services.core.system.domain.ParamExEnum;
import com.rcibanque.rcidirect.services.core.system.service.IParameterService;

@Service
public class ParameterService extends com.rcibanque.rcidirect.services.core.service.Service implements IParameterService {


	@Autowired
	private IParamDao _paramDao;

	@Autowired
	private IParamExDao _paramExDao;


	@Override
	public List<Param> getParams(IContext pContext, ParamCriteria pCriteria) {

		return _paramDao.getParams(pContext, pCriteria);
	}

	@Override
	public Param getParam(IContext pContext, ParamEnum pParameter) {

		return _paramDao.getParam(pContext, pParameter);
	}

	@Override
	public <T> T getParamValue(IContext pContext, ParamEnum pParameter) {
		T res = null;
		Param param = getParam(pContext, pParameter);
		if(param != null) {
			res = param.getValue();
		}
		return res;
	}

	@Override
	public <T> T getParamValue(IContext pContext, ParamEnum pParameter, T pDefault) {
		T res = getParamValue(pContext, pParameter);
		return res != null ? res : pDefault;
	}


	@Override
	public List<ParamEx> getParamExs(IContext pContext, ParamExCriteria pCriteria) {

		return _paramExDao.getParamExs(pContext, pCriteria);
	}

	@Override
	public ParamEx getParamEx(IContext pContext, ParamExEnum pParameter) {

		return _paramExDao.getParamEx(pContext, pParameter);
	}

	@Override
	public <T> T getParamExValue(IContext pContext, ParamExEnum pParameter) {
		T res = null;
		ParamEx param = getParamEx(pContext, pParameter);
		if(param != null) {
			res = param.getValue();
		}
		return res;
	}

	@Override
	public <T> T getParamExValue(IContext pContext, ParamExEnum pParameter, T pDefault) {
		T res = getParamExValue(pContext, pParameter);
		return res != null ? res : pDefault;
	}

}
