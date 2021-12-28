package com.rcibanque.rcidirect.services.creditcheck.listener;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.rcibanque.rcidirect.services.agreements.domain.Agreement;
import com.rcibanque.rcidirect.services.agreements.service.impl.AgreementService;
import com.rcibanque.rcidirect.services.agreements.service.impl.BasketMoveService;
import com.rcibanque.rcidirect.services.core.domain.IContext;
import com.rcibanque.rcidirect.services.creditcheck.domain.CallerType;
import com.rcibanque.rcidirect.services.creditcheck.domain.IECreditCheckMessage;
import com.rcibanque.rcidirect.services.creditcheck.domain.IECreditCheckRequest;
import com.rcibanque.rcidirect.services.creditcheck.service.impl.CreditCheckService;
import com.rcibanque.rcidirect.services.general.parameters.domain.AgreementStatus;

class CreditCheckListenerTest {

	@InjectMocks
	private CreditCheckListener _creditCheckListener;

	@Mock
	private CreditCheckService _creditCheckService;

	@Mock
	private AgreementService _agreementService;

	@Mock
	private BasketMoveService _basketMoveService;

	@Mock
	private IContext _context;

	@Mock
	private IECreditCheckMessage _creditCheckMessageStub;

	private IECreditCheckMessage _creditCheckMessage;

	private Agreement _submittedAgreement;

	@BeforeEach
	void setUp() throws Exception {

		MockitoAnnotations.initMocks(this);

		_submittedAgreement = new Agreement();
		_submittedAgreement.setAgreementId(new Long(1));
		_submittedAgreement.setStatusCode(AgreementStatus.SUBMITTED.getCode());

		IECreditCheckRequest creditCheckRequest = new IECreditCheckRequest();
		creditCheckRequest.setAgreement(_submittedAgreement);
		List<IECreditCheckRequest> creditCheckRequestList = new ArrayList<IECreditCheckRequest>();
		creditCheckRequestList.add(creditCheckRequest);

		_creditCheckMessage = new IECreditCheckMessage();
		_creditCheckMessage.setIeCreditCheckRequest(creditCheckRequestList);
		_creditCheckMessage.setCallerType(CallerType.SUBMIT);

	}

	@Test
	void testDoCreditCheck() {

		// Given
		given(_creditCheckService.doCreditCheck(any(), anyList(), any())).willReturn(Boolean.TRUE);
		given(_agreementService.getAgreement(any(), any(Long.class))).willReturn(_submittedAgreement);

		// When
		_creditCheckListener.doCreditCheck(_creditCheckMessage);

		// Then
		verify(_creditCheckService, times(1)).doCreditCheck(any(), anyList(), any());
		verify(_agreementService, times(1)).getAgreementMinimal(any(), any(Long.class));
		verify(_basketMoveService, times(1)).pendingBasketMoveFromSubmitted(any(), any());

	}

}
