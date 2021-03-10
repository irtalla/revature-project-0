package com.revature.beans;

	public class Bicycle {
	private String bikeModel;
	private String bikeType;
	private String description;
	private Employee seller;
	private double price;
	private String status;
	private Customer whoWillOwnTheBike;
	private int id;
	
	private static int idGenerator = 0;
	
	public Bicycle() {
		bikeModel = "a";
		bikeType = "a";
		description = "a";
		seller = null;
		price = 0.00;
		status = "available";
		whoWillOwnTheBike = null;
		id = 0;
	}
	
	public Bicycle(String model, String type, String description, Employee seller, double price) {
		bikeModel = model;
		bikeType = type;
		this.description = description;
		this.seller = seller;
		this.price = price;
		whoWillOwnTheBike = null;
		status = "available";
		id = ++idGenerator;
	}
	
	public Bicycle(String model, String type, String description, Employee seller, double price, int id) {
		bikeModel = model;
		bikeType = type;
		this.description = description;
		this.seller = seller;
		this.price = price;
		whoWillOwnTheBike = null;
		status = "available";
		this.id = id;
	}
	
	public Bicycle(String model, String type, String description, Employee seller, double price, String status, int id) {
		bikeModel = model;
		bikeType = type;
		this.description = description;
		this.seller = seller;
		this.price = price;
		whoWillOwnTheBike = null;
		this.status = status;
		this.id = id;
	}

	public Bicycle(String model, String type, String description, Employee seller, double price, Customer owner, String status, int id) {
		bikeModel = model;
		bikeType = type;
		this.description = description;
		this.seller = seller;
		this.price = price;
		whoWillOwnTheBike = owner;
		this.status = status;
		this.id = id;
	}
	
	public String getBikeModel() {
		return bikeModel;
	}

	public void setBikeModel(String bikeModel) {
		this.bikeModel = bikeModel;
	}

	public String getBikeType() {
		return bikeType;
	}

	public void setBikeType(String bikeType) {
		this.bikeType = bikeType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Employee getSeller() {
		return seller;
	}

	public void setSeller(Employee seller) {
		this.seller = seller;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
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

	public Customer getWhoWillOwnTheBike() {
		return whoWillOwnTheBike;
	}
	
	public void setWhoWillOwnTheBike(Customer whoWillOwnTheBike) {
		this.whoWillOwnTheBike = whoWillOwnTheBike;
	}
	
	@Override
	public String toString() {
		return bikeModel + " " + bikeType + ": " + description + "\nStarting price: " + price + "\nOffered by: " + seller.getUsername() + "\nOwned by: " + ((whoWillOwnTheBike != null) ? whoWillOwnTheBike.getUsername() : "No one");
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bikeModel == null) ? 0 : bikeModel.hashCode());
		result = prime * result + ((bikeType == null) ? 0 : bikeType.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + id;
		long temp;
		temp = Double.doubleToLongBits(price);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((seller == null) ? 0 : seller.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((whoWillOwnTheBike == null) ? 0 : whoWillOwnTheBike.hashCode());
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
		Bicycle other = (Bicycle) obj;
		if (bikeModel == null) {
			if (other.bikeModel != null)
				return false;
		} else if (!bikeModel.equals(other.bikeModel))
			return false;
		if (bikeType == null) {
			if (other.bikeType != null)
				return false;
		} else if (!bikeType.equals(other.bikeType))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (id != other.id)
			return false;
		if (Double.doubleToLongBits(price) != Double.doubleToLongBits(other.price))
			return false;
		if (seller == null) {
			if (other.seller != null)
				return false;
		} else if (!seller.equals(other.seller))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (whoWillOwnTheBike == null) {
			if (other.whoWillOwnTheBike != null)
				return false;
		} else if (!whoWillOwnTheBike.equals(other.whoWillOwnTheBike))
			return false;
		return true;
	}
	
	
}
