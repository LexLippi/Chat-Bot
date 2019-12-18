package board_game_test;

import chat_bot.game.board_game.board_levels.BoardLevel;
import chat_bot.game.board_game.board_levels.Easy;
import chat_bot.game.board_game.board_levels.Hard;
import chat_bot.game.board_game.board_levels.Medium;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

public class BoardLevelTest {
    @Test
    public void testGetOneWord() {
        BoardLevel level = new Easy();
        var words = level.getWords(1);
        Assert.assertNotNull(words[0]);
        Assert.assertEquals(1, words.length);
        level = new Medium();
        words = level.getWords(1);
        Assert.assertNotNull(words[0]);
        Assert.assertEquals(1, words.length);
        level = new Hard();
        words = level.getWords(1);
        Assert.assertNotNull(words[0]);
        Assert.assertEquals(1, words.length);
    }

    @Test
    public void testGetNoOneWord() {
        BoardLevel level = new Easy();
        var words = level.getWords(0);
        Assert.assertNull(words);
        level = new Medium();
        words = level.getWords(0);
        Assert.assertNull(words);
        level = new Hard();
        words = level.getWords(0);
        Assert.assertNull(words);
    }

    @Test
    public void testManyWords() {
        BoardLevel level = new Easy();
        var words = level.getWords(10);
        Assert.assertNotNull(words);
        Assert.assertEquals(10, words.length);
        level = new Medium();
        words = level.getWords(10);
        Assert.assertNotNull(words);
        Assert.assertEquals(10, words.length);
        level = new Hard();
        words = level.getWords(10);
        Assert.assertNotNull(words);
        Assert.assertEquals(10, words.length);
    }

    @Test
    public void testTooManyWords() {
        try {
            BoardLevel level = new Easy();
            var words = level.getWords(7000);
        }
        catch (Exception e) {
            Assert.assertTrue(e instanceof IllegalArgumentException);
        }
    }
}
