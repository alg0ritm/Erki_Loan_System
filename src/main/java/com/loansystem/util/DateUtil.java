package com.loansystem.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang.time.DateUtils;

public class DateUtil {
        public static DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    
	public static int dayDifference(final Calendar from, final Calendar to) {
		Calendar toTruncated = DateUtils.truncate(to, Calendar.DAY_OF_MONTH);
		Calendar fromTruncated = DateUtils.truncate(from, Calendar.DAY_OF_MONTH);
		long millisDifference = toTruncated.getTimeInMillis() - fromTruncated.getTimeInMillis();
		return (int) (millisDifference / DateUtils.MILLIS_PER_DAY);
	}

	public static int dayDifferenceFromNow(Calendar calendar) {
		return dayDifference(Calendar.getInstance(), calendar);
	}

	public static Map<String, String> generateMonthes() {
		Map<String, String> monthes = new HashMap<String, String>();

		DateFormat dateFormat = new SimpleDateFormat("MMMM", Locale.US);
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DATE, calendar.getActualMinimum(Calendar.DATE));
		for (int i = 0; i <= 11; i++) {
			calendar.set(Calendar.MONTH, i);
			monthes.put(String.valueOf(i), dateFormat.format(calendar.getTime()).toLowerCase());
		}
		return monthes;
	}

	/**
	 * Add one day to given date.
	 * 
	 * @param outwardDeparture
	 * @return Date with plus one day.
	 */
	public static Date getDatePlusDay(Date outwardDeparture) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(outwardDeparture);
		cal.add(Calendar.DATE, 1);

		return cal.getTime();
	}

	public static Date getDatePlusDays(Date outwardDeparture, Integer daysToAdd) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(outwardDeparture);
		cal.add(Calendar.DATE, daysToAdd);

		return cal.getTime();
	}

	public static int minuteDifference(final Calendar from, final Calendar to) {
		return minuteDifference(to.getTimeInMillis(), from.getTimeInMillis());
	}

	public static int minuteDifference(final long from, final long to) {
		long millisDifference = to - from;
		return (int) (millisDifference / DateUtils.MILLIS_PER_MINUTE);
	}

	public static int minuteDifferenceFromNow(final Calendar calendar) {
		return minuteDifference(Calendar.getInstance(), calendar);
	}

	public static int secondsDifference(final Calendar from, final Calendar to) {
		long millisDifference = to.getTimeInMillis() - from.getTimeInMillis();
		return (int) (millisDifference / DateUtils.MILLIS_PER_SECOND);
	}

	public static int secondsDifferenceFromNow(final Calendar calendar) {
		return minuteDifference(Calendar.getInstance(), calendar);
	}

	public static String normalizeDate(String dateString) {
		// Formatting date for formatter
		String[] dateInputs = dateString.split("-");
		dateString = dateInputs[0] + "-";
		if (dateInputs[1].length() == 1) {
			dateString += "0";
		}
		dateString += dateInputs[1] + "-";

		if (dateInputs[2].length() == 1) {
			dateString += "0";
		}
		dateString += dateInputs[2];
		return dateString;
	}
}
