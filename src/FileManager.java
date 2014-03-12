import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import org.joda.time.DateTime;



class FileManager {
	private static final String READ_ERROR = "Error, unable to read file.";
	
	private static final String FILE_TASK = "task.txt";
	private static final String FILE_CUSTOM = "custom.txt";
	
	private static BufferedReader getReader(String fileName) throws FileNotFoundException {
		File file = new File(fileName);
		BufferedReader bw = new BufferedReader(new FileReader(file));
		return bw;
	}
	
	private static BufferedWriter getWriter(String fileName) throws IOException {
		File file = new File(fileName);
		BufferedWriter bw = new BufferedWriter(new FileWriter(file));
		return bw;
	}
	
	protected static void writeTasksToFile(ArrayList<Task> list) {
		try {
			BufferedWriter writer = getWriter(FILE_TASK);
			for(int i = 0; i < list.size(); i++) {
				writer.write(list.get(i).toString() + "\r\n");
			}
			writer.flush();
			writer.close();
		} catch (IOException e) {
			
		}
	}
	
	protected static ArrayList<Task> readTasksFromFile() {
		ArrayList<Task> list = new ArrayList<Task>();
		try {
			BufferedReader reader = getReader(FILE_TASK);
			String line;
			while ((line = reader.readLine()) != null) {
				list.add(Task.parseTaskFromString(line));
			}
			reader.close();
			return list;
		} catch (IOException e){
			return list;
		}
	}
	
	protected static void writeCustomCommandsToFile(ArrayList<ArrayList<String>> list) {
		try {
			BufferedWriter writer = getWriter(FILE_CUSTOM);
			for(int i = 0; i < list.size(); i++) {
				for (int j = 0; j < list.get(i).size(); j++) {
					writer.write(list.get(i).get(j) + " ");
				}
				writer.write("\r\n");
			}
			writer.flush();
			writer.close();
		} catch (IOException e) {
			
		}
	}
	
	protected static ArrayList<ArrayList<String>> readCustomCommandsFromFile(){
		ArrayList<ArrayList<String>> newList = new ArrayList<ArrayList<String>>();
		try {
			BufferedReader reader = getReader(FILE_CUSTOM);
			String line;
			while ((line = reader.readLine()) != null) {
				String[] customTokens = line.split(" ");
				ArrayList<String> customList = new ArrayList<String>(Arrays.asList(customTokens));
				newList.add(customList);
			}
			reader.close();
			return newList;
		} catch (IOException e) {
			return newList;
		}
	}
}
