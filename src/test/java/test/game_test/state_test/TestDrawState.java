package test.game_test.state_test;

import chat_bot.City;
import chat_bot.Data;
import chat_bot.game.levels.Easy;
import chat_bot.game.levels.GameLevel;
import chat_bot.game.levels.Hard;
import chat_bot.game.levels.Medium;
import chat_bot.game.states.BotCourse;
import chat_bot.game.states.Draw;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

public class TestDrawState {
    @Test
    void testNextState() {
        var selector = new Draw();
        Assert.assertTrue(selector.nextState instanceof BotCourse);
    }

    @Test
    void testProcessIncorrectCommand() {
        var selector = new Draw();
        GameLevel level = new Medium(new Data(new City[] {new City("Марракеш", 100)}));
        try {
            selector.processCommand("abracadabra", level);
        }
        catch (Exception e) {
            Assert.assertTrue(e instanceof IllegalArgumentException);
        }
        level = new Easy(new Data(new City[] {new City("Марракеш", 100)}));
        try {
            selector.processCommand("abracadabra", level);
        }
        catch (Exception e) {
            Assert.assertTrue(e instanceof IllegalArgumentException);
        }
        level = new Hard(new Data(new City[] {new City("Марракеш", 100)}));
        try {
            selector.processCommand("abracadabra", level);
        }
        catch (Exception e) {
            Assert.assertTrue(e instanceof IllegalArgumentException);
        }
    }

    @Test
    void testProcessCorrectCommand() {
        var selector = new Draw();
        GameLevel level = new Medium(new Data(new City[] {new City("Марракеш", 100)}));
        var result = selector.processCommand("орёл", level);
        Assert.assertNull(result.getType());
        Assert.assertNotNull(result.getMessages()[0]);
        level = new Easy(new Data(new City[] {new City("Марракеш", 100)}));
        result = selector.processCommand("орёл", level);
        Assert.assertNull(result.getType());
        Assert.assertNotNull(result.getMessages()[0]);
        level = new Hard(new Data(new City[] {new City("Марракеш", 100)}));
        result = selector.processCommand("орёл", level);
        Assert.assertNull(result.getType());
        Assert.assertNotNull(result.getMessages()[0]);
    }
}
