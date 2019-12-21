package chat_bot.game.board_game;

import chat_bot.Api;
import chat_bot.game.IGame;
import chat_bot.game.board_game.board_levels.BoardLevel;
import chat_bot.game.board_game.board_levels.Easy;
import chat_bot.game.board_game.board_levels.Hard;
import chat_bot.game.board_game.board_levels.Medium;
import chat_bot.game.return_types.GameExitType;
import chat_bot.game.return_types.GameReturnedValue;

import java.util.ArrayList;
import java.util.HashMap;

public class BoardGame implements IGame {

    private BoardWithCompositions board;
    private BoardLevel level;

    public BoardGame(){
    }

    public BoardLevel getLevel() {
        return level;
    }

    private void generateBoard(int wordsNum){
        var words = level.getWords(wordsNum);
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
    public GameReturnedValue process(String answer, Api api) {
        answer = answer.toLowerCase();
        var words_num = 0;
        if (level == null){
            switch (answer){
                case "легкий":
                    level = new Easy();
                    words_num = 4;
                    break;
                case "средний":
                    level = new Medium();
                    words_num = 4;
                    break;
                case "сложный":
                    level = new Hard();
                    words_num = 4;
                    break;
                default:
                    return new GameReturnedValue(null, "нет такого уровня!");
            }
            generateBoard(words_num);
            return new GameReturnedValue(null, "тебе нужно найти " + words_num + " слов", board.getField());
        }
        else {
            if (answer.toLowerCase().compareTo("стоп") == 0) {
                return new GameReturnedValue(GameExitType.GAME_INTERRUPTED, "Приходи еще!");
            }
            if (answer.toLowerCase().compareTo("сдаюсь") == 0) {
                return new GameReturnedValue(GameExitType.PLAYER_LOOSE,
                        "Ничего, в другой раз повезет!");
            }
            if (board.containsWord(answer)) {
                return new GameReturnedValue(null, "нашел!", board.getField());
            } else {
                return new GameReturnedValue(null, "такого слова нет :(", board.getField());
            }
        }
    }

    @Override
    public GameReturnedValue startGame(Api api) {
        var a1 = "выбери уровень сложности: легкий, средний, сложный";
        return new GameReturnedValue(null, a1);
    }

    @Override
    public ArrayList<String> getAnswerWariants() {
        var buttons = new ArrayList<String>();
        if (level == null) {
            buttons.add("Легкий");
            buttons.add("Средний");
            buttons.add("Сложный");
        }
        else{
            buttons.add("Сдаюсь");
            buttons.add("Стоп");
        }
        return buttons;
    }
}
