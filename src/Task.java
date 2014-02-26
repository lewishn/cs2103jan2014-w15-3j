import java.util.*;
import java.lang.*;

class Task {
	private String title;
	private Calendar start_time=Calendar.getInstance();
	private Calendar end_time=Calendar.getInstance();
	private String description;
	
	public void Task(String title, int date, int month, int start, int end, String description) {
		this.title=title;
		this.start_time.set(2014, month, date, start, start);
		this.end_time.set(2014, month, date, end, end);
		this.description=description;
	}
	
	public String getTitle() {
		return this.title;
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
        String result = this.getTitle() + " || " + this.getDate() + " || " + this.getHours() + " || " + this.getHours();
        return result;
	}
}