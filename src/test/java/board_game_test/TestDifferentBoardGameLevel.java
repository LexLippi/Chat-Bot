package board_game_test;

import chat_bot.game.board_game.board_levels.BoardLevel;
import chat_bot.game.board_game.board_levels.Easy;
import chat_bot.game.board_game.board_levels.Hard;
import chat_bot.game.board_game.board_levels.Medium;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

public class TestDifferentBoardGameLevel {
    @Test
    public void testDifferentBoardGameLevel() {
        var easyData = new Easy().sortData();
        var mediumData = new Medium().sortData();
        var hardData = new Hard().sortData();
        for (var el: hardData.keySet()) {
            Assert.assertFalse(easyData.containsKey(el));
        }
        for (var el: mediumData.keySet()) {
            Assert.assertFalse(easyData.containsKey(el));
        }
        for (var el: mediumData.keySet()) {
            Assert.assertFalse(hardData.containsKey(el));
        }
        Assert.assertEquals(easyData.size(), mediumData.size() + 1);
        Assert.assertEquals(easyData.size(), hardData.size() + 1);
    }
}
