package com.rcibanque.rcidirect.services.creditcheck.resource.impl;

import static org.assertj.core.api.Assertions.assertThat;
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

import com.rcibanque.rcidirect.services.core.domain.IContext;
import com.rcibanque.rcidirect.services.core.response.APIResponseEntity;
import com.rcibanque.rcidirect.services.creditcheck.domain.CallerType;
import com.rcibanque.rcidirect.services.creditcheck.domain.CreditCheckProviderResult;
import com.rcibanque.rcidirect.services.creditcheck.domain.IECreditCheckCriteria;
import com.rcibanque.rcidirect.services.creditcheck.domain.IECreditCheckRequest;
import com.rcibanque.rcidirect.services.creditcheck.service.impl.CreditCheckService;

public class CreditCheckResourceTest {

	@InjectMocks
	CreditCheckResource _creditCheckResource;

	@Mock
	private CreditCheckService _creditCheckService;

	private IECreditCheckCriteria _creditCheckCriteria;

	private List<IECreditCheckRequest> _reqList;

	private List<IECreditCheckRequest> _emptyReqList;

	private List<IECreditCheckRequest> _nullReqList;

	@BeforeEach
	void setUp() throws Exception {

		MockitoAnnotations.initMocks(this);

		_creditCheckCriteria = new IECreditCheckCriteria();
		_reqList = new ArrayList<>();
		_emptyReqList = new ArrayList<>();
		IECreditCheckRequest creditCheckRequest = new IECreditCheckRequest();
		_reqList.add(creditCheckRequest);
	}

	@Test
	void shouldCreditCheck_WhenValidActor() {

		// Given
		given(_creditCheckService.getCreditCheckRequest(any(IContext.class), any(IECreditCheckCriteria.class))).willReturn(_reqList);
		given(_creditCheckService.requestCreditCheck(any(IContext.class), anyList(), any())).willReturn(CreditCheckProviderResult.PERFORMED_PENDING);

		// When
		APIResponseEntity<Boolean> response = _creditCheckResource.creditCheckActor(_creditCheckCriteria);

		// Then
		assertThat(response.getBody().getData()).isTrue();
		verify(_creditCheckService, times(1)).getCreditCheckRequest(any(IContext.class), any(IECreditCheckCriteria.class));
		verify(_creditCheckService, times(1)).requestCreditCheck(any(IContext.class), anyList(), any());

	}

	@Test
	public void shouldNotRequestCreditCheck_WhenEmptyReqList() {

		// Given
		given(_creditCheckService.getCreditCheckRequest(any(IContext.class), any(IECreditCheckCriteria.class))).willReturn(_emptyReqList);

		// When
		APIResponseEntity<Boolean> response = _creditCheckResource.creditCheckActor(_creditCheckCriteria);

		// Then
		assertThat(response.getBody().getData()).isTrue(); // Ignored => true
		verify(_creditCheckService, times(1)).getCreditCheckRequest(any(IContext.class), any(IECreditCheckCriteria.class));
		verify(_creditCheckService, times(0)).requestCreditCheck(any(IContext.class), anyList(), any(CallerType.class));

	}

	@Test
	public void shouldNotRequestCreditCheck_WhenNullReqList() {

		// Given
		given(_creditCheckService.getCreditCheckRequest(any(IContext.class), any(IECreditCheckCriteria.class))).willReturn(_nullReqList);

		// When
		APIResponseEntity<Boolean> response = _creditCheckResource.creditCheckActor(_creditCheckCriteria);

		// Then
		assertThat(response.getBody().getData()).isTrue(); // Ignored => true
		verify(_creditCheckService, times(1)).getCreditCheckRequest(any(IContext.class), any(IECreditCheckCriteria.class));
		verify(_creditCheckService, times(0)).requestCreditCheck(any(IContext.class), anyList(), any(CallerType.class));

	}

}
