package au.charityhub.app.service;

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import au.charityhub.app.domain.Charity;
import au.charityhub.app.domain.Post;

@Service(value="charityManager")
@Transactional
public class CharityManager {
	
	private SessionFactory sessionFactory;
	
	@Autowired
	public void setSessionFactory(SessionFactory sf) {
		this.sessionFactory = sf;
	}
	
	public void addCharity(Charity c) {
		this.sessionFactory.getCurrentSession().save(c);
	}
	
	public void updateCharity(Charity c) {
		this.sessionFactory.getCurrentSession().merge(c);
	}
	
	public Charity getCharityById(long id) {
		Session currentSession = this.sessionFactory.getCurrentSession();
		Charity c = (Charity) currentSession.get(Charity.class, id);
		return c;
	}
	
	public Charity getCharityByEmail (String email) {
		Session currentSession = this.sessionFactory.getCurrentSession();
		Query q = currentSession.createQuery("From Charity c where c.email = :email");
		q.setString("email", email);
		
		if (q.list().isEmpty())
			return null;
		
		return (Charity) q.list().get(0);
	}
	
	public Charity getCharityBySessionID (String sessionID) {
		Session currentSession = this.sessionFactory.getCurrentSession();
		Query q = currentSession.createQuery("From Charity c where c.sessionID = :sessionID");
		q.setString("sessionID", sessionID);
		
		if (q.list().isEmpty())
			return null;
		
		return (Charity) q.list().get(0);
	}

	public Charity getCharityBySessionIDLoadPost (String sessionID) {
		Session currentSession = this.sessionFactory.getCurrentSession();
		Query q = currentSession.createQuery("From Charity c where c.sessionID = :sessionID");
		q.setString("sessionID", sessionID);
		
		if (q.list().isEmpty())
			return null;
		
		Charity c = (Charity) q.list().get(0);
		
		Hibernate.initialize(c.getPosts());
		
		for (Post p: c.getPosts()) {
			Hibernate.initialize(p.getLikes());
			Hibernate.initialize(p.getComments());
		}
	
		return c;
	}
	
	public Charity getCharityBySessionIDLoadUser (String sessionID) {
		Session currentSession = this.sessionFactory.getCurrentSession();
		Query q = currentSession.createQuery("From Charity c where c.sessionID = :sessionID");
		q.setString("sessionID", sessionID);
		
		if (q.list().isEmpty())
			return null;
		
		Charity c = (Charity) q.list().get(0);
		
		Hibernate.initialize(c.getUsers());
	
		return c;
	}
	
	public List<Charity> getListOfCharities () {
		Session currentSession = this.sessionFactory.getCurrentSession();
		Query q = currentSession.createQuery("From Charity c");
		
			
		return (List<Charity>)q.list();
	}
	
	public List<Charity> getListOfCharitiesLike (String txt) {
		Session currentSession = this.sessionFactory.getCurrentSession();
		Query q = currentSession.createQuery("From Charity c where c.orgName like ?");
		q.setString(0, "%"+txt+"%");
		return (List<Charity>)q.list();
	}
}
