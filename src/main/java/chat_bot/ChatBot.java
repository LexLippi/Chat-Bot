package chat_bot;


import chat_bot.game.*;
import chat_bot.game.board_game.BoardGame;
import chat_bot.game.city_game.CityGame;
import chat_bot.game.city_game.states.BotCourse;
import chat_bot.game.city_game.states.Draw;
import chat_bot.game.city_game.states.SelectLevel;
import chat_bot.game.return_types.GameReturnedValue;
import org.telegram.telegrambots.api.methods.groupadministration.DeleteStickerSetName;

import java.util.ArrayList;

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
				+ " Если хочешь играть в города, введи команду \"Играть в города\""
				+ " Если хочешь искать слова, введи команду \"Искать слова\"";
		ArrayList buttons = new ArrayList() {
			{
				add("Играть в города");
				add("Искать слова");
			}};
		api.outkeyboard(buttons, message);
	}

	public void process(String command) {
		if (command.toLowerCase().compareTo("играть в города") == 0) {
			startGame(GameType.CityGame);
		}
		else if (command.toLowerCase().compareTo("искать слова") == 0){
			startGame(GameType.BoardGame);
		}
		else if (command.toLowerCase().compareTo("пока") == 0) {
			say("до встречи");
		}
		else if (command.toLowerCase().compareTo("сдаюсь") == 0 || command.toLowerCase().compareTo("стоп") == 0){
			react(game.process(command));
			var message = "Если хочешь играть в города, введи команду \"Играть в города\""
					+ " Если хочешь искать слова, введи команду \"Искать слова\"";;
			ArrayList buttons = new ArrayList() {
				{
					add("Играть в города");
					add("Искать слова");
				}};
			api.outkeyboard(buttons, message);
		}
		else if (game instanceof CityGame && command.toLowerCase().compareTo("получить ссылку на город") == 0) {
			var message = "https://ru.wikipedia.org/wiki/" + ((CityGame)game).getLastCity();
			ArrayList buttons = new ArrayList();
			buttons.add("Сдаюсь");
			buttons.add("Стоп");
			buttons.add("Получить ссылку на город");
			api.outkeyboard(buttons, message);
		}

		else if (game != null){
			if (game instanceof CityGame && !(((CityGame)game).getCurrentState() instanceof BotCourse)){
				var answer = game.process(command);
				var message = new StringBuilder();
				for (var replica: answer.getMessages()) {
					message.append(replica + "\n");
				}
				ArrayList buttons = new ArrayList();
				if (((CityGame)game).getCurrentState() instanceof SelectLevel) {
					buttons.add("Легкий");
					buttons.add("Средний");
					buttons.add("Тяжелый");
				}
				else if (((CityGame)game).getCurrentState() instanceof Draw) {
					buttons.add("Орел");
					buttons.add("Решка");
				}
				else {
					buttons.add("Сдаюсь");
					buttons.add("Стоп");
					buttons.add("Получить ссылку на город");
				};
				api.outkeyboard(buttons, message.toString());
			}
			else {
				var answer = game.process(command);
				react(answer);
			}
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
			var message = "Если хочешь играть в города, введи команду \"Играть в города\""
					+ " Если хочешь искать слова, введи команду \"Искать слова\"";;
			ArrayList buttons = new ArrayList() {
				{
					add("Играть в города");
					add("Искать слова");
				}};
			api.outkeyboard(buttons, message);
		}
	}

	private void say(String massage) {
		api.out(massage);
	}

	private void startGame(GameType type) {
		game = factory.getGame(type);
		if (game instanceof CityGame) {
			var buttons = new ArrayList() {
				{
					add("Легкий");
					add("Средний");
					add("Тяжелый");
				}
			};
			var message = "Выбери уровень сложности: лёгкий, средний, тяжёлый";
			api.outkeyboard(buttons, message);
		}
		else if (game instanceof BoardGame) {
			var answer = game.startGame();
			ArrayList buttons = new ArrayList() {
				{
					add("Сдаюсь");
					add("Стоп");
				}};
			var message = "";
			for (var replica: answer.getMessages()) {
				message += replica + "\n";
			}
			api.outkeyboard(buttons, message);
		}
	}

	private void incorrectCommand() {
		say("Я ещё не умею делать то, что ты хочешь.");
	}

}
