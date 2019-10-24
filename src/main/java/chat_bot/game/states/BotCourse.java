package chat_bot.game.states;

import chat_bot.game.levels.GameLevel;
import chat_bot.game.return_types.GameReturnedValue;

public class BotCourse extends State {
    public BotCourse() {
        this.nextState = this;
    }

    @Override
    public GameReturnedValue processCommand(String inputString, GameLevel level) {
        return level.processingUserCourse(inputString);
    }

    @Override
    public GameReturnedValue getIllegalGameLevel() {
        return null;
    }
}
