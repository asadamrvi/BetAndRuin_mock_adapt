package gui;


import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.GridBagLayout;
import java.awt.Image;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import businessLogic.BLFacade;
import domain.Profile;
import gui.Panels.BrowsePanel;
import gui.Panels.CreateQuestionPanel;
import gui.Panels.FeedbackPanel;
import gui.Panels.FeedbackResponsePanel;
import gui.Panels.HomePanel;
import gui.Panels.ProfilePanel;
import gui.Panels.ProfilePanel2;
import gui.Panels.SettingsPanel;
import gui.Panels.userManagementPanel;
import gui.components.Clock;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.awt.event.ActionEvent;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import java.awt.CardLayout;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;

@SuppressWarnings("serial")
public class MainGUI extends JFrame {

	boolean admin;

	private static BLFacade appFacadeInterface;

	Timer timer;

	public static BLFacade getBusinessLogic(){
		return appFacadeInterface;
	}

	private static MainGUI mainInstance;

	public static MainGUI getInstance() {
		return mainInstance;
	}

	public static void setBussinessLogic (BLFacade afi){
		appFacadeInterface=afi;
	}

	private JLayeredPane layeredPane;

	//JPanel
	private JPanel contentPane;
	private JPanel currentPanel;
	private JPanel menuPanel;
	//private JPanel profilepicPanel;
	private JPanel miniprofilePanel;
	private JPanel loggedPanel;
	private JPanel topPanel;
	private JPanel unloggedPanel;

	//private HomePanel hmPanel;
	//private BrowsePanel brwPanel;
	//private userManagementPanel umPanel;
	//private ProfilePanel pfPanel;
	//private CreateQuestionPanel cqPanel;
	//private SettingsPanel stgPanel;
	//private FeedbackPanel fbPanel;

	//JButton
	private JButton userManagementButton;
	private JButton createQuestionButton;
	private JButton settingsButton;
	private JButton profileButton; 
	private JButton browseButton;
	private JButton homeButton;
	private JButton registerButton;
	private JButton loginButton;
	private JButton logoutButton;
	private JButton addcashButton;
	private JButton menuButton;
	private JButton feedbackButton;


	private Map<JButton,Component> menubuttons;

	//JLabel
	private JLabel IDTitleLabel;
	private JLabel cashTitleLabel;
	private JLabel profilepicLabel;
	private JLabel usernameLabel;
	private JLabel cashLabel;
	private JLabel bottomImgLabel;

	/**
	 * Create the frame.
	 */
	public MainGUI(BLFacade blogic) {
		setTitle("BET & RUIN");

		//will need to be removed for remote
		mainInstance = this;
		setBussinessLogic(blogic);
		admin = false;

		menubuttons = new HashMap<JButton,Component>();

		setMinimumSize(new Dimension(1200,813));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 940, 684);

		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(null);
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{150, 200, 273, 0};
		gbl_contentPane.rowHeights = new int[]{76, 534, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, 1.0, 1.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);

		topPanel = new JPanel();
		topPanel.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		topPanel.setBackground(new Color(0, 0, 0));
		GridBagConstraints gbc_topPanel = new GridBagConstraints();
		gbc_topPanel.gridwidth = 3;
		gbc_topPanel.fill = GridBagConstraints.BOTH;
		gbc_topPanel.gridx = 0;
		gbc_topPanel.gridy = 0;
		contentPane.add(topPanel, gbc_topPanel);
		GridBagLayout gbl_topPanel = new GridBagLayout();
		gbl_topPanel.columnWidths = new int[]{179, 157, 494, 365, 0};
		gbl_topPanel.rowHeights = new int[]{71, 0};
		gbl_topPanel.columnWeights = new double[]{0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_topPanel.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		topPanel.setLayout(gbl_topPanel);

		layeredPane = new JLayeredPane();
		GridBagConstraints gbc_layeredPane = new GridBagConstraints();
		gbc_layeredPane.fill = GridBagConstraints.BOTH;
		gbc_layeredPane.gridx = 3;
		gbc_layeredPane.gridy = 0;
		topPanel.add(layeredPane, gbc_layeredPane);

		loggedPanel = new JPanel();
		loggedPanel.setBackground(new Color(0, 0, 0));
		loggedPanel.setBounds(10, 0, 355, 71);
		layeredPane.add(loggedPanel);
		loggedPanel.setLayout(null);

		miniprofilePanel = new JPanel();
		miniprofilePanel.setLayout(null);
		miniprofilePanel.setBackground(Color.WHITE);
		miniprofilePanel.setBounds(4, 5, 264, 60);
		loggedPanel.add(miniprofilePanel);

		JPanel timerPanel = new Clock();
		timerPanel.setBackground(new Color(0, 0, 0));
		GridBagConstraints gbc_timerPanel = new GridBagConstraints();
		gbc_timerPanel.fill = GridBagConstraints.BOTH;
		gbc_timerPanel.gridx = 0;
		gbc_timerPanel.gridy = 0;
		topPanel.add(timerPanel, gbc_timerPanel);

		IDTitleLabel = new JLabel("Account:");
		IDTitleLabel.setBackground(new Color(255, 255, 255));
		IDTitleLabel.setForeground(Color.BLACK);
		IDTitleLabel.setFont(new Font("Source Code Pro Medium", Font.BOLD | Font.ITALIC, 12));
		IDTitleLabel.setBounds(77, 9, 72, 17);
		miniprofilePanel.add(IDTitleLabel);

		cashTitleLabel = new JLabel("Cash:\r\n");
		cashTitleLabel.setFont(new Font("Source Code Pro Medium", Font.BOLD | Font.ITALIC, 12));
		cashTitleLabel.setBounds(77, 34, 45, 17);
		miniprofilePanel.add(cashTitleLabel);

		addcashButton = new JButton("");
		addcashButton.setBorderPainted(false);
		addcashButton.setFocusPainted(false);
		addcashButton.setIcon(new ImageIcon("images/addIncome.png"));
		addcashButton.setBackground(new Color(255, 255, 255));
		addcashButton.setBounds(226, 26, 38, 34);
		miniprofilePanel.add(addcashButton);
		addcashButton.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				boolean valid = false;
				String s = JOptionPane.showInputDialog(null, "Enter amount to add:");
				while(!valid) {
					try {	
						float addition = Float.parseFloat(s);
						if(addition < 0) {
							s = JOptionPane.showInputDialog(null, "Enter a valid amount (amount has to be > 0):");
						}
						else {
							float newcash = appFacadeInterface.addCash(addition);
							valid=true;	
							cashLabel.setText(Float.toString(newcash));
						}	
					}
					catch(NumberFormatException n) {
						s = JOptionPane.showInputDialog(null, "Enter a valid amount (amount has to be > 0):");
					}
					catch(NullPointerException nl) {
						break;
					}
				}	
			}
		});

		usernameLabel = new JLabel("");
		usernameLabel.setBounds(147, 8, 88, 18);
		miniprofilePanel.add(usernameLabel);

		cashLabel = new JLabel("");
		cashLabel.setBounds(147, 34, 80, 17);
		miniprofilePanel.add(cashLabel);

		profilepicLabel = new JLabel("\r\n");
		profilepicLabel.setBounds(9, 5, 64, 50);
		miniprofilePanel.add(profilepicLabel);
		profilepicLabel.setIcon(null);
		profilepicLabel.setBackground(Color.WHITE);
		profilepicLabel.setVerticalAlignment(SwingConstants.TOP);
		profilepicLabel.setHorizontalAlignment(SwingConstants.CENTER);

		logoutButton = new JButton("");
		logoutButton.setToolTipText("Log out");
		logoutButton.setBorderPainted(false);
		logoutButton.setFocusPainted(false);
		logoutButton.setIcon(new ImageIcon("images/logout.png"));
		logoutButton.setBackground(new Color(0,0,0));	
		logoutButton.setBounds(279, 5, 66, 60);
		loggedPanel.add(logoutButton);
		logoutButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				appFacadeInterface.logOut();
				resetPanels();
				admin=false;
				visitorView();
			}
		});		

		unloggedPanel = new JPanel();
		unloggedPanel.setBackground(new Color(0, 0, 0));
		unloggedPanel.setBounds(10, 0, 355, 71);
		layeredPane.add(unloggedPanel);
		unloggedPanel.setLayout(null);
		
				loginButton = new JButton("Login");
				loginButton.setBorderPainted(false);
				loginButton.setFocusPainted(false);
				loginButton.setIcon(new ImageIcon("images/login.png"));
				loginButton.setFont(new Font("Source Code Pro Medium", Font.BOLD, 14));
				loginButton.setBackground(new Color(0, 0, 0));
				loginButton.setForeground(new Color(255, 255, 255));
				loginButton.setBounds(60, 0, 146, 71);
				unloggedPanel.add(loginButton);
				loginButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						JDialog d = new LoginGUI();
						d.setVisible(true);		
					}
				});

		registerButton = new JButton("Register");
		registerButton.setForeground(new Color(255, 255, 255));
		registerButton.setBackground(new Color(0, 0, 0));
		registerButton.setBounds(200, 0, 155, 71);
		unloggedPanel.add(registerButton);
		registerButton.setBorderPainted(false);
		registerButton.setFocusPainted(false);
		registerButton.setIcon(new ImageIcon("images/register.png"));
		registerButton.setFont(new Font("Source Code Pro Medium", Font.BOLD, 14));
		registerButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JDialog d = new RegisterGUI(admin);
				d.setVisible(true);
			}
		});
		loggedPanel.setEnabled(false);

		menuPanel = new JPanel();
		GridBagConstraints gbc_menuPanel = new GridBagConstraints();
		gbc_menuPanel.fill = GridBagConstraints.BOTH;
		gbc_menuPanel.gridx = 0;
		gbc_menuPanel.gridy = 1;
		contentPane.add(menuPanel, gbc_menuPanel);
		menuPanel.setBackground(new Color(51, 51, 51));
		menuPanel.setBorder(null);
		GridBagLayout gbl_menuPanel = new GridBagLayout();
		gbl_menuPanel.columnWidths = new int[] {40, 148};
		gbl_menuPanel.rowHeights = new int[]{40, 41, 41, 41, 41, 41, 41, 0, 40, 166, 20, 0};
		gbl_menuPanel.columnWeights = new double[]{0.0, 0.0};
		gbl_menuPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		menuPanel.setLayout(gbl_menuPanel);

		currentPanel = new JPanel();
		GridBagConstraints gbc_currentPanel = new GridBagConstraints();
		gbc_currentPanel.fill = GridBagConstraints.BOTH;
		gbc_currentPanel.gridwidth = 2;
		gbc_currentPanel.gridx = 1;
		gbc_currentPanel.gridy = 1;
		contentPane.add(currentPanel, gbc_currentPanel);
		currentPanel.setBackground(Color.WHITE);
		currentPanel.setLayout(new CardLayout(0, 0));

		menuButton = new MenuButton("Dashboard", new ImageIcon("images/menu.png"));
		GridBagConstraints gbc_menuButton = new GridBagConstraints();
		gbc_menuButton.insets = new Insets(0, 0, 0, 0);
		gbc_menuButton.fill = GridBagConstraints.BOTH;
		gbc_menuButton.gridwidth = 2;
		gbc_menuButton.gridx = 0;
		gbc_menuButton.gridy = 0;
		menuPanel.add(menuButton, gbc_menuButton);

		homeButton = new MenuButton("Home", new ImageIcon("images/home1.png"));
		GridBagConstraints gbc_homeButton = new GridBagConstraints();
		gbc_homeButton.insets = new Insets(0, 0, 0, 0);
		gbc_homeButton.gridwidth = 2;
		gbc_homeButton.fill = GridBagConstraints.BOTH;
		gbc_homeButton.gridx = 0;
		gbc_homeButton.gridy = 1;
		menuPanel.add(homeButton, gbc_homeButton);
		homeButton.addActionListener(menuAction);

		browseButton = new MenuButton("Browse", new ImageIcon("images/browse.png"));
		GridBagConstraints gbc_browseButton = new GridBagConstraints();
		gbc_browseButton.insets = new Insets(0, 0, 0, 0);
		gbc_browseButton.gridwidth = 2;
		gbc_browseButton.fill = GridBagConstraints.BOTH;
		gbc_browseButton.gridx = 0;
		gbc_browseButton.gridy = 2;
		menuPanel.add(browseButton, gbc_browseButton);
		browseButton.addActionListener(menuAction);

		settingsButton = new MenuButton("Settings", new ImageIcon("images/settings.png"));
		GridBagConstraints gbc_settingsButton = new GridBagConstraints();
		gbc_settingsButton.insets = new Insets(0, 0, 0, 0);
		gbc_settingsButton.fill = GridBagConstraints.BOTH;
		gbc_settingsButton.gridwidth = 2;
		gbc_settingsButton.gridx = 0;
		gbc_settingsButton.gridy = 3;
		menuPanel.add(settingsButton, gbc_settingsButton);
		settingsButton.addActionListener(menuAction);

		homeButton.setEnabled(true);
		homeButton.setVisible(true);
		browseButton.setEnabled(true);
		browseButton.setVisible(true);
		settingsButton.setVisible(true);
		settingsButton.setVisible(true);
		loggedPanel.setVisible(false);

		feedbackButton = new MenuButton("Feedback", new ImageIcon("images/feedback1.png"));
		GridBagConstraints gbc_feedbackButton = new GridBagConstraints();
		gbc_feedbackButton.insets = new Insets(0, 0, 0, 0);
		gbc_feedbackButton.gridwidth = 2;
		gbc_feedbackButton.fill = GridBagConstraints.BOTH;
		gbc_feedbackButton.gridx = 0;
		gbc_feedbackButton.gridy = 4;
		menuPanel.add(feedbackButton, gbc_feedbackButton);		
		feedbackButton.addActionListener(menuAction);

		profileButton = new MenuButton("Profile\r\n", new ImageIcon("images/profileicon.png"));
		profileButton.setBackground(new Color(51, 51, 51));
		GridBagConstraints gbc_profileButton = new GridBagConstraints();
		gbc_profileButton.insets = new Insets(0, 0, 0, 0);
		gbc_profileButton.fill = GridBagConstraints.BOTH;
		gbc_profileButton.gridwidth = 3;
		gbc_profileButton.gridx = 0;
		gbc_profileButton.gridy = 5;
		menuPanel.add(profileButton, gbc_profileButton);
		profileButton.addActionListener(menuAction);

		createQuestionButton = new MenuButton("<html><left>  Create<br>  question</left></html>", new ImageIcon("images/create_question.png"));	
		GridBagConstraints gbc_createQuestionButton = new GridBagConstraints();
		gbc_createQuestionButton.insets = new Insets(0, 0, 0, 0);
		gbc_createQuestionButton.fill = GridBagConstraints.BOTH;
		gbc_createQuestionButton.gridwidth = 3;
		gbc_createQuestionButton.gridx = 0;
		gbc_createQuestionButton.gridy = 6;
		menuPanel.add(createQuestionButton, gbc_createQuestionButton);
		createQuestionButton.addActionListener(menuAction);

		userManagementButton = new MenuButton("<html><left>  User<br>  management</left></html>", new ImageIcon("images/user_management.png"));
		GridBagConstraints gbc_userManagementButton = new GridBagConstraints();
		gbc_userManagementButton.insets = new Insets(0, 0, 0, 0);
		gbc_userManagementButton.fill = GridBagConstraints.BOTH;
		gbc_userManagementButton.gridwidth = 3;
		gbc_userManagementButton.gridx = 0;
		gbc_userManagementButton.gridy = 7;
		menuPanel.add(userManagementButton, gbc_userManagementButton);
		userManagementButton.addActionListener(menuAction);		

		//menubuttons.put(homeButton,new HomePanel());
		resetPanels();		

		bottomImgLabel = new JLabel(randomSilouette());
		bottomImgLabel.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_bottomImgLabel = new GridBagConstraints();
		gbc_bottomImgLabel.gridwidth = 2;
		gbc_bottomImgLabel.gridx = 0;
		gbc_bottomImgLabel.gridy = 9;
		menuPanel.add(bottomImgLabel, gbc_bottomImgLabel);

		//initialise on visitor view
		visitorView();
	}

	/*
	 * 
	 */
	public void refreshCash() {
		cashLabel.setText(Float.toString(appFacadeInterface.getCash()));
	}

	public void refreshProfilePic() {
		Profile p = appFacadeInterface.getProfile();
		try {
			Image img = ImageIO.read(new File(p.getProfilepic()));
			profilepicLabel.setIcon(new ImageIcon(img.getScaledInstance(
					60,50, Image.SCALE_SMOOTH)));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Displays the elements on screen that a regular user should be able to see/interact with.
	 */
	public void userView() {
		profileButton.setEnabled(true);
		profileButton.setVisible(true);
		userManagementButton.setEnabled(false);
		userManagementButton.setVisible(false);
		createQuestionButton.setEnabled(false);
		createQuestionButton.setVisible(false);


		unloggedPanel.setEnabled(false);
		unloggedPanel.setVisible(false);
		loggedPanel.setEnabled(true);
		loggedPanel.setVisible(true);

		menubuttons.put(feedbackButton, new FeedbackPanel());
		Profile p = appFacadeInterface.getProfile();
		usernameLabel.setText(p.getID());
		cashLabel.setText(Float.toString(appFacadeInterface.getCash()));
		refreshProfilePic();

		currentPanel.removeAll();
		currentPanel.add(menubuttons.get(homeButton));	
		select(homeButton);
		currentPanel.updateUI();
	}

	/**
	 * Displays the elements on screen that an admin should be able to see/interact with.
	 */
	public void adminView() {
		profileButton.setEnabled(true);
		profileButton.setVisible(true);
		userManagementButton.setEnabled(true);
		userManagementButton.setVisible(true);
		createQuestionButton.setEnabled(true);
		createQuestionButton.setVisible(true);

		unloggedPanel.setEnabled(false);
		unloggedPanel.setVisible(false);
		loggedPanel.setEnabled(true);
		loggedPanel.setVisible(true);

		menubuttons.put(feedbackButton, new FeedbackResponsePanel());
		Profile p = appFacadeInterface.getProfile();
		usernameLabel.setText(appFacadeInterface.getUsername());
		cashLabel.setText(Float.toString(appFacadeInterface.getCash()));
		
		refreshProfilePic();
		//profilepicLabel.setIcon(new ImageIcon(p.getProfilepic()));

		currentPanel.removeAll();
		currentPanel.add(menubuttons.get(homeButton));	
		select(homeButton);

		currentPanel.updateUI();
	}

	/**
	 * Displays the elements on screen that a logged out visitor should be able to see/interact with.
	 */
	public void visitorView() {
		profileButton.setEnabled(false);
		profileButton.setVisible(false);
		userManagementButton.setEnabled(false);
		userManagementButton.setVisible(false);
		createQuestionButton.setEnabled(false);
		createQuestionButton.setVisible(false);

		loggedPanel.setEnabled(false);
		loggedPanel.setVisible(false);
		unloggedPanel.setEnabled(true);
		unloggedPanel.setVisible(true);

		menubuttons.put(feedbackButton, new FeedbackPanel());
		currentPanel.removeAll();
		currentPanel.add(menubuttons.get(homeButton));	
		select(homeButton);
		currentPanel.updateUI();
	}

	public void select(JButton button) {
		for(JButton jb : menubuttons.keySet()) {
			jb.setBackground(new Color(51,51,51));
			jb.setSelected(false);
		}
		button.setSelected(true);
		button.setBackground(new Color(105, 105, 105));
	}


	public ImageIcon randomSilouette() {
		ArrayList<String> imagelist = new ArrayList<String>();
		imagelist.add("images/football.png");
		imagelist.add("images/basket1.png");
		imagelist.add("images/tennis.png");
		imagelist.add("images/golf.png");
		//imagelist.add("images/boxing.png");

		Random r = new Random();

		int rnd = r.nextInt(imagelist.size());
		return (new ImageIcon(imagelist.get(rnd)));
	}



	Action menuAction = new  AbstractAction() {
		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource().equals(browseButton) ) {
				menubuttons.put(browseButton, new BrowsePanel());
			}
			
			Object panel = menubuttons.get(e.getSource());
			if(panel instanceof ProfilePanel2) {
				currentPanel.removeAll();
				ProfilePanel2 p = new ProfilePanel2();
				menubuttons.put((JButton)e.getSource(),(JPanel)p);
				currentPanel.add(p);
			}
			else {
				currentPanel.removeAll();
				currentPanel.add((Component)panel);
			}


			currentPanel.updateUI();
			select((JButton)e.getSource());		
		}
	};

	public void resetPanels() {
		menubuttons.put(homeButton,new HomePanel());
		menubuttons.put(browseButton, new BrowsePanel());
		if(appFacadeInterface.isLoggedIn()) {
			menubuttons.put(profileButton,new ProfilePanel2());
		}
		menubuttons.put(settingsButton,new SettingsPanel());
		menubuttons.put(feedbackButton, new FeedbackPanel());
		menubuttons.put(createQuestionButton,new CreateQuestionPanel());
		menubuttons.put(userManagementButton,new userManagementPanel());
	}

	public Timer getTimer() {
		if(timer == null) {
			timer = new Timer(1000, null);
			timer.setInitialDelay(0);
			timer.start();
		}
		return timer;
	}
	
	public void configureTimer() {
		//configure timer user application wide
		if(timer == null) {
			timer = new Timer(1000, null);
			timer.setInitialDelay(0);
			timer.start();
		}
		timer.addActionListener(new ActionListener() {		
			@Override
			public void actionPerformed(ActionEvent e) {
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(new Date());
				if(calendar.get(Calendar.SECOND) == 0) {
					((HomePanel)menubuttons.get(homeButton)).refreshPage();
				}			
			}
		});

		timer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(new Date());
				if(calendar.get(Calendar.SECOND) == 0) {
					appFacadeInterface.resolveQuestions();
					appFacadeInterface.resolveBets();
					if(appFacadeInterface.isLoggedIn()) {
						refreshCash();
					}
				}	
			}
		});
	}
	/**
	 * Predefined JButton class for the buttons that go in the left menu
	 */
	public class MenuButton extends JButton{

		private static final long serialVersionUID = 1L;

		public MenuButton(String text, ImageIcon icon) {
			this.setText(text);
			setHorizontalAlignment(SwingConstants.LEFT);
			setFont(new Font("Source Code Pro Medium", Font.BOLD, 16));
			setFocusPainted(false);
			setBorderPainted(false);
			setIcon(icon);
			setForeground(new Color(255, 255, 255));
			setBackground(new Color(51, 51, 51));
		}

		@Override
		protected void paintComponent(Graphics g) {

			if (getModel().isPressed()) {
				setBackground(new Color(105, 105, 105));
			} else if (getModel().isRollover()) {
				setBackground(new Color(128,128,128));
			} else if (isSelected()) {
				setBackground(new Color(105, 105, 105));
			}   
			else {
				setBackground(new Color(51, 51, 51));
			}
			super.paintComponent(g);
		}


	}
}