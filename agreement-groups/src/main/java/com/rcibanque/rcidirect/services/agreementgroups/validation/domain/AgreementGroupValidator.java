package com.rcibanque.rcidirect.services.agreementgroups.validation.domain;

import org.springframework.stereotype.Component;

import com.rcibanque.rcidirect.services.agreementgroups.domain.AgreementGroup;
import com.rcibanque.rcidirect.services.agreementgroups.validation.AbstractAgreementGroupValidator;
import com.rcibanque.rcidirect.services.agreementgroups.validation.AgreementGroupValidation;
import com.rcibanque.rcidirect.services.agreementgroups.validation.fields.AgreementGroupFields;
import com.rcibanque.rcidirect.services.core.domain.IContext;
import com.rcibanque.rcidirect.services.core.utils.ConvertUtils;
import com.rcibanque.rcidirect.services.core.validation.ValidationBuilder;
import com.rcibanque.rcidirect.services.core.validation.config.IValidationConfig;
import com.rcibanque.rcidirect.services.core.validation.key.IProcessStage;

@Component("AgreementGroupValidator")
public class AgreementGroupValidator extends AbstractAgreementGroupValidator<AgreementGroup> {

	private IValidationConfig _validationConfig = newValidationConfig("AgreementGroupValidationConfig");


	@Override
	public void validate(IContext pContext, AgreementGroup pGroup, IProcessStage pProcessStage) {

		ValidationBuilder v = new ValidationBuilder().withConfig(_validationConfig).inProcessStage(pProcessStage);
		v.withReference(pGroup).withReferenceKey(ConvertUtils.toString(pGroup.getGroupId()));
		v.addToValidation(AgreementGroupValidation.ERROR_AGREEMENT_GROUP);

		applyValidationRules(pContext, pGroup.getCustomerActorCode(), 		v.forField(AgreementGroupFields.CUSTOMER_ACTOR_CODE).build());
		applyValidationRules(pContext, pGroup.getFinancialCompanyCode(), 	v.forField(AgreementGroupFields.FINANCIAL_COMPANY_CODE).build());
		applyValidationRules(pContext, pGroup.getMasterAgreement(), 		v.forField(AgreementGroupFields.MASTER_AGREEMENT).build());
		applyValidationRules(pContext, pGroup.getGroupName(), 				v.forField(AgreementGroupFields.GROUP_NAME).build());
		applyValidationRules(pContext, pGroup.getInvoicesGrouped(), 		v.forField(AgreementGroupFields.GROUPED_INVOICES).build());
		applyValidationRules(pContext, pGroup.getDirectDebitsGrouped(), 	v.forField(AgreementGroupFields.GROUPED_DIRECT_DEBITS).build());
	}

}
