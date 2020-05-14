package gui.Panels.subpanels;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Vector;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableRowSorter;
import businessLogic.BLFacade;
import domain.Bet;
import domain.BetContainer;
import domain.BetType;
import domain.Event;
import domain.Prediction;
import domain.PredictionContainer;
import domain.Question;
import domain.User;
import exceptions.InsufficientCash;
import gui.MainGUI;
import gui.components.ButtonColumn;
import gui.components.FancyButton;
import gui.components.JNumericField;
import gui.components.JTableX;
import gui.components.CellEditorModel;
import net.miginfocom.swing.MigLayout;
import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.UIManager;


@SuppressWarnings("serial")
public class OpenBetsPanel extends JPanel {

	private boolean editmode = false;

	private BetContainer selectedbet;
	private BetType selectedBetType;
	private float poswinnings;

	private JNumericField stakeField;
	private JComboBox<BetType> betTypeComboBox;
	private DefaultComboBoxModel<BetType> betTypeComboBoxModel;

	private JLabel predictionsLabel;
	private JLabel betsLabel;
	private JLabel poswinTitleLabel;
	private JLabel poswinLabel;
	private JLabel settledTitleLabel;
	private JLabel settledLabel;
	private JLabel stakeLabel;
	private JLabel betTypeLabel;
	private JLabel minBetTitleLabel;
	private JLabel minBetLabel;
	private JLabel editModeLabel;

	private JTable betTable;
	private DefaultTableModel betTableModel;
	private DefaultTableCellRenderer selectedBetRenderer;
	private DefaultTableCellRenderer wonPredictionRendered;
	private DefaultTableCellRenderer lostPredictionRendered;
	private DefaultTableCellRenderer nonEditableBetRenderer;
	private DefaultTableCellRenderer whiteRenderer;
	private DefaultTableCellRenderer redRenderer;

	private FancyButton cancelBetButton;
	private FancyButton editBetButton;
	private FancyButton saveChangesButton;
	private FancyButton addPredictionsButton;
	private FancyButton cancelButton;

	private List<BetContainer> bets;
	private List<PredictionContainer> predictions;
	private Map<Event,List<PredictionContainer>> predictionmap  = new HashMap<Event, List<PredictionContainer>>();

	private String[] columnNamesOpen = new String[] {
			ResourceBundle.getBundle("Etiquetas").getString("BetType"), 
			ResourceBundle.getBundle("Etiquetas").getString("Stake"), 
			ResourceBundle.getBundle("Etiquetas").getString("PlacedOn"), 
			ResourceBundle.getBundle("Etiquetas").getString("ResolvesOn"), 
			ResourceBundle.getBundle("Etiquetas").getString("Status"), 
			""		
	};

	private String[] columnNamesPrediction = new String[] {
			""	,
			ResourceBundle.getBundle("Etiquetas").getString("Event"), 
			ResourceBundle.getBundle("Etiquetas").getString("Question"), 
			ResourceBundle.getBundle("Etiquetas").getString("Answer"), 
			ResourceBundle.getBundle("Etiquetas").getString("Odds"),
			ResourceBundle.getBundle("Etiquetas").getString("ResolvesOn"), 
			ResourceBundle.getBundle("Etiquetas").getString("Outcome"), 
			""		
	};


	BLFacade facade = MainGUI.getBusinessLogic();
	private JScrollPane predictionsScrollPane;
	private JTableX predictionTable;
	private NonEditableTableModel predictionTableModel;

	//private JSpinner bettypeSpinner;

	/**
	 * Create the panel.
	 */
	public OpenBetsPanel() {

		setBackground(new Color(153, 204, 153));

		setLayout(new MigLayout("gap 0px 0px", "[20:20:20][68.00][74.00][274.00,grow][20:20:20][60][20:20,grow][44:44.00][10:10:10][120,grow][20:20:20][-135.00]", "[20:14.00:20][:40.00:40.00][25:25][40:40:40][][40:40:40][40:40:40][10:10:10][40:40:40][10:10:10][30:30:30][30:30:30,grow][30:30:30][10:10:10][19.00:19.00,grow][90:100,grow][40:35.00:40][40:40:40][25:25:25][25:25:25][20:20:20]"));

		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane, "cell 1 2 3 11,grow");

		betTable = new JTable() {		
			public TableCellRenderer getCellRenderer(int row, int column)
			{
				if (whiteRenderer == null)
				{
					whiteRenderer = new DefaultTableCellRenderer();
					whiteRenderer.setHorizontalAlignment(SwingConstants.CENTER);
				}
				if (selectedBetRenderer == null)
				{
					selectedBetRenderer = new DefaultTableCellRenderer();
					selectedBetRenderer.setBackground(UIManager.getColor("InternalFrame.activeTitleGradient"));
					selectedBetRenderer.setHorizontalAlignment(SwingConstants.CENTER);
				}
				if (nonEditableBetRenderer == null)
				{
					nonEditableBetRenderer = new DefaultTableCellRenderer();
					nonEditableBetRenderer.setBackground(new Color(245,175,129));
					nonEditableBetRenderer.setHorizontalAlignment(SwingConstants.CENTER);
				}


				if(editmode && betTableModel.getValueAt(row, 5).equals(selectedbet)) {
					return selectedBetRenderer;
				}
				else if(((BetContainer)betTableModel.getValueAt(row, 5)).getBet().getStartingDate().compareTo(new Date()) < 0){
					return nonEditableBetRenderer;
				}
				else {
					return whiteRenderer;
				}
			}	
		};
		betTable.setFocusable(false);
		betTableModel = new DefaultTableModel(null, columnNamesOpen) {
			public boolean isCellEditable (int row, int column)
			{
				return false;
			}
		};
		betTable.setModel(betTableModel);


		betTable.getTableHeader().setReorderingAllowed(false);
		betTable.getTableHeader().setResizingAllowed(false);
		betTable.getTableHeader().setFont(new Font("Source sans Pro", Font.BOLD, 16));
		betTable.getTableHeader().setBackground(new Color(51,51,51));
		betTable.getTableHeader().setForeground(Color.white);
		betTable.setAutoCreateRowSorter(true);
		TableCellRenderer baseRenderer = betTable.getTableHeader().getDefaultRenderer();
		betTable.getTableHeader().setDefaultRenderer(new TableHeaderRenderer(baseRenderer));
		betTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting()) {//This line prevents double events
					int row = betTable.getSelectedRow();
					if(row != -1 && !editmode) {		
						if(((BetContainer)betTableModel.getValueAt(betTable.getSelectedRow(), 5)).getBet().getStartingDate().compareTo(new Date()) > 0) {
							editBetButton.setEnabled(true);
							addPredictionsButton.setEnabled(true);
							cancelBetButton.setEnabled(true);
						}
						else {
							editBetButton.setEnabled(false);
							addPredictionsButton.setEnabled(false);
							cancelBetButton.setEnabled(false);
						}
						selectedbet = (BetContainer)betTableModel.getValueAt(row, 5);

						predictions = selectedbet.getPredictions();
						selectedBetType = selectedbet.getBet().getType();
						computePredictionMap(predictions);
						loadPredictions(predictions);
					}
				}
			}
		});
		scrollPane.setViewportView(betTable);

		betsLabel = new JLabel("Bets:");
		betsLabel.setFont(new Font("Source Code Pro", Font.BOLD, 16));
		add(betsLabel, "cell 1 1 3 1");

		editModeLabel = new JLabel("Edit mode enabled");
		editModeLabel.setForeground(Color.RED);
		editModeLabel.setFont(new Font("Source Code Pro", Font.BOLD, 14));
		add(editModeLabel, "cell 5 2 5 1,alignx center");

		editBetButton = new FancyButton("Edit bet",new Color(51,51,51),new Color(170,170,170),new Color(150,150,150));
		editBetButton.setForeground(Color.WHITE);
		editBetButton.setFont(new Font("Source Sans Pro", Font.BOLD, 12));
		add(editBetButton, "cell 5 3 5 1,grow");
		editBetButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				enableEditMode();
			}	
		});
		editBetButton.setEnabled(true);

		addPredictionsButton = new FancyButton("Add predictions", new Color(51, 51, 51), new Color(170, 170, 170), new Color(150, 150, 150));
		addPredictionsButton.setForeground(Color.WHITE);
		addPredictionsButton.setFont(new Font("Source Sans Pro", Font.BOLD, 12));
		add(addPredictionsButton, "cell 5 5 5 1,grow");

		cancelBetButton = new FancyButton("Cancel bet",new Color(51,51,51),new Color(170,170,170),new Color(150,150,150));
		cancelBetButton.setForeground(Color.WHITE);
		cancelBetButton.setFont(new Font("Source Sans Pro", Font.BOLD, 12));
		add(cancelBetButton, "cell 5 6 5 1,grow");
		cancelBetButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int confirm = JOptionPane.showConfirmDialog(null, "Cancel selected bet?", "Cancel bet", JOptionPane.YES_NO_OPTION);
				if(confirm == 0) {
					int row = betTable.getSelectedRow();
					if(row != -1) {
						BetContainer b = ((BetContainer)betTableModel.getValueAt(row, 5));
						facade.cancelBet(b);
						User loggeduser = MainGUI.getInstance().getLoggeduser();
						loggeduser.setCash(loggeduser.getCash()+b.getBet().getStake());
						MainGUI.getInstance().refreshCash();
						refreshPage();
					}
				}
			}
		});

		saveChangesButton = new FancyButton("Save changes", new Color(51, 51, 51), new Color(170, 170, 170), new Color(150, 150, 150));
		saveChangesButton.setForeground(Color.WHITE);
		saveChangesButton.setEnabled(false);
		saveChangesButton.setFont(new Font("Source Sans Pro", Font.BOLD, 12));
		add(saveChangesButton, "cell 5 8 4 1,grow");
		saveChangesButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(predictions.size() == 0) {
					int confirm = JOptionPane.showConfirmDialog(null, "Saving an empty bet will result in cancelling the bet, continue?", "Empty bet warning", JOptionPane.YES_NO_OPTION);
					if(confirm==0) {
						facade.cancelBet(selectedbet);
						MainGUI.getInstance().refreshCash();
						disableEditMode();
						refreshPage();
					}
				}
				else if(predictions.size() > 1 && poswinnings==0) {
					JOptionPane.showMessageDialog(null, "Cannot place a bet with fully restricted predictions!","Invalid bet warning", JOptionPane.WARNING_MESSAGE);
				}
				else {
					try {
						String minbet = minBetLabel.getText().substring(0, minBetLabel.getText().length()-1);
						if(stakeField.getText().equals("")) {
							JOptionPane.showMessageDialog(null, "Introduce stake");
						}
						else if( stakeField.getFloat() >= Float.parseFloat(minbet)) {
							predictions = updatePredictions();
							float stake = stakeField.getFloat();
							Bet bet = facade.editBet(selectedbet, selectedBetType, stake, predictions);

							User loggeduser = MainGUI.getInstance().getLoggeduser();
							boolean found = false;
							int k = 0;

							for(int i = 0; i<loggeduser.getBets().size();i++ ) {
								Bet b = loggeduser.getBets().get(i);
								if(b.getBetNumber() == bet.getBetNumber()) {
									bet = b;
									found = true;
									k = i;
									break;
								}
							}
							if(found) {
								loggeduser.getBets().set(k, bet);
								loggeduser.setCash(loggeduser.getCash()+(bet.getStake()-stake));
							}


							JOptionPane.showMessageDialog(null, "Bet updated sucessfully");
							MainGUI.getInstance().refreshCash();
							disableEditMode();
							refreshPage();
						}
						else {
							JOptionPane.showMessageDialog(null, "Stake must be be at least the minimum bet amount");
						}
					}
					catch(InsufficientCash i) {
						JOptionPane.showMessageDialog(null, i.getMessage());
					}
				}
			}
		});

		cancelButton = new FancyButton("Cancel", new Color(51, 51, 51), new Color(170, 170, 170), new Color(150, 150, 150));
		cancelButton.setForeground(Color.WHITE);
		cancelButton.setEnabled(false);
		cancelButton.setFont(new Font("Source Sans Pro", Font.BOLD, 12));
		add(cancelButton, "cell 9 8,grow");
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				disableEditMode();
			}
		});

		stakeField = new JNumericField(7, JNumericField.DECIMAL);
		stakeField.setPrecision(2);
		stakeField.setAllowNegative(false);
		add(stakeField, "cell 5 11 2 1,growx");
		stakeField.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void removeUpdate(DocumentEvent e) {
				computePossibleWInnings();
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				computePossibleWInnings();	
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				computePossibleWInnings();	
			}
		});

		stakeLabel = new JLabel("Introduce stake:");
		stakeLabel.setFont(new Font("Source Code Pro", Font.BOLD, 14));
		add(stakeLabel, "cell 5 10 3 1,alignx left,growy");

		betTypeLabel = new JLabel("Bet type:");
		betTypeLabel.setFont(new Font("Source Code Pro", Font.BOLD, 14));
		add(betTypeLabel, "cell 9 10,alignx left,growy");


		betTypeComboBox = new JComboBox<BetType>();
		betTypeComboBox.setBackground(Color.WHITE);
		betTypeComboBoxModel = new DefaultComboBoxModel<BetType>();
		betTypeComboBox.setModel(betTypeComboBoxModel);
		add(betTypeComboBox, "cell 9 11,growx");
		betTypeComboBox.addActionListener(betTypeComboBoxAction);

		minBetTitleLabel = new JLabel("Minimum bet: ");
		minBetTitleLabel.setFont(new Font("Source Code Pro", Font.BOLD, 13));
		add(minBetTitleLabel, "cell 5 12,aligny top");

		minBetLabel = new JLabel("");
		minBetLabel.setHorizontalAlignment(SwingConstants.LEFT);
		minBetLabel.setFont(new Font("Source Code Pro", Font.BOLD, 12));
		add(minBetLabel, "cell 6 12,alignx left,aligny top");


		predictionsLabel = new JLabel("Predictions on this bet:\r\n");
		predictionsLabel.setFont(new Font("Source Code Pro", Font.BOLD, 16));
		add(predictionsLabel, "flowx,cell 1 14 5 1");

		predictionsScrollPane = new JScrollPane();
		add(predictionsScrollPane, "cell 1 15 9 3,grow");


		predictionTable = new JTableX() {
			public TableCellRenderer getCellRenderer(int row, int column)
			{
				if (whiteRenderer == null)
				{
					whiteRenderer = new DefaultTableCellRenderer();
					whiteRenderer.setHorizontalAlignment(SwingConstants.CENTER);
				}
				if (redRenderer == null){
					redRenderer = new DefaultTableCellRenderer();
					redRenderer.setBackground(new Color(245,102,102));
					redRenderer.setHorizontalAlignment(SwingConstants.CENTER);
				}
				if (wonPredictionRendered == null)
				{
					wonPredictionRendered = new DefaultTableCellRenderer();
					wonPredictionRendered.setBackground(new Color(212,247,166));
					wonPredictionRendered.setHorizontalAlignment(SwingConstants.CENTER);
				}
				if (lostPredictionRendered == null)
				{
					lostPredictionRendered = new DefaultTableCellRenderer();
					lostPredictionRendered.setBackground(new Color(245,135,114));
					lostPredictionRendered.setHorizontalAlignment(SwingConstants.CENTER);
				}

				int lastcol = predictionTableModel.getColumnCount()-1;
				PredictionContainer pc =(PredictionContainer)predictionTableModel.getValueAt(row, lastcol);

				if(editmode && column == 3) {
					List<Prediction> predictions = pc.getQuestion().getPredictions();
					String[] answers = new String[predictions.size()];
					for(int i = 0;i<predictions.size(); i++) {
						answers[i] = predictions.get(i).getAnswer() + ";" + predictions.get(i).getOdds();
					}
					return new MyComboBoxRenderer(answers);
				}
				else if(editmode && column == 7) {
					return  new ButtonColumn(predictionTable, delete, column, new Color(255,0,51));
				}
				else if(column == 0) {
					if(predictionmap.get(pc.getEvent()).size() > 1) {
						return redRenderer;
					}
					else {
						return whiteRenderer;
					}
				}
				else if (pc.getPrediction().getOutcome() == null)
					return whiteRenderer;
				else if(pc.getPrediction().getOutcome() == true){
					return wonPredictionRendered;
				}
				else {
					return lostPredictionRendered;
				}
			}

		};
		predictionTable.setFocusable(false);
		predictionTableModel = new NonEditableTableModel(null, columnNamesPrediction);
		predictionTable.setModel(predictionTableModel);

		predictionTable.getTableHeader().setReorderingAllowed(false);
		predictionTable.getTableHeader().setResizingAllowed(false);
		predictionTable.getTableHeader().setFont(new Font("Source sans Pro", Font.BOLD, 16));
		predictionTable.getTableHeader().setBackground(new Color(51,51,51));
		predictionTable.getTableHeader().setForeground(Color.white);
		predictionTable.setAutoCreateRowSorter(true);
		TableCellRenderer baseRenderer2 = predictionTable.getTableHeader().getDefaultRenderer();
		predictionTable.getTableHeader().setDefaultRenderer(new TableHeaderRenderer(baseRenderer2));
		predictionsScrollPane.setViewportView(predictionTable);

		settledTitleLabel = new JLabel("  Settled:");
		settledTitleLabel.setOpaque(true);
		settledTitleLabel.setBorder(null);
		settledTitleLabel.setForeground(Color.WHITE);
		settledTitleLabel.setBackground(new Color(51,51,51));
		settledTitleLabel.setFont(new Font("Source sans Pro", Font.BOLD, 16));
		add(settledTitleLabel, "cell 1 18,grow");

		settledLabel = new JLabel("");
		settledLabel.setForeground(Color.WHITE);
		settledLabel.setBackground(new Color(51,51,51));
		settledLabel.setFont(new Font("Source sans Pro", Font.BOLD, 16));
		settledLabel.setOpaque(true);
		add(settledLabel, "cell 2 18 8 1,grow");

		poswinTitleLabel = new JLabel("  Possible winnings:");
		poswinTitleLabel.setOpaque(true);
		poswinTitleLabel.setForeground(Color.WHITE);
		poswinTitleLabel.setBackground(new Color(51,51,51));
		poswinTitleLabel.setFont(new Font("Source sans Pro", Font.BOLD, 16));
		add(poswinTitleLabel, "cell 1 19 2 1,grow");

		poswinLabel = new JLabel("");
		poswinLabel.setBackground(new Color(51,51,51));
		poswinLabel.setForeground(Color.WHITE);
		poswinLabel.setFont(new Font("Source sans Pro", Font.BOLD, 16));
		poswinLabel.setOpaque(true);
		add(poswinLabel, "cell 3 19 7 1,grow");

		editModeLabel.setVisible(false);
		minBetLabel.setVisible(false);
		minBetTitleLabel.setVisible(false);
		stakeLabel.setVisible(false);
		betTypeLabel.setVisible(false);
		stakeField.setVisible(false);
		betTypeComboBox.setVisible(false);
		editBetButton.setEnabled(false);
		addPredictionsButton.setEnabled(false);
		cancelBetButton.setEnabled(false);
		cancelButton.setEnabled(false);
		saveChangesButton.setEnabled(false);

		loadBets();
	}

	/**
	 * Loads Bets that the user has currently unresolved(Open) in the upper table
	 */
	public void loadBets() {
		betTableModel.setDataVector(null, columnNamesOpen);
		betTableModel.setColumnCount(6); 
		betTable.setRowHeight(40);
		bets = facade.retrieveBets(MainGUI.getInstance().getLoggeduser().getUsername());
		for(BetContainer cBet: bets) {
			Bet b = cBet.getBet();
			if(b.getStatus().equals(Bet.BetStatus.ONGOING)) {
				addBetToTable(cBet);
			}
		}	

		betTable.getColumnModel().getColumn(0).setPreferredWidth(30);
		betTable.getColumnModel().getColumn(1).setPreferredWidth(25);
		betTable.getColumnModel().getColumn(2).setPreferredWidth(70);
		betTable.getColumnModel().getColumn(3).setPreferredWidth(70);
		betTable.getColumnModel().getColumn(4).setPreferredWidth(40);
		betTable.getColumnModel().removeColumn(betTable.getColumnModel().getColumn(5)); //not shown in JTable

		//Table sorting settings (edit and delete button columns sorting disabled)
		TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<DefaultTableModel>(betTableModel);
		betTable.setRowSorter(sorter);
	}

	/**
	 * Builds the hashmap that stores the mapping between the Events(Keys) and the predictions that has been placed on the event(List<Prediction>, the value)
	 * @param predictions	List of all predictions placed on the selected bet.
	 */
	public void computePredictionMap(List<PredictionContainer> predictions) {
		//create map with Event-predictions mapping
		predictionmap.clear();
		for(PredictionContainer p : predictions) {
			Event ev = p.getEvent();
			if(predictionmap.containsKey(ev)) {
				predictionmap.get(ev).add(p);
			}
			else {
				ArrayList<PredictionContainer> list = new ArrayList<PredictionContainer>();
				list.add(p);
				predictionmap.put(ev, list);
			}
		}
	}

	/**
	 * Computes the maximum possible winnings that the selected bet may attain (taking into consideration the predictions that have already won/failed,
	 * and sets this value on the poswinnings JLabel (the label below the prediction table)
	 */
	public void computePossibleWInnings() {
		boolean fullcover = false;
		float stake = 0;
		//ArrayList<Prediction> aux = new ArrayList<Prediction>();
		if(!stakeField.getText().isEmpty()) {
			if(editmode) {
				stake= stakeField.getFloat();
			}
			else {
				stake=selectedbet.getBet().getStake();
			}
		}


		if(selectedBetType != null && selectedBetType.getBetCount()>1) {
			fullcover = true;
		}


		if(predictions.isEmpty() || selectedBetType == null) {
			poswinnings = 0;
		}
		else if(predictions.size() == 1){
			poswinnings = stake*predictions.get(0).getPrediction().getOdds();
		}
		else if(fullcover) {
			poswinnings = Bet.calculateFullCoverWinnings(selectedBetType.predictionCount(),stake,predictions);
		}
		else {
			poswinnings = Bet.calculateCombinedWinnings(selectedBetType.predictionCount(),stake,predictions);
		}
		poswinLabel.setText(String.valueOf(poswinnings)+"€");
	}

	/**
	 * Loads the predictions placed on the selected bet on the prediction table (the bottom table)
	 * @param predictions	List of predictions on the selected bet.
	 */
	public void loadPredictions(List<PredictionContainer> predictions) {
		int objectcol = editmode? 8 : 7;

		predictionTableModel.setDataVector(null, columnNamesPrediction);
		predictionTableModel.setColumnCount(objectcol+1); 
		predictionTable.setRowHeight(40);

		//count the unresolved predictions and store on a list to later compute the possible winnings
		CellEditorModel rm = new CellEditorModel(predictions.size(),objectcol+1); //takes care of setting the answer comboboxes of edit mode
		int unresolved = 0;
		int k = 0;
		float betmin = 0;
		for(PredictionContainer p: predictions) {
			List<Prediction> questionpredictions = p.getQuestion().getPredictions();
			String[] answers = new String[questionpredictions.size()];
			for(int i = 0;i<questionpredictions.size(); i++) {
				answers[i] = questionpredictions.get(i).getAnswer() + ";" + questionpredictions.get(i).getOdds();
			}
			JComboBox<String> combobox = new JComboBox<String>(answers);
			combobox.addActionListener(comboxEditAction);
			rm.addEditorForRow(k++,3,new MyComboBoxEditor(combobox));

			addPredictionToTable(p);		

			if(p.getPrediction().getOutcome() != null) {
				unresolved++;
			}

			if(p.getQuestion().getBetMinimum()>betmin) {
				betmin=p.getQuestion().getBetMinimum();
			}
			minBetLabel.setText(String.valueOf(betmin) + "€");
		}	
		predictionTable.setRowEditorModel(rm);

		//set values of the labels of possible winnings and resolved predictions
		settledLabel.setText(String.valueOf(unresolved+"/"+predictions.size()));
		computePossibleWInnings();

		predictionTable.getColumnModel().getColumn(0).setMinWidth(10);
		predictionTable.getColumnModel().getColumn(0).setPreferredWidth(10);
		predictionTable.getColumnModel().getColumn(0).setMaxWidth(10);
		predictionTable.getColumnModel().getColumn(1).setPreferredWidth(50);
		predictionTable.getColumnModel().getColumn(2).setPreferredWidth(70);
		predictionTable.getColumnModel().getColumn(3).setPreferredWidth(70);
		predictionTable.getColumnModel().getColumn(4).setPreferredWidth(90);
		predictionTable.getColumnModel().getColumn(4).setMaxWidth(90);
		predictionTable.getColumnModel().getColumn(5).setPreferredWidth(40);
		predictionTable.getColumnModel().getColumn(6).setPreferredWidth(40);
		predictionTable.getColumnModel().getColumn(7).setPreferredWidth(70);
		predictionTable.getColumnModel().getColumn(7).setMaxWidth(80);
		predictionTable.getColumnModel().removeColumn(predictionTable.getColumnModel().getColumn(objectcol)); //not shown in JTable

		//Table sorting settings (edit and delete button columns sorting disabled)
		TableRowSorter<NonEditableTableModel> sorter = new TableRowSorter<NonEditableTableModel>(predictionTableModel);
		predictionTable.setRowSorter(sorter);
	}

	public List<PredictionContainer> updatePredictions(){
		predictions.clear();
		for(int i = 0; i<predictionTable.getRowCount();i++) {
			PredictionContainer pc = (PredictionContainer)predictionTableModel.getValueAt(i, 8);
			String answer = (String)predictionTableModel.getValueAt(i, 3);
			String[] s = answer.split(";");

			Prediction p = pc.getPrediction();
			p.setAnswer(s[0]);
			p.setOdds((float)predictionTableModel.getValueAt(i, 4));
			predictions.add(pc);
		}
		return predictions;
	}

	public void enableEditMode() {
		editmode = true;
		editModeLabel.setVisible(true);
		betTable.setEnabled(false);
		editBetButton.setEnabled(false);
		addPredictionsButton.setEnabled(false);
		cancelBetButton.setEnabled(false);
		cancelButton.setEnabled(true);
		saveChangesButton.setEnabled(true);

		//create a copy of the prediction list that will be used in the editing process(in case the user wants to cancel)
		//predictions = new ArrayList<Prediction>(selectedbet.getPredictions());
		loadPredictions(predictions);

		minBetLabel.setVisible(true);
		minBetTitleLabel.setVisible(true);
		stakeLabel.setVisible(true);
		betTypeLabel.setVisible(true);
		stakeField.setVisible(true);
		betTypeComboBox.setVisible(true);
		stakeField.setFloat(selectedbet.getBet().getStake());
		updateBetTypeCombobox();

	}

	public void disableEditMode() {
		editmode = false;
		editModeLabel.setVisible(false);
		betTable.setEnabled(true);
		editBetButton.setEnabled(true);
		addPredictionsButton.setEnabled(true);
		cancelBetButton.setEnabled(true);
		cancelButton.setEnabled(false);
		saveChangesButton.setEnabled(false);

		minBetLabel.setVisible(false);
		minBetTitleLabel.setVisible(false);
		stakeLabel.setVisible(false);
		betTypeLabel.setVisible(false);
		stakeField.setVisible(false);
		betTypeComboBox.setVisible(false);

		predictions = selectedbet.getPredictions();
		selectedBetType = selectedbet.getBet().getType();
		//betTable.setRowSelectionAllowed(false);
		computePredictionMap(predictions);
		loadPredictions(predictions);
	}

	Action comboxEditAction = new  AbstractAction() {
		public void actionPerformed(ActionEvent e) {
			int row = predictionTable.getSelectedRow();
			String answer = (String)predictionTableModel.getValueAt(row, 3);
			String[] s = answer.split(";");
			predictionTableModel.setValueAt(Float.parseFloat(s[1]), row, 4);
		}

	};

	Action delete = new AbstractAction()
	{
		public void actionPerformed(ActionEvent e)
		{	
			PredictionContainer pc = (PredictionContainer)predictionTableModel.getValueAt(predictionTable.getSelectedRow(),8);
			predictions.remove(pc);
			List<PredictionContainer> predictionsforevent = predictionmap.get(pc.getQuestion().getEvent());
			predictionsforevent.remove(pc);
			if(predictionsforevent.size() == 0) {
				predictionmap.remove(pc.getEvent());
			}

			updateBetTypeCombobox();
			loadPredictions(predictions);

			stakeField.setVisible(true);

		}
	};


	public void addBetToTable(BetContainer cBet) {
		SimpleDateFormat df = new SimpleDateFormat("HH:mm dd/MM/yyyy");
		Vector<Object> row = new Vector<Object>();
		Bet bet = cBet.getBet();

		row.add(bet.getType().name());
		row.add(bet.getStake());
		row.add(df.format(bet.getPlacementdate()));
		row.add(df.format(bet.getResolvingdate()));
		row.add(bet.getStatus());
		row.add(cBet);
		betTableModel.addRow(row);		

	}

	public void updateBetTypeCombobox() {
		betTypeComboBox.removeAllItems();
		if(predictions.size() == 0) {
			betTypeComboBox.setEnabled(false);
		}
		else if(predictions.size() == 1) {
			betTypeComboBoxModel.addElement(BetType.SINGLE);
			betTypeComboBox.setEnabled(true);
		}
		else {
			int multivalidcount = predictionmap.size();
			if(multivalidcount == 8) {
				betTypeComboBoxModel.addElement(BetType.GOLIATH);
			}
			if(multivalidcount == 7) {
				betTypeComboBoxModel.addElement(BetType.SUPER_HEINZ);
			}
			if(multivalidcount == 6) {
				betTypeComboBoxModel.addElement(BetType.HEINZ);
			}
			if(multivalidcount == 5) {
				betTypeComboBoxModel.addElement(BetType.SUPER_YANKEE);
			}
			if(multivalidcount == 4) {
				betTypeComboBoxModel.addElement(BetType.YANKEE);
			}
			if(multivalidcount == 3) {
				betTypeComboBoxModel.addElement(BetType.TRIXIE);
			}
			for(int i = multivalidcount; i>1 ; i--) {
				betTypeComboBoxModel.addElement(BetType.getType(i));
			}
			betTypeComboBox.setEnabled(true);
		}
		if(predictions.size() != 0) {
			selectedBetType = betTypeComboBoxModel.getElementAt(0);
			//betTypeComboBox.setSelectedIndex(0);
		}
		else {
			selectedBetType = selectedbet.getBet().getType();
		}
		betTypeComboBox.repaint();	
	}


	public void refreshPage() {
		int selectedrow = betTable.getSelectedRow();
		loadBets();

		int maxrow = betTable.getRowCount()-1;
		if(selectedrow != -1) {
			if(maxrow >= 0) {
				if(maxrow >= selectedrow) {
					betTable.setRowSelectionInterval(selectedrow, selectedrow);
					selectedbet = (BetContainer)betTableModel.getValueAt(selectedrow, 5);	
				}
				else {
					betTable.setRowSelectionInterval(maxrow, maxrow);
					selectedbet = (BetContainer)betTableModel.getValueAt(maxrow, 5);
				}
				if(!editmode) {
					predictions = selectedbet.getPredictions();
					predictionTableModel.setDataVector(null, columnNamesPrediction);
					computePredictionMap(predictions);
					loadPredictions(predictions);
				}
			}
		}
		repaint();
		revalidate();
	}

	public void addPredictionToTable(PredictionContainer pc) {
		SimpleDateFormat df = new SimpleDateFormat("HH:mm dd/MM/yyyy");
		Vector<Object> row = new Vector<Object>();


		Prediction p = pc.getPrediction();
		Question q = pc.getQuestion();
		row.add("");
		row.add(pc.getEvent().getDescription());
		row.add(q.getQuestion());
		row.add(p.getAnswer());
		row.add(p.getOdds());
		row.add(df.format(pc.getEvent().getEndingdate()));
		if(p.getOutcome() == null) {
			row.add("Unknown");
		}
		else if(p.getOutcome()) {
			row.add("Won");
		}
		else{
			row.add("Lost");
		}
		if(editmode) {
			try {
				row.add(new ImageIcon(ImageIO.read(new File("images/delete.png"))));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		row.add(pc);
		predictionTableModel.addRow(row);		
	}


	/**
	 * Action for the combobox that allows selection of bet type in bet editing mode
	 */
	Action betTypeComboBoxAction = new AbstractAction() {

		@Override
		public void actionPerformed(ActionEvent e) {
			selectedBetType = (BetType)betTypeComboBox.getSelectedItem();
			computePossibleWInnings();
		}
	};


	public class TableHeaderRenderer implements TableCellRenderer {

		private final TableCellRenderer baseRenderer;

		public TableHeaderRenderer(TableCellRenderer baseRenderer) {
			this.baseRenderer = baseRenderer;
		}

		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
			JComponent c = (JComponent)baseRenderer.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
			c.setBorder(new EmptyBorder(2,2,2,2));
			return c;
		}
	}


	/**
	 * Auxiliary class for making table model elements non editable
	 *
	 */
	public class NonEditableTableModel extends DefaultTableModel
	{

		public NonEditableTableModel(Object[][] data, Object[] columnNames) {
			super(data, columnNames);
		}

		public boolean isCellEditable (int row, int column)
		{
			if(editmode) {
				return (column == 3 || column == 7)? true : false;
			}
			else {
				return false;
			}

		}
	}

	////////////////////////
	public class MyComboBoxEditor extends DefaultCellEditor {
		public MyComboBoxEditor(JComboBox<String> jcb) {
			super(jcb);
		}
	}


	@SuppressWarnings("rawtypes")
	public class MyComboBoxRenderer extends JComboBox implements TableCellRenderer {
		@SuppressWarnings("unchecked")
		public MyComboBoxRenderer(String[] items) {
			super(items);
		}

		public Component getTableCellRendererComponent(JTable table, Object value,
				boolean isSelected, boolean hasFocus, int row, int column) {

			if(isSelected) {
				setForeground(table.getSelectionForeground());
				super.setBackground(Color.white);
			}
			else {
				setForeground(table.getSelectionForeground());
				super.setBackground(Color.white);
			}


			// Select the current value
			setSelectedItem(value);
			return this;
		}
	}
}


