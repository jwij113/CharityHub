package au.charityhub.app.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="Post")
public class Post {
	
	@Id
	@GeneratedValue
	@Column(name="Id")
	private long id;
	
	public long getId() {
		return id;
	}

	@Column(name="Description")
    private String description;
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name="File", columnDefinition="mediumblob")
    private byte[] file;
	
	public byte[] getFile() {
		return file;
	}

	public void setFile(byte[] file) {
		this.file = file;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="timestamp", nullable = false,
	    columnDefinition="TIMESTAMP default CURRENT_TIMESTAMP")
	private Date timestamp = new Date();
	
	@ManyToOne
    private Charity charity;

	public Charity getCharity() {
		return charity;
	}

	public void setCharity(Charity charity) {
		this.charity = charity;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "post")
    private List<Liked> likes;

	public List<Liked> getLikes() {
		return likes;
	}
	
	public void setLikes(List<Liked> likes) {
		this.likes = likes;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "post")
	@OrderBy("timestamp ASC")
    private List<Comment> comments;

	public List<Comment> getComments() {
		return comments;
	}
	
	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}


}
