package au.charityhub.app.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="User")
public class User {
	
	@Id
	@GeneratedValue
	@Column(name="Id")
	private long id;
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	@Column(name="First_Name")
    private String firstName;
	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	@Column(name="Last_Name")
    private String lastName;
	

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	@Column(name="Dob")
    private Date dob;
	
	public Date getDob() {
		return dob;
	}
	
	public void setDob(Date dob) {
		this.dob = dob;
	}
	
	@Column(name="Email", unique=true)
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
	
	@ManyToMany
    @JoinTable(name = "user_charity")
    private Set<Charity> charities = new HashSet<Charity>();

	public Set<Charity> getCharities() {
		return charities;
	}

	public void setCharities(Set<Charity> charities) {
		this.charities = charities;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<Liked> likeds;
}
