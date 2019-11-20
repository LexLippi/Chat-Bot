package board_game_test;

import chat_bot.game.board_game.BoardData;
import org.junit.Assert;
import org.junit.jupiter.api.Test;


public class BoardDataTest {
    private String[] words = new String[] {"Арбуз", "Ананас", "Дыня", "Исключение", "Апельсин", "Футбол", "Косметика",
            "Космос", "Шахматы", "Комета", "Патишах", "Ишак", "Мул", "Осёл", "Попугай", "Медведь", "Колибри", "Слон",
            "Ведро"};

    @Test
    void testDataInitializeFromArray() {
        var data = new BoardData(words);
        Assert.assertTrue(data instanceof BoardData);
    }

    @Test
    void testDataInitializeFromNet() {
        var data = new BoardData();
        Assert.assertTrue(data instanceof BoardData);
    }

    @Test
    void testGetOneWord() {
        var data = new BoardData(words);
        var resultWords = data.getWords(1);
        Assert.assertNotNull(resultWords);
        Assert.assertTrue(resultWords[0] instanceof String);
    }

    @Test
    void testGetNoOneWords() {
        var data = new BoardData(words);
        var resultWords = data.getWords(0);
        Assert.assertNull(resultWords);
    }

    @Test
    void testGetManyWords() {
        var data = new BoardData(words);
        var resultWords = data.getWords(10);
        Assert.assertNotNull(resultWords);
        Assert.assertTrue(resultWords[5] instanceof String);
    }

    @Test
    void testGetAllWords() {
        var data = new BoardData(words);
        var resultWords = data.getWords(words.length);
        Assert.assertNotNull(resultWords);
        Assert.assertTrue(resultWords[12] instanceof String);
    }

    @Test
    void testGetTooManyWords() {
        try {
            var data = new BoardData(words);
            var resultWords = data.getWords(words.length + 2);
        }
        catch (Exception e) {
            Assert.assertTrue(e instanceof IllegalArgumentException);
        }
    }


}
