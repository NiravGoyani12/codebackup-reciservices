package com.rcibanque.rcidirect.services.core.system.service;

import java.util.List;

import com.rcibanque.rcidirect.services.core.domain.IContext;
import com.rcibanque.rcidirect.services.core.system.dao.criteria.ParamCriteria;
import com.rcibanque.rcidirect.services.core.system.dao.criteria.ParamExCriteria;
import com.rcibanque.rcidirect.services.core.system.domain.Param;
import com.rcibanque.rcidirect.services.core.system.domain.ParamEnum;
import com.rcibanque.rcidirect.services.core.system.domain.ParamEx;
import com.rcibanque.rcidirect.services.core.system.domain.ParamExEnum;

public interface IParameterService {


	List<Param> getParams(IContext pContext, ParamCriteria pCriteria);

	Param getParam(IContext pContext, ParamEnum pParameter);

	<T> T getParamValue(IContext pContext, ParamEnum pParameter);

	<T> T getParamValue(IContext pContext, ParamEnum pParameter, T pDefault);


	List<ParamEx> getParamExs(IContext pContext, ParamExCriteria pCriteria);

	ParamEx getParamEx(IContext pContext, ParamExEnum pParameter);

	<T> T getParamExValue(IContext pContext, ParamExEnum pParameter);

	<T> T getParamExValue(IContext pContext, ParamExEnum pParameter, T pDefault);

}
