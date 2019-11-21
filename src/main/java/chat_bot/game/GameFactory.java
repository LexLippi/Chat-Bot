package chat_bot.game;

import chat_bot.game.board_game.BoardData;
import chat_bot.game.board_game.BoardGame;
import chat_bot.game.city_game.Data;
import chat_bot.game.city_game.CityGame;

public class GameFactory implements IGameFactory{
    private Data cityData;
    private BoardData boardData;

    public GameFactory(){
        cityData = new Data();
        System.out.print(cityData.getCities());
        boardData = new BoardData();
    }

    public IGame getGame(GameType type){
        switch (type){
            case CityGame:
                return getCityGame();
            case BoardGame:
                return new BoardGame(boardData);
            default:
                throw new IllegalArgumentException("incorrect game type");
        }
    }

    private IGame getCityGame(){
        var new_data = cityData.clone();
        return new CityGame(new_data);
    }
}
