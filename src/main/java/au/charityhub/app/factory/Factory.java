package au.charityhub.app.factory;

import au.charityhub.app.domain.Charity;
import au.charityhub.app.domain.Comment;
import au.charityhub.app.domain.Liked;
import au.charityhub.app.domain.Post;
import au.charityhub.app.domain.User;

public class Factory {
	
	public static Post getDefaultPost() {
		Post p = new Post();
		return p;
	}
	
	public static Charity getDefaultCharity() {
		Charity c = new Charity();
		return c;
	}
	
	public static User getDefaultUser() {
		User u = new User();
		return u;
	}
	
	public static Liked getDefaultLike() {
		Liked l = new Liked();
		return l;
	}
	
	public static Comment getDefaultComment() {
		Comment c = new Comment();
		return c;
	}

}
