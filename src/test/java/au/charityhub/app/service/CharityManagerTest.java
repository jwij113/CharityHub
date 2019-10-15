package au.charityhub.app.service;

import static org.springframework.test.web.server.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.server.result.MockMvcResultMatchers.status;

import javax.annotation.Resource;

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
	public void testLoginPage() {
		 try {
			 Charity c = charityManager.getCharityByEmail("joko_w_1@hotmail.com");
			 System.out.println(c.getEmail());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		           
    }

}
