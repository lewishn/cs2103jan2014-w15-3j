import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


class DiskIO {

	protected static void writeToFile(ArrayList<Task> list, String fileName) {
		try {
			File file = new File(fileName);
			BufferedWriter bw = new BufferedWriter(new FileWriter(file));
			for(int i = 0; i < list.size(); i++) {
				bw.write(list.get(i).toString());
				bw.close();
			}
		} catch (IOException e) {
			System.out.println("Cannot write to file!");
		}
	}
	
	protected static ArrayList<Task> readTaskFromFile(String fileName) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(fileName));
			String line;
			ArrayList<Task> list = new ArrayList<Task>();
			while ((line = br.readLine()) != null) {
				// to be implemented
			}
			return list;
		} catch (IOException e) {
			System.out.println("Cannot read from file!");
			return null;
		}
	}	
}
