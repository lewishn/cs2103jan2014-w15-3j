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
public class IntegrationTestingUpdate {
	private static final int FEEDBACK_TYPE = 0;
	private static final int FEEDBACK_DESC = 1;
	private static final int TASK_DESC = 2;
	private static final int TASK_ALIAS = 3;
	private static final int TASK_DATE = 5;
	
	private String getTodayDate() {
		String date;
		SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy");
		Calendar calendar = new GregorianCalendar();
		Date now = new Date();
		calendar.setTime(now);
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
		result = MainLogic
		        .runLogic("add assignment1 from today tomorrow alias:NUS0");
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
		result = MainLogic
		        .runLogic("add assignment1 from today tomorrow alias:NUS0");
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
		
		String expectedDate = getTodayDate() + " 07:00";
		assertEquals(expectedDate, result.get(TASK_DATE).get(0));
	}
	
	@Test
	public void updateSingleDescriptionSingleTimeSingleDate() {
		ArrayList<ArrayList<String>> result = MainLogic.runLogic("delete all");
		result = MainLogic
		        .runLogic("add assignment1 from today tomorrow alias:NUS0");
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
		assertEquals(expectedDate, result.get(TASK_DATE).get(0));
	}
	
	@Test
	public void updateSingleDescriptionSingleTimeSingleDateSingleAlias() {
		ArrayList<ArrayList<String>> result = MainLogic.runLogic("delete all");
		result = MainLogic
		        .runLogic("add assignment1 from today tomorrow alias:NUS0");
		result = MainLogic
		        .runLogic("update 1 assignment2 0700 15-04-2014 alias:NUS1");
		
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
		assertEquals(expectedDate, result.get(TASK_DATE).get(0));
		
		String expectedAlias = "NUS1";
		assertEquals(expectedAlias, result.get(TASK_ALIAS).get(0));
	}
	
	@Test
	public void updateSingleDescriptionSingleDate() {
		ArrayList<ArrayList<String>> result = MainLogic.runLogic("delete all");
		result = MainLogic
		        .runLogic("add assignment1 from today tomorrow alias:NUS0");
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
		assertEquals(expectedDate, result.get(TASK_DATE).get(0));
	}
	
	@Test
	public void updateSingleDescriptionSingleDateSingleAlias() {
		ArrayList<ArrayList<String>> result = MainLogic.runLogic("delete all");
		result = MainLogic
		        .runLogic("add assignment1 from today tomorrow alias:NUS0");
		result = MainLogic
		        .runLogic("update 1 assignment2 15-04-2014 alias:NUS1");
		
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
		assertEquals(expectedDate, result.get(TASK_DATE).get(0));
		
		String expectedAlias = "NUS1";
		assertEquals(expectedAlias, result.get(TASK_ALIAS).get(0));
	}
	
	@Test
	public void updateSingleDescriptionSingleAlias() {
		ArrayList<ArrayList<String>> result = MainLogic.runLogic("delete all");
		result = MainLogic
		        .runLogic("add assignment1 from today tomorrow alias:NUS0");
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
		result = MainLogic
		        .runLogic("add assignment1 from today tomorrow alias:NUS0");
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
		assertEquals(expectedDate, result.get(TASK_DATE).get(0));
		
		String expectedAlias = "NUS1";
		assertEquals(expectedAlias, result.get(TASK_ALIAS).get(0));
	}
	
	@Test
	public void updateTaskDescriptionValid() {
		ArrayList<ArrayList<String>> result = MainLogic.runLogic("delete all");
		result = MainLogic
		        .runLogic("add assignment1 from today tomorrow alias:NUS0");
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
		
		String expectedDate = getTodayDate() + " 00:00 to " + getTomorrowDate()
		        + " 23:59";
		assertEquals(expectedDate, result.get(TASK_DATE).get(0));
		
		String expectedAlias = "NUS0";
		assertEquals(expectedAlias, result.get(TASK_ALIAS).get(0));
	}
	
	@Test
	public void updateTaskDescriptionInvalid() {
		ArrayList<ArrayList<String>> result = MainLogic.runLogic("delete all");
		result = MainLogic
		        .runLogic("add assignment1 from today tomorrow alias:NUS0");
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
		
		String expectedDate = getTodayDate() + " 00:00 to " + getTomorrowDate()
		        + " 23:59";
		assertEquals(expectedDate, result.get(TASK_DATE).get(0));
		
		String expectedAlias = "NUS0";
		assertEquals(expectedAlias, result.get(TASK_ALIAS).get(0));
	}
	
	@Test
	public void updateTaskDescriptionInvalidNumber() {
		ArrayList<ArrayList<String>> result = MainLogic.runLogic("delete all");
		result = MainLogic
		        .runLogic("add assignment1 from today tomorrow alias:NUS0");
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
		
		String expectedDate = getTodayDate() + " 00:00 to " + getTomorrowDate()
		        + " 23:59";
		assertEquals(expectedDate, result.get(TASK_DATE).get(0));
		
		String expectedAlias = "NUS0";
		assertEquals(expectedAlias, result.get(TASK_ALIAS).get(0));
	}
	
	@Test
	public void updateTaskTimeValid() {
		ArrayList<ArrayList<String>> result = MainLogic.runLogic("delete all");
		result = MainLogic
		        .runLogic("add assignment1 from today tomorrow alias:NUS0");
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
		assertEquals(expectedDate, result.get(TASK_DATE).get(0));
		
		String expectedAlias = "NUS0";
		assertEquals(expectedAlias, result.get(TASK_ALIAS).get(0));
	}
	
	@Test
	public void updateTaskTimeInvalidNumber() {
		ArrayList<ArrayList<String>> result = MainLogic.runLogic("delete all");
		result = MainLogic
		        .runLogic("add assignment1 from today tomorrow alias:NUS0");
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
		
		String expectedDate = getTodayDate() + " 00:00 to " + getTomorrowDate()
		        + " 23:59";
		assertEquals(expectedDate, result.get(TASK_DATE).get(0));
		
		String expectedAlias = "NUS0";
		assertEquals(expectedAlias, result.get(TASK_ALIAS).get(0));
	}
	
	@Test
	public void updateTaskTimeInvalidBackwardDate() {
		ArrayList<ArrayList<String>> result = MainLogic.runLogic("delete all");
		result = MainLogic
		        .runLogic("add assignment1 from today tomorrow alias:NUS0");
		result = MainLogic
		        .runLogic("update 1 time 15052014 04:00 13052014 03:00");
		
		String expectedFeedbackType = "error";
		String feedbackType = result.get(FEEDBACK_TYPE).get(0);
		assertEquals(expectedFeedbackType, feedbackType);
		
		String expectedFeedbackDes = "Error, start time cannot be after end time.";
		String feedbackDesc = result.get(FEEDBACK_DESC).get(0);
		assertEquals(expectedFeedbackDes, feedbackDesc);
		
		String expectedDesc = "1. assignment1";
		String taskDesc = result.get(TASK_DESC).get(0);
		assertEquals(expectedDesc, taskDesc);
		
		String expectedDate = getTodayDate() + " 00:00 to " + getTomorrowDate()
		        + " 23:59";
		assertEquals(expectedDate, result.get(TASK_DATE).get(0));
		
		String expectedAlias = "NUS0";
		assertEquals(expectedAlias, result.get(TASK_ALIAS).get(0));
	}
	
	@Test
	public void updateTaskStartValid() {
		ArrayList<ArrayList<String>> result = MainLogic.runLogic("delete all");
		result = MainLogic
		        .runLogic("add assignment1 from 15052014 0000 25052014 0000 alias:NUS0");
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
		assertEquals(expectedDate, result.get(TASK_DATE).get(0));
		
		String expectedAlias = "NUS0";
		assertEquals(expectedAlias, result.get(TASK_ALIAS).get(0));
	}
	
	@Test
	public void updateTaskStartInvalid() {
		ArrayList<ArrayList<String>> result = MainLogic.runLogic("delete all");
		result = MainLogic
		        .runLogic("add assignment1 from 15052014 0000 25052014 0000 alias:NUS0");
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
		assertEquals(expectedDate, result.get(TASK_DATE).get(0));
		
		String expectedAlias = "NUS0";
		assertEquals(expectedAlias, result.get(TASK_ALIAS).get(0));
	}
	
	@Test
	public void updateTaskStartInvalidNumber() {
		ArrayList<ArrayList<String>> result = MainLogic.runLogic("delete all");
		result = MainLogic
		        .runLogic("add assignment1 from 15052014 0000 25052014 0000 alias:NUS0");
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
		assertEquals(expectedDate, result.get(TASK_DATE).get(0));
		
		String expectedAlias = "NUS0";
		assertEquals(expectedAlias, result.get(TASK_ALIAS).get(0));
	}
	
	@Test
	public void updateTaskEndValid() {
		ArrayList<ArrayList<String>> result = MainLogic.runLogic("delete all");
		result = MainLogic
		        .runLogic("add assignment1 from 15052014 0000 25052014 0000 alias:NUS0");
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
		assertEquals(expectedDate, result.get(TASK_DATE).get(0));
		
		String expectedAlias = "NUS0";
		assertEquals(expectedAlias, result.get(TASK_ALIAS).get(0));
	}
	
	@Test
	public void updateTaskEndInvalid() {
		ArrayList<ArrayList<String>> result = MainLogic.runLogic("delete all");
		result = MainLogic
		        .runLogic("add assignment1 from 15052014 0000 25052014 0000 alias:NUS0");
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
		assertEquals(expectedDate, result.get(TASK_DATE).get(0));
		
		String expectedAlias = "NUS0";
		assertEquals(expectedAlias, result.get(TASK_ALIAS).get(0));
	}
	
	@Test
	public void updateTaskEndInvalidNumber() {
		ArrayList<ArrayList<String>> result = MainLogic.runLogic("delete all");
		result = MainLogic
		        .runLogic("add assignment1 from 15052014 0000 25052014 0000 alias:NUS0");
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
		assertEquals(expectedDate, result.get(TASK_DATE).get(0));
		
		String expectedAlias = "NUS0";
		assertEquals(expectedAlias, result.get(TASK_ALIAS).get(0));
	}
	
	@Test
	public void updateTaskAliasValid() {
		ArrayList<ArrayList<String>> result = MainLogic.runLogic("delete all");
		result = MainLogic
		        .runLogic("add assignment1 from 15052014 0000 25052014 0000 alias:NUS0");
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
		assertEquals(expectedDate, result.get(TASK_DATE).get(0));
		
		String expectedAlias = "NUS3";
		assertEquals(expectedAlias, result.get(TASK_ALIAS).get(0));
	}
	
	@Test
	public void updateTaskAliasInvalid() {
		ArrayList<ArrayList<String>> result = MainLogic.runLogic("delete all");
		result = MainLogic
		        .runLogic("add assignment1 from 15052014 0000 25052014 0000 alias:NUS0");
		result = MainLogic
		        .runLogic("add assignment1 from 15052014 0000 25052014 0000 alias:NUS3");
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
		assertEquals(expectedDate, result.get(TASK_DATE).get(0));
		
		String expectedAlias = "NUS0";
		assertEquals(expectedAlias, result.get(TASK_ALIAS).get(0));
	}
	
	@Test
	public void updateTaskAliasInvalidNumber() {
		ArrayList<ArrayList<String>> result = MainLogic.runLogic("delete all");
		result = MainLogic
		        .runLogic("add assignment1 from 15052014 0000 25052014 0000 alias:NUS0");
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
		assertEquals(expectedDate, result.get(TASK_DATE).get(0));
		
		String expectedAlias = "NUS0";
		assertEquals(expectedAlias, result.get(TASK_ALIAS).get(0));
	}
	
	@Test
	public void updateTaskAliasInvalidNumberInvalid() {
		ArrayList<ArrayList<String>> result = MainLogic.runLogic("delete all");
		result = MainLogic
		        .runLogic("add assignment1 from 15052014 0000 25052014 0000 alias:NUS0");
		result = MainLogic
		        .runLogic("add assignment1 from 15052014 0000 25052014 0000 alias:NUS3");
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
		assertEquals(expectedDate, result.get(TASK_DATE).get(0));
		
		String expectedAlias = "NUS0";
		assertEquals(expectedAlias, result.get(TASK_ALIAS).get(0));
	}
	
}
