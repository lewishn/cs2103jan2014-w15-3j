package dothings.tests;

import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Arrays;
import org.joda.time.DateTime;
import org.junit.Test;
import dothings.parser.CommandParser;
import dothings.parser.DateParser;
import dothings.parser.TimeParser;

public class TestParser {
	////////Tests for Command Parser /////////
	
	@Test 
	public void testGetUserCommand() {
		assertEquals("add", CommandParser.getUserCommandType("add meeting with john"));
		assertEquals("meeting with jack", CommandParser.getUserCommandDesc("add meeting with jack"));
		assertNull(CommandParser.getUserCommandDesc("add"));
	}
	
	@Test
	public void testIsValidCommand() {
		assertTrue(CommandParser.isInputValid("add yolo", 2));
		assertTrue(CommandParser.isInputValid("add yolo forever and ever and ever", 2));
		assertFalse(CommandParser.isInputValid("add", 2));
		assertFalse(CommandParser.isInputValid("add ", 2));
	}
	
	@Test
	public void testGetTaskFields() {
		String[] expected = new String[4];
		String[] input;
		ArrayList<String> actual;
		
		input = "do stuff".split(" ");
		actual = new ArrayList<String>(Arrays.asList(input));
		assertArrayEquals(expected, CommandParser.getTaskFields(actual));
		
		expected[1] = "5pm";
		input = "do stuff from 5pm".split(" ");
		actual = new ArrayList<String>(Arrays.asList(input));
		assertArrayEquals(expected, CommandParser.getTaskFields(actual));
		
		expected[3] = "1800";
		input = "do stuff from 5pm to 1800".split(" ");
		actual = new ArrayList<String>(Arrays.asList(input));
		assertArrayEquals(expected, CommandParser.getTaskFields(actual));
		
		expected[0] = "monday";
		input = "do stuff on monday from 5pm to 1800".split(" ");
		actual = new ArrayList<String>(Arrays.asList(input));
		assertArrayEquals(expected, CommandParser.getTaskFields(actual));
		
		expected[2] = "13/01/2033";
		input = "do stuff from monday 5pm to 13/01/2033 1800".split(" ");
		actual = new ArrayList<String>(Arrays.asList(input));
		assertArrayEquals(expected, CommandParser.getTaskFields(actual));
		
	}
	
	@Test
	public void testRemoveDateTimeFromString() {
		assertEquals("do stuff", CommandParser.removeDateTimeFromString("do stuff"));
		assertEquals("do stuff with abby", CommandParser.removeDateTimeFromString("do stuff on monday with abby"));
		assertEquals("do stuff with abby", CommandParser.removeDateTimeFromString("on monday do stuff with abby until tue"));
		assertEquals("meeting with abby", CommandParser.removeDateTimeFromString("from 5pm to 6pm meeting with abby on 12/12/12"));
	}
	
	@Test
	public void testRemoveAliasAndEscapeChar() {
		assertEquals("do stuff", CommandParser.removeAliasAndEscapeChar("do stuff"));
		assertEquals("do stuff on monday with abby", CommandParser.removeAliasAndEscapeChar("do stuff on \\monday with abby"));
		assertEquals("do stuff with abby", CommandParser.removeAliasAndEscapeChar("alias:who do stuff with abby"));
		assertEquals("meeting with abby on 12/12/12", CommandParser.removeAliasAndEscapeChar("alias:meat meeting with abby on \\12/12/12"));
	}
	
	////////Tests for Date Parser /////////
	
	@Test
	public void testIsDate() {
		// Test for accepted date formats partition
		assertTrue(DateParser.isDate("12/12/2012"));
		assertTrue(DateParser.isDate("12-12-2012"));
		assertTrue(DateParser.isDate("12.12.2012"));
		assertTrue(DateParser.isDate("12/12"));
		assertTrue(DateParser.isDate("12-12"));
		assertTrue(DateParser.isDate("12/Feb/2012"));
		assertTrue(DateParser.isDate("12-March-2012"));
		assertTrue(DateParser.isDate("12.Dec.2012"));
		assertTrue(DateParser.isDate("Tuesday"));
		assertTrue(DateParser.isDate("Tomorrow"));
		
		// Test for unsupported date formats partition
		assertFalse(DateParser.isDate("12-Decem-2013"));
		assertFalse(DateParser.isDate("12.12"));
		assertFalse(DateParser.isDate("yolo"));
		
		// Test for accepted date formats with escape characters
		assertFalse(DateParser.isDate("/tomorrow"));
	}
	
	@Test
	public void testSetDate() {
		DateTime dt;
		dt = DateParser.setDate("11/12/2012");
		assertEquals("11/12/2012", dt.toString("dd/MM/YYYY"));
		dt = DateParser.setDate("11-12-2012");
		assertEquals("11/12/2012", dt.toString("dd/MM/YYYY"));
		dt = DateParser.setDate("11.12.2012");
		assertEquals("11/12/2012", dt.toString("dd/MM/YYYY"));
		dt = DateParser.setDate("11/12");
		assertEquals("11/12", dt.toString("dd/MM"));
		dt = DateParser.setDate("11-12");
		assertEquals("11/12", dt.toString("dd/MM"));
		dt = DateParser.setDate("11-March-2012");
		assertEquals("11/03/2012", dt.toString("dd/MM/YYYY"));
		dt = DateParser.setDate("11.Dec.2012");
		assertEquals("11/12/2012", dt.toString("dd/MM/YYYY"));
	}
	
	//////// Tests for Time Parser /////////
	
	@Test
	public void testIsTime() {
		// Test for accepted time formats
		assertTrue(TimeParser.isTime("12:13"));
		assertTrue(TimeParser.isTime("23.45"));
		assertTrue(TimeParser.isTime("2359"));
		assertTrue(TimeParser.isTime("11:30am"));
		assertTrue(TimeParser.isTime("12.20pm"));
		assertTrue(TimeParser.isTime("1130pm"));
		assertTrue(TimeParser.isTime("11pm"));
		assertTrue(TimeParser.isTime("1am"));
		
		// Test for unsupported time formats
		assertFalse(TimeParser.isTime("24:00"));
		
		// Test for supported time formats with escape characters
		assertFalse(TimeParser.isTime("/1235"));
	}
	
	@Test
	public void testSetTime() {
		DateTime dt = new DateTime();
		dt = TimeParser.setTime(dt, "12:13");
		assertEquals("12:13", dt.toString("HH:mm"));
		dt = TimeParser.setTime(dt, "23.45");
		assertEquals("23:45", dt.toString("HH:mm"));
		dt = TimeParser.setTime(dt, "2359");
		assertEquals("23:59", dt.toString("HH:mm"));
		dt = TimeParser.setTime(dt, "11:30am");
		assertEquals("11:30", dt.toString("HH:mm"));
		dt = TimeParser.setTime(dt, "12.20pm");
		assertEquals("12:20", dt.toString("HH:mm"));
		dt = TimeParser.setTime(dt, "1130pm");
		assertEquals("23:30", dt.toString("HH:mm"));
		dt = TimeParser.setTime(dt, "11pm");
		assertEquals("23:00", dt.toString("HH:mm"));
		dt = TimeParser.setTime(dt, "1am");
		assertEquals("01:00", dt.toString("HH:mm"));
	}
}

