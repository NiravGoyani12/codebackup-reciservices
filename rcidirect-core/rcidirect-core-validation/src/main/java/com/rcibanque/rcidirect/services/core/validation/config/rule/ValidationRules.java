package com.rcibanque.rcidirect.services.core.validation.config.rule;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "field_enum", "type_enum", "stage_enum", "format_enum", "rules" })
@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public final class ValidationRules {


	private String _fieldEnum; // IValidationField class reference

	private String _typeEnum; // IValidationType class reference

	private String _stageEnum; // IProcessStage class reference

	private String _formatEnum; // IValidationFormat class reference


	private List<ValidationRule> _rules;


	public String getFieldEnum() {
		return _fieldEnum;
	}

	public void setFieldEnum(String pFieldEnum) {
		_fieldEnum = pFieldEnum;
	}

	public String getTypeEnum() {
		return _typeEnum;
	}

	public void setTypeEnum(String pTypeEnum) {
		_typeEnum = pTypeEnum;
	}

	public String getStageEnum() {
		return _stageEnum;
	}

	public void setStageEnum(String pStageEnum) {
		_stageEnum = pStageEnum;
	}

	public String getFormatEnum() {
		return _formatEnum;
	}

	public void setFormatEnum(String pFormatEnum) {
		_formatEnum = pFormatEnum;
	}


	public List<ValidationRule> getRules() {
		return _rules;
	}

	public void setRules(List<ValidationRule> pRules) {
		_rules = pRules;
	}


}
