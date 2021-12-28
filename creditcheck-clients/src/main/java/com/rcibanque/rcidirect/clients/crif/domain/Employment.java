package com.rcibanque.rcidirect.clients.crif.domain;

import com.rcibanque.rcidirect.services.core.domain.DTO;

public class Employment extends DTO {

	private static final long serialVersionUID = -494693043532818998L;

	private String _occupationCode;

	private String _taxNumber;

	public String getOccupationCode() {
		return _occupationCode;
	}

	public void setOccupationCode(String pOccupationCode) {
		_occupationCode = pOccupationCode;
	}

	public String getTaxNumber() {
		return _taxNumber;
	}

	public void setTaxNumber(String pTaxNumber) {
		this._taxNumber = pTaxNumber;
	}

}
