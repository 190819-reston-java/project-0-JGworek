package com.revature.model;

public class User {

	private long id;
	private String username;
	private String password;
	private String fullName;
	private double balance;
	private boolean adminStatus;
	private String adminStatusString;
	
	public String getAdminStatusString() {
	if(adminStatus == true) {
		adminStatusString = "Admin";
	} else {
		adminStatusString = "User";
	} return adminStatusString;
	};

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	
	public boolean isAdminStatus() {
		return adminStatus;
	}

	public void setAdminStatus(boolean adminStatus) {
		this.adminStatus = adminStatus;
	}

	public User(long id, String username, String password, String fullName, double balance, boolean adminStatus) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.fullName = fullName;
		this.balance = balance;
		this.adminStatus = adminStatus;
	}

	@Override
	public String toString() {
		return  "\n" + "ID: " + id + ", Username: " + username + ", Password: " + password + ", Name: " + fullName
				+ ", Balance: " + balance + ", Type: " + getAdminStatusString();
	}

	
}
