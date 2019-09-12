package com.revature.repository;

import java.util.List;

import com.revature.model.User;


public interface UserDAO {

	User getUserWithU(String username);
	
	User getUserWithUP(String username, String password);
	
	List<User> getAllUsers();
	
	boolean createUser(User u);
	
	boolean updateUser(User u);
	
	
}
