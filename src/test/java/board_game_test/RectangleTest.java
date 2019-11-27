package board_game_test;

import chat_bot.game.board_game.Rectangle;
import chat_bot.game.board_game.VectorInt;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

public class RectangleTest {

    @Test
    void testInit(){
        var rect = new Rectangle(new VectorInt(-2, -8), new VectorInt(12, 4));
        Assert.assertEquals(rect.left, -2);
        Assert.assertEquals(rect.top, -8);
        Assert.assertEquals(rect.right, 12);
        Assert.assertEquals(rect.bottom, 4);
    }

    @Test
    void testPutIn(){
        var rect = new Rectangle();
        rect.putIn(new VectorInt(3, -12));
        Assert.assertEquals(rect.left, 0);
        Assert.assertEquals(rect.top, -12);
        Assert.assertEquals(rect.right, 3);
        Assert.assertEquals(rect.bottom, 0);
        rect.putIn(new VectorInt(1, -3));
        Assert.assertEquals(rect.left, 0);
        Assert.assertEquals(rect.top, -12);
        Assert.assertEquals(rect.right, 3);
        Assert.assertEquals(rect.bottom, 0);
    }

    @Test
    void testMove(){
        var rect = new Rectangle();
        rect.putIn(new VectorInt(3, -12));
        rect.move(new VectorInt(-6, 8));
        Assert.assertEquals(rect.left, -6);
        Assert.assertEquals(rect.top, -4);
        Assert.assertEquals(rect.right, -3);
        Assert.assertEquals(rect.bottom, 8);
    }

    @Test
    void testUnion(){
        var rect1 = new Rectangle();
        rect1.putIn(new VectorInt(3, -12));
        var rect2 = new Rectangle();
        rect2.putIn(new VectorInt(-11, 8));
        var result = Rectangle.union(rect1, rect2);
        Assert.assertEquals(result.left, -11);
        Assert.assertEquals(result.top, -12);
        Assert.assertEquals(result.right, 3);
        Assert.assertEquals(result.bottom, 8);
    }

    @Test
    void testArea(){
        var rect1 = new Rectangle();
        rect1.putIn(new VectorInt(3, -12));
        Assert.assertEquals(4 * 13, rect1.getArea());
    }
}
