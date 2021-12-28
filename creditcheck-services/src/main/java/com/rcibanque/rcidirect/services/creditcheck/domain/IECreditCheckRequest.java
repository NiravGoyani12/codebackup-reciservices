package com.rcibanque.rcidirect.services.creditcheck.domain;

import java.util.List;

import com.rcibanque.rcidirect.services.actors.domain.Actor;
import com.rcibanque.rcidirect.services.actors.domain.ActorRoles;
import com.rcibanque.rcidirect.services.actors.domain.Address;
import com.rcibanque.rcidirect.services.agreements.domain.Agreement;

public class IECreditCheckRequest extends CreditCheckRequest {

	private static final long serialVersionUID = 1L;

	private Agreement _agreement;

	private Actor _directorActor;

	private List<Address> _directorAddress;

	private ActorRoles _actorRole;

	private List<String> _providers;

	private String _enquiryType;

	private Boolean _validCreditCheckExists;

	private CallType _callType;

	private CallerType _callerType;

	private String _creditCheckRef;

	private String _applicationNumber;

	public Agreement getAgreement() {
		return _agreement;
	}

	public void setAgreement(Agreement pAgreement) {
		_agreement = pAgreement;
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

	public Boolean getValidCreditCheckExists() {
		return _validCreditCheckExists;
	}

	public void setValidCreditCheckExists(Boolean pValidCreditCheckExists) {
		_validCreditCheckExists = pValidCreditCheckExists;
	}

	public CallType getCallType() {
		return _callType;
	}

	public void setCallType(CallType pCallType) {
		_callType = pCallType;
	}

	public CallerType getCallerType() {
		return _callerType;
	}

	public void setCallerType(CallerType pCallerType) {
		_callerType = pCallerType;
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
