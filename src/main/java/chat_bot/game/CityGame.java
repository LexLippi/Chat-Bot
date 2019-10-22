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
	private Api api;
	protected GameLevel level;

	public static void main(String[] args) {
		//var game = new CityGame(new Console(), new Data(new String[] {"Москва", "Минск"}));
		var game = new CityGame(new Console(), new Data(new City[]
				{
						new City ("Москва", 1000000),
						new City ("Минск", 50000)
				}));
		game.startGame();
	}

	public CityGame(Api api, Data data) {
		this.api = api;
		selectDifficultLevel(data);
	}

	public CityGame(Api api) {
		var data = new Data();
		this.api = api;
		selectDifficultLevel(data);
	}
	
	public GameExitType startGame() {
		if (getDrawResult()) {
			var result = level.getBotFirstCourse();
			if (result != null) {
				return result;
			}
		}
		while (true)
		{
			var result = level.processingUserCourse();
			if (result != null) {
				return result;
			}
		}
	}

	private void selectDifficultLevel(Data data) {
		api.out("Выбери уровень сложности: легкий, средний, тяжелый");
		try {
			var userLevel = api.in().toLowerCase();
			if (userLevel.compareTo("легкий") == 0) {
				level = new Easy(data, api);
			}
			else if (userLevel.compareTo("средний") == 0) {
				level = new Medium(data, api);
			}
			else if (userLevel.compareTo("тяжелый") == 0) {
				level = new Hard(data, api);
			}
			else {
				api.out("У меня еще нет такого уровня. Попробуй выбрать уровень снова!");
				selectDifficultLevel(data);
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
