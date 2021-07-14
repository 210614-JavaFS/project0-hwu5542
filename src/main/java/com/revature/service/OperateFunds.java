package com.revature.service;

import java.util.Scanner;

public class OperateFunds {

	private static Scanner userInput;
	private static double balance;

	public static void setBalance(double balance) {
		OperateFunds.balance = balance;
	}

	public static double getBalance() {
		return balance;
	}
	
	public static Scanner getUserInput() {
		return userInput;
	}

	public static void setUserInput(Scanner userInput) {
		OperateFunds.userInput = userInput;
	}

	
	
	public static double operateFunds(boolean outFlow) {
		double funds = 0;
		
		while (funds < 1) {
			System.out.println("Please enter the amount:");
			if (userInput.hasNext()) funds = Double.parseDouble(userInput.nextLine());

			if (funds < 1) System.out.println("Our system does not support transitions less than one dollar, please try again.");

			if (outFlow && (funds > balance)) {
				System.out.println("Insufficient Funds, please try again");
				funds = 0;
			}
		}
		return funds;
	}
	
	
	
	public static double depositFunds() {
		System.out.println("How much would you like to deposit?");
		return balance + OperateFunds.operateFunds(false);
	}
	
	
	
	public static double withdrawFunds() {
		System.out.println("How much would you like to withdraw?");
		return balance - OperateFunds.operateFunds(true);
	}
	
	
	
	public static int transferFunds() {
		int accountID = 0;
		while (accountID < 1) {
			System.out.println("Please enter the account ID you are transfering to:");
			if (userInput.hasNext()) accountID = Integer.parseInt(userInput.nextLine()); 
			if (accountID < 1) System.out.println("Invalid account ID, please try again.");
		}
		
		System.out.println("How much would you like to transfer?");
		return accountID;
	}
}
