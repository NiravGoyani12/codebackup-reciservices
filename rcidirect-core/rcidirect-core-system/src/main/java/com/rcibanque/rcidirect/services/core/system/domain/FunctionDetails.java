package com.rcibanque.rcidirect.services.core.system.domain;

import com.rcibanque.rcidirect.services.core.domain.DTO;

public class FunctionDetails extends DTO {

	private static final long serialVersionUID = -6826575587890755139L;


	private Function _function;

	private String _description;

	private Boolean _enabled;


	public Function getFunction() {
		return _function;
	}

	public void setFunction(Function pFunction) {
		_function = pFunction;
	}


	public String getDescription() {
		return _description;
	}

	public void setDescription(String pDescription) {
		_description = pDescription;
	}


	public Boolean getEnabled() {
		return _enabled;
	}

	public void setEnabled(Boolean pEnabled) {
		_enabled = pEnabled;
	}


}
