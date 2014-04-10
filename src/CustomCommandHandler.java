import java.util.ArrayList;
import java.util.Arrays;


class CustomCommandHandler {
	private static final String FILE_CUSTOM = "custom.txt";
	
	protected static final String HEADER_ADD = "[ADD]";
	protected static final String HEADER_READ = "[LIST]";
	protected static final String HEADER_UPDATE = "[UPDATE]";
	protected static final String HEADER_DELETE = "[DELETE]";
	protected static final String HEADER_SEARCH = "[SEARCH]";
	protected static final String HEADER_UNDO = "[UNDO]";
	protected static final String HEADER_REDO = "[REDO]";
	protected static final String HEADER_CUSTOM = "[CUSTOM]";
	protected static final String HEADER_DELETE_CUSTOM = "[DELETE_CUSTOM]";
	protected static final String HEADER_HELP = "[HELP]";
	protected static final String HEADER_MARK = "[MARK]";
	protected static final String HEADER_EXIT = "[EXIT]";

	private static final String MESSAGE_CUSTOM_DUPLICATE = "Sorry, but this word is already in use.";
	private static final String MESSAGE_CUSTOM_SUCCESS = "\"%s\" has been added to the command list.";
	private static final String MESSAGE_CUSTOM_NONEXISTANT = "Error deleting. No such word in command list.";
	private static final String MESSAGE_CUSTOM_DELETED = "\"%s\" has been deleted from the command list.";
	
	protected static ArrayList<ArrayList<String>> customCommandList = loadCustomCommands();
	
	/**
	 * Returns true if the keyword is a valid keyword of the commandType.
	 * @param keyword
	 * @param commandType
	 * @return true if the keyword is a valid keyword of the commandType.
	 */
	protected static boolean isCustomCommand(String keyword, String commandType) {
		for (int i = 0; i < customCommandList.size(); i++) {
			if (customCommandList.get(i).get(0).equals(commandType)) {
				return customCommandList.get(i).contains(keyword);
			}
		}
		
		return false;
	}
	
	/**
	 * Adds a custom command to the current list of custom commands and saves the
	 * list to the file.
	 * @param userCommand
	 * @param commandType
	 * @return a Feedback object containing the message that is to be shown to the user
	 */
	protected static Feedback addCustomCommand(String userCommand, String commandType) {
		// if user inputs a white space between two or more words, take only the first
		userCommand = userCommand.split("\\s+")[0];
		
		if (isDuplicateCommand(userCommand) || MainLogic.isDefaultCommand(userCommand)) {
			return new Feedback(MESSAGE_CUSTOM_DUPLICATE);
		}
		
		addCommandToList(userCommand, commandType);
		saveCustomCommands();
		return new Feedback(userCommand + MESSAGE_CUSTOM_SUCCESS);
	}
	
	private static void addCommandToList(String userCommand, String commandType) {
		int index = getCommandHeaderIndex(commandType);
		if (index >= 0) {
			customCommandList.get(index).add(userCommand);
		} else {
			ArrayList<String> newCommandEntry = new ArrayList<String>();
			newCommandEntry.add(commandType);
			newCommandEntry.add(userCommand);
			customCommandList.add(newCommandEntry);
		}
	}

	/**
	 * Get the index of the specific command header in the customCommandList
	 * Returns -1 if not found.
	 * @return index of the specific command header
	 */
	private static int getCommandHeaderIndex(String commandType) {
		for (int i = 0; i < customCommandList.size(); i++) {
			if (customCommandList.get(i).get(0).equals(commandType)) {
				return i;
			}
		}
		
		return -1;
	}
	
	protected static String getListOfCustomCommands(String header) {
		int index = getCommandHeaderIndex(header);
		String list = " ";
		
		if (index != -1) {
			for (int i = 1; i < customCommandList.get(index).size(); i++) {
				list += customCommandList.get(index).get(i);
			}
		}
		
		return list;
	}
	
	/**
	 * Finds out if the input custom command is already in use
	 * @param command
	 * @return true if the command is already in use.
	 */
	private static boolean isDuplicateCommand(String command) {
		for (int i = 0; i < customCommandList.size(); i++) {
			if (customCommandList.get(i).contains(command)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Deletes a custom command from the list of custom commands 
	 * and saves the list to the file.
	 * @param userCommand
	 * @return a Feedback object containing the message that is to be shown to the user
	 */
	protected static Feedback deleteCustomCommand(String userCommand) {
		for (int i = 0; i < customCommandList.size(); i++) {
			for (int j = 0; j < customCommandList.get(i).size(); j++) {
				if (customCommandList.get(i).get(j).equals(userCommand)) {
					customCommandList.get(i).remove(j);
					saveCustomCommands();
					return new Feedback(userCommand + MESSAGE_CUSTOM_DELETED);
				}
			}
		}
		
		return new Feedback(MESSAGE_CUSTOM_NONEXISTANT, false);
	}	
	
	/**
	 * Reads custom commands from the "custom.txt" file
	 * @return list of custom commands
	 */
	protected static ArrayList<ArrayList<String>> loadCustomCommands() {
		ArrayList<ArrayList<String>> commandList = new ArrayList<ArrayList<String>>();
		ArrayList<String> loadedCommands = FileManager.readFromFile(FILE_CUSTOM);
		
		for (int i = 0; i < loadedCommands.size(); i++) {
			String[] tokens = loadedCommands.get(i).split(" ");
			ArrayList<String> tempList = new ArrayList<String>(Arrays.asList(tokens));
			commandList.add(tempList);
		}
		
		return commandList;
	}
	
	
	/**
	 * Saves the list of custom commands to the "customs.txt" file
	 */
	protected static void saveCustomCommands() {
		ArrayList<String> listToSave = new ArrayList<String>();
		
		for (int i = 0; i < customCommandList.size(); i++) {
			String line = "";
			for (int j = 0; j < customCommandList.get(i).size(); j++) {
				line += (customCommandList.get(i).get(j) + " ");
			}
			listToSave.add(line);
		}
		
		FileManager.writeToFile(FILE_CUSTOM, listToSave);
	}
}
