package com.rcibanque.rcidirect.services.core.user.domain;

public enum UserProfile {


	EV(1),
	DEALER_SERVICES_MANAGER(2),
	OPS_DEVELOPMENT(3),
	OPERATIONS_DIRECTOR(4),
	FINANCIAL_SOLUTIONS(10),
	CUSTOMER_SERVICE(12),
	CUSTOMER_SERVICE_MANAGER(16),
	FINANCE(17),
	SALES_EXECUTIVE(20),
	UNDERWRITER(21),
	BUSINESS_MANAGER(23),
	SYSTEM_ADMINISTRATOR(24),
	MARKETING(25),
	RCI_ACCOUNT_MANAGER(26),
	DEALER_SERVICES_LEADER(27),
	IS_PROFILE(33),
	USER_CREATOR(34),
	GROUP_MANAGER(35),
	MARKETING_PRICING(38);


	private final Integer _profileCode; // paprofil.kcd_profil


	private UserProfile(Integer pProfileCode) {
		_profileCode = pProfileCode;
	}


	public Integer getProfileCode() {
		return _profileCode;
	}


	public boolean equalCodes(Integer pProfileCode) {
		return getProfileCode().equals(pProfileCode);
	}


	public static UserProfile get(Integer pProfileCode) {
		UserProfile res = null;
		for(UserProfile profile : UserProfile.values()) {
			if(profile.getProfileCode().equals(pProfileCode)) {
				res = profile;
				break;
			}
		}
		return res;
	}
}
