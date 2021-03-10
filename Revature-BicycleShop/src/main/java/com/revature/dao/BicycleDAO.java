package com.revature.dao;

import java.util.Set;

import com.revature.beans.Bicycle;

public interface BicycleDAO {
	public Set<Bicycle> getAllAvailableBicycles();
	public Set<Bicycle> getAllBicycles();
	public int addABicycle(Bicycle b);
	public boolean removeABicycle(Bicycle b);
	
	public Set<Bicycle> getAllBicyclesOwnedByYou(int customerID);
	public Bicycle getABicycle(int bikeID);
}
