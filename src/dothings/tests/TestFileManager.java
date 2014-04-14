package dothings.tests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import dothings.storage.FileManager;

public class TestFileManager {
	private static final String TEST_FILE = "testFM.txt";
	
	@Test
	public void test() {
		ArrayList<String> list = new ArrayList<String>();
		FileManager.writeToFile(TEST_FILE, list);
		assertTrue(FileManager.readFromFile(TEST_FILE).isEmpty());
		
		for (int i = 0; i < 10; i++) {
			list.add("line " + i);
		}
		FileManager.writeToFile("testFM.txt", list);
		ArrayList<String> readList = FileManager.readFromFile(TEST_FILE);
		for (int i = 0; i < 10; i++) {
			assertEquals("line " + i , list.get(i));;
		}
		
	}

}
