package chat_bot;


import chat_bot.game.CityGame;
import chat_bot.game.return_types.GameExitType;

public class ChatBot {
	private Api api;

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
	}

	public void process(String command){
		if (command.toLowerCase().compareTo("играть") == 0) {
			StartCityGame();
		}
		else if (command.toLowerCase().compareTo("пока") == 0) {
			say("до встречи");
		}
		else {
			incorrectCommand();
		}
	}
	
	public void say(String massage) {
		api.out(massage);
	}
	
	private void StartCityGame() {
		game = new CityGame(api);
		game.startGame();
	}
	
	private void incorrectCommand() {
		say("Я ещё не умею делать то, что ты хочешь.");
	}

}
