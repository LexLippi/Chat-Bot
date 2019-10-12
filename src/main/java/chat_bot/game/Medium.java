package chat_bot.game;

import chat_bot.Data;
import java.util.Random;

public class Medium extends GameLevel {
    protected Medium(Data data) {
        this.data = data;
    }

    @Override
    public String computeCity(Character lastLetter) {
        var myCities = data.cities.get(lastLetter);
        if (myCities.isEmpty()) {
            return null;
        }
        var rnd = new Random();
        var cityIndex = rnd.nextInt(myCities.size());
        var answerCity = (String) myCities.toArray()[cityIndex];
        waitingLetter = getCityLastLetter(answerCity);
        return answerCity;
    }
}
