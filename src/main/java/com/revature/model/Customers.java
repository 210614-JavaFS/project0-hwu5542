package com.revature.model;

import java.util.Scanner;

import java.sql.*;

public class Customers {
	
	public static int customerInterface(Scanner userInput) {
		int userSelection;
		
		System.out.println("What would you like to do? \n"
				           + "1. Apply to Open A Bank Account. \n"
						   + "2. Check Balance. \n"
				           + "3. Deposit Funds. \n"
						   + "4. Withdraw Funds. \n"
				           + "5. Transfer Funds. \n"
						   + "6. Update Your Information \n"
				           + "0. Exit.");
		return(userInput.nextInt());
	}
	
	public static String[] collectUserInfo(Scanner userInput) {
		String[] userInfo = new String[5];
		System.out.println("What is your first name?");
		userInfo[0] = userInput.nextLine();
		System.out.println("What is your last name?");
		userInfo[1] = userInput.nextLine();
		System.out.println("What is your address?");
		userInfo[2] = userInput.nextLine();
		System.out.println("What is your phone number?");
		userInfo[3] = userInput.nextLine();
		System.out.println("What is your e-mail address?");
		userInfo[4] = userInput.nextLine();
		return userInfo;
	}
}
