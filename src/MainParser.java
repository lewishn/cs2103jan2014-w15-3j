import java.util.*;

//TODO : ADD, UPDATE, SEARCH, CUSTOM, HELP

class MainParser {
	protected static String[] initialParse(String userInput) {
		String[] userCommand =new String[2];
		userCommand = userInput.split(" ", 2);
		return userCommand;
	}

	protected static String[] determineTask (String userTaskDescription) {
		String[] information = new String[5];	// floatingTask, start Date, start time, end date, end time
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
			information[0] = "non-floating task";
		} else {
			information[0] = "floating task";
		}

		return information;
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
