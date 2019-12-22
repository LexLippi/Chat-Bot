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
	public boolean invite(String name) {
		return false;
	}

	@Override
	public void cancelInvision(boolean broadcast) {
		return;
	}

	@Override
	public void setButtonsProvider(IButtonsProvider provider) {
	}

	@Override
	public void outkeyboard(String message, ArrayList... buttons) {
		//ignore
	}

}
