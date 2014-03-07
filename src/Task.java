import org.joda.time.DateTime;

class Task implements Comparable<Task>{
	private static final String DELIMITER = " ||| ";
	private static final String NULL_START = "no_start_time";
	private static final String NULL_END = "no_end_time";
	
	private DateTime startDateTime;
	private DateTime endDateTime;
	private String description;
	
	// For tasks with date ranges
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
	
	@Override
	public String toString() {
		String start = (startDateTime == null) ? NULL_START : startDateTime.toString();
		String end = (endDateTime == null) ? NULL_END : endDateTime.toString();
		
		return start + DELIMITER + end + DELIMITER + description;
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
	
	/*
	protected void setStartDate(int date) {
		this.startDateTime.set(DateTime.DAY_OF_MONTH, date);
	}
	protected void setStartMonth(int month) {
		this.startDateTime.set(DateTime.MONTH, month);
	}
	protected void setStartYear(int year) {
		this.startDateTime.set(DateTime.YEAR, year);
	}
	protected void setStartHour(int hour) {
		this.startDateTime.set(DateTime.HOUR_OF_DAY, hour);
	}
	protected void setStartMinutes(int minutes) {
		this.startDateTime.set(DateTime.MINUTE, minutes);
	}
	protected void setEndDate(int date) {
		this.endDateTime.set(DateTime.DAY_OF_MONTH, date);
	}
	protected void setEndMonth(int month) {
		this.endDateTime.set(DateTime.MONTH, month);
	}
	protected void setEndYear(int year) {
		this.endDateTime.set(DateTime.YEAR, year);
	}
	protected void setEndHour(int hour) {
		this.endDateTime.set(DateTime.HOUR_OF_DAY, hour);
	}
	protected void setEndMinutes(int minutes) {
		this.startDateTime.set(DateTime.MINUTE, minutes);
	}
	
	protected DateTime getStart() {
		return this.startDateTime;
	}
	protected int getStartDate() {
		return this.getStart().get(DateTime.DAY_OF_MONTH);
	}
	protected int getStartMonth() {
		return this.getStart().get(DateTime.MONTH);
	}
	protected int getStartYear() {
		return this.getStart().get(DateTime.YEAR);
	}
	protected int getStartDay() {
		return this.getStart().get(DateTime.DAY_OF_WEEK);
	}
	protected int getStartHours() {
		return this.getStart().get(DateTime.HOUR_OF_DAY);
	}
	protected DateTime getEnd() {
		return this.endDateTime;
	}
	protected int getEndDate() {
		return this.getEnd().get(DateTime.DAY_OF_MONTH);
	}
	protected int getEndMonth() {
		return this.getEnd().get(DateTime.MONTH);
	}
	protected int getEndYear() {
		return this.getEnd().get(DateTime.YEAR);
	}
	protected int getEndDay() {
		return this.getEnd().get(DateTime.DAY_OF_WEEK);
	}
	protected int getEndHours() {
		return this.getEnd().get(DateTime.HOUR_OF_DAY);
	}
	protected String getDescription() {
		return this.description;
	}
	*/
}