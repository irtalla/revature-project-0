package com.revature.services;

import java.util.HashSet;
import java.util.Set;

import com.revature.beans.Bicycle;
import com.revature.beans.Offer;
import com.revature.dao.BicycleCollections;
import com.revature.dao.BicycleDAO;
import com.revature.dao.BicycleDAOFactory;
import com.revature.dao.OfferDAO;
import com.revature.dao.OfferDAOFactory;

public class EmployeeFunctions implements EmployeeService {
	
	
	private BicycleDAO bicycleDAO;
	private OfferDAO offerDAO;
	
	private Set<Offer> allOffers;
	private Set<Bicycle> allBicycles;

	public EmployeeFunctions() {
		BicycleDAOFactory bdf = new BicycleDAOFactory();
		bicycleDAO = bdf.getBicycleDAO();
		OfferDAOFactory odf = new OfferDAOFactory();
		offerDAO = odf.getOfferDAO();
		
		allBicycles = bicycleDAO.getAllBicycles();
		allOffers = offerDAO.getAllOffers();
		
	}
	
	public Set<Offer> getAllOffers(){
		return offerDAO.getAllOffers();
	}
	
	public Set<Bicycle> getAllBicycles(){
		return bicycleDAO.getAllAvailableBicycles();
	}
	
	//note: this is temporary.
	//this is just to establish a function that
	//I can override later on when taught about 
	@Override
	public int addABicycle(Bicycle bicycle) {
		return bicycleDAO.addABicycle(bicycle);
	}
	
	
	@Override
	public boolean removeABicycle(Bicycle bicycle) {
		return bicycleDAO.removeABicycle(bicycle);
	}
	
	@Override
	public boolean removeABicycle(int bicycleID) {
		Bicycle bicycle = bicycleDAO.getABicycle(bicycleID);
		return bicycleDAO.removeABicycle(bicycle);
	}
	
	@Override
	public Set<Offer> acceptAnOffer(int offerID) {
		Offer acceptedOffer = offerDAO.retrieveAnOffer(offerID);
		if (acceptedOffer != null) {
			offerDAO.acceptAnOffer(acceptedOffer);
		}
		return offerDAO.getAllOffers();
	}
	



	@Override
	public boolean rejectAnOffer(int offerID) {
		Offer rejectedOffer = null;
		rejectedOffer = offerDAO.retrieveAnOffer(offerID);
		if (rejectedOffer == null) {return false;}
		return offerDAO.rejectAnOffer(rejectedOffer);
	}
	
	

	
}
