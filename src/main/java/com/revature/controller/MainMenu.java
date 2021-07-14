package com.revature.controller;

import java.util.ArrayList;
import java.util.Scanner;
import java.sql.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.revature.model.Admins;
import com.revature.model.Customers;
import com.revature.model.Employees;
import com.revature.service.OperateAccounts;
import com.revature.service.OperateFunds;


public class MainMenu extends Accounts {

	public static void userLogin(Scanner userInput) throws SQLException {
		String username = "";
		String password = "";
		String userResponse = "";
		int accountType = 0;
		int userSelection = 1;
		double accountBalance = 0;
		ArrayList<String> accountsInfo = new ArrayList<String>();
		
		System.out.println("Are you a new customer? (yes / no)");
		if (userInput.hasNext()) userResponse = new String(userInput.nextLine().toLowerCase());		
		
		if (userResponse.equals("yes")) Accounts.register(userInput);
		
		System.out.println("Please Enter Your Username: ");
		if (userInput.hasNext()) username = new String(userInput.nextLine());
		
		System.out.println("Please Enter Your Password: ");
		if (userInput.hasNext()) password = new String(userInput.nextLine());

		
		accountType = Accounts.checkValidLogin(username, password);

		if (accountType > 0) {
			System.out.println("User Credential Confirm \n  Login Successful ! "); //success login
			switch (accountType) {
			
			//CUSTOMER MENU
				case 1:
					System.out.println("Welecome back, Customer");
					Customers.setUserInput(userInput);
					
					while (userSelection > 0) {
						userSelection = Customers.customerInterface();
						OperateFunds.setBalance(Accounts.getFunds());
						
						switch (userSelection) {
							case 1:
								changeInfo(userInput);
								Accounts.applyBankAccount(Customers.applyBankAccount());
								break;
							case 2:
								System.out.println("Account Balance : "+ "\u0024" + String.format("%.2f", accountBalance));
								break;
							case 3:
								Accounts.operateFunds(OperateFunds.depositFunds());
								break;
							case 4:
								Accounts.operateFunds(OperateFunds.withdrawFunds());
								break;
							case 5:
								Accounts.operateFunds(OperateFunds.transferFunds(), OperateFunds.operateFunds(true));
								break;
							case 6:
								changeInfo(userInput);
								break;
						}

						if (userSelection > 2 && userSelection < 6)
							System.out.println("Your New Balance : "+ "\u0024" + String.format("%.2f", Accounts.getFunds()));
					}
					break;

			//EMPLOYEE MENU
				case 2:
					System.out.println("Welecome back, Bank Employee");
					
					while (userSelection > 0) {
						userSelection = Employees.employeeInterface(userInput);
						
						switch (userSelection) {
							case 1:
								accountsInfo = Accounts.viewAccounts(OperateAccounts.searchAccounts(userInput));
								
								for (String singleAccountInfo: accountsInfo) {
									System.out.println(singleAccountInfo);
								}
								break; 
							case 2:
								if (Accounts.checkNewApplication())
									viewApplications(userInput);
								else
									System.out.println("No new applications.");
							break;
						}
					}
					break;
				
			//ADMIN MENU
				case 3:
					System.out.println("Welecome back, Bank Administrator");
					Admins.setUserInput(userInput);

					while (userSelection > 0) {
						userSelection = Admins.adminInterface();
					
						switch (userSelection) {
							case 1:
								accountsInfo = Accounts.viewAccounts(OperateAccounts.searchAccounts(userInput));
								
								for (String singleAccountInfo: accountsInfo) {
									System.out.println(singleAccountInfo);
								}
								break;
								
							case 2:
								if (Accounts.checkNewApplication())
									viewApplications(userInput);
								else
									System.out.println("No new applications.");
								break;
								
							case 3:									
								switch (Admins.editAccounts()) {
									case 1:
										Accounts.deleteAccount("FROM bank_accounts WHERE account_id = " + Admins.editDeletion("Account ID"));
										break;
									case 2:
										Accounts.deleteAccount("FROM login_accounts WHERE usernames = '" + Admins.editDeletion("username") + "'");
										break;
									case 3:
										System.out.println("Please enter the Account ID to operate balance:");
										tempAccessAccount(userInput, Admins.getTempID(), "");
										break;
									case 4:
										System.out.println("Please enter the Username to operate balance:");
										tempAccessAccount(userInput, 0, userInput.nextLine());
										break;
								}
								break;
						}
					}
					break;
			}
		}
		else 
			System.out.println("User Credential Invalid \n Please Start Over"); //fail login
	}

	
	
	public static void changeInfo(Scanner userInput) throws SQLException {
		String[] userInfo = new String[5];

		Accounts.getAccountInfo();
		if (Customers.changeInfo()) {
			userInfo = Customers.collectUserInfo();
			Accounts.setAccountInfo(userInfo);
		}
	}
	
	public static void viewApplications(Scanner userInput) throws SQLException{
		ArrayList<String> accountsInfo = new ArrayList<String>();
		boolean[] approveRegisterFlag = null;
		accountsInfo = Accounts.getNewApplication();							
		approveRegisterFlag = new boolean [accountsInfo.size()];
		String userResponse = "";
	
		System.out.println("Show Entire Batch? (yes / no)");
		if (userInput.hasNext()) userResponse = new String(userInput.nextLine().toLowerCase());		
		
		System.out.println(String.format("%10s|", "Username") + String.format("%10s|", "First Name") + String.format("%10s|", "Last Name")
						 + String.format("%50s|", "Current Address") + String.format("%12s|", "Phone Number") + String.format("%20s|", "E-mail")
     				     + String.format("%5s|", "ID #") + String.format("%10s|", "Main User") + String.format("%10s", "Join User"));

		if (userResponse.equals("yes")) {
			for (String singleAccountInfo: accountsInfo)
				System.out.println(singleAccountInfo);
		}
		

		System.out.println("Process each application? (yes / no)");
		if (userInput.hasNext()) userResponse = new String(userInput.nextLine().toLowerCase());		
		
		if (userResponse.equals("yes")) {
			int i = 0;
			for (String singleAccountInfo: accountsInfo) {
				if (OperateAccounts.processApplications(singleAccountInfo, userInput))
					approveRegisterFlag[i] = true;
				i++;
			}
		}

		System.out.println("Approve entire batch? (yes / no)");
		if (userInput.hasNext()) userResponse = new String(userInput.nextLine().toLowerCase());		
		
		if (userResponse.equals("yes")) {
			for (int i = 0; i<approveRegisterFlag.length; i++)
				approveRegisterFlag[i] = true;
		}
		
		Accounts.approveBankAccount(approveRegisterFlag);
	}
	
	public static void tempAccessAccount(Scanner userInput, int tempID, String tempUser) throws SQLException {
		if (tempUser == "")
			tempUser = Accounts.editAccounts(tempID);
		if (tempID == 0)
			tempID = Accounts.editAccounts(tempUser);
		if (!(tempUser.equals("") || tempID == 0)) {
			OperateFunds.setBalance(Accounts.getFunds());
			int userSelection = 0;
			System.out.println("What would you like to do? \n"
			           + "1. Deposit Funds. \n"
					   + "2. Withdraw Funds. \n"
			           + "3. Transfer Funds.");
	 		if (userInput.hasNext()) userSelection = Integer.parseInt(userInput.nextLine());
			switch (userSelection) {
				case 1:
					Accounts.operateFunds(OperateFunds.depositFunds());
					break;
				case 2:
					Accounts.operateFunds(OperateFunds.withdrawFunds());
					break;
				case 3:
					Accounts.operateFunds(OperateFunds.transferFunds(), OperateFunds.operateFunds(true));
					break;
			}
			Admins.setBalance(0);		
			Accounts.dcAccounts(tempID, tempUser);
		}
	}
}
