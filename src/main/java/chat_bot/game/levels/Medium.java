package chat_bot.game.levels;

import chat_bot.Api;
import chat_bot.Data;

import java.util.ArrayList;

public class Medium extends GameLevel {
    public Medium(Data data, Api api) {
        step_counter = 50;
        this.data = data;
        this.api = api;
    }

    @Override
    public String computeCity(Character lastLetter) {
        if (data.getCities().get(lastLetter).isEmpty()) {
            return null;
        }
        var cities = new ArrayList<String>();
        var myCities = data.getCities().get(lastLetter).keySet().iterator();
        while (myCities.hasNext()) {
            var currentCity = myCities.next();
            var currentLastLetter = getCityLastLetter(currentCity);
            if (data.getStatistics(currentLastLetter) <= 0.7 && data.getStatistics(currentLastLetter) >= 0.3) {
                cities.add(currentCity);
            }
        }
        return getRandomListElement(cities);
    }
}
