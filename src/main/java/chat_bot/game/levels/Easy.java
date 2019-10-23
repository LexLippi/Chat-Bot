package chat_bot.game.levels;

import chat_bot.Api;
import chat_bot.Data;

import java.util.ArrayList;

public class Easy extends GameLevel {
    public Easy(Data data, Api api) {
        step_counter = 10;
        this.data = data;
        this.api = api;
    }

    @Override
    public String computeCity(Character lastLetter) {
        if (data.getCities().get(lastLetter).isEmpty()) {
            return null;
        }
        var max = Integer.MIN_VALUE;
        var myCities = data.getCities().get(lastLetter).keySet().iterator();
        var bestCities = new ArrayList<String>();
        while (myCities.hasNext()) {
            var currentCity = myCities.next();
            var currentLastLetter = getCityLastLetter(currentCity);
            if (data.getStatistics(currentLastLetter) > max) {
                max = data.getStatistics(currentLastLetter);
                bestCities.clear();
                bestCities.add(currentCity);
            }
            else if (data.getStatistics(currentLastLetter) == max) {
                bestCities.add(currentCity);
            }
        }
        return getRandomListElement(bestCities);
    }
}
