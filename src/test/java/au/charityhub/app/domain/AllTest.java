package au.charityhub.app.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import au.charityhub.app.factory.Factory;

public class AllTest {
	
	@Test
	public void testAll() {
		try {
			 Post p = Factory.getDefaultPost();
			 User u = Factory.getDefaultUser();
			 Charity c = Factory.getDefaultCharity();
			 Comment co = Factory.getDefaultComment();
			 c.setEmail("joko_w_1@hotmail.com");
			 u.setEmail("twan0106@uni.sydney.edu.au");
			 p.setDescription("test");
			 co.setComment("test comment");
			 assertEquals(p.getDescription(), "test");
			 assertEquals(c.getEmail(), "joko_w_1@hotmail.com");
			 assertEquals(u.getEmail(), "twan0106@uni.sydney.edu.au");
			 assertEquals(co.getComment(), "test comment");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			assertTrue(false);
		}
		           
    }

}