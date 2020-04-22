package gui;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import businessLogic.BLFacade;
import domain.Country;
import exceptions.invalidID;
import gui.components.FancyButton;

import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;

public class EditUserGUI extends JDialog {
	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	
	private JLabel lblCountry;
	private JLabel lblPhoneNumber = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("PhoneNumber") + ":"); //$NON-NLS-1$ //$NON-NLS-2$
	private JLabel lblBirthdate = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Birthdate") + ":"); //$NON-NLS-1$ //$NON-NLS-2$
	private JLabel lblCity = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("City") + ":"); //$NON-NLS-1$ //$NON-NLS-2$
	
	private JTextField usernameField;
	private JTextField nameField;
	private JTextField surnameField;
	private JTextField emailField;

	private JRadioButton rdbtnUser = new JRadioButton("User");
	private JRadioButton rdbtnAdmin = new JRadioButton("Admin");
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JTextField phnTextField;
	private JTextField phnPrefixTextField;
	private JTextField cityTextField;
	
	
	private JComboBox<String> comboBoxNat = new JComboBox<String>();
	private JComboBox<String> comboBoxDay = new JComboBox<String>();
	private JComboBox<String> comboBoxMonth = new JComboBox<String>();
	private JComboBox<String> comboBoxYear = new JComboBox<String>();
	private JTextField addressTextField;
	private JTextField IDField;

	/**
	 * Create the frame.
	 */
	public EditUserGUI(String username,String id, String name, String surname, String email, String country, String city,String address, String phonenumber, String birthdate, String status) {
		setTitle("Edit user information");
		setResizable(false);
		
		if(status.equals("Admin.")) {
			rdbtnAdmin.setSelected(true);
		}
		else {
			rdbtnUser.setSelected(true);
		}
		
		usernameField = new JTextField(username);
		usernameField.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.WHITE));
		usernameField.setForeground(new Color(255,255, 255));
		usernameField.setCaretColor(new Color(255,255,255));	
		usernameField.setOpaque(false);
		
		IDField = new JTextField(id);
		IDField.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.WHITE));
		IDField.setForeground(new Color(255,255, 255));
		IDField.setCaretColor(new Color(255,255, 255));
		IDField.setOpaque(false);
		
		nameField = new JTextField(name);
		nameField.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.WHITE));
		nameField.setForeground(new Color(255,255, 255));
		nameField.setCaretColor(new Color(255,255,255));	
		nameField.setOpaque(false);
		
		surnameField = new JTextField(surname);
		surnameField.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.WHITE));
		surnameField.setForeground(new Color(255,255, 255));
		surnameField.setCaretColor(new Color(255,255,255));	
		surnameField.setOpaque(false);
		
		emailField = new JTextField(email);
		emailField.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.WHITE));
		emailField.setForeground(new Color(255,255, 255));
		emailField.setCaretColor(new Color(255,255,255));	
		emailField.setOpaque(false);
		
		cityTextField = new JTextField(city);
		cityTextField.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.WHITE));
		cityTextField.setForeground(new Color(255,255, 255));
		cityTextField.setCaretColor(new Color(255,255,255));	
		cityTextField.setOpaque(false);
		
		addressTextField = new JTextField(address);
		addressTextField.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.WHITE));
		addressTextField.setForeground(new Color(255,255, 255));
		addressTextField.setCaretColor(new Color(255,255,255));	
		addressTextField.setOpaque(false);
		
		String[] phone = phonenumber.split(" ");
		
		phnPrefixTextField = new JTextField(phone[0]);
		phnPrefixTextField.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.WHITE));
		phnPrefixTextField.setForeground(new Color(255,255, 255));
		phnPrefixTextField.setCaretColor(new Color(255,255,255));	
		phnPrefixTextField.setOpaque(false);
		
		phnTextField = new JTextField(phone[1]);
		phnTextField.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.WHITE));
		phnTextField.setForeground(new Color(255,255, 255));
		phnTextField.setCaretColor(new Color(255,255,255));	
		phnTextField.setOpaque(false);
		
		
		
		setModal(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 482, 509);
		contentPane = new JPanel();
		contentPane.setBorder(null);
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.BLACK);
		contentPane.add(panel, BorderLayout.CENTER);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{30, 20, 20, 15, 93, -100, 24, 40, 40, 50, 30, 20, 0};
		gbl_panel.rowHeights = new int[]{30, 0, 30, 40, 30, 40, 40, 40, 0, 0, 7, 0, 0, 0, 20, 0, 30, 0};
		gbl_panel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		JLabel lblEditUserInformation = new JLabel("Edit user information");
		lblEditUserInformation.setForeground(Color.WHITE);
		lblEditUserInformation.setFont(new Font("Source Code Pro Light", Font.BOLD, 24));
		GridBagConstraints gbc_lblEditUserInformation = new GridBagConstraints();
		gbc_lblEditUserInformation.anchor = GridBagConstraints.WEST;
		gbc_lblEditUserInformation.gridwidth = 11;
		gbc_lblEditUserInformation.insets = new Insets(0, 0, 5, 0);
		gbc_lblEditUserInformation.gridx = 1;
		gbc_lblEditUserInformation.gridy = 1;
		panel.add(lblEditUserInformation, gbc_lblEditUserInformation);
		
		JLabel lblUsername = new JLabel("Username:");
		lblUsername.setForeground(Color.WHITE);
		lblUsername.setFont(new Font("Source Code Pro Light", Font.PLAIN, 11));
		GridBagConstraints gbc_lblUsername = new GridBagConstraints();
		gbc_lblUsername.gridwidth = 3;
		gbc_lblUsername.anchor = GridBagConstraints.EAST;
		gbc_lblUsername.insets = new Insets(0, 0, 5, 5);
		gbc_lblUsername.gridx = 1;
		gbc_lblUsername.gridy = 3;
		panel.add(lblUsername, gbc_lblUsername);
		

		GridBagConstraints gbc_idField = new GridBagConstraints();
		gbc_idField.gridwidth = 5;
		gbc_idField.insets = new Insets(0, 0, 5, 5);
		gbc_idField.fill = GridBagConstraints.HORIZONTAL;
		gbc_idField.gridx = 4;
		gbc_idField.gridy = 3;
		panel.add(usernameField, gbc_idField);
		usernameField.setColumns(10);
		
		JLabel usernameError = new JLabel("ID already in use");
		usernameError.setForeground(Color.RED);
		GridBagConstraints gbc_usernameError = new GridBagConstraints();
		gbc_usernameError.anchor = GridBagConstraints.WEST;
		gbc_usernameError.gridwidth = 3;
		gbc_usernameError.insets = new Insets(0, 0, 5, 0);
		gbc_usernameError.gridx = 9;
		gbc_usernameError.gridy = 3;
		panel.add(usernameError, gbc_usernameError);
		usernameError.setVisible(false);;
		
		JLabel lblIDnumber = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("EditUserGUI.lblIDnumber.text")); //$NON-NLS-1$ //$NON-NLS-2$
		lblIDnumber.setForeground(Color.WHITE);
		lblIDnumber.setFont(new Font("Source Code Pro Light", Font.PLAIN, 11));
		GridBagConstraints gbc_lblIDnumber = new GridBagConstraints();
		gbc_lblIDnumber.gridwidth = 3;
		gbc_lblIDnumber.insets = new Insets(0, 0, 5, 5);
		gbc_lblIDnumber.gridx = 1;
		gbc_lblIDnumber.gridy = 4;
		panel.add(lblIDnumber, gbc_lblIDnumber);
		
		
		GridBagConstraints gbc_IDTextField = new GridBagConstraints();
		gbc_IDTextField.gridwidth = 5;
		gbc_IDTextField.insets = new Insets(0, 0, 5, 5);
		gbc_IDTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_IDTextField.gridx = 4;
		gbc_IDTextField.gridy = 4;
		panel.add(IDField, gbc_IDTextField);
		
		JLabel lblName = new JLabel("Name:");
		lblName.setForeground(Color.WHITE);
		lblName.setFont(new Font("Source Code Pro Light", Font.PLAIN, 11));
		GridBagConstraints gbc_lblName = new GridBagConstraints();
		gbc_lblName.gridwidth = 3;
		gbc_lblName.anchor = GridBagConstraints.EAST;
		gbc_lblName.insets = new Insets(0, 0, 5, 5);
		gbc_lblName.gridx = 1;
		gbc_lblName.gridy = 5;
		panel.add(lblName, gbc_lblName);
		
		
		GridBagConstraints gbc_nameField = new GridBagConstraints();
		gbc_nameField.gridwidth = 5;
		gbc_nameField.insets = new Insets(0, 0, 5, 5);
		gbc_nameField.fill = GridBagConstraints.HORIZONTAL;
		gbc_nameField.gridx = 4;
		gbc_nameField.gridy = 5;
		panel.add(nameField, gbc_nameField);
		nameField.setColumns(10);
		
		JLabel lblSurname = new JLabel("Surname:");
		lblSurname.setForeground(Color.WHITE);
		lblSurname.setFont(new Font("Source Code Pro Light", Font.PLAIN, 11));
		GridBagConstraints gbc_lblSurname = new GridBagConstraints();
		gbc_lblSurname.gridwidth = 3;
		gbc_lblSurname.anchor = GridBagConstraints.EAST;
		gbc_lblSurname.insets = new Insets(0, 0, 5, 5);
		gbc_lblSurname.gridx = 1;
		gbc_lblSurname.gridy = 6;
		panel.add(lblSurname, gbc_lblSurname);
		
	
		surnameField.setColumns(10);
		GridBagConstraints gbc_surnameField = new GridBagConstraints();
		gbc_surnameField.gridwidth = 5;
		gbc_surnameField.insets = new Insets(0, 0, 5, 5);
		gbc_surnameField.fill = GridBagConstraints.HORIZONTAL;
		gbc_surnameField.gridx = 4;
		gbc_surnameField.gridy = 6;
		panel.add(surnameField, gbc_surnameField);
		
		JLabel lblEmail = new JLabel("E-mail:");
		lblEmail.setForeground(Color.WHITE);
		lblEmail.setFont(new Font("Source Code Pro Light", Font.PLAIN, 11));
		GridBagConstraints gbc_lblEmail = new GridBagConstraints();
		gbc_lblEmail.gridwidth = 3;
		gbc_lblEmail.anchor = GridBagConstraints.EAST;
		gbc_lblEmail.insets = new Insets(0, 0, 5, 5);
		gbc_lblEmail.gridx = 1;
		gbc_lblEmail.gridy = 7;
		panel.add(lblEmail, gbc_lblEmail);
		

		emailField.setColumns(10);
		GridBagConstraints gbc_emailField = new GridBagConstraints();
		gbc_emailField.gridwidth = 6;
		gbc_emailField.insets = new Insets(0, 0, 5, 5);
		gbc_emailField.fill = GridBagConstraints.HORIZONTAL;
		gbc_emailField.gridx = 4;
		gbc_emailField.gridy = 7;
		panel.add(emailField, gbc_emailField);
		
		lblCountry = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Country") + ":");
		lblCountry.setForeground(Color.WHITE);
		lblCountry.setFont(new Font("Source Code Pro Light", Font.PLAIN, 11));
		GridBagConstraints gbc_lblCountry = new GridBagConstraints();
		gbc_lblCountry.anchor = GridBagConstraints.EAST;
		gbc_lblCountry.gridwidth = 3;
		gbc_lblCountry.insets = new Insets(0, 0, 5, 5);
		gbc_lblCountry.gridx = 1;
		gbc_lblCountry.gridy = 8;
		panel.add(lblCountry, gbc_lblCountry);
		
		GridBagConstraints gbc_comboBoxNat = new GridBagConstraints();
		gbc_comboBoxNat.gridwidth = 5;
		gbc_comboBoxNat.insets = new Insets(0, 0, 5, 5);
		gbc_comboBoxNat.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBoxNat.gridx = 4;
		gbc_comboBoxNat.gridy = 8;
		panel.add(comboBoxNat, gbc_comboBoxNat);
		

		GridBagConstraints gbc_lblCity = new GridBagConstraints();
		gbc_lblCity.anchor = GridBagConstraints.EAST;
		gbc_lblCity.gridwidth = 3;
		gbc_lblCity.insets = new Insets(0, 0, 5, 5);
		gbc_lblCity.gridx = 1;
		gbc_lblCity.gridy = 9;
		lblCity.setForeground(Color.WHITE);
		lblCity.setFont(new Font("Source Code Pro Light", Font.PLAIN, 11));
		panel.add(lblCity, gbc_lblCity);
		
		GridBagConstraints gbc_cityTextField = new GridBagConstraints();
		gbc_cityTextField.gridwidth = 5;
		gbc_cityTextField.insets = new Insets(0, 0, 5, 5);
		gbc_cityTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_cityTextField.gridx = 4;
		gbc_cityTextField.gridy = 9;
		panel.add(cityTextField, gbc_cityTextField);
		cityTextField.setColumns(10);
		
		JLabel lblAddress = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Address"));
		lblAddress.setForeground(Color.WHITE);
		lblAddress.setFont(new Font("Source Code Pro Light", Font.PLAIN, 11));
		GridBagConstraints gbc_lblAddress = new GridBagConstraints();
		gbc_lblAddress.gridwidth = 3;
		gbc_lblAddress.anchor = GridBagConstraints.EAST;
		gbc_lblAddress.insets = new Insets(0, 0, 5, 5);
		gbc_lblAddress.gridx = 1;
		gbc_lblAddress.gridy = 10;
		panel.add(lblAddress, gbc_lblAddress);
		
		GridBagConstraints gbc_addressTextField = new GridBagConstraints();
		gbc_addressTextField.gridwidth = 5;
		gbc_addressTextField.insets = new Insets(0, 0, 5, 5);
		gbc_addressTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_addressTextField.gridx = 4;
		gbc_addressTextField.gridy = 10;
		panel.add(addressTextField, gbc_addressTextField);
		addressTextField.setColumns(10);
		
		
		GridBagConstraints gbc_lblPhoneNumber = new GridBagConstraints();
		gbc_lblPhoneNumber.gridwidth = 3;
		gbc_lblPhoneNumber.insets = new Insets(0, 0, 5, 5);
		gbc_lblPhoneNumber.gridx = 1;
		gbc_lblPhoneNumber.gridy = 11;
		lblPhoneNumber.setForeground(Color.WHITE);
		lblPhoneNumber.setFont(new Font("Source Code Pro Light", Font.PLAIN, 11));
		panel.add(lblPhoneNumber, gbc_lblPhoneNumber);
		
		GridBagConstraints gbc_phnPrefixTextField = new GridBagConstraints();
		gbc_phnPrefixTextField.insets = new Insets(0, 0, 5, 5);
		gbc_phnPrefixTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_phnPrefixTextField.gridx = 4;
		gbc_phnPrefixTextField.gridy = 11;
		panel.add(phnPrefixTextField, gbc_phnPrefixTextField);
		phnPrefixTextField.setColumns(10);
		
		GridBagConstraints gbc_phnTextField = new GridBagConstraints();
		gbc_phnTextField.gridwidth = 4;
		gbc_phnTextField.insets = new Insets(0, 0, 5, 5);
		gbc_phnTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_phnTextField.gridx = 5;
		gbc_phnTextField.gridy = 11;
		panel.add(phnTextField, gbc_phnTextField);
		phnTextField.setColumns(10);
		
		
		GridBagConstraints gbc_lblBirthdate = new GridBagConstraints();
		gbc_lblBirthdate.anchor = GridBagConstraints.EAST;
		gbc_lblBirthdate.gridwidth = 3;
		gbc_lblBirthdate.insets = new Insets(0, 0, 5, 5);
		gbc_lblBirthdate.gridx = 1;
		gbc_lblBirthdate.gridy = 12;
		lblBirthdate.setForeground(Color.WHITE);
		lblBirthdate.setFont(new Font("Source Code Pro Light", Font.PLAIN, 11));
		panel.add(lblBirthdate, gbc_lblBirthdate);
		

		GridBagConstraints gbc_comboBoxDay = new GridBagConstraints();
		gbc_comboBoxDay.insets = new Insets(0, 0, 5, 5);
		gbc_comboBoxDay.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBoxDay.gridx = 4;
		gbc_comboBoxDay.gridy = 12;
		panel.add(comboBoxDay, gbc_comboBoxDay);
		
		GridBagConstraints gbc_comboBoxMonth = new GridBagConstraints();
		gbc_comboBoxMonth.insets = new Insets(0, 0, 5, 5);
		gbc_comboBoxMonth.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBoxMonth.gridx = 5;
		gbc_comboBoxMonth.gridy = 12;
		panel.add(comboBoxMonth, gbc_comboBoxMonth);
		
		GridBagConstraints gbc_comboBoxYear = new GridBagConstraints();
		gbc_comboBoxYear.gridwidth = 3;
		gbc_comboBoxYear.insets = new Insets(0, 0, 5, 5);
		gbc_comboBoxYear.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBoxYear.gridx = 6;
		gbc_comboBoxYear.gridy = 12;
		panel.add(comboBoxYear, gbc_comboBoxYear);
		
		JLabel lblRights = new JLabel("Rights:");
		lblRights.setForeground(Color.WHITE);
		lblRights.setFont(new Font("Source Code Pro Light", Font.PLAIN, 11));
		GridBagConstraints gbc_lblRights = new GridBagConstraints();
		gbc_lblRights.gridwidth = 3;
		gbc_lblRights.anchor = GridBagConstraints.EAST;
		gbc_lblRights.insets = new Insets(0, 0, 5, 5);
		gbc_lblRights.gridx = 1;
		gbc_lblRights.gridy = 13;
		panel.add(lblRights, gbc_lblRights);
		
		
		GridBagConstraints gbc_rdbtnUser = new GridBagConstraints();
		gbc_rdbtnUser.gridwidth = 2;
		gbc_rdbtnUser.insets = new Insets(0, 0, 5, 5);
		gbc_rdbtnUser.gridx = 4;
		gbc_rdbtnUser.gridy = 13;
		buttonGroup.add(rdbtnUser);
		rdbtnUser.setForeground(Color.WHITE);
		rdbtnUser.setOpaque(false);
		rdbtnUser.setFont(new Font("Source Code Pro", Font.PLAIN, 11));
		panel.add(rdbtnUser, gbc_rdbtnUser);
		

		GridBagConstraints gbc_rdbtnAdmin = new GridBagConstraints();
		gbc_rdbtnAdmin.gridwidth = 3;
		gbc_rdbtnAdmin.insets = new Insets(0, 0, 5, 5);
		gbc_rdbtnAdmin.gridx = 6;
		gbc_rdbtnAdmin.gridy = 13;
		buttonGroup.add(rdbtnAdmin);
		rdbtnAdmin.setForeground(Color.WHITE);
		rdbtnAdmin.setOpaque(false);
		rdbtnAdmin.setFont(new Font("Source Code Pro", Font.PLAIN, 11));
		panel.add(rdbtnAdmin, gbc_rdbtnAdmin);

		
		FancyButton btnSave = new FancyButton("Save changes", new Color(130,130,130),new Color(90,90,90),new Color(123,123,123));
		btnSave.setBorderPainted(false);
		btnSave.setFocusPainted(false);
		btnSave.setFont(new Font("Source Code Pro ExtraLight", Font.BOLD, 16));
		btnSave.setForeground(Color.WHITE);
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				usernameError.setVisible(false);
				try {
					
					Calendar cld =  Calendar.getInstance();
					
					String newUsername = usernameField.getText();
					String newName = nameField.getText();
					String newSurname = surnameField.getText();
					String newEmail = emailField.getText();
					
					Country newnat = Country.values()[comboBoxNat.getSelectedIndex()];
					String city = cityTextField.getText();
					String address = addressTextField.getText();
					String newphn = phnPrefixTextField.getText() + " " + phnTextField.getText();
					
					int day = comboBoxDay.getSelectedIndex();
					int month = comboBoxMonth.getSelectedIndex();
					int year = comboBoxYear.getSelectedIndex() + 1899;
	
					cld.set(Calendar.YEAR, year);
					cld.set(Calendar.MONTH, month-1);
					cld.set(Calendar.DAY_OF_MONTH, day);
					cld.set(Calendar.HOUR_OF_DAY, 0);
					cld.set(Calendar.MINUTE, 0);
					cld.set(Calendar.SECOND, 0);
					cld.set(Calendar.MILLISECOND, 0);
					Date newbirthdate = cld.getTime();
			
					boolean isAdmin;
					if(rdbtnAdmin.isSelected()) {
						isAdmin = true;
					}
					else {
						isAdmin = false;
					}	
					
					BLFacade f = MainGUI.getBusinessLogic();
					f.updateUserInfo(username, newUsername, newName, newSurname, newEmail,newnat, city,address,newphn,newbirthdate,isAdmin);
					dispose();
				}
				catch(invalidID i) {
					usernameError.setVisible(true);
				}
			}
		});
		GridBagConstraints gbc_btnSave = new GridBagConstraints();
		gbc_btnSave.gridwidth = 3;
		gbc_btnSave.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnSave.insets = new Insets(0, 0, 5, 5);
		gbc_btnSave.gridx = 2;
		gbc_btnSave.gridy = 15;
		panel.add(btnSave, gbc_btnSave);
		
		FancyButton btnCancel = new FancyButton("Cancel", new Color(130,130,130),new Color(90,90,90),new Color(123,123,123));
		btnCancel.setBorderPainted(false);
		btnCancel.setFocusPainted(false);
		btnCancel.setFont(new Font("Source Code Pro ExtraLight", Font.BOLD, 16));
		btnCancel.setForeground(Color.WHITE);
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				usernameField.setText(username);
				dispose();
			}
		});
		GridBagConstraints gbc_btnCancel = new GridBagConstraints();
		gbc_btnCancel.anchor = GridBagConstraints.NORTH;
		gbc_btnCancel.gridwidth = 4;
		gbc_btnCancel.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnCancel.insets = new Insets(0, 0, 5, 5);
		gbc_btnCancel.gridx = 6;
		gbc_btnCancel.gridy = 15;
		panel.add(btnCancel, gbc_btnCancel);
		
		
		String[] days = new String[32];;
		days[0] = "Day";
		for(Integer i=1; i<32;i++) {
			days[i]=i.toString();
		}

		String[] months = new String[13];
		months[0] = "Month";
		for(Integer i=1; i<13; i++) {
			months[i]=i.toString();
		}

		Calendar now = Calendar.getInstance();
		int currentyear = now.get(Calendar.YEAR);
		String[] years = new String[currentyear-1898];
		years[0] = "Year";
		for(Integer i=1900; i<=currentyear; i++) {
			years[i-1899]=i.toString();
		}
		
		String[] splitbday = birthdate.split("/");
		
		comboBoxNat.setModel(new DefaultComboBoxModel<String>(Country.namesArray()));
		comboBoxNat.setSelectedIndex(Country.getValue(country).ordinal());
		comboBoxNat.setForeground(Color.WHITE);
		comboBoxNat.setBackground(Color.GRAY);
		comboBoxDay.setModel(new DefaultComboBoxModel<String>(days));
		comboBoxDay.setSelectedItem(splitbday[0].replaceAll("^0+", ""));
		comboBoxDay.setForeground(Color.WHITE);
		comboBoxDay.setBackground(Color.GRAY);
		comboBoxMonth.setModel(new DefaultComboBoxModel<String>(months));
		comboBoxMonth.setSelectedItem(splitbday[1].replaceAll("^0+", ""));
		comboBoxMonth.setForeground(Color.WHITE);
		comboBoxMonth.setBackground(Color.GRAY);
		comboBoxYear.setModel(new DefaultComboBoxModel<String>(years));
		comboBoxYear.setSelectedItem(splitbday[2]);
		comboBoxYear.setForeground(Color.WHITE);
		comboBoxYear.setBackground(Color.GRAY);
	}
	public String[] newData() {
		String status;
		if(rdbtnAdmin.isSelected()) {
			status = "Admin.";
		}
		else {
			status = "User";
		}
		String day = (String)comboBoxDay.getSelectedItem();
		String month = (String)comboBoxMonth.getSelectedItem();
		String year = (String)comboBoxYear.getSelectedItem();
		if(day.length() == 1) {
			day = "0" + day;
		}
		if(month.length() == 1) {
			month = "0" + month;
		}
		String date = day + "/" + month + "/" +year;
		
		String[] s = {usernameField.getText(),IDField.getText(), nameField.getText(), surnameField.getText(), emailField.getText(),
					Country.values()[comboBoxNat.getSelectedIndex()].getString()  ,cityTextField.getText(),addressTextField.getText(), phnPrefixTextField.getText() +" "+ phnTextField.getText(),date ,status};
		return s;
	}
}
