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
	
	protected static ArrayList<String> readFromFile(String fileName) {
		ArrayList<String> list = new ArrayList<String>();
		
		try {
			BufferedReader reader = getReader(fileName);
			String line;
			while ((line = reader.readLine()) != null) {
				list.add(line);
			}
		} catch (IOException e) {
			
		}
		
		return list;
	}
	
	protected static void writeToFile(String fileName, ArrayList<String> list) {
		try {
			BufferedWriter writer = getWriter(fileName);
			for(int i = 0; i < list.size(); i++) {
				writer.write(list.get(i) + System.getProperty("line.separator"));
			}
			writer.flush();
			writer.close();
		} catch (IOException e) {
			
		}
	}
}
