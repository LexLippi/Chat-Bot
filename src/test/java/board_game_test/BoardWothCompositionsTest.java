package board_game_test;

import chat_bot.game.board_game.BoardWithCompositions;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class BoardWothCompositionsTest {

    @Test
    void testCreate() {
        var words = new ArrayList<String>();
        words.add("wsx");
        words.add("asdf");
        words.add("qwer");
        words.add("zxcv");
        var board = new BoardWithCompositions(words);
        var c = board.getBestComposition();
        Assert.assertEquals(words.size(), c.getWords().size());
        for (var w : c.getWords()){
            Assert.assertTrue(words.contains(w.word));
            Assert.assertTrue(board.containsWord(w.word));
        }
    }
}
