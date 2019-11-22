package chat_bot.game.board_game;

import chat_bot.game.IGame;
import chat_bot.game.return_types.GameExitType;
import chat_bot.game.return_types.GameReturnedValue;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BoardGame implements IGame {

    private Board board;

    public BoardGame(BoardData data){
        var words = data.getWords(5);
        var wordGroups = new ArrayList<ArrayList<String>>();
        Permutate(new ArrayList<>(), words, wordGroups);
        var best_length = 1000000000000000d;
        Board board = new Board();
        for (var group : wordGroups){
            var current = new Board();
            for (var word: words){
                current.addWord(word);
            }
            if (current.getSize() < best_length){
                board = current;
                best_length = current.getSize();
            }
        }
        this.board = board;
        this.board.generateField();
    }

    private void Permutate(ArrayList<String> current, String[] original, ArrayList<ArrayList<String>> result){
        if (current.size() == original.length){
            result.add(current);
        }
        for (var string : original){
            if (current.contains(string)){
                continue;
            }
            var newCurrent = (ArrayList<String>)current.clone();
            newCurrent.add(string);
            Permutate(newCurrent, original, result);
        }
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
