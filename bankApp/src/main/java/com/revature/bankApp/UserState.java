package com.revature.bankApp;

public abstract class UserState extends State {

	abstract void printMenu();

	abstract void acceptInput(String input);
	
	protected void logout() {
		State.username = null;
		State.password = null;
		State.account = -1;
	}

}
