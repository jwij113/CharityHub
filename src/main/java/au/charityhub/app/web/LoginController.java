package au.charityhub.app.web;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import au.charityhub.app.domain.Charity;
import au.charityhub.app.service.CharityManager;
import au.charityhub.app.domain.User;
import au.charityhub.app.service.UserManager;

@Controller
@RequestMapping(value="/login/**")
public class LoginController {
	
	@Resource(name="charityManager")
	private CharityManager charityManager;
	@Resource(name="userManager")
	private UserManager userManager;
	
	@RequestMapping(value="/")
	public String loginPage(HttpServletRequest httpServletRequest){
		return "login";
	}
	
	private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	public static String randomAlphaNumeric(int count) {
	StringBuilder builder = new StringBuilder();
	while (count-- != 0) {
	int character = (int)(Math.random()*ALPHA_NUMERIC_STRING.length());
	builder.append(ALPHA_NUMERIC_STRING.charAt(character));
	}
	return builder.toString();
	}
	
	@RequestMapping(value="/", method=RequestMethod.POST)
	public ModelAndView login (HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse ) {
		String email = httpServletRequest.getParameter("email");
		String password = httpServletRequest.getParameter("password");
		String sessionID = "";
		
		Charity c = charityManager.getCharityByEmail(email);
		User u = userManager.getUserByEmail(email);
		
		if (email.length() == 0)
			return new ModelAndView(new RedirectView("login?e=1"));
		else if (password.length() == 0)
			return new ModelAndView(new RedirectView("login?e=2"));
		else if (c == null) {
			if (u == null)
				return new ModelAndView(new RedirectView("login?e=3"));
			else if (!u.getPassword().equals(password))
				return new ModelAndView(new RedirectView("login?e=4"));
			else {
				sessionID = randomAlphaNumeric(20);
				u.setSessionID(sessionID);
				userManager.updateUser(u);
				Cookie cookie = new Cookie("session", sessionID);
		        cookie.setPath("/");
				httpServletResponse.addCookie(cookie);
				return new ModelAndView(new RedirectView("user/home"));
			}
		}
		else if (!c.getPassword().equals(password))
			return new ModelAndView(new RedirectView("login?e=4"));
		else{
			sessionID = randomAlphaNumeric(20);
			c.setSessionID(sessionID);
			charityManager.updateCharity(c);
			Cookie cookie = new Cookie("session", sessionID);
	        cookie.setPath("/");
			httpServletResponse.addCookie(cookie);
			return new ModelAndView(new RedirectView("charity/home"));
		}
	}
}
