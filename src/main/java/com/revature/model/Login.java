package com.revature.model;

import java.util.Scanner;
import java.sql.*;

public class Login extends Accounts {

	public static void userLogin() throws SQLException {
		String username = "";
		String password = "";
		String userResponse = "";
		int accountType = 0;
		int userSelection = 1;
		String[] userInfo = new String[5];
		
		Scanner userInput = new Scanner(System.in);
		
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
						userInput.nextLine();
						
						switch (userSelection) {
							case 1:
								userInfo = Customers.collectUserInfo(userInput);
								Accounts.setAccountInfo(userInfo);
								break;
							case 2:
								break;
							case 3:
								break;
							case 4:
								break;
							case 5:
								break;
							case 6:
								userInfo = Customers.collectUserInfo(userInput);
								break;
						}
					}
					break;
				case 2:
					System.out.println("Welecome back, Bank Employee");
					//TODO: go to Employee
					Employees.EmployeeInterface();
					
					break;
				case 3:
					System.out.println("Welecome back, Bank Administrator");
					//TODO: go to admin
					Admins.AdminInterface();
					break;
			}
		}
		else 
			System.out.println("User Credential Invalid \n Please Start Over"); //fail login

		userInput.close();
	}

}
