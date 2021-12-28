package com.rcibanque.rcidirect.services.core.validation.key;

public interface IValidationType {

	/**
	 * <i>Required</i> validation type
	 */
	static final IValidationType REQUIRED = () -> Integer.valueOf(10);

	/**
	 * <i>Required "if other fields (refs) defined/undefined, equals/different, contains/does not contain"</i> validation type
	 */
	static final IValidationType REQUIRED_IF_REF = () -> Integer.valueOf(11);

	/**
	 * <i>Format</i> validation type
	 */
	static final IValidationType FORMAT = () -> Integer.valueOf(30);

	/**
	 * <i>Custom</i> validation type
	 */
	static final IValidationType CUSTOM = () -> Integer.valueOf(40);

	/**
	 * <i>Date compared to other fields (refs)</i> validation type
	 */
	static final IValidationType DATE_VS_REF = () -> Integer.valueOf(50);

	/**
	 * <i>Date compared to today</i> validation type
	 */
	static final IValidationType DATE_VS_TODAY = () -> Integer.valueOf(51);

	/**
	 * <i>Date compared to validity period</i> validation type
	 */
	static final IValidationType DATE_VS_RANGE = () -> Integer.valueOf(52);


	/**
	 * @return Validation type code
	 */
	Integer getCode();

}
