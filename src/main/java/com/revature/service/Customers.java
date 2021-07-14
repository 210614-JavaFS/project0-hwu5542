package com.revature.service;

import java.util.Scanner;

import java.sql.*;

public class Customers extends OperateFunds {	

	public static int customerInterface() {
		int userSelection;
		
		System.out.println("What would you like to do? \n"
				           + "1. Apply to Open A Bank Account. \n"
						   + "2. Check Balance. \n"
				           + "3. Deposit Funds. \n"
						   + "4. Withdraw Funds. \n"
				           + "5. Transfer Funds. \n"
						   + "6. Your Profile. \n"
				           + "0. Exit.");
		userSelection = getUserInput().nextInt();
		getUserInput().nextLine();
		return userSelection;
	}
	
	
	public static boolean changeInfo() {
		String userResponse = "";
		System.out.println("Would you like to change your information? (yes / no)");

		if (getUserInput().hasNext()) userResponse = new String(getUserInput().nextLine().toLowerCase());		
		
		if (userResponse.equals("yes")) return true;
		
		return false;
	}
	
	
	
	public static String[] collectUserInfo() {
		String[] userInfo = new String[5];
		System.out.println("What is your first name?");
		userInfo[0] = getUserInput().nextLine();
		System.out.println("What is your last name?");
		userInfo[1] = getUserInput().nextLine();
		System.out.println("What is your address?");
		userInfo[2] = getUserInput().nextLine();
		System.out.println("What is your phone number?");
		userInfo[3] = getUserInput().nextLine();
		System.out.println("What is your e-mail address?");
		userInfo[4] = getUserInput().nextLine();
		return userInfo;
	}
	
	
	
	public static int applyBankAccount() {
		String userResponse = "";
		int accountID = 0;
		
		System.out.println("Are you applying for a join account? (yes / no)");
		if (getUserInput().hasNext()) userResponse = new String(getUserInput().nextLine().toLowerCase());		
		
		if (userResponse.equals("yes")) {
			System.out.println("Do you need to create a new account? (yes / no)");
			if (getUserInput().hasNext()) userResponse = new String(getUserInput().nextLine().toLowerCase());
			if (userResponse.equals("yes")) {
				return 0;
			} else {
				while (true) {
					System.out.println("Please enter the account ID for the join account:");
					if (getUserInput().hasNext()) accountID = Integer.parseInt(getUserInput().nextLine());
					if (accountID > 1)
						return accountID;
					else
						System.out.println("  Invailid Account ID, Please Try Again.");
				}
			}
		}

		return 0;
	}	
}
