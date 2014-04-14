package dothings.tests;
import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import dothings.logic.MainLogic;

//@author A0101924R
public class IntegrationTestingAdd {
	
	private static final int FEEDBACK_TYPE = 0;
	private static final int FEEDBACK_DESC = 1;
	private static final int TASK_DESC = 2;
	private static final int TASK_ALIAS = 3;
	private static final int TASK_STATUS = 4;
	private static final int TASK_DATE = 5;
	private static final int TASK_TIME = 6;
	
	// Success tests
	
	@Test
	public void testAddNoDateNoTimeNoAlias() {
		ArrayList<ArrayList<String>> result = MainLogic.runLogic("delete all");
		
		// Task with no date, no time, no alias
		result = MainLogic.runLogic("add assignment1");
		
		String expectedFeedbackType = "add";
		String feedbackType = result.get(FEEDBACK_TYPE).get(0);
		assertEquals(expectedFeedbackType, feedbackType);
		
		String expectedFeedbackDes = result.get(FEEDBACK_DESC).get(0);
		String feedbackDes = "Added \"assignment1\".";
		assertEquals(expectedFeedbackDes, feedbackDes);
		
		String expectedDesc = "1. assignment1";
		String taskDesc = result.get(TASK_DESC).get(0);
		assertEquals(expectedDesc, taskDesc);
	}
	
	@Test
	public void testAddNoDateNoTimeWithAlias() {
		ArrayList<ArrayList<String>> result = MainLogic.runLogic("delete all");
	
		// Task with no date, no time, with alias
		result = MainLogic.runLogic("add assignment1 alias:a1");
		
		String expectedFeedbackType = "add";
		String feedbackType = result.get(FEEDBACK_TYPE).get(0);
		assertEquals(expectedFeedbackType, feedbackType);
		
		String expectedFeedbackDes = result.get(FEEDBACK_DESC).get(0);
		String feedbackDes = "Added \"assignment1 alias:a1\".";
		assertEquals(expectedFeedbackDes, feedbackDes);
		
		String expectedDesc = "1. assignment1";
		String taskDesc = result.get(TASK_DESC).get(0);
		assertEquals(expectedDesc, taskDesc);
		
		String expectedAlias = "a1";
		String taskAlias = result.get(TASK_ALIAS).get(0);
		assertEquals(expectedAlias, taskAlias);
	}
	
	@Test
	public void testAddNoDateOneTimeNoAlias() {
		ArrayList<ArrayList<String>> result = MainLogic.runLogic("delete all");
		
		// Task with no date, 1 time, without alias
		result = MainLogic.runLogic("add assignment1 by 2359");
		
		String expectedFeedbackType = "add";
		String feedbackType = result.get(FEEDBACK_TYPE).get(0);
		assertEquals(expectedFeedbackType, feedbackType);
		
		String expectedFeedbackDes = result.get(FEEDBACK_DESC).get(0);
		String feedbackDes = "Added \"assignment1 by 2359\".";
		assertEquals(expectedFeedbackDes, feedbackDes);
		
		String expectedDesc = "1. assignment1";
		String taskDesc = result.get(TASK_DESC).get(0);
		assertEquals(expectedDesc, taskDesc);
		
		String expectedTime = "today";
		String taskTime = result.get(TASK_TIME).get(0);
		assertEquals(expectedTime, taskTime);
		
	}
	
	@Test
	public void testAddNoDateOneTimeWithAlias() {
		ArrayList<ArrayList<String>> result = MainLogic.runLogic("delete all");
		
		// Task with no date, 1 time, with alias
		result = MainLogic.runLogic("add assignment1 on 0001 alias:a1");
		
		String expectedFeedbackType = "add";
		String feedbackType = result.get(FEEDBACK_TYPE).get(0);
		assertEquals(expectedFeedbackType, feedbackType);
		
		String expectedFeedbackDes = result.get(FEEDBACK_DESC).get(0);
		String feedbackDes = "Added \"assignment1 on 0001 alias:a1\".";
		assertEquals(expectedFeedbackDes, feedbackDes);
		
		String expectedDesc = "1. assignment1";
		String taskDesc = result.get(TASK_DESC).get(0);
		assertEquals(expectedDesc, taskDesc);
		
		String expectedTime = "overdue";
		String taskTime = result.get(TASK_TIME).get(0);
		assertEquals(expectedTime, taskTime);
		
		String expectedAlias = "a1";
		String taskAlias = result.get(TASK_ALIAS).get(0);
		assertEquals(expectedAlias, taskAlias);
		
	}
	
	@Test
	public void testAddNoDateTwoTimeNoAlias() {
		ArrayList<ArrayList<String>> result = MainLogic.runLogic("delete all");
		
		//Task with no date, 2 time, without alias
		result = MainLogic.runLogic("add assignment1 on 1130 by 2359");
		
		String expectedFeedbackType = "add";
		String feedbackType = result.get(FEEDBACK_TYPE).get(0);
		assertEquals(expectedFeedbackType, feedbackType);
		
		String expectedFeedbackDes = result.get(FEEDBACK_DESC).get(0);
		String feedbackDes = "Added \"assignment1 on 1130 by 2359\".";
		assertEquals(expectedFeedbackDes, feedbackDes);
		
		String expectedDesc = "1. assignment1";
		String taskDesc = result.get(TASK_DESC).get(0);
		assertEquals(expectedDesc, taskDesc);
		
		String expectedTime = "today";
		String taskTime = result.get(TASK_TIME).get(0);
		assertEquals(expectedTime, taskTime);
	}
	
	@Test
	public void testAddNoDateTwoTimeWithAlias() {
		ArrayList<ArrayList<String>> result = MainLogic.runLogic("delete all");
		
		//Task with no date, 2 time, without alias
		result = MainLogic.runLogic("add assignment1 on 1130 by 2359 alias:a1");
		
		String expectedFeedbackType = "add";
		String feedbackType = result.get(FEEDBACK_TYPE).get(0);
		assertEquals(expectedFeedbackType, feedbackType);
		
		String expectedFeedbackDes = result.get(FEEDBACK_DESC).get(0);
		String feedbackDes = "Added \"assignment1 on 1130 by 2359 alias:a1\".";
		assertEquals(expectedFeedbackDes, feedbackDes);
		
		String expectedDesc = "1. assignment1";
		String taskDesc = result.get(TASK_DESC).get(0);
		assertEquals(expectedDesc, taskDesc);
		
		String expectedTime = "today";
		String taskTime = result.get(TASK_TIME).get(0);
		assertEquals(expectedTime, taskTime);
		
		String expectedAlias = "a1";
		String taskAlias = result.get(TASK_ALIAS).get(0);
		assertEquals(expectedAlias, taskAlias);
	}
	
	@Test
	public void testAddOneDateNoTimeNoAlias() {
		ArrayList<ArrayList<String>> result = MainLogic.runLogic("delete all");

		// Task with 1 date, 0 time, without alias
		result = MainLogic.runLogic("add assignment1 on 08/08/2015");
		
		String expectedFeedbackType = "add";
		String feedbackType = result.get(FEEDBACK_TYPE).get(0);
		assertEquals(expectedFeedbackType, feedbackType);
		
		String expectedFeedbackDes = result.get(FEEDBACK_DESC).get(0);
		String feedbackDes = "Added \"assignment1 on 08/08/2015\".";
		assertEquals(expectedFeedbackDes, feedbackDes);
		
		String expectedDesc = "1. assignment1";
		String taskDesc = result.get(TASK_DESC).get(0);
		assertEquals(expectedDesc, taskDesc);
		
		String expectedDate = "08 Aug 2015 23:59";
		String taskDate = result.get(TASK_DATE).get(0);
		assertEquals(expectedDate, taskDate);
		
	}
	
	@Test
	public void testAddOneDateNoTimeWithAlias() {
		ArrayList<ArrayList<String>> result = MainLogic.runLogic("delete all");

		// Task with 1 date, 0 time, without alias
		result = MainLogic.runLogic("add assignment1 on 08/08/2015 alias:a1");
		
		String expectedFeedbackType = "add";
		String feedbackType = result.get(FEEDBACK_TYPE).get(0);
		assertEquals(expectedFeedbackType, feedbackType);
		
		String expectedFeedbackDes = result.get(FEEDBACK_DESC).get(0);
		String feedbackDes = "Added \"assignment1 on 08/08/2015 alias:a1\".";
		assertEquals(expectedFeedbackDes, feedbackDes);
		
		String expectedDesc = "1. assignment1";
		String taskDesc = result.get(TASK_DESC).get(0);
		assertEquals(expectedDesc, taskDesc);
		
		String expectedDate = "08 Aug 2015 23:59";
		String taskDate = result.get(TASK_DATE).get(0);
		assertEquals(expectedDate, taskDate);
		
		String expectedAlias = "a1";
		String taskAlias = result.get(TASK_ALIAS).get(0);
		assertEquals(expectedAlias, taskAlias);
		
	}
	
	@Test
	public void testAddOneDateOneTimeNoAlias() {
		ArrayList<ArrayList<String>> result = MainLogic.runLogic("delete all");
		
		// Task with 1 date, 1 time, without alias
		result = MainLogic.runLogic("add assignment1 on 08/08/2015 1800");
		
		String expectedFeedbackType = "add";
		String feedbackType = result.get(FEEDBACK_TYPE).get(0);
		assertEquals(expectedFeedbackType, feedbackType);
		
		String expectedFeedbackDes = result.get(FEEDBACK_DESC).get(0);
		String feedbackDes = "Added \"assignment1 on 08/08/2015 1800\".";
		assertEquals(expectedFeedbackDes, feedbackDes);
		
		String expectedDesc = "1. assignment1";
		String taskDesc = result.get(TASK_DESC).get(0);
		assertEquals(expectedDesc, taskDesc);
		
		String expectedDate = "08 Aug 2015 18:00";
		String taskDate = result.get(TASK_DATE).get(0);
		assertEquals(expectedDate, taskDate);
		
	}
	
	@Test
	public void testAddOneDateOneTimeWithAlias() {
		ArrayList<ArrayList<String>> result = MainLogic.runLogic("delete all");
		
		// Task with 1 date, 1 time, without alias
		result = MainLogic.runLogic("add alias:a1 assignment1 on 08/08/2015 1800");
		
		String expectedFeedbackType = "add";
		String feedbackType = result.get(FEEDBACK_TYPE).get(0);
		assertEquals(expectedFeedbackType, feedbackType);
		
		String expectedFeedbackDes = result.get(FEEDBACK_DESC).get(0);
		String feedbackDes = "Added \"alias:a1 assignment1 on 08/08/2015 1800\".";
		assertEquals(expectedFeedbackDes, feedbackDes);
		
		String expectedDesc = "1. assignment1";
		String taskDesc = result.get(TASK_DESC).get(0);
		assertEquals(expectedDesc, taskDesc);
		
		String expectedDate = "08 Aug 2015 18:00";
		String taskDate = result.get(TASK_DATE).get(0);
		assertEquals(expectedDate, taskDate);
		
		String expectedAlias = "a1";
		String taskAlias = result.get(TASK_ALIAS).get(0);
		assertEquals(expectedAlias,taskAlias);
		
	}

	@Test
	public void testAddOneDateTwoTimeNoAlias() {
		ArrayList<ArrayList<String>> result = MainLogic.runLogic("delete all");
		
		// Task with 1 date, 2 time, without alias
		result = MainLogic.runLogic("add assignment1 on 08/08/2015 1800 by 2000");
		
		String expectedFeedbackType = "add";
		String feedbackType = result.get(FEEDBACK_TYPE).get(0);
		assertEquals(expectedFeedbackType, feedbackType);
		
		String expectedFeedbackDes = result.get(FEEDBACK_DESC).get(0);
		String feedbackDes = "Added \"assignment1 on 08/08/2015 1800 by 2000\".";
		assertEquals(expectedFeedbackDes, feedbackDes);
		
		String expectedDesc = "1. assignment1";
		String taskDesc = result.get(TASK_DESC).get(0);
		assertEquals(expectedDesc, taskDesc);
		
		String expectedDate = "08 Aug 2015 18:00 to 08 Aug 2015 20:00";
		String taskDate = result.get(TASK_DATE).get(0);
		assertEquals(expectedDate, taskDate);
	}
	
	@Test
	public void testAddOneDateTwoTimeWithAlias() {
		ArrayList<ArrayList<String>> result = MainLogic.runLogic("delete all");
		
		// Task with 1 date, 2 time, with alias
		result = MainLogic.runLogic("add assignment1 on 08/08/2015 1800 by 2000 alias:a1");
		
		String expectedFeedbackType = "add";
		String feedbackType = result.get(FEEDBACK_TYPE).get(0);
		assertEquals(expectedFeedbackType, feedbackType);
		
		String expectedFeedbackDes = result.get(FEEDBACK_DESC).get(0);
		String feedbackDes = "Added \"assignment1 on 08/08/2015 1800 by 2000 alias:a1\".";
		assertEquals(expectedFeedbackDes, feedbackDes);
		
		String expectedDesc = "1. assignment1";
		String taskDesc = result.get(TASK_DESC).get(0);
		assertEquals(expectedDesc, taskDesc);
		
		String expectedDate = "08 Aug 2015 18:00 to 08 Aug 2015 20:00";
		String taskDate = result.get(TASK_DATE).get(0);
		assertEquals(expectedDate, taskDate);
		
		String expectedAlias = "a1";
		String taskAlias = result.get(TASK_ALIAS).get(0);
		assertEquals(expectedAlias,taskAlias);
		
	}
	
	@Test
	public void testAddTwoDateNoTimeNoAlias() {
		ArrayList<ArrayList<String>> result = MainLogic.runLogic("delete all");
		
		// Task with 2 date, no time, no alias
		result = MainLogic.runLogic("add assignment1 on 08/08/2015 to 09082015");
	
		String expectedFeedbackType = "add";
		String feedbackType = result.get(FEEDBACK_TYPE).get(0);
		assertEquals(expectedFeedbackType, feedbackType);
		
		String expectedFeedbackDes = result.get(FEEDBACK_DESC).get(0);
		String feedbackDes = "Added \"assignment1 on 08/08/2015 to 09082015\".";
		assertEquals(expectedFeedbackDes, feedbackDes);
		
		String expectedDesc = "1. assignment1";
		String taskDesc = result.get(TASK_DESC).get(0);
		assertEquals(expectedDesc, taskDesc);
		
		String expectedDate = "08 Aug 2015 00:00 to 09 Aug 2015 23:59";
		String taskDate = result.get(TASK_DATE).get(0);
		assertEquals(expectedDate, taskDate);

	}

	@Test
	public void testAddTwoDateNoTimeWithAlias() {
		ArrayList<ArrayList<String>> result = MainLogic.runLogic("delete all");
		
		// Task with 2 date, no time, with alias
		result = MainLogic.runLogic("add assignment1 on 08/08/2015 to 09082015 alias:a1");
	
		String expectedFeedbackType = "add";
		String feedbackType = result.get(FEEDBACK_TYPE).get(0);
		assertEquals(expectedFeedbackType, feedbackType);
		
		String expectedFeedbackDes = result.get(FEEDBACK_DESC).get(0);
		String feedbackDes = "Added \"assignment1 on 08/08/2015 to 09082015 alias:a1\".";
		assertEquals(expectedFeedbackDes, feedbackDes);
		
		String expectedDesc = "1. assignment1";
		String taskDesc = result.get(TASK_DESC).get(0);
		assertEquals(expectedDesc, taskDesc);
		
		String expectedDate = "08 Aug 2015 00:00 to 09 Aug 2015 23:59";
		String taskDate = result.get(TASK_DATE).get(0);
		assertEquals(expectedDate, taskDate);
		
		String expectedAlias = "a1";
		String taskAlias = result.get(TASK_ALIAS).get(0);
		assertEquals(expectedAlias,taskAlias);

	}
	
	@Test
	public void testAddTwoDateOneTimeNoAlias() {
		ArrayList<ArrayList<String>> result = MainLogic.runLogic("delete all");
		
		// Task with 2 date, 1 time, no alias
		result = MainLogic.runLogic("add assignment1 on 08/08/2015 to 09082015 1000");
	
		String expectedFeedbackType = "add";
		String feedbackType = result.get(FEEDBACK_TYPE).get(0);
		assertEquals(expectedFeedbackType, feedbackType);
		
		String expectedFeedbackDes = result.get(FEEDBACK_DESC).get(0);
		String feedbackDes = "Added \"assignment1 on 08/08/2015 to 09082015 1000\".";
		assertEquals(expectedFeedbackDes, feedbackDes);
		
		String expectedDesc = "1. assignment1";
		String taskDesc = result.get(TASK_DESC).get(0);
		assertEquals(expectedDesc, taskDesc);
		
		String expectedDate = "08 Aug 2015 10:00 to 09 Aug 2015 23:59";
		String taskDate = result.get(TASK_DATE).get(0);
		assertEquals(expectedDate, taskDate);

	}
	
	@Test
	public void testAddTwoDateOneTimeWithAlias() {
		ArrayList<ArrayList<String>> result = MainLogic.runLogic("delete all");
		
		// Task with 2 date, 1 time, with alias
		result = MainLogic.runLogic("add assignment1 on 08/08/2015 to 09082015 1000 alias:a1");
	
		String expectedFeedbackType = "add";
		String feedbackType = result.get(FEEDBACK_TYPE).get(0);
		assertEquals(expectedFeedbackType, feedbackType);
		
		String expectedFeedbackDes = result.get(FEEDBACK_DESC).get(0);
		String feedbackDes = "Added \"assignment1 on 08/08/2015 to 09082015 1000 alias:a1\".";
		assertEquals(expectedFeedbackDes, feedbackDes);
		
		String expectedDesc = "1. assignment1";
		String taskDesc = result.get(TASK_DESC).get(0);
		assertEquals(expectedDesc, taskDesc);
		
		String expectedDate = "08 Aug 2015 10:00 to 09 Aug 2015 23:59";
		String taskDate = result.get(TASK_DATE).get(0);
		assertEquals(expectedDate, taskDate);

		String expectedAlias = "a1";
		String taskAlias = result.get(TASK_ALIAS).get(0);
		assertEquals(expectedAlias,taskAlias);
		
	}
	
	@Test
	public void testAddTwoDateTwoTimeNoAlias() {
		ArrayList<ArrayList<String>> result = MainLogic.runLogic("delete all");
		
		// Task with 2 date, 2 time, no alias
		result = MainLogic.runLogic("add assignment1 on 08/08/2015 to 09082015 1000 to 1800");
		
		String expectedFeedbackType = "add";
		String feedbackType = result.get(FEEDBACK_TYPE).get(0);
		assertEquals(expectedFeedbackType, feedbackType);
		
		String expectedFeedbackDes = result.get(FEEDBACK_DESC).get(0);
		String feedbackDes = "Added \"assignment1 on 08/08/2015 to 09082015 1000 to 1800\".";
		assertEquals(expectedFeedbackDes, feedbackDes);
		
		String expectedDesc = "1. assignment1";
		String taskDesc = result.get(TASK_DESC).get(0);
		assertEquals(expectedDesc, taskDesc);
		
		String expectedDate = "08 Aug 2015 10:00 to 09 Aug 2015 18:00";
		String taskDate = result.get(TASK_DATE).get(0);
		assertEquals(expectedDate, taskDate);
		
	}
	
	@Test
	public void testAddTwoDateTwoTimeWithAlias() {
		ArrayList<ArrayList<String>> result = MainLogic.runLogic("delete all");
		
		// Task with 2 date, 2 time, no alias
		result = MainLogic.runLogic("add assignment1 on 08/08/2015 to 09082015 1000 to 1800 alias:a1");
		
		String expectedFeedbackType = "add";
		String feedbackType = result.get(FEEDBACK_TYPE).get(0);
		assertEquals(expectedFeedbackType, feedbackType);
		
		String expectedFeedbackDes = result.get(FEEDBACK_DESC).get(0);
		String feedbackDes = "Added \"assignment1 on 08/08/2015 to 09082015 1000 to 1800 alias:a1\".";
		assertEquals(expectedFeedbackDes, feedbackDes);
		
		String expectedDesc = "1. assignment1";
		String taskDesc = result.get(TASK_DESC).get(0);
		assertEquals(expectedDesc, taskDesc);
		
		String expectedDate = "08 Aug 2015 10:00 to 09 Aug 2015 18:00";
		String taskDate = result.get(TASK_DATE).get(0);
		assertEquals(expectedDate, taskDate);

		String expectedAlias = "a1";
		String taskAlias = result.get(TASK_ALIAS).get(0);
		assertEquals(expectedAlias,taskAlias);
		
	}
	
	
	// Fail cases
	
	@Test
	public void testAddNoDateNoTimeNoAliasFail() {
		ArrayList<ArrayList<String>> result = MainLogic.runLogic("delete all");
		
		// Task with no date, no time, no alias
		// Wrong command typed
		result = MainLogic.runLogic("ad assignment1");
		
		String expectedFeedbackType = "error";
		String feedbackType = result.get(FEEDBACK_TYPE).get(0);
		assertEquals(expectedFeedbackType, feedbackType);
		
		String expectedFeedbackDes = result.get(FEEDBACK_DESC).get(0);
		String feedbackDes = "Oops, please try again.";
		assertEquals(expectedFeedbackDes, feedbackDes);
		
		assertTrue(result.get(TASK_DESC).isEmpty());
	}
	
	@Test
	public void testAddNoDateNoTimeWithAliasFail() {
		ArrayList<ArrayList<String>> result = MainLogic.runLogic("delete all");
	
		// Task with no date, no time, with alias
		// Wrong alias format
		result = MainLogic.runLogic("add assignment1 alias a1");
		
		String expectedFeedbackType = "add";
		String feedbackType = result.get(FEEDBACK_TYPE).get(0);
		assertEquals(expectedFeedbackType, feedbackType);
		
		String expectedFeedbackDes = result.get(FEEDBACK_DESC).get(0);
		String feedbackDes = "Added \"assignment1 alias a1\".";
		assertEquals(expectedFeedbackDes, feedbackDes);
		
		String expectedDesc = "1. assignment1 alias a1";
		String taskDesc = result.get(TASK_DESC).get(0);
		assertEquals(expectedDesc, taskDesc);
		
		String expectedAlias = "a1";
		String taskAlias = result.get(TASK_ALIAS).get(0);
		assertNotEquals(expectedAlias, taskAlias);
	
	}
	
	@Test
	public void testAddNoDateOneTimeNoAliasFail() {
		ArrayList<ArrayList<String>> result = MainLogic.runLogic("delete all");
		
		// Task with no date, 1 time, without alias
		// Wrong time format
		result = MainLogic.runLogic("add assignment1 by 2359am");
		
		String expectedFeedbackType = "add";
		String feedbackType = result.get(FEEDBACK_TYPE).get(0);
		assertEquals(expectedFeedbackType, feedbackType);
		
		String expectedFeedbackDes = result.get(FEEDBACK_DESC).get(0);
		String feedbackDes = "Added \"assignment1 by 2359am\".";
		assertEquals(expectedFeedbackDes, feedbackDes);
		
		String expectedDesc = "1. assignment1 by 2359am";
		String taskDesc = result.get(TASK_DESC).get(0);
		assertEquals(expectedDesc, taskDesc);
		
		String expectedTime = "today";
		String taskTime = result.get(TASK_TIME).get(0);
		assertNotEquals(expectedTime, taskTime);
		
	}
	
	@Test
	public void testAddNoDateOneTimeWithAliasFail() {
		ArrayList<ArrayList<String>> result = MainLogic.runLogic("delete all");
		
		// Task with no date, 1 time, with alias
		// Wrong alias format
		result = MainLogic.runLogic("add assignment1 on 0001 alias a1");
		
		String expectedFeedbackType = "add";
		String feedbackType = result.get(FEEDBACK_TYPE).get(0);
		assertEquals(expectedFeedbackType, feedbackType);
		
		String expectedFeedbackDes = result.get(FEEDBACK_DESC).get(0);
		String feedbackDes = "Added \"assignment1 on 0001 alias a1\".";
		assertEquals(expectedFeedbackDes, feedbackDes);
		
		String expectedDesc = "1. assignment1 alias a1";
		String taskDesc = result.get(TASK_DESC).get(0);
		assertEquals(expectedDesc, taskDesc);
		
		String expectedTime = "overdue";
		String taskTime = result.get(TASK_TIME).get(0);
		assertEquals(expectedTime, taskTime);
		
		String expectedAlias = "a1";
		String taskAlias = result.get(TASK_ALIAS).get(0);
		assertNotEquals(expectedAlias, taskAlias);
		
	}
	
	@Test
	public void testAddNoDateTwoTimeNoAliasFail() {
		ArrayList<ArrayList<String>> result = MainLogic.runLogic("delete all");
		
		//Task with no date, 2 time, without alias
		//Wrong time format
		result = MainLogic.runLogic("add assignment1 on 0000 by 2359am");
		
		String expectedFeedbackType = "add";
		String feedbackType = result.get(FEEDBACK_TYPE).get(0);
		assertEquals(expectedFeedbackType, feedbackType);
		
		String expectedFeedbackDes = result.get(FEEDBACK_DESC).get(0);
		String feedbackDes = "Added \"assignment1 on 0000 by 2359am\".";
		assertEquals(expectedFeedbackDes, feedbackDes);
		
		String expectedDesc = "1. assignment1 by 2359am";
		String taskDesc = result.get(TASK_DESC).get(0);
		assertEquals(expectedDesc, taskDesc);
		
		String expectedTime = "today";
		String taskTime = result.get(TASK_TIME).get(0);
		assertNotEquals(expectedTime, taskTime);
	}
	
	@Test
	public void testAddNoDateTwoTimeWithAliasFail() {
		ArrayList<ArrayList<String>> result = MainLogic.runLogic("delete all");
		
		//Task with no date, 2 time, without alias
		//Wrong alias format
		result = MainLogic.runLogic("add assignment1 on 1130 by 2359 alias a1");
		
		String expectedFeedbackType = "add";
		String feedbackType = result.get(FEEDBACK_TYPE).get(0);
		assertEquals(expectedFeedbackType, feedbackType);
		
		String expectedFeedbackDes = result.get(FEEDBACK_DESC).get(0);
		String feedbackDes = "Added \"assignment1 on 1130 by 2359 alias a1\".";
		assertEquals(expectedFeedbackDes, feedbackDes);
		
		String expectedDesc = "1. assignment1 alias a1";
		String taskDesc = result.get(TASK_DESC).get(0);
		assertEquals(expectedDesc, taskDesc);
		
		String expectedTime = "today";
		String taskTime = result.get(TASK_TIME).get(0);
		assertEquals(expectedTime, taskTime);
		
		String expectedAlias = "a1";
		String taskAlias = result.get(TASK_ALIAS).get(0);
		assertNotEquals(expectedAlias, taskAlias);
	}
	
	@Test
	public void testAddOneDateNoTimeNoAliasFail() {
		ArrayList<ArrayList<String>> result = MainLogic.runLogic("delete all");

		// Task with 1 date, 0 time, without alias
		// Wrong date format
		result = MainLogic.runLogic("add assignment1 on 29/29/2015");
		
		String expectedFeedbackType = "add";
		String feedbackType = result.get(FEEDBACK_TYPE).get(0);
		assertEquals(expectedFeedbackType, feedbackType);
		
		String expectedFeedbackDes = result.get(FEEDBACK_DESC).get(0);
		String feedbackDes = "Added \"assignment1 on 29/29/2015\".";
		assertEquals(expectedFeedbackDes, feedbackDes);
		
		String expectedDesc = "1. assignment1 on 29/29/2015";
		String taskDesc = result.get(TASK_DESC).get(0);
		assertEquals(expectedDesc, taskDesc);
		
		String expectedDate = "29 29 2015 23:59";
		String taskDate = result.get(TASK_DATE).get(0);
		assertNotEquals(expectedDate, taskDate);
		
	}
	
	@Test
	public void testAddOneDateNoTimeWithAliasFail() {
		ArrayList<ArrayList<String>> result = MainLogic.runLogic("delete all");

		// Task with 1 date, 0 time, with alias
		// Wrong alias format
		result = MainLogic.runLogic("add assignment1 on 08/08/2015 alias a1");
		
		String expectedFeedbackType = "add";
		String feedbackType = result.get(FEEDBACK_TYPE).get(0);
		assertEquals(expectedFeedbackType, feedbackType);
		
		String expectedFeedbackDes = result.get(FEEDBACK_DESC).get(0);
		String feedbackDes = "Added \"assignment1 on 08/08/2015 alias a1\".";
		assertEquals(expectedFeedbackDes, feedbackDes);
		
		String expectedDesc = "1. assignment1";
		String taskDesc = result.get(TASK_DESC).get(0);
		assertNotEquals(expectedDesc, taskDesc);
		
		String expectedDate = "08 Aug 2015 23:59";
		String taskDate = result.get(TASK_DATE).get(0);
		assertEquals(expectedDate, taskDate);
		
		String expectedAlias = "a1";
		String taskAlias = result.get(TASK_ALIAS).get(0);
		assertNotEquals(expectedAlias, taskAlias);
		
	}
	
	@Test
	public void testAddOneDateOneTimeNoAliasFail() {
		ArrayList<ArrayList<String>> result = MainLogic.runLogic("delete all");
		
		// Task with 1 date, 1 time, without alias
		// Wrong date format
		result = MainLogic.runLogic("add assignment1 on 29/29/2015 1800");
		
		String expectedFeedbackType = "add";
		String feedbackType = result.get(FEEDBACK_TYPE).get(0);
		assertEquals(expectedFeedbackType, feedbackType);
		
		String expectedFeedbackDes = result.get(FEEDBACK_DESC).get(0);
		String feedbackDes = "Added \"assignment1 on 29/29/2015 1800\".";
		assertEquals(expectedFeedbackDes, feedbackDes);
		
		String expectedDesc = "1. assignment1 on 29/29/2015";
		String taskDesc = result.get(TASK_DESC).get(0);
		assertEquals(expectedDesc, taskDesc);
		
		String expectedDate = "29 29 2015 18:00";
		String taskDate = result.get(TASK_DATE).get(0);
		assertNotEquals(expectedDate, taskDate);
		
	}
	
	@Test
	public void testAddOneDateOneTimeWithAliasFail() {
		ArrayList<ArrayList<String>> result = MainLogic.runLogic("delete all");
		
		// Task with 1 date, 1 time, with alias
		// Wrong alias format
		result = MainLogic.runLogic("add alias a1 assignment1 on 08/08/2015 1800");
		
		String expectedFeedbackType = "add";
		String feedbackType = result.get(FEEDBACK_TYPE).get(0);
		assertEquals(expectedFeedbackType, feedbackType);
		
		String expectedFeedbackDes = result.get(FEEDBACK_DESC).get(0);
		String feedbackDes = "Added \"alias a1 assignment1 on 08/08/2015 1800\".";
		assertEquals(expectedFeedbackDes, feedbackDes);
		
		String expectedDesc = "1. alias a1 assignment1";
		String taskDesc = result.get(TASK_DESC).get(0);
		assertEquals(expectedDesc, taskDesc);
		
		String expectedDate = "08 Aug 2015 18:00";
		String taskDate = result.get(TASK_DATE).get(0);
		assertEquals(expectedDate, taskDate);
		
		String expectedAlias = "a1";
		String taskAlias = result.get(TASK_ALIAS).get(0);
		assertNotEquals(expectedAlias,taskAlias);
		
	}

	@Test
	public void testAddOneDateTwoTimeNoAliasFail() {
		ArrayList<ArrayList<String>> result = MainLogic.runLogic("delete all");
		
		// Task with 1 date, 2 time, without alias
		// Wrong time format
		result = MainLogic.runLogic("add assignment1 on 08/08/2015 1800 by 2359am");
		
		String expectedFeedbackType = "add";
		String feedbackType = result.get(FEEDBACK_TYPE).get(0);
		assertEquals(expectedFeedbackType, feedbackType);
		
		String expectedFeedbackDes = result.get(FEEDBACK_DESC).get(0);
		String feedbackDes = "Added \"assignment1 on 08/08/2015 1800 by 2359am\".";
		assertEquals(expectedFeedbackDes, feedbackDes);
		
		String expectedDesc = "1. assignment1";
		String taskDesc = result.get(TASK_DESC).get(0);
		assertNotEquals(expectedDesc, taskDesc);
		
		String expectedDate = "08 Aug 2015 18:00 to 08 Aug 2015 20:00";
		String taskDate = result.get(TASK_DATE).get(0);
		assertNotEquals(expectedDate, taskDate);
	}
	
	@Test
	public void testAddOneDateTwoTimeWithAliasFail() {
		ArrayList<ArrayList<String>> result = MainLogic.runLogic("delete all");
		
		// Task with 1 date, 2 time, with alias
		// Wrong alias format
		result = MainLogic.runLogic("add assignment1 on 08/08/2015 1800 by 2000 alias a1");
		
		String expectedFeedbackType = "add";
		String feedbackType = result.get(FEEDBACK_TYPE).get(0);
		assertEquals(expectedFeedbackType, feedbackType);
		
		String expectedFeedbackDes = result.get(FEEDBACK_DESC).get(0);
		String feedbackDes = "Added \"assignment1 on 08/08/2015 1800 by 2000 alias a1\".";
		assertEquals(expectedFeedbackDes, feedbackDes);
		
		String expectedDesc = "1. assignment1";
		String taskDesc = result.get(TASK_DESC).get(0);
		assertNotEquals(expectedDesc, taskDesc);
		
		String expectedDate = "08 Aug 2015 18:00 to 08 Aug 2015 20:00";
		String taskDate = result.get(TASK_DATE).get(0);
		assertEquals(expectedDate, taskDate);
		
		String expectedAlias = "a1";
		String taskAlias = result.get(TASK_ALIAS).get(0);
		assertNotEquals(expectedAlias,taskAlias);
		
	}
	
	@Test
	public void testAddTwoDateNoTimeNoAliasFail() {
		ArrayList<ArrayList<String>> result = MainLogic.runLogic("delete all");
		
		// Task with 2 date, no time, no alias
		// Wrong date format
		result = MainLogic.runLogic("add assignment1 on 08/08/2015 to 09292015");
	
		String expectedFeedbackType = "add";
		String feedbackType = result.get(FEEDBACK_TYPE).get(0);
		assertEquals(expectedFeedbackType, feedbackType);
		
		String expectedFeedbackDes = result.get(FEEDBACK_DESC).get(0);
		String feedbackDes = "Added \"assignment1 on 08/08/2015 to 09292015\".";
		assertEquals(expectedFeedbackDes, feedbackDes);
		
		String expectedDesc = "1. assignment1";
		String taskDesc = result.get(TASK_DESC).get(0);
		assertNotEquals(expectedDesc, taskDesc);
		
		String expectedDate = "08 Aug 2015 00:00 to 09 Aug 2015 23:59";
		String taskDate = result.get(TASK_DATE).get(0);
		assertNotEquals(expectedDate, taskDate);

	}

	@Test
	public void testAddTwoDateNoTimeWithAliasFail() {
		ArrayList<ArrayList<String>> result = MainLogic.runLogic("delete all");
		
		// Task with 2 date, no time, with alias
		// Wrong alias format
		result = MainLogic.runLogic("add assignment1 on 08/08/2015 to 09082015 alias a1");
	
		String expectedFeedbackType = "add";
		String feedbackType = result.get(FEEDBACK_TYPE).get(0);
		assertEquals(expectedFeedbackType, feedbackType);
		
		String expectedFeedbackDes = result.get(FEEDBACK_DESC).get(0);
		String feedbackDes = "Added \"assignment1 on 08/08/2015 to 09082015 alias a1\".";
		assertEquals(expectedFeedbackDes, feedbackDes);
		
		String expectedDesc = "1. assignment1";
		String taskDesc = result.get(TASK_DESC).get(0);
		assertNotEquals(expectedDesc, taskDesc);
		
		String expectedDate = "08 Aug 2015 00:00 to 09 Aug 2015 23:59";
		String taskDate = result.get(TASK_DATE).get(0);
		assertEquals(expectedDate, taskDate);
		
		String expectedAlias = "a1";
		String taskAlias = result.get(TASK_ALIAS).get(0);
		assertNotEquals(expectedAlias,taskAlias);

	}
	
	@Test
	public void testAddTwoDateOneTimeNoAliasFail() {
		ArrayList<ArrayList<String>> result = MainLogic.runLogic("delete all");
		
		// Task with 2 date, 1 time, no alias
		// Wrong date and time format
		result = MainLogic.runLogic("add assignment1 on 08/08/2015 to 09082015 2359am");
	
		String expectedFeedbackType = "add";
		String feedbackType = result.get(FEEDBACK_TYPE).get(0);
		assertEquals(expectedFeedbackType, feedbackType);
		
		String expectedFeedbackDes = result.get(FEEDBACK_DESC).get(0);
		String feedbackDes = "Added \"assignment1 on 08/08/2015 to 09082015 2359am\".";
		assertEquals(expectedFeedbackDes, feedbackDes);
		
		String expectedDesc = "1. assignment1";
		String taskDesc = result.get(TASK_DESC).get(0);
		assertNotEquals(expectedDesc, taskDesc);
		
		String expectedDate = "08 Aug 2015 10:00 to 09 Aug 2015 23:59";
		String taskDate = result.get(TASK_DATE).get(0);
		assertNotEquals(expectedDate, taskDate);

	}
	
	@Test
	public void testAddTwoDateOneTimeWithAliasFail() {
		ArrayList<ArrayList<String>> result = MainLogic.runLogic("delete all");
		
		// Task with 2 date, 1 time, with alias
		// Wrong date, time, alias format
		result = MainLogic.runLogic("add assignment1 on 08/08/2015 to 09082015 1000 alias a1");
	
		String expectedFeedbackType = "add";
		String feedbackType = result.get(FEEDBACK_TYPE).get(0);
		assertEquals(expectedFeedbackType, feedbackType);
		
		String expectedFeedbackDes = result.get(FEEDBACK_DESC).get(0);
		String feedbackDes = "Added \"assignment1 on 08/08/2015 to 09082015 1000 alias a1\".";
		assertEquals(expectedFeedbackDes, feedbackDes);
		
		String expectedDesc = "1. assignment1";
		String taskDesc = result.get(TASK_DESC).get(0);
		assertNotEquals(expectedDesc, taskDesc);
		
		String expectedDate = "08 Aug 2015 10:00 to 09 Aug 2015 23:59";
		String taskDate = result.get(TASK_DATE).get(0);
		assertEquals(expectedDate, taskDate);

		String expectedAlias = "a1";
		String taskAlias = result.get(TASK_ALIAS).get(0);
		assertNotEquals(expectedAlias,taskAlias);
		
	}
	
	@Test
	public void testAddTwoDateTwoTimeNoAliasFail() {
		ArrayList<ArrayList<String>> result = MainLogic.runLogic("delete all");
		
		// Task with 2 date, 2 time, no alias
		// Wrong date and time format
		result = MainLogic.runLogic("add assignment1 on 29/29/2015 to 29302015 1800am to 2300am");
		
		String expectedFeedbackType = "add";
		String feedbackType = result.get(FEEDBACK_TYPE).get(0);
		assertEquals(expectedFeedbackType, feedbackType);
		
		String expectedFeedbackDes = result.get(FEEDBACK_DESC).get(0);
		String feedbackDes = "Added \"assignment1 on 29/29/2015 to 29302015 1800am to 2300am\".";
		assertEquals(expectedFeedbackDes, feedbackDes);
		
		String expectedDesc = "1. assignment1";
		String taskDesc = result.get(TASK_DESC).get(0);
		assertNotEquals(expectedDesc, taskDesc);
		
		String expectedDate = "08 Aug 2015 10:00 to 09 Aug 2015 18:00";
		String taskDate = result.get(TASK_DATE).get(0);
		assertNotEquals(expectedDate, taskDate);
		
	}
	
	@Test
	public void testAddTwoDateTwoTimeWithAliasFail() {
		ArrayList<ArrayList<String>> result = MainLogic.runLogic("delete all");
		
		// Task with 2 date, 2 time, no alias
		// Wrong date, time and alias format
		result = MainLogic.runLogic("add assignment1 on 29/29/2015 to 29302015 1800am to 2300am alias a1");
		
		String expectedFeedbackType = "add";
		String feedbackType = result.get(FEEDBACK_TYPE).get(0);
		assertEquals(expectedFeedbackType, feedbackType);
		
		String expectedFeedbackDes = result.get(FEEDBACK_DESC).get(0);
		String feedbackDes = "Added \"assignment1 on 29/29/2015 to 29302015 1800am to 2300am alias a1\".";
		assertEquals(expectedFeedbackDes, feedbackDes);
		
		String expectedDesc = "1. assignment1";
		String taskDesc = result.get(TASK_DESC).get(0);
		assertNotEquals(expectedDesc, taskDesc);
		
		String expectedDate = "08 Aug 2015 10:00 to 09 Aug 2015 18:00";
		String taskDate = result.get(TASK_DATE).get(0);
		assertNotEquals(expectedDate, taskDate);

		String expectedAlias = "a1";
		String taskAlias = result.get(TASK_ALIAS).get(0);
		assertNotEquals(expectedAlias,taskAlias);
		
	}
	
		

}
