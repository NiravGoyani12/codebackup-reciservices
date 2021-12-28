package com.rcibanque.rcidirect.services.core.user.property;

import com.rcibanque.rcidirect.services.core.property.IProperty;
import com.rcibanque.rcidirect.services.core.property.dao.impl.PropertyMetaData;

public enum UserProperty implements IProperty {

	EMAIL_WHEN_NEW_MESSAGE("fl_newMessageEmail"),
	EMAIL_WHEN_PROPOSAL_EXPIRY("fl_proposalExpiryEmail"),
	PROFILE_ROLE("va_DealerProfileRoleCode"),
	LAST_LOGGED_IN("dt_lastLoggedIn"),
	NO_LOGIN_EXPIRY("fl_notDeactivateLogin");

	private PropertyMetaData _metadata;

	private UserProperty(String pPropertyName) {
		_metadata = new PropertyMetaData(UserPropertyTable.TABLE_META_DATA, pPropertyName);
	}

	@Override
	public PropertyMetaData getPropertyMetaData() {
		return _metadata;
	}

	@Override
	public String getPropertyName() {
		return _metadata.getPropertyName();
	}

}
