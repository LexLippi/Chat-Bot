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
        var myCities = data.getCities().get(lastLetter);
        if (myCities.isEmpty()) {
            return null;
        }
        return getRandomListElement(new ArrayList<>(myCities.keySet()));
    }
}
