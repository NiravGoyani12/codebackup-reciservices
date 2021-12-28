package com.rcibanque.rcidirect.clients.crif.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rcibanque.rcidirect.clients.crif.domain.CreditCheckRequest;
import com.rcibanque.rcidirect.clients.crif.domain.CreditCheckResponse;
import com.rcibanque.rcidirect.clients.crif.service.ICrifService;
import com.rcibanque.rcidirect.services.core.domain.IContext;
import com.rcibanque.rcidirect.services.core.resource.Resource;
import com.rcibanque.rcidirect.services.core.response.APIResponseEntity;

@RestController
@RequestMapping(value = "/crif", produces = MediaType.APPLICATION_JSON_VALUE)
public class CrifResource extends Resource {

	@Autowired
	private ICrifService _crifService;

	@PostMapping(value = "/creditcheck/actor")
	public APIResponseEntity<CreditCheckResponse> creditCheckActor(@RequestBody CreditCheckRequest pCreditCheckRequest) {

		IContext context = initializeContext();

		CreditCheckResponse res = _crifService.creditCheckActor(context, pCreditCheckRequest);

		return responseOK(context, res);
	}
}
