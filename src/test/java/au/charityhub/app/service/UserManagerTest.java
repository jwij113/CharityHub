package au.charityhub.app.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.net.URI;

import javax.annotation.Resource;

import org.json.JSONObject;
import org.json.JSONTokener;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import au.charityhub.app.config.WebConfig;
import au.charityhub.app.domain.Charity;
import au.charityhub.app.domain.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = WebConfig.class)
@WebAppConfiguration
public class UserManagerTest {
	
	@Resource(name="userManager")
	private UserManager userManager;
	
	@Test
	public void testGetUser() {
		 try {
			 User u = userManager.getUserByEmail("user@user.com");
			 assertEquals(u.getEmail(),"user@user.com");
			 assertEquals(u.getPassword(),"joko");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			assertTrue(false);
		}
		           
    }
	
	@Test
	public void testGetUseryBySessionID() {
		 try {
			 URI uri = new URI("http://localhost:8080/app/api/login?email=user@user.com&password=joko");
			 JSONTokener tokener = new JSONTokener(uri.toURL().openStream());
			 JSONObject root = new JSONObject(tokener);
			 User u = userManager.getUserBySessionID(root.getString("sessionID"));
			 assertEquals(u.getEmail(),"user@user.com");
			 assertEquals(u.getPassword(),"joko");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			assertTrue(false);
		}
		           
    }


}
