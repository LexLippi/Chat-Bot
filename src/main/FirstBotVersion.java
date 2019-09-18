package main;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FirstBotVersion {
	private static HashMap<Character, HashSet<String>> cities = new HashMap<Character, HashSet<String>>();
	private static Character waitingLetter;
	private static HashMap<Character, Integer> countCities = new HashMap<Character, Integer>();

	private static String answer(String a) {
		var firstLetter = a.toUpperCase().charAt(0);
		var lastLetter = a.toUpperCase().charAt(a.length() - 1);
		var yourCity = a.toLowerCase().replace(a.charAt(0), firstLetter);
		if (waitingLetter != null && waitingLetter != firstLetter) {
			return "You lie!";
		}
		if (cities.containsKey(firstLetter) && cities.get(firstLetter).contains(yourCity)) 
		{
			cities.get(firstLetter).remove(yourCity);
			countCities.put(firstLetter, countCities.get(firstLetter) - 1);
		}
		else
			return "I don't know this city";
		if (cities.get(lastLetter).isEmpty())
			return "End of game";
		else 
		{
			var myCities = cities.get(lastLetter).iterator();
			var min = 1e10;
			String bestCity = "";
			while (myCities.hasNext()) 
			{
				var currentCity = myCities.next();
				var currentLastLetter = currentCity.toUpperCase().charAt(currentCity.length() - 1);
				if (countCities.get(currentLastLetter) == 0) 
				{
					bestCity = currentCity;
					break;
				}
				if (countCities.get(currentLastLetter) < min) 
				{
					min = countCities.get(currentLastLetter);
					bestCity = currentCity;
				}
			}
			waitingLetter = bestCity.toUpperCase().charAt(bestCity.length() - 1);
			return bestCity;
		}
			
	}
	
	private static void getData() 
	{
		for (var i = 'А'; i <= 'Я'; ++i)
		{
			cities.put(i, new HashSet<String>());
			countCities.put(i, 0);
		}
		try {
			var reader = new BufferedReader(new FileReader(System.getProperty("user.dir") + "\\main\\resources\\input.txt"));
			String line;
			while((line = reader.readLine()) != null) 
			{
				var firstLetter = line.charAt(0);
				cities.get(firstLetter).add(line);
				countCities.put(firstLetter, countCities.get(firstLetter) + 1);
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		getData();
		var message = "Привет, меня зовут Гена. Я чат бот, который умеет играть в города. Если хочешь играть в города, введи команду \"Играть\"";
		System.out.println(message);
		var input = new Scanner(System.in);
		while (true) 
		{
			var isEnding = false;
			var title = input.next();
			if (title.toLowerCase().compareTo("играть") == 0) 
			{
				while (true) 
				{
					System.out.print("Твой ход: ");
					var inputString = input.next();
					if (inputString.toLowerCase().compareTo("стоп") == 0) {
						isEnding = true;
						break;
					}
					System.out.println("Гена говорит: " + answer(inputString));
				}
			}
			else 
			{
				System.out.println("Я ещё не умею делать то, что ты хочешь.");
				isEnding = true;
				break;
			}
			if (isEnding) 
				break;
		}
		input.close();
	}
}
