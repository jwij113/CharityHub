package au.charityhub.app.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="Liked")
public class Liked {
	
	@Id
	@GeneratedValue
	@Column(name="Id")
	private long id;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="timestamp", nullable = false,
	    columnDefinition="TIMESTAMP default CURRENT_TIMESTAMP")
	private Date timestamp = new Date();
	
	@ManyToOne
    private Post post;
	
	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}

	@ManyToOne
    private Charity charity;
	
	

	public Charity getCharity() {
		return charity;
	}

	public void setCharity(Charity charity) {
		this.charity = charity;
	}
	
	@ManyToOne
    private User user;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
