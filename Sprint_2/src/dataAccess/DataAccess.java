package dataAccess;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.persistence.EntityManager;

import domain.Event;
import domain.Feedback;
import domain.Feedback.FeedbackType;
import domain.Prediction;
import domain.Profile;
import domain.Bet;
import domain.BetType;
import domain.Competition;
import domain.Country;
import domain.CreditCard;
import domain.Question;
import domain.Sport;
import domain.User;
import exceptions.NoAnswers;
import exceptions.QuestionAlreadyExist;
import exceptions.QuestionNotFound;
import exceptions.invalidID;
import exceptions.invalidPW;

public interface DataAccess {
	/**
	 * This is the data access method that initializes the database with some events and questions.
	 * This method is invoked by the business logic (constructor of BLFacadeImplementation) when the option "initialize" is declared in the tag dataBaseOpenMode of resources/config.xml file
	 */	
	public void initializeDB();
	/**
	 * This method creates a question for an event, with a question text and the minimum bet
	 * 
	 * @param event to which question is added
	 * @param question text of the question
	 * @param betMinimum minimum quantity of the bet
	 * @return the created question, or null, or an exception
	 * @throws QuestionAlreadyExist if the same question already exists for the event
	 */
	public Question createQuestion(Event event, String question, float betMinimum, List<Prediction> predictions) throws  QuestionAlreadyExist;

	/**
	 * This method retrieves from the database the events of a given date 
	 * 
	 * @param date in which events are retrieved
	 * @return collection of events
	 */
	public Vector<Event> getEvents(Date date);


	/**
	 * This method retrieves from the database the events of a given date and sport
	 * 
	 * @param date in which events are retrieved
	 * @param sport to look for
	 * @return collection of events
	 */
	public Vector<Event> getEvents(Date date, Sport sport);

	
	/**
	 * This method retrieves from the database the live events. Live events are defined as those for whom the current date(and time) is between the event starting and
	 * ending dates.
	 * 
	 * @return collection of events
	 */
	public Vector<Event> getLiveEvents();
	
	/**
	 * This method retrieves from the database events that are scheduled to start between the two input dates.
	 * 
	 * @param date1   lower bound date;
	 * @param date2   upper bound date;
	 * @return        collection of events;
	 */
	public Vector<Event> getEventsBetweenDates(Date date1, Date date2);
	
	/**
	 * This method retrieves from the database the dates a month for which there are events
	 * 
	 * @param date of the month for which days with events want to be retrieved 
	 * @return collection of dates
	 */
	public Vector<Date> getEventsMonth(Date date);

	/**
	 * This method retrieves from the database the dates a month for which there are events of the corresponding competition
	 * 
	 * @param date of the month for which days with events want to be retrieved 
	 * @param competition to look for
	 * @return collection of dates 
	 */
	public Vector<Date> getEventsMonth(Date date, Competition competition);

	/**
	 * This method retrieves from the database the competitions for the given sport
	 * 
	 * @param sport  Sport for which competitions are retrieved
	 * @return	collection of Competitions
	 */
	public Vector<Competition> getCompetitions(Sport sport);

	/**
	 * This method registers a new user in the database.
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
	public User registerUser(String username,String ID, String password, String name, String surname, String email, String address, String phone, 
			Country nat,String city, Date birthdDate, String pic, boolean isAdmin) throws invalidID;
	/**
	 * This methods checks the validity of the credentials (id / password) inputed upon login.
	 * @param ID			ID of the presumed user.
	 * @param pw			password of the presumed user.
	 * 
	 * @return				boolean indicating privilege level of the user( true:Admin , false:Regular user)
	 * @throws invalidID	exception thrown when no user entity with the input ID exists in the database.
	 */
	public User retrieveUser(String ID, String pw) throws invalidID, invalidPW ;

	/**
	 * 
	 * @param searchtext
	 * @param filter
	 * @param casesensitive
	 * @param match
	 * @return
	 */
	public List<User> retrieveUsersByCriteria(String searchtext, String filter, boolean casesensitive, int match);

	/**
	 * 
	 * @param iD
	 * @return
	 */
	public void removeUser(String iD);
	
	/**
	 * This stores the given credit card on the database with the owner set as the given user
	 * 
	 * @param number	Credit card number
	 * @param number	Credit card expiration date
	 */
	public void storeCreditCard(User user, CreditCard cc);
	
	/**
	 * This method deletes the given credit card from the database
	 * 
	 * @param cc	CreditCard to delete
	 */
	public void removeCreditCard(CreditCard cc);

	/**
	 * This method updates the default credit card value of the given user to the given credit card
	 * 
	 * @param user			User to update default card of
	 * @param defaultcc		New default credit card
	 */
	public void updateDefaultCreditCard(User user, CreditCard defaultcc);

	/**
	 * 
	 * @param key
	 * @param iD
	 * @param name
	 * @param surname
	 * @param email
	 * @param addr
	 * @param phn
	 * @param nat
	 * @param city
	 * @param birthdt
	 * @param isAdmin
	 * @throws invalidID
	 */
	public User updateUserInfo(String key, String iD, String name, String surname, String email, String addr, 
			String phn, Country nat,String city, Date birthdt, boolean isAdmin) throws invalidID;

	/**
	 *	This method updates the value of the password of the given user to the new value.
	 * 
	 * @param u					User to update password of
	 * @param newpass			new value the password should be updated to
	 * @return					true if update completes successfully, false if the new password and confirmation don't match
	 */
	public void updatePassword(User u, String newpass); 
	
	
	/**
	 * This method replaces the existing profile picture of the given user with the new picture.
	 * Only the pathname of the pictures are stored
	 * 
	 * @param p		Profile of the user to change the profile picture of
	 * @param path  Pathname of the file with the picture(must be .jpg or .png)
	 */
	public void updateProfilePic(Profile p, String path);
	
	
	/**
	 * 
	 * @param q
	 * @param u
	 * @param amount
	 */
	public void recordBets(User bettor, float stake, float price, BetType type, List<Prediction> predictions);

	/**
	 * Adds introduced amount the cash stored on the user's account
	 * @param ID		ID of the user to add the cash
	 * @param amount	amount of money to add(float)
	 * @return			cash on the account after the addition
	 */
	public float addCash(String ID, float cash);
	public ArrayList<Bet> getBets(User bettor);

	/**
	 * 
	 */
	public void storeFeedback(FeedbackType fbtype, String email, String name, String summary, String details, File file);

	//public void close();
	public void removeBet (User bettor,Bet bet);
	public void updatebet (User bettor,Bet bet,float amount);
	
	
	/**
	 * This method retrieves all Feedback objects from the database.
	 * @return	Feedback that has been sent and stored previously.
	 */
	public Vector<Feedback> retrieveFeedback();

	/**
	 * Updates the cash of a user by the amount indicates.
	 * 
	 * @param user	User to update cash of.
	 * @param update  Amount of cash to add/remove(+/-).
	 */
	public void updateUserCash(User user, float update);
	
	/**
	 * This method retrieves from the database the Bets that were scheduled to resolve in the date given.
	 * 
	 * @param date    The date in which bets need to be resolved.
	 * @return 		  collection of Bet objects.
	 */
	public List<Bet> getBetsByResolutionDate(Date date);
	
	/**
	 * This method resolves the outcomes of the questions the event of finished at the given date. The outcomes of the possible 
	 * predictions a question has are decided by a generated random number. The odds affect the likelihood of the number to be in the
	 * range of said outcome.
	 * 
	 * @param date
	 */
	public void resolveQuestions(Date date);
	
	public List<Prediction> getQuestionPredictions(int questionId) throws QuestionNotFound,NoAnswers;
}
