package chat_bot.game.levels;

import chat_bot.Data;

import java.util.ArrayList;

public class Medium extends GameLevel {
    public Medium(Data data) {
        step_counter = 50;
        this.data = data;
    }

    @Override
    public String computeCity(Character lastLetter) {
        var myCities = data.cities.get(lastLetter);
        if (myCities.isEmpty()) {
            return null;
        }
        var answerCity = getRandomListElement(new ArrayList<>(myCities));
        waitingLetter = getCityLastLetter(answerCity);
        data.cities.get(lastLetter).remove(answerCity);
        data.countCities.put(lastLetter, data.countCities.get(lastLetter) - 1);
        return answerCity;
    }
}
