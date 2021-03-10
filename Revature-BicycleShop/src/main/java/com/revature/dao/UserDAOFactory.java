package com.revature.dao;

public class UserDAOFactory {

	public UserDAO getUserDAO() {
		return new UserPostgres();
	}
	
}
