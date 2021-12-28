package com.rcibanque.rcidirect.services.core.validation.validator;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.rcibanque.rcidirect.services.core.domain.DateRange;
import com.rcibanque.rcidirect.services.core.domain.IContext;
import com.rcibanque.rcidirect.services.core.domain.Price;
import com.rcibanque.rcidirect.services.core.response.IMessage;
import com.rcibanque.rcidirect.services.core.utils.ConvertUtils;
import com.rcibanque.rcidirect.services.core.utils.DateUtils;
import com.rcibanque.rcidirect.services.core.utils.IteratorUtils;
import com.rcibanque.rcidirect.services.core.utils.MathUtils;
import com.rcibanque.rcidirect.services.core.utils.MessageBundle;
import com.rcibanque.rcidirect.services.core.validation.IValidation;
import com.rcibanque.rcidirect.services.core.validation.ValidationContext;
import com.rcibanque.rcidirect.services.core.validation.config.rule.ValidationRuleRef;
import com.rcibanque.rcidirect.services.core.validation.detail.ValidationDetails;
import com.rcibanque.rcidirect.services.core.validation.key.IProcessStage;
import com.rcibanque.rcidirect.services.core.validation.key.IValidationField;
import com.rcibanque.rcidirect.services.core.validation.key.IValidationType;

public abstract class AbstractValidator<T> implements IValidator<T> {


	public static final String NO_IDENTIFIER = "NONE";

	public static final String REQUIRED_CANCELLED_EXPECTATION = "false";

	private static final String FIELD_SEPARATOR = ".";

	private static final String FIELD_GETTER_PREFIX = "get";


	public static final String REF_EXPECTATION_EQUALS = "EQUALS";

	public static final String REF_EXPECTATION_DIFFERENT = "DIFFERENT";

	public static final String REF_EXPECTATION_DEFINED = "DEFINED";

	public static final String REF_EXPECTATION_UNDEFINED = "UNDEFINED";

	public static final String REF_EXPECTATION_CONTAINS_ANY = "CONTAINS_ANY";

	public static final String REF_EXPECTATION_CONTAINS_NONE = "CONTAINS_NONE";


	public static final String REF_EXPECTATION_POSITIVE = "POSITIVE";

	public static final String REF_EXPECTATION_LOWER = "LOWER";

	public static final String REF_EXPECTATION_LOWER_OR_EQUAL = "LOWER_OR_EQUAL";

	public static final String REF_EXPECTATION_GREATER = "GREATER";

	public static final String REF_EXPECTATION_GREATER_OR_EQUAL = "GREATER_OR_EQUAL";


	public static final String DATE_EXPECTATION_BEFORE = "BEFORE";

	public static final String DATE_EXPECTATION_BEFORE_OR_EQUAL = "BEFORE_OR_EQUAL";

	public static final String DATE_EXPECTATION_AFTER = "AFTER";

	public static final String DATE_EXPECTATION_AFTER_OR_EQUAL = "AFTER_OR_EQUAL";


	@Autowired
	protected BeanFactory _beanFactory;

	private final Map<String, Object> _beans = new HashMap<>();


	private Object getBean(String pBeanName) {
		// Although Spring makes use of caching for beans, there is a fair amount of code that is called, some of which is synchronized
		if(! _beans.containsKey(pBeanName)) {
			Object bean = _beanFactory.getBean(pBeanName);
			if(bean == null) {
				throw new IllegalStateException("Undefined bean: " + pBeanName);
			}
			_beans.put(pBeanName, bean);
		}

		return _beans.get(pBeanName);
	}


	protected Date getCurrentDate() {
		// Default, can be overridden
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(System.currentTimeMillis());
		return c.getTime();
	}


	/**
	 * Add a detailed validation message to the context
	 *
	 * @param pContext Context
	 * @param pValidationContext Validation context
	 * @param pBundle Detail message bundle
	 * @param pKey Detail message key
	 * @param pParams Detail message parameters
	 */
	protected static final void addValidation(IContext pContext, ValidationContext pValidationContext,
			MessageBundle pBundle, String pKey, Object... pParams) {

		IValidation validation = pValidationContext.getValidation();
		IValidationField field = pValidationContext.getValidationField();

		IMessage validationMessage = pContext.getMessages().message(validation.getStatus(),
				validation.getMessageBundle(), validation.getMessageKey(), StringUtils.trimToEmpty(pValidationContext.getValidationMessageParameter()));

		if(! pValidationContext.getNoDetails()) {

			validationMessage.addDetail(pContext.getLocale(),
					field.getDomainName(), field.getFieldName(), pValidationContext.getReferenceKey(),
					pBundle, pKey, pParams);
		}
	}


	/**
	 * @param pContext Context
	 * @param pValidationContext Validation context
	 * @return True if the field is required (REQUIRED or REQUIRED_IF_REF (with references matching))
	 */
	protected boolean isRequired(IContext pContext, ValidationContext pValidationContext) {

		pValidationContext.setNoDetails(true);

		boolean res = isRequired(pContext, pValidationContext, IValidationType.REQUIRED)
				|| isRequired(pContext, pValidationContext, IValidationType.REQUIRED_IF_REF) ;

		pValidationContext.setNoDetails(false); // Reset

		return res;
	}

	private boolean isRequired(IContext pContext, ValidationContext pValidationContext, IValidationType pValidationType) {

		boolean res;

		// Update context
		pValidationContext.setValidationType(pValidationType);

		ValidationDetails detail = getValidationDetails(pValidationContext);
		res = detail != null
				&& doRefsMatch(pContext, pValidationContext, detail)
				&& ! REQUIRED_CANCELLED_EXPECTATION.equalsIgnoreCase(detail.getExpectation()); // Market-specific overriding

		// Reset context
		pValidationContext.setValidationType(null);

		return res;
	}


	/**
	 * Apply <i>abstract</i> (<b>required</b>, <b>format</b>, <b>custom</b> and <b>date</b>) validations
	 *
	 * @param pContext Context
	 * @param pObject Validated object
	 * @param pValidationContext Validation context
	 * @return True if there is a validation error
	 */
	protected final boolean applyAbstractValidationRules(IContext pContext, Object pObject, ValidationContext pValidationContext) {

		boolean res = false;

		// REQUIRED
		res = res | validateRequired(pContext, pValidationContext, pObject);

		res = res | validateRequiredIfRef(pContext, pValidationContext, pObject);

		//  FORMAT
		res = res | validateFormat(pContext, pValidationContext, pObject);

		// CUSTOM
		res = res | validateCustom(pContext, pValidationContext, pObject);

		// DATE
		res = res | validateDateVSRef(pContext, pValidationContext, pObject);

		res = res | validateDateVSToday(pContext, pValidationContext, pObject);

		res = res | validateDateVSRange(pContext, pValidationContext, pObject);

		pValidationContext.setNoDetails(false); // Reset

		return res;
	}


	private boolean validateRequired(IContext pContext, ValidationContext pValidationContext, Object pObject) {

		boolean res = false;

		// Update context
		pValidationContext.setValidationType(IValidationType.REQUIRED);

		ValidationDetails detail = getValidationDetails(pValidationContext);
		if(detail != null
				&& ! REQUIRED_CANCELLED_EXPECTATION.equalsIgnoreCase(detail.getExpectation())) { // Market-specific overriding

			if(! isFieldDefined(pContext, pValidationContext, detail, pObject)) {

				addValidation(pContext, pValidationContext, detail.getMessageBundle(), detail.getMessageKey(), pObject);
				res = true;
			}
		}

		// Reset context
		pValidationContext.setValidationType(null);

		return res;
	}


	private boolean validateRequiredIfRef(IContext pContext, ValidationContext pValidationContext, Object pObject) {

		boolean res = false;

		// Update context
		pValidationContext.setValidationType(IValidationType.REQUIRED_IF_REF);

		ValidationDetails detail = getValidationDetails(pValidationContext);
		if(detail != null
				&& doRefsMatch(pContext, pValidationContext, detail)
				&& ! REQUIRED_CANCELLED_EXPECTATION.equalsIgnoreCase(detail.getExpectation())) { // Market-specific overriding

			if(! isFieldDefined(pContext, pValidationContext, detail, pObject)) {
				addValidation(pContext, pValidationContext, detail.getMessageBundle(), detail.getMessageKey(), pObject);
				res = true;
			}
		}

		// Reset context
		pValidationContext.setValidationType(null);

		return res;
	}

	private boolean doRefsMatch(IContext pContext, ValidationContext pValidationContext, ValidationDetails pDetail) {

		switch(pDetail.getRefsType()) {

		case OR:
			return pDetail.getRefs().stream().anyMatch(ref -> doesRefMatch(pContext, pValidationContext, pDetail, ref));
		case AND:
			return pDetail.getRefs().stream().allMatch(ref -> doesRefMatch(pContext, pValidationContext, pDetail, ref));
		default:
			throw new IllegalArgumentException("Unsupported references type");
		}
	}

	private boolean doesRefMatch(IContext pContext, ValidationContext pValidationContext, ValidationDetails pDetail, ValidationRuleRef pRef) {

		Boolean numeric = null;
		Boolean defined = null;
		Boolean equals = null;
		Boolean containsAny = null;

		switch(pRef.getExpectation()) {
		case REF_EXPECTATION_POSITIVE:
		case REF_EXPECTATION_LOWER:
		case REF_EXPECTATION_LOWER_OR_EQUAL:
		case REF_EXPECTATION_GREATER:
		case REF_EXPECTATION_GREATER_OR_EQUAL:
			numeric = Boolean.TRUE;
			break;
		case REF_EXPECTATION_DEFINED:
			defined = Boolean.TRUE;
			break;
		case REF_EXPECTATION_UNDEFINED:
			defined = Boolean.FALSE;
			break;
		case REF_EXPECTATION_EQUALS:
			equals = Boolean.TRUE;
			break;
		case REF_EXPECTATION_DIFFERENT:
			equals = Boolean.FALSE;
			break;
		case REF_EXPECTATION_CONTAINS_ANY:
			containsAny = Boolean.TRUE;
			break;
		case REF_EXPECTATION_CONTAINS_NONE:
			containsAny = Boolean.FALSE;
			break;
		default:
			// Should not happen, controlled at load
			throw new IllegalArgumentException("Unsupported ref expectation");
		}

		Object ref = getRef(pContext, pValidationContext, pRef);

		boolean refPositive = numeric == null
				|| (numeric && isFieldDefined(pContext, pValidationContext, pDetail, ref, pRef)
						&& doesNumericFieldMatchExpectation(pRef.getExpectation(), ref, pRef.getNumericValues()));

		boolean refDefined = defined == null
				|| ((defined && isFieldDefined(pContext, pValidationContext, pDetail, ref, pRef))
						|| (! defined && ! isFieldDefined(pContext, pValidationContext, pDetail, ref, pRef)));

		boolean refEquals = equals == null
				|| ((equals && isFieldEquals(ref, pRef.getValues()))
						|| (! equals && ! isFieldEquals(ref, pRef.getValues())));

		boolean refContainsAny = containsAny == null
				|| ((containsAny && doesFieldContainAny(ref, pRef.getValues()))
						|| (! containsAny && ! doesFieldContainAny(ref, pRef.getValues())));

		return refPositive && refDefined && refEquals && refContainsAny;
	}


	private boolean doesNumericFieldMatchExpectation(String pExpectation, Object pObject, List<BigDecimal> pValues) {
		boolean res = false;

		if(pObject instanceof BigDecimal) {
			res = doesNumericBigDecimalFieldMatchExpectation(pExpectation, (BigDecimal) pObject, pValues);
		}
		else if(pObject instanceof Price) {
			res = doesNumericBigDecimalFieldMatchExpectation(pExpectation, ((Price) pObject).getExclVat(), pValues);
		}

		return res;
	}

	private boolean doesNumericBigDecimalFieldMatchExpectation(String pExpectation, BigDecimal pValue, List<BigDecimal> pValues) {
		boolean res = false;

		BigDecimal expectedValue = CollectionUtils.isEmpty(pValues) ? null : pValues.get(0); // POSITIVE has no values

		switch(pExpectation) {
		case REF_EXPECTATION_POSITIVE:
			res = MathUtils.gt(pValue, BigDecimal.ZERO);
			break;
		case REF_EXPECTATION_LOWER:
			res = MathUtils.lt(pValue, expectedValue);
			break;
		case REF_EXPECTATION_LOWER_OR_EQUAL:
			res = MathUtils.le(pValue, expectedValue);
			break;
		case REF_EXPECTATION_GREATER:
			res = MathUtils.gt(pValue, expectedValue);
			break;
		case REF_EXPECTATION_GREATER_OR_EQUAL:
			res = MathUtils.ge(pValue, expectedValue);
			break;
		}

		return res;
	}


	private boolean isFieldDefined(IContext pContext, ValidationContext pValidationContext, ValidationDetails pDetails, Object pObject) {

		return isFieldDefined(pContext, pValidationContext, pDetails, pObject, null);
	}

	private boolean isFieldDefined(IContext pContext, ValidationContext pValidationContext, ValidationDetails pDetails, Object pObject, ValidationRuleRef pRef) {
		boolean res = false;

		if (pObject != null) {
			res = true;

			// For strings, we test it is not empty
			if (pObject.getClass().equals(String.class) && StringUtils.isBlank((String) pObject)) {
				res = false;
			}
			// For lists, we test it is not empty or has a minimum size
			else if (pObject instanceof Collection) {
				Collection<?> collection = (Collection<?>) pObject;

				Integer minSize = StringUtils.isNotBlank(pDetails.getExpectation()) ? ConvertUtils.parseInteger(pDetails.getExpectation()) : 1;
				if(CollectionUtils.isEmpty(collection) || CollectionUtils.size(collection) < minSize) {
					res = false;
				}
			}
			// For prices, we test both excluding and including VAT are defined
			else if(pObject.getClass().equals(Price.class) && Price.isNull((Price) pObject)) {
				res = false;
			}

			// Other types (long, boolean, double, etc.) are considered defined if not null

			// Sub-classes might have special rules

			// -- I/II Update field with referenced field
			IValidationField originalField = pValidationContext.getValidationField();
			if(pRef != null && pRef.getField() != null) {
				pValidationContext.setValidationField(pRef.getField());
			}

			// Only apply further tests if checking:
			//  - a simple field
			//  - a referenced field with known field configuration
			if(pRef == null || pRef.getField() != null) {
				res = res && isFieldActuallyDefined(pContext, pValidationContext, pObject);
			}

			// -- II/II Update field with original field
			if(pRef != null && pRef.getField() != null) {
				pValidationContext.setValidationField(originalField);
			}
		}

		return res;
	}


	/**
	 * @param pContext Context
	 * @param pValidationContext Validation context
	 * @param pObject Field value
	 * @return True if the field value represents an actual value (i.e. not a meaningless or default value)
	 */
	protected boolean isFieldActuallyDefined(IContext pContext, ValidationContext pValidationContext, Object pObject) {
		return true;
	}


	private static boolean isFieldEquals(Object pObject, List<String> pValues) {
		boolean res = false;

		if(pObject != null) {
			for(String fieldValue : getFieldValues(pObject)) {
				for(String value : IteratorUtils.nullableIterable(pValues)) {
					if(fieldValue.equalsIgnoreCase(value)) {
						res = true;
						break;
					}
				}
			}
		}

		return res;
	}

	private static boolean doesFieldContainAny(Object pObject, List<String> pValues) {
		boolean res = false;

		if(pObject != null ) {
			if(pObject instanceof Collection<?>) {
				for(String fieldValue : getFieldValues(pObject)) {
					for(String value : IteratorUtils.nullableIterable(pValues)) {
						if(fieldValue.equalsIgnoreCase(value)) {
							res = true;
							break;
						}
					}
				}
			}
			else {
				throw new IllegalStateException("Field is not a collection");
			}
		}

		return res;
	}

	private static List<String> getFieldValues(Object pObject) {
		List<String> res = new ArrayList<>();

		if (pObject != null) {
			if (pObject instanceof Collection<?>) {
				for(Object object : (Collection<?>) pObject) {
					res.addAll(getFieldValue(object));
				}
			}
			else {
				res.addAll(getFieldValue(pObject));
			}
		}

		return res;
	}

	private static List<String> getFieldValue(Object pObjectValue) {
		List<String> res = new ArrayList<>();

		if (pObjectValue != null) {

			Class<?> objectClass = pObjectValue.getClass();

			if(objectClass.equals(String.class)) {
				res.add((String) pObjectValue);
			}
			else if(objectClass.equals(Integer.class)) {
				res.add(ConvertUtils.toString((Integer) pObjectValue));
			}
			else if(objectClass.equals(Long.class)) {
				res.add(ConvertUtils.toString((Long) pObjectValue));
			}
			else if(objectClass.equals(Double.class)) {
				res.add(ConvertUtils.toString((Double) pObjectValue));
			}
			else if(objectClass.equals(BigDecimal.class)) {
				res.add(ConvertUtils.toString((BigDecimal) pObjectValue));
			}
			else if(objectClass.equals(Price.class)) {
				res.add(ConvertUtils.toString(((Price) pObjectValue).getExclVat()));
				res.add(ConvertUtils.toString(((Price) pObjectValue).getInclVat()));
			}
			else if(objectClass.equals(Boolean.class)) {
				res.add(ConvertUtils.toString((Boolean) pObjectValue));
			}
			else if(Date.class.isInstance(pObjectValue)) {
				res.add(ConvertUtils.toString((Date) pObjectValue));
			}
			else {
				throw new IllegalArgumentException("Unsupported field type");
			}
		}

		return res;
	}


	private Object getRef(IContext pContext, ValidationContext pValidationContext, ValidationRuleRef pRef) {

		// Reference object
		Object o = getDynamicReference(pContext, pValidationContext, pRef);
		if(o == null) {
			o = pValidationContext.getReference();
		}

		// Reference object field
		if(o != null) {
			for(String field : IteratorUtils.nullableIterable(StringUtils.split(pRef.getRef(), FIELD_SEPARATOR))) {
				o = getRef(o, field);
			}
		}

		return o;
	}

	private Object getDynamicReference(IContext pContext, ValidationContext pValidationContext, ValidationRuleRef pRef) {
		Object o = null;

		String referenceService = pRef.getRefService();
		if(StringUtils.isNotBlank(referenceService)) {
			// Cached already ?
			if(pValidationContext.containsDynamicReference(referenceService)) {
				o = pValidationContext.getDynamicReference(referenceService);
			}
			else {
				o = ((IReferenceService) getBean(referenceService)).getObject(pContext, pValidationContext.getReferenceKey());
				pValidationContext.addDynamicReference(referenceService, o);
			}
		}

		return o;
	}

	private static Object getRef(Object pObject, String pField) {
		Object res = null;
		if(pObject != null) {
			try {
				String getterMethodName = FIELD_GETTER_PREFIX + pField.substring(0, 1).toUpperCase() + pField.substring(1);
				res = pObject.getClass().getMethod(getterMethodName).invoke(pObject);
			}
			catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
				// Second attempt, with no 'get' prefix
				try {
					String getterMethodName = pField;
					res = pObject.getClass().getMethod(getterMethodName).invoke(pObject);
				}
				catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e2) {
					throw new IllegalStateException("Field reference '" + pField + "' is invalid");
				}
			}
		}
		return res;
	}


	private boolean validateFormat(IContext pContext, ValidationContext pValidationContext, Object pObject) {

		boolean res = false;

		// Update context
		pValidationContext.setValidationType(IValidationType.FORMAT);

		ValidationDetails detail = getValidationDetails(pValidationContext);
		if(detail != null
				&& isFieldDefined(pContext, pValidationContext, detail, pObject)) {

			for(String fieldValue : getFieldValues(pObject)) {

				// Check field value matches regular expression
				if(! detail.getFormat().getRegExp().matcher(fieldValue).matches()) {

					addValidation(pContext, pValidationContext, detail.getMessageBundle(), detail.getMessageKey(), pObject);
					res = true;
				}
			}
		}

		// Reset context
		pValidationContext.setValidationType(null);

		return res;
	}


	private boolean validateCustom(IContext pContext, ValidationContext pValidationContext, Object pObject) {

		boolean res = false;

		// Update context
		pValidationContext.setValidationType(IValidationType.CUSTOM);

		ValidationDetails detail = getValidationDetails(pValidationContext);
		if(detail != null
				&& isFieldDefined(pContext, pValidationContext, detail, pObject)) {

			for(String fieldValue : getFieldValues(pObject)) {

				ICustomValidator validator = (ICustomValidator) getBean(detail.getExpectation());

				if(! validator.isValid(pContext, pValidationContext.getReference(), fieldValue)) {

					addValidation(pContext, pValidationContext, detail.getMessageBundle(), detail.getMessageKey(), pObject);
					res = true;
				}
			}
		}

		// Reset context
		pValidationContext.setValidationType(null);

		return res;
	}


	private boolean validateDateVSRef(IContext pContext, ValidationContext pValidationContext, Object pObject) {

		boolean res = false;

		// Update context
		pValidationContext.setValidationType(IValidationType.DATE_VS_REF);

		ValidationDetails detail = getValidationDetails(pValidationContext);
		if(detail != null
				&& ! REQUIRED_CANCELLED_EXPECTATION.equalsIgnoreCase(detail.getExpectation())) { // Market-specific overriding

			for(ValidationRuleRef ruleRef : detail.getRefs()) {

				Object refDate = getRef(pContext, pValidationContext, ruleRef);

				if(! areDatesValid(pContext, pValidationContext, pObject, refDate, detail, ruleRef.getExpectation())) {
					res = true;
					break;
				}
			}
		}

		// Reset context
		pValidationContext.setValidationType(null);

		return res;
	}


	private boolean validateDateVSToday(IContext pContext, ValidationContext pValidationContext, Object pObject) {

		boolean res = false;

		// Update context
		pValidationContext.setValidationType(IValidationType.DATE_VS_TODAY);

		ValidationDetails detail = getValidationDetails(pValidationContext);
		if(detail != null
				&& ! REQUIRED_CANCELLED_EXPECTATION.equalsIgnoreCase(detail.getExpectation())) { // Market-specific overriding

			Date today = getCurrentDate();

			res = ! areDatesValid(pContext, pValidationContext, pObject, today, detail, detail.getExpectation());
		}

		// Reset context
		pValidationContext.setValidationType(null);

		return res;
	}


	private boolean validateDateVSRange(IContext pContext, ValidationContext pValidationContext, Object pObject) {

		boolean res = false;

		// Update context
		pValidationContext.setValidationType(IValidationType.DATE_VS_RANGE);

		ValidationDetails detail = getValidationDetails(pValidationContext);
		if(detail != null
				&& isFieldDefined(pContext, pValidationContext, detail, pObject)) {

			Date dateTested = (Date) pObject;
			Date today = getCurrentDate();
			DateRange dateRange = DateUtils.getDateRange(today, detail.getValidityPeriod());

			if(! DateUtils.isDateWithinRange(dateTested, dateRange)) {

				addValidation(pContext, pValidationContext, detail.getMessageBundle(), detail.getMessageKey(), dateRange.toString(pContext));
				res = true;
			}
		}

		// Reset context
		pValidationContext.setValidationType(null);

		return res;
	}


	private boolean areDatesValid(IContext pContext, ValidationContext pValidationContext, Object pObjectDate, Object pOtherDate, ValidationDetails pDetails, String pExpectation) {

		boolean valid = true;

		if (isFieldDefined(pContext, pValidationContext, pDetails, pObjectDate) && isFieldDefined(pContext, pValidationContext, pDetails, pOtherDate)) {

			if(! (pObjectDate instanceof Date) || ! (pOtherDate instanceof Date)
					|| ! areDatesValid(DateUtils.removeTimePart((Date) pObjectDate), DateUtils.removeTimePart((Date) pOtherDate), pExpectation)) {

				addValidation(pContext, pValidationContext, pDetails.getMessageBundle(), pDetails.getMessageKey(), pObjectDate);
				valid = false;
			}
		}

		return valid;
	}

	private static boolean areDatesValid(Date pObjectDate, Date pOtherDate, String pExpectation) {

		boolean before = false;
		boolean after = false;
		boolean equalsAllowed = false;

		switch(pExpectation) {

		case DATE_EXPECTATION_BEFORE:
			before = true;
			break;
		case DATE_EXPECTATION_BEFORE_OR_EQUAL:
			before = true;
			equalsAllowed = true;
			break;
		case DATE_EXPECTATION_AFTER:
			after = true;
			break;
		case DATE_EXPECTATION_AFTER_OR_EQUAL:
			after = true;
			equalsAllowed = true;
			break;
		default:
			// Should not happen, controlled at load
			throw new IllegalArgumentException("Unsupported date expectation");
		}


		return (before && pObjectDate.before(pOtherDate))
				|| (after && pObjectDate.after(pOtherDate))
				|| (equalsAllowed && pObjectDate.equals(pOtherDate));
	}


	/**
	 * Returns the validation details associated to the current validation context.</br>
	 * If no details are found, the stage {@link IProcessStage#ALL} is tested.
	 *
	 * @param pValidationContext Validation context
	 * @return Validation details
	 */
	protected static final ValidationDetails getValidationDetails(ValidationContext pValidationContext) {

		ValidationDetails details = pValidationContext.getValidationConfig().getValidationDetails(pValidationContext.getValidationKey());

		// If there is no validation setup for a specific stage, then check if a global setup (IProcessStage.ALL) exists
		if(details == null) {

			// Update context
			IProcessStage previousStage = pValidationContext.getProcessStage();
			pValidationContext.setProcessStage(IProcessStage.ALL);

			details = pValidationContext.getValidationConfig().getValidationDetails(pValidationContext.getValidationKey());

			// Reset context
			pValidationContext.setProcessStage(previousStage);
		}

		return details;
	}


	@Override
	public final String toString() {

		return getClass().getSimpleName();
	}


}
