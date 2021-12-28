package com.rcibanque.rcidirect.services.core.validation;

import java.util.HashMap;
import java.util.Map;

import com.rcibanque.rcidirect.services.core.validation.config.IValidationConfig;
import com.rcibanque.rcidirect.services.core.validation.key.IProcessStage;
import com.rcibanque.rcidirect.services.core.validation.key.IValidationField;
import com.rcibanque.rcidirect.services.core.validation.key.IValidationType;
import com.rcibanque.rcidirect.services.core.validation.key.ValidationKey;

/**
 * The validation context defines:
 * <ul>
 * <li>The validation configuration: the list of validation rules</li>
 * <li>The validation key: the validation rule being checked</li>
 * <li>The validation: the validation message created in case of non compliance</li>
 * <li>The reference: the object being validated</li>
 * <li>The reference key: the id of the object being validated</li>
 * </ul>
 */
public final class ValidationContext {


	private IValidationConfig _validationConfig;

	private final ValidationKey _validationKey;

	private IValidation _validation;

	private String _validationMessageParameter;

	private Object _reference;

	private String _referenceKey;

	private boolean _noDetails;

	private final Map<String, Object> _dynamicReferences;


	ValidationContext() {
		super();
		_validationKey = new ValidationKey();
		_dynamicReferences = new HashMap<>();
	}


	public IValidationConfig getValidationConfig() {
		return _validationConfig;
	}

	public void setValidationConfig(IValidationConfig pValidationConfig) {
		_validationConfig = pValidationConfig;
	}


	public ValidationKey getValidationKey() {
		return _validationKey;
	}


	public IValidationField getValidationField() {
		return getValidationKey().getField();
	}

	public void setValidationField(IValidationField pField) {
		getValidationKey().setField(pField);
	}


	public IProcessStage getProcessStage() {
		return getValidationKey().getStage();
	}

	public void setProcessStage(IProcessStage pProcessStage) {
		getValidationKey().setStage(pProcessStage);
	}


	public IValidationType getValidationType() {
		return getValidationKey().getType();
	}

	public void setValidationType(IValidationType pValidationType) {
		getValidationKey().setType(pValidationType);
	}


	public IValidation getValidation() {
		return _validation;
	}

	public void setValidation(IValidation pValidation) {
		_validation = pValidation;
	}


	public String getValidationMessageParameter() {
		return _validationMessageParameter;
	}

	public void setValidationMessageParameter(String pParameter) {
		_validationMessageParameter = pParameter;
	}


	/**
	 * @return The validated object
	 */
	public Object getReference() {
		return _reference;
	}

	/**
	 * @param pReference The validated object
	 */
	public void setReference(Object pReference) {
		_reference = pReference;
	}


	/**
	 * @return The ID of the validated object
	 */
	public String getReferenceKey() {
		return _referenceKey;
	}

	/**
	 * @param pReferenceKey The ID of the validated object
	 */
	public void setReferenceKey(String pReferenceKey) {
		_referenceKey = pReferenceKey;
		_dynamicReferences.clear();
	}


	public boolean getNoDetails() {
		return _noDetails;
	}

	public void setNoDetails(boolean pNoDetails) {
		_noDetails = pNoDetails;
	}


	/**
	 * @param pReferenceService The service used to load the dynamic validated object
	 * @param pDynamicReference The dynamic validated object
	 */
	public void addDynamicReference(String pReferenceService, Object pDynamicReference) {
		_dynamicReferences.put(pReferenceService, pDynamicReference);
	}

	/**
	 * @param pReferenceService The service used to load the dynamic validated object
	 * @return True, if the dynamic validated object has already been loaded
	 */
	public boolean containsDynamicReference(String pReferenceService) {
		return _dynamicReferences.containsKey(pReferenceService);
	}

	/**
	 * @param pReferenceService The service used to load the dynamic validated object
	 * @return The dynamic validated object
	 */
	public Object getDynamicReference(String pReferenceService) {
		return _dynamicReferences.get(pReferenceService);
	}

}
