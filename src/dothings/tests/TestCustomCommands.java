package dothings.tests;

import dothings.logic.CustomCommandHandler;
import dothings.logic.Feedback;
import static org.junit.Assert.*;
import org.junit.Test;

public class TestCustomCommands {

	@Test
	public void test() {
		CustomCommandHandler.deleteCustomCommand("insert");
		CustomCommandHandler.addCustomCommand("insert", "[ADD]");
		assertTrue(CustomCommandHandler.isCustomCommand("insert", "[ADD]"));
		assertFalse(CustomCommandHandler.isCustomCommand("insert", "[DELETE]"));
		
		Feedback feedback = CustomCommandHandler.addCustomCommand("insert", "[ADD]");
		assertEquals(feedback.toString(), CustomCommandHandler.MESSAGE_CUSTOM_DUPLICATE);
		
		feedback = CustomCommandHandler.addCustomCommand("insert", "[DELETE]");
		assertEquals(feedback.toString(), CustomCommandHandler.MESSAGE_CUSTOM_DUPLICATE);
	}

}
