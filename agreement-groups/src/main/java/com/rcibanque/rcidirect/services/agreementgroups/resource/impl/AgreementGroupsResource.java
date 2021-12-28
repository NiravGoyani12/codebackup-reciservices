package com.rcibanque.rcidirect.services.agreementgroups.resource.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rcibanque.rcidirect.services.agreementgroups.dao.criteria.AgreementGroupCriteria;
import com.rcibanque.rcidirect.services.agreementgroups.domain.AgreementGroup;
import com.rcibanque.rcidirect.services.agreementgroups.domain.AgreementGroupAgreement;
import com.rcibanque.rcidirect.services.agreementgroups.service.IAgreementGroupsService;
import com.rcibanque.rcidirect.services.core.domain.IContext;
import com.rcibanque.rcidirect.services.core.response.APIResponseEntity;
import com.rcibanque.rcidirect.services.core.validation.utils.ValidationUtils;
import com.rcibanque.rcidirect.services.core.validation.validator.IValidator;
import com.rcibanque.rcidirect.services.general.parameters.resource.impl.CommonResource;
import com.rcibanque.rcidirect.services.general.validation.ProcessStage;

@RestController
@RequestMapping(value = "/agreement-groups", produces = MediaType.APPLICATION_JSON_VALUE)
public class AgreementGroupsResource extends CommonResource {

	@Autowired
	private IAgreementGroupsService _agreementGroupsService;

	@Autowired
	@Qualifier("AgreementGroupValidator")
	private IValidator<AgreementGroup> _agreementGroupValidator;


	@GetMapping
	public APIResponseEntity<List<AgreementGroup >> getAgreementGroups(
			@RequestParam(name = "customer_actor_code", required = true) String pCustomerActorCode,
			@RequestParam(name = "financial_company_code", required = false) String pFinancialCompanyCode,
			@RequestParam(name = "master_agreement", required = false) Boolean pMasterAgreement,
			@RequestParam(name = "candidate_agreement_id", required = false) Long pCandidateAgreementId) {

		IContext context = initializeContext();

		AgreementGroupCriteria criteria = new AgreementGroupCriteria();
		criteria.setCustomerActorCode(pCustomerActorCode);
		criteria.setFinancialCompanyCode(pFinancialCompanyCode);
		criteria.setMasterAgreement(pMasterAgreement);
		criteria.setCandidateAgreementId(pCandidateAgreementId);

		List<AgreementGroup> res = _agreementGroupsService.getAgreementGroups(context, criteria);

		return responseOK(context, res);
	}

	@PostMapping
	@Transactional
	public APIResponseEntity<AgreementGroup> createAgreementGroup(
			@RequestBody AgreementGroup pAgreementGroup) {

		IContext context = initializeContext();

		ValidationUtils.validate(context, _agreementGroupValidator, pAgreementGroup, ProcessStage.WORKING);

		_agreementGroupsService.createAgreementGroup(context, pAgreementGroup);

		return responseOK(context, pAgreementGroup);
	}

	@PutMapping
	@Transactional
	public APIResponseEntity<AgreementGroup> updateAgreementGroup(
			@RequestBody AgreementGroup pAgreementGroup) {

		IContext context = initializeContext();

		ValidationUtils.validate(context, _agreementGroupValidator, pAgreementGroup, ProcessStage.WORKING);

		_agreementGroupsService.updateAgreementGroup(context, pAgreementGroup);

		return responseOK(context, pAgreementGroup);
	}

	@DeleteMapping(value = "/{group_id}")
	@Transactional
	public APIResponseEntity<Void> deleteAgreementGroup(
			@PathVariable("group_id") Long pGroupId) {

		IContext context = initializeContext();

		_agreementGroupsService.deleteAgreementGroup(context, pGroupId);

		return responseOK(context, null);
	}


	@GetMapping(value = "/{group_id}/agreements")
	public APIResponseEntity<List<AgreementGroupAgreement>> getAgreementGroupAgreements(
			@PathVariable("group_id") Long pGroupId,
			@RequestParam(name = "grouped", required = true) Boolean pGrouped) {

		IContext context = initializeContext();

		List<AgreementGroupAgreement> res = _agreementGroupsService.getAgreementGroupAgreements(context, pGroupId, pGrouped);

		return responseOK(context, res);
	}

	@PostMapping(value = "/{group_id}/agreements/{agreement_id}")
	@Transactional
	public APIResponseEntity<Void> addAgreementToAgreementGroup(
			@PathVariable("group_id") Long pGroupId,
			@PathVariable("agreement_id") Long pAgreementId) {

		IContext context = initializeContext();

		_agreementGroupsService.addAgreementToAgreementGroup(context, pGroupId, pAgreementId);

		return responseOK(context, null);
	}


	@DeleteMapping(value = "/{group_id}/agreements/{agreement_id}")
	@Transactional
	public APIResponseEntity<Void> removeAgreementFromAgreementGroup(
			@PathVariable("group_id") Long pGroupId,
			@PathVariable("agreement_id") Long pAgreementId) {

		IContext context = initializeContext();

		_agreementGroupsService.removeAgreementFromAgreementGroup(context, pGroupId, pAgreementId);

		return responseOK(context, null);
	}

}
