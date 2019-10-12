package chat_bot.game;

import chat_bot.*;
import chat_bot.game.levels.Easy;
import chat_bot.game.levels.GameLevel;
import chat_bot.game.levels.Hard;
import chat_bot.game.levels.Medium;
import chat_bot.game.return_types.CityAnswerType;
import chat_bot.game.return_types.GameExitType;

import java.util.Random;

public class CityGame {
	private Character waitingLetter;
	private Data data;
	private Api api;
	private GameLevel level;

	public static void main(String[] args) {
		var game = new CityGame(new Console());
		game.startGame();
	}

	public CityGame(Api api) {
		waitingLetter = null;
		data = new Data(new String[] {"Москва", "Минск"});
		this.api = api;
		selectDifficultLevel();
	}
	
	public GameExitType startGame() {
		if (getDrawResult()) {
			getBotCourse("");
		}
		while (true)
		{
			var result = processingUserCourse();
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

	private GameExitType processingUserCourse() throws IllegalStateException{
		api.out("Твой ход: ");
		try {
			var inputString = api.in().toLowerCase();
			if (inputString.compareTo("стоп") == 0) {
				api.out("Гена говорит: приходи еще!");
				return GameExitType.GAME_INTERRUPTED;
			}
			else if (inputString.compareTo("сдаюсь") == 0) {
				api.out("Гена говорит: ничего, в другой раз повезет!");
				return GameExitType.PLAYER_LOOSE;
			}
			var answer = checkAnswer(inputString, data);
			switch(answer)
			{
				case INCORRECT_INPUT:
					api.out("Гена говорит: врешь, не уйдешь!");
					break;
				case INCORRECT_CITY:
					api.out("Гена говорит: я не знаю такого города, попробуйте снова");
					break;
				case CORRECT_INPUT:
					var result = getBotCourse(inputString);
					if (result != null) {
						return result;
					}
					break;
				default:
					throw new IllegalStateException("incorrect CityAnswerType");
			}
		}
		catch (Exception e) {
			return null;
		}
		return null;
	}

	public GameExitType getBotCourse(String userCity)
	{
		if (level.is_step_counter_empty()) {
			api.out("Гена говорит: я проиграл :(");
			return GameExitType.PLAYER_WIN;
		}
		var lastLetter = level.getCityLastLetter(userCity);
		var resultCity = level.computeCity(lastLetter);
		level.inc_step_counter();
		if (resultCity == null) {
			api.out("Гена говорит: я проиграл :(");
			return GameExitType.PLAYER_WIN;
		}
		api.out("Гена говорит: " + resultCity);
		return null;
	}
	
	public CityAnswerType checkAnswer(String city, Data data) {
		var firstLetter = city.toUpperCase().charAt(0);
		var yourCity = firstLetter + city.toLowerCase().substring(1);
		if (waitingLetter != null && waitingLetter != firstLetter)
			return CityAnswerType.INCORRECT_INPUT;
		if (data.cities.containsKey(firstLetter) && data.cities.get(firstLetter).contains(yourCity)) 
		{
			data.cities.get(firstLetter).remove(yourCity);
			data.countCities.put(firstLetter, data.countCities.get(firstLetter) - 1);
			return CityAnswerType.CORRECT_INPUT;
		}
		return CityAnswerType.INCORRECT_CITY;
	}
}