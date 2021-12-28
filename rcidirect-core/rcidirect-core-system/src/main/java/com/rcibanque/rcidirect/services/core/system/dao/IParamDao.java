package com.rcibanque.rcidirect.services.core.system.dao;

import java.util.List;

import com.rcibanque.rcidirect.services.core.domain.IContext;
import com.rcibanque.rcidirect.services.core.system.dao.criteria.ParamCriteria;
import com.rcibanque.rcidirect.services.core.system.domain.Param;
import com.rcibanque.rcidirect.services.core.system.domain.ParamEnum;

public interface IParamDao {


	Param getParam(IContext pContext, ParamEnum pParameter);

	List<Param> getParams(IContext pContext, ParamCriteria pCriteria);

}
