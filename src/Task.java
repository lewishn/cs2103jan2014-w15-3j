import java.util.*;
import java.lang.*;

class Task implements Comparable<Task>{
	private Calendar start_time=Calendar.getInstance();
	private Calendar end_time=Calendar.getInstance();
	private String description;
	
	public Task(String description, int date, int month, int year, int start, int end) {
		this.start_time.set(year, month, date, start, start);
		this.end_time.set(year, month, date, end, end);
		this.description=description;
	}
	public Task(String description, int date, int month, int year, int start) {
		this.start_time.set(year, month, date, start, start);
		this.description=description;
		this.end_time.clear();
	}
	public Task(String description) {
		this.description=description;
	}

	public Calendar getStart() {
		return this.start_time;
	}
	public Integer getStartDate() {
		return this.getStart().get(Calendar.DAY_OF_MONTH);
	}
	public int getStartDay() {
		return this.getStart().get(Calendar.DAY_OF_WEEK);
	}
	public int getStartHours() {
		return this.getStart().get(Calendar.HOUR_OF_DAY);
	}
	public Calendar getEnd() {
		return this.end_time;
	}
	public Integer getEndDate() {
		return this.getEnd().get(Calendar.DAY_OF_MONTH);
	}
	public int getEndDay() {
		return this.getEnd().get(Calendar.DAY_OF_WEEK);
	}
	public int getEndHours() {
		return this.getEnd().get(Calendar.HOUR_OF_DAY);
	}
	public String getDescription() {
		return this.description;
	}
	public String toString() {
        String result = this.getDescription() + " || " + this.getDate() + " || " + this.getHours() + " || " + this.getHours();
        return result;
	}
	public int compareTo(Task abc) {
		return this.start_time.compareTo(abc.start_time);
	}
}