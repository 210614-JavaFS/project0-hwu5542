package com.revature;

import java.util.Scanner;

public class Customers extends Login {
	void register(Scanner userInput) {
		String username = "",
			   password = "";
		System.out.println("Please Enter a New Username:");
		if (userInput.hasNext()) username = new String(userInput.nextLine());
		while (true)
		{
			System.out.println("Please Enter a New Password:");
			if (userInput.hasNext()) password = new String(userInput.nextLine());
			System.out.println("Please Re-enter Your Password:");
			if (userInput.hasNext())
				if (password.equals(userInput.nextLine()))
					break;
				else
					System.out.println("Re-enter Password not Match With First Enter");
		}
		this.registerCredential(username, password);
	}
	
	@Override
	boolean checkValidLogin(String username, String password) {
		this.retriveCredential(); // get username and password then check them
		return false;
	}
}
