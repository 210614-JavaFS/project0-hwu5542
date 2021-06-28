package com.revature;

public class Admins extends Login {
	@Override
	boolean checkValidLogin(String username, String password) {
		this.retriveCredential(); // get username and password then check them
		return false;
	}

}
