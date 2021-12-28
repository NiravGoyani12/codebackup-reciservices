package com.rcibanque.rcidirect.services.core.utils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import com.rcibanque.rcidirect.services.core.domain.DateTime;


public class ConvertUtils {

	/**
	 * Date time format used for REST (yyyy-MM-dd HH:mm:ss)
	 */
	public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

	private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);

	/**
	 * Date format used for REST (yyyy-MM-dd)
	 */
	public static final String DATE_FORMAT = "yyyy-MM-dd";

	private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_FORMAT);

	/**
	 * Date format used for properties (yyyyMMdd)
	 */
	private static final String DATE_FORMAT_PROPERTY = "yyyyMMdd";

	private static final DateTimeFormatter DATE_FORMATTER_PROPERTY = DateTimeFormatter.ofPattern(DATE_FORMAT_PROPERTY);

	/**
	 * Date format used for properties (yyMMdd)
	 */
	private static final String DATE_FORMAT_PROPERTY_SHORT = "yyMMdd";

	private static final DateTimeFormatter DATE_FORMATTER_PROPERTY_SHORT = DateTimeFormatter.ofPattern(DATE_FORMAT_PROPERTY_SHORT);

	/**
	 * Date format used for display or V1 (dd/MM/yyyy)
	 */
	private static final String DATE_FORMAT_DISPLAY_V1 = "dd/MM/yyyy";

	private static final DateTimeFormatter DATE_FORMATTER_DISPLAY_V1 = DateTimeFormatter.ofPattern(DATE_FORMAT_DISPLAY_V1);

	/**
	 * Datetime format used for display or V1 (dd/MM/yyyy HH:mm:ss)
	 */
	private static final String DATETIME_FORMAT_DISPLAY_V1 = "dd/MM/yyyy HH:mm:ss";

	private static final DateTimeFormatter DATETIME_FORMATTER_DISPLAY_V1 = DateTimeFormatter.ofPattern(DATETIME_FORMAT_DISPLAY_V1);

	private ConvertUtils() {}


	/**
	 * Parse a string boolean
	 *
	 * @param pValue String boolean
	 * @return Boolean, or null if format is invalid
	 */
	public static Boolean parseBoolean(String pValue) {
		Boolean res = null;
		if(StringUtils.isNotBlank(pValue)) {
			res = Boolean.valueOf(StringUtils.trim(pValue));
		}
		return res;
	}

	/**
	 * Return a string representation of a boolean
	 *
	 * @param pValue Boolean value
	 * @return String representation, or null
	 */
	public static String toString(Boolean pValue) {
		return pValue != null ? Boolean.toString(pValue) : null;
	}

	/**
	 * Return a string binary representation ("1" or "0") of a primitive boolean
	 *
	 * @param pValue Boolean value
	 * @return String binary representation
	 */
	public static final String toStringBinary(boolean pValue) {
		return pValue ? "1" : "0";
	}


	/**
	 * Parse a string integer
	 *
	 * @param pValue String integer
	 * @return Integer, or null if format is invalid
	 */
	public static Integer parseInteger(String pValue) {
		Integer res = null;
		if(StringUtils.isNotBlank(pValue)) {
			try {
				res = Integer.valueOf(StringUtils.trim(pValue));
			}
			catch(NumberFormatException e) {
				// Do nothing
			}
		}
		return res;
	}

	/**
	 * Return a string representation of an integer
	 *
	 * @param pValue Integer value
	 * @return String representation, or null
	 */
	public static String toString(Integer pValue) {
		return pValue != null ? Integer.toString(pValue) : null;
	}


	/**
	 * Parse a string long
	 *
	 * @param pValue String long
	 * @return Long, or null if format is invalid
	 */
	public static Long parseLong(String pValue) {
		Long res = null;
		if(StringUtils.isNotBlank(pValue)) {
			try {
				res = Long.valueOf(StringUtils.trim(pValue));
			}
			catch(NumberFormatException e) {
				// Do nothing
			}
		}
		return res;
	}

	/**
	 * Return a string representation of an long
	 *
	 * @param pValue Long value
	 * @return String representation, or null
	 */
	public static String toString(Long pValue) {
		return pValue != null ? Long.toString(pValue) : null;
	}


	/**
	 * Parse a string double
	 *
	 * @param pValue String double
	 * @return Double, or null if format is invalid
	 */
	public static Double parseDouble(String pValue) {
		Double res = null;
		if(StringUtils.isNotBlank(pValue)) {
			try {
				res = Double.valueOf(StringUtils.trim(pValue));
			}
			catch(NumberFormatException e) {
				// Do nothing
			}
		}
		return res;
	}

	/**
	 * Return a string representation of a double
	 *
	 * @param pValue Double value
	 * @return String representation, or null
	 */
	public static String toString(Double pValue) {
		// Convert to BigDecimal to avoid "1.8E-4" output
		return pValue != null && pValue != Double.NaN ? toString(BigDecimal.valueOf(pValue)) : null;
	}


	/**
	 * Parse a string big decimal (double)
	 *
	 * @param pValue String big decimal (double)
	 * @return Big decimal, or null if format is invalid
	 */
	public static BigDecimal parseBigDecimal(String pValue) {
		BigDecimal res = null;
		if(StringUtils.isNotBlank(pValue)) {
			try {
				res = new BigDecimal(StringUtils.trim(pValue));
			}
			catch(NumberFormatException e) {
				// Do nothing
			}
		}
		return res;
	}

	/**
	 * Return a string representation of a big decimal (double)
	 *
	 * @param pValue Big decimal (double) value
	 * @return String representation, or null
	 */
	public static String toString(BigDecimal pValue) {
		return pValue != null ? pValue.toPlainString() : null;
	}


	/**
	 * Parse a string date
	 * <br/>
	 * Expected pattern {@link ConvertUtils#DATE_TIME_FORMAT} (to be used for returning data to the client, with a time part)
	 *
	 * @param pValue String date
	 * @return Date, or null if format is invalid
	 */
	public static Date parseDateTime(String pValue) {
		return parseDateTime(pValue, DATE_TIME_FORMATTER);
	}

	/**
	 * Parse a string date
	 * <br/>
	 * Expected pattern {@link ConvertUtils#DATE_FORMAT} (to be used for returning data to the client)
	 *
	 * @param pValue String date
	 * @return Date, or null if format is invalid
	 */
	public static Date parseDate(String pValue) {
		return parseDate(pValue, DATE_FORMATTER);
	}

	/**
	 * Parse a string date
	 * <br/>
	 * Expected pattern {@link ConvertUtils#DATE_FORMAT_PROPERTY} (to be used for properties)
	 *
	 * @param pDate String date
	 * @return Date, or null if format is invalid
	 */
	public static Date parseDateProperties(String pValue) {
		return parseDate(pValue, DATE_FORMATTER_PROPERTY);
	}

	/**
	 * Parse a string date
	 * <br/>
	 * Expected pattern {@link ConvertUtils#DATE_FORMAT_PROPERTY_SHORT}
	 *
	 * @param pDate String date
	 * @return Date, or null if format is invalid
	 */
	public static Date parseDatePropertiesShort(String pValue) {
		return parseDate(pValue, DATE_FORMATTER_PROPERTY_SHORT);
	}

	/**
	 * Parse a string date
	 * <br/>
	 * Expected pattern {@link ConvertUtils#DATE_FORMATTER_DISPLAY_V1}
	 *
	 * @param pDate String date
	 * @return Date, or null if format is invalid
	 */
	public static Date parseDateDisplayV1(String pValue) {
		return parseDate(pValue, DATE_FORMATTER_DISPLAY_V1);
	}

	/**
	 * Parse a string date
	 * <br/>
	 * Expected pattern {@link ConvertUtils#DATETIME_FORMATTER_DISPLAY_V1}
	 *
	 * @param pDate String date
	 * @return Date, or null if format is invalid
	 */
	public static Date parseDateTimeDisplayV1(String pValue) {
		return parseDateTime(pValue, DATETIME_FORMATTER_DISPLAY_V1);
	}

	private static Date parseDate(String pValue, DateTimeFormatter pFormatter) {
		Date res = null;
		if(StringUtils.isNotBlank(pValue)) {
			try {
				LocalDate date = LocalDate.parse(pValue, pFormatter);

				res = DateUtils.getDate(date.getYear(), date.getMonthValue(), date.getDayOfMonth());
			}
			catch(DateTimeParseException e) {
				// Do nothing
			}
		}
		return res;
	}

	private static Date parseDateTime(String pValue, DateTimeFormatter pFormatter) {
		Date res = null;
		if(StringUtils.isNotBlank(pValue)) {
			try {
				LocalDateTime date = LocalDateTime.parse(pValue, pFormatter);

				res = DateUtils.getDate(date.getYear(), date.getMonthValue(), date.getDayOfMonth(),
						date.getHour(), date.getMinute(), date.getSecond());
			}
			catch(DateTimeParseException e) {
				// Do nothing
			}
		}
		return res;
	}

	/**
	 * Return a string representation of a date
	 * <br/>
	 * Expected pattern {@link ConvertUtils#DATE_TIME_FORMAT} (to be used for returning data to the client, with a time part)
	 *
	 * @param pValue Date value
	 * @return String representation, or null
	 */
	public static String toString(DateTime pValue) {
		return toString(pValue, DATE_TIME_FORMATTER);
	}

	/**
	 * Return a string representation of a date
	 * <br/>
	 * Expected pattern {@link ConvertUtils#DATE_FORMAT} (to be used for returning data to the client)
	 *
	 * @param pValue Date value
	 * @return String representation, or null
	 */
	public static String toString(Date pValue) {
		return toString(pValue, DATE_FORMATTER);
	}

	/**
	 * Return a string representation of a date
	 * <br/>
	 * Expected pattern {@link ConvertUtils#DATE_FORMAT_PROPERTY} (to be used for properties)
	 *
	 * @param pValue Date value
	 * @return String representation, or null
	 */
	public static String toStringProperties(Date pValue) {
		return toString(pValue, DATE_FORMATTER_PROPERTY);
	}

	/**
	 * Return a string representation of a date
	 * <br/>
	 * Expected pattern {@link ConvertUtils#DATE_FORMAT_PROPERTY_SHORT}
	 *
	 * @param pValue Date value
	 * @return String representation, or null
	 */
	public static String toStringPropertiesShort(Date pValue) {
		return toString(pValue, DATE_FORMATTER_PROPERTY_SHORT);
	}

	/**
	 * Return a string representation of a date
	 * <br/>
	 * Expected pattern {@link ConvertUtils#DATE_FORMAT_DISPLAY_V1} (to be used for display or V1)
	 *
	 * @param pValue Date value
	 * @return String representation, or null
	 */
	public static String toStringDisplayV1(Date pValue) {
		return toString(pValue, DATE_FORMATTER_DISPLAY_V1);
	}

	/**
	 * Return a string representation of a date
	 * <br/>
	 * Expected pattern {@link ConvertUtils#DATETIME_FORMAT_DISPLAY_V1} (to be used for display or V1)
	 *
	 * @param pValue DateTime value
	 * @return String representation, or null
	 */
	public static String toStringDisplayV1(DateTime pValue) {
		return toString(pValue, DATETIME_FORMATTER_DISPLAY_V1);
	}

	private static String toString(Date pValue, DateTimeFormatter pFormatter) {

		return pValue != null ? pFormatter.format(LocalDateTime.ofInstant(new Date(pValue.getTime()).toInstant(), ZoneId.systemDefault())) : null;
	}



}
