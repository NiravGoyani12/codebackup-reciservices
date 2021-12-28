package com.rcibanque.rcidirect.services.core.system.dao;

import java.util.List;

import com.rcibanque.rcidirect.services.core.domain.IContext;
import com.rcibanque.rcidirect.services.core.system.dao.criteria.ParamExCriteria;
import com.rcibanque.rcidirect.services.core.system.domain.ParamEx;
import com.rcibanque.rcidirect.services.core.system.domain.ParamExEnum;

public interface IParamExDao {


	ParamEx getParamEx(IContext pContext, ParamExEnum pParameter);

	List<ParamEx> getParamExs(IContext pContext, ParamExCriteria pCriteria);

}
