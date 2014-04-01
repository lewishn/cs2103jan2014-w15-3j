public class DoThings {
	protected static String readCommand(String userInput) {
		Feedback feed = MainLogic.runLogic(userInput);
		return feed.toString();
	}
}