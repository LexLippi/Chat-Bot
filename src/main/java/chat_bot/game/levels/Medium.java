package chat_bot.game.levels;

import chat_bot.Data;

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
        var cityIndex = rnd.nextInt(myCities.size());
        var answerCity = (String) myCities.toArray()[cityIndex];
        waitingLetter = getCityLastLetter(answerCity);
        return answerCity;
    }
}
