package au.charityhub.app.web;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URLConnection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

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
	
	@Resource(name="charityManager")
	private CharityManager charityManager;
	
	@Resource(name="postManager")
	private PostManager postManager;
	
	@RequestMapping(value="/home")
	public ModelAndView userHome(HttpServletRequest httpServletRequest) {
		Cookie[] cookies = httpServletRequest.getCookies();
		String cookiestr = "";
		if (cookies != null) {
		 for (Cookie cookie : cookies) {
		   if (cookie.getName().equals("session")) {
			   cookiestr = cookie.getValue();
		    }
		  }
		}
		User u = userManager.getUserBySessionIDLoadCharities(cookiestr);
		
		if (u == null) {
			return new ModelAndView(new RedirectView("../login?e=5"));
		}
		
		List<Post> ps = userManager.getPost(u.getId());
		
		Map<String, Object> myModel = new HashMap<String, Object>();
		byte[] encoded = new byte[]{(byte)0xe0 };
		if (u.getProfilePic() != null)
			encoded = Base64.getEncoder().encode(u.getProfilePic());
		String encodeds = new String(encoded);
		
		Map<Long, Boolean> postsLiked  = new HashMap<Long, Boolean>();
		if (ps != null) {
			for (Post p: ps) {
				if (postManager.isLikeExist(p, u))
					postsLiked.put(p.getId(), true);
				else
					postsLiked.put(p.getId(), false);
			}
		}
		
		List<Charity> cs = charityManager.getListOfCharities();
		Set<Charity> csSuggested = new HashSet<Charity>();
		for(int i = 0; i<Integer.min(3, cs.size()) ; i++) {
			int selected = new Random().nextInt(cs.size());
			csSuggested.add(cs.get(selected));
		}
		
		myModel.put("postsLiked", postsLiked );
		myModel.put("csSuggested", csSuggested );
		myModel.put("user", u);
		myModel.put("posts", ps );
		myModel.put("encoded", encodeds);
		return new ModelAndView("userHome", "model", myModel);
	}	
	
	
	@RequestMapping(value="/findCharity")
	public ModelAndView findCharity(HttpServletRequest httpServletRequest) {
		Cookie[] cookies = httpServletRequest.getCookies();
		String cookiestr = "";
		if (cookies != null) {
		 for (Cookie cookie : cookies) {
		   if (cookie.getName().equals("session")) {
			   cookiestr = cookie.getValue();
		    }
		  }
		}
		User u = userManager.getUserBySessionIDLoadCharities(cookiestr);
		
		if (u == null) {
			return new ModelAndView(new RedirectView("../login?e=5"));
		}
		
		byte[] encoded = new byte[]{(byte)0xe0 };
		if (u.getProfilePic() != null)
			encoded = Base64.getEncoder().encode(u.getProfilePic());
		String encodeds = new String(encoded);
		
		String txt = httpServletRequest.getParameter("txt");
		List<Charity> cs = null;
		if (txt != null && txt.length() > 0) {
			cs= charityManager.getListOfCharitiesLike(txt);
		}else {
			cs = charityManager.getListOfCharities();
		}
		
		List<Charity> csComplete = charityManager.getListOfCharities();
		Set<Charity> csSuggested = new HashSet<Charity>();
		for(int i = 0; i<Integer.min(3, cs.size()) ; i++) {
			int selected = new Random().nextInt(cs.size());
			csSuggested.add(csComplete.get(selected));
		}
		
		Map<String, Object> myModel = new HashMap<String, Object>();
		myModel.put("user", u);
		myModel.put("charities", cs);
		myModel.put("txt", txt);
		myModel.put("encoded", encodeds);
		myModel.put("csSuggested", csSuggested);
		return new ModelAndView("userFindCharity", "model", myModel);
	}
	
	@RequestMapping(value="/follow")
	public ModelAndView followCharity(HttpServletRequest httpServletRequest) {
		Cookie[] cookies = httpServletRequest.getCookies();
		String cookiestr = "";
		if (cookies != null) {
		 for (Cookie cookie : cookies) {
		   if (cookie.getName().equals("session")) {
			   cookiestr = cookie.getValue();
		    }
		  }
		}
		User u = userManager.getUserBySessionIDLoadCharities(cookiestr);
		String id = httpServletRequest.getParameter("id");
		String txt = httpServletRequest.getParameter("txt");
		if (txt == null) {
			txt = "";
		}
		if (id != null) {
			Charity c = charityManager.getCharityById(Long.parseLong(id));
			u.getCharities().add(c);
			userManager.updateUser(u);
		}
		return new ModelAndView(new RedirectView("findCharity?txt="+txt));
	}
	
	@RequestMapping(value="/unfollow")
	public ModelAndView unfollowCharity(HttpServletRequest httpServletRequest) {
		Cookie[] cookies = httpServletRequest.getCookies();
		String cookiestr = "";
		if (cookies != null) {
		 for (Cookie cookie : cookies) {
		   if (cookie.getName().equals("session")) {
			   cookiestr = cookie.getValue();
		    }
		  }
		}
		User u = userManager.getUserBySessionIDLoadCharities(cookiestr);
		String id = httpServletRequest.getParameter("id");
		String txt = httpServletRequest.getParameter("txt");
		if (txt == null) {
			txt = "";
		}
		if (id != null) {
			Charity c = charityManager.getCharityById(Long.parseLong(id));
			u.getCharities().remove(c);
			userManager.updateUser(u);
		}
		return new ModelAndView(new RedirectView("findCharity?txt="+txt));
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
		
		User u = userManager.getUserBySessionID(cookiestr);
		
		if (u == null)
			return new ModelAndView(new RedirectView("../login?e=5"));
			
		u.setSessionID(null);
		
		userManager.updateUser(u);
		
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
		
		User u = userManager.getUserBySessionID(cookiestr);
		String pid = httpServletRequest.getParameter("id");
		
		Post p = postManager.getPostById(Long.parseLong(pid));
		
		if (postManager.isLikeExist(p, u)) {
			postManager.destroyLike(p, u);
			return "Unliked";
		}else {
			postManager.addLike(p, u);
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
		
		User u = userManager.getUserBySessionID(cookiestr);
		String pid = httpServletRequest.getParameter("id");
		String comment = httpServletRequest.getParameter("comment");
		
		Post p = postManager.getPostById(Long.parseLong(pid));
		
		try {
			postManager.addComment(p, u, comment);
		} catch (Exception e) {
			return "false";
		}
		
		return "true";
		
		
	}
	
	
	@RequestMapping(value="/ajax/follow")
	@ResponseBody
	public String ajaxfollowCharity(HttpServletRequest httpServletRequest) {
		Cookie[] cookies = httpServletRequest.getCookies();
		String cookiestr = "";
		if (cookies != null) {
		 for (Cookie cookie : cookies) {
		   if (cookie.getName().equals("session")) {
			   cookiestr = cookie.getValue();
		    }
		  }
		}
		User u = userManager.getUserBySessionIDLoadCharities(cookiestr);
		String id = httpServletRequest.getParameter("id");

		try {
			if (id != null) {
				Charity c = charityManager.getCharityById(Long.parseLong(id));
				if (!u.getCharities().contains(c)) {
					u.getCharities().add(c);
					userManager.updateUser(u);
					return "Followed";
				}else {
					u.getCharities().remove(c);
					userManager.updateUser(u);
					return "Unfollowed";
				}
				
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return "What?";
	}
	
	
}