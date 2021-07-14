package com.revature.service;

import java.util.Scanner;

public class OperateAccounts {
	public static boolean processApplications(String accountInfo, Scanner userInput) {
		String userResponse = "";
		
		System.out.println(accountInfo.replaceAll("null", " N/A"));

		System.out.println("Approval to Bank Account Register? (yes / no)");

		if (userInput.hasNext()) userResponse = new String(userInput.nextLine().toLowerCase());		
		
		if (userResponse.equals("yes")) return true;
		
		return false;
	}
	
	
	
	public static String searchAccounts(Scanner userInput) {
		String userResponse = "";
		int userSelection = 0;
		
		System.out.println("How would you like to find the account you looking for?\n"
						 + "1. Show All User Accounts.\n"
						 + "2. Search User Accounts by First Name and Last Name. \n"
						 + "3. Search User Accounts by Username. \n"
						 + "4. Show All Bank Accounts.\n"
						 + "5. Search Bank Accounts by Account ID\n"
						 + "6. Search Bank Accounts by Username");
		userSelection = Integer.parseInt(userInput.nextLine());
		
		switch(userSelection) {
			case 1:
				userResponse = "FROM login_accounts ORDER BY join_date";
				break;
			case 2:
				userResponse = "FROM login_accounts WHERE first_name = '" + OperateAccounts.getKeywords(userInput) + "' ORDER BY first_name, last_name";
				userResponse = userResponse.replaceFirst("#0", "' AND last_name = '");
				break;
			case 3:
				userResponse = "FROM login_accounts WHERE usernames = '" +  OperateAccounts.getKeywords(userInput) + "'";
				break;
			case 4:
				userResponse = "FROM bank_accounts ORDER BY account_id";
				break;
			case 5:
				userResponse = "FROM bank_accounts WHERE account_id = " + OperateAccounts.getKeywords(userInput);
				break;
			case 6:
				userResponse = OperateAccounts.getKeywords(userInput);
				userResponse = "FROM bank_accounts WHERE account_user_one = '" + userResponse + "' OR account_user_two = '" + userResponse + "'"; 
				break;
		}

		System.out.println("Printing All Selected " + (userSelection > 3 ? "Bank Accounts: " : "User Profiles: "));
		return userResponse;
	}

	
	
	private static String getKeywords(Scanner userInput) {
		String userResponse = "";
		System.out.println("Please enter your keyword:");
		if (userInput.hasNext()) userResponse = userInput.nextLine();
		if (userResponse.indexOf(' ') >= 0)
			return userResponse.replaceFirst(" ", "#0");
		return userResponse;
	}
}
