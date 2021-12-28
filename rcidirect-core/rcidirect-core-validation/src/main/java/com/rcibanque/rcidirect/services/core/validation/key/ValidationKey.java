package com.rcibanque.rcidirect.services.core.validation.key;

public final class ValidationKey {


	private IValidationField _validationField;

	private IValidationType _validationType;

	private IProcessStage _processStage;


	public IValidationField getField() {
		return _validationField;
	}

	public void setField(IValidationField pValidationField) {
		_validationField = pValidationField;
	}


	public IValidationType getType() {
		return _validationType;
	}

	public void setType(IValidationType pValidationType) {
		_validationType = pValidationType;
	}


	public IProcessStage getStage() {
		return _processStage;
	}

	public void setStage(IProcessStage pProcessStage) {
		_processStage = pProcessStage;
	}


	@Override
	public final boolean equals(Object obj) {

		if(obj == null || obj.getClass() != this.getClass()) {
			return false;
		}

		boolean res;

		ValidationKey key = (ValidationKey) obj;

		// Field
		res = key.getField().getDomainName().equals(getField().getDomainName());
		res = res && key.getField().getFieldName().equals(getField().getFieldName());

		// Type
		res = res && key.getType().getCode().equals(getType().getCode());

		// Stage
		res = res && (
				(key.getStage() == null && getStage() == null)
				|| (key.getStage() == null && getStage() != null && getStage().getCode().equals(IProcessStage.ALL.getCode()))
				|| (getStage() == null && key.getStage() != null && key.getStage().getCode().equals(IProcessStage.ALL.getCode()))
				|| (key.getStage().getCode().equals(getStage().getCode()))
				);

		return res;
	}


	@Override
	public final String toString() {

		StringBuilder sb = new StringBuilder();

		if(getField() != null) {
			sb.append("Field: ").append(getField().getDomainName()).append(" / ").append(getField().getFieldName()).append(" ; ");
		}
		if(getType() != null) {
			sb.append("Type: ").append(getType().getCode()).append(" ; ");
		}
		if(getStage() != null) {
			sb.append("Stage: ").append(getStage().getCode());
		}

		return sb.toString();
	}

}
