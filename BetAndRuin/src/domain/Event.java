package domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@SuppressWarnings("serial")
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Event implements Serializable{
	
	@XmlID
	@XmlJavaTypeAdapter(IntegerAdapter.class)
	@Id @GeneratedValue
	private Integer eventNumber;
	private String description; 
	@Temporal(TemporalType.TIMESTAMP)
	private Date eventdate;
	@Temporal(TemporalType.TIMESTAMP)
	private Date endingdate;
	private Sport sport;
	
	@XmlIDREF
	@ManyToOne(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
	private Competition competition;

	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
	private List<Question> questions;

	public Event() {
		super();
		this.questions = new ArrayList<Question>();
	}

	public Event(Integer eventNumber, String description,Date eventdate, Date endingdate , Sport sport) {
		this.eventNumber = eventNumber;
		this.description = description;
		this.eventdate = eventdate;
		this.endingdate = endingdate;
		this.sport = sport;
		this.questions = new ArrayList<Question>();
	}
	
	public Event( String description,Date eventDate, Date endingdate ,Sport sport) {
		this.description = description;
		this.eventdate=eventDate;
		this.endingdate = endingdate;
		this.sport = sport;
		this.questions = new ArrayList<Question>();
	}

	public Integer getEventNumber() {
		return eventNumber;
	}

	public void setEventNumber(Integer eventNumber) {
		this.eventNumber = eventNumber;
	}

	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description=description;
	}

	public Date getEventDate() {
		return eventdate;
	}

	public void setEventDate(Date eventDate) {
		this.eventdate = eventDate;
	}
	
	public String toString(){
		return eventNumber+";"+description;
	}
	
	public Competition getCompetition() {
		return competition;
	}

	public void setCompetition(Competition competition) {
		this.competition = competition;
	}

	public Date getEndingdate() {
		return endingdate;
	}

	public void setEndingdate(Date endingdate) {
		this.endingdate = endingdate;
	}

	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}
	
	/**
	 * This method creates a bet with a question, minimum bet ammount and percentual profit
	 * 
	 * @param question to be added to the event
	 * @param betMinimum of that question
	 * @return Bet
	 */
	public Question addQuestion(String question, float betMinimum)  {
        Question q=new Question(question,betMinimum, this);
        if(questions == null) {
        	questions = new ArrayList<Question>();
        }
        questions.add(q);
        return q;
	}
	
	
	/**
	 * This method creates a bet with a question, minimum bet ammount and percentual profit
	 * 
	 * @param question to be added to the event
	 * @param betMinimum of that question
	 * @return Bet
	 */
	public Question addQuestion(String question, float betMinimum, List<Prediction> predictions)  {
        Question q=new Question(question,betMinimum, this, predictions);
        if(questions == null) {
        	questions = new ArrayList<Question>();
        }
        questions.add(q);
        return q;
	}

	
	/**
	 * This method checks if the question already exists for that event
	 * 
	 * @param question that needs to be checked if there exists
	 * @return true if the question exists and false in other case
	 */
	public boolean DoesQuestionExists(String question)  {	
		for (Question q:this.getQuestions()){
			if (q.getQuestion().compareTo(question)==0)
				return true;
		}
		return false;
	}
		

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + eventNumber;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Event other = (Event) obj;
		if (eventNumber != other.eventNumber)
			return false;
		return true;
	}
	
	public List<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(Vector<Question> questions) {
		this.questions = questions;
	}

	public Sport getSport() {
		return sport;
	}

	public void setSport(Sport sport) {
		this.sport = sport;
	}

	
	

}
