package com.rcibanque.rcidirect.services.core.domain;

import java.util.Date;

import com.rcibanque.rcidirect.services.core.utils.ConvertUtils;

public class DateTime extends Date {

	private static final long serialVersionUID = 1135664513261537685L;


	public DateTime() {
		super();
	}

	public DateTime(long pDate) {
		super(pDate);
	}

	public DateTime(String pVal) {
		this();
		Date date = ConvertUtils.parseDateTime(pVal);
		if(date != null) {
			setTime(date.getTime());
		}
	}

	public DateTime(Date pDate) {
		this();
		if(pDate != null) {
			setTime(pDate.getTime());
		}
	}

}
