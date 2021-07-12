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
		boolean[] approveRegisterFlag = null;
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
					Customers.setScanner(userInput);
					
					while (userSelection > 0) {
						userSelection = Customers.customerInterface();
						Customers.setBalance(Accounts.getFunds());
						
						switch (userSelection) {
							case 1:
								changeInfo(userInput);
								Accounts.applyBankAccount(Customers.applyBankAccount());
								break;
							case 2:
								System.out.println("Account Balance : "+ "\u0024" + String.format("%.2f", accountBalance));
								break;
							case 3:
								Accounts.operateFunds(Customers.depositFunds());
								break;
							case 4:
								Accounts.operateFunds(Customers.withdrawFunds());
								break;
							case 5:
								Accounts.operateFunds(Customers.transferFunds(), Customers.operateFunds(true));
								break;
							case 6:
								changeInfo(userInput);
								break;
						}

						if (userSelection > 2 && userSelection < 6)
							System.out.println("Your New Balance : "+ "\u0024" + String.format("%.2f", Accounts.getFunds()));
					}
					break;
				case 2:
					System.out.println("Welecome back, Bank Employee");
					
					while (userSelection > 0) {
						userSelection = Employees.employeeInterface(userInput);
						
						switch (userSelection) {
							case 1:
							
								break;
							case 2:
								break;
							case 3:
								accountsInfo = Accounts.getNewApplication();							
								approveRegisterFlag = new boolean [accountsInfo.size()];
								
								System.out.println(String.format("%10s|", "Username") + String.format("%10s|", "First Name") + String.format("%10s|", "Last Name")
												 + String.format("%50s|", "Current Address") + String.format("%12s|", "Phone Number") + String.format("%20s|", "E-mail")
												 + String.format("%5s|", "ID #") + String.format("%10s|", "Main User") + String.format("%10s", "Join User"));
								int i = 0;

								for (String singleAccountInfo: accountsInfo) {
									if (Employees.processApplications(singleAccountInfo, userInput))
										approveRegisterFlag[i] = true;
									i++;
								}
							
							Accounts.approveBankAccount(approveRegisterFlag);
							break;
						}
					}
					break;
				case 3:
					System.out.println("Welecome back, Bank Administrator");

					while (userSelection > 0) {
						userSelection = Admins.adminInterface(userInput);
					
						switch (userSelection) {
							case 1:
							
								break;
							case 2:
								break;
							case 3:
								accountsInfo = Accounts.getNewApplication();							
								approveRegisterFlag = new boolean [accountsInfo.size()];
							
								System.out.println(String.format("%10s|", "Username") + String.format("%10s|", "First Name") + String.format("%10s|", "Last Name")
												 + String.format("%50s|", "Current Address") + String.format("%12s|", "Phone Number") + String.format("%20s|", "E-mail")
						     				     + String.format("%5s|", "ID #") + String.format("%10s|", "Main User") + String.format("%10s", "Join User"));

								int i = 0;
								for (String singleAccountInfo: accountsInfo) {
									if (Admins.processApplications(singleAccountInfo, userInput))
										approveRegisterFlag[i] = true;
									i++;	
								}
							
								Accounts.approveBankAccount(approveRegisterFlag);
								break;
						}
					}
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
		if (Customers.changeInfo()) {
			userInfo = Customers.collectUserInfo();
			Accounts.setAccountInfo(userInfo);
		}
	}
}
