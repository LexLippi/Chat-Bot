package chat_bot.game;

import chat_bot.Data;

public class Hard extends GameLevel{
    protected Hard(Data data) {
        this.data = data;
    }

    @Override
    public String computeCity(Character lastLetter) {
        if (data.cities.get(lastLetter).isEmpty())
            return null;
        var min = Integer.MIN_VALUE;
        var myCities = data.cities.get(lastLetter).iterator();
        var bestCity = "";
        while (myCities.hasNext())
        {
            var currentCity = myCities.next();
            var currentLastLetter = getCityLastLetter(currentCity);
            if (data.countCities.get(currentLastLetter) == 0)
            {
                bestCity = currentCity;
                waitingLetter = currentLastLetter;
                break;
            }
            if (data.countCities.get(currentLastLetter) < min)
            {
                min = data.countCities.get(currentLastLetter);
                bestCity = currentCity;
                waitingLetter = currentLastLetter;
            }
        }
        data.cities.get(lastLetter).remove(bestCity);
        return bestCity;
    }

    private Character getCityLastLetter(String city) {
        var i = 1;
        var currentLastLetter = city.toUpperCase().charAt(city.length() - i);
        while (data.stopLetters.contains(currentLastLetter))
        {
            ++i;
            currentLastLetter = city.toUpperCase().charAt(city.length() - i);
        }
        return currentLastLetter;
    }
}
