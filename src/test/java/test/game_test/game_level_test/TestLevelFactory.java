package test.game_test.game_level_test;

import chat_bot.game.levels.*;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

public class TestLevelFactory {
    @Test
    void testCreateEasyLevel() {
        var level = new LevelFactory().getLevel("легкий");
        Assert.assertTrue(level instanceof Easy);
    }

    @Test
    void testCreateMediumLevel() {
        var level = new LevelFactory().getLevel("средний");
        Assert.assertTrue(level instanceof Medium);
    }

    @Test
    void testCreateHardLevel() {
        var level = new LevelFactory().getLevel("тяжелый");
        Assert.assertTrue(level instanceof Hard);
    }

    @Test
    void testAbracadabra() {
        try {
            new LevelFactory().getLevel("abracadabra");
        }
        catch (Exception e) {
            Assert.assertTrue(e instanceof IllegalArgumentException);
        }
    }

    @Test
    void testDifferentCaseEasyLevel() {
        var level = new LevelFactory().getLevel("лЕгкий");
        Assert.assertTrue(level instanceof Easy);
        level = new LevelFactory().getLevel("лЕГкиЙ");
        Assert.assertTrue(level instanceof Easy);
        level = new LevelFactory().getLevel("Легкий");
        Assert.assertTrue(level instanceof Easy);
        level = new LevelFactory().getLevel("ЛеГКиЙ");
        Assert.assertTrue(level instanceof Easy);
    }

    @Test
    void testDifferentCaseMediumLevel() {
        var level = new LevelFactory().getLevel("сРедний");
        Assert.assertTrue(level instanceof Medium);
        level = new LevelFactory().getLevel("среднИЙ");
        Assert.assertTrue(level instanceof Medium);
        level = new LevelFactory().getLevel("Средний");
        Assert.assertTrue(level instanceof Medium);
        level = new LevelFactory().getLevel("СреДНиЙ");
        Assert.assertTrue(level instanceof Medium);
    }

    @Test
    void testDifferentCaseHardLevel() {
        var level = new LevelFactory().getLevel("тЯжелый");
        Assert.assertTrue(level instanceof Hard);
        level = new LevelFactory().getLevel("тяжеЛЫЙ");
        Assert.assertTrue(level instanceof Hard);
        level = new LevelFactory().getLevel("Тяжелый");
        Assert.assertTrue(level instanceof Hard);
        level = new LevelFactory().getLevel("ТяЖеЛыЙ");
        Assert.assertTrue(level instanceof Hard);
    }
}
