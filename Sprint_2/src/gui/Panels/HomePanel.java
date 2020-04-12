package gui.Panels;

import javax.swing.JPanel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import businessLogic.BLFacade;
import domain.Event;
import gui.MainGUI;
import gui.components.NonEditableTableModel;

@SuppressWarnings("serial")
public class HomePanel extends JPanel {
	
	private JTable liveTable;
	private String[] livecolnames = {"Event", "Time elapsed", "Score"};
	private NonEditableTableModel liveTableModel;
	
	private JTable upcomingTable;
	private String[] upcomingcolnames = {"Competition", "Event", "Date"};
	private NonEditableTableModel upcomingTableModel;
	private JTable resultsTable;
	

	
	BLFacade facade = MainGUI.getBusinessLogic();

	/**
	 * Create the panel.
	 */
	public HomePanel() {
		setBackground(Color.WHITE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{30, 156, 10, 104, 92, 40, 113, 0, 0, 30, 0};
		gridBagLayout.rowHeights = new int[]{20, 0, 33, 30, 20, 0, 0, 30, 0, 0, 30, 30, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JLabel logoLabel = new JLabel("");
		GridBagConstraints gbc_logoLabel = new GridBagConstraints();
		gbc_logoLabel.gridheight = 3;
		gbc_logoLabel.insets = new Insets(0, 0, 5, 5);
		gbc_logoLabel.gridx = 1;
		gbc_logoLabel.gridy = 1;
		add(logoLabel, gbc_logoLabel);
		logoLabel.setIcon(new ImageIcon("images/beticon.png"));
		
		JLabel lblBrand = new JLabel("BET & RUIN\r\n");
		lblBrand.setFont(new Font("Source Sans Pro Black", Font.BOLD, 25));
		GridBagConstraints gbc_lblBrand = new GridBagConstraints();
		gbc_lblBrand.gridheight = 2;
		gbc_lblBrand.anchor = GridBagConstraints.WEST;
		gbc_lblBrand.gridwidth = 2;
		gbc_lblBrand.insets = new Insets(0, 0, 5, 5);
		gbc_lblBrand.gridx = 2;
		gbc_lblBrand.gridy = 2;
		add(lblBrand, gbc_lblBrand);
		
		JLabel lblLive = new JLabel("Live:");
		lblLive.setFont(new Font("Source Code Pro ExtraLight", Font.PLAIN, 20));
		GridBagConstraints gbc_lblLive = new GridBagConstraints();
		gbc_lblLive.gridwidth = 4;
		gbc_lblLive.anchor = GridBagConstraints.WEST;
		gbc_lblLive.insets = new Insets(0, 0, 5, 5);
		gbc_lblLive.gridx = 1;
		gbc_lblLive.gridy = 5;
		add(lblLive, gbc_lblLive);
		
		JLabel lblNews = new JLabel("News:");
		lblNews.setFont(new Font("Source Code Pro ExtraLight", Font.PLAIN, 20));
		GridBagConstraints gbc_lblNews = new GridBagConstraints();
		gbc_lblNews.gridwidth = 3;
		gbc_lblNews.anchor = GridBagConstraints.WEST;
		gbc_lblNews.insets = new Insets(0, 0, 5, 5);
		gbc_lblNews.gridx = 6;
		gbc_lblNews.gridy = 5;
		add(lblNews, gbc_lblNews);
		
		JScrollPane liveScrollPane = new JScrollPane();
		GridBagConstraints gbc_liveScrollPane = new GridBagConstraints();
		gbc_liveScrollPane.gridwidth = 4;
		gbc_liveScrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_liveScrollPane.fill = GridBagConstraints.BOTH;
		gbc_liveScrollPane.gridx = 1;
		gbc_liveScrollPane.gridy = 6;
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
		GridBagConstraints gbc_panelNews = new GridBagConstraints();
		gbc_panelNews.gridwidth = 3;
		gbc_panelNews.insets = new Insets(0, 0, 5, 5);
		gbc_panelNews.fill = GridBagConstraints.BOTH;
		gbc_panelNews.gridx = 6;
		gbc_panelNews.gridy = 6;
		add(panelNews, gbc_panelNews);
		
		JLabel lblUpcomingEvents = new JLabel("Upcoming events:");
		lblUpcomingEvents.setFont(new Font("Source Code Pro ExtraLight", Font.PLAIN, 20));
		lblUpcomingEvents.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_lblUpcomingEvents = new GridBagConstraints();
		gbc_lblUpcomingEvents.gridwidth = 4;
		gbc_lblUpcomingEvents.anchor = GridBagConstraints.WEST;
		gbc_lblUpcomingEvents.insets = new Insets(0, 0, 5, 5);
		gbc_lblUpcomingEvents.gridx = 1;
		gbc_lblUpcomingEvents.gridy = 8;
		add(lblUpcomingEvents, gbc_lblUpcomingEvents);
		
		JLabel lblResults = new JLabel("Results:");
		lblResults.setFont(new Font("Source Code Pro ExtraLight", Font.PLAIN, 20));
		GridBagConstraints gbc_lblResults = new GridBagConstraints();
		gbc_lblResults.gridwidth = 3;
		gbc_lblResults.anchor = GridBagConstraints.WEST;
		gbc_lblResults.insets = new Insets(0, 0, 5, 5);
		gbc_lblResults.gridx = 6;
		gbc_lblResults.gridy = 8;
		add(lblResults, gbc_lblResults);
		
		JScrollPane upcomingScrollPane = new JScrollPane();
		GridBagConstraints gbc_upcomingScrollPane = new GridBagConstraints();
		gbc_upcomingScrollPane.gridwidth = 4;
		gbc_upcomingScrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_upcomingScrollPane.fill = GridBagConstraints.BOTH;
		gbc_upcomingScrollPane.gridx = 1;
		gbc_upcomingScrollPane.gridy = 9;
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
		gbc_resultsScrollPane.gridy = 9;
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
			row.add(differenceInMinutes(now, ev.getEventdate()));
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
			row.add(ev.getEventdate());
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
