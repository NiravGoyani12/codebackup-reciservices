package com.rcibanque.rcidirect.services.core.utils;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;

import org.assertj.core.data.Offset;
import org.junit.jupiter.api.Test;

import com.rcibanque.rcidirect.services.core.domain.DateTime;

public class ConvertUtilsTest {

	@Test
	public void testBooleanConversions() {

		assertThat(ConvertUtils.parseBoolean("true")).isEqualTo(Boolean.TRUE);
		assertThat(ConvertUtils.parseBoolean("false")).isEqualTo(Boolean.FALSE);
		assertThat(ConvertUtils.parseBoolean("abc")).isEqualTo(Boolean.FALSE);
		assertThat(ConvertUtils.parseBoolean("")).isEqualTo(null);
		assertThat(ConvertUtils.parseBoolean(null)).isEqualTo(null);

		assertThat(ConvertUtils.toString(Boolean.TRUE)).isEqualTo("true");
		assertThat(ConvertUtils.toString(Boolean.FALSE)).isEqualTo("false");
		assertThat(ConvertUtils.toString((Boolean) null)).isEqualTo(null);

		// Binary
		assertThat("1").isEqualTo(ConvertUtils.toStringBinary(true));
		assertThat("0").isEqualTo(ConvertUtils.toStringBinary(false));
	}


	@Test
	public void testIntegerConversions() {

		assertThat(ConvertUtils.parseInteger("123")).isEqualTo(Integer.valueOf(123));
		assertThat(ConvertUtils.parseInteger("-123")).isEqualTo(Integer.valueOf(-123));
		assertThat(ConvertUtils.parseInteger("ABC")).isEqualTo(null);
		assertThat(ConvertUtils.parseInteger("")).isEqualTo(null);
		assertThat(ConvertUtils.parseInteger(null)).isEqualTo(null);

		assertThat(ConvertUtils.toString(Integer.valueOf(123))).isEqualTo("123");
		assertThat(ConvertUtils.toString(Integer.valueOf(-123))).isEqualTo("-123");
		assertThat(ConvertUtils.toString((Integer) null)).isEqualTo(null);
	}


	@Test
	public void testLongConversions() {

		assertThat(ConvertUtils.parseLong("123")).isEqualTo(Long.valueOf(123));
		assertThat(ConvertUtils.parseLong("-123")).isEqualTo(Long.valueOf(-123));
		assertThat(ConvertUtils.parseLong("ABC")).isEqualTo(null);
		assertThat(ConvertUtils.parseLong("")).isEqualTo(null);
		assertThat(ConvertUtils.parseLong(null)).isEqualTo(null);

		assertThat(ConvertUtils.toString(Long.valueOf(123))).isEqualTo("123");
		assertThat(ConvertUtils.toString(Long.valueOf(-123))).isEqualTo("-123");
		assertThat(ConvertUtils.toString((Long) null)).isEqualTo(null);
	}


	@Test
	public void testDoubleConversions() {

		assertThat(ConvertUtils.parseDouble("123.45")).isEqualTo(Double.valueOf(123.45));
		assertThat(ConvertUtils.parseDouble("-123.45")).isEqualTo(Double.valueOf(-123.45));
		assertThat(ConvertUtils.parseDouble("ABC")).isEqualTo(null);
		assertThat(ConvertUtils.parseDouble("")).isEqualTo(null);
		assertThat(ConvertUtils.parseDouble(null)).isEqualTo(null);

		assertThat(ConvertUtils.toString(Double.valueOf(123.45))).isEqualTo("123.45");
		assertThat(ConvertUtils.toString(Double.valueOf(-123.45))).isEqualTo("-123.45");
		assertThat(ConvertUtils.toString((Double) null)).isEqualTo(null);

		// Not 1E-6
		assertThat(ConvertUtils.toString(Double.valueOf(0.000001)).startsWith("0.000001"));
	}


	@Test
	public void testDateConversions() {

		assertThat(ConvertUtils.parseDate("2016-10-28")).isEqualTo(DateUtils.getDate(2016, 10, 28));
		assertThat(ConvertUtils.parseDate("ABC")).isEqualTo(null);
		assertThat(ConvertUtils.parseDate("")).isEqualTo(null);
		assertThat(ConvertUtils.parseDate(null)).isEqualTo(null);

		assertThat(ConvertUtils.toString(DateUtils.getDate(2016, 10, 28))).isEqualTo("2016-10-28");
		assertThat(ConvertUtils.toString((Date) null)).isEqualTo(null);
	}


	@Test
	public void testDateTimeConversions() {

		assertThat(ConvertUtils.parseDateTime("2016-10-28 15:45:20")).isEqualTo(DateUtils.getDate(2016, 10, 28, 15, 45, 20));
		assertThat(ConvertUtils.parseDate("ABC")).isEqualTo(null);
		assertThat(ConvertUtils.parseDate("")).isEqualTo(null);
		assertThat(ConvertUtils.parseDate(null)).isEqualTo(null);

		assertThat(ConvertUtils.toString(DateUtils.getDate(2016, 10, 28, 15, 45, 20))).isEqualTo("2016-10-28 15:45:20");
		assertThat(ConvertUtils.toString((Date) null)).isEqualTo(null);
	}


	@Test
	public void testDatePropertiesConversions() {

		assertThat(ConvertUtils.parseDateProperties("20161028")).isEqualTo(DateUtils.getDate(2016, 10, 28));
		assertThat(ConvertUtils.parseDateProperties("ABC")).isEqualTo(null);
		assertThat(ConvertUtils.parseDateProperties("")).isEqualTo(null);
		assertThat(ConvertUtils.parseDateProperties(null)).isEqualTo(null);

		assertThat(ConvertUtils.toStringProperties(DateUtils.getDate(2016, 10, 28))).isEqualTo("20161028");
		assertThat(ConvertUtils.toStringProperties((Date) null)).isEqualTo(null);
	}


	@Test
	public void testDateDisplayV1Conversions() {

		assertThat(ConvertUtils.parseDateDisplayV1("14/02/2019")).isEqualTo(DateUtils.getDate(2019, 02, 14));
		assertThat(ConvertUtils.parseDateDisplayV1("")).isEqualTo(null);
		assertThat(ConvertUtils.parseDateDisplayV1(null)).isEqualTo(null);

		assertThat(ConvertUtils.parseDateTimeDisplayV1("14/02/2019 07:29:30")).isEqualTo(DateUtils.getDate(2019, 02, 14, 7, 29, 30));
		assertThat(ConvertUtils.parseDateTimeDisplayV1("")).isEqualTo(null);
		assertThat(ConvertUtils.parseDateTimeDisplayV1(null)).isEqualTo(null);

		assertThat(ConvertUtils.toStringDisplayV1(DateUtils.getDate(2016, 10, 28))).isEqualTo("28/10/2016");
		assertThat(ConvertUtils.toStringProperties((Date) null)).isEqualTo(null);

		assertThat(ConvertUtils.toStringDisplayV1(DateUtils.getDate(2016, 10, 28, 7, 32, 20))).isEqualTo("28/10/2016 07:32:20");
		assertThat(ConvertUtils.toStringProperties((DateTime) null)).isEqualTo(null);
	}


	@Test
	public void testParseBigDecimal() {

		assertThat(ConvertUtils.parseBigDecimal(null)).isNull();

		assertThat(ConvertUtils.parseBigDecimal("ABC")).isNull();

		assertThat(Double.valueOf(1.23456).doubleValue()).isCloseTo(ConvertUtils.parseBigDecimal("1.23456").doubleValue(), Offset.offset(0.000001));
	}

}
