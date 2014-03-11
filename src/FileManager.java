import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

class FileManager {
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
	/*
	private static void main(String args[]) throws IOException {
		
		ArrayList<ArrayList<String>> foo = new ArrayList<ArrayList<String>>();
		foo.add(new ArrayList<String>());foo.add(new ArrayList<String>());foo.add(new ArrayList<String>());foo.add(new ArrayList<String>());
		foo.get(0).add("[ADD]");foo.get(0).add("a");foo.get(0).add("b");foo.get(0).add("c");foo.get(0).add("d");
		foo.get(1).add("[UPDATE]");foo.get(1).add("he");foo.get(1).add("she");foo.get(1).add("them");
		foo.get(2).add("[DELETE]");foo.get(2).add("yo");foo.get(2).add("lo");
		foo.get(3).add("[READ]");foo.get(3).add("w");foo.get(3).add("x");foo.get(3).add("y");foo.get(3).add("z");foo.get(3).add("x");
		
		writeCustomCommandsToFile(foo);
		
		
		ArrayList<ArrayList<String>> bar = readCustomCommandsFromFile();
		for(int i = 0; i < bar.size(); i++) {
			for(int j = 0; j < bar.get(i).size(); j++)
				System.out.print(bar.get(i).get(j)+ " ");
			System.out.println();
		}
		*/
		/*
		ArrayList<Task> list = new ArrayList<Task>();
		list.add(new Task("yolooooooo", null, null));
		list.add(new Task("bolocks", new DateTime("2004-12-13T21:39:45.618-08:00"), new DateTime("2050-12-13T21:39:45.618-08:00")));
		writeTasksToFile(list);
		
		
		ArrayList<Task> list = readTasksFromFile();
		for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i).toString());
		}
	}*/
}
