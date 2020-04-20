package domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@SuppressWarnings("serial")
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Bet implements Serializable{
	
	@Id 
	@XmlJavaTypeAdapter(IntegerAdapter.class)
	@GeneratedValue
	private Integer betNumber;
	


	@XmlIDREF
	@ManyToOne(fetch=FetchType.LAZY)
	private User bettor;
	private BetType type;
	private Status status;
	private float stake;
	private Date placementdate;
	private Date startingdate;
	private Date resolvingdate;
	private float winnings;
	
	@OneToMany(fetch=FetchType.EAGER)
	private List<Prediction> predictions;

	public enum Status{ONGOING,RESOLVED,CANCELLED,PAYMENT_PENDING};
	
	public Bet(User bettor, float amount, BetType type, List<Prediction> predictions) {
		super();
		this.stake = amount;
		this.bettor = bettor;
		this.type = type;
		this.predictions = predictions;
		this.status = Status.ONGOING;
		
		//stating date is set to the starting date of the closest even in time
		if(predictions.size() > 0) {
			this.startingdate = predictions.get(0).getQuestion().getEvent().getEventDate();
			for(Prediction p : predictions) {
				Event e = p.getQuestion().getEvent();
				if(e.getEventDate().compareTo(startingdate) < 0) {
					startingdate = e.getEventDate();
				}
			}
		}
		this.placementdate = new Date();
		this.setResolvingdate(latestPredictionDate(predictions));
	}

	public void setBettor(User user) {
		this.bettor = user;
	}

	public float getStake() {
		return stake;
	}

	public void setStake(float stake) {
		this.stake = stake;
	}

	public Integer getBetNumber() {
		return betNumber;
	}

	public void setBetNumber(Integer betNumber) {
		this.betNumber = betNumber;
	}

	public BetType getType() {
		return type;
	}

	public void setType(BetType type) {
		this.type = type;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Date getPlacementdate() {
		return placementdate;
	}

	public void setPlacementdate(Date placementdate) {
		this.placementdate = placementdate;
	}
	
	public Date getStartingDate() {
		return startingdate;
	}
 
	public void setStartingDate(Date startingdate) {
		this.startingdate = startingdate;
	}

	public Date getResolvingdate() {
		return resolvingdate;
	}
	
	public String StatustoString() {
		return status.name();
	}

	public void setResolvingdate(Date resolvingdate) {
		this.resolvingdate = resolvingdate;
	}

	public List<Prediction> getPredictions() {
		return predictions;
	}

	public void setPredictions(List<Prediction> predictions) {
		this.predictions = predictions;
	}

	public User getBettor() {
		return bettor;
	}
	
	public float getWinnings() {
		return winnings;
	}
	
	public void setWinnings(float winnings) {
		this.winnings = winnings;
	}
	
	/**
	 * Used to set the resolving date of the bet as the latest date out of all the events that the user has included a prediction of in the bet.
	 * @param predictions	Predictions made by the user.
	 */
	private Date latestPredictionDate(List<Prediction> predictions) {
		Date highest = predictions.get(0).getQuestion().getEvent().getEndingdate();
		for(Prediction p : predictions) {
			Date predictionresolution = p.getQuestion().getEvent().getEndingdate();
			if(predictionresolution.compareTo(highest) > 0)
				highest = predictionresolution;
		}
		return highest;	
	}
}
