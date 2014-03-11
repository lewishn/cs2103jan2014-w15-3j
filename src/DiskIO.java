import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
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
	
	private static File taskFile;
	private static File customFile;
	private static FileWriter fileWriterTask;
	private static FileWriter fileWriterCustom;
	private static BufferedWriter bufferWriterTask;
	private static BufferedWriter bufferWriterCustom;
	private static FileReader fileReaderTask;
	private static FileReader fileReaderCustom;
	private static BufferedReader bufferReaderTask;
	private static BufferedReader bufferReaderCustom;

	protected static Boolean txtFilesDoesNotExist() {
		if(!taskFile.exists()) {
			return true;
		} else {
			return false;
		}
	}

	protected static void createFiles() throws IOException {
		taskFile= new File(FILE_TASKS);
		customFile= new File(FILE_CUSTOM);
	}

	protected static void initialiseIO() throws IOException{
		initialiseWriters();
		initialiseReaders();
	}

	private static void initialiseWriters() throws IOException {
		fileWriterTask= new FileWriter(taskFile);
		bufferWriterTask= new BufferedWriter(fileWriterTask);
		fileWriterCustom= new FileWriter(customFile);
		bufferWriterCustom= new BufferedWriter(fileWriterCustom);
	}

	private static void initialiseReaders() throws IOException {
		fileReaderTask= new FileReader(taskFile);
		bufferReaderTask= new BufferedReader(fileReaderTask);
		fileReaderCustom= new FileReader(customFile);
		bufferReaderCustom= new BufferedReader(fileReaderCustom);
	}

	protected static void closeWritersReaders() throws IOException {
		bufferWriterTask.close();
		bufferWriterCustom.close();
		bufferReaderTask.close();
		bufferReaderCustom.close();
	}

	protected static void writeTaskToFile(ArrayList<Task> list) {
		try {
			for (int i = 0; i < list.size(); i++) {
				bufferWriterTask.write(list.get(i).toString() + "\r\n");
			}
			bufferWriterTask.flush();
		} catch (IOException e) {
			Printer.print(WRITE_ERROR);
		}
	}
	
	protected static ArrayList<Task> readTaskFromFile() {
		try {
			String line;
			ArrayList<Task> list = new ArrayList<Task>();
			while ((line = bufferReaderTask.readLine()) != null) {
				list.add(Task.parseTaskFromString(line));
			}
			return list;
		} catch (IOException e) {
			Printer.print(READ_ERROR);
			return null;
		}
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
