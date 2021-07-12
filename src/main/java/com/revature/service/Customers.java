package com.revature.service;

import java.util.Scanner;

import java.sql.*;

public class Customers {
	
	private static Scanner userInput;
	private static double balance;
	
	
	public static void setScanner(Scanner userInput) {
		Customers.userInput = userInput;
	}
	
	public static void setBalance(double balance) {
		Customers.balance = balance;
	}
	
	

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
		userSelection = Customers.userInput.nextInt();
		Customers.userInput.nextLine();
		return userSelection;
	}
	
	
	public static boolean changeInfo() {
		String userResponse = "";
		System.out.println("Would you like to change your information? (yes / no)");

		if (Customers.userInput.hasNext()) userResponse = new String(Customers.userInput.nextLine().toLowerCase());		
		
		if (userResponse.equals("yes")) return true;
		
		return false;
	}
	
	
	
	public static String[] collectUserInfo() {
		String[] userInfo = new String[5];
		System.out.println("What is your first name?");
		userInfo[0] = Customers.userInput.nextLine();
		System.out.println("What is your last name?");
		userInfo[1] = Customers.userInput.nextLine();
		System.out.println("What is your address?");
		userInfo[2] = Customers.userInput.nextLine();
		System.out.println("What is your phone number?");
		userInfo[3] = Customers.userInput.nextLine();
		System.out.println("What is your e-mail address?");
		userInfo[4] = Customers.userInput.nextLine();
		return userInfo;
	}
	
	
	
	public static int applyBankAccount() {
		String userResponse = "";
		int selection = 0;
		
		System.out.println("Are you applying for a join account? (yes / no)");
		if (Customers.userInput.hasNext()) userResponse = new String(Customers.userInput.nextLine().toLowerCase());		
		
		if (userResponse.equals("yes")) {
			System.out.println("Do you need to create a new account? (yes / no)");
			if (Customers.userInput.hasNext()) userResponse = new String(Customers.userInput.nextLine().toLowerCase());
			if (userResponse.equals("yes")) {
				return 0;
			} else {
				System.out.println("Please enter the account ID for the join account:");
				if (Customers.userInput.hasNext())
					selection = Customers.userInput.nextInt();
					Customers.userInput.nextLine();
					return selection;
			}
		}

		return 0;
	}
	
	
	public static double operateFunds(boolean outFlow) {
		double funds = 0;
		
		while (funds < 1) {
			System.out.println("Please enter the amount:");
			if (Customers.userInput.hasNext()) funds = Double.parseDouble(Customers.userInput.nextLine());

			if (funds < 1) System.out.println("Our system does not support transitions less than one dollar, please try again.");

			if (outFlow && funds > Customers.balance) {
				System.out.println("Insufficient Funds, please try again");
				funds = 0;
			}
		}
		return funds;
	}
	
	
	
	public static double depositFunds() {
		System.out.println("How much would you like to deposit?");
		return Customers.balance + Customers.operateFunds(false);
	}
	
	
	
	public static double withdrawFunds() {
		System.out.println("How much would you like to withdraw?");
		return Customers.balance - Customers.operateFunds(true);
	}
	
	
	
	public static int transferFunds() {
		int accountID = 0;
		while (accountID < 3) {
			System.out.println("Please enter the account ID you are transfering to:");
			if (Customers.userInput.hasNext()) accountID = Integer.parseInt(Customers.userInput.nextLine()); 
			if (accountID < 3) System.out.println("Invalid account ID, please try again.");
		}
		
		System.out.println("How much would you like to transfer?");
		return accountID;
	}
}
