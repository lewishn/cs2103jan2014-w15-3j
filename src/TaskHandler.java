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
		Task newTask = createTask(userInput);
		
		HistoryHandler.pushUndoStack();
		Task.getList().add(newTask);
		Task.sortList();
		Task.saveTasks();
		HistoryHandler.purgeRedoStack();
		return new Feedback(String.format(MESSAGE_ADDED_TASK, userInput) + "\n", false);
	}
	
	
	protected static Task createTask(String userInput) {
		String[] inputTokens = userInput.split(" ");
		ArrayList<String> input = new ArrayList<String>(Arrays.asList(inputTokens));
		
		String alias = CommandParser.getAliasFromDescription(userInput);		
		alias = (Task.isAliasInUse(alias) || isInteger(alias)) ? null : alias;
		userInput = CommandParser.removeDateTimeFromString(userInput);
		userInput = CommandParser.removeAliasFromDescription(userInput);
		
		String[] fields = CommandParser.getTaskFields(input);
		Task newTask = createTaskFromFields(fields, userInput, alias);
		
		return newTask;
	}
	
	/**
	 * Reads the user input and time fields and creates a Task object
	 * @param fields
	 * @param input
	 * @return Task object	
	 */
	private static Task createTaskFromFields(String[] fields, String input, String alias) {
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

		return new Task(input, start, end, alias);
	}
	
	/**
	 * Updates a task in the specified field
	 * Input format is "[task number/alias][field to update][update]
	 * @param update
	 * @return a Feedback object to be shown to the user
	 */
	protected static Feedback updateTask(String update) {	
		String taskID = CommandParser.getUserCommandType(update);
		String updateStringWithoutID = CommandParser.getUserCommandDesc(update);
		String updateField = CommandParser.getUserCommandType(updateStringWithoutID);
		String updateDesc = CommandParser.getUserCommandDesc(updateStringWithoutID);
		
		Task taskToUpdate = getTaskToUpdate(taskID);
		
		if (taskToUpdate == null) {
			return new Feedback(MESSAGE_UPDATE_FAIL, false);
		}
		
		if (updateField.equalsIgnoreCase("start") || updateField.equalsIgnoreCase("end") || updateField.equalsIgnoreCase("time")) {
			taskToUpdate = updateTaskTime(taskToUpdate, updateField, updateDesc);
		} else if (updateField.equals("alias")) {
			String[] tokens = updateDesc.split(" ");
			if (tokens.length <= 0) {
				return new Feedback("Invalid alias", false);
			}
			
			String alias = tokens[0];	
			if (Task.isAliasInUse(alias) || isInteger(alias)) {
				return new Feedback("Alias is already in use", false);
			} else {
				taskToUpdate.setAlias(alias);
			}
			
		} else if (updateField.equals("desc") || updateField.equals("description")) {
			taskToUpdate.setDescription(updateDesc);
		} else {
			taskToUpdate = createTask(updateStringWithoutID);
		}
		
		Task.getList().add(taskToUpdate);
		Task.sortList();
		Task.saveTasks();
		return new Feedback(MESSAGE_UPDATE_TASK + "\n", false);
	}
	
	private static Task updateTaskTime(Task task, String field, String update) {
		ArrayList<String> updateTokens = new ArrayList<String>(Arrays.asList(update.split(" ")));
		String[] timeFields = CommandParser.getTaskFields(updateTokens);
		
		if (field.equalsIgnoreCase("time")) {
			Task tempTask = createTask(update);
			task.setStartDateTime(tempTask.getStartDateTime());
			task.setEndDateTime(tempTask.getEndDateTime());
		} else {
			DateTime date = task.getStartDateTime();
			if (timeFields[Task.START_DATE] == null && timeFields[Task.START_TIME] == null) {
				return task;
			} else if (timeFields[Task.START_DATE] == null) {
				date = TimeParser.setTime(date, timeFields[Task.START_TIME]);
			} else if (timeFields[Task.START_TIME] == null) {
				date = DateParser.setDate(timeFields[Task.START_DATE]);
				date = TimeParser.setTime(date, MINUTE_LAST);
			}
			
			if (field.equalsIgnoreCase("start")) {
				task.setStartDateTime(date);
			} else {
				task.setEndDateTime(date);
			}
		}
		
		return task;
	}
	
	private static Task getTaskToUpdate(String taskID) {
		Task taskToUpdate = null;
		int index = -1;
		
		if(isInteger(taskID)) {
			index = Integer.parseInt(taskID) - 1;
		} else if (Task.isAliasInUse(taskID)) {
			index = Task.getTaskIndexFromAlias(taskID);
		}
		
		assert(index >= 0);
		taskToUpdate = Task.getList().get(index);
		Task.getList().remove(index);
		
		return taskToUpdate;
	}
	
	protected static Feedback updateTask2(String update) {
		String updateNumber = CommandParser.getUserCommandType(update);
		String updateDesc = CommandParser.getUserCommandDesc(update);
		String updateField = CommandParser.getUserCommandType(updateDesc);
		System.out.println(updateDesc);
		
		
		if (isInteger(updateNumber)) {
			Integer index = Integer.parseInt(updateNumber);
			HistoryHandler.pushUndoStack();
			Task updatedTask = Task.getList().get(index-1);
			Task.getList().remove(index - 1);
			if (updateField.equals("start")) {
				DateTime start = DateParser.setDate(updateDesc);
				start = TimeParser.setTime(start, updateDesc);
				updatedTask.setStartDateTime(start);
			
			} else if (updateField.equals("end")) {
				DateTime end = DateParser.setDate(updateDesc);
				end = TimeParser.setTime(end, updateDesc);
				updatedTask.setEndDateTime(end);
			
			} else {
				updatedTask = new Task(updateDesc, updatedTask.getStartDateTime(), updatedTask.getEndDateTime(), null);
			}
			
			Task.getList().add(updatedTask);
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