package main.java;

public class ComputeCity {
	public static Character waitingLetter;
	
	public static String computeCity(char lastLetter, Data data) 
	{
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
