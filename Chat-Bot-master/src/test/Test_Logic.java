package test;
import main.java.Data;
import main.java.GameLogic;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

class Test_Logic {

	
	
	@Test
	void testNoCity() {
		var logic = new GameLogic();
		var cities = new String[2];
		cities[0] = "Мальорка";
		cities[1] = "Актюбинск";
		Assert.assertEquals("Я не знаю такого города. Попробуй ещё раз", logic.getAnswer("Екатеринбург", new Data(cities)));
	}
	
	@Test
	void testRightCity() {
		var logic = new GameLogic();
		var cities = new String[2];
		cities[0] = "Мальорка";
		cities[1] = "Актюбинск";
		Assert.assertEquals("Актюбинск", logic.getAnswer("Мальорка", new Data(cities)));
	}
	
	@Test
	void testCityOnWrongLetter() {
		var logic = new GameLogic();
		var cities = new String[3];
		cities[0] = "Мальорка";
		cities[1] = "Актюбинск";
		cities[2] = "Курск";
		var data = new Data(cities);
		logic.getAnswer("Мальорка", data);
		Assert.assertEquals("Врёшь, не уйдешь! Повтори ход", logic.getAnswer("Лондон", data));
	}
	
	@Test
	void testComputerLose() {
		var logic = new GameLogic();
		var cities = new String[1];
		cities[0] = "Мальорка";
		Assert.assertEquals("Я проиграл", logic.getAnswer("Мальорка", new Data(cities)));
	}
}
