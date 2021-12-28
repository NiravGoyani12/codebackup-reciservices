package com.rcibanque.rcidirect.services.core.validation.config;

import com.rcibanque.rcidirect.services.core.validation.key.IProcessStage;
import com.rcibanque.rcidirect.services.core.validation.key.IValidationType;

public interface IValidationConfigHelper {


	IValidationType getType(Integer pCode);

	IProcessStage getStage(Integer pCode);

}
