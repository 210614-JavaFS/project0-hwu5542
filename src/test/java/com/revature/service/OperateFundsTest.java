package com.revature.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.util.Scanner;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.revature.dao.profileDAO;

public class OperateFundsTest {
	public static Scanner userInput;
	public static double result;
	
	@AfterEach
	public void closeScan() {
		userInput.close();
	}
	
	@BeforeEach
	public void setScanner() {
		result = 0;
		OperateFunds.setBalance(300.00);
		OperateFundsTest.userInput = profileDAO.getProfile(10);
		OperateFunds.setUserInput(userInput);
	}
	
	
	@Test
	public void testOperateFunds() {
		System.out.println("Testing Funds Collector.");
		result = OperateFunds.operateFunds(true);
		assertEquals(result, 100);
	}
	
	@Test
	public void testWithdrawFunds() {
		System.out.println("Testing Withdrawing Funds.");
		result = OperateFunds.withdrawFunds();
		assertEquals(result, 200);
	}
	
	@Test
	public void testDepositFunds() {
		System.out.println("Testing Deposit Funds.");
		result = OperateFunds.depositFunds();
		assertEquals(result, 900);
	}
}
