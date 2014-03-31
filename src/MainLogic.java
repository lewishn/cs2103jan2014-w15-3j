import java.util.ArrayList;

public class MainLogic{
	private static final String MESSAGE_EXIT = "Exiting...";
	private static final String MESSAGE_INVALID = "Invalid command! Please try again.\n";
	
	private static final String DEFAULT_ADD = "add";
	private static final String DEFAULT_UPDATE = "update";
	private static final String DEFAULT_DELETE = "delete";
	private static final String DEFAULT_LIST = "list";
	private static final String DEFAULT_UNDO = "undo";
	private static final String DEFAULT_REDO = "redo";
	private static final String DEFAULT_CUSTOM = "custom";
	private static final String DEFAULT_DELETE_CUSTOM = "dcustom";
	private static final String DEFAULT_HELP = "help";
	private static final String DEFAULT_SEARCH = "search";
	private static final String DEFAULT_EXIT = "exit";
	
	private enum CommandType {
		ADD, DELETE, UPDATE, LIST, UNDO, REDO, SEARCH, CUSTOM, DELETE_CUSTOM, HELP, EXIT, INVALID;
	}	

	/**
	 * Get the CommandType from the user input
	 * @param com
	 * @return a CommandType enum indicating the command type
	 */
	private static CommandType getCommandType(String com) {
		
		if (com.equals(DEFAULT_ADD) || CustomCommandHandler.isCustomCommand(com, CustomCommandHandler.HEADER_ADD)) {
			return CommandType.ADD;
		} else if (com.equals(DEFAULT_LIST) || CustomCommandHandler.isCustomCommand(com, CustomCommandHandler.HEADER_READ)) {
			return CommandType.LIST;
		} else if (com.equals(DEFAULT_UPDATE) || CustomCommandHandler.isCustomCommand(com, CustomCommandHandler.HEADER_UPDATE)) {
			return CommandType.UPDATE;
		} else if (com.equals(DEFAULT_DELETE) || CustomCommandHandler.isCustomCommand(com, CustomCommandHandler.HEADER_DELETE)) {
			return CommandType.DELETE;
		} else if (com.equals(DEFAULT_UNDO) || CustomCommandHandler.isCustomCommand(com, CustomCommandHandler.HEADER_UNDO)) {
			return CommandType.UNDO;
		} else if (com.equals(DEFAULT_REDO) || CustomCommandHandler.isCustomCommand(com, CustomCommandHandler.HEADER_REDO)) {
			return CommandType.REDO;
		} else if (com.equals(DEFAULT_CUSTOM) || CustomCommandHandler.isCustomCommand(com, CustomCommandHandler.HEADER_CUSTOM)) {
			return CommandType.CUSTOM;
		} else if (com.equals(DEFAULT_DELETE_CUSTOM) || CustomCommandHandler.isCustomCommand(com, CustomCommandHandler.HEADER_DELETE_CUSTOM)) {
			return CommandType.DELETE_CUSTOM;
		} else if (com.equals(DEFAULT_HELP) || CustomCommandHandler.isCustomCommand(com, CustomCommandHandler.HEADER_HELP)) {
			return CommandType.HELP;
		} else if (com.equals(DEFAULT_EXIT) || CustomCommandHandler.isCustomCommand(com, CustomCommandHandler.HEADER_EXIT)) {
			return CommandType.EXIT;
		} else if (com.equals(DEFAULT_SEARCH) || CustomCommandHandler.isCustomCommand(com, CustomCommandHandler.HEADER_SEARCH)) {
			return CommandType.SEARCH;
		} else {
			return CommandType.INVALID;
		}
	} 

	/**
	 * Takes in the user input and executes the relevant methods in the TaskHandler/CustomCommandHandler/HistoryHandler
	 * @param userInput
	 * @return a Feedback object containing a String to be shown to the user
	 */
	protected static Feedback runLogic(String userInput) {
		String command = CommandParser.getUserCommandType(userInput);
		String commandDesc = CommandParser.getUserCommandDesc(userInput);
		
		CommandType commandType = getCommandType(command);
		
		switch (commandType) {
			case ADD:
				return TaskHandler.addTask(commandDesc);
				
			case LIST:
				return TaskHandler.listTasks();
				
			case UPDATE:
				return TaskHandler.updateTask(commandDesc);	
				
			case DELETE:
				return TaskHandler.deleteTask(commandDesc);
			
			case HELP:
				//System.out.println("Help");
				//
				
			case CUSTOM:
				if (!CommandParser.isInputValid(commandDesc, 2)) {
					return new Feedback("Invalid custom command format");
				}
				
				String type = getCustomHeader(commandDesc);
				
				if (type == null) {
					return new Feedback("Invalid custom command format");
				}
				String customToBeAdded = CommandParser.getUserCommandDesc(commandDesc);
				return CustomCommandHandler.addCustomCommand(customToBeAdded, type);
				
			case DELETE_CUSTOM:	
				return CustomCommandHandler.deleteCustomCommand(commandDesc);
				
			case UNDO:
				return HistoryHandler.undoCommand();
			
			case REDO:
				return HistoryHandler.redoCommand();
				
			case SEARCH:
				//System.out.println("Search");
				//
				
			case EXIT:
				return new Feedback(MESSAGE_EXIT + "\n", true);
				
			default:
				return new Feedback(MESSAGE_INVALID);
		}
	}
	
	/**
	 * Get the header for the custom command from the user input
	 * @param userInput
	 * @return a string containing the custom command header
	 */
	private static String getCustomHeader(String userInput) {
		String command = CommandParser.getUserCommandType(userInput);
		
		CommandType commandType = getCommandType(command);
		
		switch (commandType) {
			case ADD:
				return CustomCommandHandler.HEADER_ADD;
				
			case LIST:
				return CustomCommandHandler.HEADER_READ;
				
			case UPDATE:
				return CustomCommandHandler.HEADER_UPDATE;
				
			case DELETE:
				return CustomCommandHandler.HEADER_DELETE;
			
			case HELP:
				return CustomCommandHandler.HEADER_HELP;
				
			case CUSTOM:
				return CustomCommandHandler.HEADER_CUSTOM;
				
			case DELETE_CUSTOM:	
				return CustomCommandHandler.HEADER_DELETE_CUSTOM;
				
			case UNDO:
				return CustomCommandHandler.HEADER_UNDO;
				
			case SEARCH:
				return CustomCommandHandler.HEADER_SEARCH;
				
			case EXIT:
				return CustomCommandHandler.HEADER_EXIT;
				
			default:
				return null;
		}
	}
	
	protected static boolean isDefaultCommand(String s) {
		String[] defaultCommandList = {
				DEFAULT_ADD,
				DEFAULT_UPDATE,
				DEFAULT_DELETE,
				DEFAULT_LIST,
				DEFAULT_UNDO,
				DEFAULT_REDO,
				DEFAULT_CUSTOM,
				DEFAULT_DELETE_CUSTOM,  
				DEFAULT_SEARCH,
				DEFAULT_HELP, 
				DEFAULT_EXIT
		};
		
		for (int i = 0; i < defaultCommandList.length; i++) {
			if (s.equals(defaultCommandList[i])) {
				return true;
			}
		}
		
		return false;
	}
}


