package au.charityhub.app.web;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.server.MockMvc;
import org.springframework.test.web.server.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.test.context.ContextLoader;

import static org.junit.Assert.*;

import javax.annotation.Resource;

import au.charityhub.app.config.WebConfig;
import au.charityhub.app.domain.Post;
import au.charityhub.app.web.LoginController;

import static org.springframework.test.web.server.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.server.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.server.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = WebConfig.class)
@WebAppConfiguration
public class LoginControllerTest2  {
	
	private MockMvc mockMvc;
	
	@Autowired
	private WebApplicationContext wac;

	
	@Before
	public void init() {
		//this.mockMvc = MockMvcBuilders.standaloneSetup(new LoginController()).build();
		this.mockMvc = MockMvcBuilders.webApplicationContextSetup(this.wac).build();
		//this.mockMvc = MockMvcBuilders.standaloneSetup(new LoginController()).build();
	}
	
	@Test
	public void testLoginPage() {
		 try {
			 this.mockMvc.perform(get("/login/")).andExpect(status().isOk());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		           
    }
	
	@Test 
	public void testLogin() {

		 try {
			this.mockMvc.perform(post("/login/").param("email", "joko_w_1@hotmail.com").param("password", "joko")).andExpect(status().isOk());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

}
