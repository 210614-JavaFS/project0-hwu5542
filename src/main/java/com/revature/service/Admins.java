package com.revature.service;

import java.sql.*;
import java.util.Scanner;

import com.revature.controller.Accounts;

public class Admins extends OperateAccounts {
	public static int adminInterface(Scanner userInput) {
		int userSelection;
		
		System.out.println("What would you like to do? \n"
						   + "1. View Customers Information. \n"
						   + "2. View Bank Accounts Information. \n"
				           + "3. Approve Customer Register. \n"
				           + "4. Edit Bank Accounts. \n"
						   + "0. Exit.");
		userSelection = userInput.nextInt();
		userInput.nextLine();
		return userSelection;		
	}
}
