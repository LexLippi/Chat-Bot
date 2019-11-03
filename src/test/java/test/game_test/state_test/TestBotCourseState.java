package test.game_test.state_test;

import chat_bot.City;
import chat_bot.Data;
import chat_bot.game.levels.Easy;
import chat_bot.game.levels.GameLevel;
import chat_bot.game.levels.Hard;
import chat_bot.game.levels.Medium;
import chat_bot.game.states.BotCourse;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

public class TestBotCourseState {
    @Test
    void testNextState() {
        var selector = new BotCourse();
        Assert.assertTrue(selector.nextState instanceof BotCourse);
    }

    @Test
    void testProcessIncorrectCommand() {
        var selector = new BotCourse();
        GameLevel level = new Medium(new Data(new City[] {new City("Марракеш", 100)}));
        var result = selector.processCommand("abracadabra", level);
        Assert.assertNull(result.getType());
        Assert.assertEquals("Я не знаю такого города, попробуйте снова", result.getMessages()[0]);
        level = new Easy(new Data(new City[] {new City("Марракеш", 100)}));
        result = selector.processCommand("abracadabra", level);
        Assert.assertNull(result.getType());
        Assert.assertEquals("Я не знаю такого города, попробуйте снова", result.getMessages()[0]);
        level = new Hard(new Data(new City[] {new City("Марракеш", 100)}));
        result = selector.processCommand("abracadabra", level);
        Assert.assertNull(result.getType());
        Assert.assertEquals("Я не знаю такого города, попробуйте снова", result.getMessages()[0]);
    }

    @Test
    void testProcessCorrectCommand() {
        var selector = new BotCourse();
        GameLevel level = new Medium(new Data(new City[] {new City("Марракеш", 100)}));
        var result = selector.processCommand("сдаюсь", level);
        Assert.assertNotNull(result.getType());
        Assert.assertEquals("Ничего, в другой раз повезет!", result.getMessages()[0]);
        level = new Easy(new Data(new City[] {new City("Марракеш", 100)}));
        result = selector.processCommand("сдаюсь", level);
        Assert.assertNotNull(result.getType());
        Assert.assertEquals("Ничего, в другой раз повезет!", result.getMessages()[0]);
        level = new Hard(new Data(new City[] {new City("Марракеш", 100)}));
        result = selector.processCommand("сдаюсь", level);
        Assert.assertNotNull(result.getType());
        Assert.assertEquals("Ничего, в другой раз повезет!", result.getMessages()[0]);
    }
}
