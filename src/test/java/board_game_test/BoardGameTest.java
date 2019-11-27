package board_game_test;

import chat_bot.game.board_game.BoardData;
import chat_bot.game.board_game.BoardGame;
import chat_bot.game.board_game.BoardWithCompositions;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class BoardGameTest {

    private String[] words = new String[] {"Арбуз", "Ананас", "Дыня", "Исключение", "Апельсин", "Футбол", "Косметика",
            "Космос", "Шахматы", "Комета", "Патишах", "Ишак", "Мул", "Осёл", "Попугай", "Медведь", "Колибри", "Слон",
            "Ведро"};

    @Test
    void testCreate() {
        var game = new BoardGame(new BoardData(words));
        var answer = game.startGame();
        Assert.assertEquals(3, answer.getMessages().length);
    }

    @Test
    void testProcess() {
        var game = new BoardGame(new BoardData(words));
        var a = game.process("asdf");
        Assert.assertEquals("такого слова нет :(", a.getMessages()[0]);
    }
}
