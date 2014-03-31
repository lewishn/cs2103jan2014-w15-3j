import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Scanner;

public class DoThings {
	private static final String MESSAGE_STARTUP = "Get ready to Do Things!";
	private static final String MESSAGE_COMMAND = "Please enter a command: ";
	
	private static Scanner scanUserInput;
	
	private static void displayFeedback(String str) {
		System.out.print(str);
	}
	
	private static void displayFeedbackLn(String str) {
		System.out.println(str);
	}
	
	private boolean readCommand() {
		displayFeedback(MESSAGE_COMMAND);
		String userInput = scanUserInput.nextLine();
		Feedback feed = MainLogic.runLogic(userInput);
		displayFeedbackLn(feed.toString());
		
		return feed.getExitFlag();
	}
	
	public void run() {
		scanUserInput = new Scanner(System.in);
		System.out.println(MESSAGE_STARTUP);
		
		while (true) {
			boolean feedback = readCommand();
			System.out.println(FileManager.filepath);
			if (feedback == true) {
				System.exit(0);
			}
		}
	}
	
	public static void main(String[] args) {
		DoThings program = new DoThings();
		program.run();
	}
}