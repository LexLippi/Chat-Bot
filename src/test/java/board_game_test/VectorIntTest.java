package board_game_test;

import chat_bot.game.board_game.VectorInt;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.Random;

public class VectorIntTest {

    @Test
    void testMult(){
        var original = new VectorInt(12, -26);
        var mult = 12;
        var result = VectorInt.mult(original, mult);
        Assert.assertEquals(result.X, original.X * mult);
        Assert.assertEquals(result.Y, original.Y * mult);
    }

    @Test
    void testSum(){
        var original = new VectorInt(12, -26);
        var dop = new VectorInt(92, 19);
        var result = VectorInt.summ(original, dop);
        Assert.assertEquals(result.X, original.X + dop.X);
        Assert.assertEquals(result.Y, original.Y + dop.Y);
    }

    @Test
    void testNeg(){
        var original = new VectorInt(12, -26);
        var result = original.negative();
        Assert.assertEquals(result.X, -original.X);
        Assert.assertEquals(result.Y, -original.Y);
    }

    @Test
    void testEquals(){
        var one = new VectorInt(12, -26);
        var other = new VectorInt(12, -26);
        Assert.assertEquals(one, other);
        Assert.assertEquals(one.hashCode(), other.hashCode());
    }

}
