package au.charityhub.app.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.server.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.server.result.MockMvcResultMatchers.status;

import java.net.URI;

import javax.annotation.Resource;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import au.charityhub.app.config.WebConfig;
import au.charityhub.app.domain.Charity;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = WebConfig.class)
@WebAppConfiguration
public class CharityManagerTest {
	
	@Resource(name="charityManager")
	private CharityManager charityManager;
	
	@Test
	public void testGetCharity() {
		 try {
			 Charity c = charityManager.getCharityByEmail("joko_w_1@hotmail.com");
			 assertEquals(c.getEmail(),"joko_w_1@hotmail.com");
			 assertEquals(c.getPassword(),"joko");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			assertTrue(false);
		}
		           
    }
	
	@Test
	public void testGetCharityBySessionID() {
		 try {
			 URI uri = new URI("http://localhost:8080/app/api/login?email=joko_w_1@hotmail.com&password=joko");
			 JSONTokener tokener = new JSONTokener(uri.toURL().openStream());
			 JSONObject root = new JSONObject(tokener);
			 Charity c = charityManager.getCharityBySessionID(root.getString("sessionID"));
			 assertEquals(c.getEmail(),"joko_w_1@hotmail.com");
			 assertEquals(c.getPassword(),"joko");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			assertTrue(false);
		}
		           
    }

}
