package com.revature.services;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;

import com.revature.beans.Bicycle;
import com.revature.beans.Customer;
import com.revature.beans.Employee;
import com.revature.beans.Offer;
import com.revature.services.EmployeeFunctions;

public class EmployeeFunctionsTest {
	
	@Test
	public void testAddingABicycleToTheShop() {
		EmployeeFunctions ef = new EmployeeFunctions();
		Employee employee = new Employee("jonasanJosuta", "zaPasshon", 1);
		//Bicycle bicycle = new Bicycle("Roma Bicicleta", "Green Tea Chocolate Bike", "Start a new viral (or even moldal) trend", employee, 100.00, 6);
		Bicycle bicycle = new Bicycle("Disneyland Exercise", "The One You All Know", "Gimmicky Mickey Mouse bike", employee, 100.00, 6);
		
		assertNotEquals(-1, ef.addABicycle(bicycle));
	}
	
	/*
	@Test
	public void testAcceptingAnExistingOffer() {
		EmployeeFunctions ef = new EmployeeFunctions();
		CustomerFunctions cf = new CustomerFunctions();
		
		Customer customer = new Customer("josukeHigashikata4", "cureijiDaiyamondo", 4);
		Employee employee = new Employee("jonasanJosuta", "zaPasshon", 1);
		Bicycle bicycle = new Bicycle("Disneyland Exercise", "Strung Along", "Feels rather rubberhosey.", employee, 69.88, 17);
		Offer offer = new Offer(customer, bicycle, 20.53, 18);
		
		int realBicycleID = ef.addABicycle(bicycle);
		bicycle.setId(realBicycleID);
		int offerID = cf.makeAnOffer(offer);
		Set<Offer> whatever = ef.acceptAnOffer(offerID);
		
		offer.setId(offerID);
		offer.setStatus("accepted");
		assertTrue(whatever.contains(offer));
	}
	*/
	
	@Test
	public void testRejectingAnExistingOffer() {
		EmployeeFunctions ef = new EmployeeFunctions();	
		CustomerFunctions cf = new CustomerFunctions();
		
		Customer customer = new Customer("josukeHigashikata4", "cureijiDaiyamondo", 4);
		Employee employee = new Employee("jonasanJosuta", "zaPasshon", 1);
		Bicycle bicycle = new Bicycle("Disneyland Exercise", "Fruit Bike", "Health-conscious yet quirky", employee, 88.96, 186);
		Offer offer = new Offer(customer, bicycle, 35.02, 21);
		
		int whatever = ef.addABicycle(bicycle);
		bicycle.setId(whatever);
		int offerID = cf.makeAnOffer(offer);
		assertTrue(ef.rejectAnOffer(offerID));
	}
	
	@Test
	public void testAcceptingAndRejectingANonexistentOffer() {
		EmployeeFunctions ef = new EmployeeFunctions();
		int sizeBeforehand = ef.getAllOffers().size();
			
		assertEquals(sizeBeforehand, ef.acceptAnOffer(999).size());
		assertFalse(ef.rejectAnOffer(999));
	}
	
	@Test
	public void testRejectingSimilarBicycleOffers() {
		EmployeeFunctions ef = new EmployeeFunctions();
		CustomerFunctions cf = new CustomerFunctions();
		
		Customer customer = new Customer("josukeHigashikata4", "cureijiDaiyamondo", 4);
		Employee employee = new Employee("jonasanJosuta", "zaPasshon", 1);
		Bicycle bicycle = new Bicycle("Speedbicycle", "Strolling Pennyfarthing", "For a droll view of the countryside", employee, 69.88, 17);
		Bicycle bicycle2 = new Bicycle("Speedbicycle", "Strolling Pennyfarthing", "For a droll view of the countryside", employee, 69.88, 17);
		//Bicycle bicycle2 = new Bicycle("Roma Bicicleta", "Green Tea Chocolate Bike", "Start a new viral (or even moldal) trend", employee, 100.00, 9);
		Offer offer = new Offer(customer, bicycle, 84.33, 16);
		Offer offer2 = new Offer(customer, bicycle, 97.61, 17);
		
		int whatever = cf.makeAnOffer(offer);
		int whatever2 = cf.makeAnOffer(offer2);
		offer.setId(whatever);
		offer2.setId(whatever2);
		
		Set<Offer> newOffers = ef.acceptAnOffer(whatever);
		
		offer.setStatus("accepted");
		bicycle.setStatus("owned");
		offer.getBicycleToBeSold().setWhoWillOwnTheBike(customer);
		offer2.setStatus("rejected");
		//bicycle2.setStatus("owned");
		
		/*
		for(Offer no: newOffers) {
			if (no.getId() == offer2.getId()) {
				System.out.println("Do customers match? " + no.getOfferMaker().equals(offer2.getOfferMaker()));
				System.out.println("Do bicycles match? " + no.getBicycleToBeSold().equals(offer2.getBicycleToBeSold()));
				System.out.println("Do offers match?" + (no.getOffer() == offer2.getOffer()));
				System.out.println("Do offer statuses match?" + no.getStatus().equals(offer2.getStatus()));
				System.out.println("Do ids match? " + (no.getId() == offer2.getId()));
				System.out.println("Alright, bicycles don't match.");
				Bicycle noBicycle = no.getBicycleToBeSold();
				Bicycle offer2Bicycle = offer2.getBicycleToBeSold();
				System.out.println("Do bike models match? " + noBicycle.getBikeModel().equals(offer2Bicycle.getBikeModel()));
				System.out.println("Do bike types match? " + noBicycle.getBikeType().equals(offer2Bicycle.getBikeType()));
				System.out.println("Do descriptions match? " + noBicycle.getDescription().equals(offer2Bicycle.getDescription()));
				System.out.println("Do sellers match? " + noBicycle.getSeller().equals(offer2Bicycle.getSeller()));
				System.out.println("Do prices match? " + (noBicycle.getPrice() == offer2Bicycle.getPrice()));
				System.out.println("Do statuses match? " + noBicycle.getStatus().equals(offer2Bicycle.getStatus()));
				//System.out.println("Do bike owners match? " + noBicycle.getWhoWillOwnTheBike().equals(offerBicycle.getWhoWillOwnTheBike()));
				System.out.println("Do bike ids match? " + (noBicycle.getId() == offer2Bicycle.getId()));
				
				
				
				break;
			}
		}
		*/
		
		assertTrue(newOffers.contains(offer));
		assertTrue(newOffers.contains(offer2));		
	}
	
	@Test
	public void testAcceptedOfferMeansOwnedBike() {
		EmployeeFunctions ef = new EmployeeFunctions();
		CustomerFunctions cf = new CustomerFunctions();
		
		Customer customer = new Customer("josukeHigashikata4", "cureijiDaiyamondo", 4);
		Employee employee = new Employee("jonasanJosuta", "zaPasshon", 1);
		Bicycle bicycle = new Bicycle("Disneyland Exercise", "Great Company", "Inviting and made to be in groups", employee, 69.88, 17);
		Offer offer = new Offer(customer, bicycle, 98.76, 22);
		
		int whatever = ef.addABicycle(bicycle);
		bicycle.setId(whatever);
		int actualID = cf.makeAnOffer(offer);
		offer.setId(actualID);
		
		Set<Offer> acceptedOfferSet = ef.acceptAnOffer(actualID);
		offer.setStatus("accepted");
		offer.getBicycleToBeSold().setWhoWillOwnTheBike(customer);
		offer.getBicycleToBeSold().setStatus("owned");
		
		/*
		for(Offer no: acceptedOfferSet) {
			if (no.getId() == offer.getId()) {
				System.out.println("Do customers match? " + no.getOfferMaker().equals(offer.getOfferMaker()));
				System.out.println("Do bicycles match? " + no.getBicycleToBeSold().equals(offer.getBicycleToBeSold()));
				System.out.println("Do offers match?" + (no.getOffer() == offer.getOffer()));
				System.out.println("Do offer statuses match?" + no.getStatus().equals(offer.getStatus()));
				System.out.println("Do ids match? " + (no.getId() == offer.getId()));
				System.out.println("Alright, bicycles don't match.");
				Bicycle noBicycle = no.getBicycleToBeSold();
				Bicycle offerBicycle = offer.getBicycleToBeSold();
				System.out.println("Do bike models match? " + noBicycle.getBikeModel().equals(offerBicycle.getBikeModel()));
				System.out.println("Do bike types match? " + noBicycle.getBikeType().equals(offerBicycle.getBikeType()));
				System.out.println("Do descriptions match? " + noBicycle.getDescription().equals(offerBicycle.getDescription()));
				System.out.println("Do sellers match? " + noBicycle.getSeller().equals(offerBicycle.getSeller()));
				System.out.println("Do prices match? " + (noBicycle.getPrice() == offerBicycle.getPrice()));
				System.out.println("Do statuses match? " + noBicycle.getStatus().equals(offerBicycle.getStatus()));
				//System.out.println("Do bike owners match? " + noBicycle.getWhoWillOwnTheBike().equals(offerBicycle.getWhoWillOwnTheBike()));
				System.out.println("Do bike ids match? " + (noBicycle.getId() == offerBicycle.getId()));
				
				
				
				break;
			}
		}
		*/
		
		assertTrue(acceptedOfferSet.contains(offer));
		
		
	}
	
	@Test
	public void testRemovingABicycleFromTheShop() {
		EmployeeFunctions ef = new EmployeeFunctions();
		
		Employee employee = new Employee("jonasanJosuta", "zaPasshon", 1);
		Bicycle bicycle = new Bicycle("Steel Bike Run", "Sandman Mountain Bike", "Announce your presence on any terrain", employee, 100.00, 6);
		int actualBikeID = ef.addABicycle(bicycle);
		
		int sizeBeforeRemoval = ef.getAllBicycles().size();
		
		bicycle.setId(actualBikeID);
		ef.removeABicycle(bicycle);
		
		assertEquals(sizeBeforeRemoval - 1, ef.getAllBicycles().size());
	}
	
}
