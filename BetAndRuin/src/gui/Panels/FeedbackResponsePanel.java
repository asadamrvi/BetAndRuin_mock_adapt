package gui.Panels;

import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import javax.swing.JTable;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import javax.swing.JTextPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableRowSorter;
import com.toedter.calendar.JDateChooser;
import businessLogic.BLFacade;
import domain.Feedback;
import domain.Feedback.FeedbackType;
import domain.FeedbackRecord;
import domain.FeedbackRecordContainer;
import gui.MainGUI;
import gui.feedbackResponseGUI;
import gui.components.FancyButton;
import gui.components.NonEditableTableModel;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import javax.swing.JSeparator;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.UIManager;

@SuppressWarnings("serial")
public class FeedbackResponsePanel extends JPanel {

	private JTable feedbackTable;
	private NonEditableTableModel feedbackTableModel;

	private FeedbackRecord selectedfbr;

	private FancyButton markAsReadButton;
	private FancyButton responseButton;

	private JLabel senderLabel; 
	private JLabel emailLabel;
	private JLabel issuedescriptionLabel;
	private JTextPane detailsTextPane;
	private JLabel fileLabel;

	private JComboBox<FeedbackType> typeComboBox; 
	@SuppressWarnings("rawtypes")
	private JComboBox readComboBox;

	private JDateChooser datechooser;
	private Date selecteddate;
	
	//private String[] type = {"All","Suggestion", "Problem", "Question"};
	private String[] read = {"All", "Read", "Not read"};
	private String[] columnnames =new String[] {"Type", "Issue", "Sender", "Date", "Read"};

	//private DefaultTableCellRenderer whiteRenderer = null;
	//private DefaultTableCellRenderer readFeedbackRenderer = null;

	BLFacade facade = MainGUI.getBusinessLogic();

	/**
	 * Create the panel.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public FeedbackResponsePanel() {
		setBackground(Color.WHITE);
		setLayout(new MigLayout("", "[30:30][100:100:100][5:5:5][80:80:80][10:10,grow][30:30][][150][10:10:10][15:15][35px:n][][298.00,grow][30:30:30]", "[30:30:30][][3:3][30:30:30][][][][300,grow][5:5:5][35:35:35][30:30:30]"));

		JLabel titleLabel = new JLabel("Feedback");
		titleLabel.setFont(new Font("Source Code Pro ExtraLight", Font.BOLD, 28));
		add(titleLabel, "cell 1 1 8 1,alignx left");



		JSeparator separator = new JSeparator();
		add(separator, "cell 1 2 8 1,growx,aligny top");
		separator.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.BLACK));

		typeComboBox = new JComboBox(Feedback.FeedbackType.values());
		typeComboBox.setForeground(new Color(0, 0, 0));
		add(typeComboBox, "cell 1 4,growx");
		typeComboBox.addActionListener(new ActionListener() {		
			@Override
			public void actionPerformed(ActionEvent e) {
				loadFeedback();	
			}
		});



		readComboBox = new JComboBox(read);
		add(readComboBox, "cell 3 4,grow");
		readComboBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				loadFeedback();
			}
		});

		JLabel dateLabel = new JLabel("Date:");
		dateLabel.setFont(new Font("Source Sans Pro", Font.BOLD, 15));
		add(dateLabel, "cell 5 4 2 1,alignx right");

		datechooser = new JDateChooser();
		add(datechooser, "cell 7 4,grow");
		datechooser.addPropertyChangeListener(new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent evt) {	
				
				if(datechooser.getDate() != null) {
					Calendar cl = Calendar.getInstance();
					cl.setTime(datechooser.getDate());
					cl.set(Calendar.HOUR_OF_DAY,0);
					cl.set(Calendar.MINUTE,0);
					cl.set(Calendar.SECOND,0);
					cl.set(Calendar.MILLISECOND,0);
					selecteddate = cl.getTime();
				}	
				else {
					selecteddate = null;
				}
				
				loadFeedback();
			}
		});


		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane, "cell 1 6 8 2,grow");

		feedbackTable = new JTable() {
			@Override
			public Class getColumnClass(int column) {
				if(column == 4) {
					return Boolean.class;
				}
				else {
					return (getValueAt(0, column).getClass());
				}
			}

			@Override
			public boolean isCellEditable(int row, int column) {
				return (column == 4);
			}

			@Override
			public Component prepareRenderer(TableCellRenderer renderer, int row, int column)
			{
				Component c = super.prepareRenderer(renderer, row, column);
				FeedbackRecord fbr =  (FeedbackRecord)feedbackTableModel.getValueAt(row, 5);
				
		
				if(feedbackTable.getSelectedRow() == row){
					c.setBackground(UIManager.getColor("Table.selectionBackground"));
				}
				else if(fbr.isRead()) {
					c.setBackground(new Color(255,248,148));
				}	
				else {
					c.setBackground(Color.white);
				}
				return c;
			}


		};
		feedbackTable.setFont(new Font("Source Sans Pro", Font.ITALIC, 16));
		feedbackTable.setRowHeight(40);
		feedbackTable.setFocusable(false);
		feedbackTable.getTableHeader().setReorderingAllowed(false);
		feedbackTable.getTableHeader().setResizingAllowed(false);	
		feedbackTable.getTableHeader().setFont(new Font("Source sans Pro", Font.BOLD, 16));
		feedbackTable.getTableHeader().setBackground(new Color(255,255,255));
		feedbackTableModel = new NonEditableTableModel(null, columnnames);
		feedbackTable.setModel(feedbackTableModel);
		feedbackTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {		
			@Override
			public void valueChanged(ListSelectionEvent e) {
				int i = feedbackTable.getSelectedRow();
				if(i != -1) {
					selectedfbr = (FeedbackRecord)feedbackTableModel.getValueAt(i, 5);

					Feedback fb = selectedfbr.getFeedback();
					senderLabel.setText(fb.getName());
					emailLabel.setText(fb.getEmail());
					issuedescriptionLabel.setText(fb.getSummary());
					detailsTextPane.setText(fb.getDetails());		

					fileLabel.setText(fb.getFilename());
					fileLabel.setForeground(new Color(0,0,255));
					responseButton.setEnabled(true);
				}

			}
		});
		scrollPane.setViewportView(feedbackTable);

		TableRowSorter<NonEditableTableModel> sorter = new TableRowSorter<NonEditableTableModel>(feedbackTableModel);
		feedbackTable.setRowSorter(sorter);	

		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.black));
		panel.setBackground(new Color(240, 248, 255));
		add(panel, "cell 10 1 3 9,grow");
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{35, 0, 70, 43, 0, 24, 156, 29, 30, 0};
		gbl_panel.rowHeights = new int[]{30, 0, 25, 0, 27, 0, 261, 0, 49, 30, 0};
		gbl_panel.columnWeights = new double[]{0.0, 0.0, 0.0, 1.0, 0.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);

		JLabel lblSender = new JLabel("Sender:");
		lblSender.setFont(new Font("Source Code Pro Medium", Font.BOLD, 16));
		GridBagConstraints gbc_lblSender = new GridBagConstraints();
		gbc_lblSender.anchor = GridBagConstraints.WEST;
		gbc_lblSender.gridwidth = 3;
		gbc_lblSender.insets = new Insets(0, 0, 5, 5);
		gbc_lblSender.gridx = 1;
		gbc_lblSender.gridy = 1;
		panel.add(lblSender, gbc_lblSender);

		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setFont(new Font("Source Code Pro Medium", Font.BOLD, 16));
		GridBagConstraints gbc_lblEmail = new GridBagConstraints();
		gbc_lblEmail.anchor = GridBagConstraints.WEST;
		gbc_lblEmail.gridwidth = 4;
		gbc_lblEmail.insets = new Insets(0, 0, 5, 5);
		gbc_lblEmail.gridx = 4;
		gbc_lblEmail.gridy = 1;
		panel.add(lblEmail, gbc_lblEmail);

		senderLabel = new JLabel("");
		GridBagConstraints gbc_senderLabel = new GridBagConstraints();
		gbc_senderLabel.anchor = GridBagConstraints.WEST;
		gbc_senderLabel.gridwidth = 3;
		gbc_senderLabel.insets = new Insets(0, 0, 5, 5);
		gbc_senderLabel.gridx = 1;
		gbc_senderLabel.gridy = 2;
		panel.add(senderLabel, gbc_senderLabel);

		emailLabel = new JLabel("");
		GridBagConstraints gbc_emailLabel = new GridBagConstraints();
		gbc_emailLabel.anchor = GridBagConstraints.WEST;
		gbc_emailLabel.gridwidth = 3;
		gbc_emailLabel.insets = new Insets(0, 0, 5, 5);
		gbc_emailLabel.gridx = 4;
		gbc_emailLabel.gridy = 2;
		panel.add(emailLabel, gbc_emailLabel);

		JLabel issueLabel = new JLabel("Issue:");
		issueLabel.setFont(new Font("Source Code Pro Medium", Font.BOLD, 17));
		GridBagConstraints gbc_issueLabel = new GridBagConstraints();
		gbc_issueLabel.anchor = GridBagConstraints.WEST;
		gbc_issueLabel.gridwidth = 7;
		gbc_issueLabel.insets = new Insets(0, 0, 5, 5);
		gbc_issueLabel.gridx = 1;
		gbc_issueLabel.gridy = 3;
		panel.add(issueLabel, gbc_issueLabel);

		issuedescriptionLabel = new JLabel("");
		GridBagConstraints gbc_issuedescriptionLabel = new GridBagConstraints();
		gbc_issuedescriptionLabel.anchor = GridBagConstraints.WEST;
		gbc_issuedescriptionLabel.gridwidth = 7;
		gbc_issuedescriptionLabel.insets = new Insets(0, 0, 5, 5);
		gbc_issuedescriptionLabel.gridx = 1;
		gbc_issuedescriptionLabel.gridy = 4;
		panel.add(issuedescriptionLabel, gbc_issuedescriptionLabel);

		JLabel detailsLabel = new JLabel("Details:");
		detailsLabel.setFont(new Font("Source Code Pro Medium", Font.BOLD, 16));
		GridBagConstraints gbc_detailsLabel = new GridBagConstraints();
		gbc_detailsLabel.anchor = GridBagConstraints.WEST;
		gbc_detailsLabel.gridwidth = 7;
		gbc_detailsLabel.insets = new Insets(0, 0, 5, 5);
		gbc_detailsLabel.gridx = 1;
		gbc_detailsLabel.gridy = 5;
		panel.add(detailsLabel, gbc_detailsLabel);

		detailsTextPane = new JTextPane();
		detailsTextPane.setEditable(false);
		detailsTextPane.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.BLACK));
		GridBagConstraints gbc_detailsTextPane = new GridBagConstraints();
		gbc_detailsTextPane.gridwidth = 7;
		gbc_detailsTextPane.insets = new Insets(0, 0, 5, 5);
		gbc_detailsTextPane.fill = GridBagConstraints.BOTH;
		gbc_detailsTextPane.gridx = 1;
		gbc_detailsTextPane.gridy = 6;
		panel.add(detailsTextPane, gbc_detailsTextPane);

		JLabel fileTitleLabel = new JLabel("Attached file:");
		fileTitleLabel.setFont(new Font("Source Sans Pro", Font.BOLD, 14));
		GridBagConstraints gbc_fileTitleLabel = new GridBagConstraints();
		gbc_fileTitleLabel.insets = new Insets(0, 0, 5, 5);
		gbc_fileTitleLabel.gridx = 1;
		gbc_fileTitleLabel.gridy = 7;
		panel.add(fileTitleLabel, gbc_fileTitleLabel);

		fileLabel = new JLabel("");
		fileLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		GridBagConstraints gbc_fileLabel = new GridBagConstraints();
		gbc_fileLabel.anchor = GridBagConstraints.NORTHWEST;
		gbc_fileLabel.gridwidth = 4;
		gbc_fileLabel.insets = new Insets(0, 0, 5, 5);
		gbc_fileLabel.gridx = 1;
		gbc_fileLabel.gridy = 8;
		panel.add(fileLabel, gbc_fileLabel);
		fileLabel.addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mouseExited(MouseEvent e) {
				Feedback fb = selectedfbr.getFeedback();
				if(selectedfbr != null && fb.getFilename() != null) {
					fileLabel.setForeground(new Color(0,0,255));
					fileLabel.setText(fb.getFilename());
				}	
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				Feedback fb = selectedfbr.getFeedback();
				if(selectedfbr != null && fb.getFilename() != null) {			
					fileLabel.setText("<HTML><BODY><a href=\"\">" + fb.getFilename() + "</BODY></HTML>");
				}	
			}

			@Override
			public void mouseClicked(MouseEvent e) {

				Feedback fb = selectedfbr.getFeedback();

				JFileChooser fc = new JFileChooser();
				fc.setSelectedFile(new File(fb.getFilename()));	
				int retval = fc.showOpenDialog(null);

				if(retval == JFileChooser.APPROVE_OPTION) {
					byte[] filedata = fb.getFiledata();
					FileOutputStream fos;
					try {
						fos = new FileOutputStream(fc.getSelectedFile());
						fos.write(filedata);
						fos.flush();
						fos.close();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}
		});


		responseButton = new FancyButton("Write response",new Color(51,51,51),new Color(170,170,170),new Color(150,150,150));
		responseButton.setForeground(Color.WHITE);
		responseButton.setFont(new Font("Source Sans Pro Light", Font.BOLD, 20));
		responseButton.setFocusPainted(false);
		GridBagConstraints gbc_responseButton = new GridBagConstraints();
		gbc_responseButton.fill = GridBagConstraints.BOTH;
		gbc_responseButton.insets = new Insets(0, 0, 5, 5);
		gbc_responseButton.gridx = 6;
		gbc_responseButton.gridy = 8;
		panel.add(responseButton, gbc_responseButton);
		responseButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Feedback fb = selectedfbr.getFeedback();
				JDialog jd = new feedbackResponseGUI(fb.getEmail());
				jd.setVisible(true);
			}
		});
		responseButton.setEnabled(false);

		markAsReadButton = new FancyButton("Mark selected as read",new Color(51,51,51),new Color(170,170,170),new Color(150,150,150));
		markAsReadButton.setForeground(Color.WHITE);
		add(markAsReadButton, "cell 7 9,grow");
		markAsReadButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				List<FeedbackRecord> updatedrecords = new ArrayList<FeedbackRecord>();

				for(int i = 0; i<feedbackTableModel.getRowCount(); i++) {
					FeedbackRecord fbr = (FeedbackRecord)feedbackTableModel.getValueAt(i, 5);
					fbr.setRead(true);
					updatedrecords.add(fbr);
				}
				facade.updateFeedBackRecords(updatedrecords);
				loadFeedback();
			}
		});

		loadFeedback();
	}

	/*
	 * Returns a boolean value corresponding to each of the options in the read combobox
	 * All -> null, Read -> true, Not Read -> false.
	 */
	public Boolean getReadAsBoolean() {
		int readindex = readComboBox.getSelectedIndex();
		Boolean read = null;
		switch(readindex) {
		case 0:
			read = null;
			break;

		case 1:
			read = true;
			break;

		case 2:
			read = false;
			break;
		}
		return read;
	}

	public void loadFeedback() {
		FeedbackType type = (FeedbackType)typeComboBox.getSelectedItem();
		Boolean read = getReadAsBoolean();
		
		feedbackTableModel.setDataVector(null, columnnames);
		feedbackTableModel.setColumnCount(6);
		Vector<FeedbackRecordContainer> fbrcontainers = facade.getFeedbackRecords(MainGUI.getInstance().getLoggeduser());

		for( FeedbackRecordContainer fbrc: fbrcontainers) {
			FeedbackRecord fbr = fbrc.getFbr();
			Feedback fb = fbr.getFeedback(); 
			
			Calendar cl = Calendar.getInstance();
			cl.setTime(fb.getSubmissiondate());
			cl.set(Calendar.HOUR_OF_DAY,0);
			cl.set(Calendar.MINUTE,0);
			cl.set(Calendar.SECOND,0);
			cl.set(Calendar.MILLISECOND,0);
			Date fbdate = cl.getTime();
			
			if(read == null) {
				if(selecteddate == null) {
					if(fb.getFbtype().equals(type)){
						addRowtoTable(fbr);
					}
				}
				else {
					if(fb.getFbtype().equals(type) && fbdate.equals(selecteddate)){
						addRowtoTable(fbr);
					}
				}
			}
			else {
				if(selecteddate == null) {
					if(fb.getFbtype().equals(type) && fbr.isRead() == read){
						addRowtoTable(fbr);
					}
				}
				else {
					if(fb.getFbtype().equals(type) && fbr.isRead() == read && fbdate.equals(selecteddate)){
						addRowtoTable(fbr);
					}
				}
			}
		}

		feedbackTable.getColumnModel().getColumn(0).setPreferredWidth(90);
		feedbackTable.getColumnModel().getColumn(0).setMinWidth(90);
		feedbackTable.getColumnModel().getColumn(0).setMaxWidth(90);
		feedbackTable.getColumnModel().getColumn(1).setPreferredWidth(110);
		feedbackTable.getColumnModel().getColumn(1).setMinWidth(110);
		feedbackTable.getColumnModel().getColumn(1).setMaxWidth(999999);
		feedbackTable.getColumnModel().getColumn(2).setMinWidth(75);
		feedbackTable.getColumnModel().getColumn(2).setMaxWidth(75);
		feedbackTable.getColumnModel().getColumn(3).setPreferredWidth(110);
		feedbackTable.getColumnModel().getColumn(3).setMinWidth(110);
		feedbackTable.getColumnModel().getColumn(3).setMaxWidth(110);
		feedbackTable.getColumnModel().getColumn(4).setPreferredWidth(40);
		feedbackTable.getColumnModel().getColumn(4).setMinWidth(40);
		feedbackTable.getColumnModel().getColumn(4).setMaxWidth(40);
		feedbackTable.getColumnModel().removeColumn(feedbackTable.getColumnModel().getColumn(5));
	}
	
	public void addRowtoTable(FeedbackRecord fbr) {
		Vector<Object> row = new Vector<Object>();
		Feedback fb = fbr.getFeedback(); 
		
		row.add(fb.getFbtype());
		row.add(fb.getSummary());
		row.add(fb.getName());
		row.add(fb.getSubmissiondate());
		row.add(false);
		row.add(fbr);
		feedbackTableModel.addRow(row);	
	}
}
