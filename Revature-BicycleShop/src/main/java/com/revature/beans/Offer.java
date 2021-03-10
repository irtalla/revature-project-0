package com.revature.beans;

public class Offer {
	private Customer offerMaker;
	private Bicycle bicycleToBeSold;
	private double offer;
	private String status;
	private int id;
	
	private static int idGenerator = 0;
	
	public Offer() {
		offerMaker = null;
		bicycleToBeSold = null;
		offer = 0.00;
		status = "pending";
		id = 0;
	}
	
	public Offer(Customer customer, Bicycle bicycle, double offer) {
		offerMaker = customer;
		bicycleToBeSold = bicycle;
		this.offer = offer;
		this.status = "pending";
		id = ++idGenerator;
	}
	
	public Offer(Customer customer, Bicycle bicycle, double offer, int id) {
		offerMaker = customer;
		bicycleToBeSold = bicycle;
		this.offer = offer;
		this.status = "pending";
		this.id = id;
	}
	
	public Offer(Customer customer, Bicycle bicycle, double offer, String status, int id) {
		offerMaker = customer;
		bicycleToBeSold = bicycle;
		this.offer = offer;
		this.status = status;
		this.id = id;
	}

	/**
	 * @return the offerMaker
	 */
	public Customer getOfferMaker() {
		return offerMaker;
	}

	/**
	 * @param offerMaker the offerMaker to set
	 */
	public void setOfferMaker(Customer offerMaker) {
		this.offerMaker = offerMaker;
	}

	/**
	 * @return the bicycleToBeSold
	 */
	public Bicycle getBicycleToBeSold() {
		return bicycleToBeSold;
	}

	/**
	 * @param bicycleToBeSold the bicycleToBeSold to set
	 */
	public void setBicycleToBeSold(Bicycle bicycleToBeSold) {
		this.bicycleToBeSold = bicycleToBeSold;
	}
	
	public double getOffer() {
		return offer;
	}
	
	public void setOffer(double offer) {
		this.offer = offer;
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status){
		this.status = status;
	}
	
	
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return offerMaker.getUsername() + " is offering " + bicycleToBeSold.getSeller().getUsername() + " " + offer + " for the " + bicycleToBeSold.getBikeModel() + " " + bicycleToBeSold.getBikeType(); 
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bicycleToBeSold == null) ? 0 : bicycleToBeSold.hashCode());
		result = prime * result + id;
		long temp;
		temp = Double.doubleToLongBits(offer);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((offerMaker == null) ? 0 : offerMaker.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Offer other = (Offer) obj;
		if (bicycleToBeSold == null) {
			if (other.bicycleToBeSold != null)
				return false;
		} else if (!bicycleToBeSold.equals(other.bicycleToBeSold))
			return false;
		if (id != other.id)
			return false;
		if (Double.doubleToLongBits(offer) != Double.doubleToLongBits(other.offer))
			return false;
		if (offerMaker == null) {
			if (other.offerMaker != null)
				return false;
		} else if (!offerMaker.equals(other.offerMaker))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		return true;
	}
	
	
}
