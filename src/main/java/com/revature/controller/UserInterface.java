package com.revature.controller;

import java.util.Scanner;

import org.apache.log4j.Logger;

import com.revature.exception.NegativeBalanceException;
import com.revature.exception.NegativeDepositException;
import com.revature.exception.NegativeWithdrawException;
import com.revature.service.*;

public class UserInterface {

	private static Scanner userInput = new Scanner(System.in);
	private static String username = null;
	private static Logger logger = Logger.getLogger(UserInterface.class);
	private static int userNameAttempts = 0;
	private static int passwordAttempts = 0;

	public static void commence() {
		System.out.println("Hello, and welcome to JG Bank");
		System.out.println();
		loginUsername();
	}

	private static void loginUsername() {
		System.out.println("Please provide your username to continue:");
		String inputtedUsername = userInput.nextLine();
		username = inputtedUsername;
		if (AccountService.checkUsername(inputtedUsername) == false) {
			userNameAttempts++;
			if (userNameAttempts > 3) {
				System.out.println("You failed to log in too many times. Exiting JG Bank.");
				System.out.println();
				System.out.println("Thank you for using JG Bank. Have a great day!");
				System.exit(0);
			}
			System.out.println("This username is not in our system. Try again!");
			System.out.println();
			loginUsername();
		} else {
			System.out.println("Username recognized");
			loginPassword();
		}
	}

	private static void loginPassword() {
		System.out.println();
		System.out.println("User: " + username + ", please provide your password:");
		String inputtedPassword = userInput.nextLine();
		if (AccountService.checkUsernamePassword(username, inputtedPassword) == null) {
			passwordAttempts++;
			if (passwordAttempts > 3) {
				System.out.println("You failed to log in too many times. Exiting JG Bank.");
				System.out.println();
				System.out.println("Thank you for using JG Bank. Have a great day!");
				System.exit(0);
			}
			System.out.println("Incorrect password. Try again!");
			loginPassword();
		} else {
			if (AccountService.checkAdmin(username) == true) {
				System.out.println();
				adminMenu();
			} else {
				System.out.println();
				menu();
			}
		}
	}

	private static void menu() {
		System.out.println("Welcome " + AccountService.getFullName(username)
				+ ". Please select an option below using the corresponding number:");
		System.out.println("1. View Balance");
		System.out.println("2. Withdraw Funds");
		System.out.println("3. Deposit Funds");
		System.out.println("4. Exit");
		String menuInput = userInput.nextLine();
		switch (menuInput) {
		case "1":
			System.out.println();
			System.out
					.println("Your current balance is: " + String.format("%.2f", AccountService.viewBalance(username)));
			System.out.println();
			menu();
			break;
		case "2":
			System.out.println();
			System.out.println("Your current balance is: " + String.format("%.2f", AccountService.viewBalance(username))
					+ ". How much would you like to withdraw?");
			String withdrawInput = userInput.nextLine();
			try {
				double doubleInputNumber = Double.parseDouble(withdrawInput);
				AccountService.withdraw(doubleInputNumber, username);
			} catch (NegativeWithdrawException e) {
				System.out.println();
				System.out.println("Transaction failed!");
				logger.warn("User: " + AccountService.getFullName(username) + " attempted to withdraw a negative amount");
			} catch (NegativeBalanceException e) {
				System.out.println();
				System.out.println("Transaction failed!");
				logger.warn("User: " + AccountService.getFullName(username) + " attempted to create a negative total balance");
			} catch (NumberFormatException e) {
				System.out.println();
				System.out.println("Dollar amount must be a number!");
				logger.warn("User: " + AccountService.getFullName(username) + " attempted to change balance with a non-number");
				System.out.println();
			}
			System.out.println();
			System.out.println(
					"Your remaining balance is: " + String.format("%.2f", AccountService.viewBalance(username)));
			System.out.println();
			menu();
			break;
		case "3":
			System.out.println();
			System.out.println("Your current balance is: " + String.format("%.2f", AccountService.viewBalance(username))
					+ ". How much would you like to deposit?");
			String depositInput = userInput.nextLine();
			try {
				double doubleDepositNumber = Double.parseDouble(depositInput);
				AccountService.deposit(doubleDepositNumber, username);
			} catch (NegativeDepositException e) {
				System.out.println();
				System.out.println("Transaction failed!");
				logger.warn("User: " + AccountService.getFullName(username) + " attempted to deposit a negative amount");
			} catch (NumberFormatException e) {
				System.out.println();
				System.out.println("Dollar amount must be a number!");
				logger.warn("User: " + AccountService.getFullName(username) + " attempted to change balance with a non-number");
				System.out.println();
			}
			System.out.println();
			System.out.println(
					"Your current balance is : " + String.format("%.2f", AccountService.viewBalance(username)));
			System.out.println();
			menu();
			break;
		case "4":
			System.out.println();
			System.out.println("Thank you for using JG Bank. Have a great day!");
			System.exit(0);
			break;
		default:
			System.out.println("Input not valid. Try again.");
			System.out.println();
			menu();
		}
	}

	private static void adminMenu() {
		System.out.println("Welcome " + AccountService.getFullName(username)
				+ ". Please select an option below using the corresponding number:");
		System.out.println("1. View Balance");
		System.out.println("2. Withdraw Funds");
		System.out.println("3. Deposit Funds");
		System.out.println("4. View All Accounts");
		System.out.println("5. Create New User");
		System.out.println("6. Exit");
		String adminMenuInput = userInput.nextLine();
		switch (adminMenuInput) {
		case "1":
			System.out.println();
			System.out
					.println("Your current balance is: " + String.format("%.2f", AccountService.viewBalance(username)));
			System.out.println();
			adminMenu();
			break;
		case "2":
			System.out.println();
			System.out.println("Your current balance is: " + String.format("%.2f", AccountService.viewBalance(username))
					+ ". How much would you like to withdraw?");
			String withdrawInput = userInput.nextLine();
			try {
				double doubleInputNumber = Double.parseDouble(withdrawInput);
				AccountService.withdrawAdmin(doubleInputNumber, username);
			} catch (NegativeWithdrawException e) {
				System.out.println();
				System.out.println("Transaction failed!");
				logger.warn("Admin: " + AccountService.getFullName(username) + " attempted to withdraw a negative amount");
			} catch (NegativeBalanceException e) {
				System.out.println();
				System.out.println("Transaction failed!");
				logger.warn("Admin: " + AccountService.getFullName(username) + " attempted to create a negative total balance");
			} catch (NumberFormatException e) {
				System.out.println();
				System.out.println("Dollar amount must be a number!");
				logger.warn("Admin: " + AccountService.getFullName(username) + " attempted to change balance with a non-number");
				System.out.println();
			}
			System.out.println();
			System.out.println(
					"Your remaining balance is: " + String.format("%.2f", AccountService.viewBalance(username)));
			System.out.println();
			adminMenu();
			break;
		case "3":
			System.out.println();
			System.out.println("Your current balance is: " + String.format("%.2f", AccountService.viewBalance(username))
					+ ". How much would you like to deposit?");
			String depositInput = userInput.nextLine();
			try {
				double doubleDepositNumber = Double.parseDouble(depositInput);
				AccountService.depositAdmin(doubleDepositNumber, username);
			} catch (NegativeDepositException e) {
				System.out.println();
				System.out.println("Transaction failed!");
				logger.warn("Admin: " + AccountService.getFullName(username) + " attempted to deposit a negative amount");
			} catch (NumberFormatException e) {
				System.out.println();
				System.out.println("Dollar amount must be a number!");
				logger.warn("Admin: " + AccountService.getFullName(username) + " attempted to change balance with a non-number");
				System.out.println();
			}
			System.out.println();
			System.out.println(
					"Your current balance is : " + String.format("%.2f", AccountService.viewBalance(username)));
			System.out.println();
			adminMenu();
			break;
		case "4":
			System.out.println();
			System.out.println("All JG Bank Accounts:");
			System.out.println(AccountService.makeListOfAccounts());
			System.out.println();
			adminMenu();
			break;
		case "5":
			logger.info("Admin: " + AccountService.getFullName(username) + " attempted to create a new account");
			System.out.println();
			System.out.println("Welcome to the 'Create A User' Suite");
			System.out.println("New Username:");
			String newUsername = userInput.nextLine();
			System.out.println("New Password");
			String newPassword = userInput.nextLine();
			System.out.println("Full Name:");
			String newFullName = userInput.nextLine();
			System.out.println("Initial Deposit:");
			String newBalance = userInput.nextLine();
			double doubleBalanceNumber = 0;
			try {
				doubleBalanceNumber = Double.parseDouble(newBalance);
				AccountService.checkNegative(doubleBalanceNumber);
			} catch (NumberFormatException e) {
				System.out.println();
				System.out.println("Dollar amount must be a number!");
				logger.warn("Admin: " + AccountService.getFullName(username) + " attempted to set balance with a non-number");
				System.out.println();
				adminMenu();
			} catch (NegativeBalanceException e) {
				System.out.println();
				System.out.println("Transaction failed!");
				logger.warn("Admin: " + AccountService.getFullName(username) + " attempted to set balance to a negative amount");
			}
			System.out.println("User or Admin?");
			String typeInput = userInput.nextLine();
			boolean newType = false;
			if (typeInput.equals("Admin")) {
				newType = true;
			}
			System.out.println("New Account:");
			System.out.println(AccountService.createNewAccount(newUsername, newPassword, newFullName,
					doubleBalanceNumber, newType));
			logger.info("Admin: " + AccountService.getFullName(username) + " has successfully created a new account with username: " + newUsername);
			System.out.println();
			adminMenu();
			break;
		case "6":
			System.out.println();
			System.out.println("Thank you for using JG Bank. Have a great day!");
			System.exit(0);
			break;
		default:
			System.out.println("Input not valid. Try again.");
			System.out.println();
			adminMenu();
		}

	}

}
