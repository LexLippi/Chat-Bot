package chat_bot.game.levels;

import chat_bot.Data;
import java.util.ArrayList;

public class Hard extends GameLevel {
    public Hard() {
        step_counter = 100;
        this.data = new Data();
    }

    public Hard(Data data) {
        step_counter = 100;
        this.data = data;
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
            System.out.println(currentLastLetter);
            System.out.println(data.getStatistics(currentLastLetter));
            if (data.getStatistics(currentLastLetter) < 0.01)
            {
                bestCities.add(currentCity);
            }
        }
        if (bestCities.isEmpty()) {
            return null;
        }
        System.out.println(bestCities);
        return getRandomListElement(bestCities);
    }
}
