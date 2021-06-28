package com.revature;

import java.util.Scanner;

public abstract class Login extends Accounts {

	public void userLogin(Scanner userInput) {
		String username = "";
		String password = "";

		userInput.nextLine();
		
		System.out.println("Please Enter Your Username: ");
		if (userInput.hasNext()) username = new String(userInput.nextLine());
		
		System.out.println("Please Enter Your Password: ");
		if (userInput.hasNext()) password = new String(userInput.nextLine());

		System.out.println(username + ' ' + password);
		
		if (this.checkValidLogin(username, password))
			System.out.println("User Credential Confirm \n Login Successful"); //success login
		else 
			System.out.println("User Credential Invalid \n Please Start Over"); //fail login
	}

	abstract boolean checkValidLogin(String username, String password);
}
