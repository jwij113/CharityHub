package au.charityhub.app.web;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.server.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.server.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.server.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.server.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.server.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.server.result.MockMvcResultMatchers.redirectedUrl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.server.MockMvc;
import org.springframework.test.web.server.MvcResult;
import org.springframework.test.web.server.result.MockMvcResultHandlers;
import org.springframework.test.web.server.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import au.charityhub.app.config.WebConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = WebConfig.class)
@WebAppConfiguration
public class CharityControllerTest {
	
	private MockMvc mockMvc;
	
	@Autowired
	private WebApplicationContext wac;
	
	@Before
	public void init() {
		this.mockMvc = MockMvcBuilders.webApplicationContextSetup(this.wac).build();
	}
	
	@Test
	public void testCharityPage() {
		 try {
			 this.mockMvc.perform(get("/charity/home")).andExpect(redirectedUrl("../login?e=5")).andExpect(status().isMovedTemporarily());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		           
    }
	
	@Test
	public void testCharityHome() {
		 try {
			 MvcResult result =  this.mockMvc.perform(post("/login/").param("email", "joko_w_1@hotmail.com").param("password", "joko"))
					 .andExpect(redirectedUrl("charity/home"))
					 .andExpect(status().isMovedTemporarily())
					 .andReturn();
			 
			 String cookie = result.getResponse().getCookie("session").getValue();
			 int	length = result.getResponse().getContentLength();
			 assertNotNull(cookie);
			 
		 } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		           
    }

}
