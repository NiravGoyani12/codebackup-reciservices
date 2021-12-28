package com.rcibanque.rcidirect.services.core.validation.tests.config;

import com.rcibanque.rcidirect.services.core.utils.ConvertUtils;
import com.rcibanque.rcidirect.services.core.validation.config.AbstractValidationConfig;
import com.rcibanque.rcidirect.services.core.validation.config.IValidationConfigHelper;
import com.rcibanque.rcidirect.services.core.validation.key.IProcessStage;
import com.rcibanque.rcidirect.services.core.validation.key.IValidationType;
import com.rcibanque.rcidirect.services.core.validation.utils.CoreValidationTestMessages;

public class TestValidationConfig extends AbstractValidationConfig {


	public TestValidationConfig(String pConfigurationName) {

		loadAbstractRules(pConfigurationName, "market", CoreValidationTestMessages.getInstance());
	}


	@Override
	protected IValidationConfigHelper getHelper() {

		return new IValidationConfigHelper() {

			@Override
			public IValidationType getType(Integer pCode) {

				return new IValidationType() {

					@Override
					public Integer getCode() {
						return pCode;
					}

					@Override
					public String toString() {
						return ConvertUtils.toString(pCode);
					}
				};
			}

			@Override
			public IProcessStage getStage(Integer pCode) {

				return new IProcessStage() {

					@Override
					public Integer getCode() {
						return pCode;
					}

					@Override
					public String toString() {
						return ConvertUtils.toString(pCode);
					}
				};
			}
		};
	}

}
