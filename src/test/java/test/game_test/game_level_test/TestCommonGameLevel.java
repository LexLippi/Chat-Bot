package test.game_test.game_level_test;

import chat_bot.City;
import chat_bot.Data;
import chat_bot.game.levels.Easy;
import chat_bot.game.levels.GameLevel;
import chat_bot.game.levels.Hard;
import chat_bot.game.levels.Medium;
import chat_bot.game.return_types.GameExitType;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

public class TestCommonGameLevel {
    @Test
    void testSimpleLastLetterFromCity() {
        var data = new Data(new City[] {
                        new City ("Марракеш", 100),
                        new City ("Шабры", 100)
        });
        GameLevel level = new Medium(data);
        Assert.assertEquals(java.util.Optional.of(level.getCityLastLetter("Марракеш")), java.util.Optional.of('Ш'));
        level = new Easy(data);
        Assert.assertEquals(java.util.Optional.of(level.getCityLastLetter("Марракеш")), java.util.Optional.of('Ш'));
        level = new Hard(data);
        Assert.assertEquals(java.util.Optional.of(level.getCityLastLetter("Марракеш")), java.util.Optional.of('Ш'));
    }

    @Test
    void testNextLastLetterFromCity() {
        var data = new Data(new City[] {
                        new City ("Марракеш", 100),
                        new City ("Екатеринбург", 100)
        });
        GameLevel level = new Medium(data);
        Assert.assertEquals(java.util.Optional.of(level.getCityLastLetter("Марракеш")), java.util.Optional.of('Е'));
        level = new Easy(data);
        Assert.assertEquals(java.util.Optional.of(level.getCityLastLetter("Марракеш")), java.util.Optional.of('Е'));
        level = new Hard(data);
        Assert.assertEquals(java.util.Optional.of(level.getCityLastLetter("Марракеш")), java.util.Optional.of('Е'));
    }

    @Test
    void testFirstLetterFromCity() {
        var data = new Data(new City[] { new City ("Марракеш", 100) });
        GameLevel level = new Medium(data);
        Assert.assertEquals(java.util.Optional.of(level.getCityLastLetter("Марракеш")), java.util.Optional.of('М'));
        level = new Easy(data);
        Assert.assertEquals(java.util.Optional.of(level.getCityLastLetter("Марракеш")), java.util.Optional.of('М'));
        level = new Hard(data);
        Assert.assertEquals(java.util.Optional.of(level.getCityLastLetter("Марракеш")), java.util.Optional.of('М'));
    }

    @Test
    void testGameInterrupted() {
        var data = new Data(new City[] { new City ("Марракеш", 100) });
        GameLevel level = new Medium(data);
        Assert.assertEquals(GameExitType.GAME_INTERRUPTED, level.processingUserCourse("стоп").getType());
        level = new Easy(data);
        Assert.assertEquals(GameExitType.GAME_INTERRUPTED, level.processingUserCourse("стоп").getType());
        level = new Hard(data);
        Assert.assertEquals(GameExitType.GAME_INTERRUPTED, level.processingUserCourse("стоп").getType());
    }

    @Test
    void testPlayerLose() {
        var data = new Data(new City[] { new City ("Марракеш", 100) });
        GameLevel level = new Medium(data);
        Assert.assertEquals(GameExitType.PLAYER_LOOSE, level.processingUserCourse("сдаюсь").getType());
        level = new Easy(data);
        Assert.assertEquals(GameExitType.PLAYER_LOOSE, level.processingUserCourse("сдаюсь").getType());
        level = new Hard(data);
        Assert.assertEquals(GameExitType.PLAYER_LOOSE, level.processingUserCourse("сдаюсь").getType());
    }

    @Test
    void testBotFirstCourse() {
        var data = new Data(new City[] {
                new City ("Марракеш", 100),
                new City ("Минск", 100),
                new City ("Москва", 100)
        });
        GameLevel level = new Medium(data);
        var result = level.getBotCourse();
        Assert.assertNull(result.getType());
        Assert.assertNotNull(result.getMessages()[0]);
        level = new Easy(data);
        result = level.getBotCourse();
        Assert.assertNull(null, result.getType());
        Assert.assertNotNull(result.getMessages()[0]);
        level = new Hard(data);
        result = level.getBotCourse();
        Assert.assertNull(result.getType());
        Assert.assertNotNull(result.getMessages()[0]);
    }
}