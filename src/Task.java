import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.joda.time.DateTime;

class Task implements Comparable<Task>{
	private static final String FILE_TASK = "tasks.txt";
	
	protected static final int START_DATE = 0;
	protected static final int START_TIME = 1;
	protected static final int END_DATE = 2;
	protected static final int END_TIME = 3;
	protected static final int TASK_FIELD_SIZE = 4;
	
	private static final String DELIMITER = " ~~ ";
	private static final String TAG_IDENTIFIER = "#";
	private static final String NULL_START = "no_start_time";
	private static final String NULL_END = "no_end_time";
	private static final String NULL_ALIAS = "no_alias";
	
	private static ArrayList<Task> taskList = loadTasks();
	
	private DateTime startDateTime;
	private DateTime endDateTime;
	private String description;
	private boolean status;
	private String alias;
	
	protected Task(String desc) {
		description = desc;
		status = false;
	}
	protected Task(String desc, DateTime start, DateTime end, String name) {
		startDateTime = start;
		endDateTime = end;
		status = false;
		description = desc;
		alias = name;
	}
	
	protected void setDescription(String desc) {
		description = desc;
	}
	
	protected void setStartDateTime(DateTime start) {
		startDateTime = start;
	}
	
	protected void setEndDateTime(DateTime end) {
		endDateTime = end; 
	}
	
	protected void setStatus(boolean stat) {
		status = stat;
	}
	
	protected void setAlias(String alias) {	
		this.alias = alias;
	}
	
	protected String getDescription() {
		return description;
	}
	
	protected DateTime getStartDateTime() {
		return startDateTime;
	}
	
	protected DateTime getEndDateTime() {
		return endDateTime;
	}
	
	protected boolean getStatus() {
		return status;
	}
	
	protected String getAlias() {
		return alias;
	}
	
	protected static ArrayList<Task> getList() {
		return taskList;
	}
	
	protected static void setList(ArrayList<Task> list) {
		taskList = list;
	}
	
	protected static void sortList() {
		Collections.sort(taskList);
	}	
	
	protected static Task parseTaskFromString(String line) {
		String[] tokens = line.split(DELIMITER);
		DateTime start = (tokens[0].equals(NULL_START)) ? null : new DateTime(tokens[0]);
		DateTime end = (tokens[1].equals(NULL_END)) ? null : new DateTime(tokens[1]);
		String name = (tokens[2].equals(NULL_ALIAS)) ? null : tokens[2]; 
		String desc = tokens[3];
		
		return new Task(desc, start, end, name);
	}
	
	@Override
	public String toString() {
		String start = (startDateTime == null) ? NULL_START : startDateTime.toString();
		String end = (endDateTime == null) ? NULL_END : endDateTime.toString();
		String taskAlias = (alias == null) ? NULL_ALIAS : alias;
		
		return start + DELIMITER + end + DELIMITER + taskAlias + DELIMITER + description;
	}
	
	public String toDisplayString() {
		String display = "";
		
		if (startDateTime != null) {
			display += getDateString(startDateTime);
		}
		if (endDateTime != null) {
			display += getDateString(endDateTime);
		}
		if (alias != null) {
			display += "[alias:" + alias + "]";
		}
		
		return display + " " + description;
	}
	
	private String getDateString(DateTime date) {
		DecimalFormat df = new DecimalFormat("00");
		
		String time = df.format(date.getHourOfDay()) + ":" + df.format(date.getMinuteOfHour());
		String dt = date.toString("dd/MMM/YYYY");
		
		return "[" + time + "|" + dt + "]";
	}
	
	@Override
	public int compareTo(Task task) {
		if (this.startDateTime != null && task.startDateTime != null) {
			return this.startDateTime.compareTo(task.startDateTime);
			
		} else if (this.startDateTime == null && task.startDateTime != null) {
			if(this.endDateTime != null) {
				return this.endDateTime.compareTo(task.startDateTime);
			}
			return 1;
			
		} else if (this.startDateTime != null && task.startDateTime == null) {
			if(task.endDateTime != null) {
				return this.startDateTime.compareTo(task.endDateTime);
			}
			return -1;
			
		} else {
			if (this.endDateTime == null && task.endDateTime == null) {
				return this.description.compareToIgnoreCase(task.description);
			} else if (this.endDateTime != null && task.endDateTime == null) {
				return -1;
			} else if (this.endDateTime == null && task.endDateTime != null) {
				return 1;
			} else {
				return this.endDateTime.compareTo(task.endDateTime);
			}
		}
	}
	
	protected static boolean isAliasValid(String alias) {
		if (alias == null || alias.length() == 0 || alias.equals("")) {
			return false;
		}
		
		if (getTaskIndexFromAlias(alias) < 0) {
			return false;
		}
		if (alias.equalsIgnoreCase("completed") || alias.equalsIgnoreCase("all")) {
			return false;
		}
		
		
		return true;
	}
	
	protected static int getTaskIndexFromAlias(String alias) {
		for (int i = 0; i < taskList.size(); i++) {
			if (taskList.get(i).getAlias() == null) {
				continue;
			}
			if (alias.equals(taskList.get(i).getAlias())) {
				return i;
			}
		}
		
		return -1;
	}
	
	protected static void saveTasks() {
		ArrayList<String> listToSave = new ArrayList<String>();
		for (int i = 0; i < taskList.size(); i++) {
			String taskStringForm = taskList.get(i).toString();
			listToSave.add(taskStringForm);
		}
		
		FileManager.writeToFile(FILE_TASK, listToSave);
	}
	
	protected static ArrayList<Task> loadTasks() {
		ArrayList<Task> listOfTasks = new ArrayList<Task>();
		ArrayList<String> list = FileManager.readFromFile(FILE_TASK);
		
		for (int i = 0; i < list.size(); i++) {
			String line = list.get(i);
			listOfTasks.add(parseTaskFromString(line));
		}
		
		return listOfTasks;
	}
}