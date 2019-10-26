package chat_bot;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PopulationData
{
    private static Pattern patternRawСities = Pattern.compile("<table><tbody>([А-ЯЕЁа-яеё<>trd/a href=\"a-z_()-.]+)</tbody>");
    private static Pattern patternCities = Pattern.compile("<tr>|<td>|<a href=\"[\\w-]+\">|</a>|</td>| \\([А-ЯЁЕа-яёе.]+\\)|/ [а-яеёА-ЯЕЁ]+");
    private static Pattern patternPopulation1 = Pattern.compile("class=\"infobox-header\" style=\"[\\w\\d:#;]+\">Население[</\\w\\d\\s>\":#;%=]+Население[</\\w\\d\\s>\":#;%-=]+[↘↗▲]?</span>([ 0-9&#;]+)");
    private static Pattern patternPopulation2 = Pattern.compile("class=\"infobox-header\" style=\"[\\w\\d:#;]+\">Население[</\\w\\d\\s>\":#;%=]+Население[</\\w\\d\\s>\":#;%-=]+[↘↗▲]?<span class=\"nowrap\">([ 0-9&#;]+)");

    public static ArrayList<String> getCities() throws IOException
    {
        String[] letters = new String[]{"A", "B", "V", "G", "D", "E", "Zh", "Z", "I", "Ii", "K", "L", "M", "N", "O", "P", "R", "S", "T", "U", "F", "H", "Ts", "Ch", "Sh", "Sch", "Ye", "Yu", "Ya"};
        ArrayList<String> cities = new ArrayList<String>();
        for (var letter : letters)
        {
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

    public static Integer getStatistics(String city) throws IOException
    {
        String population = "0";
        try
        {
            population = getCityPopulation(city);
        }
        catch (FileNotFoundException e)
        {
            //ignore
        }
        if (population.equals("0"))
        {
            try
            {
                population = getCityPopulation(city + " (город)");
            }
            catch (FileNotFoundException e)
            {
                //ignore
            }
        }

        return Integer.parseInt(population);
    }

    private static String getCityPopulation(String city) throws IOException
    {
        city = city.replace(" ", "_");
        URL url = new URL("https://ru.wikipedia.org/wiki/" + city);
        BufferedReader br = new BufferedReader(new InputStreamReader(url.openConnection().getInputStream()));
        String s;
        StringBuilder str = new StringBuilder();
        while((s = br.readLine())!=null)
            str.append(s);
        br.close();
        String res = "";
        try
        {
            Matcher matcher = patternPopulation1.matcher(str.toString());
            matcher.find();
            res = matcher.group(1);
        }
        catch (IllegalStateException e)
        {
            //ignore
        }
        if (res.equals("&#32;") || res.equals("&#160;") || res.strip().equals(""))
        {
            try
            {
                Matcher matcher = patternPopulation2.matcher(str.toString());
                matcher.find();
                res = matcher.group(1);
            }
            catch (IllegalStateException e)
            {
                //ignore
            }
        }
        if (res.equals("&#32;") || res.equals("&#160;") || res.strip().equals(""))
            res = "0";
        return res.replace("&#160;", "").replace("&#32;", "").replace(" ", "");
    }
}
