package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;

public class Data {
	public HashMap<Character, HashSet<String>> cities = new HashMap<Character, HashSet<String>>();
	public HashMap<Character, Integer> countCities = new HashMap<Character, Integer>();
	
	public Data() {
		getData();
	}
	
	private void getData() 
	{
		for (var i = 'À'; i <= 'ß'; ++i)
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
}
