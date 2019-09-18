package test;
import main.GameLogic;
import main.Data;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

class Test_Logic {

	
	
	@Test
	void testNoCity() {
		var logic = new GameLogic();
		Assert.assertEquals("Я не знаю такого города. Попробуй еще раз", logic.answer("арарарарар", new Data()));
	}
	
	@Test
	void testRightCity() {
		var logic = new GameLogic();
		Assert.assertEquals("Агидель", logic.answer("Елабуга", new Data()));
	}
	
	@Test
	void testCityOnWrongLetter() {
		var data = new Data();
		var logic = new GameLogic();
		logic.answer("Ханой", data);
		Assert.assertEquals("Врешь, не уйдешь! Повтори ход", logic.answer("Елабуга", data));
	}
	
	@Test
	void testComputerLose() {
		var logic = new GameLogic();
		Assert.assertEquals("Я проиграл", logic.answer("Гомель", new Data()));
	}
}
