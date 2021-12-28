package com.rcibanque.rcidirect.services.core.utils;

import static org.assertj.core.api.Assertions.assertThat;

import java.text.SimpleDateFormat;
import java.time.Period;
import java.util.Date;

import org.junit.jupiter.api.Test;

import com.rcibanque.rcidirect.services.core.domain.ValidityPeriod;

public class DateUtilsTest {

	@Test
	public void testIsDateWithinRange() {

		assertThat(isDateWithinRange(null, null, null, null)).isFalse();

		Date currentDate = ConvertUtils.parseDate("2018-11-09");

		// MIN: Tested date before current date

		Date testedDate = ConvertUtils.parseDate("2018-10-09");

		assertThat(isDateWithinRange(currentDate, testedDate, null, null)).isTrue(); 		// Min = no limit

		assertThat(isDateWithinRange(currentDate, testedDate, "P-1M", null)).isTrue(); 		// Min = 2018-10-09
		assertThat(isDateWithinRange(currentDate, testedDate, "P-0M", null)).isFalse(); 	// Min = 2018-11-09

		assertThat(isDateWithinRange(currentDate, testedDate, "P-31D", null)).isTrue(); 	// Min = 2018-10-09
		assertThat(isDateWithinRange(currentDate, testedDate, "P-30D", null)).isFalse();	// Min = 2018-10-10

		// MAX: Tested date after current date

		testedDate = ConvertUtils.parseDate("2018-12-09");

		assertThat(isDateWithinRange(currentDate, testedDate, null, null)).isTrue(); 		// Max = no limit

		assertThat(isDateWithinRange(currentDate, testedDate, null, "P1M")).isTrue(); 		// Max = 2018-12-09
		assertThat(isDateWithinRange(currentDate, testedDate, null, "P0M")).isFalse(); 		// Max = 2018-11-09

		assertThat(isDateWithinRange(currentDate, testedDate, null, "P30D")).isTrue();		// Max = 2018-12-09
		assertThat(isDateWithinRange(currentDate, testedDate, null, "P29D")).isFalse();		// Max = 2018-12-08

		// MIN/MAX: Tested date before/after current date

		testedDate = ConvertUtils.parseDate("2018-11-09");

		assertThat(isDateWithinRange(currentDate, testedDate, "P-1M", "P1M")).isTrue(); 	// Min = 2018-10-09 / Max = 2018-12-09

		testedDate = ConvertUtils.parseDate("2018-10-09");

		assertThat(isDateWithinRange(currentDate, testedDate, "P-1M", "P1M")).isTrue(); 	// Min = 2018-10-09 / Max = 2018-12-09

		testedDate = ConvertUtils.parseDate("2018-10-08");

		assertThat(isDateWithinRange(currentDate, testedDate, "P-1M", "P1M")).isFalse(); 	// Min = 2018-10-09 / Max = 2018-12-09

		testedDate = ConvertUtils.parseDate("2018-12-09");

		assertThat(isDateWithinRange(currentDate, testedDate, "P-1M", "P1M")).isTrue(); 	// Min = 2018-10-09 / Max = 2018-12-09

		testedDate = ConvertUtils.parseDate("2018-12-10");

		assertThat(isDateWithinRange(currentDate, testedDate, "P-1M", "P1M")).isFalse(); 	// Min = 2018-10-09 / Max = 2018-12-09
	}

	private boolean isDateWithinRange(Date pCurrentDate, Date pTestedDate, String pFrom, String pTo) {

		ValidityPeriod validityPeriod = new ValidityPeriod(pFrom == null ? null : Period.parse(pFrom), pTo == null ? null : Period.parse(pTo));
		return DateUtils.isDateWithinRange(pTestedDate, DateUtils.getDateRange(pCurrentDate, validityPeriod));
	}


	@Test
	public void testGetDatePart() {

		assertThat(2016).isEqualTo(DateUtils.getYear(ConvertUtils.parseDate("2016-07-31")).intValue());
		assertThat(7).isEqualTo(DateUtils.getMonth(ConvertUtils.parseDate("2016-07-31")).intValue());
		assertThat(31).isEqualTo(DateUtils.getDay(ConvertUtils.parseDate("2016-07-31")).intValue());
	}


	@Test
	public void testGetPeriod() {

		Period p = null;

		// Null
		p = DateUtils.getPeriodBetweenDates(null, null);
		assertThat(p).isNull();

		p = DateUtils.getPeriodBetweenDates(ConvertUtils.parseDate("2016-01-01"), null);
		assertThat(p).isNull();

		p = DateUtils.getPeriodBetweenDates(null, ConvertUtils.parseDate("2016-01-01"));
		assertThat(p).isNull();

		// Same date
		p = DateUtils.getPeriodBetweenDates(ConvertUtils.parseDate("2016-01-01"), ConvertUtils.parseDate("2016-01-01"));
		assertThat(p).isNotNull();
		assertThat(0).isEqualTo(p.getYears());
		assertThat(0).isEqualTo(p.getMonths());
		assertThat(0).isEqualTo(p.getDays());

		//  1 year 1 month 1 day
		p = DateUtils.getPeriodBetweenDates(ConvertUtils.parseDate("2016-01-01"), ConvertUtils.parseDate("2017-02-02"));
		assertThat(p).isNotNull();
		assertThat(1).isEqualTo(p.getYears());
		assertThat(1).isEqualTo(p.getMonths());
		assertThat(1).isEqualTo(p.getDays());
	}


	@Test
	public void testCompareYears() {

		try {
			DateUtils.compareYears(null, 0);

			assertThat(true).isFalse(); // Invalid call not caught
		}
		catch(IllegalArgumentException e) {
			// OK
		}

		// no difference
		assertThat(0).isEqualTo(DateUtils.compareYears(DateUtils.getPeriodBetweenDates(ConvertUtils.parseDate("2016-01-01"), ConvertUtils.parseDate("2016-01-01")), 0));

		// 2 years difference
		assertThat(0).isEqualTo(DateUtils.compareYears(DateUtils.getPeriodBetweenDates(ConvertUtils.parseDate("2016-01-01"), ConvertUtils.parseDate("2018-01-01")), 2));
		assertThat(0).isEqualTo(DateUtils.compareYears(DateUtils.getPeriodBetweenDates(ConvertUtils.parseDate("2018-01-01"), ConvertUtils.parseDate("2016-01-01")), -2));

		// less that 2 years difference
		assertThat(-1).isEqualTo(DateUtils.compareYears(DateUtils.getPeriodBetweenDates(ConvertUtils.parseDate("2016-01-01"), ConvertUtils.parseDate("2017-07-01")), 2));
		assertThat(-1).isEqualTo(DateUtils.compareYears(DateUtils.getPeriodBetweenDates(ConvertUtils.parseDate("2017-07-01"), ConvertUtils.parseDate("2016-01-01")), -2));

		// more that 2 years difference
		assertThat(1).isEqualTo(DateUtils.compareYears(DateUtils.getPeriodBetweenDates(ConvertUtils.parseDate("2016-01-01"), ConvertUtils.parseDate("2018-07-01")), 2));
		assertThat(1).isEqualTo(DateUtils.compareYears(DateUtils.getPeriodBetweenDates(ConvertUtils.parseDate("2018-07-01"), ConvertUtils.parseDate("2016-01-01")), -2));
	}

	@Test
	public void testCompareMonths() {

		try {
			DateUtils.compareMonths(null, 0);

			assertThat(true).isFalse(); // Invalid call not caught
		}
		catch(IllegalArgumentException e) {
			// OK
		}

		// no difference
		assertThat(0).isEqualTo(DateUtils.compareMonths(DateUtils.getPeriodBetweenDates(ConvertUtils.parseDate("2016-01-01"), ConvertUtils.parseDate("2016-01-01")), 0));

		// 2 months difference
		assertThat(0).isEqualTo(DateUtils.compareMonths(DateUtils.getPeriodBetweenDates(ConvertUtils.parseDate("2016-01-01"), ConvertUtils.parseDate("2016-03-01")), 2));
		assertThat(0).isEqualTo(DateUtils.compareMonths(DateUtils.getPeriodBetweenDates(ConvertUtils.parseDate("2016-03-01"), ConvertUtils.parseDate("2016-01-01")), -2));

		// less that 2 months difference
		assertThat(-1).isEqualTo(DateUtils.compareMonths(DateUtils.getPeriodBetweenDates(ConvertUtils.parseDate("2016-01-01"), ConvertUtils.parseDate("2016-02-20")), 2));
		assertThat(-1).isEqualTo(DateUtils.compareMonths(DateUtils.getPeriodBetweenDates(ConvertUtils.parseDate("2016-02-20"), ConvertUtils.parseDate("2016-01-01")), -2));

		// more that 2 months difference
		assertThat(1).isEqualTo(DateUtils.compareMonths(DateUtils.getPeriodBetweenDates(ConvertUtils.parseDate("2016-01-01"), ConvertUtils.parseDate("2016-03-02")), 2));
		assertThat(1).isEqualTo(DateUtils.compareMonths(DateUtils.getPeriodBetweenDates(ConvertUtils.parseDate("2016-03-02"), ConvertUtils.parseDate("2016-01-01")), -2));
	}


	@Test
	public void testDaysBetween() {

		assertThat(1).isEqualTo(DateUtils.getDaysBetweenDates(ConvertUtils.parseDate("2016-01-01"), ConvertUtils.parseDate("2016-01-02")));
		assertThat(1).isEqualTo(DateUtils.getDaysBetweenDates(ConvertUtils.parseDate("2016-12-31"), ConvertUtils.parseDate("2017-01-01")));
		assertThat(31+29).isEqualTo(DateUtils.getDaysBetweenDates(ConvertUtils.parseDate("2016-01-01"), ConvertUtils.parseDate("2016-03-01")));
		assertThat(366).isEqualTo(DateUtils.getDaysBetweenDates(ConvertUtils.parseDate("2016-01-01"), ConvertUtils.parseDate("2017-01-01")));
	}

	@Test
	public void testMonthsBetween() {

		assertThat(1).isEqualTo(DateUtils.getMonthsBetweenDates(ConvertUtils.parseDate("2016-01-01"), ConvertUtils.parseDate("2016-02-01")));
		assertThat(0).isEqualTo(DateUtils.getMonthsBetweenDates(ConvertUtils.parseDate("2016-12-31"), ConvertUtils.parseDate("2017-01-01")));
		assertThat(12).isEqualTo(DateUtils.getMonthsBetweenDates(ConvertUtils.parseDate("2016-01-01"), ConvertUtils.parseDate("2017-01-01")));
	}


	@Test
	public void addDaysTest() {

		// Within same month
		Date updatedDate = DateUtils.addDays(ConvertUtils.parseDate("2016-07-10"), 5);
		assertThat(2016).isEqualTo(DateUtils.getYear(updatedDate).intValue());
		assertThat(7).isEqualTo(DateUtils.getMonth(updatedDate).intValue());
		assertThat(15).isEqualTo(DateUtils.getDay(updatedDate).intValue());

		updatedDate = DateUtils.addDays(ConvertUtils.parseDate("2016-07-10"), -5);
		assertThat(2016).isEqualTo(DateUtils.getYear(updatedDate).intValue());
		assertThat(7).isEqualTo(DateUtils.getMonth(updatedDate).intValue());
		assertThat(5).isEqualTo(DateUtils.getDay(updatedDate).intValue());

		// Changing month
		updatedDate = DateUtils.addDays(ConvertUtils.parseDate("2016-07-01"), 31);
		assertThat(2016).isEqualTo(DateUtils.getYear(updatedDate).intValue());
		assertThat(8).isEqualTo(DateUtils.getMonth(updatedDate).intValue());
		assertThat(1).isEqualTo(DateUtils.getDay(updatedDate).intValue());

		updatedDate = DateUtils.addDays(ConvertUtils.parseDate("2016-07-01"), -1);
		assertThat(2016).isEqualTo(DateUtils.getYear(updatedDate).intValue());
		assertThat(6).isEqualTo(DateUtils.getMonth(updatedDate).intValue());
		assertThat(30).isEqualTo(DateUtils.getDay(updatedDate).intValue());

		// Changing year
		updatedDate = DateUtils.addDays(ConvertUtils.parseDate("2016-12-31"), 1);
		assertThat(2017).isEqualTo(DateUtils.getYear(updatedDate).intValue());
		assertThat(1).isEqualTo(DateUtils.getMonth(updatedDate).intValue());
		assertThat(1).isEqualTo(DateUtils.getDay(updatedDate).intValue());

		updatedDate = DateUtils.addDays(ConvertUtils.parseDate("2016-01-01"), -1);
		assertThat(2015).isEqualTo(DateUtils.getYear(updatedDate).intValue());
		assertThat(12).isEqualTo(DateUtils.getMonth(updatedDate).intValue());
		assertThat(31).isEqualTo(DateUtils.getDay(updatedDate).intValue());
	}


	@Test
	public void addMonthsTest() {

		// Within same year
		Date updatedDate = DateUtils.addMonths(ConvertUtils.parseDate("2016-07-01"), 3);
		assertThat(2016).isEqualTo(DateUtils.getYear(updatedDate).intValue());
		assertThat(10).isEqualTo(DateUtils.getMonth(updatedDate).intValue());
		assertThat(1).isEqualTo(DateUtils.getDay(updatedDate).intValue());

		updatedDate = DateUtils.addMonths(ConvertUtils.parseDate("2016-07-01"), -3);
		assertThat(2016).isEqualTo(DateUtils.getYear(updatedDate).intValue());
		assertThat(4).isEqualTo(DateUtils.getMonth(updatedDate).intValue());
		assertThat(1).isEqualTo(DateUtils.getDay(updatedDate).intValue());

		// Changing year
		updatedDate = DateUtils.addMonths(ConvertUtils.parseDate("2016-12-31"), 2);
		assertThat(2017).isEqualTo(DateUtils.getYear(updatedDate).intValue());
		assertThat(2).isEqualTo(DateUtils.getMonth(updatedDate).intValue());
		assertThat(28).isEqualTo(DateUtils.getDay(updatedDate).intValue());

		updatedDate = DateUtils.addMonths(ConvertUtils.parseDate("2016-01-31"), -2);
		assertThat(2015).isEqualTo(DateUtils.getYear(updatedDate).intValue());
		assertThat(11).isEqualTo(DateUtils.getMonth(updatedDate).intValue());
		assertThat(30).isEqualTo(DateUtils.getDay(updatedDate).intValue());
	}

	@Test
	public void shouldParseZonedDateTime() throws Exception{
		// given
		SimpleDateFormat zonedDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		String zonedDateTimeOne = "2007-12-03T10:15:30+01:00";
		String zonedDateTimeTwo = "2007-12-03T10:15:30+0100";

		// when
		Date resultOne = DateUtils.parseFromZonedDateTime(zonedDateTimeOne);
		String formatedResultOne = zonedDateFormat.format(resultOne);

		Date resultTwo = DateUtils.parseFromZonedDateTime(zonedDateTimeTwo);
		String formatedResultTwo = zonedDateFormat.format(resultTwo);

		// then
		assertThat(formatedResultOne).isEqualTo("2007-12-03T09:15:30");
		assertThat(formatedResultTwo).isEqualTo("2007-12-03T09:15:30");
	}


	@Test
	public void shouldAddTime() throws Exception {

		// Given
		Date date1 = ConvertUtils.parseDate("2020-04-16");

		// When
		int hoursAdded = 24;
		int minutesAdded = 35;
		Date date2 = DateUtils.addTime(date1, hoursAdded, minutesAdded);

		// Then
		assertThat(date2.getTime() - date1.getTime()).isEqualTo((hoursAdded*60+minutesAdded)*60*1000);
	}

	@Test
	public void shouldNotAddTime() throws Exception {

		// When
		Date date = DateUtils.addTime(null, 0, 0);

		// Then
		assertThat(date).isNull();
	}


	@Test
	public void shouldGetDurationBetweenDates() throws Exception {

		// Given
		Date date1 = ConvertUtils.parseDateTime("2020-04-10 10:30:10");
		Date date2 = ConvertUtils.parseDateTime("2020-04-15 15:35:15");

		// When
		String duration = DateUtils.getDurationBetweenDates(date1, date2);

		// Then
		assertThat(duration).isEqualTo("5d 5h 5m");
	}

	@Test
	public void shouldNotGetDurationBetweenDates() throws Exception {

		// When
		String duration1 = DateUtils.getDurationBetweenDates(null, null);
		String duration2 = DateUtils.getDurationBetweenDates(new Date(), null);
		String duration3 = DateUtils.getDurationBetweenDates(null, new Date());

		// Then
		assertThat(duration1).isNull();
		assertThat(duration2).isNull();
		assertThat(duration3).isNull();
	}

}
