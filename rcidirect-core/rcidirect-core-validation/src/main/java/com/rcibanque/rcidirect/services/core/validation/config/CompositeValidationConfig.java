package com.rcibanque.rcidirect.services.core.validation.config;

import com.rcibanque.rcidirect.services.core.utils.IteratorUtils;
import com.rcibanque.rcidirect.services.core.validation.detail.ValidationDetails;
import com.rcibanque.rcidirect.services.core.validation.key.ValidationKey;

/**
 * Composite validation configuration holder: the <b>last</b> validation detail found is used
 */
public final class CompositeValidationConfig implements IValidationConfig {


	private final IValidationConfig[] _configs;


	public CompositeValidationConfig(IValidationConfig ...pConfigs) {
		_configs = pConfigs;
	}


	@Override
	public ValidationDetails getValidationDetails(ValidationKey pKey) {
		ValidationDetails res = null;
		for(IValidationConfig config : IteratorUtils.nullableIterable(_configs)) {
			ValidationDetails vd = config.getValidationDetails(pKey);
			if(vd != null) {
				res = vd;
				// Don't break: use the last one
			}
		}
		return res;
	}

}
