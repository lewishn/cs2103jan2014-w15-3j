import java.util.ArrayList;
import java.util.Stack;


public class HistoryHandler {
	private static final String UNDO_SUCCESS = "Undo successful!";
	private static final String UNDO_FAIL = "Nothing left to undo.";
	
	private static Stack<ArrayList<Task>> taskUndoStack = new Stack<ArrayList<Task>>();
	private static Stack<ArrayList<ArrayList<String>>> commandUndoStack = new Stack<ArrayList<ArrayList<String>>>();
	
	/**
	 * The previous action by the user which manipulates the taskList or customCommandList will be undone
	 * @return a Feedback object to be shown to the user, indicating success or failure in undoing
	 */
	protected static Feedback undoCommand() {
		boolean tryUndo = popUndoStack();
		if(tryUndo) {
			CustomCommandHandler.saveCustomCommands();
			Task.saveTasks();
			return new Feedback(UNDO_SUCCESS + "\n", false);
		} else {
			return new Feedback(UNDO_FAIL + "\n", false);
		}
	}

	/**
	 * Pushes a copy of the taskList into the undo stack.
	 */
	protected static void pushUndoStack() {
		ArrayList<Task> taskList = (ArrayList<Task>) Task.getList().clone();		
		taskUndoStack.push(taskList);
		
		commandUndoStack.push(CustomCommandHandler.customCommandList);
	}
	
	/**
	 * Get the top of the undo stack
	 * @return true if stack size is greater than zero, false otherwise
	 */
	private static boolean popUndoStack() {
		if (taskUndoStack.size() > 0) {
			ArrayList<Task> taskList = taskUndoStack.pop();
			Task.setList(taskList);
			CustomCommandHandler.customCommandList = commandUndoStack.pop();
			return true;
		} else {
			return false;
		}
	}
}
