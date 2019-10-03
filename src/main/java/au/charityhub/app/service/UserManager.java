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
	
	
	
}

