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
import domain.FeedbackRecord;
import domain.FeedbackRecordContainer;
import domain.Prediction;
import domain.PredictionContainer;
import domain.Bet;
import domain.BetContainer;
import domain.BetType;
import domain.Competition;
import domain.Event;
import domain.EventContainer;
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
	@WebMethod public Vector<Event> getEventsBySport(Date date, Sport sport);


	/**
	 * This method invokes the data access to retrieve events scheduled for dates between the
	 * two input dates.
	 * 
	 * @param date1  lower bound date
	 * @param date2  upper bound date
	 * @return  collection of events
	 */
	@WebMethod public Vector<EventContainer> getEventsBetweenDates(Date date1,Date date2);

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
	@WebMethod public Vector<Date> getEventsMonthByCompetition(Date date, Competition competition);

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
	 * @return				User object that matches the registered user with the given username and password
	 * @throws invalidID	exception thrown when no user entity with the input ID exists in the database.
	 */
	@WebMethod
	public User checkCredentials(String ID, String password) throws invalidID, invalidPW;

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
	 * This method creates a new CreditCard object, invokes the adata access to store it and assigns it to the logged user
	 * 
	 * @param username	Username(primary key) of the User to add the CreditCard
	 * @param number	Credit card number
	 * @param number	Credit card expiration date
	 */
	@WebMethod
	public CreditCard addCreditCard(String username, String number, Date dueDate);

	/**
	 * This method invokes the data access to delete the given credit card
	 * 
	 * @param cardnumber	Credit card number of the CreditCard to delete
	 */
	@WebMethod
	public void removeCreditCard(String cardnumber); 


	/**
	 * This method sets the default credit card to the given credit card and invokes the data access to store the new default card
	 * 
	 * @param u				User to set the card to
	 * @param defaultcc		CreditCard to set as default
	 */
	@WebMethod
	public void setDefaultCreditCard(User u,CreditCard defaultcc);

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
	@WebMethod
	public Bet placeBet(User u,float stake, float totalprice, BetType type, List<PredictionContainer> predictions) throws InsufficientCash;

	/**
	 * This method calls the data access to initialize the database with some events and questions.
	 * It is invoked only when the option "initialize" is declared in the tag dataBaseOpenMode of resources/config.xml file
	 */	
	@WebMethod public void initializeBD();
	
	/**
	 * Retrieves the bets the given user has in place
	 * 
	 * @param	username 	Username of the User to fetch bets of
	 * @return		List<BetContainer> user's bets
	 */
	@WebMethod
	public List<BetContainer> retrieveBets(String username);

	/**
	 * This method updates a bet of the given user with the new stake value and set of predictions
	 * 
	 * @param bet			Bet to update
	 * @param stake			new stake amount to be set on the bet
	 * @param predictions	new set of predictions
	 * @return				Edited bet
	 */
	@WebMethod
	public Bet editBet(BetContainer bet, BetType type, float stake, List<PredictionContainer> predictions) throws InsufficientCash;
	
	/**
	 * Set given bet as cancelled (Cancelled bets are kept in the database for a fixed amount of time)
	 * @param bet	Bet to cancel
	 */
	@WebMethod
	public void cancelBet(BetContainer cbet);

	/**
	 * This method replaces the existing profile picture of the given user with the new picture.
	 * Only the pathname of the pictures are stored
	 * 
	 * @param p		Profile of the user to change the profile picture of
	 * @param path  Pathname of the file with the picture(must be .jpg or .png)
	 */
	@WebMethod public void updateProfilePic(Profile p, String path);

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
	@WebMethod public boolean updatePassword(User u, String currentpass, String newpass, String confirmpass) throws invalidPW;

	/**
	 * Adds introduced amount the cash stored on the user's account
	 * 
	 * @param username  Username of the User to add cash to
	 * @param amount	amount of money to add(float)
	 * @return	cash on the account after the addition
	 */
	@WebMethod
	public float addCash(String username ,float amount);

	/**
	 * 
	 * @param fbtype
	 * @param email
	 * @param name
	 * @param summary
	 * @param details
	 * @param file
	 */
	@WebMethod public void submitFeedback(FeedbackType fbtype, String email, String name, String summary, String details, File file);


	@WebMethod public List<Prediction> getQuestionPredictions(int questionId) throws QuestionNotFound, NoAnswers;

	/**
	 * This method invokes the data access manager to retrieve the feedback stored in the database.
	 * @return	Feedback that has been sent and stored previously.
	 */
	@WebMethod public Vector<Feedback> getFeedback();

	/**
	 * This method invokes the data access manager to retrieve the feedback records stored in the database that are tied to the given user.
	 * 
	 * @param u User(administrator) to retrieve feedback records for.
	 * @return	Feedback that has been sent and stored previously.
	 */
	@WebMethod
	public Vector<FeedbackRecordContainer> getFeedbackRecords(User u);
	
	
	/**
	 * This method invokes the data access manager update the given feedback records to be marked as read.
	 * 
	 * @param updatedrecords records to be updated as read.
	 */
	@WebMethod
	public void updateFeedBackRecords(List<FeedbackRecord> updatedrecords);
	

	/**
	 * This method resolves the outcomes of the questions the event of finished at the given date. The outcomes of the possible 
	 * predictions a question has are decided by a generated random number. The odds affect the likelihood of the number to be in the
	 * range of said outcome.
	 * 
	 * @param date
	 */
	@WebMethod public void resolveQuestions();

	/**
	 * This method invokes the data access to retrieve the bets scheduled to be resolved in the exact date that the method is called,
	 * computes the winnings earner on each bet and updates the bettor's cash according to them.
	 */
	@WebMethod public void resolveBets();

}
