package chat_bot.game.board_game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Board {
    private final Character[] letters = {'A', 'Б', 'В', 'Г', 'Д', 'Е', 'Ё', 'Ж', 'З', 'И', 'Й', 'К', 'Л', 'М', 'Н',
            'О', 'П', 'Р', 'С', 'Т', 'У', 'Ф', 'Х', 'Ц', 'Ч', 'Ш', 'Щ', 'Ъ', 'Ы', 'Ь', 'Э', 'Ю', 'Я'};
    private HashMap<VectorInt, BoardCell> field;
    private String generatedField;
    private ArrayList<String> words;
    private int outerLeft = 0;
    private int outerTop = 0;
    private int outerBottom = 0;
    private int outerRight = 0;
    private int innerLeft = 0;
    private int innerTop = 0;
    private int innerBottom = 0;
    private int innerRight = 0;

    public Board(){
        field = new HashMap<>();
        this.words = new ArrayList<>();
    }

    public void addWord(String word){
        var horizontalPlace = findPlace(word.toUpperCase(), true);
        var verticalPlace = findPlace(word.toUpperCase(), false);
        if (horizontalPlace.IntersectionNumber > verticalPlace.IntersectionNumber){
            placeWord(horizontalPlace);
        }
        else{
            placeWord(verticalPlace);
        }
    }

    public void generateField(){
        StringBuilder result = new StringBuilder();
        for (var y = outerTop; y <= outerBottom; y++){
            for (var x = outerLeft; x <= outerRight; x++){
                var pos = new VectorInt(x, y);
                if (field.containsKey(pos)){
                    result.append(' ');
                    result.append(field.get(pos).getCharacter());
                    result.append(' ');
                }
                else{
                    result.append(' ');
                    result.append(getRandomLetter().toString().toLowerCase());
                    result.append(' ');
                }
            }
            result.append('\n');
        }
        result.deleteCharAt(result.length() - 1);
        generatedField = "`" + result.toString() + "`";
    }

    public boolean containsWord(String word){
        return words.contains(word.toUpperCase());
    }

    private Character getRandomLetter(){
        var rand = new Random();
        var index = rand.nextInt(letters.length);
        return letters[index];
    }

    public String getField(){
        if (generatedField == null){
            throw new IllegalArgumentException("generate field before getting it");
        }
        return generatedField;
    }

    private void placeWord(WordInfo word){
        var dx = 0;
        var dy = 0;
        if (word.IsHorizontal){
            dx = 1;
        }
        else{
            dy = 1;
        }

        for (var i = 0; i < word.word.length(); i++){
            var pos = new VectorInt(word.Position.X + dx * i, word.Position.Y + dy * i);
            if (!field.containsKey(pos)){
                field.put(pos, new BoardCell());
            }
            field.get(pos).Set(word.word.charAt(i), word.IsHorizontal);
        }
        words.add(word.word);
        outerLeft = Math.min(outerLeft, word.Position.X);
        outerTop = Math.min(outerTop, word.Position.Y);
        outerRight = Math.max(outerRight, word.Position.X + word.word.length() * dx);
        outerBottom = Math.max(outerBottom, word.Position.Y + word.word.length() * dy);
        if (word.IsHorizontal){
            innerTop = Math.min(innerTop, word.Position.Y);
            innerBottom = Math.max(innerBottom, word.Position.Y);
        }
        else{
            innerLeft = Math.min(innerLeft, word.Position.X);
            innerRight = Math.max(innerRight, word.Position.X);
        }
    }

    private WordInfo findPlace(String word, boolean isHorizontal){
        var dx = 1;
        var dy = 0;
        var left = innerLeft - word.length() + 1;
        var right = innerRight;
        var top = outerTop;
        var bottom = outerBottom;
        if (!isHorizontal){
            dx = 0;
            dy = 1;
            left = outerLeft;
            right = outerRight;
            top = innerTop - word.length() + 1;
            bottom = innerBottom;
        }
        var places = new ArrayList<WordInfo>();
        for (var x = left; x <= right; x++){ // "<=" здесь необходимо
            for (var y = top; y <= bottom; y++){ // и здесь тоже
                var canPut = true;
                var place = new WordInfo(x,
                        y,
                        isHorizontal,
                        word);
                for (var i = 0; i < word.length(); i++){
                    var pos = new VectorInt(x + i * dx, y + i * dy);
                    if (!field.containsKey(pos)){
                        continue;
                    }
                    place.IntersectionNumber++;
                    canPut = field.get(pos).CanPlace(word.charAt(i), isHorizontal);
                    if (!canPut){
                        break;
                    }
                }
                if (!canPut){
                    continue;
                }
                places.add(place);
            }
        }
        var theOne = new WordInfo(left - dy, // dy на месте
                top - dx, // как и dx
                isHorizontal,
                word);
        for (var place : places){
            if (place.IntersectionNumber > theOne.IntersectionNumber){
                theOne = place;
            }
        }
        return theOne;
    }

    public double getSize(){
        var x = outerRight - outerLeft;
        var y = outerBottom - outerTop;
        return Math.sqrt(x * x + y * y);
    }
}
