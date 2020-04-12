package gui.components;

import javax.swing.JPanel;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import domain.BetType;

import javax.swing.JTextField;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import net.miginfocom.swing.MigLayout;

@SuppressWarnings("serial")
public class multibetOption extends JPanel {
	
	private JNumericField stakeField;
	
	private float stake;
	private int combinations;
	private BetType type;	
	private  float winnings;
	private JLabel priceLabel;
	private JLabel posWinLabel;
	
	/**
	 * Create the panel.
	 */
	public multibetOption( BetType type, int combinations) {
		this.type = type;
		this.combinations = combinations;
		setBackground(new Color(240, 255, 255));
		setLayout(new MigLayout("", "[33.00px][108.00][50px][91.00px]", "[19.00px][25px:8.00px:25px]"));
		JLabel typeLabel = new JLabel(type.name());
		typeLabel.setFont(new Font("Source Sans Pro", Font.PLAIN, 14));
		add(typeLabel, "cell 0 0 2 1,grow");
		
		stakeField = new JNumericField(7, JNumericField.DECIMAL);
		stakeField.setPrecision(2);
		stakeField.setAllowNegative(false);
		add(stakeField, "cell 3 0,grow");
		stakeField.setColumns(10);
		setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.black));
		stakeField.getDocument().addDocumentListener(new stakeListener());
		
		JLabel combinationsLabel = new JLabel("x" + combinations);
		combinationsLabel.setFont(new Font("Source Sans Pro", Font.PLAIN, 15));
		combinationsLabel.setHorizontalAlignment(SwingConstants.TRAILING);
		add(combinationsLabel, "cell 2 0,grow");
		
		JLabel priceTitleLabel = new JLabel("price:");
		priceTitleLabel.setVerticalAlignment(SwingConstants.TOP);
		priceTitleLabel.setFont(new Font("Source Sans Pro", Font.PLAIN, 12));
		add(priceTitleLabel, "cell 0 1,aligny top");
		
		priceLabel = new JLabel("0€");
		priceLabel.setFont(new Font("Source Sans Pro", Font.BOLD, 13));
		add(priceLabel, "cell 1 1,alignx left,aligny top");
		
		JLabel posWinTitleLabel = new JLabel("pos. winnings:\r\n");
		posWinTitleLabel.setFont(new Font("Source Sans Pro", Font.PLAIN, 12));
		add(posWinTitleLabel, "cell 2 1,aligny top");
		
		posWinLabel = new JLabel("0€");
		posWinLabel.setFont(new Font("Source Sans Pro", Font.BOLD, 13));
		add(posWinLabel, "cell 3 1,alignx right,aligny top");

	}

	public float getStake() {
		return stake;
	}


	public void setStake(float stake) {
		this.stake = stake;
	}

	
	public JTextField getBetField() {
		return stakeField;
	}


	public void setBetField(JNumericField betField) {
		this.stakeField = betField;
	}


	public int getCombinations() {
		return combinations;
	}


	public void setCombinations(int combinations) {
		this.combinations = combinations;
	}
	
	public float getWinnings() {
		return winnings;
	}
	
	public void setWinnings(float winnings) {
		this.winnings = winnings;
	}
	
	public BetType getType() {
		return type;
	}


	public void setType(BetType type) {
		this.type = type;
	}
	
	@Override
	public Dimension getPreferredSize()
	{		
		Dimension d = new Dimension(getParent().getWidth(), 36); 
		return d;
	}
	
	@Override
	public Dimension getMaximumSize()
	{		
		Dimension d = new Dimension(getParent().getWidth(), 36);
		return d;
	}
	
	public class stakeListener implements DocumentListener{

		@Override
		public void insertUpdate(DocumentEvent e) {
			try {
				stake = stakeField.getFloat();
				//posWinLabel.setText(text);
				priceLabel.setText(Float.toString(stake*combinations));
			}
			catch (NumberFormatException n) {
				stake = 0;
			}
			
		}

		@Override
		public void removeUpdate(DocumentEvent e) {
			try {
				stake = stakeField.getFloat();
				//posWinLabel.setText(text);
				priceLabel.setText(Float.toString(stake*combinations));
			}
			catch (NumberFormatException n) {
				stake = 0;
			}
			
		}

		@Override
		public void changedUpdate(DocumentEvent e) {
			try {
				stake = stakeField.getFloat();
				//posWinLabel.setText();
				priceLabel.setText(Float.toString(stake*combinations));
			}
			catch (NumberFormatException n) {
				stake = 0;
			}
			
		}
		
		
	}
}
