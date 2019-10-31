package chat_bot;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;

public class Data
{
    private HashMap<Character, HashMap<String, Integer>> cities = new HashMap<Character, HashMap<String, Integer>>();
    private HashMap<Character, Integer> countCities = new HashMap<Character, Integer>();
    private HashSet<Character> stopLetters = new HashSet<Character>();
    private Integer totalCitiesCount = 0;

    public static void main(String[] args) {
        var data = new Data();
        System.out.println(data.cities);
    }

    public Data() {
        initialize();
        //getDataFromFile();
        getDataFromSite();
    }

    public Data(City[] cities) {
        initialize();
        for (City city: cities) {
            var firstLetter = city.name.toUpperCase().charAt(0);
            this.cities.get(firstLetter).put(city.name, city.population);
            countCities.put(firstLetter, countCities.get(firstLetter) + 1);
            totalCitiesCount++;
            if (stopLetters.contains(firstLetter))
                stopLetters.remove(firstLetter);
        }
    }

    private void getDataFromSite() {
        try {
            var citiesString = PopulationData.getCities();
            for (var city : citiesString) {
                var population = PopulationData.getStatistics(city);
                var firstLetter = city.charAt(0);
                cities.get(firstLetter).put(city, population);
                totalCitiesCount++;
                countCities.put(firstLetter, countCities.get(firstLetter) + 1);
                if (stopLetters.contains(firstLetter))
                    stopLetters.remove(firstLetter);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getDataFromFile() {
        try {
            var reader = new BufferedReader(new FileReader(System.getProperty("user.dir") + "\\src\\main\\resources\\new_input.txt"));
            String line;
            while((line = reader.readLine()) != null) {
                var firstLetter = line.charAt(0);
                var nameAndPopulation = line.split(" ");
                var name = nameAndPopulation[0];
                var population = 0;
                if (nameAndPopulation.length > 2) {
                    for (var i = 1; i < nameAndPopulation.length - 1; i++)
                        name += " " + nameAndPopulation[i];
                    population = Integer.parseInt(nameAndPopulation[nameAndPopulation.length - 1]);
                }
                else if (nameAndPopulation.length == 2) {
                    population = Integer.parseInt(nameAndPopulation[1]);
                }
                cities.get(firstLetter).put(name, population);
                totalCitiesCount++;
                countCities.put(firstLetter, countCities.get(firstLetter) + 1);
                if (stopLetters.contains(firstLetter))
                    stopLetters.remove(firstLetter);
            }
            reader.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public HashMap<Character, HashMap<String, Integer>> getCities() {
        return cities;
    }

    public HashSet<Character> getStopLetters() {
        return stopLetters;
    }

    public void updateStatistics (Character letter, String cityName) {
        countCities.put(letter, countCities.get(letter) - 1);
        totalCitiesCount--;
        var firstLetter = cityName.charAt(0);
        cities.get(firstLetter).remove(cityName);
    }

    public Double getStatistics(Character letter) {
        return (double)countCities.get(letter) / totalCitiesCount;
    }

    private void initialize() {
        for (Character i = 'А'; i <= 'Я'; ++i) {
            cities.put(i, new HashMap<String, Integer>());
            countCities.put(i, 0);
            stopLetters.add(i);
        }
        stopLetters.add('Ё');
        countCities.put('Ё', 0);
        cities.put('Ё', new HashMap<String, Integer>());
    }
}
