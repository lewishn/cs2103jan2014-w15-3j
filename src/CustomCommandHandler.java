import java.util.ArrayList;
import java.util.Arrays;


class CustomCommandHandler {
	private static final String FILE_CUSTOM = "custom.txt";
	
	protected static final String HEADER_ADD = "[ADD]";
	protected static final String HEADER_READ = "[READ]";
	protected static final String HEADER_UPDATE = "[UPDATE]";
	protected static final String HEADER_DELETE = "[DELETE]";
	protected static final String HEADER_SEARCH = "[SEARCH]";
	protected static final String HEADER_UNDO = "[UNDO]";
	protected static final String HEADER_REDO = "[REDO]";
	protected static final String HEADER_CUSTOM = "[CUSTOM]";
	protected static final String HEADER_DELETE_CUSTOM = "[DELETE_CUSTOM]";
	protected static final String HEADER_HELP = "[HELP]";
	protected static final String HEADER_EXIT = "[EXIT]";

	private static final String MESSAGE_CUSTOM_DUPLICATE = "Sorry, but this word is already in use.";
	private static final String MESSAGE_CUSTOM_SUCCESS = " has been successfully added to the command list.";
	private static final String MESSAGE_CUSTOM_NONEXISTANT = "Error deleting. There is no such word in the command list.";
	private static final String MESSAGE_CUSTOM_DELETED = " has been successfully deleted from the command list.";
	
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
		if (isDuplicateCommand(userCommand)) {
			return new Feedback(MESSAGE_CUSTOM_DUPLICATE, false);
		}
		
		HistoryHandler.pushUndoStack();
		//customCommandList.get(index).add(command);
		saveCustomCommands();
		return new Feedback(MESSAGE_CUSTOM_SUCCESS, false);
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
	
	private static void addToCustomList(String command, String type) {
		
	}
	
	/**
	 * Deletes a custom command from the list of custom commands 
	 * and saves the list to the file.
	 * @param userCommand
	 * @return a Feedback object containing the message that is to be shown to the user
	 */
	protected static Feedback deleteCustomCommand(String userCommand) {
		HistoryHandler.pushUndoStack();
		for (int i = 0; i < customCommandList.size(); i++) {
			if (customCommandList.remove(userCommand)) {
				saveCustomCommands();
				return new Feedback(userCommand + MESSAGE_CUSTOM_DELETED, false);
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
				line += (customCommandList.get(j) + " ");
			}
			listToSave.add(line + System.getProperty("line.separator"));
		}
		
		FileManager.writeToFile(FILE_CUSTOM, listToSave);
	}
}
