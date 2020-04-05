package businessLogic;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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

/**
 * It implements the business logic as a web service.
 */
@WebService(endpointInterface = "businessLogic.BLFacade")
public class BLFacadeImplementation  implements BLFacade {

	private User loggeduser;
	private Date sessionstart;


	public BLFacadeImplementation()  {	
		System.out.println("Creating BLFacadeImplementation instance");
		ConfigXML c=ConfigXML.getInstance();

		if (c.getDataBaseOpenMode().equals("initialize")) {
			DataAccessImplementation dbManager=new DataAccessImplementation(c.getDataBaseOpenMode().equals("initialize"));
			dbManager.initializeDB();
			dbManager.close();
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
		DataAccess dBManager=new DataAccessImplementation();	    
		if(new Date().compareTo(event.getEventDate())>0) {
			dBManager.close();
			throw new EventFinished(ResourceBundle.getBundle("Etiquetas").getString("ErrorEventHasFinished"));
		}	
		try {
			Question qry=dBManager.createQuestion(event,question,betMinimum, predictions);	
			dBManager.close();
			return qry;
		}
		catch(QuestionAlreadyExist q) {
			dBManager.close();
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
		DataAccess dbManager=new DataAccessImplementation();
		Vector<Event>  events=dbManager.getEvents(date);
		dbManager.close();
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
		DataAccess dbManager=new DataAccessImplementation();
		Vector<Event>  events=dbManager.getEvents(date, sport);
		dbManager.close();
		return events;
	}

	/**
	 * This method invokes the data access to retrieve the dates a month for which there are events
	 * 
	 * @param date of the month for which days with events want to be retrieved 
	 * @return collection of dates
	 */
	@WebMethod public Vector<Date> getEventsMonth(Date date) {
		DataAccess dbManager=new DataAccessImplementation();
		Vector<Date>  dates=dbManager.getEventsMonth(date);
		dbManager.close();
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
			DataAccess dbManager=new DataAccessImplementation();
			dates=dbManager.getEventsMonth(date, competition);
			dbManager.close();	
		}
		else {
			dates = new Vector<Date>();
		}
		return dates;
	}
	
	@WebMethod public Vector<Competition> getCompetitions(Sport sport){
		DataAccess dbManager=new DataAccessImplementation();
		Vector<Competition>  events=dbManager.getCompetitions(sport);
		dbManager.close();
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

		DataAccess dBManager=new DataAccessImplementation();
		try {
			dBManager.registerUser(iD, password, name, surname, email, address, phone, nat, city, birthDate, pic, isAdmin);
			dBManager.close();
		}
		catch (invalidID i) {
			dBManager.close();
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
		DataAccess dBManager=new DataAccessImplementation();
		try {
			User u = dBManager.retrieveUser(ID, password);
			dBManager.close();
			loggeduser = u;
			return u.isAdmin();
		}	
		catch (invalidID e) {
			dBManager.close();
			throw new invalidID(e.getMessage());
		}
		catch (invalidPW e) {
			dBManager.close();
			throw new invalidPW(e.getMessage());
		}
	}

	/**
	 * 
	 */
	@Override
	public List<User> searchByCriteria(String searchtext, String filter, boolean casesensitive, int match) {
		DataAccess dbManager = new DataAccessImplementation();
		List<User> searchResult = dbManager.retrieveUsersByCriteria(searchtext, filter, casesensitive,match);
		dbManager.close();
		return searchResult;
	}

	/**
	 * 
	 */
	@Override
	public void removeUser(String ID) {
		DataAccess dbManager = new DataAccessImplementation();
		dbManager.removeUser(ID);
		dbManager.close();
	}

	/**
	 * 
	 */
	public void updateUserInfo(String key, String iD, String name, String surname, String email,Country nat,String city, String addr, 
			String phn,  Date birthdt, boolean isAdmin) throws invalidID{
		DataAccess dbManager = new DataAccessImplementation();
		try {	
			dbManager.updateUserInfo(key, iD,name,surname,email,addr,phn,nat,city,birthdt,isAdmin);
			dbManager.close();
		}
		catch(invalidID i) {
			dbManager.close();
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
		DataAccess dBManager=new DataAccessImplementation();
		dBManager.initializeDB();
		dBManager.close();
	}


	public void placeBets(float stake, BetType type, List<Prediction> predictions) throws InsufficientCash{
		if(stake > loggeduser.getCash()) {
			throw new InsufficientCash();
		}
		else {
			DataAccessImplementation dbManager=new DataAccessImplementation();
			dbManager.recordBets(loggeduser, stake, type, predictions);
			dbManager.close();
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
		sessionstart = null;
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
		DataAccessImplementation dbManager=new DataAccessImplementation();
		float newcash = dbManager.addCash(loggeduser.getID(), amount);
		dbManager.close();
		return newcash;
	}

	public void submitFeedback(FeedbackType fbtype, String email, String name, String summary, String details, File file) {
		DataAccessImplementation dbManager=new DataAccessImplementation();	
		dbManager.storeFeedback(fbtype, email, name, summary, details, file);
		dbManager.close();

	}


	public List<Prediction> getQuestionPredictions(int questionId) throws QuestionNotFound, NoAnswers {
		DataAccessImplementation dbManager = new DataAccessImplementation();
		try {
			List<Prediction> pred = dbManager.getQuestionPredictions(questionId);
			dbManager.close();
			return pred;
		} catch (NoAnswers e) {
			dbManager.close();
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
				data[i][0] =predictions.get(i).getPredictionNumber();
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
		DataAccessImplementation dbManager = new DataAccessImplementation();
		//	dbManager.initializeDB();
			ArrayList<Bet> bets=dbManager.getBets(u);
			
	             dbManager.close();
			return (bets);
	}


	@Override
	public void remove_bet(User bettor, Bet bet) {
		DataAccess dbManager = new DataAccessImplementation();
		dbManager.remove_bet(bettor, bet);
		dbManager.close();
		
	}


	@Override
	public void updatebets(User bettor, Bet bet, float amount) {
		DataAccess dbManager = new DataAccessImplementation();
		dbManager.updatebets(bettor, bet,amount);
		dbManager.close();
		
	}


	@Override
	public Profile refreshProfile() {
		// TODO Auto-generated method stub
		DataAccess dbManager=new DataAccessImplementation();
		
		loggeduser =dbManager.getuserbyid(loggeduser.getID());
		
		dbManager.close();
		return loggeduser.getProfile();

	}
	}


