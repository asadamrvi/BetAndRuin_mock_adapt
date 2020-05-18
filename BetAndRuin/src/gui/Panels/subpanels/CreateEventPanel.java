package gui.Panels.subpanels;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Vector;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.table.DefaultTableModel;
import com.toedter.calendar.JCalendar;
import businessLogic.BLFacade;
import domain.Competition;
import domain.Sport;
import exceptions.EventAlreadyCreated;
import gui.MainGUI;
import gui.Panels.BrowsePanel;
import gui.components.ButtonColumn;
import gui.components.CompetitionPanel;
import gui.components.FancyButton;
import gui.components.HintTextField;


@SuppressWarnings("serial")
public class CreateEventPanel extends JPanel {

	private HashMap<String, ArrayList<String>> events = new HashMap<String, ArrayList<String>>();
	private ArrayList<Date> startingHours = new ArrayList<Date>();
	private ArrayList<Date> endingHours = new ArrayList<Date>();
	private  JComboBox<Sport> sportComboBox = new JComboBox<Sport>();

	private JLabel jLabelMinBet = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("MinimumBetPrice")); //$NON-NLS-1$ //$NON-NLS-2$
	private JLabel eventDateLabel = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("EventDate"));
	private JLabel createQuestionErrorLabel = new JLabel();
	private JLabel teamsErrorLabel = new JLabel();
	private JLabel team2Label = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Team2")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-1$ //$NON-NLS-2$
	private JLabel team1Label = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Team1"));  //$NON-NLS-1$ //$NON-NLS-2$
	private JLabel durationLabel = new JLabel((ResourceBundle.getBundle("Etiquetas").getString("duration")));
	private JLabel startHourLabel = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("start"));
	private HintTextField jHintFieldEndHour = new HintTextField("minutes", new Color(255, 255, 255), new Color(0,0,0), new Color(0,0,0), new Color(169,169,169),  new Color(169,169,169));
	private HintTextField jHintFieldDuration= new HintTextField("HH:mm", new Color(255, 255, 255), new Color(0,0,0), new Color(0,0,0), new Color(169,169,169),  new Color(169,169,169));
	
	private JCalendar jCalendar = new JCalendar();
	private Calendar calendarMio = null;


	private JButton jButtonCreate;
	private JButton addTeamsButton;

	private  JTextField team1TextField;
	private JTextField team2TextField;

	private final JTable createdEventTable = new JTable();
	private final JTable currentEventTable = new JTable();
	private DefaultTableModel createdEventsTableModel;
	private DefaultTableModel eventTableModel;
	private String[] columNameAddedEvents = new String[] {	"No.", "Event Name","Begin", ""};
	private String[] columNameEvents = new String[] {	"No.", "Event Name"};

	private final JScrollPane createdEventsScrollPane = new JScrollPane();

	private final JScrollPane eventScrollPanel = new JScrollPane();
	private final JLabel currentEvents = new JLabel("Current Events");
	private final JLabel sportLabel = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("CreateQuestionPanel.sportLabel.text")); //$NON-NLS-1$ //$NON-NLS-2$


	private CompetitionPanel competitionPanel;
	private final JScrollPane competitionScrollPane = new JScrollPane();

	private BLFacade facade = MainGUI.getBusinessLogic();

	/**
	 * Create the panel.
	 */
	public CreateEventPanel() {
		BLFacade facade = MainGUI.getBusinessLogic();

		setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		setBackground(Color.WHITE);

		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{40, 30, 0, 34, 0, 30, 38, 0, 30, 20, -20, 100, 0, 20, 37, 0, 30, 30, 40, 20, 50, 0, 109, 50};
		gridBagLayout.rowHeights = new int[]{23, 0, 4, 8, 16, 25, 0, 0, 25, 0, 20, 40, 41, 29, 30, 20, 96, 70, 0, 25, 20, 30, 30, 40, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);



		for(Component c : jCalendar.getDayChooser().getDayPanel().getComponents()) {
			c.setFont(new Font("Source sans Pro", Font.PLAIN, 14));
		}
		eventDateLabel.setFont(new Font("Source Sans Pro", Font.BOLD, 16));

		eventDateLabel.setBounds(new Rectangle(40, 15, 140, 25));
		eventDateLabel.setBounds(40, 16, 140, 25);
		GridBagConstraints gbc_eventDateLabel = new GridBagConstraints();
		gbc_eventDateLabel.anchor = GridBagConstraints.WEST;
		gbc_eventDateLabel.insets = new Insets(0, 0, 5, 5);
		gbc_eventDateLabel.gridwidth = 3;
		gbc_eventDateLabel.gridx = 1;
		gbc_eventDateLabel.gridy = 3;
		add(eventDateLabel, gbc_eventDateLabel);

		GridBagConstraints gbc_sportLabel = new GridBagConstraints();
		gbc_sportLabel.insets = new Insets(0, 0, 5, 5);
		gbc_sportLabel.gridx = 6;
		gbc_sportLabel.gridy = 3;
		sportLabel.setFont(new Font("Source Sans Pro", Font.BOLD, 16));
		add(sportLabel, gbc_sportLabel);

		GridBagConstraints gbc_currentEventLabel = new GridBagConstraints();
		gbc_currentEventLabel.gridwidth = 5;
		gbc_currentEventLabel.anchor = GridBagConstraints.WEST;
		gbc_currentEventLabel.insets = new Insets(0, 0, 5, 5);
		gbc_currentEventLabel.gridx = 18;
		gbc_currentEventLabel.gridy = 3;
		currentEvents.setFont(new Font("Source Sans Pro", Font.BOLD, 16));
		add(currentEvents, gbc_currentEventLabel);

		GridBagConstraints gbc_eventScrollPanel = new GridBagConstraints();
		gbc_eventScrollPanel.gridheight = 7;
		gbc_eventScrollPanel.gridwidth = 5;
		gbc_eventScrollPanel.insets = new Insets(0, 0, 5, 5);
		gbc_eventScrollPanel.fill = GridBagConstraints.BOTH;
		gbc_eventScrollPanel.gridx = 18;
		gbc_eventScrollPanel.gridy = 4;
		add(eventScrollPanel, gbc_eventScrollPanel);
		eventTableModel = new DefaultTableModel(null, columNameEvents){
			boolean[] columnEditables = new boolean[] {
					false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		};
		currentEventTable.setModel(eventTableModel);
		currentEventTable.setFont(new Font("Source sans Pro", Font.BOLD, 14));
		eventScrollPanel.setViewportView(currentEventTable);
		currentEventTable.getColumnModel().getColumn(0).setPreferredWidth(50);
		currentEventTable.getColumnModel().getColumn(0).setMinWidth(50);
		currentEventTable.getColumnModel().getColumn(0).setMaxWidth(50);
		currentEventTable.getColumnModel().getColumn(1).setMinWidth(100);
		eventScrollPanel.getViewport().setBackground(new Color(250,250,250));
		currentEventTable.getTableHeader().setBackground(new Color(245,245,245));
		currentEventTable.setBackground(new Color(250,250,250));
		currentEventTable.getTableHeader().setFont(new Font("Source sans Pro", Font.BOLD, 16));

		jCalendar.getDayChooser().getDayPanel().setBackground(UIManager.getColor("Button.highlight"));
		jCalendar.getMonthChooser().getSpinner().setBackground(Color.WHITE);
		jCalendar.getDayChooser().getDayPanel().setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		jCalendar.getDayChooser().setSelectedColor(new Color(196,196,196));
		jCalendar.getDayChooser().setSundayForeground(Color.RED);
		jCalendar.getDayChooser().setDecorationBackgroundColor(Color.WHITE);
		jCalendar.getDayChooser().setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
		jCalendar.getDayChooser().getDayPanel().setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0, Color.BLACK));
		jCalendar.getDayChooser().setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
		jCalendar.getYearChooser().getSpinner().setBackground(Color.WHITE);
		jCalendar.getMonthChooser().getComboBox().setBackground(Color.WHITE);


		GridBagConstraints gbc_jCalendar = new GridBagConstraints();
		gbc_jCalendar.gridheight = 7;
		gbc_jCalendar.anchor = GridBagConstraints.NORTHWEST;
		gbc_jCalendar.insets = new Insets(0, 0, 5, 5);
		gbc_jCalendar.gridwidth = 4;
		gbc_jCalendar.gridx = 1;
		gbc_jCalendar.gridy = 4;
		this.add(jCalendar, gbc_jCalendar);
		// Code for JCalendar
		this.jCalendar.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent propertychangeevent) {
				//Date firstDay = UtilDate.trim(new Date(jCalendar.getCalendar().getTime().getTime()));
				Competition selectedcompetition = competitionPanel.getSelectedCompetition();
				if (propertychangeevent.getPropertyName().equals("locale")) {
					jCalendar.setLocale((Locale) propertychangeevent.getNewValue());
				} else if (propertychangeevent.getPropertyName().equals("calendar")) {
					calendarMio = (Calendar) propertychangeevent.getNewValue();
					jCalendar.setCalendar(calendarMio);
					refreshPage(selectedcompetition, jCalendar);
				}

			}
		});

		createdEventsTableModel = new DefaultTableModel(null, columNameAddedEvents);
		resetCreatedEventTable();

		sportComboBox.setModel(new DefaultComboBoxModel<Sport>(Sport.values()));
		GridBagConstraints gbc_sportComboBox = new GridBagConstraints();
		gbc_sportComboBox.gridwidth = 11;
		gbc_sportComboBox.insets = new Insets(0, 0, 5, 5);
		gbc_sportComboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_sportComboBox.gridx = 6;
		gbc_sportComboBox.gridy = 4;
		add(sportComboBox, gbc_sportComboBox);
		sportComboBox.addItemListener(new ItemListener() {	
			@Override
			public void itemStateChanged(ItemEvent e) {
				Sport selectedsport = (Sport)sportComboBox.getSelectedItem();

				competitionScrollPane.remove(competitionPanel);
				Vector<Competition> competitions = facade.getCompetitions(selectedsport);
				competitionPanel =  new  CompetitionPanel(competitions);
				competitionPanel.addPropertyChangeListener(new CompetitionChangeEvent());
				competitionPanel.setSelectedCompetition(null);
				competitionScrollPane.add(competitionPanel);
				competitionScrollPane.setViewportView(competitionPanel);
				competitionScrollPane.updateUI();
				BrowsePanel.paintDaysWithEvents(jCalendar, new Vector<Date>()); 
				disableInputFields();
			}

		});
		

		teamsErrorLabel.setHorizontalAlignment(SwingConstants.CENTER);

		teamsErrorLabel.setBounds(new Rectangle(196, 322, 371, 30));
		teamsErrorLabel.setForeground(Color.red);
		GridBagConstraints gbc_answerErrorLabel = new GridBagConstraints();
		gbc_answerErrorLabel.gridwidth = 2;
		gbc_answerErrorLabel.anchor = GridBagConstraints.SOUTH;
		gbc_answerErrorLabel.insets = new Insets(0, 0, 5, 5);
		gbc_answerErrorLabel.gridx = 17;
		gbc_answerErrorLabel.gridy = 11;
		this.add(teamsErrorLabel, gbc_answerErrorLabel);

		GridBagConstraints gbc_competitionScrollPane;
		gbc_competitionScrollPane = new GridBagConstraints();
		gbc_competitionScrollPane.gridheight = 11;
		gbc_competitionScrollPane.gridwidth = 4;
		gbc_competitionScrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_competitionScrollPane.fill = GridBagConstraints.BOTH;
		gbc_competitionScrollPane.gridx = 1;
		gbc_competitionScrollPane.gridy = 12;
		add(competitionScrollPane, gbc_competitionScrollPane);

		addTeamsButton = new FancyButton(ResourceBundle.getBundle("Etiquetas").getString("AddTeams"),new Color(51,51,51),new Color(170,170,170),new Color(150,150,150));
		addTeamsButton.setForeground(Color.white);
		addTeamsButton.setFont(new Font("Source Sans Pro", Font.BOLD, 13));
		addTeamsButton.setEnabled(false);
		addTeamsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
					String date = df.format(jCalendar.getDate());
					teamsErrorLabel.setText("");
					createQuestionErrorLabel.setText("");
					if (team1TextField.getText().equals("") || team2TextField.getText().equals("")  ) {//if one of the teams is not set warn th admin
						teamsErrorLabel.setText("Enter both teams");
						team1TextField.setBorder(new LineBorder(Color.RED, 2));
					}else if(jHintFieldDuration.getText().equals("") || jHintFieldEndHour.getText().equals("")) {
						teamsErrorLabel.setText("Enter valid dates");
					} else {
						//get both teams
						String team1 = team1TextField.getText();
						String team2 = team2TextField.getText();
						team2TextField.setBorder(new LineBorder(Color.BLACK, 1));
						team1TextField.setBorder(new LineBorder(Color.BLACK, 1));
						String startingHour = jHintFieldDuration.getText();
						String duration = jHintFieldEndHour.getText();
						//create the description and add it to the hashmap
						String description = team1 + "-" + team2;
						ArrayList<String> eventList = events.get(date);
						if (eventList == null) {
							eventList = new ArrayList<String>();
							eventList.add(description);
							events.put(date, eventList);
							Date startHour = sdf.parse(date + " " + startingHour);
							Calendar cl = Calendar.getInstance();
							cl.setTime(startHour);
							cl.add(Calendar.MINUTE, Integer.parseInt(duration));
							Date endHour = cl.getTime();
							System.out.println(endHour);
							startingHours.add(startHour);
							endingHours.add(endHour);
							createdEventsTableAdd(description, date);
						}else if(eventList.contains(description)) {
								teamsErrorLabel.setText("Event already placed");
						}else {
							eventList.add(description);
							events.put(date, eventList);
							Date startHour = sdf.parse(date + " " + startingHour);
							Calendar cl = Calendar.getInstance();
							cl.setTime(startHour);
							cl.add(Calendar.MINUTE, Integer.parseInt(duration));
							Date endHour = cl.getTime();
							System.out.println(endHour);
							startingHours.add(startHour);
							endingHours.add(endHour);
							createdEventsTableAdd(description, date);
						}
						team1TextField.setText("");
						team2TextField.setText("");
						jHintFieldEndHour.setText("");
						jHintFieldDuration.setText("");
					}
				}
				catch(NumberFormatException n) {
					teamsErrorLabel.setText("Please enter a valid odd number");
				}catch (ParseException e2) {
					// TODO: handle exception
					teamsErrorLabel.setText("Make sure dates are correct");
					e2.printStackTrace();
				}
			}
		});



		team1Label.setFont(new Font("Source Sans Pro", Font.BOLD, 16));
		team1Label.setBounds(new Rectangle(290, 18, 277, 20));
		GridBagConstraints gbc_team1Label = new GridBagConstraints();
		gbc_team1Label.anchor = GridBagConstraints.WEST;
		gbc_team1Label.insets = new Insets(0, 0, 5, 5);
		gbc_team1Label.gridx = 6;
		gbc_team1Label.gridy = 6;
		add(team1Label, gbc_team1Label);

		team1TextField = new JTextField() {
			@Override
			public void setEnabled(boolean enable) {
				super.setEnabled(enable);

				if (enable) {
					setBackground(Color.white);
					setForeground(Color.black);
				} else {
					setBackground(new Color(220,220,220));
					setForeground(Color.darkGray);
				}
			}
		};
		team1TextField.setEnabled(false);
		team1TextField.setBorder(new LineBorder(Color.black));
		team1TextField.setBounds(100, 254, 299, 20);
		team1TextField.setColumns(10);

		GridBagConstraints gbc_team1TextField = new GridBagConstraints();
		gbc_team1TextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_team1TextField.insets = new Insets(0, 0, 5, 5);
		gbc_team1TextField.gridwidth = 9;
		gbc_team1TextField.gridx = 6;
		gbc_team1TextField.gridy = 7;
		add(team1TextField, gbc_team1TextField);

		team2Label.setFont(new Font("Source Sans Pro", Font.BOLD, 16));
		team2Label.setBounds(new Rectangle(290, 18, 277, 20));
		GridBagConstraints gbc_team2Label = new GridBagConstraints();
		gbc_team2Label.anchor = GridBagConstraints.WEST;
		gbc_team2Label.insets = new Insets(0, 0, 5, 5);
		gbc_team2Label.gridx = 6;
		gbc_team2Label.gridy = 9;
		add(team2Label, gbc_team2Label);

		team2TextField = new JTextField();
		team2TextField.setBorder(new LineBorder(Color.black));
		team2TextField.setEnabled(false);
		team2TextField.setBounds(100, 304, 86, 20);
		team2TextField = new JTextField() {
			@Override
			public void setEnabled(boolean enable) {
				super.setEnabled(enable);

				if (enable) {
					setBackground(Color.white);
					setForeground(Color.black);
				} else {
					setBackground(new Color(220,220,220));
					setForeground(Color.darkGray);
				}
			}
		};
		GridBagConstraints gbc_team2TextField = new GridBagConstraints();
		gbc_team2TextField.gridwidth = 9;
		gbc_team2TextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_team2TextField.insets = new Insets(0, 0, 5, 5);
		gbc_team2TextField.gridx = 6;
		gbc_team2TextField.gridy = 10;
		add(team2TextField, gbc_team2TextField);
		team2TextField.setColumns(10);
		addTeamsButton.setBounds(new Rectangle(100, 400, 130, 100));
		GridBagConstraints gbc_addTeamsButton = new GridBagConstraints();
		gbc_addTeamsButton.gridwidth = 2;
		gbc_addTeamsButton.fill = GridBagConstraints.HORIZONTAL;
		gbc_addTeamsButton.insets = new Insets(0, 0, 5, 5);
		gbc_addTeamsButton.gridx = 6;
		gbc_addTeamsButton.gridy = 11;
		add(addTeamsButton, gbc_addTeamsButton);

		GridBagConstraints gbc_answerScrollPane = new GridBagConstraints();
		gbc_answerScrollPane.gridheight = 10;
		gbc_answerScrollPane.gridwidth = 14;
		gbc_answerScrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_answerScrollPane.fill = GridBagConstraints.BOTH;
		gbc_answerScrollPane.gridx = 6;
		gbc_answerScrollPane.gridy = 13;
		add(createdEventsScrollPane, gbc_answerScrollPane);

		createdEventsScrollPane.setViewportView(createdEventTable);
		createdEventsScrollPane.getViewport().setBackground(new Color(250,250,250));
		createdEventTable.getTableHeader().setBackground(new Color(245,245,245));
		createdEventTable.setBackground(new Color(250,250,250));

		jButtonCreate = new FancyButton(ResourceBundle.getBundle("Etiquetas").getString("CreateEvent"),new Color(51,51,51),new Color(170,170,170),new Color(150,150,150));
		jButtonCreate.setForeground(Color.white);
		jButtonCreate.setFont(new Font("Source Sans Pro", Font.BOLD, 15));
		jButtonCreate.setEnabled(false);
		jButtonCreate.setBounds(new Rectangle(100, 400, 130, 100));
		jButtonCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jButtonCreate_actionPerformed(e);
			}
		});

		createQuestionErrorLabel.setHorizontalAlignment(SwingConstants.CENTER);
		createQuestionErrorLabel.setBounds(new Rectangle(275, 182, 305, 20));
		createQuestionErrorLabel.setForeground(Color.red);

		GridBagConstraints gbc_jLabelMsg = new GridBagConstraints();
		gbc_jLabelMsg.fill = GridBagConstraints.VERTICAL;
		gbc_jLabelMsg.gridwidth = 2;
		gbc_jLabelMsg.insets = new Insets(0, 0, 5, 5);
		gbc_jLabelMsg.gridx = 21;
		gbc_jLabelMsg.gridy = 17;
		this.add(createQuestionErrorLabel, gbc_jLabelMsg);

		GridBagConstraints gbc_jButtonCreate = new GridBagConstraints();
		gbc_jButtonCreate.gridheight = 2;
		gbc_jButtonCreate.fill = GridBagConstraints.BOTH;
		gbc_jButtonCreate.gridwidth = 2;
		gbc_jButtonCreate.insets = new Insets(0, 0, 5, 5);
		gbc_jButtonCreate.gridx = 21;
		gbc_jButtonCreate.gridy = 21;
		this.add(jButtonCreate, gbc_jButtonCreate);

		Vector<Competition> competitions = facade.getCompetitions((Sport)sportComboBox.getSelectedItem());
		competitionPanel =  new  CompetitionPanel(competitions);
		competitionPanel.addPropertyChangeListener(new CompetitionChangeEvent());
		competitionScrollPane.add(competitionPanel);
		competitionScrollPane.setViewportView(competitionPanel);
		competitionScrollPane.updateUI();
		disableInputFields();
		
		GridBagConstraints gbc_jTextFieldQuery = new GridBagConstraints();
		gbc_jTextFieldQuery.anchor = GridBagConstraints.NORTH;
		gbc_jTextFieldQuery.gridwidth = 2;
		gbc_jTextFieldQuery.fill = GridBagConstraints.HORIZONTAL;
		gbc_jTextFieldQuery.insets = new Insets(0, 0, 5, 0);
		gbc_jTextFieldQuery.gridx = 21;
		gbc_jTextFieldQuery.gridy = 19;
		this.add(jHintFieldEndHour, gbc_jTextFieldQuery);
		jHintFieldEndHour.setBounds(new Rectangle(100, 211, 75, 33));
		jHintFieldEndHour.setFont(new Font("Tahoma",Font.ITALIC,14));
		
		
		durationLabel.setFont(new Font("Source Sans Pro", Font.BOLD, 16));
		durationLabel.setBounds(new Rectangle(25, 211, 75, 20));
		GridBagConstraints gbc_durationLabel = new GridBagConstraints();
		gbc_durationLabel.gridwidth = 3;
		gbc_durationLabel.anchor = GridBagConstraints.SOUTHWEST;
		gbc_durationLabel.insets = new Insets(0, 0, 5, 5);
		gbc_durationLabel.gridx = 21;
		gbc_durationLabel.gridy = 18;
		this.add(durationLabel, gbc_durationLabel);
		
		GridBagConstraints gbc_jTextFieldDuration= new GridBagConstraints();
		gbc_jTextFieldDuration.anchor = GridBagConstraints.NORTH;
		gbc_jTextFieldDuration.gridwidth = 2;
		gbc_jTextFieldDuration.fill = GridBagConstraints.HORIZONTAL;
		gbc_jTextFieldDuration.insets = new Insets(0, 0, 5, 5);
		gbc_jTextFieldDuration.gridx = 21;
		gbc_jTextFieldDuration.gridy = 16;
		this.add(jHintFieldDuration, gbc_jTextFieldDuration);
		jHintFieldDuration.setBounds(new Rectangle(100, 211, 75, 33));
		jHintFieldDuration.setFont(new Font("Tahoma",Font.ITALIC,14));
		
		startHourLabel.setFont(new Font("Source Sans Pro", Font.BOLD, 16));
		startHourLabel.setBounds(new Rectangle(25, 211, 75, 20));
		GridBagConstraints gbc_startHourLabel = new GridBagConstraints();
		gbc_startHourLabel.gridwidth = 3;
		gbc_startHourLabel.anchor = GridBagConstraints.SOUTHWEST;
		gbc_startHourLabel.insets = new Insets(0, 0, 5, 5);
		gbc_startHourLabel.gridx = 21;
		gbc_startHourLabel.gridy = 15;
		this.add(startHourLabel, gbc_startHourLabel);
	}

	/**
	 * PropertyChangeListener for the competition panel. Alters elements of the page depending on the currently selected competition.
	 * The events on the event table, and days painted in the calendar will be changed based on it.
	 */
	public class CompetitionChangeEvent implements PropertyChangeListener{
		public void propertyChange(PropertyChangeEvent evt) {
			if (evt.getPropertyName().equals("selectedcompetition"))
				refreshPage(competitionPanel.getSelectedCompetition(), jCalendar);	
		}
	}

	public void resetErrorHighlights() {
		teamsErrorLabel.setText("");
		createQuestionErrorLabel.setText("");
		team2TextField.setBorder(new LineBorder(Color.black));
		team1TextField.setBorder(new LineBorder(Color.black));
	}

	/**
	 * Disables areas/buttons where users can usually press/enter information related with question creation.
	 */
	public void disableInputFields() {
		team2Label.setEnabled(false);
		team1Label.setEnabled(false);
		jLabelMinBet.setEnabled(false);
		jButtonCreate.setEnabled(false);
		addTeamsButton.setEnabled(false);
		team1TextField.setEnabled(false);
		team2TextField.setEnabled(false);	
	}

	/**
	 * Enables areas/buttons where users can usually press/enter information related with question creation.
	 */
	public void enableInputFields() {
		team2Label.setEnabled(true);
		team1Label.setEnabled(true);
		jLabelMinBet.setEnabled(true);
		jButtonCreate.setEnabled(true);
		addTeamsButton.setEnabled(true);
		team1TextField.setEnabled(true);
		team2TextField.setEnabled(true);
	}

	/**
	 * Recomputes and displays the events that satisfy the user inputed restrictions, that is, the selected competition and dates.
	 * 
	 * @param comp  user selected competition
	 * @param date	user selected date
	 */
	public void refreshPage(Competition comp, JCalendar jCalendar) {
		resetErrorHighlights();
		
		calendarMio = jCalendar.getCalendar();
		Competition selectedcompetition = competitionPanel.getSelectedCompetition();
		try {
			if(selectedcompetition != null) {
				loadEvents(selectedcompetition);
			}
			if (eventTableModel.getRowCount() == 0) {
				disableInputFields();
			}	    
				enableInputFields();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		BrowsePanel.paintDaysWithEvents(jCalendar, facade.getEventsMonthByCompetition(jCalendar.getDate(), selectedcompetition));
	}

	private void jButtonCreate_actionPerformed(ActionEvent e) {

		try {
			teamsErrorLabel.setText("");
			createQuestionErrorLabel.setText("");
			Competition selectedcompetition = competitionPanel.getSelectedCompetition();

			Sport sport = (Sport)sportComboBox.getSelectedItem();
				if (events.size() < 1) {//if only 1 team is introduced warm the admin
					teamsErrorLabel.setText("Introduce answers");
					
				}else{
					// Obtain the business logic from a StartWindow class (local or remote)
					BLFacade facade = MainGUI.getBusinessLogic();
					// Parse the Hours
					ArrayList<String> descriptions = new ArrayList<String>();
					//for each pair in the hash we add it to the db
					int i = 0;
					for(String date: events.keySet()) {
						descriptions = events.get(date);
						 Date start = startingHours.get(i);
						 Date end = endingHours.get(i);
						 System.out.println("Start " + i+  " : " + start);
						 System.out.println("End " + i+  " : " + end);
						 i++;
						for (String des : descriptions) {
							facade.addEvent(start, end,des, sport, selectedcompetition.getCompetitionNumber());
						}
					}
					loadEvents(selectedcompetition);
					events = new HashMap<String, ArrayList<String>>(); // reset the hash after adding the pairs
					resetCreatedEventTable(); //reset the table

					teamsErrorLabel.setText(ResourceBundle.getBundle("Etiquetas").getString("EventCreated"));
				}
				//reset text fields 
				team1TextField.setText("");
				team2TextField.setText("");
		} catch(EventAlreadyCreated e1) {
			e1.printStackTrace();
			teamsErrorLabel.setText(e1.getMessage());
		}
		catch (Exception e1) {
			e1.printStackTrace();
		}
	}
	/**
	 * 
	 * @param selectedcompetition This is the competition selected by the admin
	 */
	private void loadEvents(Competition selectedcompetition) {
		// TODO Auto-generated method stub
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		int i = 0;
		eventTableModel.setDataVector(null, columNameEvents);
		currentEventTable.getColumnModel().getColumn(0).setPreferredWidth(50);
		currentEventTable.getColumnModel().getColumn(0).setMinWidth(50);
		currentEventTable.getColumnModel().getColumn(0).setMaxWidth(50);
		currentEventTable.getColumnModel().getColumn(1).setMinWidth(100);
		for (domain.Event ev : selectedcompetition.getEvents()) {
			if(df.format(ev.getEventDate()).equals(df.format(jCalendar.getDate()))) {
				Object[] row = new Object[2];
				row[0] = ++i;
				row[1] = ev.getDescription();
				eventTableModel.addRow(row);
			}
						
		}
	}

	public void resetCreatedEventTable() {
		createdEventsTableModel.setDataVector(null, columNameAddedEvents);
		createdEventsTableModel.setColumnCount(4); 
		createdEventTable.setRowHeight(28);
		createdEventTable.setModel(createdEventsTableModel);
		createdEventTable.getColumnModel().getColumn(0).setPreferredWidth(60);
		createdEventTable.getColumnModel().getColumn(0).setMinWidth(60);
		createdEventTable.getColumnModel().getColumn(0).setMaxWidth(60);
		createdEventTable.getColumnModel().getColumn(2).setPreferredWidth(140);
		createdEventTable.getColumnModel().getColumn(2).setMinWidth(140);
		createdEventTable.getColumnModel().getColumn(2).setMaxWidth(140);
		createdEventTable.getColumnModel().getColumn(3).setPreferredWidth(50);
		createdEventTable.getColumnModel().getColumn(3).setMinWidth(50);
		createdEventTable.getColumnModel().getColumn(3).setMaxWidth(50);
		createdEventTable.getTableHeader().setFont(new Font("Source sans Pro", Font.BOLD, 16));
		createdEventTable.setFont(new Font("Source sans Pro", Font.BOLD, 14));
		@SuppressWarnings("unused")
		ButtonColumn deleteButtonColumn = new ButtonColumn(createdEventTable, delete, 3, new Color(255,0,51));
	}

	public void createdEventsTableAdd(String description, String date) {
		Object[] row = new Object[4];
		row[0] = createdEventsTableModel.getRowCount()+1;
		row[1] = description;
		row[2] = date;
		row[3] = new ImageIcon("images/delete.png");

		createdEventsTableModel.addRow(row);
	}

	Action delete = new AbstractAction() {
		@Override
		public void actionPerformed(ActionEvent e) {
			int row = createdEventTable.getSelectedRow();
			String date =  (String)createdEventTable.getValueAt(row, 2);
			ArrayList<String> des = events.get(date);
			System.out.println(date);
			des.remove(createdEventTable.getValueAt(row,1));
			endingHours.remove(createdEventTable.getSelectedRow());
			createdEventsTableModel.removeRow(row);
		}
	};
}


