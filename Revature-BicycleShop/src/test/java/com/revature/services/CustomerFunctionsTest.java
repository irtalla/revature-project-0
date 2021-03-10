package com.revature.services;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;
import java.util.Set;

import com.revature.beans.Bicycle;
import com.revature.beans.Customer;
import com.revature.beans.Employee;
import com.revature.beans.Offer;
import com.revature.dao.BicycleCollections;
import com.revature.dao.OfferCollections;
import com.revature.services.CustomerFunctions;

public class CustomerFunctionsTest {

	
	//why assertNotNull?  Have to make sure bicycles exist
	//and they exist only if the thing aren't there
	//but also test if they give same output?
	@Test
	public void testViewingAllThings() {
		CustomerFunctions cf = new CustomerFunctions();
		
		assertNotNull(cf.viewAllAvailableBicycles());
	}
	
	
	@Test
	public void testMakingAnOffer() {
		CustomerFunctions cf = new CustomerFunctions();
		EmployeeFunctions ef = new EmployeeFunctions();
		
		Customer customer = new Customer("josukeHigashikata4", "cureijiDaiyamondo", 4);
		Employee employee = new Employee("josefuJosuta", "hamittoPapuru", 2);
		Bicycle bicycle = new Bicycle("Speedbicycle", "Smokey City Bike", "Quick and nimble when it needs to be", employee, 88.96, 186);
		Offer offer = new Offer(customer, bicycle, 35.02, 21);
		
		int whatever = ef.addABicycle(bicycle);
		bicycle.setId(whatever);
		int offerID = cf.makeAnOffer(offer);
		assertTrue(ef.rejectAnOffer(offerID));
	}
	
	@Test
	public void testMakingABadOffer() {
		CustomerFunctions cf = new CustomerFunctions();
		
		Bicycle bike = new Bicycle("plaintains", "vapidity", "at the center of a restaurant", null, 8.99);
		Customer customer = new Customer("zagreus", "hIsForHamazing");
		
		Offer offer = new Offer(customer, bike, 99.99);
		
		assertEquals(-1, cf.makeAnOffer(offer));
	}
	
	@Test
	public void testLookingAtYourBikesWhenYouHaveBikes() {
		Customer customer = new Customer("josukeHigashikata4", "cureijiDaiyamondo", 4);
		CustomerFunctions cf = new CustomerFunctions(customer);
		Set<Bicycle> customerBikes = cf.viewAllAvailableBicycles();
		assertNotEquals(0, customerBikes.size());
	}
	
	@Test
	public void testLookingAtYourBikesWhenYouDontHaveBikes() {
		Customer cackletta = new Customer("cackletta", "fawful");
		CustomerFunctions cf = new CustomerFunctions(cackletta);
		
		HashSet<Bicycle> emptyHashSet = new HashSet<Bicycle>();
		
		assertArrayEquals(emptyHashSet.toArray(), cf.viewBicyclesYouOwn().toArray());
	}
	
	@Test
	public void testViewingYourOffers() {
		Customer josukeHigashikata4 = new Customer("josukeHigashikata4", "cureijiDaiyamondo");
		CustomerFunctions cf = new CustomerFunctions(josukeHigashikata4);
		
		HashSet<Bicycle> emptyHashSet = new HashSet<Bicycle>();
		assertArrayEquals(emptyHashSet.toArray(), cf.viewOffersYouMade().toArray());
	}
	
	
}
