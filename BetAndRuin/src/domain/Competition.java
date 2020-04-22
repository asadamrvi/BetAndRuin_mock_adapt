package domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@SuppressWarnings("serial")
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Competition implements Serializable{

	@XmlID
	@XmlJavaTypeAdapter(IntegerAdapter.class)
	@Id @GeneratedValue
	private Integer competitionnumber;
	private String name;
	private Country country;
	private Sport sport;
	private Date startingdate;
	private Date endingdate;
	
	@OneToMany(fetch=FetchType.EAGER, cascade = CascadeType.PERSIST)
	private ArrayList<Event> events;


	public Competition(String name, Country country, Sport sport ,Date startingdate, Date endinggdate,
			ArrayList<Event> events) {
		super();
		this.name = name;
		this.country = country;
		this.sport = sport;
		this.startingdate = startingdate;
		this.endingdate = endinggdate;
		this.events = events;
	}

	public Competition(String name, Country country, Sport sport ,Date startingdate, Date endinggdate) {
		super();
		this.name = name;
		this.country = country;
		this.sport = sport;
		this.startingdate = startingdate;
		this.endingdate = endinggdate;
		this.events = new ArrayList<Event>();
	}

	public int getCompetitionNumber() {
		return competitionnumber;
	}
	
	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public Country getCountry() {
		return country;
	}


	public void setCountry(Country country) {
		this.country = country;
	}
	
	public Sport getSport() {
		return sport;
	}


	public void setSport(Sport sport) {
		this.sport = sport;
	}



	public Date getStartingdate() {
		return startingdate;
	}


	public void setStartingdate(Date startingdate) {
		this.startingdate = startingdate;
	}


	public Date getEndinggdate() {
		return endingdate;
	}


	public void setEndinggdate(Date endinggdate) {
		this.endingdate = endinggdate;
	}


	public ArrayList<Event> getEvents() {
		return events;
	}


	public void setEvents(ArrayList<Event> events) {
		this.events = events;
	}
	
	public void addEvent(Event ev) {
		this.events.add(ev);
		ev.setCompetition(this);
	}
	
	public void removeEvent(Event ev) {
		this.events.remove(ev);
	}
}
