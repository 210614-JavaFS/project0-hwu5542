package com.revature;

import java.util.Scanner;

class Main {
	public static void main(String[] args) {
		Scanner userInput = new Scanner(System.in);
		int userSelection = 0;

		System.out.println("Which User Interface Are You Entering: \n" 
							+ "1. Customer Interface \n"
							+ "2. Employee Interface \n" 
							+ "3. Admins Interface \n" 
							+ "Please Enter Your Selection: ");
		
		if (userInput.hasNext())
			userSelection = userInput.nextInt();
		userInput.nextLine();

		switch (userSelection) {
			case 1:
				Customers customer = new Customers();

				System.out.println("Do You Want to Register a New Account:\n"
								   + "1. Yes \n"
								   + "2. No, take me to login");
				if (userInput.hasNext())
					userSelection = userInput.nextInt();
				userInput.nextLine();

				if (userSelection == 1) {
					customer.register(userInput);					
				}
				else if (userSelection == 2) {
					customer.userLogin(userInput); 
				}
				break;
			case 2:
				Employees employee = new Employees();

				employee.userLogin(userInput);
				break;
			case 3:
				Admins admin = new Admins();

				admin.userLogin(userInput);
				break;
			default:
				System.out.println("Invalid Selection\n"
								   + "Please Start over");
		}
		
		userInput.close();
	}
}