import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.joda.time.DateTime;

class TaskHandler {
	
	private static final String MESSAGE_ADDED_TASK = "Added \"%s\".";
	private static final String MESSAGE_UPDATE_TASK = "Task has been updated.";	
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
	private static final String MESSAGE_ERROR_DELETE = "No such tasks. Please enter a valid number or alias.";
	private static final String MESSAGE_ERROR_SEARCH = "Please enter a search key.";
	private static final String MESSAGE_ERROR_ALIAS = "Invalid alias";
	private static final String MINUTE_LAST = "23:59";
	private static final String MINUTE_FIRST = "00:00";
	private static final String WHITESPACE = "\\s+";
	
	private static final String UPDATE_FIELD_TIME = "time";
	private static final String UPDATE_FIELD_START = "start";
	private static final String UPDATE_FIELD_END = "end";
	private static final String UPDATE_FIELD_DESC1 = "desc";
	private static final String UPDATE_FIELD_DESC2 = "description";
	private static final String UPDATE_FIELD_ALIAS = "alias";
	
	
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
		executePostCommandRoutine();
		return new Feedback(String.format(MESSAGE_ADDED_TASK, userInput));
	}
	
	/**
	 * Creates a task from the given userInput
	 * @param userInput
	 * @return Task created from the userInput
	 */
	protected static Task createTask(String userInput) {
		String[] inputTokens = userInput.split(WHITESPACE);
		ArrayList<String> input = new ArrayList<String>(Arrays.asList(inputTokens));
		
		String alias = CommandParser.getAliasFromDescription(userInput);
		alias = (Task.isAliasValid(alias) || CommandParser.isInteger(alias)) ? null : alias;
		userInput = CommandParser.removeDateTimeFromString(userInput);
		userInput = CommandParser.removeAliasAndEscapeChar(userInput);
		
		String[] fields = CommandParser.getTaskFields(input);
		
		return createTaskFromFields(fields, userInput, alias);
	}
	
	private static Task createTaskFromFields(String[] fields, String input, String alias) {
		Task newTask = null;
		
		if (fields[Task.START_DATE] == null) {
			newTask = createTaskWithoutStartDate(fields);
		} else {
			newTask = createTaskWithStartDate(fields);
		}
		
		if (newTask.getStartDateTime() != null && newTask.getEndDateTime() != null && newTask.getStartDateTime().isAfter(newTask.getEndDateTime())) {
			return null;
		} else {
			newTask.setDescription(input);
			newTask.setAlias(alias);
			return newTask;
		}
	}

	private static Task createTaskWithoutStartDate(String[] fields) {
		DateTime start = null;
		DateTime end = null;
		
		if (fields[Task.START_TIME] != null) {
			start = DateParser.setDate();
			start = TimeParser.setTime(start, fields[Task.START_TIME]);
			
			// If null, Deadline Task with today's date. Else, Scheduled Task with today's date
			if (fields[Task.END_TIME] != null) {
				end = DateParser.setDate();
				end = TimeParser.setTime(start, fields[Task.END_TIME]);
			} 
		}
		
		return new Task(null, start, end, null);
	}
	
	private static Task createTaskWithStartDate(String[] fields) {
		if (fields[Task.START_TIME] == null) {
			return createTaskWithStartDateNoStartTime(fields);
		} else {
			return createTaskWithStartDateAndTime(fields);
		}
	}
	
	private static Task createTaskWithStartDateNoStartTime(String[] fields) {
		DateTime start = DateParser.setDate(fields[Task.START_DATE]);
		DateTime end = null;
		
		if (fields[Task.END_DATE] == null) {
			// "Deadline" Task with only start date
			start = TimeParser.setTime(start, MINUTE_LAST);
		} else {
			// Timed Task with start and end dates but no time
			start = TimeParser.setTime(start, MINUTE_FIRST);
			end = DateParser.setDate(fields[Task.END_DATE]);
			end = TimeParser.setTime(end, MINUTE_LAST);
		}
		
		return new Task(null, start, end, null);
	}
	
	private static Task createTaskWithStartDateAndTime(String[] fields) {
		DateTime start = DateParser.setDate(fields[Task.START_DATE]);
		start = TimeParser.setTime(start, fields[Task.START_TIME]);
		DateTime end = null;
		
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
		
		return new Task(null, start, end, null);
	}
	
	//////////UPDATE Functionality//////////
	/**
	 * Updates a task in the specified field
	 * Input format is [task number/alias][field to update][update]
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
		
		Task taskToUpdate = null;
		int updateIndex = getIndexToUpdate(taskID);
		
		if (updateIndex == -1) {
			return new Feedback(MESSAGE_ERROR_UPDATE_NO_SUCH_TASK);
		}
		
		if (updateField.equalsIgnoreCase("start") || updateField.equalsIgnoreCase("end") || updateField.equalsIgnoreCase("time")) {
			if (!CommandParser.isInputValid(updateDesc, 1)) {
				return new Feedback(MESSAGE_ERROR_UPDATE_ARGUMENT);
			}
			
			taskToUpdate = new Task(Task.getList().get(updateIndex));
			taskToUpdate = updateTaskTime(taskToUpdate, updateField, updateDesc);
			
			if (taskToUpdate == null) {
				return new Feedback(MESSAGE_ERROR_START_AFTER_END, true);
			}
			
		} else if (updateField.equals("alias")) {
			String[] tokens = updateDesc.split(WHITESPACE);
			if (tokens.length <= 0) {
				return new Feedback(MESSAGE_ERROR_ALIAS, true);
			}

			String alias = tokens[0];	
			if (Task.isAliasValid(alias) || CommandParser.isInteger(alias)) {
				return new Feedback(MESSAGE_ERROR_ALIAS_IN_USE, true);
			
			}
			
			taskToUpdate = new Task(Task.getList().get(updateIndex));
			taskToUpdate.setAlias(alias);
			
		} else if (updateField.equals("desc") || updateField.equals("description")) {
			if (!CommandParser.isInputValid(updateDesc, 1)) {
				return new Feedback(MESSAGE_ERROR_UPDATE_ARGUMENT, true);
			}
			
			taskToUpdate = new Task(Task.getList().get(updateIndex));
			taskToUpdate.setDescription(updateDesc);
			
		} else {
			taskToUpdate = createTask(updateStringWithoutID);
			
			if (taskToUpdate.getDescription().trim().length() == 0) {
				return new Feedback(MESSAGE_ERROR_TASK_DESC_EMPTY, true);
			}
		}
		
		HistoryHandler.pushUndoStack();
		Task.getList().remove(updateIndex);
		Task.getList().add(taskToUpdate);
		executePostCommandRoutine();
		return new Feedback(MESSAGE_UPDATE_TASK);
	}
	
	/**
	 * Toggles the status of the specified tasks.
	 * @param taskID
	 * @return Feedback object containing the information to be shown to the user.
	 */
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
	
	/**
	 * Updates the time of the input task
	 * @param task
	 * @param field
	 * @param update
	 * @return the updated task
	 */
	private static Task updateTaskTime(Task task, String field, String update) {
		ArrayList<String> updateTokens = new ArrayList<String>(Arrays.asList(update.split(WHITESPACE)));
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
	
	/**
	 * Get the index of the task from the taskID
	 * @param taskID
	 * @return
	 */
	private static int getIndexToUpdate(String taskID) {
		int index = -1;
		
		if (CommandParser.isInteger(taskID)) {
			index = Integer.parseInt(taskID) - 1;
		} else if (Task.isAliasValid(taskID)) {
			index = Task.getTaskIndexFromAlias(taskID);
		}
		
		if (index < 0 || index >= Task.getList().size()) {
			return -1;
		}
		
		return index;
	}
	
	
	//////////READ Functionality//////////
	/**
	 * Gets the lists of task to display from the input parameter
	 * @param userInput
	 * @return Feedback object containing the list of tasks to be displayed
	 */
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
	
	/**
	 * Gets the list of tasks that are either completed or incomplete
	 * @param completed
	 * @return List of completed tasks if completed is true, incomplete if false
	 */
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
	
	/**
	 * Get the list of tasks which are overdue
	 * @return list of overdue tasks
	 */
	private static ArrayList<Integer> getListOfOverdueTask() {
		ArrayList<Task> list = Task.loadTasks();
		ArrayList<Integer> indexList = new ArrayList<Integer>();
		
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).isOverdue()) {
				indexList.add(i);
			}
		}
		
		return indexList;
	}
	
	/**
	 * Get the list of tasks with end or start date same as the specified date
	 * @param input
	 * @return lists of tasks that have the specified date
	 */
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
		
		String feedback;
		
		if (taskID.equalsIgnoreCase("completed")) {
			HistoryHandler.pushUndoStack();
			deleteCompleted();
			feedback = MESSAGE_DELETE_COMPLETE;
			
		} else if (taskID.equalsIgnoreCase("all")) {
			HistoryHandler.pushUndoStack();
			deleteAll();
			feedback = MESSAGE_DELETE_ALL;
		} else {
			ArrayList<Integer> listToDelete = getTaskIdFromString(taskID);
			
			if (listToDelete.size() == 0) {
				return new Feedback(MESSAGE_ERROR_DELETE, true);
			}
			
			HistoryHandler.pushUndoStack();
			deleteList(listToDelete);
			feedback = MESSAGE_DELETE_SUCCESS;
		}
		
		executePostCommandRoutine();
		return new Feedback(feedback);
	}
	
	/**
	 * Deletes all tasks specified in the list
	 * @param list
	 */
	private static void deleteList(ArrayList<Integer> list) {
		ArrayList<Task> taskList = Task.getList();
		for (int i = 0; i < list.size(); i++) {
			taskList.remove((int)list.get(i));
		}
		Task.setList(taskList);
	}
	
	/**
	 * Deletes all tasks marked as completed
	 */
	private static void deleteCompleted() {
		ArrayList<Task> taskList = Task.getList();
		
		for (int i = 0; i < taskList.size(); i++) {
			if (taskList.get(i).getStatus()) {
				taskList.remove(i);
				i--;
			}
		}
	}
	
	/**
	 * Deletes all tasks from the taskList
	 */
	private static void deleteAll() {
		Task.setList(new ArrayList<Task>());
	}
	
	/**
	 * Checks if the integer is within the size of the task list
	 * @param index
	 * @return true if index is withing the range of the list
	 */
	private static boolean isOutOfDeleteRange(int index) {
		if (index >= 0 && index < Task.getList().size()) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Gets the list of task indices from the input string containing integers and/or aliases
	 * @param list
	 * @return Sorted list of task indices without duplicates
	 */
	private static ArrayList<Integer> getTaskIdFromString(String list) {
		String[] tempList = list.split(WHITESPACE);
		Set<Integer> deleteList = new HashSet<Integer>();
		
		for (int i = 0; i < tempList.length; i++) {
			int index = -1;
			
			if (CommandParser.isInteger(tempList[i])) {
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
	
	////////////////SEARCH Functionality////////////////////
	/**
	 * Returns the indices of tasks that contain at least one the words.
	 * @param searchKey
	 * @return ArrayList of task indices
	 */
	protected static Feedback searchTasks(String searchKey) {
		String[] keys = searchKey.split(WHITESPACE);
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
	
	/**
	 * Executes the necessary methods after a user operation on the task list
	 */
	private static void executePostCommandRoutine() {
		Task.sortList();
		Task.saveTasks();
		HistoryHandler.purgeRedoStack();
	}
}