package main;
import java.util.Scanner;

public class CityGameChatBot {
	public static void main(String[] args) {
		var message = "������, ���� ����� ����."
				+ " � ��� ���, ������� ����� ������ � ������."
				+ " ���� ������ ������ � ������, ����� ������� \"������\"";
		System.out.println(message);
		var data = new Data();
		var logic = new GameLogic();
		var input = new Scanner(System.in);
		while (true) 
		{
			var isEnding = false;
			var title = input.next();
			if (title.toLowerCase().compareTo("������") == 0) 
			{
				while (true) 
				{
					System.out.print("���� ���: ");
					var inputString = input.next();
					if (inputString.toLowerCase().compareTo("����") == 0) {
						isEnding = true;
						break;
					}
					var result = logic.answer(inputString, data);
					System.out.println("���� �������: " + result);
					if (result.compareTo("� ��������") == 0) 
					{
						isEnding = true;
						break;
					}
				}
			}
			else 
			{
				System.out.println("� ��� �� ���� ������ ��, ��� �� ������.");
				isEnding = true;
				break;
			}
			if (isEnding) 
				break;
		}
		input.close();
	}
}
