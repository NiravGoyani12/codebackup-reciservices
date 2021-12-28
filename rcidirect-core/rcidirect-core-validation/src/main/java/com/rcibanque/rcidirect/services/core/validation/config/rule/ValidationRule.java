package com.rcibanque.rcidirect.services.core.validation.config.rule;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.rcibanque.rcidirect.services.core.validation.detail.ValidationDetails;
import com.rcibanque.rcidirect.services.core.validation.key.ValidationKey;

@JsonPropertyOrder({ "field", "type", "stage", "format", "expectation", "refs", "refs_type", "message_key"})
@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public final class ValidationRule implements IValidationRule {


	private String _field; // IValidationField reference

	private String _type; // IValidationType reference

	private String _stage; // IProcessStage reference

	@JsonIgnore
	private final transient ValidationKey _validationKey;


	private String _format; // IValidationFormat reference

	private String _expectation; // Various usage (format regular expression, etc.)

	private List<ValidationRuleRef> _refs; // References

	private String _refsType; // References type (AND, OR)

	private String _messageKey;


	@JsonIgnore
	private final transient ValidationDetails _validationDetails;


	public ValidationRule() {
		super();
		_validationKey = new ValidationKey();
		_validationDetails = new ValidationDetails();
		_refs = new ArrayList<>();
	}


	public String getField() {
		return _field;
	}

	public void setField(String pFieldReference) {
		_field = pFieldReference;
	}

	public String getType() {
		return _type;
	}

	public void setType(String pTypeReference) {
		_type = pTypeReference;
	}

	public String getStage() {
		return _stage;
	}

	public void setStage(String pStageReference) {
		_stage = pStageReference;
	}


	@Override
	public ValidationKey getKey() {
		return _validationKey;
	}


	public String getFormat() {
		return _format;
	}

	public void setFormat(String pFormat) {
		_format = pFormat;
	}

	public String getExpectation() {
		return _expectation;
	}

	public void setExpectation(String pExpectation) {
		_expectation = pExpectation;
	}

	public List<ValidationRuleRef> getRefs() {
		return _refs;
	}

	public void setRefs(List<ValidationRuleRef> pRefs) {
		_refs = pRefs;
	}

	public String getRefsType() {
		return _refsType;
	}

	public void setRefsType(String pRefsType) {
		_refsType = pRefsType;
	}

	public String getMessageKey() {
		return _messageKey;
	}

	public void setMessageKey(String pMessageKey) {
		_messageKey = pMessageKey;
	}


	@Override
	public ValidationDetails getDetails() {
		return _validationDetails;
	}


}
