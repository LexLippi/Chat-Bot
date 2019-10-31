package chat_bot.game.states;

import chat_bot.game.levels.GameLevel;
import chat_bot.game.levels.LevelFactory;
import chat_bot.game.return_types.GameReturnedValue;

public class SelectLevel extends State {
    public SelectLevel() {
        this.nextState = new Draw();
    }

    @Override
    public GameReturnedValue processCommand(String inputString, GameLevel level) {
        if (new LevelFactory().checkInputString(inputString)) {
            return new GameReturnedValue(null, "Пора выбрать, кто будет ходить первым! Орёл или решка?");
        }
        throw new IllegalArgumentException();
    }

    @Override
    public GameReturnedValue getIllegalGameLevel() {
        return new GameReturnedValue(null, "Я не знаю такого уровня, попробуй снова!");
    }
}
