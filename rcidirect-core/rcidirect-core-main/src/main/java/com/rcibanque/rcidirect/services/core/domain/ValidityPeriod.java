package com.rcibanque.rcidirect.services.core.domain;

import java.time.Period;

public final class ValidityPeriod extends DTO {

	private static final long serialVersionUID = 1L;


	private final Period _from;

	private final Period _to;


	public ValidityPeriod(Period pFrom, Period pTo) {
		_from = pFrom;
		_to = pTo;
	}


	public Period getFrom() {
		return _from;
	}

	public Period getTo() {
		return _to;
	}

}
