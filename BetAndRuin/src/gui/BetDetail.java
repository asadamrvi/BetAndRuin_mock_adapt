package gui;

import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import businessLogic.BLFacade;
import domain.Bet;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.util.Locale;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class BetDetail extends JDialog {
	
	Bet bet;
	private BLFacade facade = MainGUI.getBusinessLogic();

	JScrollPane scrollPane;

	private final JPanel contentPanel = new JPanel();
	Border border = BorderFactory.createLineBorder(Color.BLACK);
	private JTable table;
	private DefaultTableModel TableModel;

	/**
	 * Create the dialog.
	 */
	public BetDetail(Bet bet) {
		setTitle("Full Bet Detail");

		this.bet=bet;
			
		DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM,Locale.getDefault());
		
		setBounds(100, 100, 728, 411);
		getContentPane().setLayout(null);
		contentPanel.setBounds(0, 0, 712, 330);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel);
		contentPanel.setLayout(null);
		
		JLabel Status = new JLabel("Status");
		Status.setBounds(56, 81, 60, 22);
		contentPanel.add(Status);
		
		JTextArea StatusText = new JTextArea();
		StatusText.setEditable(false);
		StatusText.setBorder(border);
		StatusText.setText(bet.StatustoString());
		StatusText.setBounds(138, 81, 118, 22);
		contentPanel.add(StatusText);
		
		JLabel typeLabel = new JLabel("Bet Type");
		typeLabel.setBounds(56, 46, 71, 22);
		
		contentPanel.add(typeLabel);
		
		JTextArea txtrBetType = new JTextArea();
		txtrBetType.setEditable(false);
		txtrBetType.setBorder(border);
	 txtrBetType.setText(bet.getType().toString());
		txtrBetType.setBounds(138, 46, 118, 22);
		contentPanel.add(txtrBetType);
		
		JLabel Stakelabel = new JLabel("Stake");
		Stakelabel.setBounds(56, 114, 46, 22);
		contentPanel.add(Stakelabel);
		
		JTextArea StaketextArea = new JTextArea();
		StaketextArea.setEditable(false);
		StaketextArea.setBorder(border);
		StaketextArea.setText(Float.toString(bet.getStake()));
		StaketextArea.setBounds(138, 114, 118, 22);
		contentPanel.add(StaketextArea);
		
		JLabel placementDate = new JLabel("Placement Date");
		placementDate.setBounds(357, 46, 100, 22);
		contentPanel.add(placementDate);
		
		JTextArea PlacementDatetext = new JTextArea();
		PlacementDatetext.setBorder(border);
		PlacementDatetext.setText(df.format(bet.getPlacementdate()));
		PlacementDatetext.setBounds(477, 46, 198, 22);
		contentPanel.add(PlacementDatetext);
		
		JLabel resolveLabel = new JLabel("Resolving Date");
		resolveLabel.setBounds(357, 81, 100, 22);
		contentPanel.add(resolveLabel);
		
		JTextArea txtrResolvingDate = new JTextArea();
		System.out.println(bet.getPredictions());
         txtrResolvingDate.setEditable(false);
		txtrResolvingDate.setBorder(border);
		//txtrResolvingDate.setBorder(border);
		txtrResolvingDate.setText(df.format(bet.getResolvingdate()));
		txtrResolvingDate.setForeground(Color.BLUE);
		txtrResolvingDate.setBounds(477, 81, 198, 22);
		contentPanel.add(txtrResolvingDate);
		String[] columns = new String[] {
	            "Number", "Question", "Answer","Odds","Outcome"
	        };
		Object[][] data=facade.getDAta(bet, null);
		TableModel =new DefaultTableModel(data,columns) {

			   @Override
			   public boolean isCellEditable(int row, int column) {
			       //Only the third column
			       return false;
			   }
			};
        table = new JTable(TableModel);
		table.setBounds(126, 206, 1, 1);
		contentPanel.add(table);
		TableColumnModel columnModel = table.getColumnModel();
		columnModel.getColumn(0).setPreferredWidth(20);
		columnModel.getColumn(1).setPreferredWidth(200);
		columnModel.getColumn(2).setPreferredWidth(40);
		columnModel.getColumn(3).setPreferredWidth(40);
		columnModel.getColumn(4).setPreferredWidth(20);
		
		 scrollPane = new JScrollPane(table);
		scrollPane.setBounds(56, 165, 618, 152);
		contentPanel.add(scrollPane);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBounds(0, 330, 712, 42);
			getContentPane().add(buttonPane);
			buttonPane.setLayout(null);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						dispose();
					}
				});
				okButton.setBounds(312, 5, 101, 23);
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
		}
	}



	public Bet getB() {
		return bet;
	}



	public void setB(Bet b) {
		this.bet = b;
	}
}
