package com.rcibanque.rcidirect.services.creditcheck.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

import javax.jms.JMSException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;

import com.rcibanque.rcidirect.services.actors.domain.ActorCategories;
import com.rcibanque.rcidirect.services.actors.domain.ActorLink;
import com.rcibanque.rcidirect.services.actors.domain.BusinessActor;
import com.rcibanque.rcidirect.services.actors.domain.DirectorActor;
import com.rcibanque.rcidirect.services.actors.domain.IndividualActor;
import com.rcibanque.rcidirect.services.actors.service.impl.ActorLinkService;
import com.rcibanque.rcidirect.services.actors.service.impl.ActorService;
import com.rcibanque.rcidirect.services.actors.service.impl.AddressService;
import com.rcibanque.rcidirect.services.actors.service.impl.BusinessActorService;
import com.rcibanque.rcidirect.services.actors.service.impl.DirectorActorService;
import com.rcibanque.rcidirect.services.actors.service.impl.EmploymentService;
import com.rcibanque.rcidirect.services.actors.service.impl.IndividualActorService;
import com.rcibanque.rcidirect.services.actors.service.impl.RoleService;
import com.rcibanque.rcidirect.services.agreementgroups.domain.AgreementGroup;
import com.rcibanque.rcidirect.services.agreements.domain.Agreement;
import com.rcibanque.rcidirect.services.agreements.domain.BasicAgreement;
import com.rcibanque.rcidirect.services.agreements.domain.details.FinancialDetail;
import com.rcibanque.rcidirect.services.agreements.service.impl.AgreementService;
import com.rcibanque.rcidirect.services.core.domain.IContext;
import com.rcibanque.rcidirect.services.core.domain.Price;
import com.rcibanque.rcidirect.services.core.messaging.jms.QueueSender;
import com.rcibanque.rcidirect.services.core.response.APIMessages;
import com.rcibanque.rcidirect.services.core.response.APIResponse;
import com.rcibanque.rcidirect.services.core.response.APIResponseEntity;
import com.rcibanque.rcidirect.services.core.rest.helper.impl.RestHelper;
import com.rcibanque.rcidirect.services.core.rest.helper.impl.RestType;
import com.rcibanque.rcidirect.services.core.system.service.impl.SystemService;
import com.rcibanque.rcidirect.services.core.user.User;
import com.rcibanque.rcidirect.services.creditcheck.domain.CallType;
import com.rcibanque.rcidirect.services.creditcheck.domain.CallerType;
import com.rcibanque.rcidirect.services.creditcheck.domain.CreditCheckProviderResult;
import com.rcibanque.rcidirect.services.creditcheck.domain.CreditCheckResponse;
import com.rcibanque.rcidirect.services.creditcheck.domain.IECreditCheckCriteria;
import com.rcibanque.rcidirect.services.creditcheck.domain.IECreditCheckMessage;
import com.rcibanque.rcidirect.services.creditcheck.domain.IECreditCheckRequest;
import com.rcibanque.rcidirect.services.creditcheck.domain.LegalStatus;
import com.rcibanque.rcidirect.services.finances.utils.FinanceConstants;
import com.rcibanque.rcidirect.services.general.parameters.domain.AgreementStatus;
import com.rcibanque.rcidirect.services.webservice.domain.WebServiceData;
import com.rcibanque.rcidirect.services.webservice.service.IWebServiceDataService;

class CreditCheckServiceTest {

	@InjectMocks
	@Spy
	private CreditCheckService _creditCheckService;

	@Mock
	private AgreementService _agreementService;

	@Mock
	private ActorService _actorService;

	@Mock
	private AddressService _addressService;

	@Mock
	private IndividualActorService _individualActorService;

	@Mock
	private BusinessActorService _businessActorService;

	@Mock
	private DirectorActorService _directorActorService;

	@Mock
	private EmploymentService _employmentService;

	@Mock
	private RoleService _roleService;

	@Mock
	private ActorLinkService _actorLinkService;

	@Mock
	private IWebServiceDataService _webServiceDataService;

	@Mock
	private SystemService _systemService;

	@Mock
	private QueueSender _queueSender;

	@Mock
	private IContext _context;

	@Mock
	private RestHelper _restHelper;

	@Mock
	protected Environment _env;

	private IECreditCheckRequest _creditCheckRequest;

	private IECreditCheckRequest _updateCreditCheckRequest;

	private Agreement _agreement;

	private BasicAgreement _basicAgreement;

	private FinancialDetail _financialDetail;

	private IndividualActor _individualActor;

	private WebServiceData _webServiceData;

	private WebServiceData _failedWebServiceData;

	private List<WebServiceData> _webServiceDataList;

	private IECreditCheckCriteria _creditCheckCriteria;

	private BusinessActor _businessActor;

	private DirectorActor _directorActor;

	private CreditCheckResponse _response;

	private CreditCheckResponse _failedResponse;

	private APIResponse<CreditCheckResponse> _apiResponse;

	private APIResponse<CreditCheckResponse> _failedApiResponse;

	private APIResponseEntity<CreditCheckResponse> _apiResponseEntity;

	private APIResponseEntity<CreditCheckResponse> _failedApiResponseEntity;

	private String _url;

	private static final RestType<CreditCheckResponse> RESPONSE_TYPE = new RestType<CreditCheckResponse>() {
	};

	// Regular expression for extracting the XML tag enclosing the Credit Check Reference from the response
	private static final Pattern CREDIT_CHECK_REF_TAG_REGEX = Pattern.compile("<cb:ApplicationCodes (.+?)/>", Pattern.DOTALL);

	// Regular expression for extracting the Credit Check Reference from the response
	private static final Pattern CREDIT_CHECK_REF_REGEX = Pattern.compile("CCRContractCode=\\\"(.+?)\\\"", Pattern.DOTALL);

	@BeforeEach
	void setUp() throws Exception {

		MockitoAnnotations.initMocks(this);

		_agreement = new Agreement();
		_financialDetail = new FinancialDetail();
		_financialDetail.setFinanceTypeCode(FinanceConstants.FINANCE_TYPE_PCP);
		_agreement.setFinancialDetail(_financialDetail);
		_agreement.setAgreementGroupId(1L);
		_agreement.setAgreementGroupName("CC");

		_basicAgreement = new BasicAgreement();
		_basicAgreement.setAgreementId(new Long(12345));

		_individualActor = new IndividualActor();
		_individualActor.setActorCode("12345");
		_individualActor.setActorCategoryCode(ActorCategories.INDIVIDUAL_ACTOR.getCategoryCode());
		_individualActor.setTitleCode(1);

		_businessActor = new BusinessActor();
		_businessActor.setActorCategoryCode(ActorCategories.BUSINESS_ACTOR.getCategoryCode());
		_businessActor.setLegalStatusCode(LegalStatus.CHARITY.getLegalNameCode());

		_directorActor = new DirectorActor();
		_directorActor.setActorCategoryCode(ActorCategories.INDIVIDUAL_ACTOR.getCategoryCode());
		_directorActor.setTitleCode(1);

		_creditCheckRequest = new IECreditCheckRequest();
		_creditCheckRequest.setCallType(CallType.NEW);
		_creditCheckRequest.setCallerType(CallerType.SUBMIT);
		_creditCheckRequest.setEnquiryType("NAE");
		_creditCheckRequest.setAgreement(_agreement);
		_creditCheckRequest.setActor(_individualActor);

		_updateCreditCheckRequest = new IECreditCheckRequest();
		_updateCreditCheckRequest.setCallType(CallType.UPDATE);
		_updateCreditCheckRequest.setCallerType(CallerType.STATEMENT);
		_updateCreditCheckRequest.setEnquiryType("AUE");
		_updateCreditCheckRequest.setAgreement(_agreement);
		_updateCreditCheckRequest.setActor(_individualActor);

		_webServiceData = new WebServiceData();
		_webServiceData.setWebServiceId(new Long(1));
		_webServiceData.setRequest("request");
		_webServiceData.setResponse("<cb:ApplicationCodes CCRContractCode=\"Q00327630\" CIPApplicationNo=\"2000143877\"/>");
		_webServiceData.setResponseCode(1);
		_webServiceDataList = new ArrayList<WebServiceData>();
		_webServiceDataList.add(_webServiceData);


		_failedWebServiceData = new WebServiceData();
		_failedWebServiceData.setWebServiceId(new Long(1));
		_failedWebServiceData.setResponseCode(0);


		_creditCheckCriteria = new IECreditCheckCriteria();
		_creditCheckCriteria.setActorCode(Arrays.asList("12345"));
		_creditCheckCriteria.setAgreementId(new Long(12345));
		_creditCheckCriteria.setCallerType(CallerType.SUBMIT);
		_creditCheckCriteria.setCallType(CallType.NEW);
		_creditCheckCriteria.setCurrentDate(new Date());
		_creditCheckCriteria.setEnquiryType("NAE");
		_creditCheckCriteria.setProviders(Arrays.asList("CCR"));

		_url = "testURL";

		_response = new CreditCheckResponse();
		_response.setWebServiceData(_webServiceData);

		_failedResponse = new CreditCheckResponse();
		_failedResponse.setWebServiceData(_failedWebServiceData);

		_apiResponse = new APIResponse<CreditCheckResponse>();
		_failedApiResponse = new APIResponse<CreditCheckResponse>();

		_apiResponseEntity = new APIResponseEntity<CreditCheckResponse>(HttpStatus.OK, _apiResponse);
		_failedApiResponseEntity = new APIResponseEntity<CreditCheckResponse>(HttpStatus.OK, _failedApiResponse);
		_apiResponseEntity.getBody().setData(_response);
		_failedApiResponseEntity.getBody().setData(_failedResponse);

	}

	@Test
	void shouldReturnSuccessWhenSendCreditCheckRequestForNewEnquiryToQueueIsSuccess() throws JMSException {

		// Given
		willDoNothing().given(_queueSender).sendToQueue(any(), any(IECreditCheckMessage.class));

		// When
		CreditCheckProviderResult res = _creditCheckService.requestCreditCheck(_context, Arrays.asList(_creditCheckRequest), CallerType.SUBMIT);

		// Then
		assertThat(res).isEqualTo(CreditCheckProviderResult.PERFORMED_PENDING);
		verify(_queueSender, times(1)).sendToQueue(any(), any(IECreditCheckMessage.class));
	}

	@Test
	void shouldReturnFailureWhenSendCreditCheckRequestToQueueThrowsException() throws JMSException {

		// Given
		willThrow(new JMSException("Queue is not configured")).given(_queueSender).sendToQueue(any(), any(IECreditCheckMessage.class));

		// When
		CreditCheckProviderResult	res = _creditCheckService.requestCreditCheck(_context, Arrays.asList(_creditCheckRequest), CallerType.SUBMIT);

		// Then
		assertThat(res).isEqualTo(CreditCheckProviderResult.TECHNICAL_ERROR);
		verify(_queueSender, times(1)).sendToQueue(any(), any(IECreditCheckMessage.class));
	}

	@Test
	void shouldNotCreditCheckWhenValidCreditCheckRequestForNewEnquiryAndCreditCheckDataExists() {

		// Given
		given(_env.getProperty("webservice.crif.url")).willReturn(new String(_url));
		given(_agreementService.searchAgreements(any(IContext.class), any())).willReturn(Arrays.asList(_basicAgreement));
		given(_systemService.getCurrentDateUpdatedTime()).willReturn(new Date());
		given(_webServiceDataService.searchWebServiceData(any(IContext.class), any())).willReturn(_webServiceDataList);
		given(_context.getMessages()).willReturn(APIMessages.getInstance(new Locale("en", "IE")));
		given(_context.getUser()).willReturn(new User());

		// When
		Boolean res = _creditCheckService.doCreditCheck(_context, Arrays.asList(_creditCheckRequest), CallerType.SUBMIT);

		// Then
		assertThat(res).isTrue();
		verify(_creditCheckService, times(0)).getRestHelper();
		verify(_restHelper, times(0)).post(_context, _url, null, _creditCheckRequest, RESPONSE_TYPE, true);
		verify(_env, times(1)).getProperty("webservice.crif.url");
		verify(_agreementService, times(1)).searchAgreements(any(IContext.class), any());
		verify(_systemService, times(1)).getCurrentDateUpdatedTime();
		verify(_webServiceDataService, times(1)).searchWebServiceData(any(IContext.class), any());

	}

	@Test
	void shouldReturnSuccessWhenValidCreditCheckRequestForNewEnquiryWhenNoExistingCreditCheck() {

		// Given
		given(_creditCheckService.getRestHelper()).willReturn(_restHelper);
		given(_restHelper.post(_context, _url, null, _creditCheckRequest, RESPONSE_TYPE, true)).willReturn(_apiResponseEntity);
		given(_env.getProperty("webservice.crif.url")).willReturn(new String(_url));
		given(_agreementService.searchAgreements(any(IContext.class), any())).willReturn(Arrays.asList(_basicAgreement));
		given(_systemService.getCurrentDateUpdatedTime()).willReturn(new Date());
		given(_webServiceDataService.searchWebServiceData(any(IContext.class), any())).willReturn(null);
		given(_webServiceDataService.save(any(IContext.class), any())).willReturn(_webServiceData);
		given(_context.getMessages()).willReturn(APIMessages.getInstance(new Locale("en", "IE")));
		given(_context.getUser()).willReturn(new User());

		// When
		Boolean res = _creditCheckService.doCreditCheck(_context, Arrays.asList(_creditCheckRequest), CallerType.SUBMIT);

		// Then
		assertThat(res).isTrue();
		verify(_creditCheckService, times(1)).getRestHelper();
		verify(_restHelper, times(1)).post(_context, _url, null, _creditCheckRequest, RESPONSE_TYPE, true);
		verify(_env, times(1)).getProperty("webservice.crif.url");
		verify(_agreementService, times(1)).searchAgreements(any(IContext.class), any());
		verify(_webServiceDataService, times(2)).searchWebServiceData(any(IContext.class), any());
		verify(_webServiceDataService, times(1)).save(any(IContext.class), any());

	}

	@Test
	void shouldReturnSuccessWhenValidCreditCheckRequestForUpdateEnquiry() {

		// Given
		given(_creditCheckService.getRestHelper()).willReturn(_restHelper);
		given(_restHelper.post(_context, _url, null, _updateCreditCheckRequest, RESPONSE_TYPE, true)).willReturn(_apiResponseEntity);
		given(_env.getProperty("webservice.crif.url")).willReturn(new String(_url));
		given(_agreementService.searchAgreements(any(IContext.class), any())).willReturn(Arrays.asList(_basicAgreement));
		given(_webServiceDataService.searchWebServiceData(any(IContext.class), any())).willReturn(_webServiceDataList);
		given(_systemService.getCurrentDateUpdatedTime()).willReturn(new Date());
		given(_webServiceDataService.save(any(IContext.class), any())).willReturn(_webServiceData);
		given(_context.getMessages()).willReturn(APIMessages.getInstance(new Locale("en", "IE")));
		given(_context.getUser()).willReturn(new User());
		given(_creditCheckService.getMatchingVal("", CREDIT_CHECK_REF_TAG_REGEX)).willReturn("testTag");
		given(_creditCheckService.getMatchingVal("", CREDIT_CHECK_REF_REGEX)).willReturn("creditCheckRef");

		// When
		Boolean res = _creditCheckService.doCreditCheck(_context, Arrays.asList(_updateCreditCheckRequest), CallerType.STATEMENT);

		// Then
		assertThat(res).isTrue();
		verify(_creditCheckService, times(1)).getRestHelper();
		verify(_restHelper, times(1)).post(_context, _url, null, _updateCreditCheckRequest, RESPONSE_TYPE, true);
		verify(_env, times(1)).getProperty("webservice.crif.url");
		verify(_agreementService, times(1)).searchAgreements(any(IContext.class), any());
		verify(_webServiceDataService, times(1)).searchWebServiceData(any(IContext.class), any());
		verify(_webServiceDataService, times(1)).save(any(IContext.class), any());

	}

	@Test
	void shouldReturnFailureWhenFailureResponseForNewEnquiry() {

		// Given
		given(_creditCheckService.getRestHelper()).willReturn(_restHelper);
		given(_restHelper.post(_context, _url, null, _creditCheckRequest, RESPONSE_TYPE, true)).willReturn(_failedApiResponseEntity);
		given(_env.getProperty("webservice.crif.url")).willReturn(new String(_url));
		given(_agreementService.searchAgreements(any(IContext.class), any())).willReturn(Arrays.asList(_basicAgreement));
		given(_systemService.getCurrentDateUpdatedTime()).willReturn(new Date());
		given(_webServiceDataService.searchWebServiceData(any(IContext.class), any())).willReturn(null);
		given(_webServiceDataService.save(any(IContext.class), any())).willReturn(_webServiceData);
		given(_context.getMessages()).willReturn(APIMessages.getInstance(new Locale("en", "IE")));
		given(_context.getUser()).willReturn(new User());

		// When
		Boolean res = _creditCheckService.doCreditCheck(_context, Arrays.asList(_creditCheckRequest), CallerType.SUBMIT);

		// Then
		assertThat(res).isFalse();
		verify(_creditCheckService, times(1)).getRestHelper();
		verify(_restHelper, times(1)).post(_context, _url, null, _creditCheckRequest, RESPONSE_TYPE, true);
		verify(_env, times(1)).getProperty("webservice.crif.url");
		verify(_agreementService, times(1)).searchAgreements(any(IContext.class), any());
		verify(_webServiceDataService, times(2)).searchWebServiceData(any(IContext.class), any());
		verify(_webServiceDataService, times(1)).save(any(IContext.class), any());

	}

	@Test
	void shouldReturnFailureWhenClientUrlConfigIsMissing() {

		// Given
		given(_creditCheckService.getRestHelper()).willReturn(_restHelper);
		given(_restHelper.post(_context, null, null, _creditCheckRequest, RESPONSE_TYPE, true)).willReturn(_failedApiResponseEntity);
		given(_env.getProperty("webservice.crif.url")).willReturn(null);
		given(_context.getMessages()).willReturn(APIMessages.getInstance(new Locale("en", "IE")));
		given(_systemService.getCurrentDateUpdatedTime()).willReturn(new Date());
		given(_webServiceDataService.save(any(IContext.class), any())).willReturn(_webServiceData);
		given(_context.getUser()).willReturn(new User());

		// When
		Boolean res = _creditCheckService.doCreditCheck(_context, Arrays.asList(_creditCheckRequest), CallerType.SUBMIT);

		// Then
		assertThat(res).isFalse();
		verify(_env, times(1)).getProperty("webservice.crif.url");
		verify(_creditCheckService, times(1)).getRestHelper();
		verify(_restHelper, times(1)).post(_context, null, null, _creditCheckRequest, RESPONSE_TYPE, true);
	}

	@Test
	void shouldGetCreditCheckRequestWhenValidIndividualActor() {

		// Given
		given(_agreementService.getAgreementMinimal(any(IContext.class), any())).willReturn(_agreement);
		given(_actorService.getActor(_context, _creditCheckCriteria.getActorCode().get(0), null, true)).willReturn(_individualActor);
		given(_individualActorService.getIndividualActor(any(IContext.class), any(), any())).willReturn(_individualActor);

		// When
		List<IECreditCheckRequest> res = _creditCheckService.getCreditCheckRequest(_context, _creditCheckCriteria);

		// Then
		assertThat(res).isNotEmpty();
		verify(_agreementService, times(1)).getAgreementMinimal(any(IContext.class), any());
		verify(_actorService, times(1)).getActor(_context, _creditCheckCriteria.getActorCode().get(0), null, true);
		verify(_individualActorService, times(1)).getIndividualActor(any(IContext.class), any(), any());

	}

	@Test
	void shouldGetCreditCheckRequestWhenValidBusinnessActor() {

		// Given
		given(_agreementService.getAgreementMinimal(any(IContext.class), any())).willReturn(_agreement);
		given(_actorService.getActor(_context, _creditCheckCriteria.getActorCode().get(0), null, true)).willReturn(_businessActor);
		given(_businessActorService.getBusinessActor(any(IContext.class), any(), any())).willReturn(_businessActor);
		given(_actorLinkService.getActorLink(any(IContext.class), any())).willReturn(new ActorLink());
		given(_directorActorService.getDirectorActor(any(IContext.class), any(), any())).willReturn(_directorActor);
		given(_roleService.hasActorRoleOnAgreement(any(IContext.class), any(), any(), any())).willReturn(Boolean.TRUE);

		// When
		List<IECreditCheckRequest> res = _creditCheckService.getCreditCheckRequest(_context, _creditCheckCriteria);

		// Then
		assertThat(res).isNotEmpty();
		verify(_agreementService, times(1)).getAgreementMinimal(any(IContext.class), any());
		verify(_actorService, times(1)).getActor(_context, _creditCheckCriteria.getActorCode().get(0), null, true);
		verify(_actorLinkService, times(1)).getActorLink(any(IContext.class), any());
		verify(_directorActorService, times(1)).getDirectorActor(any(IContext.class), any(), any());
		verify(_roleService, times(1)).hasActorRoleOnAgreement(any(IContext.class), any(), any(), any());

	}

	@Test
	void shouldGetCreditCheckRequestWhenValidIndividualActorBelongsToGroupAgreement() {

		// Given
		given(_agreementService.getAgreementMinimal(any(IContext.class), any())).willReturn(_agreement);
		given(_agreementService.getGroupAgreementsMinimal(any(IContext.class), any())).willReturn(getAgreements());
		given(_actorService.getActor(_context, _creditCheckCriteria.getActorCode().get(0), null, true)).willReturn(_individualActor);
		given(_individualActorService.getIndividualActor(any(IContext.class), any(), any())).willReturn(_individualActor);

		// When
		List<IECreditCheckRequest> res = _creditCheckService.getCreditCheckRequest(_context, _creditCheckCriteria);

		// Then
		assertThat(res).isNotEmpty();
		assertThat(res.get(0).getAgreement().getFinancialDetail().getFinancedAmount().getInclVat().equals(new BigDecimal(getAgreements().size()*1000)));
		verify(_agreementService, times(1)).getAgreementMinimal(any(IContext.class), any());
		verify(_agreementService, times(1)).getGroupAgreementsMinimal(any(IContext.class), any());
		verify(_actorService, times(1)).getActor(_context, _creditCheckCriteria.getActorCode().get(0), null, true);
		verify(_individualActorService, times(1)).getIndividualActor(any(IContext.class), any(), any());

	}


	private static List<Agreement> getAgreements() {

		List<Agreement> res = new ArrayList<>();

		for(int i=1; i<=5; i++) {

			Agreement agreement = new Agreement();
			agreement.setAgreementId(1L + i);
			agreement.setStatusCode(AgreementStatus.APPROVED.getCode());

			agreement.setFinancialDetail(new FinancialDetail());
			agreement.getFinancialDetail().setFinancedAmount(new Price(new BigDecimal(1000), new BigDecimal(1000)));

			res.add(agreement);
		}

		return res;
	}

}
