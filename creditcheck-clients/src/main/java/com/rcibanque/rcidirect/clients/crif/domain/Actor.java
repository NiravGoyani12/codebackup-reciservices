package com.rcibanque.rcidirect.clients.crif.domain;

import java.util.Date;

import com.rcibanque.rcidirect.services.core.domain.DTO;

public class Actor extends DTO {

	private static final long serialVersionUID = 635690133550019174L;

	private ActorRoles _actorRole;

	private String _actorCode;

	private ActorType _actorType;

	private String _name;

	private String _landline;

	private String _mobile;

	private String _email;

	private Date _creationDate;

	private Integer _titleCode;

	private String _initials;

	private String _firstName;

	private String _maidenName;

	private String _prefixName;

	private String _previousPrefixName;

	private Integer _genderCode;

	private String _maritalStatusCode;

	private Date _dateOfBirth;

	private String _nationality;

	private String _nationalRegisterNo;

	private String _tradingName;

	private String _identificationNo;

	private String _registrationNumber;

	private String _vatNumber;

	private String _legalNameCode;

	private String _businessRegistrationNumber;

	public ActorRoles getActorRole() {
		return _actorRole;
	}

	public void setActorRole(ActorRoles pActorRole) {
		_actorRole = pActorRole;
	}

	public String getActorCode() {
		return _actorCode;
	}

	public void setActorCode(String pActorCode) {
		_actorCode = pActorCode;
	}

	public ActorType getActorType() {
		return _actorType;
	}

	public void setActorType(ActorType pActorType) {
		_actorType = pActorType;
	}

	public String getName() {
		return _name;
	}

	public void setName(String pName) {
		_name = pName;
	}

	public String getLandline() {
		return _landline;
	}

	public void setLandline(String pLandline) {
		_landline = pLandline;
	}

	public String getMobile() {
		return _mobile;
	}

	public void setMobile(String pMobile) {
		_mobile = pMobile;
	}

	public String getEmail() {
		return _email;
	}

	public void setEmail(String pEmail) {
		_email = pEmail;
	}

	public Date getCreationDate() {
		return _creationDate;
	}

	public void setCreationDate(Date pCreationDate) {
		_creationDate = pCreationDate;
	}

	public Integer getTitleCode() {
		return _titleCode;
	}

	public void setTitleCode(Integer pTitleCode) {
		_titleCode = pTitleCode;
	}

	public String getInitials() {
		return _initials;
	}

	public void setInitials(String pInitials) {
		this._initials = pInitials;
	}

	public String getFirstName() {
		return _firstName;
	}

	public void setFirstName(String pFirstName) {
		_firstName = pFirstName;
	}

	public String getMaidenName() {
		return _maidenName;
	}

	public void setMaidenName(String pMaidenName) {
		_maidenName = pMaidenName;
	}

	public String getPrefixName() {
		return _prefixName;
	}

	public void setPrefixName(String pPrefixName) {
		_prefixName = pPrefixName;
	}

	public String getPreviousPrefixName() {
		return _previousPrefixName;
	}

	public void setPreviousPrefixName(String pPreviousPrefixName) {
		this._previousPrefixName = pPreviousPrefixName;
	}

	public Integer getGenderCode() {
		return _genderCode;
	}

	public void setGenderCode(Integer pGenderCode) {
		_genderCode = pGenderCode;
	}

	public String getMaritalStatusCode() {
		return _maritalStatusCode;
	}

	public void setMaritalStatusCode(String pMaritalStatusCode) {
		_maritalStatusCode = pMaritalStatusCode;
	}

	public String getNationality() {
		return _nationality;
	}

	public void setNationality(String pNationality) {
		_nationality = pNationality;
	}

	public String getNationalRegisterNo() {
		return _nationalRegisterNo;
	}

	public void setNationalRegisterNo(String pNationalRegisterNo) {
		_nationalRegisterNo = pNationalRegisterNo;
	}

	public String getTradingName() {
		return _tradingName;
	}

	public void setTradingName(String pTradingName) {
		_tradingName = pTradingName;
	}

	public Date getDateOfBirth() {
		return _dateOfBirth;
	}

	public void setDateOfBirth(Date pDateOfBirth) {
		_dateOfBirth = pDateOfBirth;
	}

	public String getIdentificationNo() {
		return _identificationNo;
	}

	public void setIdentificationNo(String pIdentificationNo) {
		_identificationNo = pIdentificationNo;
	}

	public String getRegistrationNumber() {
		return _registrationNumber;
	}

	public void setRegistrationNumber(String pRegistrationNumber) {
		_registrationNumber = pRegistrationNumber;
	}

	public String getVatNumber() {
		return _vatNumber;
	}

	public void setVatNumber(String pVatNumber) {
		_vatNumber = pVatNumber;
	}

	public String getLegalNameCode() {
		return _legalNameCode;
	}

	public void setLegalNameCode(String pLegalNameCode) {
		_legalNameCode = pLegalNameCode;
	}

	public String getBusinessRegistrationNumber() {
		return _businessRegistrationNumber;
	}

	public void setBusinessRegistrationNumber(String pBusinessRegistrationNumber) {
		_businessRegistrationNumber = pBusinessRegistrationNumber;
	}

}
