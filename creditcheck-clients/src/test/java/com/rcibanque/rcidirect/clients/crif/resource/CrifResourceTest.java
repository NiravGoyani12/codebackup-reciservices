package com.rcibanque.rcidirect.clients.crif.resource;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.core.type.TypeReference;
import com.rcibanque.rcidirect.clients.crif.domain.Actor;
import com.rcibanque.rcidirect.clients.crif.domain.ActorRoles;
import com.rcibanque.rcidirect.clients.crif.domain.ActorType;
import com.rcibanque.rcidirect.clients.crif.domain.ActorTypes;
import com.rcibanque.rcidirect.clients.crif.domain.Address;
import com.rcibanque.rcidirect.clients.crif.domain.Agreement;
import com.rcibanque.rcidirect.clients.crif.domain.CallerType;
import com.rcibanque.rcidirect.clients.crif.domain.ContractType;
import com.rcibanque.rcidirect.clients.crif.domain.CreditCheckRequest;
import com.rcibanque.rcidirect.clients.crif.domain.CreditCheckResponse;
import com.rcibanque.rcidirect.clients.crif.domain.EnquiryType;
import com.rcibanque.rcidirect.clients.crif.domain.FinancialDetail;
import com.rcibanque.rcidirect.clients.crif.domain.LegalStatus;
import com.rcibanque.rcidirect.services.core.domain.Price;
import com.rcibanque.rcidirect.services.core.response.APIResponse;
import com.rcibanque.rcidirect.services.core.test.rest.AbstractResourceTest;

public class CrifResourceTest extends AbstractResourceTest {

	@Value("${crif.resource.test.actor.firstName}")
	private String _firstName;

	@Value("${crif.resource.test.actor.name}")
	private String _name;

	@Value("${crif.resource.test.actor.gender.code}")
	private Integer _genderCode;

	@Value("${crif.resource.test.actor.dateofbirth}")
	private Long _dateOfBirth;

	@Value("${crif.resource.test.actor.landline}")
	private String _landline;

	@Value("${crif.resource.test.actor.mobile}")
	private String _mobile;

	@Value("${crif.resource.test.actor.email}")
	private String _email;

	@Value("${crif.resource.test.actor.identification.no}")
	private String _identificationNo;

	@Value("${crif.resource.test.actor.registration.no}")
	private String _registrationNo;

	@Value("${crif.resource.test.actor.trading.name}")
	private String _tradingName;

	@Value("${crif.resource.test.actor.address.line1}")
	private String _addressLine1;

	@Value("${crif.resource.test.actor.address.line2}")
	private String _addressLine2;

	@Value("${crif.resource.test.actor.address.city}")
	private String _city;

	@Value("${crif.resource.test.actor.address.county}")
	private String _county;

	@Value("${crif.resource.test.actor.address.country}")
	private String _country;

	@Value("${crif.resource.test.actor.address.postcode}")
	private String _postcode;

	@Value("${crif.resource.test.agreement.financialdetail.currency}")
	private String _currency;

	@Value("${crif.resource.test.agreement.financialdetail.price.incl.vat}")
	private String _inclVat;

	@Value("${crif.resource.test.agreement.financialdetail.price.updated.incl.vat}")
	private String _updatedInclVat;

	@Value("${crif.resource.test.private.actor.category}")
	private Integer _prvtActorCategory;

	@Value("${crif.resource.test.professional.actor.category}")
	private Integer _profActorCategory;

	@Value("${crif.resource.test.agreement.status.submitted.status}")
	private Integer _submittedStatus;

	//Regular expression for extracting the XML tag enclosing the Credit Check Reference from the response
	private static final Pattern CREDIT_CHECK_REF_TAG_REGEX = Pattern.compile("<cb:ApplicationCodes (.+?)/>", Pattern.DOTALL);

	//Regular expression for extracting the Credit Check Reference from the response
	private static final Pattern CREDIT_CHECK_REF_REGEX = Pattern.compile("CCRContractCode=\\\"(.+?)\\\"", Pattern.DOTALL);


	@Test
	public void shouldCreditCheck_GivenTheCallerTypeIsSubmitWhenValidIndividualActor() {

		try {

			CreditCheckRequest creditCheckRequest = new CreditCheckRequest();

			getCreditCheckRequest(creditCheckRequest, getIndividualActor(), null, ActorRoles.CUSTOMER, EnquiryType.NAE.getEnquiryType(),
					CallerType.SUBMIT, Stream.of("CCR").collect(Collectors.toList()), Boolean.FALSE, ContractType.LEASE_HIRE_PRIVATE.getFinanceTypeCode());

			APIResponse<CreditCheckResponse> response = performPost("/crif/creditcheck/actor", creditCheckRequest, HttpStatus.OK, getCreditCheckActorReference());

			assertThat(response).isNotNull();
			assertThat(response.getData()).isNotNull();
			assertThat(response.getErrorTrackingID()).isNull();;
			assertThat(CollectionUtils.isEmpty(response.getMessages())).isTrue();
			assertThat(response.getData().getWebServiceData().getResponseCode().equals(1)).isTrue();
			assertThat(response.getData().getWebServiceData().getResponse()).isNotNull();
			assertThat(StringUtils.containsIgnoreCase(response.getData().getWebServiceData().getRequest(), "ContractType=\"14\"")).isTrue();
			assertThat(StringUtils.containsIgnoreCase(response.getData().getWebServiceData().getRequest(), "InvidualData")).isTrue();

		} catch (Exception e) {
			fail(e);
		}

	}

	@Test
	public void shouldCreditCheck_GivenTheCallerTypeIsStatementWhenValidIndividualActor() {

		try {

			CreditCheckRequest creditCheckRequest = new CreditCheckRequest();

			getCreditCheckRequest(creditCheckRequest, getIndividualActor(), null, ActorRoles.CUSTOMER, EnquiryType.NAE.getEnquiryType(),
					CallerType.STATEMENT, Stream.of("CCR").collect(Collectors.toList()), Boolean.FALSE, ContractType.LEASE_PURCHASE_PRIVATE.getFinanceTypeCode());

			APIResponse<CreditCheckResponse> response = performPost("/crif/creditcheck/actor", creditCheckRequest, HttpStatus.OK, getCreditCheckActorReference());

			assertThat(response).isNotNull();
			assertThat(response.getData()).isNotNull();
			assertThat(response.getErrorTrackingID()).isNull();
			assertThat(CollectionUtils.isEmpty(response.getMessages())).isTrue();
			assertThat(response.getData().getWebServiceData().getResponseCode().equals(1)).isTrue();
			assertThat(response.getData().getWebServiceData().getResponse()).isNotNull();
			assertThat(StringUtils.containsIgnoreCase(response.getData().getWebServiceData().getRequest(), "ContractType=\"14\"")).isTrue();
			assertThat(StringUtils.containsIgnoreCase(response.getData().getWebServiceData().getRequest(), "InvidualData")).isTrue();

		} catch (Exception e) {
			fail(e);
		}

	}

	@Test
	public void shouldCreditCheck_GivenTheCallerTypeIsStatementAndProviderIsICBWhenValidIndividualActor() {

		try {

			CreditCheckRequest creditCheckRequest = new CreditCheckRequest();

			getCreditCheckRequest(creditCheckRequest, getIndividualActor(), null, ActorRoles.CUSTOMER, EnquiryType.ICB.getEnquiryType(),
					CallerType.STATEMENT, Stream.of("CCR", "ICB").collect(Collectors.toList()), Boolean.FALSE, ContractType.PCP_PRIVATE.getFinanceTypeCode());

			APIResponse<CreditCheckResponse> response = performPost("/crif/creditcheck/actor", creditCheckRequest, HttpStatus.OK, getCreditCheckActorReference());

			assertThat(response).isNotNull();
			assertThat(response.getData()).isNotNull();
			assertThat(response.getErrorTrackingID()).isNull();
			assertThat(CollectionUtils.isEmpty(response.getMessages())).isTrue();
			assertThat(response.getData().getWebServiceData().getResponseCode().equals(1)).isTrue();
			assertThat(response.getData().getWebServiceData().getResponse()).isNotNull();
			assertThat(StringUtils.containsIgnoreCase(response.getData().getWebServiceData().getRequest(), "ContractType=\"23\"")).isTrue();
			assertThat(StringUtils.containsIgnoreCase(response.getData().getWebServiceData().getRequest(), "InvidualData")).isTrue();

		} catch (Exception e) {
			fail(e);
		}

	}

	@Test
	public void shouldCreditCheck_GivenTheCallerTypeIsSubmitWhenValidSoleTraderActor() {

		try {

			CreditCheckRequest creditCheckRequest = new CreditCheckRequest();

			getCreditCheckRequest(creditCheckRequest, getSoleTraderActor(), getDirectorActor(), ActorRoles.CUSTOMER,
					EnquiryType.NAE.getEnquiryType(), CallerType.SUBMIT, Stream.of("CCR").collect(Collectors.toList()), Boolean.FALSE, ContractType.PCP_PROF.getFinanceTypeCode());

			APIResponse<CreditCheckResponse> response = performPost("/crif/creditcheck/actor", creditCheckRequest, HttpStatus.OK, getCreditCheckActorReference());

			assertThat(response).isNotNull();
			assertThat(response.getData()).isNotNull();
			assertThat(response.getErrorTrackingID()).isNull();
			assertThat(CollectionUtils.isEmpty(response.getMessages())).isTrue();
			assertThat(response.getData().getWebServiceData().getResponseCode().equals(1)).isTrue();
			assertThat(response.getData().getWebServiceData().getResponse()).isNotNull();
			assertThat(StringUtils.containsIgnoreCase(response.getData().getWebServiceData().getRequest(), "ContractType=\"18\"")).isTrue();
			assertThat(StringUtils.containsIgnoreCase(response.getData().getWebServiceData().getRequest(), "SoleTraderData")).isTrue();

		} catch (Exception e) {
			fail(e);
		}

	}

	@Test
	public void shouldCreditCheck_GivenTheCallerTypeIsStatementWhenValidSoleTraderActor() {

		try {

			CreditCheckRequest creditCheckRequest = new CreditCheckRequest();

			getCreditCheckRequest(creditCheckRequest, getSoleTraderActor(), getDirectorActor(), ActorRoles.CUSTOMER,
					EnquiryType.NAE.getEnquiryType(), CallerType.STATEMENT, Stream.of("CCR").collect(Collectors.toList()), Boolean.FALSE, ContractType.HIRE_PURCHASE_PROF.getFinanceTypeCode());

			APIResponse<CreditCheckResponse> response = performPost("/crif/creditcheck/actor", creditCheckRequest, HttpStatus.OK, getCreditCheckActorReference());

			assertThat(response).isNotNull();
			assertThat(response.getData()).isNotNull();
			assertThat(response.getErrorTrackingID()).isNull();
			assertThat(CollectionUtils.isEmpty(response.getMessages())).isTrue();
			assertThat(response.getData().getWebServiceData().getResponseCode().equals(1)).isTrue();
			assertThat(response.getData().getWebServiceData().getResponse()).isNotNull();
			assertThat(StringUtils.containsIgnoreCase(response.getData().getWebServiceData().getRequest(), "ContractType=\"18\"")).isTrue();
			assertThat(StringUtils.containsIgnoreCase(response.getData().getWebServiceData().getRequest(), "FlowStep=\"CCR\"")).isTrue();
			assertThat(StringUtils.containsIgnoreCase(response.getData().getWebServiceData().getRequest(), "SoleTraderData")).isTrue();

		} catch (Exception e) {
			fail(e);
		}

	}

	@Test
	public void shouldCreditCheck_GivenTheCallerTypeIsStatementAndProviderIsICBWhenValidSoleTraderActor() {

		try {

			CreditCheckRequest creditCheckRequest = new CreditCheckRequest();

			getCreditCheckRequest(creditCheckRequest, getSoleTraderActor(), getDirectorActor(), ActorRoles.CUSTOMER,
					EnquiryType.ICB.getEnquiryType(), CallerType.STATEMENT, Stream.of("CCR", "ICB").collect(Collectors.toList()), Boolean.FALSE, ContractType.HIRE_DRIVE_PROF.getFinanceTypeCode());

			APIResponse<CreditCheckResponse> response = performPost("/crif/creditcheck/actor", creditCheckRequest, HttpStatus.OK, getCreditCheckActorReference());

			assertThat(response).isNotNull();
			assertThat(response.getData()).isNotNull();
			assertThat(response.getErrorTrackingID()).isNull();
			assertThat(CollectionUtils.isEmpty(response.getMessages())).isTrue();
			assertThat(response.getData().getWebServiceData().getResponseCode().equals(1)).isTrue();
			assertThat(response.getData().getWebServiceData().getResponse()).isNotNull();
			assertThat(StringUtils.containsIgnoreCase(response.getData().getWebServiceData().getRequest(), "ContractType=\"17\"")).isTrue();
			assertThat(StringUtils.containsIgnoreCase(response.getData().getWebServiceData().getRequest(), "FlowStep=\"CCR\"")).isTrue();
			assertThat(StringUtils.containsIgnoreCase(response.getData().getWebServiceData().getRequest(), "FlowStep=\"ICB\"")).isTrue();
			assertThat(StringUtils.containsIgnoreCase(response.getData().getWebServiceData().getRequest(), "TradeName=\"testName\"")).isTrue();
			assertThat(StringUtils.containsIgnoreCase(response.getData().getWebServiceData().getRequest(), "SoleTraderData")).isTrue();

		} catch (Exception e) {
			fail(e);
		}

	}

	@Test
	public void shouldCreditCheck_GivenTheCallerTypeIsSubmitWhenValidProfessionalActor() {

		try {

			CreditCheckRequest creditCheckRequest = new CreditCheckRequest();

			getCreditCheckRequest(creditCheckRequest, getProfessionalActor(LegalStatus.CHARITY.getLegalNameCode()), getDirectorActor(), ActorRoles.CUSTOMER,
					EnquiryType.NAE.getEnquiryType(), CallerType.SUBMIT, Stream.of("CCR").collect(Collectors.toList()), Boolean.FALSE, ContractType.LEASE_HIRE_PROF.getFinanceTypeCode());

			APIResponse<CreditCheckResponse> response = performPost("/crif/creditcheck/actor", creditCheckRequest, HttpStatus.OK, getCreditCheckActorReference());

			assertThat(response).isNotNull();
			assertThat(response.getData()).isNotNull();
			assertThat(response.getErrorTrackingID()).isNull();
			assertThat(CollectionUtils.isEmpty(response.getMessages())).isTrue();
			assertThat(response.getData().getWebServiceData().getResponseCode().equals(1)).isTrue();
			assertThat(response.getData().getWebServiceData().getResponse()).isNotNull();
			assertThat(StringUtils.containsIgnoreCase(response.getData().getWebServiceData().getRequest(), "ContractType=\"17\"")).isTrue();
			assertThat(StringUtils.containsIgnoreCase(response.getData().getWebServiceData().getRequest(), "CorporateData")).isTrue();
			assertThat(StringUtils.containsIgnoreCase(response.getData().getWebServiceData().getRequest(), "TradeBusinessName=\"testName\"")).isTrue();

		} catch (Exception e) {
			fail(e);
		}

	}

	@Test
	public void shouldCreditCheck_GivenTheCallerTypeIsStatementWhenValidProfessionalActor() {

		try {

			CreditCheckRequest creditCheckRequest = new CreditCheckRequest();

			getCreditCheckRequest(creditCheckRequest, getProfessionalActor(LegalStatus.PUBLIC_LIMITED_COMPANY.getLegalNameCode()), getDirectorActor(), ActorRoles.CUSTOMER,
					EnquiryType.NAE.getEnquiryType(), CallerType.STATEMENT, Stream.of("CCR").collect(Collectors.toList()), Boolean.FALSE, ContractType.LEASE_PURCHASE_PROF.getFinanceTypeCode());

			APIResponse<CreditCheckResponse> response = performPost("/crif/creditcheck/actor", creditCheckRequest, HttpStatus.OK, getCreditCheckActorReference());

			assertThat(response).isNotNull();
			assertThat(response.getData()).isNotNull();
			assertThat(response.getErrorTrackingID()).isNull();
			assertThat(CollectionUtils.isEmpty(response.getMessages())).isTrue();
			assertThat(response.getData().getWebServiceData().getResponseCode().equals(1)).isTrue();
			assertThat(response.getData().getWebServiceData().getResponse()).isNotNull();
			assertThat(StringUtils.containsIgnoreCase(response.getData().getWebServiceData().getRequest(), "ContractType=\"17\"")).isTrue();
			assertThat(StringUtils.containsIgnoreCase(response.getData().getWebServiceData().getRequest(), "FlowStep=\"CCR\"")).isTrue();
			assertThat(StringUtils.containsIgnoreCase(response.getData().getWebServiceData().getRequest(), "CorporateData")).isTrue();

		} catch (Exception e) {
			fail(e);
		}

	}

	@Test
	public void shouldCreditCheck_GivenTheCallerTypeIsStatementAndProviderIsICBWhenValidProfessionalActor() {

		try {

			CreditCheckRequest creditCheckRequest = new CreditCheckRequest();

			getCreditCheckRequest(creditCheckRequest, getProfessionalActor(LegalStatus.PUBLIC_LIMITED_COMPANY.getLegalNameCode()), getDirectorActor(), ActorRoles.CUSTOMER,
					EnquiryType.ICB.getEnquiryType(), CallerType.STATEMENT, Stream.of("CCR", "ICB").collect(Collectors.toList()), Boolean.TRUE, ContractType.HIRE_DRIVE_PROF.getFinanceTypeCode());

			APIResponse<CreditCheckResponse> response = performPost("/crif/creditcheck/actor", creditCheckRequest, HttpStatus.OK, getCreditCheckActorReference());

			assertThat(response).isNotNull();
			assertThat(response.getData()).isNotNull();
			assertThat(response.getErrorTrackingID()).isNull();
			assertThat(CollectionUtils.isEmpty(response.getMessages())).isTrue();
			assertThat(response.getData().getWebServiceData().getResponseCode().equals(1)).isTrue();
			assertThat(response.getData().getWebServiceData().getResponse()).isNotNull();
			assertThat(StringUtils.containsIgnoreCase(response.getData().getWebServiceData().getRequest(), "ContractType=\"17\"")).isTrue();
			assertThat(StringUtils.containsIgnoreCase(response.getData().getWebServiceData().getRequest(), "FlowStep=\"CCR\"")).isTrue();
			assertThat(StringUtils.containsIgnoreCase(response.getData().getWebServiceData().getRequest(), "FlowStep=\"ICB\"")).isTrue();
			assertThat(StringUtils.containsIgnoreCase(response.getData().getWebServiceData().getRequest(), "LegalName=\"testName\"")).isTrue();
			assertThat(StringUtils.containsIgnoreCase(response.getData().getWebServiceData().getRequest(), "CorporateData")).isTrue();

		} catch (Exception e) {
			fail(e);
		}

	}

	@Test
	public void shouldNotCreditCheck_GivenTheCallerTypeIsSubmitWhenValidIndividualActor() {

		try {

			CreditCheckRequest creditCheckRequest = new CreditCheckRequest();

			getCreditCheckRequest(creditCheckRequest, getInvalidActor(), null, ActorRoles.CUSTOMER, EnquiryType.NAE.getEnquiryType(),
					CallerType.SUBMIT, Stream.of("CCR").collect(Collectors.toList()), Boolean.FALSE, ContractType.LEASE_HIRE_PRIVATE.getFinanceTypeCode());

			APIResponse<CreditCheckResponse> response = performPost("/crif/creditcheck/actor", creditCheckRequest, HttpStatus.OK, getCreditCheckActorReference());

			assertThat(response).isNotNull();
			assertThat(response.getData()).isNotNull();
			assertThat(CollectionUtils.isEmpty(response.getMessages())).isTrue();
			assertThat(response.getData().getWebServiceData().getResponseCode().equals(0)).isTrue();
			assertThat(response.getData().getWebServiceData().getResponse()).isNotNull();

		} catch (Exception e) {
			fail(e);
		}

	}

	@Test
	public void shouldCreditCheckUpdateAndCancel_GivenTheCallerTypeIsSubmitWhenValidIndividualActor() {

		try {

			CreditCheckRequest creditCheckRequest = new CreditCheckRequest();

			//Submit
			getCreditCheckRequest(creditCheckRequest, getIndividualActor(), null, ActorRoles.CUSTOMER, EnquiryType.NAE.getEnquiryType(),
					CallerType.SUBMIT, Stream.of("CCR").collect(Collectors.toList()), Boolean.FALSE, ContractType.LEASE_HIRE_PRIVATE.getFinanceTypeCode());

			APIResponse<CreditCheckResponse> response = performPost("/crif/creditcheck/actor", creditCheckRequest, HttpStatus.OK, getCreditCheckActorReference());

			assertThat(response).isNotNull();
			assertThat(response.getData()).isNotNull();
			assertThat(response.getErrorTrackingID()).isNull();;
			assertThat(CollectionUtils.isEmpty(response.getMessages())).isTrue();
			assertThat(response.getData().getWebServiceData().getResponseCode().equals(1)).isTrue();
			assertThat(response.getData().getWebServiceData().getResponse()).isNotNull();
			assertThat(StringUtils.containsIgnoreCase(response.getData().getWebServiceData().getRequest(), "ContractType=\"14\"")).isTrue();
			assertThat(StringUtils.containsIgnoreCase(response.getData().getWebServiceData().getRequest(), "InvidualData")).isTrue();

			String creditCheckRef = null;

			String appCodeTag = getMatchingVal(response.getData().getWebServiceData().getResponse(), CREDIT_CHECK_REF_TAG_REGEX);

			if (StringUtils.isNotBlank(appCodeTag)) {
				creditCheckRef = getMatchingVal(appCodeTag, CREDIT_CHECK_REF_REGEX);
			}

			//Update
			creditCheckRequest.setCallerType(CallerType.STATEMENT);
			creditCheckRequest.setEnquiryType(EnquiryType.AUE.getEnquiryType());
			creditCheckRequest.getAgreement().getFinancialDetail().getFinancedAmount().setInclVat((new BigDecimal(_updatedInclVat)));
			creditCheckRequest.setCreditCheckRef(creditCheckRef);


			APIResponse<CreditCheckResponse> updateResponse = performPost("/crif/creditcheck/actor", creditCheckRequest, HttpStatus.OK, getCreditCheckActorReference());

			assertThat(updateResponse).isNotNull();
			assertThat(updateResponse.getData()).isNotNull();
			assertThat(updateResponse.getErrorTrackingID()).isNull();;
			assertThat(CollectionUtils.isEmpty(updateResponse.getMessages())).isTrue();
			assertThat(updateResponse.getData().getWebServiceData().getResponseCode().equals(1)).isTrue();
			assertThat(updateResponse.getData().getWebServiceData().getResponse()).isNotNull();

			//Cancel
			creditCheckRequest.setCallerType(CallerType.CANCEL);

			APIResponse<CreditCheckResponse> cancelResponse = performPost("/crif/creditcheck/actor", creditCheckRequest, HttpStatus.OK, getCreditCheckActorReference());
			assertThat(cancelResponse).isNotNull();
			assertThat(cancelResponse.getData()).isNotNull();
			assertThat(cancelResponse.getErrorTrackingID()).isNull();;
			assertThat(CollectionUtils.isEmpty(cancelResponse.getMessages())).isTrue();
			assertThat(cancelResponse.getData().getWebServiceData().getResponseCode().equals(1)).isTrue();
			assertThat(cancelResponse.getData().getWebServiceData().getResponse()).isNotNull();



		} catch (Exception e) {
			fail(e);
		}

	}

	private void getCreditCheckRequest(CreditCheckRequest creditCheckRequest, Actor pActor, Actor pDirectorActor,
			ActorRoles pActorRole, String pEnquiryType, CallerType pCallerType, List<String> pProviders,
			Boolean pValidCreditCheckExists, Integer pFinanceTypeCode) {

		creditCheckRequest.setActor(pActor);
		creditCheckRequest.setDirectorActor(pDirectorActor);
		creditCheckRequest.setActorRole(pActorRole);
		creditCheckRequest.setAgreement(getAgreement(pFinanceTypeCode));
		creditCheckRequest.setAddress(getAddress());
		creditCheckRequest.setDirectorAddress(getAddress());
		creditCheckRequest.setEnquiryType(pEnquiryType);
		creditCheckRequest.setCallerType(pCallerType);
		creditCheckRequest.setProviders(pProviders);
		creditCheckRequest.setValidCreditCheckExists(pValidCreditCheckExists);
		creditCheckRequest.setApplicationNumber(getAgreement(pFinanceTypeCode).getAgreementCode());
	}

	private List<Address> getAddress() {

		Address add = new Address();

		add.setAddressLine1(_addressLine1);
		add.setAddressLine2(_addressLine2);
		add.setCity(_city);
		add.setCounty(_county);
		add.setCountry(_country);
		add.setPostcode(_postcode);

		List<Address> _address = new ArrayList<Address>();
		_address.add(add);

		return _address;
	}

	private Agreement getAgreement(Integer pFinanceTypeCode) {

		Agreement _agreement = new Agreement();

		_agreement.setAgreementCode(String.valueOf(java.util.concurrent.ThreadLocalRandom.current().nextLong(1000000000000L, 10000000000000L)));
		_agreement.setStatusCode(_submittedStatus);
		_agreement.setProposalCreationDate(new Date());

		FinancialDetail _financialDetail = new FinancialDetail();
		Price _price = new Price();
		_price.setInclVat(new BigDecimal(_inclVat));

		_financialDetail.setFinancedAmount(_price);
		_financialDetail.setFinanceTypeCode(pFinanceTypeCode);
		_financialDetail.setCurrency(_currency);

		_agreement.setFinancialDetail(_financialDetail);

		return _agreement;
	}

	private Actor getIndividualActor() {

		Actor _actor = new Actor();

		_actor.setFirstName(_firstName);
		_actor.setName(_name);
		_actor.setGenderCode(1);
		_actor.setDateOfBirth(new Date(_dateOfBirth));
		_actor.setLandline(_landline);
		_actor.setIdentificationNo(_identificationNo);
		_actor.setActorRole(ActorRoles.CUSTOMER);

		ActorType actorType = new ActorType();
		actorType.setActorCategoryCode(_prvtActorCategory);
		actorType.setActorTypeCode(ActorTypes.PRIVATE_INDIVIDUAL.getTypeCode());
		_actor.setActorType(actorType);

		return _actor;
	}

	private Actor getSoleTraderActor() {

		Actor _actor = new Actor();

		_actor.setFirstName(_firstName);
		_actor.setName(_name);
		_actor.setGenderCode(_genderCode);
		_actor.setDateOfBirth(new Date(_dateOfBirth));
		_actor.setLandline(_landline);
		_actor.setIdentificationNo(_identificationNo);
		_actor.setActorRole(ActorRoles.CUSTOMER);

		ActorType actorType = new ActorType();
		actorType.setActorCategoryCode(_profActorCategory);
		actorType.setActorTypeCode(ActorTypes.SOLE_TRADER.getTypeCode());
		_actor.setActorType(actorType);

		return _actor;
	}

	private Actor getDirectorActor() {

		Actor _actor = new Actor();

		_actor.setFirstName(_firstName);
		_actor.setName(_name);
		_actor.setGenderCode(_genderCode);
		_actor.setDateOfBirth(new Date(_dateOfBirth));
		_actor.setLandline(_landline);
		_actor.setIdentificationNo(_identificationNo);
		_actor.setActorRole(ActorRoles.CUSTOMER);
		_actor.setTradingName(_tradingName);

		ActorType actorType = new ActorType();
		actorType.setActorCategoryCode(_prvtActorCategory);
		actorType.setActorTypeCode(ActorTypes.PRIVATE_INDIVIDUAL.getTypeCode());
		_actor.setActorType(actorType);

		return _actor;
	}

	private Actor getProfessionalActor(String pLegalNameCode) {

		Actor _actor = new Actor();

		_actor.setFirstName(_firstName);
		_actor.setName(_name);
		_actor.setGenderCode(_genderCode);
		_actor.setDateOfBirth(new Date(_dateOfBirth));
		_actor.setLandline(_landline);
		_actor.setEmail(_email);
		_actor.setRegistrationNumber(_registrationNo);
		_actor.setLegalNameCode(pLegalNameCode);
		_actor.setActorRole(ActorRoles.CUSTOMER);

		ActorType actorType = new ActorType();
		actorType.setActorCategoryCode(_profActorCategory);
		actorType.setActorTypeCode(ActorTypes.LIMITED_COMPANY.getTypeCode());
		_actor.setActorType(actorType);

		return _actor;
	}

	private Actor getInvalidActor() {

		Actor _actor = new Actor();

		_actor.setFirstName(_firstName);
		_actor.setName(_name);
		_actor.setGenderCode(1);
		_actor.setDateOfBirth(new Date(_dateOfBirth));
		_actor.setLandline(_landline);
		_actor.setMobile(_mobile);
		_actor.setIdentificationNo(null);
		_actor.setActorRole(ActorRoles.CUSTOMER);

		ActorType actorType = new ActorType();
		actorType.setActorCategoryCode(_prvtActorCategory);
		actorType.setActorTypeCode(ActorTypes.PRIVATE_INDIVIDUAL.getTypeCode());
		_actor.setActorType(actorType);

		return _actor;
	}

	private String getMatchingVal(final String pTextToParse, Pattern pPattern) {

		String matchingVal = null;
		final Matcher matcher = pPattern.matcher(pTextToParse);
		if(matcher.find()) {
			matchingVal = matcher.group(1);
		}
		return matchingVal;
	}

	private TypeReference<APIResponse<CreditCheckResponse>> getCreditCheckActorReference() {
		return new TypeReference<APIResponse<CreditCheckResponse>>() {
		};
	}

}
