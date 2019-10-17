package chat_bot.game.levels;

import chat_bot.Api;
import chat_bot.Data;
import chat_bot.DataNew;

import java.util.ArrayList;

public class Medium extends GameLevel {
    public Medium(DataNew data, Api api) {
        step_counter = 50;
        this.data = data;
        this.api = api;
    }

    @Override
    public String computeCity(Character lastLetter) {
        var myCities = data.getCities().get(lastLetter);
        if (myCities.isEmpty()) {
            return null;
        }
        var answerCity = getRandomListElement(new ArrayList<>(myCities.keySet()));
        waitingLetter = getCityLastLetter(answerCity);
        data.getCities().get(lastLetter).remove(answerCity);
        data.putCountCities(lastLetter, data.getCountCities().get(lastLetter) - 1);
        return answerCity;
    }
}
