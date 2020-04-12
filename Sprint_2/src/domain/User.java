package domain;

import java.util.ArrayList;
import java.util.Date;

import javax.jdo.annotations.Index;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
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
public class User{

	@XmlID
	@XmlJavaTypeAdapter(IntegerAdapter.class)
	@Id
	private String id;
	private String password;
	private boolean isAdmin;
	private float cash;
	private Date registrationdate;
	private Date lastlogin;
	
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	private Profile profile;

	@OneToMany(fetch=FetchType.EAGER)
	private ArrayList<Bet> bets;


	public User(String iD, String password, boolean isAdmin, Profile p) {
		super();
		this.id = iD;
		this.password = password;
		this.profile = p;
		this.isAdmin = isAdmin;
		this.bets = new ArrayList<Bet>();
		this.registrationdate = new Date();
		this.cash = 9999;  
	}

	public String getID() {
		return id;
	}

	public void setID(String iD) {
		id = iD;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Profile getProfile() {
		return profile;
	}
	
	public void setProfile(Profile p) {
		this.profile = p;
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


	public boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}
	
	public void setLastlogin(Date lastlogin) {
		this.lastlogin = lastlogin;
	}

	public float getCash() {
		return cash;
	}


	public void setCash(float cash) {
		this.cash = cash;
	}

	/**
	 * Registers the bet performed by a user
	 * @param q			the question the bet has been placed on.
	 * @param amount	the amount of money.
	 */
	public void addBet(Bet c) {
		bets.add(c);
	}
	
	public void setBets(ArrayList<Bet> bets) {
		this.bets = bets;
	}
	
	public ArrayList<Bet> getBets() {
		return bets;
	}
	
	public void removeBet(Bet c) {
		int j=0;
		for (int i=0;i<bets.size();i++) {
			if (bets.get(i).equals(c)) {
				j=i;
				break;
			}
		}
		bets.remove(j);
	}
	
	public String statusToString() {
		if(this.isAdmin) {
			return("Admin.");
		}
		else {
			return("User");
		}
	}

}
