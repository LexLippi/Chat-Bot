package main;
import java.util.Scanner;

public class CityGameChatBot {
	public static void main(String[] args) {
		var message = "Привет, меня зовут Гена."
				+ " Я чат бот, который умеет играть в города."
				+ " Если хочешь играть в города, введи команду \"Играть\"";
		System.out.println(message);
		var data = new Data();
		var logic = new GameLogic();
		var input = new Scanner(System.in);
		while (true) 
		{
			var isEnding = false;
			var title = input.next();
			if (title.toLowerCase().compareTo("играть") == 0) 
			{
				while (true) 
				{
					System.out.print("Твой ход: ");
					var inputString = input.next();
					if (inputString.toLowerCase().compareTo("стоп") == 0) {
						isEnding = true;
						break;
					}
					var result = logic.answer(inputString, data);
					System.out.println("Гена говорит: " + result);
					if (result.compareTo("Я проиграл") == 0) 
					{
						isEnding = true;
						break;
					}
				}
			}
			else 
			{
				System.out.println("Я ещё не умею делать то, что ты хочешь.");
				isEnding = true;
				break;
			}
			if (isEnding) 
				break;
		}
		input.close();
	}
}
