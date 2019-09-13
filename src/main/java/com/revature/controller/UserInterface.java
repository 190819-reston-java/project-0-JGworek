package com.revature.controller;

import java.util.Scanner;

import org.apache.log4j.Logger;

import com.revature.exception.NegativeBalanceException;
import com.revature.exception.NegativeDepositException;
import com.revature.service.*;

public class UserInterface {

	private static Scanner userInput = new Scanner(System.in);
	static String username = null;
	public static Logger logger = Logger.getLogger(UserInterface.class);
	
	public static void commence() {
		System.out.println("Hello, and welcome to JG Bank");
		System.out.println();
		loginUsername();
	}

	private static void loginUsername() {
		System.out.println("Please provide your username to continue:");
		String inputtedUsername = userInput.nextLine();
		username = inputtedUsername;
		if(AccountService.checkUsername(inputtedUsername) == false) {
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
		if(AccountService.checkUsernamePassword(username, inputtedPassword) == null) {
			System.out.println("Incorrect password. Try again!");
			loginPassword();
		} else {
			if(AccountService.checkAdmin(username) == true) {
				System.out.println();
				adminMenu();
			} else {
			System.out.println();
			menu();
			}
		}
	}

		private static void menu() {
			System.out.println("Welcome " + AccountService.getFullName(username) + ". Please select an option below using the corresponding number:");
			System.out.println("1. View Balance");
			System.out.println("2. Withdraw Funds");
			System.out.println("3. Deposit Funds");
			System.out.println("4. Exit");
			String menuInput = userInput.nextLine();
			switch(menuInput) {
			case "1":
				System.out.println();
				System.out.println("Your current balance is: " + AccountService.viewBalance(username));
				System.out.println();
				menu();
				break;
			case "2":
				System.out.println();
				System.out.println("Your current balance is: " + AccountService.viewBalance(username) + ". How much would you like to withdraw?");
				String withdrawInput = userInput.nextLine();
				double doubleInputNumber = Double.parseDouble(withdrawInput);
				try {
					AccountService.withdraw(doubleInputNumber, username);
				} catch (NegativeBalanceException e) {
					System.out.println();
					System.out.println("Transaction failed!");
					logger.warn("User attempted to create a negative total balance");
				}
				System.out.println();
				System.out.println("Your remaining balance is: " + AccountService.viewBalance(username));
				System.out.println();
				menu();
				break;
			case "3":
				System.out.println();
				System.out.println("Your current balance is: " + AccountService.viewBalance(username) + ". How much would you like to deposit?");
				String depositInput = userInput.nextLine();
				double doubleDepositNumber = Double.parseDouble(depositInput);
				try {
				AccountService.deposit(doubleDepositNumber, username);
				} catch (NegativeDepositException e) {
					System.out.println();
					System.out.println("Transactionfailed!");
					logger.warn("User attempted to deposit a negative amount");
				}
				System.out.println();
				System.out.println("Your current balance is : " + AccountService.viewBalance(username));
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
			System.out.println("Welcome " + AccountService.getFullName(username) + ". Please select an option below using the corresponding number:");
			System.out.println("1. View Balance");
			System.out.println("2. Withdraw Funds");
			System.out.println("3. Deposit Funds");
			System.out.println("4. View All Accounts");
			System.out.println("5. Create New User");
			System.out.println("6. Exit");
			String menuInput = userInput.nextLine();
			switch(menuInput) {
			case "1":
				System.out.println();
				System.out.println("Your current balance is: " + AccountService.viewBalance(username));
				System.out.println();
				adminMenu();
				break;
			case "2":
				System.out.println();
				System.out.println("Your current balance is: " + AccountService.viewBalance(username) + ". How much would you like to withdraw?");
				String withdrawInput = userInput.nextLine();
				double doubleInputNumber = Double.parseDouble(withdrawInput);
				try {
					AccountService.withdrawAdmin(doubleInputNumber, username);
				} catch (NegativeBalanceException e) {
					System.out.println();
					System.out.println("Transaction failed!");
					logger.warn("User attempted to create a negative total balance");
				}
				System.out.println();
				System.out.println("Your remaining balance is: " + AccountService.viewBalance(username));
				System.out.println();
				adminMenu();
				break;
			case "3":
				System.out.println();
				System.out.println("Your current balance is: " + AccountService.viewBalance(username) + ". How much would you like to deposit?");
				String depositInput = userInput.nextLine();
				double doubleDepositNumber = Double.parseDouble(depositInput);
				try {
				AccountService.depositAdmin(doubleDepositNumber, username);
				} catch (NegativeDepositException e) {
					System.out.println();
					System.out.println("Transactionfailed!");
					logger.warn("User attempted to deposit a negative amount");
				}
				System.out.println();
				System.out.println("Your current balance is : " + AccountService.viewBalance(username));
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
				double doubleBalanceNumber = Double.parseDouble(newBalance);
				System.out.println("User or Admin?");
				String typeInput = userInput.nextLine();
				boolean newType = false;
				if(typeInput.equals("Admin")) {
					newType = true;
				}
				System.out.println("New Account:");
				System.out.println(AccountService.createNewAccount(newUsername, newPassword, newFullName, doubleBalanceNumber, newType));
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
				menu();
			}
			
		}
		
}
