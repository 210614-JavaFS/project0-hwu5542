package com.revature.controller;


import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.SQLException;

import org.junit.jupiter.api.Test;

public class AccountsTest {
	
	public static String username;
	public static String password;
	public static int resultInt;
	public static String resultStr;

	@Test
	public void checkValidLoginTest_0() {
		username = "invalid";
		password = "invalid";
		try {
			resultInt = Accounts.checkValidLogin(username, password);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Database Access Failure.");
		} 
		assertEquals(resultInt, 0);
	}
	
	@Test
	public void checkValidLoginTest_1() {
		username = "hong";
		password = "hoho";
		try {
			resultInt = Accounts.checkValidLogin(username, password);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Database Access Failure.");
		} 
		assertEquals(resultInt, 1);
	}
	
	@Test
	public void checkValidLoginTest_2() {
		username = "employee";
		password = "employee";
		try {
			resultInt = Accounts.checkValidLogin(username, password);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Database Access Failure.");
		} 
		assertEquals(resultInt, 2);
	}

	@Test
	public void checkValidLoginTest_3() {
		username = "admin";
		password = "admin";
		try {
			resultInt = Accounts.checkValidLogin(username, password);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Database Access Failure.");
		} 
		assertEquals(resultInt, 3);
	}
	
	@Test
	public void editAccountTest_Str() {
		try {
			Accounts.checkValidLogin("admin", "admin");
			resultInt = Accounts.editAccounts("hong");
			Accounts.dcAccounts(1, "hong");
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Database Access Failure.");
		}
		assertEquals(resultInt, 1);
	}

	@Test
	public void editAccountTest_Int() {
		try {
			Accounts.checkValidLogin("admin", "admin");
			resultStr = Accounts.editAccounts(1);
			Accounts.dcAccounts(1, "hong");
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Database Access Failure.");
		} 
		assertEquals(resultStr, "hong");		
	}
}
