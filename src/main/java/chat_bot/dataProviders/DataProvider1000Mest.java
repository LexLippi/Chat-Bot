package chat_bot.dataProviders;

import chat_bot.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.regex.Pattern;

public class DataProvider1000Mest implements IDataProvider {
    @Override
    public void getData(Data data) {
        var letters = new String[]{"A", "B", "V", "G", "D", "E", "Zh", "Z", "I", "Ii", "K", "L", "M", "N", "O", "P", "R", "S", "T", "U", "F", "H", "Ts", "Ch", "Sh", "Sch", "Ye", "Yu", "Ya"};
        var threadPool = Executors.newFixedThreadPool(letters.length);
        var patternRawCities = Pattern.compile("<table><tbody>([А-ЯЕЁа-яеё<>trd/a href=\"a-z_()-.]+)</tbody>");
        var patternCities = Pattern.compile("<tr>|<td>|<a href=\"[\\w-]+\">|</a>|</td>| \\([А-ЯЁЕа-яёе.]+\\)|/ [а-яеёА-ЯЕЁ]+");
        var populationData = new PopulationDataFromSite(patternRawCities, patternCities);
        List<Future<Integer>> futures = new ArrayList<>();
        for (var letter: letters) {
            final String l = letter;
            futures.add(
                    CompletableFuture.supplyAsync(
                            () -> {
                                var citiesString = populationData.getCities("http://www.1000mest.ru/city" + l);
                                for (var city : citiesString) {
                                    var population = populationData.getStatistics(city);
                                    var firstLetter = city.charAt(0);
                                    data.addCity(firstLetter,city, population);
                                    System.out.println(city + " " + population);
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
}
