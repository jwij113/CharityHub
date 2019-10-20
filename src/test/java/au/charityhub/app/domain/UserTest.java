package au.charityhub.app.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import au.charityhub.app.factory.Factory;

public class UserTest {
	
	@Test
	public void testUser() {
		 try {
			 User u = Factory.getDefaultUser();
			 u.setEmail("joko_w_1@hotmail.com");
			 u.setPassword("joko");
			 assertEquals(u.getEmail(), "joko_w_1@hotmail.com");
			 assertEquals(u.getPassword(), "joko");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			assertTrue(false);
		}
		           
    }

}
