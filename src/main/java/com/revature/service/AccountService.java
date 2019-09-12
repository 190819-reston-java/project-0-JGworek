package com.revature.service;

import com.revature.exception.NegativeBalanceException;
import com.revature.exception.NegativeDepositException;
import com.revature.model.User;
import com.revature.repository.UserDAO;
import com.revature.repository.UserDAOImpPJDBC;

public class AccountService {

	private static User usernameUser = null;
	private static UserDAO userDAO = new UserDAOImpPJDBC();
	
	public static boolean checkUsername(String username) {
		usernameUser = userDAO.getUserWithU(username);
		if (usernameUser == null) {
			return false;
		} else {
			return true;
		}
	}
	
	public static User checkUsernamePassword(String username, String password) {
		User thisUser = userDAO.getUserWithUP(username, password);
		if (thisUser == null) {
			return null;
		} else {
			return thisUser;
		}
	}

	public static String getFullName(String username) {
		User thisUser = userDAO.getUserWithU(username);
		return thisUser.getFullName();
	}

	public static double viewBalance(String username) {
		User thisUser = userDAO.getUserWithU(username);
		return thisUser.getBalance();
		
	}

	public static void withdraw(double d, String username) {
		User thisUser = userDAO.getUserWithU(username);
		double finalBalance = (thisUser.getBalance() - d);
		if (finalBalance < 0) {
			throw new NegativeBalanceException();
		}
		thisUser.setBalance(finalBalance);
		userDAO.updateUser(thisUser);
	}

	public static void deposit(double d, String username) {
		if (d < 0) {
			throw new NegativeDepositException();
		}
		User thisUser = userDAO.getUserWithU(username);
		double finalBalance = (thisUser.getBalance() + d);
		thisUser.setBalance(finalBalance);
		userDAO.updateUser(thisUser);
		
	}
	
	
	
	
	
	
}
