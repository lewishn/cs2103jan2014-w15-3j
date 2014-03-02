import java.util.Arrays;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Scanner;

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

	private static final String MESSAGE_INVALID_DELETE = " is an invalid line number, cannot delete.";
	private static final String MESSAGE_DELETE_SUCCESS = "Successfuly deleted line number ";
	
	private enum CommandType {
		ADD, DELETE, UPDATE, LIST, UNDO, SEARCH, CUSTOM, HELP, EXIT, INVALID;
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
		ArrayList<String> tokens = new ArrayList<String>(Arrays.asList(code.split(" ")));
		String command = tokens.get(0);
		tokens.remove(0);
		CommandType commandType = getCommandType(tokens.get(0));
		
		switch (commandType) {
			case ADD:
				addTasks(tokens);
				break;
			case LIST:
				listTasks(tokens);
				break;
			case UPDATE:
				System.out.println("UPDATE");
				break;
			case DELETE:
				deleteTasks(tokens);
				break;
			case HELP:
				displayHelp();
				break;
			case CUSTOM:
				break;
			case UNDO:
				undoActions(tokens);
				break;
			case SEARCH:
				// execute search
				break;
			case EXIT:
				return true;
			default:
				
		}
		
		return false;
	}
	
	private static void addTasks(ArrayList<String> tokens) {
		Date startDate;
		Date endDate;
		
		//deadline
		int index = tokens.indexOf("by");
		if (index >= 0) {
			if (DateTimeParse.isDate(tokens.get(index))) {
				//date = 
			}
		}
		
	}

	private static void listTasks(ArrayList<String> tokens) {
		if (tokens.size() == 0) {
			//call display()
		} else {
			//parse date
			//call display(date)
		}
	}
	
	private static void deleteTasks(ArrayList<String> tokens) {
		for (int i = 0; i < tokens.size(); i++) {
			if (isInteger(tokens.get(i))) {
				//Logic.deleteTask(Integer.parseInt(tokens.get(i));
				DoThings.printFeedbackLn(MESSAGE_DELETE_SUCCESS + tokens.get(i));
			} else {
				DoThings.printFeedbackLn(tokens.get(i) + MESSAGE_INVALID_DELETE);
			}
		}
	}
	
	private static void undoActions(ArrayList<String> tokens) {
		if (tokens.size() != 0 && isInteger(tokens.get(0))) {
			for (int i = 0; i < Integer.parseInt(tokens.get(0)); i++) {
				Logic.undoCommand();
			}
		} else if (tokens.size() == 0) {
			Logic.undoCommand();
		} else {
			//error?
		}
	}
	
	private static void searchTasks(){
		
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
