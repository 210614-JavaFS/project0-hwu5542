package com.revature.controller;

import java.util.ArrayList;
import java.util.Scanner;
import java.sql.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.revature.service.Admins;
import com.revature.service.Customers;
import com.revature.service.Employees;


public class Login extends Accounts {

	public static void userLogin(String file) throws SQLException {
		String username = "";
		String password = "";
		String userResponse = "";
		int accountType = 0;
		int userSelection = 1;
		double accountBalance = 0;
		ArrayList<String> accountsInfo = new ArrayList<String>();
		Scanner userInput = null;
		
		if (file == null) userInput = new Scanner(System.in);
		else
			try {
				userInput = new Scanner(new File(file));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		
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
				case 1:
					System.out.println("Welecome back, Customer");
					//TODO: go to customer interface
					while (userSelection > 0) {
						userSelection = Customers.customerInterface(userInput);
						
						switch (userSelection) {
							case 1:
								changeInfo(userInput);
								Accounts.applyBankAccount(Customers.applyBankAccount(userInput));
								break;
							case 2:
								accountBalance = Accounts.getFunds();
								if (accountBalance >= 0)
									System.out.println("Account Balance : "+ "\u0024" + String.format("%.2f", accountBalance));
								break;
							case 3:								
								break;
							case 4:
								break;
							case 5:
								break;
							case 6:
								changeInfo(userInput);
								break;
						}
					}
					break;
				case 2:
					System.out.println("Welecome back, Bank Employee");
					//TODO: go to Employee
					userSelection = Employees.employeeInterface(userInput);
					
					switch (userSelection) {
						case 1:
							
							break;
						case 2:
							break;
						case 3:
							accountsInfo = Accounts.getNewApplication();
							for (String singleAccountInfo: accountsInfo) {
								Employees.approveBankAccount();
							}								
							break;
					}
					break;
				case 3:
					System.out.println("Welecome back, Bank Administrator");
					//TODO: go to admin
					Admins.adminInterface();
					break;
			}
		}
		else 
			System.out.println("User Credential Invalid \n Please Start Over"); //fail login

		userInput.close();
	}

	
	
	public static void changeInfo(Scanner userInput) throws SQLException {
		String[] userInfo = new String[5];

		Accounts.getAccountInfo();
		if (Customers.changeInfo(userInput)) {
			userInfo = Customers.collectUserInfo(userInput);
			Accounts.setAccountInfo(userInfo);
		}
	}
}
