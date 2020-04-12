package domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@SuppressWarnings("serial")
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Prediction implements Serializable{

	@Id 
	@XmlJavaTypeAdapter(IntegerAdapter.class)
	@GeneratedValue
	private Integer predictionNumber;
	@XmlIDREF
	private Question question;
	private String answer;
	private float odds;
	private Boolean outcome;

	public Prediction(String answer, float odds) { 
		this.answer = answer;
		this.odds = odds;
		this.outcome = null;
	}
	
	public Prediction(Question q, String answer, float odds) {
		this.question = q;
		this.answer = answer;
		this.odds = odds;
		this.outcome = null;
	}
	
	public Integer getPredictionNumber() {
		return predictionNumber;
	}
	 
	public void setPredictionNumber(Integer predictionNumber) {
		this.predictionNumber = predictionNumber;
	}
	
	public Question getQuestion() {
		return question;
	}
	
	public void setQuestion(Question question) {
		this.question = question;
	}
	
	public String getAnswer() {
		return answer;
	}
	
	public void setAnswer(String answer) {
		this.answer = answer;
	}	
	
	public float getOdds() {
		return odds;
	}

	public void setOdds(float odds) {
		this.odds = odds;
	}

	public Boolean getOutcome() {
		return outcome;
	}

	public void setOutcome(Boolean outcome) {
		this.outcome = outcome;
	}
}
