package gui.Panels;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.DateFormat;
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
import javax.swing.JTextField;
import com.toedter.calendar.JCalendar;
import businessLogic.BLFacade;
import configuration.UtilDate;
import domain.Competition;
import domain.Event;
import domain.Prediction;
import domain.Question;
import domain.Sport;
import exceptions.EventFinished;
import exceptions.QuestionAlreadyExist;
import gui.MainGUI;
import gui.components.ButtonColumn;
import gui.components.HintTextField;
import gui.components.JNumericField;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import gui.components.CompetitionPanel;
import gui.components.FancyButton;

@SuppressWarnings("serial")
public class CreateQuestionPanel extends JPanel {

	//private Image backgroundImage;
	private Map<String,Float> answers = new HashMap<String, Float>();

	private  JComboBox<Sport> sportComboBox = new JComboBox<Sport>();
	private JComboBox<Event> jComboBoxEvents = new JComboBox<Event>();
	private DefaultComboBoxModel<Event> modelEvents = new DefaultComboBoxModel<Event>();

	private JLabel listOfEventsLabel = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("ListEvents"));
	private JLabel questionLabel = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Query")); //$NON-NLS-1$ //$NON-NLS-2$
	private JLabel jLabelMinBet = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("MinimumBetPrice")); //$NON-NLS-1$ //$NON-NLS-2$
	private JLabel eventDateLabel = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("EventDate"));
	private JLabel createQuestionErrorLabel = new JLabel();
	private JLabel answerErrorLabel = new JLabel();
	private JLabel oddLabel = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("CreateQuestionGUI.oddLabel.text")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-1$ //$NON-NLS-2$
	private JLabel answerLabel = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("CreateQuestionPanel.answerLabel.text"));  //$NON-NLS-1$ //$NON-NLS-2$

	private HintTextField jHintFieldQuery = new HintTextField("Introduce question here", new Color(255, 255, 255), new Color(0,0,0), new Color(0,0,0), new Color(169,169,169),  new Color(169,169,169));

	private JCalendar jCalendar = new JCalendar();
	private Calendar calendarMio = null;


	private JButton jButtonCreate;
	private JButton answerButton;

	private  JTextField answerTextField;
	private JNumericField oddTextField;
	private JNumericField minPriceTextField;

	private final JTable answerTable = new JTable();
	private final JTable questionTable = new JTable();
	private DefaultTableModel answerTableModel;
	private DefaultTableModel questionTableModel;
	private String[] columNameAnswers = new String[] {	"No.", "Anwser", "Odds", ""};
	private String[] columNameQuestions = new String[] {	"No.", "Question"};

	private final JLabel titleLabel = new JLabel("Create Questions");
	private final JScrollPane answerScrollPane = new JScrollPane();

	private final JScrollPane questionScrollPanel = new JScrollPane();
	private final JLabel currentQuestionsLabel = new JLabel("Current Questions");
	private final JLabel sportLabel = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("CreateQuestionPanel.sportLabel.text")); //$NON-NLS-1$ //$NON-NLS-2$


	private CompetitionPanel competitionPanel;
	private final JScrollPane competitionScrollPane = new JScrollPane();

	private BLFacade facade = MainGUI.getBusinessLogic();

	/**
	 * Create the panel.
	 */
	public CreateQuestionPanel() {

		BLFacade facade = MainGUI.getBusinessLogic();

		setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		setBackground(Color.WHITE);

		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{40, 30, 0, 34, 0, 30, 38, 0, 30, 20, -20, 100, 0, 20, 37, 0, 30, 30, 40, 20, 50, 0, 109, 50};
		gridBagLayout.rowHeights = new int[]{23, 0, 4, 8, 16, 25, 0, 0, 25, 0, 20, 40, 41, 29, 30, 20, 96, 70, 0, 25, 20, 30, 30, 40, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);

		GridBagConstraints gbc_titleLabel = new GridBagConstraints();
		gbc_titleLabel.anchor = GridBagConstraints.WEST;
		gbc_titleLabel.gridwidth = 22;
		gbc_titleLabel.insets = new Insets(0, 0, 5, 5);
		gbc_titleLabel.gridx = 1;
		gbc_titleLabel.gridy = 1;
		titleLabel.setFont(new Font("Source Code Pro ExtraLight", Font.PLAIN, 25));
		add(titleLabel, gbc_titleLabel);

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

		GridBagConstraints gbc_currentQuestionsLabel = new GridBagConstraints();
		gbc_currentQuestionsLabel.gridwidth = 5;
		gbc_currentQuestionsLabel.anchor = GridBagConstraints.WEST;
		gbc_currentQuestionsLabel.insets = new Insets(0, 0, 5, 5);
		gbc_currentQuestionsLabel.gridx = 18;
		gbc_currentQuestionsLabel.gridy = 3;
		currentQuestionsLabel.setFont(new Font("Source Sans Pro", Font.BOLD, 16));
		add(currentQuestionsLabel, gbc_currentQuestionsLabel);

		GridBagConstraints gbc_questionPanel = new GridBagConstraints();
		gbc_questionPanel.gridheight = 7;
		gbc_questionPanel.gridwidth = 5;
		gbc_questionPanel.insets = new Insets(0, 0, 5, 5);
		gbc_questionPanel.fill = GridBagConstraints.BOTH;
		gbc_questionPanel.gridx = 18;
		gbc_questionPanel.gridy = 4;
		add(questionScrollPanel, gbc_questionPanel);
		questionTableModel = new DefaultTableModel(null, columNameQuestions){
			boolean[] columnEditables = new boolean[] {
					false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		};
		questionTable.setModel(questionTableModel);
		questionTable.setFont(new Font("Source sans Pro", Font.BOLD, 14));
		questionScrollPanel.setViewportView(questionTable);
		questionTable.getColumnModel().getColumn(0).setPreferredWidth(50);
		questionTable.getColumnModel().getColumn(0).setMinWidth(50);
		questionTable.getColumnModel().getColumn(0).setMaxWidth(50);
		questionTable.getColumnModel().getColumn(1).setMinWidth(100);
		questionScrollPanel.getViewport().setBackground(new Color(250,250,250));
		questionTable.getTableHeader().setBackground(new Color(245,245,245));
		questionTable.setBackground(new Color(250,250,250));
		questionTable.getTableHeader().setFont(new Font("Source sans Pro", Font.BOLD, 16));

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

		answerTableModel = new DefaultTableModel(null, columNameAnswers);
		resetAnswerTable();

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

				jComboBoxEvents.removeAllItems();
				jComboBoxEvents.repaint();

				BrowsePanel.paintDaysWithEvents(jCalendar, new Vector<Date>()); 
				disableInputFields();
			}

		});

		listOfEventsLabel.setFont(new Font("Source Sans Pro", Font.BOLD, 16));
		listOfEventsLabel.setBounds(new Rectangle(290, 18, 277, 20));
		GridBagConstraints gbc_listOfEventsLabel = new GridBagConstraints();
		gbc_listOfEventsLabel.anchor = GridBagConstraints.WEST;
		gbc_listOfEventsLabel.insets = new Insets(0, 0, 5, 5);
		gbc_listOfEventsLabel.gridwidth = 9;
		gbc_listOfEventsLabel.gridx = 6;
		gbc_listOfEventsLabel.gridy = 6;
		this.add(listOfEventsLabel, gbc_listOfEventsLabel);


		jComboBoxEvents.setModel(modelEvents);
		jComboBoxEvents.setBounds(new Rectangle(275, 47, 250, 20));
		GridBagConstraints gbc_jComboBoxEvents = new GridBagConstraints();
		gbc_jComboBoxEvents.fill = GridBagConstraints.HORIZONTAL;
		gbc_jComboBoxEvents.gridwidth = 11;
		gbc_jComboBoxEvents.anchor = GridBagConstraints.NORTH;
		gbc_jComboBoxEvents.insets = new Insets(0, 0, 5, 5);
		gbc_jComboBoxEvents.gridx = 6;
		gbc_jComboBoxEvents.gridy = 7;
		this.add(jComboBoxEvents, gbc_jComboBoxEvents);
		jComboBoxEvents.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				domain.Event event = ((domain.Event) jComboBoxEvents.getSelectedItem());
				loadQuestions(event);
			}
		});


		questionLabel.setFont(new Font("Source Sans Pro", Font.BOLD, 16));
		questionLabel.setBounds(new Rectangle(25, 211, 75, 20));
		GridBagConstraints gbc_questionLabel = new GridBagConstraints();
		gbc_questionLabel.gridwidth = 3;
		gbc_questionLabel.anchor = GridBagConstraints.SOUTHWEST;
		gbc_questionLabel.insets = new Insets(0, 0, 5, 5);
		gbc_questionLabel.gridx = 6;
		gbc_questionLabel.gridy = 9;
		this.add(questionLabel, gbc_questionLabel);


		GridBagConstraints gbc_jTextFieldQuery = new GridBagConstraints();
		gbc_jTextFieldQuery.anchor = GridBagConstraints.SOUTH;
		gbc_jTextFieldQuery.gridwidth = 11;
		gbc_jTextFieldQuery.fill = GridBagConstraints.HORIZONTAL;
		gbc_jTextFieldQuery.insets = new Insets(0, 0, 5, 5);
		gbc_jTextFieldQuery.gridx = 6;
		gbc_jTextFieldQuery.gridy = 10;
		this.add(jHintFieldQuery, gbc_jTextFieldQuery);
		jHintFieldQuery.setBounds(new Rectangle(100, 211, 75, 33));
		jHintFieldQuery.setFont(new Font("Tahoma",Font.ITALIC,14));
		answerErrorLabel.setHorizontalAlignment(SwingConstants.CENTER);

		answerErrorLabel.setBounds(new Rectangle(196, 322, 371, 30));
		answerErrorLabel.setForeground(Color.red);
		GridBagConstraints gbc_answerErrorLabel = new GridBagConstraints();
		gbc_answerErrorLabel.gridwidth = 2;
		gbc_answerErrorLabel.anchor = GridBagConstraints.SOUTH;
		gbc_answerErrorLabel.insets = new Insets(0, 0, 5, 5);
		gbc_answerErrorLabel.gridx = 17;
		gbc_answerErrorLabel.gridy = 11;
		this.add(answerErrorLabel, gbc_answerErrorLabel);

		GridBagConstraints gbc_competitionScrollPane;
		gbc_competitionScrollPane = new GridBagConstraints();
		gbc_competitionScrollPane.gridheight = 11;
		gbc_competitionScrollPane.gridwidth = 4;
		gbc_competitionScrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_competitionScrollPane.fill = GridBagConstraints.BOTH;
		gbc_competitionScrollPane.gridx = 1;
		gbc_competitionScrollPane.gridy = 12;
		add(competitionScrollPane, gbc_competitionScrollPane);

		answerButton = new FancyButton(ResourceBundle.getBundle("Etiquetas").getString("AddAnswer"),new Color(51,51,51),new Color(170,170,170),new Color(150,150,150));
		answerButton.setForeground(Color.white);
		answerButton.setFont(new Font("Source Sans Pro", Font.BOLD, 13));
		answerButton.setEnabled(false);
		answerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {

					answerErrorLabel.setText("");
					createQuestionErrorLabel.setText("");
					if (answerTextField.getText().equals("") ) {
						answerErrorLabel.setText("Enter an answer");
						answerTextField.setBorder(new LineBorder(Color.RED, 2));
					}else if(oddTextField.getText().equals("")) {
						answerErrorLabel.setText("Enter odd number");
						oddTextField.setBorder(new LineBorder(Color.RED, 2));
						answerTextField.setBorder(new LineBorder(Color.BLACK, 1));
					}else if(Float.parseFloat(oddTextField.getText()) < 1) {
						answerErrorLabel.setText("Please enter a valid odd number");
						answerTextField.setBorder(new LineBorder(Color.BLACK, 1));
					}else {
						String answer = answerTextField.getText();
						float odd = Float.parseFloat(oddTextField.getText());
						oddTextField.setBorder(new LineBorder(Color.BLACK, 1));
						answerTextField.setBorder(new LineBorder(Color.BLACK, 1));

						if(answers.containsKey(answer)) {
							answerErrorLabel.setText("Answer already introduced");
						}
						else {
							answers.put(answer, odd);
							answerTableAdd(answer, odd);
						}

						answerTextField.setText("");
						oddTextField.setText("");
					}
				}
				catch(NumberFormatException n) {
					answerErrorLabel.setText("Please enter a valid odd number");
				}
			}
		});



		answerLabel.setFont(new Font("Source Sans Pro", Font.BOLD, 14));
		answerLabel.setBounds(25, 254, 75, 20);
		GridBagConstraints gbc_answerLabel = new GridBagConstraints();
		gbc_answerLabel.anchor = GridBagConstraints.WEST;
		gbc_answerLabel.insets = new Insets(0, 0, 5, 5);
		gbc_answerLabel.gridx = 6;
		gbc_answerLabel.gridy = 12;
		add(answerLabel, gbc_answerLabel);

		answerTextField = new JTextField() {
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
		answerTextField.setEnabled(false);
		answerTextField.setBorder(new LineBorder(Color.black));
		answerTextField.setBounds(100, 254, 299, 20);
		answerTextField.setColumns(10);

		GridBagConstraints gbc_answerTextField = new GridBagConstraints();
		gbc_answerTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_answerTextField.insets = new Insets(0, 0, 5, 5);
		gbc_answerTextField.gridwidth = 5;
		gbc_answerTextField.gridx = 7;
		gbc_answerTextField.gridy = 12;
		add(answerTextField, gbc_answerTextField);

		oddLabel.setFont(new Font("Source Sans Pro", Font.BOLD, 14));
		oddLabel.setBounds(25, 304, 75, 20);
		GridBagConstraints gbc_oddLabel = new GridBagConstraints();
		gbc_oddLabel.anchor = GridBagConstraints.WEST;
		gbc_oddLabel.insets = new Insets(0, 0, 5, 5);
		gbc_oddLabel.gridx = 13;
		gbc_oddLabel.gridy = 12;
		add(oddLabel, gbc_oddLabel);

		oddTextField = new JNumericField();
		oddTextField.setBorder(new LineBorder(Color.black));
		oddTextField.setEnabled(false);
		oddTextField.setBounds(100, 304, 86, 20);
		GridBagConstraints gbc_oddTextField = new GridBagConstraints();
		gbc_oddTextField.gridwidth = 3;
		gbc_oddTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_oddTextField.insets = new Insets(0, 0, 5, 5);
		gbc_oddTextField.gridx = 14;
		gbc_oddTextField.gridy = 12;
		add(oddTextField, gbc_oddTextField);
		oddTextField.setColumns(10);
		answerButton.setBounds(409, 253, 120, 23);
		GridBagConstraints gbc_answerButton = new GridBagConstraints();
		gbc_answerButton.gridwidth = 2;
		gbc_answerButton.fill = GridBagConstraints.HORIZONTAL;
		gbc_answerButton.insets = new Insets(0, 0, 5, 5);
		gbc_answerButton.gridx = 17;
		gbc_answerButton.gridy = 12;
		add(answerButton, gbc_answerButton);

		GridBagConstraints gbc_answerScrollPane = new GridBagConstraints();
		gbc_answerScrollPane.gridheight = 10;
		gbc_answerScrollPane.gridwidth = 14;
		gbc_answerScrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_answerScrollPane.fill = GridBagConstraints.BOTH;
		gbc_answerScrollPane.gridx = 6;
		gbc_answerScrollPane.gridy = 13;
		add(answerScrollPane, gbc_answerScrollPane);

		answerScrollPane.setViewportView(answerTable);
		answerScrollPane.getViewport().setBackground(new Color(250,250,250));
		answerTable.getTableHeader().setBackground(new Color(245,245,245));
		answerTable.setBackground(new Color(250,250,250));
		
		jButtonCreate = new FancyButton(ResourceBundle.getBundle("Etiquetas").getString("CreateQuery"),new Color(51,51,51),new Color(170,170,170),new Color(150,150,150));
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
		jLabelMinBet.setFont(new Font("Source Sans Pro", Font.BOLD, 14));
		jLabelMinBet.setBounds(new Rectangle(25, 359, 75, 20));

		GridBagConstraints gbc_jLabelMinBet = new GridBagConstraints();
		gbc_jLabelMinBet.anchor = GridBagConstraints.WEST;
		gbc_jLabelMinBet.insets = new Insets(0, 0, 5, 5);
		gbc_jLabelMinBet.gridx = 21;
		gbc_jLabelMinBet.gridy = 18;
		this.add(jLabelMinBet, gbc_jLabelMinBet);

		minPriceTextField = new JNumericField(8);
		minPriceTextField.setAllowNegative(false);
		minPriceTextField.setBorder(new LineBorder(Color.black));
		minPriceTextField.setBounds(new Rectangle(100, 359, 60, 20));
		minPriceTextField.setEnabled(false);
		GridBagConstraints gbc_jTextFieldPrice = new GridBagConstraints();
		gbc_jTextFieldPrice.gridwidth = 2;
		gbc_jTextFieldPrice.fill = GridBagConstraints.HORIZONTAL;
		gbc_jTextFieldPrice.insets = new Insets(0, 0, 5, 5);
		gbc_jTextFieldPrice.gridx = 21;
		gbc_jTextFieldPrice.gridy = 19;
		this.add(minPriceTextField, gbc_jTextFieldPrice);
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
		answerErrorLabel.setText("");
		createQuestionErrorLabel.setText("");
		oddTextField.setBorder(new LineBorder(Color.black));
		answerTextField.setBorder(new LineBorder(Color.black));
	}

	/**
	 * Disables areas/buttons where users can usually press/enter information related with question creation.
	 */
	public void disableInputFields() {
		oddLabel.setEnabled(false);
		answerLabel.setEnabled(false);
		jLabelMinBet.setEnabled(false);
		jButtonCreate.setEnabled(false);
		answerButton.setEnabled(false);
		minPriceTextField.setEnabled(false);
		answerTextField.setEnabled(false);
		oddTextField.setEnabled(false);	
	}

	/**
	 * Enables areas/buttons where users can usually press/enter information related with question creation.
	 */
	public void enableInputFields() {
		oddLabel.setEnabled(true);
		answerLabel.setEnabled(true);
		jLabelMinBet.setEnabled(true);
		jButtonCreate.setEnabled(true);
		answerButton.setEnabled(true);
		minPriceTextField.setEnabled(true);
		answerTextField.setEnabled(true);
		oddTextField.setEnabled(true);
	}

	/**
	 * Recomputes and displays the events that satisfy the user inputed restrictions, that is, the selected competition and dates.
	 * 
	 * @param comp  user selected competition
	 * @param date	user selected date
	 */
	public void refreshPage(Competition comp, JCalendar jCalendar) {
		resetErrorHighlights();
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		DateFormat dateformat1 = DateFormat.getDateInstance(1, jCalendar.getLocale());

		calendarMio = jCalendar.getCalendar();
		Competition selectedcompetition = competitionPanel.getSelectedCompetition();
		try {
			jComboBoxEvents.removeAllItems();
			if(selectedcompetition != null) {
				for (domain.Event ev : selectedcompetition.getEvents()) {
					if(df.format(ev.getEventDate()).equals(df.format(jCalendar.getDate())))
						modelEvents.addElement(ev);			
				}
			}
			if (modelEvents.getSize() == 0)
				listOfEventsLabel.setText(ResourceBundle.getBundle("Etiquetas").getString("NoEvents")
						+ ": " + dateformat1.format(calendarMio.getTime()));
			else {
				loadQuestions(modelEvents.getElementAt(0));
				listOfEventsLabel.setText(ResourceBundle.getBundle("Etiquetas").getString("Events") + ": "
						+ dateformat1.format(calendarMio.getTime()));
			}
			jComboBoxEvents.repaint();

			if (modelEvents.getSize() == 0) {
				disableInputFields();
			}	    
			else {
				enableInputFields();
			}

		} catch (Exception e1) {
			e1.printStackTrace();
		}
		BrowsePanel.paintDaysWithEvents(jCalendar, facade.getEventsMonthByCompetition(jCalendar.getDate(), selectedcompetition));
	}

	private void jButtonCreate_actionPerformed(ActionEvent e) {
		domain.Event event = ((domain.Event) jComboBoxEvents.getSelectedItem());

		try {
			answerErrorLabel.setText("");
			createQuestionErrorLabel.setText("");

			// Displays an exception if the query field is empty
			String inputQuery = jHintFieldQuery.getText(); 

			if (inputQuery.length() > 0) {
				System.out.println("size " + answers.toString());
				if (answers.size() < 2) {
					answerErrorLabel.setText("Introduce answers");
				}else {
					// It could be to trigger an exception if the introduced string is not a number
					float inputPrice = Float.parseFloat(minPriceTextField.getText());
					// Obtain the business logic from a StartWindow class (local or remote)
					BLFacade facade = MainGUI.getBusinessLogic();
					List<Prediction> predictions = new ArrayList<Prediction>();
					for(String ans: answers.keySet()) {
						predictions.add(new Prediction(ans,answers.get(ans)));
					}

					Question q = facade.createQuestion(event, inputQuery, inputPrice, predictions);
					event.addQuestion(q.getQuestion(), q.getBetMinimum(), predictions);
					loadQuestions(event);

					answers = new HashMap<String, Float>();
					resetAnswerTable();

					createQuestionErrorLabel.setText(ResourceBundle.getBundle("Etiquetas").getString("QueryCreated"));
				}

				minPriceTextField.setText("");
				answerTextField.setText("");
				oddTextField.setText("");

			} else
				createQuestionErrorLabel.setText(ResourceBundle.getBundle("Etiquetas").getString("ErrorQuery"));
		} catch (EventFinished e1) {
			createQuestionErrorLabel.setText(ResourceBundle.getBundle("Etiquetas").getString("ErrorEventHasFinished") + ": "
					+ event.getDescription());
		} catch (QuestionAlreadyExist e1) {
			createQuestionErrorLabel.setText(ResourceBundle.getBundle("Etiquetas").getString("ErrorQueryAlreadyExist"));
		} catch (java.lang.NumberFormatException e1) {
			createQuestionErrorLabel.setText(ResourceBundle.getBundle("Etiquetas").getString("ErrorNumber"));
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	public void loadQuestions(Event e) {
		questionTableModel.setDataVector(null, columNameQuestions);
		questionTable.getColumnModel().getColumn(0).setPreferredWidth(50);
		questionTable.getColumnModel().getColumn(0).setMinWidth(50);
		questionTable.getColumnModel().getColumn(0).setMaxWidth(50);
		questionTable.getColumnModel().getColumn(1).setMinWidth(100);

		if(e != null) {
			List<Question> questions = e.getQuestions();
			int i = 0;
			for(Question q : questions) {
				Object[] row = new Object[2];
				row[0] = ++i;
				row[1] = q.getQuestion();

				questionTableModel.addRow(row);
			}
		}	
	}

	public void resetAnswerTable() {
		answerTableModel.setDataVector(null, columNameAnswers);
		answerTableModel.setColumnCount(4); 
		answerTable.setRowHeight(28);
		answerTable.setModel(answerTableModel);
		answerTable.getColumnModel().getColumn(0).setPreferredWidth(60);
		answerTable.getColumnModel().getColumn(0).setMinWidth(60);
		answerTable.getColumnModel().getColumn(0).setMaxWidth(60);
		answerTable.getColumnModel().getColumn(2).setPreferredWidth(80);
		answerTable.getColumnModel().getColumn(2).setMinWidth(80);
		answerTable.getColumnModel().getColumn(2).setMaxWidth(80);
		answerTable.getColumnModel().getColumn(3).setPreferredWidth(50);
		answerTable.getColumnModel().getColumn(3).setMinWidth(50);
		answerTable.getColumnModel().getColumn(3).setMaxWidth(50);
		answerTable.getTableHeader().setFont(new Font("Source sans Pro", Font.BOLD, 16));
		answerTable.setFont(new Font("Source sans Pro", Font.BOLD, 14));
		@SuppressWarnings("unused")
		ButtonColumn deleteButtonColumn = new ButtonColumn(answerTable, delete, 3, new Color(255,0,51));
	}

	public void answerTableAdd(String answer, Float odds) {

		Object[] row = new Object[4];
		row[0] = answerTableModel.getRowCount()+1;
		row[1] = answer;
		row[2] = odds;
		row[3] = new ImageIcon("images/delete.png");

		answerTableModel.addRow(row);
	}

	Action delete = new AbstractAction() {
		@Override
		public void actionPerformed(ActionEvent e) {
			int row = answerTable.getSelectedRow();
			answers.remove(answerTable.getValueAt(row, 1));
			answerTableModel.removeRow(row);
		}
	};

}
