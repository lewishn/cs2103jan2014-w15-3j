import java.util.*

public class DoThings {
	private static boolean exit;
	private static Scanner sc;

	private DoThings() {
		exit=false;
		sc = new Scanner(System.in);
	}
	
	protected static void printFeedback(String message) {
		System.out.println(message);
	}

	private static void readCommand() {
		System.out.print("Command: ");
		String input=sc.nextLine();
		String capInput=input.toUpperCase();
		if(capInput.equals("EXIT")) {
			exit=true;
		} else {
			Parser ultimate = new Parser(input);
			ultimate.parseCommands();
		}
	}

	public static void main() {
		DoThings program = new DoThings();
		program.run();
	}
	public void run() throws Exception {
		System.out.println("Get ready to Do Things!");
		while(exit==false) {
			readCommand();
		}
	}
}