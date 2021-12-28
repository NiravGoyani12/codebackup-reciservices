package com.rcibanque.rcidirect.services.core.validation.validator;

import org.springframework.stereotype.Component;

import com.rcibanque.rcidirect.services.core.domain.IContext;

@Component("IBANCustomValidator")
public class IBANValidator implements ICustomValidator {

	private static final org.apache.commons.validator.routines.IBANValidator IBAN_VALIDATOR = new org.apache.commons.validator.routines.IBANValidator();


	@Override
	public boolean isValid(IContext pContext, Object pReference, Object pValue) {

		return pValue != null
				&& pValue.getClass().equals(String.class)
				&& IBAN_VALIDATOR.isValid(pValue.toString());
	}

}