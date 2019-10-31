package chat_bot.game;

import chat_bot.game.levels.*;
import chat_bot.game.return_types.GameReturnedValue;
import chat_bot.game.states.SelectLevel;
import chat_bot.game.states.State;

public class CityGame implements IGame {
	private GameLevel level;
	private State currentState;

	public static void main(String[] args) {
		var game = new CityGame();
		game.startGame();
	}

	public CityGame() {
		currentState = new SelectLevel();
	}

	@Override
	public GameReturnedValue process(String inputString) {
		inputString = inputString.toLowerCase();
		try {
			var result = currentState.processCommand(inputString, level);
			if (currentState instanceof SelectLevel) {
				level = new LevelFactory().getLevel(inputString);
			}
			currentState = currentState.nextState;
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
