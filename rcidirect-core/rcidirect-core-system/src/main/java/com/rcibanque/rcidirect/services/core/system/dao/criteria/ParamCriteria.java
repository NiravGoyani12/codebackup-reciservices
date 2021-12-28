package com.rcibanque.rcidirect.services.core.system.dao.criteria;

import com.rcibanque.rcidirect.services.core.dao.criteria.Criteria;
import com.rcibanque.rcidirect.services.core.system.domain.ParamEnum;

public class ParamCriteria extends Criteria {

	private static final long serialVersionUID = -4331550059043999602L;


	public static ParamCriteria getInstance(ParamEnum pParameter) {
		ParamCriteria res = new ParamCriteria();
		res.setParameters(new ParamEnum[]{pParameter});
		return res;
	}


	private ParamEnum[] _parameters;


	public ParamEnum[] getParameters() {
		return _parameters;
	}


	public void setParameters(ParamEnum[] pParameters) {
		_parameters = pParameters;
	}


}
