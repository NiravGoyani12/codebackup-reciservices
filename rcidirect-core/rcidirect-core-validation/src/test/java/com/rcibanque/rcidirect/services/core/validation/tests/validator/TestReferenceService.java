package com.rcibanque.rcidirect.services.core.validation.tests.validator;

import java.util.UUID;

import com.rcibanque.rcidirect.services.core.domain.IContext;
import com.rcibanque.rcidirect.services.core.validation.tests.domain.TestClass;
import com.rcibanque.rcidirect.services.core.validation.validator.IReferenceService;

public class TestReferenceService implements IReferenceService {


	private static final String KNOWN_VALUE = "Referenced field known value";

	private static final String UNKNOWN_VALUE = UUID.randomUUID().toString();


	private final String _knownValue;


	public TestReferenceService(boolean pKnownValue) {
		super();
		_knownValue = pKnownValue ? KNOWN_VALUE : UNKNOWN_VALUE;
	}


	@Override
	public Object getObject(IContext pContext, String pReferenceKey) {
		TestClass res = new TestClass();

		res.setField1(_knownValue);

		return res;
	}

}
