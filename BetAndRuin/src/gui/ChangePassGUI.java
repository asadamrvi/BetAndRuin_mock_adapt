package gui;


import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.border.EmptyBorder;
import businessLogic.BLFacade;
import exceptions.invalidID;
import exceptions.invalidPW;
import gui.components.HintPassField;
import gui.components.HintTextField;
import gui.components.passVisibleLabel;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;
import java.awt.Image;
import java.awt.Color;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import gui.components.cancelLabel;
import java.awt.FlowLayout;

public class ChangePassGUI extends JDialog {

	private static final long serialVersionUID = 1L;

	private JPanel iconPanel;

	private JPanel contentPane;
	private HintPassField newPassField;
	private HintPassField confirmPassField;
	private HintPassField currentPassField;

	private JButton saveNewPassButton;

	private JLabel titleLabel;
	private JLabel passIconLabel;	
	private JLabel currentPassLabel;
	private JLabel  newPasslabel;
	private JLabel confirmPassLabel;
	private JLabel currentPassErrorLabel;
	private JLabel newPassErrorLabel;



	BLFacade facade = MainGUI.getBusinessLogic();
	/**
	 * Create the frame.
	 */
	@SuppressWarnings("serial")
	public ChangePassGUI() {

		BLFacade facade = MainGUI.getBusinessLogic();

		setTitle("Login");

		setModal(true);
		setResizable(false);
		setBounds(600, 200, 551, 437);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(0, 0, 0));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);


		titleLabel = new JLabel("Change Password");
		titleLabel.setBounds(-36, 25, 587, 26);
		contentPane.add(titleLabel);
		titleLabel.setForeground(new Color(153, 153, 153));
		titleLabel.setOpaque(false);
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setFont(new Font("Source Code Pro Light", Font.BOLD, 27));

		currentPassErrorLabel = new JLabel("");
		currentPassErrorLabel.setFont(new Font("Source Code Pro", Font.BOLD, 13));
		currentPassErrorLabel.setHorizontalAlignment(SwingConstants.CENTER);
		currentPassErrorLabel.setForeground(Color.RED);
		currentPassErrorLabel.setBounds(108, 124, 355, 35);
		contentPane.add(currentPassErrorLabel);




		currentPassField = new HintPassField("Enter current password");
		currentPassField.setBounds(108, 99, 345, 25);
		contentPane.add(currentPassField);
		currentPassField.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.WHITE));

		newPassField = new HintPassField("Enter new password (Minimum 8 characters)");
		newPassField.setBackground(new Color(0, 0, 0));
		newPassField.setBounds(108, 183, 345, 25);
		contentPane.add(newPassField);
		newPassField.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.WHITE));

		confirmPassField = new HintPassField("Enter new password again");
		confirmPassField.setBounds(108, 239, 345, 25);
		contentPane.add(confirmPassField);
		confirmPassField.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.WHITE));

		newPassErrorLabel = new JLabel("");
		newPassErrorLabel.setFont(new Font("Source Code Pro", Font.BOLD, 13));
		newPassErrorLabel.setBounds(108, 265, 355, 24);
		newPassErrorLabel.setHorizontalAlignment(SwingConstants.CENTER);
		newPassErrorLabel.setForeground(Color.RED);
		contentPane.add(newPassErrorLabel);

		saveNewPassButton = new JButton("Save new password");
		saveNewPassButton.setBorderPainted(false);
		saveNewPassButton.setFocusPainted(false);
		saveNewPassButton.setBounds(108, 316, 345, 29);
		saveNewPassButton.setForeground(new Color(255, 255, 255));
		saveNewPassButton.setBackground(new Color(102, 102, 102));
		saveNewPassButton.setFont(new Font("Source Code Pro ExtraLight", Font.BOLD, 18));

		Action save = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				currentPassErrorLabel.setText("");
				newPassErrorLabel.setText("");

				String currentpass =  currentPassField.getText();
				String newpass =  newPassField.getText();
				String confirmpass = confirmPassField.getText();
				try {		
					if(facade.updatePassword(facade.getLoggeduser(), currentpass, newpass, confirmpass)) {
						JOptionPane.showMessageDialog(null, "Password updated sucessfully");
						dispose();
					}
					else if(newpass.length()<8) {
						newPassErrorLabel.setText("Min. password length : 8");	
					}
					else {
						newPassErrorLabel.setText("Passwords don't match");
					}
				} catch (invalidPW i) {
					currentPassErrorLabel.setText(i.getMessage());
				}
				catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		};
		saveNewPassButton.addActionListener(save);
		//keybinding for enter button
		saveNewPassButton.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "save");
		saveNewPassButton.getActionMap().put("save", save);
		contentPane.add(saveNewPassButton);

		iconPanel = new JPanel();
		iconPanel.setOpaque(false);
		iconPanel.setBounds(396, 11, 46, 51);
		contentPane.add(iconPanel);
		passIconLabel = new JLabel();
		passIconLabel.setIcon(new ImageIcon("images/passicon.png"));

		iconPanel.add(passIconLabel);


		passVisibleLabel currentVisibleLabel = new passVisibleLabel(currentPassField);
		currentVisibleLabel.setBounds(457, 99, 68, 35);
		currentVisibleLabel.setVisible(true);
		contentPane.add(currentVisibleLabel);
		
		passVisibleLabel newpassVisibleLabel = new passVisibleLabel(newPassField);
		newpassVisibleLabel.setBounds(457, 183, 68, 35);
		newpassVisibleLabel.setVisible(true);
		contentPane.add(newpassVisibleLabel);
		
		
		passVisibleLabel confirmpassVisibleLabel = new passVisibleLabel(confirmPassField);
		confirmpassVisibleLabel.setBounds(457, 241, 68, 35);
		confirmpassVisibleLabel.setVisible(true);
		contentPane.add(confirmpassVisibleLabel);

		cancelLabel cancelLabel_ = new cancelLabel(this);
		cancelLabel_.setBounds(429, 356, 122, 41);
		contentPane.add(cancelLabel_);

		currentPassLabel = new JLabel("Current password");
		currentPassLabel.setVisible(true);
		currentPassLabel.setFont(new Font("Source Sans Pro Light", Font.BOLD, 14));
		currentPassLabel.setForeground(Color.WHITE);
		currentPassLabel.setBounds(108, 73, 345, 26);
		contentPane.add(currentPassLabel);

		newPasslabel = new JLabel("New password");
		newPasslabel.setVisible(true);
		newPasslabel.setForeground(Color.WHITE);
		newPasslabel.setFont(new Font("Source Sans Pro Light", Font.BOLD, 14));
		newPasslabel.setBounds(108, 157, 345, 26);
		contentPane.add(newPasslabel);

		confirmPassLabel = new JLabel("Confirm new password");
		confirmPassLabel.setVisible(true);
		confirmPassLabel.setFont(new Font("Source Sans Pro Light", Font.BOLD, 14));
		confirmPassLabel.setForeground(Color.WHITE);
		confirmPassLabel.setBounds(108, 214, 345, 25);
		contentPane.add(confirmPassLabel);	

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

	/*
	public Boolean getResult() {
		this.setVisible(true);
		return status;
	}
	 */
}
