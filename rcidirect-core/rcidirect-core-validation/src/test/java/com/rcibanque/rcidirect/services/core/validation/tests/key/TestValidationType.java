package com.rcibanque.rcidirect.services.core.validation.tests.key;

import com.rcibanque.rcidirect.services.core.validation.key.IValidationType;

public enum TestValidationType implements IValidationType {

	REQUIRED(IValidationType.REQUIRED.getCode()),
	REQUIRED_IF_REF(IValidationType.REQUIRED_IF_REF.getCode()),
	FORMAT(IValidationType.FORMAT.getCode()),
	CUSTOM(IValidationType.CUSTOM.getCode()),
	DATE_VS_REF(IValidationType.DATE_VS_REF.getCode()),
	DATE_VS_TODAY(IValidationType.DATE_VS_TODAY.getCode()),
	DATE_VS_RANGE(IValidationType.DATE_VS_RANGE.getCode());

	private final Integer _type;

	private TestValidationType(Integer pType) {
		_type = pType;
	}

	@Override
	public Integer getCode() {
		return _type;
	}

}
