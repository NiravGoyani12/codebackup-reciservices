package com.rcibanque.rcidirect.services.core.system.dao.criteria;

import com.rcibanque.rcidirect.services.core.dao.criteria.Criteria;
import com.rcibanque.rcidirect.services.core.system.domain.ParamExEnum;

public class ParamExCriteria extends Criteria {

	private static final long serialVersionUID = 449954923941230762L;


	public static ParamExCriteria getInstance(ParamExEnum pParamEx) {
		ParamExCriteria res = new ParamExCriteria();
		res.setParameters(new ParamExEnum[]{pParamEx});
		return res;
	}


	private ParamExEnum[] _parameters;


	public ParamExEnum[] getParameters() {
		return _parameters;
	}


	public void setParameters(ParamExEnum[] pParameters) {
		_parameters = pParameters;
	}


}
