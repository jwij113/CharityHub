package au.charityhub.app.service;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import au.charityhub.app.domain.Charity;
import au.charityhub.app.domain.User;

@Service(value = "userManager")
@Transactional
public class UserManager{
	private SessionFactory sessionFactory;
	
	@Autowired
	public void setSessionFactory(SessionFactory sf) {
		this.sessionFactory = sf;
	}
	
	public void addUser(User user) {
		this.sessionFactory.getCurrentSession().save(user);
	}
	
	public void updateUser(User user) {
		this.sessionFactory.getCurrentSession().merge(user);
	}
	
	public User getUserByEmail (String email) {
		Session currentSession = this.sessionFactory.getCurrentSession();
		Query q = currentSession.createQuery("From User u where u.email = :email");
		q.setString("email", email);
		
		if (q.list().isEmpty())
			return null;
		
		return (User) q.list().get(0);
	}
	
	public User getUserBySessionID (String sessionID) {
		Session currentSession = this.sessionFactory.getCurrentSession();
		Query q = currentSession.createQuery("From User u where u.sessionID = :sessionID");
		q.setString("sessionID", sessionID);
		
		if (q.list().isEmpty())
			return null;
		
		return (User) q.list().get(0);
	}
}

