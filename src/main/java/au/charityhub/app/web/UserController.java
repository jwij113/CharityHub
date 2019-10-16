package au.charityhub.app.web;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URLConnection;
import java.sql.SQLException;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import au.charityhub.app.domain.Charity;
import au.charityhub.app.domain.Liked;
import au.charityhub.app.domain.Post;
import au.charityhub.app.domain.User;
import au.charityhub.app.factory.Factory;
import au.charityhub.app.service.CharityManager;
import au.charityhub.app.service.PostManager;
import au.charityhub.app.service.UserManager;

@Controller
@RequestMapping(value="/user/**")
public class UserController {
	@Resource(name="userManager")
	private UserManager userManager;
	
	@RequestMapping(value="/home")
	public ModelAndView charityHome(HttpServletRequest httpServletRequest) {
		Cookie[] cookies = httpServletRequest.getCookies();
		String cookiestr = "";
		if (cookies != null) {
		 for (Cookie cookie : cookies) {
		   if (cookie.getName().equals("session")) {
			   cookiestr = cookie.getValue();
		    }
		  }
		}
		User u = userManager.getUserBySessionID(cookiestr);
		Map<String, Object> myModel = new HashMap<String, Object>();
		myModel.put("user", u);
		return new ModelAndView("userHome", "model", myModel);
	}	
}