package gui.components;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JPanel;
import domain.Competition;
import domain.Country;

@SuppressWarnings("serial")
public class CompetitionPanel extends JPanel{

	private Competition selectedcompetition;
	private final PropertyChangeSupport pcs = new PropertyChangeSupport( this );
	private ButtonGroup btngroup = new ButtonGroup();
	
	public CompetitionPanel(List<Competition> competitions) {
		setBackground(new Color(245, 245, 245));
		setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		Map<Country, JPanel> panelmap = new HashMap<Country, JPanel>();
		//create the buttons of the countries and the panels associated with each
		for(Competition c : competitions) {
			Country country = c.getCountry();

			//create button for the competition
			JButton compbutton = new FancyButton(c.getName(),new Color(210,210,210),new Color(169,169,169),new Color(130, 130, 130));
			compbutton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					setSelectedCompetition(c);
					btngroup.clearSelection();
					btngroup.setSelected(compbutton.getModel(), true);
				}
			});
			btngroup.add(compbutton);

			if(panelmap.get(country) == null) {
				JPanel innerpanel = new JPanel();
				innerpanel.setLayout(new BoxLayout(innerpanel,BoxLayout.Y_AXIS));
				JButton cbutton = new CountryButton(country.getString(), innerpanel);
				add(cbutton);

				innerpanel.add(compbutton);
				add (innerpanel);
				revalidate();
				repaint();
				innerpanel.setVisible(false);
				innerpanel.setEnabled(false);
				panelmap.put(c.getCountry(), innerpanel); //mapping used to assign all competitions to the corresponding countries on one iteration
			}
			else {
				JPanel innerpanel =  panelmap.get(country);
				innerpanel.add(compbutton);
			}
		}

	}
	
	public void setSelectedCompetition(Competition c) {
		 Competition old = selectedcompetition;
		 this.selectedcompetition = c;
		 this.pcs.firePropertyChange( "selectedcompetition", old, c );
	}
	
	public Competition getSelectedCompetition() {
		return selectedcompetition;
	}
	
    public void addPropertyChangeListener( PropertyChangeListener listener )
    {
        this.pcs.addPropertyChangeListener( listener );
    }

    public void removePropertyChangeListener( PropertyChangeListener listener )
    {
        this.pcs.removePropertyChangeListener( listener );
    }
}