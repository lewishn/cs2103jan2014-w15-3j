import java.util.ArrayList;
import java.util.Stack;


public class HistoryHandler {
	private static final String UNDO_SUCCESS = "Undo successful!";
	private static final String UNDO_FAIL = "Nothing left to undo.";
	private static final String REDO_SUCCESS = "Redo successful!";
	private static final String REDO_FAIL = "Nothing left to redo.";
	
	private static Stack<ArrayList<Task>> taskUndoStack = new Stack<ArrayList<Task>>();
	private static Stack<ArrayList<Task>> taskRedoStack = new Stack<ArrayList<Task>>();
	
	/**
	 * The previous action by the user which manipulates the taskList or customCommandList will be undone
	 * @return a Feedback object to be shown to the user, indicating success or failure in undoing
	 */
	protected static Feedback undoCommand() {
		boolean tryUndo = popUndoStack();
		if(tryUndo) {
			Task.saveTasks();
			return new Feedback(UNDO_SUCCESS + "\n", false);
		} else {
			return new Feedback(UNDO_FAIL + "\n", false);
		}
	}
	
	protected static Feedback redoCommand() {
		boolean tryRedo = popRedoStack();
		if(tryRedo) {
			Task.saveTasks();
			return new Feedback(REDO_SUCCESS + "\n", false);
		} else {
			return new Feedback(REDO_FAIL + "\n", false);
		}
	}

	/**
	 * Pushes a copy of the taskList and CustomCommandList into the undo stack.
	 */
	protected static void pushUndoStack() {
		ArrayList<Task> taskNewList = (ArrayList<Task>) Task.getList().clone();		
		taskUndoStack.push(taskNewList);
	}
	
	private static void pushRedoStack() {
		ArrayList<Task> taskNewList = (ArrayList<Task>) Task.getList().clone();		
		taskRedoStack.push(taskNewList);
	}
	
	/**
	 * Get the top of the undo stack
	 * @return true if stack size is greater than zero, false otherwise
	 */
	private static boolean popUndoStack() {
		if (taskUndoStack.size() > 0) {
			pushRedoStack();
			ArrayList<Task> taskList = taskUndoStack.pop();
			Task.setList(taskList);
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Get the top of the undo stack
	 * @return true if stack size is greater than zero, false otherwise
	 */
	private static boolean popRedoStack() {
		if (taskRedoStack.size() > 0) {
			ArrayList<Task> taskList = taskRedoStack.pop();
			Task.setList(taskList);
			return true;
		} else {
			return false;
		}
	}
	
	protected static void purgeRedoStack() {
		taskRedoStack = new Stack<ArrayList<Task>>();
	}
}
