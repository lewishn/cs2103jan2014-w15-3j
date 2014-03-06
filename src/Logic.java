import java.text.Collator;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Stack;

public class Logic{
	private static final String MESSAGE_ADDED = "added to my %s: \"%s\"";
	private static final String MESSAGE_SEARCH_FOUND = "\"%s\" found:\n%s";
	private static final String MESSAGE_SEARCH_NOT_FOUND = "\"%s\" not found in %s";
	private static final String MESSAGE_INVALID_COMMAND = "Usage:\nadd <sentence>\ndelete <number>\ndisplay\nclear\nsort\nsearch\nexit";
	private static final String MESSAGE_INVALID_DELETE_NUMBER = "Invalid number deletion!";
	private static final String MESSAGE_INVALID_DELETE = "Usage: delete <number>";
	private static final String MESSAGE_INVALID_ADD = "Usage: add <sentence>";
	private static final String MESSAGE_INVALID_DISPLAY = "Usage: display";
	private static final String MESSAGE_INVALID_CLEAR = "Usage: clear";
	private static final String MESSAGE_INVALID_EXIT = "Usage: exit";
	private static final String MESSAGE_INVALID_SORT = "Usage: sort";
	private static final String MESSAGE_INVALID_SEARCH = "Usage: search <keyword>";

	private static final int DELETE_ARRAY_OFFSET = 1;
	static String FILE_NAME = "";
	
	/////////////////////////////////////////////////
	/////////////////////////////////////////////////
	/////////////////////////////////////////////////
	private static final String MESSAGE_ADDED_TASK = "Added \"%s\" to the task list.";
	private static final String MESSAGE_LIST_NUMBER = "%d. %s";
	private static final String MESSAGE_EMPTY_TASKS = "You have no tasks scheduled.";
	private static final String MESSAGE_TASK_DELETED = "\"%s\" has been deleted from the task list.";
	private static final String MESSAGE_TASK_DELETED_ALL = "All tasks have been deleted from the task list.";
	private static final String MESSAGE_CUSTOM_DUPLICATE = "Sorry, but this word is already in use.";
	private static final String MESSAGE_CUSTOM_SUCCESS = " has been successfully added to the command list.";
	private static final String MESSAGE_CUSTOM_NONEXISTANT = "Error deleting. There is no such word in the command list.";
	private static final String MESSAGE_CUSTOM_DELETED = " has been successfully deleted from the command list.";
	
	private static ArrayList<Task> taskList;
	private static ArrayList<ArrayList<String>> customCommandList;
	private static Stack<ArrayList<Task>> taskUndoStack;
	private static Stack<ArrayList<ArrayList<String>>> commandUndoStack;
	/////////////////////////////////////////////////
	/////////////////////////////////////////////////
	/////////////////////////////////////////////////
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

	protected static String firstStep (String userInput) {
		String message;
		String cmd;
		ArrayList<String> infoFromParser = new ArrayList<String>();
		checkTxtFile();
		infoFromParser = MainParser.initialParse(userInput);

		CommandType commandType = getCommandType(infoFromParser.get(0));
		String taskDescription = infoFromParser.get(1);
		determineCommand(commandType, taskDescription);


		return message;
	}

	private static void checkTxtFile() {
	}

	private static void determineCommand(CommandType commandType, String taskDescription) {
		switch (commandType) {
			case ADD:
				String[]taskInformation = MainParser.determineTask(taskDescription);
				break;
			case LIST:
				break;
			case UPDATE:
				//
				break;
			case DELETE:
				break;
			case HELP:
				break;
			case CUSTOM:
				//
				break;
			case DELETE_CUSTOM:
				//
				break;
			case UNDO:
				break;
			case SEARCH:
				//
				break;
			case EXIT:
				return true;
			default:
	}






































	private static void executeAdd(Task task) {
		//TODO: some error checking, e.g : start date > end date
		if (!isNullString(task)) {
			taskList.add(task);
			sortTasks();
			DoThings.printFeedback(String.format(MESSAGE_ADDED, FILE_NAME, task.getDescription()));
		} else {
			DoThings.printFeedback(MESSAGE_INVALID_ADD);
		}	
	}
	
	private static boolean isNullString(Task task) {
		if (task.getDescription() == null) {
			return true;
		}
		if (task.getDescription().equals("")) {
			return true;
		} else {
			return false;
		}
	}
	
	private static void executeUpdate(int index, String textToBeUpdated, Calendar startOfTask, Calendar endOfTask) {
	}
	
	/////////////////////////////////////////////////////////////////
	private static void addTask(Task task) {
		pushUndoStack();
		taskList.add(task);
		sortTasks();
		DiskIO.writeTaskToFile(taskList);
		DoThings.printFeedback(String.format(MESSAGE_ADDED_TASK, task.getDescription()));
	}

	private static void sortTasks() {
		Collections.sort(taskList, Collator.getInstance());
	}
	
	private static void listTasks() {		
		if (taskList.isEmpty()) {
			DoThings.printFeedback(MESSAGE_EMPTY_TASKS);
			return;
		}

		DoThings.printFeedback(getContentToDisplay());
	}
	
	private static String getContentToDisplay() {
		String contentToDisplay="";
		int index = 0;
		for (int i = 1; i <= taskList.size(); i++) {
			contentToDisplay += String.format(MESSAGE_LIST_NUMBER, i, taskList.get(i-1).toString()) + "\r\n";
		}
		
		return contentToDisplay;
	}
	
	private static void deleteTask(int index) {
		if (isOutOfDeleteRange(index)) {
			DoThings.printFeedback(index + MainParser.MESSAGE_INVALID_DELETE);
			return;
		}
		
		pushUndoStack();
		index--;
		String deletedTask = taskList.get(index).getDescription();
		taskList.remove(index);
		DiskIO.writeTaskToFile(taskList);
		DoThings.printFeedback(String.format(MESSAGE_TASK_DELETED, deletedTask));
	}
	
	private static void deleteAllTasks() {
		pushUndoStack();
		taskList = new ArrayList<Task>();
		DiskIO.writeTaskToFile(taskList);
		DoThings.printFeedback(MESSAGE_TASK_DELETED_ALL);
	}
	
	private static boolean isOutOfDeleteRange(int index) {
		if (index >= 0 && index < taskList.size()) {
			return false;
		} else {
			return true;
		}
	}
	
	protected static void addCustomCommand(int index, String command) {
		if (isDuplicateCommand(command)) {
			DoThings.printFeedback(MESSAGE_CUSTOM_DUPLICATE);
			return;
		}
		
		pushUndoStack();
		customCommandList.get(index).add(command);
		DiskIO.writeCustomCommands(customCommandList);
		DoThings.printFeedback(MESSAGE_CUSTOM_SUCCESS);
	}

	private static boolean isDuplicateCommand(String command) {
		for (int i = 0; i < customCommandList.size(); i++) {
			if (customCommandList.get(i).contains(command)) {
				return true;
			}
		}
		return false;
	}
	
	protected static void deleteCustomCommand(int index, String command) {
		pushUndoStack();
		boolean wordExists = false;
		for (int i = 0; i < customCommandList.size(); i++) {
			if (customCommandList.remove(command)) {
				DoThings.printFeedback(command + MESSAGE_CUSTOM_DELETED);
				wordExists = true;
			}
		}
		
		if (!wordExists) {
			DoThings.printFeedback(MESSAGE_CUSTOM_NONEXISTANT);
		} else {
			DiskIO.writeCustomCommands(customCommandList);
		}
	}
	
	private static void pushUndoStack() {
		taskUndoStack.push(taskList);
		commandUndoStack.push(customCommandList);
	}
	
	private static void popUndoStack() {
		// TODO: might need to check for stack size;
		taskList = taskUndoStack.pop();
		customCommandList = commandUndoStack.pop();
	}
	
	protected static void undoCommand() {
		popUndoStack();
		DiskIO.writeCustomCommands(customCommandList);
		DiskIO.writeTaskToFile(taskList);
	}
	
	/*	
	private enum UPDATE_TYPE {	
		TIME, DATE, DESCRIPTION
	}
  
  
 	private static void executeUpdate(int index, String update, String updateText) {
		executeDisplay();
		UPDATE_TYPE update_type = determineUpdateType(update);
		switch(update_type) {
		case TIME:
			executeSort();
			break;
		case DATE:
			executeSort();
			break;
		case DESCRIPTION:
			executeSort();
			break;
		default:
			break;
		}
	}
	
	private static UPDATE_TYPE determineUpdateType(String inputString) {
		if (inputString.equalsIgnoreCase("time")) {
			return UPDATE_TYPE.TIME;
		} else if (inputString.equalsIgnoreCase("date")) {
			return UPDATE_TYPE.DATE;
		} else {
			return UPDATE_TYPE.DESCRIPTION;
		}
	}
*/	
	
}


