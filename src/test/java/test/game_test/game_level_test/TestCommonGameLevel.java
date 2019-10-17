package test.game_test.game_level_test;

import chat_bot.City;
import chat_bot.Data;
import chat_bot.game.levels.Easy;
import chat_bot.game.levels.Medium;
import chat_bot.game.return_types.GameExitType;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import test.TestingApi;

public class TestCommonGameLevel {
    @Test
    void testSimpleLastLetterFromCity() {
        var data = new Data(new City[]
                {
                        new City ("Марракеш", 100),
                        new City ("Шабры", 100)
                });
        var api = new TestingApi(new String[] {});
        var level = new Medium(data, api);
        Assert.assertEquals(java.util.Optional.of(level.getCityLastLetter("Марракеш")), java.util.Optional.of('Ш'));
    }

    @Test
    void testNextLastLetterFromCity() {
        var data = new Data(new City[]
                {
                        new City ("Марракеш", 100),
                        new City ("Екатеринбург", 100)
                });
        var api = new TestingApi(new String[] {});
        var level = new Medium(data, api);
        Assert.assertEquals(java.util.Optional.of(level.getCityLastLetter("Марракеш")), java.util.Optional.of('Е'));
    }

    @Test
    void testFirstLetterFromCity() {
        var data = new Data(new City[]
                {
                        new City ("Марракеш", 100)
                });
        var api = new TestingApi(new String[] {});
        var level = new Medium(data, api);
        Assert.assertEquals(java.util.Optional.of(level.getCityLastLetter("Марракеш")), java.util.Optional.of('М'));
    }

    @Test
    void testGameInterrupted() {
        var data = new Data(new City[]
                {
                        new City ("Марракеш", 100)
                });
        var api = new TestingApi(new String[] {"стоп"});
        var level = new Medium(data, api);
        Assert.assertEquals(GameExitType.GAME_INTERRUPTED, level.processingUserCourse());
    }

    @Test
    void testPlayerLose() {
        var data = new Data(new City[]
                {
                        new City ("Марракеш", 100)
                });
        var api = new TestingApi(new String[] {"сдаюсь"});
        var level = new Medium(data, api);
        Assert.assertEquals(GameExitType.PLAYER_LOOSE, level.processingUserCourse());
    }

    @Test
    void testPlayerWin() {
        var data = new Data(new City[] {});
        var api = new TestingApi(new String[] {});
        var level = new Medium(data, api);
        Assert.assertEquals(GameExitType.PLAYER_WIN, level.getBotFirstCourse());
    }

    @Test
    void testBotFirstCourse() {
        var data = new Data(new City[]
                {
                        new City ("Марракеш", 100)
                });
        var api = new TestingApi(new String[] {});
        var level = new Medium(data, api);
        Assert.assertNull(level.getBotFirstCourse());
    }
}
