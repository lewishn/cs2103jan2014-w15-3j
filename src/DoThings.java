public class DoThings {
	protected static Feedback readCommand(String userInput) {
		Feedback feed = MainLogic.runLogic(userInput);
		return feed;
	}
}