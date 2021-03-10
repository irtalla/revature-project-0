package com.revature.dao;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;

import org.junit.jupiter.api.Test;

import com.revature.beans.Customer;
import com.revature.beans.Employee;
import com.revature.beans.User;

public class UserDAOTest {
	
	@Test
	public void testGetAllUsers(){
		UserDAO up = new UserPostgres();
		
		Set<User> allUsers = up.getAllUsers();
		User nullUser = new User();
		
		assertFalse(allUsers.contains(nullUser));
	}
	
	@Test
	public void testGetAllEmployees(){
		UserDAO up = new UserPostgres();
		
		Set<Employee> allEmployees = up.getAllEmployees();
		Employee nullEmployee = new Employee();
		nullEmployee.setType("user");
		
		assertFalse(allEmployees.contains(nullEmployee));
		for (Employee e: allEmployees) {
			if (!e.getType().contentEquals("employee")) {
				fail("User who is employee was not marked employee");
			}
		}
	}
	
	@Test
	public void testGetAllCustomers(){
		UserDAO up = new UserPostgres();
		
		Set<Customer> allCustomers = up.getAllCustomers();
		Customer nullCustomer = new Customer();
		nullCustomer.setType("user");
		
		assertFalse(allCustomers.contains(nullCustomer));
		for (Customer c: allCustomers) {
			if (!c.getType().contentEquals("customer")) {
				fail("User who is customer was not marked customer");
			}
		}
	}
	
	
	@Test
	public void testRegisterACustomer() {
		UserDAO up = new UserPostgres();
		String username = "username";
		String password = "password";
		
		int customerID = up.registerACustomer(username, password);
		
		assertNotEquals(-1, customerID);
	}
	
	@Test
	public void removeACustomer() {
		
		UserDAO up = new UserPostgres();
		String username = "username2";
		String password = "password2";
	
		int customerID = up.registerACustomer(username, password);
		//int sizeBeforeTest = up.getAllUsers().size();
		assertTrue(up.removeACustomer(customerID));
		
	}
	
	/*
	public User findAUser(String username, String password);
	*/
}
