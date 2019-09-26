package main.java;

import java.util.Scanner;

public class Console implements Api {
	
	private static Scanner input = new Scanner(System.in);

	@Override
	public String in() {
		return input.next();
	}

	@Override
	public void out(String massage) {
		System.out.print(massage);
	}

}
