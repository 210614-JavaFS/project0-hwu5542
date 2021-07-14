package com.revature;

import java.sql.SQLException;
import java.util.Scanner;

import com.revature.controller.MainMenu;
import com.revature.dao.profileDAO;

class Driver {
	public static void main(String[] args) {
		Scanner userInput = new Scanner(System.in);
		Scanner fileInput = null;
		

		
		try {

			for (int i=1; i<7; i++) { //up to 10
				fileInput = profileDAO.getProfile(i);
				MainMenu.userLogin(fileInput);
				fileInput.close();
			}

			MainMenu.userLogin(profileDAO.getProfile(0));

			while (true) { // For Demo, Switch between accounts
				MainMenu.userLogin(userInput);
				System.out.println("End Demo? (yes/no)");
				if (userInput.hasNext())
					if (userInput.nextLine().toLowerCase().equals("yes"))
						break;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Database Access Failure.");
		} 

		userInput.close();
	}
}