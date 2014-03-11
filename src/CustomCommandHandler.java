import java.util.ArrayList;


class CustomCommandHandler {
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
	
	protected static ArrayList<ArrayList<String>> customCommandList = FileManager.readCustomCommandsFromFile();
	
	protected static boolean isCustomCommand(String keyword, String commandType) {
		for (int i = 0; i < customCommandList.size(); i++) {
			if (customCommandList.get(i).get(0).equals(commandType)) {
				return customCommandList.get(i).contains(keyword);
			}
		}
		
		return false;
	}
	
	protected static Feedback addCustomCommand(String userCommand, String commandType) {
		if (isDuplicateCommand(userCommand)) {
			return new Feedback(MESSAGE_CUSTOM_DUPLICATE, false);
		}
		
		HistoryHandler.pushUndoStack();
		//customCommandList.get(index).add(command);
		FileManager.writeCustomCommandsToFile(customCommandList);
		return new Feedback(MESSAGE_CUSTOM_SUCCESS, false);
	}

	private static boolean isDuplicateCommand(String command) {
		for (int i = 0; i < customCommandList.size(); i++) {
			if (customCommandList.get(i).contains(command)) {
				return true;
			}
		}
		return false;
	}
	/*
	private static void addToCustomList(String command, String type) {
		
	}
	*/
	
	protected static Feedback deleteCustomCommand(String userCommand) {
		HistoryHandler.pushUndoStack();
		for (int i = 0; i < customCommandList.size(); i++) {
			if (customCommandList.remove(userCommand)) {
				FileManager.writeCustomCommandsToFile(customCommandList);
				return new Feedback(userCommand + MESSAGE_CUSTOM_DELETED, false);
			}
		}
		
		return new Feedback(MESSAGE_CUSTOM_NONEXISTANT, false);
	}
}
