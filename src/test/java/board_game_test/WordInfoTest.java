package board_game_test;

import chat_bot.game.board_game.VectorInt;
import chat_bot.game.board_game.WordInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

public class WordInfoTest {

    @Test
    void testCharIn() {
        var word = new WordInfo(new VectorInt(1, 2), true, "asdf");
        Assert.assertEquals(new Character('s'), word.charIn(new VectorInt(2, 2)));
        Assert.assertNull(word.charIn(new VectorInt(2, 3)));
    }

    @Test
    void testGetPoints() {
        var word = new WordInfo(new VectorInt(1, 2), false, "asdf");
        var poses = word.getPoints();
        Assert.assertEquals(poses.length, word.word.length());
        for (var p : poses){
            Assert.assertNotNull(word.charIn(p));
        }
    }

}
