
package dothings.tests;

import java.util.ArrayList;

import dothings.logic.CustomCommandHandler;
import dothings.logic.Feedback;
import dothings.logic.Task;
import static org.junit.Assert.*;

import org.joda.time.DateTime;
import org.junit.Test;

//@author A0099727J
public class TestLogic {

	@Test
	public void testCustomCommandHandler() {
		CustomCommandHandler.deleteCustomCommand("insert");
		CustomCommandHandler.addCustomCommand("insert", "[ADD]");
		assertTrue(CustomCommandHandler.isCustomCommand("insert", "[ADD]"));
		assertFalse(CustomCommandHandler.isCustomCommand("insert", "[DELETE]"));
		
		Feedback feedback = CustomCommandHandler.addCustomCommand("insert", "[ADD]");
		assertEquals(feedback.toString(), CustomCommandHandler.MESSAGE_CUSTOM_DUPLICATE);
		
		feedback = CustomCommandHandler.addCustomCommand("insert", "[DELETE]");
		assertEquals(feedback.toString(), CustomCommandHandler.MESSAGE_CUSTOM_DUPLICATE);
	}
	
	@Test
	public void testTaskSetGetMethods() {
		Task task = new Task("This is the first task");
		assertNull(task.getStartDateTime());
		assertNull(task.getEndDateTime());
		assertFalse(task.getStatus());
		assertEquals("This is the first task", task.getDescription());
		assertNull(task.getAlias());
		
		task.setDescription("The first task is being edited");
		assertEquals("The first task is being edited", task.getDescription());
		
		task.toggleStatus();
		assertTrue(task.getStatus());
		
		task.setAlias("hama");
		assertEquals("hama", task.getAlias());
		
		DateTime time = new DateTime();
		task.setStartDateTime(time);
		assertEquals(time, task.getStartDateTime());
		task.setEndDateTime(time);
		assertEquals(time, task.getEndDateTime());
		
		
		Task task2 = new Task("This is the second task");
		Task.setList(new ArrayList<Task>());
		Task.getList().add(task);
		Task.getList().add(task2);
		assertEquals(2, Task.getList().size());
		
		ArrayList<Task> cloneList = Task.getCloneList();
		cloneList.remove(0);
		assertEquals(2, Task.getList().size());
	}
	
	@Test
	public void testTaskTimeMethods() {
		Task task1 = new Task("This is a dummy scheduled task", new DateTime().minusDays(2), null, "yolo");
		assertTrue(task1.isOverdue());
		assertFalse(task1.isUnscheduled());

		Task task2 = new Task("This is a second dummy scheduled task", new DateTime(), new DateTime(), "yolo");
		assertFalse(task2.isOverdue());
		assertTrue(task2.isToday());
		assertFalse(task2.isUnscheduled());
		
		Task task3 = new Task("This is a dummy unscheduled task");
		assertFalse(task3.isOverdue());
		assertFalse(task3.isToday());
		assertTrue(task3.isUnscheduled());
	}
	
	@Test
	public void testTaskOtherMethods() {
		// test toString()
		Task task = new Task("This is to test the string methods", null, null, "trippy");
		String expectedString = "NO_START_TIME ~~ NO_END_TIME ~~ trippy ~~ false ~~ This is to test the string methods";
		assertEquals(expectedString,task.toString());
		
		// test parseTaskFromString()
		task = Task.parseTaskFromString(expectedString);
		assertEquals("This is to test the string methods", task.getDescription());
		assertEquals("trippy", task.getAlias());
		assertNull(task.getStartDateTime());
		assertNull(task.getEndDateTime());
		assertFalse(task.getStatus());	
		
		// test compareTo()
		Task task1 = new Task("This is the a scheduled task", new DateTime(), new DateTime(), "yoko");
		Task task2 = new Task("This is the a scheduled task", new DateTime(), new DateTime(), "hama");
		Task task3 = new Task("This is the another scheduled task", new DateTime().plusHours(1), null, "coco");
		Task task4 = new Task("A");
		Task task5 = new Task("B",  null, null, "loco");
		
		assertTrue(task1.compareTo(task2) < 0);
		assertTrue(task1.compareTo(task3) < 0);
		assertTrue(task1.compareTo(task4) < 0);
		assertTrue(task1.compareTo(task5) < 0);

		assertTrue(task2.compareTo(task3) < 0);
		assertTrue(task2.compareTo(task4) < 0);
		assertTrue(task2.compareTo(task5) < 0);
		
		assertTrue(task3.compareTo(task4) < 0);
		assertTrue(task3.compareTo(task5) < 0);
		
		assertTrue(task4.compareTo(task5) < 0);
		
		// test getTaskIndexFromAlias and sortList()
		Task.setList(new ArrayList<Task>());
		Task.getList().add(task1);
		Task.getList().add(task2);
		Task.getList().add(task3);
		Task.getList().add(task4);
		Task.getList().add(task5);
		Task.sortList();
		assertEquals(0, Task.getTaskIndexFromAlias("yoko"));
		assertEquals(1, Task.getTaskIndexFromAlias("hama"));
		assertEquals(2, Task.getTaskIndexFromAlias("coco"));
		assertEquals(4, Task.getTaskIndexFromAlias("loco"));
		
		// test isAliasValid()
		assertTrue(Task.isAliasValid("yoko"));
		assertFalse(Task.isAliasValid("jajaja"));
	}
}
