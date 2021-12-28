package com.rcibanque.rcidirect.services.core.system.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.rcibanque.rcidirect.services.core.domain.DTO;

public class Param extends DTO {

	private static final long serialVersionUID = 4211248748590202365L;


	private ParamEnum _param;

	private String _paramLabel;

	private String _paramString;

	private Integer _paramInteger;

	private BigDecimal _paramAmount;

	private Boolean _paramBoolean;

	private Date _paramDate;

	private Double _paramPercentage;


	public Param() {
		super();
	}

	public Param(ParamEnum pParam, String pParamLabel,
			String pParamString, Integer pParamInteger, BigDecimal pParamAmount,
			Boolean pParamBoolean, Date pParamDate, Double pParamPercentage) {
		super();
		_param = pParam;
		_paramLabel = pParamLabel;
		_paramString = pParamString;
		_paramInteger = pParamInteger;
		_paramAmount = pParamAmount;
		_paramBoolean = pParamBoolean;
		_paramDate = pParamDate;
		_paramPercentage = pParamPercentage;
	}


	public ParamEnum getParam() {
		return _param;
	}

	public void setParam(ParamEnum pParam) {
		_param = pParam;
	}


	@SuppressWarnings("unchecked")
	public <T> T getValue() {

		T res = null;

		if(_param != null)
			switch(_param.getParamType()) {
			case STRING:
				res = (T) getParamString();
				break;
			case INTEGER:
				res = (T) getParamInteger();
				break;
			case AMOUNT:
				res = (T) getParamAmount();
				break;
			case DATE:
				res = (T) getParamDate();
				break;
			case BOOLEAN:
				res = (T) getParamBoolean();
				break;
			case PERCENTAGE_RATE:
				res = (T) getParamPercentage();
				break;
			default:
				throw new UnsupportedOperationException("Unsupported param type");
			}

		return res;
	}


	public String getParamLabel() {
		return _paramLabel;
	}

	public void setParamLabel(String pParamLabel) {
		_paramLabel = pParamLabel;
	}


	public String getParamString() {
		return _paramString;
	}

	public void setParamString(String pParamString) {
		_paramString = pParamString;
	}


	public Integer getParamInteger() {
		return _paramInteger;
	}

	public void setParamInteger(Integer pParamInteger) {
		_paramInteger = pParamInteger;
	}


	public BigDecimal getParamAmount() {
		return _paramAmount;
	}

	public void setParamAmount(BigDecimal pParamAmount) {
		_paramAmount = pParamAmount;
	}


	public Boolean getParamBoolean() {
		return _paramBoolean;
	}

	public void setParamBoolean(Boolean pParamBoolean) {
		_paramBoolean = pParamBoolean;
	}


	public Date getParamDate() {
		return _paramDate;
	}

	public void setParamDate(Date pParamDate) {
		_paramDate = pParamDate;
	}


	public Double getParamPercentage() {
		return _paramPercentage;
	}

	public void setParamPercentage(Double pParamPercentage) {
		_paramPercentage = pParamPercentage;
	}


}
