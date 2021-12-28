package com.rcibanque.rcidirect.clients.crif.domain;

import com.rcibanque.rcidirect.services.core.domain.DTO;

public class ActorType extends DTO {

	private static final long serialVersionUID = -1710447496312741477L;

	private Integer _actorCategoryCode;

	private String _actorCategoryLabel;

	private Integer _actorTypeCode;

	private String _actorTypeLabel;

	public Integer getActorCategoryCode() {
		return _actorCategoryCode;
	}

	public void setActorCategoryCode(Integer pActorCategory) {
		_actorCategoryCode = pActorCategory;
	}

	public String getActorCategoryLabel() {
		return _actorCategoryLabel;
	}

	public void setActorCategoryLabel(String pActorCategoryLabel) {
		_actorCategoryLabel = pActorCategoryLabel;
	}

	public Integer getActorTypeCode() {
		return _actorTypeCode;
	}

	public void setActorTypeCode(Integer pActorTypeCode) {
		_actorTypeCode = pActorTypeCode;
	}

	public String getActorTypeLabel() {
		return _actorTypeLabel;
	}

	public void setActorTypeLabel(String pActorCategoryLabel) {
		_actorTypeLabel = pActorCategoryLabel;
	}
}
