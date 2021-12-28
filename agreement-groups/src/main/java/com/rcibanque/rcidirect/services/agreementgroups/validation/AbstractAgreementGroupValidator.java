package com.rcibanque.rcidirect.services.agreementgroups.validation;

import com.rcibanque.rcidirect.services.agreementgroups.util.AgreementGroupMessages;
import com.rcibanque.rcidirect.services.core.validation.config.IValidationConfig;
import com.rcibanque.rcidirect.services.general.validation.CommonValidator;
import com.rcibanque.rcidirect.services.general.validation.config.CommonValidationConfig;

public abstract class AbstractAgreementGroupValidator<T> extends CommonValidator<T> {


	/**
	 * @param pConfigurationName Name of validation configuration
	 * @return Validation configuration
	 * @see {@link CommonValidationConfig#newInstance(com.rcibanque.rcidirect.services.core.utils.MessageBundle, String)}
	 */
	protected static final IValidationConfig newValidationConfig(String pConfigurationName) {

		return CommonValidationConfig.newInstance(AgreementGroupMessages.getInstance(), pConfigurationName);
	}

}
