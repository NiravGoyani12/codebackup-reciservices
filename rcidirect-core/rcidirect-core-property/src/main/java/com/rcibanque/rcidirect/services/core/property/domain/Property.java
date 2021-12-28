package com.rcibanque.rcidirect.services.core.property.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.rcibanque.rcidirect.services.core.domain.DTO;
import com.rcibanque.rcidirect.services.core.property.IProperty;
import com.rcibanque.rcidirect.services.core.property.dao.impl.PropertyMetaData;
import com.rcibanque.rcidirect.services.core.property.dao.impl.PropertyTableMetaData;
import com.rcibanque.rcidirect.services.core.utils.ConvertUtils;

public final class Property extends DTO {

	private static final long serialVersionUID = 2333430683619452262L;


	private PropertyMetaData _propertyMetaData;

	private Serializable _parentReferenceKey;

	private String _value;


	public Property() {
		super();
	}

	public Property(IProperty pProperty, Serializable pParentReferenceKey, String pValue) {
		super();
		_propertyMetaData = pProperty.getPropertyMetaData();
		_parentReferenceKey = pParentReferenceKey;
		_value = pValue;
	}


	private PropertyTableMetaData getPropertyTableMetaData() {
		return getPropertyMetaData().getPropertyTableMetaData();
	}

	public String getPropertyTableName() {
		return getPropertyTableMetaData().getPropertyTableName();
	}

	public String getParentReferenceColumnName() {
		return getPropertyTableMetaData().getParentReferenceColumnName();
	}


	public Serializable getParentReferenceKey() {
		return _parentReferenceKey;
	}

	public void setParentReferenceKey(Serializable pParentReferenceKey) {
		_parentReferenceKey = pParentReferenceKey;
	}


	public PropertyMetaData getPropertyMetaData() {
		return _propertyMetaData;
	}

	public void setPropertyMetaData(PropertyMetaData pPropertyMetaData) {
		_propertyMetaData = pPropertyMetaData;
	}


	public String getPropertyName() {
		return getPropertyMetaData().getPropertyName();
	}


	public String getValue() {
		return _value;
	}

	public void setValue(String pValue) {
		_value = pValue;
	}


	public Boolean getValueBoolean() {
		return ConvertUtils.parseBoolean(getValue());
	}

	public void setValueBoolean(Boolean pValue) {
		setValue(ConvertUtils.toString(pValue));
	}


	public Integer getValueInteger() {
		return ConvertUtils.parseInteger(getValue());
	}

	public void setValueInteger(Integer pValue) {
		setValue(ConvertUtils.toString(pValue));
	}


	public Long getValueLong() {
		return ConvertUtils.parseLong(getValue());
	}

	public void setValueLong(Long pValue) {
		setValue(ConvertUtils.toString(pValue));
	}


	public Double getValueDouble() {
		return ConvertUtils.parseDouble(getValue());
	}

	public void setValueDouble(Double pValue) {
		setValue(ConvertUtils.toString(pValue));
	}


	public BigDecimal getValueBigDecimal() {
		return ConvertUtils.parseBigDecimal(getValue());
	}

	public void setValueBigDecimal(BigDecimal pValue) {
		setValue(ConvertUtils.toString(pValue));
	}


	public Date getValueDate() {
		return ConvertUtils.parseDateProperties(getValue());
	}

	public void setValueDate(Date pValue) {
		setValue(ConvertUtils.toStringProperties(pValue));
	}


}
