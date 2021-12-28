package com.rcibanque.rcidirect.services.core.system.domain;

import com.rcibanque.rcidirect.services.core.domain.DTO;

public class Label extends DTO {

	private static final long serialVersionUID = -158922897530654314L;


	private Long _labelCode;

	private Integer _labelTypeCode;

	private Integer _entityCodeInteger;

	private String _entityCodeString;

	private String _financialCompanyCode;

	private Integer _labelCategoryCode;

	private String _label;


	public Long getLabelCode() {
		return _labelCode;
	}

	public void setLabelCode(Long pLabelCode) {
		_labelCode = pLabelCode;
	}

	public Integer getLabelTypeCode() {
		return _labelTypeCode;
	}

	public void setLabelTypeCode(Integer pLabelTypeCode) {
		_labelTypeCode = pLabelTypeCode;
	}

	public Integer getEntityCodeInteger() {
		return _entityCodeInteger;
	}

	public void setEntityCodeInteger(Integer pEntityCodeInteger) {
		_entityCodeInteger = pEntityCodeInteger;
	}

	public String getEntityCodeString() {
		return _entityCodeString;
	}

	public void setEntityCodeString(String pEntityCodeString) {
		_entityCodeString = pEntityCodeString;
	}

	public String getFinancialCompanyCode() {
		return _financialCompanyCode;
	}

	public void setFinancialCompanyCode(String pFinancialCompanyCode) {
		_financialCompanyCode = pFinancialCompanyCode;
	}

	public Integer getLabelCategoryCode() {
		return _labelCategoryCode;
	}

	public void setLabelCategoryCode(Integer pLabelCategoryCode) {
		_labelCategoryCode = pLabelCategoryCode;
	}

	public String getLabel() {
		return _label;
	}

	public void setLabel(String pLabel) {
		_label = pLabel;
	}


}

