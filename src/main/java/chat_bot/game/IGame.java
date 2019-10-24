package chat_bot.game;

import chat_bot.game.return_types.GameReturnedValue;

public interface IGame {
    public GameReturnedValue process(String answer);
    public GameReturnedValue startGame();
}
