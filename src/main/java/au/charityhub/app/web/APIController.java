package au.charityhub.app.web;

import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import au.charityhub.app.domain.Charity;
import au.charityhub.app.domain.User;
import au.charityhub.app.service.CharityManager;
import au.charityhub.app.service.UserManager;

@Controller
@RequestMapping(value="/api/**")
public class APIController {
	
	@Resource(name="charityManager")
	private CharityManager charityManager;
	
	@Resource(name="userManager")
	private UserManager userManager;
	
	private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	public static String randomAlphaNumeric(int count) {
	StringBuilder builder = new StringBuilder();
	while (count-- != 0) {
	int character = (int)(Math.random()*ALPHA_NUMERIC_STRING.length());
	builder.append(ALPHA_NUMERIC_STRING.charAt(character));
	}
	return builder.toString();
	}
	
	@RequestMapping(value="/login", method=RequestMethod.GET)
	public @ResponseBody String login(HttpServletRequest httpServletRequest) {
		String email = httpServletRequest.getParameter("email");
		String password = httpServletRequest.getParameter("password");
		String sessionID = "";
		
		Charity c = charityManager.getCharityByEmail(email);
		User u = userManager.getUserByEmail(email);
		
		if (email.length() == 0)
			return "{success:false}";
		else if (password.length() == 0)
			return "{success:false}";
		else if (c == null) {
			if (u == null)
				return "{success:false}";
			else if (!u.getPassword().equals(password))
				return "{success:false}";
			else {
				sessionID = randomAlphaNumeric(20);
				u.setSessionID(sessionID);
				userManager.updateUser(u);
				return "{success:true, type:user, sessionID:"+sessionID+"}";
			}
		}
		else if (!c.getPassword().equals(password))
			return "{success:false}";
		else{
			sessionID = randomAlphaNumeric(20);
			c.setSessionID(sessionID);
			charityManager.updateCharity(c);
			return "{success:true, type:user, sessionID:"+sessionID+"}";
		}
	}

	@RequestMapping(value="/test", method=RequestMethod.GET)
	public @ResponseBody String getProductJSON() {
		
		JSONObject jo = new JSONObject();
		jo.put("name", "Tester");
		jo.put("age", "22");
		jo.put("city", "Sydney");
		
		return jo.toString();
	}
	
	
	@RequestMapping(value="/getUserList", method=RequestMethod.GET)
	public @ResponseBody String getUserList(HttpServletRequest httpServletRequest) {
		String sessionID = httpServletRequest.getParameter("sessionID");
		
		Charity c = charityManager.getCharityBySessionIDLoadUser(sessionID);
		
		if (c == null)
			return "{success:false}";
		
		Set<User> su = c.getUsers();
		
		JSONArray joa = new JSONArray();
		
		for (User u : su) {
		JSONObject jo = new JSONObject();
		jo.put("id", u.getId());
		jo.put("first_name", u.getFirstName());
		jo.put("last_name", u.getLastName());
		joa.put(jo.toString());
		}
		
		return joa.toString();
	}
	
	
}
