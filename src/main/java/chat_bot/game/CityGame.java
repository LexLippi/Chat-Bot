package chat_bot.game;

import chat_bot.*;
import chat_bot.game.levels.Easy;
import chat_bot.game.levels.GameLevel;
import chat_bot.game.levels.Hard;
import chat_bot.game.levels.Medium;
import chat_bot.game.return_types.CityAnswerType;
import chat_bot.game.return_types.GameExitType;

import java.util.ArrayList;
import java.util.Random;

public class CityGame {
	private Data data;
	private Api api;
	protected GameLevel level;

	public static void main(String[] args) {
		var game = new CityGame(new Console(), new Data(new String[] {"Москва", "Минск"}));
		game.startGame();
	}

	public CityGame(Api api, Data data) {
		this.data = data;
		this.api = api;
		selectDifficultLevel();
	}

	public CityGame(Api api) {
		this.data = new Data();
		this.api = api;
		selectDifficultLevel();
	}
	
	public GameExitType startGame() {
		if (getDrawResult()) {
			var result = level.getBotFirstCourse(api);
			if (result != null) {
				return result;
			}
		}
		while (true)
		{
			var result = level.processingUserCourse(api);
			if (result != null) {
				return result;
			}
		}
	}

	private void selectDifficultLevel() {
		api.out("Выбери уровень сложности: легкий, средний, тяжелый");
		try {
			var userLevel = api.in().toLowerCase();
			if (userLevel.compareTo("легкий") == 0) {
				level = new Easy(data);
			}
			else if (userLevel.compareTo("средний") == 0) {
				level = new Medium(data);
			}
			else if (userLevel.compareTo("тяжелый") == 0) {
				level = new Hard(data);
			}
			else {
				api.out("У меня еще нет такого уровня. Попробуй выбрать уровень снова!");
				selectDifficultLevel();
			}
		}
		catch (Exception e) {
			return;
		}
	}

	private boolean getDrawResult() {
		api.out("Пора выбрать, кто будет ходить первым! Орёл или решка?");
		try {
			var side = api.in().toLowerCase();
			if (side.compareTo("орёл") == 0 || side.compareTo("решка") == 0) {
				var rnd = new Random();
				var result = rnd.nextInt(2);
				var resultSide = result == 0 ? "орёл" : "решка";
				return side.compareTo(resultSide) == 0;
			}
			else {
				api.out("Кажется, ты выбрал не орла или решку, а что-то посерьезнее... Попробуй повторить!");
				return getDrawResult();
			}
		}
		catch (Exception e) {
			return false;
		}
	}
}
