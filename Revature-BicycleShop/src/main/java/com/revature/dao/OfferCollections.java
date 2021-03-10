package com.revature.dao;

import java.util.HashSet;
import java.util.Set;

import com.revature.beans.Bicycle;
import com.revature.beans.Customer;
import com.revature.beans.Employee;
import com.revature.beans.Offer;

public class OfferCollections implements OfferDAO{
	private static Set<Offer> allOffers;
	
	public OfferCollections() {
		allOffers = new HashSet<Offer>();
		
		Employee bicycleOfferer = new Employee("sagradaFamilia", "vanessaBebo");
		Bicycle bicycle = new Bicycle("ENMakis", "Mobility Mountain Bike", "Somehow this bike is so comfortable that even on the longest trails you will feel as if you're just riding a stationary bicycle at home.", bicycleOfferer, 69.42);
		Customer customerOffering = new Customer("baileyPickett", "fatAndSkinnyEpisode");
		Offer offer = new Offer(customerOffering, bicycle, 70.00);
		
		Employee bicycleOfferer2 = new Employee("pfscyhe", "ztux");
		Bicycle bicycle2 = new Bicycle("Enbolne", "Mobility Mountain Bike", "Best known ", bicycleOfferer, 69.42);
		Customer customerOffering2 = new Customer("baileyPickett", "fatAndSkinnyEpisode");
		Offer offer2 = new Offer(customerOffering, bicycle, 70.00);
		
		allOffers.add(offer);
	}

	@Override
	public Set<Offer> getAllOffers() {
		return allOffers;
	}
	
	@Override
	public boolean addAnOffer(Offer o) {
		return allOffers.add(o);
	}
	
	//surprisingly, this is the only thing that is added just because
	//it has to be. for now this will not be used
	//until database is implemented.
	
	//still, it is hard to imagine an offer will vary
	//in anything but the status.
	//thus, the inefficient, hyperspecific method.
	@Override
	public void update(Offer o) {
		HashSet<Offer> allOfTheOffers = (HashSet<Offer>) allOffers;
		
		for (Offer offer: allOfTheOffers) {
			if (offer.getBicycleToBeSold().equals(o.getBicycleToBeSold()) && offer.getOfferMaker().equals(o.getOfferMaker()) && offer.getOffer() == o.getOffer()) {
				offer.setStatus(o.getStatus());
			}
		}
	}
	
	
	
	@Override	
	public boolean removeAnOffer(Offer o) {
		return allOffers.remove(o);
	}
}
