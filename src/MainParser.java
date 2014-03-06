import java.util.Arrays;
import java.util.ArrayList;
import java.util.Date;

//TODO : ADD, UPDATE, SEARCH, CUSTOM, HELP

class MainParser {
	private static final String DEFAULT_ADD = "\\add";
	private static final String DEFAULT_ADD2 = "\\a";
	private static final String DEFAULT_UPDATE = "\\update";
	private static final String DEFAULT_UPDATE2 = "\\u";
	private static final String DEFAULT_DELETE = "\\delete";
	private static final String DEFAULT_DELETE2 = "\\d";
	private static final String DEFAULT_LIST = "\\list";
	private static final String DEFAULT_LIST2 = "\\l";
	private static final String DEFAULT_UNDO = "\\undo";
	private static final String DEFAULT_UNDO2 = "\\un";
	private static final String DEFAULT_CUSTOM = "\\custom";
	private static final String DEFAULT_CUSTOM2 = "\\cc";
	private static final String DEFAULT_DELETE_CUSTOM = "\\dcustom";
	private static final String DEFAULT_DELETE_CUSTOM2 = "\\dc";
	private static final String DEFAULT_HELP = "\\help";
	private static final String DEFAULT_HELP2 = "\\h";
	private static final String DEFAULT_SEARCH = "\\search";
	private static final String DEFAULT_SEARCH2 = "\\s";
	private static final String DEFAULT_EXIT = "\\exit";
	private static final String DEFAULT_EXIT2 = "\\e";

	public static final String MESSAGE_INVALID_DELETE = " is an invalid task number, cannot delete.";
	public static final String MESSAGE_DELETE_SUCCESS = "Successfuly deleted task number ";

	private enum CommandType {
		ADD, DELETE, UPDATE, LIST, UNDO, SEARCH, CUSTOM, DELETE_CUSTOM, HELP, EXIT, INVALID;
	}	

	private static CommandType getCommandType(String com) {
		ArrayList<ArrayList<String>> customCommand = DiskIO.readCustomCommands();
		
		if (com.equals(DEFAULT_ADD) || com.equals(DEFAULT_ADD2) || customCommand.get(DiskIO.ADD_INDEX).contains(com)) {
			return CommandType.ADD;
		} else if (com.equals(DEFAULT_LIST) || com.equals(DEFAULT_LIST2) || customCommand.get(DiskIO.LIST_INDEX).contains(com)) {
			return CommandType.LIST;
		} else if (com.equals(DEFAULT_UPDATE) || com.equals(DEFAULT_UPDATE2) || customCommand.get(DiskIO.UPDATE_INDEX).contains(com)) {
			return CommandType.UPDATE;
		} else if (com.equals(DEFAULT_DELETE) || com.equals(DEFAULT_DELETE2) || customCommand.get(DiskIO.DELETE_INDEX).contains(com)) {
			return CommandType.DELETE;
		} else if (com.equals(DEFAULT_UNDO) || com.equals(DEFAULT_UNDO2) || customCommand.get(DiskIO.UNDO_INDEX).contains(com)) {
			return CommandType.UNDO;
		} else if (com.equals(DEFAULT_CUSTOM) || com.equals(DEFAULT_CUSTOM2) || customCommand.get(DiskIO.CUSTOM_INDEX).contains(com)) {
			return CommandType.CUSTOM;
		} else if (com.equals(DEFAULT_HELP) || com.equals(DEFAULT_HELP2) || customCommand.get(DiskIO.HELP_INDEX).contains(com)) {
			return CommandType.HELP;
		} else if (com.equals(DEFAULT_EXIT) || com.equals(DEFAULT_EXIT2) || customCommand.get(DiskIO.EXIT_INDEX).contains(com)) {
			return CommandType.EXIT;
		} else if (com.equals(DEFAULT_SEARCH) || com.equals(DEFAULT_SEARCH2) || customCommand.get(DiskIO.SEACRH_INDEX).contains(com)) {
			return CommandType.SEARCH;
		} else {
			return CommandType.INVALID;
		}
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
