package gui.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentListener;
import domain.Event;
import domain.Question;
import net.miginfocom.swing.MigLayout;

/**
 * JPanel used for holding bet information, with the event, question, selected answer, odds, betting amount and the potential gainings.
 * The panels are created with a fixed size.
 * 
 * @param ev		The event the bet has been placed on
 * @param q			The question the answer for the bet has been chosen
 * @param answer	The answer picked by the user
 * @param amount	Amount of money to bet
 * @return			JPanel with components incorporated that hold the bet information. 
 */
@SuppressWarnings("serial")
public class BetPanel extends JPanel{

	private Event event;
	private Question question;
	private String answer;
	private float odds;
	private BigDecimal stake;
	private BigDecimal minbet;

	private JLabel winningsLabel;
	private JLabel poswinLabel;
	private JNumericField stakeField;
	private DocumentListener stakeListener;

	public BetPanel(Event ev, Question q, String answer, BigDecimal minbet, BigDecimal stake){ 	

		setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.black));

		this.event = ev;
		this.question = q;
		this.stake = stake;
		this.minbet = minbet;

		String[] ans = answer.split(";");
		this.answer = ans[0];
		this.odds = Float.parseFloat(ans[1]);

		setBackground(Color.WHITE);
		setLayout(new MigLayout("", "[0.00][:47.00px:73.00px][:65.00:73][51.00:45:45][11.00][38.00][49.00][:20:20][167.00:n:2]", "[:27.00:27.00][:18.00:25px][-2.00][30:7.00:30,fill][:19.00:19.00]"));

		JLabel eventLabel = new JLabel(ev.getDescription());
		eventLabel.setFont(new Font("Source Sans Pro", Font.PLAIN, 15));
		add(eventLabel, "cell 1 0 3 1,alignx left,aligny center");

		JLabel lblOdds = new JLabel("Odds:");
		lblOdds.setFont(new Font("Source Sans Pro", Font.BOLD, 13));
		lblOdds.setHorizontalAlignment(SwingConstants.TRAILING);
		add(lblOdds, "cell 5 0,alignx left,aligny center");

		JLabel oddsLabel = new JLabel(ans[1]);
		oddsLabel.setBackground(Color.WHITE);
		oddsLabel.setFont(new Font("Source Sans Pro", Font.BOLD, 18));
		oddsLabel.setHorizontalAlignment(SwingConstants.LEFT);
		add(oddsLabel, "cell 6 0,alignx left,aligny center");

		JButton closeButton = new JButton((new ImageIcon("images/closex.png")));
		closeButton.setBorderPainted(false);
		closeButton.setFocusPainted(false);
		closeButton.setBackground(Color.WHITE);
		closeButton.setContentAreaFilled(false);
		closeButton.setHorizontalAlignment(SwingConstants.LEADING);

		add(closeButton, "cell 7 0 2 1,alignx left,growy");

		JLabel quetionLabel = new JLabel("<html>" + q.getQuestion() + "</html>");
		quetionLabel.setFont(new Font("Source Sans Pro", Font.PLAIN, 15));
		add(quetionLabel, "cell 1 1 3 2,alignx left,aligny bottom");
		
				stakeField = new JNumericField(7, JNumericField.DECIMAL);
				stakeField.setPrecision(2);
				stakeField.setAllowNegative(false);
				add(stakeField, "cell 5 1 3 2,growx,aligny bottom");
				stakeField.getDocument().addDocumentListener(stakeListener);

		JLabel answerLabel = new JLabel(ans[0]);
		answerLabel.setFont(new Font("Source Sans Pro", Font.BOLD, 15));
		add(answerLabel, "cell 1 3 2 1,alignx left,aligny bottom");

		poswinLabel = new JLabel("possible winnings: \r\n\r\n");
		poswinLabel.setHorizontalAlignment(SwingConstants.TRAILING);
		poswinLabel.setFont(new Font("Source Sans Pro", Font.PLAIN, 12));
		add(poswinLabel, "flowx,cell 5 3 3 1,alignx left,aligny bottom");

		JLabel minBetTitleLabel = new JLabel("Min.Bet:\r\n\r\n");
		minBetTitleLabel.setFont(new Font("Source Sans Pro", Font.PLAIN, 15));
		add(minBetTitleLabel, "cell 1 4,grow");

		JLabel minBetLabel = new JLabel(minbet.toString()+"€");
		minBetLabel.setFont(new Font("Source Sans Pro", Font.BOLD, 16));
		add(minBetLabel, "cell 2 4,alignx left");

		winningsLabel = new JLabel("");
		winningsLabel.setVisible(true);
		winningsLabel.setFont(new Font("Source Sans Pro", Font.BOLD, 16));
		add(winningsLabel, "cell 5 4 3 1,alignx left,aligny top");
		setStakes(minbet);
		repaint();
	}

	@Override
	protected void paintComponent(Graphics g) {
		setBackground(Color.white);
		super.paintComponent(g);
	}


	@Override
	public Dimension getPreferredSize()
	{
		Dimension d = new Dimension(getParent().getWidth(), this.getHeight());
		return d;
	}


	@Override
	public Dimension getMaximumSize()
	{
		Dimension d = new Dimension(getParent().getWidth(),  this.getHeight());
		return d;
	}

	public BigDecimal getStake() {
		return stake;
	}

	public void setStake(BigDecimal f) {
		stake = f;
		setWinnings(f);
	}

	public String getAnswer() {
		return answer;
	}

	public Event getEvent() {
		return this.event;
	}

	public Question getQuestion() {
		return this.question;
	}

	public BigDecimal getMinBet() {
		return minbet;
	}

	public float getOdds() {
		return odds;
	}

	public void setStakes(BigDecimal stakes) {
		stakeField.setText(stakes.toString());
		setStake(stakes);
		setWinnings(stakes);
	}

	public void showPossibleWinnings() {
		winningsLabel.setVisible(true);
		poswinLabel.setVisible(true);
	}

	public void hidePossibleWinnings() {
		winningsLabel.setVisible(false);
		poswinLabel.setVisible(false);
	}

	public void setWinnings(BigDecimal stakes) {
		DecimalFormat df = new DecimalFormat("#");
		df.setMaximumFractionDigits(2);
		winningsLabel.setText(df.format( stakes.doubleValue() * odds) + "€");
	}

	public void enableStakeListener() {
		stakeField.getDocument().addDocumentListener(stakeListener);
	}

	public void disableStakeListener() {
		stakeField.getDocument().removeDocumentListener(stakeListener);
	}


}