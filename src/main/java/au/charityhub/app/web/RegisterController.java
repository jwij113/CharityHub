package au.charityhub.app.web;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.exception.GenericJDBCException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import au.charityhub.app.domain.Charity;
import au.charityhub.app.domain.User;
import au.charityhub.app.service.CharityManager;
import au.charityhub.app.service.UserManager;


@Controller
@RequestMapping(value="/register/**")
public class RegisterController {
	
	private static final Logger logger = LoggerFactory.getLogger(RegisterController.class);
	
	@Resource(name="charityManager")
	private CharityManager charityManager;
	
	@RequestMapping(value="/charity")
	public ModelAndView registerCharityPage(HttpServletRequest httpServletRequest) {
		
		/*Charity c = charityManager.getCharityById(1);
		byte[] encoded = Base64.getEncoder().encode(c.getProfilePic());
		String encodeds = new String(encoded);
		Map<String, Object> myModel = new HashMap<String, Object>();
		myModel.put("encoded", encodeds);
		myModel.put("charity", c);*/
		
		Map<String, Object> myModel =  null;
		
		return new ModelAndView("charityRegister", "model", myModel);
	}
	
	@RequestMapping(value="/charity", method=RequestMethod.POST)
	@ResponseBody
	public String register (HttpServletRequest httpServletRequest, @RequestParam("profile_pic") MultipartFile file) {
		String email = httpServletRequest.getParameter("email");
		String password = httpServletRequest.getParameter("password");
		String orgName = httpServletRequest.getParameter("org_name");
		String description = httpServletRequest.getParameter("description");
		
		Charity c = new Charity();
		
		c.setDesc(description);
		c.setEmail(email);
		c.setPassword(password);
		c.setOrgName(orgName);
		
		try {
			c.setProfilePic(file.getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (email.length() == 0)
			return "Email too short";
		else if (description.length() == 0)
			return "Description too short";
		else if (password.length() == 0)
			return "Password too short";
		else if (orgName.length() == 0)
			return "Organisation name too short";
		else if (email.length() >= 255)
			return "Email too long";
		else if (description.length() >= 255)
			return "Description too long";
		else if (password.length() >= 255)
			return "Password too long";
		else if (orgName.length() >= 255)
			return "Organisation name too long";
		else if (charityManager.getCharityByEmail(email) != null)
			return "Email already exist";
		
		try {
			charityManager.addCharity(c);
		}catch (Exception e) {
			if (e.getCause() instanceof SQLException) {
				SQLException se = (SQLException)e.getCause();
				
				logger.info(se.getMessage());
				return "Error while processing form";
			}
			
			logger.info(e.getCause().toString());
			return "Error while processing form";
		}
		
		return "Register success";
	}
	
	@Resource(name = "userManager")	
	private UserManager userManager;
	
	@RequestMapping(value = "/user")
	public ModelAndView registerUserPage(HttpServletRequest httpServletRequest) {
		Map<String, Object> myModel =  null;
		
		return new ModelAndView("userRegister", "model", myModel);
	}
	
	@RequestMapping(value="/user", method=RequestMethod.POST)
	@ResponseBody
	public String registerUser (HttpServletRequest httpServletRequest, @RequestParam("profile_pic") MultipartFile file) {
		String email = httpServletRequest.getParameter("email");
		String password = httpServletRequest.getParameter("password");
		
		User user = new User();
		
		user.setEmail(email);
		user.setPassword(password);

	
	if (email.length() == 0)
		return "Email too short";
		/*
		 * else if (description.length() == 0) return "Description too short";
		 */
	else if (password.length() == 0)
		return "Password too short";
		/*
		 * else if (orgName.length() == 0) return "Organisation name too short";
		 */
	else if (email.length() >= 255)
		return "Email too long";
		/*
		 * else if (description.length() >= 255) return "Description too long";
		 */
	else if (password.length() >= 255)
		return "Password too long";
		/*
		 * else if (orgName.length() >= 255) return "Organisation name too long";
		 */
	else if (charityManager.getCharityByEmail(email) != null)
		return "Email already exist";
	
	try {
		userManager.addUser(user);
	}catch (Exception e) {
		if (e.getCause() instanceof SQLException) {
			SQLException se = (SQLException)e.getCause();
			
			logger.info(se.getMessage());
			return "Error while processing form";
		}
		
		logger.info(e.getCause().toString());
		return "Error while processing form";
	}
	
	return "Register success";
}
}
