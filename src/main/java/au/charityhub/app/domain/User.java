package au.charityhub.app.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
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
	
	@Column(name="First_Name")
    private String firstName;
	
	@Column(name="Last_Name")
    private String lastName;
	
	@Column(name="Dob")
    private Date dob;
	
	public Date getDob() {
		return dob;
	}
	
	public void setEmaisetDobl(Date dob) {
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
	
	@Column(name="SessionID", unique = true)
	public String sessionID;

}
