package test.game_test.game_level_test;

import chat_bot.game.city_game.City;
import chat_bot.game.city_game.Data;
import chat_bot.game.city_game.levels.Medium;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

public class TestMediumGameLevel {
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
        var level = new Medium(data);
        Assert.assertNotNull(level.computeCity('Р'));
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
                });
        var level = new Medium(data);
        Assert.assertNull(level.computeCity('Б'));
    }

    @Test
    void testComputeBestCityTwice() {
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
        var level = new Medium(data);
        Assert.assertNotNull(level.computeCity('А'));
        Assert.assertNotNull(level.computeCity('А'));
    }

    @Test
    void testComputeManyBestCities() {
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
        var level = new Medium(data);
        for (var i = 0; i < 1000; ++i) {
            Assert.assertNotNull(level.computeCity('А'));
        }
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
                });
        var level = new Medium(data);
        Assert.assertFalse(level.isStepCounterEmpty());
    }
}
