//@author A0100234E
package dothings.logic;

import java.util.*;
import java.text.*;

import org.joda.time.*;

import dothings.storage.FileManager;

public class Task implements Comparable<Task>{
	private static final String LOG_SAVING_TASK_LIST = "Saving task list.";

	private static final String LOG_LOADING_TASK_LIST = "Loading task list.";

	private static final String FILE_TASK = "tasks.txt";

	public static final int START_DATE = 0;
	public static final int START_TIME = 1;
	public static final int END_DATE = 2;
	public static final int END_TIME = 3;
	public static final int TASK_FIELD_SIZE = 4;

	private static final String DELIMITER = " ~~ ";
	private static final String NULL_START = "NO_START_TIME";
	private static final String NULL_END = "NO_END_TIME";
	private static final String NULL_ALIAS = "NO_ALIAS";
	private static final String DATE_DISPLAY_FORMAT_1 = "%s %s to %s %s";
	private static final String DATE_DISPLAY_FORMAT_2 = "%s %s";

	private static ArrayList<Task> taskList = loadTasks();

	private DateTime startDateTime;
	private DateTime endDateTime;
	private String description;
	private boolean status;
	private String alias;

	/**
	 * Constructs and initializes a task with the specified description.
	 * @param desc the description of the new task object.
	 */
	public Task(String desc) {
		description = desc;
		status = false;
	}

	/**
	 * Constructs and initializes a task with the same fields as the input task object.
	 * @param task the input task object.
	 */
	public Task(Task task) {
		startDateTime = task.startDateTime;
		endDateTime = task.endDateTime;
		status = task.status;
		description = task.description;
		alias = task.alias;
	}

	/**
	 * Constructs and initializes a new task with the specified fields above.
	 * @param desc the description of the new task object.
	 * @param start the start time of the new task object.
	 * @param end the end time of the new task object.
	 * @param name the name of the new task object.
	 */
	public Task(String desc, DateTime start, DateTime end, String name) {
		startDateTime = start;
		endDateTime = end;
		status = false;
		description = desc;
		alias = name;
	}
	
	/**
	 * Constructs and initializes a new task with the specified fields above.
	 * @param desc the description of the new task object.
	 * @param start the start time of the new task object.
	 * @param end the end time of the new task object.
	 * @param name the name of the new task object.
	 * @param stat the status of the task, whether it is marked as complete or not.
	 */
	public Task(String desc, DateTime start, DateTime end, String name, boolean stat) {
		startDateTime = start;
		endDateTime = end;
		status = stat;
		description = desc;
		alias = name;
	}
	
	/**
	 * Sets the description of this task to the specified description.
	 * @param desc the specified description.
	 */
	public void setDescription(String desc) {
		description = desc;
	}
	
	/**
	 * Sets the start time of this task to the specified time.
	 * @param start the specified start time.
	 */
	public void setStartDateTime(DateTime start) {
		startDateTime = start;
	}
	
	/**
	 * Sets the end time of this task to to the specified time.
	 * @param end the specified end time.
	 */
	public void setEndDateTime(DateTime end) {
		endDateTime = end; 
	}
	
	/**
	 * Toggles the status of this task.
	 */
	public void toggleStatus() {
		status = !status;
	}
	
	/**
	 * Sets the alias of this task to the specified alias.
	 * @param alias
	 */
	public void setAlias(String alias) {	
		this.alias = alias;
	}
	
	/**
	 * Returns the description of this task.
	 * @return the description of this task.
	 */
	public String getDescription() {
		return description;
	}
	
	/**
	 * Returns the start time of this task.
	 * @return the start time of this task.
	 */
	public DateTime getStartDateTime() {
		return startDateTime;
	}
	
	/**
	 * Returns the end time of this task.
	 * @return the end time of this task.
	 */
	public DateTime getEndDateTime() {
		return endDateTime;
	}
	
	/**
	 * Returns the status of this task.
	 * @return true if this task is marked as complete; false otherwise.
	 */
	public boolean getStatus() {
		return status;
	}
	
	/**
	 * Returns the alias of this task.
	 * @return the alias of this task.
	 */
	public String getAlias() {
		return alias;
	}
	/**
	 * Returns the entire list of tasks.
	 * @return the entire list of tasks.
	 */
	public static ArrayList<Task> getList() {
		return taskList;
	}
	
	public static ArrayList<Task> getCloneList() {
		ArrayList<Task> clone = new ArrayList<Task>();
		for (int i = 0; i < taskList.size(); i++) {
			clone.add(new Task(taskList.get(i)));
		}
		
		return clone;
	}
	
	/**
	 * Replaces the current task list with the specified task list.
	 * @param list
	 */
	public static void setList(ArrayList<Task> list) {
		taskList = list;
	}
	
	/**
	 * Sorts the task list.
	 */
	public static void sortList() {
		Collections.sort(taskList);
	}	
	/**
	 * Checks whether this task is overdue.
	 * @return true if this task is overdue; false otherwise.
	 */
	public boolean isOverdue() {
		if (endDateTime != null) {
			return endDateTime.isBeforeNow();
		} else if (startDateTime != null) {
			return startDateTime.isBeforeNow();
		}

		return false;
	}
	/**
	 * Checks whether this task is due today.
	 * @return true if this task is due today; false otherwise.
	 */
	public boolean isToday() {
		DateTime today = (new DateTime()).withTimeAtStartOfDay();
		if (startDateTime != null) {
			return today.equals(startDateTime.withTimeAtStartOfDay());
		} else if (endDateTime != null) {
			return today.equals(endDateTime.withTimeAtStartOfDay());
		}

		return false;
	}
	/**
	 * Checks whether this task has no schedule.
	 * @return true if this task has no schedule; false otherwise;
	 */
	public boolean isUnscheduled() {
		return startDateTime == null && endDateTime == null;
	}
	/**
	 * Separates the different parameters from the input
	 * @param line a string input to be separated.
	 * @return a new task object with the specified fields.
	 */
	public static Task parseTaskFromString(String line) {
		String[] tokens = line.split(DELIMITER);
		DateTime start = (tokens[0].equals(NULL_START)) ? null : new DateTime(tokens[0]);
		DateTime end = (tokens[1].equals(NULL_END)) ? null : new DateTime(tokens[1]);
		String name = (tokens[2].equals(NULL_ALIAS)) ? null : tokens[2]; 
		boolean stat = (tokens[3].equals("true")) ? true : false;
		String desc = tokens[4];

		return new Task(desc, start, end, name , stat);
	}

	@Override
	/**
	 * Returns the string representation of the task.
	 * @return the string representation of the task.
	 */
	public String toString() {
		String start = (startDateTime == null) ? NULL_START : startDateTime.toString();
		String end = (endDateTime == null) ? NULL_END : endDateTime.toString();
		String taskAlias = (alias == null) ? NULL_ALIAS : alias;

		return start + DELIMITER + end + DELIMITER + taskAlias + DELIMITER + status + DELIMITER + description;
	}
	/**
	 * Returns the start and end time of this task.
	 * @return the start and end time of this task.
	 */
	public String getDateTimeString() {
		if (startDateTime != null && endDateTime != null) {
			return String.format(DATE_DISPLAY_FORMAT_1, getDateString(startDateTime), getTimeString(startDateTime), getDateString(endDateTime), getTimeString(endDateTime));
		} else if (startDateTime != null) {
			return String.format(DATE_DISPLAY_FORMAT_2, getDateString(startDateTime), getTimeString(startDateTime));
		} else if (endDateTime != null) {
			return String.format(DATE_DISPLAY_FORMAT_2, getDateString(endDateTime), getTimeString(endDateTime));
		} else {
			return "";
		}
	}
	/**
	 * Obtains the time from the specified DateTime object.
	 * @param date the specified DateTime object.
	 * @return the time portion of the specified DateTime object.
	 */
	public static String getTimeString(DateTime date) {
		if (date == null) {
			return null;
		}

		DecimalFormat df = new DecimalFormat("00");
		String time = df.format(date.getHourOfDay()) + ":" + df.format(date.getMinuteOfHour());
		return time;
	}
	/**
	 * Obtains the date from the specified DateTime object.
	 * @param date
	 * @return the date portion of the specified DateTime object.
	 */
	public static String getDateString(DateTime date) {
		if (date == null) {
			return null;
		}

		return date.toString("dd MMM YYYY");
	}

	@Override
	/**
	 * Checks whether this task's time is before or after the specified task's time.
	 * @param task an object to be compared with this task.
	 */
	public int compareTo(Task task) {
		if (this.startDateTime != null && task.startDateTime != null) {
			return this.startDateTime.compareTo(task.startDateTime);

		} else if (this.startDateTime == null && task.startDateTime != null) {
			if(this.endDateTime != null) {
				return this.endDateTime.compareTo(task.startDateTime);
			}
			return 1;

		} else if (this.startDateTime != null && task.startDateTime == null) {
			if(task.endDateTime != null) {
				return this.startDateTime.compareTo(task.endDateTime);
			}
			return -1;

		} else {
			if (this.endDateTime == null && task.endDateTime == null) {
				return this.description.compareToIgnoreCase(task.description);
			} else if (this.endDateTime != null && task.endDateTime == null) {
				return -1;
			} else if (this.endDateTime == null && task.endDateTime != null) {
				return 1;
			} else {
				return this.endDateTime.compareTo(task.endDateTime);
			}
		}
	}

	/**
	 * Checks whether the specified string is a valid alias.
	 * @param alias the string to be checked.
	 * @return true if the alias is valid; false otherwise.
	 */
	public static boolean isAliasValid(String alias) {
		if (alias == null || alias.length() == 0 || alias.equals("")) {
			return false;
		}

		if (getTaskIndexFromAlias(alias) < 0) {
			return false;
		}
		if (alias.equalsIgnoreCase("completed") || alias.equalsIgnoreCase("all")) {
			return false;
		}


		return true;
	}
	/**
	 * Obtains the task index from the specified string.
	 * @param alias the string from which the task index is retrieved.
	 * @return the task index; -1 otherwise.
	 */
	public static int getTaskIndexFromAlias(String alias) {
		for (int i = 0; i < taskList.size(); i++) {
			if (taskList.get(i).getAlias() == null) {
				continue;
			}
			if (alias.equals(taskList.get(i).getAlias())) {
				return i;
			}
		}

		return -1;
	}
	/**
	 * Saves the task list to text file.
	 */
	public static void saveTasks() {
		FileManager.log(LOG_SAVING_TASK_LIST);
		ArrayList<String> listToSave = new ArrayList<String>();
		for (int i = 0; i < taskList.size(); i++) {
			String taskStringForm = taskList.get(i).toString();
			listToSave.add(taskStringForm);
		}

		FileManager.writeToFile(FILE_TASK, listToSave);
	}
	/**
	 * Loads the task list from the text file.
	 * @return the task list. 
	 */
	public static ArrayList<Task> loadTasks() {
		FileManager.log(LOG_LOADING_TASK_LIST);
		ArrayList<Task> listOfTasks = new ArrayList<Task>();
		ArrayList<String> list = FileManager.readFromFile(FILE_TASK);

		for (int i = 0; i < list.size(); i++) {
			String line = list.get(i);
			listOfTasks.add(parseTaskFromString(line));
		}

		return listOfTasks;
	}
}