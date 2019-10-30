package chat_bot.game.levels;

import chat_bot.Data;
import java.util.ArrayList;

public class Medium extends GameLevel {
    public Medium() {
        step_counter = 50;
        this.data = new Data();
    }

    public Medium(Data data) {
        step_counter = 50;
        this.data = data;
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
            if (data.getStatistics(currentLastLetter) <= 0.05 && data.getStatistics(currentLastLetter) >= 0.01) {
                cities.add(currentCity);
            }
        }
        if (cities.isEmpty()) {
            return null;
        }
        System.out.println(cities);
        return getRandomListElement(cities);
    }
}
