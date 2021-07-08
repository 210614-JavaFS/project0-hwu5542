package com.revature.model;

import java.sql.*;
import java.util.Scanner;

public class Accounts{
	private class accountInfo {
		int accountID;
		String accountBalance;
		String accountUserOne;
		String accountUserTwo;
	}
	
	private class userInfo {
		String firstName;
		String LastName;
		String address;
		String phone;
		String email;
		String joinDate;
	}

	private static class loginInfo {
		static String username;
		static String password;
	}
	
	
	protected static void register(Scanner userInput) throws SQLException {
		Connection conn = Accounts.establishConnection();
		Statement stmt = conn.createStatement();
		ResultSet selectResult = null;

		String username = "",
			   password = "";		
		
		while (true) {
			System.out.println("Please Enter your Username:");
			if (userInput.hasNext()) username = new String(userInput.nextLine());
						
			selectResult = stmt.executeQuery("SELECT * FROM login_accounts WHERE usernames = '" + username + "'");
			if (selectResult.next())
				System.out.println("Username is taken, please try again.");
			else
				break;
		}

		while(true) {
			System.out.println("Please Enter your Password:");
			if (userInput.hasNext()) password = new String(userInput.nextLine());
			System.out.println("Please Re-enter Your Password:");
			if (userInput.hasNext()) {
				if (password.equals(userInput.nextLine()))
					break;
				else
					System.out.println("Re-enter Password not Match With First Enter");
			}
		}

		if (stmt.executeUpdate("INSERT INTO login_accounts (usernames, passwords, account_type) VALUES ('" + username + "', '" + password + "', 1 )")>0)
			System.out.println("  Register Succesfull !");
		else
			System.out.println("  Register Fail, Please Try Again Later.");
		conn.close();
	}

	protected static int checkValidLogin(String username, String password) throws SQLException {
		Connection conn = Accounts.establishConnection();		
		Statement stmt = conn.createStatement();
		ResultSet selectResult = null;

		int accountType = 0;

		selectResult = stmt.executeQuery("SELECT account_type FROM login_accounts WHERE usernames = '" + username + "' AND  passwords = '" + password + "'");

		if (selectResult.next()) {
			loginInfo.username = username;
			loginInfo.password = password;
			accountType = selectResult.getInt(1);
		}
		
		conn.close();
		return accountType;
	}
	
	protected static void setAccountInfo(String[] userInfo) throws SQLException { // set customer info
		Connection conn = Accounts.establishConnection();		
		Statement stmt = conn.createStatement();
		String command = "UPDATE login_accounts SET first_name = '#0', last_name = '#1', address = '#2', phone = '#3', email = '#4' WHERE usernames = '" + loginInfo.username + "'";
		
		for (int i = 0; i<5; i++) {
			command = command.replaceFirst("#"+ i, userInfo[i]);
		}		

		if (stmt.executeUpdate(command)>0)
			System.out.println("  User Information Update Succesfull !");
		else
			System.out.println("  Information Update Fail, Please Try Again Later.");
		conn.close();		
		
		return;
	}
	
	protected void applyBankAccount() {
				
	}

	protected static void operateFunds(int operation, double amount) {
		return;
	}
	
	protected void editAccounts() {
		return;
	}
	
	protected void viewAccounts() {
		return;
	}

	protected void registerPermission() {
		return;
	}
	
	
//later plans
	protected void retriveInfo() { //get customer info from database
		return;
	}
	
	private static Connection establishConnection() throws SQLException {
		String url = "jdbc:postgresql://hwu5542-db.cxoqspxuezyw.us-east-2.rds.amazonaws.com:5432/bankdb";
		String user = "postgres";
		String password = "password";
		Connection conn = DriverManager.getConnection(url, user, password);	
		
		return conn;
	}
}
