package chat_bot;


import chat_bot.game.*;
import chat_bot.game.return_types.GameReturnedValue;

public class ChatBot {
	private Api api;
	private IGameFactory factory;
	private IGame game = null;
	
	public ChatBot(Api api, IGameFactory factory) {
		this.api = api;
		this.factory = factory;
		start();
	}
	
	public void start() {
		var message = "Привет, меня зовут Гена."
				+ " Я чат бот, который умеет играть в города."
				+ " Если хочешь играть в города, введи команду \"Играть\"";
		say(message);
	}

	public void process(String command) {
		if (command.toLowerCase().compareTo("играть") == 0) {
			startGame(GameType.CityGame);
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

	private void react(GameReturnedValue answer) {
		for (var replica: answer.getMessages()) {
			say(replica);
		}
		if (answer.getType() != null){
			game = null;
		}
	}

	private void say(String massage) {
		api.out(massage);
	}

	
	private void startGame(GameType type) {
		game = factory.getGame(type);
		var answer = game.startGame();
		react(answer);
	}

	private void incorrectCommand() {
		say("Я ещё не умею делать то, что ты хочешь.");
	}

}
