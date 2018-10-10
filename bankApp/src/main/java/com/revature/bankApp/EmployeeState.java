package com.revature.bankApp;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeState extends UserState {

	@Override
	void printMenu() {
		System.out.println(State.username);
		System.out.println("view [c]ustomers");
		System.out.println("view customer-account [o]wnership");
		System.out.println("[v]iew accounts");
		System.out.println("view [p]ending accounts");
		System.out.println("[a]pprove an account");
		System.out.println("[d]eny an account");
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
		case 'c':
		case 'C':
			try(Connection conn = ConnectionUtil.getConnection()) {
				String sql = "SELECT * FROM BankUsers WHERE usertype='Customer'";
				PreparedStatement ps = conn.prepareStatement(sql);
				ResultSet rs = ps.executeQuery();
				printResult(rs);
			} catch (SQLException ex) {
				ex.getMessage();
			} catch (IOException ex) {
				ex.getMessage();
			}
			break;
		case 'o':
		case 'O':
			try(Connection conn = ConnectionUtil.getConnection()) {
				String sql = "SELECT * FROM BankRelationships";
				PreparedStatement ps = conn.prepareStatement(sql);
				ResultSet rs = ps.executeQuery();
				printResult(rs);
			} catch (SQLException ex) {
				ex.getMessage();
			} catch (IOException ex) {
				ex.getMessage();
			}
			break;
		case 'v':
		case 'V':
			try(Connection conn = ConnectionUtil.getConnection()) {
				String sql = "SELECT * FROM BankAccounts";
				PreparedStatement ps = conn.prepareStatement(sql);
				ResultSet rs = ps.executeQuery();
				printResult(rs);
			} catch (SQLException ex) {
				ex.getMessage();
			} catch (IOException ex) {
				ex.getMessage();
			}
			break;
		case 'p':
		case 'P':
			try(Connection conn = ConnectionUtil.getConnection()) {
				String sql = "SELECT * FROM BankAccounts WHERE status='Pending'";
				PreparedStatement ps = conn.prepareStatement(sql);
				ResultSet rs = ps.executeQuery();
				printResult(rs);
			} catch (SQLException ex) {
				ex.getMessage();
			} catch (IOException ex) {
				ex.getMessage();
			}
			break;
		case 'a':
		case 'A':
			System.out.println("Enter accountID to be approved");
			String acc = sc.nextLine();
			try(Connection conn = ConnectionUtil.getConnection()) {
				String sql = "UPDATE BankAccounts SET status='Approved' WHERE status='Pending' AND accountID=?";
				PreparedStatement ps = conn.prepareStatement(sql);
				ps.setString(1, acc);
				ps.execute();
			} catch (SQLException ex) {
				ex.getMessage();
			} catch (IOException ex) {
				ex.getMessage();
			}
			break;
		case 'd':
		case 'D':
			System.out.println("Enter accountID to be denied");
			String id = sc.nextLine();
			try(Connection conn = ConnectionUtil.getConnection()) {
				String sql = "UPDATE BankAccounts SET status='Denied' WHERE status='Pending' AND accountID=?";
				PreparedStatement ps = conn.prepareStatement(sql);
				ps.setString(1, id);
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
