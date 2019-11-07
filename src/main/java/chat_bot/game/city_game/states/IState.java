package chat_bot.game.city_game.states;

import chat_bot.game.city_game.levels.GameLevel;
import chat_bot.game.return_types.GameReturnedValue;

public interface IState {
    public GameReturnedValue processCommand(String inputString, GameLevel level);

    public GameReturnedValue getIllegalGameLevel();
}
