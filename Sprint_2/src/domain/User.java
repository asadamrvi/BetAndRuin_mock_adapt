package domain;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.swing.ImageIcon;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * Stores credentials and additional information of registered users, including the active bets it has in place. 
 * Users may be regular or administrators (represented by isAdmin boolean).
 *
 */

@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class User implements Comparable<User>{

	@XmlID
	@XmlJavaTypeAdapter(IntegerAdapter.class)
	@Id
	private String ID;
	private String password;
	private String name;
	private String surname;
	private String email;
	private float cash;
	private String address;
	private Gender gender;
	private String phonenumber;
	private Nationality nationality;
	private String city;
	private Date birthdate;
	private Date registrationdate;
	private Date lastlogin;
	private String profilepic;

	private boolean isAdmin;

	
	@OneToMany(fetch=FetchType.LAZY, cascade=CascadeType.PERSIST)
	private ArrayList<Bet> bets;
	
	
	
	public ArrayList<Bet> getBets() {
		return bets;
	}


	public User(String iD, String password, String name, String surname, String email, boolean isAdmin) {
		super();
		this.ID = iD;
		this.password = password;
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.isAdmin = isAdmin;
		this.bets = new ArrayList<Bet>();
		this.cash = 50;         //placeholder value of 50 euros for testing purposes before credit cards etc are implemented.
		this.registrationdate = new Date();
	}

	//User with default profile pic
	public User(String iD, String pwd, String nm, String srnm, String email, String addr, Gender g, String phn, Nationality nat,String city, Date birthdt, boolean isAdmin) {
		super();
		this.ID = iD;
		this.password = pwd;
		this.name = nm;
		this.surname = srnm;
		this.email = email;
		this.address = addr;
		this.gender = g;
		this.phonenumber = phn;
		this.nationality = nat;
		this.city = city;
		this.birthdate = birthdt;
		this.profilepic = "images/smiley.png";
		this.isAdmin = isAdmin;
		this.bets = new ArrayList<Bet>();
		this.cash = 50;         //placeholder value of 50 euros for testing purposes before credit cards etc are implemented.
	}	

	//User with custom profile pic already set
	public User(String iD, String pwd, String nm, String srnm, String email, String addr, Gender g, String phn, Nationality nat,String city, Date birthdt, String pic, boolean isAdmin) {
		super();
		this.ID = iD;
		this.password = pwd;
		this.name = nm;
		this.surname = srnm;
		this.email = email;
		this.address = addr;
		this.gender = g;
		this.phonenumber = phn;
		this.nationality = nat;
		this.city = city;
		this.birthdate = birthdt;
		this.profilepic = pic;
		this.isAdmin = isAdmin;
		this.bets = new ArrayList<Bet>();
		this.cash = 50;         //placeholder value of 50 euros for testing purposes before credit cards etc are implemented.
	}




	public String getProfilepic() {
		return profilepic;
	}


	public void setProfilepic(String profilepic) {
		this.profilepic = profilepic;
	}


	public void setCash(float cash) {
		this.cash = cash;
	}


	public void setBets(ArrayList<Bet> bets) {
		this.bets = bets;
	}


	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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


	public boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public float getCash() {
		return cash;
	}
	

	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public Gender getGender() {
		return gender;
	}


	public void setGender(Gender gender) {
		this.gender = gender;
	}


	public String getPhonenumber() {
		return phonenumber;
	}


	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}


	public Nationality getNationality() {
		return nationality;
	}


	public void setNationality(Nationality nationality) {
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


	public Date getRegistrationdate() {
		return registrationdate;
	}


	public void setRegistrationdate(Date registrationdate) {
		this.registrationdate = registrationdate;
	}


	public Date getLastlogin() {
		return lastlogin;
	}


	public void setLastlogin(Date lastlogin) {
		this.lastlogin = lastlogin;
	}

	
	
	
	
	public String statusToString() {
		if(this.isAdmin) {
			return("Admin.");
		}
		else {
			return("User");
		}
	}
	/**
	 * Registers the bet performed by a user
	 * @param q			the question the bet has been placed on.
	 * @param amount	the amount of money.
	 */
	public void placeBet(Question q, float amount, int answer) {
		bets.add(new Bet(q,this,amount,answer));	
		this.cash -= amount;
	}

	public void addBet(Bet b) {
		bets.add(b);
	}
	public void addCash(float amount) {
		this.cash += amount;
	}

	/**
	 * Natural ordering is decided by the ID's
	 */
	public int compareTo(User u) {
		return this.ID.compareTo(u.ID);
	}
	
	class sortByName implements Comparator<User>{
		@Override
		public int compare(User u1, User u2) {
			return(u1.name.compareTo(u2.name));                  //////////
		}
	}
	
}
