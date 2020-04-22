package domain;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Profile {

	@XmlID
	@XmlJavaTypeAdapter(IntegerAdapter.class)
	@Id
	private String id;
	private String name;
	private String surname;
	private String email;
	private String address;
	private String phonenumber;
	private Country nationality;
	private String city;
	private Date birthdate;
	private String profilepic;
	
	@XmlIDREF
	@OneToOne(cascade = CascadeType.PERSIST)
	private User user;

	
	public Profile(String name, String surname, String email) {
		this.name = name;
		this.surname = surname;
		this.email = email;
       //placeholder value of 50 euros for testing purposes before credit cards etc are implemented.
	}
	
	//User with default profile pic
		public Profile(String iD, String nm, String srnm, String email, String addr, String phn, Country nat,String city, Date birthdt) {
			super();
			this.id = iD;
			this.name = nm;
			this.surname = srnm;
			this.email = email;
			this.address = addr;
			this.phonenumber = phn;
			this.nationality = nat;
			this.city = city;
			this.birthdate = birthdt;
			this.profilepic = "images/profilepic/smiley.png";     
		}	
		
		
		//User with custom profile pic already set
		public Profile(String iD, String nm, String srnm, String email, String addr, String phn, Country nat,String city, Date birthdt, String pic) {
			super();
			this.id = iD;
			this.name = nm;
			this.surname = srnm;
			this.email = email;
			this.address = addr;
			this.phonenumber = phn;
			this.nationality = nat;
			this.city = city;
			this.birthdate = birthdt;
			this.profilepic = pic;
		}
		

		public String getID() {
			return this.id;
		}

		public void setID(String id) {
			this.id = id;
		}
		
		public String getProfilepic() {
			return profilepic;
		}


		public void setProfilepic(String profilepic) {
			this.profilepic = profilepic;
		}
		
		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getSurname() {
			return surname;
		}

		public void setSurname(String surname) {
			this.surname = surname;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}
		
		public String getAddress() {
			return address;
		}


		public void setAddress(String address) {
			this.address = address;
		}

		public String getPhonenumber() {
			return phonenumber;
		}


		public void setPhonenumber(String phonenumber) {
			this.phonenumber = phonenumber;
		}


		public Country getNationality() {
			return nationality;
		}


		public void setNationality(Country nationality) {
			this.nationality = nationality;
		}


		public String getCity() {
			return city;
		}


		public void setCity(String city) {
			this.city = city;
		}


		public Date getBirthdate() {
			return birthdate;
		}


		public void setBirthdate(Date birthdate) {
			this.birthdate = birthdate;
		}

		public void setUser(User u) {
			this.user = u;
		}
		
}
