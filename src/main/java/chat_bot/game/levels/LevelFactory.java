package chat_bot.game.levels;

public class LevelFactory {
    public GameLevel getLevel(String level) {
        if (level.compareTo("легкий") == 0) {
            return new Easy();
        }
        else if (level.compareTo("средний") == 0) {
            return new Medium();
        }
        else if (level.compareTo("тяжелый") == 0) {
            return new Hard();
        }
        else {
            throw new IllegalArgumentException();
        }
    }
}
