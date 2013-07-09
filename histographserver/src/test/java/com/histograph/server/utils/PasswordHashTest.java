package com.histograph.server.utils;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

import java.util.logging.Logger;

import org.junit.Test;

import com.histograph.server.controllers.NewUserServlet;
import com.histograph.server.utils.PasswordHash;


public class PasswordHashTest {
	
	private static Logger log = Logger.getLogger(PasswordHashTest.class
			.getName());

	/**
	 * Tests the basic functionality of the PasswordHash class
	 *
	 * @param   args        ignored
	 */
	@Test
	public void test(){


		// Test password validation

		try{
		for(int i = 0; i < 100; i++)
		{
			//This will create two different hashes - as the index iterating over randomly produced Salts will change the resultant hash
			String password = ""+i;
			String hash = PasswordHash.createHash(password);
			String secondHash = PasswordHash.createHash(password);
			
			assertNotSame("FAILURE: TWO HASHES ARE EQUAL", hash, secondHash);

			//Check that incorrect password will not validate:
			String wrongPassword = ""+(i+1);
			assertFalse("FAILURE: WRONG PASSWORD ACCEPTED", PasswordHash.validatePassword(wrongPassword, hash));

			//Check that Correct Password will validate
			assertTrue("FAILURE: GOOD PASSWORD NOT ACCEPTED",PasswordHash.validatePassword(password, hash));

		}
		}catch(Exception e){
			log.severe(e.getMessage());
		}
	}
}
