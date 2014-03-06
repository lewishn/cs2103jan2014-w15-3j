import java.util.*;

public class DoThings {
	private static final String MESSAGE_STARTUP = "Get ready to Do Things!";
	private static final String MESSAGE_COMMAND = "Please enter a command: ";
	
	private static boolean exit;
	private static Scanner scanner;
	
	private DoThings() {
		exit = false;
		scanner = new Scanner(System.in);
	}

	private static void readCommand() {
		Printer.printNoLine(MESSAGE_COMMAND);
		String input = scanner.nextLine();
		exit = Logic.firstStep(input);
	}

	public static void main(String[] args) throws Exception {
		DoThings program = new DoThings();
		program.run();
	}
	
	public void run() throws Exception {
		Printer.print(MESSAGE_STARTUP);
		while (exit == false) {
			readCommand();
		}
	}
}