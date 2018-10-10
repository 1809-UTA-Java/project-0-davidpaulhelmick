package com.revature.bankApp;

import java.util.Scanner;

import java.sql.*;

public abstract class State {
	protected static State state = null;
	protected static String username = null;
	protected static String password = null;
	protected static boolean quit = false;
	protected static String account = "-1";
	
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
	
	protected void printResult(ResultSet resultSet) throws SQLException{
		ResultSetMetaData rsmd = resultSet.getMetaData();
		int columnsNumber = rsmd.getColumnCount();
		while (resultSet.next()) {
			for (int i = 1; i <= columnsNumber; i++) {
				if (i > 1) System.out.print(", ");
				String columnValue = resultSet.getString(i);
				System.out.print(columnValue + " " + rsmd.getColumnName(i));
			}
			System.out.println("");
		}
	}
}
