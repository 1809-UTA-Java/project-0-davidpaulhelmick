package com.revature.bankApp;

public class OpeningState extends State {

	@Override
	void printMenu() {
		System.out.println("Welcome to BankApp");
		System.out.println("Please select an option");
		System.out.println("[l]ogin");
		System.out.println("[r]egister");
		System.out.println("[e]xit");
	}

	@Override
	void acceptInput(String input) {
		if (input.length() > 1) {
			System.out.println("Please input a single character");
			return;
		}
		else if (input.length() == 0) {
			System.out.println("No input detected");
			return;
		}
		char in = input.charAt(0);
		switch(in) {
		case 'l':
		case 'L':
			State.state = new LoginState();
			break;
		case 'r':
		case 'R':
			State.state = new RegisterState();
			break;
		case 'e':
		case 'E':
			State.quit = true;
			break;
		default:
			System.out.println("Invalid input");
		}
	}

}
