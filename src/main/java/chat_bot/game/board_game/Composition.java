package chat_bot.game.board_game;

import java.util.ArrayList;

public class Composition {
    private ArrayList<WordInfo> words;
    private Rectangle size;

    public Composition(String word){
        words = new ArrayList<>();
        words.add(new WordInfo(0, 0, true, word));
        size = new Rectangle();
        resetPosition();
    }

    public Rectangle getSize(){
        return new Rectangle(new VectorInt(size.left, size.top), new VectorInt(size.right, size.bottom));
    }

    public ArrayList<WordInfo> getWords(){
        return words;
    }

    public Composition(){
        words = new ArrayList<>();
        size = new Rectangle();
    }

    public Character charIn(VectorInt pos){
        for (var word : words){
            var answer = word.charIn(pos);
            if (answer != null)
                return answer;
        }
        return null;
    }

    public boolean shareCommonWords(Composition other){
        for (var word1 : words){
            for (var word2 : other.words){
                if (word1.word.equals(word2.word))
                    return true;
            }
        }
        return false;
    }

    public static Composition getBestCombination(Composition c1, Composition c2){
        var r1 = Composition.getRawCompositions(c1, c2);
        var r2 = Composition.getRawCompositions(c1, Composition.flipped(c2));
        r1.addAll(r2);
        Composition min = null;
        var minSize = 100000000;
        for (var comp : r1){
            if (minSize > comp.size.getArea()){
                min = comp;
                minSize = comp.size.getArea();
            }
        }
        return min;
    }

    public static ArrayList<Composition> getRawCompositions(Composition c1, Composition c2){
        var result = new ArrayList<Composition>();
        var minArea = 10000000;
        var area = Rectangle.union(c1.size, c2.size);
        for (var x = area.left; x <= area.right; x++){
            for (var y = area.top; y <= area.bottom; y++){
                var offset = new VectorInt(x, y);
                var merged = Composition.merge(c1, c2, offset);
                if (merged == null){
                    continue;
                }
                var newArea = merged.size.getArea();
                if (newArea < minArea){
                    result = new ArrayList<Composition>();
                    minArea = newArea;
                    result.add(merged);
                }
                else if (newArea == minArea){
                    result.add(merged);
                }
            }
        }
        return result;
    }

    public static Composition merge(Composition c1, Composition c2, VectorInt offset){
        //if (offset.X == -1 && offset.Y == 2){
        //    System.out.println(1);
        //}
        for (var word : c2.words){
            for (var point : word.getPoints()){
                var character1 = c1.charIn(VectorInt.summ(point, offset));
                var character2 = word.charIn(point);
                if (character1 != null && !character1.equals(character2)) {
                    return null;
                }
            }
        }
        var newCompose = new Composition();
        for (var w : c2.words){
            newCompose.words.add(new WordInfo(VectorInt.summ(w.Position,  offset), w.IsHorizontal, w.word));
        }
        newCompose.words.addAll(c1.words);
        newCompose.resetPosition();
        return newCompose;
    }

    public static Composition flipped(Composition c){
        var result = new Composition();
        for (var word : c.words){

            var newInfo = new WordInfo(word.Position.Y, word.Position.X, !word.IsHorizontal, word.word);
            result.words.add(newInfo);
        }
        result.resetPosition();
        return result;
    }

    private void move(VectorInt amount){
        for (var i = 0; i < words.size(); i++){
            var current = words.get(i);
            var word = new WordInfo(VectorInt.summ(current.Position, amount), current.IsHorizontal, current.word);
            words.set(i, word);
        }
        size.move(amount);
    }

    private void resetPosition(){
        for (var word : words){
            VectorInt tail;
            if (word.IsHorizontal){
                tail = VectorInt.summ(word.Position, new VectorInt(word.word.length() - 1, 0));
            }
            else{
                tail = VectorInt.summ(word.Position, new VectorInt(0, word.word.length() - 1));
            }
            size.putIn(word.Position);
            size.putIn(tail);
        }
        var moveAmount = new VectorInt(-(size.right + size.left) / 2 , -(size.top + size.bottom) / 2);
        move(moveAmount);
    }
}
