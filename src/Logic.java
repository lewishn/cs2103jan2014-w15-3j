import java.util.*;
import java.lang.*;
import java.io.*;

public class Logic {
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
	private static final String MESSAGE_COMMAND = "command: ";
	
	private static ArrayList<Task> list;
	static String FILE_NAME = "";
	
	enum userCommands{
		ADD, DISPLAY, DELETE, SEARCH, UPDATE, EXIT, INVALID, UNDO
	};
	
	/*
	private static void createTextFile() throws IOException {
		File fileName = new File("todolist");
		BufferedWriter outputFile;

		boolean hasFile = fileName.exists();

		if(!hasFile){
			outputFile = new BufferedWriter(new FileWriter(fileName));
			outputFile.close();
		}
	}*/

	private static String executeAdd(Task task) {
		if(!isNullString(task)) {
			list.add(task);
			return String.format(MESSAGE_ADDED, FILE_NAME, task.getDescription());
		} else {
			return MESSAGE_INVALID_ADD;
		}	
	}
	
	private static void displayTaskList(File fileName) throws IOException {
		if(list.isEmpty())	
			System.out.println(fileName + " is empty");
		else{
			int lineNumber = 1;
			int numberOfLineInText = list.size();

			while(lineNumber <= numberOfLineInText){
				int lineNumberInArray = lineNumber - 1;
				Task line = list.get(lineNumberInArray);
				System.out.println(lineNumber + ". " + line);
				lineNumber++;
			}
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
	private static void invalidCommand() 
	{
		System.out.printf(MESSAGE_INVALID);
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
}
