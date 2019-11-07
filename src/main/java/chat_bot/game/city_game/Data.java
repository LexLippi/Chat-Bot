package chat_bot.game.city_game;

import chat_bot.dataProviders.DataProviderFile;

import java.util.*;

public class Data implements Cloneable
{
    private HashMap<Character, HashMap<String, City>> cities = new HashMap<>();
    private HashMap<Character, Integer> countCities = new HashMap<>();
    private HashSet<Character> stopLetters = new HashSet<>();
    private HashSet<String> usedCities = new HashSet<>();
    private Integer totalCitiesCount = 0;

    public static void main(String[] args) {
        var data1 = new Data();
        var data2 = data1.clone();
        data2.updateStatistics('А', "александров");
        System.out.println(data2.cities.get('А').keySet());
        System.out.println(data2.usedCities);
        System.out.println(data1.cities.get('А').keySet());
        System.out.println(data1.usedCities);
    }

    public Data() {
        initialize();
        var provider = new DataProviderFile();
        //var provider = new DataProvider1000Mest();
        provider.getData(this);
    }

    public Data(City[] cities) {
        initialize();
        for (var city: cities) {
            var firstLetter = city.name.toUpperCase().charAt(0);
            this.cities.get(firstLetter).put(city.name.toLowerCase(), new City(city.name, city.population));
            countCities.put(firstLetter, countCities.get(firstLetter) + 1);
            totalCitiesCount++;
            stopLetters.remove(firstLetter);
        }
    }

    public void addCity(Character firstLetter, String city, Integer population){
        cities.get(firstLetter).put(city.toLowerCase(), new City(city, population));
        totalCitiesCount++;
        countCities.put(firstLetter, countCities.get(firstLetter) + 1);
        stopLetters.remove(firstLetter);
    }

    public HashMap<Character, HashMap<String, City>> getCities() {
        return cities;
    }

    public HashSet<Character> getStopLetters() {
        return stopLetters;
    }

    public void updateStatistics (Character letter, String cityName) {
        countCities.put(letter, countCities.get(letter) - 1);
        totalCitiesCount--;
        var firstLetter = cityName.toUpperCase().charAt(0);
        cities.get(firstLetter).remove(cityName);
        usedCities.add(cityName);
    }

    public HashSet<String> getUsedCities() {
        return usedCities;
    }

    private Integer[] getStatistics() {
        return countCities.values().stream().sorted(Integer::compareTo).toArray(Integer[]::new);
    }

    public ArrayList<Double> getPercents() {
        var percents = new ArrayList<Double>();
        for (var stats: getStatistics()) {
            if (stats > 0) {
                percents.add((double)stats / totalCitiesCount);
            }
        }
        return percents;
    }

    public Double getStatistics(Character letter) {
        return (double)countCities.get(letter) / totalCitiesCount;
    }

    private void initialize() {
        for (Character i = 'А'; i <= 'Я'; ++i) {
            cities.put(i, new HashMap<>());
            countCities.put(i, 0);
            stopLetters.add(i);
        }
        stopLetters.add('Ё');
        countCities.put('Ё', 0);
        cities.put('Ё', new HashMap<>());
    }

    public Data clone(){
        try {
            Data result = (Data) super.clone();
            result.cities = (HashMap<Character, HashMap<String, City>>) cities.clone();
            result.countCities = (HashMap<Character, Integer>) countCities.clone();
            result.stopLetters = (HashSet<Character>) stopLetters.clone();
            result.usedCities = (HashSet<String>) usedCities.clone();
            var letters = cities.keySet();
            for (var letter: letters){
                result.cities.put(letter, (HashMap<String, City>) cities.get(letter).clone());
            }
            return result;
        }
        catch (CloneNotSupportedException e) {
            return null;
        }
    }
}
