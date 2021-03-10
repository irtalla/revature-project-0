package com.revature.dao;

import java.util.HashSet;
import java.util.Set;

import com.revature.beans.Bicycle;
import com.revature.beans.Customer;
import com.revature.beans.Employee;


public class BicycleCollections implements BicycleDAO {

	private Set<Bicycle> offeredBicycles;
	
	public BicycleCollections() {
		Employee user = new Employee("gutscoTheRogue", "hisDragonslayerSword");
		Bicycle bicycle = new Bicycle("Ghiradelico", "Recumbent Bike X49", "This bike has been kept in good shape. A bike meant for comfort and also to look stylish", user, 23.98);
		
		Bicycle bicycle2 = new Bicycle("Cape Cod", "Kids' Bike Happenstance", "SHort and stout and colorful. Comes with detachable training wheels.", user, 38.66);
		Customer customer = new Customer("cloudOfDarkness", "cloudOfLight");
		bicycle2.setStatus("owned")
		;
		bicycle2.setWhoWillOwnTheBike(customer);
		offeredBicycles = new HashSet<>();
		offeredBicycles.add(bicycle);
		offeredBicycles.add(bicycle2);
	}
	
	public Set<Bicycle> getAllAvailableBicycles(){
		Set<Bicycle> availableBicycles = new HashSet<Bicycle>();
		for (Bicycle b: offeredBicycles) {
			if (b.getStatus().equals("available")) {
				availableBicycles.add(b);
			}
		}
		
		return offeredBicycles;
	}
	
	public boolean addABicycle(Bicycle b) {
		return offeredBicycles.add(b);
	}
	
	public boolean removeABicycle(Bicycle b) {
		return offeredBicycles.remove(b);
	}

	@Override
	public Set<Bicycle> getAllBicycles() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int addABicycle(Bicycle b) {
		// TODO Auto-generated method stub
		return 0;
	}
}
