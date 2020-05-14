package gui.Panels;

import java.awt.Color;
import java.awt.Font;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import businessLogic.BLFacade;
import domain.User;
import gui.EditUserGUI;
import gui.MainGUI;
import gui.RegisterGUI;
import gui.components.ButtonColumn;
import net.miginfocom.swing.MigLayout;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

@SuppressWarnings("serial")
public class UserManagementPanel extends JPanel {

	private final int PAGESIZE = 30;
	private int currentpage;
	
	private JTable userTable;
	private NonEditableTableModel userTableModel;

	private JScrollPane scrollPane = new JScrollPane();
	private JCheckBox chckbxCasesSensitive = new JCheckBox(ResourceBundle.getBundle("Etiquetas").getString("CaseSensitive"));
	private JLabel lblSearchBy = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("SearchBy"));
	private JLabel lblSearch = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Search"));
	private JLabel lbltitle = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("UserManagement"));
	private JLabel showingCountLabel = new JLabel("");

	private JTextField searchField = new JTextField();
	private String searchinput;
	private String searchfilter;
	private List<User> searchResult;

	private JButton btnSearch;
	private JButton btnNextPage;
	private JButton btnPrevPage;
	private JButton btnAddAUser;

	String[] filters = { "Username","ID", "Name", "Surname", "Email", "Nationality","City","Phone number"};  //,"Birthdate"};
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private JComboBox filterComboBox = new JComboBox(filters);
	String[] match = { "Full match", "Beginning", "Contains"};
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private JComboBox matchComboBox = new JComboBox(match);

	private String[] columnNamesUsers = new String[] {
			ResourceBundle.getBundle("Etiquetas").getString("Username"), 
			ResourceBundle.getBundle("Etiquetas").getString("ID"), 
			ResourceBundle.getBundle("Etiquetas").getString("Name"), 
			ResourceBundle.getBundle("Etiquetas").getString("Surname"), 
			ResourceBundle.getBundle("Etiquetas").getString("Email"), 
			ResourceBundle.getBundle("Etiquetas").getString("Country"), 
			ResourceBundle.getBundle("Etiquetas").getString("City"), 
			ResourceBundle.getBundle("Etiquetas").getString("Address"), 
			ResourceBundle.getBundle("Etiquetas").getString("PhoneNumber"), 
			ResourceBundle.getBundle("Etiquetas").getString("Birthdate"), 
			ResourceBundle.getBundle("Etiquetas").getString("JoinDate"), 
			ResourceBundle.getBundle("Etiquetas").getString("LastLogin"), 
			ResourceBundle.getBundle("Etiquetas").getString("Status"),
			"", ""				
	};


	private BLFacade facade=MainGUI.getBusinessLogic();





	/**
	 * Create the panel.
	 */
	public UserManagementPanel() {
		setLayout(new MigLayout("", "[10:30:30,grow][][150:150:150][][25:25:25][][110:110:110][5:16.00:5][90:90:90][][22.00:22.00,grow][110:110:110][4:4:4][110:110:110][20:20:20][10:30:30,grow]", "[][20:20:20][][20:20:20][][10:10:10][grow][2:2:2][][45:45:45]"));


		setBackground(new Color(245, 245, 245));

		userTable = new JTable();
		userTable.setBackground(new Color(255, 255, 255));
		userTable.setForeground(Color.BLACK);
		userTable.getTableHeader().setReorderingAllowed(false);
		userTable.getTableHeader().setResizingAllowed(false);
		userTable.setRowHeight(25);

		userTableModel = new NonEditableTableModel(null, columnNamesUsers);
		userTable.setModel(userTableModel);

		lbltitle = new JLabel("User management:");
		lbltitle.setFont(new Font("Source Code Pro Medium", Font.BOLD, 24));
		add(lbltitle, "cell 1 2 6 1");



		lblSearch = new JLabel("Search:");
		lblSearch.setFont(new Font("Tahoma", Font.BOLD, 11));
		add(lblSearch, "cell 1 4,alignx trailing");

		searchField = new JTextField();
		searchField.setColumns(10);
		add(searchField, "cell 2 4,growx");
		searchField.setColumns(10);
		searchField.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void removeUpdate(DocumentEvent e) {
				search.actionPerformed(new ActionEvent(this,ActionEvent.ACTION_PERFORMED,null));
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				search.actionPerformed(new ActionEvent(this,ActionEvent.ACTION_PERFORMED,null));
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				search.actionPerformed(new ActionEvent(this,ActionEvent.ACTION_PERFORMED,null));
			}
		});

		

		lblSearchBy = new JLabel("Search by");
		add(lblSearchBy, "cell 5 4,alignx trailing");

		filterComboBox.setFont(new Font("Tahoma", Font.BOLD, 11));
		filterComboBox.setForeground(Color.BLACK);
		filterComboBox.setBackground(new Color(245, 245, 245));
		filterComboBox.addActionListener(search);	
		add(filterComboBox, "cell 6 4,growx");

		matchComboBox.setFont(new Font("Tahoma", Font.BOLD, 11));
		matchComboBox.setForeground(Color.BLACK);
		matchComboBox.setBackground(new Color(245, 245, 245));
		matchComboBox.addActionListener(search);
		add(matchComboBox, "cell 8 4,growx");

		chckbxCasesSensitive = new JCheckBox("Case sensitive");
		chckbxCasesSensitive.setBackground(new Color(245, 245, 245));
		add(chckbxCasesSensitive, "cell 9 4");
		chckbxCasesSensitive.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				search.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null));
			}
		});	

		add(getBtnSearch(), "cell 3 4");
		add(getBtnAddUser(), "cell 13 4,grow");
		add(getBtnPrevPage(), "cell 11 8,grow");
		add(getBtnNextPage(), "cell 13 8,growx,aligny center");

		btnNextPage.setEnabled(false);
		btnPrevPage.setEnabled(false);

		showingCountLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		add(showingCountLabel, "cell 2 8 3 1");

		scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		add(scrollPane, "cell 1 6 14 1,grow");
		scrollPane.setViewportView(userTable);

		search.actionPerformed(new ActionEvent(this,ActionEvent.ACTION_PERFORMED,null));
	}



	private JButton getBtnSearch() {
		if(btnSearch==null) {
			btnSearch = new JButton();
			btnSearch.setForeground(new Color(255, 255, 255));
			btnSearch.setToolTipText(ResourceBundle.getBundle("Etiquetas").getString("userManagementPanel.btnSearch.toolTipText")); //$NON-NLS-1$ //$NON-NLS-2$
			btnSearch.setBackground(new Color(51, 51, 51));
			btnSearch.setFocusPainted(false);
			try {
				btnSearch.setIcon(new ImageIcon(ImageIO.read(new File("images/searchicon2.png"))));
				btnSearch.addActionListener(search);
				btnSearch.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "search");
				btnSearch.getActionMap().put("search", search);
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		return btnSearch;
	}

	AbstractAction search =new AbstractAction() {
		@Override
		public void actionPerformed(ActionEvent e) {
			btnNextPage.setEnabled(false);
			searchinput = searchField.getText();		
			searchfilter = (String)filterComboBox.getSelectedItem();

			//restart model
			userTableModel.setDataVector(null, columnNamesUsers);
			userTableModel.setColumnCount(15); 

			//perform search and create the table model with the results
			searchResult=facade.searchByCriteria(searchinput,searchfilter,chckbxCasesSensitive.isSelected(),matchComboBox.getSelectedIndex());		
			loadPage(1);
			if(searchResult.size() > PAGESIZE){
				btnNextPage.setEnabled(true);
			}
			currentpage = 1;
		}
	};

	private JButton getBtnAddUser() {
		if(btnAddAUser==null) {
			btnAddAUser = new JButton();
			btnAddAUser.setFont(new Font("Tahoma", Font.BOLD, 11));
			btnAddAUser.setForeground(new Color(255, 255, 255));
			btnAddAUser.setBackground(new Color(51, 51, 51));
			btnAddAUser.setText(ResourceBundle.getBundle("Etiquetas").getString("AddUser"));
			btnAddAUser.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					RegisterGUI j = new RegisterGUI(true);
					j.setVisible(true);
					search.actionPerformed(new ActionEvent(this,ActionEvent.ACTION_PERFORMED,null));
				}
			});
		}
		return btnAddAUser;
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


	//Actions for the Edit and Delete buttons on the table

	Action delete = new AbstractAction()
	{
		public void actionPerformed(ActionEvent e)
		{	
			int option;
			String username = (String)userTable.getValueAt(userTable.getSelectedRow(), 0);
			User loggeduser = MainGUI.getInstance().getLoggeduser();
			
			//if we try do delete our own admin account
			if(username.equals(loggeduser.getProfile().getID())) {
				option = JOptionPane.showConfirmDialog(getParent(),"Deleting your account is not reversible and will result in a log out, are you sure you want to continue?","Confirm deletion",
						JOptionPane.WARNING_MESSAGE,JOptionPane.OK_CANCEL_OPTION);
				if(option==0) {
					facade.removeUser((String)userTable.getValueAt(userTable.getSelectedRow(), 0));
					System.gc();
					for (Window window : Window.getWindows()) {
						window.dispose();
					}
					MainGUI.getInstance().logOut();
				}
			}
			else {
				option = JOptionPane.showConfirmDialog(getParent(), "Delete the user " + username + "?" , "Confirm deletion", JOptionPane.WARNING_MESSAGE,JOptionPane.OK_CANCEL_OPTION);
				if (option==0){
					int index = (currentpage-1)*PAGESIZE;
					searchResult.remove(index+userTable.getSelectedRow());
					facade.removeUser((String)userTable.getValueAt(userTable.getSelectedRow(), 0));

					if((searchResult.size()-index)==0) { //if we deleted the last element of the page
						if(currentpage==1) { 
							loadPage(currentpage);
						}
						else {  
							currentpage--;
							loadPage(currentpage);
							btnNextPage.setEnabled(false);
							if(currentpage==1) {
								btnPrevPage.setEnabled(false);
							}
						}
					}
					else { 
						loadPage(currentpage);
						if(searchResult.size()<=(currentpage*PAGESIZE)) {//if the current page has become the last page
							btnNextPage.setEnabled(false);
						}
					}
				}
			}
		}

	};

	Action edit = new AbstractAction()
	{
		public void actionPerformed(ActionEvent e)
		{
			int row = userTable.getSelectedRow();
			EditUserGUI j = new EditUserGUI((String)userTable.getValueAt(row, 0),(String)userTable.getValueAt(row, 1), (String)userTable.getValueAt(row, 2), 
					(String)userTable.getValueAt(row, 3),(String)userTable.getValueAt(row, 4),(String)userTable.getValueAt(row, 5), (String)userTable.getValueAt(row, 6),
					(String)userTable.getValueAt(row, 7),(String)userTable.getValueAt(row, 8),(String)userTable.getValueAt(row, 9),(String)userTable.getValueAt(row, 12));
			j.setVisible(true);

			//update row with new info
			String[] newData = j.newData();
			userTable.setValueAt(newData[0], row, 0); //Username
			userTable.setValueAt(newData[1], row, 1); //ID
			userTable.setValueAt(newData[2], row, 2); //name
			userTable.setValueAt(newData[3], row, 3); //Surname
			userTable.setValueAt(newData[4], row, 4); //Email
			userTable.setValueAt(newData[5], row, 5); //Country
			userTable.setValueAt(newData[6], row, 6); //City
			userTable.setValueAt(newData[7], row, 7); //Address
			userTable.setValueAt(newData[8], row, 8); //Phone number
			userTable.setValueAt(newData[9], row, 9); //birthdate
			userTable.setValueAt(newData[10], row, 12); //Status
		}
	};


	/**
	 * Loads a batch of up to PAGESIZE users found via search on the table.
	 * @param pageNumber    number of page to load.
	 * @return				number of elements in the current page.
	 */
	public int loadPage(int pageNumber) {
		userTableModel.setDataVector(null, columnNamesUsers);
		userTableModel.setColumnCount(15); 
		int elementsOnPage = 0;
		int index = (pageNumber-1)*PAGESIZE;
		int remainingelements = searchResult.size() - index;
		while((elementsOnPage < PAGESIZE) && (elementsOnPage < remainingelements)) {
			addUserToTable(searchResult.get(index+elementsOnPage));
			elementsOnPage++;
		}

		if(remainingelements==0) {
			showingCountLabel.setText("Showing " + 0 + " to " + 0 + " of " + 0);
		}
		else {
			showingCountLabel.setText("Showing " + (index+1) + " to " + (index+elementsOnPage) + " of " + searchResult.size());
		}

		userTable.getColumnModel().getColumn(0).setPreferredWidth(75);
		userTable.getColumnModel().getColumn(1).setPreferredWidth(75);
		userTable.getColumnModel().getColumn(2).setPreferredWidth(75);
		userTable.getColumnModel().getColumn(3).setPreferredWidth(75);
		userTable.getColumnModel().getColumn(4).setPreferredWidth(160);
		userTable.getColumnModel().getColumn(5).setPreferredWidth(50);
		userTable.getColumnModel().getColumn(6).setPreferredWidth(75);
		userTable.getColumnModel().getColumn(7).setPreferredWidth(160);
		userTable.getColumnModel().getColumn(8).setPreferredWidth(75);
		userTable.getColumnModel().getColumn(9).setPreferredWidth(75);
		userTable.getColumnModel().getColumn(10).setPreferredWidth(75);
		userTable.getColumnModel().getColumn(11).setPreferredWidth(75);
		userTable.getColumnModel().getColumn(12).setPreferredWidth(75);
		userTable.getColumnModel().getColumn(13).setPreferredWidth(30);
		userTable.getColumnModel().getColumn(14).setPreferredWidth(30);

		//Table sorting settings (edit and delete button columns sorting disabled)
		TableRowSorter<NonEditableTableModel> sorter = new TableRowSorter<NonEditableTableModel>(userTableModel);

		sorter.setSortable(13, false);
		sorter.setSortable(14, false);
		userTable.setRowSorter(sorter);

		ButtonColumn editButtonColumn = new ButtonColumn(userTable, edit, 13, new Color(51,51,51));
		ButtonColumn deleteButtonColumn = new ButtonColumn(userTable, delete, 14, new Color(255,0,51));
		editButtonColumn.setMnemonic(KeyEvent.VK_E);
		deleteButtonColumn.setMnemonic(KeyEvent.VK_D);
		return elementsOnPage;
	}

	/**
	 * Creates a new row in the search table with a User's data.
	 * @param u		User to insert.
	 */
	public void addUserToTable(User u) {

		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat dfh = new SimpleDateFormat("HH:mm dd/MM/yyyy");
		Vector<Object> row = new Vector<Object>();
		row.add(u.getUsername());
		row.add(u.getProfile().getID());
		row.add(u.getProfile().getName());
		row.add(u.getProfile().getSurname());
		row.add(u.getProfile().getEmail());
		row.add(u.getProfile().getNationality().getString());
		row.add(u.getProfile().getCity());
		row.add(u.getProfile().getAddress());
		row.add(u.getProfile().getPhonenumber());
		row.add(df.format(u.getProfile().getBirthdate()));
		row.add(dfh.format(u.getRegistrationdate()));
		if(u.getLastlogin() != null) {
			row.add(dfh.format(u.getLastlogin()));
		}
		else {
			row.add("Never");
		}
		row.add(u.statusToString());
		try {
			row.add(new ImageIcon(ImageIO.read(new File("images/edit1.png"))));
			row.add(new ImageIcon(ImageIO.read(new File("images/delete.png"))));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		userTableModel.addRow(row);		
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
			if(column == 12 || column==13) {
				return true;
			}
			return false;
		}
	}
}
