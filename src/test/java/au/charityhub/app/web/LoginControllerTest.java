package au.charityhub.app.web;

import junit.framework.TestCase;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.cookie;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.Assert.*;

import au.charityhub.app.config.WebConfig;
import au.charityhub.app.domain.Post;
import au.charityhub.app.web.LoginController;



@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = WebConfig.class)
public class LoginControllerTest  {
	
	private MockMvc mockMvc;
	
	@Autowired
	private WebApplicationContext wac;

	
	@Before
	public void init() {
		//this.mockMvc = MockMvcBuilders.standaloneSetup(new LoginController()).build();
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}
	
//	@Test
//	public void testLoginPage() {
//		 try {
//			 this.mockMvc.perform(get("/login/")).andExpect(status().isOk());
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		           
//    }
	
	@Test 
	public void testLogin() {

		 try {
			this.mockMvc.perform(post("/login/test").param("email", "joko_w_1@hotmail.com")).andExpect(status().isOk());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

}
