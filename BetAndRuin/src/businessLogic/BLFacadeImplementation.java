package businessLogic;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Vector;
import javax.jws.WebMethod;
import javax.jws.WebService;
import configuration.ConfigXML;
import dataAccess.DataAccess;
import dataAccess.DataAccessImplementation;
import domain.Question;
import domain.Sport;
import domain.User;
import domain.Feedback.FeedbackType;
import domain.FeedbackRecord;
import domain.FeedbackRecordContainer;
import domain.Prediction;
import domain.PredictionContainer;
import domain.Bet;
import domain.Bet.BetStatus;
import domain.BetContainer;
import domain.BetType;
import domain.Competition;
import domain.Event;
import domain.EventContainer;
import domain.Feedback;
import domain.Country;
import domain.CreditCard;
import domain.Profile;
import exceptions.EventAlreadyCreated;
import exceptions.EventFinished;
import exceptions.InsufficientCash;
import exceptions.NoAnswers;
import exceptions.QuestionAlreadyExist;
import exceptions.QuestionNotFound;
import exceptions.invalidID;
import exceptions.invalidPW;

/**
 * It implements the business logic as a web service.
 */
@WebService(endpointInterface = "businessLogic.BLFacade")
public class BLFacadeImplementation  implements BLFacade {

	private DataAccess dbManager;

	public BLFacadeImplementation()  {	
		System.out.println("Creating BLFacadeImplementation instance");
		ConfigXML c=ConfigXML.getInstance();
		if(c.getDataBaseOpenMode().equals("initialize")) {
			dbManager=new DataAccessImplementation(true);
			dbManager.initializeDB();
		}
		else {
			dbManager=new DataAccessImplementation(false);
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
	

	@Override
	public void addEvent(Date start, Date end, String des,Sport sport, int cpmunb)  throws EventAlreadyCreated {
		// TODO Auto-generated method stub
		try {
			dbManager.addEvent(start, end, des, sport, cpmunb);
		} catch (EventAlreadyCreated e) {
			throw new EventAlreadyCreated("Event already in db");
		}
		
	}
	@Override
	public void createCompetition(String count, Sport sport, String comp, Date date) {
		// TODO Auto-generated method stub
		dbManager.createCompetition(count,sport,comp,date);
	}	

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
	public Vector<Event> getEventsBySport(Date date, Sport sport)  {
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
	@WebMethod
	public Vector<EventContainer> getEventsBetweenDates(Date date1,Date date2){
		Vector<Event>  events=dbManager.getEventsBetweenDates(date1, date2);
		Vector<EventContainer> evcontainers = new Vector<EventContainer>();
		for(Event ev : events) {
			evcontainers.add(new EventContainer(ev));
		}
		return evcontainers;
	} 

	/**
	 * This method invokes the data access to retrieve the dates a month for which there are events
	 * 
	 * @param date of the month for which days with events want to be retrieved 
	 * @return collection of dates
	 */
	@WebMethod 
	public Vector<Date> getEventsMonth(Date date) {
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
	@WebMethod public Vector<Date> getEventsMonthByCompetition(Date date, Competition competition) {
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
	 * @param username			username of the new user.
	 * @param ID				National ID number of the new user.
	 * @param password			password of the new user.
	 * @param name				name of the new user.
	 * @param surname			surname of the new user.
	 * @param email				email of the new user.
	 * @param isAdmin			whether this user has admin. privileges or not.
	 * 
	 * @throws invalidID		exception thrown when there is a pre existing user with this ID in the database.
	 */
	@WebMethod
	public void registerUser(String username,String ID, String password, String name, String surname, String email, String address, String phone, 
			Country nat, String city, Date birthDate, String pic, boolean isAdmin) throws invalidID{
		try {
			dbManager.registerUser( username, ID, password, name, surname, email, address, phone, nat, city, birthDate, pic, isAdmin);
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
	 * @return				User object that matches the registered user with the given username and password
	 * @throws invalidID	exception thrown when no user entity with the input ID exists in the database.
	 */
	@WebMethod
	public User checkCredentials(String ID, String password) throws invalidID, invalidPW{
		try {
			User u = dbManager.retrieveUser(ID, password);
			return u;
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
	@WebMethod
	public List<User> searchByCriteria(String searchtext, String filter, boolean casesensitive, int match) {
		List<User> searchResult = dbManager.retrieveUsersByCriteria(searchtext, filter, casesensitive,match);
		return searchResult;
	}

	/**
	 * 
	 */
	@Override
	@WebMethod
	public void removeUser(String ID) {
		DataAccess dbManager = new DataAccessImplementation();
		dbManager.removeUser(ID);
	}


	/**
	 * This method creates a new CreditCard object, invokes the adata access to store it and assigns it to the logged user
	 * 
	 * @param username	Username of the User to add the CreditCard
	 * @param number	Credit card number
	 * @param number	Credit card expiration date
	 */
	@WebMethod
	public CreditCard addCreditCard(String username, String number, Date dueDate) {
		DataAccess dbManager = new DataAccessImplementation();
		CreditCard cc = dbManager.storeCreditCard(username, number, dueDate);
		return cc;
	}

	/**
	 * This method invokes the data access to delete the given credit card
	 * 
	 * @param cardnumber	Credit card number of the CreditCard to delete
	 */
	@WebMethod
	public void removeCreditCard(String cardnumber) {
		DataAccess dbManager = new DataAccessImplementation();
		dbManager.removeCreditCard(cardnumber);
	}

	/**
	 * This method sets the default credit card to the given credit card and invokes the data access to store the new default card
	 * 
	 * @param u				User to set the card to
	 * @param defaultcc		CreditCard to set as default
	 */
	@WebMethod
	public void setDefaultCreditCard(User u,CreditCard defaultcc) {
		if(!defaultcc.equals(u.getDefaultCreditCard())) {
			DataAccess dbManager = new DataAccessImplementation();
			dbManager.updateDefaultCreditCard(u,defaultcc);
			u.setDefaultCreditCard(defaultcc);
		}
	}

	/**
	 * This method invokes the data access to update the profile information for a given user
	 */
	@WebMethod
	public User updateUserInfo(String key, String username, String name, String surname, String email,Country nat,String city, String addr, 
			String phn,  Date birthdt, boolean isAdmin) throws invalidID{
		try {	
			User u = dbManager.updateUserInfo(key, username,name,surname,email,addr,phn,nat,city,birthdt,isAdmin);
			return u;

		}
		catch(invalidID i) {
			throw new invalidID();
		}

	}

	/**
	 * This method invokes the data access manager to replace the current password of the given user to the new value.
	 * Fails the inputed current password doesn't match the user's(exception is thrown) or when the confirmation password
	 * doesn't match the new password.
	 * 
	 * @param u					User to update password of
	 * @param currentpass		current password
	 * @param newpass			new value the password should be updated to
	 * @param confirmpass		confirmation for the new password
	 * @return					true if update completes successfully, false if the new password and confirmation don't match or the password is too short
	 * @throws invalidPW		exception thrown when currentpass doesn't match the actual current password of the user
	 */
	@WebMethod
	public boolean updatePassword(User u, String currentpass, String newpass, String confirmpass) throws invalidPW{
		if(u.getPassword().equals(currentpass)) {
			if(newpass.equals(confirmpass) && newpass.length() >= 8) {
				dbManager.updatePassword(u,newpass);
				return true;
			}
			else {
				return false;
			}
		}
		else {
			throw new invalidPW("Incorrect password");
		}
	}

	/**
	 * This method invokes the data access to initialize the database with some events and questions.
	 * It is invoked only when the option "initialize" is declared in the tag dataBaseOpenMode of resources/config.xml file
	 */	
	@WebMethod	
	public void initializeBD(){
		dbManager.initializeDB();
	}

	@WebMethod
	public Bet placeBet(User u,float stake, float totalprice, BetType type, List<PredictionContainer> predictions) throws InsufficientCash{
		if(totalprice > u.getCash()) {
			throw new InsufficientCash();
		}
		else {
			Bet bet = dbManager.recordBet(u, stake, totalprice,type, predictions);
			return bet;
		}

	}

	/**
	 * Set given bet as cancelled (Cancelled bets are kept in the database for a fixed amount of time)
	 * @param bet	Bet to cancel
	 */
	@WebMethod
	public void cancelBet(BetContainer cbet) {
		Bet bet = cbet.getBet();
		dbManager.cancelBet(bet);
		User bettor = bet.getBettor();
		for(Bet b : bettor.getBets()) {
			if(b.getBetNumber().equals(bet.getBetNumber())) {
				b.setStatus(BetStatus.CANCELLED);
			}
		}
		bettor.setCash(bettor.getCash() + bet.getStake());

	}


	/**
	 * Retrieves the bets the given user has in place
	 * 
	 * @param	username 	Username of the User to fetch bets of
	 * @return		List<BetContainer> user's bets
	 */
	@WebMethod
	public List<BetContainer> retrieveBets(String username){
		List<Bet> bets = dbManager.getBets(username);
		List<BetContainer> bcontainers = new ArrayList<BetContainer>();
		for(Bet b : bets) {
			bcontainers.add(new BetContainer(b));
		}
		return bcontainers;
	}

	/**
	 * This method replaces the existing profile picture of the given user with the new picture.
	 * Only the pathname of the pictures are stored
	 * 
	 * @param p		Profile of the user to change the profile picture of
	 * @param path  Pathname of the file with the picture(must be .jpg or .png)
	 */
	@WebMethod
	public void updateProfilePic(Profile p, String path) {
		dbManager.updateProfilePic(p,path);
	}

	/**
	 * Adds introduced amount the cash stored on the user's account
	 * 
	 * @param username  Username of the User to add cash to
	 * @param amount	amount of money to add(float)
	 * @return	cash on the account after the addition
	 */
	@WebMethod
	public float addCash(String username ,float amount) {
		float newcash = dbManager.addCash(username, amount);
		return newcash;
	}

	/**
	 * This method calls to the data access manager to store a new feedback object.
	 */
	@WebMethod
	public void submitFeedback(FeedbackType fbtype, String email, String name, String summary, String details, File file) {
		dbManager.storeFeedback(fbtype, email, name, summary, details, file);
	}

	/**
	 * This method invokes the data access manager to retrieve the feedback stored in the database.
	 * @return	Feedback that has been sent and stored previously.
	 */
	@WebMethod
	public Vector<Feedback> getFeedback(){
		Vector<Feedback> fb = dbManager.retrieveFeedback();
		return fb;
	}


	/**
	 * This method invokes the data access manager to retrieve the feedback records stored in the database that are tied to the given user.
	 * 
	 * @param u User(administrator) to retrieve feedback records for.
	 * @return	Feedback that has been sent and stored previously.
	 */
	@WebMethod 
	public Vector<FeedbackRecordContainer> getFeedbackRecords(User u){
		Vector<FeedbackRecord> fbrecords = dbManager.retrieveFeedbackRecords(u);

		Vector<FeedbackRecordContainer> fbrcontainers = new Vector<FeedbackRecordContainer>();
		for(FeedbackRecord fbr: fbrecords) {
			fbrcontainers.add(new FeedbackRecordContainer(fbr));
		}
		return fbrcontainers;
	}

	/**
	 * This method invokes the data access manager update the given feedback records to be marked as read.
	 * 
	 * @param updatedrecords records to be updated as read.
	 */
	@WebMethod
	public void updateFeedBackRecords(List<FeedbackRecord> updatedrecords) {
		dbManager.updateFeedBackRecords(updatedrecords);
	}

	@WebMethod
	public List<Prediction> getQuestionPredictions(int questionId) throws QuestionNotFound, NoAnswers {

		try {
			List<Prediction> pred = dbManager.getQuestionPredictions(questionId);
			return pred;
		} catch (NoAnswers e) {
			throw new NoAnswers(e.getMessage());
		}
	}

	/**
	 * This method updates a bet of the given user with the new stake value and set of predictions
	 * 
	 * @param bet			Bet to update
	 * @param stake			new stake amount to be set on the bet
	 * @param predictions	new set of predictions
	 * @return				Edited bet
	 */
	@WebMethod
	public Bet editBet(BetContainer cbet, BetType type, float stake, List<PredictionContainer> predictions) throws InsufficientCash{
		Bet bet = cbet.getBet();
		User bettor = bet.getBettor();

		if(stake-bet.getStake() <= bettor.getCash()) {
			Bet b = dbManager.updateBet(bet,type, stake, predictions);
			return b;
		}
		else {
			throw new InsufficientCash("Insufficient cash");
		}
	}



	/**
	 * This method resolves the outcomes of the questions the event of finished at the given date. The outcomes of the possible 
	 * predictions a question has are decided by a generated random number. The odds affect the likelihood of the number to be in the
	 * range of said outcome.
	 * 
	 * @param date
	 */
	@WebMethod
	public void resolveQuestions() {
		Calendar cl  = Calendar.getInstance();
		dbManager.resolveQuestions(cl.getTime());
	}

	/**
	 * This method invokes the data access to retrieve the bets scheduled to be resolved in the exact date that the method is called,
	 * computes the winnings earned on each bet and updates the bettor's cash according to them.
	 */
	@WebMethod
	public void resolveBets() {
		List<Bet> bets = dbManager.getBetsByResolutionDate(new Date()); 
		Map<User,Float> winningsMap = new HashMap<User, Float>();


		//Srystem.out.println("Bets found: " + bets.size() );
		long starttime = System.nanoTime();
		for(Bet bet : bets) {
			Float currentwinnings = (float)0;
			float winnings = bet.getWinnings();
			User bettor = bet.getBettor();
			bet.setStatus(Bet.BetStatus.RESOLVED);
			if(winnings > 0) {
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


}


