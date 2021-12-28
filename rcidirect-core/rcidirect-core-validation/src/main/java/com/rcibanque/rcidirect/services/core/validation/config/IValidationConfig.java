package com.rcibanque.rcidirect.services.core.validation.config;

import com.rcibanque.rcidirect.services.core.validation.detail.ValidationDetails;
import com.rcibanque.rcidirect.services.core.validation.key.ValidationKey;

public interface IValidationConfig {

	ValidationDetails getValidationDetails(ValidationKey pKey);

}
