import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.sound.sampled.Line;


class DiskIO {
	public static final int ADD_INDEX = 0;
	public static final int LIST_INDEX = 1;
	public static final int UPDATE_INDEX = 2;
	public static final int DELETE_INDEX = 3;
	public static final int UNDO_INDEX = 4;
	public static final int SEACRH_INDEX = 5;
	public static final int CUSTOM_INDEX = 6;
	public static final int DELETE_CUSTOM_INDEX = 7;
	public static final int HELP_INDEX = 8;
	public static final int EXIT_INDEX = 9;
	
	private static final String FILE_TASKS = "tasks.txt";
	private static final String FILE_CUSTOM = "custom.txt";
	
	private static final String WRITE_ERROR = "Cannot write to file!";
	private static final String READ_ERROR = "Cannot read from file!";
	
	protected static void writeToFile(ArrayList<Task> list, String fileName) {
		try {
			File file = new File(fileName);
			BufferedWriter bw = new BufferedWriter(new FileWriter(file));
			for(int i = 0; i < list.size(); i++) {
				bw.write(list.get(i).toString());
				bw.close();
			}
		} catch (IOException e) {
			DoThings.printFeedbackLn(WRITE_ERROR);
		}
	}
	
	protected static ArrayList<Task> readTaskFromFile(String fileName) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(fileName));
			String line;
			ArrayList<Task> list = new ArrayList<Task>();
			while ((line = br.readLine()) != null) {
				// to be implemented
				// Task(String);
			}
			return list;
		} catch (IOException e) {
			DoThings.printFeedbackLn(READ_ERROR);
			return null;
		}
	}	

	protected static ArrayList<ArrayList<String>> readCustomCommands() {
		ArrayList<ArrayList<String>> list = new ArrayList<ArrayList<String>>();
		File customFile = new File(FILE_CUSTOM);
		
		try {
			BufferedReader br = new BufferedReader (new FileReader(customFile));
			String line;
			for(int i = 0; i < 7; i++) {
				line = br.readLine();
				list.add(new ArrayList(Arrays.asList(line.split(" "))));
			}
		} catch (FileNotFoundException e) {
			for(int i = 0; i < 7; i++) {
				list.add(new ArrayList<String>());
			}
		} catch (IOException ex) {
			System.out.println();
		}
		
		
		// if file doesn't exist, create...
		try {
			BufferedReader br = new BufferedReader (new FileReader(FILE_CUSTOM));
			String line;
			for(int i = 0; i < 7; i++) {
				line = br.readLine();
				list.add(new ArrayList(Arrays.asList(line.split(" "))));
			}
			return list;
		} catch (IOException e) {
			System.out.println();
		}
		
		return list;
	}
}
