package com.revature.mocks;

import java.util.List;

import com.revature.model.User;
import com.revature.repository.UserDAO;

public class UserDAOMock implements UserDAO {

	@Override
	public User getUserWithU(String username) {
		if (username.equals("DefaultUsername")) {
			return new User(10L, "DefaultUsername", "defaultpassword1", "Defaultathan Userson", 700.00, false);
		} else if (username.equals("AdminUsername")) {
			return new User(11L, "AdminUsername", "adminpassword2", "Admino Username II", 30.00, true);
		}
		return null;
	}

	@Override
	public User getUserWithUP(String username, String password) {
		if (username.equals("DefaultUsername") & password.equals("defaultpassword1")) {
			return new User(10L, "DefaultUsername", "defaultpassword1", "Defaultathan Userson", 700.00, false);
		} else if (username.equals("AdminUsername") & password.equals("adminpassword2")) {
			return new User(11L, "AdminUsername", "adminpassword2", "Admino Username II", 30.00, true);
		}
		return null;
	}

	@Override
	public List<User> getAllUsers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean createUser(User u) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateUser(User u) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateAdmin(User u) {
		// TODO Auto-generated method stub
		return false;
	}

}
