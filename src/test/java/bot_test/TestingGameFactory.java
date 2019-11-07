package bot_test;

import chat_bot.game.city_game.City;
import chat_bot.game.city_game.Data;
import chat_bot.game.city_game.CityGame;
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
