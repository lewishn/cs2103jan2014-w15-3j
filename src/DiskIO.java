import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

class DiskIO {
	private static final int COMMAND_LIST_SIZE = 10;
	
	private static final String FILE_TASKS = "tasks.txt";
	private static final String FILE_CUSTOM = "custom.txt";
	
	private static final String WRITE_ERROR = "Cannot write to file!";
	private static final String READ_ERROR = "Cannot read from file!";
	
	private static File taskFile= new File(FILE_TASKS);
	private static File customFile= new File(FILE_CUSTOM);
	private static FileWriter fileWriterTask;
	private static FileWriter fileWriterCustom;
	private static BufferedWriter bufferWriterTask;
	private static BufferedWriter bufferWriterCustom;
	private static FileReader fileReaderTask;
	private static FileReader fileReaderCustom;
	private static BufferedReader bufferReaderTask;
	private static BufferedReader bufferReaderCustom;

	protected static void doesFilesExists() {
		if(!taskFile.exists()) {
			try{
				taskFile.createNewFile();
			} catch(IOException e) {
				e.printStackTrace();
			}
		} 
		if(!customFile.exists()) {
			try{
				customFile.createNewFile();
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
	}
	protected static void firstTime() {
		try{
			initialiseIO();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	private static void initialiseIO() throws IOException{
		fileWriterTask= new FileWriter(taskFile);
		bufferWriterTask= new BufferedWriter(fileWriterTask);
		fileWriterCustom= new FileWriter(customFile);
		bufferWriterCustom= new BufferedWriter(fileWriterCustom);
		fileReaderTask= new FileReader(taskFile);
		bufferReaderTask= new BufferedReader(fileReaderTask);
		fileReaderCustom= new FileReader(customFile);
		bufferReaderCustom= new BufferedReader(fileReaderCustom);
	}
	protected static void closeWritersReaders() throws IOException {
		closeWriters();
		closeReaders();
	}
	private static void closeWriters() throws IOException {
		bufferWriterTask.close();
		bufferWriterCustom.close();
	}
	private static void closeReaders() throws IOException {
		bufferReaderTask.close();
		bufferReaderCustom.close();
	}

	protected static void writeTaskToFile(ArrayList<Task> list) {
		try {

			for (int i = 0; i < list.size(); i++) {
				bufferWriterTask.write(list.get(i).toString() + "\r\n");
				bufferWriterTask.flush();
			}
		} catch (IOException e) {
			Printer.print(WRITE_ERROR);
		}
	}
	
	protected static ArrayList<Task> readTaskFromFile() {
		ArrayList<Task> list = new ArrayList<Task>();
		try {
			String line= bufferReaderTask.readLine();
			while (line!=null) {
				list.add(Task.parseTaskFromString(line));
				line=bufferReaderTask.readLine();
			}
		} catch (IOException e) {
			Printer.print(READ_ERROR);
			list= null;
		}
		return list;
	}	

	protected static void writeCustomCommands(ArrayList<ArrayList<String>> list) {
		try {
			for (int i = 0; i < list.size(); i++) {
				for (int j = 0; j < list.get(i).size(); j++) {
					bufferWriterCustom.write(list.get(i).get(j) + " ");
				}
				bufferWriterCustom.write("\r\n");
			}
			bufferWriterCustom.flush();
		} catch (IOException e) {
			Printer.print(WRITE_ERROR);
		}
	}
	
	protected static ArrayList<ArrayList<String>> readCustomCommands() {
		ArrayList<ArrayList<String>> list = new ArrayList<ArrayList<String>>();
		
		try {
			String line;
			for (int i = 0; i < COMMAND_LIST_SIZE; i++) {
				line = bufferReaderCustom.readLine();
				list.add(new ArrayList<String>(Arrays.asList(line.split(" "))));
			}
			bufferReaderCustom.close();
		} catch (NullPointerException e) {
			for (int i = 0; i < COMMAND_LIST_SIZE; i++) {
				list.add(new ArrayList<String>());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}
}
