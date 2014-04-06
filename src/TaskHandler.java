import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.joda.time.DateTime;

class TaskHandler {
	
	private static final String MESSAGE_ADDED_TASK = "Added \"%s\".";
	private static final String MESSAGE_EMPTY_TASKS = "Your list is empty";
	private static final String MESSAGE_UPDATE_TASK = "Task has been updated.";	
	private static final String MESSAGE_LIST_NUMBER = "%d. %s";
	private static final String MESSAGE_LIST_INCOMPLETE = "Showing incomplete tasks";
	private static final String MESSAGE_LIST_COMPLETE = "Showing completed tasks";
	private static final String MESSAGE_LIST_OVERDUE = "Showing overdue tasks";
	private static final String MESSAGE_LIST_ALL = "Showing all tasks";	
	private static final String MESSAGE_DELETE_COMPLETE = "All completed tasks have been deleted.";
	private static final String MESSAGE_DELETE_ALL = "All tasks have been deleted.";
	private static final String MESSAGE_DELETE_SUCCESS = "All specified tasks have been deleted.";
	private static final String MESSAGE_TASK_MARK = "Tasks have been marked.";
	private static final String MESSAGE_ERROR_ADD_NO_DESC = "Please add a task description.";
	private static final String MESSAGE_ERROR_MARK_NO_TASK = "Nothing to mark.";
	private static final String MESSAGE_ERROR_DELETE_ARGUMENT = "Incorrect delete format.";
	private static final String MESSAGE_ERROR_UPDATE_NO_SUCH_TASK = "Please enter a valid task number to update.";
	private static final String MESSAGE_ERROR_UPDATE_ARGUMENT = "Incorrect update format.";
	private static final String MESSAGE_ERROR_START_AFTER_END ="Error, start time cannot be after end time.";
	private static final String MESSAGE_ERROR_TASK_DESC_EMPTY = "Error, task description cannot be empty.";
	private static final String MESSAGE_ERROR_ALIAS_IN_USE = "Alias is already in use";
	private static final String MESSAGE_ERROR_DELETE = "No such tasks. Please enter a valid number or alias to delete.";
	private static final String MESSAGE_ERROR_SEARCH = "Please enter a search key.";
	private static final String MESSAGE_ERROR_ALIAS = "Invalid alias";
	private static final String MINUTE_LAST = "23:59";
	private static final String MINUTE_FIRST = "00:00";
	
	
	//////////ADD Functionality//////////
	
	/**
	 * Add a task using the user input
	 * @param userInput
	 * @return a Feedback object to be shown to the user
	 */
	protected static Feedback addTask(String userInput) {
		if (!CommandParser.isInputValid(userInput, 1)) {
			return new Feedback(MESSAGE_ERROR_ADD_NO_DESC);
		}
		
		Task newTask = createTask(userInput);
		
		if (newTask == null) {
			return new Feedback(MESSAGE_ERROR_START_AFTER_END, true);
		} 
		
		if (newTask.getDescription().trim().length() == 0) {
			return new Feedback(MESSAGE_ERROR_TASK_DESC_EMPTY, true);
		}
		
		HistoryHandler.pushUndoStack();
		Task.getList().add(newTask);
		Task.sortList();
		Task.saveTasks();
		HistoryHandler.purgeRedoStack();
		return new Feedback(String.format(MESSAGE_ADDED_TASK, userInput));
	}
	
	
	protected static Task createTask(String userInput) {
		String[] inputTokens = userInput.split("\\s+");
		ArrayList<String> input = new ArrayList<String>(Arrays.asList(inputTokens));
		
		String alias = CommandParser.getAliasFromDescription(userInput);		
		alias = (Task.isAliasValid(alias) || isInteger(alias)) ? null : alias;
		userInput = CommandParser.removeDateTimeFromString(userInput);
		userInput = CommandParser.removeAliasAndEscapeChar(userInput);
		
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
		
		if (start != null && end != null && start.isAfter(end)) {
			return null;
		}
		
		return new Task(input, start, end, alias);
	}
	
	
	//////////UPDATE Functionality//////////
	
	/**
	 * Updates a task in the specified field
	 * Input format is "[task number/alias][field to update][update]
	 * @param update
	 * @return a Feedback object to be shown to the user
	 */
	protected static Feedback updateTask(String update) {	
		if (!CommandParser.isInputValid(update, 2)) {
			return new Feedback(MESSAGE_ERROR_UPDATE_ARGUMENT);
		}
		
		String taskID = CommandParser.getUserCommandType(update);
		String updateStringWithoutID = CommandParser.getUserCommandDesc(update);
		String updateField = CommandParser.getUserCommandType(updateStringWithoutID);
		String updateDesc = CommandParser.getUserCommandDesc(updateStringWithoutID);
		
		Task taskToUpdate = getTaskToUpdate(taskID);
		
		if (taskToUpdate == null) {
			return new Feedback(MESSAGE_ERROR_UPDATE_NO_SUCH_TASK);
		}
		
		if (updateField.equalsIgnoreCase("start") || updateField.equalsIgnoreCase("end") || updateField.equalsIgnoreCase("time")) {
			if (!CommandParser.isInputValid(updateDesc, 1)) {
				Task.getList().add(taskToUpdate);
				return new Feedback(MESSAGE_ERROR_UPDATE_ARGUMENT);
			}
			Task tempTask = updateTaskTime(taskToUpdate, updateField, updateDesc);
			
			if (tempTask == null) {
				Task.getList().add(taskToUpdate);
				return new Feedback(MESSAGE_ERROR_START_AFTER_END, true);
			}
			taskToUpdate = tempTask;
			
		} else if (updateField.equals("alias")) {
			String[] tokens = updateDesc.split("\\s+");
			if (tokens.length <= 0) {
				// since the task is previously removed, it needs to be put back in in the event of an error
				Task.getList().add(taskToUpdate);
				return new Feedback(MESSAGE_ERROR_ALIAS, true);
			}
			
			String alias = tokens[0];	
			if (Task.isAliasValid(alias) || isInteger(alias)) {
				Task.getList().add(taskToUpdate);
				return new Feedback(MESSAGE_ERROR_ALIAS_IN_USE, true);
			
			} else {
				taskToUpdate.setAlias(alias);
			}
			
		} else if (updateField.equals("desc") || updateField.equals("description")) {
			if (!CommandParser.isInputValid(updateDesc, 1)) {
				Task.getList().add(taskToUpdate);
				return new Feedback(MESSAGE_ERROR_UPDATE_ARGUMENT);
			}
			taskToUpdate.setDescription(updateDesc);
		} else {
			Task tempTask = createTask(updateStringWithoutID);
			if (tempTask.getDescription().trim().length() == 0) {
				Task.getList().add(taskToUpdate);
				return new Feedback(MESSAGE_ERROR_TASK_DESC_EMPTY);
			}
			taskToUpdate = tempTask;
		}
		
		Task.getList().add(taskToUpdate);
		Task.sortList();
		Task.saveTasks();
		return new Feedback(MESSAGE_UPDATE_TASK);
	}
	
	protected static Feedback markTask(String taskID) {
		ArrayList<Integer> listToMark = getTaskIdFromString(taskID);
		ArrayList<Task> taskList = Task.getList();
		if(listToMark.size() == 0) {
			return new Feedback(MESSAGE_ERROR_MARK_NO_TASK, true);
		}
		
		for (int i = 0; i < listToMark.size(); i++) {
			taskList.get((int)listToMark.get(i)).toggleStatus();
		}
		Task.setList(taskList);
		Task.saveTasks();
		return new Feedback(MESSAGE_TASK_MARK);
	}
	
	private static Task updateTaskTime(Task task, String field, String update) {
		ArrayList<String> updateTokens = new ArrayList<String>(Arrays.asList(update.split("\\s+")));
		String[] timeFields = CommandParser.getTaskFields(updateTokens);
		
		if (field.equalsIgnoreCase("time")) {
			Task tempTask = createTask(update);
			if (tempTask == null) {
				return null;
			}
			task.setStartDateTime(tempTask.getStartDateTime());
			task.setEndDateTime(tempTask.getEndDateTime());
		} else {
			DateTime dateTime = task.getStartDateTime();
			String date = timeFields[Task.START_DATE];
			String time = timeFields[Task.START_TIME];
			
			if (date != null && time != null) {
				dateTime = DateParser.setDate(date);
				dateTime = TimeParser.setTime(dateTime, time);
			} else if (date != null) {
				DateTime temp = DateParser.setDate(date);
				dateTime = TimeParser.setTime(temp, Task.getTimeString(dateTime));
			} else if (time != null) {
				dateTime = TimeParser.setTime(dateTime, time);
			} else {
				return null;
			}
			
			if (field.equalsIgnoreCase("start")) {
				task.setStartDateTime(dateTime);
			} else {
				task.setEndDateTime(dateTime);
			}
			
			if (task.getStartDateTime().isAfter(task.getEndDateTime())) {
				return null;
			}
		}
		
		return task;
	}
	
	private static Task getTaskToUpdate(String taskID) {
		Task taskToUpdate = null;
		int index = -1;
		
		if (isInteger(taskID)) {
			index = Integer.parseInt(taskID) - 1;
		} else if (Task.isAliasValid(taskID)) {
			index = Task.getTaskIndexFromAlias(taskID);
		}
		if (index < 0 || index >= Task.getList().size()) {
			return null;
		}
		taskToUpdate = Task.getList().get(index);
		Task.getList().remove(index);
		
		return taskToUpdate;
	}
	
	
	//////////READ Functionality//////////
	
	protected static Feedback listTasks(String userInput) {
		Task.sortList();
		ArrayList<Integer> indexList;
		String feedback;
		
		if (userInput == null) {
			indexList = getListOfTaskWithStatus(false);
			feedback = MESSAGE_LIST_INCOMPLETE;
		} else {
			if (userInput.equals("completed")) {
				indexList = getListOfTaskWithStatus(true);
				feedback = MESSAGE_LIST_COMPLETE;
			} else if (userInput.equals("overdue")) {
				indexList = getListOfOverdueTask();
				feedback = MESSAGE_LIST_OVERDUE;
			} else if (DateParser.isDate(userInput)) {
				indexList = getListOfTaskWithDate(userInput);
				feedback = "Showing tasks on " + userInput;
			} else if (userInput.equals("all")) {
				indexList = getListOfAllTasks();
				feedback = MESSAGE_LIST_ALL;
			} else {
				indexList = getListOfTaskWithStatus(false);
				feedback = MESSAGE_LIST_INCOMPLETE;
			}
		}
		return new Feedback(feedback , indexList);
	}
	
	/**
	 * Displays all the tasks in order of date
	 * @return a Feedback Object to be shown to the user
	 */
	private static ArrayList<Integer> getListOfAllTasks() {
		ArrayList<Integer> indexList = new ArrayList<Integer>();
		if (!Task.getList().isEmpty()) {
			for (int i = 0; i < Task.getList().size(); i++) {
				indexList.add(i);
			}
		}
		return indexList;
	}
	
	protected static ArrayList<Integer> getListOfTaskWithStatus(boolean completed) {
		ArrayList<Task> list = Task.loadTasks();
		ArrayList<Integer> indexList = new ArrayList<Integer>();
		
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getStatus() == completed) {
				indexList.add(i);
			}
		}
		
		return indexList;
	}
	
	private static ArrayList<Integer> getListOfOverdueTask() {
		ArrayList<Task> list = Task.loadTasks();
		ArrayList<Integer> indexList = new ArrayList<Integer>();
		
		for (int i = 0; i < list.size(); i++) {
			DateTime start = list.get(i).getStartDateTime();
			DateTime end = list.get(i).getEndDateTime();
			
			if (start != null && end != null) { 
				if (start.isBeforeNow() || end.isBeforeNow() && list.get(i).getStatus() == false) {
					indexList.add(i);
				} 
			} else if (start != null && start.isBeforeNow() && list.get(i).getStatus() == false) {
					indexList.add(i);
			} else if (end != null && end.isBeforeNow() && list.get(i).getStatus() == false) {
					indexList.add(i);
			}
		}
		
		return indexList;
	}
	
	private static ArrayList<Integer> getListOfTaskWithDate(String input) {
		DateTime date = DateParser.setDate(input);
		
		ArrayList<Task> list = Task.loadTasks();
		ArrayList<Integer> indexList = new ArrayList<Integer>();
		
		for (int i = 0; i < list.size(); i++) {
			DateTime start = list.get(i).getStartDateTime();
			DateTime end = list.get(i).getEndDateTime();
			
			if (start != null && end != null) { 
				if (DateParser.isSameDate(start, date) || DateParser.isSameDate(end, date)) {
					indexList.add(i);
				} 
			} else if (start != null) {
				if (DateParser.isSameDate(start, date)) {
					indexList.add(i);
				}
			} else if (end != null) {
				if (DateParser.isSameDate(end, date)) {
					indexList.add(i);
				}
			}
			
		}
		
		return indexList;
	}
	
	//////////DELETE Functionality//////////
	
	/**
	 * Removes a task from the taskList
	 * @param taskID
	 * @return a Feedback object to be shown to the user
	 */
	protected static Feedback deleteTask(String taskID) {
		if (!CommandParser.isInputValid(taskID, 1)) {
			return new Feedback(MESSAGE_ERROR_DELETE_ARGUMENT);
		}
		
		if (taskID.equalsIgnoreCase("completed")) {
			HistoryHandler.pushUndoStack();
			deleteCompleted();
			Task.saveTasks();
			HistoryHandler.purgeRedoStack();
			return new Feedback(MESSAGE_DELETE_COMPLETE);
			
		} else if (taskID.equalsIgnoreCase("all")) {
			HistoryHandler.pushUndoStack();
			deleteAll();
			Task.saveTasks();
			HistoryHandler.purgeRedoStack();
			return new Feedback(MESSAGE_DELETE_ALL);
		} else {
			ArrayList<Integer> listToDelete = getTaskIdFromString(taskID);
			
			if (listToDelete.size() == 0) {
				return new Feedback(MESSAGE_ERROR_DELETE, true);
			}
			
			HistoryHandler.pushUndoStack();
			deleteList(listToDelete);
			Task.saveTasks();
			HistoryHandler.purgeRedoStack();
			return new Feedback(MESSAGE_DELETE_SUCCESS);
		}
		
	}
	
	private static void deleteList(ArrayList<Integer> list) {
		ArrayList<Task> taskList = Task.getList();
		for (int i = 0; i < list.size(); i++) {
			taskList.remove((int)list.get(i));
		}
		Task.setList(taskList);
	}
	
	private static void deleteCompleted() {
		ArrayList<Task> taskList = Task.getList();
		
		for (int i = 0; i < taskList.size(); i++) {
			if (taskList.get(i).getStatus()) {
				taskList.remove(i);
				i--;
			}
		}
	}
	
	private static void deleteAll() {
		Task.setList(new ArrayList<Task>());
	}
	
	private static boolean isOutOfDeleteRange(int index) {
		if (index >= 0 && index < Task.getList().size()) {
			return false;
		} else {
			return true;
		}
	}
	
	private static ArrayList<Integer> getTaskIdFromString(String list) {
		String[] tempList = list.split("\\s+");
		Set<Integer> deleteList = new HashSet<Integer>();
		
		for (int i = 0; i < tempList.length; i++) {
			int index = -1;
			
			if (isInteger(tempList[i])) {
				index = Integer.parseInt(tempList[i]) - 1;
			} else if (Task.isAliasValid(tempList[i])) {
				index = Task.getTaskIndexFromAlias(tempList[i]);
			} 
			
			if (!isOutOfDeleteRange(index)) {
				deleteList.add(index);
			}
			
		}
		
		ArrayList<Integer> dList = new ArrayList<Integer>(deleteList);
		Collections.sort(dList);
		Collections.reverse(dList);
		
		return dList;
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
	
	private static Feedback searchTasks(String searchKey) {
		String[] keys = searchKey.split("\\s+");
		if (keys.length == 0) {
			return new Feedback(MESSAGE_ERROR_SEARCH);
		}
		ArrayList<Task> taskList = Task.getList();
		ArrayList<Integer> indexList = new ArrayList<Integer>();
		
		for (int i = 0; i < taskList.size(); i++) {
			for (int j = 0; j < keys.length; j++) {
				if (taskList.get(i).getDescription().contains(keys[j])) {
					indexList.add(i);
					break;
				}
			}
		}
		
		return new Feedback("Search for " + searchKey , indexList);
	}
}