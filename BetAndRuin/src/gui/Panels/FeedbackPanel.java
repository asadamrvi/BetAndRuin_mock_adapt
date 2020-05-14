package gui.Panels;

import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.border.MatteBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.JTextComponent;
import businessLogic.BLFacade;
import domain.Feedback.FeedbackType;
import domain.User;
import gui.MainGUI;
import gui.components.HintTextField;
import javax.swing.ButtonGroup;
import java.util.EnumMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JSeparator;

@SuppressWarnings("serial")
public class FeedbackPanel extends JPanel {

	private final int MAXSUMMARY = 100;
	private final int MAXDETAILS = 500;

	private File selectedfile;

	private JLabel lblFeedback;
	private JLabel WhatKindOfLabel;
	private JLabel TellUsLabel;
	private JLabel sumTitleLabel;
	private JLabel sumwcLabel;
	private JLabel lblGiveUsMore;
	private JPanel pinfoPanel;
	private JLabel nameLabel;
	private JLabel lblYourEmailAddress;
	private JLabel detwcLabel;
	private JLabel atfileLabel;

	private JLabel identityErrorLabel; 

	private HintTextField nameField;
	private HintTextField emailField;
	private JTextField summaryField;

	private JTextArea detailArea;

	private JRadioButton rdbtnSuggestion;
	private JRadioButton rdbtnProblem;
	private JRadioButton rdbtnQuestion;

	private JButton choosefileButton;
	private JLabel fileChosenLabel;
	private  JButton submitButton;
	private final ButtonGroup fkindGroup;
	private JLabel fileerrLabel;
	private JLabel optionErrorLabel;
	private JLabel sumErrorLabel;
	
	BLFacade facade = MainGUI.getBusinessLogic();
	private JSeparator separator;
	
	
	/**
	 * Create the panel.
	 */
	public FeedbackPanel() {
		
		setBackground(Color.WHITE);
		setLayout(new MigLayout("", "[30px:n][67.00][39.00][34.00][18.00][][77.00,trailing][100px,grow][230.00][57.00][30px:50.00]", "[-23.00][20px:5.00][1.00][20px:n:20px][24.00,grow][53.00][20px:9.00][55.00][1.00][25px:40.00,fill][][10px:n][][400px,grow][-154.00][10px:n][7.00][10px:26.00:26.00][30px:n]"));
		
		separator = new JSeparator();
		separator.setForeground(new Color(0, 0, 0));
		add(separator, "cell 1 3 6 1,growx,aligny top");
		
		//PANEL INITIALIZATION

		pinfoPanel = new JPanel();
		pinfoPanel.setBorder(null);
		pinfoPanel.setBackground(Color.WHITE);
		add(pinfoPanel, "cell 8 4 2 4,grow");
		pinfoPanel.setLayout(null);

		//LABEL INITIALIZATION

		lblFeedback = new JLabel("Feedback");
		lblFeedback.setFont(new Font("Source Code Pro ExtraLight", Font.BOLD, 28));
		add(lblFeedback, "cell 1 2 10 1");

		WhatKindOfLabel = new JLabel("What kind of feedback is it?");
		WhatKindOfLabel.setFont(new Font("Source Sans Pro ExtraLight", Font.PLAIN, 18));
		add(WhatKindOfLabel, "cell 1 4 5 1");

		nameLabel = new JLabel("Your name:");
		nameLabel.setFont(new Font("Source Sans Pro ExtraLight", Font.BOLD, 13));
		nameLabel.setBounds(0, 0, 198, 23);
		pinfoPanel.add(nameLabel);

		TellUsLabel = new JLabel("Tell us about it");
		TellUsLabel.setFont(new Font("Source Sans Pro ExtraLight", Font.PLAIN, 18));
		add(TellUsLabel, "cell 1 7 5 1");

		sumTitleLabel = new JLabel("Summarize your issue:");
		sumTitleLabel.setFont(new Font("Source Sans Pro ExtraLight", Font.ITALIC, 14));
		add(sumTitleLabel, "cell 1 8 8 1");

		lblGiveUsMore = new JLabel("Give us more detail (Optional)");
		lblGiveUsMore.setFont(new Font("Source Sans Pro ExtraLight", Font.ITALIC, 14));
		add(lblGiveUsMore, "cell 1 12 9 1");

		atfileLabel = new JLabel("Attach a file (Optional):");
		atfileLabel.setFont(new Font("Source Sans Pro ExtraLight", Font.ITALIC, 14));
		add(atfileLabel, "cell 1 16 2 1");

		lblYourEmailAddress = new JLabel("Your email address:");
		lblYourEmailAddress.setFont(new Font("Source Sans Pro ExtraLight", Font.BOLD, 13));
		lblYourEmailAddress.setBounds(0, 43, 198, 23);
		pinfoPanel.add(lblYourEmailAddress);

		sumwcLabel = new JLabel("0/" + MAXSUMMARY );
		sumwcLabel.setForeground(Color.GRAY);
		add(sumwcLabel, "cell 1 10 7 1");

		detwcLabel = new JLabel("0/"+MAXDETAILS);
		detwcLabel.setForeground(Color.GRAY);
		add(detwcLabel, "cell 1 14 9 1");

		sumErrorLabel = new JLabel("Introduce feedback information");
		sumErrorLabel.setFont(new Font("Source Sans Pro Light", Font.PLAIN, 13));
		sumErrorLabel.setForeground(Color.RED);
		add(sumErrorLabel, "cell 8 10 2 1,alignx right");
		sumErrorLabel.setVisible(false);
		
		optionErrorLabel = new JLabel("Choose option");
		optionErrorLabel.setFont(new Font("Source Sans Pro Light", Font.PLAIN, 13));
		optionErrorLabel.setForeground(Color.RED);
		add(optionErrorLabel, "cell 4 6 2 1,alignx left");
		optionErrorLabel.setVisible(false);
		
		identityErrorLabel = new JLabel("Enter identity information");
		identityErrorLabel.setLabelFor(pinfoPanel);
		identityErrorLabel.setFont(new Font("Source Sans Pro Light", Font.PLAIN, 13));
		identityErrorLabel.setForeground(Color.RED);
		add(identityErrorLabel, "cell 8 3 2 1");
		identityErrorLabel.setVisible(false);
		
		fileerrLabel = new JLabel("");
		fileerrLabel.setBackground(Color.WHITE);
		fileerrLabel.setFont(new Font("Source Sans Pro", Font.PLAIN, 14));
		add(fileerrLabel, "cell 5 17 2 1");

		fileChosenLabel = new JLabel("No file chosen");
		fileChosenLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		add(fileChosenLabel, "cell 5 16");
		fileChosenLabel.addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mouseExited(MouseEvent e) {
				if(selectedfile != null) {
					fileChosenLabel.setForeground(new Color(0,0,255));
					fileChosenLabel.setText(selectedfile.getPath());
				}	
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				if(selectedfile != null) {
					fileChosenLabel.setText("<HTML><BODY><a href=\"\">" + selectedfile.getPath() + "</BODY></HTML>");
				}	
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				fileerrLabel.setText("");
				if(selectedfile != null) {
					if(Desktop.isDesktopSupported()) {
						try {
							Desktop.getDesktop().open(selectedfile);
						} catch (IOException e1) {
							e1.printStackTrace();
						} catch (IllegalArgumentException e2){
							fileerrLabel.setText("Couldn't open file, invalid path");
						}
					}
				}
			}
		});



		//TEXTFIELD INITIALIZATION

		nameField = new HintTextField("Enter name", new Color(255,255,255), new Color(0,0,0), new Color(0,0,0), new Color(169,169,169), new Color(0,0,0));
		nameLabel.setLabelFor(nameField);
		nameField.setBounds(0, 24, 233, 17);
		pinfoPanel.add(nameField);

		emailField = new HintTextField("Enter email address", new Color(255,255,255), new Color(0,0,0), new Color(0,0,0), new Color(169,169,169), new Color(0,0,0));
		lblYourEmailAddress.setLabelFor(emailField);
		emailField.setBounds(0, 66, 233, 17);
		pinfoPanel.add(emailField);

		summaryField = new JTextField();
		sumErrorLabel.setLabelFor(summaryField);
		sumTitleLabel.setLabelFor(summaryField);
		add(summaryField, "cell 1 9 9 1,growx");
		summaryField.setColumns(10);
		summaryField.setBorder(new MatteBorder(2, 2, 2, 2, (Color) new Color(0, 0, 0)));
		summaryField.getDocument().addDocumentListener(new charCountListener(sumwcLabel, summaryField, MAXSUMMARY));
		summaryField.addKeyListener( new charLimitListener(summaryField,MAXSUMMARY));

		//TEXTAREA INITIALIZATION

		detailArea = new JTextArea();
		lblGiveUsMore.setLabelFor(detailArea);
		detailArea.setLineWrap(true);
		detailArea.setWrapStyleWord(true);
		detailArea.setColumns(9);
		add(detailArea, "cell 1 13 9 1,grow,wmin 10");
		detailArea.setBorder(new MatteBorder(2, 2, 2, 2, (Color) new Color(0, 0, 0)));
		detailArea.getDocument().addDocumentListener(new charCountListener(detwcLabel, detailArea, MAXDETAILS));
		detailArea.addKeyListener( new charLimitListener(detailArea,MAXDETAILS));

		//RADIO BUTTION INITIALIZATION

		fkindGroup = new ButtonGroup();
		
		rdbtnSuggestion = new JRadioButton("Suggestion\r\n");
		fkindGroup.add(rdbtnSuggestion);
		rdbtnSuggestion.setFont(new Font("Source Code Pro", Font.PLAIN, 12));
		rdbtnSuggestion.setBackground(Color.WHITE);
		add(rdbtnSuggestion, "cell 1 5,aligny top");

		rdbtnProblem = new JRadioButton("Problem");
		fkindGroup.add(rdbtnProblem);
		rdbtnProblem.setFont(new Font("Source Code Pro", Font.PLAIN, 13));
		rdbtnProblem.setBackground(Color.WHITE);
		add(rdbtnProblem, "cell 2 5 2 1,aligny top");

		rdbtnQuestion = new JRadioButton("Question");
		fkindGroup.add(rdbtnQuestion);
		rdbtnQuestion.setFont(new Font("Source Code Pro", Font.PLAIN, 13));
		rdbtnQuestion.setBackground(Color.WHITE);
		add(rdbtnQuestion, "cell 4 5 2 1,alignx left,aligny top");

		RadioButtonGroupEnumAdapter<FeedbackType> enumBtns = new RadioButtonGroupEnumAdapter<FeedbackType>(FeedbackType.class);
		enumBtns.associate(FeedbackType.SUGGESTION, rdbtnSuggestion);
		enumBtns.associate(FeedbackType.PROBLEM, rdbtnProblem);
		enumBtns.associate(FeedbackType.QUESTION, rdbtnQuestion);
		
		//BUTTON INITIALIZATION

		choosefileButton = new JButton("Choose file");
		fileChosenLabel.setLabelFor(choosefileButton);
		atfileLabel.setLabelFor(choosefileButton);
		choosefileButton.setFocusPainted(false);
		choosefileButton.setBackground(new Color(192, 192, 192));
		choosefileButton.setFont(new Font("Source Sans Pro Light", Font.BOLD, 12));
		add(choosefileButton, "cell 3 16 2 1");
		choosefileButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				fileerrLabel.setText("");

				JFileChooser fc = new JFileChooser();
				int retval = fc.showOpenDialog(null);

				if(retval == JFileChooser.APPROVE_OPTION) {
					selectedfile = fc.getSelectedFile();
					fileChosenLabel.setText(selectedfile.getPath());
					fileChosenLabel.setForeground(new Color(0,0,255));
				}
			}
		});

		submitButton = new JButton("Submit feedback");
		submitButton.setBackground(new Color(192, 192, 192));
		submitButton.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		submitButton.setFont(new Font("Source Sans Pro Light", Font.BOLD, 20));
		submitButton.setFocusPainted(false);
		add(submitButton, "cell 8 16 1 2,grow");
		submitButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				sumErrorLabel.setVisible(false);
				optionErrorLabel.setVisible(false);
				identityErrorLabel.setVisible(false);
				boolean valid = true;
				User u = MainGUI.getInstance().getLoggeduser();

				FeedbackType fbtype = (FeedbackType)enumBtns.getValue();
				String name = nameField.getText();
				String email = emailField.getText();
				String summary = summaryField.getText();
				String details = detailArea.getText();
				
				if(MainGUI.getInstance().isLoggedIn()) {
					name =u.getProfile().getName();
					email =u.getProfile().getEmail();
				}
				else {
					if(fbtype == null) {
						optionErrorLabel.setVisible(true);
						valid = false;
					}
					if(name.trim().isEmpty() || email.trim().isEmpty()) {
						identityErrorLabel.setText("Enter identity information");
						identityErrorLabel.setVisible(true);
						valid = false;
					}
					else if(!isEmailValid(email)) {
						identityErrorLabel.setText("Invalid email address");
						identityErrorLabel.setVisible(true);
						valid = false;
					}
				}
				
				if(summary.trim().isEmpty()) {
					sumErrorLabel.setVisible(true);
					valid = false;
				}
				if(valid) {
					MainGUI.getBusinessLogic().submitFeedback(fbtype, email, name, summary, details, selectedfile);
					JOptionPane.showMessageDialog(null, "Feedback has been sent sucessfully, thanks for your time");
				}
			}
		});
		if(MainGUI.getInstance().isLoggedIn()){
			pinfoPanel.setVisible(false);
		}
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
	
	//AUXILIARY CLASSES

	/**
	 * Keylistener that will limit the amount of characters that can be written in the JTextComponents
	 */
	private class charLimitListener implements KeyListener{

		private JTextComponent cmp;
		private int maxcount;

		public charLimitListener(JTextComponent cmp, int maxcount) {
			this.cmp = cmp;
			this.maxcount = maxcount;
		}

		public void keyTyped(KeyEvent e) {
			String t = cmp.getText();
			if(t.length() > maxcount) { //to avoid the copy paste text off limits case
				e.consume();
				t = t.substring(0,maxcount);
				cmp.setText(t);
			}
			else if(t.length() == maxcount) { //normal case
				e.consume();
			}	
		}
		@Override
		public void keyReleased(KeyEvent e) {
		}
		@Override
		public void keyPressed(KeyEvent e) {

		}

	}


	/**
	 * Documentlistener that will continuously dispaly on a JLabel the word count on a JTextComponent
	 */
	class charCountListener implements DocumentListener{

		private JLabel lbl;
		private JTextComponent cmp;
		private int maxcount;

		public  charCountListener(JLabel lbl,JTextComponent cmp, int maxcount) {
			this.lbl = lbl;
			this.cmp = cmp;
			this.maxcount = maxcount;
		}

		@Override
		public void insertUpdate(DocumentEvent e) {
			update(); 
		}
		@Override
		public void removeUpdate(DocumentEvent e) {
			update(); 	
		}
		@Override
		public void changedUpdate(DocumentEvent e) {
			update(); 	
		}
		public void update() {
			int ccount = cmp.getText().length();
			lbl.setText(ccount + "/" + maxcount);	
		}	

	}

	/**
	 * 
	 */ 
	public class RadioButtonGroupEnumAdapter<E extends Enum<E>> {
		final private Map<E, JRadioButton> buttonMap;

		public RadioButtonGroupEnumAdapter(Class<E> enumClass)
		{
			this.buttonMap = new EnumMap<E, JRadioButton>(enumClass);
		}
		public void importMap(Map<E, JRadioButton> map)
		{
			for (E e : map.keySet())
			{
				this.buttonMap.put(e, map.get(e));
			}
		}
		public void associate(E e, JRadioButton btn)
		{
			this.buttonMap.put(e, btn);
		}
		public E getValue()
		{
			for (E e : this.buttonMap.keySet())
			{
				JRadioButton btn = this.buttonMap.get(e);
				if (btn.isSelected())
				{
					return e;
				}
			}
			return null;
		}
		public void setValue(E e)
		{
			JRadioButton btn = (e == null) ? null : this.buttonMap.get(e);
			if (btn == null)
			{
				// the following doesn't seem efficient...
				// but since when do we have more than say 10 radiobuttons?
				for (JRadioButton b : this.buttonMap.values())
				{
					b.setSelected(false);
				}

			}
			else
			{
				btn.setSelected(true);
			}
		}
	} 


}
