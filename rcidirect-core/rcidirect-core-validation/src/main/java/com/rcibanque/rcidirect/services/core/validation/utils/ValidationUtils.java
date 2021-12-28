package com.rcibanque.rcidirect.services.core.validation.utils;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rcibanque.rcidirect.services.core.domain.IContext;
import com.rcibanque.rcidirect.services.core.exception.ExceptionUtils;
import com.rcibanque.rcidirect.services.core.exception.ValidationException;
import com.rcibanque.rcidirect.services.core.validation.key.IProcessStage;
import com.rcibanque.rcidirect.services.core.validation.validator.IValidator;

public final class ValidationUtils {

	private static final Logger LOGGER = LoggerFactory.getLogger(ValidationUtils.class);


	private ValidationUtils() {}


	/**
	 * @param pContext Context
	 * @param pValidator Validator
	 * @param pObject Object to validate
	 * @param pProcessStage Process stage
	 *
	 * @see #validate(IContext, IValidator, List, IProcessStage)
	 */
	public static final <T> void validate(IContext pContext, IValidator<T> pValidator, T pObject, IProcessStage pProcessStage) {

		validate(pContext, pValidator, Arrays.asList(pObject), pProcessStage);
	}


	/**
	 * If validation fails, errors are added to the context and <b>a {@link ValidationException} is thrown</b> (after validating all objects).
	 *
	 * @param pContext Context
	 * @param pValidator Validator
	 * @param pObjects Objects to validate
	 * @param pProcessStage Process stage
	 */
	public static final <T> void validate(IContext pContext, IValidator<T> pValidator, List<T> pObjects, IProcessStage pProcessStage) {

		for(T object : pObjects) {
			invokeValidator(pContext, object, pValidator, pProcessStage);
		}

		if(pContext.getMessages().hasError()){
			ExceptionUtils.throwValidationException(pContext);
		}
	}


	/**
	 * If validation fails, errors are added to the context, but <b>no {@link ValidationException} is thrown</b>.
	 *
	 * @param pContext Context
	 * @param pObject Tested object
	 * @param pValidator Validator
	 * @param pProcessStage Process stage
	 */
	public static final <T> void invokeValidator(IContext pContext, T pObject, IValidator<T> pValidator, IProcessStage pProcessStage) {

		LOGGER.debug("Validation ({}) - START", pValidator);

		pValidator.validate(pContext, pObject, pProcessStage);

		LOGGER.debug("Validation ({}) - END", pValidator);
	}

}
