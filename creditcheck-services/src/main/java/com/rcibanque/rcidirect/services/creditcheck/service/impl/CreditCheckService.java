package com.rcibanque.rcidirect.services.creditcheck.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.jms.JMSException;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.rcibanque.rcidirect.services.actors.dao.criteria.ActorLinkCriteria;
import com.rcibanque.rcidirect.services.actors.dao.criteria.AddressCriteria;
import com.rcibanque.rcidirect.services.actors.dao.criteria.EmploymentCriteria;
import com.rcibanque.rcidirect.services.actors.domain.Actor;
import com.rcibanque.rcidirect.services.actors.domain.ActorCategories;
import com.rcibanque.rcidirect.services.actors.domain.ActorLink;
import com.rcibanque.rcidirect.services.actors.domain.ActorLinkType;
import com.rcibanque.rcidirect.services.actors.domain.ActorRoles;
import com.rcibanque.rcidirect.services.actors.domain.ActorTypes;
import com.rcibanque.rcidirect.services.actors.domain.Address;
import com.rcibanque.rcidirect.services.actors.domain.BusinessActor;
import com.rcibanque.rcidirect.services.actors.domain.DirectorActor;
import com.rcibanque.rcidirect.services.actors.domain.IndividualActor;
import com.rcibanque.rcidirect.services.actors.service.IActorLinkService;
import com.rcibanque.rcidirect.services.actors.service.IActorService;
import com.rcibanque.rcidirect.services.actors.service.IAddressService;
import com.rcibanque.rcidirect.services.actors.service.IBusinessActorService;
import com.rcibanque.rcidirect.services.actors.service.IDirectorActorService;
import com.rcibanque.rcidirect.services.actors.service.IEmploymentService;
import com.rcibanque.rcidirect.services.actors.service.IIndividualActorService;
import com.rcibanque.rcidirect.services.actors.service.IRoleService;
import com.rcibanque.rcidirect.services.agreements.dao.criteria.AgreementCriteria;
import com.rcibanque.rcidirect.services.agreements.domain.Agreement;
import com.rcibanque.rcidirect.services.agreements.domain.BasicAgreement;
import com.rcibanque.rcidirect.services.agreements.service.IAgreementService;
import com.rcibanque.rcidirect.services.core.domain.IContext;
import com.rcibanque.rcidirect.services.core.domain.Price;
import com.rcibanque.rcidirect.services.core.messaging.jms.QueueSender;
import com.rcibanque.rcidirect.services.core.rest.helper.IRestHelper;
import com.rcibanque.rcidirect.services.core.rest.helper.impl.RestType;
import com.rcibanque.rcidirect.services.core.utils.DateUtils;
import com.rcibanque.rcidirect.services.creditcheck.domain.CallType;
import com.rcibanque.rcidirect.services.creditcheck.domain.CallerType;
import com.rcibanque.rcidirect.services.creditcheck.domain.CreditCheckProviderResult;
import com.rcibanque.rcidirect.services.creditcheck.domain.CreditCheckResponse;
import com.rcibanque.rcidirect.services.creditcheck.domain.IECreditCheckCriteria;
import com.rcibanque.rcidirect.services.creditcheck.domain.IECreditCheckMessage;
import com.rcibanque.rcidirect.services.creditcheck.domain.IECreditCheckRequest;
import com.rcibanque.rcidirect.services.creditcheck.domain.LegalStatus;
import com.rcibanque.rcidirect.services.creditcheck.service.ICreditCheckService;
import com.rcibanque.rcidirect.services.creditcheck.utils.CreditCheckMessages;
import com.rcibanque.rcidirect.services.creditcheck.utils.CreditCheckUtils;
import com.rcibanque.rcidirect.services.general.parameters.service.impl.CommonService;
import com.rcibanque.rcidirect.services.webservice.dao.criteria.WebServiceDataCriteria;
import com.rcibanque.rcidirect.services.webservice.domain.WebServiceData;
import com.rcibanque.rcidirect.services.webservice.service.IWebServiceDataService;

@Service
public class CreditCheckService extends CommonService implements ICreditCheckService {

	private static final Logger LOGGER = LoggerFactory.getLogger(CreditCheckService.class);

	private static final RestType<CreditCheckResponse> RESPONSE_TYPE = new RestType<CreditCheckResponse>() {
	};

	public static final Integer SUCCESS_STATUS_CODE = 1;

	private static final int LAST_CHECK_DAYS = 7;

	//Regular expression for extracting the XML tag enclosing the Credit Check Reference from the response
	private static final Pattern CREDIT_CHECK_REF_TAG_REGEX = Pattern.compile("<cb:ApplicationCodes (.+?)/>", Pattern.DOTALL);

	//Regular expression for extracting the Credit Check Reference from the response
	private static final Pattern CREDIT_CHECK_REF_REGEX = Pattern.compile("CCRContractCode=\\\"(.+?)\\\"", Pattern.DOTALL);

	@Autowired
	private IAgreementService _agreementService;

	@Autowired
	private IActorService _actorService;

	@Autowired
	private IAddressService _addressService;

	@Autowired
	private IIndividualActorService _individualActorService;

	@Autowired
	private IBusinessActorService _businessActorService;

	@Autowired
	private IDirectorActorService _directorActorService;

	@Autowired
	private IEmploymentService _employmentService;

	@Autowired
	private IRoleService _roleService;

	@Autowired
	private IActorLinkService _actorLinkService;

	@Autowired
	private IWebServiceDataService _webServiceDataService;

	@Autowired
	private QueueSender _queueSender;

	@Value("${messaging.creditcheck.destination}")
	private String creditCheckQueueDestination;

	@Override
	public Boolean doCreditCheck(IContext pContext, List<IECreditCheckRequest> pCreditCheckRequestList, CallerType pCallerType) {

		try {

			String url = _env.getProperty("webservice.crif.url");
			pCreditCheckRequestList.forEach(creditCheckRequest -> callCreditCheckClient(pContext, url, creditCheckRequest, pCallerType));

		} catch (Exception e) {
			pContext.getMessages().error(CreditCheckMessages.getInstance(), "creditcheck.service.error");
			LOGGER.error("Error when calling CreditCheck client", e);
		}

		return !pContext.getMessages().hasError();
	}

	@Override
	public List<IECreditCheckRequest> getCreditCheckRequest(IContext pContext, IECreditCheckCriteria pIECreditCheckCriteria) {
		List<IECreditCheckRequest> reqList = new ArrayList<>();

		Agreement agreement = _agreementService.getAgreementMinimal(pContext, pIECreditCheckCriteria.getAgreementId());

		if (!CreditCheckUtils.isServiceOnlyAgreement(agreement)) {

			// If the agreement is part of a master agreement, the "credit amount" sent must be the total financed amount of the master agreement proposals.
			if(agreement.getAgreementGroupId() != null){
				agreement.getFinancialDetail().setFinancedAmount(getTotalFinancedAmount(pContext, agreement));
			}

			for (String actorCode : pIECreditCheckCriteria.getActorCode()) {

				Actor actor = _actorService.getActor(pContext, actorCode, null, true);
				ActorRoles actorRole = getActorRole(pContext, agreement.getAgreementId(), actor.getActorCode());
				DirectorActor directorActor = null;
				String criteriaActorCode = null;

				if (ActorCategories.INDIVIDUAL_ACTOR.getCategoryCode() == actor.getActorCategoryCode()) {

					criteriaActorCode = actor.getActorCode();
					actor = _individualActorService.getIndividualActor(pContext, actor.getActorCode(), Boolean.TRUE);
					((IndividualActor) actor).setGenderCode(CreditCheckUtils.getGender(((IndividualActor) actor).getTitleCode()));

				} else if (ActorCategories.BUSINESS_ACTOR.getCategoryCode() == actor.getActorCategoryCode() && ActorRoles.CUSTOMER == actorRole) {

					actor = _businessActorService.getBusinessActor(pContext, actor.getActorCode(), Boolean.TRUE);
					actor.setActorTypeCode((LegalStatus.getLegalStatus(((BusinessActor) actor).getLegalStatusCode())).getActorTypeCode());

					directorActor = getDirectorActor(pContext, actor);
					directorActor.setGenderCode(CreditCheckUtils.getGender(directorActor.getTitleCode()));

					criteriaActorCode = actor.getActorTypeCode() == ActorTypes.LIMITED_COMPANY.getTypeCode() ? actor.getActorCode() : directorActor.getActorCode();

				}

				IECreditCheckRequest creditCheck = getCreditCheckRequest(pContext, pIECreditCheckCriteria, agreement, actor, actorRole, directorActor, criteriaActorCode);

				reqList.add(creditCheck);
			}
		}

		return reqList;
	}

	private Price getTotalFinancedAmount(IContext pContext, Agreement pAgreement) {

		Price totalFinancedAmount = Price.getZero();

		List<Agreement> agreements = _agreementService.getGroupAgreementsMinimal(pContext, pAgreement.getAgreementId());

		agreements.forEach(agreement -> {
			totalFinancedAmount.add(agreement.getFinancialDetail().getFinancedAmount());
		});

		return totalFinancedAmount;
	}

	@Override
	public CreditCheckProviderResult requestCreditCheck(IContext pContext, List<IECreditCheckRequest> pCreditCheckRequestList, CallerType pCallerType) {
		CreditCheckProviderResult res;

		try {
			IECreditCheckMessage creditCheckMessage = new IECreditCheckMessage(pContext, pCreditCheckRequestList, pCallerType);
			_queueSender.sendToQueue(creditCheckQueueDestination, creditCheckMessage);
			res = CreditCheckProviderResult.PERFORMED_PENDING;

		} catch (JMSException pException) {
			LOGGER.error("Queue is not available", pException);
			res = CreditCheckProviderResult.TECHNICAL_ERROR;
		}

		return res;
	}

	private DirectorActor getDirectorActor(IContext pContext, Actor actor) {

		DirectorActor directorActor;

		ActorLinkCriteria actorLinkCriteria = new ActorLinkCriteria();
		actorLinkCriteria.setLinkedActorCode(actor.getActorCode());
		actorLinkCriteria.setLanguageCode(pContext.getLanguageCode());
		actorLinkCriteria.setLinks(Arrays.asList(ActorLinkType.IS_RESPONSIBLE_NB1.getLinkName()));

		ActorLink directorActorLink = _actorLinkService.getActorLink(pContext, actorLinkCriteria);

		directorActor = _directorActorService.getDirectorActor(pContext, actor.getActorCode(), directorActorLink.getActorCode());

		return directorActor;
	}

	private IECreditCheckRequest getCreditCheckRequest(IContext pContext, IECreditCheckCriteria pIECreditCheckCriteria,	Agreement pAgreement, Actor pActor, ActorRoles pActorRole, DirectorActor pDirectorActor, String pCriteriaActorCode) {

		IECreditCheckRequest creditCheck = new IECreditCheckRequest();

		creditCheck.setActor(pActor);
		creditCheck.setActorRole(pActorRole);
		creditCheck.setDirectorActor(pDirectorActor);
		creditCheck.setAgreement(pAgreement);
		creditCheck.setProviders(pIECreditCheckCriteria.getProviders());
		creditCheck.setEnquiryType(pIECreditCheckCriteria.getEnquiryType());
		creditCheck.setCallType(pIECreditCheckCriteria.getCallType());
		creditCheck.setCallerType(pIECreditCheckCriteria.getCallerType());
		creditCheck.setAddress(getAddress(pContext, pActor));
		creditCheck.setDirectorAddress(getAddress(pContext, pDirectorActor));
		creditCheck.setApplicationNumber(getApplicationNumber(pAgreement, pActor));

		EmploymentCriteria empCriteria = new EmploymentCriteria();
		empCriteria.setLanguageCode(pContext.getLanguageCode());
		empCriteria.setActorCode(pCriteriaActorCode);
		creditCheck.setEmployment(_employmentService.getEmploymentDetails(pContext, empCriteria));

		return creditCheck;
	}

	private String getApplicationNumber(Agreement pAgreement, Actor pActor) {

		String applicationNumber = pAgreement.getAgreementCode();

		if (pAgreement.getAgreementGroupId() != null) {
			applicationNumber = pActor.getActorCode() + "/" + pAgreement.getAgreementGroupId();
		}

		return applicationNumber;
	}

	private List<Address> getAddress(IContext pContext, Actor pActor) {

		List<Address> address = null;
		if (null != pActor) {
			AddressCriteria criteria = new AddressCriteria();
			criteria.setLanguageCode(pContext.getLanguageCode());
			criteria.setActorCode(pActor.getActorCode());
			criteria.setActive(Boolean.TRUE);

			address = Collections.singletonList(_addressService.getAddress(pContext, criteria));
		}
		return address;
	}

	private void callCreditCheckClient(IContext pContext, String pUrl, IECreditCheckRequest pCreditCheckRequest, CallerType pCallerType) {

		List<WebServiceData> existingCreditCheckData = null;
		String creditCheckRef = null;

		if (!CallType.UPDATE.equals(pCreditCheckRequest.getCallType())) {

			List<BasicAgreement> agreements = getAllAgreementsForAgreementCode(pContext, pCreditCheckRequest);
			existingCreditCheckData = getWebserviceDataForAnActorOnAgreement(pContext, pCreditCheckRequest, agreements);

			if (CollectionUtils.isEmpty(existingCreditCheckData)) {
				existingCreditCheckData = getExistingCreditCheckData(pContext, pCreditCheckRequest);
			}
		} else {
			creditCheckRef = getCreditCheckRef(pContext,pCreditCheckRequest);
			pCreditCheckRequest.setCreditCheckRef(creditCheckRef);
		}
		pCreditCheckRequest.setValidCreditCheckExists(!CollectionUtils.isEmpty(existingCreditCheckData));

		boolean isCallRequired = isCreditCheckCallRequired(pCreditCheckRequest, existingCreditCheckData, creditCheckRef);

		if (isCallRequired) {

			WebServiceData webServiceData = saveRequest(pContext, pCreditCheckRequest, pCallerType);

			if (null != webServiceData.getWebServiceId()) {

				webServiceData.setRequestTime(_systemService.getCurrentDateUpdatedTime());

				// Make the client call
				CreditCheckResponse res = getRestHelper()
						.post(pContext, pUrl, null, pCreditCheckRequest, RESPONSE_TYPE, true).getBody().getData();

				if (!saveResponse(pContext, webServiceData, res)) {
					pContext.getMessages().error(CreditCheckMessages.getInstance(), "creditcheck.service.error");
				}
			}
		}
	}

	/**
	 * This method defines the rule for calling the Credit Check client
	 * Call will be made if the call-type is NEW and no previous valid credit check record exists or
	 * if the call-type is ADDITIONAL or
	 * if the call-type is UPDATE and a Credit Check Reference from the previous valid NEW call exists.
	 *
	 */
	private Boolean isCreditCheckCallRequired(IECreditCheckRequest pCreditCheckRequest,	List<WebServiceData> pExistingCreditCheckData, String pCreditCheckRef) {

		Boolean isCallRequired = Boolean.FALSE;

		if ((CallType.NEW.equals(pCreditCheckRequest.getCallType())	&& CollectionUtils.isEmpty(pExistingCreditCheckData))
				|| CallType.ADDITIONAL.equals(pCreditCheckRequest.getCallType())
				|| (CallType.UPDATE.equals(pCreditCheckRequest.getCallType()) && StringUtils.isNotBlank(pCreditCheckRef))) {

			isCallRequired = Boolean.TRUE;
		}

		return isCallRequired;
	}

	private boolean saveResponse(IContext pContext, WebServiceData webServiceData, CreditCheckResponse res) {

		boolean retVal;

		webServiceData.setResponseTime(_systemService.getCurrentDateUpdatedTime());
		webServiceData.setRequest(res.getWebServiceData().getRequest());
		webServiceData.setResponse(res.getWebServiceData().getResponse());
		webServiceData.setResponseCode(res.getWebServiceData().getResponseCode());

		retVal = null != webServiceData.getResponseCode() && SUCCESS_STATUS_CODE == webServiceData.getResponseCode() ? true : false;

		_webServiceDataService.update(pContext, webServiceData);

		return retVal;
	}

	private List<WebServiceData> getExistingCreditCheckData(IContext pContext,
			IECreditCheckRequest pCreditCheckRequest) {

		List<WebServiceData> existingCreditCheckData;
		WebServiceDataCriteria criteria = new WebServiceDataCriteria();
		criteria.setActorId(pCreditCheckRequest.getActor().getActorCode());
		criteria.setCallerIds(CallType.getCallType(pCreditCheckRequest.getCallType().getCallType()).getEnquiryTypeCheckCriteria());
		criteria.setRequestDateFrom(DateUtils.addDays(_systemService.getCurrentDateUpdatedTime(), - LAST_CHECK_DAYS));
		criteria.setMaxOccurences(1);
		criteria.setResponseCode(SUCCESS_STATUS_CODE);

		existingCreditCheckData = _webServiceDataService.searchWebServiceData(pContext, criteria);
		return existingCreditCheckData;
	}

	private WebServiceData saveRequest(IContext pContext, IECreditCheckRequest pRequest, CallerType pCallerType) {

		WebServiceData webServiceData = new WebServiceData();
		webServiceData.setActorId(pRequest.getActor().getActorCode());
		webServiceData.setAgreementId(pRequest.getAgreement().getAgreementId());
		webServiceData.setCallerId(pCallerType.getCallerType());
		webServiceData.setCallerLogin(pContext.getUser().getUsername());
		webServiceData.setCallType(pRequest.getEnquiryType());
		webServiceData.setRequest(pRequest.getAgreement().toString());
		webServiceData.setRequestTime(_systemService.getCurrentDateUpdatedTime());

		webServiceData = _webServiceDataService.save(pContext, webServiceData);

		return webServiceData;
	}

	private ActorRoles getActorRole(IContext pContext, Long pAgreementId, String pActorCode) {

		ActorRoles actorRole = null;

		if (_roleService.hasActorRoleOnAgreement(pContext, pAgreementId, pActorCode, ActorRoles.CUSTOMER)) {
			actorRole = ActorRoles.CUSTOMER;
		} else if (_roleService.hasActorRoleOnAgreement(pContext, pAgreementId, pActorCode, ActorRoles.COSIGNER)) {
			actorRole = ActorRoles.COSIGNER;
		}

		return actorRole;
	}

	private List<BasicAgreement> getAllAgreementsForAgreementCode(IContext pContext, IECreditCheckRequest pIECreditCheckRequest) {

		AgreementCriteria criteria = new AgreementCriteria();
		criteria.setLanguageCode(pContext.getLanguageCode());
		criteria.setAgreementCode(pIECreditCheckRequest.getAgreement().getAgreementCode());

		return _agreementService.searchAgreements(pContext, criteria);
	}

	private List<WebServiceData> getWebserviceDataForAnActorOnAgreement(IContext pContext,
			IECreditCheckRequest pIECreditCheckRequest, List<BasicAgreement> agreements) {

		List<WebServiceData> existingCreditCheckData;

		WebServiceDataCriteria webServiceDataCriteria = new WebServiceDataCriteria();
		webServiceDataCriteria.setActorId(pIECreditCheckRequest.getActor().getActorCode());
		webServiceDataCriteria.setAgreementIds(agreements.stream().map(BasicAgreement::getAgreementId).filter(Objects::nonNull).collect(Collectors.toList()));
		webServiceDataCriteria.setCallerIds(CallType.getCallType(pIECreditCheckRequest.getCallType().getCallType()).getEnquiryTypeCheckCriteria());
		webServiceDataCriteria.setMaxOccurences(1);
		webServiceDataCriteria.setResponseCode(SUCCESS_STATUS_CODE);
		if (!CallType.UPDATE.equals(pIECreditCheckRequest.getCallType())) {
			webServiceDataCriteria.setRequestDateFrom(DateUtils.addDays(_systemService.getCurrentDateUpdatedTime(), -LAST_CHECK_DAYS));
		}

		existingCreditCheckData = _webServiceDataService.searchWebServiceData(pContext, webServiceDataCriteria);

		return existingCreditCheckData;
	}

	private String getCreditCheckRef(IContext pContext, IECreditCheckRequest pIECreditCheckRequest) {

		String creditCheckRef = null;
		List<BasicAgreement> agreements = getAllAgreementsForAgreementCode(pContext, pIECreditCheckRequest);

		if (CollectionUtils.isNotEmpty(agreements)) {
			List<WebServiceData> existingCreditCheckData = getWebserviceDataForAnActorOnAgreement(pContext, pIECreditCheckRequest, agreements);

			if (CollectionUtils.isNotEmpty(existingCreditCheckData)) {

				String response = existingCreditCheckData.get(0).getResponse();
				String appCodeTag = getMatchingVal(response, CREDIT_CHECK_REF_TAG_REGEX);

				if (StringUtils.isNotBlank(appCodeTag)) {
					creditCheckRef = getMatchingVal(appCodeTag, CREDIT_CHECK_REF_REGEX);
				}
			}
		}

		return creditCheckRef;
	}

	IRestHelper getRestHelper() {
		return _restHelper;
	}

	String getMatchingVal(final String pTextToParse, Pattern pPattern) {

		return CreditCheckUtils.getMatchingVal(pTextToParse, pPattern);
	}
}

