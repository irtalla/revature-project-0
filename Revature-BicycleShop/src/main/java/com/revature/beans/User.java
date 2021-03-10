package com.revature.beans;

public class User {
	private String username;
	private String password;
	private String type;
	private int id;
	
	private static int idGenerator = 0;
	
	public User() {
		username = "";
		password = "";
		type = "user";
		id = 0;
	}
	
	
	public User(String type) {
		username = "";
		password = "";
		this.type = type;
		id = 0;
	}
	
	public User(String username, String password, String type) {
		this.username = username;
		this.password = password;
		this.type = type;
		id = ++idGenerator;
	}
	
	public User(String username, String password, String type, int id) {
		this.username = username;
		this.password = password;
		this.type = type;
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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
		return "User is acting as: " + type + "\nUsername: " + username + "\nPassword: " + password;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
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
		User other = (User) obj;
		if (id != other.id)
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}


	
	
	
}
