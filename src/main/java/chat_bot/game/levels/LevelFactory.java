package chat_bot.game.levels;

public class LevelFactory {
    public GameLevel getLevel(String level) {
        level = level.toLowerCase();
        if (level.compareTo("легкий") == 0 || level.compareTo("лёгкий") == 0) {
            return new Easy();
        }
        else if (level.compareTo("средний") == 0) {
            return new Medium();
        }
        else if (level.compareTo("тяжелый") == 0 || level.compareTo("тяжёлый") == 0) {
            return new Hard();
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
