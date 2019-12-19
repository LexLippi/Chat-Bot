package chat_bot.game.city_game.levels;

import chat_bot.game.city_game.Data;
import java.util.ArrayList;

public class Medium extends GameLevel {

    public Medium(Data data) {
        step_counter = 50;
        this.data = data;
        var boundPercents = getBoundPercents();
        this.minPercent = boundPercents[0];
        this.maxPercent = boundPercents[1];
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
            if (data.getStatistics(currentLastLetter) <= maxPercent && data.getStatistics(currentLastLetter) >= minPercent) {
                cities.add(currentCity);
            }
        }
        if (cities.isEmpty()) {
            return null;
        }
        return getRandomListElement(cities);
    }

    @Override
    public String getStrName() {
        return "Medium";
    }

    private Double[] getBoundPercents() {
        var percents = data.getPercents();
        int minIndex = getLetterCount() / 3;
        int maxIndex = percents.size() - 1 - minIndex;
        return new Double[] {percents.get(minIndex), percents.get(maxIndex)};

    }
}
