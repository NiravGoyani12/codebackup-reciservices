package com.rcibanque.rcidirect.clients.crif.service.impl;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.rcibanque.rcidirect.clients.crif.domain.Actor;
import com.rcibanque.rcidirect.clients.crif.domain.ActorRoles;
import com.rcibanque.rcidirect.clients.crif.domain.ActorTypes;
import com.rcibanque.rcidirect.clients.crif.domain.Address;
import com.rcibanque.rcidirect.clients.crif.domain.Agreement;
import com.rcibanque.rcidirect.clients.crif.domain.CallerType;
import com.rcibanque.rcidirect.clients.crif.domain.Contact;
import com.rcibanque.rcidirect.clients.crif.domain.ContractPhase;
import com.rcibanque.rcidirect.clients.crif.domain.ContractType;
import com.rcibanque.rcidirect.clients.crif.domain.County;
import com.rcibanque.rcidirect.clients.crif.domain.CreditCheckRequest;
import com.rcibanque.rcidirect.clients.crif.domain.CreditCheckResponse;
import com.rcibanque.rcidirect.clients.crif.domain.CreditCheckRole;
import com.rcibanque.rcidirect.clients.crif.domain.EnquiryType;
import com.rcibanque.rcidirect.clients.crif.domain.Gender;
import com.rcibanque.rcidirect.clients.crif.domain.IdentificationType;
import com.rcibanque.rcidirect.clients.crif.domain.LegalStatus;
import com.rcibanque.rcidirect.clients.crif.domain.WebServiceData;
import com.rcibanque.rcidirect.clients.crif.domain.client.AddressDataType;
import com.rcibanque.rcidirect.clients.crif.domain.client.ApplicationCodesInputType;
import com.rcibanque.rcidirect.clients.crif.domain.client.ApplicationType;
import com.rcibanque.rcidirect.clients.crif.domain.client.CGReqInvoke;
import com.rcibanque.rcidirect.clients.crif.domain.client.CGReqInvoke.Auth;
import com.rcibanque.rcidirect.clients.crif.domain.client.CGReqInvoke.Request;
import com.rcibanque.rcidirect.clients.crif.domain.client.CGReqInvoke.Request.Params;
import com.rcibanque.rcidirect.clients.crif.domain.client.CGReqInvoke.Request.Params.Param;
import com.rcibanque.rcidirect.clients.crif.domain.client.CGRequest;
import com.rcibanque.rcidirect.clients.crif.domain.client.CGRequest.Parameters;
import com.rcibanque.rcidirect.clients.crif.domain.client.CGRequest.Parameters.DataSources;
import com.rcibanque.rcidirect.clients.crif.domain.client.ContactType;
import com.rcibanque.rcidirect.clients.crif.domain.client.CorporateDataType;
import com.rcibanque.rcidirect.clients.crif.domain.client.CorporateType;
import com.rcibanque.rcidirect.clients.crif.domain.client.IdentificationDataType;
import com.rcibanque.rcidirect.clients.crif.domain.client.InvidualDataType;
import com.rcibanque.rcidirect.clients.crif.domain.client.LinkType;
import com.rcibanque.rcidirect.clients.crif.domain.client.RequestServiceServiceLocator;
import com.rcibanque.rcidirect.clients.crif.domain.client.RequestServiceServiceSoapBindingStub;
import com.rcibanque.rcidirect.clients.crif.domain.client.SoleTraderDataType;
import com.rcibanque.rcidirect.clients.crif.domain.client.SubjectInputType;
import com.rcibanque.rcidirect.clients.crif.domain.client.SubjectInputType.Individual;
import com.rcibanque.rcidirect.clients.crif.domain.client.TradeNameType;
import com.rcibanque.rcidirect.clients.crif.service.ICrifService;
import com.rcibanque.rcidirect.clients.crif.utils.CrifMessages;
import com.rcibanque.rcidirect.clients.crif.utils.CrifUtils;
import com.rcibanque.rcidirect.services.core.domain.IContext;

@Service
public class CrifService implements ICrifService {

	private static final Logger LOGGER = LoggerFactory.getLogger(CrifService.class);

	public static final String CURRENCY_EURO = "EUR";

	public static final Integer SUCCESS_STATUS_CODE = 1;

	public static final Integer ERROR_STATUS_CODE = 0;

	public static final String ICB_MAIDEN_PROVIDER = "ICBMaiden";

	@Autowired
	private Environment _env;

	@Override
	public CreditCheckResponse creditCheckActor(IContext pContext, CreditCheckRequest pCreditCheckRequest) {

		LOGGER.debug("START >> CrifService:creditCheckActor");
		CreditCheckResponse res = new CreditCheckResponse();
		WebServiceData webServiceData = new WebServiceData();
		String result = null;

		try {

			URL serviceURL = new URL(_env.getProperty("webservice.crif.url"));
			RequestServiceServiceLocator requestServiceServiceLocator = new RequestServiceServiceLocator();
			RequestServiceServiceSoapBindingStub crifService = new RequestServiceServiceSoapBindingStub(serviceURL,
					requestServiceServiceLocator);

			String request = CrifUtils.jaxbObjectToXML(buildCrifRequest(pCreditCheckRequest, webServiceData));

			result = crifService.invoke(request);

			buildResponse(result, webServiceData, res, Boolean.FALSE, pContext);

		} catch (Exception pEx) {
			buildResponse(result, webServiceData, res, Boolean.TRUE, pContext);
			pContext.getMessages().log(pEx);
			LOGGER.error("CrifService:creditCheckActor >> ", pEx);
		}

		LOGGER.debug("END >> CrifService:creditCheckActor");

		return res;
	}

	private void buildResponse(String pResult, WebServiceData pWebServiceData, CreditCheckResponse pResponse,
			boolean pExceptionOccured, IContext pContext) {

		if (pExceptionOccured) {
			pWebServiceData.setResponse(CrifMessages.getInstance().getMessage(pContext.getLocale(), "creditcheck.service.error"));
		} else {
			pWebServiceData.setResponse(pResult);
		}

		if (pExceptionOccured || StringUtils.isBlank(pResult) || CrifUtils.responseHasErrors(pResult)) {
			pWebServiceData.setResponseCode(ERROR_STATUS_CODE);
		} else {
			pWebServiceData.setResponseCode(SUCCESS_STATUS_CODE);
		}

		pResponse.setWebServiceData(pWebServiceData);
	}

	private CGReqInvoke buildCrifRequest(CreditCheckRequest pCreditCheckRequest, WebServiceData pWebServiceData) {

		CGReqInvoke cgReqInvoke = new CGReqInvoke();
		Auth authentication = new Auth();

		authentication.setUsr(_env.getProperty("webservice.crif.user.name"));
		authentication.setPwd(_env.getProperty("webservice.crif.user.password"));
		cgReqInvoke.setAuth(authentication);
		cgReqInvoke.setRequest(buildRequest(pCreditCheckRequest, pWebServiceData));

		return cgReqInvoke;
	}

	private Request buildRequest(CreditCheckRequest pCreditCheckRequest, WebServiceData pWebServiceData) {

		Request request = new Request();
		if (EnquiryType.AUE.getEnquiryType().equalsIgnoreCase(pCreditCheckRequest.getEnquiryType())) {
			request.setContent(buildContentForAUE(pCreditCheckRequest));
		} else {
			request.setContent(buildContent(pCreditCheckRequest));
		}
		request.setParams(buildParams(pCreditCheckRequest.getEnquiryType(), pCreditCheckRequest.getValidCreditCheckExists()));
		pWebServiceData.setRequest(CrifUtils.jaxbObjectToXML(request));

		return request;
	}

	private String buildContentForAUE(CreditCheckRequest pCreditCheckRequest) {

		CGRequest cGRequest = new CGRequest();

		ApplicationType application = buildApplicationData(pCreditCheckRequest.getAgreement(), pCreditCheckRequest.getActor().getActorType().getActorCategoryCode());

		if (CallerType.CANCEL.getCallerType().equalsIgnoreCase(pCreditCheckRequest.getCallerType().getCallerType())) {
			application.setCancellationFlag(Boolean.TRUE);
		}

		ApplicationCodesInputType applicationCode = new ApplicationCodesInputType();
		applicationCode.setCBContractCode(pCreditCheckRequest.getCreditCheckRef());

		setRequestData(cGRequest, null, null, application, applicationCode, null);

		return CrifUtils.jaxbObjectToXML(cGRequest);
	}

	private String buildContent(CreditCheckRequest pCreditCheckRequest) {

		CGRequest cGRequest = new CGRequest();
		SubjectInputType subject = new SubjectInputType();

		if (ActorTypes.PRIVATE_INDIVIDUAL.getTypeCode().equals(pCreditCheckRequest.getActor().getActorType().getActorTypeCode())) {
			buildIndividualSubject(pCreditCheckRequest, subject, false);
		} else if (ActorTypes.SOLE_TRADER.getTypeCode().equals( pCreditCheckRequest.getActor().getActorType().getActorTypeCode())) {
			buildIndividualSubject(pCreditCheckRequest, subject, true);
		} else {
			buildCorporateSubject(pCreditCheckRequest, subject);
		}

		LinkType link = buildLink(pCreditCheckRequest.getActorRole());

		ApplicationType application = buildApplicationData(pCreditCheckRequest.getAgreement(), pCreditCheckRequest.getActor().getActorType().getActorCategoryCode());

		ApplicationCodesInputType applicationCode = buildApplicationCode(pCreditCheckRequest);

		addICBMaidenProviderToRequest(pCreditCheckRequest);

		Parameters parameters = buildParameters(pCreditCheckRequest);

		setRequestData(cGRequest, subject, link, application, applicationCode, parameters);

		return CrifUtils.jaxbObjectToXML(cGRequest);
	}

	private void buildIndividualSubject(CreditCheckRequest pCreditCheckRequest, SubjectInputType pSubject, boolean pCreateSoleTrader) {

		Actor actor = pCreateSoleTrader ? pCreditCheckRequest.getDirectorActor() : pCreditCheckRequest.getActor();
		List<Address> individualAddress = pCreateSoleTrader ? pCreditCheckRequest.getDirectorAddress() : pCreditCheckRequest.getAddress();

		Individual individual = buildIndividual(actor, pCreditCheckRequest.getEnquiryType());

		List<AddressDataType> address = buildAddress(individualAddress, null);

		List<IdentificationDataType> identification = buildId(actor);

		List<ContactType> contact = buildContact(actor);

		individual.getAddressData().addAll(address);

		individual.getIdentificationData().addAll(identification);

		individual.getContact().addAll(contact);

		if (pCreateSoleTrader) {

			individual.setSoleTraderData(buildSoleTraderData(pCreditCheckRequest));
		}

		pSubject.setIndividual(individual);
	}

	private void buildCorporateSubject(CreditCheckRequest pCreditCheckRequest, SubjectInputType pSubject) {

		CorporateType corporate =  buildCorporate(pCreditCheckRequest.getActor());

		List<AddressDataType> address = buildAddress(pCreditCheckRequest.getAddress(),
				LegalStatus.getLegalStatus(pCreditCheckRequest.getActor().getLegalNameCode()).getAddressTypeCode());

		List<IdentificationDataType> identification = buildId(pCreditCheckRequest.getActor());

		List<ContactType> contact = buildContact(pCreditCheckRequest.getActor());

		corporate.getAddressData().addAll(address);
		corporate.getIdentificationData().addAll(identification);
		corporate.getContact().addAll(contact);

		pSubject.setCorporate(corporate);

	}

	private CorporateType buildCorporate( Actor pActor) {

		CorporateType corporate =  new CorporateType();

		CorporateDataType corporateData = new CorporateDataType();
		corporateData.setEntityForm(LegalStatus.getLegalStatus(pActor.getLegalNameCode()).getEntityFormCode());
		if (LegalStatus.CHARITY.equals(LegalStatus.getLegalStatus(pActor.getLegalNameCode()))) {
			corporateData.setTradeBusinessName(pActor.getName());
		} else {
			corporateData.setLegalName(pActor.getName());
		}

		corporate.setCorporateData(corporateData);

		return corporate;
	}

	private SoleTraderDataType buildSoleTraderData(CreditCheckRequest pCreditCheckRequest) {

		SoleTraderDataType soleTrader = new SoleTraderDataType();
		List<IdentificationDataType> identification = buildId(pCreditCheckRequest.getDirectorActor());

		TradeNameType tradeName = new TradeNameType();
		tradeName.setTradeName(pCreditCheckRequest.getActor().getName());

		soleTrader.getAddressData().addAll(buildAddress(pCreditCheckRequest.getAddress(), null));
		soleTrader.setTradeName(tradeName);
		soleTrader.getIdentificationCode().addAll(identification);

		return soleTrader;
	}

	private Individual buildIndividual(Actor pActor, String pEnquiryType) {

		Individual individual = new Individual();

		InvidualDataType invidualData = new InvidualDataType();
		invidualData.setLastName(pActor.getName());
		invidualData.setFirstName(pActor.getFirstName());
		invidualData.setMaidenName(getMaidenName(pActor, pEnquiryType));
		invidualData.setBirthDate(CrifUtils.getDate(pActor.getDateOfBirth()));
		invidualData.setGender(Gender.valueOf(pActor.getGenderCode()).getGender());

		individual.setInvidualData(invidualData);

		return individual;
	}

	private String getMaidenName(Actor pActor, String pEnquiryType) {

		String maidenName = null;
		if (pEnquiryType.equalsIgnoreCase(EnquiryType.ICB.getEnquiryType()) && StringUtils.isNotBlank(pActor.getMaidenName())) {
			maidenName = pActor.getMaidenName();
		}
		return maidenName;
	}

	private List<AddressDataType> buildAddress(List<Address> pAddress, String pAddressType) {

		List<AddressDataType> addressList = new ArrayList<>();

		if (CollectionUtils.isNotEmpty(pAddress)) {

			pAddress.forEach(address -> addressList.add(getAddress(address, pAddressType)));
		}

		return addressList;
	}

	private AddressDataType getAddress(Address pAddress, String pAddressType) {

		AddressDataType address = new AddressDataType();

		if (StringUtils.isNotBlank(pAddressType)) {
			address.setType(pAddressType);
		}

		address.setAddressLine1(pAddress.getAddressLine1());
		address.setAddressLine2(pAddress.getAddressLine2());
		address.setCity(pAddress.getCity());
		address.setCounty(County.getCounty(pAddress.getCounty()).getCounty());
		address.setCountry(pAddress.getCountry());
		address.setEircode(pAddress.getPostcode());

		return address;
	}

	private List<IdentificationDataType> buildId(Actor pActor) {

		List<IdentificationDataType> idList = new ArrayList<>();

		IdentificationDataType identificationData = null;

		if (ActorTypes.PRIVATE_INDIVIDUAL.getTypeCode().equals(pActor.getActorType().getActorTypeCode())
				|| ActorTypes.SOLE_TRADER.getTypeCode().equals(pActor.getActorType().getActorTypeCode())) {

			identificationData = buildIdData(IdentificationType.valueOf(pActor.getActorType().getActorTypeCode()).getIdTypeCode(),
					pActor.getIdentificationNo());
		} else if (!LegalStatus.CHARITY.equals(LegalStatus.getLegalStatus(pActor.getLegalNameCode()))) {

			identificationData = buildIdData(LegalStatus.getLegalStatus(pActor.getLegalNameCode()).getIdentificationCode(),
					pActor.getRegistrationNumber());
		}

		CollectionUtils.addIgnoreNull(idList, identificationData);

		return idList;
	}

	private IdentificationDataType buildIdData(String pType, String pNumber) {

		IdentificationDataType identificationData = new IdentificationDataType();
		identificationData.setType(pType);
		identificationData.setNumber(pNumber);

		return identificationData;
	}

	private List<ContactType> buildContact(Actor pActor) {

		List<ContactType> contactList = new ArrayList<>();

		if (ActorTypes.PRIVATE_INDIVIDUAL.getTypeCode().equals(pActor.getActorType().getActorTypeCode())
				|| ActorTypes.SOLE_TRADER.getTypeCode().equals(pActor.getActorType().getActorTypeCode())) {
			buildContactForIndividual(pActor, contactList);
		} else {
			buildContactForCompany(pActor, contactList);
		}

		return contactList;
	}

	private void buildContactForIndividual(Actor pActor, List<ContactType> contactList) {

		if (StringUtils.isNotBlank(pActor.getLandline())) {
			contactList.add(getContact(Contact.LANDLINE.getType().toString(), pActor.getLandline()));
		}
		if (StringUtils.isNotBlank(pActor.getMobile())) {
			contactList.add(getContact(Contact.MOBILE.getType().toString(), pActor.getMobile()));
		}
	}

	private void buildContactForCompany(Actor pActor, List<ContactType> contactList) {

		if (StringUtils.isNotBlank(pActor.getLandline())) {
			contactList.add(getContact(Contact.PRIMARY_BUSINESS_LANDLINE.getType().toString(), pActor.getLandline()));
		}
		if (StringUtils.isNotBlank(pActor.getMobile())) {
			contactList.add(getContact(Contact.SECONDARY_BUSINESS_MOBILE.getType().toString(), pActor.getMobile()));
		}
		if (StringUtils.isNotBlank(pActor.getEmail())
				&& (StringUtils.isBlank(pActor.getLandline()) || StringUtils.isBlank(pActor.getMobile()))) {
			contactList.add(getContact(Contact.EMAIL_ADDRESS.getType().toString(), pActor.getEmail()));
		}
	}

	private ContactType getContact(String pType, String pValue) {

		ContactType contact = new ContactType();
		contact.setType(pType);
		contact.setValue(pValue);

		return contact;
	}

	private LinkType buildLink(ActorRoles pActorRoles) {

		LinkType link = new LinkType();
		link.setRole(CreditCheckRole.valueOf(pActorRoles.getRoleCode()).getRole());

		return link;
	}

	private ApplicationType buildApplicationData(Agreement pAgreement, Integer pActorCategoryCode) {

		ApplicationType applicationType = new ApplicationType();
		applicationType.setContractType(
				ContractType.valueOf(pAgreement.getFinancialDetail().getFinanceTypeCode(), pActorCategoryCode).getContractTypeCode());
		applicationType.setContractPhase(ContractPhase.valueOf(pAgreement.getStatusCode()).getContractPhaseCode());
		applicationType.setContractRequestDate(CrifUtils.getDate(pAgreement.getProposalCreationDate()));

		if (StringUtils.isNotBlank(pAgreement.getFinancialDetail().getCurrency())) {
			applicationType.setCurrency(pAgreement.getFinancialDetail().getCurrency());
		} else {
			applicationType.setCurrency(CURRENCY_EURO);
		}

		applicationType.setCreditAmount(String.valueOf(pAgreement.getFinancialDetail().getFinancedAmount().getInclVat().intValue()));

		return applicationType;
	}

	private ApplicationCodesInputType buildApplicationCode(CreditCheckRequest pCreditCheckRequest) {

		ApplicationCodesInputType applicationCode = new ApplicationCodesInputType();
		applicationCode.setProviderApplicationNo(pCreditCheckRequest.getApplicationNumber());

		return applicationCode;
	}

	private Parameters buildParameters(CreditCheckRequest pCreditCheckRequest) {

		Parameters parameters = new Parameters();
		parameters.setDeDup(Boolean.valueOf(_env.getProperty("webservice.crif."+ pCreditCheckRequest.getEnquiryType().toLowerCase() +".param.dedup")));
		parameters.setCustomScore(Boolean.valueOf(_env.getProperty("webservice.crif."+ pCreditCheckRequest.getEnquiryType().toLowerCase() +".param.customscore")));

		pCreditCheckRequest.getProviders().forEach(provider -> buildDataSource(pCreditCheckRequest.getProviders(), parameters, provider));

		return parameters;
	}

	private void addICBMaidenProviderToRequest(CreditCheckRequest pCreditCheckRequest) {

		if (EnquiryType.ICB.getEnquiryType().equalsIgnoreCase(pCreditCheckRequest.getEnquiryType())
				&& StringUtils.isNotBlank(pCreditCheckRequest.getActor().getMaidenName())) {
			pCreditCheckRequest.getProviders().add(ICB_MAIDEN_PROVIDER);
		}
	}

	private void buildDataSource(List<String> pProviders, Parameters pParameters, String pProvider) {

		DataSources dataSource = new DataSources();
		dataSource.setFlowStep(pProvider);
		dataSource.setOrder((long) pProviders.indexOf(pProvider));

		pParameters.getDataSources().add(dataSource);
	}

	private void setRequestData(CGRequest pRequest, SubjectInputType pSubject, LinkType pLink,
			ApplicationType pApplication, ApplicationCodesInputType pApplicationCode, Parameters pParameters) {

		pRequest.setSubject(pSubject);
		pRequest.setLink(pLink);
		pRequest.setApplication(pApplication);
		pRequest.setApplicationCode(pApplicationCode);
		pRequest.setParameters(pParameters);

	}

	private Params buildParams(String pCreditCheckEnquiryType, Boolean pValidCreditCheckExists) {

		Params params = new Params();
		Param param = new Param();
		param.setServiceID(_env.getProperty("webservice.crif."+ pCreditCheckEnquiryType.toLowerCase() +".param.serviceid"));
		param.setProcessID(_env.getProperty("webservice.crif."+ pCreditCheckEnquiryType.toLowerCase() +".param.processid"));
		param.setForceRequest(getForceRequest(pCreditCheckEnquiryType, pValidCreditCheckExists));
		params.setParam(param);

		return params;
	}

	private Boolean getForceRequest(String pCreditCheckEnquiryType, Boolean pValidCreditCheckExists) {

		Boolean forceReqVal;

		if (EnquiryType.ICB.getEnquiryType().equalsIgnoreCase(pCreditCheckEnquiryType)) {
			forceReqVal = !pValidCreditCheckExists;
		} else {
			forceReqVal = Boolean.valueOf(_env.getProperty("webservice.crif." + pCreditCheckEnquiryType.toLowerCase() + ".param.forcerequest"));
		}

		return forceReqVal;
	}

}
