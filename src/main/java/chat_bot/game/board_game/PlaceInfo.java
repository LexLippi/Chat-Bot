package chat_bot.game.board_game;

public class PlaceInfo {
    public final int X;
    public final int Y;

    public final boolean IsHorizontal;
    public final String word;

    public int IntersectionNumber = 0;

    public PlaceInfo(int x, int y, boolean isHorizontal, String word){
        X = x;
        Y = y;
        IsHorizontal = isHorizontal;
        this.word = word;
    }
}
