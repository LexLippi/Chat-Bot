package chat_bot.game.city_game.levels;

import chat_bot.game.city_game.Data;

public class LevelFactory {
    public GameLevel getLevel(String level, Data data) {
        level = level.toLowerCase();
        if (level.compareTo("легкий") == 0 || level.compareTo("лёгкий") == 0) {
            return new Easy(data);
        }
        else if (level.compareTo("средний") == 0) {
            return new Medium(data);
        }
        else if (level.compareTo("тяжелый") == 0 || level.compareTo("тяжёлый") == 0) {
            return new Hard(data);
        }
        else {
            throw new IllegalArgumentException();
        }
    }

    public Boolean checkInputString(String inputString) {
        return inputString.compareTo("легкий") == 0 || inputString.compareTo("лёгкий") == 0 ||
                inputString.compareTo("тяжелый") == 0 || inputString.compareTo("тяжёлый") == 0 ||
                inputString.compareTo("средний") == 0;
    }
}
