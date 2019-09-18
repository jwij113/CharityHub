package au.charityhub.app.service;

import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import au.charityhub.app.domain.Product;

@Service(value="productManager")
@Transactional
public class DatabaseProductManager implements ProductManager {
	
	private SessionFactory sessionFactory;
	
	@Autowired
	public void setSessionFactory(SessionFactory sf) {
		this.sessionFactory = sf;
	}
	
	@Override
	public void addProduct(Product product) {
		this.sessionFactory.getCurrentSession().save(product);
	}
	
	@Override
	public Product getProductById(long id) {
		Session currentSession = this.sessionFactory.getCurrentSession();
		Product product = (Product) currentSession.get(Product.class, id);
		return product;
	}
	
	@Override
	public void updateProduct(Product product) {
		Session currentSession = this.sessionFactory.getCurrentSession();
		currentSession.merge(product);
	}
	
	
	@Override
	public void deleteProduct(long id) {
		Session currentSession = this.sessionFactory.getCurrentSession();
		Product product = (Product) currentSession.get(Product.class, id);
		currentSession.delete(product);
	}

	@Override
	public void increasePrice(int percentage) {
		Session currentSession = this.sessionFactory.getCurrentSession();
		List<Product> products = currentSession.createQuery("FROM Product").list();
		
		if (products != null) {
            for (Product product : products) {
                double newPrice = product.getPrice().doubleValue() * 
                                    (100 + percentage)/100;
                product.setPrice(newPrice);
                currentSession.save(product);
            }
        }
	}

	@Override
	public List<Product> getProducts() {
		return this.sessionFactory.getCurrentSession().createQuery("FROM Product").list();
	}

}
