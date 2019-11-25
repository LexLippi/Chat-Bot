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

    @Test
    void testDataContainsWord() {
        var data = new BoardData(words);
        Assert.assertTrue(data.isDataContainsWord("Арбуз"));
        Assert.assertTrue(data.isDataContainsWord("Ананас"));
        Assert.assertTrue(data.isDataContainsWord("Дыня"));
        Assert.assertTrue(data.isDataContainsWord("Исключение"));
        Assert.assertTrue(data.isDataContainsWord("Косметика"));
        Assert.assertTrue(data.isDataContainsWord("Патишах"));
        Assert.assertTrue(data.isDataContainsWord("Мул"));
        Assert.assertTrue(data.isDataContainsWord("Ишак"));
        Assert.assertTrue(data.isDataContainsWord("Осёл"));
        Assert.assertTrue(data.isDataContainsWord("Попугай"));
        Assert.assertTrue(data.isDataContainsWord("Медведь"));
        Assert.assertTrue(data.isDataContainsWord("Ведро"));
        Assert.assertTrue(data.isDataContainsWord("Колибри"));
        Assert.assertTrue(data.isDataContainsWord("Слон"));
        Assert.assertTrue(data.isDataContainsWord("Комета"));
        Assert.assertTrue(data.isDataContainsWord("Шахматы"));
        Assert.assertTrue(data.isDataContainsWord("Футбол"));
        Assert.assertTrue(data.isDataContainsWord("Космос"));
        Assert.assertTrue(data.isDataContainsWord("Апельсин"));
    }

    @Test
    void testDataNotContainsWord() {
        var data = new BoardData(words);
        Assert.assertFalse(data.isDataContainsWord("abracadabra"));
    }

}
