package dothings.storage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import dothings.gui.DoThingsGUI;

//@author A0099727J
public class FileManager {
	private static final String ENCODING_UTF8 = "UTF8";
	private static final String LOG_FILE = "dothings.log";
	private static final String MESSAGE_ERROR_LOGGER_READ = "Error reading from file ";
	private static final String MESSAGE_ERROR_LOGGER_WRITE = "Error writing to file ";
	private static final String MESSAGE_ERROR_ENCODING = "Unsupported file encoding";
	@SuppressWarnings("deprecation")
	public static final String FILEPATH = new File(
	        URLDecoder.decode(DoThingsGUI.class.getProtectionDomain()
	                .getCodeSource().getLocation().getPath())).getParent()
	        + System.getProperty("file.separator");
	
	private static Logger logger = Logger
	        .getLogger(FileManager.class.getName());
	
	/**
	 * Get a new buffered reader with UTF8 encoding
	 * 
	 * @param fileName
	 * @return buffered reader
	 * @throws FileNotFoundException
	 */
	private static BufferedReader getReader(String fileName)
	        throws FileNotFoundException {
		File file = new File(fileName);
		BufferedReader bw;
		try {
			bw = new BufferedReader(new InputStreamReader(new FileInputStream(
			        file), ENCODING_UTF8));
			return bw;
		} catch (UnsupportedEncodingException e) {
			log(MESSAGE_ERROR_ENCODING);
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * Get a new buffered writer with UTF8 encoding
	 * 
	 * @param fileName
	 * @return buffered writer
	 * @throws FileNotFoundException
	 */
	private static BufferedWriter getWriter(String fileName) throws IOException {
		File file = new File(fileName);
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
		        new FileOutputStream(file), "UTF8"));
		return bw;
	}
	
	/**
	 * Appends any log messages to the log file
	 * 
	 * @param message
	 */
	public static void log(String message) {
		FileHandler fh;
		try {
			fh = new FileHandler(FILEPATH + LOG_FILE, true);
			logger.addHandler(fh);
			SimpleFormatter formatter = new SimpleFormatter();
			fh.setFormatter(formatter);
			logger.info(message);
			fh.close();
			
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Read lines from a text file
	 * 
	 * @param fileName
	 * @return ArrayList of String with each line as an entry
	 */
	public static ArrayList<String> readFromFile(String fileName) {
		ArrayList<String> list = new ArrayList<String>();
		
		try {
			BufferedReader reader = getReader(FILEPATH + fileName);
			String line;
			while ((line = reader.readLine()) != null) {
				list.add(line);
			}
		} catch (IOException e) {
			log(MESSAGE_ERROR_LOGGER_READ + fileName);
		}
		
		return list;
	}
	
	/**
	 * Saves ArrayList of String to a file
	 * 
	 * @param fileName
	 * @param list
	 */
	public static void writeToFile(String fileName, ArrayList<String> list) {
		try {
			BufferedWriter writer = getWriter(FILEPATH + fileName);
			for (int i = 0; i < list.size(); i++) {
				writer.write(list.get(i) + System.getProperty("line.separator"));
			}
			writer.flush();
			writer.close();
		} catch (IOException e) {
			log(MESSAGE_ERROR_LOGGER_WRITE + fileName);
			e.printStackTrace();
		} finally {
			
		}
	}
}
