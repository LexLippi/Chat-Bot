package main.java;
import java.util.Scanner;

public class ChatBot {
	
	private static boolean run = true;
	private static Scanner input = new Scanner(System.in);

	public static void main(String[] args) {
		var message = "Привет, меня зовут Гена."
				+ " Я чат бот, который умеет играть в города."
				+ " Если хочешь играть в города, введи команду \"Играть\"";
		System.out.println(message);
		while (run) 
		{
			var title = getInput();
			if (title.toLowerCase().compareTo("играть") == 0) 
			{
				var exitType = CityGame.StartGame();
				if (exitType == GameExitType.PLAYER_WIN) {
					say("вам понравилась игра?");
					var answer = getInput();
					if (answer.toLowerCase().compareTo("да") == 0)
						say("замечательно");
					if (answer.toLowerCase().compareTo("нет") == 0)
						say("жаль");
				}
			}
			else if (title.toLowerCase().compareTo("пока") == 0) 
			{
				exit("до встречи");
			}
			else 
			{
				incorrectCommand();
			}
		}
	}
	
	public static void say(String massage) {
		System.out.println(massage);
	}
	
	public static String getInput() {
		return input.next();
	}
	
	private static void incorrectCommand() {
		System.out.println("Я ещё не умею делать то, что ты хочешь.");
	}
	
	private static void exit(String massage) {
		say(massage);
		run = false;
	}

}
