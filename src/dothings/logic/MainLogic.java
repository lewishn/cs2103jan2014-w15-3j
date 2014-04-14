package dothings.logic;

import java.util.ArrayList;

import dothings.parser.CommandParser;
import dothings.storage.FileManager;

//@author A0097082Y
public class MainLogic {
	private static final String LOG_USER_INPUT = "User Input: %s";
	private static final String MESSAGE_EXIT = "exiting..";
	private static final String MESSAGE_INVALID = "Oops, please try again.";
	private static final String MESSAGE_ERROR_CUSTOM = "Invalid custom command format";
	private static final String MESSAGE_ERROR_CUSTOM_FORMAT = "Invalid custom command format";
	private static final String DEFAULT_ADD = "add";
	private static final String DEFAULT_UPDATE = "update";
	private static final String DEFAULT_DELETE = "delete";
	private static final String DEFAULT_LIST = "list";
	private static final String DEFAULT_UNDO = "undo";
	private static final String DEFAULT_REDO = "redo";
	private static final String DEFAULT_CUSTOM = "custom";
	private static final String DEFAULT_DELETE_CUSTOM = "dcustom";
	private static final String DEFAULT_MARK = "mark";
	private static final String DEFAULT_HELP = "help";
	private static final String DEFAULT_SEARCH = "search";
	private static final String DEFAULT_EXIT = "exit";
	private static final String HELP_FEEDBACK_MSG = "Need help? Your Commands:";
	private static final String MESSAGE_ERROR_SEARCH = "Please enter something to search.";
	
	private static final String ERROR_CODE = "error";
	private static final String DOT = ". ";
	private static final String MARK_CODE = "marked";
	private static final String UNMARK_CODE = "unmarked";
	private static final String OVERDUE = "overdue";
	private static final String DUE_TODAY = "today";
	private static final String DUE_OTHER = "others";
	private static final String FLOATING = "floating";
	
	private static final int FEEDBACK_TYPE = 0;
	private static final int FEEDBACK_DESC = 1;
	private static final int TASK_DESC = 2;
	private static final int TASK_ALIAS = 3;
	private static final int TASK_STATUS = 4;
	private static final int TASK_DATE = 5;
	private static final int TASK_TIME = 6;
	private static final int NUM_OF_FEEDBACK = 7;
	
	private enum CommandType {
		ADD, DELETE, UPDATE, LIST, UNDO, REDO, SEARCH, 
		CUSTOM, DELETE_CUSTOM, MARK, HELP, EXIT, INVALID;
	}
	
	/**
	 * Get the CommandType from the user input
	 * 
	 * @param com
	 * @return a CommandType enum indicating the command type
	 */
	private static CommandType getCommandType(String com) {
		
		if (com.equalsIgnoreCase(DEFAULT_ADD)
		        || CustomCommandHandler.isCustomCommand(com,
		                CustomCommandHandler.HEADER_ADD)) {
			return CommandType.ADD;
		} else if (com.equalsIgnoreCase(DEFAULT_LIST)
		        || CustomCommandHandler.isCustomCommand(com,
		                CustomCommandHandler.HEADER_READ)) {
			return CommandType.LIST;
		} else if (com.equalsIgnoreCase(DEFAULT_UPDATE)
		        || CustomCommandHandler.isCustomCommand(com,
		                CustomCommandHandler.HEADER_UPDATE)) {
			return CommandType.UPDATE;
		} else if (com.equalsIgnoreCase(DEFAULT_DELETE)
		        || CustomCommandHandler.isCustomCommand(com,
		                CustomCommandHandler.HEADER_DELETE)) {
			return CommandType.DELETE;
		} else if (com.equalsIgnoreCase(DEFAULT_UNDO)
		        || CustomCommandHandler.isCustomCommand(com,
		                CustomCommandHandler.HEADER_UNDO)) {
			return CommandType.UNDO;
		} else if (com.equalsIgnoreCase(DEFAULT_REDO)
		        || CustomCommandHandler.isCustomCommand(com,
		                CustomCommandHandler.HEADER_REDO)) {
			return CommandType.REDO;
		} else if (com.equalsIgnoreCase(DEFAULT_CUSTOM)
		        || CustomCommandHandler.isCustomCommand(com,
		                CustomCommandHandler.HEADER_CUSTOM)) {
			return CommandType.CUSTOM;
		} else if (com.equalsIgnoreCase(DEFAULT_DELETE_CUSTOM)
		        || CustomCommandHandler.isCustomCommand(com,
		                CustomCommandHandler.HEADER_DELETE_CUSTOM)) {
			return CommandType.DELETE_CUSTOM;
		} else if (com.equalsIgnoreCase(DEFAULT_HELP)
		        || CustomCommandHandler.isCustomCommand(com,
		                CustomCommandHandler.HEADER_HELP)) {
			return CommandType.HELP;
		} else if (com.equalsIgnoreCase(DEFAULT_EXIT)
		        || CustomCommandHandler.isCustomCommand(com,
		                CustomCommandHandler.HEADER_EXIT)) {
			return CommandType.EXIT;
		} else if (com.equalsIgnoreCase(DEFAULT_SEARCH)
		        || CustomCommandHandler.isCustomCommand(com,
		                CustomCommandHandler.HEADER_SEARCH)) {
			return CommandType.SEARCH;
		} else if (com.equalsIgnoreCase(DEFAULT_MARK)
		        || CustomCommandHandler.isCustomCommand(com,
		                CustomCommandHandler.HEADER_MARK)) {
			return CommandType.MARK;
		} else {
			return CommandType.INVALID;
		}
	}
	
	/**
	 * Takes in the user input and executes the relevant methods in the
	 * TaskHandler/CustomCommandHandler/HistoryHandler
	 * 
	 * @param userInput
	 * @return a Feedback object containing a String to be shown to the user
	 */
	public static ArrayList<ArrayList<String>> runLogic(String userInput) {
		FileManager.log(String.format(LOG_USER_INPUT, userInput));
		String command = CommandParser.getUserCommandType(userInput);
		String commandDesc = CommandParser.getUserCommandDesc(userInput);
		Feedback feed;
		
		CommandType commandType = getCommandType(command);
		
		switch (commandType)
			{
				case ADD:
					feed = TaskHandler.addTask(commandDesc);
					return processFeedback(feed, DEFAULT_ADD);
					
				case LIST:
					feed = TaskHandler.listTasks(commandDesc);
					return processFeedback(feed, DEFAULT_LIST);
					
				case UPDATE:
					feed = TaskHandler.updateTask(commandDesc);
					return processFeedback(feed, DEFAULT_UPDATE);
					
				case DELETE:
					feed = TaskHandler.deleteTask(commandDesc);
					return processFeedback(feed, DEFAULT_DELETE);
					
				case HELP:
					Feedback feedback = new Feedback(HELP_FEEDBACK_MSG);
					return processFeedback(feedback, DEFAULT_HELP);
					
				case CUSTOM:
					if (!CommandParser.isInputValid(commandDesc, 2)) {
						feed = new Feedback(MESSAGE_ERROR_CUSTOM);
						return processFeedback(feed, DEFAULT_CUSTOM);
					}
					
					String type = getCustomHeader(commandDesc);
					
					if (type == null) {
						feed = new Feedback(MESSAGE_ERROR_CUSTOM_FORMAT);
						return processFeedback(feed, DEFAULT_CUSTOM);
					}
					String customToBeAdded = CommandParser
					        .getUserCommandDesc(commandDesc);
					feed = CustomCommandHandler.addCustomCommand(
					        customToBeAdded, type);
					return processFeedback(feed, DEFAULT_CUSTOM);
					
				case DELETE_CUSTOM:
					feed = CustomCommandHandler
					        .deleteCustomCommand(commandDesc);
					return processFeedback(feed, DEFAULT_DELETE_CUSTOM);
					
				case UNDO:
					if (CommandParser.isInteger(commandDesc)) {
						feed = HistoryHandler.undoCommand(Integer
						        .parseInt(commandDesc));
					} else {
						feed = HistoryHandler.undoCommand(1);
					}
					return processFeedback(feed, DEFAULT_UNDO);
					
				case REDO:
					if (CommandParser.isInteger(commandDesc)) {
						feed = HistoryHandler.redoCommand(Integer
						        .parseInt(commandDesc));
					} else {
						feed = HistoryHandler.redoCommand(1);
					}
					return processFeedback(feed, DEFAULT_REDO);
					
				case SEARCH:
					if (commandDesc == null) {
						feed = new Feedback(MESSAGE_ERROR_SEARCH);
						return processFeedback(feed, DEFAULT_SEARCH);
					} else {
						feed = TaskHandler.searchTasks(commandDesc);
						return processFeedback(feed, DEFAULT_SEARCH);
					}
					
				case MARK:
					feed = TaskHandler.markTask(commandDesc);
					return processFeedback(feed, DEFAULT_MARK);
					
				case EXIT:
					feed = new Feedback(MESSAGE_EXIT, false, true);
					return processFeedback(feed, DEFAULT_EXIT);
					
				default:
					feed = new Feedback(MESSAGE_INVALID, true);
					return processFeedback(feed, ERROR_CODE);
			}
	}
	
	/**
	 * Get the header for the custom command from the user input
	 * 
	 * @param userInput
	 * @return a string containing the custom command header
	 */
	private static String getCustomHeader(String userInput) {
		String command = CommandParser.getUserCommandType(userInput);
		
		CommandType commandType = getCommandType(command);
		
		switch (commandType)
			{
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
					
				case REDO:
					return CustomCommandHandler.HEADER_REDO;
					
				case SEARCH:
					return CustomCommandHandler.HEADER_SEARCH;
					
				case MARK:
					return CustomCommandHandler.HEADER_MARK;
					
				case EXIT:
					return CustomCommandHandler.HEADER_EXIT;
					
				default:
					return null;
			}
	}
	
	/**
	 * Processes feedback before sending an ArrayList of ArrayList<String> to GUI which contains all
	 * the feedback information
	 * 
	 * @param feed
	 * @param type
	 * @return ArrayList<ArrayList<String>> result
	 */
	private static ArrayList<ArrayList<String>> processFeedback(Feedback feed,
	                                                            String type) {
		ArrayList<ArrayList<String>> result = new ArrayList<ArrayList<String>>();
		for (int i = 0; i < NUM_OF_FEEDBACK; i++) {
			result.add(new ArrayList<String>());
		}
		
		if (feed.getExitFlag()) {
			result.get(FEEDBACK_TYPE).add(DEFAULT_EXIT);
			return result;
		} else if (feed.getErrorFlag()) {
			result.get(FEEDBACK_TYPE).add(ERROR_CODE);
		} else {
			result.get(FEEDBACK_TYPE).add(type);
		}
		result.get(FEEDBACK_DESC).add(feed.getDesc());
		
		if (type.equals(DEFAULT_HELP)) {
			result.get(TASK_DESC).add(helpDescription());
		}
		else {
			ArrayList<Task> taskList = Task.getList();
			ArrayList<Integer> numberList = feed.getIndexList();
			
			for (int i = 0; i < numberList.size(); i++) {
				Integer number = numberList.get(i);
				Task task = taskList.get(number);
				Integer numberToString = number + 1;
				result.get(TASK_DESC)
				        .add(numberToString.toString() + DOT
				                + task.getDescription());
				result.get(TASK_ALIAS).add(task.getAlias());
				result.get(TASK_DATE).add(task.getDateTimeString());
				if (task.getStatus()) {
					result.get(TASK_STATUS).add(MARK_CODE);
					if (task.isOverdue()) {
						result.get(TASK_TIME).add(OVERDUE);
					} else if (task.isToday()) {
						result.get(TASK_TIME).add(DUE_TODAY);
					} else if (task.isUnscheduled()) {
						result.get(TASK_TIME).add(FLOATING);
					} else {
						result.get(TASK_TIME).add(DUE_OTHER);
					}
				} else {
					result.get(TASK_STATUS).add(UNMARK_CODE);
					if (task.isOverdue()) {
						result.get(TASK_TIME).add(OVERDUE);
					} else if (task.isToday()) {
						result.get(TASK_TIME).add(DUE_TODAY);
					} else if (task.isUnscheduled()) {
						result.get(TASK_TIME).add(FLOATING);
					} else {
						result.get(TASK_TIME).add(DUE_OTHER);
					}
				}
			}
		}
		return result;
	}
	
	// @author A0099727J
	/**
	 * Check whether a command input is a default command
	 * 
	 * @param s
	 * @return true or false
	 */
	protected static boolean isDefaultCommand(String s) {
		String[] defaultCommandList = {
		        DEFAULT_ADD, DEFAULT_UPDATE, DEFAULT_MARK,
		        DEFAULT_DELETE, DEFAULT_LIST, DEFAULT_SEARCH,
		        DEFAULT_UNDO, DEFAULT_REDO, DEFAULT_CUSTOM,
		        DEFAULT_DELETE_CUSTOM, DEFAULT_HELP, DEFAULT_EXIT
		};
		
		for (int i = 0; i < defaultCommandList.length; i++) {
			if (s.equals(defaultCommandList[i])) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * creates help description
	 * 
	 * @return longHelpDescription
	 */
	private static String helpDescription() {
		String[] commandList = {
		        DEFAULT_ADD, DEFAULT_UPDATE, DEFAULT_MARK,
		        DEFAULT_DELETE, DEFAULT_LIST, DEFAULT_SEARCH,
		        DEFAULT_UNDO, DEFAULT_REDO, DEFAULT_CUSTOM,
		        DEFAULT_DELETE_CUSTOM, DEFAULT_HELP, DEFAULT_EXIT
		};
		
		String longHelpDescription = "";
		
		for (int i = 0; i < commandList.length; i++) {
			String commandHeader = getCustomHeader(commandList[i]);
			longHelpDescription += (commandHeader + "\n");
			longHelpDescription += (commandList[i]
			        + CustomCommandHandler
			                .getListOfCustomCommands(commandHeader) + "\n\n");
		}
		return longHelpDescription;
	}
}
