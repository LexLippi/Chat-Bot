package chat_bot.game.board_game;

import javax.print.attribute.standard.MediaSize;

public class VectorInt {
    public final int X;
    public final int Y;

    public VectorInt(int x, int y){
        X = x;
        Y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof VectorInt)) {
            return false;
        }
        return X == ((VectorInt) o).X && Y == ((VectorInt) o).Y;
    }

    @Override
    public int hashCode() {
        return 109 * X + Y;
    }

    public VectorInt negative(){
        return new VectorInt(-X, -Y);
    }

    public static VectorInt mult(VectorInt v, int n){
        return new VectorInt(v.X * n, v.Y * n);
    }

    public static VectorInt summ(VectorInt one, VectorInt other){
        return new VectorInt(one.X + other.X, one.Y + other.Y);
    }
}
