package chat_bot.game;

import chat_bot.*;

import java.util.Random;

public class CityGame {
	private Character waitingLetter;
	private Data data;
	private Api api;
	private DifficultLevel level;

	
	public static void main(String[] args) {
		var game = new CityGame();
		game.startGame();
	}

	public CityGame() {
		waitingLetter = null;
		data = new Data();
		api = new Console();
		selectDifficultLevel();
	}
	
	public GameExitType startGame() {
		if (getDrawResult()) {
			getBotCourse();
		}
		while (true)
		{
			processingUserCourse();
			getBotCourse();
		}
	}

	private void selectDifficultLevel() {
		api.out("Выбери уровень сложности: легкий, средний, тяжелый");
		var userLevel = api.in().toLowerCase();
		if (userLevel.compareTo("легкий") == 0) {
			level = new Easy();
		}
		else if (userLevel.compareTo("средний") == 0) {
			level = new Medium();
		}
		else if (userLevel.compareTo("тяжелый") == 0) {
			level = new Hard(data);
		}
		else {
			api.out("У меня еще нет такого уровня. Попробуй выбрать уровень снова!");
			selectDifficultLevel();
		}
	}

	private boolean getDrawResult() {
		api.out("Пора выбрать, кто будет ходить первым! Орёл или решка?");
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

	private GameExitType processingUserCourse() {
		api.out("Твой ход: ");
		var inputString = api.in();
		if (inputString.toLowerCase().compareTo("стоп") == 0) {
			api.out("Гена говорит: приходи еще!");
			return GameExitType.GAME_INTERRUPTED;
		}
		else if (inputString.toLowerCase().compareTo("сдаюсь") == 0) {
			api.out("Гена говорит: ничего, в другой раз повезет!");
			return GameExitType.PLAYER_LOOSE;
		}
		var answer = checkAnswer(inputString, data);
	}

	public String getBotCourse(CityAnswerType answer, String inputString)
	{
		switch(answer)
		{
			case INCORRECT_INPUT:
				api.out("Гена говорит: врешь, не уйдешь!");
				break;
			case INCORRECT_CITY:
				api.out("Гена говорит: я не знаю такого города, попробуйте снова");
				break;
			case CORRECT_INPUT:
				var lastLetter = inputString.toUpperCase().charAt(inputString.length() - 1);
				var resultCity = level.computeCity(lastLetter);
				if (resultCity == null) {
					api.out("Гена говорит: я проиграл :(");
					return GameExitType.PLAYER_WIN;
				}
				api.out("Гена говорит: " + resultCity);
				break;
			default:
				throw new IllegalStateException("incorrect CityAnswerType");
		}
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
