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
	
	/*
	private static userCommands checkCommandType(String commandInputted) {
		if (commandInputted.equalsIgnoreCase("ADD")){
			return userCommands.ADD;
		}
		else if (commandInputted.equalsIgnoreCase("DISPLAY")){
			return userCommands.DISPLAY;
		}
		else if (commandInputted.equalsIgnoreCase("DELETE")){
			return userCommands.DELETE;
		}
		else if (commandInputted.equalsIgnoreCase("UPDATE")){
			return userCommands.UPDATE;
		}
		else if (commandInputted.equalsIgnoreCase("UNDO")){
			return userCommands.UNDO;
		}
		else if (commandInputted.equalsIgnoreCase("EXIT")){
			return userCommands.EXIT;
		}
		else{
			return userCommands.INVALID;
		}
		
	}
	private static void executeCommands(Scanner scanner, File fileName)
			throws IOException, Error {
		final boolean isExecuting = true;

		while(isExecuting){	
			System.out.print("command: ");
			userCommands command = checkCommandType(scanner.next());

			switch(command)	{
			}
			case ADD:
				String userInputLine = readUserInputForAdd();
				addLine(userInputLine, fileName);
				break;

			case DISPLAY:
				displayTaskList(fileName);
				break;

			case DELETE:
				int lineNumberToBeDeleted = scanner.nextInt();
				deleteLineNumber(lineNumberToBeDeleted, fileName);
				break;

			case SORT:
				sortList(fileName);
				break;

			case EXIT:
				System.exit(0);
				break;

			case SEARCH:
				String keyWord=scanner.next();
				int result=searchList(keyWord);
				if (result==1) System.out.println(MESSAGE_SEARCH_FOUND);
				else System.out.println(MESSAGE_SEARCH_NOTFOUND);
				break;

			case INVALID:
				invalidCommand();
				break;

			default:
				throw new Error("There is an error in the command");
				
			}
			
		}
		
	}
	
	*/
	/*
	private static void addLine(String userInputLine, File fileName) throws IOException {
		//tasksToBeWritten.add(userInputLine);
		updateFileOutput(fileName);

		System.out.println("added to " + fileName + ": \"" + userInputLine + "\"");
	}*/
	
	private static String executeAdd(String inputString) {
		if(!isNullString(inputString)) {
			list.add(inputString);
			return String.format(MESSAGE_ADDED, FILE_NAME, inputString);
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

	private static boolean isNullString(String inputString) {
		if(inputString == null) {
			return true;
		}
		if(inputString.equals("")) {
			return true;
		} else {
			return false;
		}
	}
}
