package chat_bot.game.levels;

import chat_bot.Api;
import chat_bot.Data;
import chat_bot.DataNew;

import java.util.ArrayList;

public class Easy extends GameLevel {
    public Easy(DataNew data, Api api) {
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
            if (data.getCountCities().get(currentLastLetter) > max) {
                max = data.getCountCities().get(currentLastLetter);
                bestCities.clear();
                bestCities.add(currentCity);
            }
            else if (data.getCountCities().get(currentLastLetter) == max) {
                bestCities.add(currentCity);
            }
        }
        var bestCity = getRandomListElement(bestCities);
        waitingLetter = getCityLastLetter(bestCity);
        data.getCities().get(lastLetter).remove(bestCity);
        data.putCountCities(lastLetter, data.getCountCities().get(lastLetter) - 1);
        return bestCity;
    }
}
