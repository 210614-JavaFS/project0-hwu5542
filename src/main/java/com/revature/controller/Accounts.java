package com.revature.controller;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

import com.revature.dao.AccountsDAO;

public class Accounts extends AccountsDAO {
	
	private static class privateInfo {
		static String username;
		static ArrayList<Integer> applicationID = new ArrayList<Integer>();
	}
	
	
	protected static void register(Scanner userInput) throws SQLException {
		ResultSet selectResult = null;

		String username = "",
			   password = "";		
		
		while (true) {
			System.out.println("Please Enter your Username:");
			if (userInput.hasNext()) username = new String(userInput.nextLine());

			if (username.length() > 10) {
				System.out.println("Username is too long.  Only allow less than 10 characters.");
				continue;
			}
			
			selectResult = AccountsDAO.selectDB("SELECT login_accounts.usernames FROM login_accounts WHERE usernames = '" + username + "'");
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

		if (AccountsDAO.updateDB("INSERT INTO login_accounts (usernames, passwords, account_type, account_id) VALUES ('" + username + "', '" + password + "', 1, 1)")>0)
			System.out.println("  Register Succesfull !");
		else
			System.out.println("  Register Fail, Please Try Again Later.");
	}


	
	protected static int checkValidLogin(String username, String password) throws SQLException {
		ResultSet selectResult = null;

		int accountType = 0;

		selectResult = AccountsDAO.selectDB("SELECT account_type FROM login_accounts WHERE usernames = '" + username + "' AND  passwords = '" + password + "'");

		if (selectResult.next()) {
			privateInfo.username = username;
			accountType = selectResult.getInt(1);
		}
		
		return accountType;
	}

	
	
	protected static void getAccountInfo() throws SQLException { //get customer info from database
		String command = "SELECT first_name, last_name, address, phone, email, join_date, account_id FROM login_accounts WHERE usernames = '" + privateInfo.username + "'";
		ResultSet selectResult = AccountsDAO.selectDB(command);
		if (selectResult.next()) {
			System.out.println("     User Info "
							 + "\n Account ID:      " + (selectResult.getInt(7) == 1? "Account Not Created or Application Denied": selectResult.getInt(7))
							 + "\n First Name:      " + (selectResult.getString(1) == null? "N/A": selectResult.getString(1))
							 + "\n Last Name:       " + (selectResult.getString(2) == null? "N/A": selectResult.getString(2))
							 + "\n Home Address:    " + (selectResult.getString(3) == null? "N/A": selectResult.getString(3))
							 + "\n Phone Number:    " + (selectResult.getString(4) == null? "N/A": selectResult.getString(4))
							 + "\n E-mail Address:  " + (selectResult.getString(5) == null? "N/A": selectResult.getString(5))
							 + "\n Register Date:   " + selectResult.getTimestamp(6).toString());
		} else {
			System.out.println("  Fail to Find Your Information !");
		}
	}
	
	
	
	protected static void setAccountInfo(String[] userInfo) throws SQLException { // set customer info
		String command = "UPDATE login_accounts SET first_name = '#0', last_name = '#1', address = '#2', phone = '#3', email = '#4' WHERE usernames = '" + privateInfo.username + "'";
		
		for (int i = 0; i<5; i++) {
			command = command.replaceFirst("#"+ i, userInfo[i]);
		}		

		if (AccountsDAO.updateDB(command)>0)
			System.out.println("  User Information Update Succesfull !");
		else
			System.out.println("  Information Update Fail, Please Try Again Later.");
	}

	
	
	protected static void applyBankAccount(int accountID) throws SQLException {
		ResultSet resultSet = null;
		String command = "";

		if (accountID >= 0) { //add new application 
			command = "INSERT INTO applications (usernames) VALUES ('" + privateInfo.username + "')";
			if (AccountsDAO.updateDB(command) == 0) {
				System.out.println("  Create New Application Fail!\n  Please Try Again Later.");
				return;
			}
			
			if (accountID > 0) { //add join account
				command = "SELECT account_id FROM login_accounts WHERE usernames = '" + privateInfo.username + "'";
				resultSet = AccountsDAO.selectDB(command);
			
				command = "UPDATE login_accounts SET account_id = '" + accountID + "' WHERE usernames = '" + privateInfo.username + "'";
				AccountsDAO.updateDB(command);

				resultSet.next();
				command = "DELETE FROM bank_accounts WHERE account_id = '" + resultSet.getInt(1) + "'";
				AccountsDAO.updateDB(command);

			} else {
				command = "INSERT INTO bank_accounts (account_fund) VALUES (0)";

				resultSet = AccountsDAO.insertBankDB(command);

				if (resultSet.next()) {
					 command = "UPDATE login_accounts SET account_id = '" + resultSet.getInt(1) + "' WHERE usernames = '" + privateInfo.username + "'";
					 AccountsDAO.updateDB(command);
				} else {
					System.out.println("  Fail to Create New account ! +\n"
									   + "Please Try Again Later.");				
				}
			}
			
		} else { //invalid account
			System.out.println("  Invalid Account ID !");
			return;
		}
		
		System.out.println("  Application summited!\n  Your bank account access will be available after passing credential review process.");
	}

	

	protected static ArrayList<String> getNewApplication() throws SQLException {
		ArrayList<String> applications = new ArrayList<String>();		
		ResultSet selectResult = AccountsDAO.selectDB("SELECT login_accounts.usernames, first_name, last_name, address, phone, email, account_user_one, account_user_two, login_accounts.account_id, application_id FROM login_accounts, applications, bank_accounts \n"
													+ "  WHERE applications.usernames = login_accounts.usernames AND login_accounts.account_id = bank_accounts.account_id;");
		while (selectResult.next()) {
			applications.add(String.format("%10s|", selectResult.getString(1)) + String.format("%10s|", selectResult.getString(2)) 				
						   + String.format("%10s|", selectResult.getString(3)) + String.format("%50s|", selectResult.getString(4))
						   + String.format("%12s|", selectResult.getString(5)) + String.format("%20s|", selectResult.getString(6))
						   + String.format("%5d|", selectResult.getInt(9)) + String.format("%10s|", selectResult.getString(7)) + String.format("%10s", selectResult.getString(8)));
			privateInfo.applicationID.add(selectResult.getInt(10));
		}
		return applications;
	}
	
	
	
	protected static void approveBankAccount(boolean[] approveRegisterFlag) throws SQLException {
		String command = "";
		ResultSet resultSet = null;
		int countSuccess = 0;

		for (int i = 0; i<approveRegisterFlag.length; i++) {
			if (approveRegisterFlag[i]) {
				command = "SELECT login_accounts.account_id, login_accounts.usernames, account_user_one FROM bank_accounts, login_accounts, applications WHERE application_id = " + privateInfo.applicationID.get(i) + " AND applications.usernames = login_accounts.usernames AND login_accounts.account_id = bank_accounts.account_id";  
				resultSet = AccountsDAO.selectDB(command);

				if (resultSet.next()) {
					if (resultSet.getString(3) == null) {
						command = command.replaceFirst("SELECT account_user_one FROM bank_accounts,", "UPDATE bank_accounts SET account_user_one = login_accounts.usernames FROM");
						if (AccountsDAO.updateDB(command)>0) {
							countSuccess++;
						}
					} else if (!resultSet.getString(3).equals(resultSet.getString(2))){
						command = command.replaceFirst("SELECT account_user_one FROM bank_accounts,", "UPDATE bank_accounts SET account_user_two = login_accounts.usernames FROM");
						if (AccountsDAO.updateDB(command)>0) countSuccess++;
					}
				}
			} else {
				command = "UPDATE login_accounts SET account_id = 1 FROM bank_accout WHERE login_accounts.usernames =  AND bank_accounts.";  
				AccountsDAO.updateDB(command);
			}
		}
		command = "TRUNCATE TABLE applications";
		AccountsDAO.updateDB(command);
		
		command = "DELTE FROM bank_accounts WHERE account_user_one = null AND account_user_two = null";
		AccountsDAO.updateDB(command);
		
		System.out.println("  Successfully Updated " + countSuccess + " User Accounts.");
	}

	
	
	protected static void operateFunds(double fundsAmount) throws SQLException {
		String command = "UPDATE bank_accounts SET account_fund = " + String.format("%.2f", fundsAmount) + " WHERE account_user_one = '" + privateInfo.username + "' OR account_user_two = '" + privateInfo.username + "'";
		if (AccountsDAO.updateDB(command)>0) System.out.println("  Balance Changed !");
		else System.out.println("  Operating Balance Fail !\n  Please Try Again Later.");
	}


	
	protected static void operateFunds(int accountID, double fundsAmount) throws SQLException {
		String command = "UPDATE bank_accounts SET account_fund = account_fund - " + String.format("%.2f", fundsAmount) + " WHERE account_user_one = '" + privateInfo.username + "' OR account_user_two = '" + privateInfo.username + "'";
		if (AccountsDAO.updateDB(command)>0) {		
			command = "UPDATE bank_accounts SET account_fund = account_fund + " + String.format("%.2f", fundsAmount) + " WHERE account_id = '" + accountID + "'";
			if (AccountsDAO.updateDB(command)>0) System.out.println("  Funds Transferring Success !");
			else {
				command = "UPDATE bank_accounts SET account_fund = account_fund + " + String.format("%.2f", fundsAmount) + " WHERE account_user_one = '" + privateInfo.username + "' OR account_user_two = '" + privateInfo.username + "'";
				System.out.println("  Funds Transferring Failure ! \n  Reverting Changes... ");
				if (AccountsDAO.updateDB(command)>0) System.out.println("  Changes Reverted !");
				else System.out.println("  Changes Reverting Failure ! \n  Please Conntect Bank Administrator.");
			}
		} else {
			System.out.println("  Funds Transferring Failure ! \n" + "  No Changes was made.");
		}
	}

	
	
	protected static double getFunds() throws SQLException{
		double funds;
		ResultSet selectResult = AccountsDAO.selectDB("SELECT account_fund FROM bank_accounts WHERE account_user_one = '" + privateInfo.username + "' OR account_user_two = '" + privateInfo.username + "'");
		if (selectResult.next()) {
			funds = selectResult.getDouble(1);
			return funds;
		} else {
			System.out.println("  Bank Account Has Not Been Created !");
			return -1;
		}
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
}
