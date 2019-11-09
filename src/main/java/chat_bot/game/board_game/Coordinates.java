package chat_bot.game.board_game;

public class Coordinates {
    public final int X;
    public final int Y;

    public Coordinates(int x, int y){
        X = x;
        Y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Coordinates)) {
            return false;
        }
        return X == ((Coordinates) o).X && Y == ((Coordinates) o).Y;
    }

    @Override
    public int hashCode() {
        return 109 * X + Y;
    }
}
