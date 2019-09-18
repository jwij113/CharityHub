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
	
	@Column(name="First_Name")
    private String firstName;
	
	@Column(name="Last_Name")
    private String lastName;
	
	@Column(name="Dob")
    private Date dob;
	
	@Column(name="Email")
    private String email;
	
	@Column(name="Password")
    private String password;
	
	@Column(name="Profile_Pic", columnDefinition="mediumblob")
    private byte[] profilePic;

}
