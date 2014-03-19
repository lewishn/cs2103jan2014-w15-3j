import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;


public class DoThingsTest {

	@Test
	public void testExecuteCommand() throws IOException {
		testAddFunction("Added meet tutor 21/3/14 2pm", "add meet tutor 21/3/14 2pm");
		
	}

	private void testAddFunction(String expected, String command) {
		assertEquals(expected, MainLogic.runLogic(command));
	}
	
}
