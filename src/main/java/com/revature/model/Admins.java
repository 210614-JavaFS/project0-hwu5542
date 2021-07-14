package com.revature.model;

import java.sql.*;
import java.util.Scanner;

import com.revature.controller.Accounts;
import com.revature.service.OperateFunds;

public class Admins extends OperateFunds {
	
	public static int adminInterface() {
		int userSelection;
		
		System.out.println("What would you like to do? \n"
						   + "1. View Account Information. \n"
				           + "2. Approve Customer Register. \n"
				           + "3. Edit Bank Accounts. \n"
						   + "0. Exit.");
		userSelection = getUserInput().nextInt();
		getUserInput().nextLine();
		return userSelection;		
	}
	
	public static int editAccounts() {
		int userSelection = 0;
		
		System.out.println("Which operation are you taking? \n"
						   + "1. Cancel an Bank Account. \n"
				           + "2. Cancel an User Account. \n"
				           + "3. Operate Funds on Bank Accounts. \n"
				           + "4. Operate Funds on User Accounts.");
		if (getUserInput().hasNext())
			userSelection = Integer.parseInt(getUserInput().nextLine());
		return userSelection;
	}
	
	public static String editDeletion(String keyword) {
		String userDecision = "";
		System.out.println("Please enter the " + keyword + " to delete bank account:");
		if (getUserInput().hasNext()) userDecision = getUserInput().nextLine();
		return userDecision;
	}
	
	public static int getTempID() {
		int accountID = 0;
		while (accountID < 1) {
			if (getUserInput().hasNext()) accountID = Integer.parseInt(getUserInput().nextLine()); 
			if (accountID < 1) System.out.println("Invalid account ID, please try again:");
		}
		return accountID;
	}
}
