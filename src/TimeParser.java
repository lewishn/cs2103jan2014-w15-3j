import java.util.ArrayList;
import java.util.Arrays;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class TimeParser{
	private static final String TIME_FORMAT_0 = "HH:mm";
	private static final String TIME_FORMAT_1 = "HH.mm";
	private static final String TIME_FORMAT_2 = "HHmm";
	private static final String TIME_FORMAT_3 = "hh:mmaa";
	private static final String TIME_FORMAT_4 = "hh.mmaa";
	private static final String TIME_FORMAT_5 = "hhmmaa";
	private static final String TIME_FORMAT_6 = "hhaa";
	private static final String TIME_FORMAT_7 = "haa";
	private static final int FORMAT_SIZE = 8;
	
	private static ArrayList<DateTimeFormatter> timeFormats = 
		new ArrayList<DateTimeFormatter> (Arrays.asList( 
			DateTimeFormat.forPattern(TIME_FORMAT_0), 
			DateTimeFormat.forPattern(TIME_FORMAT_1), 
			DateTimeFormat.forPattern(TIME_FORMAT_2), 	
			DateTimeFormat.forPattern(TIME_FORMAT_3), 
			DateTimeFormat.forPattern(TIME_FORMAT_4), 
			DateTimeFormat.forPattern(TIME_FORMAT_5), 
			DateTimeFormat.forPattern(TIME_FORMAT_6),
			DateTimeFormat.forPattern(TIME_FORMAT_7)
		));
	
	/**
	 * Alters the time component of the input DateTime object with the input time string
	 * @param date
	 * @param input
	 * @return a DateTime object with date and time already set
	 */
	protected static DateTime setTime(DateTime date, String input) {
		if (isValidFormat(input)) {
			date = parseTimeFormat(date, input);
			return date;
		} else {
			return date;
		}
	}

	/**
	 * Checks if the input string is a valid date format
	 * @param input
	 * @return true if valid, false otherwise
	 */
	private static boolean isValidFormat(String input) {
		DateTime date;
		int i = 0;
		for ( ; i < timeFormats.size(); i++) {
			try {
				date = timeFormats.get(i).parseDateTime(input);
				break;
			} catch (IllegalArgumentException e) {
				continue;
			} catch (NullPointerException e) {
				continue;
			}
		}
		
		if (i < FORMAT_SIZE) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Checks wheter the input string is a time
	 * @param input
	 * @return true if string is a time, false otherwise
	 */
	protected static boolean isTime(String input) {
		return isValidFormat(input);
	}
	
	private static DateTime parseTimeFormat(DateTime date, String input){
		DateTime time = null;
		
		for (int i = 0; i < FORMAT_SIZE; i++) {
			try {
				time = timeFormats.get(i).parseDateTime(input);
			} catch (IllegalArgumentException e) {
				continue;
			}
		}
		
		return new DateTime(date.getYear(), date.getMonthOfYear(), date.getDayOfMonth(), time.getHourOfDay(), time.getMinuteOfHour());
	}
	
}
