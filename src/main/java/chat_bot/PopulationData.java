package chat_bot;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PopulationData {
    private static Pattern patternRawСities = Pattern.compile("<table><tbody>([А-ЯЕЁа-яеё<>trd/a href=\"a-z_()-.]+)</tbody>");
    private static Pattern patternCities = Pattern.compile("<tr>|<td>|<a href=\"[\\w-]+\">|</a>|</td>| \\([А-ЯЁЕа-яёе.]+\\)|/ [а-яеёА-ЯЕЁ]+");
    private static Pattern patternPopulation1 = Pattern.compile("class=\"infobox-header\" style=\"[\\w\\d:#;]+\">Население[</\\w\\d\\s>\":#;%=]+Население[</\\w\\d\\s>\":#;%-=]+[↘↗▲]?</span>([ 0-9&#;]+)");
    private static Pattern patternPopulation2 = Pattern.compile("class=\"infobox-header\" style=\"[\\w\\d:#;]+\">Население[</\\w\\d\\s>\":#;%=]+Население[</\\w\\d\\s>\":#;%-=]+[↘↗▲]?<span class=\"nowrap\">([ 0-9&#;]+)");

    public static ArrayList<String> getCities() throws IOException {
        String[] letters = new String[]{"A", "B", "V", "G", "D", "E", "Zh", "Z", "I", "Ii", "K", "L", "M", "N", "O", "P", "R", "S", "T", "U", "F", "H", "Ts", "Ch", "Sh", "Sch", "Ye", "Yu", "Ya"};
        ArrayList<String> cities = new ArrayList<String>();
        for (var letter : letters) {
            URL url = new URL("http://www.1000mest.ru/city" + letter);
            BufferedReader br = new BufferedReader(new InputStreamReader(url.openConnection().getInputStream()));
            String s;
            StringBuilder str = new StringBuilder();
            while ((s = br.readLine()) != null)
                str.append(s);
            br.close();
            Matcher matcher = patternRawСities.matcher(str.toString());
            matcher.find();
            var res = matcher.group(1).replace("</tr>", ",");
            var citiesOnLetter = patternCities.matcher(res).replaceAll("").split(",");
            for (var city : citiesOnLetter)
                cities.add(city);
        }
        return cities;
    }

    public static Integer getStatistics(String city) throws IOException {
        String population = "0";
        var res = getCityPopulation(city);
        if (res != null)
            population = res;
        if (population.equals("0")) {
            res = getCityPopulation(city + " (город)");
            if (res != null)
                population = res;
        }
        System.out.println(city + " " + population);
        return Integer.parseInt(population);
    }

    private static String getCityPopulation(String city) throws IOException {
        StringBuilder str;
        var html = getHTML(city);
        if (html != null)
            str = html;
        else
            return null;
        String res = "";
        var resMatch = matchCities(str, patternPopulation1);
        if (resMatch != null)
            res = resMatch;
        if (res.equals("&#32;") || res.equals("&#160;") || res.strip().equals("")) {
            resMatch = matchCities(str, patternPopulation2);
            if (resMatch != null)
                res = resMatch;
        }
        if (res.equals("&#32;") || res.equals("&#160;") || res.strip().equals(""))
            res = "0";
        return res.replace("&#160;", "").replace("&#32;", "").replace(" ", "");
    }

    private static StringBuilder getHTML(String city) throws IOException {
        try {
            city = city.replace(" ", "_");
            URL url = new URL("https://ru.wikipedia.org/wiki/" + city);
            BufferedReader br = new BufferedReader(new InputStreamReader(url.openConnection().getInputStream()));
            String s;
            StringBuilder str = new StringBuilder();
            while ((s = br.readLine()) != null)
                str.append(s);
            br.close();
            return str;
        }
        catch (FileNotFoundException e) {
            return null;
        }
    }

    private static String matchCities(StringBuilder str, Pattern pattern) {
        try {
            Matcher matcher = pattern.matcher(str.toString());
            matcher.find();
            return matcher.group(1);
        }
        catch (IllegalStateException e) {
            return null;
        }
    }
}
