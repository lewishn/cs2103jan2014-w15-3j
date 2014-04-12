//@author A0099727
package dothings.parser;
import java.util.ArrayList;

import dothings.logic.Task;
import dothings.storage.FileManager;

public class CommandParser {
	private static final String ESCAPE_CHARACTER = "\\";
	private static final String ALIAS_IDENTIFIER = "alias:";
	private static final String WHITESPACE = "\\s+";
	private static final String timeIdentifier = "at @ by from for this to on before until end start - , and";
	private static final String LOG_IS_INPUT_VALID = "CommandParser.isInputValid(%s, %d) : %b";
	
	/**
	 * Returns the command type portion of the string
	 * @param userInput
	 * @return command type portion of the input
	 */
	public static String getUserCommandType(String userInput) {
		assert(userInput != null);
		
		String[] tokens = userInput.split(WHITESPACE);
		return tokens[0];
	}

	/**
	 * Returns the description portion of the string
	 * @param userInput
	 * @return description portion of the input
	 */
	public static String getUserCommandDesc(String userInput) {
		assert(userInput != null);
		
		String[] tokens = userInput.split(WHITESPACE, 2);
		if (tokens.length < 2) {
			return null;
		}
		return tokens[1];
	}
	
	/**
	 * Finds whether the command input by the user is valid,
	 * with at least two words in length.
	 * @param userInput
	 * @return true if the command is at least 2 words long, false otherwise
	 */
	public static boolean isInputValid(String userInput, int length) {
		if (userInput == null || userInput.length() == 0 || userInput.equals("")) {
			return false;
		}
		
		String[] tokens = userInput.split(WHITESPACE);
		boolean isValid = (tokens.length >= length);
		FileManager.log(String.format(LOG_IS_INPUT_VALID, userInput, length, isValid));
		
		return isValid;
	}
	
	/**
	 * Returns an array of string of length 4 containing the start and end
	 * dates and times of the input. Index 0 and 1 contains the start date
	 * and time respectively. Index 2 and 3 contains the end date and time.
	 * @param input
	 * @return a String array containing start and end date/times
	 */
	public static String[] getTaskFields(ArrayList<String> input) {
		String[] fields = new String[Task.TASK_FIELD_SIZE];
		fields = getDateFields(input, fields);
		fields = getTimeFields(input, fields);
		return fields;
	}
	
	/**
	 * Returns an array of string of length 4 containing the start and end
	 * dates of the input at index 0 and 2 of the array respectively.
	 * @param input
	 * @param fields
	 * @return a String array containing the start and end date
	 */
	private static String[] getDateFields(ArrayList<String> input, String[] fields) {
		for (int i = 0; i < input.size(); i++) {
			if (DateParser.isDate(input.get(i))) {
				if (fields[Task.START_DATE] == null) {
					fields[Task.START_DATE] = input.get(i);
				} else if (fields[Task.END_DATE] == null) {
					fields[Task.END_DATE] = input.get(i);
					break;
				}
			}
		}
		
		return fields;
	}
	
	/**
	 * Returns an array of string of length 4 containing the start and end
	 * times of the input at index 1 and 3 of the array respectively.
	 * @param input
	 * @param fields
	 * @return a String array containing the start and end date
	 */
	private static String[] getTimeFields(ArrayList<String> input, String[] fields) {
		for (int i = 0; i < input.size(); i++) {
			if (TimeParser.isTime(input.get(i))) {
				if (fields[Task.START_TIME] == null) {
					fields[Task.START_TIME] = input.get(i);
				} else if (fields[Task.END_TIME] == null) {
					fields[Task.END_TIME] = input.get(i);
					break;
				}
			}
		}
		
		return fields;
	}
	
	/**
	 * Removes date and time from the string
	 * @param s
	 * @return String without date and time
	 */
	public static String removeDateTimeFromString(String s) {
		String[] tokens = s.split(WHITESPACE);
		
		for(int i = 0; i < tokens.length; i++) {
			if (DateParser.isDate(tokens[i]) || TimeParser.isTime(tokens[i])) {
				int j = i - 1;
				while (j >= 0 && isDateTimeIdentifier(tokens[j])) {
					tokens[j--] = null;
				}
				tokens[i] = null;
			}
		}
		
		return arrayToString(tokens);
	}
	
	/**
	 * Removes all occurrences of "alias:<string>" and escape character "\" from the string
	 * @param desc
	 * @return String with alias and escape character removed
	 */
	public static String removeAliasAndEscapeChar(String desc) {
		String[] tokens = desc.split(WHITESPACE);
		for (int i = 0; i < tokens.length; i++) {
			
			if (tokens[i].startsWith(ESCAPE_CHARACTER)) {
				tokens[i] = tokens[i].substring(1);
			}
			if (tokens[i].contains(ALIAS_IDENTIFIER)) {
				tokens[i] = null;
			}	
		}
		
		return arrayToString(tokens);
	}
	
	/**
	 * Get the first occurrence of an alias in the string
	 * @param desc
	 * @return
	 */
	public static String getAliasFromDescription(String desc) {
		String[] tokens = desc.split(WHITESPACE);
		for (int i = 0; i < tokens.length; i++) {
			if (tokens[i].contains(ALIAS_IDENTIFIER)) {
				return tokens[i].substring(ALIAS_IDENTIFIER.length());
			}
		}
		
		return null;
	}
	
	/**
	 * Checks if the string is a time identifier
	 * @param s
	 * @return true if s is a time identifier
	 */
	private static boolean isDateTimeIdentifier(String s) {
		String[] identifierList = timeIdentifier.split(" ");
		
		if (s == null) {
			return false;
		}
		
		for(String identifier: identifierList) {
			if (s.equals(identifier)) return true;
		}
		
		return false;
	}
	
	/**
	 * Converts a String array to a sentence
	 * @param s
	 * @return array s as String sentence
	 */
	protected static String arrayToString(String[] s) {
		assert(s != null);
		
		String r = "";
		for (int i = 0; i < s.length; i++) {
			if (s[i] == null) continue;
			if (i == s.length) {
				r = r + s[i];
			} else {
				r = r + s[i] + " ";
			}
		}
		
		return r.trim();
	}
	
	/**
	 * Checks if the String is a valid integer
	 * @param str
	 * @return true is str is an integer
	 */
	public static boolean isInteger(String str) {
		assert(str != null);
		
		try {
			Integer.parseInt(str);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
}
