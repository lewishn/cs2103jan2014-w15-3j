package dothings.tests;
import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import org.junit.Test;
import dothings.logic.MainLogic;

public class IntegrationTesting {
	private static final int FEEDBACK_TYPE = 0;
	private static final int FEEDBACK_DESC = 1;
	private static final int TASK_DESC = 2;
	private static final int TASK_ALIAS = 3;
	private static final int TASK_STATUS = 4;
	private static final int TASK_DATE = 5;
	private static final int TASK_TIME = 6;

	@Test
	public void testHelp() {
		ArrayList<ArrayList<String>> result = MainLogic.runLogic("delete all");
		// add tasks
		result = MainLogic.runLogic("help");

		String expectedType = "help";
		String feedbackType = result.get(FEEDBACK_TYPE).get(0);
		assertEquals(expectedType, feedbackType);
		/*
		String expectedDesc = "[ADD]\n add\n\n [UPDATE]\n update \n\n [MARK]\n mark\n\n [DELETE]\n delete\n\n [LIST]\n list\n\n [SEARCH]\n search"
				+ "\n\n [UNDO]\n undo\n\n [REDO]\n redo\n\n [CUSTOM]\n custom\n\n [DELETE_CUSTOM]\n dcustom\n\n [HELP]\n help\n\n [EXIT]\n exit \n\n";
		String feedbackDesc = result.get(TASK_DESC).get(0);
		assertEquals(expectedDesc, feedbackDesc);*/
	}

}
