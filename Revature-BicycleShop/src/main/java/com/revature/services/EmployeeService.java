package com.revature.services;

import java.util.Set;

import com.revature.beans.Bicycle;
import com.revature.beans.Offer;

public interface EmployeeService {

	public int addABicycle(Bicycle bicycle);
	
	public boolean removeABicycle(Bicycle bicycle);
	public boolean removeABicycle(int bicycleID);
	
	public Set<Offer> acceptAnOffer(int offerID);
	public boolean rejectAnOffer(int offerID);
	public Set<Offer> getAllOffers();
	public Set<Bicycle> getAllBicycles();
}
