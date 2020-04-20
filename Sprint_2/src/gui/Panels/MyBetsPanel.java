package gui.Panels;

import javax.swing.JPanel;

import gui.Panels.subpanels.BettingHistoryPanel;
import gui.Panels.subpanels.OpenBetsPanel;
import gui.components.FancyButton;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.CardLayout;

public class MyBetsPanel extends JPanel {


	private FancyButton historyBetButton;
	private FancyButton openBetsButton;
	private JPanel contentPanel;

	/**
	 * Create the panel.
	 */
	public MyBetsPanel() {

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

		openBetsButton = new FancyButton("Open bets",new Color(51,51,51),new Color(170,170,170),new Color(150,150,150));
		openBetsButton.setBackground(new Color(0, 0, 0));
		openBetsButton.setForeground(new Color(255, 255, 255));
		openBetsButton.setFont(new Font("Source Code Pro Light", Font.BOLD, 18));
		openBetsButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!openBetsButton.isSelected()) {
					openBetsButton.setSelected(true);
					historyBetButton.setSelected(false);
					contentPanel.removeAll();
					contentPanel.add(new OpenBetsPanel());
					contentPanel.repaint();
					contentPanel.revalidate();
				}		
			}
		});
		topMenuPanel.setLayout(new GridLayout(0, 2, 0, 0));
		topMenuPanel.add(openBetsButton);

		historyBetButton = new FancyButton("Betting history",new Color(51,51,51),new Color(170,170,170),new Color(150,150,150));
		historyBetButton.setForeground(new Color(255, 255, 255));
		historyBetButton.setBackground(new Color(0, 0, 0));
		historyBetButton.setFont(new Font("Source Code Pro Light", Font.BOLD, 18));
		historyBetButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!historyBetButton.isSelected()) {
					openBetsButton.setSelected(false);
					historyBetButton.setSelected(true);
					contentPanel.removeAll();
					contentPanel.add(new BettingHistoryPanel());
					contentPanel.repaint();
					contentPanel.revalidate();
				}		
			}
		});
		topMenuPanel.add(historyBetButton);

		contentPanel = new JPanel();
		GridBagConstraints gbc_contentPanel = new GridBagConstraints();
		gbc_contentPanel.fill = GridBagConstraints.BOTH;
		gbc_contentPanel.gridx = 0;
		gbc_contentPanel.gridy = 1;
		add(contentPanel, gbc_contentPanel);
		contentPanel.setLayout(new CardLayout(0, 0));

		openBetsButton.doClick();
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
