package au.charityhub.app.domain;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;


@Entity
@Table(name="Charity")
public class Charity {
	
	@Id
	@GeneratedValue
	@Column(name="Id")
	private long id;
	
	public long getId() {
		return id;
	}

	@Column(name="Org_Name")
    private String orgName;
	
	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	@Column(name="Description")
    private String desc;
	
	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	@Column(name="Email", unique = true)
    private String email;
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name="Password")
    private String password;
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name="Profile_Pic", columnDefinition="mediumblob")
    private byte[] profilePic;

	public byte[] getProfilePic() {
		return profilePic;
	}

	public void setProfilePic(byte[] profilePic) {
		this.profilePic = profilePic;
	}
	
	
	@Column(name="SessionID", unique = true)
	public String sessionID;

	public String getSessionID() {
		return sessionID;
	}

	public void setSessionID(String sessionID) {
		this.sessionID = sessionID;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "charity")
	@OrderBy("timestamp DESC")
    private List<Post> posts;

	public List<Post> getPosts() {
		return posts;
	}

	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "charity")
    private List<Liked> likeds;
	
	@ManyToMany(mappedBy = "charities")
	private Set<User> users = new HashSet<User>();

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}
	
	 @Override
     public boolean equals(Object o) {
		 
         if (this == o) {
             return true;
         }
         if (o == null || getClass() != o.getClass()) {
        	 
             return false;
         }
         Charity c = (Charity) o;
         return c.id == this.id;
     }
	 
	 @Override
	 public int hashCode(){
			return (int) this.id;
	 }
	

}
