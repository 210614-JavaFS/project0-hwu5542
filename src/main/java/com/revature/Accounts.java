package com.revature;

public class Accounts{
	private class accountInfo {
		String accountBalance;
		String name;
		String address;
		String email;		
	}

	private class loginInfo {
		String username;
		String password;
	}
	
	protected void registerCredential(String username, String password) {
		return;
	}
	
	protected void retriveCredential() { //get login info from database
		return;
	}

	protected void retriveInfo() { //get customer info from database
		return;
	}
	
	protected void setCredential() { //set customer credential info
		return;
	}
	
	protected void setAccountInfo() { // set customer info
		return;
	}
}
