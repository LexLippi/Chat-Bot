package test.game_test.state_test;

import chat_bot.game.city_game.City;
import chat_bot.game.city_game.Data;
import chat_bot.game.city_game.levels.Easy;
import chat_bot.game.city_game.levels.GameLevel;
import chat_bot.game.city_game.levels.Hard;
import chat_bot.game.city_game.levels.Medium;
import chat_bot.game.city_game.states.Draw;
import chat_bot.game.city_game.states.SelectLevel;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

public class TestSelectLevelState {
    @Test
    void testNextState() {
        var selector = new SelectLevel();
        Assert.assertTrue(selector.nextState instanceof Draw);
    }

    @Test
    void testProcessIncorrectCommand() {
        var selector = new SelectLevel();
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
        var selector = new SelectLevel();
        GameLevel level = new Medium(new Data(new City[] {new City("Марракеш", 100)}));
        var result = selector.processCommand("тяжёлый", level);
        Assert.assertNull(result.getType());
        Assert.assertEquals("Пора выбрать, кто будет ходить первым! Орёл или решка?", result.getMessages()[0]);
        level = new Easy(new Data(new City[] {new City("Марракеш", 100)}));
        result = selector.processCommand("тяжёлый", level);
        Assert.assertNull(result.getType());
        Assert.assertEquals("Пора выбрать, кто будет ходить первым! Орёл или решка?", result.getMessages()[0]);
        level = new Hard(new Data(new City[] {new City("Марракеш", 100)}));
        result = selector.processCommand("тяжёлый", level);
        Assert.assertNull(result.getType());
        Assert.assertEquals("Пора выбрать, кто будет ходить первым! Орёл или решка?", result.getMessages()[0]);
    }
}
