package dataAccess;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Vector;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import configuration.ConfigXML;
import configuration.UtilDate;
import domain.Bet;
import domain.BetType;
import domain.Competition;
import domain.Event;
import domain.Feedback;
import domain.Feedback.FeedbackType;
import domain.Prediction;
import domain.Country;
import domain.Profile;
import domain.Question;
import domain.Sport;
import domain.User;
import exceptions.InsufficientCash;
import exceptions.NoAnswers;
import exceptions.QuestionAlreadyExist;
import exceptions.QuestionNotFound;
import exceptions.invalidID;
import exceptions.invalidPW;

/**
 * It implements the data access to the objectDb database
 */
public class DataAccessImplementation implements DataAccess {
	protected static EntityManager  db;
	protected static EntityManagerFactory emf;


	ConfigXML c;

	public DataAccessImplementation(boolean initializeMode)  {

		c=ConfigXML.getInstance();

		System.out.println("Creating DataAccess instance => isDatabaseLocal: "+c.isDatabaseLocal()+" getDatabBaseOpenMode: "+c.getDataBaseOpenMode());

		String fileName=c.getDbFilename();
		if (initializeMode)
			fileName=fileName+";drop";

		if (c.isDatabaseLocal()) {
			emf = Persistence.createEntityManagerFactory("objectdb:"+fileName);
			db = emf.createEntityManager();
		} else {
			Map<String, String> properties = new HashMap<String, String>();
			properties.put("javax.persistence.jdbc.user", c.getUser());
			properties.put("javax.persistence.jdbc.password", c.getPassword());

			emf = Persistence.createEntityManagerFactory("objectdb://"+c.getDatabaseNode()+":"+c.getDatabasePort()+"/"+fileName, properties);

			db = emf.createEntityManager();
		}
	}

	public DataAccessImplementation()  {	
		new DataAccessImplementation(false);
	}


	/**
	 * This is the data access method that initializes the database with some events and questions.
	 * This method is invoked by the business logic (constructor of BLFacadeImplementation) when the option "initialize" is declared in the tag dataBaseOpenMode of resources/config.xml file
	 */	
	public void initializeDB(){

		db.getTransaction().begin();
		try {


			Calendar today = Calendar.getInstance();

			int month=today.get(Calendar.MONTH);
			month+=1;
			int year=today.get(Calendar.YEAR);
			if (month==12) { month=0; year+=1;}  

			SimpleDateFormat df = new SimpleDateFormat("HH:mm dd/MM/yyyy");

			Event ev1=new Event(1, "Atlético-Athletic", df.parse("12:00 17/4/2020"),df.parse("13:45 17/4/2020"),Sport.FOOTBALL);
			Event ev2=new Event(2, "Eibar-Barcelona", df.parse("15:00 03/4/2020"),df.parse("16:45 17/4/2020"),Sport.FOOTBALL);
			Event ev3=new Event(3, "Getafe-Celta", df.parse("18:00 11/5/2020"),df.parse("19:45 11/5/2020"),Sport.FOOTBALL);
			Event ev4=new Event(4, "Alavés-Deportivo", df.parse("20:00 25/4/2020"),df.parse("21:45 25/4/2020"),Sport.FOOTBALL);
			Event ev5=new Event(5, "Español-Villareal",df.parse("22:00 25/4/2020"),df.parse("23:45 25/4/2020"),Sport.FOOTBALL);
			Event ev6=new Event(6, "Las Palmas-Sevilla",df.parse("16:00 17/4/2020"),df.parse("17:45 17/4/2020"),Sport.FOOTBALL);
			Event ev7=new Event(7, "Malaga-Valencia", df.parse("18:00 18/4/2020"),df.parse("19:45 18/4/2020"),Sport.FOOTBALL);
			Event ev8=new Event(8, "Girona-Leganés",df.parse("15:00 10/4/2020"),df.parse("16:45 10/4/2020"),Sport.FOOTBALL);
			Event ev9=new Event(9, "Real Sociedad-Levante", df.parse("22:00 10/4/2020"),df.parse("23:45 10/4/2020"),Sport.FOOTBALL);
			Event ev10=new Event(10, "Betis-Real Madrid", df.parse("14:00 11/4/2020"),df.parse("15:45 11/4/2020"),Sport.FOOTBALL);

			Event ev11=new Event(11, "Atletico-Athletic", df.parse("17:00 2/5/2020"),df.parse("18:45 2/5/2020"),Sport.FOOTBALL);
			Event ev12=new Event(12, "Eibar-Barcelona",  df.parse("19:00 2/5/2020"),df.parse("20:45 2/5/2020"),Sport.FOOTBALL);
			Event ev13=new Event(13, "Getafe-Celta", df.parse("21:00 2/5/2020"),df.parse("22:45 2/5/2020"),Sport.FOOTBALL);
			Event ev14=new Event(14, "Alavés-Deportivo", df.parse("15:30 3/5/2020"),df.parse("17:15 3/5/2020"),Sport.FOOTBALL);
			Event ev15=new Event(15, "Español-Villareal", df.parse("17:30 3/5/2020"),df.parse("19:15 3/5/2020"),Sport.FOOTBALL);
			Event ev16=new Event(16, "Las Palmas-Sevilla", df.parse("20:00 3/5/2020"),df.parse("21:45 3/5/2020"),Sport.FOOTBALL);


			Event ev17=new Event(17, "Málaga-Valencia",  df.parse("20:00 9/5/2020"),df.parse("21:45 9/5/2020"),Sport.FOOTBALL);
			Event ev18=new Event(18, "Girona-Leganés",  df.parse("20:00 10/5/2020"),df.parse("21:45 10/5/2020"),Sport.FOOTBALL);
			Event ev19=new Event(19, "Real Sociedad-Levante",  df.parse("15:30 16/5/2020"),df.parse("17:15 16/5/2020"),Sport.FOOTBALL);
			Event ev20=new Event(20, "Betis-Real Madrid", df.parse("18:00 16/5/2020"),df.parse("19:45 16/5/2020"),Sport.FOOTBALL);
			Event ev21=new Event(21, "Nadal-Federer",  df.parse("20:00 16/5/2020"),df.parse("21:45 16/5/2020"),Sport.TENNIS);
			Event ev22=new Event(22, "Juventus-AC Milan", df.parse("18:00 17/5/2020"),df.parse("19:45 17/5/2020"),Sport.FOOTBALL);
			Event ev23=new Event(23, "PSG-Olympique Lyonnais",  df.parse("20:00 17/5/2020"),df.parse("21:45 17/5/2020"),Sport.FOOTBALL);
			Event ev24=new Event(24, "Bayern Munich-Borussia Dortmund", df.parse("22:00 17/5/2020"),df.parse("23:45 17/5/2020"),Sport.FOOTBALL);
			Event ev25=new Event(25, "Liverpool-Tottenham Hotspur",  df.parse("15:00 25/5/2020"),df.parse("16:45 25/5/2020"),Sport.FOOTBALL);
			Event ev26=new Event(26, "Arsenal-Chelsea", df.parse("18:00 25/5/2020"),df.parse("19:45 25/5/2020"),Sport.FOOTBALL);


			Competition liga = new Competition("LaLiga Santander", Country.ES, Sport.FOOTBALL, new Date(), new Date());
			Competition bundesliga = new Competition("Bundesliga", Country.DE, Sport.FOOTBALL, new Date(), new Date());
			Competition ligue_1 = new Competition("Ligue 1", Country.FR, Sport.FOOTBALL, new Date(), new Date());
			Competition serieA = new Competition("Serie A", Country.IT, Sport.FOOTBALL, new Date(), new Date());
			Competition premier = new Competition("Premier League", Country.GB, Sport.FOOTBALL, new Date(), new Date());

			liga.addEvent(ev1);
			liga.addEvent(ev2);
			liga.addEvent(ev3); 
			liga.addEvent(ev4);
			liga.addEvent(ev5);
			liga.addEvent(ev6);
			liga.addEvent(ev7);
			liga.addEvent(ev8);
			liga.addEvent(ev9);
			liga.addEvent(ev10);
			liga.addEvent(ev11);
			liga.addEvent(ev12);
			liga.addEvent(ev13);
			liga.addEvent(ev14);
			liga.addEvent(ev15);
			liga.addEvent(ev16);
			liga.addEvent(ev17);
			liga.addEvent(ev18);
			liga.addEvent(ev17);
			liga.addEvent(ev18);
			liga.addEvent(ev19);
			liga.addEvent(ev20);
			liga.addEvent(ev21);
			serieA.addEvent(ev22);
			ligue_1.addEvent(ev23);
			bundesliga.addEvent(ev24);
			premier.addEvent(ev25);
			premier.addEvent(ev26);

			Question q1;
			Question q2;
			Question q3;
			Question q4;
			Question q5;
			Question q6;

			if (Locale.getDefault().equals(new Locale("es"))) {
				q1=ev1.addQuestion("¿Quién ganará el partido?",1);
				q2=ev1.addQuestion("¿Quién meterá el primer gol?",2);
				q3=ev11.addQuestion("¿Quién ganará el partido?",1);
				q4=ev11.addQuestion("¿Cuántos goles se marcarán?",2);
				q5=ev17.addQuestion("¿Quién ganará el partido?",1);
				q6=ev17.addQuestion("¿Habrá goles en la primera parte?",2);
			}
			else if (Locale.getDefault().equals(new Locale("en"))) {
				q1=ev1.addQuestion("Who will win the match?",1);
				q2=ev1.addQuestion("Who will score first?",2);
				q3=ev11.addQuestion("Who will win the match?",1);
				q4=ev11.addQuestion("How many goals will be scored in the match?",2);
				q5=ev17.addQuestion("Who will win the match?",1);
				q6=ev17.addQuestion("Will there be goals in the first half?",2);
			}			
			else {
				q1=ev1.addQuestion("Zeinek irabaziko du partidua?",1);
				q2=ev1.addQuestion("Zeinek sartuko du lehenengo gola?",2);
				q3=ev11.addQuestion("Zeinek irabaziko du partidua?",1);
				q4=ev11.addQuestion("Zenbat gol sartuko dira?",2);
				q5=ev17.addQuestion("Zeinek irabaziko du partidua?",1);
				q6=ev17.addQuestion("Golak sartuko dira lehenengo zatian?",2);

			}
			q1.addPrediction("Atlético", Float.valueOf((float) 4.0));
			q1.addPrediction("Athletic", Float.valueOf((float) 1.0));


			today.set(Calendar.HOUR_OF_DAY, 0);
			today.set(Calendar.MINUTE, 0);
			today.set(Calendar.SECOND, 0);
			today.set(Calendar.MILLISECOND, 0);

			Profile p1 = new Profile("aaa" , "Julen", "Urroz", "testemail@gmail.com", "testaddr", "+34 688689414", Country.ES, "Zarautz",today.getTime() , "images/profilepic/palmera.png");
			Profile p2 = new Profile("bbb","JonAnder", "Beroz", "testemail2@gmail.com", "testaddr", "+34 6478646", Country.ES, "Hondarribi", today.getTime(), "images/profilepic/smiley.png");
			Profile p3 = new Profile("ccc","Asad", "Hayat", "testemail3@gmail.com","testaddr", "+34 7543734", Country.ES, "Donostia", today.getTime(), "images/profilepic/smiley.png");
			Profile p4 = new Profile("a","a", "a", "a@gmail.com","testaddr", "+11 1111111", Country.CI, "a",today.getTime(), "images/profilepic/smiley.png");	
			Profile p5 = new Profile("b","b", "b", "b@gmail.com","testaddr", "+22 2222222", Country.MA, "b", today.getTime(), "images/profilepic/smiley.png");
			Profile p6 = new Profile("Virus","Corona", "Virus","cvirus@gmail.com","testaddr", "+66 6666666", Country.ES, "London", today.getTime(), "images/profilepic/cvirus.png");
			Profile p7 = new Profile("Lemon","Yellow", "Lemon","ylemon@yahoo.es","testaddr", "+44 4444444", Country.ES, "Lima",today.getTime(), "images/profilepic/smiley.png");
			Profile p8 = new Profile("George11","George", "Washington","gwash@gg.gg","testaddr", "+77 77777777", Country.ES, "Washington DC", today.getTime(), "images/profilepic/smiley.png");
			Profile p9 = new Profile ("Dog", "Im", "Dog", "doggie@gmail.com","testaddr", "+88 888888", Country.ES, "Boston", today.getTime(), "images/profilepic/smiley.png");
			Profile p10 = new Profile ("Maria","Maria", "Ardilla", "marie123@gmail.com","testaddr", "+99 999999", Country.AR, "Buenos Aires", today.getTime(), "images/profilepic/smiley.png");
			Profile p11 = new Profile ("xx", "xx", "xx", "xx@gmail.com","testaddr", "+25 262662", Country.MX, "Donostia", today.getTime(), "images/profilepic/smiley.png");
			Profile p12 = new Profile ("ggwp", "gg", "wp", "ggwp@gmail.com","testaddr", "+36 4586838", Country.MA, "Cancun", today.getTime(), "images/profilepic/smiley.png");
			Profile p13 = new Profile ("hello","hello", "hello", "hello@gmail.com","testaddr", "+86 3121525", Country.JP, "Hello", today.getTime(), "images/profilepic/smiley.png");
			Profile p14 = new Profile ("r", "r", "r", "r@gmail.com","testaddr", "+34 7543734", Country.RU, "Donostia", today.getTime(), "images/profilepic/smiley.png");
			Profile p15 = new Profile ("Grandpa", "Grandpa", "Arnold", "arnold666@gmail.com","testaddr", "+23 56754216", Country.GE, "Donostia", today.getTime(), "images/profilepic/smiley.png");
			Profile p16 = new Profile ("Antonio","Antonio", "Antonia", "Antoniojk@gmail.com","testaddr", "+62 4246264", Country.ES, "Donostia", today.getTime(), "images/profilepic/smiley.png");
			Profile p17 = new Profile ("Carl","Carl", "B", "carlb@gmail.com","testaddr", "+11 3552352", Country.US, "Donostia", today.getTime(), "images/profilepic/smiley.png");

			User u1 = new User("aaa", "bbb", true, p1);
			User u2 = new User("bbb", "bbb", true, p2);
			User u3 = new User("ccc", "bbb", true, p3);
			User u4 = new User("a", "a", false, p4);
			User u5 = new User("b", "b", false, p5);
			User u6 = new User("Virus", "aaaaaaaa", false, p6);
			User u7 = new User("Lemon", "12345678", false, p7);
			User u8 = new User("George11", "bbb", false, p8);
			User u9 = new User("Dog", "dddddddd", false, p9);
			User u10 = new User("Maria", "aaffgghh", false,p10);
			User u11 = new User("xx", "xx", false, p11);
			User u12 = new User("ggwp", "gggggggg", false,p12);
			User u13 = new User("hello", "hhhhhhh", false, p13);
			User u14 = new User("r", "rrrrrrr", false, p14);
			User u15 = new User("Grandpa", "bbbbbbbb", false,p15);
			User u16 = new User("Antonio", "bbb", false, p16);
			User u17 = new User("Carl", "bbb", false, p17);


			db.persist(u1);
			db.persist(u2);
			db.persist(u3);
			db.persist(u4);
			db.persist(u5);
			db.persist(u6);
			db.persist(u7);
			db.persist(u8);
			db.persist(u9);
			db.persist(u10);
			db.persist(u11);
			db.persist(u12);
			db.persist(u13);
			db.persist(u14);
			db.persist(u15);
			db.persist(u16);
			db.persist(u17);

			db.persist(q1);
			db.persist(q2);
			db.persist(q3);
			db.persist(q4);
			db.persist(q5);
			db.persist(q6);

			db.persist(liga);
			db.persist(premier);
			db.persist(serieA);
			db.persist(ligue_1);
			db.persist(bundesliga);

			db.persist(ev1);
			db.persist(ev2);
			db.persist(ev3);
			db.persist(ev4);
			db.persist(ev5);
			db.persist(ev6);
			db.persist(ev7);
			db.persist(ev8);
			db.persist(ev9);
			db.persist(ev10);
			db.persist(ev11);
			db.persist(ev12);
			db.persist(ev13);
			db.persist(ev14);
			db.persist(ev15);
			db.persist(ev16);
			db.persist(ev17);
			db.persist(ev18);
			db.persist(ev19);
			db.persist(ev20);			
			db.persist(ev21);

			db.getTransaction().commit();
			System.out.println("Db initialized");
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * This method creates a question for an event, with a question text and the minimum bet
	 * 
	 * @param event to which question is added
	 * @param question text of the question
	 * @param betMinimum minimum quantity of the bet
	 * @return the created question, or null, or an exception
	 * @throws QuestionAlreadyExist if the same question already exists for the event
	 */
	public Question createQuestion(Event event, String question, float betMinimum,List<Prediction> predicitons) throws  QuestionAlreadyExist {
		System.out.println(">> DataAccess: createQuestion=> event= "+event+" question= "+question+" betMinimum="+betMinimum);

		Event ev = db.find(Event.class, event.getEventNumber());

		if (ev.DoesQuestionExists(question)) throw new QuestionAlreadyExist(ResourceBundle.getBundle("Etiquetas").getString("ErrorQueryAlreadyExist"));

		db.getTransaction().begin();
		Question q = ev.addQuestion(question, betMinimum,predicitons);

		db.persist(ev); // db.persist(q) not required when CascadeType.PERSIST is added in questions property of Event class
		// @OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
		db.getTransaction().commit();
		return q;
	}

	/**
	 * This method retrieves from the database the events of a given date 
	 * 
	 * @param date in which events are retrieved
	 * @return collection of events
	 */
	public Vector<Event> getEvents(Date date) {
		System.out.println(">> DataAccess: getEvents");
		Vector<Event> res = new Vector<Event>();	
		TypedQuery<Event> query = db.createQuery("SELECT ev FROM Event ev WHERE ev.eventDate=?1",Event.class);   
		query.setParameter(1, date);
		List<Event> events = query.getResultList();
		for (Event ev:events){	 
			res.add(ev);
		}
		return res;
	}

	/**
	 * This method retrieves from the database the events of a given date  and sport
	 * 
	 * @param date in which events are retrieved
	 * @param sport to look for
	 * @return collection of events
	 */
	public Vector<Event> getEvents(Date date, Sport sport) {
		System.out.println(">> DataAccess: getEvents");
		Vector<Event> res = new Vector<Event>();	
		TypedQuery<Event> query = db.createQuery("SELECT ev FROM Event ev WHERE ev.eventDate=?1 AND ev.sport=?2",Event.class);   
		query.setParameter(1, date);
		query.setParameter(2, sport);
		List<Event> events = query.getResultList();
		for (Event ev:events){	 
			res.add(ev);
		}
		return res;
	}


	/**
	 * This method retrieves from the database the dates a month for which there are events
	 * 
	 * @param date of the month for which days with events want to be retrieved 
	 * @return collection of dates
	 */
	public Vector<Date> getEventsMonth(Date date) {
		System.out.println(">> DataAccess: getEventsMonth");
		Vector<Date> res = new Vector<Date>();	

		Date firstDayMonthDate= UtilDate.firstDayMonth(date);
		Date lastDayMonthDate= UtilDate.lastDayMonth(date);


		TypedQuery<Date> query = db.createQuery("SELECT DISTINCT ev.eventDate FROM Event ev WHERE ev.eventDate BETWEEN ?1 and ?2",Date.class);   
		query.setParameter(1, firstDayMonthDate);
		query.setParameter(2, lastDayMonthDate);
		List<Date> dates = query.getResultList();
		for (Date d:dates){	 
			res.add(d);
		}
		return res;
	}

	/**
	 * This method retrieves from the database the dates a month for which there are events of the given competition
	 * 
	 * @param date of the month for which days with events want to be retrieved 
	 * @param competition to look for
	 * @return collection of dates
	 */
	public Vector<Date> getEventsMonth(Date date, Competition competition) {
		System.out.println(">> DataAccess: getEventsMonth");
		Vector<Date> res = new Vector<Date>();	

		Date firstDayMonthDate= UtilDate.firstDayMonth(date);
		Date lastDayMonthDate= UtilDate.lastDayMonth(date);


		TypedQuery<Date> query = db.createQuery("SELECT DISTINCT ev.eventdate FROM Event ev WHERE ev.eventdate BETWEEN ?1 and ?2 AND ev.competition=?3",Date.class);   
		query.setParameter(1, firstDayMonthDate);
		query.setParameter(2, lastDayMonthDate);
		query.setParameter(3, competition);
		List<Date> dates = query.getResultList();
		for (Date d:dates){	 
			res.add(d);
		}
		return res;
	}

	/**
	 * This method retrieves from the database the competitions for the given sport
	 * 
	 * @param sport  Sport for which competitions are retrieved
	 * @return	collection of Competitions
	 */
	public Vector<Competition> getCompetitions(Sport sport) {
		System.out.println(">> DataAccess: getEvents");
		Vector<Competition> res = new Vector<Competition>();	
		TypedQuery<Competition> query = db.createQuery("SELECT com FROM Competition com WHERE com.sport=?1",Competition.class);   
		query.setParameter(1, sport);
		List<Competition> events = query.getResultList();
		for (Competition ev:events){	 
			res.add(ev);
		}
		return res;
	}

	/**
	 * This method registers a new user in the database.
	 * 
	 * @param iD				ID of the new user.
	 * @param password			password of the new user.
	 * @param name				name of the new user.
	 * @param surname			surname of the new user.
	 * @param email				email of the new user.
	 * @param isAdmin			whether this user has admin. privileges or not.
	 *  
	 * @return					the newly created User object.
	 * @throws invalidID		exception thrown when there is a pre existing user with this ID in the database.
	 */
	public User registerUser(String iD, String password, String name, String surname, String email, String addr, String phn, 
			Country nat,String city, Date birthdt, String pic, boolean isAdmin ) throws invalidID{

		if(db.find(User.class, iD) != null) {throw new invalidID("This ID is taken");}

		db.getTransaction().begin();
		Profile p = new Profile(iD, name, surname, email, addr, phn, nat, city, birthdt, pic);
		User u = new User(iD, password,isAdmin, p);
		db.persist(u);
		db.persist(p);
		db.getTransaction().commit();
		return u;

	}

	/**
	 * This methods checks the validity of the credentials (id / password) inputed upon login.
	 * @param ID			ID of the presumed user.
	 * @param pw			password of the presumed user.
	 * 
	 * @return				boolean indicating privilege level of the user( true:Admin, false:Regular user)
	 * @throws invalidID	exception thrown when no user entity with the input ID exists in the database.
	 */
	public User retrieveUser(String ID, String pw) throws invalidID, invalidPW {
		User u = db.find(User.class, ID);
		if(u == null) {
			throw new invalidID("ID does not correspond to a registered user");
		}
		else if(!u.getPassword().equals(pw)) {
			throw new invalidPW("Incorrect password");
		}
		else{
			db.getTransaction().begin();
			u.setLastlogin(new Date());
			db.getTransaction().commit();
			return u;
		}
	}

	/**
	 * 
	 * @param iD
	 * @param password
	 * @param name
	 * @param surname
	 * @param email
	 * @return 
	 */
	public List<User> retrieveUsersByCriteria(String searchtext, String filter, boolean casesensitive, int match) {
		List<User> searchResult = new ArrayList<User>();
		TypedQuery<User> query = null;
		if(searchtext.equals("")) {
			query = db.createQuery("SELECT u FROM User u", User.class);
		}
		else {
			//Non string cases
			if(filter.equals("Nationality")) {
				if(!casesensitive) {
					searchtext = Character.toUpperCase(searchtext.charAt(0)) + searchtext.substring(1);
				}
				query = db.createQuery("SELECT u FROM User u,Profile p WHERE p." + filter.toLowerCase() + " =?1 AND u.id = p.id", User.class);
				query.setParameter(1, Country.getValue(searchtext));
			}
			else if(filter.equals("Birthdate")) {
				SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
				query = db.createQuery("SELECT u FROM User u,Profile p WHERE p." + filter.toLowerCase() + " =?1 AND u.id = p.id", User.class);
				try {
					query.setParameter(1, df.parse(searchtext));
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
			//basic string cases
			else {
				filter = filter.replaceAll(" ", "");
				String pattern = null;
				if(casesensitive) {
					pattern = " p." + filter.toLowerCase();
				}
				else {
					pattern = " LOWER(p." + filter.toLowerCase() + ") ";
					searchtext = searchtext.toLowerCase();
				}
				
				if(match == 0) {
					pattern = pattern + " = \"" + searchtext +"\"";
				}
				else if(match == 1) {
					pattern = pattern +" LIKE '" + searchtext + "%'";
				}
				else if(match == 2) {
					pattern = pattern + " LIKE '%" + searchtext + "%'";
				}
				query = db.createQuery("SELECT u FROM User u, Profile p WHERE" + pattern +" AND u.id = p.id", User.class);
			}
		}
		searchResult = query.getResultList();
		return searchResult;
	}

	/**
	 * 
	 * @param iD
	 * @return
	 */
	public void removeUser(String iD) {
		User u = db.find(User.class, iD);

		db.getTransaction().begin();
		TypedQuery query = db.createQuery("DELETE FROM User user where id ="+"'"+ u.getID()+"'",User.class);
		query.executeUpdate();
		db.getTransaction().commit();
		System.out.println(iD + " has been deleted");
	}

	/**
	 * 
	 * @param iD
	 * @param name
	 * @param surname
	 * @param email
	 * @param isAdmin
	 */
	public void updateUserInfo(String key, String iD, String name, String surname, String email, String addr, 
			String phn, Country nat,String city, Date birthdt, boolean isAdmin) throws invalidID{

		User u = db.find(User.class, iD);
		//check if there is an existing user for the new ID
		if(!key.equals(iD)) {
			if(u != null) {
				throw new invalidID();
			}
			else {
				u = db.find(User.class, key);
				db.getTransaction().begin();
				db.remove(u);
				Profile p = new Profile(iD,name,surname,email, addr, phn, nat, city, birthdt);	
				User w = new User(iD,u.getPassword(),isAdmin,p);
				db.persist(w);
				db.getTransaction().commit();
			}
		}
		else {
			u = db.find(User.class, key);
			db.getTransaction().begin();
			Profile p = u.getProfile();
			p.setName(name);
			p.setSurname(surname);
			p.setEmail(email);
			p.setAddress(addr);
			p.setPhonenumber(phn);
			p.setNationality(nat);
			p.setCity(city);
			p.setBirthdate(birthdt);
			u.setAdmin(isAdmin);
			db.getTransaction().commit();
		}
		System.out.println(iD + " has been updated");
	}

	public void recordBets(User bettor, float stake, BetType type, List<Prediction> predictions){
		User u = db.find(User.class, bettor.getID());
		List<Prediction> predictionInstances = new ArrayList<Prediction>();
		for(Prediction pred : predictions){
			TypedQuery<Prediction> query = db.createQuery("SELECT p FROM Prediction p WHERE p.question =?1 AND p.answer =?2" , Prediction.class);
			query.setParameter(1, pred.getQuestion());
			query.setParameter(2, pred.getAnswer());
			List<Prediction> temp = query.getResultList();
			for(Prediction t : temp) {
				predictionInstances.add(t);
			}	
		}
		db.getTransaction().begin();
		Bet b = new Bet(bettor, stake, type, predictionInstances);
		u.addBet(b);
		db.persist(b);
		db.getTransaction().commit();
		System.out.println("Bet placed sucessfully");
	}

	public void close(){
		db.close();
		emf.close();
		System.out.println("DataBase closed");
	}


	public float addCash(String ID, float addition) {
		User u = db.find(User.class, ID);
		db.getTransaction().begin();
		float updatedcash = u.getCash()+addition;
		u.setCash(updatedcash);
		db.getTransaction().commit();
		System.out.println("Cash added sucessfully");	
		return updatedcash;
	}

	public void storeFeedback(FeedbackType fbtype, String email, String name, String summary, String details, File file) {
		db.getTransaction().begin();
		Feedback fb = new Feedback(fbtype, email, name, summary, details, file);
		db.persist(fb);
		db.getTransaction().commit();
		System.out.println("Feedback sent sucessfully");
	}

	public List<Prediction> getQuestionPredictions(int questionId) throws QuestionNotFound,NoAnswers {
		Question q = db.find(Question.class, questionId);
		if (q == null) {
			throw new QuestionNotFound();
		}else {
			List<Prediction> predictions = q.getPredictions();
			if (predictions == null || predictions.size() == 0) {
				throw new NoAnswers("There is no Answer for the selected Question");
			}else {
				return predictions;
			}
		}
	}
	public ArrayList<Bet> getBets(User bettor){
		User u = db.find(User.class, bettor.getID());
		TypedQuery q1 = db.createQuery("select user FROM User user where id ="+"'"+ u.getID()+"'",User.class);
		List<User> results = q1.getResultList();
        ArrayList<Bet> bets =  results.get(0).getBets();
		System.out.println("Bet reterived sucessfully");
		return bets;
	}

	@Override
	public void remove_bet(User bettor, Bet bet) {
		User u = db.find(User.class, bettor.getID());

	    db.getTransaction().begin();
		
		u.remove_bet(bet);
	  //   db.remove(bet);
//		db.pers
		db.getTransaction().commit();
		System.out.println("Bet removed sucessfully");
		
	}

	@Override
	public void updatebets(User bettor, Bet bet, float amount) {
		User u = db.find(User.class, bettor.getID());
		ArrayList<Bet> bets=u.getBets();
		Bet aux=null;
		for (Bet b:bets) {
			if (b.getBetNumber()==bet.getBetNumber()) {
				 aux=b;
				 break;
			}
		}
if (aux!=null) {
	    db.getTransaction().begin();
		
		aux.setAmount(amount);
		db.persist(aux);
		db.getTransaction().commit();
		System.out.println("Bet update sucessfully");
	}
		
	}
}
