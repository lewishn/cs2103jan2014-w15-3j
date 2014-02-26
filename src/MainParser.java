import java.util.Arrays;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Scanner;



class MainParser {
	
	private enum CommandType{
		CREATE, READ, UPDATE, DELETE, SEARCH, EXIT;
	}
	
	private static CommandType getCommandType(String com){
		if ( com.equalsIgnoreCase("\\add") || com.equalsIgnoreCase("\\a") ) {
			return CommandType.CREATE;
		} else if ( com.equalsIgnoreCase("\\list") || com.equalsIgnoreCase("\\l") ) {
			return CommandType.READ;
		} else if ( com.equalsIgnoreCase("\\update") || com.equalsIgnoreCase("\\u") ) {
			return CommandType.UPDATE;
		} else if ( com.equalsIgnoreCase("\\delete") || com.equalsIgnoreCase("\\d") ) {
			return CommandType.DELETE;
		} else {
			return CommandType.CREATE;
		} 
	}
	
	public static void main(String args[]){
		Scanner sc = new Scanner(System.in);
		while (true) {
			DateTimeParse.parseDate(sc.nextLine());
		}
	}
	
	public static void parse(String code) {
		ArrayList<String> tokens = new ArrayList<String>(Arrays.asList(code.split(" ")));
		String command = tokens.get(0);
		CommandType commandType = getCommandType(tokens.get(0));
		
		switch (commandType) {
			case CREATE:
				System.out.println("ADD");
				break;
			case READ:
				System.out.println("LIST");
				listTasks(tokens);
				break;
			case UPDATE:
				System.out.println("UPDATE");
				break;
			case DELETE:
				System.out.println("DELETE");
				deleteTasks(tokens);
				break;
			default:
				System.out.println("SEARCH");
				// execute search
		}
	}
	
	private static void addTasks(ArrayList<String> tokens) {
		Date startDate;
		Date endDate;
		
		
		tokens.remove(0);
		
		//deadline
		int index = tokens.indexOf("by");
		if (index >= 0) {
			if(DateTimeParse.isDate(tokens.get(index))){
				//date = 
			}
		}
		
	}

	private static void listTasks(ArrayList<String> tokens) {
		tokens.remove(0);
		if (tokens.size() == 0) {
			//call display()
		} else {
			//parse date
			//call display(date)
		}
	}
	
	public static void deleteTasks(ArrayList<String> tokens) {
		tokens.remove(0);
		for (int i = 0; i < tokens.size(); i++) {
			if (isInteger(tokens.get(i))) {
				//call delete
			} else {
				//error?
			}
		}
	}
	
	public static boolean isInteger(String str) {
		try {
			Integer.parseInt(str);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
}
