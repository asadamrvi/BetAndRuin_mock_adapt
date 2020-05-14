package gui.Panels.subpanels;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Vector;
import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableRowSorter;
import businessLogic.BLFacade;
import domain.CreditCard;
import domain.User;
import gui.AddCreditCardGUI;
import gui.MainGUI;
import gui.components.ButtonColumn;
import gui.components.FancyButton;
import gui.components.Switch;
import net.miginfocom.swing.MigLayout;
import gui.components.DefaultCreditCard;

@SuppressWarnings("serial")
public class CreditCardsPanel extends JPanel {

	private JLabel defaultLabel;
	private JLabel defaultPaymentLabel;
	private JLabel creditCardsLabel;

	private Switch defaultSwitch;
	private JTable creditCardTable;
	private NonEditableTableModel creditCardTableModel;
	private DefaultTableCellRenderer defaultCardRenderer;
	private DefaultTableCellRenderer whiteRenderer;

	private FancyButton addCreditCardButton;
	private FancyButton saveDefaultCardButton;

	private DefaultCreditCard defaultCardPanel;
	
	private Map<String,CreditCard> creditcardmap;

	private String[] columnNamesCards = new String[] {
			ResourceBundle.getBundle("Etiquetas").getString("CardType"), 
			ResourceBundle.getBundle("Etiquetas").getString("CardNumber"), 
			ResourceBundle.getBundle("Etiquetas").getString("DueDate"), 
			ResourceBundle.getBundle("Etiquetas").getString("Balance"), 
			ResourceBundle.getBundle("Etiquetas").getString("Limit"), 
			ResourceBundle.getBundle("Etiquetas").getString("Status"), 
			""		
	};

	BLFacade facade = MainGUI.getBusinessLogic();

	/**
	 * Create the panel.
	 */
	public CreditCardsPanel() {

		User loggeduser = MainGUI.getInstance().getLoggeduser();
		setBackground(new Color(250, 235, 215));

		setLayout(new MigLayout("", "[20:20:20][55.00][63.00][66.00][30:30:30][10:10:10][25:25,grow][][215.00][20:20:20][20:20:20][-135.00]", "[20:14.00:20][:40.00:40.00][][103.00,grow][48.00][42.00][47.00][10:10:10][18.00:18.00:18.00][][19.00:19.00,grow][30:51.00:30][40:40:40][40:40:40][15:15:15][25:25:25][20:20:20]"));

		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane, "cell 1 3 9 4,grow");
		
		creditCardTable = new JTable() {
			public TableCellRenderer getCellRenderer(int row, int column)
			{
				if (whiteRenderer == null)
				{
					whiteRenderer = new DefaultTableCellRenderer();
					whiteRenderer.setHorizontalAlignment(SwingConstants.CENTER);
				}
				if (defaultCardRenderer == null)
				{
					defaultCardRenderer = new DefaultTableCellRenderer();
					defaultCardRenderer.setBackground(new Color(192,235,240));
					defaultCardRenderer.setHorizontalAlignment(SwingConstants.CENTER);
				}

				if ( creditCardTableModel.getValueAt(row, 7).equals(loggeduser.getDefaultCreditCard()) && column!=6)
					return defaultCardRenderer;
				else if(column!=6) {
					return whiteRenderer;
				}
				else {
					return (new ButtonColumn(creditCardTable, delete, 6, new Color(255,0,51)));
				}
			}
		};
		creditCardTable.setFocusable(true);
		creditCardTableModel = new NonEditableTableModel(null, columnNamesCards);
		creditCardTable.setModel(creditCardTableModel);

		creditCardTable.getTableHeader().setReorderingAllowed(false);
		creditCardTable.getTableHeader().setResizingAllowed(false);
		creditCardTable.getTableHeader().setFont(new Font("Source sans Pro", Font.BOLD, 16));
		creditCardTable.getTableHeader().setBackground(new Color(228,149,53));
		creditCardTable.getTableHeader().setForeground(Color.white);
		creditCardTable.setAutoCreateRowSorter(true);
		TableCellRenderer baseRenderer = creditCardTable.getTableHeader().getDefaultRenderer();
		creditCardTable.getTableHeader().setDefaultRenderer(new TableHeaderRenderer(baseRenderer));
		creditCardTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				int row = creditCardTable.getSelectedRow();
				if(row != -1) {
					if(creditCardTableModel.getValueAt(row, 7).equals(defaultCardPanel.getDefaultCard())){
						saveDefaultCardButton.setEnabled(false);
					}
					else {
						saveDefaultCardButton.setEnabled(true);
					}
				}
			}
		});
		scrollPane.setViewportView(creditCardTable);

		creditCardsLabel = new JLabel("Credit cards:");
		creditCardsLabel.setFont(new Font("Source Code Pro", Font.BOLD, 16));
		add(creditCardsLabel, "cell 1 1 5 2");

		addCreditCardButton = new FancyButton("Add credit card\r\n",new Color(51,51,51),new Color(170,170,170),new Color(150,150,150));
		addCreditCardButton.setForeground(Color.WHITE);
		addCreditCardButton.setFont(new Font("Source Sans Pro", Font.BOLD, 12));
		add(addCreditCardButton, "cell 8 1,grow");
		addCreditCardButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JDialog addcard = new AddCreditCardGUI();
				addcard.setVisible(true);
				loadCreditCards();
			}
		});

		defaultLabel = new JLabel("Chosen card to use by default:\r\n");
		defaultLabel.setFont(new Font("Source Code Pro", Font.BOLD, 16));
		add(defaultLabel, "cell 1 8 3 2");

		saveDefaultCardButton = new FancyButton("Save selected as default",new Color(51,51,51),new Color(170,170,170),new Color(150,150,150));
		saveDefaultCardButton.setForeground(Color.WHITE);
		saveDefaultCardButton.setFont(new Font("Source Sans Pro", Font.BOLD, 12));
		add(saveDefaultCardButton, "cell 7 7 3 2,grow");
		saveDefaultCardButton.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				CreditCard newdefault = (CreditCard)creditCardTableModel.getValueAt(creditCardTable.getSelectedRow(), 7);
				facade.setDefaultCreditCard(loggeduser,newdefault);
				MainGUI.getInstance().getLoggeduser().setDefaultCreditCard(newdefault);
				defaultCardPanel.setCard(newdefault);
				JOptionPane.showMessageDialog(null, "Default card updated sucessfully");
				loadCreditCards();
				saveDefaultCardButton.setEnabled(false);
			}
		});

		defaultCardPanel = new DefaultCreditCard();
		defaultCardPanel.setCard(loggeduser.getDefaultCreditCard());
		add(defaultCardPanel, "cell 1 10 5 4,grow");

		defaultPaymentLabel = new JLabel("Default payment:");
		defaultPaymentLabel.setFont(new Font("Source Code Pro", Font.BOLD, 16));
		add(defaultPaymentLabel, "cell 1 14 1 2,aligny center");

		defaultSwitch = new Switch() {
			   @Override
			    public void mouseClicked(MouseEvent e) {
				   if(defaultCardPanel.getDefaultCard() != null) {
				        if (isEnabled()) {
				            defaultSwitch.setOnOff(!defaultSwitch.isOnOff());
				            repaint();
				        }
				   }

			    }
		}; 
		add(defaultSwitch, "cell 2 14 1 2,alignx left,aligny top");

		loadCreditCards();
	}
	
	public void loadCreditCards() {
		creditCardTableModel.setDataVector(null, columnNamesCards);
		creditCardTableModel.setColumnCount(8); 
		creditCardTable.setRowHeight(40);
		creditcardmap = MainGUI.getInstance().getLoggeduser().getCreditCards();
		for(String number: creditcardmap.keySet()) {
			addCardToTable(creditcardmap.get(number));
		}	

		creditCardTable.getColumnModel().getColumn(0).setPreferredWidth(75);
		creditCardTable.getColumnModel().getColumn(1).setPreferredWidth(85);
		creditCardTable.getColumnModel().getColumn(2).setPreferredWidth(30);
		creditCardTable.getColumnModel().getColumn(3).setPreferredWidth(40);
		creditCardTable.getColumnModel().getColumn(4).setPreferredWidth(40);
		creditCardTable.getColumnModel().getColumn(5).setPreferredWidth(30);
		creditCardTable.getColumnModel().getColumn(6).setMinWidth(30);
		creditCardTable.getColumnModel().getColumn(6).setPreferredWidth(45);
		creditCardTable.getColumnModel().getColumn(6).setMaxWidth(50);
		creditCardTable.getColumnModel().removeColumn(creditCardTable.getColumnModel().getColumn(7)); //not shown in JTable

		//Table sorting settings (edit and delete button columns sorting disabled)
		TableRowSorter<NonEditableTableModel> sorter = new TableRowSorter<NonEditableTableModel>(creditCardTableModel);

		sorter.setSortable(6, false);
		creditCardTable.setRowSorter(sorter);

		ButtonColumn deleteButtonColumn = new ButtonColumn(creditCardTable, delete, 6, new Color(255,0,51));
		deleteButtonColumn.setMnemonic(KeyEvent.VK_D);
		
		saveDefaultCardButton.setEnabled(false);
	}

	Action delete = new AbstractAction()
	{
		public void actionPerformed(ActionEvent e)
		{	
			String cardnumber = (String)creditCardTable.getValueAt(creditCardTable.getSelectedRow(), 1);

			int option;
			option = JOptionPane.showConfirmDialog(getParent(), "Remove the credit card: " + cardnumber + "from this account?" , "Confirm deletion", JOptionPane.WARNING_MESSAGE,JOptionPane.OK_CANCEL_OPTION);
			if (option==0){
				CreditCard cc = (CreditCard)creditCardTableModel.getValueAt(creditCardTable.getSelectedRow(), 7);
				creditcardmap.remove(cc.getCardNumber());
				facade.removeCreditCard(cc.getCardNumber()); 
				if(cc.equals(defaultCardPanel.getDefaultCard())) {
					defaultCardPanel.reset();
					defaultSwitch.setOnOff(false);
					defaultSwitch.repaint();
				}
				loadCreditCards();
				
			}
		}
	};
	
	public void addCardToTable(CreditCard cc) {
		SimpleDateFormat df = new SimpleDateFormat("MM/yyyy");
		Vector<Object> row = new Vector<Object>();
		
		String numberending = cc.getCardNumber().substring(12);
		String hiddennumber = "**** **** **** " + numberending;
		
		row.add(cc.getCardType());
		row.add(hiddennumber);
		row.add(df.format(cc.getDueDate()));
		row.add(cc.getBalance());
		row.add(cc.getLimit());
		row.add(cc.getStatus());
		try {
			row.add(new ImageIcon(ImageIO.read(new File("images/delete.png"))));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		row.add(cc);
		creditCardTableModel.addRow(row);		

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
			if(column == 6) {
				return true;
			}
			return false;
		}
	}

}
