package com.rcibanque.rcidirect.services.core.validation.validators;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import com.rcibanque.rcidirect.services.core.validation.validator.IBANValidator;

public class IBANValidatorTest {

	@Test
	public void validIBANTest() {

		IBANValidator validator = new IBANValidator();

		assertThat(validator.isValid(null, null, "BE33363062158246")).isTrue();
	}

	@Test
	public void invalidIBANTest() {

		IBANValidator validator = new IBANValidator();

		assertThat(validator.isValid(null, null, "BE33363062158247")).isFalse();
		assertThat(validator.isValid(null, null, "XX")).isFalse();
		assertThat(validator.isValid(null, null, "AE33363062158246")).isFalse();
		assertThat(validator.isValid(null, null, 12343)).isFalse();
		assertThat(validator.isValid(null, null, null)).isFalse();
	}


}
