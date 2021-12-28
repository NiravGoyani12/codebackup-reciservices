package com.rcibanque.rcidirect.services.core.validation.config;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.regex.Pattern;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

import com.rcibanque.rcidirect.services.core.utils.ConvertUtils;
import com.rcibanque.rcidirect.services.core.utils.CoreUtils;
import com.rcibanque.rcidirect.services.core.utils.IteratorUtils;
import com.rcibanque.rcidirect.services.core.utils.MessageBundle;
import com.rcibanque.rcidirect.services.core.utils.TextUtils;
import com.rcibanque.rcidirect.services.core.validation.config.rule.IValidationRule;
import com.rcibanque.rcidirect.services.core.validation.config.rule.ValidationRuleRef;
import com.rcibanque.rcidirect.services.core.validation.detail.IValidationFormat;
import com.rcibanque.rcidirect.services.core.validation.detail.ValidationDetails;
import com.rcibanque.rcidirect.services.core.validation.key.IProcessStage;
import com.rcibanque.rcidirect.services.core.validation.key.IValidationField;
import com.rcibanque.rcidirect.services.core.validation.key.IValidationType;
import com.rcibanque.rcidirect.services.core.validation.key.ValidationKey;
import com.rcibanque.rcidirect.services.core.validation.utils.ValidationRuleUtils;
import com.rcibanque.rcidirect.services.core.validation.validator.AbstractValidator;

public abstract class AbstractValidationConfig implements IValidationConfig {


	private static final Comparator<IValidationRule> RULE_COMPARATOR = (IValidationRule pVR1, IValidationRule pVR2) -> {

		// Order by field name
		int res = pVR1.getKey().getField().getFieldName().compareTo(pVR2.getKey().getField().getFieldName());
		if(res == 0) {
			// Order by type
			res = pVR1.getKey().getType().getCode().compareTo(pVR2.getKey().getType().getCode());
		}
		return res;
	};


	private String _configurationName;

	private final List<IValidationRule> _validationRules;


	protected AbstractValidationConfig() {
		super();
		_validationRules = new ArrayList<>();
	}


	protected abstract IValidationConfigHelper getHelper();


	/**
	 * Load <i>abstract</i> (<b>required</b> and <b>format</b>) validation rules
	 *
	 * @param pConfigurationName Name of validation configuration
	 * @param pMarket Market name
	 * @param pMessageBundle Message bundle used for validation messages
	 * @return Rules
	 */
	protected final List<IValidationRule> loadAbstractRules(String pConfigurationName, String pMarket, MessageBundle pMessageBundle) {

		_configurationName = pConfigurationName;

		List<IValidationRule> rules = ValidationRuleUtils.loadRules(pConfigurationName, pMarket);

		for(IValidationRule rule : IteratorUtils.nullableIterable(rules)) {

			ValidationKey key = rule.getKey();
			ValidationDetails details = rule.getDetails();

			if(CoreUtils.isIn(key.getType().getCode(),
					IValidationType.REQUIRED.getCode(),
					IValidationType.REQUIRED_IF_REF.getCode(),
					IValidationType.DATE_VS_REF.getCode(),
					IValidationType.DATE_VS_TODAY.getCode())) {

				if(key.getStage() == null) {
					key.setStage(IProcessStage.ALL);
				}

				addRule(pConfigurationName, pMessageBundle, rule);
			}
			else if(IValidationType.DATE_VS_RANGE.getCode().equals(key.getType().getCode())) {

				details.setValidityPeriod(ValidationRuleUtils.getValidityPeriod(details.getExpectation()));

				details.setExpectation(null);

				if(key.getStage() == null) {
					key.setStage(IProcessStage.ALL);
				}

				addRule(pConfigurationName, pMessageBundle, rule);
			}
			else if(IValidationType.FORMAT.getCode().equals(key.getType().getCode())) {

				// Create validation format ?
				if(details.getFormat() == null
						&& StringUtils.isNotEmpty(details.getExpectation())) {

					Pattern regExp = ValidationRuleUtils.compileRegularExpression(details.getExpectation());

					details.setFormat(new IValidationFormat() {

						@Override
						public Pattern getRegExp() {
							return regExp;
						}

						@Override
						public String toString() {
							return regExp.toString();
						}
					});

					details.setExpectation(null);
				}

				key.setStage(IProcessStage.ALL);

				addRule(pConfigurationName, pMessageBundle, rule);
			}
			else if(IValidationType.CUSTOM.getCode().equals(key.getType().getCode())) {

				if(key.getStage() == null) {
					key.setStage(IProcessStage.ALL);
				}

				addRule(pConfigurationName, pMessageBundle, rule);
			}
		}

		return rules;
	}


	/**
	 * Add the rule to the list of rules, and checks it is configured properly
	 *
	 * @param pConfigurationName Name of validation configuration
	 * @param pMessageBundle Message bundle used for validation messages
	 * @param pRule The validation rule
	 */
	protected final void addRule(String pConfigurationName, MessageBundle pMessageBundle, IValidationRule pRule) {

		String prefix = "[" + pConfigurationName + "] ";

		IValidationField field = pRule.getKey().getField();
		Assert.notNull(field, prefix + "Validation field is null");
		Assert.notNull(field.getDomainName(), prefix + "Validation field domain is null for: " + field);
		Assert.notNull(field.getFieldName(), prefix + "Validation field name is null for: " + field);

		IProcessStage stage = pRule.getKey().getStage();
		Assert.notNull(stage, prefix + "Process stage is null for: " + stage);
		Assert.notNull(stage.getCode(), prefix + "Process stage value is null for: " + stage);

		IValidationType type = pRule.getKey().getType();
		Assert.notNull(type, prefix + "Validation type is null");
		Assert.notNull(type.getCode(), prefix + "Validation type value is null for: " + type);

		Assert.notNull(pMessageBundle, prefix + "Message bundle is null");
		pRule.getDetails().setMessageBundle(pMessageBundle);

		if(! AbstractValidator.REQUIRED_CANCELLED_EXPECTATION.equalsIgnoreCase(StringUtils.trimToNull(pRule.getDetails().getExpectation()))) {

			String messageKey = pRule.getDetails().getMessageKey();
			Assert.notNull(messageKey, prefix + "Message key is null for: " + pRule.getKey());
			try {
				pMessageBundle.getMessage(Locale.ENGLISH, messageKey); // Check that the message is at least in the default bundle
			}
			catch(MissingResourceException e) {
				Assert.state(false, prefix + "Message key '" + messageKey + "' does not exist");
			}
		}

		if(CoreUtils.isIn(type.getCode(),
				IValidationType.REQUIRED_IF_REF.getCode(),
				IValidationType.DATE_VS_REF.getCode())) {

			if(! AbstractValidator.REQUIRED_CANCELLED_EXPECTATION.equalsIgnoreCase(pRule.getDetails().getExpectation())) {
				Assert.isTrue(CollectionUtils.isNotEmpty(pRule.getDetails().getRefs()), prefix + "Ref is undefined for: " + pRule.getKey());
			}

			for(ValidationRuleRef ruleRef : pRule.getDetails().getRefs()) {

				Assert.isTrue(StringUtils.isNotBlank(ruleRef.getRef()), prefix + "Ref is undefined for: " + pRule.getKey());
				Assert.isTrue(StringUtils.isNotBlank(ruleRef.getExpectation()), prefix + "Ref expectation is undefined for: " + pRule.getKey());

				if(type.getCode().equals(IValidationType.REQUIRED_IF_REF.getCode())) {

					Assert.isTrue(CoreUtils.isIn(ruleRef.getExpectation(),
							AbstractValidator.REF_EXPECTATION_POSITIVE,
							AbstractValidator.REF_EXPECTATION_LOWER,
							AbstractValidator.REF_EXPECTATION_LOWER_OR_EQUAL,
							AbstractValidator.REF_EXPECTATION_GREATER,
							AbstractValidator.REF_EXPECTATION_GREATER_OR_EQUAL,
							AbstractValidator.REF_EXPECTATION_DEFINED,
							AbstractValidator.REF_EXPECTATION_UNDEFINED,
							AbstractValidator.REF_EXPECTATION_EQUALS,
							AbstractValidator.REF_EXPECTATION_DIFFERENT,
							AbstractValidator.REF_EXPECTATION_CONTAINS_ANY,
							AbstractValidator.REF_EXPECTATION_CONTAINS_NONE), prefix + "Ref expectation is incorrect for: " + pRule.getKey());

					if(CoreUtils.isIn(ruleRef.getExpectation(),
							AbstractValidator.REF_EXPECTATION_LOWER,
							AbstractValidator.REF_EXPECTATION_LOWER_OR_EQUAL,
							AbstractValidator.REF_EXPECTATION_GREATER,
							AbstractValidator.REF_EXPECTATION_GREATER_OR_EQUAL,
							AbstractValidator.REF_EXPECTATION_EQUALS,
							AbstractValidator.REF_EXPECTATION_DIFFERENT,
							AbstractValidator.REF_EXPECTATION_CONTAINS_ANY,
							AbstractValidator.REF_EXPECTATION_CONTAINS_NONE)) {

						Assert.isTrue(CollectionUtils.isNotEmpty(ruleRef.getValues()), prefix + "No ref values defined for: " + pRule.getKey());
					}

					if(CoreUtils.isIn(ruleRef.getExpectation(),
							AbstractValidator.REF_EXPECTATION_LOWER,
							AbstractValidator.REF_EXPECTATION_LOWER_OR_EQUAL,
							AbstractValidator.REF_EXPECTATION_GREATER,
							AbstractValidator.REF_EXPECTATION_GREATER_OR_EQUAL)) {

						ruleRef.setNumericValues(new ArrayList<>());

						for(String value : ruleRef.getValues()) {

							BigDecimal numericValue = ConvertUtils.parseBigDecimal(value);
							Assert.notNull(numericValue, prefix + "Invalid numeric ref value for" + pRule.getKey());

							ruleRef.getNumericValues().add(numericValue);
						}
					}
				}

				if(type.getCode().equals(IValidationType.DATE_VS_REF.getCode())) {

					Assert.isTrue(CoreUtils.isIn(ruleRef.getExpectation(),
							AbstractValidator.DATE_EXPECTATION_BEFORE,
							AbstractValidator.DATE_EXPECTATION_BEFORE_OR_EQUAL,
							AbstractValidator.DATE_EXPECTATION_AFTER,
							AbstractValidator.DATE_EXPECTATION_AFTER_OR_EQUAL), prefix + "Expectation is incorrect for: " + pRule.getKey());
				}
			}
		}

		if(CoreUtils.isIn(type.getCode(),
				IValidationType.DATE_VS_TODAY.getCode())) {

			Assert.isTrue(StringUtils.isNotBlank(pRule.getDetails().getExpectation()), prefix + "Expectation is undefined for: " + pRule.getKey());

			Assert.isTrue(CoreUtils.isIn(pRule.getDetails().getExpectation(),
					AbstractValidator.DATE_EXPECTATION_BEFORE,
					AbstractValidator.DATE_EXPECTATION_BEFORE_OR_EQUAL,
					AbstractValidator.DATE_EXPECTATION_AFTER,
					AbstractValidator.DATE_EXPECTATION_AFTER_OR_EQUAL), prefix + "Expectation is incorrect for: " + pRule.getKey());
		}

		if(CoreUtils.isIn(type.getCode(),
				IValidationType.FORMAT.getCode())) {

			Assert.notNull(pRule.getDetails().getFormat(), prefix + "Format is null for: " + pRule.getKey());
		}

		if(CoreUtils.isIn(type.getCode(),
				IValidationType.CUSTOM.getCode())) {

			Assert.notNull(pRule.getDetails().getExpectation(), prefix + "Custom validator is null for: " + pRule.getKey());
		}

		_validationRules.add(pRule);
	}


	@Override
	public final ValidationDetails getValidationDetails(ValidationKey pKey) {

		ValidationDetails res = null;

		for(IValidationRule rule : _validationRules) {

			if(rule.getKey().equals(pKey)) {

				res = rule.getDetails();
				break;
			}
		}

		return res;
	}


	@Override
	public final String toString() {

		StringBuilder sb = new StringBuilder();

		sb.append(StringUtils.LF).append(StringUtils.LF).append(_configurationName).append(StringUtils.LF).append(StringUtils.LF);

		Collections.sort(_validationRules, RULE_COMPARATOR);
		for(IValidationRule rule : _validationRules) {

			ValidationKey key = rule.getKey();
			ValidationDetails details = rule.getDetails();

			sb.append(StringUtils.SPACE).append(StringUtils.SPACE).append(StringUtils.SPACE);

			// Field
			sb.append(key.getField()).append(StringUtils.SPACE);

			// Validation type & stage
			sb.append("[").append(getHelper().getType(key.getType().getCode())).append(StringUtils.SPACE);
			sb.append(getHelper().getStage(key.getStage().getCode())).append("]").append(StringUtils.SPACE);

			// Format
			if(key.getType().getCode().equals(IValidationType.FORMAT.getCode())) {
				sb.append(details.getFormat()).append(StringUtils.SPACE);
			}

			// Reference
			Iterator<ValidationRuleRef> ruleRefIter = details.getRefs().iterator();
			if(ruleRefIter.hasNext()) {
				sb.append("(").append(details.getRefsType()).append(")").append(StringUtils.SPACE);
			}
			while(ruleRefIter.hasNext()) {
				ValidationRuleRef ruleRef = ruleRefIter.next();

				// Ref service + Ref
				if(StringUtils.isNotBlank(ruleRef.getRefService())) {
					sb.append(ruleRef.getRefService()).append("#");
				}
				if(StringUtils.isNotBlank(ruleRef.getRef())) {
					sb.append(ruleRef.getRef());
				}
				sb.append(StringUtils.SPACE);

				// Expectation
				if(ruleRef.getExpectation() != null) {
					sb.append(ruleRef.getExpectation()).append(StringUtils.SPACE);
				}
				sb.append(StringUtils.SPACE);

				// Values
				if(CollectionUtils.isNotEmpty(ruleRef.getValues())) {
					sb.append("(").append(TextUtils.withSeparator(ruleRef.getValues(), StringUtils.SPACE)).append(")").append(StringUtils.SPACE);
				}

				if(ruleRefIter.hasNext()) {
					sb.append(" | ");
				}
			}

			// Expectation
			if(details.getExpectation() != null) {
				sb.append(details.getExpectation()).append(StringUtils.SPACE);
			}

			// Validity period
			if(details.getValidityPeriod() != null) {
				if(details.getValidityPeriod().getFrom() != null) {
					sb.append("min: ").append(details.getValidityPeriod().getFrom()).append(StringUtils.SPACE);
				}
				if(details.getValidityPeriod().getTo() != null) {
					sb.append("max: ").append(details.getValidityPeriod().getTo()).append(StringUtils.SPACE);
				}
			}

			sb.append(StringUtils.LF);
		}

		return sb.toString();
	}

}