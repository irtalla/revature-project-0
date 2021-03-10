package com.revature.services;

import java.util.Set;

import com.revature.beans.Bicycle;
import com.revature.beans.Customer;
import com.revature.beans.Offer;

public interface CustomerService {

	public Set<Bicycle> viewAllAvailableBicycles();
	public Set<Bicycle> viewBicyclesYouOwn();
	public Set<Offer> viewOffersYouMade();
	public int makeAnOffer(Offer o);
	public void addCustomerization(Customer c);
	
	public String calculatePayment(Offer o);
	public Offer createOfferObject(int bicycleID, double offer);
}
