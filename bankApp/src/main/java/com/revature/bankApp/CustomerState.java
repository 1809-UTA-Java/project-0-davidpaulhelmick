package com.revature.bankApp;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerState extends UserState {

	@Override
	void printMenu() {
		System.out.println(State.username);
		System.out.println("[a]ccess account");
		System.out.println("[r]equest account");
		System.out.println("[v]iew accounts");
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
		case 'a':
		case 'A':
			System.out.println("Enter account number");
			String acc = sc.nextLine();
			try(Connection conn = ConnectionUtil.getConnection()) {
				String sql = "SELECT * FROM BankRelations WHERE username=? AND accountID=?";
				PreparedStatement ps = conn.prepareStatement(sql);
				ps.setString(1, State.username);
				ps.setString(2, acc);
				ResultSet rs = ps.executeQuery();
				if (rs.next()) {
					sql = "SELECT status FROM BankAccounts WHERE accountID=?";
					PreparedStatement ps2 = conn.prepareStatement(sql);
					ps2.setString(1, acc);
					ResultSet rs2 = ps2.executeQuery();
					rs2.next();
					if (rs2.getString("status") == "Approved") {
						State.account = acc;
						State.state = new AccountState();
					}
					else {
						System.out.println("Account is not approved");
					}
					ps2.close();
					rs2.close();
				}
				else {
					System.out.println("You do not have an account with that number");
				}
				
				ps.close();
				rs.close();
				
			} catch (SQLException ex) {
				ex.getMessage();
			} catch (IOException ex) {
				ex.getMessage();
			}
			break;
		case 'v':
		case 'V':
			try(Connection conn = ConnectionUtil.getConnection()) {
				String sql = "SELECT BankRelations.username, BankAccounts.accountID, BankAccounts.balance, BankAccounts.status FROM BankRelations INNERJOIN BankAccounts ON BankRelations.accountID=BankAccounts.accountID WHERE username=?";
				PreparedStatement ps = conn.prepareStatement(sql);
				ps.setString(1, State.username);
				ResultSet rs = ps.executeQuery();
				printResult(rs);
				ps.close();
				rs.close();
			} catch (SQLException ex) {
				ex.getMessage();
			} catch (IOException ex) {
				ex.getMessage();
			}
			break;
		case 'r':
		case 'R':
			try(Connection conn = ConnectionUtil.getConnection()) {
				String sql = "INSERT INTO BankRelations VALUES (?, account_sequence.NEXTVAL)";
				PreparedStatement ps = conn.prepareStatement(sql);
				ps.setString(1, State.username);
				ps.execute();
				ps.close();
				sql = "INSERT INTO BankAccounts VALUES (account_sequence.CURRBAL, 0.0, 'Pending'";
				PreparedStatement ps2 = conn.prepareStatement(sql);
				ps2.execute();
				ps2.close();
			} catch (SQLException ex) {
				ex.getMessage();
			} catch (IOException ex) {
				ex.getMessage();
			}
			break;
		default:
				System.out.println("Invalid input");
				break;
		}
	}
}
