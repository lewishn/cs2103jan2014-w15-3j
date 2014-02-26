import java.util.Arrays;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Scanner;



class MainParser {
	private static final String DEFAULT_ADD = "\\add";
	private static final String DEFAULT_ADD2 = "\\a";
	private static final String DEFAULT_UPDATE = "\\update";
	private static final String DEFAULT_UPDATE2 = "\\u";
	private static final String DEFAULT_DELETE = "\\delete";
	private static final String DEFAULT_DELETE2 = "\\d";
	private static final String DEFAULT_LIST = "\\list";
	private static final String DEFAULT_LIST2 = "\\l";
	private static final String DEFAULT_UNDO = "\\undo";
	private static final String DEFAULT_UNDO2 = "\\un";
	private static final String DEFAULT_CUSTOM = "\\custom";
	private static final String DEFAULT_CUSTOM2 = "\\cc";
	private static final String DEFAULT_HELP = "\\help";
	private static final String DEFAULT_HELP2 = "\\h";

	private enum CommandType {
		ADD, DELETE, UPDATE, LIST, UNDO, CUSTOM, HELP;
	}

	private static CommandType getCommandType(String com) {
		ArrayList<ArrayList<String>> customCommand = DiskIO.readCustomCommands();
		
		if( com.equals(DEFAULT_ADD) || com.equals(DEFAULT_ADD2) || customCommad.get(0).contains(com)) {
			return CommandType.CREATE;
		} else if( com.equals(DEFAULT_LIST) || com.equals(DEFAULT_LIST2) || customCommad.get(1).contains(com)) {
			return CommandType.LIST;
		} else if( com.equals(DEFAULT_UPDATE) || com.equals(DEFAULT_UPDATE2) || customCommad.get(2).contains(com)) {
			return CommandType.UPDATE;
		} else if( com.equals(DEFAULT_DELETE) || com.equals(DEFAULT_DELETE2) || customCommad.get(3).contains(com)) {
			return CommandType.DELETE;
		} else if( com.equals(DEFAULT_UNDO) || com.equals(DEFAULT_UNDO2) || customCommad.get(4).contains(com)) {
			return CommandType.UNDO;
		} else if( com.equals(DEFAULT_CUSTOM) || com.equals(DEFAULT_CUSTOM2) || customCommad.get(5).contains(com)) {
			return CommandType.CUSTOM;
		} else if( com.equals(DEFAULT_HELP) || com.equals(DEFAULT_HELP2) || customCommad.get(6).contains(com)) {
			return CommandType.HELP;
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
