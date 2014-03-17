import java.util.ArrayList;
import java.util.Collections;

import org.joda.time.DateTime;

class Task implements Comparable<Task>{
	private static final String FILE_TASK = "tasks.txt";
	
	protected static final int START_DATE = 0;
	protected static final int START_TIME = 1;
	protected static final int END_DATE = 2;
	protected static final int END_TIME = 3;
	protected static final int TASK_FIELD_SIZE = 4;
	
	private static final String DELIMITER = " ~~ ";
	private static final String NULL_START = "no_start_time";
	private static final String NULL_END = "no_end_time";
	
	private static ArrayList<Task> taskList = loadTasks();
	
	private DateTime startDateTime;
	private DateTime endDateTime;
	private String description;
	private boolean completed;
	
	protected Task(String desc) {
		description = desc;
	}
	protected Task(String desc, DateTime start, DateTime end) {
		startDateTime = start;
		endDateTime = end;
		description = desc;
	}
	
	protected static Task parseTaskFromString(String line) {
		String[] tokens = line.split(DELIMITER);
		DateTime start = (tokens[0].equals(NULL_START)) ? null : new DateTime(tokens[0]);
		DateTime end = (tokens[1].equals(NULL_END)) ? null : new DateTime(tokens[1]);
		String desc = tokens[2];
		
		return new Task(desc, start, end);
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
	
	protected String getDescription() {
		return description;
	}
	
	protected DateTime getStartDateTime() {
		return startDateTime;
	}
	
	protected DateTime getEndDateTime() {
		return endDateTime;
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
	
	@Override
	public String toString() {
		String start = (startDateTime == null) ? NULL_START : startDateTime.toString();
		String end = (endDateTime == null) ? NULL_END : endDateTime.toString();
		
		return start + DELIMITER + end + DELIMITER + description;
	}
	
	public String toDisplayString() {
		String start, end;
		String display = "";
		
		if (startDateTime != null) {
			display += getDateString(startDateTime);
		}
		if (endDateTime != null) {
			display += getDateString(startDateTime);
		}
		
		return display + " " + description;
	}
	
	private String getDateString(DateTime date) {
		String time = date.getHourOfDay() + ":" + date.getMinuteOfHour();
		String dt = date.getDayOfMonth() + "/" + date.getMonthOfYear() + "/" + date.getYear();
		
		return "[" + time + "|" + dt + "]";
	}
	
	@Override
	public int compareTo(Task task) {
		if (this.startDateTime != null && task.startDateTime != null) {
			return this.startDateTime.compareTo(task.startDateTime);
		} else if (this.startDateTime == null && task.startDateTime != null) {
			return -1;
		} else if (this.startDateTime != null && task.startDateTime == null) {
			return 1;
		} else {
			return this.description.compareTo(task.description);
		}
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