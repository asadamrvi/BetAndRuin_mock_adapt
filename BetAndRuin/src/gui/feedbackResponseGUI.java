package gui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.sql.DataSource;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import gui.components.FancyButton;

@SuppressWarnings("serial")
public class feedbackResponseGUI extends JDialog  {
	
	private JPanel contentPane;
	
	private JLabel sendingStatusLabel;
	
	private JTextField topicField;
	private JTextField receiverTextField;
	
	private  JTextArea textArea;

	private File selectedfile;

	/**
	 * Create the frame.
	 */
	public feedbackResponseGUI(String recepient) {
		
		setModal(true);
		setResizable(false);
		setBounds(100, 100, 551, 466);
		contentPane = new JPanel();
		contentPane.setBackground(Color.BLACK);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		topicField = new JTextField();
		topicField.setBounds(55, 152, 437, 20);
		contentPane.add(topicField);
		topicField.setColumns(10);
		
		textArea = new JTextArea();
		textArea.setBounds(55, 200, 437, 136);
		contentPane.add(textArea);
		
		JLabel fileChosenLabel = new JLabel("");
		fileChosenLabel.setBounds(55, 370, 236, 14);
		fileChosenLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		contentPane.add(fileChosenLabel);
		fileChosenLabel.addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mouseExited(MouseEvent e) {
				if(selectedfile != null) {
					fileChosenLabel.setForeground(new Color(255,255,255));
					fileChosenLabel.setText(selectedfile.getPath());
				}	
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				if(selectedfile != null) {
					fileChosenLabel.setText("<HTML><BODY><a href=\"\"><font color='#ffffff'>" + selectedfile.getPath() + "</font></BODY></HTML>");
				}	
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				if(selectedfile != null) {
					if(Desktop.isDesktopSupported()) {
						try {
							Desktop.getDesktop().open(selectedfile);
						} catch (IOException e1) {
							e1.printStackTrace();
						} 
					}
				}
			}
		});
		
		FancyButton sendButton = new FancyButton("Send",new Color(102, 102, 102),new Color(150, 150, 150),new Color(120, 120, 120));
		sendButton.setForeground(Color.white);
		sendButton.setFont(new Font("Source Code Pro ExtraLight", Font.BOLD, 14));
		sendButton.setBounds(301, 365, 89, 23);
		contentPane.add(sendButton);
		sendButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				sendingStatusLabel.setText("Sending email...");
				Address receiver = null;
				try {
					receiver = new InternetAddress(recepient);
				} catch (AddressException e1) {
					sendingStatusLabel.setText("Invalid email address");
					e1.printStackTrace();
				}
				String topic = topicField.getText();
				String body = textArea.getText();
			
				sendWithGmail(receiver, topic, body,selectedfile);
				sendingStatusLabel.setText("Email sent successfully!");
				
			}
		});
		
		FancyButton choosefileButton = new FancyButton("Choose file", new Color(102, 102, 102), new Color(150, 150, 150), new Color(120, 120, 120));
		choosefileButton.setBounds(55, 342, 104, 23);
		contentPane.add(choosefileButton);	
		choosefileButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();
				int retval = fc.showOpenDialog(null);

				if(retval == JFileChooser.APPROVE_OPTION) {
					selectedfile = fc.getSelectedFile();
					fileChosenLabel.setText(selectedfile.getPath());
					fileChosenLabel.setForeground(new Color(255,255,255));
				}
			}
		});
		
		
		JLabel subjectLabel = new JLabel("Subject:\r\n");
		subjectLabel.setForeground(Color.WHITE);
		subjectLabel.setFont(new Font("Source Sans Pro Light", Font.BOLD, 14));
		subjectLabel.setBounds(55, 132, 75, 14);
		contentPane.add(subjectLabel);
		
		JLabel bodyLabel = new JLabel("Body:");
		bodyLabel.setForeground(Color.WHITE);
		bodyLabel.setFont(new Font("Source Code Pro Light", Font.BOLD, 14));
		bodyLabel.setBounds(55, 177, 61, 20);
		contentPane.add(bodyLabel);
		
		FancyButton cancelButton = new FancyButton("Cancel",new Color(102, 102, 102),new Color(150, 150, 150),new Color(120, 120, 120));
		cancelButton.setForeground(Color.white);
		cancelButton.setFont(new Font("Source Code Pro ExtraLight", Font.BOLD, 14));
		cancelButton.setBounds(400, 365, 89, 23);
		contentPane.add(cancelButton);
		cancelButton.addActionListener(new ActionListener() {		
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
		receiverTextField = new JTextField(recepient);
		receiverTextField.setEditable(false);
		receiverTextField.setBounds(55, 101, 437, 20);
		contentPane.add(receiverTextField);
		receiverTextField.setColumns(10);
		
		JLabel receiverLabel = new JLabel("Receiver:");
		receiverLabel.setForeground(Color.WHITE);
		receiverLabel.setFont(new Font("Source Code Pro Light", Font.BOLD, 14));
		receiverLabel.setBounds(55, 77, 153, 20);
		contentPane.add(receiverLabel);

		sendingStatusLabel = new JLabel("");
		sendingStatusLabel.setVisible(true);
		sendingStatusLabel.setForeground(Color.WHITE);
		sendingStatusLabel.setFont(new Font("Source Sans Pro", Font.PLAIN, 15));
		sendingStatusLabel.setBounds(301, 399, 191, 14);
		contentPane.add(sendingStatusLabel);
		
		JLabel titleLabel = new JLabel("Write response email\r\n");
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setForeground(new Color(153,153,153));
		titleLabel.setFont(new Font("Source Code Pro Light", Font.BOLD, 27));
		titleLabel.setBounds(55, 11, 437, 51);
		contentPane.add(titleLabel);
		
		ImageIcon background = new ImageIcon("images/background/black.jpg");
		Image img = background.getImage();
		Image tenp = img.getScaledInstance(551, 442, Image.SCALE_SMOOTH);
		background = new ImageIcon(tenp);
		JLabel back = new JLabel(background);
		back.setLayout(null);
		back.setBounds(0,0,554,442);
		contentPane.add(back);		
	}
	
	
	private static void sendWithGmail(Address receiver, String topic, String body, File file) {
	    //This is what goes right before @gmail.com in your email account. It's also the sender.
	    String sender = "betandruin";  //For the address addrname@gmail.com

	    Properties props = System.getProperties();
	    props.put("mail.smtp.host", "smtp.gmail.com");  //Google's SMTP server
	    props.put("mail.smtp.user", sender);
	    props.put("mail.smtp.clave", "betandruin123");    //account password
	    props.put("mail.smtp.auth", "true");    //Use authentication by username/password 
	    props.put("mail.smtp.starttls.enable", "true"); //To connect safely to the SMTP server
	    props.put("mail.smtp.port", "587"); //Google's safe SMTP port El 

	    Session session = Session.getDefaultInstance(props);
	    MimeMessage message = new MimeMessage(session);
      
        
	    try {	    	
	        message.setFrom(new InternetAddress(sender));
	        message.addRecipient(Message.RecipientType.TO, receiver);
	        message.setSubject(topic);
	        message.setText(body);
	        
	        if(file != null) {
	        	  DataSource source = (DataSource) new FileDataSource(file.getAbsolutePath()); 
	        	  message.setFileName(file.getName()); 
	        	  message.setDataHandler(new DataHandler((javax.activation.DataSource) source));
	        }
	    	
	        Transport transport = session.getTransport("smtp");
	        transport.connect("smtp.gmail.com", sender, "betandruin123");
	        transport.sendMessage(message, message.getAllRecipients());
	        transport.close();
	    }
	    catch (MessagingException me) {
	        me.printStackTrace();  
	    }
	}
}
