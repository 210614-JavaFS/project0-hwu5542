package com.revature.dao;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class profileDAO {
	public static Scanner getProfile(int index) {
		Scanner fileScan = null;
		try {
			fileScan = new Scanner(new File("src//test//resources//text_" + index + ".txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return fileScan;
	}
}
