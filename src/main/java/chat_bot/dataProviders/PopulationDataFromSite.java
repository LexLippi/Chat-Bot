package chat_bot.dataProviders;

import java.io.*;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PopulationDataFromSite {
    private final Pattern patternRawCities;
    private final Pattern patternCities;
    private final Pattern patternPopulation1 = Pattern.compile("class=\"infobox-header\" style=\"[\\w\\d:#;]+\">Население[</\\w\\d\\s>\":#;%=]+Население[</\\w\\d\\s>\":#;%-=]+[↘↗▲]?</span>([ 0-9&#;]+)");
    private final Pattern patternPopulation2 = Pattern.compile("class=\"infobox-header\" style=\"[\\w\\d:#;]+\">Население[</\\w\\d\\s>\":#;%=]+Население[</\\w\\d\\s>\":#;%-=]+[↘↗▲]?<span class=\"nowrap\">([ 0-9&#;]+)");

    public PopulationDataFromSite(Pattern patternRawCities, Pattern patternCities){
        this.patternRawCities = patternRawCities;
        this.patternCities = patternCities;
    }

    public String[] getCities(String site) {
        try{
            URL url = new URL(site);
            BufferedReader br = new BufferedReader(new InputStreamReader(url.openConnection().getInputStream()));
            String s;
            StringBuilder str = new StringBuilder();
            while ((s = br.readLine()) != null)
                str.append(s);
            br.close();
            Matcher matcher = patternRawCities.matcher(str.toString());
            matcher.find();
            var res = matcher.group(1).replace("</tr>", ",");
            var citiesOnLetter = patternCities.matcher(res).replaceAll("").split(",");
            return citiesOnLetter;
        }
        catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }

    public Integer getStatistics(String city) {
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

    private String getCityPopulation(String city) {
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

    private StringBuilder getHTML(String city)  {
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
        catch (IOException e) {
            return null;
        }
    }

    private String matchCities(StringBuilder str, Pattern pattern) {
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
