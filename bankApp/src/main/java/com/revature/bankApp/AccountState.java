package com.revature.bankApp;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountState extends State {

	@Override
	void printMenu() {
		System.out.println(State.account);
		System.out.println("[w]ithdraw");
		System.out.println("[d]eposit");
		System.out.println("[t]ransfer");
		System.out.println("[v]iew balance");
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
		double balance = 0;
		double amount = 0;
		try(Connection conn = ConnectionUtil.getConnection()) {
			String sql = "SELECT balance FROM BankAccounts WHERE accountID=?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, State.account);
			ResultSet rs = ps.executeQuery();
			rs.next();
			balance = rs.getDouble("balance");
			
			ps.close();
			rs.close();
			
		} catch (SQLException ex) {
			ex.getMessage();
		} catch (IOException ex) {
			ex.getMessage();
		}
		switch(in) {
		case 'e':
		case 'E':
			State.account = "-1";
			State.state = new CustomerState();
			break;
		case 'w':
		case 'W':
			System.out.println("Input ammount to be withdrawn");
			amount = sc.nextDouble();
			if (amount < 0) System.out.println("You can't withdraw a negative sum");
			else if (amount > balance) System.out.println("Your account balance is not that large");
			else {
				balance -= amount;
				try(Connection conn = ConnectionUtil.getConnection()) {
					String sql = "UPDATE BankAccounts SET balance=? WHERE accountID=?";
					PreparedStatement ps = conn.prepareStatement(sql);
					ps.setDouble(1, balance);
					ps.setString(2, State.account);
					ps.execute();
					
					ps.close();
				} catch (SQLException ex) {
					ex.getMessage();
				} catch (IOException ex) {
					ex.getMessage();
				}
			}
			break;
		case 'd':
		case 'D':
			System.out.println("Input ammount to be deposited");
			amount = sc.nextDouble();
			if (amount < 0) System.out.println("You can't deposit a negative sum");
			else {
				balance += amount;
				try(Connection conn = ConnectionUtil.getConnection()) {
					String sql = "UPDATE BankAccounts SET balance=? WHERE accountID=?";
					PreparedStatement ps = conn.prepareStatement(sql);
					ps.setDouble(1, balance);
					ps.setString(2, State.account);
					ps.execute();
					
					ps.close();
				} catch (SQLException ex) {
					ex.getMessage();
				} catch (IOException ex) {
					ex.getMessage();
				}
			}
			break;
		case 't':
		case 'T':
			System.out.println("Enter the account to transfer to");
			String target = sc.nextLine();
			System.out.println("Input ammount to be transfered");
			amount = sc.nextDouble();
			if (amount < 0) System.out.println("You can't trasfer a negative sum");
			else if (amount > balance) System.out.println("Your account balance is not that large");
			else {
				try(Connection conn = ConnectionUtil.getConnection()) {
					String sql = "SELECT * FROM BankAccounts WHERE accountID=?";
					PreparedStatement ps = conn.prepareStatement(sql);
					ps.setString(1, target);
					if(ps.execute()) {
						sql = "{CALL TRANSFER_SP(?, ?, ?)}";
						CallableStatement cs = conn.prepareCall(sql);
						cs.setString(1, State.account);
						cs.setString(2, target);
						cs.setDouble(3, amount);
						cs.execute();
						cs.close();
					}
					else {
						System.out.println(target + " does not exist");
					}
					
					ps.close();
				} catch (SQLException ex) {
					ex.getMessage();
				} catch (IOException ex) {
					ex.getMessage();
				}
			}
			break;
		case 'b':
		case 'B':
			System.out.println("You have a balance of $" + balance);
			break;
		default:
			System.out.println("Invalid input");
		}
	}

}
