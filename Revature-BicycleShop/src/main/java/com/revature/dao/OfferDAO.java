package com.revature.dao;

import java.util.Set;

import com.revature.beans.Offer;

public interface OfferDAO {
	public Set<Offer> getAllOffers();
	public int addAnOffer(Offer o);
	public boolean removeAnOffer(Offer o);

	public void acceptAnOffer(Offer o);
	public boolean rejectAnOffer(Offer o);
	
	public Offer retrieveAnOffer(int offerID);
	public Set<Offer> retrieveOffersSomeoneMade(int offerMakerID);
}
