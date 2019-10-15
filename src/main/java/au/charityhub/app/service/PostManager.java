package au.charityhub.app.service;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import au.charityhub.app.domain.Charity;
import au.charityhub.app.domain.Comment;
import au.charityhub.app.domain.Liked;
import au.charityhub.app.domain.Post;
import au.charityhub.app.factory.Factory;

@Service(value="postManager")
@Transactional
public class PostManager {
	
private SessionFactory sessionFactory;
	
	@Autowired
	public void setSessionFactory(SessionFactory sf) {
		this.sessionFactory = sf;
	}
	
	public void addPost(Post p) {
		this.sessionFactory.getCurrentSession().save(p);
	}
	
	public Post getPostById(long id) {
		Session currentSession = this.sessionFactory.getCurrentSession();
		Post p = (Post) currentSession.get(Post.class, id);
		return p;
	}
	
	
	public boolean isLikeExist(Post p, Charity c) {
		Session currentSession = this.sessionFactory.getCurrentSession();
		Query q = currentSession.createQuery("From Liked l where l.charity.id = :cid and l.post.id = :pid");
		q.setLong("cid", c.getId());
		q.setLong("pid", p.getId());
		
		if (q.list().isEmpty())
			return false;
		else
			return true;
	}
	
	public void destroyLike(Post p, Charity c) {
		Session currentSession = this.sessionFactory.getCurrentSession();
		Query q = currentSession.createQuery("From Liked l where l.charity.id = :cid and l.post.id = :pid");
		q.setLong("cid", c.getId());
		q.setLong("pid", p.getId());
		
		if (q.list().isEmpty())
			return;
		else {
			Liked l = (Liked) q.list().get(0);
			currentSession.delete(l);
		}
			
	}
	
	public void addLike(Post p, Charity c) {
		Liked l = Factory.getDefaultLike();
		l.setCharity(c);
		l.setPost(p);
		this.sessionFactory.getCurrentSession().save(l);
	}
	
	public void addComment(Post p, Charity c, String comment) {
		Comment co = Factory.getDefaultComment();
		co.setCharity(c);
		co.setPost(p);
		co.setComment(comment);
		this.sessionFactory.getCurrentSession().save(co);
	}

}
