import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;


public class DoThingsTest {

	@Test
	public void testExecuteCommand() throws IOException {
		testWelcomeCommand("create new welcome message", "Welcome to TextBuddy. testfile is ready for use", "testfile");
		testSearchCommand(1, "apple");
		testSearchCommand(0, "pear");
		testSortCommand("apple", 2);
		testSortCommand("aaabbb", 0);
		testSortCommand("ABCABC", 1);
	}

	private void testWelcomeCommand(String description, String expected, String command) {
		assertEquals(description, expected, textbuddy.createWelcomeMessage(command)); 
	}
	private void testSearchCommand(int expected, String command) throws IOException{
		assertEquals(expected , textbuddy.testSearch(command));
	}
	private void testSortCommand(String expected, int command) throws IOException{
		assertEquals(expected, textbuddy.testSort(command));
	}
	private void testAddFunction(String expected, String command) {
		assertEquals(expected, TaskHandler.testAdd(command));
	}
}
