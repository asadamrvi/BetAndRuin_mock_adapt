package businessLogic;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.Vector;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.net.ssl.SSLEngineResult.Status;
import javax.swing.Timer;

import configuration.ConfigXML;
import configuration.UtilDate;
import dataAccess.DataAccess;
import dataAccess.DataAccessImplementation;
import domain.Question;
import domain.Sport;
import domain.User;
import domain.Feedback.FeedbackType;
import domain.Prediction;
import domain.Bet;
import domain.BetType;
import domain.Competition;
import domain.Event;
import domain.Feedback;
import domain.Country;
import domain.Profile;
import exceptions.EventFinished;
import exceptions.InsufficientCash;
import exceptions.NoAnswers;
import exceptions.QuestionAlreadyExist;
import exceptions.QuestionNotFound;
import exceptions.invalidID;
import exceptions.invalidPW;
import gui.Panels.HomePanel;
import gui.MainGUI;
import gui.Panels.BrowsePanel.Pair;

/**
 * It implements the business logic as a web service.
 */
@WebService(endpointInterface = "businessLogic.BLFacade")
public class BLFacadeImplementation  implements BLFacade {

	private User loggeduser;
	private DataAccess dbManager;
	
	public BLFacadeImplementation()  {	
		System.out.println("Creating BLFacadeImplementation instance");
		ConfigXML c=ConfigXML.getInstance();
		
		if (c.getDataBaseOpenMode().equals("initialize")) {
			dbManager=new DataAccessImplementation(c.getDataBaseOpenMode().equals("initialize"));
			dbManager.initializeDB();
		}		
	}


	/** 
	 * This method creates a question for an event, with a question text and the minimum bet
	 * 
	 * @param event to which question is added
	 * @param question text of the question
	 * @param betMinimum minimum quantity of the bet
	 * @return the created question, or null, or an exception
	 * @throws EventFinished if current data is after data of the event
	 * @throws QuestionAlreadyExist if the same question already exists for the event
	 */
	@WebMethod
	public Question createQuestion(Event event, String question, float betMinimum,  List<Prediction> predictions) throws EventFinished, QuestionAlreadyExist{

		//The minimum bed must be greater than 0
		if(new Date().compareTo(event.getEventDate())>0) {
			throw new EventFinished(ResourceBundle.getBundle("Etiquetas").getString("ErrorEventHasFinished"));
		}	
		try {
			Question qry=dbManager.createQuestion(event,question,betMinimum, predictions);	
			return qry;
		}
		catch(QuestionAlreadyExist q) {
			throw new QuestionAlreadyExist(q.getMessage());
		}

	};

	/**
	 * This method invokes the data access to retrieve the events of a given date 
	 * 
	 * @param date in which events are retrieved
	 * @return collection of events
	 */
	@WebMethod	
	public Vector<Event> getEvents(Date date)  {
		Vector<Event>  events=dbManager.getEvents(date);
		return events;
	}

	/**
	 * This method invokes the data access to retrieve the events of a given date and sport
	 * 
	 * @param date in which events are retrieved
	 * @param sport to look for
	 * @return collection of events
	 */
	@WebMethod	
	public Vector<Event> getEvents(Date date, Sport sport)  {
		Vector<Event>  events=dbManager.getEvents(date, sport);
		return events;
	}

	/**
	 * This method invokes the data access to retrieve events scheduled for dates between the
	 * two input dates.
	 * 
	 * @param date1  lower bound date
	 * @param date2  upper bound date
	 * @return  collection of events
	 */
	public Vector<Event> getEventsBetweenDates(Date date1,Date date2){
		Vector<Event>  events=dbManager.getEventsBetweenDates(date1, date2);
		return events;
	} 

	/**
	 * This method invokes the data access to retrieve the dates a month for which there are events
	 * 
	 * @param date of the month for which days with events want to be retrieved 
	 * @return collection of dates
	 */
	@WebMethod public Vector<Date> getEventsMonth(Date date) {
		Vector<Date>  dates=dbManager.getEventsMonth(date);
		return dates;
	}

	/**
	 * This method invokes the data access to retrieve the dates a month for which there are events for the given competition
	 * 
	 * @param date of the month for which days with events want to be retrieved 
	 * @param competition to look for
	 * @return collection of dates
	 */
	@WebMethod public Vector<Date> getEventsMonth(Date date, Competition competition) {
		Vector<Date>  dates = null;
		if(competition != null) {
			dates=dbManager.getEventsMonth(date, competition);
		}
		else {
			dates = new Vector<Date>();
		}
		return dates;
	}

	/**
	 * This method invokes the data access manager to retrieve the events that are currently live, that is, that the current time
	 * is between the starting and ending date of said events.
	 * 
	 * @return collection of events
	 */
	@WebMethod public Vector<Event> getLiveEvents(){
		Vector<Event> events=dbManager.getLiveEvents();
		return events;
	}


	@WebMethod public Vector<Competition> getCompetitions(Sport sport){
		Vector<Competition>  events=dbManager.getCompetitions(sport);
		return events;
	}

	/**
	 * This method registers a new user.
	 * 
	 * @param iD				ID of the new user.
	 * @param password			password of the new user.
	 * @param name				name of the new user.
	 * @param surname			surname of the new user.
	 * @param email				email of the new user.
	 * @param isAdmin			whether this user has admin. privileges or not.
	 * 
	 * @throws invalidID		exception thrown when there is a pre existing user with this ID in the database.
	 */
	@WebMethod
	public void registerUser(String iD, String password, String name, String surname, String email, String address, String phone, 
			Country nat, String city, Date birthDate, String pic, boolean isAdmin) throws invalidID{
		try {
			dbManager.registerUser(iD, password, name, surname, email, address, phone, nat, city, birthDate, pic, isAdmin);
		}
		catch (invalidID i) {
			throw new invalidID(i.getMessage());
		}
	}

	/**
	 * This methods checks the validity of the credentials (id / password) inputed upon login.
	 * @param ID			ID of the presumed user.
	 * @param pw			password of the presumed user.
	 * 
	 * @return				boolean indicating privilege level of the user( true:Admin , false:Regular user).
	 * @throws invalidID	exception thrown when no user entity with the input ID exists in the database.
	 */
	@WebMethod
	public boolean checkCredentials(String ID, String password) throws invalidID, invalidPW{
		try {
			User u = dbManager.retrieveUser(ID, password);
			loggeduser = u;
			return u.isAdmin();
		}	
		catch (invalidID e) {
			throw new invalidID(e.getMessage());
		}
		catch (invalidPW e) {
			throw new invalidPW(e.getMessage());
		}
	}

	/**
	 * 
	 */
	@Override
	public List<User> searchByCriteria(String searchtext, String filter, boolean casesensitive, int match) {
		List<User> searchResult = dbManager.retrieveUsersByCriteria(searchtext, filter, casesensitive,match);
		return searchResult;
	}

	/**
	 * 
	 */
	@Override
	public void removeUser(String ID) {
		DataAccess dbManager = new DataAccessImplementation();
		dbManager.removeUser(ID);
		;
	}

	/**
	 * 
	 */
	public void updateUserInfo(String key, String iD, String name, String surname, String email,Country nat,String city, String addr, 
			String phn,  Date birthdt, boolean isAdmin) throws invalidID{
		try {	
			dbManager.updateUserInfo(key, iD,name,surname,email,addr,phn,nat,city,birthdt,isAdmin);
		}
		catch(invalidID i) {
			throw new invalidID();
		}

	}

	public User getLoggeduser() {
		return loggeduser;
	}


	/**
	 * This method invokes the data access to initialize the database with some events and questions.
	 * It is invoked only when the option "initialize" is declared in the tag dataBaseOpenMode of resources/config.xml file
	 */	
	@WebMethod	
	public void initializeBD(){
		dbManager.initializeDB();
	}


	public void placeBets(float stake, float totalprice, BetType type, List<Prediction> predictions) throws InsufficientCash{
		if(totalprice > loggeduser.getCash()) {
			throw new InsufficientCash();
		}
		else {
			dbManager.recordBets(loggeduser, stake, totalprice,type, predictions);
			loggeduser.setCash(loggeduser.getCash() - totalprice);
		}

	}

	/**
	 * This method checks if a user is currently logged in
	 * @return    boolean(true: if a user is logged in, false: else)
	 */
	public boolean isLoggedIn() {
		return (loggeduser != null);
	}

	/**
	 * Retrieves the currently logged users ID
	 * @return ID field value of the logged user
	 */
	public String getUserID() {
		return loggeduser.getID();
	}

	/**
	 * Retrieves the bets the currently logged user has in place
	 * @return		List<Bet> user's bets
	 */
	public List<Bet> retrieveBets(){
		if(isLoggedIn()) {
			return loggeduser.getBets();
		}
		else {
			return null;
		}
	}

	/**
	 * Logs the current user out by setting the attributes related to the current session to null
	 */
	public void logOut() {
		loggeduser = null;
		//sessionstart = null;
	}

	/**
	 * Retrieves the profile of the currently logged user
	 * @return	Profile object containing information about the user
	 */
	public Profile getProfile() {
		if(isLoggedIn()) {
			return loggeduser.getProfile();
		}
		else {
			return null;	
		}
	}

	/**
	 * Indicates if the logged user has an admin status.
	 * @return	boolean(true: if loggeduser is an admin, false:else)
	 */
	public boolean isAdmin() {
		return loggeduser.isAdmin();
	}

	/**
	 * Returns cash currently stored on the user account.
	 * @return  current cash amount.
	 */
	public float getCash() {
		return loggeduser.getCash();
	}

	/**
	 * Adds introduced amount the cash stored on the user's account
	 * @param amount	amount of money to add(float)
	 * @return	cash on the account after the addition
	 */
	public float addCash(float amount) {
		float newcash = dbManager.addCash(loggeduser.getID(), amount);
		return newcash;
	}

	/**
	 * This method calls to the data access manager to store a new feedback object.
	 */
	public void submitFeedback(FeedbackType fbtype, String email, String name, String summary, String details, File file) {
		dbManager.storeFeedback(fbtype, email, name, summary, details, file);
	}

	/**
	 * This method invokes the data access manager to retrieve the feedback stored in the database.
	 * @return	Feedback that has been sent and stored previously.
	 */
	public Vector<Feedback> getFeedback(){
		Vector<Feedback> fb = dbManager.retrieveFeedback();
		return fb;
	}

	public List<Prediction> getQuestionPredictions(int questionId) throws QuestionNotFound, NoAnswers {

		try {
			List<Prediction> pred = dbManager.getQuestionPredictions(questionId);
			return pred;
		} catch (NoAnswers e) {
			throw new NoAnswers(e.getMessage());
		}
	}


	@Override
	public boolean Enable_or_not(Bet b, int Hours) {
		Date placement_date=b.getPlacementdate();
		Date finish_date=b.getResolvingdate();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(placement_date);
		calendar.add(Calendar.HOUR_OF_DAY, Hours);
		Date last=calendar.getTime();
		int high_low=last.compareTo(finish_date);
		if (high_low<0) {
			return true;
		}
		else

			return false;
	}


	@Override
	public Object[][] getDAta(Bet b, ArrayList<Bet> bets) {

		if (b==null) {
			Object[][] data = new Object[bets.size()][3];
			for (int i = 0; i < bets.size(); i++) {
				data[i][0] = bets.get(i).getStatus();
				data[i][1] = bets.get(i).getStake();
				data[i][2] = bets.get(i).getPlacementdate();
			}
			return data;
		}
		else {
			List<Prediction> predictions=b.getPredictions();
			Object[][] data = new Object[predictions.size()][5];
			for (int i = 0; i < predictions.size(); i++) { 
				data[i][0] = predictions.get(i).getPredictionNumber();
				data[i][1] = predictions.get(i).getQuestion().getQuestion();
				data[i][2] = predictions.get(i).getAnswer();
				data[i][3] = predictions.get(i).getOdds();
				data[i][4] = predictions.get(i).getOutcome();
			}
			return data;
		}
	}


	@Override
	public ArrayList<Bet> getBets(User u) {
		ArrayList<Bet> bets=dbManager.getBets(u);
		return (bets);
	}


	@Override
	public void remove_bet(User bettor, Bet bet) {
		dbManager.removeBet(bettor, bet);
	}


	@Override
	public void updatebets(User bettor, Bet bet, float amount) {
		dbManager.updatebet(bettor, bet,amount);
	}

	/**
	 * This method resolves the outcomes of the questions the event of finished at the given date. The outcomes of the possible 
	 * predictions a question has are decided by a generated random number. The odds affect the likelihood of the number to be in the
	 * range of said outcome.
	 * 
	 * @param date
	 */
	public void resolveQuestions() {
		Calendar cl  = Calendar.getInstance();
		dbManager.resolveQuestions(cl.getTime());
	}
	
	/**
	 * This method invokes the data access to retrieve the bets scheduled to be resolved in the exact date that the method is called,
	 * computes the winnings earner on each bet and updates the bettor's cash according to them.
	 */
	public void resolveBets() {
		List<Bet> bets = dbManager.getBetsByResolutionDate(new Date()); 
		Map<User,Float> winningsMap = new HashMap<User, Float>();

		
		//System.out.println("Bets found: " + bets.size() );
		 long starttime = System.nanoTime();
		for(Bet bet : bets) {
			Float currentwinnings = (float)0;
			float winnings = calculateBetWinnings(bet);
			User bettor = bet.getBettor();
			bet.setStatus(Bet.Status.RESOLVED);
			if(winnings > 0) {
				if(bettor.getID().equals(loggeduser.getID())) {
					loggeduser.setCash(getCash() + winnings);
				}	
				if(winningsMap.containsKey(bettor)) {
					currentwinnings = winningsMap.get(bettor);
				}
				winningsMap.put(bettor, currentwinnings+winnings);
			}
		}
		for(User u : winningsMap.keySet()) {
			dbManager.updateUserCash(u, winningsMap.get(u));
		}
		
		 long endtime = System.nanoTime();
		 long dif = endtime-starttime;
		 System.out.println("Execution in miliseconds: " + dif/1000000);
	}
	
	/**
	 * This method calculates the winnings earned on the given bet, according to the outcomes of the selected predictions and the corresponding odds.
	 * 
	 * @param bet 	Bet to calculate winnings for.
	 * @return		Total winnings earned from the bet(float).
	 */
	public float calculateBetWinnings(Bet bet) {
		int i;
		int rightpredictions = 0;
		float winnings = 0;
		List<Prediction> predictions = new ArrayList<Prediction>();
		Queue<PartialSol> queue = new LinkedList<PartialSol>();
		//remove the failed predictions to minimize the computed combinations to calculate total winnings and initialise the queue.
		for(Prediction p : bet.getPredictions()) {
			if(p.getOutcome() == true) {
				rightpredictions++;
				Event ev = p.getQuestion().getEvent();
				queue.add(new PartialSol(rightpredictions,ev,p.getOdds()));
				predictions.add(p);
			}
		}
		//Single bet case
		if(bet.getType().equals(BetType.SINGLE)) {
			if(bet.getPredictions().get(0).getOutcome() == true) {
				winnings = predictions.get(0).getOdds()*bet.getStake(); 
			}
		}
		//Normal combined bet case(DOUBLE,TREBLE,FOURFOLD...)
		else if(bet.getType().getBetCount() == 1) {
			if(predictions.size() >= bet.getType().predictionCount()) {
				winnings = calculateCombinedWinnings(bet.getType().predictionCount(),bet.getStake(), predictions);	
			}
		}
		else {
			winnings = calculateFullCoverWinnings(bet.getType().predictionCount(),bet.getStake(), predictions);
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
	
	public float calculateCombinedWinnings(int size, float stake, List<Prediction> predictions) { 
		int[] solution = new int[size];
		float winnings = 0;
		Set<Event> events = new HashSet<Event>();
		return calculateCombinedWinningsWorker(1,0,solution,(float)1,winnings,stake,predictions,events);
	}
	
	private float calculateCombinedWinningsWorker(int k,int pos, int[] solution, float odds, float winnings,float stake, List<Prediction> predictions, Set<Event> events) {
		if(pos == solution.length) {
			winnings += odds*stake;
		}
		else {
			for(int i = k; i<=(predictions.size()-(solution.length-(pos+1)));i++) {
				Prediction pred = predictions.get(i-1);
				Event ev = pred.getQuestion().getEvent();
				if(!events.contains(ev)) {
					solution[pos]=i;
					events.add(ev);
					winnings = calculateCombinedWinningsWorker(i+1,pos+1,solution,odds*predictions.get(i-1).getOdds(),winnings,stake,predictions,events);
					events.remove(ev);
				}
				
			}	
		}
		return winnings;
	}
	
	
	public float calculateFullCoverWinnings(int size, float stake, List<Prediction> predictions) {
		Map<Event,List<Prediction>> map = new HashMap<Event, List<Prediction>>();
		for(Prediction p: predictions) {
			Event ev = p.getQuestion().getEvent();
			if(map.containsKey(ev)) {
				map.get(ev).add(p);
			}
			else {
				List<Prediction> list = new ArrayList<Prediction>();
				list.add(p);
				map.put(ev, list);
			}
		}
		int[] arr = computeMultiBets(map);		
		int[] solution = new int[size];
		float winnings = 0;
		Set<Event> events = new HashSet<Event>();
		return calculateFullCoverWinningsWorker(1,0,arr[size-2],map,solution,(float)1,winnings,stake,predictions,events);
	}
	
	private float calculateFullCoverWinningsWorker(int k,int pos, int size ,  Map map  , int[] solution, float odds, float winnings,float stake, List<Prediction> predictions, Set<Event> events) {
		System.out.println("next:");
		if(pos == solution.length) {
			winnings += odds*stake;
			
			
			System.out.println(pos);
			String s = "";
			for (int j = 0; j < pos; j++) {
				s = s+solution[j];
			}
			for (int j = 0; j < pos; j++) {
				System.out.println("ev" + j + ": "+  predictions.get(solution[j]-1).getQuestion().getEvent().getEventNumber());
			}
			System.out.println("sol: " + s + " sum: " + winnings + "odds: " + odds + "stake: " +stake);
		}
		else {
			float test = 0;
			if(pos>1) {	
				boolean single = false;
				for (int i = 0; i < pos; i++) {
					if(((List<Prediction>)map.get(predictions.get(solution[i]-1).getQuestion().getEvent())).size() > 1) {
						single = true;
					}
				}
				
				
				System.out.println("single: " + single);
				if(single) {
					test += odds*stake;
				}
				else {
					test += odds*stake*size;
				}
				System.out.println(pos);
				String s = "";
				for (int j = 0; j < pos; j++) {
					s = s+solution[j];
				}
				for (int j = 0; j < pos; j++) {
					System.out.println("ev" + j + ": "+  predictions.get(solution[j]-1).getQuestion().getEvent().getEventNumber());
				}
				System.out.println("sol: " + s + " sum: " + test);
				
				
				winnings += test;
			}
			for(int i = k; i<=predictions.size();i++) {
				Prediction pred = predictions.get(i-1);
				Event ev = pred.getQuestion().getEvent();
				
				if(!events.contains(ev)) {
					solution[pos]=i;
					events.add(ev);	
					winnings = calculateFullCoverWinningsWorker(i+1,pos+1, size , map ,solution,odds*predictions.get(i-1).getOdds(),winnings,stake,predictions,events);
					events.remove(ev);
				}	
			}	
		}
		return winnings;
	}
	
	/**
	 * Calculates the number of possible combinations that can be made of each size
	 * @param map	HashMap storing the predictions made for each event
	 * @return		array of int, position i stores the number of combinations that can be made of size i+1
	 */
	public int[] computeMultiBets(Map<Event, List<Prediction>> map) {
		int current = 1;
		int i;
		int combinationsum = 0;
		int[] result = new int[map.size()];
		List<Pair> comblist = new ArrayList<Pair>();
		Object[] keyset = map.keySet().toArray();
		for(i=1 ; i <= map.size(); i++) {
			comblist.add(new Pair(i, map.get(keyset[i-1]).size()));
		}
		while(current <= map.size()) {
			List<Pair> temp = new ArrayList<Pair>();
			combinationsum=0;
			for(Pair p: comblist) {	
				int last = p.getList().get(p.getList().size()-1);
				for(i=last+1; i<=map.size(); i++) {
					Pair nextpair = new Pair(p,i,map.get(keyset[i-1]).size());
					temp.add(nextpair);	
					combinationsum += nextpair.getCombinations();
				}	
			}
			comblist = new ArrayList<Pair>(temp);
			result[current-1]=combinationsum;

			current++;
		}
		return result;
	}
	
	/**
	 * Auxiliary class for computeMultiBets
	 */
	public class Pair{
		private ArrayList<Integer> list;
		private int combinations;

		public Pair( int i, int size) {
			list = new ArrayList<Integer>();
			list.add(i);
			combinations = size;
		}

		public Pair(Pair p, int i, int size) {
			list = new ArrayList<Integer>(p.getList());
			list.add(i);
			combinations = p.getCombinations()*size;
		}

		public ArrayList<Integer> getList(){
			return list;
		}

		public int getCombinations() {
			return combinations;
		}

		public String toString() {
			String s = "";
			for(Integer i : list) {
				s = s + String.valueOf(i);
			}
			return s;
		}
	}
	
	
}


