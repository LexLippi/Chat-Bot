package chat_bot;


import chat_bot.game.*;
import chat_bot.game.board_game.BoardGame;
import chat_bot.game.city_game.CityGame;
import chat_bot.game.city_game.CityMultiplayerGame;
import chat_bot.game.city_game.Statistic;
import chat_bot.game.city_game.states.BotCourse;
import chat_bot.game.city_game.states.Draw;
import chat_bot.game.city_game.states.SelectLevel;
import chat_bot.game.return_types.GameExitType;
import chat_bot.game.return_types.GameReturnedValue;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;

public class ChatBot implements IButtonsProvider {
	private Api api;
	private IGameFactory factory;
	private ArrayDeque<ChatBot> waitingBots = new ArrayDeque<>();
	private IGame game = null;
	private HashMap<String, Statistic> statistic = new HashMap<>();

	public void addWaitingBot(ChatBot bot){
		waitingBots.add(bot);
		say("похоже, тебя приглашают в игру");
	}

	public void cancelWaiting(ChatBot bot, boolean sayIt){
		waitingBots.remove(bot);
		if (!sayIt)
			return;
		say("приглашение отменили :(");
	}

	public IGame getGame(){
		return game;
	}

	public ChatBot(Api api, IGameFactory factory) {
		System.out.println(this);
		this.api = api;
		api.setButtonsProvider(this);
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
				add("Multiplayer");
			}};
		for (var level: levels)
			statistic.put(level, new Statistic());
	}

	private void getOptions(){
		var message = "Если хочешь играть в города, введи команду \"Играть в города\""
				+ " Если хочешь искать слова, введи команду \"Искать слова\""
				+ " Если хочешь играть в города с другом, введи команду \"Играть в города с другом\"";
		if (waitingBots.size() > 0){
			message = "С тобой хотят поиграть по сети, чтобы принять, введи команду \"Играть по сети\""
					+ "чтобы отказаться - \"Отказаться от приглашения\""+ message;
		}
		say(message);
	}

	public void start() {
		var message = "Привет, меня зовут Гена."
				+ " Я чат бот, который умеет играть в города, а еще делать кроссворды.";
		say(message);
		getOptions();
	}

	public void finish(){
		if (game != null)
			game.process("сдаюсь", api);
	}

	public void process(String command) {
		if (game != null){
			var answer = game.process(command, api);
			react(answer);
		}
		else {
			if (command.toLowerCase().compareTo("получить статистику по игре в города") == 0) {
				var message = "Легкий уровень\n " + statistic.get("Easy").getStatistic()
						+ "\nСредний уровень\n " + statistic.get("Medium").getStatistic()
						+ "\nСложный уровень\n " + statistic.get("Hard").getStatistic()
						+ "\nПо сети\n " + statistic.get("Multiplayer").getStatistic();
				say(message);
			}
			else if (command.toLowerCase().compareTo("играть в города") == 0) {
				startGame(GameType.CityGame);
			}
			else if (command.toLowerCase().compareTo("искать слова") == 0){
				startGame(GameType.BoardGame);
			}
			else if (command.toLowerCase().compareTo("играть в города с другом") == 0){
				startGame(GameType.MultiplayerCityGame);
			}
			else if (command.toLowerCase().compareTo("играть по сети") == 0){
				if (waitingBots.size() <= 0){
					var message = "похоже, тебя никто не пригласил, но это ничего, ты можешь сам пригласить своего друга"
							+ "для этого просто введи \"Играть в города с другом\"";
					say(message);
				}
				else{
					game = waitingBots.poll().getGame();
					react(game.startGame(api));
				}
			}
			else if (command.toLowerCase().compareTo("отказаться от приглашения") == 0){
				if (waitingBots.size() <= 0){
					var message = "похоже, тебя никто не пригласил, но это ничего, ты можешь сам пригласить своего друга"
							+ "для этого просто введи \"Играть в города с другом\"";
					say(message);
				}
				else{
					var bot = waitingBots.poll();
					bot.say("похоже, твой противник отказался от приглашения");
				}
			}
			else {
				incorrectCommand();
			}
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
			var answerType = answer.getType();
			if (game instanceof CityGame) {
				var level = ((CityGame)game).getLevel();
				var strLevel = level.getStrName();
				changeStatistic(strLevel, answerType);
			}
			else if (game instanceof CityMultiplayerGame) {
				var strLevel = "Multiplayer";
				changeStatistic(strLevel, answerType);
			}
			game = null;
			getOptions();
		}
	}

	public void say(String massage) {
		api.out(massage);
	}

	private void startGame(GameType type) {
		game = factory.getGame(type);
		react(game.startGame(api));
	}

	private void incorrectCommand() {
		say("Я ещё не умею делать то, что ты хочешь.");
	}

	@Override
	public ArrayList<String> getButtons() {
		if (game != null){
			return game.getAnswerWariants();
		}
		var buttons = new ArrayList<String>() {};
		if (waitingBots.size() > 0){
			buttons.add("Играть по сети");
			buttons.add("Отказаться от приглашения");
		}
		buttons.add("Играть в города с другом");
		buttons.add("Играть в города");
		buttons.add("Искать слова");
		buttons.add("Получить статистику по игре в города");
		return buttons;
	}
}
