package gui.Panels;

import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JTabbedPane;
import net.miginfocom.swing.MigLayout;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;
import businessLogic.BLFacade;
import domain.Profile;
import domain.User;
import gui.ChangePassGUI;
import gui.MainGUI;
import gui.Panels.subpanels.CreditCardsPanel;
import gui.Panels.subpanels.PersonalInfoPanel;
import gui.components.FancyButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Window;
import javax.swing.border.MatteBorder;
import javax.swing.filechooser.FileFilter;
import javax.swing.SwingConstants;
import java.awt.BorderLayout;
import javax.swing.JSeparator;


@SuppressWarnings("serial")
public class ProfilePanel extends JPanel {

	private JLabel titleLabel;
	private JLabel imageFeedbackLabel;
	private JLabel profilePicLabel;

	private PersonalInfoPanel personalInfoPanel;
	private CreditCardsPanel creditCardsPanel;

	private FancyButton chooseImageButton;
	private FancyButton saveImageButton;
	private FancyButton changePasswordButton;

	private File selectedfile;
	private JSeparator separator;
	private FancyButton deleteAccountButton;

	/**
	 * Create the panel.
	 */
	public ProfilePanel() {
		setBackground(Color.WHITE);
		BLFacade facade = MainGUI.getBusinessLogic();
		User loggeduser = MainGUI.getInstance().getLoggeduser();

		Profile p = loggeduser.getProfile();

		setFont(new Font("Source Sans Pro", Font.BOLD, 15));

		setBackground(Color.WHITE);
		setLayout(new MigLayout("", "[30.00:30.00][25:25:25][100px:100:100px,grow][-19.00][100:100:100][25:25:25][30:30:30][85.00,grow][51.00][51.00][35.00][73.00,grow][][62.00][42.00][48.00,grow][][][55.00][25.00:25.00:25.00]", "[:20:20][10:10:10][::14.00px][][20:20:20][34.00][102:102:102][102:102:102][10.00:10.00:10.00][][][][][:105.00:16.00][15.00][][][40.00,grow][30:74.00:30][20:20:20][30:30:30]"));

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setFont(new Font("Source Code Pro Black", Font.PLAIN, 14));
		add(tabbedPane, "cell 7 2 12 18,grow");

		personalInfoPanel = new PersonalInfoPanel();
		tabbedPane.addTab("Personal info", personalInfoPanel);

		creditCardsPanel = new CreditCardsPanel();
		tabbedPane.addTab("My credit cards", creditCardsPanel);

		titleLabel = new JLabel("Profile");
		titleLabel.setFont(new Font("Source Code Pro ExtraLight", Font.BOLD, 28));
		add(titleLabel, "cell 1 2 5 2");
		setBackground(Color.WHITE);

		imageFeedbackLabel = new JLabel("invalid file format");
		imageFeedbackLabel.setVisible(false);

		separator = new JSeparator();
		add(separator, "cell 1 4 5 1,growx,aligny top");
		separator.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.BLACK));


		imageFeedbackLabel.setFont(new Font("Source Sans Pro Light", Font.BOLD, 14));
		imageFeedbackLabel.setForeground(Color.red);
		add(imageFeedbackLabel, "cell 1 12 4 1,alignx center,aligny top");	

		JLabel usernameTitleLabel = new JLabel(loggeduser.getUsername());
		usernameTitleLabel.setFont(new Font("Source Code Pro", Font.BOLD, 18));
		add(usernameTitleLabel, "cell 2 5 3 1,alignx center");


		JPanel profilePicPanel = new JPanel();
		profilePicPanel.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		profilePicPanel.setBackground(Color.WHITE);
		add(profilePicPanel, "cell 2 6 3 2,grow");
		profilePicPanel.setLayout(new BorderLayout(0, 0));
		profilePicLabel = new JLabel("");
		profilePicLabel.setHorizontalAlignment(SwingConstants.CENTER);
		profilePicLabel.setBounds(0, 0, 200, 200);

		profilePicPanel.add(profilePicLabel);
		try {
			Image img = ImageIO.read(new File(p.getProfilepic()));
			profilePicLabel.setIcon(new ImageIcon(img.getScaledInstance(
					200,200, Image.SCALE_SMOOTH)));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		profilePicPanel.setPreferredSize(new Dimension(profilePicLabel.getWidth(),profilePicLabel.getHeight()));
		profilePicPanel.setMinimumSize(new Dimension(profilePicLabel.getWidth(),profilePicLabel.getHeight()));
		setVisible(false);

		JLabel lblChangeProfilePicture = new JLabel("Change profile picture:");
		lblChangeProfilePicture.setFont(new Font("Source Sans Pro ExtraLight", Font.ITALIC, 14));
		add(lblChangeProfilePicture, "cell 1 9 2 1");

		chooseImageButton = new FancyButton("choose image",new Color(51,51,51),new Color(170,170,170),new Color(150,150,150));
		chooseImageButton.setForeground(Color.white);
		chooseImageButton.setFont(new Font("Source Sans Pro", Font.BOLD, 12));
		add(chooseImageButton, "cell 4 9,grow");
		chooseImageButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				imageFeedbackLabel.setVisible(false);

				JFileChooser fc = new JFileChooser();
				ImageFilter filter = new ImageFilter();
				fc.setFileFilter(filter);

				int retval = fc.showOpenDialog(null);

				if(retval == JFileChooser.APPROVE_OPTION) {
					if(filter.accept(fc.getSelectedFile())) {
						try {
							selectedfile = fc.getSelectedFile();
							Image img = ImageIO.read(selectedfile);
							profilePicLabel.setIcon(new ImageIcon(img.getScaledInstance(
									200,200, Image.SCALE_SMOOTH)));
							saveImageButton.setEnabled(true);
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}
					else {
						imageFeedbackLabel.setForeground(Color.red);
						imageFeedbackLabel.setText("Invalid format");
						imageFeedbackLabel.setVisible(true);
					}
					//fileChosenLabel.setText(selectedfile.getPath());
					//fileChosenLabel.setForeground(new Color(0,0,255));
				}
			}
		});

		saveImageButton = new FancyButton("Save selected image", new Color(51,51,51),new Color(170,170,170),new Color(150,150,150));
		saveImageButton.setForeground(Color.white);
		saveImageButton.setEnabled(false);
		saveImageButton.setFont(new Font("Source Sans Pro", Font.BOLD, 12));
		add(saveImageButton, "cell 1 10 5 1,growx");
		saveImageButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					facade.updateProfilePic(loggeduser.getProfile(), "images/profilepic/" + selectedfile.getName());
					loggeduser.getProfile().setProfilepic("images/profilepic/" + selectedfile.getName());

					Path source = Paths.get(selectedfile.getAbsolutePath());
					Path dest = Paths.get("images/profilepic/" + selectedfile.getName());
					File f = new File("images/profilepic/" + selectedfile.getName());
					f.delete();
					Files.copy(source, dest);
					imageFeedbackLabel.setText("Update sucessful");
					imageFeedbackLabel.setForeground(new Color(0, 153, 0));
					imageFeedbackLabel.setVisible(true);
					saveImageButton.setEnabled(false);
					MainGUI.getInstance().refreshProfilePic();
				} catch (IOException e1) {
					e1.printStackTrace();
				}

			}
		});

		JLabel supportedFormatsLabel = new JLabel("supported formats: jpg,jpeg,png,gif,bmp,wbmp");
		supportedFormatsLabel.setFont(new Font("Source Sans Pro Light", Font.PLAIN, 13));
		add(supportedFormatsLabel, "cell 1 11 4 1");

		changePasswordButton = new FancyButton("Change password",new Color(51,51,51),new Color(170,170,170),new Color(150,150,150));
		add(changePasswordButton, "cell 1 15 5 1,growx");
		changePasswordButton.setForeground(Color.white);
		changePasswordButton.setFont(new Font("Source Sans Pro", Font.BOLD, 12));
		changePasswordButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JDialog d = new ChangePassGUI();
				d.setVisible(true);
			}
		});


		deleteAccountButton = new FancyButton("Delete account", new Color(51, 51, 51), new Color(170, 170, 170), new Color(150, 150, 150));
		deleteAccountButton.setForeground(Color.white);
		add(deleteAccountButton, "cell 1 16 5 1,grow");
		deleteAccountButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
			int option = JOptionPane.showConfirmDialog(getParent(),"Deleting your account is not reversible and will result in a log out, are you sure you want to continue?","Confirm deletion",JOptionPane.WARNING_MESSAGE,JOptionPane.OK_CANCEL_OPTION);
				if(option==0) {
					facade.removeUser(loggeduser.getUsername());
					MainGUI.getInstance().logOut();
					for (Window window : Window.getWindows()) {
						window.dispose();
					}
					System.gc();
					MainGUI a=new MainGUI(facade);
					a.setVisible(true);
					MainGUI.setBussinessLogic(facade);
					MainGUI.getInstance().configureTimer();
				}
			}
		});

		

	}
	
	class ImageFilter extends FileFilter {

		@Override
		public boolean accept(File pathname) {
			String filename = pathname.getName();
			if (pathname.isDirectory()) {
				return true;

			} else if (filename.endsWith("jpg") || filename.endsWith("jpeg") || filename.endsWith("png") || filename.endsWith("gif") || filename.endsWith("bmp") || filename.endsWith("wbmp")) {
				return true;
			} else {
				return false;
			}
		}

		@Override
		public String getDescription() {
			return  "Image files: jpg,jpeg,png,gif,bmp,wbmp";
		}
	}
}
