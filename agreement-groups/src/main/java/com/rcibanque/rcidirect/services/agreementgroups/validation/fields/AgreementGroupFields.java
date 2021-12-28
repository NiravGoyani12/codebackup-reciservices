package com.rcibanque.rcidirect.services.agreementgroups.validation.fields;

import com.rcibanque.rcidirect.services.core.validation.key.IValidationField;
import com.rcibanque.rcidirect.services.general.validation.ValidationDomain;

public enum AgreementGroupFields implements IValidationField {

	CUSTOMER_ACTOR_CODE,
	FINANCIAL_COMPANY_CODE,
	MASTER_AGREEMENT,
	GROUP_NAME,
	GROUPED_INVOICES,
	GROUPED_DIRECT_DEBITS
	;

	@Override
	public String getDomainName() {
		return ValidationDomain.NONE.name();
	}

	@Override
	public String getFieldName() {
		return name();
	}

}
