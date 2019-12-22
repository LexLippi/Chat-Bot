package statistic_test;

import bot_test.TestingApi;
import bot_test.TestingGameFactory;
import chat_bot.ChatBot;
import chat_bot.game.city_game.City;
import chat_bot.game.city_game.CityGame;
import chat_bot.game.city_game.Data;
import chat_bot.game.city_game.levels.Easy;
import chat_bot.game.city_game.levels.Hard;
import chat_bot.game.city_game.levels.Medium;
import chat_bot.game.city_game.states.BotCourse;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

public class TestStatistic {

    @Test
    public void testWin(){
        var api = new TestingApi();
        var factory = new TestingGameFactory();
        var bot = new ChatBot(api, factory);
        bot.process("Играть в города");
        City[] cites =  {new City("Москва", 0), new City("Анапа", 0), new City("Йокнеам", 0)};
        var data = new Data(cites);
        ((CityGame)(bot.getGame())).setLevel(new Easy(data));
        ((CityGame)(bot.getGame())).setCurrentState(new BotCourse());
        bot.process("Йокнеам");
        bot.process("Анапа");
        Assert.assertEquals("всего: 1, побед: 1, поражений: 0, прерываний: 0", bot.getStatistic("Easy"));
    }

    @Test
    public void testDefeats(){
        var api = new TestingApi();
        var factory = new TestingGameFactory();
        var bot = new ChatBot(api, factory);
        bot.process("Играть в города");
        City[] cites =  {new City("Москва", 0), new City("Анапа", 0), new City("Йокнеам", 0)};
        var data = new Data(cites);
        ((CityGame)(bot.getGame())).setLevel(new Hard(data));
        ((CityGame)(bot.getGame())).setCurrentState(new BotCourse());
        bot.process("Сдаюсь");
        Assert.assertEquals("всего: 1, побед: 0, поражений: 1, прерываний: 0", bot.getStatistic("Hard"));
    }

    @Test
    public void testInterruption(){
        var api = new TestingApi();
        var factory = new TestingGameFactory();
        var bot = new ChatBot(api, factory);
        bot.process("Играть в города");
        City[] cites =  {new City("Москва", 0), new City("Анапа", 0), new City("Йокнеам", 0)};
        var data = new Data(cites);
        ((CityGame)(bot.getGame())).setLevel(new Medium(data));
        ((CityGame)(bot.getGame())).setCurrentState(new BotCourse());
        bot.process("Стоп");
        Assert.assertEquals("всего: 1, побед: 0, поражений: 0, прерываний: 1", bot.getStatistic("Medium"));
    }
}
