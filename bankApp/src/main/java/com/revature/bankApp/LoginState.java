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
		try(Connection conn = ConnectionUtil.getConnection()) {
			String sql = "SELECT * FROM BankUsers WHERE username=?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, input);
			ResultSet rs = ps.executeQuery();
			
			System.out.println("Please input password");
			String pass = sc.nextLine();
			
			if(rs.next()) {
				if (pass == rs.getString("password")) {
					String userType = rs.getString("usertype");
					if (userType == "Customer") {
						State.state = new CustomerState();
						State.username = input;
						State.password = pass;
					}
					else if (userType == "Employee") {
						State.state = new EmployeeState();
						State.username = input;
						State.password = pass;
					}
					else if (userType == "Admin") {
						State.state = new AdminState();
						State.username = input;
						State.password = pass;
					}
				}
			}
			else {
				System.out.println("Invalid username or password");
			}
			
			rs.close();
			ps.close();
		} catch (SQLException ex) {
			ex.getMessage();
		} catch (IOException ex) {
			ex.getMessage();
		}
	}

}
