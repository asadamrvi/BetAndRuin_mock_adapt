package gui.components;

import javax.swing.JPanel;
import javax.swing.JPasswordField;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.border.MatteBorder;

import domain.CardType;
import domain.CreditCard;

import java.awt.Font;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JTextField;

public class DefaultCreditCard extends JPanel {
	
	private CreditCard defaultcard;
	
	private JLabel dueDateTitleLabel;
	private JLabel dueDateLabel;
	private JLabel balanceTitleLabel;	
	private JLabel lblLimit;
	private JLabel balanceLabel;	
	private JLabel limitLabel;
	private JLabel cardTypeTitleLabel;
	private JLabel cardTypeLabel;
	private JLabel lblCardNumber;
	
	
	private JPasswordField number1TextField;
	private JPasswordField number2TextField;
	private JPasswordField number3TextField;
	private JTextField number4TextField;

	/**
	 * Create the panel.
	 */
	public DefaultCreditCard() {
		setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		setBackground(new Color(255, 255, 255));
		setLayout(null);
		
		cardTypeTitleLabel = new JLabel("Card Type:");
		cardTypeTitleLabel.setFont(new Font("Source Sans Pro Light", Font.BOLD, 15));
		cardTypeTitleLabel.setBounds(10, 11, 79, 22);
		add(cardTypeTitleLabel);
		
		cardTypeLabel = new JLabel("\r\n");
		cardTypeLabel.setFont(new Font("Source Code Pro Black", Font.PLAIN, 13));
		cardTypeLabel.setBounds(85, 11, 122, 22);
		add(cardTypeLabel);
		
		lblCardNumber = new JLabel("Card number:");
		lblCardNumber.setFont(new Font("Source Sans Pro Light", Font.BOLD, 15));
		lblCardNumber.setBounds(10, 38, 94, 28);
		add(lblCardNumber);
		
		number1TextField = new JPasswordField();
		number1TextField.setEditable(false);
		number1TextField.setBounds(10, 66, 65, 20);
		add(number1TextField);
		number1TextField.setColumns(10);
		
		number2TextField = new JPasswordField();
		number2TextField.setEditable(false);
		number2TextField.setColumns(10);
		number2TextField.setBounds(88, 66, 65, 20);
		add(number2TextField);
		
		number3TextField = new JPasswordField();
		number3TextField.setEditable(false);
		number3TextField.setColumns(10);
		number3TextField.setBounds(163, 66, 65, 20);
		add(number3TextField);
		
		number4TextField = new JTextField();
		number4TextField.setEditable(false);
		number4TextField.setColumns(10);
		number4TextField.setBounds(238, 66, 65, 20);
		add(number4TextField);
		
		dueDateTitleLabel = new JLabel("Due Date:");
		dueDateTitleLabel.setFont(new Font("Source Sans Pro Light", Font.BOLD, 15));
		dueDateTitleLabel.setBounds(212, 11, 73, 22);
		add(dueDateTitleLabel);
		
		dueDateLabel = new JLabel("");
		dueDateLabel.setFont(new Font("Source Code Pro Black", Font.PLAIN, 13));
		dueDateLabel.setBounds(279, 11, 73, 22);
		add(dueDateLabel);
		
		balanceTitleLabel = new JLabel("Balance:");
		balanceTitleLabel.setFont(new Font("Source Sans Pro Light", Font.BOLD, 15));
		balanceTitleLabel.setBounds(10, 97, 65, 28);
		add(balanceTitleLabel);
		
		lblLimit = new JLabel("Limit:");
		lblLimit.setFont(new Font("Source Sans Pro Light", Font.BOLD, 15));
		lblLimit.setBounds(185, 97, 58, 28);
		add(lblLimit);
		
		balanceLabel = new JLabel("");
		balanceLabel.setForeground(new Color(60, 179, 113));
		balanceLabel.setFont(new Font("Source Code Pro Black", Font.PLAIN, 13));
		balanceLabel.setBounds(75, 100, 88, 22);
		add(balanceLabel);
		
		limitLabel = new JLabel("");
		limitLabel.setForeground(new Color(255, 0, 0));
		limitLabel.setFont(new Font("Source Code Pro Black", Font.PLAIN, 13));
		limitLabel.setBounds(229, 97, 103, 28);
		add(limitLabel);
	}
	
	public CreditCard getDefaultCard() {
		return defaultcard;
	}
	
	public void setDueDate(Date duedate) {
		SimpleDateFormat df = new SimpleDateFormat("MM/yyyy");
		dueDateLabel.setText(df.format(duedate));
	}
	
	public void setCardType(CardType ct) {
		cardTypeLabel.setText(ct.toString());
	}
	
	public void setCardNumber(String number) {
		number1TextField.setText(number.substring(0, 4));
		number2TextField.setText(number.substring(4, 8));
		number3TextField.setText(number.substring(8, 12));
		number4TextField.setText(number.substring(12, 16));
	}
	
	public void setBalance(double balance) {
		balanceLabel.setText(String.valueOf(balance));
	}
	
	public void setLimit(double limit) {
		limitLabel.setText(String.valueOf(limit));
	}
	
	public void setCard(CreditCard cc) {
		this.defaultcard = cc;
		if(cc != null) {
			setCardType(cc.getCardType());
			setDueDate(cc.getDueDate());
			setCardNumber(cc.getCardNumber());
			setBalance(cc.getBalance());
			setLimit(cc.getLimit());
		}
	}
	
	public void reset() {
		this.defaultcard = null;
		cardTypeLabel.setText("");
		dueDateLabel.setText("");
		number1TextField.setText("");
		number2TextField.setText("");
		number3TextField.setText("");
		number4TextField.setText("");
		balanceLabel.setText("");
		limitLabel.setText("");
	}
}
