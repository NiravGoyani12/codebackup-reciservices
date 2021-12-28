package com.rcibanque.rcidirect.clients.crif.domain;

import java.util.Date;

import com.rcibanque.rcidirect.services.core.domain.DTO;

public class Address extends DTO  {

	private static final long serialVersionUID = -4141618857077494436L;

	private Long _addressId;

	private String _houseNumber;

	private String _houseNumberExtension;

	private String _addressLine1;

	private String _addressLine2;

	private String _postcode;

	private String _city;

	private String _county;

	private String _country;

	private Date _moveInDate;

	public Long getAddressId() {
		return _addressId;
	}

	public void setAddressId(Long pAddressId) {
		_addressId = pAddressId;
	}

	public String getHouseNumber() {
		return _houseNumber;
	}

	public void setHouseNumber(String pHouseNumber) {
		_houseNumber = pHouseNumber;
	}

	public String getHouseNumberExtension() {
		return _houseNumberExtension;
	}

	public void setHouseNumberExtension(String pHouseNumberExtension) {
		_houseNumberExtension = pHouseNumberExtension;
	}

	public String getAddressLine1() {
		return _addressLine1;
	}

	public void setAddressLine1(String pAddressLine1) {
		_addressLine1 = pAddressLine1;
	}

	public String getAddressLine2() {
		return _addressLine2;
	}

	public void setAddressLine2(String pAddressLine2) {
		_addressLine2 = pAddressLine2;
	}

	public String getPostcode() {
		return _postcode;
	}

	public void setPostcode(String pPostcode) {
		_postcode = pPostcode;
	}

	public String getCity() {
		return _city;
	}

	public void setCity(String pCity) {
		_city = pCity;
	}

	public String getCounty() {
		return _county;
	}

	public void setCounty(String pCounty) {
		_county = pCounty;
	}

	public String getCountry() {
		return _country;
	}

	public void setCountry(String pCountry) {
		_country = pCountry;
	}

	public Date getMoveInDate() {
		return _moveInDate;
	}

	public void setMoveInDate(Date pMoveInDate) {
		_moveInDate = pMoveInDate;
	}


}
