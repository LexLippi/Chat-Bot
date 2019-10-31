package test.game_test.game_level_test;

import chat_bot.City;
import chat_bot.Data;
import chat_bot.game.levels.Easy;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

public class TestEasyGameLevel {
    @Test
    void testSimpleComputeCity() {
        var data = new Data(new City[] {
                        new City ("Мальорка", 100),
                        new City ("Москва",100),
                        new City ("Минск", 100),
                        new City ("Анапа", 100),
                        new City("Краснодар", 100),
                        new City ("Рига", 100),
                        new City ("Агдам",100)
        });
        var level = new Easy(data);
        Assert.assertNotNull(level.computeCity('А'));
    }

    @Test
    void testComputeCityOnStopLetter() {
        var data = new Data(new City[] {
                        new City ("Мальорка", 100),
                        new City ("Москва",100),
                        new City ("Минск", 100),
                        new City ("Анапа", 100),
                        new City("Краснодар", 100),
                        new City ("Рига", 100),
                        new City ("Агдам",100)
        });
        var level = new Easy(data);
        Assert.assertNull(level.computeCity('Б'));
    }

    @Test
    void testTwoComputeBestCity() {
        var data = new Data(new City[] {
                        new City ("Мальорка", 100),
                        new City ("Москва",100),
                        new City ("Минск", 100),
                        new City ("Анапа", 100),
                        new City("Краснодар", 100),
                        new City ("Рига", 100),
                        new City ("Агдам",100)
        });
        var level = new Easy(data);
        level.computeCity('А');
        Assert.assertNotNull(level.computeCity('А'));
    }

    @Test
    void testManyComputeBestCities() {
        var data = new Data(new City[] {
                        new City ("Мальорка", 100),
                        new City ("Москва",100),
                        new City ("Минск", 100),
                        new City ("Анапа", 100),
                        new City("Краснодар", 100),
                        new City ("Рига", 100),
                        new City ("Агдам",100)
        });
        var level = new Easy(data);
        for (var i = 0; i < 8; i++) {
            Assert.assertNotNull(level.computeCity('А'));
        }
    }

    @Test
    void testStepCounter() {
        var data = new Data(new City[] {
                        new City ("Мальорка", 100),
                        new City ("Москва",100),
                        new City ("Минск", 100),
                        new City ("Анапа", 100),
                        new City("Краснодар", 100),
                        new City ("Рига", 100),
                        new City ("Агдам",100)
        });
        var level = new Easy(data);
        level.computeCity('А');
        Assert.assertFalse(level.isStepCounterEmpty());
    }
}
