package au.charityhub.app.service;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import au.charityhub.app.domain.Post;

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
	

}
