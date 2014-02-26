import java.util.*;
import java.lang.*;

public class FloatingTask implements Comparable<FloatingTask>{
	private Calendar input_time=Calendar.getInstance();
	private String description;
	
	public FloatingTask(String description) {
		this.description=description;
	}
	public String getDescription() {
		return this.description;
	}
	public void setDescription(String description) {
		this.description=description;
	}
	public int compareTo(FloatingTask abc) {
		return this.input_time.compareTo(abc.input_time);
	}
}
