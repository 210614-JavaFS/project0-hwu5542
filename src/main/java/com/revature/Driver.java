package com.revature;

import java.sql.SQLException;

import com.revature.model.Login;

class Driver {
	public static void main(String[] args) {
		try {
			Login.userLogin();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Database Access Failure.");
		} 
	}
}