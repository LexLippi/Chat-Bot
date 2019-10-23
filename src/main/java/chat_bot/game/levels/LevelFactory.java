package chat_bot.game.levels;

import chat_bot.Api;
import chat_bot.Data;

public class LevelFactory {
    private Data data;
    private Api api;

    public LevelFactory(Data data, Api api) {
        this.data = data;
        this.api = api;
    }

    public GameLevel getLevel(String level) {
        if (level.compareTo("легкий") == 0) {
            return new Easy(data, api);
        }
        else if (level.compareTo("средний") == 0) {
            return new Medium(data, api);
        }
        else if (level.compareTo("тяжелый") == 0) {
            return new Hard(data, api);
        }
        else if (level.compareTo("сдаюсь") == 0) {
            return null;
        }
        else {
            api.out("У меня еще нет такого уровня. Попробуй выбрать уровень снова!");
            throw new IllegalArgumentException();
        }
    }
}
