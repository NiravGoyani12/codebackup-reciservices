package com.rcibanque.rcidirect.clients.crif.domain;

public enum IdentificationType {

	ID_CODE_PRIVATE_INDIVIDUAL(1, "1");

	private final Integer _actorTypeCode;

	private final String _idTypeCode;

	private IdentificationType(Integer pActorTypeCode, String pIdTypeCode) {
		_actorTypeCode = pActorTypeCode;
		_idTypeCode = pIdTypeCode;
	}

	public Integer getActorTypeCode() {
		return _actorTypeCode;
	}

	public String getIdTypeCode() {
		return _idTypeCode;
	}

	public static IdentificationType valueOf(Integer pActorTypeCode) {
		IdentificationType res = null;
		for (IdentificationType p : values()) {
			if (p.getActorTypeCode().equals(pActorTypeCode)) {
				res = p;
				break;
			}
		}
		return res;
	}

}
