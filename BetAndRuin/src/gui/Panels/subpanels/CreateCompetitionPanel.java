package gui.Panels.subpanels;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import java.util.Map;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.DefaultComboBoxModel;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;


import businessLogic.BLFacade;
import domain.Competition;
import domain.Country;
import domain.Sport;
import gui.MainGUI;
import gui.components.ButtonColumn;
import gui.components.FancyButton;
import gui.components.HintTextField;

@SuppressWarnings("serial")
public class CreateCompetitionPanel extends JPanel {

	private  JComboBox<Sport> sportComboBox = new JComboBox<Sport>();
	private JScrollPane addedCompetitionScrollPane = new JScrollPane();
	private JTable addedCompetitions = new JTable();
	private JTable currentCompetitions = new JTable();
	private JTable countriesTable = new JTable();
	private JScrollPane currentCompetitionsScrollPane = new JScrollPane();
	private JScrollPane countries = new JScrollPane();
	private DefaultTableModel currentCompetitionsTableModel;
	private DefaultTableModel countryTableModel;
	private DefaultTableModel addedCompetitionTableModel;
	private JLabel  currentCompetitionLabel = new JLabel("Current Competitions");
	private JLabel countriesLabel = new JLabel("Countries");
	private JLabel enterCompetitionLabel = new JLabel("Enter competition name");
	private JLabel errorLabel = new JLabel();
	private HintTextField jHintFieldCompName = new HintTextField("Introduce competition name here", new Color(255, 255, 255), new Color(0,0,0), new Color(0,0,0), new Color(169,169,169),  new Color(169,169,169));
	private JButton jButtonCreate;
	private JButton jButtonAdd;
	private String[] columNameCurrentCompetitions = new String[] {	"No.", "Competition"};
	private String[] columNameAddedCompetitions = new String[] {"No.", "Competition","Country","Sport", ""};
	private String[] columcountries = new String[] {"", "Country"};
	private String selectedCountry;
	private Map<String, ArrayList<HashMap<Sport, ArrayList<String>>>> compMap = new HashMap<String, ArrayList<HashMap<Sport, ArrayList<String>>>>();
	/**
	 * Create the panel.
	 */
	public CreateCompetitionPanel() {
		//Bussiness Logic

		BLFacade facade = MainGUI.getBusinessLogic();


		setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		setBackground(Color.WHITE);
		GridBagLayout gridBagLayout = new GridBagLayout();

		setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		setBackground(Color.WHITE);
		gridBagLayout.columnWidths = new int[]{214, 154, 114, 214, 324};
		gridBagLayout.rowHeights = new int[]{32, 115,115, 55, 67, 68,95};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0, 1.0, 1.0,1.0 };
		gridBagLayout.rowWeights = new double[]{0.0, 1.0, 1.0, 1.0};
		setLayout(gridBagLayout);


		//added event scrollpane

		GridBagConstraints gbc_addedCompetitionScrollPane = new GridBagConstraints();
		gbc_addedCompetitionScrollPane.fill = GridBagConstraints.BOTH;
		gbc_addedCompetitionScrollPane.insets = new Insets(0, 25, 15, 0);
		gbc_addedCompetitionScrollPane.gridheight = 4;
		gbc_addedCompetitionScrollPane.gridwidth = 4;
		gbc_addedCompetitionScrollPane.gridx = 0;
		gbc_addedCompetitionScrollPane.gridy = 3;
		add(addedCompetitionScrollPane, gbc_addedCompetitionScrollPane);

		//create button

		jButtonCreate = new FancyButton(ResourceBundle.getBundle("Etiquetas").getString("CreateComp"),new Color(51,51,51),new Color(170,170,170),new Color(150,150,150));
		jButtonCreate.setForeground(Color.white);
		jButtonCreate.setFont(new Font("Source Sans Pro", Font.BOLD, 13));
		GridBagConstraints gbc_jbuttonCrate = new GridBagConstraints();
		gbc_jbuttonCrate.fill = GridBagConstraints.BOTH;
		gbc_jbuttonCrate.insets = new Insets(0, 25, 25, 25);
		gbc_jbuttonCrate.gridheight = 1;
		gbc_jbuttonCrate.gridwidth = 1;
		gbc_jbuttonCrate.gridx = 4;
		gbc_jbuttonCrate.gridy = 6;
		add(jButtonCreate, gbc_jbuttonCrate);

		//add button

		jButtonAdd = new FancyButton(ResourceBundle.getBundle("Etiquetas").getString("AddComp"),new Color(51,51,51),new Color(170,170,170),new Color(150,150,150));
		jButtonAdd.setForeground(Color.white);
		jButtonAdd.setFont(new Font("Source Sans Pro", Font.BOLD, 13));
		GridBagConstraints gbc_jButtonAdd = new GridBagConstraints();
		gbc_jButtonAdd.fill = GridBagConstraints.BOTH;
		gbc_jButtonAdd.insets = new Insets(0, 25, 25, 25);
		gbc_jButtonAdd.gridheight = 1;
		gbc_jButtonAdd.gridwidth = 1;
		gbc_jButtonAdd.gridx = 4;
		gbc_jButtonAdd.gridy = 5;
		add(jButtonAdd, gbc_jButtonAdd);

		//countries scroll pane

		GridBagConstraints gbc_countries = new GridBagConstraints();
		gbc_countries.fill = GridBagConstraints.BOTH;
		gbc_countries.insets = new Insets(0, 25, 25, 0);
		gbc_countries.gridheight = 2;
		gbc_countries.gridwidth = 2;
		gbc_countries.gridx = 0;
		gbc_countries.gridy = 1;
		add(countries, gbc_countries);

		//current competitions scroll pane 

		GridBagConstraints gbc_currentCompetitionsScrollPane = new GridBagConstraints();
		gbc_currentCompetitionsScrollPane.fill = GridBagConstraints.BOTH;
		gbc_currentCompetitionsScrollPane.insets = new Insets(0, 0, 25, 25);
		gbc_currentCompetitionsScrollPane.gridheight = 2;
		gbc_currentCompetitionsScrollPane.gridwidth = 1;
		gbc_currentCompetitionsScrollPane.gridx = 4;
		gbc_currentCompetitionsScrollPane.gridy = 1;
		add(currentCompetitionsScrollPane, gbc_currentCompetitionsScrollPane);

		//label for current competitions

		currentCompetitionLabel.setFont(new Font("Source Sans Pro", Font.BOLD, 16));
		currentCompetitionLabel.setBounds(new Rectangle(25, 211, 75, 20));
		GridBagConstraints gbc_currentCompetitionLabel = new GridBagConstraints();
		gbc_currentCompetitionLabel.anchor = GridBagConstraints.SOUTHWEST;
		gbc_currentCompetitionLabel.insets = new Insets(25, 0, 0, 0);
		gbc_currentCompetitionLabel.gridwidth = 1;
		gbc_currentCompetitionLabel.gridx = 4; 
		gbc_currentCompetitionLabel.gridy = 0;
		add(currentCompetitionLabel, gbc_currentCompetitionLabel);

		//label for countries

		countriesLabel.setFont(new Font("Source Sans Pro", Font.BOLD, 16));
		countriesLabel.setBounds(new Rectangle(25, 211, 75, 20));
		GridBagConstraints gbc_countriesLabel = new GridBagConstraints();
		gbc_countriesLabel.anchor = GridBagConstraints.SOUTHWEST;
		gbc_countriesLabel.insets = new Insets(25, 25, 0, 0);
		gbc_countriesLabel.gridwidth = 1;
		gbc_countriesLabel.gridx = 0; 
		gbc_countriesLabel.gridy = 0;
		add(countriesLabel, gbc_countriesLabel);

		//Label for entering the competition

		enterCompetitionLabel.setFont(new Font("Source Sans Pro", Font.BOLD, 16));
		enterCompetitionLabel.setBounds(new Rectangle(25, 211, 75, 20));
		GridBagConstraints gbc_enterCompetitionLabel = new GridBagConstraints();
		gbc_enterCompetitionLabel.anchor = GridBagConstraints.SOUTHWEST;
		gbc_enterCompetitionLabel.fill = GridBagConstraints.HORIZONTAL;
		gbc_enterCompetitionLabel.insets = new Insets(0, 25, 25, 25);
		gbc_enterCompetitionLabel.gridwidth = 1;
		gbc_enterCompetitionLabel.gridx = 4; 
		gbc_enterCompetitionLabel.gridy = 3;
		add(enterCompetitionLabel, gbc_enterCompetitionLabel);

		//Field for enterin the competition name

		GridBagConstraints gbc_jTextFieldQuery = new GridBagConstraints();
		gbc_jTextFieldQuery.anchor = GridBagConstraints.NORTH;
		gbc_jTextFieldQuery.gridwidth = 1;
		gbc_jTextFieldQuery.fill = GridBagConstraints.HORIZONTAL;
		gbc_jTextFieldQuery.insets = new Insets(0, 25, 25, 25);
		gbc_jTextFieldQuery.gridx = 4;
		gbc_jTextFieldQuery.gridy = 4;
		this.add(jHintFieldCompName, gbc_jTextFieldQuery);
		jHintFieldCompName.setBounds(new Rectangle(100, 211, 75, 33));
		jHintFieldCompName.setFont(new Font("Tahoma",Font.ITALIC,14));

		//table for added competitions

		addedCompetitionTableModel = new DefaultTableModel(null, columNameAddedCompetitions);
		resetAddedCompetitionTable();
		addedCompetitionScrollPane.setViewportView(addedCompetitions);
		addedCompetitionScrollPane.getViewport().setBackground(new Color(250,250,250));
		addedCompetitions.getTableHeader().setBackground(new Color(245,245,245));
		addedCompetitionScrollPane.setBackground(new Color(250,250,250));

		//table for current competitions

		currentCompetitionsTableModel = new DefaultTableModel(null, columNameCurrentCompetitions){
			boolean[] columnEditables = new boolean[] {
					false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		};
		currentCompetitions.setModel(currentCompetitionsTableModel);
		currentCompetitions.setFont(new Font("Source sans Pro", Font.BOLD, 14));
		currentCompetitionsScrollPane.setViewportView(currentCompetitions);
		currentCompetitions.getColumnModel().getColumn(0).setPreferredWidth(50);
		currentCompetitions.getColumnModel().getColumn(0).setMinWidth(50);
		currentCompetitions.getColumnModel().getColumn(0).setMaxWidth(50);
		currentCompetitions.getColumnModel().getColumn(1).setMinWidth(100);
		currentCompetitionsScrollPane.getViewport().setBackground(new Color(250,250,250));
		currentCompetitions.getTableHeader().setBackground(new Color(245,245,245));
		currentCompetitions.setBackground(new Color(250,250,250));
		currentCompetitions.getTableHeader().setFont(new Font("Source sans Pro", Font.BOLD, 16));

		//Sport Combo Box

		sportComboBox.setModel(new DefaultComboBoxModel<Sport>(Sport.values()));
		GridBagConstraints gbc_sportComboBox = new GridBagConstraints();
		gbc_sportComboBox.gridwidth = 2;
		gbc_sportComboBox.insets = new Insets(0, 0, 5, 5);
		gbc_sportComboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_sportComboBox.gridx = 2;
		gbc_sportComboBox.gridy = 1;
		add(sportComboBox, gbc_sportComboBox);
		sportComboBox.addItemListener(new ItemListener() {	
			@Override
			public void itemStateChanged(ItemEvent e) {
				Sport selectedsport = (Sport)sportComboBox.getSelectedItem();
				Vector<Competition> comp = facade.getCompetitions(selectedsport);
				int i = 1;
				boolean enter = false;
				for (Competition comps: comp) {	
					if (comps.getCountry().getString().equals(selectedCountry)) {
						System.out.println("hello");
						loadCompetitions(comps, i);
						i++;
						enter = true;
					}
				}
				if (!enter) {
					loadCompetitions(null, 0);
				}
			}

		});

		//competition panel

		countryTableModel = new DefaultTableModel(null, columcountries){
			boolean[] columnEditables = new boolean[] {
					false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}

			@SuppressWarnings({ "rawtypes", "unchecked" })
			public Class getColumnClass(int column)
			{
				return getValueAt(0, column).getClass();
			}
		};
		countriesTable.setModel(countryTableModel);
		countriesTable.setFont(new Font("Source sans Pro", Font.BOLD, 14));
		countries.setViewportView(countriesTable);
		countriesTable.getColumnModel().getColumn(0).setPreferredWidth(50);
		countriesTable.getColumnModel().getColumn(0).setMinWidth(50);
		countriesTable.getColumnModel().getColumn(0).setMaxWidth(50);
		countriesTable.getColumnModel().getColumn(1).setMinWidth(100);
		countriesTable.setRowHeight(80);
		countries.getViewport().setBackground(new Color(250,250,250));
		countriesTable.getTableHeader().setBackground(new Color(245,245,245));
		countriesTable.setBackground(new Color(250,250,250));
		countriesTable.getTableHeader().setFont(new Font("Source sans Pro", Font.BOLD, 16));

		Country[] contryList = Country.values();

		for (Country country : contryList) {
			Object row[] = new Object[2];
			ImageIcon icon = new ImageIcon("images/country/" + country.getString() +  ".png");
			row[0]= icon;
			row[1] = country.getString();
			countryTableModel.addRow(row);
		}

		//selection listener for listening to the selected contries

		ListSelectionModel cellSelectionModel = countriesTable.getSelectionModel();
		cellSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		cellSelectionModel.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				//String selectedData = null;
				int selectedRow = countriesTable.getSelectedRow();
				int selectedColumn = 1;

				selectedCountry = (String) countriesTable.getValueAt(selectedRow, selectedColumn);
				Vector<Competition> comp = facade.getCompetitions((Sport)sportComboBox.getSelectedItem());
				int i = 1;
				boolean enter = false;
				for (Competition comps: comp) {	
					if (comps.getCountry().getString().equals(selectedCountry)) {
						System.out.println("hello");
						loadCompetitions(comps, i);
						i++;
						enter = true;
					}
				}
				if (!enter) {
					loadCompetitions(null, 0);
				}
			}

		});

		//action listener for adding competitions
		jButtonAdd.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String comp = jHintFieldCompName.getText();
				Sport sport = (Sport)sportComboBox.getSelectedItem();
				errorLabel.setText("");
				if (comp.equals("") || sport == null || selectedCountry == null) {
					errorLabel.setText("Make sure everything is selected or set");
				} else {
					int err = addToMap(comp, sport);
					if (err == -1) {
						errorLabel.setText("Competition already added");
					} else {
						addedCompTableAdd(comp, selectedCountry, sport.name());
					}				
					jHintFieldCompName.setText("");
				}

			}
		});
		
		//error label
		
		
		errorLabel.setHorizontalAlignment(SwingConstants.CENTER);

		errorLabel.setBounds(new Rectangle(196, 322, 371, 30));
		errorLabel.setForeground(Color.red);
		GridBagConstraints gbc_errorLabel = new GridBagConstraints();
		gbc_errorLabel.fill = GridBagConstraints.HORIZONTAL;
		gbc_errorLabel.gridwidth = 2;
		gbc_errorLabel.anchor = GridBagConstraints.NORTH;
		gbc_errorLabel.insets = new Insets(0, 0, 5, 5);
		gbc_errorLabel.gridx = 2;
		gbc_errorLabel.gridy = 2;
		this.add(errorLabel, gbc_errorLabel);
		//action listener for create buttom
		
		jButtonCreate.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				BLFacade facade = MainGUI.getBusinessLogic();
				if (compMap.isEmpty()) {
					errorLabel.setText("Make sure to add some competitions");
				} else {
					for(String count: compMap.keySet()) {
						System.out.println(count);
						ArrayList<HashMap<Sport,ArrayList<String>>> innerMap = compMap.get(count);
						for (HashMap<Sport,ArrayList<String>> hashMap : innerMap) {
							for (Sport sport : hashMap.keySet()) {
								System.out.println(sport);
								ArrayList<String> list = hashMap.get(sport);
								for (String comp : list) {
									Date date = new Date(System.currentTimeMillis());
									System.out.println(comp);
									facade.createCompetition(count,sport,comp,date);
									resetAddedCompetitionTable();
								}
							}
						}
					}
					
				}
			}
		});

	}





	public void loadCompetitions(Competition c, int i) {
		currentCompetitionsTableModel.setDataVector(null, columNameCurrentCompetitions);
		currentCompetitions.getColumnModel().getColumn(0).setPreferredWidth(50);
		currentCompetitions.getColumnModel().getColumn(0).setMinWidth(50);
		currentCompetitions.getColumnModel().getColumn(0).setMaxWidth(50);
		currentCompetitions.getColumnModel().getColumn(1).setMinWidth(100);
		if(c != null) {
			Object[] row = new Object[2];
			row[0] = i;
			row[1] = c.getName();
			currentCompetitionsTableModel.addRow(row);
		}	
	}


	public void addedCompTableAdd(String competition, String sport, String Country) {

		Object[] row = new Object[5];
		row[0] = addedCompetitionTableModel.getRowCount()+1;
		row[1] = competition;
		row[2] = sport;
		row[3] = Country;
		row[4] = new ImageIcon("images/delete.png");

		addedCompetitionTableModel.addRow(row);
	}

	public int addToMap(String comp, Sport sport) {
		// TODO Auto-generated method stub
		ArrayList<HashMap<Sport, ArrayList<String>>> compList = compMap.get(selectedCountry);
		
		if(compList == null) {
			compList = new ArrayList<HashMap<Sport,ArrayList<String>>>();
	        HashMap<Sport,ArrayList<String>> map = new HashMap<Sport,ArrayList<String>>();
	        ArrayList<String> competitions = new ArrayList<String>();
	        competitions.add(comp);
	        map.put(sport,competitions);
	        compList.add(map);
			compMap.put(selectedCountry, compList);
		}else {
			for (HashMap<Sport, ArrayList<String>> hashMap : compList) {
				HashMap<Sport, ArrayList<String>> map = hashMap;
				ArrayList<HashMap<Sport,ArrayList<String>>> list = new ArrayList<HashMap<Sport,ArrayList<String>>>();
				ArrayList<String> competitions = map.get(sport);
				if (competitions == null) {
					competitions = new ArrayList<String>();
					competitions.add(comp);		
					map.put(sport, competitions);
					list.add(map);
					compMap.put(selectedCountry,list);
				} else {
					if (competitions.contains(comp)) {
						return -1;
					} else {
						competitions.add(comp);
						map.put(sport, competitions);
						list.add(map);
						compMap.put(selectedCountry,list);
					}
				}	
			}

		}
		return 0;
		
	}
	private void resetAddedCompetitionTable() {
		// TODO Auto-generated method stub
		addedCompetitionTableModel.setDataVector(null, columNameAddedCompetitions);
		addedCompetitionTableModel.setColumnCount(5); 
		addedCompetitions.setRowHeight(28);
		addedCompetitions.setModel(addedCompetitionTableModel);
		addedCompetitions.getColumnModel().getColumn(0).setPreferredWidth(60);
		addedCompetitions.getColumnModel().getColumn(0).setMinWidth(60);
		addedCompetitions.getColumnModel().getColumn(0).setMaxWidth(60);
		addedCompetitions.getColumnModel().getColumn(1).setPreferredWidth(280);
		addedCompetitions.getColumnModel().getColumn(1).setMinWidth(280);
		addedCompetitions.getColumnModel().getColumn(1).setMaxWidth(280);
		addedCompetitions.getColumnModel().getColumn(2).setPreferredWidth(120);
		addedCompetitions.getColumnModel().getColumn(2).setMinWidth(120);
		addedCompetitions.getColumnModel().getColumn(2).setMaxWidth(120);
		addedCompetitions.getColumnModel().getColumn(3).setPreferredWidth(120);
		addedCompetitions.getColumnModel().getColumn(3).setMinWidth(120);
		addedCompetitions.getColumnModel().getColumn(3).setMaxWidth(120);
		addedCompetitions.getColumnModel().getColumn(4).setPreferredWidth(80);
		addedCompetitions.getColumnModel().getColumn(4).setMinWidth(80);
		addedCompetitions.getColumnModel().getColumn(4).setMaxWidth(80);
		addedCompetitions.getTableHeader().setFont(new Font("Source sans Pro", Font.BOLD, 16));
		addedCompetitions.setFont(new Font("Source sans Pro", Font.BOLD, 14));
		@SuppressWarnings("unused")
		ButtonColumn deleteButtonColumn = new ButtonColumn(addedCompetitions, delete, 4, new Color(255,0,51));
	}

	Action delete = new AbstractAction() {
		public void actionPerformed(ActionEvent e) {
			int row = addedCompetitions.getSelectedRow();
			addedCompetitionTableModel.removeRow(row);
		}
	};


}
