package chat_bot.game.city_game.states;

import chat_bot.game.city_game.levels.GameLevel;
import chat_bot.game.return_types.GameReturnedValue;

abstract public class State implements IState {
    public State nextState;

    abstract public GameReturnedValue processCommand(String inputString, GameLevel level);

    abstract public GameReturnedValue getIllegalGameLevel();
}
