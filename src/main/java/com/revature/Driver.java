package com.revature;

import java.sql.SQLException;

import com.revature.controller.Login;
class Driver {
	public static void main(String[] args) {
		try {
	//		for (int i=1; i<10; i++) {
		//		Login.userLogin("src//test//resources//text_" + i + ".txt");
		//	}
//			Login.userLogin("src//test//resources//text_0.txt");
			Login.userLogin(null);

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Database Access Failure.");
		} 
	}
}