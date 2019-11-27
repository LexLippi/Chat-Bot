package board_game_test;

import chat_bot.game.board_game.Composition;
import chat_bot.game.board_game.VectorInt;
import chat_bot.game.board_game.WordInfo;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

public class CompositionTest {

    @Test
    void testMerge() {
        var c1 = new Composition("asdf");
        var c2 = new Composition("zxcv");
        var c3 = Composition.flipped(new Composition("abc"));
        Assert.assertNull(Composition.merge(c1, c2, new VectorInt(0, 0)));
        Assert.assertNotNull(Composition.merge(c1, c2, new VectorInt(0, 1)));
        Assert.assertNotNull(Composition.merge(c1, c3, new VectorInt(-1, 1)));
    }

    @Test
    void testShareWords(){
        var c1 = new Composition("asdf");
        var c2 = new Composition("bsed");
        var c3 = new Composition("bsed");
        Assert.assertFalse(c1.shareCommonWords(c2));
        Assert.assertTrue(c2.shareCommonWords(c3));
    }

    @Test
    void testBestPlace(){
        var c1 = new Composition("asdf");
        var c2 = new Composition("bsed");
        var c3 = Composition.getBestCombination(c1, c2);
        Assert.assertNotEquals(c3.getWords().get(0).IsHorizontal, c3.getWords().get(1).IsHorizontal);
        var c4 = new Composition("pdmk");
        var c5 = new Composition("lemz");
        var c6 = Composition.getBestCombination(c4, c5);
        Assert.assertNotEquals(c6.getWords().get(0).IsHorizontal, c6.getWords().get(1).IsHorizontal);
        var result = Composition.getBestCombination(c3, c6);
        Assert.assertEquals(16, result.getSize().getArea());
    }

}
