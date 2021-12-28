package com.rcibanque.rcidirect.services.core.property;

import com.rcibanque.rcidirect.services.core.property.dao.impl.PropertyMetaData;

public interface IProperty {


	PropertyMetaData getPropertyMetaData();

	String getPropertyName();

}
