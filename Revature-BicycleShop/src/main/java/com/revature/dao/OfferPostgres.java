package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

import com.revature.beans.Bicycle;
import com.revature.beans.Customer;
import com.revature.beans.Employee;
import com.revature.beans.Offer;
import com.revature.utils.ConnectionUtil;

public class OfferPostgres implements OfferDAO {

	ConnectionUtil cu = ConnectionUtil.getConnectionUtil();
	
	@Override
	public Set<Offer> getAllOffers() {
		Set<Offer> allOffers = new HashSet<Offer>();
		
		try(Connection conn = cu.getConnection()){
			//this long string means "merge offer and bicycles where the bicycles are being
			//have offers made for them and its potential owner, then add the info of the
			//offer made on that bicycle.
			
			String motherOfAllMerges = "select * from (select * from offers join bike_shop_users "
					+ "on offers.offer_maker_id = bike_shop_users.user_id) as"
					+ " offer_cust join (select * from (select * from bicycles "
					+ "join bike_shop_users on (seller_id = user_id)) as bike_empl "
					+ "left join bike_shop_users bsu on owner_id = bike_empl.user_id) "
					+ "as bike_empl_owner on bike_empl_owner.bike_id = offer_cust.bike_id";
			Statement iWokeUpToAMessageOfLove = conn.createStatement();
			
			//String getUsersOwningBikes = "select * from users where user_id = (select owner_id from bicycles where bike_id = ?)"

			ResultSet rs = iWokeUpToAMessageOfLove.executeQuery(motherOfAllMerges);
			
			while(rs.next()) {
				Customer customer = new Customer();
				Employee employee = new Employee();
				Bicycle bicycle = new Bicycle();
				Offer offer = new Offer();
				
				offer.setId(rs.getInt(1));
				customer.setId(rs.getInt(2));
				bicycle.setId(rs.getInt(3));
				employee.setId(rs.getInt(14));
				
			
				//offer-exclusive stuff is set up here
				offer.setOffer(rs.getDouble(4));
				offer.setStatus(rs.getString(5));
				
				
				//customer-exclusive stuff is set up here
				//rs.getInt(6) repeat of rs.getInt(2)
				customer.setUsername(rs.getString(7));
				customer.setPassword(rs.getString(8));
				customer.setType(rs.getString(9));
				offer.setOfferMaker(customer);
				
				//bike-exclusive stuff is set up here
				//rs.getInt(10) repeat of rs.getInt(3)
				bicycle.setBikeModel(rs.getString(11));
				bicycle.setBikeType(rs.getString(12));
				bicycle.setDescription(rs.getString(13));
				//rs.getInt(14) fits better with ID setters.
				bicycle.setPrice(rs.getDouble(15));
				bicycle.setStatus(rs.getString(16));
				//owner id not necessary here and can be gotten if offer is whatever
				offer.setBicycleToBeSold(bicycle);
				
				
				//employee-exclusive stuff is set up here
				//rs.getInt(17) repeat of rs.getInt(14)
				employee.setUsername(rs.getString(19));
				employee.setPassword(rs.getString(20));
				employee.setType(rs.getString(21));
				bicycle.setSeller(employee);
				
				if (offer.getStatus().equals("accepted")) {
					bicycle.setWhoWillOwnTheBike(customer);
				}
				else if (bicycle.getStatus().equals("owned")) {
					String getUsersOwningBikes = "select * from bike_shop_users where user_id = (select owner_id from bicycles where bike_id = ?)";
					PreparedStatement pstmt = conn.prepareStatement(getUsersOwningBikes);
					pstmt.setInt(1, bicycle.getId());
					ResultSet rsOwner = pstmt.executeQuery();
					Customer owner = new Customer();
					
					if (rsOwner.next()) {
						owner.setId(rsOwner.getInt("user_id"));
						owner.setUsername(rsOwner.getString("user_username"));
						owner.setPassword(rsOwner.getString("user_password"));
						owner.setType(rsOwner.getString("user_role"));
						bicycle.setWhoWillOwnTheBike(owner);
					}
				}
				else {
					//do nothing as if it hasn't been owned or accepted then it's not a problem.
				}
			
				allOffers.add(offer);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
			Offer nullOffer = new Offer();
			allOffers.add(nullOffer);
		}
		
		return allOffers;
	}

	@Override
	public int addAnOffer(Offer o) {
		int offerIndex = -1;
		
		try (Connection conn = cu.getConnection()){
			conn.setAutoCommit(false);
			
			String sql = "insert into offers values (default, ?, ?, ?, ?)";
			String[] keys = {"offer_id"};
			PreparedStatement pstmt = conn.prepareStatement(sql, keys);
			pstmt.setInt(1, o.getOfferMaker().getId());
			pstmt.setInt(2, o.getBicycleToBeSold().getId());
			pstmt.setDouble(3, o.getOffer());
			pstmt.setString(4, o.getStatus());
			
			pstmt.executeUpdate();
			ResultSet rs = pstmt.getGeneratedKeys();
			
			if (rs.next()) {
				offerIndex = rs.getInt(1);
				conn.commit();
			}
			else {
				conn.rollback();
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		return offerIndex;
	}

	@Override
	public boolean removeAnOffer(Offer o) {		
		try (Connection conn = cu.getConnection()){			
			String sql = "delete from offers where offer_id = ?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, o.getId());

			
			pstmt.executeUpdate();

			return true;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}

	@Override
	public void acceptAnOffer(Offer o) {
		Connection conn = cu.getConnection();
		try {
			conn.setAutoCommit(false);
			
			String sql = "update offers set status = 'accepted' where offer_id = ? and status = 'pending'";
			String bikeOwnerConsequence = "update bicycles set owner_id = ? where bike_id = ?";
			String dependency = "update bicycles set status = 'owned' where bike_id = ?";
			String acceptanceTrigger = "update offers set status = 'rejected' where bike_id = ? and status = 'pending'";
			
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, o.getId());
			pstmt.executeUpdate();
			
			pstmt = conn.prepareStatement(bikeOwnerConsequence);
			pstmt.setInt(1, o.getOfferMaker().getId());
			pstmt.setInt(2, o.getBicycleToBeSold().getId());
			pstmt.executeUpdate();
			
			pstmt = conn.prepareStatement(dependency);
			pstmt.setInt(1, o.getBicycleToBeSold().getId());
			pstmt.executeUpdate();
			
			PreparedStatement pstmtConsequences = conn.prepareStatement(acceptanceTrigger);
			pstmtConsequences.setInt(1, o.getBicycleToBeSold().getId());
			pstmtConsequences.executeUpdate();

			conn.commit();
		}
		catch(Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException sqle) {
				// TODO Auto-generated catch block
				sqle.printStackTrace();
				return;
			}
		}
		finally {
			try {
				conn.close();
			} catch (SQLException sqle) {
				sqle.printStackTrace();
				return;
			}
		}
		
	}

	@Override
	public boolean rejectAnOffer(Offer o) {		
		try(Connection conn = cu.getConnection()){
			String rejectionTrigger = "update offers set status = 'rejected' where offer_id = ?"; 
			
			PreparedStatement pstmt = conn.prepareStatement(rejectionTrigger);
			pstmt.setInt(1, o.getId());
			return (pstmt.executeUpdate() > 0);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}

	@Override
	public Offer retrieveAnOffer(int offerID) {
		Offer offer = null;
		try (Connection conn = cu.getConnection()){
			String getOffer = "select * from (select * from offers join bike_shop_users "
					+ "on offers.offer_maker_id = bike_shop_users.user_id) as"
					+ " offer_cust join (select * from (select * from bicycles "
					+ "join bike_shop_users on (seller_id = user_id)) as bike_empl "
					+ "left join bike_shop_users bsu on owner_id = bike_empl.user_id) "
					+ "as bike_empl_owner on bike_empl_owner.bike_id = offer_cust.bike_id "
					+ "where offer_id = ?";
			
			PreparedStatement pstmt = conn.prepareStatement(getOffer);
			pstmt.setInt(1, offerID);
			ResultSet rs = pstmt.executeQuery();
			
			if (rs.next()) {
				Customer customer = new Customer();
				Employee employee = new Employee();
				Bicycle bicycle = new Bicycle();
				offer = new Offer();
				
				offer.setId(rs.getInt(1));
				customer.setId(rs.getInt(2));
				bicycle.setId(rs.getInt(3));
				employee.setId(rs.getInt(14));
				
			
				//offer-exclusive stuff is set up here
				offer.setOffer(rs.getDouble(4));
				offer.setStatus(rs.getString(5));
				
				
				//customer-exclusive stuff is set up here
				//rs.getInt(6) repeat of rs.getInt(2)
				customer.setUsername(rs.getString(7));
				customer.setPassword(rs.getString(8));
				customer.setType(rs.getString(9));
				offer.setOfferMaker(customer);
				
				//bike-exclusive stuff is set up here
				//rs.getInt(10) repeat of rs.getInt(3)
				bicycle.setBikeModel(rs.getString(11));
				bicycle.setBikeType(rs.getString(12));
				bicycle.setDescription(rs.getString(13));
				//rs.getInt(14) fits better with ID setters.
				bicycle.setPrice(rs.getDouble(15));
				bicycle.setStatus(rs.getString(16));
				//owner id not necessary here and can be gotten if offer is whatever
				offer.setBicycleToBeSold(bicycle);
				
				
				//employee-exclusive stuff is set up here
				//rs.getInt(17) repeat of rs.getInt(14)
				employee.setUsername(rs.getString(19));
				employee.setPassword(rs.getString(20));
				employee.setType(rs.getString(21));
				bicycle.setSeller(employee);
				
				if (offer.getStatus().equals("accepted")) {
					bicycle.setWhoWillOwnTheBike(customer);
				}	
			}
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		return offer;
	}

	@Override
	public Set<Offer> retrieveOffersSomeoneMade(int offerMakerID) {
		Set<Offer> allYourOffers = new HashSet<Offer>();
		
		try(Connection conn = cu.getConnection()){
			//this long string means "merge offer and bicycles where the bicycles are being
			//have offers made for them and its potential owner, then add the info of the
			//offer made on that bicycle but only if it has this id.
			
			String motherOfAllMerges = "select * from (select * from offers join bike_shop_users on offers.offer_maker_id = bike_shop_users.user_id) as offer_cust join (select * from (select * from bicycles join bike_shop_users on (seller_id = user_id)) as bike_empl left join bike_shop_users bsu on owner_id = bike_empl.user_id) as bike_empl_owner on bike_empl_owner.bike_id = offer_cust.bike_id where offer_maker_id = ?";
			
			PreparedStatement pstmt = conn.prepareStatement(motherOfAllMerges);
			pstmt.setInt(1, offerMakerID);
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				Customer customer = new Customer();
				Employee employee = new Employee();
				Bicycle bicycle = new Bicycle();
				Offer offerRep = new Offer();
				
				offerRep.setId(rs.getInt(1));
				customer.setId(rs.getInt(2));
				bicycle.setId(rs.getInt(3));
				employee.setId(rs.getInt(14));
				
				offerRep.setOffer(rs.getDouble(4));
				offerRep.setStatus(rs.getString(5));
				
				customer.setUsername(rs.getString(7));
				customer.setPassword(rs.getString(8));
				customer.setType(rs.getString(9));
				offerRep.setOfferMaker(customer);
				
				bicycle.setBikeModel(rs.getString(11));
				bicycle.setBikeType(rs.getString(12));
				bicycle.setDescription(rs.getString(13));
				bicycle.setPrice(rs.getDouble(15));
				bicycle.setStatus(rs.getString(16));
				offerRep.setBicycleToBeSold(bicycle);
				
				employee.setUsername(rs.getString(19));
				employee.setPassword(rs.getString(20));
				employee.setType(rs.getString(21));
				bicycle.setSeller(employee);
				
				if (bicycle.getStatus().equals("owned")) {
					String getUsersOwningBikes = "select * from bike_shop_users where user_id = (select owner_id from bicycles where bike_id = ?)";
					PreparedStatement pstmtOwner = conn.prepareStatement(getUsersOwningBikes);
					pstmtOwner.setInt(1, bicycle.getId());
					ResultSet rsOwner = pstmtOwner.executeQuery();
					Customer owner = new Customer();
					
					if (rsOwner.next()) {
						owner.setId(rsOwner.getInt("user_id"));
						owner.setUsername(rsOwner.getString("user_username"));
						owner.setPassword(rsOwner.getString("user_password"));
						owner.setType(rsOwner.getString("user_role"));
						bicycle.setWhoWillOwnTheBike(owner);
					}
				}
				
				allYourOffers.add(offerRep);
				
				
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		return allYourOffers;
	}

}
