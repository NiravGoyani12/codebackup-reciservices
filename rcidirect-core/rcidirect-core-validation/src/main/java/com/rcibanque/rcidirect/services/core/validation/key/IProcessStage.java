package com.rcibanque.rcidirect.services.core.validation.key;

public interface IProcessStage {

	/**
	 * <i>Default</i> process stage</br>
	 * </br>
	 * If no validation configuration is found for a stage,
	 * and a configuration for the <i>default</i> stage exists, then it is applied.
	 */
	static final IProcessStage ALL = () -> Integer.valueOf(1);


	/**
	 * @return Process stage code
	 */
	Integer getCode();

}
