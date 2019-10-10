package test;

<<<<<<< HEAD:Chat-Bot-master/src/test/java/test/Test_Bot.java
import chat_bot.ChatBot;

=======
import main.java.ChatBot;
import main.java.CityAnswerType;
import main.java.CityGame;
import main.java.Data;
>>>>>>> 544a96c84182b64c7d1b13c642b1bb990bdb7684:Chat-Bot-master/src/test/Test_Bot.java

import org.junit.Assert;
import org.junit.jupiter.api.Test;



class Test_Bot {

	@Test
<<<<<<< HEAD:Chat-Bot-master/src/test/java/test/Test_Bot.java
	void TestIncorrectCommand() {
=======
	void testIncorrectCommand() {
>>>>>>> 544a96c84182b64c7d1b13c642b1bb990bdb7684:Chat-Bot-master/src/test/Test_Bot.java
		var api = new TestingApi(new String[] {"asdf", "пока"});
		new ChatBot(api);
		Assert.assertEquals(3, api.answers.size());
		
		Assert.assertEquals("Я ещё не умею делать то, что ты хочешь.", api.answers.get(1));
		Assert.assertEquals("до встречи", api.answers.get(2));
	}
	
	@Test
<<<<<<< HEAD:Chat-Bot-master/src/test/java/test/Test_Bot.java
	void TestGame() {
=======
	void testGame() {
>>>>>>> 544a96c84182b64c7d1b13c642b1bb990bdb7684:Chat-Bot-master/src/test/Test_Bot.java
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
