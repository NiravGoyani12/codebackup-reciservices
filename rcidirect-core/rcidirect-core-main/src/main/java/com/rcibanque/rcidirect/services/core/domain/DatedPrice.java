package com.rcibanque.rcidirect.services.core.domain;

import java.util.Date;

public class DatedPrice extends DTO {

	private static final long serialVersionUID = 1L;


	private Date _date;

	private Price _price;


	public Date getDate() {
		return _date;
	}

	public void setDate(Date pDate) {
		_date = pDate;
	}


	public Price getPrice() {
		return _price;
	}

	public void setPrice(Price pPrice) {
		_price = pPrice;
	}


}
