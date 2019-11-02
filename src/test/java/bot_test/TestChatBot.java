package bot_test;

import chat_bot.ChatBot;
import chat_bot.game.GameType;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

public class TestChatBot {

    @Test
    void testStart(){
        var api = new TestingApi();
        var factory = new TestingGameFactory();
        var bot = new ChatBot(api, factory);
        Assert.assertEquals(1, api.answers.size());
    }

    @Test
    void testProcessPlay(){
        var api = new TestingApi();
        var factory = new TestingGameFactory();
        var bot = new ChatBot(api, factory);
        bot.process("играть");
        Assert.assertEquals(1, factory.calls.size());
        Assert.assertEquals(GameType.CityGame, factory.calls.get(0));
    }

    @Test
    void testProcessBye(){
        var api = new TestingApi();
        var factory = new TestingGameFactory();
        var bot = new ChatBot(api, factory);
        bot.process("пока");
        Assert.assertEquals(2, api.answers.size());
        Assert.assertEquals("до встречи", api.answers.get(1));
    }

    @Test
    void testProcessIncorrectCommand(){
        var api = new TestingApi();
        var factory = new TestingGameFactory();
        var bot = new ChatBot(api, factory);
        bot.process("asdf");
        Assert.assertEquals(2, api.answers.size());
        Assert.assertEquals("Я ещё не умею делать то, что ты хочешь.", api.answers.get(1));
    }

}
