package com.rcibanque.rcidirect.services.creditcheck.domain;

import java.io.Serializable;
import java.util.List;

import com.rcibanque.rcidirect.services.core.domain.IContext;

public class IECreditCheckMessage implements Serializable {

	private static final long serialVersionUID = -6874280364573326136L;

	private IContext _context;

	private List<IECreditCheckRequest> _creditCheckRequest;

	private CallerType _callerType;

	public IECreditCheckMessage() {
	}

	public IECreditCheckMessage(IContext pContext, List<IECreditCheckRequest> pCreditCheckRequest, CallerType pCallerType) {
		_context = pContext;
		_creditCheckRequest = pCreditCheckRequest;
		_callerType = pCallerType;
	}

	public IContext getContext() {
		return _context;
	}

	public void setContext(IContext pContext) {
		_context = pContext;
	}

	public List<IECreditCheckRequest> getCreditCheckRequest() {
		return _creditCheckRequest;
	}

	public void setIeCreditCheckRequest(List<IECreditCheckRequest> pCreditCheckRequest) {
		_creditCheckRequest = pCreditCheckRequest;
	}

	public CallerType getCallerType() {
		return _callerType;
	}

	public void setCallerType(CallerType pCallerType) {
		_callerType = pCallerType;
	}
}
