import java.util.*;

public class Parser {
	private static String input;
	private static ArrayList<String> parsedInput;

	public Parser(String userInput) {
		input = userInput;
	}
	
	enum CommandType {
		ADD, DELETE, DISPLAY, CLEAR, SEARCH, EXIT, INVALID, UPDATE;
	}
	private static CommandType getCommandType(String com){
		if ( com.equalsIgnoreCase("\\add") || com.equalsIgnoreCase("\\a") ) {
			return CommandType.ADD;
		} else if ( com.equalsIgnoreCase("\\list") || com.equalsIgnoreCase("\\l") ) {
			return CommandType.DISPLAY;
		} else if ( com.equalsIgnoreCase("\\update") || com.equalsIgnoreCase("\\u") ) {
			return CommandType.UPDATE;
		} else if ( com.equalsIgnoreCase("\\delete") || com.equalsIgnoreCase("\\d") ) {
			return CommandType.DELETE;
		} else {
			return CommandType.INVALID;
		} 
	}
	protected static void parseCommands() {
		parsedInput = new ArrayList<String>(Arrays.asList(input.split(" ", 2)));
		String command = parsedInput.get(0); 
		CommandType commandType = getCommandType(command);

		switch (commandType) {
			case ADD:
				
				break;
			case DELETE:
				//call delete function
				break;
			case DISPLAY:
				//display
				break;
			case CLEAR:
				//clear all
				break;
			case SEARCH:
				//search for keyword
				break;
			case EXIT:
				//exit
				break;
			case INVALID:
				//print error msg
				break;	
			case UPDATE:
				//call edit function			
		}
	}
}
