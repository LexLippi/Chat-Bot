package chat_bot.game.levels;

import chat_bot.Api;
import chat_bot.Data;

public class LevelFactory {
    private Data data;

    public LevelFactory(Data data) {
        this.data = data;
    }

    public GameLevel getLevel(String level) {
        if (level.compareTo("легкий") == 0) {
            return new Easy(data);
        }
        else if (level.compareTo("средний") == 0) {
            return new Medium(data);
        }
        else if (level.compareTo("тяжелый") == 0) {
            return new Hard(data);
        }
        else if (level.compareTo("сдаюсь") == 0) {
            return null;
        }
        else {
            throw new IllegalArgumentException();
        }
    }
}
