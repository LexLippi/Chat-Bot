package test.game_test.game_level_test;

import chat_bot.City;
import chat_bot.Data;
import chat_bot.game.levels.Hard;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import test.TestingApi;

public class TestHardGameLevel {
    @Test
    void testSimpleComputeCity() {
        var data = new Data(new City[]
                {
                        new City ("Мальорка", 100),
                        new City ("Москва",100),
                        new City ("Минск", 100),
                        new City ("Анапа", 100),
                        new City("Краснодар", 100),
                        new City ("Рига", 100),
                        new City ("Агдам",100)
                });
        var api = new TestingApi(new String[] {});
        var level = new Hard(data, api);
        Assert.assertEquals("Анапа", level.computeCity('А'));
    }

    @Test
    void testComputeCityOnStopLetter() {
        var data = new Data(new City[]
                {
                        new City ("Мальорка", 100),
                        new City ("Москва",100),
                        new City ("Минск", 100),
                        new City ("Анапа", 100),
                        new City("Краснодар", 100),
                        new City ("Рига", 100),
                        new City ("Агдам",100)
                });        var api = new TestingApi(new String[] {});
        var level = new Hard(data, api);
        Assert.assertNull(level.computeCity('Б'));
    }

    @Test
    void testComputeCityRemoveBestCity() {
        var data = new Data(new City[]
                {
                        new City ("Мальорка", 100),
                        new City ("Москва",100),
                        new City ("Минск", 100),
                        new City ("Анапа", 100),
                        new City("Краснодар", 100),
                        new City ("Рига", 100),
                        new City ("Агдам",100)
                });        var api = new TestingApi(new String[] {});
        var level = new Hard(data, api);
        level.computeCity('А');
        Assert.assertEquals("Агдам", level.computeCity('А'));
    }

    @Test
    void testComputeCityRemoveBestCities() {
        var data = new Data(new City[]
                {
                        new City ("Мальорка", 100),
                        new City ("Москва",100),
                        new City ("Минск", 100),
                        new City ("Анапа", 100),
                        new City("Краснодар", 100),
                        new City ("Рига", 100),
                        new City ("Агдам",100)
                });        var api = new TestingApi(new String[] {});
        var level = new Hard(data, api);
        level.computeCity('А');
        level.computeCity('А');
        Assert.assertNull(level.computeCity('А'));
    }

    @Test
    void testStepCounter() {
        var data = new Data(new City[]
                {
                        new City ("Мальорка", 100),
                        new City ("Москва",100),
                        new City ("Минск", 100),
                        new City ("Анапа", 100),
                        new City("Краснодар", 100),
                        new City ("Рига", 100),
                        new City ("Агдам",100)
                });        var api = new TestingApi(new String[] {});
        var level = new Hard(data, api);
        Assert.assertFalse(level.isStepCounterEmpty());
    }
}
