package com.revature.services;

import com.revature.beans.User;

public interface UserService {
	
	//create method: a User makes
	
	//while this is pretty bare-bones, it is the only thing a "User"
	//and only a user alone can do. "read" method.
	//public User validatePotentialUser(String username, String password);
	public User validatePotentialUser(String username, String password);
	
	public void customerRegistration(String username, String password);
}
