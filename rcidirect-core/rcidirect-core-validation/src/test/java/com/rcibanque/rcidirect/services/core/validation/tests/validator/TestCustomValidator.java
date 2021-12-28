package com.rcibanque.rcidirect.services.core.validation.tests.validator;

import com.rcibanque.rcidirect.services.core.domain.IContext;
import com.rcibanque.rcidirect.services.core.validation.validator.ICustomValidator;

public class TestCustomValidator implements ICustomValidator {


	@Override
	public boolean isValid(IContext pContext, Object pReference, Object pValue) {

		return pValue.equals("OK");
	}

}