package gui;
import businessLogic.BLFacade;
import domain.Country;
import domain.User;
import exceptions.invalidID;
import gui.components.HintPassField;
import gui.components.HintTextField;
import gui.components.passVisibleLabel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JDialog;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.awt.event.ActionEvent;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JRadioButton;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JCheckBox;
import gui.components.cancelLabel;
import javax.swing.SwingConstants;


public class RegisterGUI extends JDialog {
	private static final long serialVersionUID = 1L;

	private boolean adminview;

	User registeredUser;

	private HintTextField usernameTextField  = new HintTextField("Enter your username");
	private HintTextField emailTextField = new HintTextField("example@emailprovider.com");
	private HintTextField nameTextField = new HintTextField("Enter first name");
	private HintTextField surnameTextField = new HintTextField("Enter surname");
	private HintPassField passwordField = new HintPassField("Enter password (Minimum 8 characters)");
	private HintPassField passwordConfirmField = new HintPassField("Enter confirm password");
	private HintTextField cityTextField = new HintTextField("Enter city of residence");
	private HintTextField addressTextField = new HintTextField("Enter address");
	private HintTextField phoneTextField = new HintTextField("Number");
	private HintTextField phonePrefTextField  = new HintTextField("Prefix (ex. +34)");
	private HintTextField pcodeTextField = new HintTextField("ex. 20800");

	private JLabel lblPostalCode = new JLabel("Postal code:");
	private JLabel lblPhoneNumber = new JLabel("Phone number:");
	private JLabel lblAddress = new JLabel("Address:");
	private JLabel lblCity = new JLabel("City:");
	private JLabel lblNationality = new JLabel("Nationality:");	
	private JLabel lblSurname = new JLabel("Surname:");
	private JLabel lblName = new JLabel("Name:");
	private JLabel lblBithdate = new JLabel("Bithdate:");
	private JLabel lblRights = new JLabel("Rights:");
	private JLabel lblConfirmPassword = new JLabel("Confirm password:");
	private JLabel lblPassword = new JLabel("Password:");
	private JLabel lblUsername = new JLabel("Username:");
	private JLabel lblEmail = new JLabel("E-mail:");

	private JLabel usernameErrorLabel = new JLabel("");
	private JLabel pwErrorLabel = new JLabel("");
	private JLabel confirmPwErrorLabel = new JLabel("");
	private JLabel emailErrorLabel = new JLabel("");
	private JLabel termsErrorLabel = new JLabel("");
	private JLabel genderErrorLabel = new JLabel("");
	private JLabel bdateErrorLabel = new JLabel("");
	private JLabel natErrorLabel = new JLabel("");
	private JLabel phoneErrorLabel = new JLabel("");

	private final ButtonGroup buttonGroupStatus = new ButtonGroup();
	private JRadioButton rdbtnAdmin = new JRadioButton("Admin.");
	private JRadioButton rdbtnUser = new JRadioButton("user");

	private JCheckBox chckbxTerms = new JCheckBox("I've read and agree to bound by the Terms and Conditions and Privacy Policy");
	private JScrollPane scrollPaneTerms = new JScrollPane();

	private JComboBox<String> comboBoxNat = new JComboBox<String>();
	private JComboBox<String> comboBoxYear = new JComboBox<String>();
	private JComboBox<String> comboBoxMonth = new JComboBox<String>();
	private JComboBox<String> comboBoxDay = new JComboBox<String>();

	private JButton registerButton;
	private final passVisibleLabel passVisibleToggle = new passVisibleLabel(passwordField);
	private final passVisibleLabel passConfirmVisibleToggle = new passVisibleLabel(passwordConfirmField);
	private final cancelLabel cancelLabel_ = new cancelLabel(this);



	public User getRegisteredUser() {
		return registeredUser;
	}
	/**
	 * Create the frame.
	 */
	public RegisterGUI(boolean isadmin) {
		getContentPane().setForeground(new Color(255, 255, 255));
		getContentPane().setBackground(Color.BLACK);
		setTitle("User register");
		adminview = isadmin;
		setModal(true);

		setBounds(600, 200, 737,903);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{40, 50, 30, 0, 0, 80, 30, 15, 40, 40, 10, 75, 5, 70, 5, 40, 0};
		gridBagLayout.rowHeights = new int[]{20, 15, 20, 12, 25, 30, 0, 0, 30, 0, 0, 30, 0, 0, 30, 0, 0, 30, 20, 25, 30, 0, 25, 0, 28, 23, 25, 30, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		getContentPane().setLayout(gridBagLayout);

		JLabel lblInsertRegistrationData = new JLabel("Insert registration data");;
		if (adminview) {
			lblInsertRegistrationData = new JLabel("Insert new user data");
		}
		else {
			lblInsertRegistrationData = new JLabel("Insert registration data");
		}

		lblInsertRegistrationData.setFont(new Font("Source Code Pro ExtraLight", Font.BOLD, 26));
		lblInsertRegistrationData.setForeground(Color.WHITE);
		GridBagConstraints gbc_lblInsertRegistrationData = new GridBagConstraints();
		gbc_lblInsertRegistrationData.gridwidth = 16;
		gbc_lblInsertRegistrationData.insets = new Insets(0, 0, 5, 0);
		gbc_lblInsertRegistrationData.gridx = 0;
		gbc_lblInsertRegistrationData.gridy = 1;
		getContentPane().add(lblInsertRegistrationData, gbc_lblInsertRegistrationData);	

		GridBagConstraints gbc_lblUsername = new GridBagConstraints();
		gbc_lblUsername.gridwidth = 6;
		gbc_lblUsername.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblUsername.insets = new Insets(0, 0, 5, 5);
		gbc_lblUsername.gridx = 1;
		gbc_lblUsername.gridy = 3;
		lblUsername.setForeground(Color.WHITE);
		lblUsername.setFont(new Font("Source Code Pro Light", Font.BOLD, 15));
		getContentPane().add(lblUsername, gbc_lblUsername);

		usernameTextField.setColumns(10);
		GridBagConstraints gbc_usernameTextField = new GridBagConstraints();
		gbc_usernameTextField.gridwidth = 6;
		gbc_usernameTextField.fill = GridBagConstraints.BOTH;
		gbc_usernameTextField.insets = new Insets(0, 0, 5, 5);
		gbc_usernameTextField.gridx = 1;
		gbc_usernameTextField.gridy = 4;
		getContentPane().add(usernameTextField, gbc_usernameTextField);

		usernameErrorLabel.setForeground(new Color(255, 51, 51));
		GridBagConstraints gbc_usernameErrorLabel = new GridBagConstraints();
		gbc_usernameErrorLabel.gridwidth = 6;
		gbc_usernameErrorLabel.anchor = GridBagConstraints.NORTHWEST;
		gbc_usernameErrorLabel.insets = new Insets(0, 0, 5, 5);
		gbc_usernameErrorLabel.gridx = 1;
		gbc_usernameErrorLabel.gridy = 5;
		getContentPane().add(usernameErrorLabel, gbc_usernameErrorLabel);

		GridBagConstraints gbc_lblPassword = new GridBagConstraints();
		gbc_lblPassword.anchor = GridBagConstraints.WEST;
		gbc_lblPassword.insets = new Insets(0, 0, 5, 5);
		gbc_lblPassword.gridx = 1;
		gbc_lblPassword.gridy = 6;
		lblPassword.setForeground(Color.WHITE);
		lblPassword.setFont(new Font("Source Code Pro Light", Font.BOLD, 15));
		getContentPane().add(lblPassword, gbc_lblPassword);


		GridBagConstraints gbc_lblName = new GridBagConstraints();
		gbc_lblName.gridwidth = 4;
		gbc_lblName.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblName.insets = new Insets(0, 0, 5, 5);
		gbc_lblName.gridx = 8;
		gbc_lblName.gridy = 6;
		lblName.setFont(new Font("Source Code Pro ExtraLight", Font.PLAIN, 15));
		lblName.setForeground(new Color(255, 255, 255));
		getContentPane().add(lblName, gbc_lblName);

		GridBagConstraints gbc_lblRights = new GridBagConstraints();
		gbc_lblRights.anchor = GridBagConstraints.WEST;
		gbc_lblRights.gridwidth = 2;
		gbc_lblRights.insets = new Insets(0, 0, 5, 5);
		gbc_lblRights.gridx = 13;
		gbc_lblRights.gridy = 6;
		lblRights.setForeground(Color.WHITE);
		lblRights.setFont(new Font("Source Code Pro ExtraLight", Font.BOLD, 15));
		getContentPane().add(lblRights, gbc_lblRights);

		GridBagConstraints gbc_passwordField = new GridBagConstraints();
		gbc_passwordField.gridwidth = 5;
		gbc_passwordField.fill = GridBagConstraints.HORIZONTAL;
		gbc_passwordField.insets = new Insets(0, 0, 5, 5);
		gbc_passwordField.gridx = 1;
		gbc_passwordField.gridy = 7;
		getContentPane().add(passwordField, gbc_passwordField);

		GridBagConstraints gbc_passVisibleToggle = new GridBagConstraints();
		gbc_passVisibleToggle.insets = new Insets(0, 0, 5, 5);
		gbc_passVisibleToggle.gridx = 6;
		gbc_passVisibleToggle.gridy = 7;
		getContentPane().add(passVisibleToggle, gbc_passVisibleToggle);
		nameTextField.setBackground(Color.BLACK);

		nameTextField.setToolTipText("");
		nameTextField.setColumns(10);
		GridBagConstraints gbc_nameTextField = new GridBagConstraints();
		gbc_nameTextField.gridwidth = 4;
		gbc_nameTextField.fill = GridBagConstraints.HORIZONTAL; 
		gbc_nameTextField.insets = new Insets(0, 0, 5, 5);
		gbc_nameTextField.gridx = 8;
		gbc_nameTextField.gridy = 7;
		getContentPane().add(nameTextField, gbc_nameTextField);

		buttonGroupStatus.add(rdbtnUser);
		rdbtnUser.setFont(new Font("Source Sans Pro", Font.PLAIN, 16));
		rdbtnUser.setBackground(Color.BLACK);
		rdbtnUser.setForeground(Color.WHITE);
		rdbtnUser.setSelected(true);
		GridBagConstraints gbc_rdbtnUser = new GridBagConstraints();
		gbc_rdbtnUser.anchor = GridBagConstraints.WEST;
		gbc_rdbtnUser.insets = new Insets(0, 0, 5, 5);
		gbc_rdbtnUser.gridx = 13;
		gbc_rdbtnUser.gridy = 7;
		getContentPane().add(rdbtnUser, gbc_rdbtnUser);

		buttonGroupStatus.add(rdbtnAdmin);
		GridBagConstraints gbc_rdbtnAdmin = new GridBagConstraints();
		gbc_rdbtnAdmin.anchor = GridBagConstraints.WEST;
		gbc_rdbtnAdmin.insets = new Insets(0, 0, 5, 5);
		gbc_rdbtnAdmin.gridx = 14;
		gbc_rdbtnAdmin.gridy = 7;
		rdbtnAdmin.setFont(new Font("Source Sans Pro", Font.PLAIN, 15));
		rdbtnAdmin.setForeground(Color.WHITE);
		rdbtnAdmin.setBackground(Color.BLACK);
		getContentPane().add(rdbtnAdmin, gbc_rdbtnAdmin);

		pwErrorLabel.setForeground(new Color(255, 51, 51));
		GridBagConstraints gbc_pwErrorLabel = new GridBagConstraints();
		gbc_pwErrorLabel.gridwidth = 6;
		gbc_pwErrorLabel.anchor = GridBagConstraints.NORTHWEST;
		gbc_pwErrorLabel.insets = new Insets(0, 0, 5, 5);
		gbc_pwErrorLabel.gridx = 1;
		gbc_pwErrorLabel.gridy = 8;
		getContentPane().add(pwErrorLabel, gbc_pwErrorLabel);

		confirmPwErrorLabel.setForeground(new Color(255, 51, 51));
		GridBagConstraints gbc_confirmPwErrorLabel = new GridBagConstraints();
		gbc_confirmPwErrorLabel.gridwidth = 4;
		gbc_confirmPwErrorLabel.anchor = GridBagConstraints.NORTHWEST;
		gbc_confirmPwErrorLabel.insets = new Insets(0, 0, 5, 5);
		gbc_confirmPwErrorLabel.gridx = 8;
		gbc_confirmPwErrorLabel.gridy = 8;
		getContentPane().add(confirmPwErrorLabel, gbc_confirmPwErrorLabel);

		GridBagConstraints gbc_lblConfirmPassword = new GridBagConstraints();
		gbc_lblConfirmPassword.gridwidth = 6;
		gbc_lblConfirmPassword.anchor = GridBagConstraints.WEST;
		gbc_lblConfirmPassword.insets = new Insets(0, 0, 5, 5);
		gbc_lblConfirmPassword.gridx = 1;
		gbc_lblConfirmPassword.gridy = 9;
		lblConfirmPassword.setForeground(Color.WHITE);
		lblConfirmPassword.setFont(new Font("Source Code Pro Light", Font.BOLD, 15));
		getContentPane().add(lblConfirmPassword, gbc_lblConfirmPassword);

		GridBagConstraints gbc_lblSurname = new GridBagConstraints();
		gbc_lblSurname.gridwidth = 4;
		gbc_lblSurname.anchor = GridBagConstraints.NORTH;
		gbc_lblSurname.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblSurname.insets = new Insets(0, 0, 5, 5);
		gbc_lblSurname.gridx = 8;
		gbc_lblSurname.gridy = 9;
		lblSurname.setFont(new Font("Source Code Pro ExtraLight", Font.BOLD, 15));
		lblSurname.setBackground(Color.BLACK);
		lblSurname.setForeground(Color.WHITE);
		getContentPane().add(lblSurname, gbc_lblSurname);

		GridBagConstraints gbc_passwordConfirmField = new GridBagConstraints();
		gbc_passwordConfirmField.gridwidth = 5;
		gbc_passwordConfirmField.insets = new Insets(0, 0, 5, 5);
		gbc_passwordConfirmField.fill = GridBagConstraints.HORIZONTAL;
		gbc_passwordConfirmField.gridx = 1;
		gbc_passwordConfirmField.gridy = 10;
		getContentPane().add(passwordConfirmField, gbc_passwordConfirmField);

		GridBagConstraints gbc_passConfirmVisibleToggle = new GridBagConstraints();
		gbc_passConfirmVisibleToggle.insets = new Insets(0, 0, 5, 5);
		gbc_passConfirmVisibleToggle.gridx = 6;
		gbc_passConfirmVisibleToggle.gridy = 10;
		getContentPane().add(passConfirmVisibleToggle, gbc_passConfirmVisibleToggle);

		surnameTextField.setColumns(10);
		GridBagConstraints gbc_surnameTextField = new GridBagConstraints();
		gbc_surnameTextField.gridwidth = 4;
		gbc_surnameTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_surnameTextField.insets = new Insets(0, 0, 5, 5);
		gbc_surnameTextField.gridx = 8;
		gbc_surnameTextField.gridy = 10;
		getContentPane().add(surnameTextField, gbc_surnameTextField);

		GridBagConstraints gbc_genderErrorLabel = new GridBagConstraints();
		gbc_genderErrorLabel.gridwidth = 3;
		gbc_genderErrorLabel.insets = new Insets(0, 0, 5, 0);
		gbc_genderErrorLabel.gridx = 13;
		gbc_genderErrorLabel.gridy = 11;
		genderErrorLabel.setForeground(Color.RED);
		getContentPane().add(genderErrorLabel, gbc_genderErrorLabel);

		GridBagConstraints gbc_lblEmail = new GridBagConstraints();
		gbc_lblEmail.gridwidth = 6;
		gbc_lblEmail.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblEmail.insets = new Insets(0, 0, 5, 5);
		gbc_lblEmail.gridx = 1;
		gbc_lblEmail.gridy = 12;
		lblEmail.setFont(new Font("Source Code Pro Light", Font.PLAIN, 15));
		lblEmail.setForeground(Color.WHITE);
		getContentPane().add(lblEmail, gbc_lblEmail);

		GridBagConstraints gbc_lblBithdate = new GridBagConstraints();
		gbc_lblBithdate.anchor = GridBagConstraints.WEST;
		gbc_lblBithdate.gridwidth = 8;
		gbc_lblBithdate.insets = new Insets(0, 0, 5, 0);
		gbc_lblBithdate.gridx = 8;
		gbc_lblBithdate.gridy = 12;
		lblBithdate.setForeground(Color.WHITE);
		lblBithdate.setFont(new Font("Source Code Pro ExtraLight", Font.BOLD, 15));
		getContentPane().add(lblBithdate, gbc_lblBithdate);

		emailTextField.setToolTipText("");
		emailTextField.setColumns(10);
		GridBagConstraints gbc_emailTextField = new GridBagConstraints();
		gbc_emailTextField.gridwidth = 6;
		gbc_emailTextField.anchor = GridBagConstraints.NORTH;
		gbc_emailTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_emailTextField.insets = new Insets(0, 0, 5, 5);
		gbc_emailTextField.gridx = 1;
		gbc_emailTextField.gridy = 13;
		getContentPane().add(emailTextField, gbc_emailTextField);

		GridBagConstraints gbc_comboBoxDay = new GridBagConstraints();
		gbc_comboBoxDay.gridwidth = 2;
		gbc_comboBoxDay.insets = new Insets(0, 0, 5, 5);
		gbc_comboBoxDay.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBoxDay.gridx = 8;
		gbc_comboBoxDay.gridy = 13;
		comboBoxDay.setForeground(Color.WHITE);
		comboBoxDay.setBackground(Color.GRAY);
		getContentPane().add(comboBoxDay, gbc_comboBoxDay);

		GridBagConstraints gbc_comboBoxMonth = new GridBagConstraints();
		gbc_comboBoxMonth.insets = new Insets(0, 0, 5, 5);
		gbc_comboBoxMonth.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBoxMonth.gridx = 11;
		gbc_comboBoxMonth.gridy = 13;
		comboBoxMonth.setBackground(Color.GRAY);
		comboBoxMonth.setForeground(Color.WHITE);
		getContentPane().add(comboBoxMonth, gbc_comboBoxMonth);

		GridBagConstraints gbc_comboBoxYear = new GridBagConstraints();
		gbc_comboBoxYear.gridwidth = 2;
		gbc_comboBoxYear.insets = new Insets(0, 0, 5, 5);
		gbc_comboBoxYear.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBoxYear.gridx = 13;
		gbc_comboBoxYear.gridy = 13;
		comboBoxYear.setForeground(Color.WHITE);
		comboBoxYear.setBackground(Color.GRAY);
		getContentPane().add(comboBoxYear, gbc_comboBoxYear);

		emailErrorLabel.setForeground(new Color(255, 51, 51));
		GridBagConstraints gbc_emailErrorLabel = new GridBagConstraints();
		gbc_emailErrorLabel.anchor = GridBagConstraints.NORTH;
		gbc_emailErrorLabel.gridwidth = 6;
		gbc_emailErrorLabel.insets = new Insets(0, 0, 5, 5);
		gbc_emailErrorLabel.gridx = 1;
		gbc_emailErrorLabel.gridy = 14;
		getContentPane().add(emailErrorLabel, gbc_emailErrorLabel);	

		GridBagConstraints gbc_bdateErrorLabel = new GridBagConstraints();
		gbc_bdateErrorLabel.gridwidth = 8;
		gbc_bdateErrorLabel.insets = new Insets(0, 0, 5, 0);
		gbc_bdateErrorLabel.gridx = 8;
		gbc_bdateErrorLabel.gridy = 14;
		bdateErrorLabel.setForeground(Color.RED);
		getContentPane().add(bdateErrorLabel, gbc_bdateErrorLabel);

		GridBagConstraints gbc_lblNationality = new GridBagConstraints();
		gbc_lblNationality.anchor = GridBagConstraints.WEST;
		gbc_lblNationality.insets = new Insets(0, 0, 5, 5);
		gbc_lblNationality.gridx = 1;
		gbc_lblNationality.gridy = 15;
		lblNationality.setForeground(Color.WHITE);
		lblNationality.setFont(new Font("Source Code Pro ExtraLight", Font.BOLD, 15));
		getContentPane().add(lblNationality, gbc_lblNationality);

		GridBagConstraints gbc_lblPostalCode = new GridBagConstraints();
		gbc_lblPostalCode.anchor = GridBagConstraints.WEST;
		gbc_lblPostalCode.gridwidth = 3;
		gbc_lblPostalCode.insets = new Insets(0, 0, 5, 5);
		gbc_lblPostalCode.gridx = 4;
		gbc_lblPostalCode.gridy = 15;
		lblPostalCode.setForeground(Color.WHITE);
		lblPostalCode.setFont(new Font("Source Code Pro ExtraLight", Font.BOLD, 15));
		getContentPane().add(lblPostalCode, gbc_lblPostalCode);

		GridBagConstraints gbc_lblCity = new GridBagConstraints();
		gbc_lblCity.gridwidth = 8;
		gbc_lblCity.anchor = GridBagConstraints.WEST;
		gbc_lblCity.insets = new Insets(0, 0, 5, 0);
		gbc_lblCity.gridx = 8;
		gbc_lblCity.gridy = 15;
		lblCity.setForeground(Color.WHITE);
		lblCity.setFont(new Font("Source Sans Pro ExtraLight", Font.BOLD, 16));
		getContentPane().add(lblCity, gbc_lblCity);

		GridBagConstraints gbc_comboBoxNat = new GridBagConstraints();
		gbc_comboBoxNat.gridwidth = 3;
		gbc_comboBoxNat.insets = new Insets(0, 0, 5, 5);
		gbc_comboBoxNat.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBoxNat.gridx = 1;
		gbc_comboBoxNat.gridy = 16;
		comboBoxNat.setBackground(Color.GRAY);
		comboBoxNat.setForeground(Color.WHITE);
		getContentPane().add(comboBoxNat, gbc_comboBoxNat);

		//	pcodeTextField.setText();
		GridBagConstraints gbc_pcodeTextField = new GridBagConstraints();
		gbc_pcodeTextField.gridwidth = 3;
		gbc_pcodeTextField.insets = new Insets(0, 0, 5, 5);
		gbc_pcodeTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_pcodeTextField.gridx = 4;
		gbc_pcodeTextField.gridy = 16;
		getContentPane().add(pcodeTextField, gbc_pcodeTextField);
		pcodeTextField.setColumns(10);

		GridBagConstraints gbc_cityTextField = new GridBagConstraints();
		gbc_cityTextField.gridwidth = 7;
		gbc_cityTextField.insets = new Insets(0, 0, 5, 5);
		gbc_cityTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_cityTextField.gridx = 8;
		gbc_cityTextField.gridy = 16;
		getContentPane().add(cityTextField, gbc_cityTextField);
		cityTextField.setColumns(10);

		GridBagConstraints gbc_natErrorLabel = new GridBagConstraints();
		gbc_natErrorLabel.gridwidth = 2;
		gbc_natErrorLabel.insets = new Insets(0, 0, 5, 5);
		gbc_natErrorLabel.gridx = 1;
		gbc_natErrorLabel.gridy = 17;
		natErrorLabel.setForeground(Color.RED);
		getContentPane().add(natErrorLabel, gbc_natErrorLabel);

		GridBagConstraints gbc_lblPhoneNumber = new GridBagConstraints();
		gbc_lblPhoneNumber.gridwidth = 6;
		gbc_lblPhoneNumber.anchor = GridBagConstraints.WEST;
		gbc_lblPhoneNumber.insets = new Insets(0, 0, 5, 5);
		gbc_lblPhoneNumber.gridx = 1;
		gbc_lblPhoneNumber.gridy = 18;
		lblPhoneNumber.setForeground(Color.WHITE);
		lblPhoneNumber.setFont(new Font("Source Code Pro ExtraLight", Font.PLAIN, 15));
		getContentPane().add(lblPhoneNumber, gbc_lblPhoneNumber);

		GridBagConstraints gbc_lblAddress = new GridBagConstraints();
		gbc_lblAddress.anchor = GridBagConstraints.WEST;
		gbc_lblAddress.gridwidth = 8;
		gbc_lblAddress.insets = new Insets(0, 0, 5, 0);
		gbc_lblAddress.gridx = 8;
		gbc_lblAddress.gridy = 18;
		lblAddress.setForeground(Color.WHITE);
		lblAddress.setFont(new Font("Source Code Pro ExtraLight", Font.PLAIN, 15));
		getContentPane().add(lblAddress, gbc_lblAddress);

		//phonePrefTextField.setText();
		GridBagConstraints gbc_phonePrefTextField = new GridBagConstraints();
		gbc_phonePrefTextField.insets = new Insets(0, 0, 5, 5);
		gbc_phonePrefTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_phonePrefTextField.gridx = 1;
		gbc_phonePrefTextField.gridy = 19;
		getContentPane().add(phonePrefTextField, gbc_phonePrefTextField);
		phonePrefTextField.setColumns(10);

		GridBagConstraints gbc_phoneTextField = new GridBagConstraints();
		gbc_phoneTextField.gridwidth = 5;
		gbc_phoneTextField.insets = new Insets(0, 0, 5, 5);
		gbc_phoneTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_phoneTextField.gridx = 2;
		gbc_phoneTextField.gridy = 19;
		getContentPane().add(phoneTextField, gbc_phoneTextField);
		phoneTextField.setColumns(10);

		GridBagConstraints gbc_addressTextField = new GridBagConstraints();
		gbc_addressTextField.gridwidth = 7;
		gbc_addressTextField.insets = new Insets(0, 0, 5, 5);
		gbc_addressTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_addressTextField.gridx = 8;
		gbc_addressTextField.gridy = 19;
		getContentPane().add(addressTextField, gbc_addressTextField);
		addressTextField.setColumns(10);

		GridBagConstraints gbc_phoneErrorLabel = new GridBagConstraints();
		gbc_phoneErrorLabel.gridwidth = 6;
		gbc_phoneErrorLabel.insets = new Insets(0, 0, 5, 5);
		gbc_phoneErrorLabel.gridx = 1;
		gbc_phoneErrorLabel.gridy = 20;
		phoneErrorLabel.setForeground(Color.RED);
		getContentPane().add(phoneErrorLabel, gbc_phoneErrorLabel);

		GridBagConstraints gbc_scrollPaneTerms = new GridBagConstraints();
		gbc_scrollPaneTerms.gridheight = 2;
		gbc_scrollPaneTerms.gridwidth = 14;
		gbc_scrollPaneTerms.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPaneTerms.fill = GridBagConstraints.BOTH;
		gbc_scrollPaneTerms.gridx = 1;
		gbc_scrollPaneTerms.gridy = 21;
		getContentPane().add(scrollPaneTerms, gbc_scrollPaneTerms);

		GridBagConstraints gbc_chckbxTerms = new GridBagConstraints();
		gbc_chckbxTerms.gridwidth = 14;
		gbc_chckbxTerms.insets = new Insets(0, 0, 5, 5);
		gbc_chckbxTerms.gridx = 1;
		gbc_chckbxTerms.gridy = 23;
		chckbxTerms.setBackground(Color.BLACK);
		chckbxTerms.setForeground(Color.WHITE);
		getContentPane().add(chckbxTerms, gbc_chckbxTerms);

		if(!adminview) {
			lblRights.setVisible(false);
			rdbtnAdmin.setVisible(false);
			rdbtnUser.setVisible(false);		
		}

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
		String[] years = new String[currentyear-1899];
		years[0] = "Year";
		for(Integer i=1900; i<currentyear; i++) {
			years[i-1899]=i.toString();
		}
		comboBoxNat.setModel(new DefaultComboBoxModel<String>(Country.namesArray()));
		comboBoxDay.setModel(new DefaultComboBoxModel<String>(days));
		comboBoxMonth.setModel(new DefaultComboBoxModel<String>(months));
		comboBoxYear.setModel(new DefaultComboBoxModel<String>(years));
		
		termsErrorLabel.setHorizontalAlignment(SwingConstants.CENTER);
		termsErrorLabel.setForeground(Color.RED);
		GridBagConstraints gbc_termsErrorLabel = new GridBagConstraints();
		gbc_termsErrorLabel.insets = new Insets(0, 0, 5, 0);
		gbc_termsErrorLabel.fill = GridBagConstraints.HORIZONTAL;
		gbc_termsErrorLabel.anchor = GridBagConstraints.NORTH;
		gbc_termsErrorLabel.gridwidth = 9;
		gbc_termsErrorLabel.gridx = 3;
		gbc_termsErrorLabel.gridy = 24;
		getContentPane().add(termsErrorLabel, gbc_termsErrorLabel);
		GridBagConstraints gbc_registerButton = new GridBagConstraints();
		gbc_registerButton.gridwidth = 9;
		gbc_registerButton.insets = new Insets(0, 0, 5, 5);
		gbc_registerButton.anchor = GridBagConstraints.NORTH;
		gbc_registerButton.fill = GridBagConstraints.HORIZONTAL;
		gbc_registerButton.gridx = 3;
		gbc_registerButton.gridy = 25;
		getContentPane().add(getRegisterBtn(), gbc_registerButton);

		GridBagConstraints gbc_cancelLabel_ = new GridBagConstraints();
		gbc_cancelLabel_.insets = new Insets(0, 0, 5, 5);
		gbc_cancelLabel_.gridx = 14;
		gbc_cancelLabel_.gridy = 26;
		cancelLabel_.setHorizontalAlignment(SwingConstants.RIGHT);
		getContentPane().add(cancelLabel_, gbc_cancelLabel_);

		usernameErrorLabel.requestFocus();
		this.setResizable(false);
	}


	/**
	 * JButton to press in order to register. Checks whether the user inputted information format is correct, whether all areas have been filled 
	 * and if the user has agreed with the terms and conditions before registering the user. All information must be correct in order to proceed 
	 * with registration.
	 * 
	 * @return 		JButton instance of the register button.
	 */
	private JButton getRegisterBtn() {
		if(registerButton == null) {
			registerButton = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Register"));
			registerButton.setBorderPainted(false);
			registerButton.setFocusPainted(false);
			registerButton.setFont(new Font("Source Code Pro ExtraLight", Font.BOLD, 16));
			registerButton.setForeground(Color.WHITE);
			registerButton.setBackground(new Color(102,102,102));
			
			Action register = new AbstractAction() {
				private static final long serialVersionUID = 1L;

				@Override
				public void actionPerformed(ActionEvent arg0) {

					BLFacade facade = MainGUI.getBusinessLogic();

					usernameErrorLabel.setText("");
					pwErrorLabel.setText("");
					confirmPwErrorLabel.setText("");
					emailErrorLabel.setText("");
					genderErrorLabel.setText("");
					termsErrorLabel.setText("");
					bdateErrorLabel.setText("");
					natErrorLabel.setText("");
					phoneErrorLabel.setText("");

					//retrieve input data
					String username = usernameTextField.getText();
					String pass = new String(passwordField.getPassword());
					String passconfirm = new String(passwordConfirmField.getPassword());
					String name = nameTextField.getText();
					String surname = surnameTextField.getText();
					String email = emailTextField.getText();
					String city = cityTextField.getText();
					String address = addressTextField.getText();
					String phone = phoneTextField.getText();
					String phonepref = phonePrefTextField.getText();
					String pcode = pcodeTextField.getText();

					String[] s = comboBoxNat.getSelectedItem().toString().split(", ");
					Country nat = Country.valueOf(s[1]);
					int day = comboBoxDay.getSelectedIndex();
					int month = comboBoxMonth.getSelectedIndex();
					int year = comboBoxYear.getSelectedIndex();
					String date = comboBoxDay.getSelectedItem() + "-" + comboBoxMonth.getSelectedItem() + "-" + comboBoxYear.getSelectedItem();

					boolean rights = rdbtnAdmin.isSelected();

					//check input validity
					if (username.equals("") || pass.equals("") || name.equals("") || surname.equals("") || email.equals("") || city.equals("") || address.equals("") || phone.equals("")|| phonepref.equals("") || pcode.equals("")){
						termsErrorLabel.setText("Please fill all areas");
					}
					else if(pass.length()<8) {
						pwErrorLabel.setText("Min. password length : 8");
					}
					else if(!passconfirm.equals(pass)){
						confirmPwErrorLabel.setText("Passwords don't match");
					}
					else if(day <= 0 || month <= 0 || year <= 0) {
						bdateErrorLabel.setText("Please a value for all areas");
					}
					else if(!isEmailValid(email)) {
						emailErrorLabel.setText("Invalid email format");
					}
					///else if(!isDateValid(date)) {
					//bdateErrorLabel.setText("Please enter a valid date");
					//}
					else if(!isPhoneValid(phonepref+phone)) {
						phoneErrorLabel.setText("Invalid phone number");
					}
					else if(!chckbxTerms.isSelected() ) {
						termsErrorLabel.setText("Need to agree with the terms and conditions in order to register");
					}
					else {	
						try {
							DateFormat f = new SimpleDateFormat("dd-MM-yyyy");
							f.setLenient(false);
							Date bdate = f.parse(date);

							System.out.println(username + " " + pass + " " + passconfirm + " " + name + " " + surname + " " + email);
							facade.registerUser(username, pass, name, surname, email, address,phone, nat ,city,bdate, "images/profilepic/smiley.png",rights);
							JOptionPane.showMessageDialog(null, "Registration sucessfull");
							dispose();
						}
						catch (invalidID e) {
							usernameErrorLabel.setText(e.getMessage());
						} catch (ParseException e) {
							System.out.println("ttt");
							bdateErrorLabel.setText("Please enter a valid date");
						}
					}
				}
			};	
			registerButton.addActionListener(register); 
			};
		return registerButton;
	}

	/**
	 * Checks if the a date is valid both format and whether they are possible dates(Ex. Feb 30 would be an impossible date)
	 * 
	 * @param date		the date to check.
	 * @return			boolean indicating if the date is valid.
	 */
	/*
	private boolean isDateValid(String date) {
		try {
			String d_format = "dd-MM-yyyy";
			DateFormat df = new SimpleDateFormat(d_format);
			df.setLenient(false);
			df.parse(date);
			return true;
		}
		catch(ParseException e){
			return false;
		}	
	}
	*/
	
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
		return pnumber.matches("^[+][0-9]{7,10}");
	}

	/*
	private ImageIcon resizeImage(String path) {
		BufferedImage i = null;
		try {
			i = ImageIO.read(new File(path));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Graphics2D g = i.createGraphics();
		g.drawImage(i, 0, 0, 50, 50, null);
		return new ImageIcon(i);

	}
	*/
		


}
