package com.revature.service;

import java.util.Scanner;

public class OperateAccounts {
	public static boolean processApplications(String accountInfo, Scanner userInput) {
		String userResponse = "";
		
		System.out.println(accountInfo.replaceAll("null", " N/A"));

		System.out.println("Approval to Bank Account Register? (yes / no)");

		if (userInput.hasNext()) userResponse = new String(userInput.nextLine().toLowerCase());		
		
		if (userResponse.equals("yes")) return true;
		
		return false;
	}
}
