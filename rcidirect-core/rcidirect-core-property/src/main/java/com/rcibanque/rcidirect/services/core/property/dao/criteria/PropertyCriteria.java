package com.rcibanque.rcidirect.services.core.property.dao.criteria;

import java.io.Serializable;
import java.util.Collection;

import com.rcibanque.rcidirect.services.core.dao.criteria.Criteria;
import com.rcibanque.rcidirect.services.core.property.dao.impl.PropertyMetaData;
import com.rcibanque.rcidirect.services.core.property.dao.impl.PropertyTableMetaData;

public final class PropertyCriteria extends Criteria {

	private static final long serialVersionUID = 784949457685335564L;


	private PropertyTableMetaData _propertyTableMetaData;

	private PropertyMetaData _propertyMetaData;

	private Serializable _parentReferenceKey;

	private Collection<Serializable> _parentReferenceKeys;

	private String _value;


	public PropertyTableMetaData getPropertyTableMetaData() {
		return _propertyTableMetaData != null ? _propertyTableMetaData : _propertyMetaData != null ? _propertyMetaData.getPropertyTableMetaData() : null;
	}

	public void setPropertyTableMetaData(PropertyTableMetaData pPropertyTableMetaData) {
		_propertyTableMetaData = pPropertyTableMetaData;
	}


	public Serializable getParentReferenceKey() {
		return _parentReferenceKey;
	}

	public void setParentReferenceKey(Serializable pParentReferenceKey) {
		_parentReferenceKey = pParentReferenceKey;
	}


	public Collection<Serializable> getParentReferenceKeys() {
		return _parentReferenceKeys;
	}

	public void setParentReferenceKeys(Collection<Serializable> pParentReferenceKeys) {
		_parentReferenceKeys = pParentReferenceKeys;
	}


	public PropertyMetaData getPropertyMetaData() {
		return _propertyMetaData;
	}

	public void setPropertyMetaData(PropertyMetaData pPropertyMetaData) {
		_propertyMetaData = pPropertyMetaData;
	}


	public String getValue() {
		return _value;
	}

	public void setValue(String pValue) {
		_value = pValue;
	}


}
