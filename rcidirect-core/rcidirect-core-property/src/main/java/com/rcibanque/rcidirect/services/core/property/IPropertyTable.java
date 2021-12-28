package com.rcibanque.rcidirect.services.core.property;

import com.rcibanque.rcidirect.services.core.property.dao.impl.PropertyTableMetaData;

public interface IPropertyTable {


	PropertyTableMetaData getPropertyTableMetaData();

	IProperty get(String pPropertyName);

}
