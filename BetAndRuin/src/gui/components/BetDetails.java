package gui.components;

import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import java.awt.Font;
import java.text.SimpleDateFormat;
import java.awt.Color;
import javax.swing.border.MatteBorder;

import domain.Prediction;

public class BetDetails extends JPanel {

	/**
	 * Create the panel.
	 */
	public BetDetails(Prediction prediction) {
		setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		setBackground(new Color(255, 255, 255));
		setLayout(new MigLayout("", "[10:10:10][][3.00][5:5:5][grow][21.00][69.00][5:5:5][56.00,grow][20:19.00:20]", "[10:10:10][][20:20:20][][][][15:15:15]"));
		
		JLabel eventTitleLabel = new JLabel("Event:");
		eventTitleLabel.setFont(new Font("Source Sans Pro Light", Font.BOLD, 18));
		add(eventTitleLabel, "cell 1 1 2 1");
		
		JLabel eventLabel = new JLabel(prediction.getQuestion().getEvent().getDescription());
		eventLabel.setFont(new Font("Tahoma", Font.ITALIC, 15));
		add(eventLabel, "cell 3 1 2 1");
		
		JLabel oddsTitleLabel = new JLabel("Odds:\r\n");
		oddsTitleLabel.setFont(new Font("Source Sans Pro Light", Font.BOLD, 18));
		add(oddsTitleLabel, "cell 6 1");
		
		JLabel oddsLabel = new JLabel(Float.toString(prediction.getOdds()));
		oddsLabel.setFont(new Font("Source Sans Pro Black", Font.ITALIC, 18));
		add(oddsLabel, "cell 8 1");
		
		JLabel questionTitleLabel = new JLabel("Question:");
		questionTitleLabel.setFont(new Font("Source Sans Pro Light", Font.BOLD, 18));
		add(questionTitleLabel, "cell 1 3");
		
		JLabel questionLabel = new JLabel(prediction.getQuestion().getQuestion());
		questionLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		add(questionLabel, "cell 3 3 3 1");
		
		JLabel settlesOnTitleLabel = new JLabel("Settles on:\r\n");
		settlesOnTitleLabel.setFont(new Font("Source Sans Pro Light", Font.BOLD, 18));
		add(settlesOnTitleLabel, "cell 6 3");
		
		SimpleDateFormat df = new SimpleDateFormat("HH:mm dd/MM/yyyy");
		
		JLabel settleDateLabel = new JLabel(df.format(prediction.getQuestion().getEvent().getEndingdate()));
		settleDateLabel.setFont(new Font("Source Sans Pro Light", Font.BOLD, 17));
		add(settleDateLabel, "cell 8 3");
		
		JLabel answerTitleLabel = new JLabel("Answer:\r\n");
		answerTitleLabel.setFont(new Font("Source Sans Pro Light", Font.BOLD, 18));
		add(answerTitleLabel, "cell 1 5");
		
		JLabel answerLabel = new JLabel(prediction.getAnswer());
		answerLabel.setFont(new Font("Source Sans Pro Light", Font.PLAIN, 15));
		add(answerLabel, "cell 3 5 2 1");
		
		JLabel outcomeTitleLabel = new JLabel("Outcome:\r\n");
		outcomeTitleLabel.setFont(new Font("Source Sans Pro Light", Font.BOLD, 18));
		add(outcomeTitleLabel, "cell 6 5");
		
		JLabel outcomeLabel = new JLabel();
		if(prediction.getOutcome() == null) {
			outcomeLabel.setText("Unresolved");
		}
		else if(prediction.getOutcome()){
			outcomeLabel.setText("Won");
		}
		else {
			outcomeLabel.setText("Lost");
		}
		outcomeLabel.setFont(new Font("Source Sans Pro Light", Font.BOLD, 17));
		add(outcomeLabel, "cell 8 5");

	}

}
