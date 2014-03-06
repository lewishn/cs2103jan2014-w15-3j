class Action {
	private static final String MESSAGE_ADDED_TASK = "Added \"%s\" ";

	protected static void addTask(String taskDescription) {
		String[]taskInformation = MainParser.determineTask(taskDescription);
		// floatingTask, start Date, start time, end date, end time
		Task userTask = new task(taskDescription);		

		Printer.print(String.format(MESSAGE_ADDED_TASK, taskDescription));
	}
}