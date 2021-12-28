package com.rcibanque.rcidirect.services.creditcheck.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.rcibanque.rcidirect.services.actors.domain.CosignerActor;
import com.rcibanque.rcidirect.services.actors.service.ICosignerActorService;
import com.rcibanque.rcidirect.services.agreements.domain.Agreement;
import com.rcibanque.rcidirect.services.agreements.service.impl.AgreementService;
import com.rcibanque.rcidirect.services.core.domain.IContext;
import com.rcibanque.rcidirect.services.creditcheck.domain.CallerType;
import com.rcibanque.rcidirect.services.creditcheck.domain.CreditCheckProviderResult;
import com.rcibanque.rcidirect.services.creditcheck.domain.CreditCheckRequest;
import com.rcibanque.rcidirect.services.creditcheck.domain.IECreditCheckRequest;

class CreditCheckProviderTest {

	@InjectMocks
	private CreditCheckProvider _creditCheckProvider;

	@Mock
	private CreditCheckService _creditCheckService;

	@Mock
	private AgreementService _agreementService;

	@Mock
	private ICosignerActorService _cosignerActorService;

	@Mock
	private IContext _context;

	private CreditCheckRequest _creditCheckRequest;

	@BeforeEach
	void setUp() throws Exception {

		MockitoAnnotations.initMocks(this);

		_creditCheckRequest = new CreditCheckRequest();
	}

	@Test
	void shouldReturnSuccessGivenAValidCreditCheckRequestWhenCallingHandle() {

		// Given
		given(_agreementService.getAgreementMinimal(any(IContext.class), any())).willReturn(new Agreement());
		given(_creditCheckService.getCreditCheckRequest(any(IContext.class), any())).willReturn(Collections.singletonList(new IECreditCheckRequest()));
		given(_creditCheckService.requestCreditCheck(any(IContext.class), anyList(), any(CallerType.class))).willReturn(CreditCheckProviderResult.PERFORMED_PENDING);
		given(_cosignerActorService.getCosignerActors(any(IContext.class), any())).willReturn(new ArrayList<CosignerActor>());

		// When
		CreditCheckProviderResult response = _creditCheckProvider.handle(_context, _creditCheckRequest);

		// Then
		assertThat(response).isEqualTo(CreditCheckProviderResult.PERFORMED_PENDING);
		verify(_agreementService, times(1)).getAgreementMinimal(any(IContext.class), any());
		verify(_creditCheckService, times(1)).requestCreditCheck(any(IContext.class), anyList(), any(CallerType.class));
	}


}
