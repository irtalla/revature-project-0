package com.revature.services;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import com.revature.dao.UserPostgres;
import com.revature.services.UserFunctions;


public class UserLoginTest {

	
	//note: this one is actually not voluntary
	//it will be changed to reflect a thing.
	@Test
	public void testUserValidationIfThere() {
		UserFunctions userCollections = new UserFunctions();
		
		assertNotEquals(-1, userCollections.validatePotentialUser("jonasanJosuta", "zaPasshon"));
	}
	
	@Test
	public void testUserValidationIfNotThere() {
		UserFunctions userCollections = new UserFunctions();
		
		assertNotNull(userCollections.validatePotentialUser("lesenfantesclotildes", "14605"));
	}
	
	//username and password are separate because
	//they will be separate when first making an account.
	@Test
	public void testCustomerRegistration() {
		String username = "deColores";
		String password = "inExasperation";
		
		UserFunctions uf = new UserFunctions();
		int sizeBeforeAddition = uf.getUserDAO().getAllUsers().size();
		uf.customerRegistration(username, password);
		
		assertEquals(sizeBeforeAddition + 1, uf.getUserDAO().getAllUsers().size());
	}
	
}
