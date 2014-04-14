package dothings.tests;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import org.junit.Test;

import dothings.logic.MainLogic;

//@author A0097082Y
public class IntegrationTesting {
	private static final int FEEDBACK_TYPE = 0;
	private static final int FEEDBACK_DESC = 1;
	private static final int TASK_DESC = 2;
	private static final int TASK_ALIAS = 3;
	private static final int TASK_STATUS = 4;
	private static final int TASK_DATE = 5;
	private static final int TASK_TIME = 6;

	private String getTodayDate() {
		String date;
		SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy");
		Calendar calendar = new GregorianCalendar();
		Date now = new Date();
		calendar.setTime(now);
		date = formatter.format(now);
		return date;
	}
	private String getYesterdayDate() {
		String date;
		SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy");
		Calendar calendar = new GregorianCalendar();
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		Date now = calendar.getTime();
		date = formatter.format(now);
		return date;
	}
	private String getTomorrowDate() {
		String date;
		SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy");
		Calendar calendar = new GregorianCalendar();
		calendar.add(Calendar.DAY_OF_MONTH, 1);
		Date now = calendar.getTime();
		date = formatter.format(now);
		return date;
	}
	private String getDayAfterDate() {
		String date;
		SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy");
		Calendar calendar = new GregorianCalendar();
		calendar.add(Calendar.DAY_OF_MONTH, 2);
		Date now = calendar.getTime();
		date = formatter.format(now);
		return date;
	}
	private String getDayAfterDateDash() {
		String date;
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		Calendar calendar = new GregorianCalendar();
		calendar.add(Calendar.DAY_OF_MONTH, 2);
		Date now = calendar.getTime();
		date = formatter.format(now);
		return date;
	}
	
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
		result = MainLogic.runLogic("list yesterday");

		String expectedFeedbackType = "list";
		String feedbackType = result.get(FEEDBACK_TYPE).get(0);
		assertEquals(expectedFeedbackType, feedbackType);
				
		String expectedFeedbackDes = "Showing tasks on yesterday";
		String feedbackDesc = result.get(FEEDBACK_DESC).get(0);
		assertEquals(expectedFeedbackDes, feedbackDesc);

		assertFalse(result.get(TASK_DESC).isEmpty());
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
	
	@Test
	public void updateEmpty() {
		ArrayList<ArrayList<String>> result = MainLogic.runLogic("delete all");
		result = MainLogic.runLogic("add assignment1 due tomorrow alias:NUS0");
		result = MainLogic.runLogic("update");
		
		String expectedFeedbackType = "update";
		String feedbackType = result.get(FEEDBACK_TYPE).get(0);
		assertEquals(expectedFeedbackType, feedbackType);
		
		String expectedFeedbackDes = "Incorrect update format.";
		String feedbackDesc = result.get(FEEDBACK_DESC).get(0);
		assertEquals(expectedFeedbackDes, feedbackDesc);

		String expectedDesc = "1. assignment1 due";
		String taskDesc = result.get(TASK_DESC).get(0);
		assertEquals(expectedDesc, taskDesc);
	}
	@Test
	public void updateSingleDescription() {
		ArrayList<ArrayList<String>> result = MainLogic.runLogic("delete all");
		result = MainLogic.runLogic("add assignment1 from today tomorrow alias:NUS0");
		result = MainLogic.runLogic("update 1 assignment2");
		
		String expectedFeedbackType = "update";
		String feedbackType = result.get(FEEDBACK_TYPE).get(0);
		assertEquals(expectedFeedbackType, feedbackType);
		
		String expectedFeedbackDes = "Task has been updated.";
		String feedbackDesc = result.get(FEEDBACK_DESC).get(0);
		assertEquals(expectedFeedbackDes, feedbackDesc);

		String expectedDesc = "1. assignment2";
		String taskDesc = result.get(TASK_DESC).get(0);
		assertEquals(expectedDesc, taskDesc);
	}
	@Test
	public void updateSingleDescriptionSingleTime() {
		ArrayList<ArrayList<String>> result = MainLogic.runLogic("delete all");
		result = MainLogic.runLogic("add assignment1 from today tomorrow alias:NUS0");
		result = MainLogic.runLogic("update 1 assignment2 0700");
		
		String expectedFeedbackType = "update";
		String feedbackType = result.get(FEEDBACK_TYPE).get(0);
		assertEquals(expectedFeedbackType, feedbackType);
		
		String expectedFeedbackDes = "Task has been updated.";
		String feedbackDesc = result.get(FEEDBACK_DESC).get(0);
		assertEquals(expectedFeedbackDes, feedbackDesc);

		String expectedDesc = "1. assignment2";
		String taskDesc = result.get(TASK_DESC).get(0);
		assertEquals(expectedDesc, taskDesc);
		
		String expectedDate = getTodayDate() +" 07:00";
		assertEquals( expectedDate,result.get(TASK_DATE).get(0));
	}
	@Test
	public void updateSingleDescriptionSingleTimeSingleDate() {
		ArrayList<ArrayList<String>> result = MainLogic.runLogic("delete all");
		result = MainLogic.runLogic("add assignment1 from today tomorrow alias:NUS0");
		result = MainLogic.runLogic("update 1 assignment2 0700 15-04-2014");
		
		String expectedFeedbackType = "update";
		String feedbackType = result.get(FEEDBACK_TYPE).get(0);
		assertEquals(expectedFeedbackType, feedbackType);
		
		String expectedFeedbackDes = "Task has been updated.";
		String feedbackDesc = result.get(FEEDBACK_DESC).get(0);
		assertEquals(expectedFeedbackDes, feedbackDesc);

		String expectedDesc = "1. assignment2";
		String taskDesc = result.get(TASK_DESC).get(0);
		assertEquals(expectedDesc, taskDesc);
		
		String expectedDate = "15 Apr 2014 07:00";
		assertEquals( expectedDate,result.get(TASK_DATE).get(0));
	}
	@Test
	public void updateSingleDescriptionSingleTimeSingleDateSingleAlias() {
		ArrayList<ArrayList<String>> result = MainLogic.runLogic("delete all");
		result = MainLogic.runLogic("add assignment1 from today tomorrow alias:NUS0");
		result = MainLogic.runLogic("update 1 assignment2 0700 15-04-2014 alias:NUS1");
		
		String expectedFeedbackType = "update";
		String feedbackType = result.get(FEEDBACK_TYPE).get(0);
		assertEquals(expectedFeedbackType, feedbackType);
		
		String expectedFeedbackDes = "Task has been updated.";
		String feedbackDesc = result.get(FEEDBACK_DESC).get(0);
		assertEquals(expectedFeedbackDes, feedbackDesc);

		String expectedDesc = "1. assignment2";
		String taskDesc = result.get(TASK_DESC).get(0);
		assertEquals(expectedDesc, taskDesc);
		
		String expectedDate = "15 Apr 2014 07:00";
		assertEquals( expectedDate,result.get(TASK_DATE).get(0));
		
		String expectedAlias = "NUS1";
		assertEquals(expectedAlias, result.get(TASK_ALIAS).get(0));
	}
	@Test
	public void updateSingleDescriptionSingleDate() {
		ArrayList<ArrayList<String>> result = MainLogic.runLogic("delete all");
		result = MainLogic.runLogic("add assignment1 from today tomorrow alias:NUS0");
		result = MainLogic.runLogic("update 1 assignment2 15-04-2014");
		
		String expectedFeedbackType = "update";
		String feedbackType = result.get(FEEDBACK_TYPE).get(0);
		assertEquals(expectedFeedbackType, feedbackType);
		
		String expectedFeedbackDes = "Task has been updated.";
		String feedbackDesc = result.get(FEEDBACK_DESC).get(0);
		assertEquals(expectedFeedbackDes, feedbackDesc);

		String expectedDesc = "1. assignment2";
		String taskDesc = result.get(TASK_DESC).get(0);
		assertEquals(expectedDesc, taskDesc);
		
		String expectedDate = "15 Apr 2014 23:59";
		assertEquals( expectedDate,result.get(TASK_DATE).get(0));
	}
	@Test
	public void updateSingleDescriptionSingleDateSingleAlias() {
		ArrayList<ArrayList<String>> result = MainLogic.runLogic("delete all");
		result = MainLogic.runLogic("add assignment1 from today tomorrow alias:NUS0");
		result = MainLogic.runLogic("update 1 assignment2 15-04-2014 alias:NUS1");
		
		String expectedFeedbackType = "update";
		String feedbackType = result.get(FEEDBACK_TYPE).get(0);
		assertEquals(expectedFeedbackType, feedbackType);
		
		String expectedFeedbackDes = "Task has been updated.";
		String feedbackDesc = result.get(FEEDBACK_DESC).get(0);
		assertEquals(expectedFeedbackDes, feedbackDesc);

		String expectedDesc = "1. assignment2";
		String taskDesc = result.get(TASK_DESC).get(0);
		assertEquals(expectedDesc, taskDesc);
		
		String expectedDate = "15 Apr 2014 23:59";
		assertEquals( expectedDate,result.get(TASK_DATE).get(0));
		
		String expectedAlias = "NUS1";
		assertEquals(expectedAlias, result.get(TASK_ALIAS).get(0));
	}
	@Test
	public void updateSingleDescriptionSingleAlias() {
		ArrayList<ArrayList<String>> result = MainLogic.runLogic("delete all");
		result = MainLogic.runLogic("add assignment1 from today tomorrow alias:NUS0");
		result = MainLogic.runLogic("update 1 assignment2 alias:NUS1");
		
		String expectedFeedbackType = "update";
		String feedbackType = result.get(FEEDBACK_TYPE).get(0);
		assertEquals(expectedFeedbackType, feedbackType);
		
		String expectedFeedbackDes = "Task has been updated.";
		String feedbackDesc = result.get(FEEDBACK_DESC).get(0);
		assertEquals(expectedFeedbackDes, feedbackDesc);

		String expectedDesc = "1. assignment2";
		String taskDesc = result.get(TASK_DESC).get(0);
		assertEquals(expectedDesc, taskDesc);
		
		String expectedAlias = "NUS1";
		assertEquals(expectedAlias, result.get(TASK_ALIAS).get(0));
	}
	@Test
	public void updateSingleDescriptionSingleTimeSingleAlias() {
		ArrayList<ArrayList<String>> result = MainLogic.runLogic("delete all");
		result = MainLogic.runLogic("add assignment1 from today tomorrow alias:NUS0");
		result = MainLogic.runLogic("update 1 assignment2 0700 alias:NUS1");
		
		String expectedFeedbackType = "update";
		String feedbackType = result.get(FEEDBACK_TYPE).get(0);
		assertEquals(expectedFeedbackType, feedbackType);
		
		String expectedFeedbackDes = "Task has been updated.";
		String feedbackDesc = result.get(FEEDBACK_DESC).get(0);
		assertEquals(expectedFeedbackDes, feedbackDesc);

		String expectedDesc = "1. assignment2";
		String taskDesc = result.get(TASK_DESC).get(0);
		assertEquals(expectedDesc, taskDesc);
		
		String expectedDate = getTodayDate() + " 07:00";
		assertEquals( expectedDate,result.get(TASK_DATE).get(0));
		
		String expectedAlias = "NUS1";
		assertEquals(expectedAlias, result.get(TASK_ALIAS).get(0));
	}
	@Test
	public void updateTaskDescriptionValid() {
		ArrayList<ArrayList<String>> result = MainLogic.runLogic("delete all");
		result = MainLogic.runLogic("add assignment1 from today tomorrow alias:NUS0");
		result = MainLogic.runLogic("update 1 desc assignment2");
		
		String expectedFeedbackType = "update";
		String feedbackType = result.get(FEEDBACK_TYPE).get(0);
		assertEquals(expectedFeedbackType, feedbackType);
		
		String expectedFeedbackDes = "Task has been updated.";
		String feedbackDesc = result.get(FEEDBACK_DESC).get(0);
		assertEquals(expectedFeedbackDes, feedbackDesc);

		String expectedDesc = "1. assignment2";
		String taskDesc = result.get(TASK_DESC).get(0);
		assertEquals(expectedDesc, taskDesc);
		
		String expectedDate = getTodayDate() + " 00:00 to " + getTomorrowDate() + " 23:59";
		assertEquals( expectedDate,result.get(TASK_DATE).get(0));
		
		String expectedAlias = "NUS0";
		assertEquals(expectedAlias, result.get(TASK_ALIAS).get(0));
	}
	@Test
	public void updateTaskDescriptionInvalid() {
		ArrayList<ArrayList<String>> result = MainLogic.runLogic("delete all");
		result = MainLogic.runLogic("add assignment1 from today tomorrow alias:NUS0");
		result = MainLogic.runLogic("update 1 desc");
		
		String expectedFeedbackType = "error";
		String feedbackType = result.get(FEEDBACK_TYPE).get(0);
		assertEquals(expectedFeedbackType, feedbackType);
		
		String expectedFeedbackDes = "Incorrect update format.";
		String feedbackDesc = result.get(FEEDBACK_DESC).get(0);
		assertEquals(expectedFeedbackDes, feedbackDesc);

		String expectedDesc = "1. assignment1";
		String taskDesc = result.get(TASK_DESC).get(0);
		assertEquals(expectedDesc, taskDesc);
		
		String expectedDate = getTodayDate() + " 00:00 to " + getTomorrowDate() + " 23:59";
		assertEquals( expectedDate,result.get(TASK_DATE).get(0));
		
		String expectedAlias = "NUS0";
		assertEquals(expectedAlias, result.get(TASK_ALIAS).get(0));
	}
	@Test
	public void updateTaskDescriptionInvalidNumber() {
		ArrayList<ArrayList<String>> result = MainLogic.runLogic("delete all");
		result = MainLogic.runLogic("add assignment1 from today tomorrow alias:NUS0");
		result = MainLogic.runLogic("update 3 desc");
		
		String expectedFeedbackType = "error";
		String feedbackType = result.get(FEEDBACK_TYPE).get(0);
		assertEquals(expectedFeedbackType, feedbackType);
		
		String expectedFeedbackDes = "Please enter a valid task number to update.";
		String feedbackDesc = result.get(FEEDBACK_DESC).get(0);
		assertEquals(expectedFeedbackDes, feedbackDesc);

		String expectedDesc = "1. assignment1";
		String taskDesc = result.get(TASK_DESC).get(0);
		assertEquals(expectedDesc, taskDesc);
		
		String expectedDate = getTodayDate() + " 00:00 to " + getTomorrowDate() + " 23:59";
		assertEquals( expectedDate,result.get(TASK_DATE).get(0));
		
		String expectedAlias = "NUS0";
		assertEquals(expectedAlias, result.get(TASK_ALIAS).get(0));
	}
	@Test
	public void updateTaskTimeValid() {
		ArrayList<ArrayList<String>> result = MainLogic.runLogic("delete all");
		result = MainLogic.runLogic("add assignment1 from today tomorrow alias:NUS0");
		result = MainLogic.runLogic("update 1 time 15042014 04:00");
		
		String expectedFeedbackType = "update";
		String feedbackType = result.get(FEEDBACK_TYPE).get(0);
		assertEquals(expectedFeedbackType, feedbackType);
		
		String expectedFeedbackDes = "Task has been updated.";
		String feedbackDesc = result.get(FEEDBACK_DESC).get(0);
		assertEquals(expectedFeedbackDes, feedbackDesc);

		String expectedDesc = "1. assignment1";
		String taskDesc = result.get(TASK_DESC).get(0);
		assertEquals(expectedDesc, taskDesc);
		
		String expectedDate = "15 Apr 2014 04:00";
		assertEquals( expectedDate,result.get(TASK_DATE).get(0));
		
		String expectedAlias = "NUS0";
		assertEquals(expectedAlias, result.get(TASK_ALIAS).get(0));
	}
	@Test
	public void updateTaskTimeInvalidNumber() {
		ArrayList<ArrayList<String>> result = MainLogic.runLogic("delete all");
		result = MainLogic.runLogic("add assignment1 from today tomorrow alias:NUS0");
		result = MainLogic.runLogic("update 3 time 15042014 04:00");
		
		String expectedFeedbackType = "error";
		String feedbackType = result.get(FEEDBACK_TYPE).get(0);
		assertEquals(expectedFeedbackType, feedbackType);
		
		String expectedFeedbackDes = "Please enter a valid task number to update.";
		String feedbackDesc = result.get(FEEDBACK_DESC).get(0);
		assertEquals(expectedFeedbackDes, feedbackDesc);

		String expectedDesc = "1. assignment1";
		String taskDesc = result.get(TASK_DESC).get(0);
		assertEquals(expectedDesc, taskDesc);
		
		String expectedDate = getTodayDate() + " 00:00 to " + getTomorrowDate() + " 23:59";
		assertEquals( expectedDate,result.get(TASK_DATE).get(0));
		
		String expectedAlias = "NUS0";
		assertEquals(expectedAlias, result.get(TASK_ALIAS).get(0));
	}
	@Test
	public void updateTaskTimeInvalidBackwardDate() {
		ArrayList<ArrayList<String>> result = MainLogic.runLogic("delete all");
		result = MainLogic.runLogic("add assignment1 from today tomorrow alias:NUS0");
		result = MainLogic.runLogic("update 1 time 15052014 04:00 13052014 03:00");
		
		String expectedFeedbackType = "error";
		String feedbackType = result.get(FEEDBACK_TYPE).get(0);
		assertEquals(expectedFeedbackType, feedbackType);
		
		String expectedFeedbackDes = "Error, start time cannot be after end time.";
		String feedbackDesc = result.get(FEEDBACK_DESC).get(0);
		assertEquals(expectedFeedbackDes, feedbackDesc);

		String expectedDesc = "1. assignment1";
		String taskDesc = result.get(TASK_DESC).get(0);
		assertEquals(expectedDesc, taskDesc);
		
		String expectedDate = getTodayDate() + " 00:00 to " + getTomorrowDate() + " 23:59";
		assertEquals( expectedDate,result.get(TASK_DATE).get(0));
		
		String expectedAlias = "NUS0";
		assertEquals(expectedAlias, result.get(TASK_ALIAS).get(0));
	}
	@Test
	public void updateTaskStartValid() {
		ArrayList<ArrayList<String>> result = MainLogic.runLogic("delete all");
		result = MainLogic.runLogic("add assignment1 from 15052014 0000 25052014 0000 alias:NUS0");
		result = MainLogic.runLogic("update 1 start 16052014 04:00");
		
		String expectedFeedbackType = "update";
		String feedbackType = result.get(FEEDBACK_TYPE).get(0);
		assertEquals(expectedFeedbackType, feedbackType);
		
		String expectedFeedbackDes = "Task has been updated.";
		String feedbackDesc = result.get(FEEDBACK_DESC).get(0);
		assertEquals(expectedFeedbackDes, feedbackDesc);

		String expectedDesc = "1. assignment1";
		String taskDesc = result.get(TASK_DESC).get(0);
		assertEquals(expectedDesc, taskDesc);
		
		String expectedDate = "16 May 2014 04:00 to 25 May 2014 00:00";
		assertEquals( expectedDate,result.get(TASK_DATE).get(0));
		
		String expectedAlias = "NUS0";
		assertEquals(expectedAlias, result.get(TASK_ALIAS).get(0));
	}
	@Test
	public void updateTaskStartInvalid() {
		ArrayList<ArrayList<String>> result = MainLogic.runLogic("delete all");
		result = MainLogic.runLogic("add assignment1 from 15052014 0000 25052014 0000 alias:NUS0");
		result = MainLogic.runLogic("update 1 start 16052014 04:00");
		
		String expectedFeedbackType = "update";
		String feedbackType = result.get(FEEDBACK_TYPE).get(0);
		assertEquals(expectedFeedbackType, feedbackType);
		
		String expectedFeedbackDes = "Task has been updated.";
		String feedbackDesc = result.get(FEEDBACK_DESC).get(0);
		assertEquals(expectedFeedbackDes, feedbackDesc);

		String expectedDesc = "1. assignment1";
		String taskDesc = result.get(TASK_DESC).get(0);
		assertEquals(expectedDesc, taskDesc);
		
		String expectedDate = "16 May 2014 04:00 to 25 May 2014 00:00";
		assertEquals( expectedDate,result.get(TASK_DATE).get(0));
		
		String expectedAlias = "NUS0";
		assertEquals(expectedAlias, result.get(TASK_ALIAS).get(0));
	}
	@Test
	public void updateTaskStartInvalidNumber() {
		ArrayList<ArrayList<String>> result = MainLogic.runLogic("delete all");
		result = MainLogic.runLogic("add assignment1 from 15052014 0000 25052014 0000 alias:NUS0");
		result = MainLogic.runLogic("update 3 start 16052014 04:00");
		
		String expectedFeedbackType = "error";
		String feedbackType = result.get(FEEDBACK_TYPE).get(0);
		assertEquals(expectedFeedbackType, feedbackType);
		
		String expectedFeedbackDes = "Please enter a valid task number to update.";
		String feedbackDesc = result.get(FEEDBACK_DESC).get(0);
		assertEquals(expectedFeedbackDes, feedbackDesc);

		String expectedDesc = "1. assignment1";
		String taskDesc = result.get(TASK_DESC).get(0);
		assertEquals(expectedDesc, taskDesc);
		
		String expectedDate = "15 May 2014 00:00 to 25 May 2014 00:00";
		assertEquals( expectedDate,result.get(TASK_DATE).get(0));
		
		String expectedAlias = "NUS0";
		assertEquals(expectedAlias, result.get(TASK_ALIAS).get(0));
	}
	@Test
	public void updateTaskEndValid() {
		ArrayList<ArrayList<String>> result = MainLogic.runLogic("delete all");
		result = MainLogic.runLogic("add assignment1 from 15052014 0000 25052014 0000 alias:NUS0");
		result = MainLogic.runLogic("update 1 end 16052014 04:00");
		
		String expectedFeedbackType = "update";
		String feedbackType = result.get(FEEDBACK_TYPE).get(0);
		assertEquals(expectedFeedbackType, feedbackType);
		
		String expectedFeedbackDes = "Task has been updated.";
		String feedbackDesc = result.get(FEEDBACK_DESC).get(0);
		assertEquals(expectedFeedbackDes, feedbackDesc);

		String expectedDesc = "1. assignment1";
		String taskDesc = result.get(TASK_DESC).get(0);
		assertEquals(expectedDesc, taskDesc);
		
		String expectedDate = "15 May 2014 00:00 to 16 May 2014 04:00";
		assertEquals( expectedDate,result.get(TASK_DATE).get(0));
		
		String expectedAlias = "NUS0";
		assertEquals(expectedAlias, result.get(TASK_ALIAS).get(0));
	}
	@Test
	public void updateTaskEndInvalid() {
		ArrayList<ArrayList<String>> result = MainLogic.runLogic("delete all");
		result = MainLogic.runLogic("add assignment1 from 15052014 0000 25052014 0000 alias:NUS0");
		result = MainLogic.runLogic("update 1 end 13052014 04:00");
		
		String expectedFeedbackType = "error";
		String feedbackType = result.get(FEEDBACK_TYPE).get(0);
		assertEquals(expectedFeedbackType, feedbackType);
		
		String expectedFeedbackDes = "Error, start time cannot be after end time.";
		String feedbackDesc = result.get(FEEDBACK_DESC).get(0);
		assertEquals(expectedFeedbackDes, feedbackDesc);

		String expectedDesc = "1. assignment1";
		String taskDesc = result.get(TASK_DESC).get(0);
		assertEquals(expectedDesc, taskDesc);
		
		String expectedDate = "15 May 2014 00:00 to 25 May 2014 00:00";
		assertEquals( expectedDate,result.get(TASK_DATE).get(0));
		
		String expectedAlias = "NUS0";
		assertEquals(expectedAlias, result.get(TASK_ALIAS).get(0));
	}
	@Test
	public void updateTaskEndInvalidNumber() {
		ArrayList<ArrayList<String>> result = MainLogic.runLogic("delete all");
		result = MainLogic.runLogic("add assignment1 from 15052014 0000 25052014 0000 alias:NUS0");
		result = MainLogic.runLogic("update 3 end 13052014 04:00");
		
		String expectedFeedbackType = "error";
		String feedbackType = result.get(FEEDBACK_TYPE).get(0);
		assertEquals(expectedFeedbackType, feedbackType);
		
		String expectedFeedbackDes = "Please enter a valid task number to update.";
		String feedbackDesc = result.get(FEEDBACK_DESC).get(0);
		assertEquals(expectedFeedbackDes, feedbackDesc);

		String expectedDesc = "1. assignment1";
		String taskDesc = result.get(TASK_DESC).get(0);
		assertEquals(expectedDesc, taskDesc);
		
		String expectedDate = "15 May 2014 00:00 to 25 May 2014 00:00";
		assertEquals( expectedDate,result.get(TASK_DATE).get(0));
		
		String expectedAlias = "NUS0";
		assertEquals(expectedAlias, result.get(TASK_ALIAS).get(0));
	}
	@Test
	public void updateTaskAliasValid() {
		ArrayList<ArrayList<String>> result = MainLogic.runLogic("delete all");
		result = MainLogic.runLogic("add assignment1 from 15052014 0000 25052014 0000 alias:NUS0");
		result = MainLogic.runLogic("update 1 alias NUS3");
		
		String expectedFeedbackType = "update";
		String feedbackType = result.get(FEEDBACK_TYPE).get(0);
		assertEquals(expectedFeedbackType, feedbackType);
		
		String expectedFeedbackDes = "Task has been updated.";
		String feedbackDesc = result.get(FEEDBACK_DESC).get(0);
		assertEquals(expectedFeedbackDes, feedbackDesc);

		String expectedDesc = "1. assignment1";
		String taskDesc = result.get(TASK_DESC).get(0);
		assertEquals(expectedDesc, taskDesc);
		
		String expectedDate = "15 May 2014 00:00 to 25 May 2014 00:00";
		assertEquals( expectedDate,result.get(TASK_DATE).get(0));
		
		String expectedAlias = "NUS3";
		assertEquals(expectedAlias, result.get(TASK_ALIAS).get(0));
	}
	@Test
	public void updateTaskAliasInvalid() {
		ArrayList<ArrayList<String>> result = MainLogic.runLogic("delete all");
		result = MainLogic.runLogic("add assignment1 from 15052014 0000 25052014 0000 alias:NUS0");
		result = MainLogic.runLogic("add assignment1 from 15052014 0000 25052014 0000 alias:NUS3");
		result = MainLogic.runLogic("update 1 alias NUS3");
		
		String expectedFeedbackType = "error";
		String feedbackType = result.get(FEEDBACK_TYPE).get(0);
		assertEquals(expectedFeedbackType, feedbackType);
		
		String expectedFeedbackDes = "Alias is already in use";
		String feedbackDesc = result.get(FEEDBACK_DESC).get(0);
		assertEquals(expectedFeedbackDes, feedbackDesc);

		String expectedDesc = "1. assignment1";
		String taskDesc = result.get(TASK_DESC).get(0);
		assertEquals(expectedDesc, taskDesc);
		
		String expectedDate = "15 May 2014 00:00 to 25 May 2014 00:00";
		assertEquals( expectedDate,result.get(TASK_DATE).get(0));
		
		String expectedAlias = "NUS0";
		assertEquals(expectedAlias, result.get(TASK_ALIAS).get(0));
	}
	@Test
	public void updateTaskAliasInvalidNumber() {
		ArrayList<ArrayList<String>> result = MainLogic.runLogic("delete all");
		result = MainLogic.runLogic("add assignment1 from 15052014 0000 25052014 0000 alias:NUS0");
		result = MainLogic.runLogic("update 3 alias NUS3");
		
		String expectedFeedbackType = "error";
		String feedbackType = result.get(FEEDBACK_TYPE).get(0);
		assertEquals(expectedFeedbackType, feedbackType);
		
		String expectedFeedbackDes = "Please enter a valid task number to update.";
		String feedbackDesc = result.get(FEEDBACK_DESC).get(0);
		assertEquals(expectedFeedbackDes, feedbackDesc);

		String expectedDesc = "1. assignment1";
		String taskDesc = result.get(TASK_DESC).get(0);
		assertEquals(expectedDesc, taskDesc);
		
		String expectedDate = "15 May 2014 00:00 to 25 May 2014 00:00";
		assertEquals( expectedDate,result.get(TASK_DATE).get(0));
		
		String expectedAlias = "NUS0";
		assertEquals(expectedAlias, result.get(TASK_ALIAS).get(0));
	}
	@Test
	public void updateTaskAliasInvalidNumberInvalid() {
		ArrayList<ArrayList<String>> result = MainLogic.runLogic("delete all");
		result = MainLogic.runLogic("add assignment1 from 15052014 0000 25052014 0000 alias:NUS0");
		result = MainLogic.runLogic("add assignment1 from 15052014 0000 25052014 0000 alias:NUS3");
		result = MainLogic.runLogic("update 3 alias NUS3");
		
		String expectedFeedbackType = "error";
		String feedbackType = result.get(FEEDBACK_TYPE).get(0);
		assertEquals(expectedFeedbackType, feedbackType);
		
		String expectedFeedbackDes = "Please enter a valid task number to update.";
		String feedbackDesc = result.get(FEEDBACK_DESC).get(0);
		assertEquals(expectedFeedbackDes, feedbackDesc);

		String expectedDesc = "1. assignment1";
		String taskDesc = result.get(TASK_DESC).get(0);
		assertEquals(expectedDesc, taskDesc);
		
		String expectedDate = "15 May 2014 00:00 to 25 May 2014 00:00";
		assertEquals( expectedDate,result.get(TASK_DATE).get(0));
		
		String expectedAlias = "NUS0";
		assertEquals(expectedAlias, result.get(TASK_ALIAS).get(0));
	}
	
	@Test
	public void redColorScheme() {
		ArrayList<ArrayList<String>> result = MainLogic.runLogic("delete all");
		result = MainLogic.runLogic("add assignment1 due yesterday");
		
		String expectedFeedbackType = "add";
		String feedbackType = result.get(FEEDBACK_TYPE).get(0);
		assertEquals(expectedFeedbackType, feedbackType);
		
		String expectedFeedbackDes = "Added \"assignment1 due yesterday\".";
		String feedbackDesc = result.get(FEEDBACK_DESC).get(0);
		assertEquals(expectedFeedbackDes, feedbackDesc);

		String expectedDesc = "1. assignment1 due";
		String taskDesc = result.get(TASK_DESC).get(0);
		assertEquals(expectedDesc, taskDesc);
		
		String expectedDate = getYesterdayDate() + " 23:59";
		assertEquals( expectedDate,result.get(TASK_DATE).get(0));
		
		String expectedStatus = "unmarked";
		assertEquals(expectedStatus, result.get(TASK_STATUS).get(0));
		
		String expectedTime = "overdue";
		assertEquals(expectedTime, result.get(TASK_TIME).get(0));
	}
	@Test
	public void yellowColorScheme() {
		ArrayList<ArrayList<String>> result = MainLogic.runLogic("delete all");
		result = MainLogic.runLogic("add assignment1 due today");
		
		String expectedFeedbackType = "add";
		String feedbackType = result.get(FEEDBACK_TYPE).get(0);
		assertEquals(expectedFeedbackType, feedbackType);
		
		String expectedFeedbackDes = "Added \"assignment1 due today\".";
		String feedbackDesc = result.get(FEEDBACK_DESC).get(0);
		assertEquals(expectedFeedbackDes, feedbackDesc);

		String expectedDesc = "1. assignment1 due";
		String taskDesc = result.get(TASK_DESC).get(0);
		assertEquals(expectedDesc, taskDesc);
		
		String expectedDate = getTodayDate() + " 23:59";
		assertEquals( expectedDate,result.get(TASK_DATE).get(0));
		
		String expectedStatus = "unmarked";
		assertEquals(expectedStatus, result.get(TASK_STATUS).get(0));
		
		String expectedTime = "today";
		assertEquals(expectedTime, result.get(TASK_TIME).get(0));
	}
	@Test
	public void greenColorScheme() {
		ArrayList<ArrayList<String>> result = MainLogic.runLogic("delete all");
		result = MainLogic.runLogic("add assignment1 " + getDayAfterDateDash());
		
		String expectedFeedbackType = "add";
		String feedbackType = result.get(FEEDBACK_TYPE).get(0);
		assertEquals(expectedFeedbackType, feedbackType);
		
		String expectedFeedbackDes = "Added \"assignment1 " + getDayAfterDateDash() + "\".";
		String feedbackDesc = result.get(FEEDBACK_DESC).get(0);
		assertEquals(expectedFeedbackDes, feedbackDesc);

		String expectedDesc = "1. assignment1";
		String taskDesc = result.get(TASK_DESC).get(0);
		assertEquals(expectedDesc, taskDesc);
		
		String expectedDate = getDayAfterDate() + " 23:59";
		assertEquals( expectedDate,result.get(TASK_DATE).get(0));
		
		String expectedStatus = "unmarked";
		assertEquals(expectedStatus, result.get(TASK_STATUS).get(0));
		
		String expectedTime = "others";
		assertEquals(expectedTime, result.get(TASK_TIME).get(0));
	}
	@Test
	public void turqoiseColorScheme() {
		ArrayList<ArrayList<String>> result = MainLogic.runLogic("delete all");
		result = MainLogic.runLogic("add assignment1");
		
		String expectedFeedbackType = "add";
		String feedbackType = result.get(FEEDBACK_TYPE).get(0);
		assertEquals(expectedFeedbackType, feedbackType);
		
		String expectedFeedbackDes = "Added \"assignment1\".";
		String feedbackDesc = result.get(FEEDBACK_DESC).get(0);
		assertEquals(expectedFeedbackDes, feedbackDesc);

		String expectedDesc = "1. assignment1";
		String taskDesc = result.get(TASK_DESC).get(0);
		assertEquals(expectedDesc, taskDesc);
		
		String expectedStatus = "unmarked";
		assertEquals(expectedStatus, result.get(TASK_STATUS).get(0));
		
		String expectedTime = "floating";
		assertEquals(expectedTime, result.get(TASK_TIME).get(0));
	}
	@Test
	public void greyColorSchemeUnmarkedToMarked() {
		ArrayList<ArrayList<String>> result = MainLogic.runLogic("delete all");
		result = MainLogic.runLogic("add assignment1");
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
	public void greyColorSchemeMarkedToUnmarked() {
		ArrayList<ArrayList<String>> result = MainLogic.runLogic("delete all");
		result = MainLogic.runLogic("add assignment1");
		result = MainLogic.runLogic("mark 1");
		result = MainLogic.runLogic("list all");
		result = MainLogic.runLogic("mark 1");
		
		String expectedFeedbackType = "mark";
		String feedbackType = result.get(FEEDBACK_TYPE).get(0);
		assertEquals(expectedFeedbackType, feedbackType);
		
		String expectedFeedbackDes = "Task(s) have been marked.";
		String feedbackDesc = result.get(FEEDBACK_DESC).get(0);
		assertEquals(expectedFeedbackDes, feedbackDesc);

		String expectedDesc = "1. assignment1";
		String taskDesc = result.get(TASK_DESC).get(0);
		assertEquals(expectedDesc, taskDesc);
		
		String expectedStatus = "unmarked";
		assertEquals(expectedStatus, result.get(TASK_STATUS).get(0));
	}
}

