package chat_bot;


public class ChatBot {
	private Api api;
	private boolean run = true;

	public static void main(String[] args) {
		var console = new Console();
		new ChatBot(console);
	}
	
	public ChatBot(Api api) {
		this.api = api;
		start();
	}
	
	public void start() {
		var message = "Привет, меня зовут Гена."
				+ " Я чат бот, который умеет играть в города."
				+ " Если хочешь играть в города, введи команду \"Играть\"";
		say(message);
		while (run) {
			var title = getInput();
			if (title.toLowerCase().compareTo("играть") == 0) {
<<<<<<< HEAD:Chat-Bot-master/src/main/java/chat_bot/ChatBot.java
				StartCityGame();
=======
				startCityGame();
>>>>>>> 544a96c84182b64c7d1b13c642b1bb990bdb7684:Chat-Bot-master/src/main/java/ChatBot.java
			}
			else if (title.toLowerCase().compareTo("пока") == 0) {
				exit("до встречи");
			}
			else {
				incorrectCommand();
			}
		}
	}
	
	public void say(String massage) {
		api.out(massage);
	}
	
	public String getInput() {
<<<<<<< HEAD:Chat-Bot-master/src/main/java/chat_bot/ChatBot.java
		var text = api.in();
=======
		var text = api.in();		
>>>>>>> 544a96c84182b64c7d1b13c642b1bb990bdb7684:Chat-Bot-master/src/main/java/ChatBot.java
		return text;
	}
	
	private void startCityGame() {
		var game = new CityGame();
		var exitType = game.startGame(api);
		if (exitType == GameExitType.PLAYER_WIN) {
			say("вам понравилась игра?");
			var answer = getInput();
			if (answer.toLowerCase().compareTo("да") == 0) {
				say("замечательно");
			}
			if (answer.toLowerCase().compareTo("нет") == 0) {
				say("жаль");
			}
		}
	}
	
	private void incorrectCommand() {
		say("Я ещё не умею делать то, что ты хочешь.");
	}
	
	private void exit(String massage) {
		say(massage);
		run = false;
	}

}
