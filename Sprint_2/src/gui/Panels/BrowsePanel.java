package gui.Panels;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.Vector;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import com.toedter.calendar.JCalendar;
import businessLogic.BLFacade;
import domain.BetType;
import domain.Competition;
import domain.Event;
import domain.Prediction;
import domain.Question;
import domain.Sport;
import exceptions.InsufficientCash;
import gui.LoginGUI;
import gui.MainGUI;
import gui.RegisterGUI;
import gui.components.CompetitionPanel;
import gui.components.FancyButton;
import gui.components.JNumericField;
import gui.components.NonEditableTableModel;
import net.miginfocom.swing.MigLayout;
import javax.swing.UIManager;
import javax.swing.border.EtchedBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListSelectionModel;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.factories.DefaultComponentFactory;


@SuppressWarnings("serial")
public class BrowsePanel extends JPanel {
	//JCalendar
	private JCalendar jCalendar1 = new JCalendar();

	private JComboBox<String> answerComboBox = new JComboBox<String>();	

	private JButton btnBet;
	private JButton multibetButton ;
	private final FancyButton addToCouponButton; //$NON-NLS-1$ //$NON-NLS-2$;
	private final SportButton footballButton = new SportButton(new ImageIcon("images/sports/yfootball.png"), new ImageIcon("images/sports/football.png"), Sport.FOOTBALL);
	private final SportButton basketButton = new SportButton(new ImageIcon("images/sports/ybasketball.png"), new ImageIcon("images/sports/basketball.png"), Sport.BASKETBALL);
	private final SportButton tennisButton = new SportButton(new ImageIcon("images/sports/ytennis.png"), new ImageIcon("images/sports/tennis.png"), Sport.TENNIS);
	private final SportButton boxingButton = new SportButton(new ImageIcon("images/sports/yboxing.png"), new ImageIcon("images/sports/boxing.png"), Sport.BOXING);
	private final SportButton horseButton = new SportButton(new ImageIcon("images/sports/yhorse.png"), new ImageIcon("images/sports/horse.png"), Sport.HORSE_RACING);
	private final SportButton golfButton = new SportButton(new ImageIcon("images/sports/ygolf.png"), new ImageIcon("images/sports/golf.png"), Sport.GOLF);
	private List<SportButton> sportbuttons = new ArrayList<SportButton>();

	private final JLabel totwinLabel = new JLabel("");
	private final JLabel chosenCompetitionLabel = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("NoCompetitionSelected"));
	private final JLabel couponLabel = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Coupon"));
	private final JLabel competitionsLabel = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("COMPETITIONS"));
	private final JLabel sportsLabel = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("SPORTS"));
	private final JLabel stakeLabel = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("StakePerBet") +":");
	private final JLabel totwintitleLabel = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("PossibleWinnings") +":");
	private final JLabel jLabelQueries = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Queries")); 
	private final JLabel jLabelEvents = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Events"));  //$NON-NLS-1$ //$NON-NLS-2$
	private final JLabel errorlabel = new JLabel();
	private final JLabel lblChooseYourPrediction = DefaultComponentFactory.getInstance().createLabel(ResourceBundle.getBundle("Etiquetas").getString("ChooseYourPrediction")); 

	private final JPanel selectionPanel = new JPanel();
	private final JScrollPane competitionScrollPane = new JScrollPane();
	private CompetitionPanel competitionPanel;

	private  JScrollPane betScrollPane = new JScrollPane();
	private final JScrollPane scrollPaneEvents = new JScrollPane();
	private final JScrollPane scrollPaneQueries = new JScrollPane();
	private final JPanel betPane = new JPanel();
	private final JPanel sportsPanel = new JPanel();
	private final JPanel couponPanel = new JPanel();

	private JTable tableEvents;
	private JTable tableQueries;

	private NonEditableTableModel tableModelEvents;
	private NonEditableTableModel tableModelQueries;

	private String[] columnNamesEvents = new String[] {
			ResourceBundle.getBundle("Etiquetas").getString("EventN"), 
			ResourceBundle.getBundle("Etiquetas").getString("Event"),
			ResourceBundle.getBundle("Etiquetas").getString("Starts"),
			ResourceBundle.getBundle("Etiquetas").getString("Ends"),
	};
	private String[] columnNamesQueries = new String[] {
			ResourceBundle.getBundle("Etiquetas").getString("QueryN"), 
			ResourceBundle.getBundle("Etiquetas").getString("Query"),
			ResourceBundle.getBundle("Etiquetas").getString("MinimumBet")

	};


	private final JNumericField globalStakeTextField = new JNumericField(7, JNumericField.DECIMAL);

	private DocumentListener allStakeListener = new allStakeListener();

	private final JPanel logoPanel = new JPanel();
	private final JPanel bettingPanel = new JPanel();
	private  JScrollPane multibetScrollPanel = new JScrollPane();
	private JPanel multibetPanel = new JPanel();

	private Set<Question> selectedquestions = new HashSet<Question>();
	private Map<Event, List<Prediction>> selectedevents = new HashMap<Event, List<Prediction>>();
	private Set<Prediction> selectedpredictions = new HashSet<Prediction>();

	private DefaultTableCellRenderer whiteRenderer = null;
	private DefaultTableCellRenderer activeEventRenderer = null;
	private DefaultTableCellRenderer activeQuestionRenderer = null;
	private DefaultTableCellRenderer fullQuestionRenderer = null;

	private BLFacade facade = MainGUI.getBusinessLogic();

	/**
	 * Create the panel.
	 */
	public BrowsePanel() {


		setBackground(UIManager.getColor("Button.highlight"));

		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{19, 265, 30, 54, 0, 72, 30, 150, 30, 100, 39, 38, 0, 46, 76, 0};
		gridBagLayout.rowHeights = new int[]{49, 65, 60, 25, 64, 150, 15, 14, 60, 22, 10, 35, 50, 10, 10, 22, 40, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);

		GridBagConstraints gbc_sportsPanel = new GridBagConstraints();
		gbc_sportsPanel.gridwidth = 15;
		gbc_sportsPanel.insets = new Insets(0, 0, 0, 0);
		gbc_sportsPanel.fill = GridBagConstraints.BOTH;
		gbc_sportsPanel.gridx = 0;
		gbc_sportsPanel.gridy = 0;
		sportsPanel.setBackground(new Color(105, 105, 105));
		add(sportsPanel, gbc_sportsPanel);
		sportsPanel.setLayout(new FormLayout(new ColumnSpec[] {
				FormSpecs.LABEL_COMPONENT_GAP_COLSPEC,
				ColumnSpec.decode("129px"),
				ColumnSpec.decode("100px"),
				ColumnSpec.decode("107px"),
				ColumnSpec.decode("100px"),
				ColumnSpec.decode("100px"),
				ColumnSpec.decode("116px"),
				ColumnSpec.decode("100px"),},
				new RowSpec[] {
						RowSpec.decode("fill:74px"),}));


		//SET UP SPORTS MENU
		sportsLabel.setOpaque(false);
		sportsLabel.setForeground(new Color(255,202,24));
		sportsLabel.setHorizontalAlignment(SwingConstants.CENTER);
		sportsLabel.setFont(new Font("Source Sans Pro", Font.BOLD | Font.ITALIC, 22));
		sportsPanel.add(sportsLabel, "2, 1, fill, fill");
		sportsPanel.add(footballButton, "3, 1, fill, fill");		
		sportsPanel.add(basketButton, "4, 1, fill, fill");		
		sportsPanel.add(tennisButton, "5, 1, fill, fill");
		sportsPanel.add(boxingButton, "6, 1, fill, fill");	
		sportsPanel.add(horseButton, "7, 1, fill, fill");
		sportsPanel.add(golfButton, "8, 1, fill, fill");

		sportbuttons.add(footballButton);
		sportbuttons.add(basketButton);
		sportbuttons.add(tennisButton);
		sportbuttons.add(boxingButton);
		sportbuttons.add(horseButton);
		sportbuttons.add(golfButton);

		GridBagConstraints gbc_selectionPanel = new GridBagConstraints();
		gbc_selectionPanel.insets = new Insets(0, 0, 0, 5);
		gbc_selectionPanel.anchor = GridBagConstraints.WEST;
		gbc_selectionPanel.gridheight = 16;
		gbc_selectionPanel.gridwidth = 2;
		gbc_selectionPanel.fill = GridBagConstraints.VERTICAL;
		gbc_selectionPanel.gridx = 0;
		gbc_selectionPanel.gridy = 1;
		selectionPanel.setBackground((new Color(240, 240, 240)));
		add(selectionPanel, gbc_selectionPanel);
		GridBagLayout gbl_selectionPanel = new GridBagLayout();
		gbl_selectionPanel.columnWidths = new int[]{0, 75, 10, 157};
		gbl_selectionPanel.rowHeights = new int[]{11, 0, 0, 0, 30, 2, 0, 0, 0, 0, 216, 0};
		gbl_selectionPanel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0};
		gbl_selectionPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		selectionPanel.setLayout(gbl_selectionPanel);
		jCalendar1.getDayChooser().getDayPanel().setBorder(null);
		jCalendar1.getDayChooser().getDayPanel().setBackground(UIManager.getColor("Button.highlight"));
		jCalendar1.getMonthChooser().getSpinner().setBackground(Color.WHITE);
		jCalendar1.getDayChooser().getDayPanel().setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		jCalendar1.getDayChooser().setSelectedColor(new Color(196,196,196));
		jCalendar1.getDayChooser().setSundayForeground(Color.RED);
		jCalendar1.getDayChooser().setDecorationBackgroundColor(Color.WHITE);
		jCalendar1.getDayChooser().setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
		jCalendar1.getDayChooser().getDayPanel().setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0, Color.BLACK));
		jCalendar1.getDayChooser().setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
		jCalendar1.getYearChooser().getSpinner().setBackground(Color.WHITE);
		jCalendar1.getMonthChooser().getComboBox().setBackground(Color.WHITE);
		GridBagConstraints gbc_jCalendar1 = new GridBagConstraints();
		gbc_jCalendar1.fill = GridBagConstraints.BOTH;
		gbc_jCalendar1.gridheight = 3;
		gbc_jCalendar1.gridwidth = 4;
		gbc_jCalendar1.insets = new Insets(15, 15, 5, 15);
		gbc_jCalendar1.gridx = 0;
		gbc_jCalendar1.gridy = 0;
		selectionPanel.add(jCalendar1, gbc_jCalendar1);

		GridBagConstraints gbc_competitionsLabel = new GridBagConstraints();
		gbc_competitionsLabel.fill = GridBagConstraints.BOTH;
		gbc_competitionsLabel.gridwidth = 4;
		gbc_competitionsLabel.insets = new Insets(10, 0, 0, 0);
		gbc_competitionsLabel.gridx = 0;
		gbc_competitionsLabel.gridy = 3;
		competitionsLabel.setBackground(new Color(0, 0, 0));
		competitionsLabel.setForeground(new Color(0, 0, 0));
		competitionsLabel.setFont(new Font("Source Sans Pro", Font.BOLD, 16));
		competitionsLabel.setHorizontalAlignment(SwingConstants.CENTER);
		selectionPanel.add(competitionsLabel, gbc_competitionsLabel);

		GridBagConstraints gbc_competitionScrollPane = new GridBagConstraints();
		gbc_competitionScrollPane.insets = new Insets(0, 15, 15, 15);
		gbc_competitionScrollPane.gridwidth = 4;
		gbc_competitionScrollPane.gridheight = 7;
		gbc_competitionScrollPane.fill = GridBagConstraints.BOTH;
		gbc_competitionScrollPane.gridx = 0;
		gbc_competitionScrollPane.gridy = 4;
		competitionScrollPane.setViewportBorder(null);
		selectionPanel.add(competitionScrollPane, gbc_competitionScrollPane);
		competitionScrollPane.setBackground(new Color(245, 245, 245));

		// Code for JCalendar
		this.jCalendar1.addPropertyChangeListener(new PropertyChangeListener()
		{

			public void propertyChange(PropertyChangeEvent propertychangeevent)
			{

				if (propertychangeevent.getPropertyName().equals("locale"))
				{
					jCalendar1.setLocale((Locale) propertychangeevent.getNewValue());
				}
				else if (propertychangeevent.getPropertyName().equals("calendar"))
				{
					//DateFormat dateformat1 = DateFormat.getDateInstance(1, jCalendar1.getLocale());
					//Date firstDay=UtilDate.trim(new Date(calendarMio.getTime().getTime()));
					refreshPage();
				}
				Vector<Date> eventsMonth = facade.getEventsMonth(jCalendar1.getDate(), competitionPanel.getSelectedCompetition());
				paintDaysWithEvents(jCalendar1, eventsMonth);
			} 
		});

		GridBagConstraints gbc_logoPanel = new GridBagConstraints();
		gbc_logoPanel.gridwidth = 7;
		gbc_logoPanel.gridheight = 2;
		gbc_logoPanel.insets = new Insets(0, 0, 5, 5);
		gbc_logoPanel.fill = GridBagConstraints.HORIZONTAL;
		gbc_logoPanel.gridx = 2;
		gbc_logoPanel.gridy = 1;
		logoPanel.setBackground(Color.WHITE);
		add(logoPanel, gbc_logoPanel);
		logoPanel.add(chosenCompetitionLabel);
		chosenCompetitionLabel.setFont(new Font("Source Sans Pro Light", Font.PLAIN, 18));

		GridBagConstraints gbc_couponPanel = new GridBagConstraints();
		gbc_couponPanel.gridheight = 16;
		gbc_couponPanel.gridwidth = 6;
		gbc_couponPanel.fill = GridBagConstraints.BOTH;
		gbc_couponPanel.gridx = 9;
		gbc_couponPanel.gridy = 1;
		add(couponPanel, gbc_couponPanel);
		GridBagLayout gbl_couponPanel = new GridBagLayout();
		gbl_couponPanel.columnWidths = new int[]{79, 20, 20, 76, 20, 51, 33, 0};
		gbl_couponPanel.rowHeights = new int[]{33, 30, 134, 0, 0, 30, 20, 40, 40, 20, 0};
		gbl_couponPanel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_couponPanel.rowWeights = new double[]{0.0, 1.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		couponPanel.setLayout(gbl_couponPanel);
		couponPanel.setBackground((new Color(240,240,240)));

		GridBagConstraints gbc_couponLabel = new GridBagConstraints();
		gbc_couponLabel.fill = GridBagConstraints.HORIZONTAL;
		gbc_couponLabel.gridwidth = 7;
		gbc_couponLabel.insets = new Insets(0, 0, 0, 0);
		gbc_couponLabel.gridx = 0;
		gbc_couponLabel.gridy = 0;
		couponLabel.setHorizontalAlignment(SwingConstants.CENTER);
		couponLabel.setBackground(Color.GRAY);
		couponLabel.setForeground(Color.BLACK);
		couponLabel.setFont(new Font("Source Sans Pro", Font.BOLD, 18));
		couponPanel.add(couponLabel, gbc_couponLabel);

		GridBagConstraints gbc_bettingPanel = new GridBagConstraints();
		gbc_bettingPanel.gridheight = 4;
		gbc_bettingPanel.gridwidth = 7;
		gbc_bettingPanel.insets = new Insets(0, 0, 5, 0);
		gbc_bettingPanel.fill = GridBagConstraints.BOTH;
		gbc_bettingPanel.gridx = 0;
		gbc_bettingPanel.gridy = 1;
		couponPanel.add(bettingPanel, gbc_bettingPanel);

		multibetButton = new FancyButton(ResourceBundle.getBundle("Etiquetas").getString("ShowMultipleBetOptions"), new Color(210,210,210),new Color(169,169,169),new Color(130, 130, 130)) {
			@Override
			public Dimension getMaximumSize()
			{
				Dimension d = new Dimension(getParent().getWidth(), 30);
				return d;
			}
		};
		multibetButton.setFont(new Font("Source Sans Pro", Font.PLAIN, 15));

		multibetButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		multibetButton.setEnabled(false);
		multibetButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(multibetScrollPanel.isVisible()) {
					multibetButton.setText(ResourceBundle.getBundle("Etiquetas").getString("ShowMultipleBetOptions"));
					multibetScrollPanel.setVisible(false);
					multibetPanel.setVisible(false);
					betScrollPane.repaint();
					betScrollPane.revalidate();
					bettingPanel.repaint();
					bettingPanel.revalidate();
				}
				else {
					multibetButton.setText(ResourceBundle.getBundle("Etiquetas").getString("HideMultipleBetOptions"));
					multibetScrollPanel.setVisible(true);
					multibetPanel.setVisible(true);
					betScrollPane.repaint();
					betScrollPane.revalidate();
					bettingPanel.repaint();
					bettingPanel.revalidate();
				}
			}
		});

		betScrollPane = new JScrollPane(){
			@Override
			public Dimension getMinimumSize()
			{
				Dimension d = new Dimension(getParent().getWidth(), 3);
				return d;
			}
		};
		betPane.setLayout(new BoxLayout(betPane,BoxLayout.Y_AXIS));
		betScrollPane.add(betPane);
		betScrollPane.setViewportView(betPane);
		//betScrollPane.setBorder(null);
		//betScrollPane.getVerticalScrollBar().setUI(new MyScrollBarUI());

		multibetScrollPanel = new JScrollPane();

		bettingPanel.setLayout(new BoxLayout(bettingPanel,BoxLayout.Y_AXIS));

		multibetPanel.setLayout(new BoxLayout(multibetPanel,BoxLayout.Y_AXIS));
		multibetScrollPanel.add(multibetPanel);
		multibetScrollPanel.setViewportView(multibetPanel);
		multibetScrollPanel.setVisible(false);

		bettingPanel.add(betScrollPane);
		bettingPanel.add(multibetScrollPanel);
		bettingPanel.add(multibetButton);
		betScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		multibetScrollPanel.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		GridBagConstraints gbc_jLabelEvents = new GridBagConstraints();

		jLabelEvents.setFont(new Font("Source Sans Pro", Font.BOLD, 16));
		gbc_jLabelEvents.fill = GridBagConstraints.HORIZONTAL;
		gbc_jLabelEvents.insets = new Insets(0, 0, 5, 5);
		gbc_jLabelEvents.gridwidth = 5;
		gbc_jLabelEvents.gridx = 3;
		gbc_jLabelEvents.gridy = 3;
		add(jLabelEvents, gbc_jLabelEvents);


		tableEvents = new JTable() {
			public TableCellRenderer getCellRenderer(int row, int column)
			{
				if (whiteRenderer == null)
				{
					whiteRenderer = new DefaultTableCellRenderer();
					whiteRenderer.setHorizontalAlignment(SwingConstants.CENTER);
				}
				if (activeEventRenderer == null)
				{
					activeEventRenderer = new DefaultTableCellRenderer();
					activeEventRenderer.setBackground(new Color(192,235,240));
					activeEventRenderer.setHorizontalAlignment(SwingConstants.CENTER);
				}

				if ( selectedevents.keySet().contains(tableModelEvents.getValueAt(row, 4)))
					return activeEventRenderer;
				else
					return whiteRenderer;
			}
		};
		tableEvents.setBorder(null);
		tableEvents.setFont(new Font("Source Sans Pro", Font.PLAIN, 15));
		tableEvents.setBackground(Color.WHITE);

		tableEvents.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				int i=tableEvents.getSelectedRow();
				if(i != -1) {
					domain.Event ev=(domain.Event)tableModelEvents.getValueAt(i,4); // obtain ev object
					List<Question> queries=ev.getQuestions();

					tableModelQueries.setDataVector(null, columnNamesQueries);
					tableModelQueries.setColumnCount(4); // another column added to allocate ev object

					if (queries.isEmpty())
						jLabelQueries.setText(ResourceBundle.getBundle("Etiquetas").getString("NoQueries")+": "+ev.getDescription());
					else 
						jLabelQueries.setText(ResourceBundle.getBundle("Etiquetas").getString("SelectedEvent")+" "+ev.getDescription());

					for (domain.Question q:queries){
						Vector<Object> row = new Vector<Object>();

						row.add(q.getQuestionNumber());
						row.add(q.getQuestion());
						row.add(q.getBetMinimum());
						row.add(q); // Question object added in order to obtain it with tableModelQueries.getValueAt(i,3)
						tableModelQueries.addRow(row);	
					}
					tableQueries.getColumnModel().getColumn(0).setPreferredWidth(60);
					tableQueries.getColumnModel().getColumn(0).setMinWidth(60);
					tableQueries.getColumnModel().getColumn(0).setMaxWidth(60);
					tableQueries.getColumnModel().getColumn(1).setPreferredWidth(268);
					tableQueries.getColumnModel().getColumn(2).setPreferredWidth(25);
					tableQueries.getColumnModel().removeColumn(tableQueries.getColumnModel().getColumn(3));
				}
				

			}
		});

		/*

		 */
		scrollPaneEvents.setViewportView(tableEvents);

		tableModelEvents = new NonEditableTableModel(null, columnNamesEvents);
		tableModelQueries = new NonEditableTableModel(null, columnNamesQueries);

		tableEvents.setModel(tableModelEvents);
		tableEvents.getColumnModel().getColumn(0).setResizable(false);
		tableEvents.getColumnModel().getColumn(0).setPreferredWidth(60);
		tableEvents.getColumnModel().getColumn(0).setMinWidth(60);
		tableEvents.getColumnModel().getColumn(0).setMaxWidth(60);
		tableEvents.getColumnModel().getColumn(1).setPreferredWidth(200);
		tableEvents.getColumnModel().getColumn(1).setMinWidth(200);
		tableEvents.getColumnModel().getColumn(2).setMinWidth(75);
		tableEvents.getColumnModel().getColumn(2).setMaxWidth(75);
		tableEvents.getColumnModel().getColumn(3).setMinWidth(75);
		tableEvents.getColumnModel().getColumn(3).setMaxWidth(75);

		tableEvents.setRowHeight(40);
		tableEvents.setFocusable(false);
		tableEvents.getTableHeader().setReorderingAllowed(false);
		tableEvents.getTableHeader().setResizingAllowed(false);
		tableEvents.getTableHeader().setFont(new Font("Source sans Pro", Font.BOLD, 16));
		tableEvents.getTableHeader().setBackground(new Color(255,255,255));


		for(Component c : jCalendar1.getDayChooser().getDayPanel().getComponents()) {
			c.setFont(new Font("Source sans Pro", Font.PLAIN, 14));
		}

		GridBagConstraints gbc_scrollPaneEvents = new GridBagConstraints();
		gbc_scrollPaneEvents.gridheight = 2;
		gbc_scrollPaneEvents.gridwidth = 5;
		gbc_scrollPaneEvents.fill = GridBagConstraints.BOTH;
		gbc_scrollPaneEvents.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPaneEvents.gridx = 3;
		gbc_scrollPaneEvents.gridy = 4;
		add(scrollPaneEvents, gbc_scrollPaneEvents);

		tableQueries = new JTable() {
			public TableCellRenderer getCellRenderer(int row, int column)
			{
				Question rowquestion =  (Question)tableModelQueries.getValueAt(row, 3);
				boolean full = true;
				if(rowquestion.getPredictions() != null) {
					for(Prediction p : rowquestion.getPredictions()) {
						if(!selectedpredictions.contains(p)) {
							full = false;
						}
					}
				}

				if (whiteRenderer == null)
				{
					whiteRenderer = new DefaultTableCellRenderer();
					whiteRenderer.setHorizontalAlignment(SwingConstants.CENTER);
				}
				if (activeQuestionRenderer == null)
				{
					activeQuestionRenderer = new DefaultTableCellRenderer();
					activeQuestionRenderer.setBackground(new Color(255,248,148));
					activeQuestionRenderer.setHorizontalAlignment(SwingConstants.CENTER);
				}
				if (fullQuestionRenderer == null)
				{
					fullQuestionRenderer = new DefaultTableCellRenderer();
					fullQuestionRenderer.setBackground(new Color(255,149,73));
					fullQuestionRenderer.setHorizontalAlignment(SwingConstants.CENTER);
				}

				if(full) {
					return fullQuestionRenderer;
				}
				else if (selectedquestions.contains(tableModelQueries.getValueAt(row, 3))) {
					return activeQuestionRenderer;
				}		
				else {
					return whiteRenderer;
				}			
			}
		};

		tableQueries.setRowHeight(40);
		tableQueries.setBackground(Color.WHITE);
		tableQueries.setFocusable(false);
		tableQueries.setModel(tableModelQueries);
		tableQueries.getColumnModel().getColumn(0).setPreferredWidth(60);
		tableQueries.getColumnModel().getColumn(0).setMinWidth(60);
		tableQueries.getColumnModel().getColumn(0).setMaxWidth(60);
		tableQueries.getColumnModel().getColumn(1).setPreferredWidth(100);
		tableQueries.getColumnModel().getColumn(1).setMinWidth(100);
		tableQueries.getColumnModel().getColumn(2).setMinWidth(75);
		tableQueries.getTableHeader().setReorderingAllowed(false);
		tableQueries.getTableHeader().setResizingAllowed(false);
		tableQueries.getTableHeader().setFont(new Font("Source sans Pro", Font.BOLD, 16));
		tableQueries.getTableHeader().setBackground(new Color(255,255,255));
		tableQueries.setSelectionModel(new DefaultListSelectionModel() {
			@Override
			public boolean isSelectedIndex(final int index) {
				Question rowquestion = (Question)tableModelQueries.getValueAt(index, 3);
				boolean isSelected;
				boolean full = true;
				if(rowquestion.getPredictions() != null) {
					for(Prediction p : rowquestion.getPredictions()) {
						if(!selectedpredictions.contains(p)) {
							full = false;
						}
					}
				}
				if ( full ) {
					isSelected = false;		
				} else {
					isSelected = super.isSelectedIndex(index);
				}
				return isSelected;
			}
		});
		tableQueries.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				answerComboBox.removeAllItems();
				errorlabel.setText("");
				refreshAnswerSelection();
			}
		});	
		scrollPaneQueries.setViewportView(tableQueries);


		GridBagConstraints gbc_jLabelQueries = new GridBagConstraints();
		gbc_jLabelQueries.gridwidth = 3;
		gbc_jLabelQueries.anchor = GridBagConstraints.NORTHWEST;
		gbc_jLabelQueries.insets = new Insets(0, 0, 5, 5);
		gbc_jLabelQueries.gridx = 3;
		gbc_jLabelQueries.gridy = 7;
		jLabelQueries.setFont(new Font("Source Sans Pro", Font.BOLD, 16));
		add(jLabelQueries, gbc_jLabelQueries);

		GridBagConstraints gbc_lblChooseYourPrediction = new GridBagConstraints();
		gbc_lblChooseYourPrediction.anchor = GridBagConstraints.SOUTH;
		gbc_lblChooseYourPrediction.insets = new Insets(0, 0, 5, 5);
		gbc_lblChooseYourPrediction.gridx = 7;
		gbc_lblChooseYourPrediction.gridy = 9;
		lblChooseYourPrediction.setFont(new Font("Source Sans Pro", Font.PLAIN, 15));
		add(lblChooseYourPrediction, gbc_lblChooseYourPrediction);


		GridBagConstraints gbc_scrollPaneQueries = new GridBagConstraints();
		gbc_scrollPaneQueries.gridwidth = 3;
		gbc_scrollPaneQueries.fill = GridBagConstraints.BOTH;
		gbc_scrollPaneQueries.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPaneQueries.gridheight = 7;
		gbc_scrollPaneQueries.gridx = 3;
		gbc_scrollPaneQueries.gridy = 8;
		add(scrollPaneQueries, gbc_scrollPaneQueries);

		GridBagConstraints gbc_answerComboBox = new GridBagConstraints();
		gbc_answerComboBox.fill = GridBagConstraints.BOTH;
		gbc_answerComboBox.insets = new Insets(0, 0, 5, 5);
		gbc_answerComboBox.gridx = 7;
		gbc_answerComboBox.gridy = 10;
		add(answerComboBox, gbc_answerComboBox);



		addToCouponButton = new FancyButton(ResourceBundle.getBundle("Etiquetas").getString("AddBetToCoupon"), new Color(200,200,200),new Color(169,169,169),new Color(130, 130, 130));
		addToCouponButton.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
		addToCouponButton.setFont(new Font("Source Sans Pro", Font.BOLD, 13));
		addToCouponButton.setBorderPainted(true);
		refreshAnswerSelection();
		GridBagConstraints gbc_addToCouponButton = new GridBagConstraints();
		gbc_addToCouponButton.fill = GridBagConstraints.BOTH;
		gbc_addToCouponButton.insets = new Insets(0, 0, 5, 5);
		gbc_addToCouponButton.gridx = 7;
		gbc_addToCouponButton.gridy = 12;
		addToCouponButton.setBackground(new Color(200, 200, 200));
		addToCouponButton.setFont(new Font("Source Sans Pro", Font.PLAIN, 13));
		add(addToCouponButton, gbc_addToCouponButton);
		addToCouponButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int s = tableQueries.getSelectedRow();
				if(s != -1) {
					Question selectedq = (Question)tableModelQueries.getValueAt(tableQueries.getSelectedRow(),3);
					BigDecimal minbet = new BigDecimal((Float)tableModelQueries.getValueAt(tableQueries.getSelectedRow(),2));
					System.out.println(selectedq.toString());
					if(answerComboBox.getSelectedItem() != null) {

						String[] ans = ((String)answerComboBox.getSelectedItem()).split(";");
						JPanel betpanel = new BetPanel(selectedq.getEvent(), selectedq, (String)answerComboBox.getSelectedItem(),minbet, minbet);
						betPane.add(betpanel);
						betPane.revalidate();

						//update event-prediction mappings that will be used to compute valid multiple bet combinations.
						if(selectedevents.containsKey(selectedq.getEvent())) {
							List<Prediction> currentpredictions = selectedevents.get(selectedq.getEvent());
							if(currentpredictions == null) {
								currentpredictions = new ArrayList<Prediction>();		
							}
							currentpredictions.add(selectedq.getPredictionByAnswer(ans[0]));
						}
						else {
							List<Prediction> currentpredictions = new ArrayList<Prediction>();	
							currentpredictions.add(selectedq.getPredictionByAnswer(ans[0]));
							selectedevents.put(selectedq.getEvent(),currentpredictions);
						}	

						selectedquestions.add(selectedq);
						selectedpredictions.add(selectedq.getPredictionByAnswer(ans[0]));
						for(Prediction p :selectedpredictions) {
							System.out.println(p.getAnswer());
						}

						tableEvents.repaint();
						tableQueries.repaint();

						refreshAnswerSelection();
						calcTotalBetValues();
						generateMultiBetOptions();
						if(!btnBet.isEnabled()) {
							btnBet.setEnabled(true);
						}
						if(betPane.getComponents().length == 10) {
							addToCouponButton.setEnabled(false);
							addToCouponButton.setText(ResourceBundle.getBundle("Etiquetas").getString("MaximumAmountReached"));
						}

					}					
				}	
			}
		});

		competitionScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

		Vector<Competition> competitions = facade.getCompetitions(Sport.FOOTBALL);
		competitionPanel = new CompetitionPanel(competitions);
		competitionPanel.addPropertyChangeListener(new CompetitionChangeEvent());
		competitionScrollPane.add(competitionPanel);
		competitionScrollPane.setViewportView(competitionPanel);

		GridBagConstraints gbc_stakeLabel = new GridBagConstraints();
		gbc_stakeLabel.gridwidth = 3;
		gbc_stakeLabel.insets = new Insets(0, 0, 5, 5);
		gbc_stakeLabel.anchor = GridBagConstraints.EAST;
		gbc_stakeLabel.gridx = 1;
		gbc_stakeLabel.gridy = 5;
		stakeLabel.setFont(new Font("Source Sans Pro", Font.PLAIN, 15));
		couponPanel.add(stakeLabel, gbc_stakeLabel);

		GridBagConstraints gbc_stakeTextField = new GridBagConstraints();
		gbc_stakeTextField.gridwidth = 3;
		gbc_stakeTextField.insets = new Insets(0, 0, 5, 7);
		gbc_stakeTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_stakeTextField.gridx = 4;
		gbc_stakeTextField.gridy = 5;
		couponPanel.add(globalStakeTextField, gbc_stakeTextField);

		GridBagConstraints gbc_totwintitleLabel = new GridBagConstraints();
		gbc_totwintitleLabel.gridwidth = 3;
		gbc_totwintitleLabel.anchor = GridBagConstraints.EAST;
		gbc_totwintitleLabel.insets = new Insets(0, 0, 5, 5);
		gbc_totwintitleLabel.gridx = 1;
		gbc_totwintitleLabel.gridy = 6;
		totwintitleLabel.setFont(new Font("Source Sans Pro", Font.PLAIN, 15));
		couponPanel.add(totwintitleLabel, gbc_totwintitleLabel);

		GridBagConstraints gbc_totwinLabel = new GridBagConstraints();
		gbc_totwinLabel.anchor = GridBagConstraints.EAST;
		gbc_totwinLabel.gridwidth = 3;
		gbc_totwinLabel.insets = new Insets(0, 0, 5, 7);
		gbc_totwinLabel.gridx = 4;
		gbc_totwinLabel.gridy = 6;
		totwinLabel.setHorizontalAlignment(SwingConstants.TRAILING);
		couponPanel.add(totwinLabel, gbc_totwinLabel);

		globalStakeTextField.setText("");
		globalStakeTextField.setPrecision(2);
		globalStakeTextField.setAllowNegative(false);
		globalStakeTextField.getDocument().addDocumentListener(allStakeListener);

		GridBagConstraints gbc_btnBet = new GridBagConstraints();
		gbc_btnBet.gridwidth = 5;
		gbc_btnBet.fill = GridBagConstraints.BOTH;
		gbc_btnBet.insets = new Insets(0, 0, 5, 5);
		gbc_btnBet.gridx = 1;
		gbc_btnBet.gridy = 8;
		couponPanel.add(getBtnBet(), gbc_btnBet);
		btnBet.setEnabled(false);

		errorlabel.setHorizontalAlignment(SwingConstants.CENTER);
		errorlabel.setBackground(Color.RED);
		errorlabel.setForeground(Color.RED);
		GridBagConstraints gbc_errorlabel = new GridBagConstraints();
		gbc_errorlabel.gridwidth = 5;
		gbc_errorlabel.insets = new Insets(0, 0, 5, 5);
		gbc_errorlabel.fill = GridBagConstraints.BOTH;
		gbc_errorlabel.gridx = 3;
		gbc_errorlabel.gridy = 15;
		add(errorlabel, gbc_errorlabel);

		footballButton.setSelected(true);
	}

	public void refreshAnswerSelection() {
		int i = tableQueries.getSelectedRow();
		answerComboBox.removeAllItems();
		if (i != -1 && tableQueries.getSelectionModel().isSelectedIndex(i)) {
			answerComboBox.setEnabled(true);
			Question selectedquestion = (Question)tableModelQueries.getValueAt(tableQueries.getSelectedRow(), 3);
			List<Prediction> predictions = selectedquestion.getPredictions();
			if(predictions != null) {
				for (Prediction p : predictions) {
					if(!selectedpredictions.contains(p)) {
						String item = p.getAnswer() + "; " + p.getOdds();
						answerComboBox.addItem(item);
					}
				}
				addToCouponButton.setEnabled(true);
			}
		}
		else {
			answerComboBox.setEnabled(false);
			addToCouponButton.setEnabled(false);
		}
	}


	/**
	 * PropertyChangeListener for the competition panel. Alters elements of the page depending on the currently selected competition.
	 * The events on the event table, and days painted in the calendar will be changed based on it.
	 */
	public class CompetitionChangeEvent implements PropertyChangeListener{
		public void propertyChange(PropertyChangeEvent evt) {
			if (evt.getPropertyName().equals("selectedcompetition"))
				refreshPage();	
		}
	}

	/**
	 * Calculates the addition of all individual possible winnings of each bet nad the total price.
	 */
	public void calcTotalBetValues() {
		double winnings = 0;
		double price = 0;
		DecimalFormat df = new DecimalFormat("#");
		df.setMaximumFractionDigits(2);

		for(Component bp : betPane.getComponents()) {
			if(bp instanceof BetPanel) {
				winnings += ((BetPanel)bp).getStake().doubleValue() * ((BetPanel)bp).getOdds();
				price += ((BetPanel)bp).getStake().doubleValue();
			}		
		}
		for(Component mbp : multibetPanel.getComponents()) {
			if(mbp instanceof multibetOption) {
				winnings += ((multibetOption)mbp).getWinnings();
				price += ((multibetOption)mbp).getStake()*((multibetOption)mbp).getCombinations();
			}
		}
		totwinLabel.setText(df.format(winnings) + "€");
		JButton btnbet = getBtnBet();
		if(price == 0) {
			btnbet.setText("Bet");
		}
		else {
			btnbet.setText("Bet" + " " + df.format(price) + "€");
		}
	}

	/**
	 * Sets the amount to bet on all currently selected bets to the indicated amount.
	 * @param stakes	The amount to set all bets to.
	 */
	public void setStakes(BigDecimal stakes) {
		for(Component c : betPane.getComponents()) {
			((BetPanel)c).disableStakeListener();
			if(stakes.compareTo(((BetPanel)c).getMinBet()) > 0) {
				((BetPanel)c).setStakes(stakes);
			}
			else {
				((BetPanel)c).setStakes(((BetPanel)c).getMinBet());
			}
			((BetPanel)c).enableStakeListener();
		}
	}


	/**
	 * Recomputes and displays the events that satisfy the user inputed restrictions, that is, the selected competition and dates.
	 * 
	 * @param comp  user selected competition
	 * @param date	user selected date
	 */
	public void refreshPage() {
		try {
			Competition comp = competitionPanel.getSelectedCompetition();

			SimpleDateFormat df1 = new SimpleDateFormat("HH:mm");
			SimpleDateFormat df2 = new SimpleDateFormat("dd/MM/yyyy");
			Vector<Date> dates = facade.getEventsMonth(jCalendar1.getDate(), comp);

			tableModelEvents.setDataVector(null, columnNamesEvents);
			tableModelEvents.setColumnCount(5); // another column added to allocate ev objects
			if(comp != null) {
				for(Event e: comp.getEvents()) {
					System.out.println(e.toString());
					//Date date = e.getEventDate();
					if(df2.format(e.getEventDate()).equals(df2.format(jCalendar1.getDate()))) {
						Vector<Object> row = new Vector<Object>();
						System.out.println("Events "+e);

						row.add(e.getEventNumber());
						row.add(e.getDescription());
						row.add(df1.format(e.getEventdate()));
						row.add(df1.format(e.getEndingdate()));
						row.add(e); // ev object added in order to obtain it with tableModelEvents.getValueAt(i,4)
						tableModelEvents.addRow(row);				
					}
				}
			}		
			tableEvents.getColumnModel().getColumn(0).setPreferredWidth(60);
			tableEvents.getColumnModel().getColumn(0).setMinWidth(60);
			tableEvents.getColumnModel().getColumn(0).setMaxWidth(60);
			tableEvents.getColumnModel().getColumn(0).setResizable(false);
			tableEvents.getColumnModel().getColumn(1).setPreferredWidth(200);
			tableEvents.getColumnModel().getColumn(1).setMinWidth(200);
			tableEvents.getColumnModel().getColumn(2).setMaxWidth(75);
			tableEvents.getColumnModel().getColumn(2).setMinWidth(75);
			tableEvents.getColumnModel().getColumn(3).setMaxWidth(75);
			tableEvents.getColumnModel().getColumn(3).setMinWidth(75);
			tableEvents.getColumnModel().removeColumn(tableEvents.getColumnModel().getColumn(4)); // not shown in JTable

			DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
			centerRenderer.setHorizontalAlignment( SwingConstants.CENTER );

			tableEvents.getColumnModel().getColumn(0).setCellRenderer( centerRenderer );
			tableEvents.getColumnModel().getColumn(1).setCellRenderer( centerRenderer );
			tableEvents.getColumnModel().getColumn(2).setCellRenderer( centerRenderer );
			tableEvents.getColumnModel().getColumn(3).setCellRenderer( centerRenderer );

			tableModelQueries.setDataVector(null, columnNamesQueries);

			if(comp == null) {
				chosenCompetitionLabel.setText("No competition selected");
				chosenCompetitionLabel.setIcon(null);
			}
			else {
				chosenCompetitionLabel.setIcon(new ImageIcon("images/competitions/" + comp.getName() + ".png"));
				chosenCompetitionLabel.setText("");
			}
			DateFormat dateformat1 = DateFormat.getDateInstance(1, jCalendar1.getLocale());

			jLabelEvents.setText(ResourceBundle.getBundle("Etiquetas").getString("Events") + ": " +  dateformat1.format(jCalendar1.getCalendar().getTime()));
			paintDaysWithEvents(jCalendar1, dates);
		} catch (Exception e1) {
			jLabelQueries.setText(e1.getMessage());
			e1.printStackTrace();
		}
	}


	/**
	 * Generates the possible options for multiple betting taking into account the bets the user has included in the coupon so far.
	 */
	public void generateMultiBetOptions() {
		multibetPanel.removeAll();
		int multivalidcount = selectedevents.size();
		int[] combinations = computeMultiBets(selectedevents);
		if( betPane.getComponents().length > multivalidcount && multivalidcount > 1) {
			errorlabel.setText("Multi betting options have been restricted");
			errorlabel.setVisible(true);
		}
		else {
			errorlabel.setVisible(false);
		}

		if(multivalidcount > 1) {
			multibetButton.setEnabled(true);
			multibetPanel.setVisible(true);
			if(multivalidcount == 8) {
				multibetPanel.add(new multibetOption(BetType.GOLIATH, 247*combinations[6]));
			}
			if(multivalidcount == 7) {
				multibetPanel.add(new multibetOption(BetType.SUPER_HEINZ, 120*combinations[5]));
			}
			if(multivalidcount == 6) {
				multibetPanel.add(new multibetOption(BetType.HEINZ, 57*combinations[4]));
			}
			if(multivalidcount == 5) {
				multibetPanel.add(new multibetOption(BetType.SUPER_YANKEE, 26*combinations[3]));
			}
			if(multivalidcount == 4) {
				multibetPanel.add(new multibetOption(BetType.YANKEE, 11*combinations[2]));
			}
			if(multivalidcount == 3) {
				multibetPanel.add(new multibetOption(BetType.TRIXIE, 4*combinations[1]));
			}
			for(int i = multivalidcount; i>1 ; i--) {
				multibetPanel.add(new multibetOption(BetType.getType(i), combinations[i-2]));
			}
			multibetScrollPanel.setMinimumSize(new Dimension(bettingPanel.getWidth(), 50*multibetPanel.getComponents().length));
			multibetScrollPanel.setMaximumSize(new Dimension(bettingPanel.getWidth(), 50*multibetPanel.getComponents().length));

			multibetPanel.repaint();
			multibetPanel.revalidate();
			multibetScrollPanel.revalidate();
			bettingPanel.revalidate();
		}
		else {
			multibetScrollPanel.setVisible(false);
			multibetPanel.setVisible(false);
			multibetButton.setText(ResourceBundle.getBundle("Etiquetas").getString("ShowMultipleBetOptions"));
			multibetButton.setEnabled(false);
		}
	}


	/**
	 * Paints the days that have at least an event on them on the selected competition with a different color in the jcalendar.
	 * @param jCalendar		JCalendar to paint days on;
	 * @param eventsMonth	Dates of the days with events on the selected month.
	 */
	public static void paintDaysWithEvents(JCalendar jCalendar, Vector<Date> eventsMonth ) {
		// For each day in current month, it is checked if there are events, and in that
		// case, the background color for that day is changed.

		Calendar calendar = jCalendar.getCalendar();

		int month = calendar.get(Calendar.MONTH);		
		int selectedday =calendar.get(Calendar.DAY_OF_MONTH);

		calendar.set(Calendar.DAY_OF_MONTH, 1);
		int offset = calendar.get(Calendar.DAY_OF_WEEK);
		if (Locale.getDefault().equals(new Locale("es")))
			offset += 4;
		else
			offset += 5;   
		for (Component c:  jCalendar.getDayChooser().getDayPanel().getComponents()) {
			if(!(((JButton)c).hasFocus())) {
				c.setBackground(Color.WHITE);
			}

		}
		for (Date d:eventsMonth){

			calendar.setTime(d);
			Component o = (Component) jCalendar.getDayChooser().getDayPanel()
					.getComponent(calendar.get(Calendar.DAY_OF_MONTH) + offset);
			o.setBackground(Color.CYAN);
		}
		calendar.set(Calendar.DAY_OF_MONTH, selectedday); 
		calendar.set(Calendar.MONTH, month);
	}

	/**
	 * 
	 */
	private JButton getBtnBet() {
		if(btnBet == null) {
			btnBet = new FancyButton(ResourceBundle.getBundle("Etiquetas").getString("Bet"), new Color(200,200,200),new Color(169,169,169),new Color(130, 130, 130));
			btnBet.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
			btnBet.setFont(new Font("Source Sans Pro", Font.BOLD, 13));
			btnBet.setBorderPainted(true);
			btnBet.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					BLFacade facade = MainGUI.getBusinessLogic();
					if(!facade.isLoggedIn()) {
						String[] options = {"Log in","Register","Cancel"};
						int selectedoption = JOptionPane.showOptionDialog(null, "This action requires the user to be logged in", "Login required", JOptionPane.DEFAULT_OPTION,
								JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
						if(selectedoption == 0) {
							JDialog d = new LoginGUI();
							d.setVisible(true);
						}
						if(selectedoption == 1) {
							JDialog d = new RegisterGUI(false);
							d.setVisible(true);
						}
					}
					else {
						bet.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null));

					}//else				
				}//actionperformed
			});//addactionlistener
		}
		return btnBet;
	}					

	/**
	 * 
	 */
	AbstractAction bet = new AbstractAction() {
		@Override
		public void actionPerformed(ActionEvent e) {
			List<Prediction> pred =  new ArrayList<Prediction>();
			errorlabel.setVisible(false);
			//SINGLES
			try {
				for(Component c : betPane.getComponents()) {
					List<Prediction> temp =  new ArrayList<Prediction>();
					Prediction nextprediction = new Prediction(((BetPanel)c).getQuestion(),((BetPanel)c).getAnswer(), ((BetPanel)c).getOdds());
					temp.add(nextprediction);
					pred.add(nextprediction);
					if(((BetPanel)c).getStake().intValue() > 0) {								
						facade.placeBets(((BetPanel)c).getStake().floatValue(),((BetPanel)c).getStake().floatValue(), BetType.SINGLE, temp);
					}
				}
				//MULTIPLE
				for(Component c: multibetPanel.getComponents()) {
					if(((multibetOption)c).getStake() > 0) {						
						facade.placeBets(((multibetOption)c).getStake(),((multibetOption)c).getPrice(), ((multibetOption)c).getType(), pred);
					}
				}
				JOptionPane.showMessageDialog(null, "Bets placed sucesfully");
				MainGUI.getInstance().refreshCash();
			}
			catch(InsufficientCash i) {
				errorlabel.setText("Not enough money to bet with that amount");	
				errorlabel.setVisible(true);
			}
		}
	};

	/**
	 * 
	 */
	public int[] computeMultiBets(Map<Event, List<Prediction>> map) {
		int current = 1;
		int i;
		int combinationsum = 0;
		int[] result = new int[map.size()];
		List<Pair> comblist = new ArrayList<Pair>();
		Object[] keyset = map.keySet().toArray();
		for(i=1 ; i <= map.size(); i++) {
			comblist.add(new Pair(i, map.get(keyset[i-1]).size()));
		}
		while(current <= map.size()) {
			List<Pair> temp = new ArrayList<Pair>();
			combinationsum=0;
			for(Pair p: comblist) {	
				int last = p.getList().get(p.getList().size()-1);
				for(i=last+1; i<=map.size(); i++) {
					Pair nextpair = new Pair(p,i,map.get(keyset[i-1]).size());
					temp.add(nextpair);	
					combinationsum += nextpair.getCombinations();
				}	
			}
			comblist = new ArrayList<Pair>(temp);
			result[current-1]=combinationsum;

			current++;
		}
		return result;
	}

	/**
	 * Auxiliary class for computeMultiBets
	 */
	public class Pair{
		private ArrayList<Integer> list;
		private int combinations;

		public Pair( int i, int size) {
			list = new ArrayList<Integer>();
			list.add(i);
			combinations = size;
		}

		public Pair(Pair p, int i, int size) {
			list = new ArrayList<Integer>(p.getList());
			list.add(i);
			combinations = p.getCombinations()*size;
		}

		public ArrayList<Integer> getList(){
			return list;
		}

		public int getCombinations() {
			return combinations;
		}

		public String toString() {
			String s = "";
			for(Integer i : list) {
				s = s + String.valueOf(i);
			}
			return s;
		}
	}


	public class multiBetListener implements DocumentListener {
		@Override
		public void removeUpdate(DocumentEvent e) {
			if(globalStakeTextField.getText().equals("")) {
				setStakes(new BigDecimal(0));
				calcTotalBetValues();	
			}
			else {
				setStakes(new BigDecimal(globalStakeTextField.getText()));
				calcTotalBetValues();		
			}	
		}

		@Override
		public void insertUpdate(DocumentEvent e) {
			setStakes(new BigDecimal(globalStakeTextField.getText()));
			calcTotalBetValues();			
		}

		@Override
		public void changedUpdate(DocumentEvent e) {
			if(globalStakeTextField.getText().equals("")) {
				setStakes(new BigDecimal(0.0));
				calcTotalBetValues();	
			}
			else {
				setStakes(new BigDecimal(globalStakeTextField.getText()));
				calcTotalBetValues();		
			}	
		}
	}



	/**
	 * DocumentListener to set the values of all single bets to the value a user 
	 * enters in the "Stake per bet" field.
	 */
	public class  allStakeListener implements DocumentListener {
		@Override
		public void removeUpdate(DocumentEvent e) {
			if(globalStakeTextField.getText().equals("")) {
				setStakes(new BigDecimal(0));
				calcTotalBetValues();	
			}
			else {
				setStakes(new BigDecimal(globalStakeTextField.getText()));
				calcTotalBetValues();		
			}	
		}

		@Override
		public void insertUpdate(DocumentEvent e) {
			setStakes(new BigDecimal(globalStakeTextField.getText()));
			calcTotalBetValues();			
		}

		@Override
		public void changedUpdate(DocumentEvent e) {
			if(globalStakeTextField.getText().equals("")) {
				setStakes(new BigDecimal(0.0));
				calcTotalBetValues();	
			}
			else {
				setStakes(new BigDecimal(globalStakeTextField.getText()));
				calcTotalBetValues();		
			}	
		}
	}


	/**
	 *	Buttons on the upper panel, each for a sport, the country and competitions displayed in the competition panel are
	 *	altered depending on the selected sport(button)
	 */
	private class SportButton extends JButton{

		private ImageIcon active;
		private ImageIcon inactive;
		private Sport sport;

		public SportButton(ImageIcon active, ImageIcon inactive ,  Sport sport) {


			this.active = active;
			this.inactive = inactive;
			this.setText(sport.getString());
			this.sport = sport;

			setIcon(inactive);
			setFont(new Font("Source Sans Pro Light", Font.PLAIN, 16));
			setText(sport.getString());
			setOpaque(false);
			setVerticalTextPosition(SwingConstants.BOTTOM);
			setHorizontalTextPosition(SwingConstants.CENTER);	
			setContentAreaFilled(false);
			setBorderPainted(false);
			setFocusPainted(false);
			setForeground(new Color(195,195,195));
			addActionListener(selectSport);

		}

		@Override
		protected void paintComponent(Graphics g) {
			if (isSelected()) {
				setForeground(new Color(255,202,24));
				this.setSelectedIcon(active);  
			} else if (getModel().isPressed()) {
				setForeground(new Color(195,195,195));
				this.setPressedIcon(inactive);  
			} else if (getModel().isRollover()) {
				this.setRolloverIcon(active);
				setForeground(new Color(255,202,24));

			} else {
				setForeground(new Color(195,195,195));
			}
			super.paintComponent(g);

		}

		Action selectSport = new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SportButton button = (SportButton) e.getSource();
				for(SportButton spb : sportbuttons) {
					spb.setSelected(false);
				}
				button.setSelected(true);

				competitionScrollPane.remove(competitionPanel);
				Vector<Competition> competitions = facade.getCompetitions(button.sport);
				competitionPanel =  new  CompetitionPanel(competitions);
				competitionPanel.addPropertyChangeListener(new CompetitionChangeEvent());
				competitionPanel.setSelectedCompetition(null);
				competitionScrollPane.add(competitionPanel);
				competitionScrollPane.setViewportView(competitionPanel);
				competitionScrollPane.updateUI();
			}
		};
	}

	/**
	 * JPanel used for holding bet information, with the event, question, selected answer, odds, betting amount and the potential gainings.
	 * The panels are created with a fixed size.
	 * 
	 * @param ev		The event the bet has been placed on
	 * @param q			The question the answer for the bet has been chosen
	 * @param answer	The answer picked by the user
	 * @param amount	Amount of money to bet
	 * @return			JPanel with components incorporated that hold the bet information. 
	 */
	public class BetPanel extends JPanel{

		private Event event;
		private Question question;
		private String answer;
		private float odds;
		private BigDecimal stake;
		private BigDecimal minbet;

		private JLabel winningsLabel;
		private JLabel poswinLabel;
		private JNumericField stakeField;
		private DocumentListener stakeListener;
		private JButton closeButton;

		public BetPanel(Event ev, Question q, String answer, BigDecimal minbet, BigDecimal stake){ 	

			setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.black));

			this.event = ev;
			this.question = q;
			this.stake = stake;
			this.minbet = minbet;

			String[] ans = answer.split(";");
			this.answer = ans[0];
			this.odds = Float.parseFloat(ans[1]);

			setBackground(Color.WHITE);
			setLayout(new MigLayout("", "[0.00][:47.00px:73.00px][:65.00:73][51.00:45:45][11.00][38.00][49.00][:20:20][167.00:n:2]", "[:27.00:27.00][:18.00:25px][-2.00][30:7.00:30,fill][:19.00:19.00]"));

			JLabel eventLabel = new JLabel(ev.getDescription());
			eventLabel.setFont(new Font("Source Sans Pro", Font.PLAIN, 15));
			add(eventLabel, "cell 1 0 3 1,alignx left,aligny center");

			JLabel lblOdds = new JLabel("Odds:");
			lblOdds.setFont(new Font("Source Sans Pro", Font.BOLD, 13));
			lblOdds.setHorizontalAlignment(SwingConstants.TRAILING);
			add(lblOdds, "cell 5 0,alignx left,aligny center");

			JLabel oddsLabel = new JLabel(ans[1]);
			oddsLabel.setBackground(Color.WHITE);
			oddsLabel.setFont(new Font("Source Sans Pro", Font.BOLD, 18));
			oddsLabel.setHorizontalAlignment(SwingConstants.LEFT);
			add(oddsLabel, "cell 6 0,alignx left,aligny center");

			closeButton = new JButton((new ImageIcon("images/closex.png")));
			closeButton.setMargin(new Insets(0,0,0,0));
			closeButton.setBorderPainted(false);
			closeButton.setFocusPainted(false);
			closeButton.setBackground(Color.WHITE);
			closeButton.setContentAreaFilled(false);
			closeButton.setHorizontalAlignment(SwingConstants.LEADING);
			closeButton.addActionListener(new ActionListener() {	
				@Override
				public void actionPerformed(ActionEvent e) {
					BetPanel panel = (BetPanel)closeButton.getParent();
					betPane.remove(closeButton.getParent());
					generateMultiBetOptions();
					if(addToCouponButton.isEnabled() == false) {
						addToCouponButton.setEnabled(true);
						addToCouponButton.setText("Add bet to coupon");
					}

					//remove the prediction
					selectedpredictions.remove(question.getPredictionByAnswer(panel.getAnswer()));
					selectedevents.get(panel.getEvent()).remove(question.getPredictionByAnswer(panel.getAnswer()));

					//check if there is other prediction on the coupon for the same question before removing it
					Question question = panel.getQuestion();
					boolean removequestion = true;
					for(Prediction p: selectedpredictions) {
						if(p.getQuestion().equals(question)) {
							removequestion = false;
						}
					}
					if(removequestion) {
						selectedquestions.remove(question);
					}

					//check if there is other prediction on the coupon for the same event before removing it
					Event ev = panel.getEvent();
					boolean removeevent = true;
					for(Question q : selectedquestions) {
						if(q.getEvent().equals(ev)) {
							removeevent = false;
						}
					}
					if(removeevent) {
						for (Event t : selectedevents.keySet()) {
							System.out.println(t.getDescription());
						}
						selectedevents.remove(ev);

					}
					refreshAnswerSelection();

					tableEvents.repaint();
					tableQueries.repaint();

					betPane.revalidate();
					betPane.repaint();
					calcTotalBetValues();
					generateMultiBetOptions();
					if(betPane.getComponents().length == 0) {
						btnBet.setEnabled(false);
					}
				}
			});
			add(closeButton, "cell 7 0 2 1");

			JLabel quetionLabel = new JLabel("<html>" + q.getQuestion() + "</html>");
			quetionLabel.setFont(new Font("Source Sans Pro", Font.PLAIN, 15));
			add(quetionLabel, "cell 1 1 3 2,alignx left,aligny bottom");

			stakeField = new JNumericField(7, JNumericField.DECIMAL);
			stakeField.setPrecision(2);
			stakeField.setAllowNegative(false);
			add(stakeField, "cell 5 1 3 2,growx,aligny bottom");
			stakeListener = new singleStakeListener();
			stakeField.getDocument().addDocumentListener(stakeListener);

			JLabel answerLabel = new JLabel(ans[0]);
			answerLabel.setFont(new Font("Source Sans Pro", Font.BOLD, 15));
			add(answerLabel, "cell 1 3 2 1,alignx left,aligny bottom");

			poswinLabel = new JLabel("possible winnings: \r\n\r\n");
			poswinLabel.setHorizontalAlignment(SwingConstants.TRAILING);
			poswinLabel.setFont(new Font("Source Sans Pro", Font.PLAIN, 12));
			add(poswinLabel, "flowx,cell 5 3 3 1,alignx left,aligny bottom");

			JLabel minBetTitleLabel = new JLabel("Min.Bet:\r\n\r\n");
			minBetTitleLabel.setFont(new Font("Source Sans Pro", Font.PLAIN, 15));
			add(minBetTitleLabel, "cell 1 4,grow");

			JLabel minBetLabel = new JLabel(minbet.toString()+"€");
			minBetLabel.setFont(new Font("Source Sans Pro", Font.BOLD, 16));
			add(minBetLabel, "cell 2 4,alignx left");

			winningsLabel = new JLabel("");
			winningsLabel.setVisible(true);
			winningsLabel.setFont(new Font("Source Sans Pro", Font.BOLD, 16));
			add(winningsLabel, "cell 5 4 3 1,alignx left,aligny top");
			setStakes(minbet);
		}

		@Override
		protected void paintComponent(Graphics g) {
			setBackground(Color.white);
			super.paintComponent(g);
		}


		@Override
		public Dimension getPreferredSize()
		{
			Dimension d = null;
			int l = getQuestion().getQuestion().length();
			if(l>25) {
				d = new Dimension(getParent().getWidth(),150);
			}
			else {
				d = new Dimension(getParent().getWidth(),130);
			}	
			return d;
		}


		@Override
		public Dimension getMaximumSize()
		{
			Dimension d = null;
			int l = getQuestion().getQuestion().length();
			if(l>25) {
				d = new Dimension(getParent().getWidth(),150);
			}
			else {
				d = new Dimension(getParent().getWidth(),130);
			}	
			return d;
		}


		public BigDecimal getStake() {
			return stake;
		}

		public void setStake(BigDecimal f) {
			stake = f;
			setWinnings(f);
		}

		public String getAnswer() {
			return answer;
		}

		public Event getEvent() {
			return this.event;
		}

		public Question getQuestion() {
			return this.question;
		}

		public BigDecimal getMinBet() {
			return minbet;
		}

		public float getOdds() {
			return odds;
		}

		public void setStakes(BigDecimal stakes) {
			stakeField.setText(stakes.toString());
			setStake(stakes);
			setWinnings(stakes);
		}

		public void showPossibleWinnings() {
			winningsLabel.setVisible(true);
			poswinLabel.setVisible(true);
		}

		public void hidePossibleWinnings() {
			winningsLabel.setVisible(false);
			poswinLabel.setVisible(false);
		}

		public void setWinnings(BigDecimal stakes) {
			DecimalFormat df = new DecimalFormat("#");
			df.setMaximumFractionDigits(2);
			winningsLabel.setText(df.format( stakes.doubleValue() * odds) + "€");
		}

		public void enableStakeListener() {
			stakeField.getDocument().addDocumentListener(stakeListener);
		}

		public void disableStakeListener() {
			stakeField.getDocument().removeDocumentListener(stakeListener);
		}

		public class singleStakeListener implements DocumentListener{	
			@Override
			public void removeUpdate(DocumentEvent e) {
				globalStakeTextField.getDocument().removeDocumentListener(allStakeListener);
				globalStakeTextField.setText("");
				try {
					BigDecimal bd = new BigDecimal(stakeField.getText());
					setStake(bd);
					setWinnings(bd);
				}
				catch(NumberFormatException n) {
					setStake(new BigDecimal(0));
					setWinnings(new BigDecimal(0));
				}
				calcTotalBetValues();	
				globalStakeTextField.getDocument().addDocumentListener(allStakeListener);
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				globalStakeTextField.getDocument().removeDocumentListener(allStakeListener);
				globalStakeTextField.setText("");
				try {
					BigDecimal bd = new BigDecimal(stakeField.getText());
					setStake(bd);
					setWinnings(bd);
				}
				catch(NumberFormatException n) {
					setStake(new BigDecimal(0));
					setWinnings(new BigDecimal(0));
				}
				calcTotalBetValues();
				globalStakeTextField.getDocument().addDocumentListener(allStakeListener);
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				globalStakeTextField.getDocument().removeDocumentListener(allStakeListener);
				globalStakeTextField.setText("");
				try {
					BigDecimal bd = new BigDecimal(stakeField.getText());
					setStake(bd);
					setWinnings(bd);
				}
				catch(NumberFormatException n) {
					setStake(new BigDecimal(0));
					setWinnings(new BigDecimal(0));
				}
				calcTotalBetValues();
				globalStakeTextField.getDocument().addDocumentListener(allStakeListener);
			}
		}
	}

	/**
	 * Panel that holds information about a combined bet
	 */
	public class multibetOption extends JPanel {
		
		private float stake;
		private float price;
		private int combinations;
		private BetType type;	
		private  float winnings;
		private boolean fullcover;
		
		private JNumericField stakeField;
		private JLabel priceLabel;
		private JLabel posWinLabel;
		private DecimalFormat df;
		
		/**
		 * Create the panel.
		 */
		public multibetOption( BetType type, int combinations) {
			this.type = type;
			if(type.getBetCount()>1) {
				fullcover = true;
			}
			this.price = 0;
			this.combinations = combinations;
			setBackground(new Color(240, 255, 255));
			setLayout(new MigLayout("", "[33.00px][108.00][50px][91.00px]", "[19.00px][25px:8.00px:25px]"));
			JLabel typeLabel = new JLabel(type.name());
			typeLabel.setFont(new Font("Source Sans Pro", Font.PLAIN, 14));
			add(typeLabel, "cell 0 0 2 1,grow");
			
			stakeField = new JNumericField(7, JNumericField.DECIMAL);
			stakeField.setPrecision(2);
			stakeField.setAllowNegative(false);
			add(stakeField, "cell 3 0,grow");
			stakeField.setColumns(10);
			setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.black));
			stakeField.getDocument().addDocumentListener(new stakeListener());
			
			JLabel combinationsLabel = new JLabel("x" + combinations);
			combinationsLabel.setFont(new Font("Source Sans Pro", Font.PLAIN, 15));
			combinationsLabel.setHorizontalAlignment(SwingConstants.TRAILING);
			add(combinationsLabel, "cell 2 0,grow");
			
			JLabel priceTitleLabel = new JLabel("price:");
			priceTitleLabel.setVerticalAlignment(SwingConstants.TOP);
			priceTitleLabel.setFont(new Font("Source Sans Pro", Font.PLAIN, 12));
			add(priceTitleLabel, "cell 0 1,aligny top");
			
			priceLabel = new JLabel("0€");
			priceLabel.setFont(new Font("Source Sans Pro", Font.BOLD, 13));
			add(priceLabel, "cell 1 1,alignx left,aligny top");
			
			JLabel posWinTitleLabel = new JLabel("pos. winnings:\r\n");
			posWinTitleLabel.setFont(new Font("Source Sans Pro", Font.PLAIN, 12));
			add(posWinTitleLabel, "cell 2 1,aligny top");
			
			posWinLabel = new JLabel("0€");
			posWinLabel.setFont(new Font("Source Sans Pro", Font.BOLD, 13));
			add(posWinLabel, "cell 3 1,alignx right,aligny top");
			
			df = new DecimalFormat("#");
			df.setMaximumFractionDigits(2);

		}

		public float getStake() {
			return stake;
		}


		public void setStake(float stake) {
			this.stake = stake;
		}

		public float getPrice() {
			return price;
		}
		
		public JTextField getBetField() {
			return stakeField;
		}


		public void setBetField(JNumericField betField) {
			this.stakeField = betField;
		}


		public int getCombinations() {
			return combinations;
		}


		public void setCombinations(int combinations) {
			this.combinations = combinations;
		}
		
		public float getWinnings() {
			return winnings;
		}
		
		public void setWinnings(float winnings) {
			this.winnings = winnings;
		}
		
		public BetType getType() {
			return type;
		}


		public void setType(BetType type) {
			this.type = type;
		}
		
		@Override
		public Dimension getPreferredSize()
		{		
			Dimension d = new Dimension(getParent().getWidth(), 50); 
			return d;
		}
		
		@Override
		public Dimension getMaximumSize()
		{		
			Dimension d = new Dimension(getParent().getWidth(), 50);
			return d;
		}
		
		public class stakeListener implements DocumentListener{

			@Override
			public void insertUpdate(DocumentEvent e) {
				try {
					List<Prediction> temp = new ArrayList<Prediction>(selectedpredictions); 
					stake = stakeField.getFloat();
					if(fullcover) {
						winnings = facade.calculateFullCoverWinnings(type.predictionCount(),stake,temp);
					}
					else {
						winnings = facade.calculateCombinedWinnings(type.predictionCount(),stake,temp);
					}
					
					price = stake*combinations;
					posWinLabel.setText(df.format(winnings)+"€");
					priceLabel.setText(df.format(price)+"€");
					
				}
				catch (NumberFormatException n) {
					stake = 0;
					winnings = 0;
					price = 0;
					posWinLabel.setText("0€");
					priceLabel.setText(df.format(stake*combinations)+"€");
				}
				calcTotalBetValues();
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				try {
					List<Prediction> temp = new ArrayList<Prediction>(selectedpredictions); 
					stake = stakeField.getFloat();
					if(fullcover) {
						winnings = facade.calculateFullCoverWinnings(type.predictionCount(),stake,temp);
					}
					else {
						winnings = facade.calculateCombinedWinnings(type.predictionCount(),stake,temp);
					}
					price = stake*combinations;
					posWinLabel.setText(df.format(winnings)+"€");
					priceLabel.setText(df.format(price)+"€");
				}
				catch (NumberFormatException n) {
					stake = 0;
					winnings = 0;
					price = 0;
					posWinLabel.setText("0€");
					priceLabel.setText(df.format(stake*combinations)+"€");
				}
				calcTotalBetValues();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				try {
					List<Prediction> temp = new ArrayList<Prediction>(selectedpredictions); 
					stake = stakeField.getFloat();
					if(fullcover) {
						winnings = facade.calculateFullCoverWinnings(type.predictionCount(),stake,temp);
					}
					else {
						winnings = facade.calculateCombinedWinnings(type.predictionCount(),stake,temp);
					}
					price = stake*combinations;
					posWinLabel.setText(df.format(winnings)+"€");
					priceLabel.setText(df.format(price)+"€");
				}
				catch (NumberFormatException n) {
					stake = 0;
					winnings = 0;
					price = 0;
					posWinLabel.setText("0€");
					priceLabel.setText(df.format(stake*combinations)+"€");
				}
				calcTotalBetValues();
			}
			
			
		}
	}
}
