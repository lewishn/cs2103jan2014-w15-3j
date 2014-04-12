//@author A0099727J
package dothings.tests;
import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import dothings.logic.MainLogic;
import dothings.logic.Task;


public class TestCommands {

	@Test
	public void test() {
		MainLogic.runLogic("delete all");
		ArrayList<Task> list = Task.getList();
		assertTrue(list.isEmpty());
		
		MainLogic.runLogic("add buy milk");
		list = Task.getList();
		assertTrue(list.get(0).getDescription().equals("buy milk"));
		
		MainLogic.runLogic("update 1 alias milk");
		list = Task.getList();
		assertTrue(list.get(0).getAlias().equals("milk"));
		
		MainLogic.runLogic("update milk desc eat cereal");
		list = Task.getList();
		assertTrue(list.get(0).getDescription().equals("eat cereal"));
		
		MainLogic.runLogic("delete milk");
		list = Task.getList();
		assertTrue(list.isEmpty());
		
		MainLogic.runLogic("undo");
		list = Task.getList();
		assertFalse(list.isEmpty());
	}
}
