import java.util.ArrayList;

public class Logic{
	//private static Boolean hasTxtFile=false;

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
	//private static final String DEFAULT_DELETE_CUSTOM = "\\dcustom";
	//private static final String DEFAULT_DELETE_CUSTOM2 = "\\dc";
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

	protected static Boolean firstStep (String userInput) {
		Boolean exit;
		String[] infoFromParser = new String[2];
		infoFromParser[0]="";
		infoFromParser[1]="";
		checkTxtFile();
		infoFromParser = MainParser.initialParse(userInput);

		CommandType commandType = getCommandType(infoFromParser[0]);
		String taskDescription = infoFromParser[1];
		exit = determineCommand(commandType, taskDescription);
		return exit;
	}

	private static void checkTxtFile() {
		// call DiskIO to check whether text file exists
		// if true set hasTxtFile to true
	}

	private static Boolean determineCommand(CommandType commandType, String taskDescription) {
		switch (commandType) {
			case ADD:
				Action.addTask(taskDescription);
				return false;
			case LIST:
				Action.listTasks();
				return false;
			case UPDATE:
				return false;
			case DELETE:
				Action.deleteTask(taskDescription);
				return false;
			case HELP:
				return false;
			case CUSTOM:
				Action.addCustomCommand(taskDescription);
				return false;
			case DELETE_CUSTOM:	
				Action.deleteCustomCommand(taskDescription);
				return false;
			case UNDO:
				Action.undoCommand();
				return false;	
			case SEARCH:
				return false;
			case EXIT:
				return true;
			default:
				return false;
		}
	}
}


