package businessLogic;

import java.util.Vector;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
import domain.CreditCard;
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
import javax.swing.Timer;

import businessLogic.BLFacadeImplementation.Pair;

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
	 * This method invokes the data access to retrieve events scheduled for dates between the
	 * two input dates.
	 * 
	 * @param date1  lower bound date
	 * @param date2  upper bound date
	 * @return  collection of events
	 */
	public Vector<Event> getEventsBetweenDates(Date date1,Date date2);

	/**
	 * This method invokes the data access manager to retrieve the events that are currently live, that is, that the current time
	 * is between the starting and ending date of said events.
	 * 
	 * @return collection of events
	 */
	@WebMethod public Vector<Event> getLiveEvents();

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
	@WebMethod public void registerUser(String username,String ID, String password, String name, String surname, String email, String address, String phone, 
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
	 * Retrieves the bets the currently logged has placed between the indicated dates
	 * 
	 * @param		fromdate lower bound date
	 * @param		fromdate upper bound date
	 * @return		List<Bet> user's bets
	 */
	public List<Bet> retrieveBetsByDate(Date fromdate, Date todate);

	/**
	 * 
	 * @param searchtext
	 * @param filter
	 * @return
	 */
	@WebMethod public void removeUser(String ID);

	/**
	 * This method creates a new CreditCard object, invokes the adata access to store it and assigns it to the logged user
	 * 
	 * @param number	Credit card number
	 * @param number	Credit card expiration date
	 */
	public CreditCard addCreditCard(String number, Date dueDate);

	/**
	 * This method invokes the data access manager to delete the given credit card
	 * 
	 * @param cc	CreditCard to delete
	 */
	public void removeCreditCard(CreditCard cc); 


	/**
	 * This method sets the default credit card to the given credit card and invokes the data access to store the new default card
	 * 
	 * @param defaultcc		CreditCard to set as default
	 */
	public void setDefaultCreditCard(CreditCard defaultcc);

	/**
	 * 
	 * @param searchtext
	 * @param filter
	 * @return
	 */
	@WebMethod public User updateUserInfo(String key, String username, String name, String surname, String email,Country nat,String city, String addr, 
			String phn,  Date birthdt, boolean isAdmin) throws invalidID;
	
	/**
	 * 
	 * @param q
	 * @param amount
	 */
	@WebMethod public void placeBet(float stake, float totalprice, BetType type, List<Prediction> predictions) throws InsufficientCash;

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
	 * Retrieves the bets the given user has in place
	 * @return		List<Bet> user's bets
	 */
	public ArrayList<Bet> retrieveBets(User u);

	/**
	 * This method updates a bet of the given user with the new stake value and set of predictions
	 * 
	 * @param bet			Bet to update
	 * @param stake			new stake amount to be set on the bet
	 * @param predictions	new set of predictions
	 */
	public void editBet(Bet bet, BetType type, float stake, List<Prediction> predictions) throws InsufficientCash;
	
	/**
	 * Set given bet as cancelled (Cancelled bets are kept in the database for a fixed amount of time)
	 * @param bet	Bet to cancel
	 */
	public void cancelBet(Bet bet);
	
	/**
	 * Retrieves the profile of the currently logged user
	 * @return	Profile object containing information about the user
	 */
	public Profile getProfile();

	/**
	 * This method replaces the existing profile picture of the given user with the new picture.
	 * Only the pathname of the pictures are stored
	 * 
	 * @param p		Profile of the user to change the profile picture of
	 * @param path  Pathname of the file with the picture(must be .jpg or .png)
	 */
	public void updateProfilePic(Profile p, String path);

	/**
	 * This method invokes the data access manager to replace the current password of the given user to the new value.
	 * Fails the inputed current password doesn't match the user's(exception is thrown) or when the confirmation password
	 * doesn't match the new password.
	 * 
	 * @param u					User to update password of
	 * @param currentpass		current password
	 * @param newpass			new value the password should be updated to
	 * @param confirmpass		confirmation for the new password
	 * @return					true if update completes successfully, false if the new password and confirmation don't match
	 * @throws invalidPW		exception thrown when currentpass doesn't match the actual current password of the user
	 */
	public boolean updatePassword(User u, String currentpass, String newpass, String confirmpass) throws invalidPW;

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
	 * Retrieves the currently logged users username
	 * @return username field value of the logged user
	 */
	public String getUsername();

	public void setLoggeduser(User loggeduser);

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

	///////////////////////////////////////////////////////////////////////////////////////
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
	///////////////////////////////////////////////////////////////////////////////////////

	/**
	 * This method invokes the data access manager to retrieve the feedback stored in the database.
	 * @return	Feedback that has been sent and stored previously.
	 */
	public Vector<Feedback> getFeedback();

	/**
	 * This method returns the User object that holds information about the user logged in currently.
	 * @return 	User object.
	 */
	public User getLoggeduser();

	/**
	 * This method resolves the outcomes of the questions the event of finished at the given date. The outcomes of the possible 
	 * predictions a question has are decided by a generated random number. The odds affect the likelihood of the number to be in the
	 * range of said outcome.
	 * 
	 * @param date
	 */
	public void resolveQuestions();

	/**
	 * This method invokes the data access to retrieve the bets scheduled to be resolved in the exact date that the method is called,
	 * computes the winnings earner on each bet and updates the bettor's cash according to them.
	 */
	public void resolveBets();

	/**
	 * This method computes the number of possible multiple bets that can be made with the given selection of predictions,
	 * taking into account the restrictions on same event multi betting
	 * 
	 * @param map	Map storing mapping between Events and the list of predictions selected for each event
	 * @return		array with the number of possible multiple bets for each size(Double,Treble...)
	 */
	public int[] computeMultiBets(Map<Event, List<Prediction>> map);

	/**
	 * This method calculates the winnings for a combined bet
	 * 
	 * @param size			Combined bet size(Double: 2, Treble:3 ...)
	 * @param stake			Amount placed on the bet(float)
	 * @param predictions   Collection of predictions made when placing the bet(List<Prediction>)
	 * @return				winnings earned from the bet(float)
	 */
	public float calculateCombinedWinnings(int size, float stake, List<Prediction> predictions);

	/**
	 * This method calculates the winnings for a full cover bet
	 * 
	 * @param size			Combined bet size(Double: 2, Treble:3 ...)
	 * @param stake			Amount placed on the bet(float)
	 * @param predictions   Collection of predictions made when placing the bet(List<Prediction>)
	 * @return				winnings earned from the bet(float)
	 */
	public float calculateFullCoverWinnings(int size, float stake, List<Prediction> predictions);

}
