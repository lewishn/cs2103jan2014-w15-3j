import java.text.*;
import java.util.*;

class Action {
	private static final String MESSAGE_ADDED_TASK = "Added \"%s\" ";
	private static final String MESSAGE_EMPTY_TASKS = "You have no tasks scheduled.";
	private static final String MESSAGE_LIST_NUMBER = "%d. %s";
	private static final String MESSAGE_TASK_DELETED = "\"%s\" has been deleted from the task list.";
	private static final String MESSAGE_TASK_DELETED_ALL = "All tasks have been deleted from the task list.";
	private static final String MESSAGE_CUSTOM_DUPLICATE = "Sorry, but this word is already in use.";
	private static final String MESSAGE_CUSTOM_SUCCESS = " has been successfully added to the command list.";
	private static final String MESSAGE_CUSTOM_NONEXISTANT = "Error deleting. There is no such word in the command list.";
	private static final String MESSAGE_CUSTOM_DELETED = " has been successfully deleted from the command list.";
	
	private static final String MESSAGE_INVALID_ADD = "Missing task, please enter: add <sentence>";
	private static final String MESSAGE_INVALID_DELETE = "No such task, please enter: delete <number>";
	private static final String MESSAGE_INVALID_DELETE_ENTER_NUMBER = "Error, please enter a number: delete <number>";

	//private static final String FLOATING_TASK = "floating task";
	private static final String NON_FLOATING_TASK = "non-floating task";

	private static ArrayList<Task> taskList;
	private static ArrayList<ArrayList<String>> customCommandList;
	private static Stack<ArrayList<Task>> taskUndoStack;
	private static Stack<ArrayList<ArrayList<String>>> commandUndoStack;

	/* functions related to Add task */
	protected static void addTask(String taskDescription) {
		if(!isNullString(taskDescription)) {
			String[]taskInformation = MainParser.determineTask(taskDescription);
			// floatingTask, start Date, start time, end date, end time
			Task userTask = new Task(taskDescription);	
			// edit task object	
			if(taskInformation[0].equals(NON_FLOATING_TASK)) {
				pushUndoStack();
				taskList=DiskIO.readTaskFromFile();
				taskList.add(userTask);
				sortTasks();
			} else {
				// floating task
			}
			DiskIO.writeTaskToFile(taskList);

			Printer.print(String.format(MESSAGE_ADDED_TASK, taskDescription));
		} else {
			Printer.print(MESSAGE_INVALID_ADD);
		}	
	}
	private static boolean isNullString(String task) {
		if (task == null) {
			return true;
		}
		if (task.equals("")) {
			return true;
		} else {
			return false;
		}
	}
	private static void sortTasks() {
		Collections.sort(taskList, Collator.getInstance());
	}
	
	/* functions related to List task */
	protected static void listTasks() {		
		if (taskList.isEmpty()) {
			Printer.print(MESSAGE_EMPTY_TASKS);
		} else {
			Printer.print(getContentToDisplay());
		}
	}
	private static String getContentToDisplay() {
		String contentToDisplay="";
		for (int i = 1; i <= taskList.size(); i++) {
			contentToDisplay += String.format(MESSAGE_LIST_NUMBER, i, taskList.get(i-1).toString()) + "\r\n";
		}
		return contentToDisplay;
	}

	/* functions related to Delete task */
	protected static void deleteTask(String taskNumber) {
		
		if(isInteger(taskNumber)) {
			int rowToDelete=Integer.parseInt(taskNumber);
			if (isOutOfDeleteRange(rowToDelete)) {
				Printer.print(rowToDelete + MESSAGE_INVALID_DELETE);
				return;
			}

			pushUndoStack();
			rowToDelete--;
			String deletedTask = taskList.get(rowToDelete).getDescription();
			taskList.remove(rowToDelete);
			DiskIO.writeTaskToFile(taskList);
			Printer.print(String.format(MESSAGE_TASK_DELETED, deletedTask));
		} else {
			Printer.print(MESSAGE_INVALID_DELETE_ENTER_NUMBER);
		}
	}
	
	protected static void deleteAllTasks() {
		pushUndoStack();
		taskList = new ArrayList<Task>();
		DiskIO.writeTaskToFile(taskList);
		Printer.print(MESSAGE_TASK_DELETED_ALL);
	}
	
	private static boolean isOutOfDeleteRange(int index) {
		if (index >= 0 && index < taskList.size()) {
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
	


	

	/* functions related to addCustomCommand */
	protected static void addCustomCommand(String userCommand) {
		// send userCommand to main parser to find out index and command

		int index=0; 
		String command="";

		if (isDuplicateCommand(command)) {
			Printer.print(MESSAGE_CUSTOM_DUPLICATE);
			return;
		}
		
		pushUndoStack();
		customCommandList.get(index).add(command);
		DiskIO.writeCustomCommands(customCommandList);
		Printer.print(MESSAGE_CUSTOM_SUCCESS);
	}

	private static boolean isDuplicateCommand(String command) {
		for (int i = 0; i < customCommandList.size(); i++) {
			if (customCommandList.get(i).contains(command)) {
				return true;
			}
		}
		return false;
	}

	/* functions related to deleteCustomCommand */
	protected static void deleteCustomCommand(String userCommand) {
		// send userCommand to main parser to find out index and command

		//int index=0;
		String command="";

		pushUndoStack();
		boolean wordExists = false;
		for (int i = 0; i < customCommandList.size(); i++) {
			if (customCommandList.remove(command)) {
				Printer.print(command + MESSAGE_CUSTOM_DELETED);
				wordExists = true;
			}
		}
		
		if (!wordExists) {
			Printer.print(MESSAGE_CUSTOM_NONEXISTANT);
		} else {
			DiskIO.writeCustomCommands(customCommandList);
		}
	}
	




	/* functions related to undoCommand */
	protected static void undoCommand() {
		popUndoStack();
		DiskIO.writeCustomCommands(customCommandList);
		DiskIO.writeTaskToFile(taskList);
	}







	private static void pushUndoStack() {
		taskUndoStack.push(taskList);
		commandUndoStack.push(customCommandList);
	}
	
	private static void popUndoStack() {
		// TODO: might need to check for stack size;
		taskList = taskUndoStack.pop();
		customCommandList = commandUndoStack.pop();
	}
}