package com.rcibanque.rcidirect.services.core.validation.utils;

import java.io.IOException;
import java.net.URL;
import java.time.Period;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rcibanque.rcidirect.services.core.domain.ValidityPeriod;
import com.rcibanque.rcidirect.services.core.utils.IteratorUtils;
import com.rcibanque.rcidirect.services.core.utils.RestUtils;
import com.rcibanque.rcidirect.services.core.validation.config.rule.IValidationRule;
import com.rcibanque.rcidirect.services.core.validation.config.rule.ValidationRule;
import com.rcibanque.rcidirect.services.core.validation.config.rule.ValidationRuleRef;
import com.rcibanque.rcidirect.services.core.validation.config.rule.ValidationRules;
import com.rcibanque.rcidirect.services.core.validation.detail.IValidationFormat;
import com.rcibanque.rcidirect.services.core.validation.detail.ReferencesType;
import com.rcibanque.rcidirect.services.core.validation.detail.ValidationDetails;
import com.rcibanque.rcidirect.services.core.validation.key.IProcessStage;
import com.rcibanque.rcidirect.services.core.validation.key.IValidationField;
import com.rcibanque.rcidirect.services.core.validation.key.IValidationType;
import com.rcibanque.rcidirect.services.core.validation.key.ValidationKey;
import com.rcibanque.rcidirect.services.core.validation.validator.AbstractValidator;

public final class ValidationRuleUtils {

	private static final Logger LOGGER = LoggerFactory.getLogger(ValidationRuleUtils.class);


	private ValidationRuleUtils() {}


	/**
	 * Load validation rules (from JSON files):
	 * <ul>
	 * <li><u>market-specific rules</u>: /validation/&lt;market&gt;/&lt;configuration&gt;.json</li>
	 * <li><u>default rules</u>: /validation/_default/&lt;configuration&gt;.json</li>
	 * </ul>
	 *
	 * For a specific rule (field/type/stage):
	 * <ul>
	 * <li>When both market-specific and default rules exist, the market-specific rule overrides the default one</li>
	 * <li>When the market-specific rule turns off the default rule (present in the same file), no rule is returned</li>
	 * </ul>
	 *
	 * @param pConfigurationName Name of validation configuration rules
	 * @param pMarket Market name
	 * @return Validation rules
	 */
	public static final List<IValidationRule> loadRules(String pConfigurationName, String pMarket) {

		List<IValidationRule> res = new ArrayList<>();

		List<IValidationRule> marketSpecificRules = loadRulesFile(pConfigurationName, pMarket);
		List<IValidationRule> defaultRules = loadRulesFile(pConfigurationName, null);

		for(IValidationRule defaultRule : IteratorUtils.nullableIterable(defaultRules)) {

			boolean marketSpecificRuleExists = ruleExists(marketSpecificRules, defaultRule);
			if(! marketSpecificRuleExists) {
				res.add(defaultRule);
			}
		}

		for(IValidationRule marketSpecificRule : IteratorUtils.nullableIterable(marketSpecificRules)) {

			boolean defaultRuleExists = ruleExists(defaultRules, marketSpecificRule);
			if(! defaultRuleExists || ! AbstractValidator.REQUIRED_CANCELLED_EXPECTATION.equals(marketSpecificRule.getDetails().getExpectation())) {
				res.add(marketSpecificRule);
			}
		}

		return res;
	}

	private static boolean ruleExists(List<IValidationRule> pRules, IValidationRule pRule) {
		boolean res = false;

		for(IValidationRule rule : IteratorUtils.nullableIterable(pRules)) {

			if(rule.getKey().equals(pRule.getKey())) {

				res = true;
				break;
			}
		}

		return res;
	}


	private static final List<IValidationRule> loadRulesFile(String pConfigurationName, String pMarket) {

		String marketPath = StringUtils.isNotBlank(pMarket) ? StringUtils.lowerCase(StringUtils.trimToNull(pMarket)) : "_default";

		String relativePath = "/validation/" + marketPath + "/" + pConfigurationName + ".json";

		URL url = ValidationRuleUtils.class.getResource(relativePath);

		try {
			return loadRulesFile(url);
		}
		catch(IllegalArgumentException e) {
			throw new IllegalStateException("Validation configuration rules: failed to interpret file " + relativePath, e);
		}
	}

	private static final List<IValidationRule> loadRulesFile(URL pURL) {

		List<IValidationRule> res = new ArrayList<>();

		ValidationRules rules = loadValidationRules(pURL);
		if(rules != null) {

			for(ValidationRule rule : IteratorUtils.nullableIterable(rules.getRules())) {

				ValidationKey key = rule.getKey();
				ValidationDetails details = rule.getDetails();

				// Load values (reference -> value)
				if(StringUtils.isNotEmpty(rule.getType())) {
					key.setType(loadValue(IValidationType.class, rules.getTypeEnum(), rule.getType()));
					rule.setType(null);
				}
				if(StringUtils.isNotEmpty(rule.getField())) {
					key.setField(loadValue(IValidationField.class, rules.getFieldEnum(), rule.getField()));
					rule.setField(null);
				}
				if(StringUtils.isNotEmpty(rule.getStage())) {
					key.setStage(loadValue(IProcessStage.class, rules.getStageEnum(), rule.getStage()));
					rule.setStage(null);
				}
				if(StringUtils.isNotEmpty(rule.getFormat())) {
					details.setFormat(loadValue(IValidationFormat.class, rules.getFormatEnum(), rule.getFormat()));
					rule.setFormat(null);
				}

				// Copy values
				details.setMessageKey(rule.getMessageKey());
				rule.setMessageKey(null);

				details.setExpectation(StringUtils.trim(rule.getExpectation()));
				rule.setExpectation(null);

				details.setRefs(rule.getRefs());
				rule.setRefs(null);

				details.setRefsType(loadReferencesTypeValue(rule.getRefsType()));
				rule.setRefsType(null);

				// Load values (reference -> value)
				for(ValidationRuleRef ruleRef : IteratorUtils.nullableIterable(details.getRefs())) {

					if(StringUtils.isNotEmpty(ruleRef.getRefField())) {
						ruleRef.setField(loadValue(IValidationField.class, rules.getFieldEnum(), ruleRef.getRefField()));
						ruleRef.setRefField(null);
					}
				}

				res.add(rule);
			}
		}

		return res;
	}

	private static final ValidationRules loadValidationRules(URL pURL) {
		ValidationRules res = null;

		if(pURL != null) {
			try {
				LOGGER.debug("Loading validation configuration: {}", pURL);

				res = RestUtils.getObjectMapper().readValue(pURL, ValidationRules.class);
			}
			catch (IOException e) {
				throw new IllegalStateException("Validation configuration rules: failed to load file " + pURL, e);
			}
		}

		return res;
	}

	private static final <T> T loadValue(Class<T> pClass, String pEnum, String pReference) {

		T res = null;

		String enumName;
		String fieldName;

		// Absolute path (package + class + field) ?
		if(pReference.contains(".")) {
			int index = StringUtils.lastIndexOf(pReference, ".");
			enumName = StringUtils.substring(pReference, 0, index);
			fieldName = StringUtils.substring(pReference, index+1, StringUtils.length(pReference));
		}
		else {
			enumName = pEnum;
			fieldName = pReference;
		}

		try {

			// Load enumeration
			@SuppressWarnings("unchecked") // Class implements interface
			Class<T> classDef = (Class<T>) pClass.getClassLoader().loadClass(StringUtils.trim(enumName));

			// Load enumeration value
			for(T enumConstant : IteratorUtils.nullableIterable(classDef.getEnumConstants())) {
				if(enumConstant.toString().equalsIgnoreCase(StringUtils.trim(fieldName))) {
					res = enumConstant;
					break;
				}
			}
		}
		catch (ClassNotFoundException | ClassCastException e) {
			throw new IllegalArgumentException("Validation configuration rules: failed to load class '" + enumName + "'", e);
		}

		if(res == null) {
			throw new IllegalArgumentException("Validation configuration rules: failed to load value '" + enumName + "." + fieldName + "'");
		}

		return res;
	}


	private static final ReferencesType loadReferencesTypeValue(String pReferencesType) {

		ReferencesType res = ReferencesType.AND; // Default

		try {

			if(StringUtils.isNotBlank(pReferencesType)) {
				res = ReferencesType.valueOf(pReferencesType);
			}
		}
		catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("Validation configuration rules: undefined reference type '" + pReferencesType + "'", e);
		}

		return res;
	}


	/**
	 * @param pExpectation String containing two optional {@link Period} of time separated with a ',' character.
	 * An undefined period should be provided as NULL
	 * <ul>
	 * <li>P-15D,P1M: between 15 days before the current date to 1 month after</li>
	 * <li>P1D,NULL: at least 1 day after the current date</li>
	 * <li>NULL,NULL: all dates are valid</li>
	 * </ul>
	 *
	 * @return {@link ValidityPeriod}
	 */
	public static final ValidityPeriod getValidityPeriod(String pExpectation) {

		String[] periods = StringUtils.splitPreserveAllTokens(pExpectation, ',');
		if(periods.length != 2) {
			throw new IllegalArgumentException("Validation configuration rules: invalid validity period (expecting \"<Period>;<Period>\"): " + pExpectation);
		}
		try {
			return new ValidityPeriod(parsePeriod(periods[0]), parsePeriod(periods[1]));
		}
		catch(DateTimeParseException e) {
			throw new IllegalArgumentException("Validation configuration rules: invalid validity period (wrong period format): " + pExpectation, e);
		}
	}

	private static Period parsePeriod(String pPeriod) {
		String period = StringUtils.upperCase(StringUtils.trimToNull(pPeriod));
		return "NULL".equals(period) ? null : Period.parse(period);
	}


	/**
	 * @param pRegExp Regular expression
	 * @return Compiled regular expression pattern
	 */
	public static final Pattern compileRegularExpression(String pRegExp) {
		Pattern res = null;
		try {
			res = Pattern.compile(pRegExp);
		}
		catch(PatternSyntaxException e) {
			throw new IllegalArgumentException("Validation configuration rules: invalid format regular expression: " + pRegExp, e);
		}
		return res;
	}

}
