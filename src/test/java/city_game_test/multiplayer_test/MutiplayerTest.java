package city_game_test.multiplayer_test;


import bot_test.TestingApi;
import chat_bot.game.city_game.City;
import chat_bot.game.city_game.CityMultiplayerGame;
import chat_bot.game.city_game.Data;
import chat_bot.game.return_types.GameReturnedValue;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

public class MutiplayerTest {

    private Data getData(){
        var cities = new City[3];
        cities[0] = new City("москва", 10);
        cities[1] = new City("абакан", 10);
        cities[2] = new City("новосибирск", 10);
        return new Data(cities);
    }

    private void add(GameReturnedValue value, TestingApi api){
        for (var s : value.getMessages()){
            api.answers.add(s);
        }
    }

    @Test
    public void testConnect(){
        var a1 = new TestingApi();
        var a2 = new TestingApi();
        var game = new CityMultiplayerGame(getData());
        add(game.startGame(a1), a1);
        add(game.process("asdf", a1), a1);
        add(game.startGame(a2), a2);
        Assert.assertEquals(a1.answers.get(0), "вы успешно подключились к игре, " +
                "чтобы пригласить своего друга введите его username");
        Assert.assertEquals(a2.answers.get(0), "ты успешно подключился к игре");
        Assert.assertEquals(a1.answers.get(1), "приглашение было отправлено");
    }

    @Test
    public void testSurrender(){
        var a1 = new TestingApi();
        var game = new CityMultiplayerGame(getData());
        add(game.startGame(a1), a1);
        add(game.process("asdf", a1), a1);
        add(game.process("сдаюсь", a1), a1);
        Assert.assertEquals(a1.answers.get(0), "вы успешно подключились к игре, " +
                "чтобы пригласить своего друга введите его username");
        Assert.assertEquals(a1.answers.get(2), "игра завершена");
    }

    @Test
    public void testPlay(){
        var a1 = new TestingApi();
        var a2 = new TestingApi();
        var game = new CityMultiplayerGame(getData());
        add(game.startGame(a1), a1);
        add(game.process("asdf", a1), a1);
        add(game.startGame(a2), a2);
        Assert.assertEquals(a1.answers.get(0), "вы успешно подключились к игре, " +
                "чтобы пригласить своего друга введите его username");
        Assert.assertEquals(a1.answers.get(1), "приглашение было отправлено");
        Assert.assertEquals(a2.answers.get(0), "ты успешно подключился к игре");
        var first_index = 3;
        var second_index = 2;
        if (a1.answers.get(2).equals("второй игрок подключился, он ходит первым")){
            var a3 = a2;
            a2 = a1;
            a1 = a3;
            first_index = 2;
            second_index = 3;
        }
        add(game.process("москва", a1), a1);
        add(game.process("сдаюсь", a2), a2);
        Assert.assertEquals(a1.answers.get(first_index), "принято!");
        Assert.assertEquals(a2.answers.get(second_index), "твой противник говорит:");
        Assert.assertEquals(a2.answers.get(second_index + 1), "москва");
        Assert.assertEquals(a2.answers.get(second_index + 2), "твой ход");
        Assert.assertEquals(a2.answers.get(second_index + 3), "ничего, в другой раз повезет");
    }
}
