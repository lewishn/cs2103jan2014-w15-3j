import java.util.*;

public class Parser {
	private static String input;
	private static String command;
	private static ArrayList<String> parsedInput;

	public Parser(String userInput) {
		input = userInput;
	}
	
	enum CommandType {
		ADD, DELETE, DISPLAY, CLEAR, SORT, SEARCH, EXIT, INVALID;
	}

	protected static void parseCommands() {
		parsedInput = new ArrayList<String>(Arrays.asList(input.split(" ", 2)));
		command = parsedInput.get(0); 
	}
}
