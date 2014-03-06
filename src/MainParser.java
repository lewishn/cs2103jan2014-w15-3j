import java.util.Arrays;
import java.util.ArrayList;
import java.util.Date;

//TODO : ADD, UPDATE, SEARCH, CUSTOM, HELP

class MainParser {
	protected static String[] parseCommand(String userInput) {
		String[] userCommand =new String[2];
		userCommand = Arrays.asList(userInput.split(" ", 2));
		return userCommand;
	}

	// returns true only on exit command
	public static boolean parse(String code) {
		ArrayList<String> tokens = new ArrayList<String>(Arrays.asList(code.split(" ", 2)));
		CommandType commandType = getCommandType(tokens.get(0));
		String command = tokens.get(1);
		
		switch (commandType) {
			case ADD:
				addTasks(command);
				break;
			case LIST:
				listTasks(command);
				break;
			case UPDATE:
				//
				break;
			case DELETE:
				deleteTasks(command);
				break;
			case HELP:
				displayHelp();
				break;
			case CUSTOM:
				//
				break;
			case DELETE_CUSTOM:
				//
				break;
			case UNDO:
				undoActions(command);
				break;
			case SEARCH:
				//
				break;
			case EXIT:
				return true;
			default:
				//	
		}
		return false;
	}
	
	private static void addTasks(String command) {
		Date startDate;
		Date endDate;
		
		//deadline
		/*
		int index = tokens.indexOf("by");
		if (index >= 0) {
			if (DateTimeParse.isDate(tokens.get(index))) {
				//date = 
			}
		}
		*/
	}

	private static void listTasks(String command) {
		if (command.equals("") || command == null) {
			//call display()
		} else {
			//parse date
			//call display(date)
		}
	}
	
	private static void deleteTasks(String command) {
		if (isInteger(command)) {
			//Logic.deleteTask(Integer.parseInt(tokens.get(i));
			DoThings.printFeedbackLn(MESSAGE_DELETE_SUCCESS + command);
		} else {
			DoThings.printFeedbackLn(command + MESSAGE_INVALID_DELETE);
		}
	}
	
	private static void undoActions(String command) {
		if (command.equals("") || command == null) {
			Logic.undoCommand();
		} else if (isInteger(command)) {
			for (int i = 0; i < Integer.parseInt(command); i++) {
				Logic.undoCommand();
			}
		} else {
			//error?
		}
	}
	
	//
	private static void searchTasks(){
		// to be implemented later
	}
	
	private static void addCustomCommands() {
		
	}
	
	private static void deleteCustomCommands() {
		
	}
	
	private static void displayHelp() {
		
	}
	
	public static boolean isInteger(String str) {
		try {
			Integer.parseInt(str);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
	
}
