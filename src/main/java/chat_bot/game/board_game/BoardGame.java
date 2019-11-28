package chat_bot.game.board_game;

import chat_bot.game.IGame;
import chat_bot.game.return_types.GameExitType;
import chat_bot.game.return_types.GameReturnedValue;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BoardGame implements IGame {

    private BoardWithCompositions board;

    public BoardGame(BoardData data){
        var words = data.getWords(8);
        for (var i = 0; i < words.length; i++) {
            System.out.print(words[i]);
            System.out.print(" ");
        }
        System.out.println();
        var wordGroups = new ArrayList<ArrayList<String>>();
        Permutate(new ArrayList<>(), words, wordGroups);
        board = new BoardWithCompositions(wordGroups.get(0));
        for (var i = 1; i < wordGroups.size(); i++){
            board.AddWords(wordGroups.get(i));
        }
        this.board.generate();
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
