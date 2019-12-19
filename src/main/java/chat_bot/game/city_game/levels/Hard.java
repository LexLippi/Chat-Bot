package chat_bot.game.city_game.levels;

import chat_bot.game.city_game.Data;
import java.util.ArrayList;

public class Hard extends GameLevel {

    public Hard(Data data) {
        step_counter = 100;
        this.data = data;
        this.maxPercent = getMaxPercent();
    }

    @Override
    public String computeCity(Character lastLetter) {
        if (data.getCities().get(lastLetter).isEmpty())
            return null;
        var myCities = data.getCities().get(lastLetter).keySet().iterator();
        var bestCities = new ArrayList<String>();
        while (myCities.hasNext())
        {
            var currentCity = myCities.next();
            var currentLastLetter = getCityLastLetter(currentCity);
            if (data.getStatistics(currentLastLetter) <= maxPercent)
            {
                bestCities.add(currentCity);
            }
        }
        if (bestCities.isEmpty()) {
            return null;
        }
        return getRandomListElement(bestCities);
    }

    @Override
    public String getStrName() {
        return "Hard";
    }

    private Double getMaxPercent() {
        var percents = data.getPercents();
        int index = getLetterCount() / 3;
        return percents.get(index);
    }
}
