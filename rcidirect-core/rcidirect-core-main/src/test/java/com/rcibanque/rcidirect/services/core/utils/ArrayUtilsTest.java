package com.rcibanque.rcidirect.services.core.utils;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class ArrayUtilsTest {


	@Test
	public void testSwitchPosition() {

		String[] array = null;
		ArrayUtils.switchPosition(array, "one", 0);

		array = new String[]{};
		ArrayUtils.switchPosition(array, "one", 0);
		assertThat(array).hasSize(0);

		array = new String[]{"one", "two", "three"};
		ArrayUtils.switchPosition(array, "one", 2);
		assertThat(array).hasSize(3);
		assertThat("three").isEqualTo(array[0]);
		assertThat("two").isEqualTo(array[1]);
		assertThat("one").isEqualTo(array[2]);
	}

}
