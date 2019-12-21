package chat_bot.game.city_game;

import chat_bot.Api;
import chat_bot.game.IGame;
import chat_bot.game.city_game.levels.*;
import chat_bot.game.city_game.states.BotCourse;
import chat_bot.game.city_game.states.Draw;
import chat_bot.game.return_types.GameReturnedValue;
import chat_bot.game.city_game.states.SelectLevel;
import chat_bot.game.city_game.states.State;

import java.util.ArrayList;

public class CityGame implements IGame {
	private GameLevel level;
	private State currentState;
	private Data data;
	private String lastCity;

	public CityGame(Data data) {
	    currentState = new SelectLevel();
	    this.data = data;
	}

	public String getLastCity() {
		return lastCity;
	}

	public State getCurrentState(){
		return currentState;
	}

	public GameLevel getLevel() {
		return level;
	}

	@Override
	public GameReturnedValue process(String inputString, Api api) {
		inputString = inputString.toLowerCase();
		if (inputString.compareTo("получить ссылку на город") == 0
				&& currentState instanceof BotCourse
				&& lastCity != null){
			var message = "https://ru.wikipedia.org/wiki/" + getLastCity();
			return new GameReturnedValue(null, message);
		}
		try {
			var result = currentState.processCommand(inputString, level);
			if (currentState instanceof SelectLevel) {
				level = new LevelFactory().getLevel(inputString, data);
			}
			currentState = currentState.nextState;
			var message = result.getMessages();
			var city = message[message.length - 1];
			if (data.getUsedCities().contains(city.toLowerCase()))
				lastCity = city.replace(' ', '_');
			return result;
		}
		catch (IllegalArgumentException e) {
			return currentState.getIllegalGameLevel();
		}
	}

	public GameReturnedValue startGame(Api api) {
		return new GameReturnedValue(null, "Выбери уровень сложности: лёгкий, средний, тяжёлый");
	}

	@Override
	public ArrayList<String> getAnswerWariants() {
		var buttons = new ArrayList<String>();
		if (currentState instanceof SelectLevel) {
			buttons.add("Лёгкий");
			buttons.add("Средний");
			buttons.add("Тяжёлый");
		}
		else if (currentState instanceof Draw){
			buttons.add("Орел");
			buttons.add("Решка");
		}
		else{
			buttons.add("Сдаюсь");
			buttons.add("Стоп");
			buttons.add("Получить ссылку на город");
		}
		return buttons;
	}
}
