package gui.Panels.subpanels;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Vector;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableRowSorter;
import com.toedter.calendar.JDateChooser;
import businessLogic.BLFacade;
import domain.Bet;
import domain.Prediction;
import domain.Question;
import gui.MainGUI;
import net.miginfocom.swing.MigLayout;
import java.awt.SystemColor;

@SuppressWarnings("serial")
public class BettingHistoryPanel extends JPanel {

	private Bet selectedbet;

	private JLabel predictionsLabel;
	private JLabel betsLabel;
	private JTable betTable;
	private NonEditableTableModel betTableModel;
	private DefaultTableCellRenderer selectedBetRenderer;
	private DefaultTableCellRenderer wonPredictionRendered;
	private DefaultTableCellRenderer lostPredictionRendered;
	private DefaultTableCellRenderer whiteRenderer;

	private JLabel showingCountLabel;
	private final int PAGESIZE = 30;
	private int currentpage;

	private List<Bet> searchResult;

	private List<Bet> bets;
	private List<Prediction> predictions;
	
	private JButton btnNextPage;
	private JButton btnPrevPage;
	
	private JDateChooser todatechooser;
	private JDateChooser fromdatechooser;

	private String[] columnNamesHistory = new String[] {
			ResourceBundle.getBundle("Etiquetas").getString("BetType"), 
			ResourceBundle.getBundle("Etiquetas").getString("Stake"), 
			ResourceBundle.getBundle("Etiquetas").getString("PlacedOn"), 
			ResourceBundle.getBundle("Etiquetas").getString("Outcome"), 
			ResourceBundle.getBundle("Etiquetas").getString("Winnings"), 
			""		
	};

	private String[] columnNamesPrediction = new String[] {
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
	private JTable predictionTable;
	private NonEditableTableModel predictionTableModel;

	/**
	 * Create the panel.
	 */
	public BettingHistoryPanel() {

		setBackground(SystemColor.inactiveCaption);

		setLayout(new MigLayout("gap 0px 0px", "[20:20:20][113.00][96.00,grow][100:100:100][8:8:8][100:100:100][5:5:5][20:20:20][20:20:20][-135.00]", "[20:14.00:20][][:40.00:40.00][35][40:40:40][40:40:40][40:40:40][10:10:10][40:40:40][][grow][25:25:25][19.00:19.00,grow][90:100,grow][10:10:10][30:30:30][40:40:40][15:15:15][25:25:25][20:20:20]"));
		
		
		Calendar cld = Calendar.getInstance();
		cld.set(Calendar.DAY_OF_YEAR, 5);
		cld.set(Calendar.MONTH, 3);
		cld.set(Calendar.YEAR, 2019);
		fromdatechooser = new JDateChooser(cld.getTime());
		add(fromdatechooser, "cell 3 2,grow");
		fromdatechooser.addPropertyChangeListener(new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				search.actionPerformed(new ActionEvent(this,ActionEvent.ACTION_PERFORMED,null));
			}
		});
		
		cld.set(Calendar.DAY_OF_YEAR, 5);
		cld.set(Calendar.MONTH, 6);
		cld.set(Calendar.YEAR, 2020);

		todatechooser = new JDateChooser(cld.getTime());
		add(todatechooser, "cell 5 2,grow");
		todatechooser.addPropertyChangeListener(new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				search.actionPerformed(new ActionEvent(this,ActionEvent.ACTION_PERFORMED,null));
			}
		});

		
		showingCountLabel = new JLabel(""); 
		add(showingCountLabel, "cell 1 15");

		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane, "cell 1 4 7 10,grow");

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
					selectedBetRenderer.setBackground(new Color(192,235,240));
					selectedBetRenderer.setHorizontalAlignment(SwingConstants.CENTER);
				}

				if ( betTableModel.getValueAt(row, 5).equals(selectedbet))
					return selectedBetRenderer;
				else {
					return whiteRenderer;
				}
			}
		};
		betTable.setFocusable(false);
		betTableModel = new NonEditableTableModel(null, columnNamesHistory);
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
					if(row != -1) {
						loadPredictions(((Bet)betTableModel.getValueAt(row, 5)));
					}
				}
			}
		});
		scrollPane.setViewportView(betTable);

		betsLabel = new JLabel("Bets:");
		betsLabel.setFont(new Font("Source Code Pro", Font.BOLD, 16));
		add(betsLabel, "cell 1 1 1 2");

		add(getBtnPrevPage(), "cell 3 15,growy");
		
		add(getBtnNextPage(), "cell 5 15 2 1,grow");

		predictionsLabel = new JLabel("Predictions on this bet:\r\n");
		predictionsLabel.setFont(new Font("Source Code Pro", Font.BOLD, 16));
		add(predictionsLabel, "flowx,cell 1 17");

		predictionsScrollPane = new JScrollPane();
		add(predictionsScrollPane, "cell 1 18 7 1,grow");


		predictionTable = new JTable(){
			public TableCellRenderer getCellRenderer(int row, int column)
			{
				if (whiteRenderer == null)
				{
					whiteRenderer = new DefaultTableCellRenderer();
					whiteRenderer.setHorizontalAlignment(SwingConstants.CENTER);
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
					lostPredictionRendered.setBackground(new Color(245,102,102));
					lostPredictionRendered.setHorizontalAlignment(SwingConstants.CENTER);
				}

				if (((Prediction)predictionTableModel.getValueAt(row, 6)).getOutcome() == null)
					return whiteRenderer;
				else if(((Prediction)predictionTableModel.getValueAt(row, 6)).getOutcome() == true){
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
		
		btnNextPage.setEnabled(false);
		btnPrevPage.setEnabled(false);
		
		search.actionPerformed(new ActionEvent(this,ActionEvent.ACTION_PERFORMED,null));
	}


	public void loadPredictions(Bet bet) {
		predictionTableModel.setDataVector(null, columnNamesPrediction);
		predictionTableModel.setColumnCount(7); 
		predictionTable.setRowHeight(40);
		predictions = bet.getPredictions();
		for(Prediction p: predictions) {
			addPredictionToTable(p);
		}	


		predictionTable.getColumnModel().getColumn(0).setPreferredWidth(50);
		predictionTable.getColumnModel().getColumn(1).setPreferredWidth(25);
		predictionTable.getColumnModel().getColumn(2).setPreferredWidth(70);
		predictionTable.getColumnModel().getColumn(3).setPreferredWidth(70);
		predictionTable.getColumnModel().getColumn(4).setPreferredWidth(40);
		predictionTable.getColumnModel().getColumn(5).setPreferredWidth(40);
		predictionTable.getColumnModel().removeColumn(predictionTable.getColumnModel().getColumn(6)); //not shown in JTable

		//Table sorting settings (edit and delete button columns sorting disabled)
		TableRowSorter<NonEditableTableModel> sorter = new TableRowSorter<NonEditableTableModel>(predictionTableModel);
		predictionTable.setRowSorter(sorter);
	}

	public void addBetToTable(Bet bet) {
		SimpleDateFormat df = new SimpleDateFormat("HH:mm dd/MM/yyyy");
		Vector<Object> row = new Vector<Object>();

		row.add(bet.getType().name());
		row.add(bet.getStake());
		row.add(df.format(bet.getPlacementdate()));
		if(bet.getStatus().equals(Bet.Status.RESOLVED)) {
			List<Prediction> pred = bet.getPredictions();
			int won = 0;
			for(Prediction p : pred) {
				if(p.getOutcome()!=null && p.getOutcome()) {
					won++;
				}
			}
			row.add(won + "/" + pred.size());
			row.add(bet.getWinnings());
		}
		else {
			row.add(df.format(bet.getResolvingdate()));
			row.add(bet.getStatus());
		}
		row.add(bet);
		betTableModel.addRow(row);		

	}

	public void addPredictionToTable(Prediction p) {
		SimpleDateFormat df = new SimpleDateFormat("HH:mm dd/MM/yyyy");
		Vector<Object> row = new Vector<Object>();

		Question q = p.getQuestion();

		row.add(q.getEvent().getDescription());
		row.add(q.getQuestion());
		row.add(p.getAnswer());
		row.add(p.getOdds());
		row.add(df.format(p.getQuestion().getEvent().getEndingdate()));
		if(p.getOutcome() == null) {
			row.add("Unknown");
		}
		else if(p.getOutcome()) {
			row.add("Won");
		}
		else{
			row.add("Lost");
		}
		row.add(p);
		predictionTableModel.addRow(row);		
	}
	
	private JButton getBtnPrevPage() {
		if(btnPrevPage==null) {
			btnPrevPage = new JButton();
			btnPrevPage.setFont(new Font("Tahoma", Font.BOLD, 11));
			btnPrevPage.setForeground(new Color(255, 255, 255));
			btnPrevPage.setBackground(new Color(51, 51, 51));
			btnPrevPage.setText(ResourceBundle.getBundle("Etiquetas").getString("PreviousPage"));
			btnPrevPage.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					currentpage--;
					loadPage(currentpage);
					if(currentpage==1) {
						btnPrevPage.setEnabled(false);
					}
					btnNextPage.setEnabled(true);
				}
			});
		}
		return btnPrevPage;
	}
	

	private JButton getBtnNextPage() {
		if(btnNextPage==null) {
			btnNextPage = new JButton();
			btnNextPage.setFont(new Font("Tahoma", Font.BOLD, 11));
			btnNextPage.setForeground(new Color(255, 255, 255));
			btnNextPage.setBackground(new Color(51, 51, 51));
			btnNextPage.setText(ResourceBundle.getBundle("Etiquetas").getString("NextPage"));
			btnNextPage.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					currentpage++;
					loadPage(currentpage);
					if((currentpage*PAGESIZE)>searchResult.size()) {
						btnNextPage.setEnabled(false);
					}
					btnPrevPage.setEnabled(true);
				}
			});
		}
		return btnNextPage;
	}

	AbstractAction search =new AbstractAction() {
		@Override
		public void actionPerformed(ActionEvent e) {
			btnNextPage.setEnabled(false);
			
			Date fromdate = fromdatechooser.getDate();
			Date todate = todatechooser.getDate();

			//restart model
			betTableModel.setDataVector(null, columnNamesHistory);
			betTableModel.setColumnCount(6); 

			//perform search and create the table model with the results
			searchResult=facade.retrieveBetsByDate(fromdate, todate);	
			System.out.println("BettingHistory searchresult size: " + searchResult.size());
			System.out.println("search: " + searchResult.size());
			loadPage(1);
			if(searchResult.size() > PAGESIZE){
				btnNextPage.setEnabled(true);
			}
			currentpage = 1;
		}
	};
	

	/**
	 * Loads a batch of up to PAGESIZE users found via search on the table.
	 * @param pageNumber    number of page to load.
	 * @return				number of elements in the current page.
	 */
	public int loadPage(int pageNumber) {
		betTableModel.setDataVector(null, columnNamesHistory);
		betTableModel.setColumnCount(6); 
		betTable.setRowHeight(40);
		
		int elementsOnPage = 0;
		int index = (pageNumber-1)*PAGESIZE;
		int remainingelements = searchResult.size() - index;
		while((elementsOnPage < PAGESIZE) && (elementsOnPage < remainingelements)) {
			addBetToTable(searchResult.get(index+elementsOnPage));
			elementsOnPage++;
		}

		if(remainingelements==0) {
			showingCountLabel.setText("Showing " + 0 + " to " + 0 + " of " + 0);
		}
		else {
			showingCountLabel.setText("Showing " + (index+1) + " to " + (index+elementsOnPage) + " of " + searchResult.size());
		}

		betTable.getColumnModel().getColumn(0).setPreferredWidth(30);
		betTable.getColumnModel().getColumn(1).setPreferredWidth(25);
		betTable.getColumnModel().getColumn(2).setPreferredWidth(70);
		betTable.getColumnModel().getColumn(3).setPreferredWidth(70);
		betTable.getColumnModel().getColumn(4).setPreferredWidth(40);
		betTable.getColumnModel().removeColumn(betTable.getColumnModel().getColumn(5)); //not shown in JTable

		//Table sorting settings (edit and delete button columns sorting disabled)
		TableRowSorter<NonEditableTableModel> sorter = new TableRowSorter<NonEditableTableModel>(betTableModel);
		betTable.setRowSorter(sorter);
		
		return elementsOnPage;
	}

	
	public void refreshPage() {
		int selectedrow = betTable.getSelectedRow();
		loadPage(currentpage);
		int maxrow = betTable.getRowCount()-1;
		if(selectedrow != -1) {
			if(maxrow >= 0) {
				if(maxrow >= selectedrow) {
					betTable.setRowSelectionInterval(selectedrow, selectedrow);
				}
				else {
					betTable.setRowSelectionInterval(maxrow, maxrow);
				}
			}
		}
		repaint();
		revalidate();
	}

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
			return false;
		}
	}

}
