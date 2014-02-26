import java.util.*

public class Parser {
	private String input;
	private String command;
	private ArrayList<String> parsedInput;

	public Parser(String input) {
		this.input = input;
	}
	
	enum CommandType {
		ADD, DELETE, DISPLAY, CLEAR, SORT, SEARCH, EXIT, INVALID;
	}


	public static void main(String[] args) {
		checkArguments(args.length);
		checkFile(args[0]);
		showToUser(String.format(MESSAGE_WELCOME, FILE_NAME));
		while(true) {
			String userCommand = readUserCommand();
			String feedback = executeCommand(userCommand);
			showToUser(feedback);
			saveFile();
		}
	}

	private static void parseCommands() {
		parsedInput = new ArrayList<String>(Arrays.asList(input.split(" ", 2)));
		command = parsedInput.get(0); 
	}
}
