package test.game_test.game_level_test;

import chat_bot.game.city_game.Data;
import chat_bot.game.city_game.levels.*;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

public class TestLevelFactory {
    private Data data = new Data();
    @Test
    void testCreateEasyLevel() {
        var level = new LevelFactory().getLevel("легкий", data.clone());
        Assert.assertTrue(level instanceof Easy);
        level = new LevelFactory().getLevel("лёгкий", data.clone());
        Assert.assertTrue(level instanceof Easy);
    }

    @Test
    void testCreateMediumLevel() {
        var level = new LevelFactory().getLevel("средний", data.clone());
        Assert.assertTrue(level instanceof Medium);
    }

    @Test
    void testCreateHardLevel() {
        var level = new LevelFactory().getLevel("тяжелый", data.clone());
        Assert.assertTrue(level instanceof Hard);
        level = new LevelFactory().getLevel("тяжёлый", data.clone());
        Assert.assertTrue(level instanceof Hard);
    }

    @Test
    void testAbracadabra() {
        var factory = new LevelFactory();
        Assert.assertFalse(factory.checkInputString("abracadabra"));
    }

    @Test
    void testAbracadabraOnRussian() {
        var factory = new LevelFactory();
        Assert.assertFalse(factory.checkInputString("абракадабра"));
    }

    @Test
    void testDifferentCaseEasyLevel() {
        var level = new LevelFactory().getLevel("лЕгкий", data.clone());
        Assert.assertTrue(level instanceof Easy);
        level = new LevelFactory().getLevel("лЕГкиЙ", data.clone());
        Assert.assertTrue(level instanceof Easy);
        level = new LevelFactory().getLevel("Легкий", data.clone());
        Assert.assertTrue(level instanceof Easy);
        level = new LevelFactory().getLevel("ЛеГКиЙ", data.clone());
        Assert.assertTrue(level instanceof Easy);
    }

    @Test
    void testDifferentCaseMediumLevel() {
        var level = new LevelFactory().getLevel("сРедний", data.clone());
        Assert.assertTrue(level instanceof Medium);
        level = new LevelFactory().getLevel("среднИЙ", data.clone());
        Assert.assertTrue(level instanceof Medium);
        level = new LevelFactory().getLevel("Средний", data.clone());
        Assert.assertTrue(level instanceof Medium);
        level = new LevelFactory().getLevel("СреДНиЙ", data.clone());
        Assert.assertTrue(level instanceof Medium);
    }

    @Test
    void testDifferentCaseHardLevel() {
        var level = new LevelFactory().getLevel("тЯжелый", data.clone());
        Assert.assertTrue(level instanceof Hard);
        level = new LevelFactory().getLevel("тяжеЛЫЙ", data.clone());
        Assert.assertTrue(level instanceof Hard);
        level = new LevelFactory().getLevel("Тяжелый", data.clone());
        Assert.assertTrue(level instanceof Hard);
        level = new LevelFactory().getLevel("ТяЖеЛыЙ", data.clone());
        Assert.assertTrue(level instanceof Hard);
    }
}
