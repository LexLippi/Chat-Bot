package chat_bot.game.city_game;

import chat_bot.game.IGame;
import chat_bot.game.city_game.levels.*;
import chat_bot.game.return_types.GameReturnedValue;
import chat_bot.game.city_game.states.SelectLevel;
import chat_bot.game.city_game.states.State;

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

	@Override
	public GameReturnedValue process(String inputString) {
		inputString = inputString.toLowerCase();
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

	public GameReturnedValue startGame() {
		return new GameReturnedValue(null, "Выбери уровень сложности: лёгкий, средний, тяжёлый");
	}
}
