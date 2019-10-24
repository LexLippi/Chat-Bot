package chat_bot;


import chat_bot.game.CityGame;
import chat_bot.game.IGame;
import chat_bot.game.return_types.GameExitType;
import chat_bot.game.return_types.GameReturnedValue;

public class ChatBot {
	private Api api;

	private IGame game = null;

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
		else if (game != null){
			var answer = game.process(command);
			react(answer);
		}
		else {
			incorrectCommand();
		}
	}

	private void react(GameReturnedValue answer){
		say(answer.getMessage());
		if (answer.getType() != null){
			game = null;
		}
	}

	private void say(String massage) {
		api.out(massage);
	}
	
	private void StartCityGame() {
		game = new CityGame();
		var answer = game.startGame();
		react(answer);
	}

	private void incorrectCommand() {
		say("Я ещё не умею делать то, что ты хочешь.");
	}

}
