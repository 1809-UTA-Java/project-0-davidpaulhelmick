package com.revature.bankApp;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RegisterState extends State {

	@Override
	void printMenu() {
		System.out.println("Registration:");
		System.out.println("Please enter a username");
	}

	@Override
	void acceptInput(String input) {
		try(Connection conn = ConnectionUtil.getConnection()) {
			String sql = "SELECT * FROM BankUsers WHERE username=?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, input);
			
			System.out.println("Please input password");
			String pass = sc.nextLine();
			
			System.out.println("Reconfirm password");
			String pass2 = sc.nextLine();
			
			if (pass != pass2) {
				System.out.println("Passwords do not match");
				State.state = new OpeningState();
				return;
			}
			
			if(!ps.execute()) {
				System.out.println("Username is already in use");
			}
			else {
				ResultSet rs = ps.executeQuery();
				sql = "INSERT INTO BankUsers (username,p4ssword,usertype) VALUES (?, ?, Customer)";
				PreparedStatement ps2 = conn.prepareStatement(sql);
				ps2.setString(1, input);
				ps2.setString(2, pass);
				ps2.execute();
				ps2.close();
				System.out.println("Registration complete");
				State.state = new LoginState();
				rs.close();
			}
			

			ps.close();
		} catch (SQLException ex) {
			ex.getMessage();
		} catch (IOException ex) {
			ex.getMessage();
		}

	}

}
