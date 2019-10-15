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
import au.charityhub.app.factory.Factory;
import au.charityhub.app.service.CharityManager;
import au.charityhub.app.service.PostManager;

@Controller
@RequestMapping(value="/charity/**")
public class CharityController {
	
	@Resource(name="charityManager")
	private CharityManager charityManager;
	
	@Resource(name="postManager")
	private PostManager postManager;
	
	private static final Logger logger = LoggerFactory.getLogger(RegisterController.class);
	
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
		
		Charity c = charityManager.getCharityBySessionIDLoadPost(cookiestr);
		
		
		if (c == null)
			return new ModelAndView(new RedirectView("../login?e=5"));
		
		List<Post> lp =  c.getPosts();
		
		byte[] encoded = Base64.getEncoder().encode(c.getProfilePic());
		String encodeds = new String(encoded);
		Map<String, Object> myModel = new HashMap<String, Object>();
		myModel.put("encoded", encodeds);
		myModel.put("charity", c);
		myModel.put("posts", lp);
		
		Map<Long, Boolean> postsLiked  = new HashMap<Long, Boolean>();
		for (Post p: lp) {
			if (postManager.isLikeExist(p, c))
				postsLiked.put(p.getId(), true);
			else
				postsLiked.put(p.getId(), false);
		}
		
		myModel.put("postsLiked", postsLiked );
			
		String contentType = null;
		
		try {
			contentType = URLConnection.guessContentTypeFromStream(new ByteArrayInputStream(c.getProfilePic()));
		} catch (IOException e) {
		}
		
		logger.info(contentType);
		return new ModelAndView("charityHome", "model", myModel);
	}
	
	@RequestMapping(value="/addPost")
	public ModelAndView charityAddPost(HttpServletRequest httpServletRequest) {
		
		
		Cookie[] cookies = httpServletRequest.getCookies();
		String cookiestr = "";
		if (cookies != null) {
		 for (Cookie cookie : cookies) {
		   if (cookie.getName().equals("session")) {
			   cookiestr = cookie.getValue();
		    }
		  }
		}
		
		Charity c = charityManager.getCharityBySessionID(cookiestr);
		
		if (c == null)
			return new ModelAndView(new RedirectView("../login?e=5"));
			
		byte[] encoded = Base64.getEncoder().encode(c.getProfilePic());
		String encodeds = new String(encoded);
		Map<String, Object> myModel = new HashMap<String, Object>();
		myModel.put("encoded", encodeds);
		myModel.put("charity", c);
		
		return new ModelAndView("charityAddPost", "model", myModel);
	}
	
	@RequestMapping(value="/addPost", method=RequestMethod.POST)
	public ModelAndView charityAddPostHandler(HttpServletRequest httpServletRequest, @RequestParam("file") MultipartFile file) {
		
		
		Cookie[] cookies = httpServletRequest.getCookies();
		String cookiestr = "";
		if (cookies != null) {
		 for (Cookie cookie : cookies) {
		   if (cookie.getName().equals("session")) {
			   cookiestr = cookie.getValue();
		    }
		  }
		}
		
		Charity c = charityManager.getCharityBySessionID(cookiestr);
		
		if (c == null)
			return new ModelAndView(new RedirectView("../login?e=5"));
			
		String description = httpServletRequest.getParameter("description");
		
		Post p = Factory.getDefaultPost();
		
		p.setDescription(description);
		
		try {
			p.setFile(file.getBytes());
		} catch (IOException e) {
		}
		
		p.setCharity(c);
		
		try {
			postManager.addPost(p);
		}catch (Exception e) {
			
			if (e.getCause() instanceof SQLException) {
				SQLException se = (SQLException)e.getCause();
				logger.info(se.getMessage());
			}
			
			logger.info(e.getCause().toString());
			
			return new ModelAndView(new RedirectView("home?e=1"));
		}
		
		
		return new ModelAndView(new RedirectView("home?s=1"));
	}
	
	@RequestMapping(value="/logout")
	public ModelAndView logout(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		
		
		Cookie[] cookies = httpServletRequest.getCookies();
		String cookiestr = "";
		if (cookies != null) {
		 for (Cookie cookie : cookies) {
		   if (cookie.getName().equals("session")) {
			   cookiestr = cookie.getValue();
		    }
		  }
		}
		
		Charity c = charityManager.getCharityBySessionID(cookiestr);
		
		if (c == null)
			return new ModelAndView(new RedirectView("../login?e=5"));
			
		c.setSessionID(null);
		
		charityManager.updateCharity(c);
		
		Cookie cookie = new Cookie("session", "");
        cookie.setPath("/");
        cookie.setMaxAge(0);
        httpServletResponse.addCookie(cookie);
		
		return new ModelAndView(new RedirectView("../login?w=2"));
	}
	
	
	@RequestMapping(value="/post/like")
	@ResponseBody
	public String likePost (HttpServletRequest httpServletRequest) {
		
		Cookie[] cookies = httpServletRequest.getCookies();
		String cookiestr = "";
		if (cookies != null) {
		 for (Cookie cookie : cookies) {
		   if (cookie.getName().equals("session")) {
			   cookiestr = cookie.getValue();
		    }
		  }
		}
		
		Charity c = charityManager.getCharityBySessionID(cookiestr);
		String pid = httpServletRequest.getParameter("id");
		
		Post p = postManager.getPostById(Long.parseLong(pid));
		
		if (postManager.isLikeExist(p, c)) {
			postManager.destroyLike(p, c);
			return "Unliked";
		}else {
			postManager.addLike(p, c);
			return "Liked";
		}
		
		
		
	}
	
	
	@RequestMapping(value="/post/comment")
	@ResponseBody
	public String commentPost (HttpServletRequest httpServletRequest) {
		
		Cookie[] cookies = httpServletRequest.getCookies();
		String cookiestr = "";
		if (cookies != null) {
		 for (Cookie cookie : cookies) {
		   if (cookie.getName().equals("session")) {
			   cookiestr = cookie.getValue();
		    }
		  }
		}
		
		Charity c = charityManager.getCharityBySessionID(cookiestr);
		String pid = httpServletRequest.getParameter("id");
		String comment = httpServletRequest.getParameter("comment");
		
		Post p = postManager.getPostById(Long.parseLong(pid));
		
		try {
			postManager.addComment(p, c, comment);
		} catch (Exception e) {
			return "false";
		}
		
		return "true";
		
		
	}
	
	
		
	

}
