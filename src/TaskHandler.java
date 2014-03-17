import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Stack;

import org.joda.time.DateTime;

class TaskHandler {
	private static final String MESSAGE_ADDED_TASK = "Added \"%s\" ";
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
			if (fields[Task.START_TIME] == null) {
				//floating task
				
			} else if (fields[Task.END_DATE] == null) {
				start = DateParser.setDate();
				start = TimeParser.setTime(start, fields[Task.START_TIME]);
				
				//Task with today's date, with start and end time. Otherwise, no end time
				if (fields[Task.START_TIME] != null) {
					end = DateParser.setDate();
					end = TimeParser.setTime(start, fields[Task.END_TIME]);
				} 
			}
		} else {
			start = DateParser.setDate(fields[Task.START_DATE]);
			if (fields[Task.START_TIME] == null) {
				if (fields[Task.END_DATE] == null) {
					//task with start date and end date set sa the same day
					start = TimeParser.setTime(start, MINUTE_FIRST);
					end = DateParser.setDate(fields[Task.START_DATE]);
					end = TimeParser.setTime(end, MINUTE_LAST);
				} else {
					// task with start date only
					start = TimeParser.setTime(start, MINUTE_LAST);
				}
			} else {
				start = TimeParser.setTime(start, fields[Task.START_TIME]);
				if (fields[Task.END_DATE] == null) {
					// task with same end and start dates, with different start and end times.
					if (fields[Task.END_TIME] != null) {
						end = DateParser.setDate(fields[Task.START_DATE]);
						end = TimeParser.setTime(end, fields[Task.END_TIME]);
					}
				} else {
					if (fields[Task.END_TIME] == null) {
						// task with start and end dates, start time , but no specific end time.
						end = DateParser.setDate(fields[Task.END_DATE]);
						end = TimeParser.setTime(end, MINUTE_LAST);
					} else {
						// full task with start and end time/dates
						end = DateParser.setDate(fields[Task.END_DATE]);
						end = TimeParser.setTime(end, fields[Task.END_TIME]);
					}
				}
			}
		}

		return new Task(input, start, end);
	}
	
	/**
	 * Updates a task entirely
	 * @param update
	 * @return a Feedback object to be shown to the user
	 */
	protected static Feedback updateTask(String update) {
		String updateNumber = CommandParser.getUserCommandType(update);
		String updateDesc = CommandParser.getUserCommandDesc(update);
		
		if (isInteger(updateNumber)) {
			Integer index = Integer.parseInt(updateNumber);
			HistoryHandler.pushUndoStack();
			Task.getList().remove(index - 1);
			addTask(updateDesc);
			return new Feedback(MESSAGE_UPDATE_TASK + "\n", false);
		}
		
		return new Feedback(MESSAGE_UPDATE_FAIL + "\n", false);
	}
	
	/**
	 * Displays all the tasks in order
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
}