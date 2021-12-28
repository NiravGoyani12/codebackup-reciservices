package com.rcibanque.rcidirect.services.core.property.utils;

import com.rcibanque.rcidirect.services.core.property.IProperty;
import com.rcibanque.rcidirect.services.core.property.dao.impl.PropertyMetaData;
import com.rcibanque.rcidirect.services.core.property.dao.impl.PropertyTableMetaData;

public class PropertyUtils {

	private PropertyUtils() {}


	public static final String getFullName(IProperty pProperty) {

		PropertyMetaData pmd = pProperty.getPropertyMetaData();
		PropertyTableMetaData ptmd = pmd.getPropertyTableMetaData();

		return ptmd.getPropertyTableName() + "." + pmd.getPropertyName();
	}


}
