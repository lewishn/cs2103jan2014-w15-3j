import java.util.ArrayList;

class CommandParser {
	/**
	 * Returns the command type portion of the string
	 * @param userInput
	 * @return command type portion of the input
	 */
	protected static String getUserCommandType(String userInput) {
		String[] tokens = userInput.split(" ", 2);
		return tokens[0];
	}
	/**
	 * Returns the field portion of the string
	 * @param userInput
	 * @return command type portion of the input
	 */
	protected static String getUserCommandField(String userInput) {
		String[] tokens = userInput.split(" ", 2);
		return tokens[1];
	}
	/**
	 * Returns the description portion of the string
	 * @param userInput
	 * @return description portion of the input
	 */
	protected static String getUserCommandDesc(String userInput) {
		String[] tokens = userInput.split(" ", 2);
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
	protected static boolean isValidCommand(String userInput) {
		String[] tokens = userInput.split(" ", 2);
		return tokens.length == 2;
	}
	
	/**
	 * Returns an array of string of length 4 containing the start and end
	 * dates and times of the input. Index 0 and 1 contains the start date
	 * and time respectively. Index 2 and 3 contains the end date and time.
	 * @param input
	 * @return a String array containing start and end date/times
	 */
	protected static String[] getTaskFields(ArrayList<String> input) {
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
	
	protected static String removeDateTimeFromString(String s) {
		String[] tokens = s.split(" ");
		
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
	
	protected static String removeAliasFromDescription(String desc) {
		String[] tokens = desc.split(" ");
		for (int i = 0; i < tokens.length; i++) {
			if (tokens[i].contains("alias:")) {
				tokens[i] = null;
			}
		}
		
		return arrayToString(tokens);
	}
	
	protected static String getAliasFromDescription(String desc) {
		String[] tokens = desc.split(" ");
		for (int i = 0; i < tokens.length; i++) {
			if (tokens[i].contains("alias:")) {
				return tokens[i].substring("alias:".length());
			}
		}
		
		return null;
	}
	
	private static boolean isDateTimeIdentifier(String s) {
		String[] identifierList = {"at", "@", "by", "from", "to", "on", "before", "until", "end", "start", "-", ","};
		
		if (s == null)
			return false;
		
		for(String identifier: identifierList) {
			if (s.equals(identifier)) return true;
		}
		
		return false;
	}
	
	protected static String arrayToString(String[] s) {
		String r = "";
		for (int i = 0; i < s.length; i++) {
			if (s[i] == null) continue;
			if (i == s.length) {
				r = r + s[i];
			} else {
				r = r + s[i] + " ";
			}
		}
		return r;
	}
}
