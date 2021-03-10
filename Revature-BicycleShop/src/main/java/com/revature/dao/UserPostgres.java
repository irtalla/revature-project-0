package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

import com.revature.beans.Customer;
import com.revature.beans.Employee;
import com.revature.beans.Offer;
import com.revature.beans.User;
import com.revature.utils.ConnectionUtil;

public class UserPostgres implements UserDAO {
	
	private ConnectionUtil cu = ConnectionUtil.getConnectionUtil();

	@Override
	public Set<User> getAllUsers() {
		Set<User> allUsers = new HashSet<User>();
		
		try (Connection conn = cu.getConnection()){
			String sql = "select * from bike_shop_users";
			
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
			while (rs.next()) {
				int userID = rs.getInt("user_id");
				String username = rs.getString("user_username");
				String password = rs.getString("user_password");
				String role = rs.getString("user_role");
				User user = new User(username, password, role, userID);
				allUsers.add(user);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			User nullUser = new User();
			allUsers.add(nullUser);
		}
		
		return allUsers;
	
	}

	@Override
	public Set<Employee> getAllEmployees() {
		Set<Employee> allEmployees = new HashSet<Employee>();
		
		try (Connection conn = cu.getConnection()){
			String sql = "select * from bike_shop_users where user_role = 'employee'";
			
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
			while (rs.next()) {
				int userID = rs.getInt("user_id");
				String username = rs.getString("user_username");
				String password = rs.getString("user_password");
				Employee employee = new Employee(username, password, userID);
				allEmployees.add(employee);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			Employee nullEmployee = new Employee();
			nullEmployee.setType("user");
			allEmployees.add(nullEmployee);
		}
		
		return allEmployees;
	}

	@Override
	public Set<Customer> getAllCustomers() {
		Set<Customer> allCustomers = new HashSet<Customer>();
		
		try (Connection conn = cu.getConnection()){
			String sql = "select * from bike_shop_users where user_role = 'customer'";
			
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
			while (rs.next()) {
				int userID = rs.getInt("user_id");
				String username = rs.getString("user_username");
				String password = rs.getString("user_password");
				Customer user = new Customer(username, password, userID);
				allCustomers.add(user);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			Customer nullCustomer = new Customer();
			nullCustomer.setType("user");
			allCustomers.add(nullCustomer);
		}
		
		return allCustomers;
	}

	@Override
	public int registerACustomer(String username, String password) {
		int customerIndex = -1;
		
		try (Connection conn = cu.getConnection()){
			conn.setAutoCommit(false);
			
			String customerAdd = "insert into bike_shop_users values (default, ?, ?, 'customer')";
			String[] keys = {"user_id"};
			PreparedStatement pstmt = conn.prepareStatement(customerAdd, keys);
			pstmt.setString(1, username);
			pstmt.setString(2, password);

			
			pstmt.executeUpdate();
			ResultSet rs = pstmt.getGeneratedKeys();		

			
			if (rs.next()) {
				customerIndex = rs.getInt(1);
				conn.commit();
			}
			else {
				conn.rollback();
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return customerIndex;
		
	}

	@Override
	public boolean removeACustomer(int id) {
		Connection conn = cu.getConnection();
		
		try {
			conn.setAutoCommit(false);
			
			String removeUser = "delete from bike_shop_users where user_id = ?";
			String removeUsersBikes = "delete from bicycles where owner_id = ?";
			String removeUsersOffers = "delete from offers where offer_maker_id = ?;";
			
			PreparedStatement pstmt = conn.prepareStatement(removeUsersOffers);
			pstmt.setInt(1, id);
			pstmt.executeUpdate();
			
			pstmt = conn.prepareStatement(removeUsersBikes);
			pstmt.setInt(1, id);
			pstmt.executeUpdate();
			
			pstmt = conn.prepareStatement(removeUsersBikes);
			pstmt.setInt(1, id);
			pstmt.executeUpdate();
			
			conn.commit();
			return true;
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				conn.close();
			}
			catch(SQLException sqle) {
				sqle.printStackTrace();
				return false;
			}
		}
		
		return false;
	}

	@Override
	public User findAUser(String username, String password) {
		 User user = null;
		 
		 try (Connection conn = cu.getConnection()){
 			 String sql = "select * from bike_shop_users where user_username = ? and user_password = ?";
			 
			 PreparedStatement pstmt = conn.prepareStatement(sql);
			 pstmt.setString(1, username);
			 pstmt.setString(2, password);
			 ResultSet rs = pstmt.executeQuery();
			 
			 //assumption: there is only 1.
			 //makes sense, except I did not make usernames and password unique.
			 if (rs.next()) {
				String role = rs.getString("user_role");
				if (role.equals("customer")) {
					user = new Customer();
				}
				else if (role.equals("employee")) {
					user = new Employee();
				}
				else {
					user = new User();
				}
				
				user.setUsername(rs.getString("user_username"));
				user.setPassword(rs.getString("user_password"));
				user.setId(rs.getInt("user_id"));
				user.setType(rs.getString("user_role"));
				//int userID = rs.getInt("user_id");
				//String userUsername = rs.getString("user_username");
				//String userPassword = rs.getString("user_password");
				//user = new User(userUsername, userPassword, role, userID);
			 }
		 }
		 catch(Exception e) {
			 e.printStackTrace();
		 }
		 
		 return user;
	}
	

}
