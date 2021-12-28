package com.rcibanque.rcidirect.services.core.system.domain;

import java.math.BigDecimal;
import java.util.Date;

public enum ParamType {

	STRING(String.class),
	INTEGER(Integer.class),
	AMOUNT(BigDecimal.class),
	DATE(Date.class),
	BOOLEAN(Boolean.class),
	PERCENTAGE_RATE(Double.class);


	private final Class<?> _class;


	private ParamType(Class<?> pClass) {
		_class = pClass;
	}


	public Class<?> getParamType() {
		return _class;
	}

}
