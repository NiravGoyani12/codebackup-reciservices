package com.rcibanque.rcidirect.services.core.utils;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class DAOUtilsTest {


	@Test
	public void testEscape() {

		test("[", "[[]");
		test("]", "[]]");
		test("_", "[_]");
		test("-", "[-]");
		test("%", "[%]");
		test("*", "%");

		test("[]_-%*", "[[][]][_][-][%]%");
	}


	private static void test(String pProvided, String pExpected) {

		assertThat(DAOUtils.escapeDatabaseSpecialCharacters(pProvided)).isEqualTo(pExpected);
	}

}
