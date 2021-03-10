package com.revature.services;

import java.util.HashSet;
import java.util.Set;

import com.revature.beans.Bicycle;
import com.revature.beans.Customer;
import com.revature.beans.Offer;
import com.revature.beans.User;
import com.revature.dao.BicycleDAO;
import com.revature.dao.BicycleDAOFactory;
import com.revature.dao.OfferDAO;
import com.revature.dao.OfferDAOFactory;

public class CustomerFunctions implements CustomerService{
	
	private BicycleDAO bicycleDAO;
	private OfferDAO offerDAO;
	private Set<Bicycle> yourBicycles;
	private Set<Offer> yourOffers;
	
	private Customer whoeverYouAre;

	public CustomerFunctions() {
		BicycleDAOFactory bdf = new BicycleDAOFactory();
		bicycleDAO = bdf.getBicycleDAO();
		OfferDAOFactory odf = new OfferDAOFactory();
		offerDAO = odf.getOfferDAO();
	}
	
	public CustomerFunctions(Customer c) {
		BicycleDAOFactory bdf = new BicycleDAOFactory();
		bicycleDAO = bdf.getBicycleDAO();
		OfferDAOFactory odf = new OfferDAOFactory();
		offerDAO = odf.getOfferDAO();
		
		HashSet<Bicycle> yourBicycles = new HashSet<Bicycle>();
		HashSet<Offer> yourOffers = new HashSet<Offer>();
		
		for(Bicycle someBicycle: bicycleDAO.getAllAvailableBicycles()) {
			Customer assumedOwner = someBicycle.getWhoWillOwnTheBike();
			if (assumedOwner != null && assumedOwner.equals(c)) {
				yourBicycles.add(someBicycle);
			}
		}
		
		for(Offer someOffer: offerDAO.getAllOffers()) {
			Customer assumedOfferMaker = someOffer.getOfferMaker();
			if (assumedOfferMaker != null && assumedOfferMaker.equals(c)) {
				yourOffers.add(someOffer);
			}
		}
		
		this.yourBicycles = yourBicycles;
		this.yourOffers = yourOffers;
		
		whoeverYouAre = c;
	}
	
	//pun is intended. It is literally the customization of a program
	//by adding a customer.
	@Override
	public void addCustomerization(Customer c) {
		HashSet<Bicycle> yourBicycles = new HashSet<Bicycle>();
		HashSet<Offer> yourOffers = new HashSet<Offer>();
		
		for(Bicycle someBicycle: bicycleDAO.getAllAvailableBicycles()) {
			Customer assumedOwner = someBicycle.getWhoWillOwnTheBike();
			if (assumedOwner != null && assumedOwner.equals(c)) {
				yourBicycles.add(someBicycle);
			}
		}
		
		for(Offer someOffer: offerDAO.getAllOffers()) {
			Customer assumedOfferMaker = someOffer.getOfferMaker();
			if (assumedOfferMaker != null && assumedOfferMaker.equals(c)) {
				yourOffers.add(someOffer);
			}
		}
		
		whoeverYouAre = c;
		this.yourBicycles = yourBicycles;
		this.yourOffers = yourOffers;
	}
	
	@Override
	public int makeAnOffer(Offer o) {
		Set<Bicycle> bikeListAtTheTime = bicycleDAO.getAllAvailableBicycles();
		/*
		for(Bicycle blatt: bikeListAtTheTime) {
			if (blatt.getId() == o.getBicycleToBeSold().getId()) {
				Bicycle bicycle = o.getBicycleToBeSold();
				System.out.println("Do bike models match? " + blatt.getBikeModel().equals(bicycle.getBikeModel()));
				System.out.println("Do bike types match? " + blatt.getBikeType().equals(bicycle.getBikeType()));
				System.out.println("Do descriptions match? " + blatt.getDescription().equals(bicycle.getDescription()));
				System.out.println("Do sellers match? " + blatt.getSeller().equals(bicycle.getSeller()));
				System.out.println("Do prices match? " + (blatt.getPrice() == bicycle.getPrice()));
				System.out.println("Do statuses match? " + blatt.getStatus().equals(bicycle.getStatus()));
				//System.out.println("Do bike owners match? " + noBicycle.getWhoWillOwnTheBike().equals(offerBicycle.getWhoWillOwnTheBike()));
				System.out.println("Do bike ids match? " + (blatt.getId() == bicycle.getId()));
				
				
				
				break;
			}
		}
		*/
		
		if (!bikeListAtTheTime.contains(o.getBicycleToBeSold())) {
			return -1;
		}
		
		int globallyAddingOffer = offerDAO.addAnOffer(o);
		
		return globallyAddingOffer;
		
	}

	@Override
	public Set<Bicycle> viewAllAvailableBicycles() {
		return bicycleDAO.getAllAvailableBicycles();
	}
	
	@Override 
	public Set<Bicycle> viewBicyclesYouOwn() {
		return bicycleDAO.getAllBicyclesOwnedByYou(whoeverYouAre.getId());
	}
	
	@Override
	public Set<Offer> viewOffersYouMade(){
		return offerDAO.retrieveOffersSomeoneMade(whoeverYouAre.getId());
	}

	@Override
	public String calculatePayment(Offer o) {
		String calculatedPayment = "";
		double weeklyPaymentsOverAMonth = o.getOffer() / 4;
		
		if ((weeklyPaymentsOverAMonth * 10) % 1 == 0) {
			calculatedPayment += String.valueOf(weeklyPaymentsOverAMonth) + "0";
		}
		else {
			calculatedPayment += String.valueOf(weeklyPaymentsOverAMonth);
		}
		
		return calculatedPayment;
	}

	@Override
	public Offer createOfferObject(int bicycleID, double offer) {
		Offer newOffer = new Offer();
		
		Bicycle correspondingBike = bicycleDAO.getABicycle(bicycleID);
		newOffer.setBicycleToBeSold(correspondingBike);
		newOffer.setOffer(offer);
		newOffer.setStatus("pending");
		
		return newOffer;
	}
}
