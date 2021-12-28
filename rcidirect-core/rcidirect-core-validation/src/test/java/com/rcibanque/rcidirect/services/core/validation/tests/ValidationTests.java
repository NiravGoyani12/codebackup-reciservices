package com.rcibanque.rcidirect.services.core.validation.tests;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.BeanFactory;

import com.rcibanque.rcidirect.services.core.domain.ContextFactory;
import com.rcibanque.rcidirect.services.core.domain.IContext;
import com.rcibanque.rcidirect.services.core.domain.Price;
import com.rcibanque.rcidirect.services.core.response.APIMessage;
import com.rcibanque.rcidirect.services.core.response.APIMessageDetail;
import com.rcibanque.rcidirect.services.core.user.IUser;
import com.rcibanque.rcidirect.services.core.user.User;
import com.rcibanque.rcidirect.services.core.utils.DateUtils;
import com.rcibanque.rcidirect.services.core.validation.key.IProcessStage;
import com.rcibanque.rcidirect.services.core.validation.tests.domain.TestClass;
import com.rcibanque.rcidirect.services.core.validation.tests.validator.TestCustomValidator;
import com.rcibanque.rcidirect.services.core.validation.tests.validator.TestReferenceService;
import com.rcibanque.rcidirect.services.core.validation.tests.validator.TestValidator;

public class ValidationTests {


	@Mock
	private BeanFactory _beanFactory;

	private IContext _context;

	private TestValidator _validator;

	private TestClass _object;


	@BeforeEach
	void init() {

		MockitoAnnotations.initMocks(this);

		when(_beanFactory.getBean("TestCustomValidator")).thenReturn(new TestCustomValidator());

		when(_beanFactory.getBean("TestReferenceServiceKnownValue")).thenReturn(new TestReferenceService(true));
		when(_beanFactory.getBean("TestReferenceServiceUnknownValue")).thenReturn(new TestReferenceService(false));


		IUser user = new User();
		_context = ContextFactory.getContext(user);

		_validator = new TestValidator(_beanFactory, "TestValidationConfig");

		_object = buildValidObject(getCurrentDate());
	}


	private static Date getCurrentDate() {
		// Same default implementation as AbstractValidator
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(System.currentTimeMillis());
		return c.getTime();
	}


	@Test
	public void testShouldSucceedAsInitiallyBuiltObjectIsValid() {

		// ARRANGE

		// ACT
		_validator.validate(_context, _object, IProcessStage.ALL);

		// ASSERT
		assertNbOfValidationErrors(_context, 0);
	}


	@Test
	public void testShouldFailAsField1IsRequired() {

		// ARRANGE
		_object.setField1(null);

		// ACT
		_validator.validate(_context, _object, IProcessStage.ALL);

		// ASSERT
		assertNbOfValidationErrors(_context, 1);
	}


	@Test
	public void testShouldFailAsField1ShouldBeEqualToOKBasedOnCustomValidation() {

		// ARRANGE
		_object.setField1("KO");

		// ACT
		_validator.validate(_context, _object, IProcessStage.ALL);

		// ASSERT
		assertNbOfValidationErrors(_context, 1);
	}


	@Test
	public void testShouldFailAsField2IsRequiredIfField1IsDefined() {

		// ARRANGE
		_object.setField2(null);

		// ACT
		_validator.validate(_context, _object, IProcessStage.ALL);

		// ASSERT
		assertNbOfValidationErrors(_context, 1);
	}


	@Test
	public void testShouldFailAsField2IsRequiredActuallyDefinedIfField1IsDefined() {

		// ARRANGE
		_object.setField2("");

		// ACT
		_validator.validate(_context, _object, IProcessStage.ALL);

		// ASSERT
		assertNbOfValidationErrors(_context, 1);
	}


	@Test
	public void testShouldFailAsField2ShouldBeAlphabeticBasedOnFormat() {

		// ARRANGE
		_object.setField2("123");

		// ACT
		_validator.validate(_context, _object, IProcessStage.ALL);

		// ASSERT
		assertNbOfValidationErrors(_context, 1);
	}


	@Test
	public void testShouldFailAsField3ShouldBeAlphabeticBasedOnFormat() {

		// ARRANGE
		_object.setField3("123");

		// ACT
		_validator.validate(_context, _object, IProcessStage.ALL);

		// ASSERT
		assertNbOfValidationErrors(_context, 1);
	}


	@Test
	public void testShouldSucceedAsField4IsRequiredIfField3IsUndefined() {

		// ARRANGE
		_object.setField3(null);

		// ACT
		_validator.validate(_context, _object, IProcessStage.ALL);

		// ASSERT
		assertNbOfValidationErrors(_context, 0);
	}


	@Test
	public void testShouldSucceedAsField3IsRequiredIfField4IsUndefined() {

		// ARRANGE
		_object.setField4(null);

		// ACT
		_validator.validate(_context, _object, IProcessStage.ALL);

		// ASSERT
		assertNbOfValidationErrors(_context, 0);
	}


	@Test
	public void testShouldFailAsEitherField3OrField4OrBothAreRequired() {

		// ARRANGE
		_object.setField3(null);
		_object.setField4(null);

		// ACT
		_validator.validate(_context, _object, IProcessStage.ALL);

		// ASSERT
		assertNbOfValidationErrors(_context, 2);
	}


	@Test
	public void testShouldFailAsField5IsRequiredIfField6IsEqualToABC() {

		// ARRANGE
		_object.setField5(null);
		_object.setField6("ABC");

		// ACT
		_validator.validate(_context, _object, IProcessStage.ALL);

		// ASSERT
		assertNbOfValidationErrors(_context, 1);
	}


	@Test
	public void testShouldFailAsField6IsRequiredIfField5IsDifferentToXYZ() {

		// ARRANGE
		_object.setField5("VALUE");
		_object.setField6(null);

		// ACT
		_validator.validate(_context, _object, IProcessStage.ALL);

		// ASSERT
		assertNbOfValidationErrors(_context, 1);
	}


	@Test
	public void testShouldSucceedAsField21RulesAreFollowedWithEqual() {

		// ARRANGE
		_object.setField22(DateUtils.addDays(_object.getField21(), 1));
		_object.setField23(DateUtils.addDays(_object.getField21(), 0));
		_object.setField24(DateUtils.addDays(_object.getField21(), 0));
		_object.setField25(DateUtils.addDays(_object.getField21(), -1));

		// ACT
		_validator.validate(_context, _object, IProcessStage.ALL);

		// ASSERT
		assertNbOfValidationErrors(_context, 0);
	}


	@Test
	public void testShouldSucceedAsField21AsRulesAreFollowedWithoutEqual() {

		// ARRANGE
		_object.setField22(DateUtils.addDays(_object.getField21(), 1));
		_object.setField23(DateUtils.addDays(_object.getField21(), 1));
		_object.setField24(DateUtils.addDays(_object.getField21(), -1));
		_object.setField25(DateUtils.addDays(_object.getField21(), -1));

		// ACT
		_validator.validate(_context, _object, IProcessStage.ALL);

		// ASSERT
		assertNbOfValidationErrors(_context, 0);
	}


	@Test
	public void testShouldFailAsField21ShouldBeBeforeField22() {

		// ARRANGE
		_object.setField22(DateUtils.addDays(_object.getField21(), 0));

		// ACT
		_validator.validate(_context, _object, IProcessStage.ALL);

		// ASSERT
		assertNbOfValidationErrors(_context, 1);
	}


	@Test
	public void testShouldFailAsField21ShouldBeBeforeOrEqualToField23() {

		// ARRANGE
		_object.setField23(DateUtils.addDays(_object.getField21(), -1));

		// ACT
		_validator.validate(_context, _object, IProcessStage.ALL);

		// ASSERT
		assertNbOfValidationErrors(_context, 1);
	}


	@Test
	public void testShouldFailAsField21ShouldBeAfterOrEqualField24() {

		// ARRANGE
		_object.setField24(DateUtils.addDays(_object.getField21(), 1));

		// ACT
		_validator.validate(_context, _object, IProcessStage.ALL);

		// ASSERT
		assertNbOfValidationErrors(_context, 1);
	}


	@Test
	public void testShouldFailAsField21ShouldBeAfterField25() {

		// ARRANGE
		_object.setField25(DateUtils.addDays(_object.getField21(), 0));

		// ACT
		_validator.validate(_context, _object, IProcessStage.ALL);

		// ASSERT
		assertNbOfValidationErrors(_context, 1);
	}


	@Test
	public void testShouldSucceedAsField31IsRequiredIfField32ContainsAnyOfAbcOrDefAndField33ContainsNoneOfUvwXyz() {

		// ARRANGE
		_object.setField31(null);
		_object.setField32(Arrays.asList("QWE", "RTY"));
		_object.setField33(Arrays.asList("AZERTY"));

		// ACT
		_validator.validate(_context, _object, IProcessStage.ALL);

		// ASSERT
		assertNbOfValidationErrors(_context, 0);

		// --------------------------------------

		// ARRANGE
		_object.setField31(null);
		_object.setField32(Arrays.asList("ABC", "ABC"));
		_object.setField33(Arrays.asList("XYZ"));

		// ACT
		_validator.validate(_context, _object, IProcessStage.ALL);

		// ASSERT
		assertNbOfValidationErrors(_context, 0);

		// --------------------------------------

		// ARRANGE
		_object.setField31(null);
		_object.setField32(Arrays.asList("QWE", "RTY"));
		_object.setField33(Arrays.asList("XYZ"));

		// ACT
		_validator.validate(_context, _object, IProcessStage.ALL);

		// ASSERT
		assertNbOfValidationErrors(_context, 0);
	}


	@Test
	public void testShouldFailAsField31IsRequiredIfField32ContainsAnyOfAbcOrDefAndField33ContainsNoneOfUvwXyz() {

		// ARRANGE
		_object.setField31(null);
		_object.setField32(Arrays.asList("DEF", "DEF"));
		_object.setField33(Arrays.asList("AZERTY"));

		// ACT
		_validator.validate(_context, _object, IProcessStage.ALL);

		// ASSERT
		assertNbOfValidationErrors(_context, 1);
	}


	@Test
	public void testShouldSucceedAsField31bIsRequiredIfField32bContainsAnyOfAbcOrDefOrField33bContainsNoneOfUvwXyz() {

		// ARRANGE
		_object.setField31b(null);
		_object.setField32b(Arrays.asList("QWERTY"));
		_object.setField33b(Arrays.asList("XYZ"));

		// ACT
		_validator.validate(_context, _object, IProcessStage.ALL);

		// ASSERT
		assertNbOfValidationErrors(_context, 0);
	}


	@Test
	public void testShouldFailAsField31bIsRequiredIfField32bContainsAnyOfAbcOrDefOrField33bContainsNoneOfUvwXyz() {

		// ARRANGE
		_object.setField31b(null);
		_object.setField32b(Arrays.asList("DEF"));
		_object.setField33b(Arrays.asList("AZERTY"));

		// ACT
		_validator.validate(_context, _object, IProcessStage.ALL);

		// ASSERT
		assertNbOfValidationErrors(_context, 1);

		// --------------------------------------

		// ARRANGE
		_object.setField31b(null);
		_object.setField32b(Arrays.asList("AZERTY"));
		_object.setField33b(Arrays.asList("XYZ"));

		// ACT
		_validator.validate(_context, _object, IProcessStage.ALL);

		// ASSERT
		assertNbOfValidationErrors(_context, 1);
	}


	@Test
	public void testShouldFailAsField32RequiresAtLeastTwoElements() {

		// ARRANGE
		_object.setField32(Arrays.asList("XYZ"));

		// ACT
		_validator.validate(_context, _object, IProcessStage.ALL);

		// ASSERT
		assertNbOfValidationErrors(_context, 1);
	}


	@Test
	public void testShouldFailAsField41ShouldBeBeforeToday() {

		// ARRANGE
		_object.setField41(DateUtils.addDays(getCurrentDate(), 0));

		// ACT
		_validator.validate(_context, _object, IProcessStage.ALL);

		// ASSERT
		assertNbOfValidationErrors(_context, 1);
	}


	@Test
	public void testShouldFailAsField42ShouldBeBeforeOrEqualToToday() {

		// ARRANGE
		_object.setField42(DateUtils.addDays(getCurrentDate(), 1));

		// ACT
		_validator.validate(_context, _object, IProcessStage.ALL);

		// ASSERT
		assertNbOfValidationErrors(_context, 1);
	}


	@Test
	public void testShouldSucceedAsField42ShouldBeBeforeOrEqualToTodayWithEqualDates() {

		// ARRANGE
		_object.setField42(DateUtils.addDays(getCurrentDate(), 0));

		// ACT
		_validator.validate(_context, _object, IProcessStage.ALL);

		// ASSERT
		assertNbOfValidationErrors(_context, 0);
	}


	@Test
	public void testShouldSucceedAsField42ShouldBeBeforeOrEqualToTodayWithoutEqualDates() {

		// ARRANGE
		_object.setField42(DateUtils.addDays(getCurrentDate(), -1));

		// ACT
		_validator.validate(_context, _object, IProcessStage.ALL);

		// ASSERT
		assertNbOfValidationErrors(_context, 0);
	}


	@Test
	public void testShouldFailAsField43ShouldBeAfterOrEqualToToday() {

		// ARRANGE
		_object.setField43(DateUtils.addDays(getCurrentDate(), -1));

		// ACT
		_validator.validate(_context, _object, IProcessStage.ALL);

		// ASSERT
		assertNbOfValidationErrors(_context, 1);
	}


	@Test
	public void testShouldSucceedAsField43ShouldBeAfterOrEqualToTodayWithEqualDates() {

		// ARRANGE
		_object.setField43(DateUtils.addDays(getCurrentDate(), 0));

		// ACT
		_validator.validate(_context, _object, IProcessStage.ALL);

		// ASSERT
		assertNbOfValidationErrors(_context, 0);
	}


	@Test
	public void testShouldSucceedAsField43ShouldBeAfterOrEqualToTodayWithoutEqualDates() {

		// ARRANGE
		_object.setField43(DateUtils.addDays(getCurrentDate(), 1));

		// ACT
		_validator.validate(_context, _object, IProcessStage.ALL);

		// ASSERT
		assertNbOfValidationErrors(_context, 0);
	}


	@Test
	public void testShouldFailAsField44ShouldBeAfterToday() {

		// ARRANGE
		_object.setField44(DateUtils.addDays(getCurrentDate(), 0));

		// ACT
		_validator.validate(_context, _object, IProcessStage.ALL);

		// ASSERT
		assertNbOfValidationErrors(_context, 1);
	}


	@Test
	public void testShouldSucceedAsField45RespectsDateRange() {

		// ARRANGE
		_object.setField45(DateUtils.addDays(getCurrentDate(), -2));

		// ACT
		_validator.validate(_context, _object, IProcessStage.ALL);

		// ASSERT
		assertNbOfValidationErrors(_context, 0);

		// --------------------------------------

		// ARRANGE
		_object.setField45(DateUtils.addDays(getCurrentDate(), 0));

		// ACT
		_validator.validate(_context, _object, IProcessStage.ALL);

		// ASSERT
		assertNbOfValidationErrors(_context, 0);

		// --------------------------------------

		// ARRANGE
		_object.setField45(DateUtils.addDays(getCurrentDate(), +2));

		// ACT
		_validator.validate(_context, _object, IProcessStage.ALL);

		// ASSERT
		assertNbOfValidationErrors(_context, 0);
	}


	@Test
	public void testShouldFailAsField45ShouldRespectDateRange() {

		// ARRANGE
		_object.setField45(DateUtils.addDays(getCurrentDate(), -3));

		// ACT
		_validator.validate(_context, _object, IProcessStage.ALL);

		// ASSERT
		assertNbOfValidationErrors(_context, 1);

		// --------------------------------------

		// ARRANGE
		_object.setField45(DateUtils.addDays(getCurrentDate(), +3));

		// ACT
		_validator.validate(_context, _object, IProcessStage.ALL);

		// ASSERT
		assertNbOfValidationErrors(_context, 1);
	}


	@Test
	public void testShouldFailAsField51IsRequiredIfReferencedFieldEqualToKnownValue() {

		// ARRANGE
		_object.setField51(null);

		// ACT
		_validator.validate(_context, _object, IProcessStage.ALL);

		// ASSERT
		assertNbOfValidationErrors(_context, 1);
	}


	@Test
	public void testShouldFailAsField52IsRequiredIfReferencedFieldDifferentToUnknownValue() {

		// ARRANGE
		_object.setField52(null);

		// ACT
		_validator.validate(_context, _object, IProcessStage.ALL);

		// ASSERT
		assertNbOfValidationErrors(_context, 1);
	}


	@Test
	public void testShouldSucceedAsField62isRequiredIfField61IsDefinedButField61IsNotActuallyDefined() {

		// ARRANGE
		_object.setField61("-1");
		_object.setField62(null);

		// ACT
		_validator.validate(_context, _object, IProcessStage.ALL);

		// ASSERT
		assertNbOfValidationErrors(_context, 0);
	}


	@Test
	public void testShouldFailAsField63isRequiredIfField64IsDefinedAndAlthoughField64IsNotActuallyDefinedNoReferencedFieldHasBeenConfiguredToKnowThat() {

		// ARRANGE
		_object.setField63(null);
		_object.setField64("-1");

		// ACT
		_validator.validate(_context, _object, IProcessStage.ALL);

		// ASSERT
		assertNbOfValidationErrors(_context, 1);
	}


	@Test
	public void testShouldFailAsField72isRequiredInMarket() {

		// ARRANGE
		_object.setField72(null);

		// ACT
		_validator.validate(_context, _object, IProcessStage.ALL);

		// ASSERT
		assertNbOfValidationErrors(_context, 1);
	}


	@Test
	public void testShouldSucceedAsField81isRequiredIfField82IsPositive() {

		// ARRANGE
		_object.setField81("Value");
		_object.setField82(Price.getFromExclVat(BigDecimal.valueOf(10), 0.2));

		// ACT
		_validator.validate(_context, _object, IProcessStage.ALL);

		// ASSERT
		assertNbOfValidationErrors(_context, 0);
	}


	@Test
	public void testShouldFailAsField81isRequiredIfField82IsPositive() {

		// ARRANGE
		_object.setField81("");
		_object.setField82(Price.getFromExclVat(BigDecimal.valueOf(10), 0.2));

		// ACT
		_validator.validate(_context, _object, IProcessStage.ALL);

		// ASSERT
		assertNbOfValidationErrors(_context, 1);
	}


	@Test
	public void testShouldFailAsField81isRequiredIfField83IsPositive() {

		// ARRANGE
		_object.setField81("");
		_object.setField83(BigDecimal.valueOf(0.1));

		// ACT
		_validator.validate(_context, _object, IProcessStage.ALL);

		// ASSERT
		assertNbOfValidationErrors(_context, 1);
	}


	@Test
	public void testShouldFailAsField91isRequiredIfField92IsLT1() {

		// ARRANGE
		_object.setField91("");
		_object.setField92(BigDecimal.valueOf(0.9));

		// ACT
		_validator.validate(_context, _object, IProcessStage.ALL);

		// ASSERT
		assertNbOfValidationErrors(_context, 1);
	}


	@Test
	public void testShouldFailAsField91isRequiredIfField93IsLE1() {

		// ARRANGE
		_object.setField91("");
		_object.setField93(BigDecimal.valueOf(1.0));

		// ACT
		_validator.validate(_context, _object, IProcessStage.ALL);

		// ASSERT
		assertNbOfValidationErrors(_context, 1);
	}


	@Test
	public void testShouldFailAsField91isRequiredIfField94IsGT1() {

		// ARRANGE
		_object.setField91("");
		_object.setField94(BigDecimal.valueOf(1.1));

		// ACT
		_validator.validate(_context, _object, IProcessStage.ALL);

		// ASSERT
		assertNbOfValidationErrors(_context, 1);
	}


	@Test
	public void testShouldFailAsField91isRequiredIfField95IsGE1() {

		// ARRANGE
		_object.setField91("");
		_object.setField95(BigDecimal.valueOf(1.0));

		// ACT
		_validator.validate(_context, _object, IProcessStage.ALL);

		// ASSERT
		assertNbOfValidationErrors(_context, 1);
	}


	private static TestClass buildValidObject(Date pToday) {
		TestClass object = new TestClass();

		// Field 1 is required
		// Field 1 must be equal to OK based on custom validation
		// Field 1 must be alphabetic based format validations
		object.setField1("OK");

		// Field 2 is required if field 1 is defined
		// Field 2 must be alphabetic based format validations
		object.setField2("Defined");

		// Field 3 is required if field 4 is undefined
		// Field 4 is required if field 3 is undefined
		object.setField3("OK");
		object.setField4("OK");

		// Field 5 is required if field 6 is equal to ABC
		// Field 6 is required if field 5 is different to XYZ
		object.setField5("OK");
		object.setField6("ABC");

		// Field 21 should be before field 22, before or equal to field 23, after field 24, after or equal to field 25
		object.setField21(getCurrentDate());

		// Field 31 is required if field 32 contains any of ABC/DEF and field 33 contains none of UVW/XYZ
		// Field 32 requires at least two elements
		object.setField31("OK");
		object.setField32(Arrays.asList("ABC", "AZERTY"));
		object.setField33(Arrays.asList("QWERTY"));

		// Field 31b is required if field 32b contains any of ABC/DEF or field 33b contains none of UVW/XYZ
		object.setField31b("OK");
		object.setField32b(Arrays.asList("ABC"));
		object.setField33b(Arrays.asList("XYZ"));

		// Field 41 must be before today
		// Field 42 must be before or equal to today
		// Field 43 must be after or equal to today
		// Field 44 must be after today
		object.setField41(DateUtils.addDays(pToday, -1));
		object.setField42(DateUtils.addDays(pToday, 0));
		object.setField43(DateUtils.addDays(pToday, 0));
		object.setField44(DateUtils.addDays(pToday, 1));

		// Field 51 is required if referenced field is equal to a value
		object.setField51("Defined");

		// Field 52 is required if referenced field is different to a value
		object.setField52("Defined");

		// Field 62 is required if field 61 is defined
		// Field 63 is required if field 64 is defined
		object.setField61("Defined");
		object.setField62("Defined");
		object.setField63("Defined");
		object.setField64("Defined");

		// Field 71 is required (overridden to optional in market)
		object.setField71(null);

		// Field 72 is optional (overridden to required in market)
		object.setField72("Defined");

		// Field 81 is required if field 82 or field 83 is positive
		object.setField82(Price.getZero());
		object.setField83(BigDecimal.ZERO);

		// Field 91 is required if field 92 is lt 1 or field 93 is le 1 or field 94 is gt 1 or field 95 is ge 1
		object.setField92(BigDecimal.valueOf(1.0));
		object.setField93(BigDecimal.valueOf(1.1));
		object.setField94(BigDecimal.valueOf(1.0));
		object.setField95(BigDecimal.valueOf(0.9));

		return object;
	}


	private static void assertNbOfValidationErrors(IContext pContext, int pExtectedNb) {

		List<APIMessage> messages = pContext.getMessages().get(true);

		if(pExtectedNb == 0) {

			assertThat(messages.size()).isEqualTo(0);
		}
		else {
			assertThat(messages.size()).isEqualTo(1);

			List<APIMessageDetail> validations = messages.get(0).getDetails();
			assertThat(validations.size()).isEqualTo(pExtectedNb);
		}
	}

}
