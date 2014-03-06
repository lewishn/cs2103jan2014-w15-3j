import java.util.*;

public class Parser {
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

	private static Boolean floatingTask;

	private enum CommandType {
		ADD, DELETE, UPDATE, LIST, UNDO, SEARCH, CUSTOM, DELETE_CUSTOM, HELP, EXIT, INVALID;
	}
	private static CommandType getCommandType(String com){
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
	protected static void parseCommands(String userInput) {
		ArrayList<String> parsedInput = new ArrayList<String>(Arrays.asList(userInput.split(" ", 2)));
		String command = parsedInput.get(0); 
		CommandType commandType = getCommandType(command);

		switch (commandType) {
			case ADD:
				addTask(parsedInput.get(1));
				break;
			case LIST:
				//listTasks(command);
				break;
			case UPDATE:
				//
				break;
			case DELETE:
				//deleteTasks(command);
				break;
			case HELP:
				//displayHelp();
				break;
			case CUSTOM:
				//
				break;
			case DELETE_CUSTOM:
				//
				break;
			case UNDO:
				//undoActions(command);
				break;
			case SEARCH:
				//
				break;
			case EXIT:
				//return true;
			default:
				//			
		}
	}
	protected static void setFloatingTaskFalse() {
		floatingTask=false;
	}
	private static void addTask(String parsedUserInput) {
		String description, upperCasedParsedUserInput;
		description = parsedUserInput;
		parsedUserInput = parsedUserInput.trim();
		upperCasedParsedUserInput = parsedUserInput.toUpperCase();
		
		ArrayList<String> keyWordIdentifiers = new ArrayList<String>();
		keyWordIdentifiers.add("@");
		keyWordIdentifiers.add("`");
		keyWordIdentifiers.add("due");
		keyWordIdentifiers.add("by");	
		keyWordIdentifiers.add("at");
		int numOfKeyWords = keyWordIdentifiers.size();
		
		floatingTask=true;
		String[] listOfUserInput=upperCasedParsedUserInput.split(" ");
		int numberOfWordsInUserInput = listOfUserInput.length;
		
		for(int i=0; i<numberOfWordsInUserInput; i++) {
			/* test for identifiers */
			/*
			for(int j=0; j<numOfKeyWords;j++) {
				if(listOfUserInput[i].equals(keyWordIdentifiers.get(j).toUpperCase())) {
					try {
						Integer.parseInt(listOfUserInput[i+1]);
						floatingTask=false;
					} catch (NumberFormatException e) {
						System.out.println("Identified " + keyWordIdentifiers.get(j));
					}
				}
			}*/
			/* check for dates */
			DateParse.setDate(listOfUserInput[i]);
		}
		if(floatingTask) {
			System.out.println("This is a floating task: " + parsedUserInput);
		} else {
			System.out.println("This is a non-floating task.");
		}
		
		// find description
		// Logic.executeAdd(createTask(description, startOfTask, endOfTask));
	}
}
