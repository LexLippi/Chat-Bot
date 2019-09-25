package main.java;

public class GameLogic {
	public String getAnswer(String a, Data data) {
		var firstLetter = a.toUpperCase().charAt(0);
		var lastLetter = a.toUpperCase().charAt(a.length() - 1);
		var yourCity = firstLetter + a.toLowerCase().substring(1);
		if (ComputeCity.waitingLetter != null && ComputeCity.waitingLetter != firstLetter) {
			return "Врёшь, не уйдешь! Повтори ход";
		}
		if (data.cities.containsKey(firstLetter) && data.cities.get(firstLetter).contains(yourCity)) 
		{
			data.cities.get(firstLetter).remove(yourCity);
			data.countCities.put(firstLetter, data.countCities.get(firstLetter) - 1);
		}
		else
			return "Я не знаю такого города. Попробуй ещё раз";
		if (data.cities.get(lastLetter).isEmpty())
			return "Я проиграл";
		else 
			return ComputeCity.computeCity(lastLetter, data);
		}	
}
