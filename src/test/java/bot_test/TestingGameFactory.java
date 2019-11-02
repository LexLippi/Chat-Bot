package bot_test;

import chat_bot.City;
import chat_bot.Data;
import chat_bot.game.CityGame;
import chat_bot.game.GameType;
import chat_bot.game.IGame;
import chat_bot.game.IGameFactory;

import java.util.ArrayList;

public class TestingGameFactory implements IGameFactory {

    public ArrayList<GameType> calls = new ArrayList<>();

    @Override
    public IGame getGame(GameType type) {
        calls.add(type);
        return new CityGame(new Data(new City[]{}));
    }
}
