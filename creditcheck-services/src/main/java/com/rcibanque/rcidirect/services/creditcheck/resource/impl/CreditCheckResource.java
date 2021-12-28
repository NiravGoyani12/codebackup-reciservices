package com.rcibanque.rcidirect.services.creditcheck.resource.impl;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rcibanque.rcidirect.services.core.domain.IContext;
import com.rcibanque.rcidirect.services.core.response.APIResponseEntity;
import com.rcibanque.rcidirect.services.creditcheck.domain.CreditCheckProviderResult;
import com.rcibanque.rcidirect.services.creditcheck.domain.IECreditCheckCriteria;
import com.rcibanque.rcidirect.services.creditcheck.domain.IECreditCheckRequest;
import com.rcibanque.rcidirect.services.creditcheck.service.ICreditCheckService;
import com.rcibanque.rcidirect.services.general.parameters.resource.impl.CommonResource;

@RestController
@RequestMapping(value = "/creditcheck", produces = MediaType.APPLICATION_JSON_VALUE)
public class CreditCheckResource extends CommonResource {

	@Autowired
	private ICreditCheckService _creditCheckService;


	@PostMapping(value = "/actor")
	public APIResponseEntity<Boolean> creditCheckActor(@RequestBody IECreditCheckCriteria pIECreditCheckCriteria) {

		IContext context = initializeContext();

		CreditCheckProviderResult creditCheckResult;

		List<IECreditCheckRequest> reqList = _creditCheckService.getCreditCheckRequest(context, pIECreditCheckCriteria);

		if (CollectionUtils.isNotEmpty(reqList)) {

			creditCheckResult = _creditCheckService.requestCreditCheck(context, reqList, pIECreditCheckCriteria.getCallerType());
		}
		else {
			creditCheckResult = CreditCheckProviderResult.IGNORED;
		}

		// Response type should be replaced to explicitly describe what happened (a boolean is too restrictive)
		// Despite introducing the CreditCheckProviderResult enumeration, the boolean response has been kept as there is an impact on V1
		return responseOK(context, creditCheckResult.isOK());
	}

}
