package main.java;

public class CityGame {
	private static Character waitingLetter;
	
	public static void main(String[] args) {
		StartGame();
	}
	
	public static GameExitType StartGame() {
		waitingLetter = null;
		String[] c = {"Москва", "Абакан", "Новосибирск"};
		var data = new Data(c);
		while (true) 
		{
			ChatBot.say("Твой ход: ");
			var inputString = ChatBot.getInput();
			if (inputString.toLowerCase().compareTo("стоп") == 0) {
				ChatBot.say("Гена говорит: приходи еще!");
				return GameExitType.GAME_INTERRUPTED;
			}
			else if (inputString.toLowerCase().compareTo("сдаюсь") == 0) {
				ChatBot.say("Гена говорит: ничего, в другой раз повезет!");
				return GameExitType.PLAYER_LOOSE;
			}
			
			var answer = checkAnswer(inputString, data);
			switch(answer) 
			{
				case INCORRECT_INPUT:
					ChatBot.say("Гена говорит: врешь, не уйдешь!");
					break;
				case INCORRECT_CITY:
					ChatBot.say("Гена говорит: я не знаю такого города, попробуйте снова");
					break;
				case CORRECT_INPUT:
					var lastLetter = inputString.toUpperCase().charAt(inputString.length() - 1);
					var resultCity = computeCity(lastLetter, data);
					if (resultCity == null) {
						ChatBot.say("Гена говорит: я проиграл :(");
						return GameExitType.PLAYER_WIN;
					}
					ChatBot.say("Гена говорит: " + resultCity);
					break;
				default:
					throw new IllegalStateException("incorrect CityAnswerType");
			}
		}
	}
	
	private static CityAnswerType checkAnswer(String city, Data data) {
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

	public static String computeCity(char lastLetter, Data data) 
	{
		if (data.cities.get(lastLetter).isEmpty())
			return null;
		var myCities = data.cities.get(lastLetter).iterator();
		var min = 1e10;
		String bestCity = "";
		while (myCities.hasNext()) 
		{
			var currentCity = myCities.next();
			var i = 1;
			var currentLastLetter = currentCity.toUpperCase().charAt(currentCity.length() - i);
			while (data.stopLetters.contains(currentLastLetter)) 
			{
				++i;
				currentLastLetter = currentCity.toUpperCase().charAt(currentCity.length() - i);
			}
			if (data.countCities.get(currentLastLetter) == 0) 
			{
				bestCity = currentCity;
				waitingLetter = currentLastLetter;
				break;
			}
			if (data.countCities.get(currentLastLetter) < min) 
			{
				min = data.countCities.get(currentLastLetter);
				bestCity = currentCity;
				waitingLetter = currentLastLetter;
			}
		}
		data.cities.get(lastLetter).remove(bestCity);
		return bestCity;
	}

}
