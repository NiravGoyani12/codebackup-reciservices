package com.rcibanque.rcidirect.services.core.validation.config.rule;

import com.rcibanque.rcidirect.services.core.validation.detail.ValidationDetails;
import com.rcibanque.rcidirect.services.core.validation.key.ValidationKey;

public interface IValidationRule {


	ValidationKey getKey();

	ValidationDetails getDetails();

}