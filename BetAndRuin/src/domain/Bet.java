package domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


@SuppressWarnings("serial")
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Bet implements Serializable{
	
	@Id 
	@XmlID
	@XmlJavaTypeAdapter(IntegerAdapter.class)
	@GeneratedValue
	private Integer betNumber;
	
	@XmlIDREF
	@ManyToOne(fetch=FetchType.EAGER)
	private User bettor;
	
	private BetType type;
	private BetStatus status;
	private float stake;
	private Date placementdate;
	private Date startingdate;
	private Date resolvingdate;
	private float winnings;
	

	@OneToMany(fetch=FetchType.EAGER)
	private List<Prediction> predictions;

	public enum BetStatus{ONGOING,RESOLVED,CANCELLED,PAYMENT_PENDING};
	
	public Bet() {
		this.predictions = new ArrayList<Prediction>();
	}
	
	public Bet(User bettor, float amount, BetType type, List<Prediction> predictions) {
		super();
		this.stake = amount;
		this.bettor = bettor;
		this.type = type;
		this.predictions = predictions;
		this.status = BetStatus.ONGOING;

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

	public BetStatus getStatus() {
		return status;
	}

	public void setStatus(BetStatus status) {
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
	


	/**
	 * This method calculates the winnings earned on the given bet, according to the outcomes of the selected predictions and the corresponding odds.
	 * 
	 * @param bet 	Bet to calculate winnings for.
	 * @return		Total winnings earned from the bet(float).
	 */
	public float calculateBetWinnings() {
		int rightpredictions = 0;
		float winnings = 0;
		List<Prediction> predictions = new ArrayList<Prediction>();
		Queue<PartialSol> queue = new LinkedList<PartialSol>();
		//remove the failed predictions to minimize the computed combinations to calculate total winnings and initialise the queue.
		for(Prediction p : this.getPredictions()) {
			if(p.getOutcome() == true) {
				rightpredictions++;
				Event ev = p.getQuestion().getEvent();
				queue.add(new PartialSol(rightpredictions,ev,p.getOdds()));
				predictions.add(p);
			}
		}
		//Single bet case
		if(this.getType().equals(BetType.SINGLE)) {
			if(this.getPredictions().get(0).getOutcome() == true) {
				winnings = predictions.get(0).getOdds()*this.getStake(); 
			}
		}
		//Normal combined bet case(DOUBLE,TREBLE,FOURFOLD...)
		else if(this.getType().getBetCount() == 1) {
			if(predictions.size() >= this.getType().predictionCount()) {
				List<PredictionContainer> pContainers = new ArrayList<PredictionContainer>();
				for(Prediction p : predictions) {
					pContainers.add(new PredictionContainer(p));
				}
				winnings = calculateCombinedWinnings(this.getType().predictionCount(),this.getStake(), pContainers);	
			}
		}
		else {
			List<PredictionContainer> pContainers = new ArrayList<PredictionContainer>();
			for(Prediction p : predictions) {
				pContainers.add(new PredictionContainer(p));
			}
			winnings = calculateFullCoverWinnings(this.getType().predictionCount(),this.getStake(), pContainers);
			/*
			PartialSol p = queue.remove();
			while(p.getList().size() < predictions.size()) {
				List<Integer> list = p.getList();
				for(i=(list.get(list.size()-1)+1); i<=rightpredictions;i++) {
					Prediction pred = predictions.get(i-1);
					Event ev = pred.getQuestion().getEvent();

					PartialSol nextParSol = new PartialSol(p, i,ev, predictions.get(i-1).getOdds());
					//System.out.println("full cover "+nextpair.toString());
					queue.add(nextParSol);
					winnings += nextParSol.getOdds()*bet.getStake();
				}
				p = queue.remove();
			}
			 */
		}
		System.out.println("winnings: " + winnings);
		this.setWinnings(winnings);
		return winnings;
	}

	/**
	 * Auxiliary class for calculateBetWinnings
	 */
	public class PartialSol{
		private List<Integer> list;
		private Set<Event> events;
		private float odds;

		public PartialSol( int i, Event ev, float odds) {
			list = new ArrayList<Integer>();
			events = new HashSet<Event>();
			list.add(i);
			events.add(ev);
			this.odds = odds;
		}

		public PartialSol(PartialSol p, int i,Event ev, float odds) {
			list = new ArrayList<Integer>(p.getList());
			list.add(i);
			events = new HashSet<Event>(p.getEvents());
			events.add(ev);
			this.odds = p.getOdds()*odds;
		}

		public List<Integer> getList(){
			return list;
		}

		public Set<Event> getEvents(){
			return events;
		}

		public float getOdds() {
			return odds;
		}

		public String toString() {
			String s = "";
			for(Integer i : list) {
				s = s + String.valueOf(i);
			}
			return s;
		}
	}

	/**
	 * This method calculates the winnings for a combined bet
	 * 
	 * @param size			Combined bet size(Double: 2, Treble:3 ...)
	 * @param stake			Amount placed on the bet(float)
	 * @param predictions   Collection of predictions made when placing the bet(List<Prediction>)
	 * @return				winnings earned from the bet(float)
	 */
	public static float calculateCombinedWinnings(int size, float stake, List<PredictionContainer> predictions) { 
		int[] solution = new int[size];
		float winnings = 0;
		Set<Event> events = new HashSet<Event>();
		return calculateCombinedWinningsWorker(1,0,solution,(float)1,winnings,stake,predictions,events);
	}

	/**
	 * Worker method for calculateCombinedWinnings
	 * 
	 * @param k						
	 * @param pos			
	 * @param solution
	 * @param odds			
	 * @param winnings
	 * @param stake
	 * @param predictions
	 * @param events
	 * @return
	 */
	private static float calculateCombinedWinningsWorker(int k,int pos, int[] solution, float odds, float winnings,float stake, List<PredictionContainer> predictions, Set<Event> events) {
		if(pos == solution.length) {
			winnings += odds*stake;
		}
		else {
			for(int i = k; i<=(predictions.size()-(solution.length-(pos+1)));i++) {
				PredictionContainer pc = predictions.get(i-1);
				Prediction pred = pc.getPrediction();
				Event ev = pc.getEvent();
				if(!events.contains(ev)) {
					solution[pos]=i;
					events.add(ev);
					winnings = calculateCombinedWinningsWorker(i+1,pos+1,solution,odds*pred.getOdds(),winnings,stake,predictions,events);
					events.remove(ev);
				}
			}	
		}
		return winnings;
	}

	/**
	 * This method calculates the winnings for a full cover bet
	 * 
	 * @param size			Combined bet size(Double: 2, Treble:3 ...)
	 * @param stake			Amount placed on the bet(float)
	 * @param predictions   Collection of predictions made when placing the bet(List<Prediction>)
	 * @return				winnings earned from the bet(float)
	 */
	public static float calculateFullCoverWinnings(int size, float stake, List<PredictionContainer> predictions) {
		Map<Event,ArrayList<Prediction>> map = new HashMap<Event, ArrayList<Prediction>>();
		for(PredictionContainer pc: predictions) {
			Event ev = pc.getEvent();
			if(map.containsKey(ev)) {
				map.get(ev).add(pc.getPrediction());
			}
			else {
				ArrayList<Prediction> list = new ArrayList<Prediction>(); 
				list.add(pc.getPrediction());
				map.put(ev, list); 
			}
		}		
		int[] solution = new int[size];
		float winnings = 0;
		Set<Event> events = new HashSet<Event>();
		return calculateFullCoverWinningsWorker(1,0,map,solution,(float)1,winnings,stake,predictions,events);
	}

	/**
	 * Worker method for calculateFullCoverWinnings
	 * 
	 * @param k
	 * @param pos
	 * @param map
	 * @param solution
	 * @param odds
	 * @param winnings
	 * @param stake
	 * @param predictions
	 * @param events
	 * @return
	 */
	private static float calculateFullCoverWinningsWorker(int k,int pos,  Map<Event, ArrayList<Prediction>> map  , int[] solution, float odds, float winnings,float stake, List<PredictionContainer> predictions, Set<Event> events) {
		if(pos == solution.length) {
			winnings += odds*stake;
		}
		else {
			for(int i = k; i<=predictions.size();i++) {
				PredictionContainer pc = predictions.get(i-1);
				Prediction pred = pc.getPrediction();
				Event ev = pc.getEvent();
				if(!events.contains(ev)) {
					solution[pos]=i;
					events.add(ev);	
					winnings = calculateFullCoverWinningsWorker(i+1,pos+1, map ,solution,odds*pred.getOdds(),winnings,stake,predictions,events);
					events.remove(ev);
				}	
			}	
		}
		return winnings;
	}

	
}
