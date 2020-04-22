package gui.Panels;

import javax.swing.JPanel;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import java.awt.Font;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import businessLogic.BLFacade;
import domain.Event;
import gui.MainGUI;
import gui.components.NonEditableTableModel;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import java.awt.BorderLayout;
import javax.swing.JSeparator;
import net.miginfocom.swing.MigLayout;
import javax.swing.JButton;

@SuppressWarnings("serial")
public class HomePanel extends JPanel {

	private JTable liveTable;
	private String[] livecolnames = {"Event", "Time elapsed", "Score"};
	private NonEditableTableModel liveTableModel;

	private JTable upcomingTable;
	private String[] upcomingcolnames = {"Competition", "Event", "Date"};
	private NonEditableTableModel upcomingTableModel;
	private JTable resultsTable;

	private  JLabel newsImageLabel;

	BLFacade facade = MainGUI.getBusinessLogic();

	/**
	 * Create the panel.
	 */
	public HomePanel() {
		setBackground(Color.WHITE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{30, 156, 10, 294, 92, 40, 113, 0, 0, 30, 0};
		gridBagLayout.rowHeights = new int[]{10, 20, 0, 33, 22, 15, 0, 0, 30, 0, 0, 30, 30, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 1.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);

		JPanel panel = new JPanel();
		panel.setBackground(new Color(240, 248, 255));
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.gridwidth = 10;
		gbc_panel.gridheight = 5;
		gbc_panel.insets = new Insets(0, 0, 5, 0);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 0;
		add(panel, gbc_panel);
		panel.setLayout(new MigLayout("", "[3:3:3][][298.00px][300.00px,grow][78.00][30:30:30][][5:5:5][][5:5:5][][30:30:30]", "[5:15.00:5][62.00][12.00px][1.00][][][52.00][]"));

		JLabel followUsLabel = new JLabel("Follow us");
		followUsLabel.setFont(new Font("Rubik", Font.BOLD, 20));
		panel.add(followUsLabel, "cell 5 1 7 2,alignx center,aligny center");

		JLabel logoLabel = new JLabel("");
		panel.add(logoLabel, "cell 1 1 1 6,alignx right,aligny center");
		logoLabel.setIcon(new ImageIcon("images/beticon.png"));

		JLabel lblBrand = new JLabel("BET & RUIN\r\n");
		panel.add(lblBrand, "cell 2 2 1 4,alignx left,aligny center");
		lblBrand.setFont(new Font("Rubik", Font.BOLD | Font.ITALIC, 41));

		JButton twitterButton = new JButton("");
		panel.add(twitterButton, "cell 6 3 1 3");
		twitterButton.setIcon(new ImageIcon("images/social media/twitter.png"));
		twitterButton.setBorder(null);
		twitterButton.setBackground(Color.WHITE);
		twitterButton.setContentAreaFilled(false);
		twitterButton.setFocusPainted(false);
		twitterButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		twitterButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					Desktop.getDesktop().browse(new URL("http://www.twitter.com").toURI());
				} catch (Exception ex) {}
			}
		});

		JButton facebookButton = new JButton("");
		panel.add(facebookButton, "cell 8 3 1 3");
		facebookButton.setIcon(new ImageIcon("images/social media/facebook.png"));
		facebookButton.setBorder(null);
		facebookButton.setBackground(Color.WHITE);
		facebookButton.setContentAreaFilled(false);
		facebookButton.setFocusPainted(false);
		facebookButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		facebookButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					Desktop.getDesktop().browse(new URL("http://www.facebook.com").toURI());
				} catch (Exception ex) {}
			}
		});

		JButton instagramButton = new JButton("");
		instagramButton.setIcon(new ImageIcon("images/social media/instagram.png"));
		panel.add(instagramButton, "cell 10 3 1 3");
		instagramButton.setBorder(null);
		instagramButton.setBackground(Color.WHITE);
		instagramButton.setContentAreaFilled(false);
		instagramButton.setFocusPainted(false);
		instagramButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		instagramButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					Desktop.getDesktop().browse(new URL("http://www.instagram.com").toURI());
				} catch (Exception ex) {}
			}
		});

		JSeparator separator = new JSeparator();
		panel.add(separator, "cell 2 6,growx,aligny top");
		separator.setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, Color.BLACK));

		JLabel lblLive = new JLabel("Live:");
		lblLive.setFont(new Font("Source Code Pro Medium", Font.BOLD, 24));
		GridBagConstraints gbc_lblLive = new GridBagConstraints();
		gbc_lblLive.gridwidth = 4;
		gbc_lblLive.anchor = GridBagConstraints.WEST;
		gbc_lblLive.insets = new Insets(0, 0, 5, 5);
		gbc_lblLive.gridx = 1;
		gbc_lblLive.gridy = 6;
		add(lblLive, gbc_lblLive);

		JLabel lblNews = new JLabel("News:");
		lblNews.setFont(new Font("Source Code Pro Medium", Font.BOLD, 24));
		GridBagConstraints gbc_lblNews = new GridBagConstraints();
		gbc_lblNews.gridwidth = 3;
		gbc_lblNews.anchor = GridBagConstraints.WEST;
		gbc_lblNews.insets = new Insets(0, 0, 5, 5);
		gbc_lblNews.gridx = 6;
		gbc_lblNews.gridy = 6;
		add(lblNews, gbc_lblNews);

		JScrollPane liveScrollPane = new JScrollPane();
		GridBagConstraints gbc_liveScrollPane = new GridBagConstraints();
		gbc_liveScrollPane.gridwidth = 4;
		gbc_liveScrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_liveScrollPane.fill = GridBagConstraints.BOTH;
		gbc_liveScrollPane.gridx = 1;
		gbc_liveScrollPane.gridy = 7;
		add(liveScrollPane, gbc_liveScrollPane);


		liveTable = new JTable();
		liveTable.setRowHeight(30);
		liveTableModel = new NonEditableTableModel(null, livecolnames);
		liveTableModel.setColumnCount(4);
		liveTable.setModel(liveTableModel);
		liveScrollPane.setViewportView(liveTable);
		liveTable.getTableHeader().setReorderingAllowed(false);
		liveTable.getTableHeader().setResizingAllowed(false);
		liveTable.getTableHeader().setFont(new Font("Source sans Pro", Font.BOLD, 16));
		liveTable.getTableHeader().setBackground(new Color(255,255,255));

		JPanel panelNews = new JPanel();
		panelNews.setMinimumSize(new Dimension(260,260));
		panelNews.setPreferredSize(new Dimension(260,260));
		GridBagConstraints gbc_panelNews = new GridBagConstraints();
		gbc_panelNews.insets = new Insets(0, 0, 5, 5);
		gbc_panelNews.fill = GridBagConstraints.BOTH;
		gbc_panelNews.gridx = 6;
		gbc_panelNews.gridy = 7;
		add(panelNews, gbc_panelNews);
		panelNews.setLayout(new BorderLayout(0, 0));

		///////////////////////////////////////////////////////////////////
		newsImageLabel = new JLabel("");
		newsImageLabel.setBounds(0, 0, 260, 260);
		newsImageLabel.setHorizontalAlignment(SwingConstants.CENTER);
		panelNews.add(newsImageLabel);
		panelNews.setPreferredSize(new Dimension(newsImageLabel.getWidth(),newsImageLabel.getHeight()));
		panelNews.setMinimumSize(new Dimension(newsImageLabel.getWidth(),newsImageLabel.getHeight()));
		///////////////////////////////////////////////////////////////////
		List<String> newslist = new ArrayList<String>();
		newslist.add("images/news/news1.jpg");
		newslist.add("images/news/news3.jpg");
		Image img = null;
		try {
			img = ImageIO.read(new File(newslist.get(0)));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		newsImageLabel.setIcon(new ImageIcon(img.getScaledInstance(
				260,260, Image.SCALE_SMOOTH)));

		Timer t = new Timer(5000, new ActionListener() {
			int i = 1;
			@Override
			public void actionPerformed(ActionEvent e) {
				if(i==1) {
					i++;
				}
				else {
					i--;
				}
				Image img = null;
				try {
					img = ImageIO.read(new File(newslist.get(i-1)));
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				newsImageLabel.setIcon(new ImageIcon(img.getScaledInstance(
						260,260, Image.SCALE_SMOOTH)));

			}
		});
		t.start();

		///////////////////////////////////////////////////////////////////////////////////////////////////////////////


		JLabel lblUpcomingEvents = new JLabel("Upcoming events:");
		lblUpcomingEvents.setFont(new Font("Source Code Pro Medium", Font.BOLD, 24));
		lblUpcomingEvents.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_lblUpcomingEvents = new GridBagConstraints();
		gbc_lblUpcomingEvents.gridwidth = 4;
		gbc_lblUpcomingEvents.anchor = GridBagConstraints.WEST;
		gbc_lblUpcomingEvents.insets = new Insets(0, 0, 5, 5);
		gbc_lblUpcomingEvents.gridx = 1;
		gbc_lblUpcomingEvents.gridy = 9;
		add(lblUpcomingEvents, gbc_lblUpcomingEvents);

		JLabel lblResults = new JLabel("Results:");
		lblResults.setFont(new Font("Source Code Pro Medium", Font.BOLD, 24));
		GridBagConstraints gbc_lblResults = new GridBagConstraints();
		gbc_lblResults.gridwidth = 3;
		gbc_lblResults.anchor = GridBagConstraints.WEST;
		gbc_lblResults.insets = new Insets(0, 0, 5, 5);
		gbc_lblResults.gridx = 6;
		gbc_lblResults.gridy = 9;
		add(lblResults, gbc_lblResults);

		JScrollPane upcomingScrollPane = new JScrollPane();
		GridBagConstraints gbc_upcomingScrollPane = new GridBagConstraints();
		gbc_upcomingScrollPane.gridwidth = 4;
		gbc_upcomingScrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_upcomingScrollPane.fill = GridBagConstraints.BOTH;
		gbc_upcomingScrollPane.gridx = 1;
		gbc_upcomingScrollPane.gridy = 10;
		add(upcomingScrollPane, gbc_upcomingScrollPane);

		upcomingTable = new JTable();
		upcomingTable.setRowHeight(30);
		upcomingTableModel = new NonEditableTableModel(null, upcomingcolnames);
		upcomingTableModel.setColumnCount(4);
		upcomingTable.setModel(upcomingTableModel);
		upcomingScrollPane.setViewportView(upcomingTable);
		upcomingTable.getTableHeader().setReorderingAllowed(false);
		upcomingTable.getTableHeader().setResizingAllowed(false);
		upcomingTable.getTableHeader().setFont(new Font("Source sans Pro", Font.BOLD, 16));
		upcomingTable.getTableHeader().setBackground(new Color(255,255,255));

		JScrollPane resultsScrollPane = new JScrollPane();
		GridBagConstraints gbc_resultsScrollPane = new GridBagConstraints();
		gbc_resultsScrollPane.gridwidth = 3;
		gbc_resultsScrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_resultsScrollPane.fill = GridBagConstraints.BOTH;
		gbc_resultsScrollPane.gridx = 6;
		gbc_resultsScrollPane.gridy = 10;
		add(resultsScrollPane, gbc_resultsScrollPane);

		resultsTable = new JTable();
		resultsTable.setModel(new DefaultTableModel(
				new Object[][] {
				},
				new String[] {
						"Event", "Result"
				}
				));
		resultsScrollPane.setColumnHeaderView(resultsTable);

		refreshPage();
	}


	public int differenceInMinutes(Date d1, Date d2) {
		long dif = d1.getTime() - d2.getTime();
		long seconds = dif/1000;
		long minutes = seconds/60;
		int result = (int)minutes;
		return result;
	}


	public void refreshPage() {
		//refresh content of live event table
		Vector<Event> liveevents = facade.getLiveEvents();
		liveTableModel.setDataVector(null, livecolnames);
		liveTableModel.setColumnCount(4);

		for (domain.Event ev:liveevents){
			Vector<Object> row = new Vector<Object>();
			Date now = new Date();

			row.add(ev.getDescription());
			row.add(differenceInMinutes(now, ev.getEventDate()));
			row.add("18-29");
			row.add(ev); // Event object added in order to obtain it with tableModelQueries.getValueAt(i,3)
			liveTableModel.addRow(row);	
		}
		liveTable.getColumnModel().getColumn(0).setPreferredWidth(268);
		liveTable.getColumnModel().getColumn(0).setMinWidth(268);
		liveTable.getColumnModel().getColumn(1).setPreferredWidth(150);
		liveTable.getColumnModel().getColumn(1).setMaxWidth(150);
		liveTable.getColumnModel().getColumn(2).setPreferredWidth(150);
		liveTable.getColumnModel().getColumn(2).setMaxWidth(150);
		liveTable.getColumnModel().removeColumn(liveTable.getColumnModel().getColumn(3));

		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment( SwingConstants.CENTER );
		liveTable.getColumnModel().getColumn(0).setCellRenderer( centerRenderer );
		liveTable.getColumnModel().getColumn(1).setCellRenderer( centerRenderer );
		liveTable.getColumnModel().getColumn(2).setCellRenderer( centerRenderer );

		//refresh content of upcoming event table
		Date now = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(now);
		calendar.add(Calendar.DAY_OF_YEAR, 14);
		Date twoweekslater = calendar.getTime();
		Vector<Event> upcomingevents = facade.getEventsBetweenDates(now, twoweekslater);
		upcomingTableModel.setDataVector(null, upcomingcolnames);
		upcomingTableModel.setColumnCount(4);

		for(Event ev:upcomingevents) {
			Vector<Object> row = new Vector<Object>();

			row.add(ev.getCompetition().getName());
			row.add(ev.getDescription());
			row.add(ev.getEventDate());
			row.add(ev);
			upcomingTableModel.addRow(row);
		}
		upcomingTable.getColumnModel().getColumn(0).setPreferredWidth(200);
		upcomingTable.getColumnModel().getColumn(0).setMinWidth(200);
		upcomingTable.getColumnModel().getColumn(1).setPreferredWidth(200);
		upcomingTable.getColumnModel().getColumn(1).setMinWidth(200);
		upcomingTable.getColumnModel().getColumn(2).setPreferredWidth(200);
		upcomingTable.getColumnModel().getColumn(2).setMaxWidth(200);
		upcomingTable.getColumnModel().removeColumn(upcomingTable.getColumnModel().getColumn(3));

		upcomingTable.getColumnModel().getColumn(0).setCellRenderer( centerRenderer );
		upcomingTable.getColumnModel().getColumn(1).setCellRenderer( centerRenderer );
		upcomingTable.getColumnModel().getColumn(2).setCellRenderer( centerRenderer );

	}
}
