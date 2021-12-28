package com.rcibanque.rcidirect.services.core.validation.tests.key;

import com.rcibanque.rcidirect.services.core.validation.key.IValidationField;

public enum TestFields implements IValidationField {

	FIELD_1,
	FIELD_2,
	FIELD_2B,
	FIELD_3,
	FIELD_4,
	FIELD_5,
	FIELD_6,

	FIELD_21,
	FIELD_22,
	FIELD_23,
	FIELD_24,
	FIELD_25,

	FIELD_31,
	FIELD_32,
	FIELD_33,

	FIELD_31b,
	FIELD_32b,
	FIELD_33b,

	FIELD_41,
	FIELD_42,
	FIELD_43,
	FIELD_44,
	FIELD_45,

	FIELD_51,
	FIELD_52,

	FIELD_61,
	FIELD_62,
	FIELD_63,
	FIELD_64,

	FIELD_71,
	FIELD_72,

	FIELD_81,
	FIELD_82,
	FIELD_83,

	FIELD_91,
	FIELD_92,
	FIELD_93,
	FIELD_94,
	FIELD_95,
	;

	@Override
	public String getDomainName() {
		return "DOMAIN";
	}

	@Override
	public String getFieldName() {
		return name();
	}

}
