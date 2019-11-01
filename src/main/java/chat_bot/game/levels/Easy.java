package chat_bot.game.levels;

import chat_bot.Data;

import java.util.ArrayList;

public class Easy extends GameLevel {

    public Easy(Data data) {
        step_counter = 10;
        this.data = data;
        this.minPercent = getMinPercent();
    }

    @Override
    public String computeCity(Character lastLetter) {
        if (data.getCities().get(lastLetter).isEmpty()) {
            return null;
        }
        var myCities = data.getCities().get(lastLetter).keySet().iterator();
        var bestCities = new ArrayList<String>();
        while (myCities.hasNext()) {
            var currentCity = myCities.next();
            var currentLastLetter = getCityLastLetter(currentCity);
            if (data.getStatistics(currentLastLetter) >= minPercent) {
                bestCities.add(currentCity);
            }
        }
        if (bestCities.isEmpty()) {
            return null;
        }
        return getRandomListElement(bestCities);
    }

    private Double getMinPercent() {
        var percents = data.getPercents();
        int index = percents.size() - 1 - getLetterCount() / 3;
        return data.getPercents().get(index);
    }
}
