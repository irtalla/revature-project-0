package com.revature.dao;

public class BicycleDAOFactory {
	
	public BicycleDAO getBicycleDAO() {
		return new BicyclePostgres();
	}

}
