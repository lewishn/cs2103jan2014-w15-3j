
import static org.junit.Assert.*;

import org.junit.Test;


public class TestDateParser {

	@Test
	public void testIsDate() {
		assertEquals(true, DateParser.isDate("12/12/2012"));
		assertEquals(true, DateParser.isDate("12-12-2012"));
		assertEquals(true, DateParser.isDate("12.12.2012"));
		assertEquals(true, DateParser.isDate("12/12"));
		assertEquals(true, DateParser.isDate("12-12"));
		assertEquals(true, DateParser.isDate("12.12"));
		assertEquals(true, DateParser.isDate("12/Feb/2012"));
		assertEquals(true, DateParser.isDate("12-March-2012"));
		assertEquals(true, DateParser.isDate("12.December.2012"));
		assertEquals(true, DateParser.isDate("Tuesday"));
		assertEquals(false, DateParser.isDate("yolo"));
	}

}

