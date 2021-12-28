package com.rcibanque.rcidirect.services.core.response;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Locale;

import org.junit.jupiter.api.Test;

public class APIMessagesBuilderTest {

	@Test
	public void testAPIMessageBuilder() {

		IMessages messages = APIMessages.getInstance(Locale.UK);

		assertThat(messages.hasError()).isFalse();
		messages.error(TestMessages.getInstance(), "test.address.already.exist", "1 Elle Road, WD12 7KU" );
		assertThat(messages.hasError()).isTrue();

		assertThat(messages.hasWarning()).isFalse();
		messages.warning(TestMessages.getInstance(), "test.bank.details.already.exist", "HSBC Bank, London" );
		assertThat(messages.hasWarning()).isTrue();

		assertThat(messages.hasInfo()).isFalse();
		messages.info(TestMessages.getInstance(), "test.bank.details.already.exist", "info message test" );
		assertThat(messages.hasInfo()).isTrue();

	}


}
