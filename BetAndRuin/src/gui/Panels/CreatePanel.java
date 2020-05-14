package gui.Panels;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import gui.Panels.subpanels.BettingHistoryPanel;
import gui.Panels.subpanels.CreateCompetitionPanel;
import gui.Panels.subpanels.CreateEventPanel;
import gui.Panels.subpanels.CreateQuestionPanel;
import gui.Panels.subpanels.OpenBetsPanel;
import gui.components.FancyButton;

@SuppressWarnings("serial")
public class CreatePanel extends JPanel {

	private FancyButton createEvent;
	private FancyButton createQuestion;
	private FancyButton createCompetition;
	private JPanel contentPanel;
	/**
	 * Create the panel.
	 */
	public CreatePanel() {

		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{721, 0};
		gridBagLayout.rowHeights = new int[]{50, 430, 0};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);

		JPanel topMenuPanel = new JPanel();
		GridBagConstraints gbc_topMenuPanel = new GridBagConstraints();
		gbc_topMenuPanel.fill = GridBagConstraints.BOTH;
		gbc_topMenuPanel.insets = new Insets(0, 0, 0, 0);
		gbc_topMenuPanel.gridx = 0;
		gbc_topMenuPanel.gridy = 0;
		add(topMenuPanel, gbc_topMenuPanel);

		createQuestion = new FancyButton("Create Question",new Color(51,51,51),new Color(170,170,170),new Color(150,150,150));
		createQuestion.setBackground(new Color(0, 0, 0));
		createQuestion.setForeground(new Color(255, 255, 255));
		createQuestion.setFont(new Font("Source Code Pro Light", Font.BOLD, 18));
		createQuestion.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!createQuestion.isSelected()) {
					createQuestion.setSelected(true);
					createEvent.setSelected(false);
					createCompetition.setSelected(false);
					contentPanel.removeAll();
					contentPanel.add(new CreateQuestionPanel());
					contentPanel.repaint();
					contentPanel.revalidate();
				}		
			}
		});
		topMenuPanel.setLayout(new GridLayout(0, 3, 0, 0));
		topMenuPanel.add(createQuestion);

		createEvent = new FancyButton("Create Event",new Color(51,51,51),new Color(170,170,170),new Color(150,150,150));
		createEvent.setForeground(new Color(255, 255, 255));
		createEvent.setBackground(new Color(0, 0, 0));
		createEvent.setFont(new Font("Source Code Pro Light", Font.BOLD, 18));
		createEvent.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!createEvent.isSelected()) {
					createQuestion.setSelected(false);
					createEvent.setSelected(true);
					createCompetition.setSelected(false);
					contentPanel.removeAll();
					contentPanel.add(new CreateEventPanel());
					contentPanel.repaint();
					contentPanel.revalidate();
				}		
			}
		});
		topMenuPanel.add(createEvent);


		createCompetition = new FancyButton("Create competition",new Color(51,51,51),new Color(170,170,170),new Color(150,150,150));
		createCompetition.setForeground(new Color(255, 255, 255));
		createCompetition.setBackground(new Color(0, 0, 0));
		createCompetition.setFont(new Font("Source Code Pro Light", Font.BOLD, 18));
		createCompetition.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!createCompetition.isSelected()) {
					createQuestion.setSelected(false);
					createEvent.setSelected(false);
					createCompetition.setSelected(true);
					contentPanel.removeAll();
					contentPanel.add(new CreateCompetitionPanel());
					contentPanel.repaint();
					contentPanel.revalidate();
				}		
			}
		});
		topMenuPanel.add(createCompetition);

		contentPanel = new JPanel();
		GridBagConstraints gbc_contentPanel = new GridBagConstraints();
		gbc_contentPanel.fill = GridBagConstraints.BOTH;
		gbc_contentPanel.gridx = 0;
		gbc_contentPanel.gridy = 1;
		add(contentPanel, gbc_contentPanel);
		contentPanel.setLayout(new CardLayout(0, 0));

		createQuestion.doClick();
	}

	public void refreshPage() {

		for(Component c : contentPanel.getComponents()) {
			if(c instanceof BettingHistoryPanel) {
				//((BettingHistoryPanel)c).refreshPage();
			}
			else if(c instanceof OpenBetsPanel) {
				((OpenBetsPanel)c).refreshPage();
			}
		}
	}
}

