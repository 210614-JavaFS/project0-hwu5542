package com.revature.model;

import java.sql.*;
import java.util.Scanner;

import com.revature.controller.Accounts;

public class Employees {
	public static int employeeInterface(Scanner userInput) {
		int userSelection;
		
		System.out.println("What would you like to do? \n"
						   + "1. View Account Information. \n"
				           + "2. Approve Customer Register. \n"
				           + "0. Exit.");
		userSelection = userInput.nextInt();
		userInput.nextLine();
		return userSelection;
	}
}
