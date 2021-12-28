package com.rcibanque.rcidirect.services.core.validation.validator;

import com.rcibanque.rcidirect.services.core.domain.IContext;
import com.rcibanque.rcidirect.services.core.validation.key.IProcessStage;

public interface IValidator<T> {

	/**
	 * Validate a tested object
	 *
	 * @param pContext Context
	 * @param pObject Tested object
	 * @param pProcessStage Process stage at which the validation takes place
	 */
	void validate(IContext pContext, T pObject, IProcessStage pProcessStage);

}
