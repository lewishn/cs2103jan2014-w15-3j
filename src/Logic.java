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
	private static ArrayList<Task> list;
	static String FILE_NAME = "";
	
	enum UPDATE_TYPE{
		TIME, DATE, DESCRIPTION
	};
	
	private static Task createTask(String description, int date, int month, int year, int start, int end){
		Task task = new Task(description, date, month, year, start, end);
		return task;
	}
	
	private static void executeAdd(Task task) {
		if(!isNullString(task)) {
			list.add(task);
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
		list = new ArrayList<Task>();
		DoThings.printFeedback(String.format(MESSAGE_CLEAR, FILE_NAME));
	}
	
	private static void executeSort(){
		java.util.Collections.sort(list, Collator.getInstance());

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
		for(int i = 1; i <= list.size(); i++) {
			if(index==0){
				contentToDisplay = contentToDisplay.concat(String.format(MESSAGE_DISPLAY, i, list.get(i-1)));
				index++;
			}
			else{
				contentToDisplay = contentToDisplay.concat("\n"+String.format(MESSAGE_DISPLAY, i, list.get(i-1)));
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
			String deletedString = list.get(index).getDescription();
			list.remove(index);
			DoThings.printFeedback(String.format(MESSAGE_DELETED, FILE_NAME, deletedString));
		}
	}
	
	private static boolean unableToDelete(int index) {
		if(index + 1 <= list.size() && list.size() > 0 && index >= 0) {
			return false;
		} else {
			return true;
		}
	}
	
	private static void executeUpdate(int index, String update, String updateText){
		executeDisplay();
		UPDATE_TYPE update_type = determineUpdateType(update);
		switch(update_type){
		case TIME:
			break;
		case DATE:
			break;
		case DESCRIPTION:
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
	
	private static boolean fileIsEmpty() {
		if(list.isEmpty()) {
			return true;
		} else {
			return false;
		}
	}
	
	/*
	private static void createTextFile() throws IOException {
		File fileName = new File("todolist");
		BufferedWriter outputFile;

		boolean hasFile = fileName.exists();

		if(!hasFile){
			outputFile = new BufferedWriter(new FileWriter(fileName));
			outputFile.close();
		}
	}
	
	private static void updateFileOutput(File fileName) throws IOException {
		int numberOfLine = list.size();

		BufferedWriter outputFile = new BufferedWriter(new FileWriter(fileName));

		for(int i=0;i<numberOfLine;i++){
			outputFile.write(list.get(i) + "\n");
		}

		outputFile.close();
	}
	*/
}


