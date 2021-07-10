package com.revature.service;

import java.sql.*;
import java.util.Scanner;

import com.revature.controller.Accounts;

public class Employees extends Accounts {
	public static int employeeInterface(Scanner userInput) {
		int userSelection;
		
		System.out.println("What would you like to do? \n"
						   + "1. View Customers Information. \n"
						   + "2. View Bank Accounts Information. \n"
				           + "3. Approve Customer Register. \n"
				           + "0. Exit.");
		userSelection = userInput.nextInt();
		userInput.nextLine();
		return userSelection;
	}
	
	public static boolean Employee(String accountInfo, Scanner userInput) {
		String userResponse = "";
		
		System.out.println(String.format("%20s", "Username") + String.format("%20s", "User First Name") + String.format("%20s", "User Last Name")
					     + String.format("%20s", "Current Address") + String.format("%20s", "phone number") + String.format("%20s", "email")
					     + String.format("%20s", "User Account ID") + String.format("%20s", "First Account User") + String.format("%20s", "Second Account User"));
		System.out.println(accountInfo);

		System.out.println("Approval to Bank Account Register? (yes / no)");

		if (userInput.hasNext()) userResponse = new String(userInput.nextLine().toLowerCase());		
		
		if (userResponse.equals("yes")) return true;
		
		return false;
	}
}
