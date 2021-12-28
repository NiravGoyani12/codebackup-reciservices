package com.rcibanque.rcidirect.services.core.system.dao.criteria;

import com.rcibanque.rcidirect.services.core.dao.criteria.Criteria;

public class LabelCriteria extends Criteria {

	private static final long serialVersionUID = 6574261036221971045L;


	private Integer _labelCode;

	private Integer _labelTypeCode;

	private Integer _entityCodeInteger;

	private String _entityCodeString;

	private String _financialCompanyCode;

	private Integer _labelCategoryCode;

	private String _label;


	public Integer getLabelCode() {
		return _labelCode;
	}

	public void setLabelCode(Integer pLabelCode) {
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
