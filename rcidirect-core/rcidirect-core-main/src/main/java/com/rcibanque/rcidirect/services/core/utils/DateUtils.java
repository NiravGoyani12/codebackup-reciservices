package com.rcibanque.rcidirect.services.core.utils;

import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.time.DurationFormatUtils;
import org.springframework.util.Assert;

import com.rcibanque.rcidirect.services.core.domain.DateRange;
import com.rcibanque.rcidirect.services.core.domain.DateTime;
import com.rcibanque.rcidirect.services.core.domain.ValidityPeriod;

public class DateUtils {

	public static final String ZONE_PREFIX = "+";
	public static final String TIME_SEPARATOR = ":";

	private DateUtils() {}

	/**
	 * Parse the zoned date time given a String input
	 * into a {@link Date} object.<br />
	 *
	 * Format the zoned part accordingly if required also.
	 *
	 * @param zonedDateTime
	 * @return
	 */
	public static Date parseFromZonedDateTime(String zonedDateTime){
		String zonedDateTimeToParse = zonedDateTime;
		Pattern zoneDataPattern = Pattern.compile("\\+\\d{4}");
		Matcher zoneDataMatcher = zoneDataPattern.matcher(zonedDateTime);

		if(zoneDataMatcher.find()){
			String zonedData = zoneDataMatcher.group();
			zonedDateTimeToParse = zonedDateTime.substring(0, zonedDateTime.length() - 5);

			int characterCounter = 0;
			for(Character character: zonedData.toCharArray()){
				if(characterCounter == 3){
					zonedDateTimeToParse += TIME_SEPARATOR;
				}

				zonedDateTimeToParse += character;
				characterCounter++;
			}
		}

		return Date.from(java.time.ZonedDateTime.parse(zonedDateTimeToParse).toInstant());
	}


	/**
	 * @param pTestedDate Tested date
	 * @param pDateRange Date range
	 *
	 * @return True if tested date is within the range
	 *
	 * @see #getDateRange(Date, ValidityPeriod)
	 */
	public static boolean isDateWithinRange(Date pTestedDate, DateRange pDateRange) {

		boolean res = false;

		if(pTestedDate != null && pDateRange != null) {

			Date testedDate = DateUtils.removeTimePart(pTestedDate);

			res = (pDateRange.getFrom() == null || ! testedDate.before(pDateRange.getFrom()))
					&& (pDateRange.getTo() == null || ! testedDate.after(pDateRange.getTo()));
		}

		return res;
	}

	/**
	 * @param pCurrentDate Current date
	 * @param pValidityPeriod Validity period
	 *
	 * @return Date range
	 *
	 * @see #isDateWithinRange(Date, DateRange)
	 */
	public static DateRange getDateRange(Date pCurrentDate, ValidityPeriod pValidityPeriod) {

		DateRange res = null;

		if(pCurrentDate != null && pValidityPeriod != null) {

			Date currentDate = DateUtils.removeTimePart(pCurrentDate);

			Date validityStartDate = null;
			if(pValidityPeriod.getFrom() != null) {
				validityStartDate = addPeriod(currentDate, pValidityPeriod.getFrom());
			}

			Date validityEndDate = null;
			if(pValidityPeriod.getTo() != null) {
				validityEndDate = addPeriod(currentDate, pValidityPeriod.getTo());
			}

			res = new DateRange(validityStartDate, validityEndDate);
		}

		return res;
	}


	/**
	 * Remove the time part of a date
	 *
	 * @param pDate Input date
	 * @return Date with time part removed (set at 0)
	 */
	public static Date removeTimePart(Date pDate) {

		Date res = null;

		if(pDate != null) {
			Calendar cal = Calendar.getInstance();

			cal.setTime(pDate);
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);

			res = cal.getTime();
		}

		return res;
	}


	/**
	 * Add/remove hours and minutes to a date
	 *
	 * @param pDate Input date
	 * @param pHours Number of hours to add/remove, or null
	 * @param pMinutes Number of minutes to add/remove, or null
	 * @return Updated date
	 */
	public static Date addTime(Date pDate, Integer pHours, Integer pMinutes) {
		Date res = null;

		if(pDate != null) {

			Calendar cal = Calendar.getInstance();
			cal.setTime(pDate);

			if(pHours != null) {
				cal.set(Calendar.HOUR, cal.get(Calendar.HOUR) + pHours);
			}
			if(pMinutes != null) {
				cal.set(Calendar.MINUTE, cal.get(Calendar.MINUTE) + pMinutes);
			}

			res = cal.getTime();
		}

		return res;
	}


	/**
	 * @param pStartDate Start date
	 * @param pEndDate End date
	 * @return Number of days between start and end dates
	 * @see {@link DateUtils#getPeriodBetweenDates(Date, Date)}
	 */
	public static int getDaysBetweenDates(Date pStartDate, Date pEndDate) {

		return (int) ChronoUnit.DAYS.between(getLocalDate(pStartDate), getLocalDate(pEndDate));
	}

	/**
	 * @param pStartDate Start date
	 * @param pEndDate End date
	 * @return Number of months between start and end dates
	 * @see {@link DateUtils#getPeriodBetweenDates(Date, Date)}
	 */
	public static int getMonthsBetweenDates(Date pStartDate, Date pEndDate) {

		return (int) ChronoUnit.MONTHS.between(getLocalDate(pStartDate), getLocalDate(pEndDate));
	}


	/**
	 * This method gives difference in time between start date and end date in days hours minutes format for example 1d 1h 1m
	 * @param pStartDate Start date
	 * @param pEndDate End date
	 * @return Duration in 1d 1h 1m format
	 */
	public static String getDurationBetweenDates(Date pStartDate, Date pEndDate) {

		String res = null;

		if(pStartDate != null && pEndDate != null) {

			res = DurationFormatUtils.formatDuration(
					Math.abs(pStartDate.getTime() - pEndDate.getTime()),
					"d'd 'H'h 'm'm'");
		}

		return res;
	}

	/**
	 * @param pStartDate Start date
	 * @param pEndDate End date
	 * @return Period between start and end dates
	 */
	public static Period getPeriodBetweenDates(Date pStartDate, Date pEndDate) {

		Period res = null;

		if(pStartDate != null && pEndDate != null) {

			res = Period.between(getLocalDate(pStartDate), getLocalDate(pEndDate));
		}

		return res;
	}

	/**
	 * @param pPeriod Period
	 * @param pNbYears Number of years
	 * @return -1, 0 or 1, depending on whether period is lower, equal or greater than the number of years
	 */
	public static int compareYears(Period pPeriod, int pNbYears) {
		Assert.notNull(pPeriod, "Period argument is null");

		Period normalizedPeriod = pPeriod.normalized(); // 25 months = 2 years 1 month

		int periodNbYears = normalizedPeriod.getYears();

		if(isLower(periodNbYears, pNbYears)) {
			return -1;
		}
		else if(isGreater(periodNbYears, pNbYears)) {
			return 1;
		}
		else {
			return compareMonths(normalizedPeriod, pNbYears * 12);
		}
	}

	/**
	 * @param pPeriod Period
	 * @param pNbMonths Number of months
	 * @return -1, 0 or 1, depending on whether period is lower, equal or greater than the number of months
	 */
	public static int compareMonths(Period pPeriod, int pNbMonths) {
		Assert.notNull(pPeriod, "Period argument is null");

		Period normalizedPeriod = pPeriod.normalized(); // 45 days = 1 months +/- 15 days

		int periodNbMonths = normalizedPeriod.getYears() * 12 + normalizedPeriod.getMonths();

		if(isLower(periodNbMonths, pNbMonths)) {
			return -1;
		}
		else if(isGreater(periodNbMonths, pNbMonths)) {
			return 1;
		}
		else {
			return compareDays(normalizedPeriod);
		}
	}

	private static int compareDays(Period pNormalizedPeriod) {

		boolean negative = pNormalizedPeriod.isNegative();
		int nbDays = pNormalizedPeriod.getDays();

		if((! negative && isLower(nbDays, 0)) || (negative && isGreater(nbDays, 0))) {
			return -1;
		}
		else if((! negative && isGreater(nbDays, 0)) || (negative && isLower(nbDays, 0))) {
			return 1;
		}
		else {
			return 0;
		}
	}

	private static boolean isLower(int pValue, int pExpected) {

		return (pValue >= 0 && pExpected >= 0 && pValue < pExpected)
				|| (pValue < 0 && pExpected < 0 && pValue > pExpected)
				|| (pValue < 0 && pExpected >= 0);
	}

	private static boolean isGreater(int pValue, int pExpected) {

		return (pValue >= 0 && pExpected >= 0 && pValue > pExpected)
				|| (pValue < 0 && pExpected < 0 && pValue < pExpected)
				|| (pValue > 0 && pExpected <= 0);
	}

	public static LocalDate getLocalDate(Date pDate) {

		Calendar c = Calendar.getInstance();
		c.setTime(pDate);

		return LocalDate.of(
				c.get(Calendar.YEAR),
				c.get(Calendar.MONTH) + 1,
				c.get(Calendar.DAY_OF_MONTH));
	}


	/**
	 * @param pDate Input date
	 * @return Year part of the date
	 */
	public static Integer getYear(Date pDate) {
		return getDatePart(pDate, Calendar.YEAR);
	}

	/**
	 * @param pDate Input date
	 * @return Month part of the date, <b>starting at 1 for January</b> ({@link Calendar#JANUARY} = 0)
	 */
	public static Integer getMonth(Date pDate) {
		return getDatePart(pDate, Calendar.MONTH) + 1;
	}

	/**
	 * @param pDate Input date
	 * @return Day of month part of the date
	 */
	public static Integer getDay(Date pDate) {
		return getDatePart(pDate, Calendar.DAY_OF_MONTH);
	}

	private static Integer getDatePart(Date pDate, int pField) {
		Calendar c = Calendar.getInstance();
		c.setTime(pDate);
		return c.get(pField);
	}


	/**
	 * @param pDate Date
	 * @param pPeriod Period (can represent a negative period)
	 * @return Date updated to reflect period
	 */
	public static Date addPeriod(Date pDate, Period pPeriod) {

		Date res = pDate;

		res = DateUtils.addYears(res, pPeriod.getYears());
		res = DateUtils.addMonths(res, pPeriod.getMonths());
		res = DateUtils.addDays(res, pPeriod.getDays());

		return res;
	}

	/**
	 * @param pDate Original date
	 * @param pYears Number of years to add
	 * @return Updated date
	 */
	public static Date addYears(Date pDate, Integer pYears) {

		return org.apache.commons.lang3.time.DateUtils.addYears(pDate, pYears);
	}

	/**
	 * @param pDate Original date
	 * @param pMonths Number of months to add
	 * @return Updated date
	 */
	public static Date addMonths(Date pDate, Integer pMonths) {

		return org.apache.commons.lang3.time.DateUtils.addMonths(pDate, pMonths);
	}

	/**
	 * @param pDate Original date
	 * @param pMonths Number of days to add
	 * @return Updated date
	 */
	public static Date addDays(Date pDate, Integer pDays) {

		return org.apache.commons.lang3.time.DateUtils.addDays(pDate, pDays);
	}


	/**
	 * @param pYear Year
	 * @param pMonth Month (starting at 1 for January)
	 * @param pDay Day of month
	 * @return {@link Date}
	 */
	public static Date getDate(int pYear, int pMonth, int pDay) {
		return new Date(getDate(pYear, pMonth, pDay, 0, 0, 0).getTime());
	}

	/**
	 * @param pYear Year
	 * @param pMonth Month (starting at 1 for January)
	 * @param pDay Day of month
	 * @param pHour Hour
	 * @param pMinute Minute
	 * @param pSecond Second
	 * @return {@link DateTime}
	 */
	public static DateTime getDate(int pYear, int pMonth, int pDay, int pHour, int pMinute, int pSecond) {
		Date res;
		Calendar c = Calendar.getInstance();
		c.set(pYear, pMonth-1, pDay, pHour, pMinute, pSecond);
		c.set(Calendar.MILLISECOND, 0);
		res = c.getTime();
		return new DateTime(res);
	}


}
