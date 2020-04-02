package domain;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@SuppressWarnings("serial")
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Bet {
	
	@Id 
	@XmlJavaTypeAdapter(IntegerAdapter.class)
	@GeneratedValue
	private Integer betNumber;
	
	public float getStake() {
		return stake;
	}

	public void setStake(float stake) {
		this.stake = stake;
	}

	@XmlIDREF
	@ManyToOne
	private User bettor;
	private BetType type;
	private Status status;
	private float stake;
	private Date placementdate;
	private Date resolvingdate;
	
	@OneToMany(fetch=FetchType.EAGER)

	private List<Prediction> predictions;

	private enum Status{ONGOING,CANCELLED,PAYMENT_PENDING};
	
	public Bet(User bettor, float amount, BetType type, List<Prediction> predictions) {
		super();
		this.stake = amount;
		this.bettor = bettor;
		this.type = type;
		this.predictions = predictions;
		this.status = Status.ONGOING;
		this.placementdate = new Date();
		this.setResolvingdate(latestPredictionDate(predictions));
	}

	public void setBettor(User user) {
		this.bettor = user;
	}

	public float getAmount() {
		return stake;
	}

	public void setAmount(float amount) {
		this.stake = amount;
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
