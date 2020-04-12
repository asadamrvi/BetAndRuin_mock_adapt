package gui.Panels;

import java.awt.Font;
import java.awt.SystemColor;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import businessLogic.BLFacade;
import domain.Bet;
import domain.Profile;
import gui.BetDetail;
import gui.MainGUI;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

@SuppressWarnings("serial")
public class ProfilePanel extends JPanel {

	private Profile profile;
	//private BLFacade facade = MainGUI.getBusinessLogic();
	private JTable table;
	private JButton editbet;
	private JButton DeleteBet;
	private JButton ViewFulldetail;
	private JScrollPane scrollPane;
	private ArrayList<Bet> bet_list;
	private DefaultTableModel TableModel;
	private Border border = BorderFactory.createLineBorder(Color.BLACK);

	/**
	 * Create the panel.
	 */
	public ProfilePanel() {

		setBackground(Color.WHITE);


		BLFacade facade = MainGUI.getBusinessLogic();
		profile = facade.getProfile();
		setLayout(null);

		JLabel lblUserProfile = new JLabel("User Profile");
		lblUserProfile.setBounds(43, 30, 96, 20);
		lblUserProfile.setFont(new Font("Tahoma", Font.BOLD, 16));
		add(lblUserProfile);

		JLabel lblUsername = new JLabel("Username:");
		lblUsername.setBounds(30, 87, 62, 16);
		add(lblUsername);
		
		JTextArea textArea = new JTextArea();
		textArea.setBounds(102, 84, 95, 22);
		textArea.setBorder(border);
		textArea.setBackground(SystemColor.menu);
		textArea.setEditable(false);
		add(textArea);

		JLabel lblAvailableMoney = new JLabel("Available money:");
		lblAvailableMoney.setBounds(232, 87, 95, 16);
		add(lblAvailableMoney);

		JTextArea textArea_3 = new JTextArea();
		textArea_3.setBounds(332, 85, 82, 20);
		textArea_3.setBorder(border);
		textArea_3.setBackground(SystemColor.menu);
		textArea_3.setEditable(false);
		add(textArea_3);

		JLabel lblName = new JLabel("Name:");
		lblName.setBounds(30, 112, 36, 16);
		add(lblName);

		JTextArea textArea_1 = new JTextArea();
		textArea_1.setBounds(102, 110, 95, 20);
		textArea_1.setBorder(border);
		textArea_1.setBackground(SystemColor.menu);
		textArea_1.setEditable(false);
		add(textArea_1);

		JLabel lblLastName = new JLabel("Surname:");
		lblLastName.setBounds(232, 112, 55, 16);
		add(lblLastName);

		JTextArea textArea_2 = new JTextArea();
		textArea_2.setBounds(332, 110, 82, 20);
		textArea_2.setBorder(border);
		textArea_2.setBackground(SystemColor.menu);
		textArea_2.setEditable(false);
		add(textArea_2);


		editbet = new JButton("Edit Bet Amount");
		editbet.setEnabled(false);
		editbet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name = JOptionPane.showInputDialog("Give New Bet");
				if (name.equals("") || name.equals(" ")) {}
				editbet.setEnabled(false);

				try
				{
					// float f = Float.valueOf(s.trim()).floatValue();
					float amount=new Float(name);
					int j=table.getSelectedRow();
					Bet b=bet_list.get(j);
					if (amount>b.getAmount() && amount>0 ) {
						facade.updatebets(facade.getLoggeduser(),b, amount);

						if (j>=0) {
							TableModel.setValueAt(amount, j, 1);
						}
						JOptionPane.showMessageDialog(null, "Bet Amount Updated successfully");

					}
					else
					{
						JOptionPane.showMessageDialog(null, "Stack Amount Must be Greater then Previous Amount");

					}


				}
				catch (NumberFormatException nfe)
				{
					JOptionPane.showMessageDialog(null, "Please Enter Valid Amount");
				}
			}
		});

		editbet.setBounds(243, 426, 141, 26);
		add(editbet);

		JLabel lblActiveBets = new JLabel("Active bets:");
		lblActiveBets.setBounds(30, 137, 66, 16);
		add(lblActiveBets);

		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				int j=table.getSelectedRow();
				ViewFulldetail.setEnabled(true);

				Bet b=bet_list.get(j);
				if (facade.Enable_or_not(b, 48)) {
					editbet.setEnabled(true);

				}
				//				else
				//				{
				//					JOptionPane.showMessageDialog(null, "There Must be Gap of 48 hours before bet take place");
				//	
				//				}
				//48 hours
				if (facade.Enable_or_not(b, 24)) {
					DeleteBet.setEnabled(true);

				}
				//				else
				//				{
				//					JOptionPane.showMessageDialog(null, "Sorry,There is less than 24 hours for bet to Place");
				//
				//				}
			}
		});
		table.setBounds(75, 189, 1, 1);
		add(table);



		scrollPane = new JScrollPane(table);
		scrollPane.setBounds(12, 165, 551, 249);
		add(scrollPane);

		ViewFulldetail = new JButton("Show Full Detail");
		ViewFulldetail.setEnabled(false);
		ViewFulldetail.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int j=table.getSelectedRow();
				Bet b=bet_list.get(j);
				BetDetail fulldetail=new BetDetail(b);
				//fulldetail.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

				fulldetail.setVisible(true);
			}
		});
		ViewFulldetail.setEnabled(false);
		ViewFulldetail.setBounds(87, 426, 141, 26);
		add(ViewFulldetail);



		DeleteBet = new JButton("Delete Bet");
		DeleteBet.setEnabled(false);
		DeleteBet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int j=table.getSelectedRow();
				DeleteBet.setEnabled(false);
				Bet b=bet_list.get(j);
				facade.remove_bet(facade.getLoggeduser(),b);
				TableModel.removeRow(j);
				JOptionPane.showMessageDialog(null, "Bet Removed successfully");
			}
		});
		DeleteBet.setBounds(402, 426, 122, 26);
		add(DeleteBet);

		JButton btnNewButton = new JButton("Delete Profile");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				facade.removeUser(facade.getLoggeduser().getID());
				facade.logOut();
				MainGUI mai=MainGUI.getInstance();
				mai.resetPanels();
				mai.visitorView();
			}
		});
		btnNewButton.setBounds(501, 87, 128, 26);
		add(btnNewButton);


		if(profile != null) {
			textArea.setText(profile.getID());
			textArea_2.setText(profile.getSurname());
			textArea_1.setText(profile.getName());
			textArea_3.setText(String.valueOf(facade.getCash()));

			bet_list=facade.getBets(facade.getLoggeduser());
			String[] columns = new String[] {
					"Status", "Stake","Placement Date"};
			Object data[][]=facade.getDAta(null, bet_list);
			TableModel =new DefaultTableModel(data,columns) {
				@Override
				public boolean isCellEditable(int row, int column) {
					//Only the third column
					return false;
				}
			};
			table.setModel(TableModel);

			TableColumnModel columnModel = table.getColumnModel();
			columnModel.getColumn(0).setPreferredWidth(40);
			columnModel.getColumn(1).setPreferredWidth(40);
			columnModel.getColumn(2).setPreferredWidth(140);
		}
	}
}




