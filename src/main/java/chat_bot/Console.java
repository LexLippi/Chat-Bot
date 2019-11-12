package chat_bot;

import java.util.ArrayList;
import java.util.Scanner;

public class Console implements Api {
	
	private static Scanner input = new Scanner(System.in);

	@Override
	public void out(String massage) {
		System.out.println(massage);
	}

	@Override
	public void outkeyboard(ArrayList buttons, String message) {

	}

}
