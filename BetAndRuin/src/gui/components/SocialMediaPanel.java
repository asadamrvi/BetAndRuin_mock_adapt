package gui.components;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.border.MatteBorder;
import java.awt.Color;
import java.awt.Cursor;
import javax.swing.JButton;

@SuppressWarnings("serial")
public class SocialMediaPanel extends JPanel{
	public SocialMediaPanel() {
		setBackground(new Color(245, 245, 245));
		setBorder(new MatteBorder(2, 2, 2, 2, (Color) new Color(0, 0, 0)));
		setLayout(null);
		
		JLabel titleLabel = new JLabel("Follow Us");
		titleLabel.setFont(new Font("Source Code Pro Light", Font.BOLD | Font.ITALIC, 22));
		titleLabel.setBounds(21, 11, 181, 28);
		add(titleLabel);
		
		JLabel contact = new JLabel();
		contact.setFont(new Font("Source Code Pro Light", Font.PLAIN, 14));
		contact.setBounds(131, 50, 233, 36);
		JLabel website = new JLabel();
		website.setFont(new Font("Source Code Pro Light", Font.PLAIN, 15));
		website.setLocation(138, 90);
		website.setSize(241, 47);

        contact.setText("<html> contact : <a href=\"\">YourEmailAddress@gmail.com</a></html>");
        contact.setCursor(new Cursor(Cursor.HAND_CURSOR));

        website.setText("<html> Website : <a href=\"\">http://www.google.com/</a></html>");
        website.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        add(contact);
        add(website);
        
        JButton btnNewButton = new JButton("New button");
        btnNewButton.setBounds(348, 11, 57, 41);
        add(btnNewButton);
        
        JButton button = new JButton("New button");
        button.setBounds(348, 58, 57, 41);
        add(button);
        
        JButton button_1 = new JButton("New button");
        button_1.setBounds(348, 104, 57, 41);
        add(button_1);
	}
}
