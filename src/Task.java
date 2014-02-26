import java.util.*;
import java.lang.*;

class Task implements Comparable<Task>{
	private Calendar start_time=Calendar.getInstance();
	private Calendar end_time=Calendar.getInstance();
	private String description;
	// for tasks with time and date range
	public Task(String description, int date, int month, int year, int start, int end) {
		this.start_time.set(year, month, date, start, 0);
		this.end_time.set(year, month, date, end, 0);
		this.description=description;
	}
	// for deadlines
	public Task(String description, int date, int month, int year, int start) {
		this.start_time.set(year, month, date, start, 0);
		this.description=description;
		this.end_time.set(0,0,0,0,0);
	}
	// for floating tasks
	public Task(String description) {
		this.description=description;		
	}
	public void setDescription(String description) {
		this.description=description;
	}
	public void setStartDate(int date) {
		this.start_time.set(Calendar.DAY_OF_MONTH, date);
	}
	public void setStartMonth(int month) {
		this.start_time.set(Calendar.MONTH, month);
	}
	public void setStartYear(int year) {
		this.start_time.set(Calendar.YEAR, year);
	}
	public void setStartHour(int hour) {
		this.start_time.set(Calendar.HOUR_OF_DAY, hour);
	}
	public void setStartMinutes(int minutes) {
		this.start_time.set(Calendar.MINUTE, minutes);
	}
	public void setEndDate(int date) {
		this.end_time.set(Calendar.DAY_OF_MONTH, date);
	}
	public void setEndMonth(int month) {
		this.end_time.set(Calendar.MONTH, month);
	}
	public void setEndYear(int year) {
		this.end_time.set(Calendar.YEAR, year);
	}
	public void setEndHour(int hour) {
		this.end_time.set(Calendar.HOUR_OF_DAY, hour);
	}
	public void setEndMinutes(int minutes) {
		this.start_time.set(Calendar.MINUTE, minutes);
	}
	public Calendar getStart() {
		return this.start_time;
	}
	public int getStartDate() {
		return this.getStart().get(Calendar.DAY_OF_MONTH);
	}
	public int getStartMonth() {
		return this.getStart().get(Calendar.MONTH);
	}
	public int getStartYear() {
		return this.getStart().get(Calendar.YEAR);
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
	public int getEndDate() {
		return this.getEnd().get(Calendar.DAY_OF_MONTH);
	}
	public int getEndMonth() {
		return this.getEnd().get(Calendar.MONTH);
	}
	public int getEndYear() {
		return this.getEnd().get(Calendar.YEAR);
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
		String result; 
		if (this.getStartDate()==0) {
			result=this.getDescription();
		}
		else {
			result= this.getDescription() + " || " + this.getStartDate() + "-" + this.getStartMonth() 
					+"-"+ this.getStartYear()+ " "+this.getStartHours() + " || " + this.getEndDate() + "-"
					+ this.getEndMonth()+ this.getEndYear() + " " + this.getEndHours();
		}
		return result;
	}
	public int compareTo(Task abc) {
		return this.start_time.compareTo(abc.start_time);
	}
}