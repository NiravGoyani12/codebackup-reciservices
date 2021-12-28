package com.rcibanque.rcidirect.clients.crif.domain;

import java.util.List;

import com.rcibanque.rcidirect.services.core.domain.DTO;

public class CreditCheckRequest extends DTO {

	private static final long serialVersionUID = -7692060720698820170L;

	private Agreement _agreement;

	private Actor _actor;

	private Actor _directorActor;

	private List<Address> _directorAddress;

	private ActorRoles _actorRole;

	private List<Address> _address;

	private Employment _employment;

	private List<String> _providers;

	private String _enquiryType;

	private CallerType _callerType;

	private Boolean _validCreditCheckExists;

	private String _creditCheckRef;

	private String _applicationNumber;


	public Agreement getAgreement() {
		return _agreement;
	}

	public void setAgreement(Agreement pAgreement) {
		_agreement = pAgreement;
	}

	public Actor getActor() {
		return _actor;
	}

	public void setActor(Actor pActor) {
		_actor = pActor;
	}

	public Actor getDirectorActor() {
		return _directorActor;
	}

	public void setDirectorActor(Actor pDirectorActor) {
		_directorActor = pDirectorActor;
	}

	public List<Address> getDirectorAddress() {
		return _directorAddress;
	}

	public void setDirectorAddress(List<Address> pDirectorAddress) {
		_directorAddress = pDirectorAddress;
	}

	public ActorRoles getActorRole() {
		return _actorRole;
	}

	public void setActorRole(ActorRoles pActorRole) {
		_actorRole = pActorRole;
	}

	public List<Address> getAddress() {
		return _address;
	}

	public void setAddress(List<Address> pAddress) {
		_address = pAddress;
	}

	public Employment getEmployment() {
		return _employment;
	}

	public void setEmployment(Employment pEmployment) {
		_employment = pEmployment;
	}

	public List<String> getProviders() {
		return _providers;
	}

	public void setProviders(List<String> pProviders) {
		_providers = pProviders;
	}

	public String getEnquiryType() {
		return _enquiryType;
	}

	public void setEnquiryType(String pEnquiryType) {
		_enquiryType = pEnquiryType;
	}

	public CallerType getCallerType() {
		return _callerType;
	}

	public void setCallerType(CallerType pCallerType) {
		_callerType = pCallerType;
	}

	public Boolean getValidCreditCheckExists() {
		return _validCreditCheckExists;
	}

	public void setValidCreditCheckExists(Boolean pValidCreditCheckExists) {
		_validCreditCheckExists = pValidCreditCheckExists;
	}

	public String getCreditCheckRef() {
		return _creditCheckRef;
	}

	public void setCreditCheckRef(String pCreditCheckRef) {
		_creditCheckRef = pCreditCheckRef;
	}

	public String getApplicationNumber() {
		return _applicationNumber;
	}

	public void setApplicationNumber(String pApplicationNumber) {
		_applicationNumber = pApplicationNumber;
	}

}
