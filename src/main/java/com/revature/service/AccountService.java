package com.revature.service;

import java.util.List;

import com.revature.exception.NegativeBalanceException;
import com.revature.exception.NegativeDepositException;
import com.revature.exception.NegativeWithdrawException;
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
		if (d < 0) {
			throw new NegativeWithdrawException();
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

	public static boolean checkAdmin(String username) {
		usernameUser = userDAO.getUserWithU(username);
		if (usernameUser.isAdminStatus() == true) {
			return true;
		} else {
			return false;
		}
	}

	public static void withdrawAdmin(double d, String username) {
		User thisUser = userDAO.getUserWithU(username);
		double finalBalance = (thisUser.getBalance() - d);
		if (finalBalance < 0) {
			throw new NegativeBalanceException();
		}
		if (d < 0) {
			throw new NegativeWithdrawException();
		}
		thisUser.setBalance(finalBalance);
		userDAO.updateAdmin(thisUser);
	}

	public static void depositAdmin(double d, String username) {
		if (d < 0) {
			throw new NegativeDepositException();
		}
		User thisUser = userDAO.getUserWithU(username);
		double finalBalance = (thisUser.getBalance() + d);
		thisUser.setBalance(finalBalance);
		userDAO.updateAdmin(thisUser);

	}

	public static List<User> makeListOfAccounts() {
		return userDAO.getAllUsers();

	}

	public static User createNewAccount(String newUsername, String newPassword, String newFullName,
			double doubleBalanceNumber, boolean newType) {
		User newAccount = new User(0L, newUsername, newPassword, newFullName, doubleBalanceNumber, newType);
		userDAO.createUser(newAccount);
		return userDAO.getUserWithU(newUsername);

	}

	public static void checkNegative(double doubleBalanceNumber) {
		if (doubleBalanceNumber < 0) {
			throw new NegativeBalanceException();
		}

	}

}
