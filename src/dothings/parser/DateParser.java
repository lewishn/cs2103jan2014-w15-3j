package dothings.parser;
//@author A0099727J

import java.util.ArrayList;
import java.util.Arrays;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class DateParser{
	private static final String DATE_FORMAT_0 = "dd/MM/YYYY";
	private static final String DATE_FORMAT_1 = "dd-MM-YYYY";
	private static final String DATE_FORMAT_2 = "dd.MM.YYYY";
	private static final String DATE_FORMAT_3 = "dd/MMM/YYYY";
	private static final String DATE_FORMAT_4 = "dd-MMM-YYYY";
	private static final String DATE_FORMAT_5 = "dd.MMM.YYYY";
	private static final String DATE_FORMAT_6 = "dd/MM";
	private static final String DATE_FORMAT_7 = "dd-MM";
	private static final String DATE_FORMAT_8 = "dd/MMM";
	private static final String DATE_FORMAT_9 = "dd-MMM";
	private static final String DATE_FORMAT_10 = "E";
	private static final int FORMAT_TYPE_0_INDEX = 6;	//format 0 : full date, with year
	private static final int FORMAT_TYPE_1_INDEX = 10;	//format 1 : with day and month only
	private static final int FORMAT_TYPE_2_INDEX = 11;	//format 2 : day of the week, Friday, etc.
	
	private static final String DATE_TODAY = "today";
	private static final String DATE_TOMORROW = "tomorrow";
	private static final String DATE_YESTERDAY = "yesterday";
	
	private static ArrayList<DateTimeFormatter> dateFormats = 
		new ArrayList<DateTimeFormatter> (Arrays.asList( 
			DateTimeFormat.forPattern(DATE_FORMAT_0), 
			DateTimeFormat.forPattern(DATE_FORMAT_1), 
			DateTimeFormat.forPattern(DATE_FORMAT_2), 	
			DateTimeFormat.forPattern(DATE_FORMAT_3), 
			DateTimeFormat.forPattern(DATE_FORMAT_4), 
			DateTimeFormat.forPattern(DATE_FORMAT_5), 
			DateTimeFormat.forPattern(DATE_FORMAT_6),
			DateTimeFormat.forPattern(DATE_FORMAT_7), 
			DateTimeFormat.forPattern(DATE_FORMAT_8), 
			DateTimeFormat.forPattern(DATE_FORMAT_9), 
			DateTimeFormat.forPattern(DATE_FORMAT_10)
		));

	
	/**
	 * Sets the date to the one from the input.
	 * @param input
	 * @return a DateTime object with the date to the input
	 */
	public static DateTime setDate(String input) {
		int formatType = getDateFormatType(input);
		DateTime date = new DateTime();

		if (formatType == 0) {
			date = parseDateFormat0(input);
		} else if (formatType == 1) {
			date = parseDateFormat1(input, date);
		} else if (formatType == 2) { 
			date = parseDateFormat2(input, date);
		} else if (formatType == 3) {
			date = parseDateFormat3(input);
		}
		
		return date;
	}
	
	/**
	 * Set the date to today's date
	 * @return a DateTime object with the date set to today.
	 */
	public static DateTime setDate() {
		return new DateTime();
	}

	/**
	 * Get the date format type of the input.
	 * returns 0 if date contains day, month and year
	 * returns 1 if date contains day and month only
	 * returns 2 if date is written in English (Tuesday, wed etc)
	 * returns 3 if date is yesterday/today/tomorrow
	 * returns -1 if invalid
	 * @param input
	 * @return the format of the input date
	 */
	private static int getDateFormatType(String input) {
		DateTime date;
		int i = 0;
		for ( ; i < dateFormats.size(); i++) {
			try {
				date = dateFormats.get(i).parseDateTime(input);
				break;
			} catch (IllegalArgumentException e) {
				continue;
			}
		}
		
		if (i < FORMAT_TYPE_0_INDEX) {
			return 0;
		} else if (i < FORMAT_TYPE_1_INDEX) {
			return 1;
		} else if (i < FORMAT_TYPE_2_INDEX) {
			return 2;
		} else if (isFormat3(input)) {
			return 3;
		}
			
		return -1;
	}
	
	/**
	 * Checks if input String is a date with format 3 (yesterday, today, tomorrow)
	 * @param input
	 * @return true if input is format3
	 */
	private static boolean isFormat3(String input) {
		return input.equalsIgnoreCase(DATE_TODAY) || input.equalsIgnoreCase(DATE_TOMORROW) || input.equalsIgnoreCase(DATE_YESTERDAY);
	}
	
	/**
	 * Finds if the input string is a valid date
	 * @param input
	 * @return true if the input string is a valid date
	 */
	public static boolean isDate(String input) {
		int formatType = getDateFormatType(input);
		assert(formatType >= -1 && formatType <=3);
		
		if (formatType == -1) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Checks if two dates are the same day
	 * @param date1
	 * @param date2
	 * @return true if date1 is the same as date 2
	 */
	public static boolean isSameDate(DateTime date1, DateTime date2) {
		assert(date1 != null && date2 != null);
		return (date1.getYear() == date2.getYear()) && (date1.getDayOfYear() == date2.getDayOfYear());
	}
	
	/**
	 * Parse the input string with date format type 0 and returns a
	 * DateTime object with the corresponding date
	 * @param input
	 * @return a DateTime object with the date set to the input date
	 */
	private static DateTime parseDateFormat0(String input){
		DateTime date = null;
		
		for (int i = 0; i < FORMAT_TYPE_0_INDEX; i++) {
			try {
				date = dateFormats.get(i).parseDateTime(input);
			} catch (IllegalArgumentException e) {
				continue;
			}
		}
		
		return date;
	}
	
	/**
	 * Parse the input string with date format type 1 and returns a
	 * DateTime object with the corresponding date
	 * @param input
	 * @return a DateTime object with the date set to the input date
	 */
	private static DateTime parseDateFormat1(String input, DateTime date) {
		int[] dateTokens = splitDayMonth(input);
		int targetDay = dateTokens[0];
		int targetMonth = dateTokens[1];
		int currentDay = date.getDayOfMonth();
		int currentMonth = date.getMonthOfYear();
		
		// Target day to be set has already passed, so set it to next year
		if (targetMonth < currentMonth || (targetMonth == currentMonth && targetDay < currentDay)) {
			date = new DateTime(date.getYear()+1 , targetMonth, targetDay, 0, 0);
		} else {
			date = new DateTime(date.getYear(), targetMonth, targetDay, 0, 0);
		}
		return date;
	}
	
	/**
	 * Parse the input string with date format type 2 and returns a
	 * DateTime object with the corresponding date
	 * @param input
	 * @return a DateTime object with the date set to the input date
	 */
	private static DateTime parseDateFormat2(String input, DateTime date) {
		DateTime tempDate = dateFormats.get(FORMAT_TYPE_2_INDEX - 1).parseDateTime(input);
		int targetDayOfWeek = tempDate.getDayOfWeek();
		int currentDayOfWeek = date.getDayOfWeek();
		
		if (targetDayOfWeek < currentDayOfWeek) {
			date = date.plusWeeks(1);
		} 
		
		date = date.withDayOfWeek(targetDayOfWeek);
		return date;
	}
	
	/**
	 * Parse the input string with date format type 3 and returns a
	 * DateTime object with the corresponding date
	 * @param input
	 * @return a DateTime object with the date set to the input date
	 */
	private static DateTime parseDateFormat3(String input) {
		DateTime today = new DateTime();
		
		if (input.equalsIgnoreCase(DATE_YESTERDAY)) {
			return today.minusDays(1);
		} else if (input.equalsIgnoreCase(DATE_TOMORROW)) {
			return today.plusDays(1);
		}
		
		return today;
	}
	
	/**
	 * Splits the day and month of the input date string into two integers
	 * @param date
	 * @return integer array with the day of the month in index 0 and month of the year in index 1
	 */
	private static int[] splitDayMonth(String date) {
		String[] tokens = new String[2];
		if (date.contains("/")) {
			tokens = date.split("/");
		} else if (date.contains("-")) {
			tokens = date.split("-");
		}
		
		int[] intTokens = new int[tokens.length];
		for (int i = 0; i < tokens.length; i++) {
			try {	
				intTokens[i] = Integer.parseInt(tokens[i]);
			} catch (NumberFormatException e) {
				DateTime tempDate = DateTimeFormat.forPattern("MMM").parseDateTime(tokens[i]);
				intTokens[i] = tempDate.getMonthOfYear();
			}
		}
		
		return intTokens;
	}
}
