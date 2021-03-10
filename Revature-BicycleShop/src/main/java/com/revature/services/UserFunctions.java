package com.revature.services;

import java.util.Set;

import com.revature.beans.Customer;
import com.revature.beans.Employee;
import com.revature.beans.User;
import com.revature.dao.UserDAO;
import com.revature.dao.UserDAOFactory;

public class UserFunctions implements UserService {
	
	private UserDAO userDAO;
	
	public UserFunctions() {
		UserDAOFactory udf = new UserDAOFactory();
		userDAO = udf.getUserDAO();
	}
	
	public UserDAO getUserDAO() {
		return userDAO;
	}
	
	//note: while this does look rather weird, it is noted that
	//effectively a User is a CUstomer or an Employee.
	//This effectively does cut down on what nees to be done
	//although it is super-inefficient even as is.
	//when a database is used, Users will exist, but only as Customers and Employee
	@Override
	public User validatePotentialUser(String username, String password) {
		User supposedUser = userDAO.findAUser(username, password);
		//Customer supposedUserC = new Customer(username, password);
		//Employee supposedUserE = new Employee(username, password);
		
		return supposedUser;
	}
	
	@Override
	public void customerRegistration(String username, String password) {
		userDAO.registerACustomer(username, password);
	}
}
