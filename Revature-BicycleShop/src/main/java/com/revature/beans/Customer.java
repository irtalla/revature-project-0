package com.revature.beans;

public class Customer extends User{
	
	//private double balance;
	
	public Customer() {
		super("customer");
		//balance = 500.00;
	}
	
	public Customer(String username, String password) {
		super(username, password, "customer");
		//balance = 500.00;
	}
	
	public Customer(String username, String password, int id) {
		super(username, password, "customer", id);
		//balance = 500.00;
	}
	
	@Override
	public String toString() {
		return "Customer " + getUsername() + "'s password is " + getPassword(); //+ "\nThat customer's balance is " + balance;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		return true;
	}
	
	

}
