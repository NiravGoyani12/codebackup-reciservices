package com.rcibanque.rcidirect.services.core.validation.tests.key;

import com.rcibanque.rcidirect.services.core.validation.key.IProcessStage;

public enum TestProcessStage implements IProcessStage {

	ALL(IProcessStage.ALL.getCode());

	private final Integer _stage;

	private TestProcessStage(Integer pStage) {
		_stage = pStage;
	}

	@Override
	public Integer getCode() {
		return _stage;
	}

}
