package com.revature.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.revature.exception.NegativeBalanceException;
import com.revature.exception.NegativeDepositException;
import com.revature.mocks.UserDAOMock;
import com.revature.model.User;
import com.revature.repository.UserDAO;

public class AccountServiceTest {

	private static AccountService accountService = null;
	private static UserDAO userDAOTwo = new UserDAOMock();

	@Before
	public void setUpDAO() {
		accountService = new AccountService(userDAOTwo);
	}

	@After
	public void tearDown() {
		accountService = null;
	}

	@Test
	public void getFullNameUsingUsername() {
		assertTrue(accountService.getFullName("DefaultUsername").equals("Defaultathan Userson"));
	}

	@Test
	public void checkAdminStatus() {
		assertTrue(accountService.checkAdmin("AdminUsername") == true);
		assertFalse(accountService.checkAdmin("DefaultUsername") == true);
	}

	@Test
	public void isPasswordCorrectForUsername() {
		assertNotNull(accountService.checkUsernamePassword("AdminUsername", "adminpassword2"));
		assertNull(accountService.checkUsernamePassword("AdminUsername", "wrongPassword"));
	}

	@Test(expected = NegativeBalanceException.class)
	public void negativeBalance() {
		accountService.withdraw(1000.00, "DefaultUsername");
		accountService.withdraw(1000.00, "AdminUsername");
	}

	@Test(expected = NegativeDepositException.class)
	public void negativeDeposit() {
		accountService.deposit(-50, "AdminUsername");
		accountService.deposit(-50, "DefaultUsername");
	}

}
