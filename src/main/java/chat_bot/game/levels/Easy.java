package chat_bot.game.levels;

import chat_bot.Data;

import java.util.ArrayList;

public class Easy extends GameLevel {
    public Easy(Data data) {
        step_counter = 10;
        this.data = data;
    }

    @Override
    public String computeCity(Character lastLetter) {
        if (data.cities.get(lastLetter).isEmpty()) {
            return null;
        }
        var max = Integer.MIN_VALUE;
        var myCities = data.cities.get(lastLetter).iterator();
        var bestCities = new ArrayList<String>();
        while (myCities.hasNext()) {
            var currentCity = myCities.next();
            var currentLastLetter = getCityLastLetter(currentCity);
            if (data.countCities.get(currentLastLetter) > max) {
                max = data.countCities.get(currentLastLetter);
                bestCities.clear();
                bestCities.add(currentCity);
            }
            else if (data.countCities.get(currentLastLetter) == max) {
                bestCities.add(currentCity);
            }
        }
        var bestCity = getRandomListElement(bestCities);
        waitingLetter = getCityLastLetter(bestCity);
        data.cities.get(lastLetter).remove(bestCity);
        data.countCities.put(lastLetter, data.countCities.get(lastLetter) - 1);
        return bestCity;
    }
}
