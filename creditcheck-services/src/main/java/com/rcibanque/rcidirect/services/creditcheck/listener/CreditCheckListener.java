package com.rcibanque.rcidirect.services.creditcheck.listener;

import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.JmsException;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.rcibanque.rcidirect.services.agreements.domain.Agreement;
import com.rcibanque.rcidirect.services.agreements.service.IAgreementService;
import com.rcibanque.rcidirect.services.agreements.service.IBasketMoveService;
import com.rcibanque.rcidirect.services.core.domain.IContext;
import com.rcibanque.rcidirect.services.creditcheck.domain.CallerType;
import com.rcibanque.rcidirect.services.creditcheck.domain.IECreditCheckMessage;
import com.rcibanque.rcidirect.services.creditcheck.domain.IECreditCheckRequest;
import com.rcibanque.rcidirect.services.creditcheck.service.ICreditCheckService;

@Component
public class CreditCheckListener {

	private static final Logger LOGGER = LoggerFactory.getLogger(CreditCheckListener.class);


	@Autowired
	private ICreditCheckService _creditCheckService;

	@Autowired
	private IAgreementService _agreementService;

	@Autowired
	private IBasketMoveService _basketMoveService;


	@JmsListener(containerFactory = "jmsListenerContainerFactory", destination = "${messaging.creditcheck.destination}")
	public void doCreditCheck(IECreditCheckMessage pCreditCheckMessage) throws JmsException {

		IContext context = pCreditCheckMessage.getContext();
		List<IECreditCheckRequest> creditCheckRequests = pCreditCheckMessage.getCreditCheckRequest();

		LOGGER.debug("Received credit check request for agreements: " + listAgreements(creditCheckRequests));

		Boolean isCreditCheckSuccess = _creditCheckService.doCreditCheck(context, creditCheckRequests, pCreditCheckMessage.getCallerType());

		if (isCreditCheckSuccess && CallerType.SUBMIT.equals(pCreditCheckMessage.getCallerType())) {

			moveProposalToPendingBasket(pCreditCheckMessage, context);
		}
	}

	private void moveProposalToPendingBasket(IECreditCheckMessage pCreditCheckMessage, IContext pContext) {

		IECreditCheckRequest creditCheckReq = pCreditCheckMessage.getCreditCheckRequest().stream()
				.filter(Objects::nonNull).findFirst().orElse(null);

		if (null != creditCheckReq) {

			Agreement agreement = _agreementService.getAgreementMinimal(pContext, creditCheckReq.getAgreement().getAgreementId());
			_basketMoveService.pendingBasketMoveFromSubmitted(pContext, agreement);
		}
	}

	private String listAgreements(List<IECreditCheckRequest> pCreditCheckRequest){

		return pCreditCheckRequest.stream()
				.map(creditCheck -> creditCheck.getAgreement().getAgreementId().toString())
				.reduce("", (charset, creditCheck) -> charset + creditCheck + ",");
	}

}
