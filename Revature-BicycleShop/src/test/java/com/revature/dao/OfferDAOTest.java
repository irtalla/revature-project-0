package com.revature.dao;

import org.junit.jupiter.api.Test;

import com.revature.beans.Bicycle;
import com.revature.beans.Customer;
import com.revature.beans.Employee;
import com.revature.beans.Offer;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;

public class OfferDAOTest {
	
	
	@Test
	public void testGetAllOffers(){
		OfferDAO op = new OfferPostgres();
		Offer nullOffer = new Offer();
		Set<Offer> allOffers = op.getAllOffers();
		
		for (Offer offer: allOffers) {
			assertFalse(offer.getBicycleToBeSold() == null && offer.getOfferMaker() == null && offer.getOffer() == 0.00 && offer.getStatus().equals("pending"));
			if (offer.getBicycleToBeSold() == null && offer.getOfferMaker() == null && offer.getOffer() == 0.00 && offer.getStatus().equals("pending")) {
				op.removeAnOffer(offer);
				break;
			}
		}
	}

	@Test
	public void testAddAnOffer() {
		Customer customer = new Customer("josukeHigashikata4", "cureijiDaiyamondo", 4);
		Employee employee = new Employee("jonasanJosuta", "zaPasshon", 1);
		Bicycle bicycle = new Bicycle("Roma Bicicleta", "Green Tea Chocolate Bike", "Start a new viral (or even moldal) trend", employee, 100.00, 9);
		Offer offer = new Offer(customer, bicycle, 555.55, 1000);
		
		OfferDAO op = new OfferPostgres();
		int offerID = op.addAnOffer(offer);
		offer.setId(offerID);
		
		Bicycle ownedBicycle = new Bicycle("Roma Bicicleta", "Green Tea Chocolate Bike", "Start a new viral (or even moldal) trend", employee, 100.00, customer, "owned", 9);
		Offer paradoxicalOffer = new Offer(customer, ownedBicycle, 555.55, "pending", offerID);
		Offer offerAccepted = new Offer(customer, ownedBicycle, 555.55, "accepted", offerID);
		Offer offerRejected = new Offer(customer, ownedBicycle, 555.55, "rejected", offerID);
		
		Set<Offer> allOffers = op.getAllOffers();
				
		assertTrue(allOffers.contains(offer) || allOffers.contains(offerAccepted) || allOffers.contains(offerRejected) || allOffers.contains(paradoxicalOffer));
		
		//clean up steps, for some reason (never was this meticulous in the services tests.
		for (Offer offerInAllOffers: allOffers) {
			if (offerInAllOffers.getBicycleToBeSold() == null && offerInAllOffers.getOfferMaker() == null && offerInAllOffers.getOffer() == 0.00 && offerInAllOffers.getStatus().equals("pending")) {
				op.removeAnOffer(offerInAllOffers);
				break;
			}
		}
		
	}
	
	
	@Test
	public void testRemoveAnOffer() {
		Customer customer = new Customer("josukeHigashikata4", "cureijiDaiyamondo", 4);
		Employee employee = new Employee("jonasanJosuta", "zaPasshon", 1);
		Bicycle bicycle = new Bicycle("Roma Bicicleta", "Green Tea Chocolate Bike", "Start a new viral (or even moldal) trend", employee, 100.00, 9);
		Offer offer = new Offer(customer, bicycle, 555.55, 1000);
		
		OfferDAO op = new OfferPostgres();
		int offerID = op.addAnOffer(offer);
		offer.setId(offerID);

		assertTrue(op.removeAnOffer(offer));
		
		Set<Offer> allOffers = op.getAllOffers();
		for (Offer offerInAllOffers: allOffers) {
			if (offerInAllOffers.getBicycleToBeSold() == null && offerInAllOffers.getOfferMaker() == null && offerInAllOffers.getOffer() == 0.00 && offerInAllOffers.getStatus().equals("pending")) {
				op.removeAnOffer(offerInAllOffers);
				break;
			}
		}
	}
	
	@Test
	public void testAcceptAnOffer(){
		Customer customer = new Customer("josukeHigashikata4", "cureijiDaiyamondo", 4);
		Employee employee = new Employee("jonasanJosuta", "zaPasshon", 1);
		Bicycle bicycle = new Bicycle("Roma Bicicleta", "Green Tea Chocolate Bike", "Start a new viral (or even moldal) trend", employee, 100.00, 9);
		Offer offer = new Offer(customer, bicycle, 555.56, 1000);
		
		OfferDAO op = new OfferPostgres();
		int offerID = op.addAnOffer(offer);
		offer.setId(offerID);
		
		op.acceptAnOffer(offer);
		Offer offerCheck = op.retrieveAnOffer(offerID);
		assertEquals("accepted", offerCheck.getStatus());
	}
	
	@Test
	public void testRejectAnOffer(){
		Customer customer = new Customer("josukeHigashikata4", "cureijiDaiyamondo", 4);
		Employee employee = new Employee("jonasanJosuta", "zaPasshon", 1);
		Bicycle bicycle = new Bicycle("Roma Bicicleta", "Green Tea Chocolate Bike", "Start a new viral (or even moldal) trend", employee, 100.00, 9);
		Offer offer = new Offer(customer, bicycle, 555.57, 1000);
		
		OfferDAO op = new OfferPostgres();
		int offerID = op.addAnOffer(offer);
		offer.setId(offerID);
		
		op.rejectAnOffer(offer);
		Offer offerCheck = op.retrieveAnOffer(offerID);
		assertEquals("rejected", offerCheck.getStatus());
	}
	
	@Test
	public void testRetrieveAnOffer(){
		Customer customer = new Customer("josukeHigashikata4", "cureijiDaiyamondo", 4);
		Employee employee = new Employee("jonasanJosuta", "zaPasshon", 1);
		Bicycle bicycle = new Bicycle("Roma Bicicleta", "Green Tea Chocolate Bike", "Start a new viral (or even moldal) trend", employee, 100.00, 9);
		Offer offer = new Offer(customer, bicycle, 556.57, 1000);
		
		OfferDAO op = new OfferPostgres();
		int offerID = op.addAnOffer(offer);
		offer.setId(offerID);
		
		Offer offerCheck = op.retrieveAnOffer(offerID);
		assertEquals(offer, offerCheck);
	}
	
	
	@Test
	public void retrieveOffersSomeoneMade(){
		Customer customer = new Customer("josukeHigashikata4", "cureijiDaiyamondo", 4);
		Employee employee = new Employee("jonasanJosuta", "zaPasshon", 1);
		Bicycle bicycle = new Bicycle("Roma Bicicleta", "Green Tea Chocolate Bike", "Start a new viral (or even moldal) trend", employee, 100.00, 9);
		Offer offer = new Offer(customer, bicycle, 756.55, 1000);
		
		OfferDAO op = new OfferPostgres();
		int offerID = op.addAnOffer(offer);
		offer.setId(offerID);
		
		Bicycle ownedBicycle = new Bicycle("Roma Bicicleta", "Green Tea Chocolate Bike", "Start a new viral (or even moldal) trend", employee, 100.00, customer, "owned", 9);
		Offer futileOffer = new Offer(customer, ownedBicycle, 756.55, offerID);
		
		Set<Offer> yourOffers = op.retrieveOffersSomeoneMade(customer.getId());
		
		assertTrue(yourOffers.contains(offer) || yourOffers.contains(futileOffer));
		
	}
}
