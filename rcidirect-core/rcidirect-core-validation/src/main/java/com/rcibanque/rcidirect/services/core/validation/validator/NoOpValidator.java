package com.rcibanque.rcidirect.services.core.validation.validator;

import org.springframework.stereotype.Component;

import com.rcibanque.rcidirect.services.core.domain.IContext;

@Component("NoOpValidator")
public class NoOpValidator implements ICustomValidator {


	@Override
	public boolean isValid(IContext pContext, Object pReference, Object pValue) {

		return true;
	}

}