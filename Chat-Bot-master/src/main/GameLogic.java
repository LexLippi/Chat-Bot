package main;

public class GameLogic {
	private static Character waitingLetter;

	public static String answer(String a) {
		var data = new Data();
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
		
	private static String computeCity(char lastLetter, Data data) 
	{
		var myCities = data.cities.get(lastLetter).iterator();
		var min = 1e10;
		String bestCity = "";
		while (myCities.hasNext()) 
		{
			var currentCity = myCities.next();
			var currentLastLetter = currentCity.toUpperCase().charAt(currentCity.length() - 1);
			if (data.countCities.get(currentLastLetter) == 0) 
			{
				bestCity = currentCity;
				break;
			}
			if (data.countCities.get(currentLastLetter) < min) 
			{
				min = data.countCities.get(currentLastLetter);
				bestCity = currentCity;
			}
		}
		waitingLetter = bestCity.toUpperCase().charAt(bestCity.length() - 1);
		return bestCity;
	}
}
