package com.rcibanque.rcidirect.clients.crif.service;

import com.rcibanque.rcidirect.clients.crif.domain.CreditCheckRequest;
import com.rcibanque.rcidirect.clients.crif.domain.CreditCheckResponse;
import com.rcibanque.rcidirect.services.core.domain.IContext;

public interface ICrifService {

	CreditCheckResponse creditCheckActor(IContext context, CreditCheckRequest pCreditCheckRequest);

}
