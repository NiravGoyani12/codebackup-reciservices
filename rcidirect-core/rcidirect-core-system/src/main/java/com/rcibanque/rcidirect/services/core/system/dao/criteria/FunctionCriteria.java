package com.rcibanque.rcidirect.services.core.system.dao.criteria;

import com.rcibanque.rcidirect.services.core.dao.criteria.Criteria;

public class FunctionCriteria extends Criteria {

	private static final long serialVersionUID = -8419126948908468340L;


	public static FunctionCriteria getInstance(Integer pFunctionCode) {
		FunctionCriteria res = new FunctionCriteria();
		res.setFunctionCodes(new Integer[]{pFunctionCode});
		return res;
	}


	private Integer[] _functionCodes;


	public Integer[] getFunctionCodes() {
		return _functionCodes;
	}


	public void setFunctionCodes(Integer[] pFunctionCodes) {
		_functionCodes = pFunctionCodes;
	}


}
