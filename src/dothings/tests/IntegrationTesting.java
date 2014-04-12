package dothings.tests;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;

import dothings.logic.MainLogic;

// @author A0097082Y
public class IntegrationTesting {
	private static final int FEEDBACK_TYPE = 0;
	private static final int FEEDBACK_DESC = 1;
	private static final int TASK_DESC = 2;
	private static final int TASK_ALIAS = 3;
	private static final int TASK_STATUS = 4;
	private static final int TASK_DATE = 5;
	private static final int TASK_TIME = 6;

	@Test
	public void invalidCommand() {
		ArrayList<ArrayList<String>> result = MainLogic.runLogic("delete all");
		result = MainLogic.runLogic("blah iu21s");

		String expectedFeedbackType = "error";
		String feedbackType = result.get(FEEDBACK_TYPE).get(0);
		assertEquals(expectedFeedbackType, feedbackType);
				
		String expectedFeedbackDes = "Oops, please try again.";
		String feedbackDesc = result.get(FEEDBACK_DESC).get(0);
		assertEquals(expectedFeedbackDes, feedbackDesc);

		assertTrue(result.get(TASK_DESC).isEmpty());
	}
	
	@Test
	public void testHelpSuccess() {
		ArrayList<ArrayList<String>> result = MainLogic.runLogic("help");
		
		String expectedType = "help";
		String feedbackType = result.get(FEEDBACK_TYPE).get(0);
		assertEquals(expectedType, feedbackType);
		
		String expectedDesc = "[ADD]\nadd \n\n[UPDATE]\nupdate \n\n[MARK]\nmark \n\n[DELETE]\ndelete \n\n[LIST]\nlist \n\n[SEARCH]\nsearch "
				+ "\n\n[UNDO]\nundo \n\n[REDO]\nredo \n\n[CUSTOM]\ncustom \n\n[DELETE_CUSTOM]\ndcustom \n\n[HELP]\nhelp \n\n[EXIT]\nexit \n\n";
		String helpDesc = result.get(TASK_DESC).get(0);
		assertEquals(expectedDesc, helpDesc);
		
		String expectedFeedbackDes = "Need help? Your Commands:";
		String feedbackDesc = result.get(FEEDBACK_DESC).get(0);
		assertEquals(expectedFeedbackDes, feedbackDesc);
	}
	@Test
	public void testHelpFail() {
		ArrayList<ArrayList<String>> result = MainLogic.runLogic("help");

		String expectedType = "elp";
		String feedbackType = result.get(FEEDBACK_TYPE).get(0);
		assertNotEquals(expectedType, feedbackType);
				
		String expectedFeedbackDes = "Oops, please try again.";
		String feedbackDesc = result.get(FEEDBACK_DESC).get(0);
		assertNotEquals(expectedFeedbackDes, feedbackDesc);
	}
	
	@Test
	public void undoSuccess() {
		ArrayList<ArrayList<String>> result = MainLogic.runLogic("delete all");
		result = MainLogic.runLogic("add assignment due tomorrow");
		result = MainLogic.runLogic("undo");

		String expectedFeedbackType = "undo";
		String feedbackType = result.get(FEEDBACK_TYPE).get(0);
		assertEquals(expectedFeedbackType, feedbackType);
				
		String expectedFeedbackDes = "1 step(s) undone";
		String feedbackDesc = result.get(FEEDBACK_DESC).get(0);
		assertEquals(expectedFeedbackDes, feedbackDesc);
		
		assertTrue(result.get(TASK_DESC).isEmpty());
	}
	@Test
	public void undoExtraWordsSuccess() {
		ArrayList<ArrayList<String>> result = MainLogic.runLogic("delete all");
		result = MainLogic.runLogic("add assignment due tomorrow");
		result = MainLogic.runLogic("undo asdqwegwegkljhgqeg");

		String expectedFeedbackType = "undo";
		String feedbackType = result.get(FEEDBACK_TYPE).get(0);
		assertEquals(expectedFeedbackType, feedbackType);
				
		String expectedFeedbackDes = "1 step(s) undone";
		String feedbackDesc = result.get(FEEDBACK_DESC).get(0);
		assertEquals(expectedFeedbackDes, feedbackDesc);
		
		assertTrue(result.get(TASK_DESC).isEmpty());
	}
	@Test
	public void undoNegNumPass() {
		ArrayList<ArrayList<String>> result = MainLogic.runLogic("delete all");
		result = MainLogic.runLogic("add assignment due tomorrow");
		result = MainLogic.runLogic("undo -1");

		String expectedFeedbackType = "undo";
		String feedbackType = result.get(FEEDBACK_TYPE).get(0);
		assertEquals(expectedFeedbackType, feedbackType);
				
		String expectedFeedbackDes = "1 step(s) undone";
		String feedbackDesc = result.get(FEEDBACK_DESC).get(0);
		assertEquals(expectedFeedbackDes, feedbackDesc);

		assertTrue(result.get(TASK_DESC).isEmpty());
	}
	@Test
	public void undoMultipleSuccess() {
		ArrayList<ArrayList<String>> result = MainLogic.runLogic("delete all");
		result = MainLogic.runLogic("add assignment1 due tomorrow");
		result = MainLogic.runLogic("add assignment2 due tomorrow");
		result = MainLogic.runLogic("add assignment3 due tomorrow");
		result = MainLogic.runLogic("add assignment4 due tomorrow");
		result = MainLogic.runLogic("add assignment5 due tomorrow");
		result = MainLogic.runLogic("add assignment6 due tomorrow");
		result = MainLogic.runLogic("undo 5");

		String expectedFeedbackType = "undo";
		String feedbackType = result.get(FEEDBACK_TYPE).get(0);
		assertEquals(expectedFeedbackType, feedbackType);
				
		String expectedFeedbackDes = "5 step(s) undone";
		String feedbackDesc = result.get(FEEDBACK_DESC).get(0);
		assertEquals(expectedFeedbackDes, feedbackDesc);
		
		String expectedDesc = "1. assignment1 due";
		String taskDesc = result.get(TASK_DESC).get(0);
		assertEquals(expectedDesc, taskDesc);
	}
	
	@Test
	public void redoSuccess() {
		ArrayList<ArrayList<String>> result = MainLogic.runLogic("delete all");
		result = MainLogic.runLogic("add assignment due tomorrow");
		result = MainLogic.runLogic("undo");
		result = MainLogic.runLogic("redo");

		String expectedFeedbackType = "redo";
		String feedbackType = result.get(FEEDBACK_TYPE).get(0);
		assertEquals(expectedFeedbackType, feedbackType);
				
		String expectedFeedbackDes = "1 step(s) redone";
		String feedbackDesc = result.get(FEEDBACK_DESC).get(0);
		assertEquals(expectedFeedbackDes, feedbackDesc);

		String expectedDesc = "1. assignment due";
		String taskDesc = result.get(TASK_DESC).get(0);
		assertEquals(expectedDesc, taskDesc);
	}
	@Test
	public void redoExtraWordsSuccess() {
		ArrayList<ArrayList<String>> result = MainLogic.runLogic("delete all");
		result = MainLogic.runLogic("add assignment due tomorrow");
		result = MainLogic.runLogic("undo");
		result = MainLogic.runLogic("redo asdqwfeqgtrsjk");

		String expectedFeedbackType = "redo";
		String feedbackType = result.get(FEEDBACK_TYPE).get(0);
		assertEquals(expectedFeedbackType, feedbackType);
				
		String expectedFeedbackDes = "1 step(s) redone";
		String feedbackDesc = result.get(FEEDBACK_DESC).get(0);
		assertEquals(expectedFeedbackDes, feedbackDesc);

		String expectedDesc = "1. assignment due";
		String taskDesc = result.get(TASK_DESC).get(0);
		assertEquals(expectedDesc, taskDesc);
	}
	@Test
	public void redoNegNumPass() {
		ArrayList<ArrayList<String>> result = MainLogic.runLogic("delete all");
		result = MainLogic.runLogic("add assignment due tomorrow");
		result = MainLogic.runLogic("undo");
		result = MainLogic.runLogic("redo -1");

		String expectedFeedbackType = "redo";
		String feedbackType = result.get(FEEDBACK_TYPE).get(0);
		assertEquals(expectedFeedbackType, feedbackType);
				
		String expectedFeedbackDes = "1 step(s) redone";
		String feedbackDesc = result.get(FEEDBACK_DESC).get(0);
		assertEquals(expectedFeedbackDes, feedbackDesc);

		String expectedDesc = "1. assignment due";
		String taskDesc = result.get(TASK_DESC).get(0);
		assertEquals(expectedDesc, taskDesc);
	}
	@Test
	public void redoMultipleSuccess() {
		ArrayList<ArrayList<String>> result = MainLogic.runLogic("delete all");
		result = MainLogic.runLogic("add assignment1 due tomorrow");
		result = MainLogic.runLogic("add assignment2 due tomorrow");
		result = MainLogic.runLogic("add assignment3 due tomorrow");
		result = MainLogic.runLogic("add assignment4 due tomorrow");
		result = MainLogic.runLogic("add assignment5 due tomorrow");
		result = MainLogic.runLogic("add assignment6 due tomorrow");
		result = MainLogic.runLogic("undo 5");
		result = MainLogic.runLogic("redo 5");
		
		String expectedFeedbackType = "redo";
		String feedbackType = result.get(FEEDBACK_TYPE).get(0);
		assertEquals(expectedFeedbackType, feedbackType);
				
		String expectedFeedbackDes = "5 step(s) redone";
		String feedbackDesc = result.get(FEEDBACK_DESC).get(0);
		assertEquals(expectedFeedbackDes, feedbackDesc);
		
		String expectedDesc = "1. assignment1 due";
		String taskDesc = result.get(TASK_DESC).get(0);
		assertEquals(expectedDesc, taskDesc);
		
		expectedDesc = "2. assignment2 due";
		taskDesc = result.get(TASK_DESC).get(1);
		assertEquals(expectedDesc, taskDesc);
		
		expectedDesc = "3. assignment3 due";
		taskDesc = result.get(TASK_DESC).get(2);
		assertEquals(expectedDesc, taskDesc);

		expectedDesc = "4. assignment4 due";
		taskDesc = result.get(TASK_DESC).get(3);
		assertEquals(expectedDesc, taskDesc);

		expectedDesc = "5. assignment5 due";
		taskDesc = result.get(TASK_DESC).get(4);
		assertEquals(expectedDesc, taskDesc);

		expectedDesc = "6. assignment6 due";
		taskDesc = result.get(TASK_DESC).get(5);
		assertEquals(expectedDesc, taskDesc);
	}
	
	@Test
	public void searchSuccess() {
		ArrayList<ArrayList<String>> result = MainLogic.runLogic("delete all");
		result = MainLogic.runLogic("add assignment due tomorrow");
		result = MainLogic.runLogic("search assignment");

		String expectedFeedbackType = "search";
		String feedbackType = result.get(FEEDBACK_TYPE).get(0);
		assertEquals(expectedFeedbackType, feedbackType);
				
		String expectedFeedbackDes = "Search for assignment";
		String feedbackDesc = result.get(FEEDBACK_DESC).get(0);
		assertEquals(expectedFeedbackDes, feedbackDesc);

		String expectedDesc = "1. assignment due";
		String taskDesc = result.get(TASK_DESC).get(0);
		assertEquals(expectedDesc, taskDesc);
	}
	@Test
	public void searchMultipleSuccess() {
		ArrayList<ArrayList<String>> result = MainLogic.runLogic("delete all");
		result = MainLogic.runLogic("add assignment1 due tomorrow");
		result = MainLogic.runLogic("add assignment2 due tomorrow");
		result = MainLogic.runLogic("add assignment3 due tomorrow");
		result = MainLogic.runLogic("search assignment");

		String expectedFeedbackType = "search";
		String feedbackType = result.get(FEEDBACK_TYPE).get(0);
		assertEquals(expectedFeedbackType, feedbackType);
				
		String expectedFeedbackDes = "Search for assignment";
		String feedbackDesc = result.get(FEEDBACK_DESC).get(0);
		assertEquals(expectedFeedbackDes, feedbackDesc);

		String expectedDesc = "1. assignment1 due";
		String taskDesc = result.get(TASK_DESC).get(0);
		assertEquals(expectedDesc, taskDesc);
		
		expectedDesc = "2. assignment2 due";
		taskDesc = result.get(TASK_DESC).get(1);
		assertEquals(expectedDesc, taskDesc);
		
		expectedDesc = "3. assignment3 due";
		taskDesc = result.get(TASK_DESC).get(2);
		assertEquals(expectedDesc, taskDesc);
	}	
	@Test
	public void searchFail() {
		ArrayList<ArrayList<String>> result = MainLogic.runLogic("delete all");
		result = MainLogic.runLogic("add assignment due tomorrow");
		result = MainLogic.runLogic("search");

		String expectedFeedbackType = "search";
		String feedbackType = result.get(FEEDBACK_TYPE).get(0);
		assertEquals(expectedFeedbackType, feedbackType);
				
		String expectedFeedbackDes = "Please enter something to search.";
		String feedbackDesc = result.get(FEEDBACK_DESC).get(0);
		assertEquals(expectedFeedbackDes, feedbackDesc);

		String expectedDesc = "1. assignment due";
		String taskDesc = result.get(TASK_DESC).get(0);
		assertEquals(expectedDesc, taskDesc);
	}
	@Test
	public void searchInvalidDescription() {
		ArrayList<ArrayList<String>> result = MainLogic.runLogic("delete all");
		result = MainLogic.runLogic("add assignment due tomorrow");
		result = MainLogic.runLogic("search banana");

		String expectedFeedbackType = "search";
		String feedbackType = result.get(FEEDBACK_TYPE).get(0);
		assertEquals(expectedFeedbackType, feedbackType);
				
		String expectedFeedbackDes = "Search for banana";
		String feedbackDesc = result.get(FEEDBACK_DESC).get(0);
		assertEquals(expectedFeedbackDes, feedbackDesc);

		assertTrue(result.get(TASK_DESC).isEmpty());
	}
	
	@Test
	public void listAll() {
		ArrayList<ArrayList<String>> result = MainLogic.runLogic("delete all");
		result = MainLogic.runLogic("add assignment due tomorrow");
		result = MainLogic.runLogic("list all");

		String expectedFeedbackType = "list";
		String feedbackType = result.get(FEEDBACK_TYPE).get(0);
		assertEquals(expectedFeedbackType, feedbackType);
				
		String expectedFeedbackDes = "Showing all tasks";
		String feedbackDesc = result.get(FEEDBACK_DESC).get(0);
		assertEquals(expectedFeedbackDes, feedbackDesc);

		String expectedDesc = "1. assignment due";
		String taskDesc = result.get(TASK_DESC).get(0);
		assertEquals(expectedDesc, taskDesc);
	}
	@Test
	public void listAllEmpty() {
		ArrayList<ArrayList<String>> result = MainLogic.runLogic("delete all");
		result = MainLogic.runLogic("list all");

		String expectedFeedbackType = "list";
		String feedbackType = result.get(FEEDBACK_TYPE).get(0);
		assertEquals(expectedFeedbackType, feedbackType);
				
		String expectedFeedbackDes = "Showing all tasks";
		String feedbackDesc = result.get(FEEDBACK_DESC).get(0);
		assertEquals(expectedFeedbackDes, feedbackDesc);

		assertTrue(result.get(TASK_DESC).isEmpty());
	}
	@Test
	public void listOverdueTrue() {
		ArrayList<ArrayList<String>> result = MainLogic.runLogic("delete all");
		result = MainLogic.runLogic("add assignment due yesterday");
		result = MainLogic.runLogic("list overdue");

		String expectedFeedbackType = "list";
		String feedbackType = result.get(FEEDBACK_TYPE).get(0);
		assertEquals(expectedFeedbackType, feedbackType);
				
		String expectedFeedbackDes = "Showing overdue tasks";
		String feedbackDesc = result.get(FEEDBACK_DESC).get(0);
		assertEquals(expectedFeedbackDes, feedbackDesc);

		String expectedDesc = "1. assignment due";
		String taskDesc = result.get(TASK_DESC).get(0);
		assertEquals(expectedDesc, taskDesc);
	}
	@Test
	public void listOverdueFail() {
		ArrayList<ArrayList<String>> result = MainLogic.runLogic("delete all");
		result = MainLogic.runLogic("add assignment due today");
		result = MainLogic.runLogic("list overdue");

		String expectedFeedbackType = "list";
		String feedbackType = result.get(FEEDBACK_TYPE).get(0);
		assertEquals(expectedFeedbackType, feedbackType);
				
		String expectedFeedbackDes = "Showing overdue tasks";
		String feedbackDesc = result.get(FEEDBACK_DESC).get(0);
		assertEquals(expectedFeedbackDes, feedbackDesc);
		
		assertTrue(result.get(TASK_DESC).isEmpty());
	}
	@Test
	public void listCompletedTrue() {
		ArrayList<ArrayList<String>> result = MainLogic.runLogic("delete all");
		result = MainLogic.runLogic("add assignment due today");
		result = MainLogic.runLogic("mark 1");
		result = MainLogic.runLogic("list completed");

		String expectedFeedbackType = "list";
		String feedbackType = result.get(FEEDBACK_TYPE).get(0);
		assertEquals(expectedFeedbackType, feedbackType);
				
		String expectedFeedbackDes = "Showing completed tasks";
		String feedbackDesc = result.get(FEEDBACK_DESC).get(0);
		assertEquals(expectedFeedbackDes, feedbackDesc);

		String expectedDesc = "1. assignment due";
		String taskDesc = result.get(TASK_DESC).get(0);
		assertEquals(expectedDesc, taskDesc);
	}
	@Test
	public void listCompletedFail() {
		ArrayList<ArrayList<String>> result = MainLogic.runLogic("delete all");
		result = MainLogic.runLogic("add assignment due today");
		result = MainLogic.runLogic("list completed");

		String expectedFeedbackType = "list";
		String feedbackType = result.get(FEEDBACK_TYPE).get(0);
		assertEquals(expectedFeedbackType, feedbackType);
				
		String expectedFeedbackDes = "Showing completed tasks";
		String feedbackDesc = result.get(FEEDBACK_DESC).get(0);
		assertEquals(expectedFeedbackDes, feedbackDesc);

		assertTrue(result.get(TASK_DESC).isEmpty());
	}
	@Test
	public void listIncommpletedFail() {
		ArrayList<ArrayList<String>> result = MainLogic.runLogic("delete all");
		result = MainLogic.runLogic("add assignment due today");
		result = MainLogic.runLogic("mark 1");
		result = MainLogic.runLogic("list incompleted");

		String expectedFeedbackType = "list";
		String feedbackType = result.get(FEEDBACK_TYPE).get(0);
		assertEquals(expectedFeedbackType, feedbackType);
				
		String expectedFeedbackDes = "Showing incomplete tasks";
		String feedbackDesc = result.get(FEEDBACK_DESC).get(0);
		assertEquals(expectedFeedbackDes, feedbackDesc);

		assertTrue(result.get(TASK_DESC).isEmpty());
	}
	@Test
	public void listInCompletedTrue() {
		ArrayList<ArrayList<String>> result = MainLogic.runLogic("delete all");
		result = MainLogic.runLogic("add assignment due today");
		result = MainLogic.runLogic("list incompleted");

		String expectedFeedbackType = "list";
		String feedbackType = result.get(FEEDBACK_TYPE).get(0);
		assertEquals(expectedFeedbackType, feedbackType);
				
		String expectedFeedbackDes = "Showing incomplete tasks";
		String feedbackDesc = result.get(FEEDBACK_DESC).get(0);
		assertEquals(expectedFeedbackDes, feedbackDesc);

		String expectedDesc = "1. assignment due";
		String taskDesc = result.get(TASK_DESC).get(0);
		assertEquals(expectedDesc, taskDesc);
	}
	@Test
	public void listDateTrue() {
		ArrayList<ArrayList<String>> result = MainLogic.runLogic("delete all");
		result = MainLogic.runLogic("add assignment due today");
		result = MainLogic.runLogic("list today");

		String expectedFeedbackType = "list";
		String feedbackType = result.get(FEEDBACK_TYPE).get(0);
		assertEquals(expectedFeedbackType, feedbackType);
				
		String expectedFeedbackDes = "Showing tasks on today";
		String feedbackDesc = result.get(FEEDBACK_DESC).get(0);
		assertEquals(expectedFeedbackDes, feedbackDesc);

		String expectedDesc = "1. assignment due";
		String taskDesc = result.get(TASK_DESC).get(0);
		assertEquals(expectedDesc, taskDesc);
	}
	@Test
	public void listDateFalse() {
		ArrayList<ArrayList<String>> result = MainLogic.runLogic("delete all");
		result = MainLogic.runLogic("add assignment due yesterday");
		result = MainLogic.runLogic("list 18.04.2014");

		String expectedFeedbackType = "list";
		String feedbackType = result.get(FEEDBACK_TYPE).get(0);
		assertEquals(expectedFeedbackType, feedbackType);
				
		String expectedFeedbackDes = "Showing tasks on 18.04.2014";
		String feedbackDesc = result.get(FEEDBACK_DESC).get(0);
		assertEquals(expectedFeedbackDes, feedbackDesc);

		assertTrue(result.get(TASK_DESC).isEmpty());
	}
	@Test
	public void listDateInvalid() {
		ArrayList<ArrayList<String>> result = MainLogic.runLogic("delete all");
		result = MainLogic.runLogic("add assignment due yesterday");
		result = MainLogic.runLogic("list 18.20.2014");

		String expectedFeedbackType = "list";
		String feedbackType = result.get(FEEDBACK_TYPE).get(0);
		assertEquals(expectedFeedbackType, feedbackType);
				
		String expectedFeedbackDes = "Oh no! Please enter a valid date or status";
		String feedbackDesc = result.get(FEEDBACK_DESC).get(0);
		assertEquals(expectedFeedbackDes, feedbackDesc);

		String expectedDesc = "1. assignment due";
		String taskDesc = result.get(TASK_DESC).get(0);
		assertEquals(expectedDesc, taskDesc);
	}
	
	@Test
	public void exitValid() {
		ArrayList<ArrayList<String>> result = MainLogic.runLogic("delete all");
		result = MainLogic.runLogic("exit");

		String expectedFeedbackType = "exit";
		String feedbackType = result.get(FEEDBACK_TYPE).get(0);
		assertEquals(expectedFeedbackType, feedbackType);
	}
	@Test
	public void exitExtraNum() {
		ArrayList<ArrayList<String>> result = MainLogic.runLogic("delete all");
		result = MainLogic.runLogic("exit 1253");

		String expectedFeedbackType = "exit";
		String feedbackType = result.get(FEEDBACK_TYPE).get(0);
		assertEquals(expectedFeedbackType, feedbackType);
	}
	@Test
	public void exitExtraText() {
		ArrayList<ArrayList<String>> result = MainLogic.runLogic("delete all");
		result = MainLogic.runLogic("exit ase");

		String expectedFeedbackType = "exit";
		String feedbackType = result.get(FEEDBACK_TYPE).get(0);
		assertEquals(expectedFeedbackType, feedbackType);
	}
	
	@Test
	public void customValidCommandValidCustom() {
		ArrayList<ArrayList<String>> result = MainLogic.runLogic("delete all");
		result = MainLogic.runLogic("add assignment due tomorrow");
		result = MainLogic.runLogic("custom add +");

		String expectedFeedbackType = "custom";
		String feedbackType = result.get(FEEDBACK_TYPE).get(0);
		assertEquals(expectedFeedbackType, feedbackType);
		
		String expectedFeedbackDes = "\"+\" has been added to the command list.";
		String feedbackDesc = result.get(FEEDBACK_DESC).get(0);
		assertEquals(expectedFeedbackDes, feedbackDesc);
		
		String expectedDesc = "1. assignment due";
		String taskDesc = result.get(TASK_DESC).get(0);
		assertEquals(expectedDesc, taskDesc);
		
		result = MainLogic.runLogic("dcustom +");
	}
	@Test
	public void customValidCommandInvalidCustom() {
		ArrayList<ArrayList<String>> result = MainLogic.runLogic("delete all");
		result = MainLogic.runLogic("add assignment due tomorrow");
		result = MainLogic.runLogic("custom add add");

		String expectedFeedbackType = "custom";
		String feedbackType = result.get(FEEDBACK_TYPE).get(0);
		assertEquals(expectedFeedbackType, feedbackType);
		
		String expectedFeedbackDes = "Sorry, but this word is already in use.";
		String feedbackDesc = result.get(FEEDBACK_DESC).get(0);
		assertEquals(expectedFeedbackDes, feedbackDesc);
		
		String expectedDesc = "1. assignment due";
		String taskDesc = result.get(TASK_DESC).get(0);
		assertEquals(expectedDesc, taskDesc);
	}
	@Test
	public void customInvalidCommandValidCustom() {
		ArrayList<ArrayList<String>> result = MainLogic.runLogic("delete all");
		result = MainLogic.runLogic("add assignment due tomorrow");
		result = MainLogic.runLogic("custom addy +");

		String expectedFeedbackType = "custom";
		String feedbackType = result.get(FEEDBACK_TYPE).get(0);
		assertEquals(expectedFeedbackType, feedbackType);
		
		String expectedFeedbackDes = "Invalid custom command format";
		String feedbackDesc = result.get(FEEDBACK_DESC).get(0);
		assertEquals(expectedFeedbackDes, feedbackDesc);
		
		String expectedDesc = "1. assignment due";
		String taskDesc = result.get(TASK_DESC).get(0);
		assertEquals(expectedDesc, taskDesc);
	}
	@Test
	public void customInvalidCommandInvalidCustom() {
		ArrayList<ArrayList<String>> result = MainLogic.runLogic("delete all");
		result = MainLogic.runLogic("add assignment due tomorrow");
		result = MainLogic.runLogic("custom addy add");

		String expectedFeedbackType = "custom";
		String feedbackType = result.get(FEEDBACK_TYPE).get(0);
		assertEquals(expectedFeedbackType, feedbackType);
		
		String expectedFeedbackDes = "Invalid custom command format";
		String feedbackDesc = result.get(FEEDBACK_DESC).get(0);
		assertEquals(expectedFeedbackDes, feedbackDesc);
		
		String expectedDesc = "1. assignment due";
		String taskDesc = result.get(TASK_DESC).get(0);
		assertEquals(expectedDesc, taskDesc);
	}
	@Test
	public void customEmptyCommandValidCustom() {
		ArrayList<ArrayList<String>> result = MainLogic.runLogic("delete all");
		result = MainLogic.runLogic("add assignment due tomorrow");
		result = MainLogic.runLogic("custom +");

		String expectedFeedbackType = "custom";
		String feedbackType = result.get(FEEDBACK_TYPE).get(0);
		assertEquals(expectedFeedbackType, feedbackType);
		
		String expectedFeedbackDes = "Invalid custom command format";
		String feedbackDesc = result.get(FEEDBACK_DESC).get(0);
		assertEquals(expectedFeedbackDes, feedbackDesc);
		
		String expectedDesc = "1. assignment due";
		String taskDesc = result.get(TASK_DESC).get(0);
		assertEquals(expectedDesc, taskDesc);
	}
	@Test
	public void customValidCommandEmptyCustom() {
		ArrayList<ArrayList<String>> result = MainLogic.runLogic("delete all");
		result = MainLogic.runLogic("add assignment due tomorrow");
		result = MainLogic.runLogic("custom add");

		String expectedFeedbackType = "custom";
		String feedbackType = result.get(FEEDBACK_TYPE).get(0);
		assertEquals(expectedFeedbackType, feedbackType);
		
		String expectedFeedbackDes = "Invalid custom command format";
		String feedbackDesc = result.get(FEEDBACK_DESC).get(0);
		assertEquals(expectedFeedbackDes, feedbackDesc);
		
		String expectedDesc = "1. assignment due";
		String taskDesc = result.get(TASK_DESC).get(0);
		assertEquals(expectedDesc, taskDesc);
	}
	
	@Test
	public void dcustomInvalidCustom() {
		ArrayList<ArrayList<String>> result = MainLogic.runLogic("delete all");
		result = MainLogic.runLogic("add assignment due tomorrow");
		result = MainLogic.runLogic("dcustom big");

		String expectedFeedbackType = "dcustom";
		String feedbackType = result.get(FEEDBACK_TYPE).get(0);
		assertEquals(expectedFeedbackType, feedbackType);
		
		String expectedFeedbackDes = "Error deleting. No such word in command list.";
		String feedbackDesc = result.get(FEEDBACK_DESC).get(0);
		assertEquals(expectedFeedbackDes, feedbackDesc);
		
		String expectedDesc = "1. assignment due";
		String taskDesc = result.get(TASK_DESC).get(0);
		assertEquals(expectedDesc, taskDesc);
	}
	@Test
	public void dcustomValidCustom() {
		ArrayList<ArrayList<String>> result = MainLogic.runLogic("delete all");
		result = MainLogic.runLogic("add assignment due tomorrow");
		result = MainLogic.runLogic("custom add +");
		result = MainLogic.runLogic("dcustom +");

		String expectedFeedbackType = "dcustom";
		String feedbackType = result.get(FEEDBACK_TYPE).get(0);
		assertEquals(expectedFeedbackType, feedbackType);
		
		String expectedFeedbackDes = "\"+\" has been deleted from the command list.";
		String feedbackDesc = result.get(FEEDBACK_DESC).get(0);
		assertEquals(expectedFeedbackDes, feedbackDesc);
		
		String expectedDesc = "1. assignment due";
		String taskDesc = result.get(TASK_DESC).get(0);
		assertEquals(expectedDesc, taskDesc);
	}
	@Test
	public void dcustomEmpty() {
		ArrayList<ArrayList<String>> result = MainLogic.runLogic("delete all");
		result = MainLogic.runLogic("add assignment due tomorrow");
		result = MainLogic.runLogic("dcustom");

		String expectedFeedbackType = "dcustom";
		String feedbackType = result.get(FEEDBACK_TYPE).get(0);
		assertEquals(expectedFeedbackType, feedbackType);
		
		String expectedFeedbackDes = "Error deleting. No such word in command list.";
		String feedbackDesc = result.get(FEEDBACK_DESC).get(0);
		assertEquals(expectedFeedbackDes, feedbackDesc);
		
		String expectedDesc = "1. assignment due";
		String taskDesc = result.get(TASK_DESC).get(0);
		assertEquals(expectedDesc, taskDesc);
	}
	
	@Test
	public void markUnmarkedTask() {
		ArrayList<ArrayList<String>> result = MainLogic.runLogic("delete all");
		result = MainLogic.runLogic("add assignment due tomorrow");
		result = MainLogic.runLogic("mark 1");

		String expectedFeedbackType = "mark";
		String feedbackType = result.get(FEEDBACK_TYPE).get(0);
		assertEquals(expectedFeedbackType, feedbackType);
		
		String expectedFeedbackDes = "Task(s) have been marked.";
		String feedbackDesc = result.get(FEEDBACK_DESC).get(0);
		assertEquals(expectedFeedbackDes, feedbackDesc);
		
		assertTrue(result.get(TASK_DESC).isEmpty());
	}
	@Test
	public void markMarkedTask() {
		ArrayList<ArrayList<String>> result = MainLogic.runLogic("delete all");
		result = MainLogic.runLogic("add assignment due tomorrow");
		result = MainLogic.runLogic("mark 1");
		result = MainLogic.runLogic("mark 1");

		String expectedFeedbackType = "mark";
		String feedbackType = result.get(FEEDBACK_TYPE).get(0);
		assertEquals(expectedFeedbackType, feedbackType);
		
		String expectedFeedbackDes = "Task(s) have been marked.";
		String feedbackDesc = result.get(FEEDBACK_DESC).get(0);
		assertEquals(expectedFeedbackDes, feedbackDesc);
		
		String expectedDesc = "1. assignment due";
		String taskDesc = result.get(TASK_DESC).get(0);
		assertEquals(expectedDesc, taskDesc);
	}
	@Test
	public void markUnmarkedAlias() {
		ArrayList<ArrayList<String>> result = MainLogic.runLogic("delete all");
		result = MainLogic.runLogic("add assignment due tomorrow alias:NUS");
		result = MainLogic.runLogic("mark NUS");

		String expectedFeedbackType = "mark";
		String feedbackType = result.get(FEEDBACK_TYPE).get(0);
		assertEquals(expectedFeedbackType, feedbackType);
		
		String expectedFeedbackDes = "Task(s) have been marked.";
		String feedbackDesc = result.get(FEEDBACK_DESC).get(0);
		assertEquals(expectedFeedbackDes, feedbackDesc);
		
		assertTrue(result.get(TASK_DESC).isEmpty());
	}
	@Test
	public void markMarkedAlias() {
		ArrayList<ArrayList<String>> result = MainLogic.runLogic("delete all");
		result = MainLogic.runLogic("add assignment due tomorrow alias:NUS");
		result = MainLogic.runLogic("mark NUS");
		result = MainLogic.runLogic("mark NUS");

		String expectedFeedbackType = "mark";
		String feedbackType = result.get(FEEDBACK_TYPE).get(0);
		assertEquals(expectedFeedbackType, feedbackType);
		
		String expectedFeedbackDes = "Task(s) have been marked.";
		String feedbackDesc = result.get(FEEDBACK_DESC).get(0);
		assertEquals(expectedFeedbackDes, feedbackDesc);
		
		String expectedDesc = "1. assignment due";
		String taskDesc = result.get(TASK_DESC).get(0);
		assertEquals(expectedDesc, taskDesc);
	}
	@Test
	public void markInvalidTask() {
		ArrayList<ArrayList<String>> result = MainLogic.runLogic("delete all");
		result = MainLogic.runLogic("add assignment due tomorrow alias:NUS");
		result = MainLogic.runLogic("mark 3");

		String expectedFeedbackType = "error";
		String feedbackType = result.get(FEEDBACK_TYPE).get(0);
		assertEquals(expectedFeedbackType, feedbackType);
		
		String expectedFeedbackDes = "Nothing to mark.";
		String feedbackDesc = result.get(FEEDBACK_DESC).get(0);
		assertEquals(expectedFeedbackDes, feedbackDesc);
		
		String expectedDesc = "1. assignment due";
		String taskDesc = result.get(TASK_DESC).get(0);
		assertEquals(expectedDesc, taskDesc);
	}
	@Test
	public void markInvalidAlias() {
		ArrayList<ArrayList<String>> result = MainLogic.runLogic("delete all");
		result = MainLogic.runLogic("add assignment due tomorrow alias:NUS");
		result = MainLogic.runLogic("mark NTU");

		String expectedFeedbackType = "error";
		String feedbackType = result.get(FEEDBACK_TYPE).get(0);
		assertEquals(expectedFeedbackType, feedbackType);
		
		String expectedFeedbackDes = "Nothing to mark.";
		String feedbackDesc = result.get(FEEDBACK_DESC).get(0);
		assertEquals(expectedFeedbackDes, feedbackDesc);
		
		String expectedDesc = "1. assignment due";
		String taskDesc = result.get(TASK_DESC).get(0);
		assertEquals(expectedDesc, taskDesc);
	}
	
	@Test
	public void deleteOneTask() {
		ArrayList<ArrayList<String>> result = MainLogic.runLogic("delete all");
		result = MainLogic.runLogic("add assignment due tomorrow alias:NUS");
		result = MainLogic.runLogic("delete 1");

		String expectedFeedbackType = "delete";
		String feedbackType = result.get(FEEDBACK_TYPE).get(0);
		assertEquals(expectedFeedbackType, feedbackType);
		
		String expectedFeedbackDes = "All specified tasks have been deleted.";
		String feedbackDesc = result.get(FEEDBACK_DESC).get(0);
		assertEquals(expectedFeedbackDes, feedbackDesc);

		assertTrue(result.get(TASK_DESC).isEmpty());
	}
	@Test
	public void deleteMultipleTask() {
		ArrayList<ArrayList<String>> result = MainLogic.runLogic("delete all");
		result = MainLogic.runLogic("add assignment1 due tomorrow alias:NUS");
		result = MainLogic.runLogic("add assignment2 due tomorrow alias:NUS");
		result = MainLogic.runLogic("add assignment3 due tomorrow alias:NUS");
		result = MainLogic.runLogic("add assignment4 due tomorrow alias:NUS");
		result = MainLogic.runLogic("add assignment5 due tomorrow alias:NUS");
		result = MainLogic.runLogic("delete 1 3 5");
		
		String expectedFeedbackType = "delete";
		String feedbackType = result.get(FEEDBACK_TYPE).get(0);
		assertEquals(expectedFeedbackType, feedbackType);
		
		String expectedFeedbackDes = "All specified tasks have been deleted.";
		String feedbackDesc = result.get(FEEDBACK_DESC).get(0);
		assertEquals(expectedFeedbackDes, feedbackDesc);

		String expectedDesc = "1. assignment2 due";
		String taskDesc = result.get(TASK_DESC).get(0);
		assertEquals(expectedDesc, taskDesc);

		expectedDesc = "2. assignment4 due";
		taskDesc = result.get(TASK_DESC).get(1);
		assertEquals(expectedDesc, taskDesc);
	}
	@Test
	public void deleteOneAlias() {
		ArrayList<ArrayList<String>> result = MainLogic.runLogic("delete all");
		result = MainLogic.runLogic("add assignment due tomorrow alias:NUS");
		result = MainLogic.runLogic("delete NUS");

		String expectedFeedbackType = "delete";
		String feedbackType = result.get(FEEDBACK_TYPE).get(0);
		assertEquals(expectedFeedbackType, feedbackType);
		
		String expectedFeedbackDes = "All specified tasks have been deleted.";
		String feedbackDesc = result.get(FEEDBACK_DESC).get(0);
		assertEquals(expectedFeedbackDes, feedbackDesc);

		assertTrue(result.get(TASK_DESC).isEmpty());
	}
	@Test
	public void deleteMultipleAlias() {
		ArrayList<ArrayList<String>> result = MainLogic.runLogic("delete all");
		result = MainLogic.runLogic("add assignment1 due tomorrow alias:NUS0");
		result = MainLogic.runLogic("add assignment2 due tomorrow alias:NUS1");
		result = MainLogic.runLogic("add assignment3 due tomorrow alias:NUS2");
		result = MainLogic.runLogic("add assignment4 due tomorrow alias:NUS3");
		result = MainLogic.runLogic("add assignment5 due tomorrow alias:NUS4");
		result = MainLogic.runLogic("delete NUS0 NUS2 NUS4");
		
		String expectedFeedbackType = "delete";
		String feedbackType = result.get(FEEDBACK_TYPE).get(0);
		assertEquals(expectedFeedbackType, feedbackType);
		
		String expectedFeedbackDes = "All specified tasks have been deleted.";
		String feedbackDesc = result.get(FEEDBACK_DESC).get(0);
		assertEquals(expectedFeedbackDes, feedbackDesc);

		String expectedDesc = "1. assignment2 due";
		String taskDesc = result.get(TASK_DESC).get(0);
		assertEquals(expectedDesc, taskDesc);

		expectedDesc = "2. assignment4 due";
		taskDesc = result.get(TASK_DESC).get(1);
		assertEquals(expectedDesc, taskDesc);
	}
	@Test
	public void deleteOneTaskOneAlias() {
		ArrayList<ArrayList<String>> result = MainLogic.runLogic("delete all");
		result = MainLogic.runLogic("add assignment1 due tomorrow alias:NUS0");
		result = MainLogic.runLogic("add assignment2 due tomorrow alias:NUS1");
		result = MainLogic.runLogic("delete 1 NUS1");
		
		String expectedFeedbackType = "delete";
		String feedbackType = result.get(FEEDBACK_TYPE).get(0);
		assertEquals(expectedFeedbackType, feedbackType);
		
		String expectedFeedbackDes = "All specified tasks have been deleted.";
		String feedbackDesc = result.get(FEEDBACK_DESC).get(0);
		assertEquals(expectedFeedbackDes, feedbackDesc);

		assertTrue(result.get(TASK_DESC).isEmpty());
	}
	@Test
	public void deleteOneTaskMultipleAlias() {
		ArrayList<ArrayList<String>> result = MainLogic.runLogic("delete all");
		result = MainLogic.runLogic("add assignment1 due tomorrow alias:NUS0");
		result = MainLogic.runLogic("add assignment2 due tomorrow alias:NUS1");
		result = MainLogic.runLogic("add assignment3 due tomorrow alias:NUS2");
		result = MainLogic.runLogic("add assignment4 due tomorrow alias:NUS3");
		result = MainLogic.runLogic("add assignment5 due tomorrow alias:NUS4");
		result = MainLogic.runLogic("delete 1 NUS2 NUS4");
		
		String expectedFeedbackType = "delete";
		String feedbackType = result.get(FEEDBACK_TYPE).get(0);
		assertEquals(expectedFeedbackType, feedbackType);
		
		String expectedFeedbackDes = "All specified tasks have been deleted.";
		String feedbackDesc = result.get(FEEDBACK_DESC).get(0);
		assertEquals(expectedFeedbackDes, feedbackDesc);

		String expectedDesc = "1. assignment2 due";
		String taskDesc = result.get(TASK_DESC).get(0);
		assertEquals(expectedDesc, taskDesc);

		expectedDesc = "2. assignment4 due";
		taskDesc = result.get(TASK_DESC).get(1);
		assertEquals(expectedDesc, taskDesc);
	}
	@Test
	public void deleteMultipleTaskOneAlias() {
		ArrayList<ArrayList<String>> result = MainLogic.runLogic("delete all");
		result = MainLogic.runLogic("add assignment1 due tomorrow alias:NUS0");
		result = MainLogic.runLogic("add assignment2 due tomorrow alias:NUS1");
		result = MainLogic.runLogic("add assignment3 due tomorrow alias:NUS2");
		result = MainLogic.runLogic("add assignment4 due tomorrow alias:NUS3");
		result = MainLogic.runLogic("add assignment5 due tomorrow alias:NUS4");
		result = MainLogic.runLogic("delete 1 3 NUS4");
		
		String expectedFeedbackType = "delete";
		String feedbackType = result.get(FEEDBACK_TYPE).get(0);
		assertEquals(expectedFeedbackType, feedbackType);
		
		String expectedFeedbackDes = "All specified tasks have been deleted.";
		String feedbackDesc = result.get(FEEDBACK_DESC).get(0);
		assertEquals(expectedFeedbackDes, feedbackDesc);

		String expectedDesc = "1. assignment2 due";
		String taskDesc = result.get(TASK_DESC).get(0);
		assertEquals(expectedDesc, taskDesc);

		expectedDesc = "2. assignment4 due";
		taskDesc = result.get(TASK_DESC).get(1);
		assertEquals(expectedDesc, taskDesc);
	}
	@Test
	public void deleteMultipleTaskMultipleAlias() {
		ArrayList<ArrayList<String>> result = MainLogic.runLogic("delete all");
		result = MainLogic.runLogic("add assignment1 due tomorrow alias:NUS0");
		result = MainLogic.runLogic("add assignment2 due tomorrow alias:NUS1");
		result = MainLogic.runLogic("add assignment3 due tomorrow alias:NUS2");
		result = MainLogic.runLogic("add assignment4 due tomorrow alias:NUS3");
		result = MainLogic.runLogic("add assignment5 due tomorrow alias:NUS4");
		result = MainLogic.runLogic("add assignment6 due tomorrow alias:NUS5");
		result = MainLogic.runLogic("delete 1 3 NUS4 NUS5");
		
		String expectedFeedbackType = "delete";
		String feedbackType = result.get(FEEDBACK_TYPE).get(0);
		assertEquals(expectedFeedbackType, feedbackType);
		
		String expectedFeedbackDes = "All specified tasks have been deleted.";
		String feedbackDesc = result.get(FEEDBACK_DESC).get(0);
		assertEquals(expectedFeedbackDes, feedbackDesc);

		String expectedDesc = "1. assignment2 due";
		String taskDesc = result.get(TASK_DESC).get(0);
		assertEquals(expectedDesc, taskDesc);

		expectedDesc = "2. assignment4 due";
		taskDesc = result.get(TASK_DESC).get(1);
		assertEquals(expectedDesc, taskDesc);
	}
	@Test
	public void deleteZero() {
		ArrayList<ArrayList<String>> result = MainLogic.runLogic("delete all");
		result = MainLogic.runLogic("add assignment1 due tomorrow alias:NUS0");
		result = MainLogic.runLogic("delete 0");
		
		String expectedFeedbackType = "error";
		String feedbackType = result.get(FEEDBACK_TYPE).get(0);
		assertEquals(expectedFeedbackType, feedbackType);
		
		String expectedFeedbackDes = "No such task(s). Please enter a valid number or alias.";
		String feedbackDesc = result.get(FEEDBACK_DESC).get(0);
		assertEquals(expectedFeedbackDes, feedbackDesc);

		String expectedDesc = "1. assignment1 due";
		String taskDesc = result.get(TASK_DESC).get(0);
		assertEquals(expectedDesc, taskDesc);
	}
	@Test
	public void deleteMaxPlusOne() {
		ArrayList<ArrayList<String>> result = MainLogic.runLogic("delete all");
		result = MainLogic.runLogic("add assignment1 due tomorrow alias:NUS0");
		result = MainLogic.runLogic("delete 2");
		
		String expectedFeedbackType = "error";
		String feedbackType = result.get(FEEDBACK_TYPE).get(0);
		assertEquals(expectedFeedbackType, feedbackType);
		
		String expectedFeedbackDes = "No such task(s). Please enter a valid number or alias.";
		String feedbackDesc = result.get(FEEDBACK_DESC).get(0);
		assertEquals(expectedFeedbackDes, feedbackDesc);

		String expectedDesc = "1. assignment1 due";
		String taskDesc = result.get(TASK_DESC).get(0);
		assertEquals(expectedDesc, taskDesc);
	}
	@Test
	public void deleteInvalidAlias() {
		ArrayList<ArrayList<String>> result = MainLogic.runLogic("delete all");
		result = MainLogic.runLogic("add assignment1 due tomorrow alias:NUS0");
		result = MainLogic.runLogic("delete NUS1");
		
		String expectedFeedbackType = "error";
		String feedbackType = result.get(FEEDBACK_TYPE).get(0);
		assertEquals(expectedFeedbackType, feedbackType);
		
		String expectedFeedbackDes = "No such task(s). Please enter a valid number or alias.";
		String feedbackDesc = result.get(FEEDBACK_DESC).get(0);
		assertEquals(expectedFeedbackDes, feedbackDesc);

		String expectedDesc = "1. assignment1 due";
		String taskDesc = result.get(TASK_DESC).get(0);
		assertEquals(expectedDesc, taskDesc);
	}
	@Test
	public void deleteEmpty() {
		ArrayList<ArrayList<String>> result = MainLogic.runLogic("delete all");
		result = MainLogic.runLogic("add assignment1 due tomorrow alias:NUS0");
		result = MainLogic.runLogic("delete");
		
		String expectedFeedbackType = "error";
		String feedbackType = result.get(FEEDBACK_TYPE).get(0);
		assertEquals(expectedFeedbackType, feedbackType);
		
		String expectedFeedbackDes = "Hmmm ... Please enter a task to delete";
		String feedbackDesc = result.get(FEEDBACK_DESC).get(0);
		assertEquals(expectedFeedbackDes, feedbackDesc);

		String expectedDesc = "1. assignment1 due";
		String taskDesc = result.get(TASK_DESC).get(0);
		assertEquals(expectedDesc, taskDesc);
	}
}
