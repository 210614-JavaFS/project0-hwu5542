package com.revature;

import java.util.Scanner;

class Main {
	public static void main(String[] args) {
		Scanner userInput = new Scanner(System.in);
		int userSelection = 0;
		Customers customer = new Customers();
		Admins admin = new Admins();
		Employees employee = new Employees();

		System.out.println("Which User Interface Are You Entering: \n" 
							+ "1. Customer Interface \n"
							+ "2. Employee Interface \n" 
							+ "3. Admins Interface \n" 
							+ "Please Enter Your Selection: ");
		
		if (userInput.hasNext())
			userSelection = userInput.nextInt();
		switch (userSelection) {
			case 1:
				customer.userLogin(userInput);
				break;
			case 2:
				employee.userLogin(userInput);
				break;
			case 3:
				admin.userLogin(userInput);
				break;
			default:
				System.out.println("Invalid Selection\n"
								   + "Please Start over");
		}
		
		userInput.close();
	}
}
