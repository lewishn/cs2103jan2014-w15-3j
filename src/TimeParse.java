import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class TimeParse{
	private static final String TIME_FORMAT_0 = "HH:mm";
	private static final String TIME_FORMAT_1 = "HHmm";
	private static final String TIME_FORMAT_2 = "hh:mmaa";
	private static final String TIME_FORMAT_3 = "hh:mm aa";
	private static final String TIME_FORMAT_4 = "hhmmaa";
	private static final String TIME_FORMAT_5 = "hhmm aa";
	private static final String TIME_FORMAT_6 = "HH";
	private static final String TIME_FORMAT_7 = "hhaa";
	private static final String TIME_FORMAT_8 = "hh aa";
	private static final int FORMAT_SIZE = 9;
	
	private static ArrayList<DateTimeFormatter> timeFormats = 
		new ArrayList<DateTimeFormatter> (Arrays.asList( 
			DateTimeFormat.forPattern(TIME_FORMAT_0), 
			DateTimeFormat.forPattern(TIME_FORMAT_1), 
			DateTimeFormat.forPattern(TIME_FORMAT_2), 	
			DateTimeFormat.forPattern(TIME_FORMAT_3), 
			DateTimeFormat.forPattern(TIME_FORMAT_4), 
			DateTimeFormat.forPattern(TIME_FORMAT_5), 
			DateTimeFormat.forPattern(TIME_FORMAT_6),
			DateTimeFormat.forPattern(TIME_FORMAT_7),
			DateTimeFormat.forPattern(TIME_FORMAT_8)
		));
	
	private static void setTime(DateTime date, String input) {
		if (isValidFormat(input)) {
			date = parseTimeFormat(date, input);
			System.out.println(date);
		} else {
			System.out.println("error");
		}
	}

	private static boolean isValidFormat(String input) {
		DateTime date;
		int i = 0;
		for ( ; i < timeFormats.size(); i++) {
			try {
				date = timeFormats.get(i).parseDateTime(input);
				break;
			} catch (IllegalArgumentException e) {
				continue;
			}
		}
		
		if (i < FORMAT_SIZE) {
			return true;
		} else {
			return false;
		}
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
