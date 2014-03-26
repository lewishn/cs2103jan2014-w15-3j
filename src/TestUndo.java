import static org.junit.Assert.*;

import org.junit.Test;


public class TestUndo {

	@Test
	public void testUndo() {
		MainLogic.runLogic("add meeting with john");
		MainLogic.runLogic("Undo");
		Feedback feedback = HistoryHandler.undoCommand();
		assertEquals("Undo successful!" + "\n",feedback.getDesc());
	}
}
