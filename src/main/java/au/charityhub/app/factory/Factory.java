package au.charityhub.app.factory;

import au.charityhub.app.domain.Charity;
import au.charityhub.app.domain.Comment;
import au.charityhub.app.domain.Liked;
import au.charityhub.app.domain.Post;

public class Factory {
	
	public static Post getDefaultPost() {
		Post p = new Post();
		return p;
	}
	
	public static Charity getDefaultCharity() {
		Charity c = new Charity();
		return c;
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
