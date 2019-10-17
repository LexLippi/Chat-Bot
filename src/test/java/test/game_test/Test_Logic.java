
package test.game_test;

import chat_bot.City;
import chat_bot.Data;
import chat_bot.game.CityGame;
import chat_bot.game.return_types.GameExitType;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import test.TestingApi;

class Test_Logic {
	@Test
	void testPlayerWin() {
		var api = new TestingApi(new String[] {"легкий", "орёл", "Актюбинск"});
		var data = new Data(new City[]
				{
						new City ("Мальорка", 100),
						new City ("Актюбинск", 100)
				});
		var game = new CityGame(api, data);
		Assert.assertEquals(GameExitType.PLAYER_WIN, game.startGame());
	}

	@Test
	void TestInterruptGame() {
		var api = new TestingApi(new String[] {"легкий", "орёл", "стоп"});
		var data = new Data(new City[]
				{
						new City ("Мальорка", 100),
						new City ("Актюбинск", 100)
				});
		var game = new CityGame(api, data);
		Assert.assertEquals(GameExitType.GAME_INTERRUPTED, game.startGame());
	}

    @Test
	void TestStopGame() {
		var api = new TestingApi(new String[] {"легкий", "орёл", "сдаюсь"});
		var data = new Data(new City[]
				{
						new City ("Мальорка", 100),
						new City ("Актюбинск", 100)
				});
		var game = new CityGame(api, data);
		Assert.assertEquals(GameExitType.PLAYER_LOOSE, game.startGame());
	}
}
