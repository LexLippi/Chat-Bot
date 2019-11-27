package chat_bot.game.board_game;

public class Rectangle {
    public int left;
    public int right;
    public int top;
    public int bottom;

    public Rectangle(){
        left = 0;
        right = 0;
        top = 0;
        bottom = 0;
    }

    public Rectangle(VectorInt topLeft, VectorInt bottomRight){
        left = topLeft.X;
        right = bottomRight.X;
        top = topLeft.Y;
        bottom = bottomRight.Y;
    }

    public void move(VectorInt amount){
        left = left + amount.X;
        right = right + amount.X;
        top = top + amount.Y;
        bottom = bottom + amount.Y;
    }

    public void putIn(VectorInt coord){
        putIn(coord.X, coord.Y);
    }
    
    public void putIn(int x, int y){
        if (x < left){
            left = x;
        }
        if (x > right){
            right = x;
        }
        if (y < top){
            top = y;
        }
        if (y > bottom){
            bottom = y;
        }
    }

    public static Rectangle union(Rectangle one, Rectangle other){
        return new Rectangle(new VectorInt(Math.min(one.left, other.left), Math.min(one.top, other.top)),
                new VectorInt(Math.max(one.right, other.right), Math.max(one.bottom, other.bottom)));
    }

    public int getArea(){
        return (right - left + 1) * (bottom - top + 1);
    }
}
