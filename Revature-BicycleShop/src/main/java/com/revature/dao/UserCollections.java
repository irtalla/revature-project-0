package com.revature.dao;

import java.util.HashSet;
import java.util.Set;

import com.revature.beans.Customer;
import com.revature.beans.Employee;
import com.revature.beans.Offer;
import com.revature.beans.User;

public class UserCollections implements UserDAO {
	private static Set<User> allRegisteredUsers;
	private static Set<Customer> allRegisteredCustomers;
	private static Set<Employee> allRegisteredEmployees;
	
	public UserCollections() {
		allRegisteredUsers = new HashSet<User>();
		allRegisteredCustomers = new HashSet<Customer>();
		allRegisteredEmployees = new HashSet<Employee>();
		
		User justAUserForSomeReason = new User("Jordan really is a curse", "for literally any team he is on does badly", "mcc12");
		Customer customer = new Customer("banebadibi", "parameciumfatality");
		Employee employee = new Employee("pfscyhe", "ztux");
		
		allRegisteredUsers.add(justAUserForSomeReason);
		allRegisteredUsers.add(customer);
		allRegisteredUsers.add(employee);
		allRegisteredCustomers.add(customer);
		allRegisteredEmployees.add(employee);
	}

	@Override
	public Set<User> getAllUsers() {
		// TODO Auto-generated method stub
		return allRegisteredUsers;
	}

	@Override
	public Set<Employee> getAllEmployees() {
		// TODO Auto-generated method stub
		return allRegisteredEmployees;
	}

	@Override
	public Set<Customer> getAllCustomers() {
		// TODO Auto-generated method stub
		return allRegisteredCustomers;
	}
	
	//CRUD (create, read, update, delete)
	// but not now, read the details. simiulate picking from the postgresql thing.
	@Override
	public void registerACustomer(String username, String password) {
		Customer customer = new Customer(username, password);
		allRegisteredUsers.add(customer);
		allRegisteredCustomers.add(customer);
	}
	
	
	@Override
	public void calculatePayment(Offer offer) {
		double payment = offer.getOffer();
		double weeklyPayments = payment / 4; //(assumption is that it's paid over a month?)
		System.out.println("You must pay " + weeklyPayments + " over 4 weeks.");
	}
	
}
