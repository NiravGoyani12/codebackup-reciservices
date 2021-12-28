package com.rcibanque.rcidirect.services.core.validation;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

import com.rcibanque.rcidirect.services.core.validation.config.IValidationConfig;
import com.rcibanque.rcidirect.services.core.validation.key.IProcessStage;
import com.rcibanque.rcidirect.services.core.validation.key.IValidationField;
import com.rcibanque.rcidirect.services.core.validation.validator.AbstractValidator;

/**
 * {@link ValidationContext} builder
 */
public final class ValidationBuilder {


	private final ValidationContext _validationContext;


	public ValidationBuilder() {

		_validationContext = new ValidationContext();
	}


	public ValidationBuilder withConfig(IValidationConfig pConfig) {
		_validationContext.setValidationConfig(pConfig);
		return this;
	}

	public ValidationBuilder addToValidation(IValidation pValidation) {
		_validationContext.setValidation(pValidation);
		return this;
	}

	public ValidationBuilder withValidationMessageParameter(String pParameter) {
		_validationContext.setValidationMessageParameter(pParameter);
		return this;
	}

	public ValidationBuilder forField(IValidationField pValidationField) {
		_validationContext.setValidationField(pValidationField);
		return this;
	}

	/**
	 * @param pReferenceKey The ID of the validated object
	 * @return The builder
	 */
	public ValidationBuilder withReferenceKey(String pReferenceKey) {
		_validationContext.setReferenceKey(pReferenceKey);
		return this;
	}

	/**
	 * @param pReference The validated object
	 * @return The builder
	 */
	public ValidationBuilder withReference(Object pReference) {
		_validationContext.setReference(pReference);
		return this;
	}

	public ValidationBuilder inProcessStage(IProcessStage pProcessStage) {
		_validationContext.setProcessStage(pProcessStage);
		return this;
	}

	public ValidationBuilder noDetails() {
		_validationContext.setNoDetails(true);
		return this;
	}


	public ValidationContext build() {

		Assert.notNull(_validationContext.getValidationConfig(), "Validation configuration is null");
		Assert.notNull(_validationContext.getValidation(), "Validation is null");
		Assert.notNull(_validationContext.getValidationField(), "Validation field is null");
		Assert.notNull(_validationContext.getProcessStage(), "Process stage is null");

		if (StringUtils.isBlank(_validationContext.getReferenceKey())) {
			withReferenceKey(AbstractValidator.NO_IDENTIFIER);
		}

		return _validationContext;
	}

}
