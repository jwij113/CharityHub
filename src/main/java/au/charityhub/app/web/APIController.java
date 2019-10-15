package au.charityhub.app.web;

import javax.annotation.Resource;

import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import au.charityhub.app.domain.Charity;
import au.charityhub.app.service.CharityManager;

@Controller
@RequestMapping(value="/api/**")
public class APIController {
	
	@Resource(name="charityManager")
	private CharityManager charityManager;
	
	@RequestMapping(value="/charity", method=RequestMethod.GET)
	public @ResponseBody Charity getCharityJSON() {
		Charity c = new Charity();
		c.setDesc("test");
		return c;
	}

	@RequestMapping(value="/test", method=RequestMethod.GET)
	public @ResponseBody String getProductJSON() {
		
		JSONObject jo = new JSONObject();
		jo.put("name", "jon doe");
		jo.put("age", "22");
		jo.put("city", "chicago");
		
		return jo.toString();
	}
}
