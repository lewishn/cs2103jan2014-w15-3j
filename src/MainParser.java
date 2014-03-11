import org.joda.time.DateTime;

//TODO : ADD, UPDATE, SEARCH, CUSTOM, HELP

class MainParser {
	private static final String FLOATING_TASK = "floating task";
	private static final String NON_FLOATING_TASK_SINGLE_DATE = "non-floating task with one date";
	private static final String NON_FLOATING_TASK_SINGLE_DATE_NO_TIME = "non-floating task with one date and no time";
	private static final String NON_FLOATING_TASK_DOUBLE_DATE = "non-floating task with two dates";
	private static final String NON_FLOATING_TASK_DOUBLE_DATE_NO_TIME = "non-floating task with two dates and no time";
	private static final String ERROR_TASK = "wrong format";

	private static final String DEFAULT_TIME="2359";
	
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
		if(numOfDates==1 && numOfTime ==1) {
			information[0] = NON_FLOATING_TASK_SINGLE_DATE;
		} else if(numOfDates==1 && numOfTime==0) {
			information[0] = NON_FLOATING_TASK_SINGLE_DATE_NO_TIME;
		}	else if(numOfDates>=2 && numOfTime==2) {
			information[0] = NON_FLOATING_TASK_DOUBLE_DATE;
		} else if(numOfDates==2 && numOfTime==0) {
			information[0] = NON_FLOATING_TASK_DOUBLE_DATE_NO_TIME;
		}	else if(numOfDates==0 && numOfTime==0) {
			information[0] = FLOATING_TASK;
		} else {
			information[0] = ERROR_TASK;
		}

		return information;
	}
	
	protected static Task processTaskInformation(Task userTask, String[] taskInformation) {
		String type = taskInformation[0];
		
		if(type.equals(NON_FLOATING_TASK_SINGLE_DATE)) {
			userTask.setEndDateTime(editTimeOfTask(taskInformation[1],taskInformation[2]));
		} else if(type.equals(NON_FLOATING_TASK_SINGLE_DATE_NO_TIME)) {
			userTask.setEndDateTime(editTimeOfTask(taskInformation[1],DEFAULT_TIME));
		} else if(type.equals(NON_FLOATING_TASK_DOUBLE_DATE)) {
			userTask.setStartDateTime(editTimeOfTask(taskInformation[1],taskInformation[2]));
			userTask.setEndDateTime(editTimeOfTask(taskInformation[3],taskInformation[4]));
		} else if (type.equals(NON_FLOATING_TASK_DOUBLE_DATE_NO_TIME)) {
			userTask.setStartDateTime(editTimeOfTask(taskInformation[1],DEFAULT_TIME));
			userTask.setEndDateTime(editTimeOfTask(taskInformation[3],DEFAULT_TIME));
		} else {
			// All other format considered floating task
		}
		return userTask;
	}
	private static DateTime editTimeOfTask(String stringDate, String time) {
		DateTime date = DateParse.setDate(stringDate);
		date = TimeParse.setTime(date, time);
		return date;
	}
}
