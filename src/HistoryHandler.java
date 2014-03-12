import java.util.ArrayList;
import java.util.Stack;


public class HistoryHandler {
	private static final String UNDO_SUCCESS = "Undo successful!";
	private static final String UNDO_FAIL = "Nothing left to undo.";
	
	private static Stack<ArrayList<Task>> taskUndoStack = new Stack<ArrayList<Task>>();
	private static Stack<ArrayList<ArrayList<String>>> commandUndoStack = new Stack<ArrayList<ArrayList<String>>>();
	
	protected static Feedback undoCommand() {
		boolean tryUndo = popUndoStack();
		if(tryUndo) {
			FileManager.writeCustomCommandsToFile(CustomCommandHandler.customCommandList);
			FileManager.writeTasksToFile(TaskHandler.getTaskList());
			return new Feedback(UNDO_SUCCESS + "\n", false);
		} else {
			return new Feedback(UNDO_FAIL + "\n", false);
		}
	}

	protected static void pushUndoStack() {
		ArrayList<Task> taskList = (ArrayList<Task>) TaskHandler.getTaskList().clone();		
		taskUndoStack.push(taskList);
		
		commandUndoStack.push(CustomCommandHandler.customCommandList);
	}
	
	private static boolean popUndoStack() {
		if (taskUndoStack.size() > 0) {
			ArrayList<Task> taskList = taskUndoStack.pop();
			TaskHandler.setTaskList(taskList);
			CustomCommandHandler.customCommandList = commandUndoStack.pop();
			return true;
		} else {
			return false;
		}
	}
}
