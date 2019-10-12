package chat_bot;

import java.util.Scanner;

public class Console implements Api {
	
	private static Scanner input = new Scanner(System.in);

	@Override
	public String in() {
		return input.nextLine();
	}

	@Override
	public void out(String massage) {
		System.out.println(massage);
	}

}
