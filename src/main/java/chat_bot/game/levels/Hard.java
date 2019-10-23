package chat_bot.game.levels;

import chat_bot.Api;
import chat_bot.Data;
import chat_bot.game.levels.GameLevel;

import java.util.ArrayList;

public class Hard extends GameLevel {
    public Hard(Data data, Api api) {
        step_counter = 100;
        this.data = data;
        this.api = api;
    }

    @Override
    public String computeCity(Character lastLetter) {
        if (data.getCities().get(lastLetter).isEmpty())
            return null;
        var min = Integer.MAX_VALUE;
        var myCities = data.getCities().get(lastLetter).keySet().iterator();
        var bestCities = new ArrayList<String>();
        while (myCities.hasNext())
        {
            var currentCity = myCities.next();
            var currentLastLetter = getCityLastLetter(currentCity);
            if (data.getStatistics(currentLastLetter) < min)
            {
                min = data.getStatistics(currentLastLetter);
                bestCities.clear();
                bestCities.add(currentCity);
            }
            else if (data.getStatistics(currentLastLetter) == min) {
                bestCities.add(currentCity);
            }
        }
        return getRandomListElement(bestCities);
    }
}
