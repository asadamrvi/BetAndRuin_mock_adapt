package businessLogic;

import java.util.Vector;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

//import domain.Booking;
import domain.Question;
import domain.Sport;
import domain.User;
import domain.Feedback.FeedbackType;
import domain.Prediction;
import domain.Bet;
import domain.BetType;
import domain.Competition;
import domain.Event;
import domain.Country;
import domain.Profile;
import exceptions.EventFinished;
import exceptions.InsufficientCash;
import exceptions.NoAnswers;
import exceptions.QuestionAlreadyExist;
import exceptions.QuestionNotFound;
import exceptions.invalidID;
import exceptions.invalidPW;

import javax.jws.WebMethod;
import javax.jws.WebService;

/**
 * Interface that specifies the business logic.
 */
@WebService
public interface BLFacade  {
	

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
	@WebMethod Question createQuestion(Event event, String question, float betMinimum,  List<Prediction> prediction) throws EventFinished, QuestionAlreadyExist;
	
	
	/**
	 * This method retrieves the events of a given date 
	 * 
	 * @param date in which events are retrieved
	 * @return collection of events
	 */
	@WebMethod public Vector<Event> getEvents(Date date);
	
	/**
	 * This method retrieves the events of a given date and sport 
	 * 
	 * @param date in which events are retrieved
	 * @return collection of events
	 */
	@WebMethod public Vector<Event> getEvents(Date date, Sport sport);
	
	/**
	 * This method retrieves from the database the dates a month for which there are events
	 * 
	 * @param date of the month for which days with events want to be retrieved 
	 * @return collection of dates
	 */
	@WebMethod public Vector<Date> getEventsMonth(Date date);
	
	
	/**
	 * This method retrieves from the database the dates a month for which there are events for the given competition
	 * 
	 * @param date of the month for which days with events want to be retrieved 
	 * @return collection of dates
	 */
	@WebMethod public Vector<Date> getEventsMonth(Date date, Competition competition);
	
	/**
	 * 
	 * @param sport
	 * @return
	 */
	@WebMethod public Vector<Competition> getCompetitions(Sport sport);

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
	@WebMethod public void registerUser(String iD, String password, String name, String surname, String email, String address, String phone, 
			Country nat, String city, Date birthDate, String pic, boolean isAdmin) throws invalidID;

	/**
	 * This methods checks the validity of the credentials (id / password) inputed upon login.
	 * @param ID			ID of the presumed user.
	 * @param pw			password of the presumed user.
	 * 
	 * @return				boolean indicating privilege level of the user( true: Admin, false:Regular user).
	 * @throws invalidID	exception thrown when no user entity with the input ID exists in the database.
	 */
	@WebMethod public boolean checkCredentials(String ID, String password) throws invalidID, invalidPW;
		
	/**
	 * 
	 * @param iD
	 * @param nam
	 * @param surn
	 * @param Email
	 * @return
	 */ 
	@WebMethod public List<User> searchByCriteria(String searchtext, String filter, boolean casesensitive, int match);
	
	/**
	 * 
	 * @param searchtext
	 * @param filter
	 * @return
	 */
	@WebMethod public void removeUser(String ID);
	
	/**
	 * 
	 * @param searchtext
	 * @param filter
	 * @return
	 */
	@WebMethod public void updateUserInfo(String key, String iD, String name, String surname, String email,Country nat,String city, String addr, 
			String phn,  Date birthdt, boolean isAdmin) throws invalidID;
	
	/**
	 * 
	 * @param q
	 * @param amount
	 */
	@WebMethod public void placeBets(float stake, BetType type, List<Prediction> predictions) throws InsufficientCash;
	
	/**
	 * This method calls the data access to initialize the database with some events and questions.
	 * It is invoked only when the option "initialize" is declared in the tag dataBaseOpenMode of resources/config.xml file
	 */	
	@WebMethod public void initializeBD();

	/**
	 * This method checks if a user is currently logged in
	 * @return    boolean(true: if a user is logged in, false: else)
	 */
	public boolean isLoggedIn();
	
	/**
	 * Logs the current user out by setting the attributes related to the current session to null
	 */
	public void logOut();
	
	/**
	 * Retrieves the coupons the currently logged user has in place
	 * @return		List<Coupon> user's bets
	 */
	public List<Bet> retrieveBets();
	
	/**
	 * Retrieves the profile of the currently logged user
	 * @return	Profile object containing information about the user
	 */
	public Profile getProfile();
	
	/**
	 * Indicates if the logged user has an admin status.
	 * @return	boolean(true: if loggeduser is an admin, false:else)
	 */
	public boolean isAdmin();
	
	/**
	 * Returns cash currently stored on the user account.
	 * @return  current cash amount.
	 */
	public float getCash();
	
	/**
	 * Retrieves the currently logged users ID
	 * @return ID field value of the logged user
	 */
	public String getUserID();
		
	/**
	 * Adds introduced amount the cash stored on the user's account
	 * @param amount	amount of money to add(float)
	 * @return	cash on the account after the addition
	 */
	public float addCash(float amount);
	
	/**
	 * 
	 * @param fbtype
	 * @param email
	 * @param name
	 * @param summary
	 * @param details
	 * @param file
	 */
	public void submitFeedback(FeedbackType fbtype, String email, String name, String summary, String details, File file);

	
	public List<Prediction> getQuestionPredictions(int questionId) throws QuestionNotFound, NoAnswers;
	
	/** Add Hours in Placment Date of bet, and compare new date with Resolving Date.
	 * 
	 * @param A Bet Variable, Hours
	 * @return	return True if adding hours in Bet Placement date is less then Resolving Date
	 */
	public boolean Enable_or_not(Bet b,int Hours);
	
	/** Function To Get TAbles Data.
	 * 
	 * @param Bet Variable and Arraylist of Predictions.
	 * @return	return Matrix DAta later to Use in tables.
	 */
	public Object[][] getDAta(Bet b,ArrayList<Bet> bets) ;
	/** Function To Get BEts of User .
	 * 
	 * @param User U.
	 * @return	return ArrayList of User bets.
	 */
	public ArrayList<Bet> getBets(User u);
	
	/** Function To remove Bet of USer.
	 * 
	 * @param User U, and Bet B.
	 * @return	Remove Bet from USer bet List.
	 */
	public void remove_bet (User bettor, Bet bet);
	/** Function to update Bet Amount of given Bet..
	 * 
	 * @param A user , Bet and New Amount.
	 * @return	Set amount as new BEt amount.
	 */
	public void updatebets (User bettor, Bet bet, float amount);
	
	public User getLoggeduser();
	
	public Profile refreshProfile();

	
}
