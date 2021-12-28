package com.rcibanque.rcidirect.clients.crif.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum ActorRoles {

	CUSTOMER(4),
	COSIGNER(5);

	private final Integer _roleCode;

	private ActorRoles(Integer pRoleCode) {
		_roleCode = pRoleCode;
	}

	@JsonValue
	public Integer getRoleCode() {
		return _roleCode;
	}

	@JsonCreator
	public static ActorRoles get(Integer pValue) {
		ActorRoles res = null;
		for(ActorRoles mode : values()) {
			if(mode.getRoleCode().equals(pValue)) {
				res = mode;
				break;
			}
		}
		return res;
	}

}
