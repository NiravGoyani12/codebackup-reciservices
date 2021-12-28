package com.rcibanque.rcidirect.services.core.property.dao.impl;

import com.rcibanque.rcidirect.services.core.domain.DTO;
import com.rcibanque.rcidirect.services.core.property.IProperty;
import com.rcibanque.rcidirect.services.core.property.IPropertyTable;

public final class PropertyTableMetaData extends DTO implements IPropertyTable {

	private static final long serialVersionUID = -5744516283896222440L;


	private Class<? extends IProperty> _propertiesMetaDataEnum;

	private String _propertyTableName;

	private String _parentReferenceColumnName;

	private KeyType _parentReferenceColumnType;


	public PropertyTableMetaData(String pPropertyTableName, String pParentReferenceColumnName, KeyType pParentReferenceColumnType, Class<? extends IProperty> pPropertiesMetaDataEnum) {
		super();
		setPropertyTableName(pPropertyTableName);
		setParentReferenceColumnName(pParentReferenceColumnName);
		setParentReferenceColumnType(pParentReferenceColumnType);
		setPropertiesMetaDataEnum(pPropertiesMetaDataEnum);
	}


	@Override
	public PropertyTableMetaData getPropertyTableMetaData() {
		return this;
	}


	public String getPropertyTableName() {
		return _propertyTableName;
	}

	public void setPropertyTableName(String pPropertyTableName) {
		_propertyTableName = pPropertyTableName;
	}


	public String getParentReferenceColumnName() {
		return _parentReferenceColumnName;
	}

	public void setParentReferenceColumnName(String pParentReferenceColumnName) {
		_parentReferenceColumnName = pParentReferenceColumnName;
	}


	public KeyType getParentReferenceColumnType() {
		return _parentReferenceColumnType;
	}

	public void setParentReferenceColumnType(KeyType pParentReferenceColumnType) {
		_parentReferenceColumnType = pParentReferenceColumnType;
	}


	public Class<? extends IProperty> getPropertiesMetaDataEnum() {
		return _propertiesMetaDataEnum;
	}

	public void setPropertiesMetaDataEnum(Class<? extends IProperty> pPropertiesMetaDataEnum) {
		_propertiesMetaDataEnum = pPropertiesMetaDataEnum;
	}


	@Override
	public IProperty get(String pPropertyName) {
		IProperty res = null;
		for(IProperty property : _propertiesMetaDataEnum.getEnumConstants()) {
			if(property.getPropertyName().equals(pPropertyName)) {
				res = property;
				break;
			}
		}
		return res;
	}


}
