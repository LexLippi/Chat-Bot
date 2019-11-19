package chat_bot.game.board_game;

import chat_bot.game.IGame;
import chat_bot.game.return_types.GameExitType;
import chat_bot.game.return_types.GameReturnedValue;

import java.util.Random;

public class BoardGame implements IGame {

    private String[] words = {"апельсин",
            "груша",
            "мандарин",
            "яблоко",
            "банан",
            "киви",
            "манго",
            "арбуз",
            "клубника",
            "малина",
            "огурец"};

    private Board board;

    public BoardGame(){
        var rand = new Random();
        board = new Board();
        for (var i = 0; i < 5; i++){
            var index = rand.nextInt(words.length);
            board.addWord(words[index]);
        }
        board.generateField();
    }

    @Override
    public GameReturnedValue process(String answer) {
        if (answer.toLowerCase().compareTo("стоп") == 0) {
            return new GameReturnedValue(GameExitType.GAME_INTERRUPTED, "Приходи еще!");
        }
        if (answer.toLowerCase().compareTo("сдаюсь") == 0) {
            return new GameReturnedValue(GameExitType.PLAYER_LOOSE,
                    "Ничего, в другой раз повезет!");
        }
        if (board.containsWord(answer)) {
            return new GameReturnedValue(null, "нашел!", board.getField());
        }
        else {
            return new GameReturnedValue(null, "такого слова нет :(", board.getField());
        }
    }

    @Override
    public GameReturnedValue startGame() {
        var a1 = "тебе нужно найти все слова на доске";
        var a3 = "правда, закончиться эта игра пока не может";
        var a2 = board.getField();
        return new GameReturnedValue(null, a1, a3, a2);
    }
}
