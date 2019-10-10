package test;

import chat_bot.ChatBot;


import org.junit.Assert;
import org.junit.jupiter.api.Test;



public class Test_Bot {

	@Test
	void TestIncorrectCommand() {
		var api = new TestingApi(new String[] {"asdf", "пока"});
		new ChatBot(api);
		Assert.assertEquals(3, api.answers.size());
		Assert.assertEquals("Я ещё не умею делать то, что ты хочешь.", api.answers.get(1));
		Assert.assertEquals("до встречи", api.answers.get(2));
	}
	
	@Test
	void TestGame() {
		var api = new TestingApi(new String[] {"играть"});
		try {
			new ChatBot(api);
		}
		catch (ArrayIndexOutOfBoundsException e) {
			Assert.assertEquals(2, api.answers.size());
			Assert.assertEquals("Твой ход: ", api.answers.get(1));
			return;
		}
		Assert.assertTrue("incorrect number of answers", false);
	}
}
