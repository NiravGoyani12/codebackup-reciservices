package com.rcibanque.rcidirect.services.creditcheck.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rcibanque.rcidirect.services.actors.dao.criteria.CosignerCriteria;
import com.rcibanque.rcidirect.services.actors.domain.CosignerActor;
import com.rcibanque.rcidirect.services.actors.service.ICosignerActorService;
import com.rcibanque.rcidirect.services.agreements.domain.Agreement;
import com.rcibanque.rcidirect.services.agreements.service.IAgreementService;
import com.rcibanque.rcidirect.services.core.domain.IContext;
import com.rcibanque.rcidirect.services.core.utils.IteratorUtils;
import com.rcibanque.rcidirect.services.creditcheck.domain.CallType;
import com.rcibanque.rcidirect.services.creditcheck.domain.CallerType;
import com.rcibanque.rcidirect.services.creditcheck.domain.CreditCheckProviderResult;
import com.rcibanque.rcidirect.services.creditcheck.domain.CreditCheckRequest;
import com.rcibanque.rcidirect.services.creditcheck.domain.IECreditCheckCriteria;
import com.rcibanque.rcidirect.services.creditcheck.domain.IECreditCheckRequest;
import com.rcibanque.rcidirect.services.creditcheck.service.ICreditCheckProvider;
import com.rcibanque.rcidirect.services.creditcheck.service.ICreditCheckService;
import com.rcibanque.rcidirect.services.general.parameters.service.impl.CommonService;

@Service
public class CreditCheckProvider extends CommonService implements ICreditCheckProvider {

	public static final String DEFAULT_PROVIDER = "CCR";

	public static final String DEFAULT_ENQUIRY_TYPE = "NAE";


	@Autowired
	private ICreditCheckService _creditCheckService;

	@Autowired
	private IAgreementService _agreementService;

	@Autowired
	private ICosignerActorService _cosignerActorService;


	@Override
	public CreditCheckProviderResult handle(IContext pContext, CreditCheckRequest pCreditCheckRequest) {

		CreditCheckProviderResult res;

		Agreement agreement = _agreementService.getAgreementMinimal(pContext, pCreditCheckRequest.getAgreementId());

		// Credit check should be done on the Customer and the cosigner actors
		Collection<String> actorCodes = new HashSet<>();
		actorCodes.add(agreement.getCustomerActorCode());

		CosignerCriteria cosignerCriteria = new CosignerCriteria();
		cosignerCriteria.setAgreementId(pCreditCheckRequest.getAgreementId());
		for(CosignerActor cosigner : IteratorUtils.nullableIterable(_cosignerActorService.getCosignerActors(pContext, cosignerCriteria))) {
			actorCodes.add(cosigner.getActorCode());
		}

		IECreditCheckCriteria creditCheckCriteria = new IECreditCheckCriteria();
		creditCheckCriteria.setAgreementId(pCreditCheckRequest.getAgreementId());
		creditCheckCriteria.setActorCode(new ArrayList<>(actorCodes));
		creditCheckCriteria.setProviders(Arrays.asList(DEFAULT_PROVIDER));
		creditCheckCriteria.setEnquiryType(DEFAULT_ENQUIRY_TYPE);
		creditCheckCriteria.setCallType(CallType.NEW);
		creditCheckCriteria.setCallerType(CallerType.SUBMIT);

		List<IECreditCheckRequest> creditCheckRequests = _creditCheckService.getCreditCheckRequest(pContext, creditCheckCriteria);

		if (CollectionUtils.isNotEmpty(creditCheckRequests)) {

			res = _creditCheckService.requestCreditCheck(pContext, creditCheckRequests, CallerType.SUBMIT);
		}
		else {
			res = CreditCheckProviderResult.IGNORED;
		}

		return res;
	}

}
