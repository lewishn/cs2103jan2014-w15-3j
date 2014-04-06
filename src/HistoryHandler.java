import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Stack;


public class HistoryHandler {
	private static final String FILE_UNDO = "undo.txt";
	private static final String DELIMITER = "!@#$%^&*()";
	private static final int MAXIMUM_UNDO_STEPS = 100;
	private static final int MAXIMUM_SAVE_STACK_SIZE = 10;
	
	private static final String UNDO_SUCCESS = "Undo successful!";
	private static final String UNDO_FAIL = "Nothing left to undo.";
	private static final String REDO_SUCCESS = "Redo successful!";
	private static final String REDO_FAIL = "Nothing left to redo.";
	
	private static ArrayDeque<ArrayList<Task>> taskUndoStack = loadUndoStack();
	private static ArrayDeque<ArrayList<Task>> taskRedoStack = new ArrayDeque<ArrayList<Task>>();
	
	/**
	 * The previous action by the user which manipulates the taskList or customCommandList will be undone
	 * @return a Feedback object to be shown to the user, indicating success or failure in undoing
	 */
	protected static Feedback undoCommand() {
		boolean tryUndo = popUndoStack();
		if(tryUndo) {
			Task.saveTasks();
			return new Feedback(UNDO_SUCCESS);
		} else {
			return new Feedback(UNDO_FAIL);
		}
	}
	
	protected static Feedback redoCommand() {
		boolean tryRedo = popRedoStack();
		if(tryRedo) {
			Task.saveTasks();
			return new Feedback(REDO_SUCCESS);
		} else {
			return new Feedback(REDO_FAIL);
		}
	}

	/**
	 * Pushes a copy of the taskList and CustomCommandList into the undo stack.
	 */
	protected static void pushUndoStack() {
		ArrayList<Task> taskNewList = (ArrayList<Task>) Task.getList().clone();		
		taskUndoStack.add(taskNewList);
		reduceStackSize();
		saveUndoStack();
	}
	
	private static void reduceStackSize() {
		while (taskUndoStack.size() > MAXIMUM_UNDO_STEPS) {
			taskUndoStack.pollFirst();
		}
	}
	
	private static void pushRedoStack() {
		ArrayList<Task> taskNewList = (ArrayList<Task>) Task.getList().clone();		
		taskRedoStack.add(taskNewList);
	}
	
	/**
	 * Get the top of the undo stack
	 * @return true if stack size is greater than zero, false otherwise
	 */
	private static boolean popUndoStack() {
		if (taskUndoStack.size() > 0) {
			pushRedoStack();
			ArrayList<Task> taskList = taskUndoStack.pollLast();
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
			pushUndoStack();
			ArrayList<Task> taskList = taskRedoStack.pollLast();
			Task.setList(taskList);
			return true;
		} else {
			return false;
		}
	}
	
	protected static void purgeRedoStack() {
		taskRedoStack = new ArrayDeque<ArrayList<Task>>();
	}
	
	private static void saveUndoStack() {
		ArrayDeque<ArrayList<Task>> undoStack = (ArrayDeque<ArrayList<Task>>) taskUndoStack.clone();
		ArrayList<String> saveList = new ArrayList<String>();
		
		for (int i = 0; i < MAXIMUM_SAVE_STACK_SIZE; i++) {
			if (undoStack.size() > 0) {
				ArrayList<Task> stackEntry = undoStack.pop();
				for (int j = 0; j < stackEntry.size(); j++) {
					saveList.add(stackEntry.get(j).toString());
				}
				saveList.add(DELIMITER);
			}
		}
		
		FileManager.writeToFile(FILE_UNDO, saveList);
	}
	
	private static ArrayDeque<ArrayList<Task>> loadUndoStack() {
		ArrayList<String> list = FileManager.readFromFile(FILE_UNDO);
		ArrayDeque<ArrayList<Task>> undoStack = new ArrayDeque<ArrayList<Task>>();
		ArrayList<Task> stackEntry = new ArrayList<Task>();
		
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).equals(DELIMITER)) {
				undoStack.add(stackEntry);
				stackEntry = new ArrayList<Task>();
				continue;
			}
			
			stackEntry.add(Task.parseTaskFromString(list.get(i)));
		}
		
		return undoStack;
	}
	
}
