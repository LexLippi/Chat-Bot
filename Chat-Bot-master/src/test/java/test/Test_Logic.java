
package test;
import chat_bot.Data;
import chat_bot.CityAnswerType;
import chat_bot.GameExitType;
import chat_bot.CityGame;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

class Test_Logic {

	
	
	@Test
	void testNoCity() {
		var game = new CityGame();
		var cities = new String[2];
		cities[0] = "Мальорка";
		cities[1] = "Актюбинск";
		Assert.assertEquals(CityAnswerType.INCORRECT_CITY, game.checkAnswer("Новосибирск", new Data(cities)));
	}
	
	@Test
	void testRightCity() {
		var game = new CityGame();
		var cities = new String[2];
		cities[0] = "Мальорка";
		cities[1] = "Актюбинск";
		Assert.assertEquals(CityAnswerType.CORRECT_INPUT, game.checkAnswer("Мальорка", new Data(cities)));
	}
	
	@Test
	void testCityStartsOnWrongLetter() {
		var api = new TestingApi(new String[] {"Москва", "стоп"});
		var game = new CityGame();
		game.startGame(api);
		var cities = new String[2];
		cities[0] = "Мальорка";
		cities[1] = "Актюбинск";
		Assert.assertEquals(CityAnswerType.INCORRECT_INPUT, game.checkAnswer("Мальорка", new Data(cities)));
	}
	
	@Test
	void testPlayerWin() {
		var api = new TestingApi(new String[] {"Новосибирск"});
		var game = new CityGame();
		Assert.assertEquals(GameExitType.PLAYER_WIN, game.startGame(api));
	}
	
	@Test
	void testComputeCityOnNotExistLetter() {
		var game = new CityGame();
		var cities = new String[2];
		cities[0] = "Мальорка";
		cities[1] = "Актюбинск";
		Assert.assertNull(game.computeCity('Л', new Data(cities)));
	}
	
	@Test
	void testComputeAnyCity() {
		var game = new CityGame();
		var cities = new String[2];
		cities[0] = "Мальорка";
		cities[1] = "Актюбинск";
		Assert.assertEquals("Мальорка", game.computeCity('М', new Data(cities)));
	}
	
	@Test
	void testComputeBestCity() {
		var game = new CityGame();
		var cities = new String[3];
		cities[0] = "Мальорка";
		cities[1] = "Актюбинск";
		cities[2] = "Анапа";
		Assert.assertEquals("Актюбинск", game.computeCity('А', new Data(cities)));
	}
	
	@Test
	void TestInterruptGame() {
		var api = new TestingApi(new String[] {"стоп"});
		var game = new CityGame();
		Assert.assertEquals(GameExitType.GAME_INTERRUPTED, game.startGame(api));	
	}
	
	@Test
	void TestStopGame() {
		var api = new TestingApi(new String[] {"сдаюсь"});
		var game = new CityGame();
<<<<<<< HEAD:Chat-Bot-master/src/test/java/test/Test_Logic.java
		Assert.assertEquals(GameExitType.PLAYER_LOOSE, game.StartGame(api));
=======
		Assert.assertEquals(GameExitType.PLAYER_LOOSE, game.startGame(api));	
>>>>>>> 544a96c84182b64c7d1b13c642b1bb990bdb7684:Chat-Bot-master/src/test/Test_Logic.java
	}
}
