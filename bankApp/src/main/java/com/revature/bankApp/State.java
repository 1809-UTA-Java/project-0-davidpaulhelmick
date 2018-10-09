package com.revature.bankApp;

import java.util.Scanner;

public abstract class State {
	protected static State state = null;
	protected static String username = null;
	protected static String password = null;
	protected static boolean quit = false;
	protected static int account = -1;
	
	protected static Scanner sc = new Scanner(System.in);
	
	protected State() {
		State.state = this;
	}
	
	abstract void printMenu();
	
	abstract void acceptInput(String input);
	
	public static void run() {
		while (!quit) {
			state.printMenu();
			String input = sc.nextLine();
			state.acceptInput(input);
		}
	}
}
