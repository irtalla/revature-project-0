package com.revature.dao;

public class OfferDAOFactory {

	public OfferDAO getOfferDAO() {
		return new OfferPostgres();
	}
	
}
