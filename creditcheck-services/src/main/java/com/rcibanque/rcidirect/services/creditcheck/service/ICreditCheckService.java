package com.rcibanque.rcidirect.services.creditcheck.service;

import java.util.List;

import com.rcibanque.rcidirect.services.core.domain.IContext;
import com.rcibanque.rcidirect.services.creditcheck.domain.CallerType;
import com.rcibanque.rcidirect.services.creditcheck.domain.CreditCheckProviderResult;
import com.rcibanque.rcidirect.services.creditcheck.domain.IECreditCheckCriteria;
import com.rcibanque.rcidirect.services.creditcheck.domain.IECreditCheckRequest;

public interface ICreditCheckService {

	CreditCheckProviderResult requestCreditCheck(IContext pContext, List<IECreditCheckRequest> pCreditCheckRequest, CallerType pCallerType);

	Boolean doCreditCheck(IContext pContext, List<IECreditCheckRequest> pCreditCheckRequest, CallerType pCallerType);

	List<IECreditCheckRequest> getCreditCheckRequest(IContext pContext, IECreditCheckCriteria pIECreditCheckCriteria);

}
