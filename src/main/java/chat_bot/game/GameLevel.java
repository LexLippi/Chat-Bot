package chat_bot.game;

import chat_bot.Data;

abstract class GameLevel implements DifficultLevel {
    protected Data data;
    protected Character waitingLetter = null;

    abstract public String computeCity(Character lastLetter);

    protected Character getCityLastLetter(String city) {
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
