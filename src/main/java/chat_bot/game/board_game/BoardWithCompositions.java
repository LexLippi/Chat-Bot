package chat_bot.game.board_game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BoardWithCompositions {
    private final Character[] letters = {'A', 'Б', 'В', 'Г', 'Д', 'Е', 'Ё', 'Ж', 'З', 'И', 'Й', 'К', 'Л', 'М', 'Н',
            'О', 'П', 'Р', 'С', 'Т', 'У', 'Ф', 'Х', 'Ц', 'Ч', 'Ш', 'Щ', 'Ъ', 'Ы', 'Ь', 'Э', 'Ю', 'Я'};
    private String generatedField;
    private ArrayList<String> words;
    private Composition best;
    private int bestSize = 10000000;

    public BoardWithCompositions(ArrayList<String> words){
        this.words = new ArrayList<>();
        for (var w : words) {
            this.words.add(w.toUpperCase());
        }
        best = null;
        AddWords(words);
    }

    public Composition getBestComposition(){
        return best;
    }

    public void AddWords(ArrayList<String> words){
        var composes = new ArrayList<Composition>();
        for (var word : words){
            composes.add(new Composition(word));
        }
        var previousComposes = new ArrayList<Composition>();
        while (composes.size() > 0){

            previousComposes = composes;
            composes = join(composes);
        }
        for (var c : previousComposes){
            var area = c.getSize().getArea();
            if (area <= bestSize){
                best = c;
                bestSize = area;
            }
        }
    }

    public boolean containsWord(String word){
        return words.contains(word.toUpperCase());
    }

    public void generate(){
        StringBuilder result = new StringBuilder();
        var size = best.getSize();
        for (var y = size.top; y <= size.bottom; y++){
            for (var x = size.left; x <= size.right; x++){
                var letter = best.charIn(new VectorInt(x, y));
                if (letter == null){
                    letter = getRandomLetter();
                }
                result.append(letter);
            }
            if (y < size.bottom)
                result.append('\n');
        }
        generatedField = "`" +  result.toString() + "`";
    }

    private Character getRandomLetter(){
        var rand = new Random();
        var index = rand.nextInt(letters.length);
        return letters[index].toString().toLowerCase().charAt(0);
    }

    public String getField(){
        if (generatedField == null){
            throw new IllegalArgumentException("generate field first");
        }
        return generatedField;
    }

    private ArrayList<Composition> join(List<Composition> composes){
        var result = new ArrayList<Composition>();
        for (var i = 0; i < composes.size() - 1; i++){
            for (var j = i + 1; j < composes.size(); j++){
                if (composes.get(i).shareCommonWords(composes.get(j))){
                    continue;
                }
                var newCompose = Composition.getBestCombination(composes.get(i), composes.get(j));
                if (newCompose != null){
                    result.add(newCompose);
                }
            }
        }
        return result;
    }
}
