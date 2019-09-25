package main;

public class GameLogic {
	private Character waitingLetter;

	public String answer(String a, Data data) {
		var firstLetter = a.toUpperCase().charAt(0);
		var lastLetter = a.toUpperCase().charAt(a.length() - 1);
		var yourCity = firstLetter + a.toLowerCase().substring(1);
		if (waitingLetter != null && waitingLetter != firstLetter) {
			return "Врешь, не уйдешь! Повтори ход";
		}
		if (data.cities.containsKey(firstLetter) && data.cities.get(firstLetter).contains(yourCity)) 
		{
			data.cities.get(firstLetter).remove(yourCity);
			data.countCities.put(firstLetter, data.countCities.get(firstLetter) - 1);
		}
		else
			return "Я не знаю такого города. Попробуй еще раз";
		if (data.cities.get(lastLetter).isEmpty())
			return "Я проиграл";
		else 
			return computeCity(lastLetter, data);
		}
		
	private String computeCity(char lastLetter, Data data) 
	{
		var myCities = data.cities.get(lastLetter).iterator();
		var min = 1e10;
		String bestCity = "";
		while (myCities.hasNext()) 
		{
			var currentCity = myCities.next();
			var currentLastLetter = currentCity.toUpperCase().charAt(currentCity.length() - 1);
			while (currentLastLetter == 'Ь' || currentLastLetter == 'Ы' || currentLastLetter == 'Ё' || currentLastLetter == 'Ъ') 
			{
				var i = 2;
				currentLastLetter = currentCity.toUpperCase().charAt(currentCity.length() - i);
				++i;
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
