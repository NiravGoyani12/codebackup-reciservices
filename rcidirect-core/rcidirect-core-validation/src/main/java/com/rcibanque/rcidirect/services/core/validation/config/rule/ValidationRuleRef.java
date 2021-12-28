package com.rcibanque.rcidirect.services.core.validation.config.rule;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.rcibanque.rcidirect.services.core.validation.key.IValidationField;

@JsonPropertyOrder({ "ref_service", "ref", "ref_field", "values", "expectation"})
@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public final class ValidationRuleRef {


	private String _refService; // Other field reference service: provides the referenced object

	private String _ref; // Other field referenced object attribute

	private String _refField; // Other field IValidationField

	@JsonIgnore
	private transient IValidationField _field;

	private List<String> _values; // Other field values

	@JsonIgnore
	private List<BigDecimal> _numericValues;

	private String _expectation; // Various usage



	public String getRefService() {
		return _refService;
	}

	public void setRefService(String pRefService) {
		_refService = pRefService;
	}


	public String getRef() {
		return _ref;
	}

	public void setRef(String pRef) {
		_ref = pRef;
	}


	public String getRefField() {
		return _refField;
	}

	public void setRefField(String pRefField) {
		_refField = pRefField;
	}


	public IValidationField getField() {
		return _field;
	}

	public void setField(IValidationField pField) {
		_field = pField;
	}


	public List<String> getValues() {
		return _values;
	}

	public void setValues(List<String> pValues) {
		_values = pValues;
	}

	public List<BigDecimal> getNumericValues() {
		return _numericValues;
	}

	public void setNumericValues(List<BigDecimal> pNumericValues) {
		_numericValues = pNumericValues;
	}


	public String getExpectation() {
		return _expectation;
	}

	public void setExpectation(String pExpectation) {
		_expectation = pExpectation;
	}


}
