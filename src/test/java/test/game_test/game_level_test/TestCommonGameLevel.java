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
        Assert.assertEquals(level.getCityLastLetter("Марракеш"), java.util.Optional.of('Ш'));
    }
}
