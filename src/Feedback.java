import java.util.ArrayList;


class Feedback {
	private String description;
	private ArrayList<Integer> indexList;
	private boolean exit;
	private boolean error;
	
	public Feedback(String desc) {
		description = desc;
		error = false;
		exit = false;
		indexList = TaskHandler.getListOfTaskWithStatus(false);
	}
	
	public Feedback(String desc, boolean isError) {
		description = desc;
		error = isError;
		exit = false;
		indexList = TaskHandler.getListOfTaskWithStatus(false);
	}
	
	public Feedback(String desc, boolean isError, boolean isExit) {
		description = desc;
		error = isError;
		exit = isExit;
		indexList = null;
	}
	
	public Feedback(String desc, ArrayList<Integer> list) {
		description = desc;
		error = false;
		exit = false;
		indexList = list;
	}
	
	public String getDesc() {
		return this.description;
	}
	
	public boolean getExitFlag() {
		return exit;
	}
	
	public ArrayList<Integer> getIndexList() {
		return indexList;
	}
	
	public boolean getErrorFlag() {
		return error;
	}
	
	public void setDescription(String desc) {
		description = desc;
	}
	
	@Override
	public String toString(){
		return description;
	}
	
	public boolean equals(Feedback other){
		if (!description.equals(other.getDesc())) {
			return false;
		}
		if (exit != other.getExitFlag()) {
			return false;
		}
		if (error != other.getErrorFlag()) {
			return false;
		}
		if (indexList.equals(other.getIndexList())) {
			return false;
		}
		
		return true;
		
	}
}
