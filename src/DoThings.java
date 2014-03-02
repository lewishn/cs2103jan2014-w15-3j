import java.util.*;

public class DoThings {
	private static final String MESSAGE_COMMAND = "Please enter a command: ";
	private static boolean exit;
	private static Scanner userInput;
	private DoThings() {
		exit = false;
		userInput = new Scanner(System.in);
	}
	
	protected static void printFeedback(String message) {
		System.out.print(message);
	}
	
	protected static void printFeedbackLn(String message) {
		System.out.println(message);
	}

	private static void readCommand() {
		printFeedback(MESSAGE_COMMAND);
		String input = userInput.nextLine();
		
		exit = MainParser.parse(input);
	}

	public static void main() throws Exception {
		DoThings program = new DoThings();
		program.run();
	}
	
	public void run() throws Exception {
		System.out.println("Get ready to Do Things!");
		while(exit == false) {
			readCommand();
		}
	}
}