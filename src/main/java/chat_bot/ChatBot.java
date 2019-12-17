package chat_bot;


import chat_bot.game.*;
import chat_bot.game.board_game.BoardGame;
import chat_bot.game.city_game.CityGame;
import chat_bot.game.city_game.Statistic;
import chat_bot.game.city_game.levels.Easy;
import chat_bot.game.city_game.levels.GameLevel;
import chat_bot.game.city_game.levels.Hard;
import chat_bot.game.city_game.levels.Medium;
import chat_bot.game.city_game.states.BotCourse;
import chat_bot.game.city_game.states.Draw;
import chat_bot.game.city_game.states.SelectLevel;
import chat_bot.game.return_types.GameExitType;
import chat_bot.game.return_types.GameReturnedValue;
import org.telegram.telegrambots.api.methods.groupadministration.DeleteStickerSetName;

import java.util.ArrayList;
import java.util.HashMap;

public class ChatBot {
	private Api api;
	private IGameFactory factory;
	private IGame game = null;
	//private Statistic statistic = new Statistic();
	private HashMap<String, Statistic> statistic = new HashMap<>();

	public ChatBot(Api api, IGameFactory factory) {
		this.api = api;
		this.factory = factory;
		start();
		initStatistic();
	}

	private void initStatistic() {
		var levels = new ArrayList<String>() {
			{
				add("Easy");
				add("Medium");
				add("Hard");
			}};
		for (var level: levels)
			statistic.put(level, new Statistic());
	}

	public void start() {
		var message = "Привет, меня зовут Гена."
				+ " Я чат бот, который умеет играть в города, а еще делать кроссворды."
				+ " Если хочешь играть в города, введи команду \"Играть в города\""
				+ " Если хочешь искать слова, введи команду \"Искать слова\"";
		var buttons = new ArrayList<String>() {
			{
				add("Играть в города");
				add("Искать слова");
				add("Получить статистику по игре в города");
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
		else if (command.toLowerCase().compareTo("сдаюсь") == 0 || command.toLowerCase().compareTo("стоп") == 0){
			react(game.process(command));
		}
		else if (game instanceof CityGame &&
				((CityGame)game).getCurrentState() instanceof BotCourse &&
				command.toLowerCase().compareTo("получить ссылку на город") == 0) {
			var message = "https://ru.wikipedia.org/wiki/" + ((CityGame)game).getLastCity();
			var buttons = new ArrayList<String>();
			buttons.add("Сдаюсь");
			buttons.add("Стоп");
			buttons.add("Получить ссылку на город");
			api.outkeyboard(buttons, message);
		}
		else if (command.toLowerCase().compareTo("получить статистику по игре в города") == 0) {
			var message = "Легкий уровень\n " + statistic.get("Easy").getStatistic()
						+ "\nСредний уровень\n " + statistic.get("Medium").getStatistic()
						+ "\nСложный уровень\n " + statistic.get("Hard").getStatistic();
			var buttons = new ArrayList<String>();
			buttons.add("Играть в города");
			buttons.add("Искать слова");
			buttons.add("Получить статистику по игре в города");
			api.outkeyboard(buttons, message);
		}
		else if (game != null){
			if (game instanceof CityGame && !(((CityGame)game).getCurrentState() instanceof BotCourse)){
				var answer = game.process(command);
				var message = new StringBuilder();
				for (var replica: answer.getMessages()) {
					message.append(replica + "\n");
				}
				var buttons = new ArrayList<String>();
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

	private void changeStatistic(String level, GameExitType answerType) {
		statistic.get(level).increaseTotalGames();
		if (answerType.equals(GameExitType.PLAYER_LOOSE)) {
			statistic.get(level).increaseDefeats();
		}
		else if (answerType.equals(GameExitType.PLAYER_WIN)) {
			statistic.get(level).increaseWins();
		}
		else if (answerType.equals(GameExitType.GAME_INTERRUPTED)) {
			statistic.get(level).increaseInterruptions();
		}
	}

	private void react(GameReturnedValue answer) {
		for (var replica: answer.getMessages()) {
			say(replica);
		}
		if (answer.getType() != null){
			if (game instanceof CityGame) {
				var level = ((CityGame)game).getLevel();
				var answerType = answer.getType();
				String strLevel = "";
				if (level instanceof Easy) {
					strLevel = "Easy";
				}
				else if (level instanceof Medium) {
					strLevel = "Medium";
				}
				else if (level instanceof Hard) {
					strLevel = "Hard";
				}
				changeStatistic(strLevel, answerType);
			}
			game = null;
			var message = "Если хочешь играть в города, введи команду \"Играть в города\""
					+ " Если хочешь искать слова, введи команду \"Искать слова\"";;
			var buttons = new ArrayList<String>() {
				{
					add("Играть в города");
					add("Искать слова");
					add("Получить статистику по игре в города");
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
			var buttons = new ArrayList<String>() {
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
			ArrayList buttons = new ArrayList<String>() {
				{
					add("Сдаюсь");
					add("Стоп");
				}};
			var message = new StringBuilder();
			var replics = answer.getMessages();
			for (var i = 0; i < replics.length - 1; i++) {
				message.append(replics[i] + "\n");
			}
			api.outkeyboard(buttons, message.toString());
			say(replics[replics.length - 1]);
		}
	}

	private void incorrectCommand() {
		say("Я ещё не умею делать то, что ты хочешь.");
	}

}
