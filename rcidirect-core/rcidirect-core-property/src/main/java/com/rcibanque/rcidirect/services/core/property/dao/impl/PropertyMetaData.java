package com.rcibanque.rcidirect.services.core.property.dao.impl;

import com.rcibanque.rcidirect.services.core.domain.DTO;
import com.rcibanque.rcidirect.services.core.property.IProperty;

public final class PropertyMetaData extends DTO implements IProperty {

	private static final long serialVersionUID = -8594285126332857122L;


	private PropertyTableMetaData _propertyTableMetaData;

	private String _propertyName;


	public PropertyMetaData(PropertyTableMetaData pPropertyTableMetaData, String pPropertyName) {
		super();
		setPropertyTableMetaData(pPropertyTableMetaData);
		setPropertyName(pPropertyName);
	}


	@Override
	public PropertyMetaData getPropertyMetaData() {
		return this;
	}

	public PropertyTableMetaData getPropertyTableMetaData() {
		return _propertyTableMetaData;
	}

	public void setPropertyTableMetaData(PropertyTableMetaData pPropertyTableMetaData) {
		_propertyTableMetaData = pPropertyTableMetaData;
	}


	@Override
	public String getPropertyName() {
		return _propertyName;
	}

	public void setPropertyName(String pPropertyName) {
		_propertyName = pPropertyName;
	}


}
