package domain;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
public class BetContainer {

	private User cOwner;
	private List<PredictionContainer> cPredictions;
	private Bet cBet;
	
	public BetContainer() {
		this.cPredictions = new ArrayList<PredictionContainer>();
	}
	
	public BetContainer(Bet b) {
		this.cOwner = b.getBettor();
		this.cPredictions = new ArrayList<PredictionContainer>();
		for(Prediction p : b.getPredictions()) {
			cPredictions.add(new PredictionContainer(p));
		}
		this.cBet = b;
	}

	public List<PredictionContainer> getPredictions() {
		return cPredictions;
	}

	public void setPredictions(List<PredictionContainer> cPredictions) {
		this.cPredictions = cPredictions;
	}

	public User getOwner() {
		return cOwner;
	}

	public void setOwner(User cOwner) {
		this.cOwner = cOwner;
	}

	public Bet getBet() {
		return cBet;
	}

	public void setBet(Bet cBet) {
		this.cBet = cBet;
	}
}
