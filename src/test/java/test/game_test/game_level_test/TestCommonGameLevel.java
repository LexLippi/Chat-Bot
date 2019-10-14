package test.game_test.game_level_test;

import chat_bot.Data;
import chat_bot.game.levels.Easy;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

public class TestCommonGameLevel {
    @Test
    void testSimpleLastLetterFromCity() {
        var data = new Data(new String[] {"Марракеш", "Шабры"});
        var level = new Easy(data);
        Assert.assertEquals(java.util.Optional.of(level.getCityLastLetter("Марракеш")), java.util.Optional.of('Ш'));
    }

    @Test
    void testNextLastLetterFromCity() {
        var data = new Data(new String[] {"Марракеш", "Екатеринбург"});
        var level = new Easy(data);
        Assert.assertEquals(java.util.Optional.of(level.getCityLastLetter("Марракеш")), java.util.Optional.of('Е'));
    }

    @Test
    void testFirstLetterFromCity() {
        var data = new Data(new String[] {"Марракеш"});
        var level = new Easy(data);
        Assert.assertEquals(java.util.Optional.of(level.getCityLastLetter("Марракеш")), java.util.Optional.of('М'));
    }
}
