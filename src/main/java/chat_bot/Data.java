package chat_bot;

import jdk.jshell.spi.ExecutionControl;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Data implements Cloneable
{
    private HashMap<Character, HashMap<String, City>> cities = new HashMap<Character, HashMap<String, City>>();
    private HashMap<Character, Integer> countCities = new HashMap<Character, Integer>();
    private HashSet<Character> stopLetters = new HashSet<Character>();
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
        getDataFromFile();
        //getDataFromSite();
    }

    public Data(City[] cities) {
        initialize();
        for (var city: cities) {
            var firstLetter = city.name.toUpperCase().charAt(0);
            this.cities.get(firstLetter).put(city.name.toLowerCase(), new City(city.name, city.population));
            countCities.put(firstLetter, countCities.get(firstLetter) + 1);
            totalCitiesCount++;
            if (stopLetters.contains(firstLetter))
                stopLetters.remove(firstLetter);
        }
    }

    private void getDataFromSite() {
        String[] letters = new String[]{"A", "B", "V", "G", "D", "E", "Zh", "Z", "I", "Ii", "K", "L", "M", "N", "O", "P", "R", "S", "T", "U", "F", "H", "Ts", "Ch", "Sh", "Sch", "Ye", "Yu", "Ya"};
        ExecutorService threadPool = Executors.newFixedThreadPool(letters.length);
        List<Future<Integer>> futures = new ArrayList<>();
        for (var letter: letters) {
            final String l = letter;
            futures.add(
                    CompletableFuture.supplyAsync(
                            () -> {
                                try {
                                    var populationData = new PopulationData();
                                    var citiesString = populationData.getCities(l);
                                    for (var city : citiesString) {
                                        var population = populationData.getStatistics(city);
                                        var firstLetter = city.charAt(0);
                                        cities.get(firstLetter).put(city.toLowerCase(), new City(city, population));
                                        totalCitiesCount++;
                                        countCities.put(firstLetter, countCities.get(firstLetter) + 1);
                                        if (stopLetters.contains(firstLetter))
                                            stopLetters.remove(firstLetter);
                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                return null;
                            },
                            threadPool
                    ));
            }
        for (Future<Integer> future : futures) {
            try {
                future.get();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        threadPool.shutdown();
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
                cities.get(firstLetter).put(name.toLowerCase(), new City(name, population));
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
            cities.put(i, new HashMap<String, City>());
            countCities.put(i, 0);
            stopLetters.add(i);
        }
        stopLetters.add('Ё');
        countCities.put('Ё', 0);
        cities.put('Ё', new HashMap<String, City>());
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
