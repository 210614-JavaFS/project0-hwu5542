package com.revature.dao;

import java.sql.*;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;


public class AccountsDAO {
	
	protected static Connection establishConnection() throws SQLException {
		String url = "";
		String user = "";
		String password = "";

		try(Scanner loginDBInfo = new Scanner(new File("src//main//resources//login.txt"))) {
			url = loginDBInfo.nextLine();
			user = loginDBInfo.nextLine();
			password = loginDBInfo.nextLine();
		} catch(IOException e) {
			System.out.println("Can't find Login File: "+e.getMessage());
		}
		
		Connection conn = DriverManager.getConnection(url, user, password);			
		return conn;
	}
	
	
	protected static ResultSet insertBankDB(String query) throws SQLException {
		Connection conn = AccountsDAO.establishConnection();	
		Statement stmt = conn.createStatement();
		stmt.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
		conn.close();
		return stmt.getGeneratedKeys();
	}
	
	
	protected static int updateDB(String query) throws SQLException {
		Connection conn = AccountsDAO.establishConnection();	
		Statement stmt = conn.createStatement();
		int result = stmt.executeUpdate(query);
		conn.close();
		return result;		
	}
	
	
	protected static ResultSet selectDB(String query) throws SQLException {
		Connection conn = AccountsDAO.establishConnection();	
		Statement stmt = conn.createStatement();
		ResultSet result = stmt.executeQuery(query);
		conn.close();
		return result;
	}
}