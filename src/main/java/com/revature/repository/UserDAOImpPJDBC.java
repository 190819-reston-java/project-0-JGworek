package com.revature.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


import com.revature.model.User;
import com.revature.utils.ConnectionUtil;
import com.revature.utils.StreamCloser;

public class UserDAOImpPJDBC implements UserDAO {

	private User createUserFromRS(ResultSet rsset) throws SQLException {
		return new User(rsset.getLong("id"),
				rsset.getString("username"),
				rsset.getString("_password"),
				rsset.getString("full_name"),
				rsset.getDouble("balance"));
	}
	
	@Override
	public User getUserWithU(String username) {
		Connection conn = null;		
		PreparedStatement stmt = null;
		ResultSet rsset = null;
		
		User user = null;
		
		String query = "SELECT * FROM jg_bank.accounts WHERE username = ?;";
		
		try {
			conn = ConnectionUtil.getConnection();
			stmt = conn.prepareStatement(query);
			stmt.setString(1, username);
			if(stmt.execute() ) {
				rsset = stmt.getResultSet();
				if(rsset.next()) {
					user = createUserFromRS(rsset);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			StreamCloser.close(rsset);
			StreamCloser.close(stmt);
			StreamCloser.close(conn);
		}
		return user;
	}

	@Override
	public User getUserWithUP(String username, String password) {
		Connection conn = null;		
		PreparedStatement stmt = null;
		ResultSet rsset = null;
		
		User user = null;
		
		String query = "SELECT * FROM jg_bank.accounts WHERE username = ? AND _password = ?;";
		
		try {
			conn = ConnectionUtil.getConnection();
			stmt = conn.prepareStatement(query);
			stmt.setString(1, username);
			stmt.setString(2, password);
			if(stmt.execute() ) {
				rsset = stmt.getResultSet();
				if(rsset.next()) {
					user = createUserFromRS(rsset);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			StreamCloser.close(rsset);
			StreamCloser.close(stmt);
			StreamCloser.close(conn);
		}
		return user;
	}
	
	@Override
	public List<User> getAllUsers() {
				Connection conn = null;		
				PreparedStatement stmt = null;
				ResultSet rsset = null;
				
				List<User> users = new ArrayList<User>();
				String query = "SELECT * FROM accounts;";
				
				try {
					conn = ConnectionUtil.getConnection();
					stmt = conn.prepareStatement(query);
					while(rsset.next()) {
						users.add(new User(rsset.getLong("id"),
								rsset.getString("username"),
								rsset.getString("_password"),
								rsset.getString("full_name"),
								rsset.getDouble("balance"))
								);
					}
				} catch (SQLException e) {
					e.printStackTrace();
				} finally {
					StreamCloser.close(rsset);
					StreamCloser.close(stmt);
					StreamCloser.close(conn);
				}
				return users;
	}

	@Override
	public boolean createUser(User u) {
		Connection conn = null;
		PreparedStatement stmt = null;
		
		String query = "INSERT INTO jg_bank.accounts VALUES (DEFAULT, ?, ?, ?, ?);";
	
		try {
			conn = ConnectionUtil.getConnection();
			stmt = conn.prepareStatement(query);
			stmt.setString(1, u.getUsername());
			stmt.setString(2, u.getPassword());
			stmt.setString(3, u.getFullName());
			stmt.setDouble(4, u.getBalance());
			stmt.execute();
		}catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			StreamCloser.close(stmt);
			StreamCloser.close(conn);
		}
		return true;
	}

	@Override
	public boolean updateUser(User u) {
		Connection conn = null;
		PreparedStatement stmt = null;
		
		final String query = "UPDATE jg_bank.accounts SET username=?, _password=?, full_name=?, balance=? WHERE id = ?;";
		
		try {
			conn = ConnectionUtil.getConnection();
			stmt = conn.prepareStatement(query);
			stmt.setString(1, u.getUsername());
			stmt.setString(2, u.getPassword());
			stmt.setString(3, u.getFullName());
			stmt.setDouble(4, u.getBalance());
			stmt.setLong(5, u.getId());
			stmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			StreamCloser.close(stmt);
			StreamCloser.close(conn);
		}
		return true;
	}
}
