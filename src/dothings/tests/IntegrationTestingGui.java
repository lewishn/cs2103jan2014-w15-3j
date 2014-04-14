package dothings.tests;

import static org.junit.Assert.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.Test;

import dothings.logic.MainLogic;

//@author A0097082Y
public class IntegrationTestingGui {
	private static final int FEEDBACK_TYPE = 0;
	private static final int FEEDBACK_DESC = 1;
	private static final int TASK_DESC = 2;
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
		assertEquals(expectedDate, result.get(TASK_DATE).get(0));
		
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
		assertEquals(expectedDate, result.get(TASK_DATE).get(0));
		
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
		
		String expectedFeedbackDes = "Added \"assignment1 "
		        + getDayAfterDateDash() + "\".";
		String feedbackDesc = result.get(FEEDBACK_DESC).get(0);
		assertEquals(expectedFeedbackDes, feedbackDesc);
		
		String expectedDesc = "1. assignment1";
		String taskDesc = result.get(TASK_DESC).get(0);
		assertEquals(expectedDesc, taskDesc);
		
		String expectedDate = getDayAfterDate() + " 23:59";
		assertEquals(expectedDate, result.get(TASK_DATE).get(0));
		
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
