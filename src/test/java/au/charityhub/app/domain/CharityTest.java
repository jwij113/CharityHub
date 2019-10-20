package au.charityhub.app.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import au.charityhub.app.factory.Factory;

public class CharityTest {
	
	@Test
	public void testCharity() {
		 try {
			 Charity c = Factory.getDefaultCharity();
			 c.setEmail("joko_w_1@hotmail.com");
			 c.setPassword("joko");
			 assertEquals(c.getEmail(), "joko_w_1@hotmail.com");
			 assertEquals(c.getPassword(), "joko");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			assertTrue(false);
		}
		           
    }

}
