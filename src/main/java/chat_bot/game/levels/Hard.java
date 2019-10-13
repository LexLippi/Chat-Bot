package chat_bot.game.levels;

import chat_bot.Data;
import chat_bot.game.levels.GameLevel;

import java.util.ArrayList;

public class Hard extends GameLevel {
    public Hard(Data data) {
        step_counter = 100;
        this.data = data;
    }

    @Override
    public String computeCity(Character lastLetter) {
        if (data.cities.get(lastLetter).isEmpty())
            return null;
        var min = Integer.MAX_VALUE;
        var myCities = data.cities.get(lastLetter).iterator();
        var bestCities = new ArrayList<String>();
        while (myCities.hasNext())
        {
            var currentCity = myCities.next();
            var currentLastLetter = getCityLastLetter(currentCity);
            if (data.countCities.get(currentLastLetter) < min)
            {
                min = data.countCities.get(currentLastLetter);
                bestCities.clear();
                bestCities.add(currentCity);
            }
            else if (data.countCities.get(currentLastLetter) == min) {
                bestCities.add(currentCity);
            }
        }
        var bestCity = getRandomListElement(bestCities);
        waitingLetter = getCityLastLetter(bestCity);
        data.cities.get(lastLetter).remove(bestCity);
        return bestCity;
    }
}
