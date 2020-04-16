package gui.Panels;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
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
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import businessLogic.BLFacade;
import domain.User;
import gui.EditUserGUI;
import gui.MainGUI;
import gui.RegisterGUI;
import gui.components.ButtonColumn;
import java.awt.Color;

@SuppressWarnings("serial")
public class userManagementPanel extends JPanel {


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
	
	private final int PAGESIZE = 30;
	private int currentpage;

	private JButton btnSearch;
	private JButton btnNextPage;
	private JButton btnPrevPage;
	private JButton btnAddAUser;

	
	String[] filters = { "Username","ID", "Name", "Surname", "Email", "Nationality","City","Phone number"};  //,"Birthdate"};
	private JComboBox filterComboBox = new JComboBox(filters);
	String[] match = { "Full match", "Beginning", "Contains"};
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
	 * Create the 
	 */
	public userManagementPanel() {
		setBackground(new Color(245, 245, 245));

		userTable = new JTable();
		userTable.setBackground(new Color(255, 255, 255));
		userTable.setForeground(Color.BLACK);
		userTable.getTableHeader().setReorderingAllowed(false);
		userTable.getTableHeader().setResizingAllowed(false);
		userTable.setRowHeight(25);

		userTableModel = new NonEditableTableModel(null, columnNamesUsers);
		userTable.setModel(userTableModel);
		
		setBounds(100, 100, 881, 589);
		setBorder(new EmptyBorder(5, 5, 5, 5));
		setLayout(new BorderLayout(0, 0));

		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{48, 30, 30, 30, 30, 0, 20, 70, 47, 70, 20, 74, 60, 103, 20, 20, 20, 0};
		gbl_panel.rowHeights = new int[]{33, 20, 20, 0, 20, 0, 30, 30, 30, 185, 20, 30, 0, 20, 0};
		gbl_panel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gbl_panel);

		lbltitle.setFont(new Font("Source Code Pro Medium", Font.BOLD, 24));
		GridBagConstraints gbc_lbltitle = new GridBagConstraints();
		gbc_lbltitle.gridwidth = 4;
		gbc_lbltitle.insets = new Insets(0, 0, 5, 5);
		gbc_lbltitle.gridx = 1;
		gbc_lbltitle.gridy = 1;
		add(lbltitle, gbc_lbltitle);
		
		GridBagConstraints gbc_lblSearch = new GridBagConstraints();
		gbc_lblSearch.anchor = GridBagConstraints.WEST;
		gbc_lblSearch.insets = new Insets(0, 0, 5, 5);
		gbc_lblSearch.gridx = 1;
		gbc_lblSearch.gridy = 3;
		lblSearch.setFont(new Font("Tahoma", Font.BOLD, 11));
		add(lblSearch, gbc_lblSearch);

		GridBagConstraints gbc_searchField = new GridBagConstraints();
		gbc_searchField.gridwidth = 3;
		gbc_searchField.insets = new Insets(0, 0, 5, 5);
		gbc_searchField.fill = GridBagConstraints.HORIZONTAL;
		gbc_searchField.gridx = 2;
		gbc_searchField.gridy = 3;
		add(searchField, gbc_searchField);
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

		GridBagConstraints gbc_btnSearch = new GridBagConstraints();
		gbc_btnSearch.insets = new Insets(0, 0, 5, 5);
		gbc_btnSearch.gridx = 5;
		gbc_btnSearch.gridy = 3;
		add(getBtnSearch(), gbc_btnSearch);
		
		GridBagConstraints gbc_lblSearchBy = new GridBagConstraints();
		gbc_lblSearchBy.insets = new Insets(0, 0, 5, 5);
		gbc_lblSearchBy.anchor = GridBagConstraints.EAST;
		gbc_lblSearchBy.gridx = 7;
		gbc_lblSearchBy.gridy = 3;
		add(lblSearchBy, gbc_lblSearchBy);

		GridBagConstraints gbc_filterComboBox = new GridBagConstraints();
		gbc_filterComboBox.insets = new Insets(0, 0, 5, 5);
		gbc_filterComboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_filterComboBox.gridx = 8;
		gbc_filterComboBox.gridy = 3;
		add(filterComboBox, gbc_filterComboBox);
		
		GridBagConstraints gbc_comboboxMatch = new GridBagConstraints();
		gbc_comboboxMatch.insets = new Insets(0, 0, 5, 5);
		gbc_comboboxMatch.gridx = 9;
		gbc_comboboxMatch.gridy = 3;
		add(matchComboBox, gbc_comboboxMatch);

		GridBagConstraints gbc_chckbxCasesSensitive = new GridBagConstraints();
		gbc_chckbxCasesSensitive.insets = new Insets(0, 0, 5, 5);
		gbc_chckbxCasesSensitive.gridx = 10;
		gbc_chckbxCasesSensitive.gridy = 3;
		chckbxCasesSensitive.setBackground(new Color(245, 245, 245));
		add(chckbxCasesSensitive, gbc_chckbxCasesSensitive);
		chckbxCasesSensitive.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				search.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null));
			}
		});	
		
		GridBagConstraints gbc_showingCountLabel = new GridBagConstraints();
		gbc_showingCountLabel.gridwidth = 5;
		gbc_showingCountLabel.insets = new Insets(0, 0, 5, 5);
		gbc_showingCountLabel.gridx = 1;
		gbc_showingCountLabel.gridy = 10;
		showingCountLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		add(showingCountLabel, gbc_showingCountLabel);

		GridBagConstraints gbc_btnPrevPage = new GridBagConstraints();
		gbc_btnPrevPage.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnPrevPage.insets = new Insets(0, 0, 5, 5);
		gbc_btnPrevPage.gridx = 12;
		gbc_btnPrevPage.gridy = 10;
		add(getBtnPrevPage(), gbc_btnPrevPage);

		GridBagConstraints gbc_btnNextPage = new GridBagConstraints();
		gbc_btnNextPage.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnNextPage.insets = new Insets(0, 0, 5, 5);
		gbc_btnNextPage.gridx = 13;
		gbc_btnNextPage.gridy = 10;
		add(getBtnNextPage(), gbc_btnNextPage);

		GridBagConstraints gbc_btnAddAUser = new GridBagConstraints();
		gbc_btnAddAUser.insets = new Insets(0, 0, 5, 5);
		gbc_btnAddAUser.gridx = 13;
		gbc_btnAddAUser.gridy = 3;
		add(getBtnAddUser(), gbc_btnAddAUser);

		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridheight = 5;
		gbc_scrollPane.gridwidth = 14;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 1;
		gbc_scrollPane.gridy = 5;
		add(scrollPane, gbc_scrollPane);
		scrollPane.setViewportView(userTable);
		
		matchComboBox.setFont(new Font("Tahoma", Font.BOLD, 11));
		matchComboBox.setForeground(Color.BLACK);
		matchComboBox.setBackground(new Color(245, 245, 245));
		matchComboBox.addActionListener(search);
		
		filterComboBox.setFont(new Font("Tahoma", Font.BOLD, 11));
		filterComboBox.setForeground(Color.BLACK);
		filterComboBox.setBackground(new Color(245, 245, 245));
		filterComboBox.addActionListener(search);
			
		btnNextPage.setEnabled(false);
		btnPrevPage.setEnabled(false);

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

			//if we try do delete our own admin account
			if(username.equals(facade.getProfile().getID())) {
				option = JOptionPane.showConfirmDialog(getParent(),"Deleting your account is not reversible and will result in a log out, are you sure you want to continue?","Confirm deletion",
													JOptionPane.WARNING_MESSAGE,JOptionPane.OK_CANCEL_OPTION);
				if(option==0) {
					facade.removeUser((String)userTable.getValueAt(userTable.getSelectedRow(), 0));
					System.gc();
					for (Window window : Window.getWindows()) {
					    window.dispose();
					}
					facade.logOut();;
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
