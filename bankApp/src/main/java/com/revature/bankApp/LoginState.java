package com.revature.bankApp;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginState extends State {

	@Override
	void printMenu() {
		System.out.println("Login:");
		System.out.println("Please input your username");
	}

	@Override
	void acceptInput(String input) {
		System.out.println("Please input password");
		String pass = sc.nextLine();
		try(Connection conn = ConnectionUtil.getConnection()) {
			String sql = "SELECT * FROM BankUsers WHERE username=?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, input);
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()) {
				if (pass.equals(rs.getString("p4ssword"))) {
					String userType = rs.getString("usertype");
					if (userType.equals("Customer")) {
						State.state = new CustomerState();
						State.username = input;
						State.password = pass;
					}
					else if (userType.equals("Employee")) {
						State.state = new EmployeeState();
						State.username = input;
						State.password = pass;
					}
					else if (userType.equals("Admin")) {
						State.state = new AdminState();
						State.username = input;
						State.password = pass;
					}
				}
				rs.close();
			}
			else {
				System.out.println("Invalid username or password");
				State.state = new OpeningState();
			}
			
			ps.close();
		} catch (SQLException ex) {
			ex.getMessage();
		} catch (IOException ex) {
			ex.getMessage();
		}
	}

}
