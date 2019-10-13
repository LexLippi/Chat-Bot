package chat_bot.game.levels;

import chat_bot.Data;
import chat_bot.game.levels.DifficultLevel;

import java.util.Random;

abstract public class GameLevel implements DifficultLevel {
    protected Data data;
    protected Character waitingLetter = null;
    protected Integer step_counter;
    public Random rnd = new Random();

    abstract public String computeCity(Character lastLetter);

    public Character getCityLastLetter(String city) {
        var i = 1;
        var currentLastLetter = city.toUpperCase().charAt(city.length() - i);
        while (data.stopLetters.contains(currentLastLetter))
        {
            ++i;
            currentLastLetter = city.toUpperCase().charAt(city.length() - i);
        }
        return currentLastLetter;
    }

    public void inc_step_counter() {
        if (step_counter > 0) {
            step_counter--;
        }
    }

    public Boolean is_step_counter_empty() {
        return step_counter == 0;
    }
}
