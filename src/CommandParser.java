import java.util.ArrayList;

class CommandParser {
	protected static String getUserCommandType(String userInput) {
		String[] tokens = userInput.split(" ", 2);
		return tokens[0];
	}
	
	protected static String getUserCommandDesc(String userInput) {
		String[] tokens = userInput.split(" ", 2);
		if (tokens.length < 2) {
			return null;
		}
		return tokens[1];
	}
	
	protected static boolean isValidCommand(String userInput) {
		String[] tokens = userInput.split(" ", 2);
		return tokens.length == 2;
	}
	
	protected static String[] getTaskFields(ArrayList<String> input) {
		String[] fields = new String[Task.TASK_FIELD_SIZE];
		fields = getDateFields(input, fields);
		fields = getTimeFields(input, fields);
		return fields;
	}
	
	protected static String[] getDateFields(ArrayList<String> input, String[] fields) {
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
	
	protected static String[] getTimeFields(ArrayList<String> input, String[] fields) {
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
}
