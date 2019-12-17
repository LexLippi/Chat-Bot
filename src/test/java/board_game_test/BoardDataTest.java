package board_game_test;

import chat_bot.game.board_game.BoardData;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

public class BoardDataTest {
    private HashMap<String, Double> words = new HashMap<>();

    public BoardDataTest() {
        words.put("Арбуз", 1.0);
        words.put("Ананас", 1.0);
        words.put("Дыня", 1.0);
        words.put("Исключение", 1.0);
        words.put("Шахматы", 1.0);
        words.put("Комета", 1.0);
        words.put("Ведро", 1.0);
        words.put("Патишах", 1.0);
        words.put("Ишак", 1.0);
        words.put("Мул", 1.0);
        words.put("Осёл", 1.0);
        words.put("Космос", 1.0);
        words.put("Попугай", 1.0);
        words.put("Медведь", 1.0);
        words.put("Колибри", 1.0);
        words.put("Слон", 1.0);
        words.put("Косметика", 1.0);
        words.put("Апельсин", 1.0);
        words.put("Футбол", 1.0);
    }

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
