package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import businessLogic.BLFacade;
import domain.CardType;
import domain.Prediction;
import exceptions.invalidID;
import exceptions.invalidPW;
import gui.components.FancyButton;
import gui.components.HintPassField;
import gui.components.HintTextField;
import gui.components.JNumericField;
import gui.components.cancelLabel;
import gui.components.passVisibleLabel;
import javax.swing.JTextField;

public class AddCreditCardGUI extends JDialog {

	private JPanel contentPane = new JPanel();
	private JNumericField number1TextField;
	private JNumericField number2TextField;
	private JNumericField number3TextField;
	private JNumericField number4TextField;
	private JTextField typeTextField;
	private JNumericField yearTextField;
	private JNumericField monthTextField;
	private JNumericField securityCodeTextField;
	
	private JLabel cardErrorLabel;
	private JLabel dateErrorLabel;
	
	private FancyButton addCardButton;
	
	private String cardnumber;
	private CardType cardtype;
	private JLabel fillAllAreasLabel;

	/**
	 * Create the dialog.
	 */
	public AddCreditCardGUI() {

		BLFacade facade = MainGUI.getBusinessLogic();
		
		setTitle("Login");
		
		setModal(true);
		setResizable(false);
		setBounds(600, 200, 551, 422);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(0, 0, 0));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		cancelLabel cancelLabel_ = new cancelLabel(this);
		cancelLabel_.setBounds(429, 345, 96, 23);
		contentPane.add(cancelLabel_);
		
		JLabel titleLabel = new JLabel("Add credit card");
		titleLabel.setOpaque(false);
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setForeground(new Color(153, 153, 153));
		titleLabel.setFont(new Font("Source Code Pro Light", Font.BOLD, 27));
		titleLabel.setBounds(-25, 22, 587, 26);
		contentPane.add(titleLabel);
		
		JLabel cardNumberLabel = new JLabel("Card number:");
		cardNumberLabel.setForeground(Color.WHITE);
		cardNumberLabel.setFont(new Font("Source Code Pro Light", Font.BOLD, 14));
		cardNumberLabel.setBounds(57, 81, 174, 37);
		contentPane.add(cardNumberLabel);
		
		numberListener listener = new numberListener();
		
		number1TextField = new JNumericField(4);
		number1TextField.setBounds(57, 121, 78, 20);
		contentPane.add(number1TextField);
		number1TextField.setColumns(10);
		number1TextField.getDocument().addDocumentListener(listener);
		
		number2TextField = new JNumericField(4);
		number2TextField.setColumns(10);
		number2TextField.setBounds(145, 121, 78, 20);
		contentPane.add(number2TextField);
		number2TextField.getDocument().addDocumentListener(listener);
		
		number3TextField = new JNumericField(4);
		number3TextField.setColumns(10);
		number3TextField.setBounds(233, 121, 78, 20);
		contentPane.add(number3TextField);
		number3TextField.getDocument().addDocumentListener(listener);
		
		number4TextField = new JNumericField(4);
		number4TextField.setColumns(10);
		number4TextField.setBounds(321, 121, 78, 20);
		contentPane.add(number4TextField);
		number4TextField.getDocument().addDocumentListener(listener);
		
		JLabel typeLabel = new JLabel("Card type:");
		typeLabel.setForeground(Color.WHITE);
		typeLabel.setFont(new Font("Source Code Pro Light", Font.BOLD, 14));
		typeLabel.setBounds(57, 152, 174, 23);
		contentPane.add(typeLabel);
		
		typeTextField = new JTextField();
		typeTextField.setEditable(false);
		typeTextField.setBounds(57, 182, 174, 20);
		contentPane.add(typeTextField);
		typeTextField.setColumns(10);
		
		JLabel dueDateLabel = new JLabel("Due date:");
		dueDateLabel.setForeground(Color.WHITE);
		dueDateLabel.setFont(new Font("Source Code Pro Light", Font.BOLD, 14));
		dueDateLabel.setBounds(57, 213, 112, 14);
		contentPane.add(dueDateLabel);
		
		JLabel slashLabel = new JLabel("/");
		slashLabel.setForeground(Color.WHITE);
		slashLabel.setFont(new Font("Source Code Pro Light", Font.BOLD, 18));
		slashLabel.setBounds(107, 223, 21, 45);
		contentPane.add(slashLabel);
		
		yearTextField = new JNumericField(2);
		yearTextField.setColumns(10);
		yearTextField.setBounds(123, 237, 46, 20);
		contentPane.add(yearTextField);
		
		monthTextField = new JNumericField(2);
		monthTextField.setColumns(10);
		monthTextField.setBounds(57, 237, 46, 20);
		contentPane.add(monthTextField);
		
		JLabel lblSecurityCode = new JLabel("Security code:");
		lblSecurityCode.setForeground(Color.WHITE);
		lblSecurityCode.setFont(new Font("Source Code Pro Light", Font.BOLD, 14));
		lblSecurityCode.setBounds(57, 272, 230, 14);
		contentPane.add(lblSecurityCode);
		
		securityCodeTextField = new JNumericField(3);
		securityCodeTextField.setBounds(57, 297, 78, 20);
		contentPane.add(securityCodeTextField);
		securityCodeTextField.setColumns(10);
		
		addCardButton = new FancyButton("Add card",new Color(102, 102, 102),new Color(150, 150, 150),new Color(120, 120, 120));
		addCardButton.setForeground(Color.white);
		addCardButton.setFont(new Font("Source Code Pro ExtraLight", Font.BOLD, 18));
		addCardButton.setBounds(222, 288, 177, 31);
		contentPane.add(addCardButton);
		addCardButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				fillAllAreasLabel.setVisible(false);
				dateErrorLabel.setVisible(false);
				cardErrorLabel.setVisible(false);

				boolean valid = true;
				
				if(number1TextField.getText().equals("") || number2TextField.getText().equals("") || number3TextField.getText().equals("") || number3TextField.getText().equals("") 
					||	monthTextField.getText().equals("") || yearTextField.getText().equals("") || securityCodeTextField.getText().equals("")) {
					valid = false;
					fillAllAreasLabel.setVisible(true);	
				}
				else if(cardtype.equals(CardType.UNKNOWN) || !isValid(cardnumber)) {
					valid = false;
					cardErrorLabel.setVisible(true);
				}
				else if(monthTextField.getInt()<1 || monthTextField.getInt()>12 || yearTextField.getInt()<20 || yearTextField.getInt() > 21) {
					valid = false;
					dateErrorLabel.setVisible(true);
				}
				System.out.println(valid);
				if(valid) {
					BLFacade facade = MainGUI.getBusinessLogic();
					
					Integer month = monthTextField.getInt();
					Integer year = yearTextField.getInt();
					
					Calendar calendar = Calendar.getInstance();
					calendar.clear();
					calendar.set(Calendar.MONTH, month-1);
					calendar.set(Calendar.YEAR, year+2000);
					Date dueDate = calendar.getTime();
					facade.addCreditCard(cardnumber, dueDate);
					JOptionPane.showMessageDialog(null, "Card added sucessfully");
					dispose();
				}	
			}
		});
		
		cardErrorLabel = new JLabel("Invalid card");
		cardErrorLabel.setFont(new Font("Source Sans Pro", Font.PLAIN, 16));
		cardErrorLabel.setForeground(Color.RED);
		cardErrorLabel.setBounds(251, 183, 141, 14);
		contentPane.add(cardErrorLabel);
		
		dateErrorLabel = new JLabel("Invalid date");
		dateErrorLabel.setForeground(Color.RED);
		dateErrorLabel.setFont(new Font("Source Sans Pro", Font.PLAIN, 16));
		dateErrorLabel.setBounds(186, 232, 141, 23);
		contentPane.add(dateErrorLabel);
		
		fillAllAreasLabel = new JLabel("Please fill all areas\r\n");
		fillAllAreasLabel.setFont(new Font("Source Sans Pro", Font.PLAIN, 15));
		fillAllAreasLabel.setForeground(Color.RED);
		fillAllAreasLabel.setBounds(222, 321, 177, 26);
		contentPane.add(fillAllAreasLabel);
		
		cardErrorLabel.setVisible(false);
		dateErrorLabel.setVisible(false);
		fillAllAreasLabel.setVisible(false);
		
		//Width:551.0
		//Height:422.0
		//Background image
		ImageIcon background = new ImageIcon("images/background/black.jpg");
		Image img = background.getImage();
		Image tenp = img.getScaledInstance(551, 442, Image.SCALE_SMOOTH);
		background = new ImageIcon(tenp);
		JLabel back = new JLabel(background);
		back.setLayout(null);
		back.setBounds(0,0,551,442);
		contentPane.add(back);

	}
	
	public boolean isValid(String cardNumber) {
	    int sum = 0;
	    boolean alternate = false;
	    for (int i = cardNumber.length() - 1; i >= 0; i--) {
	        int n = Integer.parseInt(cardNumber.substring(i, i + 1));
	        if (alternate) {
	            n *= 2;
	            if (n > 9) {
	                n = (n % 10) + 1;
	            }
	        }
	        sum += n;
	        alternate = !alternate;
	    }
	    return (sum % 10 == 0);
	}
	
	public class numberListener implements DocumentListener{

		@Override
		public void insertUpdate(DocumentEvent e) {
			cardnumber = number1TextField.getText() + number2TextField.getText() + number3TextField.getText() + number4TextField.getText();
			cardtype = CardType.detect(cardnumber);
			typeTextField.setText(cardtype.toString());
		}

		@Override
		public void removeUpdate(DocumentEvent e) {
			cardnumber = number1TextField.getText() + number2TextField.getText() + number3TextField.getText() + number4TextField.getText();
			cardtype = CardType.detect(cardnumber);
			typeTextField.setText(cardtype.toString());
		}

		@Override
		public void changedUpdate(DocumentEvent e) {
			cardnumber = number1TextField.getText() + number2TextField.getText() + number3TextField.getText() + number4TextField.getText();
			cardtype = CardType.detect(cardnumber);
			typeTextField.setText(cardtype.toString());
		}
		
		
	}
}
