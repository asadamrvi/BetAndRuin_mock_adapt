package gui.Panels.subpanels;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import businessLogic.BLFacade;
import domain.Country;
import domain.Profile;
import exceptions.invalidID;
import gui.MainGUI;
import gui.components.FancyButton;
import net.miginfocom.swing.MigLayout; 

@SuppressWarnings("serial")
public class PersonalInfoPanel extends JPanel{
	
	private JLabel saveChangesErrorLabel;
	
	private JTextField usernameTextField;
	private JTextField nameTextField;
	private JTextField surnameTextField;
	private JTextField emailTextField;
	private JTextField prefixTextField;
	private JTextField phnumberTextField;
	private JTextField cityTextField;
	private JTextField addressTextField;
	
	private JTextField IDNumberTextField;
	private JTextField birthdateTextField;
	
	private FancyButton saveProfileButton;
	
	public PersonalInfoPanel() {
		
		BLFacade facade = MainGUI.getBusinessLogic();
		Profile p = facade.getProfile();
		
		setBackground(new Color(240, 248, 255));
		setLayout(new MigLayout("", "[25:25:25][grow][][117.00,grow][32.00][67.00,grow][49.00][45.00][51.00,grow][25:25:25]", "[6:6:6][][grow][:20:50,grow][13.00][:20:50,grow][15:15:15,grow][grow][15:31.00:15,grow][45:45:45][16:16:16][][24.00,grow][15:15:15,grow][grow][15:15:15,grow][grow][15:15:15,grow][grow][15:15:15,grow][grow][84.00:84.00:84.00,growprio 10][20:20:20][9:9:9]"));

		JLabel identityLabel = new JLabel(" Identity\r\n");
		identityLabel.setOpaque(true);
		identityLabel.setBackground(new Color(244, 164, 96));
		identityLabel.setForeground(Color.WHITE);
		identityLabel.setFont(new Font("Source Code Pro Medium", Font.BOLD | Font.ITALIC, 22));
		add(identityLabel, "cell 1 1 8 1,grow");

		JPanel identityPanel = new JPanel();
		identityPanel.setBorder(null);
		identityPanel.setBackground(new Color(255, 222, 173));
		add(identityPanel, "cell 1 2 8 8,grow");
		identityPanel.setLayout(new MigLayout("", "[25:25:25][165px][279px,grow][20:20:20]", "[5:5:28,grow][10.00px][5:5:28,grow][23px][5:5:28,grow][23px][5:5:28,grow][23px][5:5:28,grow]"));
		
		JLabel IDNumberLabel = new JLabel("ID Card number:\r\n");
		identityPanel.add(IDNumberLabel, "cell 1 1,alignx left,aligny top");
		IDNumberLabel.setFont(new Font("Source Code Pro", Font.BOLD, 18));

		IDNumberTextField = new JTextField(p.getID());
		IDNumberTextField.setEditable(false);
		IDNumberTextField.setBackground(new Color(245, 245, 245));
		identityPanel.add(IDNumberTextField, "cell 2 1,grow");
		IDNumberTextField.setFont(new Font("Tahoma", Font.PLAIN, 15));
		IDNumberTextField.setColumns(10);
		IDNumberTextField.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));

		JLabel lblName = new JLabel("Name:");
		identityPanel.add(lblName, "cell 1 3,alignx left,aligny top");
		lblName.setFont(new Font("Source Code Pro", Font.BOLD, 18));

		nameTextField = new JTextField(p.getName());
		nameTextField.setEditable(false);
		identityPanel.add(nameTextField, "cell 2 3,grow");
		nameTextField.setFont(new Font("Tahoma", Font.PLAIN, 15));
		nameTextField.setColumns(10);
		nameTextField.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));

		JLabel lblSurname = new JLabel("Surname:\r\n");
		identityPanel.add(lblSurname, "cell 1 5,alignx left,aligny top");
		lblSurname.setFont(new Font("Source Code Pro", Font.BOLD, 18));

		surnameTextField = new JTextField(p.getSurname());
		surnameTextField.setEditable(false);
		identityPanel.add(surnameTextField, "cell 2 5,grow");
		surnameTextField.setFont(new Font("Tahoma", Font.PLAIN, 15));
		surnameTextField.setColumns(10);
		surnameTextField.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));

		JLabel lblBirthdate = new JLabel("Birthdate:\r\n");
		identityPanel.add(lblBirthdate, "cell 1 7,alignx left,aligny top");
		lblBirthdate.setFont(new Font("Source Code Pro", Font.BOLD, 18));
		
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		
		birthdateTextField = new JTextField(df.format(p.getBirthdate()));
		birthdateTextField.setFont(new Font("Tahoma", Font.PLAIN, 15));
		birthdateTextField.setEditable(false);
		birthdateTextField.setColumns(10);
		birthdateTextField.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
		identityPanel.add(birthdateTextField, "cell 2 7,growx");

		JLabel lblContactBilling = new JLabel(" Contact & Billing information");
		lblContactBilling.setOpaque(true);
		lblContactBilling.setForeground(Color.WHITE);
		lblContactBilling.setFont(new Font("Source Code Pro Medium", Font.BOLD | Font.ITALIC, 22));
		lblContactBilling.setBackground(new Color(100, 149, 237));
		add(lblContactBilling, "cell 1 11 8 1,growx");
		
		JPanel contactBillingPanel = new JPanel();
		contactBillingPanel.setBackground(new Color(172, 218, 255));
		add(contactBillingPanel, "cell 1 12 8 11,grow");
		contactBillingPanel.setLayout(new MigLayout("", "[20:20:20][][6.00px][57.00][20:20:20,grow][][40.00,grow][][40.00px][20:20:20]", "[5:5:28,grow][][5:5:28,grow][23px][5:5:28,grow][][5:5:28,grow][][5:5:28,grow][][5:5:28,grow][][5:5:28,grow][20:20:20][][10:10:10]"));

		JLabel lblUsername = new JLabel("Username:\r\n");
		contactBillingPanel.add(lblUsername, "cell 1 1");
		lblUsername.setFont(new Font("Source Code Pro", Font.BOLD, 18));

		usernameTextField = new JTextField(facade.getUsername());
		contactBillingPanel.add(usernameTextField, "cell 2 1 7 1,grow");
		usernameTextField.setFont(new Font("Tahoma", Font.PLAIN, 15));
		usernameTextField.setColumns(10);
		usernameTextField.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));

		JLabel lblEmail = new JLabel("Email:\r\n");
		contactBillingPanel.add(lblEmail, "cell 1 3,alignx left,aligny top");
		lblEmail.setFont(new Font("Source Code Pro", Font.BOLD, 18));

		emailTextField = new JTextField(p.getEmail());
		contactBillingPanel.add(emailTextField, "cell 2 3 7 1,grow");
		emailTextField.setFont(new Font("Tahoma", Font.PLAIN, 15));
		emailTextField.setColumns(10);
		emailTextField.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));

		JLabel lblPhoneNumber = new JLabel("Phone number:");
		contactBillingPanel.add(lblPhoneNumber, "cell 1 5,alignx left,aligny top");
		lblPhoneNumber.setFont(new Font("Source Code Pro", Font.BOLD, 18));

		JLabel prefixLabel = new JLabel("prefix\r\n");
		contactBillingPanel.add(prefixLabel, "cell 2 5,alignx left,aligny center");
		prefixLabel.setFont(new Font("Source Sans Pro", Font.ITALIC, 14));

		String[] phone = p.getPhonenumber().split(" ");
		
		prefixTextField = new JTextField(phone[0]);
		contactBillingPanel.add(prefixTextField, "cell 3 5 2 1,alignx right,growy");
		prefixTextField.setFont(new Font("Tahoma", Font.PLAIN, 15));
		prefixTextField.setColumns(10);
		prefixTextField.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));

		JLabel lblNewLabel = new JLabel("number");
		contactBillingPanel.add(lblNewLabel, "cell 5 5,alignx left,aligny center");
		lblNewLabel.setFont(new Font("Source Sans Pro", Font.ITALIC, 14));

		phnumberTextField = new JTextField(phone[1]);
		contactBillingPanel.add(phnumberTextField, "cell 6 5 3 1,grow");
		phnumberTextField.setFont(new Font("Tahoma", Font.PLAIN, 15));
		phnumberTextField.setColumns(10);
		phnumberTextField.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));

		JLabel lblCountry = new JLabel("Country:\r\n");
		contactBillingPanel.add(lblCountry, "cell 1 7,alignx left,aligny top");
		lblCountry.setFont(new Font("Source Code Pro", Font.BOLD, 18));

		JComboBox<String> comboBoxNat = new JComboBox<String>();
		contactBillingPanel.add(comboBoxNat, "cell 2 7 7 1,growx,aligny top");
		comboBoxNat.setFont(new Font("Tahoma", Font.PLAIN, 16));
		comboBoxNat.setModel(new DefaultComboBoxModel<String>(Country.namesArray()));
		comboBoxNat.setSelectedIndex(p.getNationality().ordinal());

		JLabel lblCity = new JLabel("City:");
		contactBillingPanel.add(lblCity, "cell 1 9,alignx left,aligny top");
		lblCity.setFont(new Font("Source Code Pro", Font.BOLD, 18));

		cityTextField = new JTextField(p.getCity());
		contactBillingPanel.add(cityTextField, "cell 2 9 7 1,grow");
		cityTextField.setFont(new Font("Tahoma", Font.PLAIN, 15));
		cityTextField.setColumns(10);
		cityTextField.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));

		JLabel lblAddress = new JLabel("Address:");
		contactBillingPanel.add(lblAddress, "cell 1 11,alignx left,aligny top");
		lblAddress.setFont(new Font("Source Code Pro", Font.BOLD, 18));

		addressTextField = new JTextField(p.getAddress());
		contactBillingPanel.add(addressTextField, "cell 2 11 7 1,grow");
		addressTextField.setFont(new Font("Tahoma", Font.PLAIN, 15));
		addressTextField.setColumns(10);
		addressTextField.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
		
		saveChangesErrorLabel = new JLabel("");
		saveChangesErrorLabel.setForeground(Color.RED);
		saveChangesErrorLabel.setFont(new Font("Source Sans Pro Light", Font.BOLD, 17));
		contactBillingPanel.add(saveChangesErrorLabel, "cell 1 13 6 1,alignx right");

		saveProfileButton = new FancyButton("Save changes",new Color(51,51,51),new Color(170,170,170),new Color(150,150,150));
		saveProfileButton.setForeground(Color.white);
		saveProfileButton.setFont(new Font("Source Sans Pro", Font.BOLD, 12));
		contactBillingPanel.add(saveProfileButton, "cell 8 13 1 2,growy");
		saveProfileButton.setFont(new Font("Source Sans Pro", Font.PLAIN, 21));
		saveProfileButton.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				saveChangesErrorLabel.setText("");
				
				Calendar cld =  Calendar.getInstance();
				
				String newUsername = usernameTextField.getText();
				String newName = nameTextField.getText();
				String newSurname = surnameTextField.getText();
				String newEmail = emailTextField.getText();
				
				Country newnat = Country.values()[comboBoxNat.getSelectedIndex()];
				String city = cityTextField.getText();
				String address = addressTextField.getText();
				String newphn = prefixTextField.getText() + " " + phnumberTextField.getText();
				
				cld.setTime(p.getBirthdate());
				cld.set(Calendar.HOUR_OF_DAY, 0);
				cld.set(Calendar.MINUTE, 0);
				cld.set(Calendar.SECOND, 0);
				cld.set(Calendar.MILLISECOND, 0);
				Date newbirthdate = cld.getTime();
		
				if( newUsername.isEmpty() || newName.isEmpty() || newSurname.isEmpty() || city.isEmpty() || address.isEmpty()) {
					saveChangesErrorLabel.setText("Please fill all areas");
				}
				if(!isEmailValid(newEmail)) {
					saveChangesErrorLabel.setText("Invalid email address");
				}
				else if(!isPhoneValid(prefixTextField.getText() + phnumberTextField.getText())) {
					saveChangesErrorLabel.setText("Invalid phone number");
				}
				else {
					try {
						facade.setLoggeduser(facade.updateUserInfo(facade.getUsername(), newUsername, newName, newSurname, newEmail,newnat, city,address,newphn,newbirthdate,facade.isAdmin()));	
						JOptionPane.showMessageDialog(null, "Changes saved sucessfully");
					} catch (invalidID e1) {
						saveChangesErrorLabel.setText("Username already in use");
					}
				}	
			}
		});

	}
	
	/**
	 * Checks if an email has the correct format.
	 * @param email			email String to check.
	 * @return				boolean indicating if the email is valid.
	 */
	private boolean isEmailValid(String email) {
		String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";

		Pattern p = Pattern.compile(regex);
		Matcher m =  p.matcher(email);
		return m.matches();
	}

	/**
	 * Checks if the input string corresponds to a valid phone number.
	 * @param pnumber		String to verify.
	 * @return				boolean indicating if the input corresponds to a phone number.
	 */
	private boolean isPhoneValid(String pnumber) {
		return pnumber.matches("^[+][0-9]{7,11}");
	}

	
	
}
