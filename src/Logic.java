import java.text.Collator;
import java.util.*;
import java.lang.*;
import java.io.*;

public class Logic{
	private static final String MESSAGE_INVALID = "Invalid command.\nPlease input the available command: \n 1. add\n 2. display\n 3. update\n 4. delete\n";
	private static final String MESSAGE_WELCOME = "Welcome to TextBuddy. %s is ready for use";
	private static final String MESSAGE_ADDED = "added to my %s: \"%s\"";
	private static final String MESSAGE_DELETED = "deleted from %s: \"%s\"";
	private static final String MESSAGE_EMPTY = "%s is empty";
	private static final String MESSAGE_DISPLAY = "%d. %s";
	private static final String MESSAGE_CLEAR = "all content deleted from %s";
	private static final String MESSAGE_SORTED = "content in %s sorted";
	private static final String MESSAGE_SEARCH_FOUND = "\"%s\" found:\n%s";
	private static final String MESSAGE_SEARCH_NOT_FOUND = "\"%s\" not found in %s";
	private static final String MESSAGE_ERROR = "An error has occured, please restart the program";
	private static final String MESSAGE_INVALID_ARGUMENT = "Usage: java TextBuddy <file name>";
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
	
	private static final String MESSAGE_CUSTOM_DUPLICATE = "Sorry, but this word is already in use.";
	private static final String MESSAGE_CUSTOM_SUCCESS = " has been successfully added to the command list.";
	private static final String MESSAGE_CUSTOM_NONEXISTANT = "Error deleting. There is no such word in the command list.";
	private static final String MESSAGE_CUSTOM_DELETED = " has been successfully deleted from the command list.";
	
	private static ArrayList<Task> taskList;
	private static ArrayList<ArrayList<String>> customCommandList;
	private static Stack<ArrayList<Task>> taskUndoStack;
	private static Stack<ArrayList<ArrayList<String>>> commandUndoStack;
	
	protected static Task createTask(String description, Calendar startOfTask, Calendar endOfTask){
		Task task = new Task(description, startOfTask, endOfTask);
		return task;
	}
	
	private static void executeAdd(Task task) {
		if(!isNullString(task)) {
			taskList.add(task);
			executeSort();
			DoThings.printFeedback(String.format(MESSAGE_ADDED, FILE_NAME, task.getDescription()));
		} else {
			DoThings.printFeedback(MESSAGE_INVALID_ADD);
		}	
	}
	
	private static boolean isNullString(Task task) {
		if(task.getDescription() == null) {
			return true;
		}
		if(task.getDescription().equals("")) {
			return true;
		} else {
			return false;
		}
	}
	
	private static void executeClear() {
		taskList = new ArrayList<Task>();
		DoThings.printFeedback(String.format(MESSAGE_CLEAR, FILE_NAME));
	}
	
	private static void executeSort(){
		java.util.Collections.sort(taskList, Collator.getInstance());

	}
	
	private static void executeDisplay() {		
		
		if(fileIsEmpty()) {
			DoThings.printFeedback(String.format(MESSAGE_EMPTY, FILE_NAME));
		}
		else{
			executeSort();
			String contentToDisplay = concatContentToDisplay();
			DoThings.printFeedback(contentToDisplay);
		}
	}
	
	private static String concatContentToDisplay() {
		String contentToDisplay="";
		int index = 0;
		for(int i = 1; i <= taskList.size(); i++) {
			if(index==0){
				contentToDisplay = contentToDisplay.concat(String.format(MESSAGE_DISPLAY, i, taskList.get(i-1)));
				index++;
			}
			else{
				contentToDisplay = contentToDisplay.concat("\n"+String.format(MESSAGE_DISPLAY, i, taskList.get(i-1)));
			}
			index++;
		}
		return contentToDisplay;
	}
	
	private static void executeDelete(int index) {
		executeDisplay();
		// Method will exit if index is out of range
		if(unableToDelete(index)) {
			DoThings.printFeedback(String.format(MESSAGE_INVALID_DELETE_NUMBER));
		}
		else{
			String deletedString = taskList.get(index).getDescription();
			taskList.remove(index);
			executeSort();
			DoThings.printFeedback(String.format(MESSAGE_DELETED, FILE_NAME, deletedString));
		}
	}
	
	private static boolean unableToDelete(int index) {
		if(index + 1 <= taskList.size() && taskList.size() > 0 && index >= 0) {
			return false;
		} else {
			return true;
		}
	}
	
	private static void executeUpdate(int index, String textToBeUpdated, Calendar startOfTask, Calendar endOfTask){
		
	}
	
	private static boolean fileIsEmpty() {
		if(taskList.isEmpty()) {
			return true;
		} else {
			return false;
		}
	}
	
	/////////////////////////////////////////////////////////////////
	protected static void addCustomCommand(int index, String command) {
		if (isDuplicateCommand(command)) {
			DoThings.printFeedbackLn(MESSAGE_CUSTOM_DUPLICATE);
			return;
		}
		
		pushUndoStack();
		customCommandList.get(index).add(command);
		DiskIO.writeCustomCommands(customCommandList);
		DoThings.printFeedbackLn(MESSAGE_CUSTOM_SUCCESS);
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
				DoThings.printFeedbackLn(command + MESSAGE_CUSTOM_DELETED);
				wordExists = true;
			}
		}
		
		if (!wordExists) {
			DoThings.printFeedbackLn(MESSAGE_CUSTOM_NONEXISTANT);
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
  	enum UPDATE_TYPE{
		TIME, DATE, DESCRIPTION
	};
  
  
 	private static void executeUpdate(int index, String update, String updateText){
		executeDisplay();
		UPDATE_TYPE update_type = determineUpdateType(update);
		switch(update_type){
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
		if(inputString.equalsIgnoreCase("time")){
			return UPDATE_TYPE.TIME;
		}
		else if(inputString.equalsIgnoreCase("date")){
			return UPDATE_TYPE.DATE;
		}
		else{
			return UPDATE_TYPE.DESCRIPTION;
		}
	}
*/	
	
}


