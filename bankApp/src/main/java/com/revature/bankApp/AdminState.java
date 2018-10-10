package com.revature.bankApp;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminState extends UserState {

	@Override
	void printMenu() {
		System.out.println(State.username);
		System.out.println("[e]xecute command");
		System.out.println("execute [q]uery");
		System.out.println("[l]ogout");
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
			logout();
			break;
		case 'q':
		case 'Q':
			System.out.println("Enter sql query");
			String sql = sc.nextLine();
			try(Connection conn = ConnectionUtil.getConnection()) {
				PreparedStatement ps = conn.prepareStatement(sql);
				ResultSet rs = ps.executeQuery();
				printResult(rs);
			} catch (SQLException ex) {
				ex.getMessage();
			} catch (IOException ex) {
				ex.getMessage();
			}
			break;
		case 'e':
		case 'E':
			System.out.println("Enter sql command");
			String sql2 = sc.nextLine();
			try(Connection conn = ConnectionUtil.getConnection()) {
				PreparedStatement ps = conn.prepareStatement(sql2);
				ps.execute();
			} catch (SQLException ex) {
				ex.getMessage();
			} catch (IOException ex) {
				ex.getMessage();
			}
			break;
		default:
			System.out.println("invalid input");
		}
	}

}
