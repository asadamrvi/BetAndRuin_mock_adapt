package dataAccess;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TemporalType;
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
import domain.CreditCard;
import domain.Profile;
import domain.Question;
import domain.Sport;
import domain.User;
import exceptions.NoAnswers;
import exceptions.QuestionAlreadyExist;
import exceptions.QuestionNotFound;
import exceptions.invalidID;
import exceptions.invalidPW;
import gui.Panels.CreateQuestionPanel;

/**
 * It implements the data access to the objectDb database
 */
public class DataAccessImplementation implements DataAccess {
	
	protected static EntityManagerFactory emf;
	private String fileName;
	ConfigXML c;
	
	public DataAccessImplementation(boolean initializeMode)  {

		c=ConfigXML.getInstance();

		System.out.println("Creating DataAccess instance => isDatabaseLocal: "+c.isDatabaseLocal()+" getDatabBaseOpenMode: "+c.getDataBaseOpenMode());

		fileName=c.getDbFilename();
		if (initializeMode)
			fileName=fileName+";drop";

		if (c.isDatabaseLocal()) {
			emf = Persistence.createEntityManagerFactory("objectdb:"+fileName);
		} else {
			Map<String, String> properties = new HashMap<String, String>();
			properties.put("javax.persistence.jdbc.user", c.getUser());
			properties.put("javax.persistence.jdbc.password", c.getPassword());

			emf = Persistence.createEntityManagerFactory("objectdb://"+c.getDatabaseNode()+":"+c.getDatabasePort()+"/"+fileName, properties);
		}
	}

	public DataAccessImplementation()  {	
		new DataAccessImplementation(false);
	}

	public EntityManager createEntityManager() {
        if (emf == null) {
            throw new IllegalStateException("Entity manager factory is not initialized yet.");
        }
        return emf.createEntityManager();
	}

	/**
	 * This is the data access method that initializes the database with some events and questions.
	 * This method is invoked by the business logic (constructor of BLFacadeImplementation) when the option "initialize" is declared in the tag dataBaseOpenMode of resources/config.xml file
	 */	
	public void initializeDB(){
		EntityManager db = createEntityManager();
		db.getTransaction().begin();
		try {
			Calendar today = Calendar.getInstance();

			int month=today.get(Calendar.MONTH);
			month+=1;
			@SuppressWarnings("unused")
			int year=today.get(Calendar.YEAR);
			if (month==12) { month=0; year+=1;}  

			SimpleDateFormat df = new SimpleDateFormat("HH:mm dd/MM/yyyy");

			Event ev1=new Event(1, "Atlético-Athletic", df.parse("12:00 17/4/2020"),df.parse("13:45 17/4/2020"),Sport.FOOTBALL);
			Event ev2=new Event(2, "Eibar-Barcelona", df.parse("15:00 03/4/2020"),df.parse("16:45 3/4/2020"),Sport.FOOTBALL);
			Event ev3=new Event(3, "Getafe-Celta", df.parse("18:00 11/5/2020"),df.parse("19:45 11/5/2020"),Sport.FOOTBALL);
			Event ev4=new Event(4, "Alavés-Deportivo", df.parse("20:00 25/4/2020"),df.parse("21:45 25/4/2020"),Sport.FOOTBALL);
			Event ev5=new Event(5, "Español-Villareal",df.parse("22:00 25/4/2020"),df.parse("23:45 25/4/2020"),Sport.FOOTBALL);
			Event ev6=new Event(6, "Las Palmas-Sevilla",df.parse("16:00 17/4/2020"),df.parse("17:45 17/4/2020"),Sport.FOOTBALL);
			Event ev7=new Event(7, "Malaga-Valencia", df.parse("18:00 18/4/2020"),df.parse("19:45 18/4/2020"),Sport.FOOTBALL);
			Event ev8=new Event(8, "Girona-Leganés",df.parse("15:00 10/4/2020"),df.parse("16:45 10/4/2020"),Sport.FOOTBALL);
			Event ev9=new Event(9, "Real Sociedad-Levante", df.parse("22:00 10/4/2020"),df.parse("23:45 10/4/2020"),Sport.FOOTBALL);
			Event ev10=new Event(10, "Betis-Real Madrid", df.parse("14:00 11/4/2020"),df.parse("15:45 11/4/2020"),Sport.FOOTBALL);

			Event ev11=new Event(11, "Atlético-Athletic", df.parse("17:00 2/5/2020"),df.parse("18:45 2/5/2020"),Sport.FOOTBALL);
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

			Event ev27=new Event(27, "Miami Heat-Lakers",df.parse("18:30 12/4/2020"),df.parse("20:00 12/4/2020"),Sport.BASKETBALL);
			Event ev28=new Event(28, "Boston Celtics-Toronto Raptors",  df.parse("15:30 16/5/2020"),df.parse("17:15 16/5/2020"),Sport.BASKETBALL);
			Event ev29=new Event(29, "San Antonio Spurs-Houston Rockets", df.parse("18:00 17/5/2020"),df.parse("19:45 17/5/2020"),Sport.BASKETBALL);
			Event ev30=new Event(30, "Golden State Warrions-Chicago Bulls", df.parse("15:00 25/5/2020"),df.parse("16:45 25/5/2020"),Sport.BASKETBALL);
			Event ev31=new Event(31, "New York Knicks-Milwakee Bucks", df.parse("18:00 25/5/2020"),df.parse("19:45 25/5/2020"),Sport.BASKETBALL);
			Event ev32=new Event(32, "Cleveland Cavaliers-Dallas Mavericks", df.parse("14:00 17/4/2020"),df.parse("15:45 17/4/2020"),Sport.BASKETBALL);
			
			Event ev33=new Event(33, "Saski Baskonia-Unicaja Baloncesto",df.parse("12:00 25/4/2020"),df.parse("13:45 25/4/2020"),Sport.BASKETBALL);
			Event ev34=new Event(34, "Real Madrid Baloncesto-Basket Zaragoza",  df.parse("15:30 26/4/2020"),df.parse("17:15 26/4/2020"),Sport.BASKETBALL);
			Event ev35=new Event(35, "Valencia Basket-FC Barcelona Baloncesto", df.parse("18:00 25/4/2020"),df.parse("19:45 25/4/2020"),Sport.BASKETBALL);
			Event ev36=new Event(36, "Baloncesto Estudiantes-Joventut Badalona", df.parse("15:00 25/4/2020"),df.parse("16:45 25/4/2020"),Sport.BASKETBALL);
			Event ev37=new Event(37, "Club Baloncesto Murcia-Bàsquet Manresa", df.parse("18:00 25/4/2020"),df.parse("19:45 25/4/2020"),Sport.BASKETBALL);
		
			
			

			Competition liga = new Competition("LaLiga Santander", Country.ES, Sport.FOOTBALL, new Date(), new Date());
			Competition bundesliga = new Competition("Bundesliga", Country.DE, Sport.FOOTBALL, new Date(), new Date());
			Competition ligue_1 = new Competition("Ligue 1", Country.FR, Sport.FOOTBALL, new Date(), new Date());
			Competition serieA = new Competition("Serie A", Country.IT, Sport.FOOTBALL, new Date(), new Date());
			Competition premier = new Competition("Premier League", Country.GB, Sport.FOOTBALL, new Date(), new Date());
			Competition nba = new Competition("NBA", Country.US, Sport.BASKETBALL, new Date(), new Date());
			Competition endesa = new Competition("Liga Endesa", Country.ES, Sport.BASKETBALL, new Date(), new Date());
			Competition openaus = new Competition("Australia Open", Country.AU, Sport.TENNIS, new Date(), new Date());
			Competition frenchopen = new Competition("Roland Garros", Country.FR, Sport.TENNIS, new Date(), new Date());
			Competition wimbledon = new Competition("Wimbledon", Country.GB, Sport.TENNIS, new Date(), new Date());
			Competition usopen = new Competition("US Open", Country.US, Sport.TENNIS, new Date(), new Date());
			
///////////////////// TEST ////////////////////////
			
			Event test = new Event(50, "test1-test2",  df.parse("16:00 24/4/2020"),df.parse("18:11 24/4/2020"),Sport.FOOTBALL);
 
			Question testq = test.addQuestion("???", 4);
			testq.addPrediction("yes", Float.valueOf((float) 1.7));
			testq.addPrediction("no", Float.valueOf((float) 1.3));
			Question testqq = test.addQuestion("???", 4);
			testqq.addPrediction("yes", Float.valueOf((float) 1.7));
			testqq.addPrediction("no", Float.valueOf((float) 1.3));
			db.persist(test);
			liga.addEvent(test);
			
			Event test1 = new Event(51, "test3-test4",  df.parse("16:00 24/4/2020"),df.parse("18:11  24/4/2020"),Sport.FOOTBALL);
			 
			Question testq1 = test1.addQuestion("???", 4);
			testq1.addPrediction("yes", Float.valueOf((float) 1.7));
			testq1.addPrediction("no", Float.valueOf((float) 1.3));
			Question testqq1 = test1.addQuestion("???", 4);
			testqq1.addPrediction("yes", Float.valueOf((float) 1.7));
			testqq1.addPrediction("no", Float.valueOf((float) 1.3));
			db.persist(test1);
			liga.addEvent(test1);
			
			Event test2 = new Event(52, "test5-test6",  df.parse("16:00 24/4/2020"),df.parse("18:11 24/4/2020"),Sport.FOOTBALL);
			 
			Question testq2 = test2.addQuestion("???", 4);
			testq2.addPrediction("yes", Float.valueOf((float) 1.7));
			testq2.addPrediction("no", Float.valueOf((float) 1.3));
			db.persist(test2);
			liga.addEvent(test2);
			
			Event test3 = new Event(53, "test7-test8",  df.parse("16:00 24/4/2020"),df.parse("18:11 24/4/2020"),Sport.FOOTBALL);
			 
			Question testq3 = test3.addQuestion("???", 4);
			testq3.addPrediction("yes", Float.valueOf((float) 1.7));
			testq3.addPrediction("no", Float.valueOf((float) 1.3));
			db.persist(test3);
			liga.addEvent(test3);
			
			Event test4 = new Event(54, "test1-test2",  df.parse("16:00 24/4/2020"),df.parse("18:11 24/4/2020"),Sport.FOOTBALL);
			 
			Question testq4 = test4.addQuestion("???", 4);
			testq4.addPrediction("yes", Float.valueOf((float) 1.7));
			testq4.addPrediction("no", Float.valueOf((float) 1.3));
			db.persist(test4);
			liga.addEvent(test4);
			
			Event test5 = new Event(55, "test3-test4",  df.parse("16:00 24/4/2020"),df.parse("18:11 24/4/2020"),Sport.FOOTBALL);
			 
			Question testq5 = test5.addQuestion("???", 4);
			testq5.addPrediction("yes", Float.valueOf((float) 1.7));
			testq5.addPrediction("no", Float.valueOf((float) 1.3));
			db.persist(test5);
			liga.addEvent(test5);
			
			Event test6 = new Event(56, "test5-test6",  df.parse("16:00 24/4/2020"),df.parse("18:11 24/4/2020"),Sport.FOOTBALL);
			 
			Question testq6 = test6.addQuestion("???", 4);
			testq6.addPrediction("yes", Float.valueOf((float) 1.7));
			testq6.addPrediction("no", Float.valueOf((float) 1.3));
			db.persist(test6);
			liga.addEvent(test6);
			
			Event test7 = new Event(57, "test7-test8",  df.parse("16:00 24/4/2020"),df.parse("18:11  24/4/2020"),Sport.FOOTBALL);
			 
			Question testq7 = test7.addQuestion("???", 4);
			testq7.addPrediction("yes", Float.valueOf((float) 1.7));
			testq7.addPrediction("no", Float.valueOf((float) 1.3));
			db.persist(test7);
			liga.addEvent(test7);
			
//////////////////////////////////////////////////////////////////////
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
			liga.addEvent(ev19);
			liga.addEvent(ev20);
			serieA.addEvent(ev22);
			ligue_1.addEvent(ev23);
			bundesliga.addEvent(ev24);
			premier.addEvent(ev25);
			premier.addEvent(ev26);
			nba.addEvent(ev27);
			nba.addEvent(ev28);
			nba.addEvent(ev29);
			nba.addEvent(ev30);
			nba.addEvent(ev31);
			nba.addEvent(ev32);
			endesa.addEvent(ev33);
			endesa.addEvent(ev34);
			endesa.addEvent(ev35);
			endesa.addEvent(ev36);
			endesa.addEvent(ev37);
			
			Question q1;
			Question q2;
			Question q3;
			Question q4;
			Question q5;
			Question q6;
			Question q7;
			Question q8;
			Question q9;
			Question q10;
			Question q11;
			Question q12;

			if (Locale.getDefault().equals(new Locale("es"))) {
				q1=ev1.addQuestion("¿Quién ganará el partido?",4);
				q2=ev1.addQuestion("¿Quién meterá el primer gol?",2);
				q3=ev11.addQuestion("¿Quién ganará el partido?",1);
				q4=ev11.addQuestion("¿Cuántos goles se marcarán?",2);
				q5=ev17.addQuestion("¿Quién ganará el partido?",1);
				q6=ev17.addQuestion("¿Habrá goles en la primera parte?",2);
				q7=ev2.addQuestion("¿Quién ganará el partido?",3);
				q8=ev3.addQuestion("¿Quién meterá el primer gol?",3);
				q9=ev4.addQuestion("¿Quién ganará el partido?",1);
				q10=ev5.addQuestion("¿Cuántos goles se marcarán?",2);
				q11=ev6.addQuestion("¿Quién ganará el partido?",1);
				q12=ev6.addQuestion("¿Habrá goles en la primera parte?",2);
			}
			else if (Locale.getDefault().equals(new Locale("en"))) {
				q1=ev1.addQuestion("Who will win the match?",4);
				q2=ev1.addQuestion("Who will score first?",2);
				q3=ev11.addQuestion("Who will win the match?",1);
				q4=ev11.addQuestion("How many goals will be scored in the match?",2);
				q5=ev17.addQuestion("Who will win the match?",1);
				q6=ev17.addQuestion("Will there be goals in the first half?",2);
				q7=ev2.addQuestion("Who will win the match?",3);
				q8=ev3.addQuestion("Who will score first?",3);
				q9=ev4.addQuestion("Who will win the match?",1);
				q10=ev5.addQuestion("How many goals will be scored in the match?",2);
				q11=ev6.addQuestion("Who will win the match?",1);
				q12=ev6.addQuestion("Will there be goals in the first half?",2);
			}			
			else {
				q1=ev1.addQuestion("Zeinek irabaziko du partidua?",4);
				q2=ev1.addQuestion("Zeinek sartuko du lehenengo gola?",2);
				q3=ev11.addQuestion("Zeinek irabaziko du partidua?",1);
				q4=ev11.addQuestion("Zenbat gol sartuko dira?",2);
				q5=ev17.addQuestion("Zeinek irabaziko du partidua?",1);
				q6=ev17.addQuestion("Golak sartuko dira lehenengo zatian?",2);
				q7=ev2.addQuestion("Zeinek irabaziko du partidua?",3);
				q8=ev3.addQuestion("Zeinek sartuko du lehenengo gola?",3);
				q9=ev4.addQuestion("Zeinek irabaziko du partidua?",1);
				q10=ev5.addQuestion("Zenbat gol sartuko dira?",2);
				q11=ev6.addQuestion("Zeinek irabaziko du partidua?",1);
				q12=ev6.addQuestion("Golak sartuko dira lehenengo zatian?",2);
			}
			q1.addPrediction("Atlético", Float.valueOf((float) 1.4));
			q1.addPrediction("X", Float.valueOf((float) 1.5));
			q1.addPrediction("Athletic", Float.valueOf((float) 1.8));
			q2.addPrediction("João Félix", Float.valueOf((float) 1.3));
			q2.addPrediction("Álvaro Morata", Float.valueOf((float) 1.5));
			q2.addPrediction("Diego Costa", Float.valueOf((float) 2.5));
			q2.addPrediction("Marcos Llorente", Float.valueOf((float) 2.2));
			q2.addPrediction("Raúl García", Float.valueOf((float) 2.2));
			q2.addPrediction("Iñaki Williams", Float.valueOf((float) 2.1));
			q2.addPrediction("Iker Muniain", Float.valueOf((float) 1.5));
			q2.addPrediction("Aritz Adúriz", Float.valueOf((float) 1.3));
			q3.addPrediction("Atlético", Float.valueOf((float) 1.8));
			q3.addPrediction("Athletic", Float.valueOf((float) 2.4));
			q4.addPrediction("0", Float.valueOf((float) 2.0));
			q4.addPrediction("1", Float.valueOf((float) 2.0));
			q4.addPrediction("2", Float.valueOf((float) 2.0));
			q4.addPrediction("3", Float.valueOf((float) 2.0));
			q4.addPrediction("4", Float.valueOf((float) 2.0));
			q4.addPrediction("5", Float.valueOf((float) 2.0));
			q5.addPrediction("Málaga", Float.valueOf((float) 2.0));
			q5.addPrediction("X", Float.valueOf((float) 1.4));
			q5.addPrediction("Valencia", Float.valueOf((float) 1.4));
			q6.addPrediction("Yes", Float.valueOf((float) 1.5));
			q6.addPrediction("No", Float.valueOf((float) 2.5));
			q7.addPrediction("Eibar", Float.valueOf((float) 2.5));
			q7.addPrediction("X", Float.valueOf((float) 1.9));
			q7.addPrediction("Barcelona", Float.valueOf((float) 1.2));
			q8.addPrediction("No goals", Float.valueOf((float) 2.1));
			q8.addPrediction("Getafe", Float.valueOf((float) 2.1));
			q8.addPrediction("Celta", Float.valueOf((float) 1.5));
			q9.addPrediction("Alaves", Float.valueOf((float) 1.3));
			q9.addPrediction("Deportivo", Float.valueOf((float) 1.8));
			q9.addPrediction("Tie", Float.valueOf((float) 1.7));
			q10.addPrediction("0", Float.valueOf((float) 1.7));
			q10.addPrediction("1", Float.valueOf((float) 1.6));
			q10.addPrediction("2", Float.valueOf((float) 1.5));
			q10.addPrediction("3", Float.valueOf((float) 1.6));
			q10.addPrediction("4", Float.valueOf((float) 2.0));
			q10.addPrediction("5", Float.valueOf((float) 2.5));
			q11.addPrediction("Las Palmas", Float.valueOf((float) 2.1));
			q11.addPrediction("X", Float.valueOf((float) 1.5));
			q11.addPrediction("Sevilla", Float.valueOf((float) 1.3));
			q12.addPrediction("Yes", Float.valueOf((float) 2.1));
			q12.addPrediction("No", Float.valueOf((float) 2.1));

			today.set(Calendar.HOUR_OF_DAY, 0);
			today.set(Calendar.MINUTE, 0);
			today.set(Calendar.SECOND, 0);
			today.set(Calendar.MILLISECOND, 0);

			Profile p1 = new Profile("754888121A" , "Julen", "Urroz", "testemail@gmail.com", "testaddr", "+34 688689414", Country.ES, "Zarautz",today.getTime() , "images/profilepic/testprofile.png");
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

			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.YEAR, 2020);
			cal.set(Calendar.MONTH, 8);
					
			CreditCard card1 = new CreditCard("5412753456789010", cal.getTime());
			CreditCard card2 = new CreditCard("5412753456789011", cal.getTime());
			
			
			u1.addCreditCard(card1);
			u1.addCreditCard(card2);
			u1.setDefaultCreditCard(card1);
			
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
			db.persist(q7);
			db.persist(q8);
			db.persist(q9);
			db.persist(q10);
			db.persist(q11);
			db.persist(q12);

			db.persist(liga);
			db.persist(premier);
			db.persist(serieA);
			db.persist(ligue_1);
			db.persist(bundesliga);
			db.persist(nba);
			db.persist(endesa);
			db.persist(openaus);
			db.persist(frenchopen);
			db.persist(wimbledon);
			db.persist(usopen);


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
			db.persist(ev27);
			
			db.getTransaction().commit();
			db.close();
			System.out.println("Db initialized");
		}
		catch (Exception e){
			e.printStackTrace();
			db.close();
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
	public Question createQuestion(Event event, String question, float betMinimum,List<Prediction> predictions) throws  QuestionAlreadyExist {
		System.out.println(">> DataAccess: createQuestion=> event= "+event+" question= "+question+" betMinimum="+betMinimum);
		EntityManager db = createEntityManager();
		Event ev = db.find(Event.class, event.getEventNumber());

		if (ev.DoesQuestionExists(question)) throw new QuestionAlreadyExist(ResourceBundle.getBundle("Etiquetas").getString("ErrorQueryAlreadyExist"));

		db.getTransaction().begin();

		Question q = ev.addQuestion(question, betMinimum, predictions);
		db.persist(ev); // db.persist(q) not required when CascadeType.PERSIST is added in questions property of Event class
		
		db.getTransaction().commit();
		db.close();
		return q;
	}

	/**
	 * This method retrieves from the database the events of a given date 
	 * 
	 * @param date in which events are retrieved
	 * @return collection of events
	 */
	public Vector<Event> getEvents(Date date) {
		EntityManager db = createEntityManager();
		System.out.println(">> DataAccess: getEvents");
		Vector<Event> res = new Vector<Event>();	
		TypedQuery<Event> query = db.createQuery("SELECT ev FROM Event ev WHERE ev.eventdate=?1",Event.class);   
		query.setParameter(1, date);
		List<Event> events = query.getResultList();
		for (Event ev:events){	 
			res.add(ev);
		}
		db.close();
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
		EntityManager db = createEntityManager();
		System.out.println(">> DataAccess: getEvents");
		Vector<Event> res = new Vector<Event>();	
		TypedQuery<Event> query = db.createQuery("SELECT ev FROM Event ev WHERE ev.eventDate=?1 AND ev.sport=?2",Event.class);   
		query.setParameter(1, date);
		query.setParameter(2, sport);
		List<Event> events = query.getResultList();
		for (Event ev:events){	 
			res.add(ev);
		}
		db.close();
		return res;
	}


	/**
	 * This method retrieves from the database the live events. Live events are defined as those for whom the current date(and time) is between the event starting and
	 * ending dates.
	 * 
	 * @return collection of events
	 */
	public Vector<Event> getLiveEvents(){
		EntityManager db = createEntityManager();
		System.out.println(">> DataAccess: getLiveEvents");
		Vector<Event> res = new Vector<Event>();	
		TypedQuery<Event> query = db.createQuery("SELECT ev FROM Event ev WHERE ev.eventdate < ?1  AND ev.endingdate > ?1",Event.class);   
		query.setParameter(1, new Date());
		List<Event> events = query.getResultList();
		for (Event ev:events){	 
			res.add(ev);
		}
		db.close();
		return res;
	}


	/**
	 * This method retrieves from the database events that are scheduled to start between the two input dates.
	 * 
	 * @param date1   lower bound date;
	 * @param date2   upper bound date;
	 * @return        collection of events;
	 */
	public Vector<Event> getEventsBetweenDates(Date date1, Date date2){
		EntityManager db = createEntityManager();
		Vector<Event> res = new Vector<Event>();	
		TypedQuery<Event> query = db.createQuery("SELECT ev FROM Event ev WHERE ev.eventdate BETWEEN ?1  AND ?2",Event.class);   
		query.setParameter(1, date1);
		query.setParameter(2, date2);
		List<Event> events = query.getResultList();
		for (Event ev:events){	 
			res.add(ev);
		}
		db.close();
		return res;
	}

	/**
	 * This method retrieves from the database the dates a month for which there are events
	 * 
	 * @param date of the month for which days with events want to be retrieved 
	 * @return collection of dates
	 */
	public Vector<Date> getEventsMonth(Date date) {
		EntityManager db = createEntityManager();
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
		EntityManager db = createEntityManager();
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
		db.close();
		return res;
	}

	/**
	 * This method retrieves from the database the competitions for the given sport
	 * 
	 * @param sport  Sport for which competitions are retrieved
	 * @return	collection of Competitions
	 */
	public Vector<Competition> getCompetitions(Sport sport) {
		EntityManager db = createEntityManager();
		System.out.println(">> DataAccess: getCompetitions");
		Vector<Competition> res = new Vector<Competition>();	
		TypedQuery<Competition> query = db.createQuery("SELECT com FROM Competition com WHERE com.sport=?1",Competition.class);   
		query.setParameter(1, sport);
		List<Competition> competitions  = query.getResultList();
		for (Competition comp:competitions){	 
			res.add(comp);
		}
		db.close();
		return res;
	}

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
	public User registerUser(String username,String ID, String password, String name, String surname, String email, String addr, String phn, 
			Country nat,String city, Date birthdt, String pic, boolean isAdmin ) throws invalidID{
		EntityManager db = createEntityManager();
		if(db.find(User.class, username) != null) {throw new invalidID("This username is taken");}

		db.getTransaction().begin();
		Profile p = new Profile(ID, name, surname, email, addr, phn, nat, city, birthdt, pic);
		User u = new User(username, password,isAdmin, p);
		db.persist(u);
		db.persist(p);
		db.getTransaction().commit();
		db.close();
		return u;

	}

	/**
	 *	This method updates the value of the password of the given user to the new value.
	 * 
	 * @param u					User to update password of
	 * @param newpass			new value the password should be updated to
	 * @return					true if update completes successfully, false if the new password and confirmation don't match
	 */
	public void updatePassword(User u, String newpass) {
		EntityManager db = createEntityManager();
		User user = db.find(User.class, u.getUsername());
		db.getTransaction().begin();
		user.setPassword(newpass);
		db.getTransaction().commit();
		db.close();
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
		EntityManager db = createEntityManager();
		User u = db.find(User.class, ID);
		if(u == null) {
			db.close();
			throw new invalidID("ID does not correspond to a registered user");
		}
		else if(!u.getPassword().equals(pw)) {
			db.close();
			throw new invalidPW("Incorrect password");
		}
		else{
			db.getTransaction().begin();
			u.setLastlogin(new Date());
			db.getTransaction().commit();
			db.close();
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
		EntityManager db = createEntityManager();
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
				query = db.createQuery("SELECT u FROM User u INNER JOIN u.profile Profile WHERE u.profile." + filter.toLowerCase() + " =?1", User.class);
				query.setParameter(1, Country.getValue(searchtext));
			}
			else if(filter.equals("Birthdate")) {
				SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
				query = db.createQuery("SELECT u FROM User u INNER JOIN u.profile Profile WHERE u.profile." + filter.toLowerCase() + " =?1", User.class);
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
				if(filter.equals("Username")) {
					if(casesensitive) {
						pattern = " u." + filter.toLowerCase();
					}
					else {
						pattern = " LOWER(u." + filter.toLowerCase() + ") ";
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
					query = db.createQuery("SELECT u FROM User u WHERE" + pattern, User.class);
					query.setParameter(1, Country.getValue(searchtext));
				}
				else {
					if(casesensitive) {
						pattern = " u.profile." + filter.toLowerCase();
					}
					else {
						pattern = " LOWER(u.profile." + filter.toLowerCase() + ") ";
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
					query = db.createQuery("SELECT u FROM User u INNER JOIN u.profile Profile WHERE" + pattern, User.class);
				}
				
				
			}
		}
		searchResult = query.getResultList();
		db.close();
		return searchResult;
	}

	/**
	 * 
	 * @param iD
	 * @return
	 */
	public void removeUser(String iD) {
		EntityManager db = createEntityManager();
		User u = db.find(User.class, iD);
		db.getTransaction().begin();
		TypedQuery<User> query = db.createQuery("DELETE FROM User user where id ="+"'"+ u.getUsername()+"'",User.class);
		query.executeUpdate();
		db.getTransaction().commit();
		db.close();
		System.out.println(iD + " has been deleted");
	}

	
	/**
	 * This stores the given credit card on the database with the owner set as the given user
	 * 
	 * @param number	Credit card number
	 * @param number	Credit card expiration date
	 */
	public void storeCreditCard(User user, CreditCard cc) {
		EntityManager db = createEntityManager();
		User u = db.find(User.class, user.getUsername());
		if(u != null) {
			db.getTransaction().begin();
			u.addCreditCard(cc);
			db.getTransaction().commit();
			db.close();
			System.out.println("Credit card: " + cc.getCardNumber() + " has been added to: " + user.getUsername());
		}
		else {
			System.out.println("User not found, could not store credit card");
		}
	}
	
	/**
	 * This method deletes the given credit card from the database
	 * 
	 * @param cc	CreditCard to delete
	 */
	public void removeCreditCard(CreditCard card) {
		EntityManager db = createEntityManager();
		CreditCard cc = db.find(CreditCard.class, card.getCardNumber());
		User owner = db.find(User.class, cc.getOwner().getUsername());
		if(cc != null) {
			db.getTransaction().begin();

			if(cc.equals(cc.getOwner().getDefaultCreditCard())) {
				owner.setDefaultCreditCard(null);
				owner.getCreditCards().remove(cc.getCardNumber());
			}
			else {
				TypedQuery<CreditCard> query = db.createQuery("DELETE FROM CreditCard cc WHERE cardNumber ="+"'"+ cc.getCardNumber()+"'", CreditCard.class);
				query.executeUpdate();
			}
			db.getTransaction().commit();
			db.close();
			System.out.println("Credit card: " + cc.getCardNumber() + " has been deleted");
		}
		else {
			System.out.println("Credit card not found");
		}
	}
	
	/**
	 * This method updates the default credit card value of the given user to the given credit card
	 * 
	 * @param user			User to update default card of
	 * @param defaultcc		New default credit card
	 */
	public void updateDefaultCreditCard(User user, CreditCard defaultcc) {
		EntityManager db = createEntityManager();
		User u = db.find(User.class,user.getUsername());
		CreditCard cc = db.find(CreditCard.class, defaultcc.getCardNumber());
		if(u != null) {
			db.getTransaction().begin();
			u.setDefaultCreditCard(cc);
			db.getTransaction().commit();
			db.close();
			user.setDefaultCreditCard(defaultcc);
		}
		
	}
	
	/**
	 * 
	 * @param username
	 * @param name
	 * @param surname
	 * @param email
	 * @param isAdmin
	 */
	public User updateUserInfo(String key, String username, String name, String surname, String email, String addr, 
			String phn, Country nat,String city, Date birthdt, boolean isAdmin) throws invalidID{
		EntityManager db = createEntityManager();
		User u = db.find(User.class, username);
		//check if there is an existing user for the new ID
		if(!key.equals(username)) {
			if(u != null) {
				throw new invalidID();
			}
			else {
				u = db.find(User.class, key);
				db.getTransaction().begin();
				db.remove(u);
				Profile p = new Profile(username,name,surname,email, addr, phn, nat, city, birthdt);	
				User w = new User(username,u.getPassword(),isAdmin,p);
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
		db.close();
		System.out.println(username + " has been updated");
		return u;

	}
	
	/**
	 * This method replaces the existing profile picture of the given user with the new picture.
	 * Only the pathname of the pictures are stored
	 * 
	 * @param p		Profile of the user to change the profile picture of
	 * @param path  Pathname of the file with the picture(must be .jpg or .png)
	 */
	public void updateProfilePic(Profile p, String path) {
		EntityManager db = createEntityManager();
		Profile profile = db.find(Profile.class, p);
		
		db.getTransaction().begin();
		profile.setProfilepic(path);
		db.getTransaction().commit();
		db.close();
	}
	

	public void recordBets(User bettor, float stake, float price, BetType type, List<Prediction> predictions){
		EntityManager db = createEntityManager();
		User u = db.find(User.class, bettor.getUsername());
		
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
		u.setCash(u.getCash()-price);
		db.persist(b);
		db.getTransaction().commit();
		db.close();
		System.out.println("Bet placed sucessfully");
	}
/*
	public void close(){
		db.close();
		System.out.println("DataBase closed");
	}
*/

	public float addCash(String ID, float addition) {
		EntityManager db = createEntityManager();
		User u = db.find(User.class, ID);
		db.getTransaction().begin();
		float updatedcash = u.getCash()+addition;
		u.setCash(updatedcash);
		db.getTransaction().commit();
		db.close();
		System.out.println("Cash added sucessfully");	
		return updatedcash;
	}

	public void storeFeedback(FeedbackType fbtype, String email, String name, String summary, String details, File file) {
		EntityManager db = createEntityManager();
		db.getTransaction().begin();
		Feedback fb = new Feedback(fbtype, email, name, summary, details, file);
		db.persist(fb);
		db.getTransaction().commit();
		db.close();
		System.out.println("Feedback sent sucessfully");
	}


	/**
	 * This method retrieves all Feedback objects from the database.
	 * @return	Feedback that has been sent and stored previously.
	 */
	public Vector<Feedback> retrieveFeedback(){
		EntityManager db = createEntityManager();
		db.getTransaction().begin();
		Vector<Feedback> fbvector = new Vector<Feedback>();
		TypedQuery<Feedback> query = db.createQuery("Select f From Feedback f", Feedback.class);
		List<Feedback> fblist = query.getResultList();
		for(Feedback f:fblist) {
			fbvector.add(f);
		}
		db.close();
		return fbvector;	
	}

	public List<Prediction> getQuestionPredictions(int questionId) throws QuestionNotFound,NoAnswers {
		EntityManager db = createEntityManager();
		Question q = db.find(Question.class, questionId);
		db.close();
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

	/**
	 * This method resolves the outcomes of the questions the event of finished at the given date. The outcomes of the possible 
	 * predictions a question has are decided by a generated random number. The odds affect the likelihood of the number to be in the
	 * range of said outcome.
	 * 
	 * @param date
	 */
	public void resolveQuestions(Date date) {
		Calendar cl = Calendar.getInstance();
		cl.setTime(date);
		cl.set(Calendar.MINUTE, cl.get(Calendar.MINUTE)-1);
		Date date1 = cl.getTime();
		EntityManager db = createEntityManager();
		TypedQuery<Event> query = db.createQuery("SELECT ev FROM Event ev WHERE ev.endingdate < ?2  AND ev.endingdate >= ?1",Event.class);
		query.setParameter(1, date1);
		query.setParameter(2, date);
		List<Event> events = query.getResultList();
		db.getTransaction().begin();
		for(Event e : events) {
			for(Question q : e.getQuestions()) {
				Random r = new Random();
				int random = r.nextInt(q.getPredictions().size()-1);
				for(Prediction p: q.getPredictions()) {
					p.setOutcome(false);
				}
				q.getPredictions().get(random).setOutcome(true);	
			}
		}
		db.getTransaction().commit();
		db.close();
	}


	public ArrayList<Bet> getBets(User bettor){
		EntityManager db = createEntityManager();
		User u = db.find(User.class, bettor.getUsername());
		TypedQuery<User> q1 = db.createQuery("select user FROM User user where id ="+"'"+ u.getUsername()+"'",User.class);
		List<User> results = q1.getResultList();
		ArrayList<Bet> bets =  results.get(0).getBets();
		db.close();
		System.out.println("Bet retrieved sucessfully");
		return bets;
	}

	/**
	 * This method retrieves from the database the Bets that were scheduled to resolve in the date given.
	 * 
	 * @param date    The date in which bets need to be resolved.
	 * @return 		  collection of Bet objects.
	 */
	public List<Bet> getBetsByResolutionDate(Date date){
		EntityManager db = createEntityManager();
		Calendar cl = Calendar.getInstance();
		cl.setTime(date);
		cl.set(Calendar.MINUTE, cl.get(Calendar.MINUTE)-1);
		Date date1 = cl.getTime();
		TypedQuery<Bet> query = db.createQuery("SELECT b FROM Bet b WHERE b.resolvingdate >= ?1 AND b.resolvingdate < ?2", Bet.class);
		query.setParameter(1, date1);
		query.setParameter(2, date);
		List<Bet> results = query.getResultList();
		db.getTransaction().begin();
		for(Bet b : results) {
			b.setStatus(Bet.Status.RESOLVED);
		}
		db.getTransaction().commit();
		db.close();
		return results;
	}

	@Override
	public void removeBet(User bettor, Bet bet) {
		EntityManager db = createEntityManager();
		User u = db.find(User.class, bettor.getUsername());
		db.getTransaction().begin();	
		u.removeBet(bet);
		db.getTransaction().commit();
		db.close();
		System.out.println("Bet removed sucessfully");

	}

	@Override
	public void updatebet(User bettor, Bet bet, float amount) {
		EntityManager db = createEntityManager();
		Bet b = db.find(Bet.class, bet.getBetNumber());
		db.getTransaction().begin();
		b.setAmount(amount);
		db.getTransaction().commit();
		db.close();
		System.out.println("Bet updated sucessfully");

	}

	/**
	 * Updates the cash of a user by the amount indicates.
	 * 
	 * @param user	User to update cash of.
	 * @param update  Amount of cash to add/remove(+/-).
	 */
	public void updateUserCash(User user, float update) {
		EntityManager db = createEntityManager();
		User u = db.getReference(User.class, user.getUsername());
		db.getTransaction().begin();
		u.setCash(u.getCash() + update);
		db.getTransaction().commit();
		db.close();
	}
}
