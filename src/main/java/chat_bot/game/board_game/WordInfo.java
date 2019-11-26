package chat_bot.game.board_game;

public class WordInfo {
    public final VectorInt Position;

    public final boolean IsHorizontal;
    public final String word;

    public int IntersectionNumber = 0;

    public WordInfo(int x, int y, boolean isHorizontal, String word){
        Position = new VectorInt(x, y);
        IsHorizontal = isHorizontal;
        this.word = word;
    }

    public WordInfo(VectorInt pos, boolean isHorizontal, String word){
        Position = pos;
        IsHorizontal = isHorizontal;
        this.word = word;
    }

    public Character charIn(VectorInt place){
        if (IsHorizontal){
            if (place.Y != Position.Y)
                return null;
            if (place.X < Position.X || place.X >= Position.X + word.length())
                return null;
            return word.charAt(place.X - Position.X);
        }
        else{
            if (place.X != Position.X)
                return null;
            if (place.Y < Position.Y || place.Y >= Position.Y + word.length())
                return null;
            return word.charAt(place.Y - Position.Y);
        }
    }

    public VectorInt[] getPoints(){
        var result = new VectorInt[word.length()];
        VectorInt delta;
        if (IsHorizontal){
            delta = new VectorInt(1, 0);
        }
        else{
            delta = new VectorInt(0, 1);
        }
        for (var i = 0; i < word.length(); i++){
            result[i] = VectorInt.summ(Position, VectorInt.mult(delta, i));
        }
        return result;
    }
}
