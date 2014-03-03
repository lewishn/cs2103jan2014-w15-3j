import java.util.*;

public class Parser {
	
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
	protected static void parseCommands(String userInput) {
		ArrayList<String> parsedInput = new ArrayList<String>(Arrays.asList(userInput.split(" ", 2)));
		String command = parsedInput.get(0); 
		CommandType commandType = getCommandType(command);

		switch (commandType) {
			case ADD:
				addTask(parsedInput.get(1));
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
	private static void addTask(String parsedUserInput) {
		String description, upperCasedParsedUserInput;
		Calendar startOfTask, endOfTask;
		startOfTask=Calendar.getInstance();
		endOfTask=Calendar.getInstance();
		
		parsedUserInput = parsedUserInput.trim();
		upperCasedParsedUserInput = parsedUserInput.toUpperCase();
		
		ArrayList<String> keyWordIdentifiers = new ArrayList<String>();
		int index=0;
		for(int i=0; i<keyWordIdentifiers.size(); i++) {
			index=upperCasedParsedUserInput.indexOf(keyWordIdentifiers.get(i));
			
		}
		
		// find date 
		// find time
		// find description
		// Logic.executeAdd(createTask(description, startOfTask, endOfTask));
	}
}
