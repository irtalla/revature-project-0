package com.revature.dao;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;

import org.junit.jupiter.api.Test;

import com.revature.beans.Bicycle;
import com.revature.beans.Customer;
import com.revature.beans.Employee;

public class BicycleDAOTest {
	
	@Test
	public void getAllAvailableBicycles(){
		BicycleDAO bp = new BicyclePostgres();
		Set<Bicycle> allBicycles = bp.getAllAvailableBicycles();
		Bicycle nullBicycle = new Bicycle();
		
		assertFalse(allBicycles.contains(nullBicycle));
		for (Bicycle b: allBicycles) {
			if (!b.getStatus().contentEquals("available")) {
				fail("retrieved a bicycle that was marked owned");
			}
		}
		
	}
	
	@Test
	public void getAllBicycles(){
		BicycleDAO bp = new BicyclePostgres();
		Set<Bicycle> allBicycles = bp.getAllBicycles();
		Bicycle nullBicycle = new Bicycle();
		
		assertFalse(allBicycles.contains(nullBicycle));
	}
	
	@Test
	public void addABicycle() {
		BicycleDAO bp = new BicyclePostgres();
		
		Bicycle newBicycle = new Bicycle();
		newBicycle.setBikeModel("Jokes End Tale");
		newBicycle.setBikeType("Sluggish Bike");
		newBicycle.setDescription("As bad as its name makes it sound. Maybe even worse.");
		newBicycle.setId(79);
		newBicycle.setPrice(1.94);
		newBicycle.setStatus("available");
		
		Employee employee = new Employee("josefuJosuta", "hamittoPapuru", 2);
		newBicycle.setSeller(employee);
		
		int newBikeID = bp.addABicycle(newBicycle);
		newBicycle.setId(newBikeID);
		
		Set<Bicycle> allBikes = bp.getAllBicycles();
		assertTrue(allBikes.contains(newBicycle));
		
		
	}
	
	@Test
	public void removeABicycle() {
		BicycleDAO bp = new BicyclePostgres();
		
		Bicycle newBicycle = new Bicycle();
		newBicycle.setBikeModel("Jokes End Tale");
		newBicycle.setBikeType("Bicyclissa");
		newBicycle.setDescription("Strong as hell, this can take you up any rough path");
		newBicycle.setId(74);
		newBicycle.setPrice(199.94);
		newBicycle.setStatus("available");
		
		Employee employee = new Employee("josefuJosuta", "hamittoPapuru", 2);
		newBicycle.setSeller(employee);
		
		int newBikeID = bp.addABicycle(newBicycle);
		newBicycle.setId(newBikeID);
		Set<Bicycle> allBikes = bp.getAllBicycles();
		int sizeBeforeTest = allBikes.size();
		
		bp.removeABicycle(newBicycle);
		assertEquals(sizeBeforeTest - 1, bp.getAllBicycles().size());
	}
	
	
	@Test
	public void getAllBicyclesOwnedByYou() {
		BicycleDAO bp = new BicyclePostgres();
		Customer customer = new Customer("josukeHigashikata4", "cureijiDaiyamondo", 4);
		
		Set<Bicycle> josukesBicycles = bp.getAllBicyclesOwnedByYou(customer.getId());
		Bicycle nullBicycle = new Bicycle();
		
		assertFalse(josukesBicycles.contains(nullBicycle));
		for (Bicycle b: josukesBicycles) {
			if (b.getWhoWillOwnTheBike().getId() != customer.getId()) {
				fail("retrieved a bicycles that was not customer #4");
			}
		}
	}
	
	@Test
	public void getABicycle() {
		BicycleDAO bp = new BicyclePostgres();
		
		int wantedBikeId = 2;
		assertNotNull(bp.getABicycle(2));
	}
}
