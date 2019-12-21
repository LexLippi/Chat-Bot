package chat_bot.game;

import chat_bot.Api;
import chat_bot.game.return_types.GameReturnedValue;

import java.util.ArrayList;

public interface IGame {
    public GameReturnedValue process(String answer, Api api);
    public GameReturnedValue startGame(Api api);
    public ArrayList<String> getAnswerWariants();
}
