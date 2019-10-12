package chat_bot.game;

import chat_bot.Data;

public class Easy extends GameLevel {
    protected Easy(Data data) {
        this.data = data;
    }

    @Override
    public String computeCity(Character lastLetter) {
        if (data.cities.get(lastLetter).isEmpty()) {
            return null;
        }
        var max = Integer.MIN_VALUE;
        var myCities = data.cities.get(lastLetter).iterator();
        var bestCity = "";
        while (myCities.hasNext()) {
            var currentCity = myCities.next();
            var currentLastLetter = getCityLastLetter(currentCity);
            if (data.countCities.get(currentLastLetter) > max) {
                max = data.countCities.get(currentLastLetter);
                bestCity = currentCity;
                waitingLetter = currentLastLetter;
            }
        }
        return bestCity;
    }
}
