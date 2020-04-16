package domain;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


@SuppressWarnings("serial")
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Question implements Serializable {
	
	@Id 
	@XmlJavaTypeAdapter(IntegerAdapter.class)
	@GeneratedValue
	private Integer questionNumber;
	private String question; 
	private float betMinimum;
	private int result;  
	@OneToMany(cascade = CascadeType.PERSIST ,fetch = FetchType.EAGER)
	private List<Prediction> predictions;
	@XmlIDREF
	@ManyToOne(fetch = FetchType.EAGER)
	private Event event;
	
	
	
	
	public Question(){
		super();
	}
	
	public Question( String query, float betMinimum, Event event) {
		super();
		this.question = query;
		this.betMinimum=betMinimum;
		this.event = event;
	}
	
	public Question(Integer queryNumber, String query, float betMinimum, Event event) {
		super();
		this.questionNumber = queryNumber;
		this.question = query;
		this.betMinimum=betMinimum;
		this.event = event;
	}
	

	public Question(String query, float betMinimum,  Event event, List<Prediction> predictions) {
		super();
		this.question = query;
		this.betMinimum=betMinimum;

		this.event = event;
		for(Prediction p : predictions) {
			p.setQuestion(this);
		}
		this.predictions = predictions;
	}

	/**
	 * Get the  number of the question
	 * 
	 * @return the question number
	 */
	public Integer getQuestionNumber() {
		return questionNumber;
	}

	/**
	 * Set the bet number to a question
	 * 
	 * @param questionNumber to be setted
	 */
	public void setQuestionNumber(Integer questionNumber) {
		this.questionNumber = questionNumber;
	}


	/**
	 * Get the question description of the bet
	 * 
	 * @return the bet question
	 */

	public String getQuestion() {
		return question;
	}


	/**
	 * Set the question description of the bet
	 * 
	 * @param question to be setted
	 */	
	public void setQuestion(String question) {
		this.question = question;
	}



	/**
	 * Get the minimun ammount of the bet
	 * 
	 * @return the minimum bet ammount
	 */
	
	public float getBetMinimum() {
		return betMinimum;
	}


	/**
	 * Get the minimun ammount of the bet
	 * 
	 * @param  betMinimum minimum bet ammount to be setted
	 */

	public void setBetMinimum(float betMinimum) {
		this.betMinimum = betMinimum;
	}



	/**
	 * Get the result of the  query
	 * 
	 * @return the the query result
	 */
	public int getResult() {
		return result;
	}



	/**
	 * Get the result of the  query
	 * 
	 * @param result of the query to be setted
	 */
	
	public void setResult(int result) {
		this.result = result;
	}



	/**
	 * Get the event associated to the bet
	 * 
	 * @return the associated event
	 */
	public Event getEvent() {
		return event;
	}



	/**
	 * Set the event associated to the bet
	 * 
	 * @param event to associate to the bet
	 */
	public void setEvent(Event event) {
		this.event = event;
	}


	/**
	 * Get the answers associated to the question
	 * 
	 * @return the associated answers
	 */
	
	public List<Prediction> getPredictions() {
		return this.predictions;
	}

	/**
	 * Finds and retrieves the prediction corresponding to the input answer
	 * @param ans  answer to search for
	 * @return	   Prediction object if matches answer, null else;
	 */
	public Prediction getPredictionByAnswer(String ans) {
		Prediction prediction = null;
		for(Prediction p : predictions) {
			if(p.getAnswer().equals(ans)) {
				prediction = p;
				break;
			}
		}
		return prediction;
	}
	/**
	 * Set the answers associated to the question
	 * 
	 * @param answers to associate to the question
	 */
	public void setPredictions(List<Prediction> answers) {
		this.predictions =answers;
	}


	public void addPrediction(String answer, Float odd) {
		if(predictions == null) {
			predictions = new ArrayList<Prediction>();
		}
		predictions.add(new Prediction(this,answer,odd));
	}
	
	public String toString(){
		return questionNumber+";"+question+";"+Float.toString(betMinimum);
	}
	
}