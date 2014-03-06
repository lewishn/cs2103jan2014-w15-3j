import java.util.Arrays;
import java.util.ArrayList;
import java.util.Date;

//TODO : ADD, UPDATE, SEARCH, CUSTOM, HELP

class MainParser {
	protected static ArrayList<String> initialParse(String userInput) {
		ArryaList<String> userCommand =new ArrayList<String>();
		userCommand = Arrays.asList(userInput.split(" ", 2));
		return userCommand;
	}

	protected static String[] determineTask (String userTaskDescription) {
		Boolean floatingTask=true;
		String[5] information;	// floatingTask, start Date, start time, end date, end time
		String upperCasedDescription = userTaskDescription.trim().toUpperCase();
		String[] listOfUserInput = upperCasedDescription.split("");
		int numOfWords = listOfUserInput.length;
		int numOfDates = 0;
		int numOfTime = 0;

		for(int i=0; i<numOfWords; i++) {
			if(DateParse.isDate(listOfUserInput[i])) {
				numOfDates++;
				if(numOfDates==1) {
					information[1] = listOfUserInput[i];
				} else if(numOfDates==2) {
					information[3] = listOfUserInput[i];
				} 
			}
			if(TimeParse.isValidFormat(listOfUserInput[i])) {
				numOfTime++;
				if(numOfTime==1) {
					information[2] = listOfUserInput[i];
				} else if(numOfTime==2) {
					information[4] = listOfUserInput[i];
				} 
			}
		}
		if(numOfDates>0) {
			information[0] = "non-floating task"
		} else {
			information[0] = "floating task"
		}

		retrun information;
	}


































	private static void addTasks(String command) {
		Date startDate;
		Date endDate;
		
		//deadline
		/*
		int index = tokens.indexOf("by");
		if (index >= 0) {
			if (DateTimeParse.isDate(tokens.get(index))) {
				//date = 
			}
		}
		*/
	}

	private static void listTasks(String command) {
		if (command.equals("") || command == null) {
			//call display()
		} else {
			//parse date
			//call display(date)
		}
	}
	
	private static void deleteTasks(String command) {
		if (isInteger(command)) {
			//Logic.deleteTask(Integer.parseInt(tokens.get(i));
			DoThings.printFeedbackLn(MESSAGE_DELETE_SUCCESS + command);
		} else {
			DoThings.printFeedbackLn(command + MESSAGE_INVALID_DELETE);
		}
	}
	
	private static void undoActions(String command) {
		if (command.equals("") || command == null) {
			Logic.undoCommand();
		} else if (isInteger(command)) {
			for (int i = 0; i < Integer.parseInt(command); i++) {
				Logic.undoCommand();
			}
		} else {
			//error?
		}
	}
	
	//
	private static void searchTasks(){
		// to be implemented later
	}
	
	private static void addCustomCommands() {
		
	}
	
	private static void deleteCustomCommands() {
		
	}
	
	private static void displayHelp() {
		
	}
	
	public static boolean isInteger(String str) {
		try {
			Integer.parseInt(str);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
	
}
