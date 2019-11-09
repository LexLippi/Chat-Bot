package chat_bot.game.board_game;

import chat_bot.game.IGame;
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
        if (board.containsWord(answer)){
            return new GameReturnedValue(null, "нашел!", board.getFild());
        }
        else{
            return new GameReturnedValue(null, "такого слова нет :(", board.getFild());
        }
    }

    @Override
    public GameReturnedValue startGame() {
        var a1 = "тебе нужно найти все слова на доске";
        var a3 = "правда, закончиться эта игра пока не может";
        var a2 = board.getFild();
        return new GameReturnedValue(null, a1, a3, a2);
    }
}
