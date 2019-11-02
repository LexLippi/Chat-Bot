package chat_bot.game;

import chat_bot.Data;

public class GameFactory {
    private Data data;

    public GameFactory(){
        data = new Data();
    }

    public IGame getGame(GameType type){
        switch (type){
            case CityGame:
                return getCityGame();
            default:
                throw new IllegalArgumentException("incorrect game type");
        }
    }

    private IGame getCityGame(){
        var new_data = data.clone();
        var game = new CityGame(new_data);
        return game;
    }
}