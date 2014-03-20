import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Stack;

import org.joda.time.DateTime;

class TaskHandler {
	private static final String MESSAGE_ADDED_TASK = "Added \"%s\".";
	private static final String MESSAGE_EMPTY_TASKS = "You have no tasks scheduled.";
	private static final String MESSAGE_UPDATE_TASK = "Task has been updated";
	private static final String MESSAGE_UPDATE_FAIL = "Error, please enter a valid task number to update";
	private static final String MESSAGE_LIST_NUMBER = "%d. %s";
	private static final String MESSAGE_TASK_DELETED = "\"%s\" has been deleted from the task list.";
	private static final String MESSAGE_TASK_DELETED_ALL = "All tasks have been deleted from the task list.";
	private static final String MESSAGE_INVALID_DELETE = "No such task, please enter a valid number to delete.";
	private static final String MESSAGE_DISPLAY_HEADER = "[No.][Start Time][End Time][Task Description]";
	
	private static final String MINUTE_LAST = "23:59";
	private static final String MINUTE_FIRST = "00:00";
	
	/**
	 * Add a task using the user input
	 * @param userInput
	 * @return a Feedback object to be shown to the user
	 */
	protected static Feedback addTask(String userInput) {
		String[] inputTokens = userInput.split(" ");
		ArrayList<String> input = new ArrayList<String>(Arrays.asList(inputTokens));
		
		String[] fields = CommandParser.getTaskFields(input);
		Task newTask = createTask(fields, userInput);
		
		HistoryHandler.pushUndoStack();
		Task.getList().add(newTask);
		Task.sortList();
		Task.saveTasks();
		HistoryHandler.purgeRedoStack();
		return new Feedback(String.format(MESSAGE_ADDED_TASK, userInput) + "\n", false);
	}
	
	/**
	 * Reads the user input and time fields and creates a Task object
	 * @param fields
	 * @param input
	 * @return Task object
	 */
	private static Task createTask(String[] fields, String input) {
		DateTime start = null;
		DateTime end = null;
	
		if (fields[Task.START_DATE] == null) {
			if (fields[Task.START_TIME] != null) {
				start = DateParser.setDate();
				start = TimeParser.setTime(start, fields[Task.START_TIME]);
				
				// If null, "Deadline" Task with today's date
				// Else, Timed Task with today's date
				if (fields[Task.END_TIME] != null) {
					end = DateParser.setDate();
					end = TimeParser.setTime(start, fields[Task.END_TIME]);
				} 
			}
		} else {
			start = DateParser.setDate(fields[Task.START_DATE]);
			
			if (fields[Task.START_TIME] == null) {
				if (fields[Task.END_DATE] == null) {
					// "Deadline" Task with only start date
					start = TimeParser.setTime(start, MINUTE_LAST);
				} else {
					// Timed Task with start and end dates but no time
					start = TimeParser.setTime(start, MINUTE_FIRST);
					end = DateParser.setDate(fields[Task.END_DATE]);
					end = TimeParser.setTime(end, MINUTE_LAST);
				}
				
			} else {
				start = TimeParser.setTime(start, fields[Task.START_TIME]);
				
				if (fields[Task.END_DATE] == null) {
					if (fields[Task.END_TIME] != null) {
						// Timed Task with start and end times within one specific date
						end = DateParser.setDate(fields[Task.START_DATE]);
						end = TimeParser.setTime(end, fields[Task.END_TIME]);
					}
				} else {
					end = DateParser.setDate(fields[Task.END_DATE]);
					
					if (fields[Task.END_TIME] == null) {
						// Timed Task with start time, start and end dates, but no end time
						end = TimeParser.setTime(end, MINUTE_LAST);
					} else {
						// Timed Task with start and end time/dates
						end = TimeParser.setTime(end, fields[Task.END_TIME]);
					}
				}
			}
		}

		return new Task(input, start, end);
	}
	
	/**
	 * Updates a task in the specified field
	 * @param update
	 * @return a Feedback object to be shown to the user
	 */
	protected static Feedback updateTask(String update) {
		String updateNumber = CommandParser.getUserCommandType(update);
		String updateField = CommandParser.getUserCommandField(update);
		String updateDesc = CommandParser.getUserCommandDesc(update);
		Task newTask;
		if (isInteger(updateNumber)) {
			Integer index = Integer.parseInt(updateNumber);
			HistoryHandler.pushUndoStack();
			Task updatedTask=Task.getList().get(index-1);
			Task.getList().remove(index - 1);
			if (updateField=="start") {
				DateTime start=DateParser.setDate(updateDesc);
				start=TimeParser.setTime(start, updateDesc);
				newTask=new Task(updatedTask.getDescription(), start, updatedTask.getEndDateTime());
			} else if (updateField=="end") {
				DateTime end=DateParser.setDate(updateDesc);
				end=TimeParser.setTime(end	, updateDesc);
				newTask=new Task(updatedTask.getDescription(), updatedTask.getStartDateTime(), end);
			} else {
				newTask=new Task(updateDesc, updatedTask.getStartDateTime(), updatedTask.getEndDateTime());
			}
			Task.getList().add(newTask);
			Task.sortList();
			Task.saveTasks();
			return new Feedback(MESSAGE_UPDATE_TASK + "\n", false);
		}
		
		return new Feedback(MESSAGE_UPDATE_FAIL + "\n", false);
	}
	
	/**
	 * Displays all the tasks in order of date
	 * @return a Feedback Object to be shown to the user
	 */
	protected static Feedback listTasks() {
		Task.sortList();
		if (Task.getList().isEmpty()) {
			return new Feedback(MESSAGE_EMPTY_TASKS + "\n", false);
		} else {
			return new Feedback(getListOfTasks(), false);
		}
	}
	
	private static String getListOfTasks() {
		ArrayList<Task> list = Task.loadTasks();
		String stringList = MESSAGE_DISPLAY_HEADER + "\n";
		for (int i = 1; i <= list.size(); i++) {
			stringList += String.format(MESSAGE_LIST_NUMBER, i, list.get(i-1).toDisplayString()) + "\n";
		}
		return stringList;
	}

	/**
	 * Removes a task from the taskList
	 * @param taskNumber
	 * @return a Feedback object to be shown to the user
	 */
	protected static Feedback deleteTask(String taskNumber) {
		
		if(isInteger(taskNumber)) {
			int rowToDelete = Integer.parseInt(taskNumber) - 1;
			if (isOutOfDeleteRange(rowToDelete)) {
				return new Feedback(MESSAGE_INVALID_DELETE + "\n", false);
			}

			HistoryHandler.pushUndoStack();
			String deletedTask = Task.getList().get(rowToDelete).getDescription();
			Task.getList().remove(rowToDelete);
			Task.saveTasks();
			HistoryHandler.purgeRedoStack();
			return new Feedback(String.format(MESSAGE_TASK_DELETED, deletedTask) + "\n", false);
		}
			
		return new Feedback(MESSAGE_INVALID_DELETE, false);
	}
	
	private static boolean isOutOfDeleteRange(int index) {
		if (index >= 0 && index < Task.getList().size()) {
			return false;
		} else {
			return true;
		}
	}
	
	private static boolean isInteger(String str) {
		try {
			Integer.parseInt(str);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
	
	//search
	
	//test functions
	public static String testAdd(String command) {
		ArrayList<Task> list= new ArrayList<Task>();		
		return addTask(command).toString();
	}
		
}