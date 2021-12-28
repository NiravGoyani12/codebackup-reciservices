package com.rcibanque.rcidirect.services.core.user.property;

import com.rcibanque.rcidirect.services.core.property.dao.impl.KeyType;
import com.rcibanque.rcidirect.services.core.property.dao.impl.PropertyTableMetaData;

public class UserPropertyTable {


	public static final PropertyTableMetaData TABLE_META_DATA = new PropertyTableMetaData("acteurs_prop", "ka_acteur", KeyType.STRING, UserProperty.class);


	private UserPropertyTable() {}

}
