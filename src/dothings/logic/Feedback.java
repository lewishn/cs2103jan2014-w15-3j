package dothings.logic;

import java.util.ArrayList;


class Feedback {
	private String description;
	private ArrayList<Integer> indexList;
	private boolean exit;
	private boolean error;
	
	/**
	 * Constructs and initializes a feedback object with the specified description.
	 * @param desc the description of the new feedback object.
	 */
	protected Feedback(String desc) {
		description = desc;
		error = false;
		exit = false;
		indexList = TaskHandler.getListOfTaskWithStatus(false);
	}
	/**
	 * Constructs and initializes a feedback object with an error status and a description of the error.
	 * @param desc the description of the new feedback object.
	 * @param isError true if the feedback object is an error message; false otherwise.
	 */
	protected Feedback(String desc, boolean isError) {
		description = desc;
		error = isError;
		exit = false;
		indexList = TaskHandler.getListOfTaskWithStatus(false);
	}
	/**
	 * Constructs and initializes a feedback object with an error status, exit flag, and its description.
	 * @param desc the description of the new feedback object.
	 * @param isError true if the feedback object is an error message; false otherwise.
	 * @param isExit true if the feedback object is an exit message; false otherwise.
	 */
	protected Feedback(String desc, boolean isError, boolean isExit) {
		description = desc;
		error = isError;
		exit = isExit;
		indexList = null;
	}
	/**
	 * Constructs and initializes a feedback object with a list of tasks to be displayed.
	 * @param desc the description of the new feedback object.
	 * @param list the index list of tasks to be displayed.
	 */
	protected Feedback(String desc, ArrayList<Integer> list) {
		description = desc;
		error = false;
		exit = false;
		indexList = list;
	}
	/**
	 * Returns the description of this feedback object.
	 * @return the description of this feedback object.
	 */
	protected String getDesc() {
		return description;
	}
	/**
	 * Returns the exit flag of this feedback object.
	 * @return the exit flag of this feedback object.
	 */
	protected boolean getExitFlag() {
		return exit;
	}
	/**
	 * Returns the index list of this feedback object.
	 * @return the index list of this feedback object.
	 */
	protected ArrayList<Integer> getIndexList() {
		return indexList;
	}
	/**
	 * Returns the error flag of this feedback object.
	 * @return the error flag of this feedback object.
	 */
	protected boolean getErrorFlag() {
		return error;
	}
	/**
	 * Sets the description of this task to the specified description.
	 * @param desc the specified description.
	 */
	protected void setDescription(String desc) {
		description = desc;
	}
	
	@Override
	/**
	 * Returns the string representation of this feedback object.
	 * @return the description of this feedback object.
	 */
	public String toString(){
		return description;
	}
	/**
	 * Checks whether this feedback object is the same as the specified feedback object.
	 * @param other the feedback object to be compared.
	 * @return true if this feedback object is the same as the specified feedback object.
	 */
	protected boolean equals(Feedback other){
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
