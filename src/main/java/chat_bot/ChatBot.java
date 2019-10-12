package chat_bot;


import chat_bot.game.CityGame;
import chat_bot.game.return_types.GameExitType;

public class ChatBot {
	private Api api;
	private boolean run = true;

	public CityGame game = null;

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
				StartCityGame();
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
		try {
			var text = api.in();
			return text;
		}
		catch (Exception e) {
			return null;
		}
	}
	
	private void StartCityGame() {
		game = new CityGame(api);
		var exitType = game.startGame();
		if (exitType == GameExitType.PLAYER_WIN) {
			say("вам понравилась игра?");
			var answer = getInput();
			if (answer.toLowerCase().compareTo("да") == 0)
				say("замечательно");
			if (answer.toLowerCase().compareTo("нет") == 0)
				say("жаль");
		}
	}
	
	private void incorrectCommand() {
		say("Я ещё не умею делать то, что ты хочешь.");
	}
	
	public void exit(String massage) {
		say(massage);
		run = false;
	}

}
